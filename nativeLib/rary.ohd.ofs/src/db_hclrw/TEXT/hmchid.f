C MODULE HMCHID
C-----------------------------------------------------------------------
C
      SUBROUTINE HMCHID (ITYPE,NAME,NIDSET,IFLAG)
C
C  THIS ROUTINE TRIES TO MATCH NAME WITH THE IDENTIFIERS IN THE
C  OPTION RECORD.
C
C  IF THE MATCH IS TO INCLUDE, IFLAG=1
C  IF THE MATCH IS TO EXCLUDE, IFLAG=-1
C  IF THERE IS NO MATCH, IFLAG=2 FOR EXCLUDES AND -2 FOR INCLUDES
C
      DIMENSION NAME(2)
C
      INCLUDE 'uiox'
      INCLUDE 'udebug'
      INCLUDE 'hclcommon/hseg1'
      INCLUDE 'hclcommon/hunits'
C
C    ================================= RCS keyword statements ==========
      CHARACTER*68     RCSKW1,RCSKW2
      DATA             RCSKW1,RCSKW2 /                                 '
     .$Source: /fs/hseb/ob72/rfc/ofs/src/db_hclrw/RCS/hmchid.f,v $
     . $',                                                             '
     .$Id: hmchid.f,v 1.2 2001/06/13 13:42:46 dws Exp $
     . $' /
C    ===================================================================
C
C
      NS=0
C
C  CHECK IDENTIFIERS - NXOPT IS POINTING AT TYPE WORD
10    CALL HINCNX
      IF (ISTAT.NE.0) GO TO 180
      NS=NS+1
      KEY=IOPTRC(NXOPT)
      ISET=1
C
C  IF KEY IS NEGATIVE, THIS IS EXCLUDES
      IF (KEY.LT.0) ISET=-1
      IF (KEY.GT.1.OR.KEY.LT.-1) GO TO 70
C
C  THIS IS A RANGE - CHECK IF NAME MATCHES FIRST
      CALL HINCNX
      IF (ISTAT.NE.0) GO TO 180
      NN=2
      IF (IOPTRC(NXOPT).NE.NAME(1)) GO TO 40
      NN=1
      CALL HINCNX
      IF (ISTAT.NE.0) GO TO 180
      IF (IOPTRC(NXOPT).NE.NAME(2)) GO TO 40
C
C  MATCHES FIRST NAME, SET FLAG AND TURN ON RANGE FLAG
      DO 20 I=1,3
         CALL HINCNX
         IF (ISTAT.NE.0) GO TO 180
20       CONTINUE
C
      IOPTRC(NXOPT)=1
C
C  REWRITE THIS RECORD
30    CALL UWRITT (KHDFLT,NUMRED,IOPTRC,ISTAT)
      NSKIP=0
      GO TO 110
C
C  NOT MATCH OF FIRST, TRY SECOND - FIRST SKIP TO IT
40    DO 50 I=1,NN
         CALL HINCNX
         IF (ISTAT.NE.0) GO TO 180
50       CONTINUE
C
C  IF RANGE FLAG IS OFF AND DID NOT MATCH FIRST, NO MORE CHECKS
C  NEED TO GET BOTH WORDS OF NAME AND RANGE FLAG TO MAKE ALL CHECKS
      IRNAM1=IOPTRC(NXOPT)
      CALL HINCNX
      IRNAM2=IOPTRC(NXOPT)
      CALL HINCNX
      IRFLAG=IOPTRC(NXOPT)
      IF (IRFLAG.EQ.0) GO TO 60
      IF (IRNAM1.NE.NAME(1)) GO TO 60
      IF (IRNAM2.NE.NAME(2)) GO TO 60
C
C  MATCH OF SECOND - TURN OFF RANGE FLAG AND SET FLAG
      IOPTRC(NXOPT)=0
      GO TO 30
C
C  NO MATCH - TRY RANGE FLAG
60    IF (IOPTRC(NXOPT).NE.1) GO TO 100
      IFLAG=ISET
      GO TO 100
C
C  A LIST OF IDENTIFIERS
70    NIDS=IABS(KEY)-1
C
C  COMPARE ALL THE NAMES
      DO 90 I=1,NIDS
         CALL HINCNX
         IF (ISTAT.NE.0) GO TO 180
         IF (NAME(1).NE.IOPTRC(NXOPT)) GO TO 80
            CALL HINCNX
            IF (ISTAT.NE.0) GO TO 180
            IF (NAME(2).NE.IOPTRC(NXOPT)) GO TO 90
            NSKIP=(NIDS-I)*2
            GO TO 110
C     FIRST DID NOT MATCH - SKIP A WORD
80       CALL HINCNX
         IF (ISTAT.NE.0) GO TO 180
90       CONTINUE
C
C  NO MATCH - TRY ANOTHER SET - NIDSET IS THE NUMBER OF ID SETS
100   IF (NS.EQ.NIDSET) GO TO 170
      GO TO 10
C
C  MATCH - SET FLAG AND SKIP TO END OF IDS
110   IFLAG=ISET
120   IF (IHCLDB.GT.1) WRITE (LPD,130) NXOPT,NSKIP,NIDSET
130   FORMAT (' IN HMCHID - NXOPT=',I4,' NSKIP=',I4,' NIDSET=',I4)
C
C  NSKIP IS NUMBER OF WORDS TO SKIP TO GET TO END OF THIS ID SET
      IF (NSKIP.LT.1) GO TO 160
      IF (IHCLDB.GT.1) WRITE (LPD,140) NSKIP
140   FORMAT (' IN HMCHID - SKIPPING ',I3,' WORDS IN OPTION RECORD.')
      DO 150 I=1,NSKIP
         CALL HINCNX
         IF (ISTAT.NE.0) GO TO 180
150      CONTINUE
C
160   NS=NS+1
      IF (NS.GT.NIDSET) GO TO 180
      CALL HINCNX
      IF (ISTAT.NE.0) GO TO 180
      KEY=IABS(IOPTRC(NXOPT))
      IF (KEY.EQ.1) NSKIP=5
      IF (KEY.NE.1) NSKIP=(KEY-1)*2
      GO TO 120
C
C  NO MATCH
170   IF (IFLAG.NE.0) GO TO 180
      IFLAG=ISET*(-2)
C
180   IF (IHCLDB.GT.1) WRITE (LPD,190) ITYPE,NAME,IFLAG
190   FORMAT (' IN HMCHID - ITYPE=',I4,' NAME=',2A4,' IFLAG=',I4)
C
      RETURN
C
      END
