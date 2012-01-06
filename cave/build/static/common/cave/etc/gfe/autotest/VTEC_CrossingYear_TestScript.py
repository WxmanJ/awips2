##
# This software was developed and / or modified by Raytheon Company,
# pursuant to Contract DG133W-05-CQ-1067 with the US Government.
# 
# U.S. EXPORT CONTROLLED TECHNICAL DATA
# This software product contains export-restricted data whose
# export/transfer/disclosure is restricted by U.S. law. Dissemination
# to non-U.S. persons whether in the United States or abroad requires
# an export license or other authorization.
# 
# Contractor Name:        Raytheon Company
# Contractor Address:     6825 Pine Street, Suite 340
#                         Mail Stop B8
#                         Omaha, NE 68106
#                         402.291.0100
# 
# See the AWIPS II Master Rights File ("Master Rights File.pdf") for
# further licensing information.
##
# ----------------------------------------------------------------------------
# This software is in the public domain, furnished "as is", without technical
# support, and with no warranty, express or implied, as to its usefulness for
# any purpose.
#
# Headlines Timing
#
# Author:
# ----------------------------------------------------------------------------

scripts = [
    {
    "commentary": "Clear out all Hazards Table and Grids.",
    "name": "Hazard_CrossingYear_0",
    "productType": None,
    "clearHazardsTable": 1,
    "checkStrings": [],
    },
    {
    "commentary": """<*---1---> <---2---> |
      Two events in December. Current time at start of first hazard.
      ETNs for December events 0001 and 0002.""",
    "name": "Hazard_CrossingYear_1a",
    "drtTime": "20091230_1200",
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", -36, -24, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", -18, -6, "DU.Y", ["FLZ049"]),
       ],
    "checkStrings": [
       "WWUS72 KTBW 301200",
       "NPWTBW",
       "URGENT - WEATHER MESSAGE",
       "NATIONAL WEATHER SERVICE TAMPA BAY RUSKIN FL",
       "700 AM EST WED DEC 30 2009",
       "...|*OVERVIEW HEADLINE (MUST EDIT)*|...",
       ".|*OVERVIEW (MUST EDIT)*|.",
       "FLZ049-302000-",
       "/O.NEW.KTBW.DU.Y.0001.091230T1200Z-091231T0000Z/",
       "/O.NEW.KTBW.DU.Y.0002.091231T0600Z-091231T1800Z/",
       "PASCO-",
       "700 AM EST WED DEC 30 2009",
       "...BLOWING DUST ADVISORY IN EFFECT UNTIL 7 PM EST THIS EVENING...",
       "...BLOWING DUST ADVISORY IN EFFECT FROM 1 AM TO 1 PM EST THURSDAY...",
#       "THE NATIONAL WEATHER SERVICE IN TAMPA BAY RUSKIN HAS ISSUED A BLOWING DUST ADVISORY...WHICH IS IN EFFECT UNTIL 7 PM EST THIS EVENING. A BLOWING DUST ADVISORY HAS ALSO BEEN ISSUED. THIS BLOWING DUST ADVISORY IS IN EFFECT FROM 1 AM TO 1 PM EST THURSDAY.",
       "|* SEGMENT TEXT GOES HERE *|.",
       "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
       "$$",
       ],
    },

    {
    "commentary": """<---1---> <---2---> |* <---1--->
      Time has marched forward into the new year and forecaster issues
      another DU.Y to start in the future. Events from last year 
      are 'old' and ignored. Event from new year given ETN of 0001.""",
    "name": "Hazard_CrossingYear_1b",
    "drtTime": "20100101_0200",
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", -36, -24, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", -18, -6, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 6, 12, "DU.Y", ["FLZ049"]),
       ],
    "checkStrings": [
       "WWUS72 KTBW 010200",
       "NPWTBW",
       "URGENT - WEATHER MESSAGE",
       "NATIONAL WEATHER SERVICE TAMPA BAY RUSKIN FL",
       "900 PM EST THU DEC 31 2009",
       "...|*OVERVIEW HEADLINE (MUST EDIT)*|...",
       ".|*OVERVIEW (MUST EDIT)*|.",
       "FLZ049-011000-",
       "/O.NEW.KTBW.DU.Y.0001.100101T0600Z-100101T1200Z/",
       "PASCO-",
       "900 PM EST THU DEC 31 2009",
       "...BLOWING DUST ADVISORY IN EFFECT FROM 1 AM TO 7 AM EST FRIDAY...",
       "THE NATIONAL WEATHER SERVICE IN TAMPA BAY RUSKIN HAS ISSUED A BLOWING DUST ADVISORY...WHICH IS IN EFFECT FROM 1 AM TO 7 AM EST FRIDAY.",
       "|* SEGMENT TEXT GOES HERE *|.",
       "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
       "$$",
       ],
    },

    {
    "commentary": """<---1---> <---2---> | <----1--->*
       Forecaster runs formatter within 30 minutes of event ending, thus
       generating an EXP event.""",
    "name": "Hazard_CrossingYear_1c",
    "drtTime": "20100101_1200",
    "productType": "Hazard_NPW_Local",
    "createGrids": [
        ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
    ],
    "checkStrings": [
       "WWUS72 KTBW 011200",
       "NPWTBW",
       "URGENT - WEATHER MESSAGE",
       "NATIONAL WEATHER SERVICE TAMPA BAY RUSKIN FL",
       "700 AM EST FRI JAN 1 2010",
       "...|*OVERVIEW HEADLINE (MUST EDIT)*|...",
       ".|*OVERVIEW (MUST EDIT)*|.",
       "FLZ049-011300-",
       "/O.EXP.KTBW.DU.Y.0001.000000T0000Z-100101T1200Z/",
       "PASCO-",
       "700 AM EST FRI JAN 1 2010",
       "...BLOWING DUST ADVISORY HAS EXPIRED...",
       "THE BLOWING DUST ADVISORY IS NO LONGER IN EFFECT.",
       "$$",
       ],
    },

    {
    "commentary": "Deleting hazard grids.",
    "name": "Hazard_CrossingYear_Cleanup1",
    "productType": None,
    "checkStrings": [],
    "clearHazardsTable": 1,
    },
    {
    "commentary": """<*----1---->   <---2--|--->   
       Forecaster issues two events in December.  The second event crosses
       into the new year. ETNs are 0001 and 0002 representing the year
       they were issued.""",
    "name": "Hazard_CrossingYear_2a",
    "drtTime": "20091230_1200",
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", -36, -24, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", -18, 6, "DU.Y", ["FLZ049"]),
       ],
    "checkStrings": [
       "WWUS72 KTBW 301200",
       "NPWTBW",
       "URGENT - WEATHER MESSAGE",
       "NATIONAL WEATHER SERVICE TAMPA BAY RUSKIN FL",
       "700 AM EST WED DEC 30 2009",
       "...|*OVERVIEW HEADLINE (MUST EDIT)*|...",
       ".|*OVERVIEW (MUST EDIT)*|.",
       "FLZ049-302000-",
       "/O.NEW.KTBW.DU.Y.0001.091230T1200Z-091231T0000Z/",
       "/O.NEW.KTBW.DU.Y.0002.091231T0600Z-100101T0600Z/",
       "PASCO-",
       "700 AM EST WED DEC 30 2009",
       "...BLOWING DUST ADVISORY IN EFFECT UNTIL 7 PM EST THIS EVENING...",
       "...BLOWING DUST ADVISORY IN EFFECT FROM 1 AM THURSDAY TO 1 AM EST FRIDAY...",
#       "THE NATIONAL WEATHER SERVICE IN TAMPA BAY RUSKIN HAS ISSUED A BLOWING DUST ADVISORY...WHICH IS IN EFFECT UNTIL 7 PM EST THIS EVENING. A BLOWING DUST ADVISORY HAS ALSO BEEN ISSUED. THIS BLOWING DUST ADVISORY IS IN EFFECT FROM 1 AM THURSDAY TO 1 AM EST FRIDAY.",
       "|* SEGMENT TEXT GOES HERE *|.",
       "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
       "$$",
       ],
    },

    {
    "commentary": """<----1---->   <---2--|-*-->  
       Time marches forward into the new year, into the middle of the 2nd
       event.  The ETN remains at 0002 for this event.""",
    "name": "Hazard_CrossingYear_2b",
    "drtTime": "20100101_0200",
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", -36, -24, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", -18, 6, "DU.Y", ["FLZ049"]),
       ],
    "checkStrings": [
       "WWUS72 KTBW 010200",
       "NPWTBW",
       "URGENT - WEATHER MESSAGE",
       "NATIONAL WEATHER SERVICE TAMPA BAY RUSKIN FL",
       "900 PM EST THU DEC 31 2009",
       "...|*OVERVIEW HEADLINE (MUST EDIT)*|...",
       ".|*OVERVIEW (MUST EDIT)*|.",
       "FLZ049-010600-",
       "/O.CON.KTBW.DU.Y.0002.000000T0000Z-100101T0600Z/",
       "PASCO-",
       "900 PM EST THU DEC 31 2009",
       "...BLOWING DUST ADVISORY REMAINS IN EFFECT UNTIL 1 AM EST FRIDAY...",
       "A BLOWING DUST ADVISORY REMAINS IN EFFECT UNTIL 1 AM EST FRIDAY.",
       "|* SEGMENT TEXT GOES HERE *|.",
       "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
       "$$ ",
       ],
    },

    {
    "commentary": """<----1---->   <---2--|--*->   <----1---->
      In the middle of the 2nd event ETN0002 in the new year, the forecaster 
      issues another event, which will get ETN 0001 since it is the first
      event issued in that year.""",
    "name": "Hazard_CrossingYear_2c",
    "drtTime": "20100101_0400",
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", -36, -24, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", -18, 6, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 7, 12, "DU.Y", ["FLZ049"]),
       ],
    "checkStrings": [
       "WWUS72 KTBW 010400",
       "NPWTBW",
       "URGENT - WEATHER MESSAGE",
       "NATIONAL WEATHER SERVICE TAMPA BAY RUSKIN FL",
       "1100 PM EST THU DEC 31 2009",
       "...|*OVERVIEW HEADLINE (MUST EDIT)*|...",
       ".|*OVERVIEW (MUST EDIT)*|.",
       "FLZ049-011200-",
       "/O.NEW.KTBW.DU.Y.0001.100101T0700Z-100101T1200Z/",
       "/O.CON.KTBW.DU.Y.0002.000000T0000Z-100101T0600Z/",
       "PASCO-",
       "1100 PM EST THU DEC 31 2009",
       "...BLOWING DUST ADVISORY REMAINS IN EFFECT UNTIL 1 AM EST FRIDAY...",
       "...BLOWING DUST ADVISORY IN EFFECT FROM 2 AM TO 7 AM EST FRIDAY...",
#       "THE NATIONAL WEATHER SERVICE IN TAMPA BAY RUSKIN HAS ISSUED A BLOWING DUST ADVISORY...WHICH IS IN EFFECT FROM 2 AM TO 7 AM EST FRIDAY. A BLOWING DUST ADVISORY REMAINS IN EFFECT UNTIL 1 AM EST FRIDAY.",
       "|* SEGMENT TEXT GOES HERE *|.",
       "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
       "$$",
       ],
    },

    {
    "commentary": """<----1---->   <---2--|---> *  <----1---->
      Forecaster runs the formatter within 30 minutes of the ending time
      of the ETN 0002 event, which generates an EXP.  The ETN 0001 event
      has not yet started.""",
    "name": "Hazard_CrossingYear_2d",
    "drtTime": "20100101_0630",
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", -36, -24, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", -18, 6, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 7, 12, "DU.Y", ["FLZ049"]),
       ],
    "checkStrings": [
       "WWUS72 KTBW 010630",
       "NPWTBW",
       "URGENT - WEATHER MESSAGE",
       "NATIONAL WEATHER SERVICE TAMPA BAY RUSKIN FL",
       "130 AM EST FRI JAN 1 2010",
       "...|*OVERVIEW HEADLINE (MUST EDIT)*|...",
       ".|*OVERVIEW (MUST EDIT)*|.",
       "FLZ049-011200-",
       "/O.EXP.KTBW.DU.Y.0002.000000T0000Z-100101T0600Z/",
       "/O.CON.KTBW.DU.Y.0001.100101T0700Z-100101T1200Z/",
       "PASCO-",
       "130 AM EST FRI JAN 1 2010",
       "...BLOWING DUST ADVISORY REMAINS IN EFFECT UNTIL 7 AM EST THIS MORNING...",
       "...BLOWING DUST ADVISORY HAS EXPIRED...",
       "THE BLOWING DUST ADVISORY IS NO LONGER IN EFFECT. A BLOWING DUST ADVISORY REMAINS IN EFFECT UNTIL 7 AM EST THIS MORNING.",
       "$$ ",

       ],
    },

    {
    "commentary": """<----1---->   <---2--|--->   <----1----*>
      We are now in the middle of the ETN 0001 event in the new year,
      and within 30 minutes of its ending time, thus an EXP is generated.""",
    "name": "Hazard_CrossingYear_2e",
    "drtTime": "20100101_1145",
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", -36, -24, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", -18, 6, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 7, 12, "DU.Y", ["FLZ049"]),
       ],
    "checkStrings": [
       "WWUS72 KTBW 011145",
       "NPWTBW",
       "URGENT - WEATHER MESSAGE",
       "NATIONAL WEATHER SERVICE TAMPA BAY RUSKIN FL",
       "645 AM EST FRI JAN 1 2010",
       "...|*OVERVIEW HEADLINE (MUST EDIT)*|...",
       ".|*OVERVIEW (MUST EDIT)*|.",
       "FLZ049-011245-",
       "/O.EXP.KTBW.DU.Y.0001.000000T0000Z-100101T1200Z/",
       "PASCO-",
       "645 AM EST FRI JAN 1 2010",
       "...BLOWING DUST ADVISORY WILL EXPIRE AT 7 AM EST THIS MORNING...",
       "THE BLOWING DUST ADVISORY WILL EXPIRE AT 7 AM EST THIS MORNING.",
       "$$",
       ],
    },

    {
    "commentary": "Deleting hazard grids.",
    "name": "Hazard_CrossingYear_Cleanup2",
    "productType": None,
    "checkStrings": [],
    "clearHazardsTable": 1,
    },
    {
    "commentary": """* <--1---> <-----2---|----->
      This test scenario checks extending an event from a prior year.  The
      initial setup is two events in the previous year with the 2nd event
      ending in the new year. """,
    "name": "Hazard_CrossingYear_3a",
    "drtTime": "20091230_0011",
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", -36, -28, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", -24, 24, "DU.Y", ["FLZ049"]),
       ],
    "checkStrings": [
       "WWUS72 KTBW 300011",
       "NPWTBW",
       "URGENT - WEATHER MESSAGE",
       "NATIONAL WEATHER SERVICE TAMPA BAY RUSKIN FL",
       "711 PM EST TUE DEC 29 2009",
       "...|*OVERVIEW HEADLINE (MUST EDIT)*|...",
       ".|*OVERVIEW (MUST EDIT)*|.",
       "FLZ049-300815-",
       "/O.NEW.KTBW.DU.Y.0001.091230T1200Z-091230T2000Z/",
       "/O.NEW.KTBW.DU.Y.0002.091231T0000Z-100102T0000Z/",
       "PASCO-",
       "711 PM EST TUE DEC 29 2009",
       "...BLOWING DUST ADVISORY IN EFFECT FROM 7 AM TO 3 PM EST WEDNESDAY...",
       "...BLOWING DUST ADVISORY IN EFFECT FROM 7 PM WEDNESDAY TO 7 PM EST FRIDAY...",
#       "THE NATIONAL WEATHER SERVICE IN TAMPA BAY RUSKIN HAS ISSUED A BLOWING DUST ADVISORY...WHICH IS IN EFFECT FROM 7 AM TO 3 PM EST WEDNESDAY. A BLOWING DUST ADVISORY HAS ALSO BEEN ISSUED. THIS BLOWING DUST ADVISORY IS IN EFFECT FROM 7 PM WEDNESDAY TO 7 PM EST FRIDAY.",
       "|* SEGMENT TEXT GOES HERE *|.",
       "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION. ",
       "$$",
       ],
    },

    {
    "commentary": """<--1---> <-----2---|---*--><-----2------>
       The current time is in the new year, in the middle of the 2nd event.
       Forecaster extends the ending time.  ETN remains at 0002. EXT code
       generated.""",
    "name": "Hazard_CrossingYear_3b",
    "drtTime": "20100101_1823",
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", -24, 24, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 24, 29, "DU.Y", ["FLZ049"]),
       ],
    "checkStrings": [
       "WWUS72 KTBW 011823",
       "NPWTBW",
       "URGENT - WEATHER MESSAGE",
       "NATIONAL WEATHER SERVICE TAMPA BAY RUSKIN FL",
       "123 PM EST FRI JAN 1 2010",
       "...|*OVERVIEW HEADLINE (MUST EDIT)*|...",
       ".|*OVERVIEW (MUST EDIT)*|.",
       "FLZ049-020230-",
       "/O.EXT.KTBW.DU.Y.0002.000000T0000Z-100102T0500Z/",
       "PASCO-",
       "123 PM EST FRI JAN 1 2010",
       "...BLOWING DUST ADVISORY NOW IN EFFECT UNTIL MIDNIGHT EST TONIGHT...",
       "THE BLOWING DUST ADVISORY IS NOW IN EFFECT UNTIL MIDNIGHT EST TONIGHT.",
       "|* SEGMENT TEXT GOES HERE *|.",
       "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
       "$$",
       ],
    },

    {
    "commentary": """<--1---> <-----2---|-----><*-----2------> <-1-> <-2->
      We are now in the extended section of the second event.  Forecaster 
      adds two new events further into the future.  Since this are the first 
      event of this type issued in this new year, the ETNs are 0001 and 0002.""",
    "name": "Hazard_CrossingYear_3c",
    "drtTime": "20100102_0000",
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", -24, 24, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 24, 29, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 36, 55, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 77, 89, "DU.Y", ["FLZ049"]),
       ],
    "checkStrings": [
       "WWUS72 KTBW 020000",
       "NPWTBW",
       "URGENT - WEATHER MESSAGE",
       "NATIONAL WEATHER SERVICE TAMPA BAY RUSKIN FL",
       "700 PM EST FRI JAN 1 2010",
       "...|*OVERVIEW HEADLINE (MUST EDIT)*|...",
       ".|*OVERVIEW (MUST EDIT)*|.",
       "FLZ049-020800-",
       "/O.NEW.KTBW.DU.Y.0001.100102T1200Z-100103T0700Z/",
       "/O.NEW.KTBW.DU.Y.0002.100104T0500Z-100104T1700Z/",
       "/O.CON.KTBW.DU.Y.0002.000000T0000Z-100102T0500Z/",
       "PASCO-",
       "700 PM EST FRI JAN 1 2010",
       "...BLOWING DUST ADVISORY REMAINS IN EFFECT UNTIL MIDNIGHT EST TONIGHT...",
       "...BLOWING DUST ADVISORY IN EFFECT FROM 7 AM SATURDAY TO 2 AM EST SUNDAY...",
       "...BLOWING DUST ADVISORY IN EFFECT FROM MIDNIGHT SUNDAY NIGHT TO NOON EST MONDAY...",
#       "THE NATIONAL WEATHER SERVICE IN TAMPA BAY RUSKIN HAS ISSUED A BLOWING DUST ADVISORY...WHICH IS IN EFFECT FROM 7 AM SATURDAY TO 2 AM EST SUNDAY. A BLOWING DUST ADVISORY HAS ALSO BEEN ISSUED. THIS BLOWING DUST ADVISORY IS IN EFFECT FROM MIDNIGHT SUNDAY NIGHT TO NOON EST MONDAY. A BLOWING DUST ADVISORY REMAINS IN EFFECT UNTIL MIDNIGHT EST TONIGHT.",
       "|* SEGMENT TEXT GOES HERE *|.",
       "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT",
       "VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
       "$$",
       ],
    },
    {
    "commentary": """<--1---> <-----2---|-----><*-----2------> <-1-> <-2->
       No time changes or grid changes from previous step, thus we get CONs
       in the product. Note the confusing VTEC.""",
    "name": "Hazard_CrossingYear_3d",
    "drtTime": "20100102_0000",
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", -24, 24, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 24, 29, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 36, 55, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 77, 89, "DU.Y", ["FLZ049"]),
       ],
    "checkStrings": [
       "WWUS72 KTBW 020000",
       "NPWTBW",
       "URGENT - WEATHER MESSAGE",
       "NATIONAL WEATHER SERVICE TAMPA BAY RUSKIN FL",
       "700 PM EST FRI JAN 1 2010",
       "...|*OVERVIEW HEADLINE (MUST EDIT)*|...",
       ".|*OVERVIEW (MUST EDIT)*|.",
       "FLZ049-020800-",
       "/O.CON.KTBW.DU.Y.0002.000000T0000Z-100102T0500Z/",
       "/O.CON.KTBW.DU.Y.0001.100102T1200Z-100103T0700Z/",
       "/O.CON.KTBW.DU.Y.0002.100104T0500Z-100104T1700Z/",
       "PASCO-",
       "700 PM EST FRI JAN 1 2010",
       "...BLOWING DUST ADVISORY REMAINS IN EFFECT UNTIL MIDNIGHT EST TONIGHT...",
       "...BLOWING DUST ADVISORY REMAINS IN EFFECT FROM 7 AM SATURDAY TO 2 AM EST SUNDAY...",
       "...BLOWING DUST ADVISORY REMAINS IN EFFECT FROM MIDNIGHT SUNDAY NIGHT TO NOON EST MONDAY...",
#       "A BLOWING DUST ADVISORY REMAINS IN EFFECT UNTIL MIDNIGHT EST TONIGHT. A BLOWING DUST ADVISORY REMAINS IN EFFECT FROM 7 AM SATURDAY TO 2 AM EST SUNDAY. A BLOWING DUST ADVISORY REMAINS IN EFFECT FROM MIDNIGHT SUNDAY NIGHT TO NOON EST MONDAY.",
       "|* SEGMENT TEXT GOES HERE *|.",
       "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
       "$$",
       ],
    },

    {
    "commentary": """<--1---> <-----2---|-----><-----2------*> <-1-> <-2->
       Formatter run within 30 minutes of the event ending time.  EXP is
       generated.  The two new events for this year remain as CON with ETNs
       of 0001 and 0002.""",
    "name": "Hazard_CrossingYear_3e",
    "drtTime": "20100102_0500",
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", -24, 24, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 24, 29, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 36, 55, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 77, 89, "DU.Y", ["FLZ049"]),
       ],
    "checkStrings": [
       "WWUS72 KTBW 020500",
       "NPWTBW",
       "URGENT - WEATHER MESSAGE",
       "NATIONAL WEATHER SERVICE TAMPA BAY RUSKIN FL",
       "1200 AM EST SAT JAN 2 2010",
       "...|*OVERVIEW HEADLINE (MUST EDIT)*|...",
       ".|*OVERVIEW (MUST EDIT)*|.",
       "FLZ049-021300-",
       "/O.EXP.KTBW.DU.Y.0002.000000T0000Z-100102T0500Z/",
       "/O.CON.KTBW.DU.Y.0001.100102T1200Z-100103T0700Z/",
       "/O.CON.KTBW.DU.Y.0002.100104T0500Z-100104T1700Z/",
       "PASCO-",
       "1200 AM EST SAT JAN 2 2010",
       "...BLOWING DUST ADVISORY REMAINS IN EFFECT FROM 7 AM THIS MORNING TO 2 AM EST SUNDAY...",
       "...BLOWING DUST ADVISORY REMAINS IN EFFECT FROM MIDNIGHT SUNDAY NIGHT TO NOON EST MONDAY...",
       "...BLOWING DUST ADVISORY HAS EXPIRED...",
#       "THE BLOWING DUST ADVISORY IS NO LONGER IN EFFECT. A BLOWING DUST ADVISORY REMAINS IN EFFECT FROM 7 AM THIS MORNING TO 2 AM EST SUNDAY. A BLOWING DUST ADVISORY REMAINS IN EFFECT FROM MIDNIGHT SUNDAY NIGHT TO NOON EST MONDAY.",
       "|* SEGMENT TEXT GOES HERE *|.",
       "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
       "$$",
       ],
    },


    {
    "commentary": "Deleting hazard grids.",
    "name": "Hazard_CrossingYear_Cleanup3",
    "productType": None,
    "checkStrings": [],
    "clearHazardsTable": 1,
    },

    {
    "commentary": """<-*-------1------|---->
       Forecaster creates event that spans the year. ETN assigned 0001
       based on 2009's pool of ETNs.""",
    "name": "Hazard_CrossingYear_4a",
    "drtTime": "20091231_0211",
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", -24, 24, "DU.Y", ["FLZ049"]),
       ],
    "checkStrings": [
       "WWUS72 KTBW 310211",
       "NPWTBW",
       "URGENT - WEATHER MESSAGE",
       "NATIONAL WEATHER SERVICE TAMPA BAY RUSKIN FL",
       "911 PM EST WED DEC 30 2009",
       "...|*OVERVIEW HEADLINE (MUST EDIT)*|...",
       ".|*OVERVIEW (MUST EDIT)*|.",
       "FLZ049-311015-",
       "/O.NEW.KTBW.DU.Y.0001.091231T0211Z-100102T0000Z/",
       "PASCO-",
       "911 PM EST WED DEC 30 2009",
       "...BLOWING DUST ADVISORY IN EFFECT UNTIL 7 PM EST FRIDAY...",
#       "THE NATIONAL WEATHER SERVICE IN TAMPA BAY RUSKIN HAS ISSUED A BLOWING DUST ADVISORY...WHICH IS IN EFFECT UNTIL 7 PM EST FRIDAY.",
       "|* SEGMENT TEXT GOES HERE *|.",
       "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
       "$$",
       ],
    },

    {
    "commentary": """<--------1------|*---->  <-1->
       Current time is Jan 1 and we are in the middle of the event issued
       last year. Forecaster issues a new event, it gets assigned ETN 0001
       since it is the first event for the new year.""",
    "name": "Hazard_CrossingYear_4b",
    "drtTime": "20100101_0000",
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", -24, 24, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 30, 38, "DU.Y", ["FLZ049"]),
       ],
    "checkStrings": [
       "WWUS72 KTBW 010000",
       "NPWTBW",
       "URGENT - WEATHER MESSAGE",
       "NATIONAL WEATHER SERVICE TAMPA BAY RUSKIN FL",
       "700 PM EST THU DEC 31 2009",
       "...|*OVERVIEW HEADLINE (MUST EDIT)*|...",
       ".|*OVERVIEW (MUST EDIT)*|.",
       "FLZ049-010800-",
       "/O.NEW.KTBW.DU.Y.0001.100102T0600Z-100102T1400Z/",
       "/O.CON.KTBW.DU.Y.0001.000000T0000Z-100102T0000Z/",
       "PASCO-",
       "700 PM EST THU DEC 31 2009",
       "...BLOWING DUST ADVISORY REMAINS IN EFFECT UNTIL 7 PM EST FRIDAY...",
       "...BLOWING DUST ADVISORY IN EFFECT FROM 1 AM TO 9 AM EST SATURDAY...",
#       "THE NATIONAL WEATHER SERVICE IN TAMPA BAY RUSKIN HAS ISSUED A BLOWING DUST ADVISORY...WHICH IS IN EFFECT FROM 1 AM TO 9 AM EST SATURDAY. A BLOWING DUST ADVISORY REMAINS IN EFFECT UNTIL 7 PM EST FRIDAY.",
       "|* SEGMENT TEXT GOES HERE *|.",
       "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
       "$$",

       ],
    },

    {
    "commentary": """<--------1------|--*-->  <-1->  <------2------->
       Still in the middle of the event issued the previous year.  Forecaster
       issues another event, which gets assigned an ETN of 0002.""",
    "name": "Hazard_CrossingYear_4c",
    "drtTime": "20100101_2200",
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", -24, 24, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 26, 28, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 30, 38, "DU.Y", ["FLZ049"]),
       ],
    "checkStrings": [
       "WWUS72 KTBW 012200",
       "NPWTBW",
       "URGENT - WEATHER MESSAGE",
       "NATIONAL WEATHER SERVICE TAMPA BAY RUSKIN FL",
       "500 PM EST FRI JAN 1 2010",
       "...|*OVERVIEW HEADLINE (MUST EDIT)*|...",
       ".|*OVERVIEW (MUST EDIT)*|.",
       "FLZ049-020600-",
       "/O.NEW.KTBW.DU.Y.0002.100102T0200Z-100102T0400Z/",
       "/O.CON.KTBW.DU.Y.0001.000000T0000Z-100102T0000Z/",
       "/O.CON.KTBW.DU.Y.0001.100102T0600Z-100102T1400Z/",
       "PASCO-",
       "500 PM EST FRI JAN 1 2010",
       "...BLOWING DUST ADVISORY REMAINS IN EFFECT UNTIL 7 PM EST THIS EVENING...",
       "...BLOWING DUST ADVISORY IN EFFECT FROM 9 PM TO 11 PM EST THIS EVENING...",
       "...BLOWING DUST ADVISORY REMAINS IN EFFECT FROM 1 AM TO 9 AM EST SATURDAY...",
#       "THE NATIONAL WEATHER SERVICE IN TAMPA BAY RUSKIN HAS ISSUED A BLOWING DUST ADVISORY...WHICH IS IN EFFECT FROM 9 PM TO 11 PM EST THIS EVENING. A BLOWING DUST ADVISORY REMAINS IN EFFECT UNTIL 7 PM EST THIS EVENING. A BLOWING DUST ADVISORY REMAINS IN EFFECT FROM 1 AM TO 9 AM EST SATURDAY.",
       "|* SEGMENT TEXT GOES HERE *|.",
       "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
       "$$",
       ],
    },

    {
    "commentary": """<--------1------|---->  <*-1->  <------2------->
      We are in the 1st event issued for the current year. We get two CONs
      for the events since no changes were made to the grids.""",
    "name": "Hazard_CrossingYear_4d",
    "drtTime": "20100102_0300",
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", -24, 24, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 26, 28, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 30, 38, "DU.Y", ["FLZ049"]),
       ],
    "checkStrings": [
       "WWUS72 KTBW 020300",
       "NPWTBW",
       "URGENT - WEATHER MESSAGE",
       "NATIONAL WEATHER SERVICE TAMPA BAY RUSKIN FL",
       "1000 PM EST FRI JAN 1 2010",
       "...|*OVERVIEW HEADLINE (MUST EDIT)*|...",
       ".|*OVERVIEW (MUST EDIT)*|.",
       "FLZ049-021100-",
       "/O.CON.KTBW.DU.Y.0002.000000T0000Z-100102T0400Z/",
       "/O.CON.KTBW.DU.Y.0001.100102T0600Z-100102T1400Z/",
       "PASCO-",
       "1000 PM EST FRI JAN 1 2010",
       "...BLOWING DUST ADVISORY REMAINS IN EFFECT UNTIL 11 PM EST THIS EVENING...",
       "...BLOWING DUST ADVISORY REMAINS IN EFFECT FROM 1 AM TO 9 AM EST SATURDAY...",
#       "A BLOWING DUST ADVISORY REMAINS IN EFFECT UNTIL 11 PM EST THIS EVENING. A BLOWING DUST ADVISORY REMAINS IN EFFECT FROM 1 AM TO 9 AM EST SATURDAY.",
       "|* SEGMENT TEXT GOES HERE *|.",
       "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
       "$$",
       ],
    },

    {
    "commentary": """<--------1------|---->  <-1-> * <------2-------> 
      The first event for the new year is over.  The second event for the
      new year has not yet started. Result is a CON code for the 2nd event
      and no mention of the first event.""",
    "name": "Hazard_CrossingYear_4e",
    "drtTime": "20100102_0500",
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", -24, 24, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 26, 28, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 30, 38, "DU.Y", ["FLZ049"]),
       ],
    "checkStrings": [
       "WWUS72 KTBW 020500",
       "NPWTBW",
       "URGENT - WEATHER MESSAGE",
       "NATIONAL WEATHER SERVICE TAMPA BAY RUSKIN FL",
       "1200 AM EST SAT JAN 2 2010",
       "...|*OVERVIEW HEADLINE (MUST EDIT)*|...",
       ".|*OVERVIEW (MUST EDIT)*|.",
       "FLZ049-021300-",
       "/O.CON.KTBW.DU.Y.0001.100102T0600Z-100102T1400Z/",
       "PASCO-",
       "1200 AM EST SAT JAN 2 2010",
       "...BLOWING DUST ADVISORY REMAINS IN EFFECT UNTIL 9 AM EST THIS MORNING...",
       "A BLOWING DUST ADVISORY REMAINS IN EFFECT UNTIL 9 AM EST THIS MORNING.",
       "|* SEGMENT TEXT GOES HERE *|.",
       "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT",
       "VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
       "$$",

       ],
    },

    {
    "commentary": """<--------1------|---->  <-1->  <*------2------->
      Time has progressed to be start of the 2nd event for the year.  Start
      time for VTEC is all zeros to indicate event in progress. CON code
      since no other changes to time.""",
    "name": "Hazard_CrossingYear_4f",
    "drtTime": "20100102_0600",
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", -24, 24, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 26, 28, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 30, 38, "DU.Y", ["FLZ049"]),
       ],
    "checkStrings": [
       "WWUS72 KTBW 020600",
       "NPWTBW",
       "URGENT - WEATHER MESSAGE",
       "NATIONAL WEATHER SERVICE TAMPA BAY RUSKIN FL",
       "100 AM EST SAT JAN 2 2010",
       "...|*OVERVIEW HEADLINE (MUST EDIT)*|...",
       ".|*OVERVIEW (MUST EDIT)*|.",
       "FLZ049-021400-",
       "/O.CON.KTBW.DU.Y.0001.000000T0000Z-100102T1400Z/",
       "PASCO-",
       "100 AM EST SAT JAN 2 2010",
       "...BLOWING DUST ADVISORY REMAINS IN EFFECT UNTIL 9 AM EST THIS MORNING...",
       "A BLOWING DUST ADVISORY REMAINS IN EFFECT UNTIL 9 AM EST THIS MORNING.",
       "|* SEGMENT TEXT GOES HERE *|.",
       "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
       "$$",
       ],
    },

    {
    "commentary": """<--------1------|---->  <-1->  <------2-----*-->
       Farther into the 2nd event for the year.  CON code generated.""",
    "name": "Hazard_CrossingYear_4g",
    "drtTime": "20100102_1300",
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", -24, 24, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 26, 28, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 30, 38, "DU.Y", ["FLZ049"]),
       ],
    "checkStrings": [
       "WWUS72 KTBW 021300",
       "NPWTBW",
       "URGENT - WEATHER MESSAGE",
       "NATIONAL WEATHER SERVICE TAMPA BAY RUSKIN FL",
       "800 AM EST SAT JAN 2 2010",
       "...|*OVERVIEW HEADLINE (MUST EDIT)*|...",
       ".|*OVERVIEW (MUST EDIT)*|.",
       "FLZ049-021400-",
       "/O.CON.KTBW.DU.Y.0001.000000T0000Z-100102T1400Z/",
       "PASCO-",
       "800 AM EST SAT JAN 2 2010",
       "...BLOWING DUST ADVISORY REMAINS IN EFFECT UNTIL 9 AM EST THIS MORNING...",
       "A BLOWING DUST ADVISORY REMAINS IN EFFECT UNTIL 9 AM EST THIS MORNING.",
       "|* SEGMENT TEXT GOES HERE *|.",
       "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
       "$$",

       ],
    },

    {
    "commentary": "Deleting hazard grids.",
    "name": "Hazard_CrossingYear_Cleanup4",
    "productType": None,
    "checkStrings": [],
    "clearHazardsTable": 1,
    },
    {
    "commentary": """<*---1--->|
      This scenario involves multiple zones and crossing years. This first
      step simply sets up an event starting in December and ending at 0z
      Jan 1.""",
    "name": "Hazard_CrossingYear_5a",
    "drtTime": "20091231_0211",
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", -24, 0, "DU.Y", ["FLZ049"]),
       ],
    "checkStrings": [
       "WWUS72 KTBW 310211",
       "NPWTBW",
       "URGENT - WEATHER MESSAGE",
       "NATIONAL WEATHER SERVICE TAMPA BAY RUSKIN FL",
       "911 PM EST WED DEC 30 2009",
       "...|*OVERVIEW HEADLINE (MUST EDIT)*|...",
       ".|*OVERVIEW (MUST EDIT)*|.",
       "FLZ049-311015-",
       "/O.NEW.KTBW.DU.Y.0001.091231T0211Z-100101T0000Z/",
       "PASCO-",
       "911 PM EST WED DEC 30 2009",
       "...BLOWING DUST ADVISORY IN EFFECT UNTIL 7 PM EST THURSDAY...",
#       "THE NATIONAL WEATHER SERVICE IN TAMPA BAY RUSKIN HAS ISSUED A BLOWING DUST ADVISORY...WHICH IS IN EFFECT UNTIL 7 PM EST THURSDAY.",
       "|* SEGMENT TEXT GOES HERE *|.",
       "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
       "$$",
       ],
    },

    {
    "commentary": """<---1--->*|<---1-----> and |<---1---> 
       This step isn't decoded by the VTEC decoder allowing us to explore
       different scenarios.  This scenario is the current time is right before
       the start of the new year and the forecaster extends the current 
       event until 7z for one zone plus a new zone (EXB coding), and until
       9z for the original zone (EXT coding).""",
    "name": "Hazard_CrossingYear_5b",
    "drtTime": "20091231_2359",
    "decodeVTEC": 0,  #don't decode the VTEC
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", -24, 0, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 0, 7, "DU.Y", ["FLZ049","FLZ050"]),
       ("Fcst", "Hazards", "DISCRETE", 7, 9, "DU.Y", ["FLZ049"]),
       ],
    "checkStrings": [
       "WWUS72 KTBW 312359",
       "NPWTBW",
       "URGENT - WEATHER MESSAGE",
       "NATIONAL WEATHER SERVICE TAMPA BAY RUSKIN FL",
       "659 PM EST THU DEC 31 2009",
       "...|*OVERVIEW HEADLINE (MUST EDIT)*|...",
       ".|*OVERVIEW (MUST EDIT)*|.",
       "FLZ050-010700-",
       "/O.EXB.KTBW.DU.Y.0001.100101T0000Z-100101T0700Z/",
       "PINELLAS-",
       "659 PM EST THU DEC 31 2009",
       "...BLOWING DUST ADVISORY IN EFFECT UNTIL 2 AM EST FRIDAY...",
       "THE NATIONAL WEATHER SERVICE IN TAMPA BAY RUSKIN HAS ISSUED A BLOWING DUST ADVISORY...WHICH IS IN EFFECT UNTIL 2 AM EST FRIDAY.",
       "|* SEGMENT TEXT GOES HERE *|.",
       "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
       "$$",
       "FLZ049-010800-",
       "/O.EXT.KTBW.DU.Y.0001.000000T0000Z-100101T0900Z/",
       "PASCO-",
       "659 PM EST THU DEC 31 2009",
       "...BLOWING DUST ADVISORY NOW IN EFFECT UNTIL 4 AM EST FRIDAY...",
       "THE BLOWING DUST ADVISORY IS NOW IN EFFECT UNTIL 4 AM EST FRIDAY.",
       "|* SEGMENT TEXT GOES HERE *|.",
       "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
       "$$",
       ],
    },

    {
    "commentary": """<---1--->|*<---1-----> and |<---1--->
       We continue this scenario and run the decoder this time.
       This scenario is the current time is at the beginning of the year.
       The forecaster extends the current event that just ended at 0000z 
       until 9z for the original zone, and adds a zone for the event until
       7z. Result is two NEW events with ETN of 0001 for the new year.""",
    "name": "Hazard_CrossingYear_5c",
    "drtTime": "20100101_0000",
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", -24, 0, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 0, 7, "DU.Y", ["FLZ049","FLZ050"]),
       ("Fcst", "Hazards", "DISCRETE", 7, 9, "DU.Y", ["FLZ049"]),
       ],
    "checkStrings": [
      "WWUS72 KTBW 010000",
      "NPWTBW",
      "URGENT - WEATHER MESSAGE",
      "NATIONAL WEATHER SERVICE TAMPA BAY RUSKIN FL",
      "700 PM EST THU DEC 31 2009",
      "...|*OVERVIEW HEADLINE (MUST EDIT)*|...",
      ".|*OVERVIEW (MUST EDIT)*|.",
      "FLZ049-010800-",
      "/O.EXP.KTBW.DU.Y.0001.000000T0000Z-100101T0000Z/",
      "/O.NEW.KTBW.DU.Y.0001.100101T0000Z-100101T0900Z/",
      "PASCO-",
      "700 PM EST THU DEC 31 2009",
      "...BLOWING DUST ADVISORY IN EFFECT UNTIL 4 AM EST FRIDAY...",
      "...BLOWING DUST ADVISORY HAS EXPIRED...",
#      "THE NATIONAL WEATHER SERVICE IN TAMPA BAY RUSKIN HAS ISSUED A BLOWING DUST ADVISORY...WHICH IS IN EFFECT UNTIL 4 AM EST FRIDAY. THE BLOWING DUST ADVISORY IS NO LONGER IN EFFECT.",
      "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
      "$$",
      "FLZ050-010700-",
      "/O.NEW.KTBW.DU.Y.0001.100101T0000Z-100101T0700Z/",
      "PINELLAS-",
      "700 PM EST THU DEC 31 2009",
      "...BLOWING DUST ADVISORY IN EFFECT UNTIL 2 AM EST FRIDAY...",
      "THE NATIONAL WEATHER SERVICE IN TAMPA BAY RUSKIN HAS ISSUED A BLOWING DUST ADVISORY...WHICH IS IN EFFECT UNTIL 2 AM EST FRIDAY.",
      "|* SEGMENT TEXT GOES HERE *|.",
      "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
      "$$",
       ],
    },

    {
    "commentary": """<---1--->|<---1---*--> and |<---1--->* 
      Time continues until the event expires in the one zone, but continues
      another two hours for the second zone.  EXP generated in one zone and
      CON in the other zone.""",
    "name": "Hazard_CrossingYear_5d",
    "drtTime": "20100101_0715",
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", -24, 0, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 0, 7, "DU.Y", ["FLZ049","FLZ050"]),
       ("Fcst", "Hazards", "DISCRETE", 7, 9, "DU.Y", ["FLZ049"]),
       ],
    "checkStrings": [
      "WWUS72 KTBW 010715",
      "NPWTBW",
      "URGENT - WEATHER MESSAGE",
      "NATIONAL WEATHER SERVICE TAMPA BAY RUSKIN FL",
      "215 AM EST FRI JAN 1 2010",
      "...|*OVERVIEW HEADLINE (MUST EDIT)*|...",
      ".|*OVERVIEW (MUST EDIT)*|.",
      "FLZ050-010815-",
      "/O.EXP.KTBW.DU.Y.0001.000000T0000Z-100101T0700Z/",
      "PINELLAS-",
      "215 AM EST FRI JAN 1 2010",
      "...BLOWING DUST ADVISORY HAS EXPIRED...",
      "THE BLOWING DUST ADVISORY IS NO LONGER IN EFFECT.",
      "$$",
      "FLZ049-010900-",
      "/O.CON.KTBW.DU.Y.0001.000000T0000Z-100101T0900Z/",
      "PASCO-",
      "215 AM EST FRI JAN 1 2010",
      "...BLOWING DUST ADVISORY REMAINS IN EFFECT UNTIL 4 AM EST EARLY THIS MORNING...",
      "A BLOWING DUST ADVISORY REMAINS IN EFFECT UNTIL 4 AM EST EARLY THIS MORNING.",
      "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
      "$$",
       ],
    },

    {
    "commentary": """<---1--->|, <---1----*-> and |<---1--->* 
      Time is after the first event, but still in the second event.  CON
      is generated.""",
    "name": "Hazard_CrossingYear_5e",
    "drtTime": "20100101_0829",
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", -24, 0, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 0, 7, "DU.Y", ["FLZ049","FLZ050"]),
       ("Fcst", "Hazards", "DISCRETE", 7, 9, "DU.Y", ["FLZ049"]),
       ],
    "checkStrings": [
      "WWUS72 KTBW 010829",
      "NPWTBW",
      "URGENT - WEATHER MESSAGE",
      "NATIONAL WEATHER SERVICE TAMPA BAY RUSKIN FL",
      "329 AM EST FRI JAN 1 2010",
      "...|*OVERVIEW HEADLINE (MUST EDIT)*|...",
      ".|*OVERVIEW (MUST EDIT)*|.",
      "FLZ049-010930-",
      "/O.CON.KTBW.DU.Y.0001.000000T0000Z-100101T0900Z/",
      "PASCO-",
      "329 AM EST FRI JAN 1 2010",
      "...BLOWING DUST ADVISORY REMAINS IN EFFECT UNTIL 4 AM EST EARLY THIS MORNING...",
      "A BLOWING DUST ADVISORY REMAINS IN EFFECT UNTIL 4 AM EST EARLY THIS MORNING.",
      "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
      "$$",
       ],
    },

    {
    "commentary": """<---1--->|<---1-----*> and |<---1--->  *
      Forecaster removes the grid prior to the ending time of the remaining
      event, which generates a CAN.""",
    "name": "Hazard_CrossingYear_5f",
    "drtTime": "20100101_0835",
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ],
    "checkStrings": [
      "WWUS72 KTBW 010835",
      "NPWTBW",
      "URGENT - WEATHER MESSAGE",
      "NATIONAL WEATHER SERVICE TAMPA BAY RUSKIN FL",
      "335 AM EST FRI JAN 1 2010",
      "...|*OVERVIEW HEADLINE (MUST EDIT)*|...",
      ".|*OVERVIEW (MUST EDIT)*|.",
      "FLZ049-010945-",
      "/O.CAN.KTBW.DU.Y.0001.000000T0000Z-100101T0900Z/",
      "PASCO-",
      "335 AM EST FRI JAN 1 2010",
      "...BLOWING DUST ADVISORY IS CANCELLED...",
      "THE NATIONAL WEATHER SERVICE IN TAMPA BAY RUSKIN HAS CANCELLED THE BLOWING DUST ADVISORY.",
      "$$",
       ],
    },


    {
    "commentary": "Deleting hazard grids.",
    "name": "Hazard_CrossingYear_Cleanup5",
    "productType": None,
    "checkStrings": [],
    "clearHazardsTable": 1,
    },

    {
    "commentary": """<*---1--->|
       This scenario tests another two zone event.  The first event is issued
       last year (for one zone) and extends until 0000z on Jan 1st.""",
    "name": "Hazard_CrossingYear_6a",
    "drtTime": "20091231_0211",
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", -24, 0, "DU.Y", ["FLZ049"]),
       ],
    "checkStrings": [
      "WWUS72 KTBW 310211",
      "NPWTBW",
      "URGENT - WEATHER MESSAGE",
      "NATIONAL WEATHER SERVICE TAMPA BAY RUSKIN FL",
      "911 PM EST WED DEC 30 2009",
      "...|*OVERVIEW HEADLINE (MUST EDIT)*|...",
      ".|*OVERVIEW (MUST EDIT)*|.",
      "FLZ049-311015-",
      "/O.NEW.KTBW.DU.Y.0001.091231T0211Z-100101T0000Z/",
      "PASCO-",
      "911 PM EST WED DEC 30 2009",
      "...BLOWING DUST ADVISORY IN EFFECT UNTIL 7 PM EST THURSDAY...",
      "THE NATIONAL WEATHER SERVICE IN TAMPA BAY RUSKIN HAS ISSUED A BLOWING DUST ADVISORY...WHICH IS IN EFFECT UNTIL 7 PM EST THURSDAY.",
      "|* SEGMENT TEXT GOES HERE *|.",
      "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
      "$$",
       ],
    },

    {
    "commentary": """<---1--->*|<---1-----> and |<---1---> 
       Time is just before 0000z Jan 1st. Forecaster extends the event until
       9z for the same zone, and adds a second zone starting at 0z until 7z.
       EXB and EXT are generated for the zones.""",
    "name": "Hazard_CrossingYear_6b",
    "drtTime": "20091231_2359",
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", -24, 0, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 0, 7, "DU.Y", ["FLZ049","FLZ050"]),
       ("Fcst", "Hazards", "DISCRETE", 7, 9, "DU.Y", ["FLZ049"]),
       ],
    "checkStrings": [
      "WWUS72 KTBW 312359",
      "NPWTBW",
      "URGENT - WEATHER MESSAGE",
      "NATIONAL WEATHER SERVICE TAMPA BAY RUSKIN FL",
      "659 PM EST THU DEC 31 2009",
      "...|*OVERVIEW HEADLINE (MUST EDIT)*|...",
      ".|*OVERVIEW (MUST EDIT)*|.",
      "FLZ050-010700-",
      "/O.EXB.KTBW.DU.Y.0001.100101T0000Z-100101T0700Z/",
      "PINELLAS-",
      "659 PM EST THU DEC 31 2009",
      "...BLOWING DUST ADVISORY IN EFFECT UNTIL 2 AM EST FRIDAY...",
      "THE NATIONAL WEATHER SERVICE IN TAMPA BAY RUSKIN HAS ISSUED A BLOWING DUST ADVISORY...WHICH IS IN EFFECT UNTIL 2 AM EST FRIDAY.",
      "|* SEGMENT TEXT GOES HERE *|.",
      "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
      "$$",
      "FLZ049-010800-",
      "/O.EXT.KTBW.DU.Y.0001.000000T0000Z-100101T0900Z/",
      "PASCO-",
      "659 PM EST THU DEC 31 2009",
      "...BLOWING DUST ADVISORY NOW IN EFFECT UNTIL 4 AM EST FRIDAY...",
      "THE BLOWING DUST ADVISORY IS NOW IN EFFECT UNTIL 4 AM EST FRIDAY.",
      "|* SEGMENT TEXT GOES HERE *|.",
      "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
      "$$",

       ],
    },


    {
    "commentary": """<---1--->|<---1---*--> <--1--> and |<---1--->* <---1--->
      We are at the end of the first event in one zone, but in the middle of
      the event in the second zone.  Forecaster adds a new event with starting
      times of 14z for both zones and continuing until 19z in one zone and 0z
      for the second zone.  ETNs of 0001 are generated for the new event since
      it is the first event issued for the new year.""", 
    "name": "Hazard_CrossingYear_6c",
    "drtTime": "20100101_0715",
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", -24, 0, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 0, 7, "DU.Y", ["FLZ049","FLZ050"]),
       ("Fcst", "Hazards", "DISCRETE", 7, 9, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 14, 19, "DU.Y", ["FLZ049","FLZ050"]),
       ("Fcst", "Hazards", "DISCRETE", 19, 24, "DU.Y", ["FLZ050"]),
       ],
    "checkStrings": [
      "WWUS72 KTBW 010715",
      "NPWTBW",
      "URGENT - WEATHER MESSAGE",
      "NATIONAL WEATHER SERVICE TAMPA BAY RUSKIN FL",
      "215 AM EST FRI JAN 1 2010",
      "...|*OVERVIEW HEADLINE (MUST EDIT)*|...",
      ".|*OVERVIEW (MUST EDIT)*|.",
      "FLZ050-011515-",
      "/O.EXP.KTBW.DU.Y.0001.000000T0000Z-100101T0700Z/",
      "/O.NEW.KTBW.DU.Y.0001.100101T1400Z-100102T0000Z/",
      "PINELLAS-",
      "215 AM EST FRI JAN 1 2010",
      "...BLOWING DUST ADVISORY IN EFFECT FROM 9 AM THIS MORNING TO 7 PM EST THIS EVENING...",
      "...BLOWING DUST ADVISORY HAS EXPIRED...",
#      "THE NATIONAL WEATHER SERVICE IN TAMPA BAY RUSKIN HAS ISSUED A BLOWING DUST ADVISORY...WHICH IS IN EFFECT FROM 9 AM THIS MORNING TO 7 PM EST THIS EVENING. THE BLOWING DUST ADVISORY IS NO LONGER IN EFFECT.",
      "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
      "$$",
      "FLZ049-011515-",
      "/O.NEW.KTBW.DU.Y.0001.100101T1400Z-100101T1900Z/",
      "/O.CON.KTBW.DU.Y.0001.000000T0000Z-100101T0900Z/",
      "PASCO-",
      "215 AM EST FRI JAN 1 2010",
      "...BLOWING DUST ADVISORY REMAINS IN EFFECT UNTIL 4 AM EST EARLY THIS MORNING...",
      "...BLOWING DUST ADVISORY IN EFFECT FROM 9 AM THIS MORNING TO 2 PM EST THIS AFTERNOON...",
#      "THE NATIONAL WEATHER SERVICE IN TAMPA BAY RUSKIN HAS ISSUED A BLOWING DUST ADVISORY...WHICH IS IN EFFECT FROM 9 AM THIS MORNING TO 2 PM EST THIS AFTERNOON. A BLOWING DUST ADVISORY REMAINS IN EFFECT UNTIL 4 AM EST EARLY THIS MORNING.",
      "|* SEGMENT TEXT GOES HERE *|.",
      "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
      "$$",

       ],
    },

    {
    "commentary": """<---1--->| <---1----*-> <--1--> and |<---1--->* <--1-->
       Event within 30 minutes of ending time in one zone, still ongoing in
       other zone. No changes for the new event.""", 
    "name": "Hazard_CrossingYear_6d",
    "drtTime": "20100101_0845",
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", -24, 0, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 0, 7, "DU.Y", ["FLZ049","FLZ050"]),
       ("Fcst", "Hazards", "DISCRETE", 7, 9, "DU.Y", ["FLZ049"]),
       ("Fcst", "Hazards", "DISCRETE", 14, 19, "DU.Y", ["FLZ049","FLZ050"]),
       ("Fcst", "Hazards", "DISCRETE", 19, 24, "DU.Y", ["FLZ050"]),
       ],
    "checkStrings": [
      "WWUS72 KTBW 010845",
      "NPWTBW",
      "URGENT - WEATHER MESSAGE",
      "NATIONAL WEATHER SERVICE TAMPA BAY RUSKIN FL",
      "345 AM EST FRI JAN 1 2010",
      "...|*OVERVIEW HEADLINE (MUST EDIT)*|...",
      ".|*OVERVIEW (MUST EDIT)*|.",
      "FLZ049-011645-",
      "/O.EXP.KTBW.DU.Y.0001.000000T0000Z-100101T0900Z/",
      "/O.CON.KTBW.DU.Y.0001.100101T1400Z-100101T1900Z/",
      "PASCO-",
      "345 AM EST FRI JAN 1 2010",
      "...BLOWING DUST ADVISORY REMAINS IN EFFECT FROM 9 AM THIS MORNING TO 2 PM EST THIS AFTERNOON...",
      "...BLOWING DUST ADVISORY WILL EXPIRE AT 4 AM EST EARLY THIS MORNING...",
#      "THE BLOWING DUST ADVISORY WILL EXPIRE AT 4 AM EST EARLY THIS MORNING. A BLOWING DUST ADVISORY REMAINS IN EFFECT FROM 9 AM THIS MORNING TO 2 PM EST THIS AFTERNOON.",
      "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
      "$$",
      "FLZ050-011645-",
      "/O.CON.KTBW.DU.Y.0001.100101T1400Z-100102T0000Z/",
      "PINELLAS-",
      "345 AM EST FRI JAN 1 2010",
      "...BLOWING DUST ADVISORY REMAINS IN EFFECT FROM 9 AM THIS MORNING TO 7 PM EST THIS EVENING...",
      "A BLOWING DUST ADVISORY REMAINS IN EFFECT FROM 9 AM THIS MORNING TO 7 PM EST THIS EVENING.",
      "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
      "$$",
       ],
    },

    {
    "commentary": """<---1--->|<---1-----> * <--1--> and |<---1---> * <---1--->
      Last event has not started yet.  CONs generated.""",
    "name": "Hazard_CrossingYear_6e",
    "drtTime": "20100101_0914",
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", 14, 19, "DU.Y", ["FLZ049","FLZ050"]),
       ("Fcst", "Hazards", "DISCRETE", 19, 24, "DU.Y", ["FLZ050"]),
       ],
    "checkStrings": [
      "WWUS72 KTBW 010914",
      "NPWTBW",
      "URGENT - WEATHER MESSAGE",
      "NATIONAL WEATHER SERVICE TAMPA BAY RUSKIN FL",
      "414 AM EST FRI JAN 1 2010",
      "...|*OVERVIEW HEADLINE (MUST EDIT)*|...",
      ".|*OVERVIEW (MUST EDIT)*|.",
      "FLZ050-011715-",
      "/O.CON.KTBW.DU.Y.0001.100101T1400Z-100102T0000Z/",
      "PINELLAS-",
      "414 AM EST FRI JAN 1 2010",
      "...BLOWING DUST ADVISORY REMAINS IN EFFECT FROM 9 AM THIS MORNING TO 7 PM EST THIS EVENING...",
      "A BLOWING DUST ADVISORY REMAINS IN EFFECT FROM 9 AM THIS MORNING TO 7 PM EST THIS EVENING.",
      "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
      "$$",
      "FLZ049-011715-",
      "/O.CON.KTBW.DU.Y.0001.100101T1400Z-100101T1900Z/",
      "PASCO-",
      "414 AM EST FRI JAN 1 2010",
      "...BLOWING DUST ADVISORY REMAINS IN EFFECT FROM 9 AM THIS MORNING TO 2 PM EST THIS AFTERNOON...",
      "A BLOWING DUST ADVISORY REMAINS IN EFFECT FROM 9 AM THIS MORNING TO 2 PM EST THIS AFTERNOON.",
      "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
      "$$",
       ],
    },

    {
    "commentary": """<---1--->|<---1-----> * and |<---1--->  * <---1--->
      Forecaster removes hazard in one zone. Extends time of the event for
      second zone.""", 
    "name": "Hazard_CrossingYear_6f",
    "drtTime": "20100101_1350",
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", 17, 19, "DU.Y", ["FLZ050"]),
       ("Fcst", "Hazards", "DISCRETE", 19, 24, "DU.Y", ["FLZ050"]),
       ],
    "checkStrings": [
      "WWUS72 KTBW 011350",
      "NPWTBW",
      "URGENT - WEATHER MESSAGE",
      "NATIONAL WEATHER SERVICE TAMPA BAY RUSKIN FL",
      "850 AM EST FRI JAN 1 2010",
      "...|*OVERVIEW HEADLINE (MUST EDIT)*|...",
      ".|*OVERVIEW (MUST EDIT)*|.",
      "FLZ049-011500-",
      "/O.CAN.KTBW.DU.Y.0001.100101T1400Z-100101T1900Z/",
      "PASCO-",
      "850 AM EST FRI JAN 1 2010",
      "...BLOWING DUST ADVISORY IS CANCELLED...",
      "THE NATIONAL WEATHER SERVICE IN TAMPA BAY RUSKIN HAS CANCELLED THE BLOWING DUST ADVISORY.",
      "$$",
      "FLZ050-012200-",
      "/O.EXT.KTBW.DU.Y.0001.100101T1700Z-100102T0000Z/",
      "PINELLAS-",
      "850 AM EST FRI JAN 1 2010",
      "...BLOWING DUST ADVISORY NOW IN EFFECT FROM NOON TODAY TO 7 PM EST THIS EVENING...",
      "THE BLOWING DUST ADVISORY IS NOW IN EFFECT FROM NOON TODAY TO 7 PM EST THIS EVENING.",
      "A BLOWING DUST ADVISORY MEANS THAT BLOWING DUST WILL RESTRICT VISIBILITIES. TRAVELERS ARE URGED TO USE CAUTION.",
      "$$",
       ],
    },


    {
    "commentary": "Deleting hazard grids.",
    "name": "Hazard_CrossingYear_Cleanup5",
    "productType": None,
    "checkStrings": [],
    "clearHazardsTable": 1,
    },
    ]

       
import TestScript
def testScript(self, dataMgr):
    defaults = {
        "database": "<site>_GRID__Official_00000000_0000",
        "publishGrids": 1,
        "decodeVTEC": 1,
        "gridsStartTime": "20100101_0000",
        "orderStrings": 1,
        "vtecMode": "O",
        "deleteGrids": [("Fcst", "Hazards", "SFC", "all", "all")],
        }
    return TestScript.generalTestScript(self, dataMgr, scripts, defaults)




