C MODULE MUADJ
C----------------------------------------------------------------------
C
C
C
C     DESC - THIS SUBROUTINE PERFORMS THE UADJ MOD
C
C
      SUBROUTINE MUADJ(MP,P,NCARDS,MODCRD,IDATE,LDATE,IHZERO)
C
      LOGICAL FIRST
      CHARACTER*8 OPNAME,OPN,BLANK8
C
      INCLUDE 'ufreex'
      INCLUDE 'common/ionum'
      INCLUDE 'common/fctime'
      INCLUDE 'common/fctim2'
      INCLUDE 'common/fdbug'
      INCLUDE 'common/fpwarn'
      INCLUDE 'common/fmodft'
C
      COMMON/MOD219/NDT219,IOP219(2,20),JDT219(2,20),UADJ19(20)
      REAL UADJ19
C
      DIMENSION P(MP),IFIELD(3)
      DIMENSION OLDOPN(2),MODCRD(20,NCARDS)
C
C    ================================= RCS keyword statements ==========
      CHARACTER*68     RCSKW1,RCSKW2
      DATA             RCSKW1,RCSKW2 /                                 '
     .$Source: /fs/hseb/ob72/rfc/ofs/src/fcst_mods/RCS/muadj.f,v $
     . $',                                                             '
     .$Id: muadj.f,v 1.3 1998/07/02 20:51:32 page Exp $
     . $' /
C    ===================================================================
C
C
      DATA ISLASH/4H/   /,BLANK8/'        '/
C
      CALL FSTWHR('MUADJ   ',0,OLDOPN,IOLDOP)
C
C     SET DEBUG FLAG
C
      IBUG=IFBUG(4HMODS)+IFBUG(4HUADJ)
C
      IF(IBUG.GT.0) WRITE(IODBUG,10) IDATE,LDATE
10    FORMAT(5X,'*** SUBROUTINE MUADJ ENTERED *** IDATE,LDATE = ',
     1       I11,2X,I11)
C
C     CALL UMEMOV(MODCRD(20,NRDCRD-1),NTEMP(1),20)
C
      FIRST=.TRUE.
C
C     CHECK DATES ON MOD CARD AGAINST STARTING AND ENDING DATES OF THE
C     ENTIRE RUN - PRINT WARNING IF ENDING DATE ON MOD CARD IS PRIOR TO
C     OR EQUAL TO THE START OF THE RUN, OR STARTING DATE ON MOD CARD IS
C     MORE THAN 12 HOURS AFTER THE END OF THE RUN.  IN BOTH CASES, THE
C     MOD IS IGNORED.
C
      ISTRUN=(IDARUN-1)*24+IHRRUN
      IENDRN=(LDARUN-1)*24+LHRRUN
C
      IF(IBUG.GT.0) WRITE(IODBUG,20)IDATE,ISTRUN,LDATE,IENDRN
20    FORMAT(5X,' IDATE,  ISTRUN,   LDATE,  IENDRN ='/8X,4I9)
C
C     SEE IF ALL OF CURRENT OBS. DATA PERIOD IS WITHIN PERIOD ENTERED
C
      IF(LDATE.LE.ISTRUN.OR.IDATE+12.GT.IENDRN)GO TO 30
C
C     ALL OF OBS. DATA PERIOD IS WITHIN PERIOD BEING CHANGED
C
      GO TO 60
C
30    CONTINUE
C
C     OBS. DATA PERIOD IS NOT ENTIRELY WITHIN THE PERIOD BEING CHANGED
C
      IF(MODWRN.EQ.0)GO TO 340
      CALL MDYH2(IDARUN,IHRRUN,IM1,ID1,IY1,IH1,DUM1,DUM2,MODTZC)
      CALL MDYH2(LDARUN,LHRRUN,IM2,ID2,IY2,IH2,DUM1,DUM2,MODTZC)
      IXDA=IDATE/24+1
      IXHR=IDATE-(IXDA-1)*24
      IF(IXHR.EQ.0)IXDA=IXDA-1
      IF(IXHR.EQ.0)IXHR=24
      CALL MDYH2(IXDA,IXHR,IM3,ID3,IY3,IH3,DUM1,DUM2,MODTZC)
      LXDA=LDATE/24+1
      LXHR=LDATE-(LXDA-1)*24
      IF(LXHR.EQ.0)LXDA=LXDA-1
      IF(LXHR.EQ.0)LXHR=24
      CALL MDYH2(LXDA,LXHR,IM4,ID4,IY4,IH4,DUM1,DUM2,MODTZC)
