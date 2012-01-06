      SUBROUTINE UNPKSECDIF(KFILDO,iwork,A,ND2X3,JMIN,LBIT,NOV,LX,NX,
     1                      NY,IPACK,ND5,IFIRST,ISECOND,ISECMIN,REF,
     2                      LOCN,IPOS,MAXGPREF,IMISSING,XMISSP,XMISSS,
     3                      ISCAL,SCAL10,SCAL2,L3264B,IER)
C
C        MAY      1999   GLAHN   TDL   GRIB2
C        JANUARY  2001   GLAHN   COMMENTS; CHANGED IER = 17 TO 502
C        FEBRUARY 2001   GLAHN   REMOVED XMISSX FROM CALL
C        FEBRUARY 2001   GLAHN   INSERTED SELECT CASE (ISCAL) IN 
C                                3 PLACES
C        NOVEMBER 2001   GLAHN   MODIFIED TO HANDLE PRIMARY MISSINGS
C                                PROPERLY WHEN A GROUP IS ALL OF ONE
C                                VALUE AND MISSING.  ADDED MAXGPREF TO
C                                CALL AND TO CALLS TO UNPKPO AND UNPKPS
C        NOVEMBER 2001   GLAHN   CHANGED IER=17 TO IER=705; COMMENTS
C                                ABOUT IWORK( )
C        OCTOBER  2002   GLAHN   CHANGED DIMENSION OF IWORK FROM ND5
C                                TO ND2X3, AND TEST ABOVE D339
C        OCTOBER  2002   GLAHN   CORRECTED ERROR RELATED TO ABOVE
C
C        PURPOSE
C            UNPACKS DATA IN THE SO-CALLED WMO "SECOND ORDER SPATIAL
C            DIFFERENCING" CORRESPONDING TO CODE TABLE 5.0 VALUE 
C            OF 3.  IT HANDLES (1) 2ND ORDER DIFFERENCE REMOVAL
C            CALLED COMPLEX PACKING BY GRIB, (2) 2ND ORDER SPATIAL
C            DIFFERENCES AND ALTERNATE ROW REVERSAL, AND (3)
C            PRIMARY AND SECONDARY MISSING VALUES.
C
C        DATA SET USE
C           KFILDO - UNIT NUMBER FOR OUTPUT (PRINT) FILE. (OUTPUT) 
C
C        VARIABLES 
C              KFILDO = UNIT NUMBER FOR OUTPUT (PRINT) FILE.  (INPUT) 
C              A(IXY) = GRID OF UNPACKED VALUES  (IXY=1,ND2X3).  THE
C                       VALUES WILL BE IN FORTRAN ARRAY FORMAT
C                       A(IX,JY), (IX=1,NX) (JY=1,NY).  (OUTPUT)
C               ND2X3 = SIZE OF A( ).  (OUTPUT)
C             JMIN(L) = THE MINIMUM VALUE SUBTRACTED FOR EACH GROUP
C                       L BEFORE PACKING (L=1,LX).  (INTERNAL)
C             LBIT(L) = THE NUMBER OF BITS NECESSARY TO HOLD THE
C                       PACKED VALUES FOR EACH GROUP L (L=1,LX). 
C                       (INTERNAL)
C              NOV(L) = THE NUMBER OF VALUES IN GROUP L (L=1,LX).
C                       (INTERNAL)
C                  LX = THE NUMBER OF VALUES IN LBIT( ), JMIN( ), AND
C                       NOV( ).  ALSO THEIR DIMENSIONS BY ALLOCATION.
C                       (INTERNAL)
C              NX, NY = SIZE OF DATA ARRAY FOR A( ).  (INPUT)
C            IPACK(J) = THE ARRAY HOLDING THE ACTUAL PACKED MESSAGE
C                       (J=1,MAX OF ND5).  (INPUT/OUTPUT)
C                 ND5 = DIMENSION OF IPACK( ).  (INPUT)
C              IFIRST = THE FIRST VALUE IN THE FIELD.  USED ONLY WHEN
C                       PACKING SECOND ORDER DIFFERENCES.  (INPUT)
C             ISECOND = THE SECOND ORIGINAL VALUE IN THE FIELD. USED
C                       ONLY WHEN PACKING SECOND ORDER DIFFERENCES.
C                       (INPUT)
C             ISECMIN =
C                 REF = THE MINIMUM VALUE THAT WAS SUBTRACTED BEFORE
C                       PACKING.  THIS IS ADDED BACK IN BEFORE RETURNING
C                       THE DATA IN A( ).  (INPUT)
C                LOCN = THE WORD POSITION FROM WHICH TO UNPACK THE
C                       NEXT VALUE. (INPUT/OUTPUT)
C                IPOS = THE BIT POSITION IN LOCN FROM WHICH TO START
C                       UNPACKING THE NEXT VALUE.  (INPUT/OUTPUT)
C            MAXGPREF = THE MAXIMUM VALUE THAT CAN BE STORED IN THE
C                       GROUP REFERENCES.  (INPUT)
C            IMISSING = REPRESENTS OCTET 23 OF SECTION 5. DETERMINES
C                       IF THERE ARE NO MISSING VALUES, PRIMARY MISSING
C                       VALUES, OR PRIMARY AND SECONDARY MISSING VALUES.
C                       (INPUT)
C              XMISSP = PRIMARY MISSING VALUE INDICATOR THAT IS 
C                       TO BE TREATED AS MISSING.  (INPUT)
C              XMISSS = SECONDARY MISSING VALUE INDICATOR.  (OUTPUT)
C               ISCAL = INDICATES COMBINATIONS OF SCALING.
C                       0 = NONE
C                       1 = DECIMAL ONLY
C                       2 = BINARY ONLY
C                       3 = BOTH DECIMAL AND BINARY
C              SCAL10 = DECIMAL SCALING PARAMETER.  (OUTPUT)
C               SCAL2 = BINARY SCALING PARAMETER.  (OUTPUT)
C              L3264B = INTEGER WORD LENGTH OF MACHINE BEING USED.
C                       (INPUT)
C                 IER = ERROR RETURNS:
C                         0 = GOOD RETURN.
C                         6 = NOT ENOUGH ROOM IN IPACK( ) OR IWORK( ) TO
C                             ACCOMMODATE THE DATA INDICATED BY LBIT( )
C                             AND NOV( ).
C                         7 = IPOS NOT IN RANGE 1 TO 32.
C                         8 = LBIT(L) NOT IN RANGE 0 TO 30.
C                       705 = ND5 NOT LARGE ENOUGH.
C              NVALUE = AN UNPACKED VALUE RETURNED FROM SUBROUTINE
C                       UNPKBG.  (INTERNAL)
C                KBIT = THE NUMBER OF BITS REQUIRED TO HOLD THE NUMBER
C                       OF VALUES IN EACH GROUP.  (INTERNAL)  
C                JBIT = THE NUMBER OF BITS REQUIRED TO HOLD THE NUMBER
C                       OF BITS REQUIRED FOR EACH GROUP'S VALUES.
C                       (INTERNAL)
C                MBIT = THE NUMBER OF BITS REQUIRED TO PACK THE
C                       ABSOLUTE VALUE OF THE FIRST FIRST ORDER  
C                       DIFFERENCE.  USED ONLY WHEN PACKING SECOND ORDER
C                       DIFFERENCES.  (INTERNAL)
C                NBIT = THE NUMBER OF BITS REQUIRED TO HOLD THE ABSOLUTE
C                       VALUE OF THE OVERALL MINIMUM, REF.  (INTERNAL)
C                IFOD = THE FIRST FIRST ORDER DIFFERENCE.  USED ONLY
C                       WHEN PACKING SECOND ORDER DIFFERENCES.
C                       (INTERNAL)
C                   N = WORKING COPY OF L3264B.  (INTERNAL)
C              LBYPWD = BYTES PER WORD FOR EITHER A 32- OR 64-BIT
C                       MACHINE.  (INTERNAL)
C            IWORK(J) = WORK ARRAY J(=1,ND2X3).  (INTERNAL)
C
C        NON-SYSTEM SUBROUTINES CALLED
C           UNPKOO, UNPKPO, UNPKPS
C
      DIMENSION A(ND2X3)
      DIMENSION IWORK(ND2X3)
