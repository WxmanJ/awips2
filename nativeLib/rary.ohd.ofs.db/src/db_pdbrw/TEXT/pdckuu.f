C MODULE PDCKUU
C-----------------------------------------------------------------------
C
      SUBROUTINE PDCKUU (IXD,IXJ,ISLOT,ISTAT)
C
C   THIS ROUTINE SEARCHES A DAILY DATA FILE POINTER RECORD FOR
C   AN UNUSED SLOT FOR THE TYPE IN DIRECTORY SUBSCRIPT IXD.
C   AN UNUSED SLOT FOR VR DATA WILL BE IN IDDTDR(22,IXD).
C   IT MUST READ THROUGH THE POINTER RECORD LOOKING FOR A SET OF
C   POINTERS ALL SET TO -1.
C
C   ARGUMENT LIST:
C
C       NAME     TYPE   I/O   DIM   DESCRIPTION
C       ------   ----   ---   ---   -----------
C       IXD        I     I    1    SUBSCRIPT OF DAILY DATA DIRECTORY
C       IXJ        I     I    1    SUBSCRIPT OF WRITE TYPE FOR PPVR
C       ISLOT      I     O    1    SLOT NUMBER OR ZERO IF NONE
C       ISTAT      I     O    1    STATUS CODE:
C                                    0=OK
C                                    1=READ ERROR
C
      INCLUDE 'uio'
      INCLUDE 'udebug'
      INCLUDE 'pdbcommon/pdsifc'
      INCLUDE 'pdbcommon/pddtdr'
      INCLUDE 'pdbcommon/pdbdta'
      INCLUDE 'pdbcommon/pdunts'
C
      INTEGER*2 IPTREC(32),ISLOT
C
C    ================================= RCS keyword statements ==========
      CHARACTER*68     RCSKW1,RCSKW2
      DATA             RCSKW1,RCSKW2 /                                 '
     .$Source: /fs/hseb/ob72/rfc/ofs/src/db_pdbrw/RCS/pdckuu.f,v $
     . $',                                                             '
     .$Id: pdckuu.f,v 1.2 1999/04/26 11:43:44 page Exp $
     . $' /
C    ===================================================================
C
C
      IF (IPDTR.GT.0) WRITE (IOGDB,10)
10    FORMAT (' ENTER PDCKUU')
C
      IF (IPDDB.GT.0) WRITE (IOGDB,*) 'IXD=',IXD
C
      LRCDD2=LRCPDD*2
C
C  SET NUMBER OF SLOTS TO CHECK - IF PPVR OR TAVR JUST CHECK 1
      NUMSLT=IDDTDR(5,IXD)
      NS=NUMSLT
      IF (IDDTDR(6,IXD).LT.0) NS=1
      IREC=IDDTDR(14,IXD)
      IFILE=IDDTDR(4,IXD)
      ISLOT=0
      ISLOT1=0
C
C  READ THE FIRST RECORD
      CALL UREADT (KPDDDF(IFILE),IREC,IPTREC,ISTAT)
      IF (ISTAT.NE.0) GO TO 160
C
      I=1
      K=1
30    IF (IPDDB.GT.0) WRITE (IOGDB,40) I,K,NUMSLT,IREC,IPTREC(K)
40    FORMAT (' I=',I4,3X,'K=',I4,3X,'NUMSLT=',I4,4X,'IREC=',I4,3X,
     *   'IPTREC(K)=',I6)
C
      DO 60 J=1,NS
         IF (IPTREC(K).NE.0) GO TO 110
         IF (K.LT.LRCDD2) GO TO 50
         IF (J.EQ.NS) GO TO 70
C     READ NEXT RECORD
         IREC=IREC+1
         CALL UREADT (KPDDDF(IFILE),IREC,IPTREC,ISTAT)
         IF (ISTAT.NE.0) GO TO 160
         K=0
50       K=K+1
60       CONTINUE
C
C  IF PPVR OR TAVR, CHECK DATA TIME INTERVAL TO SEE IF CAN REPLACE DATA
70    IF (IDDTDR(6,IXD).GT.0) GO TO 100
C
C SAVE SLOT FOUND INCASE CANT DO DATA
      IF (ISLOT1.EQ.0) ISLOT1=I
C
C  CHECK DATA TIME INTERVAL NOW
      IVALUE=0
      IF (IDDTDR(5,IXD).EQ.4) CALL PGTNXP (IXD,K,IPTREC,IREC,ISTAT)
      IF (IPDDB.GT.0) WRITE (IOGDB,80) K,IPTREC(K)
80    FORMAT (' K=',I6,3X,'IPTREC=',I6)
      IF (IPTREC(K).EQ.IDDTDR(5,IXJ)) GO TO 90
C
C  NO DATA TIME INTERVAL MATCH
      GO TO 110
C
C  GOT A MATCH ON DATA TIME INTERVAL, REUSE BOTH
90    CALL PGTNXP (IXD,K,IPTREC,IREC,ISTAT)
      IDDTDR(22,IXD)=IPTREC(K)
C
C ALL THROUGH LOOP SO FOUND AN EMPTY SLOT
100   ISLOT=I
      GO TO 180
C
C  SLOT NOT EMPTY - TRY NEXT. CHECK FOR MAXIMUM SLOTS AVAILABLE.
110   I=I+NUMSLT
      IF (IPDDB.GT.0) WRITE (IOGDB,120) I,IDDTDR(16,IXD),IDDTDR(5,IXD)
120   FORMAT (' I=',I4,3X,'IDDTDR(16,IXD)=',I4,3X,
     *   'IDDTDR(5,IXD)=',I4)
      IF (I.GT.IDDTDR(16,IXD)*IDDTDR(5,IXD)) GO TO 150
      L=MOD(I,LRCDD2)
      IF (IPDDB.GT.0) WRITE (IOGDB,130) L,K
130   FORMAT (' L=',I4,3X,'K=',I4)
      IF (L.EQ.0) L=LRCDD2
      IF (L.GT.K) GO TO 140
C
C MUST READ ANOTHER RECORD
      IREC=IREC+1
      CALL UREADT (KPDDDF(IFILE),IREC,IPTREC,ISTAT)
      IF (ISTAT.NE.0) GO TO 160
140   K=L
      GO TO 30
C
C  CAN NOT FIND SLOT WITH SAME DATA TIME INTERVAL OR ONE AT ALL
C  IF DATA TIME INTERVAL PROBLEM - USE THAT SLOT
150   IF (ISLOT1.NE.0.AND.ISLOT.EQ.0) ISLOT=ISLOT1
      GO TO 180
C
C READ ERROR
160   WRITE (LP,170) IFILE,ISTAT
170   FORMAT ('0**ERROR** IN PDCKUU - READING DAILY FILE ',I2,
     *   ' STATUS=',I4)
C
180   IF (IPDTR.GT.0) WRITE (IOGDB,190) IDDTDR(2,IXD),
     *   IDDTDR(3,IXD),ISLOT,IDDTDR(22,IXD),ISTAT
190   FORMAT (' EXIT PDCKUU - TYPE=',2A2,' SLOT=',I4,
     *   ' WD22=',I4,' STATUS=',I4)
C
      RETURN
C
      END
