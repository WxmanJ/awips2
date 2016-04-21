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
# ETN REuse test case - CAR
#
# Author:
# ----------------------------------------------------------------------------

scripts = [
    {
    "commentary": "Clear out all Hazards Table and Grids.",
    "name": "ETNReuse_1a",
    "productType": None,
    "clearHazardsTable": 1,
    "checkStrings": [],
    },
    {
    "commentary": "Initial setup of two GL.As",
    "name": "ETNReuse_1b",
    "drtTime": "20101203_0200",
    "gridsStartTime": "20101203_0000",
    "productType": "CWF",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", 0, 7, "GL.A", ["GMZ870"]),
       ("Fcst", "Hazards", "DISCRETE", 18, 24+15, "GL.A", ["GMZ870"]),
       ],
    "checkStrings": [
       "FZUS52 KTBW 030200",
       "CWFTBW",
       "Coastal Waters Forecast for Florida",
       "National Weather Service Tampa Bay Ruskin FL",
       "900 PM EST Thu Dec 2 2010",
       "GMZ800-031500-",
       "900 PM EST Thu Dec 2 2010",
       "Synopsis for Bonita Beach to Suwannee River FL out 60 NM",
       "$$",
       "GMZ870-031500-",
#       "/O.NEW.KTBW.GL.A.0001.101203T0200Z-101203T0700Z/",
#       "/O.NEW.KTBW.GL.A.0002.101203T1800Z-101204T1500Z/",
       "Waters from Tarpon Springs to Suwannee River FL out 20 to 60 NM-",
       "900 PM EST Thu Dec 2 2010",
       "...GALE WATCH IN EFFECT UNTIL 2 AM EST FRIDAY...",
       "...GALE WATCH IN EFFECT FROM FRIDAY AFTERNOON THROUGH SATURDAY MORNING...",
       ".TODAY...",
       ".TONIGHT...",
       ".FRIDAY...",
       ".FRIDAY NIGHT...",
       ".SATURDAY...",
       ".SATURDAY NIGHT...",
       ".SUNDAY...",
       ".MONDAY...",
       "$$",
       ],
    },

    {
    "commentary": "Initial setup of two GL.As - switch to CONs - P1",
    "name": "ETNReuse_1c",
    "drtTime": "20101203_0202",
    "gridsStartTime": "20101203_0000",
    "productType": "CWF",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", 0, 7, "GL.A", ["GMZ870"]),
       ("Fcst", "Hazards", "DISCRETE", 18, 24+15, "GL.A", ["GMZ870"]),
       ],
    "checkStrings": [
       "FZUS52 KTBW 030202",
       "CWFTBW",
       "Coastal Waters Forecast for Florida",
       "National Weather Service Tampa Bay Ruskin FL",
       "902 PM EST Thu Dec 2 2010",
       "GMZ800-031500-",
       "902 PM EST Thu Dec 2 2010",
       "Synopsis for Bonita Beach to Suwannee River FL out 60 NM",
       "$$",
       "GMZ870-031500-",
#      "/O.CON.KTBW.GL.A.0001.000000T0000Z-101203T0700Z/",
#      "/O.CON.KTBW.GL.A.0002.101203T1800Z-101204T1500Z/",
       "Waters from Tarpon Springs to Suwannee River FL out 20 to 60 NM-",
       "902 PM EST Thu Dec 2 2010",
       "...GALE WATCH REMAINS IN EFFECT UNTIL 2 AM EST FRIDAY...",
       "...GALE WATCH REMAINS IN EFFECT FROM FRIDAY AFTERNOON THROUGH SATURDAY MORNING...",
       ".TODAY...",
       ".TONIGHT...",
       ".FRIDAY...",
       ".FRIDAY NIGHT...",
       ".SATURDAY...",
       ".SATURDAY NIGHT...",
       ".SUNDAY...",
       ".MONDAY...",
       "$$",
       ],
    },

    {
    "commentary": "1stGL.A EXP, adjust remaining GL.A to allow a SR.A - P2" ,
    "name": "ETNReuse_1d",
    "drtTime": "20101203_0820",
    "gridsStartTime": "20101203_0000",
    "productType": "CWF",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", 8, 18, "GL.A", ["GMZ870"]),
       ("Fcst", "Hazards", "DISCRETE", 18, 24+3, "SR.A", ["GMZ870"]),
       ("Fcst", "Hazards", "DISCRETE", 24+3, 24+16, "GL.A", ["GMZ870"]),
       ],
    "checkStrings": [
      "FZUS52 KTBW 030820",
      "CWFTBW",
      "Coastal Waters Forecast for Florida",
      "National Weather Service Tampa Bay Ruskin FL",
      "320 AM EST Fri Dec 3 2010",
      "GMZ800-032100-",
      "320 AM EST Fri Dec 3 2010",
      "Synopsis for Bonita Beach to Suwannee River FL out 60 NM",
      "$$",
      "GMZ870-032100-",
#      "/O.NEW.KTBW.GL.A.0003.101203T0820Z-101203T1800Z/",
#      "/O.NEW.KTBW.SR.A.0001.101203T1800Z-101204T0300Z/",
#      "/O.EXT.KTBW.GL.A.0002.101204T0300Z-101204T1600Z/",
      "Waters from Tarpon Springs to Suwannee River FL out 20 to 60 NM-",
      "320 AM EST Fri Dec 3 2010",
      "...GALE WATCH IN EFFECT UNTIL 1 PM EST THIS AFTERNOON...",
      "...STORM WATCH IN EFFECT FROM 1 PM EST THIS AFTERNOON THROUGH THIS EVENING...",
      "...GALE WATCH NOW IN EFFECT FROM THIS EVENING THROUGH SATURDAY MORNING...",
      ".TODAY...",
      ".TONIGHT...",
      ".SATURDAY...",
      ".SATURDAY NIGHT...",
      ".SUNDAY...",
      ".SUNDAY NIGHT...",
      ".MONDAY...",
      ".TUESDAY...",
      "$$",
       ],
    },

    {
    "commentary": "CAN SR.A, EXT the GL.A - P3",
    "name": "ETNReuse_1e",
    "drtTime": "20101203_1940",
    "gridsStartTime": "20101203_0000",
    "productType": "CWF",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", 0, 7, "GL.A", ["GMZ870"]),
       ("Fcst", "Hazards", "DISCRETE", 19, 24+15, "GL.A", ["GMZ870"]),
       ],
    "checkStrings": [
      "FZUS52 KTBW 031940",
      "CWFTBW",
      "Coastal Waters Forecast for Florida",
      "National Weather Service Tampa Bay Ruskin FL",
      "240 PM EST Fri Dec 3 2010",
      "GMZ800-040800-",
      "240 PM EST Fri Dec 3 2010",
      "Synopsis for Bonita Beach to Suwannee River FL out 60 NM",
      "$$",
      "GMZ870-040800-",
#     "/O.CAN.KTBW.SR.A.0001.000000T0000Z-101204T0300Z/",
#      "/O.EXT.KTBW.GL.A.0002.101203T1940Z-101204T1500Z/",
      "Waters from Tarpon Springs to Suwannee River FL out 20 to 60 NM-",
      "240 PM EST Fri Dec 3 2010",
      "...GALE WATCH NOW IN EFFECT THROUGH SATURDAY MORNING...",
      ".TODAY...",
      ".TONIGHT...",
      ".SATURDAY...",
      ".SATURDAY NIGHT...",
      ".SUNDAY...",
      ".SUNDAY NIGHT...",
      ".MONDAY...",
      ".TUESDAY...",
      "$$",
       ],
    },

    {
    "commentary": "NEW GL.A and SR.A",
    "name": "ETNReuse_1f",
    "drtTime": "20101206_0834",
    "gridsStartTime": "20101206_0000",
    "productType": "CWF",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", 24+1, 24+11, "SR.A", ["GMZ870"]),
       ("Fcst", "Hazards", "DISCRETE", 24+11, 24+23, "GL.A", ["GMZ870"]),
       ],
    "checkStrings": [
      "FZUS52 KTBW 060834",
      "CWFTBW",
      "Coastal Waters Forecast for Florida",
      "National Weather Service Tampa Bay Ruskin FL",
      "334 AM EST Mon Dec 6 2010",
      "GMZ800-062100-",
      "334 AM EST Mon Dec 6 2010",
      "Synopsis for Bonita Beach to Suwannee River FL out 60 NM",
      "$$",
      "GMZ870-062100-",
#      "/O.NEW.KTBW.SR.A.0002.101207T0100Z-101207T1100Z/",
#      "/O.NEW.KTBW.GL.A.0004.101207T1100Z-101207T2300Z/",
      "Waters from Tarpon Springs to Suwannee River FL out 20 to 60 NM-",
      "334 AM EST Mon Dec 6 2010",
      "...STORM WATCH IN EFFECT FROM THIS EVENING THROUGH LATE TONIGHT...",
      "...GALE WATCH IN EFFECT FROM TUESDAY MORNING THROUGH TUESDAY AFTERNOON...",
      ".TODAY...",
      ".TONIGHT...",
      ".TUESDAY...",
      ".TUESDAY NIGHT...",
      ".WEDNESDAY...",
      ".WEDNESDAY NIGHT...",
      ".THURSDAY...",
      ".FRIDAY...",
      "$$",
       ],
    },

    {
    "commentary": "Deleting hazard grids.",
    "name": "ETNReuse_1g",
    "productType": None,
    "clearHazardsTable": 1,
    "checkStrings": [],
    },

