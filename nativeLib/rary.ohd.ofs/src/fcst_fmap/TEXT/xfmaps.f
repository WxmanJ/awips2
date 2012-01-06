C MODULE XFMAPS
C-----------------------------------------------------------------------
C
      SUBROUTINE XFMAPS (NUMDAY,IFFHR,ILFHR,IFFPRD,ILFPRD,IERR)
C
C  *********************************************************************
C
C  XMAPS SIZES THE FUTURE PRECIP ARRAY DEPENDING UPON THE AMOUNT OF
C  SPACE AVAILABLE IN THE PROCESSED DATA BASE.
C
C  *********************************************************************
C
C  IMPORTANT VARIABLES INCLUDE:
C
C     IARGC = ARGUMENT VALUES OF THE LSTCMPDY TECHNIQUE
C     IFFHR = HOUR(LSTCMPDY) 0,6,12,18
C     ILFHR = HOUR(ENDRUN)  0,6,12,18
C    IFFPRD = LAST COMPUTATIONAL JULIAN HOUR (INTERNAL TIME)
C    ILFPRD = ENDRUN JULIAN HOUR
C    NUMDAY = NUMBER OF DAYS IN THE RUN
C
      CHARACTER*8 OLDOPN,TECHNAME
      PARAMETER (LIARGC=7)
      DIMENSION IARGC(LIARGC)
C
      INCLUDE 'common/ionum'
      INCLUDE 'common/fctime'
      INCLUDE 'common/fctim2'
      INCLUDE 'common/pudbug'
      COMMON /XFDATE/ IFEM,IFED,IFEY,IFEH,ILEM,ILED,ILEY,ILEH,IM(31),
     *   ID(31),IY(31),IH(4),IOPTZC
C
C    ================================= RCS keyword statements ==========
      CHARACTER*68     RCSKW1,RCSKW2
      DATA             RCSKW1,RCSKW2 /                                 '
     .$Source: /fs/hseb/ob72/rfc/ofs/src/fcst_fmap/RCS/xfmaps.f,v $
     . $',                                                             '
     .$Id: xfmaps.f,v 1.4 2002/02/11 20:37:40 dws Exp $
     . $' /
C    ===================================================================
C
C
      IOPNUM=-1
      CALL FSTWHR ('XFMAPS  ',IOPNUM,OLDOPN,IOLDOP)
C
      IF (IPTRCE.GT.0) WRITE (IOPDBG,*) 'ENTER XFMAPS'
C
      IBUG=IPBUG('XFS ')
C
C  FIND THE FIRST DAY OF THE FUTURE PERIOD
      TECHNAME='LSTCMPDY'
      CALL HPASTA (TECHNAME,LIARGC,ITVAL,NWORDS,IARGC,ISTAT)
      IF (ISTAT.NE.0) THEN
         WRITE (IPR,150) TECHNAME
         CALL ERROR
         IERR=1
         IF (IBUG.GT.0) WRITE (IOPDBG,160) TECHNAME,LIARGC,NWORDS
         GO TO 100
         ENDIF
      IFFPRD=IARGC(1)
      IFFPRD=(IFFPRD/6)*6
      IFFHR=MOD(IFFPRD,24)
C
      IF (IBUG.GT.0) WRITE (IOPDBG,*) 'IFFPRD=',IFFPRD,' IFFHR=',IFFHR
C
C  FIND THE END DATE FOR THE RUN
      TECHNAME='ENDRUN'
      CALL HPASTA (TECHNAME,LIARGC,ITVAL,NWORDS,IARGC,ISTAT)
      IF (ISTAT.NE.0) THEN
         WRITE (IPR,150) TECHNAME
         CALL ERROR
         IERR=1
         IF (IBUG.GT.0) WRITE (IOPDBG,160) TECHNAME,LIARGC,NWORDS
         GO TO 100
         ENDIF
      ILFPRD=IARGC(1)
      ILFPRD=(ILFPRD/6)*6
      ILFHR=MOD(ILFPRD,24)
      IF (IBUG.GT.0) WRITE (IOPDBG,*) 'ILFPRD=',ILFPRD,'ILFHR=',ILFHR