C
      WRITE(IPR,40)IM3,ID3,IY3,IH3,MODTZC,IM4,ID4,IY4,IH4,MODTZC,
     1IM1,ID1,IY1,IH1,MODTZC,IM2,ID2,IY2,IH2,MODTZC
40    FORMAT(5X,'**WARNING** THE DATES FOR CHANGES IN THE ',
     1 'UADJ MOD (',I2,1H/,I2,1H/,I4,1H-,I2,1X,A4,
     1 4H TO ,I2,1H/,I2,1H/,I4,1H-,I2,1X,A4,1H)/22X,
     2 ' ARE OUTSIDE THE RUN PERIOD (',I2,1H/,I2,
     3 1H/,I4,1H-,I2,1X,A4,4H TO ,I2,1H/,I2,1H/,I4,1H-,I2,1X,A4,1H)/
     4 23X,'THESE CHANGES WILL BE IGNORED.')
C
      CALL WARN
      GO TO 340
C
60    CONTINUE
C
C     READ CARD - IF COMMAND, LEAVE - IF COMMAND AND 1ST CARD, ERROR
C
      OPNAME=BLANK8
      OPN=BLANK8
      FIRST=.TRUE.
C
      IF(NRDCRD.EQ.NCARDS)GO TO 80
C
70    IF(NRDCRD.EQ.NCARDS)GO TO 340
C
      IF(MISCMD(NCARDS,MODCRD).EQ.0)GO TO 100
C
      IF(.NOT.FIRST)GO TO 340
C
C     HAVE FOUND COMMAND AS FIRST SUBSEQUENT CARD - ERROR
C
80    IF(MODWRN.EQ.1)
     .WRITE(IPR,90)
90    FORMAT(5X,'**WARNING** NO SUBSEQUENT CARDS FOUND FOR THE ',
     1  'UADJ MOD.  PROCESSING CONTINUES')
      IF(MODWRN.EQ.1)CALL WARN
      GO TO 340
C
100   FIRST=.FALSE.
      NRDCRD=NRDCRD+1
C
C     NOW READ 2ND FIELD - MUST BE A REAL NO. OR INTEGER
C
      NFLD=1
      ISTRT=-3
      NCHAR=3
      ICKDAT=0
C
      CALL UFIEL2(NCARDS,MODCRD,NFLD,ISTRT,LEN,ITYPE,NREP,INTEGR,VALUE,
     1  NCHAR,IFIELD,LLPAR,LRPAR,LASK,LATSGN,LAMPS,LEQUAL,ISTAT)
C
      IF(ISTAT.EQ.0)GO TO 120
C
C     ERROR - DATA EXPECTED - PROCESS NEXT CARD
C
      IF(MODWRN.EQ.1)
     .WRITE(IPR,110)(MODCRD(I,NRDCRD),I=1,20)
110   FORMAT(5X,'**WARNING** IN THE UADJ MOD - NOT ENOUGH ',
     1  'FIELDS ON SUBSEQUENT CARD.  THE CARD BEING PROCESSED IS:'/
     2  5X,20A4)
      IF(MODWRN.EQ.1)CALL WARN
      GO TO 70
C
120   IF(ITYPE.LT.2.AND.NREP.EQ.-1)GO TO 140
C
      IF (MODWRN.EQ.1) WRITE(IPR,130)(MODCRD(I,NRDCRD),I=1,20)
130   FORMAT(5X,'**WARNING** IN UADJ MOD - INVALID VALUE FOUND.',
     1 '  THIS CARD WILL BE IGNORED.',/,5X,'THE CURRENT CARD ',
     2 'IMAGE IS: ',20A4)
      IF(MODWRN.EQ.1)CALL WARN
      GO TO 70
C
140   IF ((VALUE .GE. 0.0) .AND. (VALUE .LE. 10.0)) GOTO 160
C
C     BAD UADJ VALUE ENTERED
C
      IF (MODWRN.EQ.1) WRITE(IPR,150) VALUE,(MODCRD(I,NRDCRD),I=1,20)
150   FORMAT(5X,'**WARNING** IN UADJ MOD - VALUE ',F5.2,' IS ',
     1 'OUT OF VALID RANGE (0.0 - 10.0).  THIS CARD WILL BE IGNORED.',
     2 /5X,'THE CURRENT CARD IMAGE IS: ',20A4)
      IF(MODWRN.EQ.1)CALL WARN
      GO TO 70