C        IWORK( ) IS AN AUTOMATIC ARRAY.
      DIMENSION IPACK(ND5)
      DIMENSION JMIN(LX),LBIT(LX),NOV(LX)
C
C    ================================= RCS keyword statements ==========
      CHARACTER*68     RCSKW1,RCSKW2
      DATA             RCSKW1,RCSKW2 /                                 '
     .$Source: /fs/hseb/ob72/rfc/util/src/grib2unpacker/RCS/unpksecdif.f,v $
     . $',                                                             '
     .$Id: unpksecdif.f,v 1.1 2004/09/16 16:51:50 dsa Exp $
     . $' /
C    ===================================================================
C
C
C        SET ERROR RETURN AND INITIALIZE VARIABLES.
      IER=0
      MISSP=INT(XMISSP)
      MISSS=INT(XMISSS)
      LBYPWD=L3264B/8
      NXY=NX*NY
C
C        LBYPWD IS THE BYTES PER WORD FOR EITHER A 32- OR 64-BIT
C        MACHINE.
      N=L3264B
C        THIS ASSIGNMENT IS MADE MAINLY TO KEEP MOST CALLS TO UNPKBG
C        TO ONE LINE.  IT MAY HELP THE COMPILER TO BE MORE EFFICIENT.
C
      IF(NXY.GT.ND2X3)THEN
