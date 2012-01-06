C MEMBER TS1626
C  (from old member FCTS1626)
C
      SUBROUTINE TS1626(WORK,IUSEW,LEFTW,NTS16,NEEDEP,LOCWHC,IT2,
     .            LENDSU,JDEST,IERR)
C                             LAST UPDATE: 01/06/94.11:41:52 BY $WC21DT
C
C--------------------------------------------------------------------
C  ROUTINE TO GET AND CHECK TS SPECIFICATIONS FOR S/U #16 -
C  RAIN/EVAP ON RESERVOIR UTILITY
C---------------------------------------------------------------------
C  JTOSTROWSKI - HRL - MARCH 1983
C----------------------------------------------------------------
C
      INCLUDE 'common/comn26'
      INCLUDE 'common/err26'
      INCLUDE 'common/fld26'
      INCLUDE 'common/read26'
      INCLUDE 'common/suky26'
      INCLUDE 'common/warn26'
C
      DIMENSION TSID(2,3),TSKEY(3),LKEY(3),IDT(3),DTYPE(3),IT(3)
      DIMENSION WORK(1)
C
      LOGICAL TS1OK,TS2OK,TS3OK,ALLOK,ENDFND,NEEDEP
C
C    ================================= RCS keyword statements ==========
      CHARACTER*68     RCSKW1,RCSKW2
      DATA             RCSKW1,RCSKW2 /                                 '
     .$Source: /fs/hseb/ob72/rfc/ofs/src/fcinit_res/RCS/ts1626.f,v $
     . $',                                                             '
     .$Id: ts1626.f,v 1.1 1995/09/17 18:53:20 dws Exp $
     . $' /
C    ===================================================================
C
C
      DATA TSKEY/4HPCPN,4HEVAP,4HADDQ/
      DATA LKEY/1,1,1/
      DATA NKEY/3/
      DATA NDKEY/1/
C
      DATA BLANK/4H    /
C
C  INITIALIZE LOCAL VARIABLES AND COUNTERS
C
      ALLOK = .TRUE.
      TS1OK = .FALSE.
      ENDFND = .FALSE.
      TS2OK = .TRUE.
      TS3OK = .TRUE.
      NTS16 = 0
C
      DO 3 I = 1,3
           IT(I) = 0
    3 CONTINUE
C
      IERR = 0
C
C  TS FOUND, LOOKING FOR ENDT
C
      LPOS = LSPEC + NCARD + 1
      LASTCD = LENDSU
      IBLOCK = 1
C
    5 IF (NCARD .LT. LASTCD) GO TO 8
           CALL STRN26(59,1,SUKYWD(1,9),3)
           IERR = 99
           GO TO 9
    8 NUMFLD = 0
      CALL UFLD26(NUMFLD,IERF)
      IF(IERF .GT. 0 ) GO TO 9000
      NUMWD = (LEN -1)/4 + 1
      IDEST = IKEY26(CHAR,NUMWD,SUKYWD,LSUKEY,NSUKEY,NDSUKY)
      IF (IDEST.EQ.0) GO TO 5
C
C  IDEST = 9 IS FOR ENDT
C
      IF (IDEST.EQ.9.OR.IDEST.EQ.10) GO TO 9
          CALL STRN26(59,1,SUKYWD(1,9),3)
          JDEST = IDEST
          IERR = 89
    9 LENDT = NCARD
C
C  ENDT CARD OR PARMS OR CO FOUND AT LENDT,
C  ALSO ERR RECOVERY IF NEITHER ONE OF THEM FOUND.
C
C
      IBLOCK = 2
      CALL POSN26(MUNI26,LPOS)
      NCARD = LPOS - LSPEC -1
C
   10 CONTINUE
      NUMFLD = 0
      CALL UFLD26(NUMFLD,IERF)
      IF(IERF .GT. 0) GO TO 9000
      NUMWD = (LEN -1)/4 + 1
      IDEST = IKEY26(CHAR,NUMWD,TSKEY,LKEY,NKEY,NDKEY)
      IF(IDEST .GT. 0) GO TO 50
      IF(NCARD .GE. LENDT) GO TO 900
C
C  NO VALID KEYWORD FOUND
C
      CALL STER26(1,1)
      ALLOK = .FALSE.
      GO TO 10
C
C  NOW SEND CONTROL TO PROPER LOCATION FOR PROCESSING EXPECTED INPUT
C
   50 CONTINUE
      NPR = IDEST
      GO TO (100,200,300), NPR
C
C----------------------------------------------------------------------
C  'PCPN' FOUND. GET THE TS INFO FROM THE REST OF THE LINE.
C
  100 CONTINUE
      IT(1) = IT(1) + 1
      IF (IT(1).GT.1) CALL STER26(39,1)