C
160   CONTINUE
C
C     LOOK FOR SNOW-17 AND/OR SNOW-43 OPERATIONS IN THIS SEGMENT 
C     WITH NAME OPNAME.  IF OPNAME BLANK - CHANGE FOR ALL SNOW-17 
C     OPERATIONS IN THIS SEGMENT
C
C     READ NEXT FIELD - IF NONE CHANGE ALL SNOW-17 AND/OR SNOW-43
C     OPERATIONS IN SEGMENT.  IF A SLASH FOUND - READ OPERATION
C     NAME IN FOLLOWING FIELD
C
      ISTRT=-3
      NCHAR=3
      ICKDAT=0
C
      CALL UFIEL2(NCARDS,MODCRD,NFLD,ISTRT,LEN,ITYPE,NREP,INTEGR,REAL,
     1  NCHAR,IFIELD,LLPAR,LRPAR,LASK,LATSGN,LAMPS,LEQUAL,ISTAT)
C
      IF (ISTRT.EQ.-2) GOTO 180
C
C     FIELD FOUND - MUST BE A SLASH
C
      IF (LEN.EQ.1.AND.IFIELD(1).EQ.ISLASH) GOTO 210
C
C     FIELD IS NOT SLASH - ERROR
C
      IF(MODWRN.EQ.1)
     .WRITE(IPR,170) (MODCRD(I,NRDCRD),I=1,20)
170   FORMAT(5X,'**WARNING** A SLASH WAS EXPECTED AFTER THE ',
     1 'THIRD FIELD OF THE INPUT CARD.  THIS CARD WILL BE IGNORED.',/,
     2 5X,'THE CURRENT CARD IMAGE IS: ',20A4)
      IF(MODWRN.EQ.1)CALL WARN
      GO TO 70
C
180   CONTINUE
      LOCP=1
      CALL FSERCH(19,OPNAME,LOCP,P,MP)
      IF(LOCP.EQ.0) then
         locp=1
         call fserch(31,opname,locp,p,mp)
         if(locp.eq.0) goto 190
         endif
      OPNAME=BLANK8
      GOTO 250
C
C  NOT FOUND ANY SNOW-17 AND/OR SNOW-43 OPERATIONS IN THIS SEGMENT
C
190   IF (MODWRN.EQ.1) WRITE(IPR,200) (MODCRD(IY,NRDCRD),IY=1,20)
200   FORMAT(5X,'**WARNING** IN UADJ MOD - NO SNOW-17 AND/OR SNOW-43 ',
     1  'OPERATIONS FOUND.  THIS CHANGE WILL BE IGNORED.',/,
     2  5X,'THE CURRENT ','CARD IMAGE IS: ',20A4)
      IF(MODWRN.EQ.1) CALL WARN
      GOTO 340
C
210   CONTINUE
C
C     SLASH FOUND - NOW READ OPERATION NAME
C
      ISTRT=-3
      NCHAR=2
      ICKDAT=0
C
      CALL UFIEL2(NCARDS,MODCRD,NFLD,ISTRT,LEN,ITYPE,NREP,INTEGR,REAL,
     1  NCHAR,OPNAME,LLPAR,LRPAR,LASK,LATSGN,LAMPS,LEQUAL,ISTAT)
C
      IF(ISTRT.NE.-2)GO TO 230
C
C     NO FIELD FOUND FOR OPERATION NAME - ERROR
C
      IF (MODWRN.EQ.1) WRITE(IPR,220) (MODCRD(I,NRDCRD),I=1,20)
220   FORMAT(5X,'**WARNING** NO OPERATION NAME WAS FOUND AFTER ',
     1 'THE SLASH.  THIS CHANGE WILL BE IGNORED.',/,5X,'THE CURRENT ',
     2 'CARD IMAGE IS: ',20A4)
      IF(MODWRN.EQ.1)CALL WARN
      GO TO 70
C
C     HAVE A SPECIFIC OPERATION NAME - FIND THAT OPERATION
C
230   LOCP=0
      CALL FSERCH(19,OPNAME,LOCP,P,MP)
      IF(LOCP.GT.0)GO TO 250
c
      locp=0
      call fserch(31,opname,locp,p,mp)
      if (locp.gt.0) goto 250
C
C     HAVE NOT FOUND THE REQUESTED OPERATION - ERROR
C
      IF (MODWRN.EQ.1)
     .WRITE(IPR,240) OPNAME,(MODCRD(IX,NRDCRD),IX=1,20)
