C MODULE FIDFLT
C-----------------------------------------------------------------------
C
C  THIS ROUTINE GETS THE USER DEFAULTS.
C
      SUBROUTINE FIDFLT

      INCLUDE 'common/fdbug'
      INCLUDE 'common/errdat'
      INCLUDE 'common/fcunit'
      INCLUDE 'common/fcrfc'
      INCLUDE 'common/ionum'
      INCLUDE 'common/fctim2'
      INCLUDE 'common/fcdflt'
      INCLUDE 'common/fctime'
      COMMON /HDFLTS/ IHDFLT(25)
C
      CHARACTER*8 TECHNAME
      PARAMETER (MTECHS=10)
      CHARACTER*8 TECHS(MTECHS)/
     *   'FUTPRECP','METRIC  ',
     *   'PRINTOUT','PLOTHYD ',
     *   'TABLES  ','SNOW    ',
     *   'UPWE    ','UPSC    ',
     *   'PRINTSNW','PRINTSMA'/
      DIMENSION IRFCNM(2),IRETA(7)
      EQUIVALENCE (IRFCNM(1),RFCNAM(1))
C
C    ================================= RCS keyword statements ==========
      CHARACTER*68     RCSKW1,RCSKW2
      DATA             RCSKW1,RCSKW2 /                                 '
     .$Source: /fs/hseb/ob72/rfc/ofs/src/db_hclrw/RCS/fidflt.f,v $
     . $',                                                             '
     .$Id: fidflt.f,v 1.3 2000/12/18 21:31:17 dws Exp $
     . $' /
C    ===================================================================
C
      DATA IAST/4H*   /
C
C
      IF (ITRACE.GE.1) WRITE (IODBUG,*) 'ENTER FIDFLT'
C
C  TRANSFER VALUES FROM /HDFLTS/ AND THE TECHNIQUES TO /FCDFLT/
      LOCAL=IHDFLT(22)
      IDEFLT(1)=LOCAL
      NLSTZ=IHDFLT(23)
      IDEFLT(4)=NLSTZ
      INPTZC=IHDFLT(3)
      IDEFLT(5)=INPTZC
      NHOPDB=IHDFLT(24)
      IDEFLT(21)=NHOPDB
      NHOCAL=IHDFLT(25)
      IDEFLT(22)=NHOCAL
      IDEFLT(23)=IHDFLT(7)
C
C  SET USER NAME
      IRFCNM(1)=IHDFLT(4)
      IRFCNM(2)=IHDFLT(5)
C
C  SET TODAY'S DATE FOR DATING FUNCTIONS
      CALL HSYSDA (JULDAT)
C
      IERR=0
C
C  GET TECHNIQUE VALUES
      TECHNAME='NOUTZ'
      CALL HPAST (TECHNAME,IVAL,IRC)
      CALL FPHPWN (IRC,TECHNAME)
      IERR=IERR+IRC
      NOUTZ=IVAL
      IDEFLT(2)=IVAL
      TECHNAME='NOUTDS'
      CALL HPAST (TECHNAME,IVAL,IRC)
      CALL FPHPWN (IRC,TECHNAME)
      IERR=IERR+IRC
      NOUTDS=IVAL
      IDEFLT(3)=IVAL
      DO 10 I=1,MTECHS
         CALL HPAST (TECHS(I),IVAL,IRC)
         CALL FPHPWN (IRC,TECHS(I))
         IERR=IERR+IRC
         IDEFLT(I+5)=IVAL
10       CONTINUE
C
C  GET ARGUMENT VALUES FOR CERTAIN TECHNIQUES
      DO 20 I=1,7
         IRETA(I)=-999
20       CONTINUE
      IHRRUN=-999
      IDEFLT(18)=-999
      IDEFLT(16)=-999
      IDEFLT(17)=-999
      IDEFLT(19)=-999
      IDEFLT(20)=-999
      LHRRUN=-999
      LHRCPD=-999
C
C  GET VALUES OF CMPDAYS AND IHRRUN FROM TECHNIQUE 'STARTRUN'
      TECHNAME='STARTRUN'
      CALL HPASTA (TECHNAME,7,IVAL,NRET,IRETA,IRC)
      CALL FPHPWN (IRC,TECHNAME)
      IERR=IERR+IRC
      IF (IRC.EQ.2) GO TO 30
         IHRRUN=IRETA(5)
         IF (IHRRUN.EQ.-1) CALL MDYH2 (10,0,MXM,MXD,MXY,IHRRUN,ITD,NDZ,
     *      INPTZC)
         IDEFLT(18)=IHRRUN
         IDEFLT(16)=IRETA(3)
         IF (IRETA(2).NE.IAST) IDEFLT(16)=-999
C
C  GET VALUES OF FUTDAYS AND LHRRUN FROM TECHNIQUE 'ENDRUN'
30    TECHNAME='ENDRUN'
      CALL HPASTA (TECHNAME,7,IVAL,NRET,IRETA,IRC)
      CALL FPHPWN (IRC,TECHNAME)
      IERR=IERR+IRC
      IF (IRC.EQ.2) GO TO 40
         LHRRUN=IRETA(5)
         IF (LHRRUN.EQ.-1) CALL MDYH2 (10,0,MXM,MXD,MXY,LHRRUN,ITD,NDZ,
     *      INPTZC)
         IDEFLT(20)=LHRRUN
         IDEFLT(17)=IRETA(3)
         IF (IRETA(2).NE.IAST) IDEFLT(17)=-999
C
C  GET VALUE OF LHRCPD FROM TECHNIQUE 'LSTCMPDY'
40    TECHNAME='LSTCMPDY'
      CALL HPASTA (TECHNAME,7,IVAL,NRET,IRETA,IRC)
      CALL FPHPWN (IRC,TECHNAME)
      IERR=IERR+IRC
      IF (IRC.EQ.2) GO TO 50
         LHRCPD=IRETA(5)
         IF (LHRCPD.EQ.-1) CALL MDYH2 (10,0,MXM,MXD,MXY,LHRCPD,ITD,NDZ,
     *      INPTZC)
         IDEFLT(19)=LHRCPD
C
50    IF (IERR.NE.0) THEN
         WRITE (IPR,60)
60    FORMAT ('0**ERROR** ERRORS ENCOUNTERED OBTAINING USER DEFAULTS.')
         CALL KILLPM ()
         ENDIF
C
      IF (ITRACE.GE.1) WRITE (IODBUG,*) 'EXIT FIDFLT'
C
      RETURN
C
      END