C
      CALL TSID26(TSID(1,NPR),DTYPE(NPR),IDT(NPR),TS1OK)
      IF (.NOT.TS1OK) GO TO 10
C
C  TIME INTERVAL MUST BE EQUAL TO THE OPERATION TIME INTERVAL.
C
      TS1OK = .FALSE.
      IF (IDT(NPR).EQ.MINODT) GO TO 140
C
      CALL STER26(47,1)
      GO TO 10
C
C  SEE IF TIME SERIES EXISTS
C
  140 CONTINUE
      CALL CHEKTS(TSID(1,NPR),DTYPE(NPR),IDT(NPR),0,DCODE,0,1,IERCK)
      IF (IERCK.EQ.0) GO TO 145
C
      CALL STER26(45,1)
      GO TO 10
C
C  CHECK UNITS FOR THIS TIME-SERIES.
C
  145 CONTINUE
      CALL FDCODE(DTYPE(NPR),UCODE,X,IX,IX,X,IX,IERD)
      IF (UCODE.EQ.UMM) GO TO 150
C
      CALL STRN26(93,1,UCODE,1)
      GO TO 10
C
C  CHECK DIMENSIONS AGAINST ALLOWED FOR THIS TIME SERIES.
C
  150 CONTINUE
      IF (DCODE.EQ.DIML) GO TO 160
C
      CALL STER26(46,1)
      GO TO 10
C
C  SEE IF THIS TIME SERIES WAS ALREADY DEFINED, AND ADD ID TO LIST
C  OF INPUT TIME-SERIES IF IT'S NEW.
C
  160 CONTINUE
      IO = 0
      CALL MLTS26(TSID(1,NPR),DTYPE(NPR),IDT(NPR),IO,IERM)
      IF (IERM.GT.0) GO TO 10
C
C  INPUT IS OK
C
  190 CONTINUE
      TS1OK = .TRUE.
      GO TO 10
C
C----------------------------------------------------------------------
C  'EVAP' EXPECTED. IF THIS KEYWORD IS FOUND, GET INFO FROM THE
C   THE LINE.
C
C  IF NOT FOUND, NO PROBLEM, IT JUST IS NOT TO BE USED.
C  IF NOT ALLOWED, AND FOUND THIS IS AN ERROR.
C
  200 CONTINUE
      IT(2) = IT(2) + 1
      IF (IT(2).GT.1) CALL STER26(39,1)
      IF (NEEDEP) GO TO 210
           CALL STRN26(60,1,TSKEY(NPR),LKEY(NPR))
C
C  GET REST OF TS SPECIFICATION
C
  210 CONTINUE
C
C  MUST INDICATE THAT EVAP TIME-SERIES IS FOUND IN WORK ARRAY
C
      CALL RFIL26(WORK,LOCWHC,1.01)
      CALL TSID26(TSID(1,NPR),DTYPE(NPR),IDT(NPR),TS2OK)
      IF (.NOT.TS2OK) GO TO 10
C
C  SEE IF TIME INTERVAL IS EQUAL TO OPERATION TIME INTERVAL.
C
      TS2OK = .FALSE.
C  THE FOLLOWING CHANGE MADE ON 10/17/90
C      IF (IDT(NPR).EQ.MINODT) GO TO 240
C      CALL STER26(47,1)
      IF (IDT(NPR).EQ.24) GO TO 240
      CALL STER26(104,1)
C  END OF CHANGE OF 10/17/90
      GO TO 10
C
C  SEE IF TIME SERIES EXISTS
C
  240 CONTINUE
      CALL CHEKTS(TSID(1,NPR),DTYPE(NPR),IDT(NPR),0,DCODE,0,1,IERCK)
      IF (IERCK.EQ.0) GO TO 245
C
      CALL STER26(45,1)
      GO TO 10
C
C  CHECK UNITS FOR THIS TIME-SERIES.
C
  245 CONTINUE
      CALL FDCODE(DTYPE(NPR),UCODE,X,IX,IX,X,IX,IERD)
      IF (UCODE.EQ.UMM) GO TO 250
C
      CALL STRN26(93,1,UCODE,1)
      GO TO 10
C
C  CHECK DIMENSIONS AGAINST ALLOWED FOR THIS TIME SERIES.
C
  250 CONTINUE
      IF (DCODE.EQ.DIML) GO TO 260
C
      CALL STER26(46,1)
      GO TO 10
C
C  SEE IF THIS TIME SERIES WAS ALREADY DEFINED, AND ADD ID TO LIST
C  OF INPUT TIME-SERIES IF IT'S NEW.
C
  260 CONTINUE
      IO = 0
      CALL MLTS26(TSID(1,NPR),DTYPE(NPR),IDT(NPR),IO,IERM)
      IF (IERM.GT.0) GO TO 10
C
C  INPUT IS OK
C
  290 CONTINUE
      TS2OK = .TRUE.
      GO TO 10