D        WRITE(KFILDO,339)ND2X3,NXY
D339     FORMAT(/' ****DIMENSION ND2X3 ='I6,' NOT LARGE ENOUGH',
D    1           ' IN UNPKSECDIF.  INCREASE TO'I6)
          IER=705
          GO TO 910
       ENDIF
C
C       SET OSCAL10, THE RECIPROCAL OF SCAL10.
C
      OSCAL10=1./SCAL10
C
C        UNPACK THE DATA VALUES THEMSELVES.  THE ROUTINES
C        UNPKOO, UNPKPO, AND UNPKPS HANDLE THE CASE WHEN
C        THERE ARE NO MISSING VALUES, THERE CAN BE
C        PRIMARY MISSING VALUES, AND THERE CAN BE BOTH
C        PRIMARY AND SECONDARY MISSING VALUES, RESPECTIVELY.
C
 340  IF(IMISSING.EQ.0)THEN
         CALL UNPKOO(KFILDO,IPACK,ND5,LOCN,IPOS,JMIN,LBIT,
     1               NOV,LX,IWORK,ND2X3,L3264B,IER)
      ELSEIF(IMISSING.EQ.1)THEN
         CALL UNPKPO(KFILDO,IPACK,ND5,LOCN,IPOS,MISSP,JMIN,
     1               LBIT,NOV,LX,MAXGPREF,IWORK,ND2X3,L3264B,IER)
      ELSE
         CALL UNPKPS(KFILDO,IPACK,ND5,LOCN,IPOS,MISSP,MISSS,
     1               JMIN,LBIT,NOV,LX,IWORK,ND2X3,L3264B,IER)
      ENDIF
C
      IF(IER.NE.0)GO TO 910
C
C        ADD THE MINIMUM VALUE BACK INTO THE SECOND ORDER DIFFERENCE
C        FIELD
      IF(IMISSING.EQ.0)THEN
         DO 370 K=3,NXY 
            IWORK(K)=IWORK(K)+ISECMIN
 370     CONTINUE
      ELSE
         DO 380 K=3,NXY
            IF(IWORK(K).NE.MISSP)IWORK(K)=IWORK(K)+ISECMIN
 380     CONTINUE
      ENDIF
C
C        RECOVER FIELD FROM 2ND ORDER DIFFERENCES.
C
C        THIS LOOP IS FOR NO MISSING VALUES AND 2ND ORDER DIFFERENCES.
C
      IFOD=ISECOND-IFIRST
C
      IF(IMISSING.EQ.0)THEN
         IWORK(1)=IFIRST
         IWORK(2)=ISECOND
         ISUM=IFOD
