C MEMBER NTER26
C  (from old member FCNTER26)
C
      SUBROUTINE NTER26(X,Y,XA,YA,N,IFLAG,NTERP,IBUG)
C
C SUBROUTINE NTER26 INTERPOLATES BETWEEN TWO POINTS ON A CURVE (OR
C EXTRAPOLATES BEYOND THE LAST POINT) WITH ARITHMETIC (NTERP=0) OR
C LOGARITHMIC INTERPOLATION (NTERP=1).  SUBROUTINES TERP26 AND FTERPL
C ARE CALLED FOR ARITHMETIC AND LOGARITHMIC INTERPOLATION, RESPECTIVELY.
C
C THIS SUBROUTINE WAS ORIGINALLY PROGRAMMED BY
C     WILLIAM E. FOX -- CONSULTING HYDROLOGIST
C     JULY, 1982
C
C SUBROUTINE NTER26 IS IN
C
C VARIABLES IN THE ARGUMENT LIST ARE DEFINED AS FOLLOWS:
C     X -- INDEPENDENT VARIABLE (GIVEN VALUE).
C     Y -- DEPENDENT VARIABLE (INTERPOLATED OR EXTRAPOLATED VALUE).
C     XA -- ARRAY CORRESPONDING TO X.
C     YA -- ARRAY CORRESPONDING TO Y.
C     N -- NO. OF PAIRS OF XA AND YA VALUES.
C     IFLAG -- USED IN TERP26 TO INDICATE IF VALUE OF X IS WITHIN CURVE
C       BOUNDARIES AND Y IS INTERPOLATED (IFLAG=0), ABOVE CURVE BOUNDARY
C       AND Y IS EXTRAPOLATED (IFLAG=1), OR BELOW CURVE BOUNDARY AND Y
C       IS SET TO YA(1)  (IFLAG=-1).
C     NTERP -- INDICATES ARITHMETIC (NTERP=0) OR LOGARITHMIC INTERPOLA-
C       TION (NTERP=1).
C     IBUG -- NO TRACE OR DEBUG (IBUG=0), TRACE AND DEBUG (IBUG=5)
C
      DIMENSION XA(1),YA(1)
      INCLUDE 'common/fdbug'
C
C    ================================= RCS keyword statements ==========
      CHARACTER*68     RCSKW1,RCSKW2
      DATA             RCSKW1,RCSKW2 /                                 '
     .$Source: /fs/hseb/ob72/rfc/ofs/src/shared_res/RCS/nter26.f,v $
     . $',                                                             '
     .$Id: nter26.f,v 1.1 1995/09/17 19:20:13 dws Exp $
     . $' /
C    ===================================================================
C
C
C WRITE TRACE AND DEBUG IF REQUIRED.
C
      IF (IBUG.GE.5) WRITE(IODBUG,30)
   30 FORMAT(1H0,10X,17H** NTER26 ENTERED)
      IF(NTERP.EQ.0) GO TO 50
      ITBUG = 0
      IF (IBUG .GE. 5) ITBUG = 1
      CALL FTERPL(X,XA,N,YA,Y,ITBUG)
      IFLAG=0
      IF(X.LT.XA(1)) IFLAG=-1
      IF(X.GT.XA(N)) IFLAG=1
      GO TO 60
   50 CALL TERP26(X,Y,XA,YA,N,IFLAG,IBUG)
   60 CONTINUE
      IF (IBUG.LT.5) GO TO 130
   70 WRITE(IODBUG,80) X,Y,IFLAG,NTERP,IBUG
   80 FORMAT(1H0,21H X,Y,IFLAG,NTERP,IBUG/1X,2F12.3,3I6)
      WRITE(IODBUG,90)(XA(I),I=1,N)
   90 FORMAT(1H0,22H VALUES IN X ARRAY ARE/(1X,10F12.3))
      WRITE(IODBUG,100)(YA(I),I=1,N)
  100 FORMAT(1H0,22H VALUES IN Y ARRAY ARE/(1X,10F12.3))
  110 WRITE(IODBUG,120)
  120 FORMAT(1H0,10X,17H** LEAVING NTER26)
  130 RETURN
      END