C
C----------------------------------------------------------------------
C  'ADDQ' FOUND. GET THE TS INFO FROM THE REST OF THE LINE.
C
  300 CONTINUE
C
      IT(3) = IT(3) + 1
      IF (IT(3).GT.1) CALL STER26(39,1)
C
C  GET REST OF TS SPECIFICATION.
C
      TS3OK = .FALSE.
C
      CALL TSID26(TSID(1,NPR),DTYPE(NPR),IDT(NPR),TS3OK)
      IF (.NOT.TS3OK) GO TO 10
      TS3OK = .FALSE.
C
C  SEE IF TIME INTERVAL IS MULTIPLE OF OPERATION TIME INTERVAL.
C
  330 CONTINUE
      IF (IDT(NPR).EQ.MINODT) GO TO 340
C
      CALL STER26(48,1)
      GO TO 10
C
C  SEE IF TIME SERIES EXISTS
C
  340 CONTINUE
      CALL CHEKTS(TSID(1,NPR),DTYPE(NPR),IDT(NPR),0,DCODE,0,1,IERCK)
      IF (IERCK.EQ.0) GO TO 345
C
      CALL STER26(45,1)
      GO TO 10
C
C  CHECK UNITS FOR THIS TIME-SERIES.
C
  345 CONTINUE
      CALL FDCODE(DTYPE(NPR),UCODE,X,IX,IX,X,IX,IERD)
      IF (UCODE.EQ.UCMSD) GO TO 350
C
      CALL STRN26(93,1,UCODE,1)
      GO TO 10
C
C  CHECK DIMENSIONS AGAINST ALLOWED FOR THIS TIME SERIES.
C
  350 CONTINUE
      IF (DCODE.EQ.DIML3) GO TO 360
C
      CALL STER26(46,1)
      GO TO 10
C
C  SEE IF THIS TIME SERIES WAS ALREADY DEFINED, AND ADD ID TO LIST
C  OF INPUT TIME-SERIES IF IT'S NEW.
C
  360 CONTINUE
      IO = 1
      CALL MLTS26(TSID(1,NPR),DTYPE(NPR),IDT(NPR),IO,IERM)
      IF (IERM.GT.0) GO TO 10
C
C  INPUT IS OK
C
      TS3OK = .TRUE.
      GO TO 10
C
C------------------------------------------------------------------
C  'ENDT' FOUND. STORE TS INFO IF INPUT WAS ERROR FREE
C
  900 CONTINUE
C
      IF (IT(1).EQ.0) CALL STRN26(59,1,TSKEY(1),LKEY(1))
C
      IF (ALLOK.AND.TS1OK.AND.TS2OK.AND.TS3OK) GO TO 1010
C
C  IF ERRORS OCCURRED, JUST EXIT
C
      GO TO 9999
C
 1010 CONTINUE
C
      DO 1050 NPR = 1,3
      IF (NPR.EQ.2.AND.IT(2).EQ.0) GO TO 1050
      IF (NPR.EQ.3.AND.IT(3).EQ.0) GO TO 1020
C
      IADD = 5
C
      DT = IDT(NPR) + 0.01
      CALL FLWK26(WORK,IUSEW,LEFTW,TSID(1,NPR),501)
      CALL FLWK26(WORK,IUSEW,LEFTW,TSID(2,NPR),501)
      CALL FLWK26(WORK,IUSEW,LEFTW,DTYPE(NPR),501)
      CALL FLWK26(WORK,IUSEW,LEFTW,DT,501)
C
      VIO = 0.01
      IF (NPR .EQ. 3) VIO = 1.01
C
      CALL FLWK26(WORK,IUSEW,LEFTW,VIO,501)
      GO TO 1030
C
C  JUST STORE BLANK ID
C
 1020 CONTINUE
      IADD = 2
      CALL FLWK26(WORK,IUSEW,LEFTW,BLANK,501)
      CALL FLWK26(WORK,IUSEW,LEFTW,BLANK,501)
C
 1030 CONTINUE
C
      NTS16 = NTS16 + IADD
C
 1050 CONTINUE
      GO TO 9999
C
C--------------------------------------------------------------
C  ERROR IN UFLD26
C
 9000 CONTINUE
      IF (IERF.EQ.1) CALL STER26(19,1)
      IF (IERF.EQ.2) CALL STER26(20,1)
      IF (IERF.EQ.3) CALL STER26(21,1)
      IF (IERF.EQ.4) CALL STER26( 1,1)
C
      IF (NCARD.GE.LASTCD) GO TO 9100
      IF (IBLOCK.EQ.1)  GO TO 5
      IF (IBLOCK.EQ.2)  GO TO 10
C
 9100 USEDUP = .TRUE.
C
 9999 CONTINUE
      IT2 = IT(2)
      RETURN
      END