C
C  CHECK THE SPACE AVAILABLE IN THE PROCESSED DATA BASE
      NDYPDB=IPRDMF('MAP ')
      IF (NDYPDB.EQ.0) THEN
         WRITE (IPR,180)
         CALL ERROR
         IERR=1
         IF (IBUG.GT.0) WRITE (IOPDBG,*) 'NDYPDB=',NDYPDB
         GO TO 100
         ENDIF
C
      LDA=ILFPRD/24
      IF (ILFHR.GT.0) LDA=LDA+1
      IDA=(IFFPRD-IFFHR)/24
C
      NUMDAY=LDA-IDA
      IF (NUMDAY.GT.NDYPDB) THEN
         NUMDAY=NDYPDB
         ILFPRD=IDA*24+NUMDAY*24
         ILFHR=0
         WRITE (IPR,170) NUMDAY
         CALL WARN
         ENDIF
C
      MAXDAY=31
      IF (NUMDAY.GT.MAXDAY) THEN
         NUMDAY=MAXDAY
         ILFPRD=IDA*24+744
         ILFHR=0
         WRITE (IPR,130) MAXDAY
         CALL WARN
         ENDIF
C
C  FILL XFDATE COMMON AND PRINT BANNER PAGE
      IDA=IFFPRD/24+1
      LDA=(ILFPRD-1)/24+1
      LHR=ILFHR
      IF (LHR.EQ.0) LHR=24
      CALL MDYH2 (IDA,IFFHR,IFEM,IFED,IFEY,IFEH,I,J,INPTZC)
      CALL MDYH2 (LDA,LHR,ILEM,ILED,ILEY,ILEH,I,J,INPTZC)
      CALL UPAGE (IPR)
      WRITE (IPR,210)
      WRITE (IPR,220) IFEM,IFED,IFEY,IFEH,INPTZC,
     *                ILEM,ILED,ILEY,ILEH,INPTZC,            
     *                IFEM,IFED,IFEY,IFEH,INPTZC
      JDA=IDA
      DO 80 I=1,NUMDAY
         CALL MDYH1 (JDA,24,IM(I),ID(I),IY(I),IH(4),NOUTZ,NOUTDS,IOPTZC)
         JDA=JDA+1
80       CONTINUE
      DO 90 I=1,3
         IH(I)=IH(4)-((4-I)*6)
         IF (IH(I).LT.1) IH(I)=IH(I)+24
90       CONTINUE
C
100   CALL FSTWHR (OLDOPN,IOLDOP,OLDOPN,IOLDOP)
C
      IF (IPTRCE.GT.0) WRITE (IOPDBG,*) 'EXIT XFMAPS'
C
      RETURN
C
C- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
C
130   FORMAT ('0**WARNING** THE FMAP FUNCTION CAN ONLY ',
     *   'PROCESS A MAXIMUM OF ',I2,' DAYS. REMAINING DAYS TRUNCATED.')
150   FORMAT ('0**ERROR** ARGUMENT ARRAY DIMENSIONED TOO ',
     *   'SMALL OR TECHNIQUE ',A,' NOT FOUND.')
160   FORMAT (' ARGUMENT ARRAY FOR TECHNIQUE ',A,' HAS ',
     *   'DIMENSION OF ',I2,' ACTUAL NUMBER OF WORDS FOR THE ',
     *   'ARGUMENTS ARE ',I3,'.')
170   FORMAT ('0**WARNING** THE RUN PERIOD SPECIFIED IS ',
     *      'LONGER THAN THE NUMBER OF DAYS AVAILABLE ',
     *      'IN THE PROCESSED DATA BASE FOR FUTURE MAP.' /
     *   13X,'SPACE IS AVAILABLE FOR ',I3,' DAYS. ',
     *       'REMAINING DAYS TRUNCATED.')
180   FORMAT ('0**ERROR** FUTURE DATA TYPE MAP NOT FOUND OR ',
     *   'NO FUTURE DATA TYPE FOR MAP.')
210   FORMAT ('0',45X,'FMAP FUNCTION')
220   FORMAT ('0',30X,'RUN PERIOD',2X,
     *      I2.2,'/',I2.2,'/',I4.4,'-',I2.2,A4,' THRU ',
     *      I2.2,'/',I2.2,'/',I4.4,'-',I2.2,A4 /
     *   '0',30X,'OBSERVED DATA ENDS AT ',
     *      I2.2,'/',I2.2,'/',I4.4,'-',I2.2,A4)
C
      END