# Shannon NHDW test case, split, create, delete, create, ETN codes and action
    {
    "commentary": "Deleting hazard grids.",
    "name": "ETNReuse_2a",
    "productType": None,
    "clearHazardsTable": 1,
    "checkStrings": [],
    },

    {
    "commentary": "Step1-initial HT.Y, etn0001",
    "name": "ETNReuse_2b",
    "drtTime": "20100706_2056",
    "gridsStartTime": "20100706_0000",
    "cmdLineVars": None,
    "comboFlag": 0,
    "combinations": None,
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", 21, 22, "HT.Y", ["FLZ052"]),
       ],
    "checkStrings": [
      "WWUS72 KTBW 062056",
      "NPWTBW",
      "URGENT - WEATHER MESSAGE",
      "National Weather Service Tampa Bay Ruskin FL",
      "456 PM EDT Tue Jul 6 2010",
      "...|*Overview headline (must edit)*|...",
      ".|*Overview (must edit)*|.",
      "FLZ052-062200-",
      "/O.NEW.KTBW.HT.Y.0001.100706T2100Z-100706T2200Z/",
      "Polk-",
      "456 PM EDT Tue Jul 6 2010",
      "...HEAT ADVISORY IN EFFECT UNTIL 6 PM EDT THIS EVENING...",
      "The National Weather Service in Tampa Bay Ruskin has issued a Heat Advisory...which is in effect until 6 PM EDT this evening.",
#       "|* SEGMENT TEXT GOES HERE *|.",
      "A Heat Advisory means that a period of hot temperatures is expected. The combination of hot temperatures and high humidity will combine to create a situation in which heat illnesses are possible. Drink plenty of fluids...stay in an air-conditioned room...stay out of the sun...and check up on relatives and neighbors.",
      "$$",
       ],
    },

    {
    "commentary": "Step2-initial HT.Y, etn0002",
    "name": "ETNReuse_2c",
    "drtTime": "20101206_2056",
    "gridsStartTime": "20101206_0000",
    "cmdLineVars": None,
    "comboFlag": 0,
    "combinations": None,
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", 21, 48+9, "HT.Y", ["FLZ050","FLZ151"]),
       ],
    "checkStrings": [
      "WWUS72 KTBW 062056",
      "NPWTBW",
      "URGENT - WEATHER MESSAGE",
      "National Weather Service Tampa Bay Ruskin FL",
      "356 PM EST Mon Dec 6 2010",
      "...|*Overview headline (must edit)*|...",
      ".|*Overview (must edit)*|.",
      "FLZ050-151-070500-",
      "/O.NEW.KTBW.HT.Y.0002.101206T2100Z-101208T0900Z/",
      "Pinellas-Coastal Hillsborough-",
      "356 PM EST Mon Dec 6 2010",
      "...HEAT ADVISORY IN EFFECT UNTIL 4 AM EST WEDNESDAY...",
      "The National Weather Service in Tampa Bay Ruskin has issued a Heat Advisory...which is in effect until 4 AM EST Wednesday. ",
#       "|* SEGMENT TEXT GOES HERE *|.",
      "A Heat Advisory means that a period of hot temperatures is expected. The combination of hot temperatures and high humidity will combine to create a situation in which heat illnesses are possible. Drink plenty of fluids...stay in an air-conditioned room...stay out of the sun...and check up on relatives and neighbors.",
      "$$",
       ],
    },


    {
    "commentary": "Step3-insert EH.W in middle of HT.Y",
    "name": "ETNReuse_2d",
    "drtTime": "20101206_2104",
    "gridsStartTime": "20101206_0000",
    "cmdLineVars": None,
    "comboFlag": 0,
    "combinations": None,
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", 21, 24+8, "HT.Y", ["FLZ050","FLZ151"]),
       ("Fcst", "Hazards", "DISCRETE", 24+8, 24+17, "EH.W", ["FLZ050","FLZ151"]),
       ("Fcst", "Hazards", "DISCRETE", 24+17, 48+9, "HT.Y", ["FLZ050","FLZ151"]),
       ],
    "checkStrings": [
      "WWUS72 KTBW 062104",
      "NPWTBW",
      "URGENT - WEATHER MESSAGE",
      "National Weather Service Tampa Bay Ruskin FL",
      "404 PM EST Mon Dec 6 2010",
      "...|*Overview headline (must edit)*|...",
      ".|*Overview (must edit)*|.",
      "FLZ050-151-070515-",
      "/O.NEW.KTBW.EH.W.0001.101207T0800Z-101207T1700Z/",
      "/O.NEW.KTBW.HT.Y.0003.101207T1700Z-101208T0900Z/",
      "/O.EXT.KTBW.HT.Y.0002.000000T0000Z-101207T0800Z/",
      "Pinellas-Coastal Hillsborough-",
      "404 PM EST Mon Dec 6 2010",
      "...HEAT ADVISORY NOW IN EFFECT UNTIL 3 AM EST TUESDAY...",
      "...EXCESSIVE HEAT WARNING IN EFFECT FROM 3 AM TO NOON EST TUESDAY...",
      "...HEAT ADVISORY IN EFFECT FROM NOON TUESDAY TO 4 AM EST WEDNESDAY...",
#      "The National Weather Service in Tampa Bay Ruskin has issued an Excessive Heat Warning...which is in effect from 3 AM to noon EST Tuesday. A Heat Advisory HAS ALSO BEEN ISSUED. THIS Heat Advisory is in effect from noon Tuesday to 4 AM EST Wednesday. The Heat Advisory is now in effect until 3 AM EST Tuesday.",
#       "|* SEGMENT TEXT GOES HERE *|.",
      "A Heat Advisory means that a period of hot temperatures is expected. The combination of hot temperatures and high humidity will combine to create a situation in which heat illnesses are possible. Drink plenty of fluids...stay in an air-conditioned room...stay out of the sun...and check up on relatives and neighbors.",
      "An Excessive Heat Warning means that a prolonged period of dangerously hot temperatures will occur. The combination of hot temperatures and high humidity will combine to create a DANGEROUS SITUATION in which heat illnesses are likely. Drink plenty of fluids...stay in an air-conditioned room...stay out of the sun...and check up on relatives and neighbors.",
      "$$",
       ],
    },

    {
    "commentary": "Step4-delete first HT.Y, leaving EH.W and HT.Y",
    "name": "ETNReuse_2e",
    "drtTime": "20101206_2107",
    "gridsStartTime": "20101206_0000",
    "cmdLineVars": None,
    "comboFlag": 0,
    "combinations": None,
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", 24+8, 24+17, "EH.W", ["FLZ050","FLZ151"]),
       ("Fcst", "Hazards", "DISCRETE", 24+17, 48+9, "HT.Y", ["FLZ050","FLZ151"]),
       ],
    "checkStrings": [
       "WWUS72 KTBW 062107",
       "NPWTBW",
       "URGENT - WEATHER MESSAGE",
       "National Weather Service Tampa Bay Ruskin FL",
       "407 PM EST Mon Dec 6 2010",
       "...|*Overview headline (must edit)*|...",
       ".|*Overview (must edit)*|.",
       "FLZ050-151-070515-",
       "/O.CAN.KTBW.HT.Y.0002.000000T0000Z-101207T0800Z/",
       "/O.CON.KTBW.EH.W.0001.101207T0800Z-101207T1700Z/",
       "/O.CON.KTBW.HT.Y.0003.101207T1700Z-101208T0900Z/",
       "Pinellas-Coastal Hillsborough-",
       "407 PM EST Mon Dec 6 2010",
       "...EXCESSIVE HEAT WARNING REMAINS IN EFFECT FROM 3 AM TO NOON EST TUESDAY...",
       "...HEAT ADVISORY REMAINS IN EFFECT FROM NOON TUESDAY TO 4 AM EST WEDNESDAY...",
       "...HEAT ADVISORY IS CANCELLED...",
#       "The National Weather Service in Tampa Bay Ruskin has cancelled the Heat Advisory. An Excessive Heat Warning remains in effect from 3 AM to noon EST Tuesday. A Heat Advisory remains in effect from noon Tuesday to 4 AM EST Wednesday. ",
#       "|*|* SEGMENT TEXT GOES HERE *|.*|",
       "An Excessive Heat Warning means that a prolonged period of dangerously hot temperatures will occur. The combination of hot temperatures and high humidity will combine to create a DANGEROUS SITUATION in which heat illnesses are likely. Drink plenty of fluids...stay in an air-conditioned room...stay out of the sun...and check up on relatives and neighbors.",
       "A Heat Advisory means that a period of hot temperatures is expected. The combination of hot temperatures and high humidity will combine to create a situation in which heat illnesses are possible. Drink plenty of fluids...stay in an air-conditioned room...stay out of the sun...and check up on relatives and neighbors.",
       "$$",
       ],
    },

    {
    "commentary": "Step5-create HT.Y again in 1st slot",
    "name": "ETNReuse_2f",
    "drtTime": "20101206_2113",
    "gridsStartTime": "20101206_0000",
    "cmdLineVars": None,
    "comboFlag": 0,
    "combinations": None,
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", 23, 24+8, "HT.Y", ["FLZ050","FLZ151"]),
       ("Fcst", "Hazards", "DISCRETE", 24+8, 24+17, "EH.W", ["FLZ050","FLZ151"]),
       ("Fcst", "Hazards", "DISCRETE", 24+17, 48+9, "HT.Y", ["FLZ050","FLZ151"]),
       ],
    "checkStrings": [
      "WWUS72 KTBW 062113",
      "NPWTBW",
      "URGENT - WEATHER MESSAGE",
      "National Weather Service Tampa Bay Ruskin FL",
      "413 PM EST Mon Dec 6 2010",
      "...|*Overview headline (must edit)*|...",
      ".|*Overview (must edit)*|.",
      "FLZ050-151-070515-",
      "/O.NEW.KTBW.HT.Y.0004.101206T2300Z-101207T0800Z/",
      "/O.CON.KTBW.EH.W.0001.101207T0800Z-101207T1700Z/",
      "/O.CON.KTBW.HT.Y.0003.101207T1700Z-101208T0900Z/",
      "Pinellas-Coastal Hillsborough-",
      "413 PM EST Mon Dec 6 2010",
      "...HEAT ADVISORY IN EFFECT UNTIL 3 AM EST TUESDAY...",
      "...EXCESSIVE HEAT WARNING REMAINS IN EFFECT FROM 3 AM TO NOON EST TUESDAY...",
      "...HEAT ADVISORY REMAINS IN EFFECT FROM NOON TUESDAY TO 4 AM EST WEDNESDAY...",
#      "The National Weather Service in Tampa Bay Ruskin has issued a Heat Advisory...which is in effect until 3 AM EST Tuesday. An Excessive Heat Warning remains in effect from 3 AM to noon EST Tuesday. A Heat Advisory remains in effect from noon Tuesday to 4 AM EST Wednesday. ",
#      "|*|* SEGMENT TEXT GOES HERE *|.*|",
      "An Excessive Heat Warning means that a prolonged period of dangerously hot temperatures will occur. The combination of hot temperatures and high humidity will combine to create a DANGEROUS SITUATION in which heat illnesses are likely. Drink plenty of fluids...stay in an air-conditioned room...stay out of the sun...and check up on relatives and neighbors.",
      "A Heat Advisory means that a period of hot temperatures is expected. The combination of hot temperatures and high humidity will combine to create a situation in which heat illnesses are possible. Drink plenty of fluids...stay in an air-conditioned room...stay out of the sun...and check up on relatives and neighbors.",
      "$$",
       ],
    },

    {
    "commentary": "Step6-remove first HT.Y",
    "name": "ETNReuse_2g",
    "drtTime": "20101206_2115",
    "gridsStartTime": "20101206_0000",
    "cmdLineVars": None,
    "comboFlag": 0,
    "combinations": None,
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", 24+8, 24+17, "EH.W", ["FLZ050","FLZ151"]),
       ("Fcst", "Hazards", "DISCRETE", 24+17, 48+9, "HT.Y", ["FLZ050","FLZ151"]),
       ],
    "checkStrings": [
      "WWUS72 KTBW 062115",
      "NPWTBW",
      "URGENT - WEATHER MESSAGE",
      "National Weather Service Tampa Bay Ruskin FL",
      "415 PM EST Mon Dec 6 2010",
      "...|*Overview headline (must edit)*|...",
      ".|*Overview (must edit)*|.",
      "FLZ050-151-070515-",
      "/O.CAN.KTBW.HT.Y.0004.101206T2300Z-101207T0800Z/",
      "/O.CON.KTBW.EH.W.0001.101207T0800Z-101207T1700Z/",
      "/O.CON.KTBW.HT.Y.0003.101207T1700Z-101208T0900Z/",
      "Pinellas-Coastal Hillsborough-",
      "415 PM EST Mon Dec 6 2010",
      "...EXCESSIVE HEAT WARNING REMAINS IN EFFECT FROM 3 AM TO NOON EST TUESDAY...",
      "...HEAT ADVISORY REMAINS IN EFFECT FROM NOON TUESDAY TO 4 AM EST WEDNESDAY...",
      "...HEAT ADVISORY IS CANCELLED...",
#      "The National Weather Service in Tampa Bay Ruskin has cancelled the Heat Advisory. An Excessive Heat Warning remains in effect from 3 AM to noon EST Tuesday. A Heat Advisory remains in effect from noon Tuesday to 4 AM EST Wednesday. ",
#     "|*|*|* SEGMENT TEXT GOES HERE *|.*|*|",
      "An Excessive Heat Warning means that a prolonged period of dangerously hot temperatures will occur. The combination of hot temperatures and high humidity will combine to create a DANGEROUS SITUATION in which heat illnesses are likely. Drink plenty of fluids...stay in an air-conditioned room...stay out of the sun...and check up on relatives and neighbors.",
      "A Heat Advisory means that a period of hot temperatures is expected. The combination of hot temperatures and high humidity will combine to create a situation in which heat illnesses are possible. Drink plenty of fluids...stay in an air-conditioned room...stay out of the sun...and check up on relatives and neighbors.",
      "$$",
       ],
    },

    {
    "commentary": "Step7-put back in part of HT.Y in 1st slot",
    "name": "ETNReuse_2h",
    "drtTime": "20101206_2134",
    "gridsStartTime": "20101206_0000",
    "cmdLineVars": None,
    "comboFlag": 0,
    "combinations": None,
    "productType": "Hazard_NPW_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", 21, 24+1, "HT.Y", ["FLZ050","FLZ151"]),
       ("Fcst", "Hazards", "DISCRETE", 24+8, 24+17, "EH.W", ["FLZ050","FLZ151"]),
       ("Fcst", "Hazards", "DISCRETE", 24+17, 48+9, "HT.Y", ["FLZ050","FLZ151"]),
       ],
    "checkStrings": [
      "WWUS72 KTBW 062134",
      "NPWTBW",
      "URGENT - WEATHER MESSAGE",
      "National Weather Service Tampa Bay Ruskin FL",
      "434 PM EST Mon Dec 6 2010",
      "...|*Overview headline (must edit)*|...",
      ".|*Overview (must edit)*|.",
      "FLZ050-151-070545-",
      "/O.NEW.KTBW.HT.Y.0005.101206T2134Z-101207T0100Z/",
      "/O.CON.KTBW.EH.W.0001.101207T0800Z-101207T1700Z/",
      "/O.CON.KTBW.HT.Y.0003.101207T1700Z-101208T0900Z/",
      "Pinellas-Coastal Hillsborough-",
      "434 PM EST Mon Dec 6 2010",
      "...HEAT ADVISORY IN EFFECT UNTIL 8 PM EST THIS EVENING...",
      "...EXCESSIVE HEAT WARNING REMAINS IN EFFECT FROM 3 AM TO NOON EST TUESDAY...",
      "...HEAT ADVISORY REMAINS IN EFFECT FROM NOON TUESDAY TO 4 AM EST WEDNESDAY...",
#      "The National Weather Service in Tampa Bay Ruskin has issued a Heat Advisory...which is in effect until 8 PM EST this evening. An Excessive Heat Warning remains in effect from 3 AM to noon EST Tuesday. A Heat Advisory remains in effect from noon Tuesday to 4 AM EST Wednesday. ",
#      "|*|*|* SEGMENT TEXT GOES HERE *|.*|*|",
      "An Excessive Heat Warning means that a prolonged period of dangerously hot temperatures will occur. The combination of hot temperatures and high humidity will combine to create a DANGEROUS SITUATION in which heat illnesses are likely. Drink plenty of fluids...stay in an air-conditioned room...stay out of the sun...and check up on relatives and neighbors.",
      "A Heat Advisory means that a period of hot temperatures is expected. The combination of hot temperatures and high humidity will combine to create a situation in which heat illnesses are possible. Drink plenty of fluids...stay in an air-conditioned room...stay out of the sun...and check up on relatives and neighbors.",
      "$$",
       ],
    },

    {
    "commentary": "Deleting hazard grids.",
    "name": "ETNReuse_2i",
    "productType": None,
    "clearHazardsTable": 1,
    "checkStrings": [],
    },

    ]

       
import TestScript
def testScript(self, dataMgr):
    defaults = {
        "database": "<site>_GRID__Fcst_00000000_0000",
        "publishGrids": 0,
        "decodeVTEC": 1,
        "gridsStartTime": "20100101_0500",
        "orderStrings": 1,
        "vtecMode": "O",
        "comboFlag": 1,
        "combinations": [(["GMZ870"],"")],
        "cmdLineVars": "{('Product Issuance', 'productIssuance'): 'Morning', ('Issued By', 'issuedBy'): None}",
        "deleteGrids": [("Fcst", "Hazards", "SFC", "all", "all")],
        }
    return TestScript.generalTestScript(self, dataMgr, scripts, defaults)




