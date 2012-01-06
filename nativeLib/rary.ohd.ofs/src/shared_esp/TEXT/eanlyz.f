C MODULE EANLYZ
C
C   THIS SUBROUTINE FILLS THE ACCUMULATOR ARRAY FOR THE PORTIONS
C   OF THE BASE PERIOD OUTSIDE TTHE RUN PERIOD, THEN IT CALLS
C   THE DISPLAY AND STATISTICAL PACKAGE DRIVERS.
C
C   THIS SUBROUTINE WAS WRITTEN BY GERALD N DAY.
C
      SUBROUTINE EANLYZ(IMONTH,IDAY,IHOUR,LCOND,IDIFFY,NYRS,
     1 NBYRS,TSESP,MTSESP,PESP,MPESP,SPESP,MSPESP,A,MA,D,IWKLOC,NWORK)
C
      INCLUDE 'common/fdbug'
      INCLUDE 'common/ionum'
      INCLUDE 'common/where'
      INCLUDE 'common/fctime'
      INCLUDE 'common/etime'
      INCLUDE 'common/eswtch'
      INCLUDE 'common/espseg'
      INCLUDE 'common/esprun'
C
      DIMENSION TSESP(1),PESP(1),SPESP(1),A(1),D(1),NDAYS(12)
      DIMENSION SBNAME(2),OLDOPN(2)
C
C    ================================= RCS keyword statements ==========
      CHARACTER*68     RCSKW1,RCSKW2
      DATA             RCSKW1,RCSKW2 /                                 '
     .$Source: /fs/hseb/ob72/rfc/ofs/src/shared_esp/RCS/eanlyz.f,v $
     . $',                                                             '
     .$Id: eanlyz.f,v 1.2 1998/07/06 11:35:40 page Exp $
     . $' /
C    ===================================================================
C
C
      DATA  SBNAME / 4hEANL,4hYZ   /
      DATA NDAYS/31,28,31,30,31,30,31,31,30,31,30,31/
C
      IOLDOP=IOPNUM
      IOPNUM=0
      DO 10 I=1,2
      OLDOPN(I)=OPNAME(I)
   10 OPNAME(I)=SBNAME(I)
C
      IF(ITRACE.GE.1) WRITE(IODBUG,900)
  900 FORMAT(1H0,17H** EANLYZ ENTERED)
C
      IF(LPESP.EQ.0) GO TO 999
C
      IF(JBPS.EQ.0) GO TO 400
      IEPASS=4
C
C   SET UP BEGINNING OF BASE PERIOD
C
      KK=1
      IF(IBHYR.GE.IHYR) GO TO 100
      IY=IBHYR
      IF(LBHYR.GE.IHYR) GO TO 50
C
C   BASE PERIOD IS SEPARATE FROM HISTORICAL PERIOD
C
      LY=LBHYR
      GO TO 180
C
C   BASE PERIOD EXTENDS INTO HISTORICAL PERIOD
C
   50 LY=IHYR-IDIFFY
      IF(LY.GT.LBHYR) LY=LBHYR
      GO TO 180
C
C   SET UP END OF BASE PERIOD
C
  100 KK=2
      LY=LBHYR
      IF(IBHYR.GT.LHYR) GO TO 150
C
C   BASE PERIOD IS PART OF HISTORICAL PERIOD
C
      IY=IHYR+NYRS
      IF(IY.LT.IBHYR) IY=IBHYR
      IF(IY.GT.LBHYR) GO TO 400
      GO TO 180
C
C   BASE PERIOD IS SEPARATE FROM HISTORICAL PERIOD
C
  150 IY=IBHYR
C
C   SET UP ACCUMULATOR LOOP FOR BASE PERIOD
C
  180 NYRSP=LY-IBHYR+IDIFFY
      KNTYR=IY-IBHYR+1
      ISTART=KNTYR
  200 NM=IMONTH
      NDAY=IDAY
      NY=IY+KNTYR-ISTART
      IF(IMONTH.GE.10) NY=IY-1
C
C   CALCULATE THE FIRST DAY OF THE LOOP
C
      IF(NM.NE.2.OR.NDAY.NE.29) GO TO 205
      IF(NY.EQ.(4*(NY/4))) GO TO 205
      NDAY=28
  205 CALL FCTZC(100,0,TZC)
      CALL JULDA(IDLOOP,IHLOOP,NM,NDAY,NY,IHOUR,100,0,TZC)
C
C   CALCULATE LAST DAY OF LOOP
C
      LJDCON=IDLOOP+LCOND
C
C   RESET IDLOOP
C
      IF(IHLOOP.LT.24) GO TO 210
      IDLOOP=IDLOOP+1
      IHLOOP=0
  210 CALL FCTZC(100,0,TZC)
      CALL JULDA(IDA,IHZERO,NM,1,NY,24,100,0,TZC)
      IHZERO=0
      IDADAT=IDA
      LDARUN=LJDCON
C
      LDA=IDA+NDAYS(NM)-1
      LHR=24
      IF(NM.EQ.2.AND.NY.EQ.(4*(NY/4))) LDA=LDA+1
C
      IF(IDA.GT.IDLOOP) GO TO 215
      IDA=IDLOOP
      IHZERO=IHLOOP
C
  215 IF(LDA.LT.LJDCON) GO TO 220
      LDA=LJDCON
      LHR=LHRRUN
C
C   GET DATA
C
  220 CALL ETSRD(TSESP,MTSESP,D,IWKLOC,NWORK,IDLOOP,LJDCON,IHZERO,KNTYR,
     1 NYRSP,IERR)
C
      IF(IERR.EQ.0) GO TO 230
      WRITE(IPR,600) ISEGEX
  600 FORMAT(1H0,10X,45H**ERROR IN LOADING TS DATA FOR SEGMENT NUMBER,
     1 I6)
      CALL ERROR
      GO TO 999
C
  230 CALL EACDRV(PESP,MPESP,IDLOOP,KNTYR,A,MA,D,IWKLOC,NWORK,NYRSP,
     1 IHZERO)
C
      NM=NM+1
      IF(NM.LE.12) GO TO 240
      NM=1
      NY=NY+1
  240 IF(LDA.LT.LJDCON) GO TO 210
      KNTYR=KNTYR+1
      IF(KNTYR.LE.NYRSP) GO TO 200
C
  250 IF(KK.LT.2) GO TO 100
C
C   CALL DISPLAY DRIVER
C
  400 CALL EDSDRV(PESP,MPESP,A,NYRS,NBYRS,D)
C
C   CALL STATISTICS PACKAGE DRIVER
C
  999 CONTINUE
      IOPNUM=IOLDOP
      OPNAME(1)=OLDOPN(1)
      OPNAME(2)=OLDOPN(2)
C
      RETURN
      END
