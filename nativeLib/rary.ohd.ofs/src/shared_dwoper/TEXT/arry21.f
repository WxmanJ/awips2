C MEMBER ARRY21
C  (from old member FCARRY21)
C
C DESC -- THE FUNCTION OF THIS SUBROUTINE IS TO LIST OUT THE CONTENTS OF
C DESC -- THE PARAMETER ARRAY IN REAL, INTEGER, AND ALPHANUMERIC FORMAT.
C                             LAST UPDATE: 03/01/94.13:12:17 BY $WC30JL
C
C @PROCESS LVL(77)
C
      SUBROUTINE ARRY21(P,IP,ISIZE)
C
C               THIS SUBROUTINE WAS WRITTEN BY::
C               JANICE LEWIS    HRL     JANUARY 1985    VERSION NO. 1
C
      COMMON/FDBUG/IODBUG,ITRACE,IDBALL,NDEBUG,IDEBUG(20)
C
      DIMENSION P(*),IP(*),SNAME(2)
C
C    ================================= RCS keyword statements ==========
      CHARACTER*68     RCSKW1,RCSKW2
      DATA             RCSKW1,RCSKW2 /                                 '
     .$Source: /fs/hseb/ob72/rfc/ofs/src/shared_dwoper/RCS/arry21.f,v $
     . $',                                                             '
     .$Id: arry21.f,v 1.2 1996/01/17 19:55:20 page Exp $
     . $' /
C    ===================================================================
C
C
      DATA SNAME/4HARRA,4HY21 /
C
      CALL FPRBUG(SNAME,1,21,IBUG)
C
      WRITE(IODBUG,8010)
      DO 666 J=1,ISIZE,10
      IEND=J+9
      IF(IEND.GT.ISIZE)IEND=ISIZE
      WRITE(IODBUG,8020) J,(P(I),I=J,IEND)
  666 CONTINUE
C
      WRITE(IODBUG,8030)
      DO 667 J=1,ISIZE,10
      IEND=J+9
      IF(IEND.GT.ISIZE)IEND=ISIZE
      WRITE(IODBUG,8040) J,(IP(I),I=J,IEND)
  667 CONTINUE
      WRITE(IODBUG,8050)
      DO 668 J=1,ISIZE,20
      IEND=J+19
      IF(IEND.GT.ISIZE)IEND=ISIZE
      WRITE(IODBUG,8060) J,(P(I),I=J,IEND)
  668 CONTINUE
 8020 FORMAT(1X,I6,4X,F10.2,1X,F10.2,1X,F10.2,1X,F10.2,1X,F10.2,1X,
     1                F10.2,1X,F10.2,1X,F10.2,1X,F10.2,1X,F10.2)
 8040 FORMAT(1X,I6,4X,10I11)
 8060 FORMAT(1X,I6,4X,A4,1X,A4,1X,A4,1X,A4,1X,A4,1X,A4,1X,A4,1X,A4,1X,
     1                A4,1X,A4,1X,A4,1X,A4,1X,A4,1X,A4,1X,A4,1X,A4,1X,
     2                A4,1X,A4,1X,A4,1X,A4,1X)
 8010 FORMAT(//1H ,10X,11HREAL VALUES)
 8030 FORMAT(//1H ,10X,14HINTEGER VALUES)
 8050 FORMAT(//1H ,10X,19HALPHANUMERIC VALUES)
C
      IF(ITRACE.EQ.1) WRITE(IODBUG,9000) SNAME
 9000 FORMAT(1H0,2H**,2A4,1X,8H EXITED.)
      RETURN
      END
