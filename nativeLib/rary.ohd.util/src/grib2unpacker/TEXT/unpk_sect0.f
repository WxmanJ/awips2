      SUBROUTINE UNPK_SECT0(KFILDO,IPACK,ND5,IS0,NS0,L3264B,
     1                      LOCN,IPOS,IER,ISEVERE,*)
C
C        MARCH    2000   LAWRENCE  GSC/TDL   ORIGINAL CODING
C        JANUARY  2001   GLAHN     COMMENTS
C        FEBRUARY 2001   GLAHN     CHECKED SIZE OF NS0
C        MARCH    2001   LAWRENCE  CHANGED HOW THIS ROUTINE VALIDATES
C                                  THAT THE GRIB2 MESSAGE STARTS WITH
C                                  THE NUMERIC EQUIVALENT OF THE
C                                  STRING "GRIB".
C        NOVEMBER 2001   GLAHN     MOVED ISEVERE=2 UP TO BEGINNING
C
C        PURPOSE
C            UNPACKS SECTION 0, THE INDICATOR SECTION, OF A GRIB2
C            MESSAGE.
C
C        DATA SET USE
C           KFILDO - UNIT NUMBER FOR OUTPUT (PRINT) FILE. (OUTPUT)
C
C        VARIABLES
C              KFILDO = UNIT NUMBER FOR OUTPUT (PRINT) FILE. (INPUT)
C            IPACK(J) = THE ARRAY THAT HOLDS THE ACTUAL PACKED MESSAGE
C                       (J=1,ND5). (INPUT/OUTPUT)
C                 ND5 = THE SIZE OF THE ARRAY IPACK( ). (INPUT)
C              IS0(J) = CONTAINS THE INDICATOR DATA THAT
C                       WILL BE UNPACKED FROM IPACK( ) (J=1,NS0).
C                       (OUTPUT)
C                 NS0 = SIZE OF IS0( ). (INPUT)
C              L3264B = THE INTEGER WORD LENGTH IN BITS OF THE MACHINE
C                       BEING USED. VALUES OF 32 AND 64 ARE
C                       ACCOMMODATED. (INPUT)
C                LOCN = THE WORD POSITION FROM WHICH TO UNPACK THE
C                       NEXT VALUE. (INPUT/OUTPUT)
C                IPOS = THE BIT POSITION IN LOCN FROM WHICH TO START
C                       UNPACKING THE NEXT VALUE.  (INPUT/OUTPUT)
C                 IER = RETURN STATUS CODE. (OUTPUT)
C                          0 = GOOD RETURN.
C                        6-8 = ERROR CODES GENERATED BY UNPKBG. SEE THE
C                              DOCUMENTATION IN THE UNPKBG ROUTINE.
C                       1002 = IS0( ) HAS NOT BEEN DIMENSIONED LARGE
C                              ENOUGH TO CONTAIN THE ENTIRE TEMPLATE. 
C                       1010 = BEGINNING OF GRIB2 MESSAGE NOT FOUND.
C                       1011 = MESSAGE MUST BE GRIB2 VERSION 2.
C             ISEVERE = THE SEVERITY LEVEL OF THE ERROR.  THE ONLY
C                       VALUE RETUNED IS:
C                       2 = A FATAL ERROR  (OUTPUT)
C                   * = ALTERNATE RETURN WHEN IER NE 0.
C
C             LOCAL VARIABLES
C               IGRIB = CONTAINS THE HEXADECIMAL REPRESENTATION
C                       OF THE STRING "GRIB" AS IT WOULD APPEAR
C                       WHEN THE STRING IS EQUIVALENCED TO 
C                       AN INTEGER*4 VARIABLE ON A "BIG ENDIAN"
C                       PLATFORM.
C                IS01 = CONTAINS THE CONTENTS OF IS0(1) FOR START
C                       OF MESSAGE TESTING.
C                   N = L3264B = THE INTEGER WORD LENGTH IN BITS OF
C                       THE MACHINE BEING USED. VALUES OF 32 AND
C                       64 ARE ACCOMMODATED.
C
C        NON SYSTEM SUBROUTINES CALLED
C           UNPKBG
C
      DIMENSION IPACK(ND5),IS0(NS0)