240   FORMAT(5X,'**WARNING** A SNOW-17 AND/OR SNOW-43 OPERATION WITH ',
     1 'WITH NAME ',A8,' HAS NOT BEEN FOUND.  THIS CHANGE WILL BE ', 
     2 'IGNORED.',/5X,'THE CURRENT CARD IMAGE IS: ',20A4)
      IF(MODWRN.EQ.1)CALL WARN
      GO TO 70
C
250   CONTINUE
C
C     HAVE FOUND OPERATION - FILL COMMON BLOCK MOD219
C
      IF (NDT219 .GT. 0) GOTO 270
C
C  NONE IN CB MOD219 - JUST ADD THIS UADJ TO CB MOD219
C
      NDT219=1
      CALL UMEMOV(OPNAME,IOP219(1,1),2)
      JDT219(1,1)=IDATE
      JDT219(2,1)=LDATE
      UADJ19(1)=VALUE
C
      IF(IBUG.GT.0)WRITE(IODBUG,260)VALUE
260   FORMAT(5X,'UADJ VALUE OF ',F5.2,
     1 ' STORED IN THE 1ST POSITION OF ARRAY UADJ19.')
      GOTO 70
C
C     CHECK IF NAME MATCHES ANY NAME IN CB OR IS BLANK -
C       AND IF DATES MATCH DATES IN COMMON BLOCK MOD219
C
270   DO 290 I=1,NDT219
      IF(IUSAME(OPNAME,IOP219(1,I),2).EQ.0) GOTO 290
C
C     NAME IN CB MATCHES NAME TO BE STORED
C     NOW SEE IF DATES MATCH
C
      IF((JDT219(1,I).NE.IDATE).OR.(JDT219(2,I).NE.LDATE)) GOTO 290
C
C     DATES MATCH TOO - REPLACE PREVIOUS VALUE WITH NEW VALUE TO THIS
C     SPOT IN COMMON BLOCK MOD219
C
      UADJ19(I)=VALUE
      IF (IBUG.GT.0) WRITE(IODBUG,280) VALUE,I
280   FORMAT(5X,'UADJ VALUE OF ',F5.2,
     1 ' REPLACED IN THE POSITION ',I2,' OF ARRAY UADJ19.')
      GOTO 70
C
290   CONTINUE
C
C     HAVE NOT FOUND MATCH FOR NAME AND DATE -
C     ADD NEW ENTRY TO COMMON BLOCK MOD219
C
      IF(NDT219.LT.20) GOTO 310
C
C     NOT ENOUGH ROOM IN COMMON BLOCK MOD219
C
      IF (MODWRN.EQ.1) WRITE(IPR,300) (MODCRD(J,NRDCRD),J=1,20)
300   FORMAT(5X,'**WARNING** NOT ENOUGH ROOM IN COMMON BLOCK ',
     1'/MOD219/ TO HOLD A NEW UADJ VALUE.',/,
     2 5X,'THE CURRENT CARD IMAGE IS: ',20A4)
      GOTO 330
C
C     ENOUGH ROOM - ADD NEW ENTRY TO COMMON BLOCK MOD219
C
310   NDT219=NDT219+1
      I=NDT219
      CALL UMEMOV(OPNAME,IOP219(1,I),2)
      JDT219(1,I)=IDATE
      JDT219(2,I)=LDATE
      UADJ19(I)=VALUE
      IF (IBUG.GT.0) WRITE(IODBUG,320) VALUE,I
320   FORMAT(5X,'UADJ VALUE OF ',F5.2,
     1 ' ADDED IN THE POSITION ',I2,' OF ARRAY UADJ19.')
      GOTO 70
C
330   IF (MODWRN.EQ.1) CALL WARN
      GOTO 70
C
340   IF (IBUG.EQ.0) GOTO 370
C
      WRITE(IODBUG,350) NDT219
350   FORMAT(5X,'***LEAVING SUBROUTINE MUADJ*** NDT219=',I3)
      DO 355 I=1,NDT219
         WRITE (IODBUG,356) IOP219(1,I),IOP219(2,I)
         WRITE (IODBUG,357) JDT219(1,I),JDT219(2,I)
         WRITE (IODBUG,358) UADJ19(I)
355   CONTINUE
356   FORMAT(5X,'OPNAME     = ',A4,A4)
357   FORMAT(5X,'STARTHOUR  = ',I10,' ENDHOUR = ',I10)
358   FORMAT(5X,'UADJ VALUE = ',F6.2)
C
370   CALL FSTWHR(OLDOPN,IOLDOP,OLDOPN,IOLDOP)
C
      RETURN
      END