C
         DO 385 K=3,NXY
            ISUM=IWORK(K)+ISUM
            IWORK(K)=IWORK(K-1)+ISUM
 385     CONTINUE
C
C        THIS LOOP FOR MISSING VALUES AND 2ND ORDER DIFFERENCES.
C
      ELSE
C
         KOUNT=0
         KSTART=1
C           THE ABOVE STATEMENT SHOULD NOT BE NEEDED.
C
         DO 387 K=1,NXY
         IF(IWORK(K).EQ.MISSP)GO TO 387
         KOUNT=KOUNT+1
C
         IF(KOUNT.EQ.1)THEN
            IWORK(K)=IFIRST
         ELSEIF(KOUNT.EQ.2)THEN
            IWORK(K)=IFIRST+IFOD
            ISUM=IFOD
            IWKEEP=IWORK(K)
            KSTART=K+1
            GO TO 388
         ENDIF
C 
 387     CONTINUE        
C   
         GO TO 391
C           THIS EXIT SHOULD NOT OCCUR, BECAUSE THIS WOULD 
C           INDICATE ALL VALUES WERE MISSING, AND ALL MISSING
C           VALUES SHOULD NOT HAVE BEEN PACKED WITH SECOND
C           ORDER DIFFERENCES.
 388     IF(KSTART.GT.NXY)GO TO 391
C           THE ABOVE TEST WOULD BE MET ONLY WHEN EXACTLY
C           TWO NON-MISSING VALUES WERE PACKED.
C
         DO 390 K=KSTART,NXY
         IF(IWORK(K).EQ.MISSP)GO TO 3880
         ISUM=IWORK(K)+ISUM
         IWORK(K)=IWKEEP+ISUM
         IWKEEP=IWORK(K)
C
 3880    CONTINUE
 390     CONTINUE
C
      ENDIF
C
C        DECODE THE ORIGINAL VALUES BASED UPON
C        WHETHER THERE ARE NO MISSING VALUES,
C        PRIMARY MISSING VALUES, OR PRIMARY AND
C        SECONDARY MISSING VALUES.
 391  IF(IMISSING.EQ.0)THEN
C
         DO 395 K=1,NXY
C
            SELECT CASE (ISCAL)
               CASE(0)
                  A(K)=IWORK(K)+REF
               CASE(1)
                  A(K)=(IWORK(K)+REF)*OSCAL10
               CASE(2)
                  A(K)=IWORK(K)*SCAL2+REF
               CASE(3)
                  A(K)=(IWORK(K)*SCAL2+REF)*OSCAL10
            END SELECT
C
 395     CONTINUE
C
      ELSEIF(IMISSING.EQ.1)THEN
C
         DO 398 K=1,NXY
            IF(IWORK(K).EQ.MISSP)THEN
               A(K)=MISSP
            ELSE
C
               SELECT CASE (ISCAL)
                  CASE(0)
                     A(K)=IWORK(K)+REF
                  CASE(1)
                     A(K)=(IWORK(K)+REF)*OSCAL10
                  CASE(2)
                     A(K)=IWORK(K)*SCAL2+REF
                  CASE(3)
                     A(K)=(IWORK(K)*SCAL2+REF)*OSCAL10
               END SELECT
C
            ENDIF       
 398     CONTINUE
C
      ELSE
C
         DO 399 K=1,NXY
            IF(IWORK(K).EQ.MISSP)THEN
               A(K)=MISSP
            ELSEIF(IWORK(K).EQ.MISSS)THEN
               A(K)=MISSS
            ELSE
C
               SELECT CASE (ISCAL)
                  CASE(0)
                     A(K)=IWORK(K)+REF
                  CASE(1)
                     A(K)=(IWORK(K)+REF)*OSCAL10
                  CASE(2)
                     A(K)=IWORK(K)*SCAL2+REF
                  CASE(3)
                     A(K)=(IWORK(K)*SCAL2+REF)*OSCAL10
               END SELECT
C
            ENDIF       
 399     CONTINUE
      ENDIF
C
      RETURN
C
C        ERROR RETURN SECTION.
C
 910  DO 920 K=1,ND2X3
         A(K)=9999.
 920  CONTINUE
C
      RETURN
      END