C
C    ================================= RCS keyword statements ==========
      CHARACTER*68     RCSKW1,RCSKW2
      DATA             RCSKW1,RCSKW2 /                                 '
     .$Source: /fs/hseb/ob72/rfc/util/src/grib2unpacker/RCS/unpk_sect0.f,v $
     . $',                                                             '
     .$Id: unpk_sect0.f,v 1.1 2004/09/16 16:51:50 dsa Exp $
     . $' /
C    ===================================================================
C
C
      DATA IGRIB/'47524942'X/
C
      N=L3264B
      IER=0
C
C        ALL ERRORS GENERATED IN THIS ROUTINE ARE FATAL.
      ISEVERE=2 
C
C        CHECK SIZE OF IS0( ).
C
      IF(NS0.LT.9)THEN
         IER=1002
         GO TO 900
      ENDIF
C
C        IPOS = BIT POSITION IN IPACK(LOCN) TO START PUTTING
C        THE VALUE. PKBG UPDATES IT.
      LOCN=1
      IPOS=1
C
C        MAKE SURE THAT THE IS0( ) ARRAY INITIALLY CONTAINS
C        ZERO'S.
C
      DO K=1,NS0
         IS0(K)=0
      ENDDO
C
C        UNPACK THE 'GRIB' IDENTIFICATION.
C        ACCOMMODATE A 64 BIT WORD IF NEED BE.
      CALL UNPKBG(KFILDO,IPACK,ND5,LOCN,IPOS,IS0(1),32,N,IER,*900)
      IS01=IS0(1)
      IF(L3264B.EQ.64)IS01=ISHFT(IS0(1),32)
C
      IF(IGRIB.NE.IS01)THEN
D        WRITE(KFILDO,10)IS0(1)
D10      FORMAT(/' ****BEGINNING OF GRIB MESSAGE NOT FOUND IN',
D    1           ' UNPACK.  FIRST 4 CHARACTERS = 'A4)
         IER=1010
         GO TO 900
      ENDIF
C
C        OCTETS 5 AND 6 ARE RESERVED. THEY CONTAIN NO VALUES.
      CALL UNPKBG(KFILDO,IPACK,ND5,LOCN,IPOS,ITRASH,16,N,IER,*900)
C
C        UNPACK THE DISCIPLINE-GRIB MASTER TABLE NUMBER.
      CALL UNPKBG(KFILDO,IPACK,ND5,LOCN,IPOS,IS0(7),8,N,IER,*900)  
C
C        UNPACK THE GRIB EDITION NUMBER.
      CALL UNPKBG(KFILDO,IPACK,ND5,LOCN,IPOS,IS0(8),8,N,IER,*900)
C
C        THIS MUST BE GRIB EDITION 2.
C
      IF(IS0(8).NE.2)THEN
D        WRITE(KFILDO,113)IS0(8)
D113     FORMAT(/' ****GRIB EDITION NUMBER ='I3,
D    1           ' IN UNPACK NOT EXPECTED.')
         IER=1011
         GO TO 900 
      ENDIF
C
C        A 64-BIT MESSAGE LENGTH IS NOT SUPPORTED AT THIS TIME.
C        UNPACK AND DISCARD THE GARBAGE VALUES IN THE FIRST FOUR
C        OCTETS.
      CALL UNPKBG(KFILDO,IPACK,ND5,LOCN,IPOS,ITRASH,32,N,IER,*900)
C
C        UNPACK THE TOTAL LENGTH OF THE GRIB2 MESSAGE IN OCTETS.
      CALL UNPKBG(KFILDO,IPACK,ND5,LOCN,IPOS,IS0(9),32,N,IER,*900)
C
C       ERROR RETURN SECTION
C
 900  IF(IER.NE.0)RETURN 1 
C
      RETURN
      END
