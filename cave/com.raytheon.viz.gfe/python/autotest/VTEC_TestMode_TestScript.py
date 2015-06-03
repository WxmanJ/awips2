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

def1 = """#Definition["state_IDs"] = ["ST"]"""
def2 = """Definition["state_IDs"] = ["FL"]"""

pfmDefaultEditAreas = """Definition["defaultEditAreas"] = [
  ('FLZ050','FLZ050\\nGFE TEST AREA 1\\n35.00N  90.00W\\n101'),
  ('FLZ151','FLZ151\\nGFE TEST AREA 2\\n35.00N  90.00W\\n102'),
  ('FLZ052','FLZ052\\nGFE TEST AREA 3\\n35.00N  90.00W\\n103'),
  ('FLZ053','FLZ053\\nGFE TEST AREA 4\\n35.00N  90.00W\\n104'),
  ]
"""



scripts = [
    {
    "commentary": "Clear out all Hazards Table and Grids.",
    "name": "TESTMODE_0",
    "productType": None,
    "clearHazardsTable": 1,
    "checkStrings": [],
    },

    {
    "name":"TESTMODE_AFD",
    "commentary": "AFD product in test mode.",
    "productType":"AFD",
    "cmdLineVars": "{('Issued By', 'issuedBy'): None, ('Issuance Type', 'issuanceType'): 'ROUTINE', ('IncludePrevious AFD?', 'includePreviousAFD'): 'NO', ('Long TermForecaster', 'longTermFcstrNumber'): '99', ('Product Issuance', 'productIssuance'): 'Morning', ('Short TermForecaster', 'shortTermFcstrNumber'): '99', ('IncludePrevious AFDIssue Time?', 'includePreviousAFD_issueTime'): 'NO', ('OptionalTopics', 'optionalTopics'): [], ('AviationForecaster', 'aviationFcstrNumber'): '99'}",
    "comboFlag": 0,
    "checkStrings": [
       "FXUS62 KTBW 010900",
       "AFDTBW",
       "TEST...Area Forecast Discussion...TEST",
       "National Weather Service Tampa Bay Ruskin FL",
       "400 AM EST Fri Jan 1 2010",
       ".SHORT TERM...",
       ".LONG TERM...",
       "&&",
       ".TBW Watches/Warnings/Advisories...",
       "Test Winter Storm Warning until midnight EST tonight for FLZ",
       "&&",
       "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
       "$$",
       "99/99",
       ],
    "createGrids": [("Fcst", "Hazards", "DISCRETE", 0, 24, "WS.W", "all")],
    "fileChanges": [("AFD_<site>_Definition", "TextUtility", "replace", (def1, def2), "undo")],
    },

    {
    "name":"TESTMODE_AFM",
    "productType":"AFM",
    "commentary": "AFM product in test mode.",
    "cmdLineVars": "{('Product Issuance', 'productIssuance'): 'Morning', ('Issued By', 'issuedBy'): None }",
    "comboFlag": 1,
    "combinations" : "ZONE",
    "checkStrings": [
      "TEST...Area Forecast Matrices...TEST",
      "National Weather Service Tampa Bay Ruskin FL",
      "400 AM EST Fri Jan 1 2010",
#      "FLZ042-012100-",
#      "Citrus-",
      "400 AM EST Fri Jan 1 2010",
      "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
      "$$",
       ],
    "createGrids": [("Fcst", "Hazards", "DISCRETE", 0, 24, "WS.W", "all")],
    },

    {
    "name":"TESTMODE_CCF",
    "productType":"CCF",
    "commentary": "CCF product in test mode.",
     "cmdLineVars": "{('Product Issuance', 'productIssuance'): 'Morning', ('Issued By', 'issuedBy'): None, ('Forecaster Number', 'forecasterNumber'): 99.0}",
    "comboFlag": 0,
    "checkStrings": [
       "FPUS42 KTBW 010900",
       "CCFTBW",
       "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
        ],
    },

    {
     "commentary": "Checking Test Mode for CivilEmerg",
     "name":"TESTMODE_CivilEmerg",
     "productType" : "CivilEmerg_CAE_Local",
     "cmdLineVars" :"{('Source', 'source'): 'Colorado Emergency Management Agency Denver Colorado', ('Issued By', 'issuedBy'): None, ('EAS Level', 'eas'): 'BULLETIN - EAS ACTIVATION REQUESTED'}",
     "checkStrings" : [
       "WOUS42 KTBW 010900",
       "CAETBW",
#        "FLZ042-011200-",
       "BULLETIN - EAS ACTIVATION REQUESTED",
       "TEST...Child Abduction Emergency...TEST",
       "Colorado Emergency Management Agency Denver Colorado",
       "Relayed by National Weather Service Tampa Bay Ruskin FL",
       "400 AM EST Fri Jan 1 2010",
       "The following message is transmitted at the request of the Colorado Emergency Management Agency Denver Colorado.",
       "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
       "$$",
        ],
     "comboFlag": 1,
     "combinations": "ZONE",
     },

    {
    "name": "TESTMODE_FFA",
    "commentary": "Checking Test Mode for FFA",
    "productType": "Hazard_FFA_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", 0, 6, "FF.A", ["FLZ149","FLZ050"]),
       ],
    "cmdLineVars" : "{('Issued By', 'issuedBy'): None, ('Flood Reason (HVTEC)', 'floodReason'): 'ER (Excessive Rainfall)'}",
    "checkStrings": [
      "WGUS62 KTBW 010900",
      "FFATBW",
      "URGENT - IMMEDIATE BROADCAST REQUESTED",
      "TEST...Flood Watch...TEST",
      "National Weather Service Tampa Bay Ruskin FL",
      "400 AM EST Fri Jan 1 2010",
      "...|*Overview headline (must edit)*|...",
      ".|*Overview (must edit)*|.",
      "FLZ050-149-011100-",
      "/T.NEW.KTBW.FF.A.0001.100101T0900Z-100101T1100Z/",
      "/00000.0.ER.000000T0000Z.000000T0000Z.000000T0000Z.OO/",
      "Pinellas-Coastal Pasco-",
      "400 AM EST Fri Jan 1 2010",
      "...THIS MESSAGE IS FOR TEST PURPOSES ONLY...",
      "...TEST FLASH FLOOD WATCH IN EFFECT UNTIL 6 AM EST EARLY THIS MORNING TEST...",
      "The National Weather Service in Tampa Bay Ruskin has issued a",
      "* Test Flash Flood Watch for a portion of west central Florida...including the following areas...Coastal Pasco and Pinellas.",
      "* until 6 AM EST early this morning",
      "* |* Basis for the watch *|",
      "* |* (optional) potential impacts of flooding *|",
      "Precautionary/preparedness actions...",
      "A Flash Flood Watch means that conditions may develop that lead to flash flooding. Flash flooding is a very dangerous situation.",
      "You should monitor later forecasts and be prepared to take action should Flash Flood Warnings be issued.",
      "&&", 
      "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
      "$$",
      ],
    },

    {
    "name": "TESTMODE_FWF",
    "productType": "FWF",
    "commentary": "Checking Test Mode for FWF",
    "createGrids": [("Fcst", "Hazards", "DISCRETE", 0, 24, "FW.W", "all")],
    "checkStrings": [
       "FNUS52 KTBW 010900",
       "FWFTBW",
       "TEST...Fire Weather Planning Forecast for Florida...TEST",
       "National Weather Service Tampa Bay Ruskin FL",
       "400 AM EST Fri Jan 1 2010",
       "...THIS MESSAGE IS FOR TEST PURPOSES ONLY...",
       "...TEST RED FLAG WARNING IN EFFECT UNTIL MIDNIGHT EST TONIGHT TEST...",
       ".DISCUSSION...",
#      "FLZ042-012100-",
       "/T.NEW.KTBW.FW.W.0001.100101T0900Z-100102T0500Z/",
#      "Citrus-",
       "400 AM EST Fri Jan 1 2010",
       "...THIS MESSAGE IS FOR TEST PURPOSES ONLY...",
       "...TEST RED FLAG WARNING IN EFFECT UNTIL MIDNIGHT EST TONIGHT TEST...",
       ".TODAY...",
       ".TONIGHT...",
       ".SATURDAY...",
       "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
       "$$",
       ".FORECAST DAYS 3 THROUGH 7...",
       ".SUNDAY...",
       ".MONDAY...",
       ".TUESDAY...",
       ".WEDNESDAY...",
       ".THURSDAY...",
       ".Outlook",
       "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
       "$$",
       ],
    "cmdLineVars": "{('Product Issuance', 'productIssuance'): 'Morning', ('Issued By', 'issuedBy'): None, ('Forecaster Number', 'forecasterNumber'): 99.0 }",
    "comboFlag": 1,
    "combinations": "ZONE",
    },

    {
    "name":"TESTMODE_FWS",
    "productType":"FWS",
    "commentary": "Checking Test Mode for FWS",
    "cmdLineVars": "{('Product Issuance:', 'productIssuance'): 'Morning', ('Issued By', 'issuedBy'): None, ('Agency:', 'requestingAgency'): 'Agency 1', ('Tomorrow Elements', 'tomorrowElements'): ['SKY/WEATHER', 'BEGIN/END OF PCPN', 'TEMPERATURE', 'HUMIDITY', 'DEWPOINT', '20 FOOT WINDS', 'EYE LEVEL WINDS', 'WIND SHIFT', 'RIDGE TOP WIND', 'SURROUNDING RIDGE', 'CWR', 'POP', 'LIGHTNING ACTIVITY LEVEL', 'SMOKE DISPERSION', 'MIXING HEIGHT', 'TRANSPORT WINDS', 'LDSI', 'LVORI', 'DISPERSION INDEX', 'CLEARING INDEX', 'STABILITY CLASS', 'MARINE LAYER', 'HAINES INDEX'], ('Fire Longitude (Deg)...................', 'fireLongitude'): 82.19, ('WebSiteTag:', 'webSiteTag'): '', ('Check Items to Include:', 'extendedQuestions'): [], ('Forecaster:', 'forecaster'): ['Forecaster C'], ('Type of Fire:', 'fireType'): 'Prescribed', ('Name of Agency Contact..........', 'agencyContact'): 'yyyy', ('Today Elements', 'todayElements'): ['SKY/WEATHER', 'BEGIN/END OF PCPN', 'TEMPERATURE', 'HUMIDITY', 'DEWPOINT', '20 FOOT WINDS', 'EYE LEVEL WINDS', 'WIND SHIFT', 'RIDGE TOP WIND', 'SURROUNDING RIDGE', 'CWR', 'POP', 'LIGHTNING ACTIVITY LEVEL', 'SMOKE DISPERSION', 'MIXING HEIGHT', 'TRANSPORT WINDS', 'LDSI', 'LVORI', 'DISPERSION INDEX', 'CLEARING INDEX', 'STABILITY CLASS', 'MARINE LAYER', 'HAINES INDEX'], ('Fire Latitude (Deg).......................', 'fireLatitude'): 28.27, ('WFOid:', 'wfoID'): '', ('Fire Size (Acres) .........................', 'fireSize'): .005, ('Name of Fire ...................................', 'fireName'): 'xxxx', ('Tonight Elements', 'tonightElements'): ['SKY/WEATHER', 'BEGIN/END OF PCPN', 'TEMPERATURE', 'HUMIDITY', 'DEWPOINT', '20 FOOT WINDS', 'EYE LEVEL WINDS', 'WIND SHIFT', 'RIDGE TOP WIND', 'SURROUNDING RIDGE', 'CWR', 'POP', 'LIGHTNING ACTIVITY LEVEL', 'SMOKE DISPERSION', 'MIXING HEIGHT', 'TRANSPORT WINDS', 'LDSI', 'LVORI', 'DISPERSION INDEX', 'CLEARING INDEX', 'STABILITY CLASS', 'MARINE LAYER', 'HAINES INDEX'], ('Creation Date', 'creationDate'): '', ('Creation Time', 'creationTime'): '', ('What Type of Forecast?', 'forecastType'): 'Narrative Only', ('Include Ignition Times?', 'withIgnitionTimes'): 'yes', ('Name of Agency if not listed....', 'otherAgencyName'): 'xxxx', ('Date of Fire .....................................', 'fireDate'): '1/1/10', ('Time of Fire .....................................', 'fireTime'): '1300', ('Tab Hrs', 'todayTableRes'): 1, ('Tab Hrs', 'tonightTableRes'): 2, ('Tab Hrs', 'tomorrowTableRes'): 3, ('TimeZone:', 'fireTZ'): 'EST7EDT'}",
    "comboFlag":  0,
    "createGrids": [("Fcst", "Hazards", "DISCRETE", 0, 24, "FW.W", "all")],
    "checkStrings": [
      "FNUS72 KTBW 010900",
      "FWSTBW",
      "TEST...Spot Forecast for xxxx...Agency 1...TEST",
      "National Weather Service Tampa Bay Ruskin FL",
      "400 AM EST Fri Jan 1 2010",
      "If conditions become unrepresentative...contact the National Weather Service.",
      "...THIS MESSAGE IS FOR TEST PURPOSES ONLY...",
      "...TEST RED FLAG WARNING IN EFFECT UNTIL MIDNIGHT EST TONIGHT TEST...",
      ".DISCUSSION...",
      ".TODAY...",
      "TEMPERATURE.....",
      "RH........",
      "MIXING HEIGHT.......",
      "TRANSPORT WINDS.....",
      ".TONIGHT...",
      "TEMPERATURE.....",
      "RH........",
      "MIXING HEIGHT.......",
      "TRANSPORT WINDS.....",
      ".SATURDAY...",
      "TEMPERATURE.....",
      "RH........",
      "MIXING HEIGHT.......",
      "TRANSPORT WINDS.....",
      "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
      "$$",
      "Forecaster...Forecaster C",
      "Requested by...yyyy",
      "Type of request...Prescribed",
      ],
    },  

    {
    "name": "TESTMODE_FWFTable",
    "productType": "FWFTable",
    "commentary": "Checking Test Mode for FWFTable",
    "createGrids": [("Fcst", "Hazards", "DISCRETE", 0, 24, "FW.W", "all")],
    "checkStrings": [
      "FNUS52 KTBW 010900",
      "FWFTBW",
      "TEST...Fire Weather Planning Forecast for Florida...TEST",
      "National Weather Service Tampa Bay Ruskin FL",
      "400 AM EST Fri Jan 1 2010",
      "...THIS MESSAGE IS FOR TEST PURPOSES ONLY...",
      "...TEST RED FLAG WARNING IN EFFECT UNTIL MIDNIGHT EST TONIGHT TEST...",
      ".DISCUSSION...",
#     "FLZ042-012100-",
      "/T.NEW.KTBW.FW.W.0001.100101T0900Z-100102T0500Z/",
#     "Citrus-",
      "400 AM EST Fri Jan 1 2010",
      "...THIS MESSAGE IS FOR TEST PURPOSES ONLY...",
      "...TEST RED FLAG WARNING IN EFFECT UNTIL MIDNIGHT EST TONIGHT TEST...",
      "Remarks...None.",
      "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
      "$$",
      ".FORECAST FOR DAYS 3 THROUGH 7...",
      ".SUNDAY...",
      ".MONDAY...",
      ".TUESDAY...",
      ".WEDNESDAY...",
      ".THURSDAY...",
       ],
    "cmdLineVars": "{('Product Issuance', 'productIssuance'): 'Morning', ('Issued By', 'issuedBy'): None, ('Forecaster Number', 'forecasterNumber'): 99.0 }",
    "comboFlag": 1,
    "combinations" : "ZONE",
    },

    {
    "name":"TESTMODE_FWM",
    "productType":"FWM",
    "commentary": "Checking Test Mode for FWM",
    "cmdLineVars":  "{('Issued By', 'issuedBy'): None}",
    "comboFlag":  0,
    "checkStrings": [
       "FNUS82 KTBW 010900",
       "FWMTBW",
       "FCST,086401",
       "FCST,086402",
       "ZONE,668",
       "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
       ],
    },

    {
    "name":"TESTMODE_GLF",
    "productType":"GLF",
    "commentary": "Checking Test Mode for GLF",
    "cmdLineVars": "{('Product Issuance', 'productIssuance'): '400 AM', ('Issued By', 'issuedBy'): None}",
    "createGrids": [("Fcst", "Hazards", "DISCRETE", 0, 24, "HF.W", "all")],
    "comboFlag":  0,
    "checkStrings": [
      "UFUS42 KTBW 010900",
      "GLFABC",
      "LSZ260-012100-",
      "TEST...Open Lakes Forecast for Statename...TEST",
      "National Weather Service Tampa Bay Ruskin FL",
      "400 AM EST Fri Jan 1 2010",
      "Lake Superior forecast beyond five nautical miles from shore",
      ".SYNOPSIS...",
      "...THIS MESSAGE IS FOR TEST PURPOSES ONLY...",
      "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
      "$$",
      "LSZ261-012100-",
      "MAFOR 0110/",
      "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
      "$$",
      ],
    },

    {
    "name":"TESTMODE_MVF",
    "productType":"MVF",
    "commentary": "Checking Test Mode for MVF",
    "cmdLineVars": "{('Issued By', 'issuedBy'): None, ('Forecaster Number', 'forecasterNumber'): 99.0}",
    "comboFlag":  0,
    "checkStrings": [
       "FXUS52 KTBW 010900",
       "MVF003",
       "%%F99 Area 1 ",
       "%%F99 Area 2 ",
       "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
       "$$",
       ],
    }, 

    {
    "commentary": "TESTMODE_MWS",
    "name": "TESTMODE_MWS",
    "commentary": "Checking Test Mode for MWS",
    "productType": "Hazard_MWS_Local",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", 0, 24, "MA.S", ["GMZ870"]),
       ],
    "checkStrings": [
       "FZUS72 KTBW 010900",
       "MWSTBW",
       "TEST...Marine Weather Statement...TEST",
       "National Weather Service Tampa Bay Ruskin FL",
       "400 AM EST Fri Jan 1 2010",
       "GMZ870-011700-",
       "/T.NEW.KTBW.MA.S.0001.100101T0900Z-100102T0500Z/",
       "Waters from Tarpon Springs to Suwannee River FL out 20 to 60 NM-",
       "400 AM EST Fri Jan 1 2010",
       "...THIS MESSAGE IS FOR TEST PURPOSES ONLY...",
       "|* Statement text goes here *|.",
       "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
       "$$",
       ],
    },

    {
    "name":"TESTMODE_MWW",
    "productType":"Hazard_MWW_Local",
    "commentary": "Checking Test Mode for MWW",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", 0, 24, "GL.W", ["GMZ870"]),
       ("Fcst", "Hazards", "DISCRETE", 0, 24, "HF.W", ["GMZ850"]),
       ],
    "checkStrings": [
      "WHUS72 KTBW 010900",
      "MWWTBW",
      "TEST...URGENT - MARINE WEATHER MESSAGE...TEST",
      "National Weather Service Tampa Bay Ruskin FL",
      "400 AM EST Fri Jan 1 2010",
      "...|*Overview headline (must edit)*|...",
      ".|*Overview (must edit)*|.",
      "GMZ870-011700-",
      "/T.NEW.KTBW.GL.W.0001.100101T0900Z-100102T0500Z/",
      "Waters from Tarpon Springs to Suwannee River FL out 20 to 60 NM-",
      "400 AM EST Fri Jan 1 2010",
      "...THIS MESSAGE IS FOR TEST PURPOSES ONLY...",
      "...TEST GALE WARNING IN EFFECT UNTIL MIDNIGHT EST TONIGHT TEST...",
      "The National Weather Service in Tampa Bay Ruskin has issued a Test Gale Warning...which is in effect until midnight EST tonight.",
#       "|* SEGMENT TEXT GOES HERE *|.",
      "A Gale Warning means winds of 34 to 47 knots are imminent or occuring. Operating a vessel in gale conditions requires experience and properly equipped vessels. It is highly recommended that mariners without the proper experience seek safe harbor prior to the onset of gale conditions.",
      "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
      "$$",
      "GMZ850-011700-",
      "/T.NEW.KTBW.HF.W.0001.100101T0900Z-100102T0500Z/",
      "Coastal waters from Tarpon Springs to Suwannee River FL out 20 NM-",
      "400 AM EST Fri Jan 1 2010",
      "...THIS MESSAGE IS FOR TEST PURPOSES ONLY...",
      "...TEST HURRICANE FORCE WIND WARNING IN EFFECT UNTIL MIDNIGHT EST TONIGHT TEST...",
      "The National Weather Service in Tampa Bay Ruskin has issued a Test Hurricane Force Wind Warning...which is in effect until midnight EST tonight.",
#       "|* SEGMENT TEXT GOES HERE *|.",
      "Precautionary/preparedness actions...",
      "A Hurricane Force Wind Warning means winds of 64 knots or greater are imminent or occuring. All vessels should remain in port...or take shelter as soon as possible...until winds and waves subside.",
      "&&",
      "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
      "$$",
        ],
    },

    {
    "name":"TESTMODE_NOW",
    "productType":"NOW_Local",
    "commentary": "Checking Test Mode for NOW",
    "cmdLineVars": "{('Issued By', 'issuedBy'): None}",
    "comboFlag":  1,
    "combinations": "ZONE",
    "checkStrings": [
       "FPUS72 KTBW 010900",
       "NOWTBW",
       "TEST...Short Term Forecast...TEST",
       "National Weather Service Tampa Bay Ruskin FL",
       "400 AM EST Fri Jan 1 2010",
#        "FLZ042-011100-",
#        "Citrus-",
       "400 AM EST Fri Jan 1 2010",
       ".NOW...",
       "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
       "$$",
       ],
    },

    {
    "name":"TESTMODE_NSH",
    "productType":"NSH",
    "commentary": "Checking Test Mode for NSH",
    "cmdLineVars": "{('Product Issuance', 'productIssuance'): '430 AM', ('Issued By', 'issuedBy'): None}",
    "createGrids": [("Fcst", "Hazards", "DISCRETE", 0, 24, "HF.W", "all")],
    "comboFlag":  1,
    "combinations": "ZONE",
    "checkStrings": [
      "UFUS42 KTBW 010900",
      "NSHABC",
      "TEST...Nearshore Marine Forecast for Florida...TEST",
      "National Weather Service Tampa Bay Ruskin FL",
      "400 AM EST Fri Jan 1 2010",
      "For waters within five nautical miles of shore on Lake (name)",
#       "FLZ042-011600-",
      "/T.NEW.KTBW.HF.W.0001.100101T0900Z-100102T0500Z/",
#       "Citrus-",
      "400 AM EST Fri Jan 1 2010",
      "...THIS MESSAGE IS FOR TEST PURPOSES ONLY...",
      "...TEST HURRICANE FORCE WIND WARNING IN EFFECT THROUGH THIS EVENING TEST...",
      "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
      "$$",
       ],
    },


    {
    "name": "TESTMODE_NPW",
    "productType": "Hazard_NPW_Local",
    "commentary": "Checking Test Mode for NPW",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", 0, 24, "DU.Y", ["FLZ149","FLZ050"]),
       ],
    "checkStrings": [
      "WWUS72 KTBW 010900",
      "NPWTBW",
      "TEST...URGENT - WEATHER MESSAGE...TEST",
      "National Weather Service Tampa Bay Ruskin FL",
      "400 AM EST Fri Jan 1 2010",
      "...|*Overview headline (must edit)*|...",
      ".|*Overview (must edit)*|.",
      "FLZ050-149-011700-",
      "/T.NEW.KTBW.DU.Y.0001.100101T0900Z-100102T0500Z/",
      "Pinellas-Coastal Pasco-",
      "400 AM EST Fri Jan 1 2010",
      "...THIS MESSAGE IS FOR TEST PURPOSES ONLY...",
      "...TEST BLOWING DUST ADVISORY IN EFFECT UNTIL MIDNIGHT EST TONIGHT TEST...",
      "The National Weather Service in Tampa Bay Ruskin has issued a Test Blowing Dust Advisory...which is in effect until midnight EST tonight.",
#       "|* SEGMENT TEXT GOES HERE *|.",
      "Precautionary/preparedness actions...",
      "A Blowing Dust Advisory means that blowing dust will restrict visibilities. Travelers are urged to use caution.",
      "&&",
      "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
      "$$",

       ],
    },

    {
    "name":"TESTMODE_PFM",
    "productType":"PFM",
    "commentary": "Checking Test Mode for PFM",
    "cmdLineVars": "{('Product Issuance', 'productIssuance'): 'Morning', ('Issued By', 'issuedBy'): None}",
    "comboFlag":  0,
    "fileChanges": [
       ("PFM_<site>_Definition", "TextUtility", "add", pfmDefaultEditAreas, "undo"),
       ],
    "checkStrings": [
      "FOUS52 KTBW 010900",
      "PFMTBW",
      "TEST...Point Forecast Matrices...TEST",
      "National Weather Service Tampa Bay Ruskin FL",
      "400 AM EST Fri Jan 1 2010",
      "FLZ050-012100-",
      "GFE TEST AREA 1",
      "35.00N 90.00W ELEV. 101 FT",
      "400 AM EST Fri Jan 1 2010",
      "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
      "$$",
      "FLZ151-012100-",
      "GFE TEST AREA 2",
      "35.00N 90.00W ELEV. 102 FT",
      "400 AM EST Fri Jan 1 2010",
      "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
      "$$",
      "FLZ052-012100-",
      "GFE TEST AREA 3",
      "35.00N 90.00W ELEV. 103 FT",
      "400 AM EST Fri Jan 1 2010",
      "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
      "$$",
      "FLZ053-012100-",
      "GFE TEST AREA 4",
      "35.00N 90.00W ELEV. 104 FT",
      "400 AM EST Fri Jan 1 2010",
      "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
      "$$",
       ],
    },

    {
    "name":"TESTMODE_OFF",
    "productType":"OFF",
    "commentary": "Checking Test Mode for OFF",
    "cmdLineVars": "{('Product Issuance', 'productIssuance'): '400 AM', ('Issued By', 'issuedBy'): None}",
    "createGrids": [("Fcst", "Hazards", "DISCRETE", 0, 24, "HU.W", "all")],
    "comboFlag":  1,
    "combinations": "ZONE",
    "checkStrings": [
       "UFUS42 KTBW 010900",
       "OFFABC",
       "Offshore Forecast for Florida",
       "National Weather Service Tampa Bay Ruskin FL",
       "400 AM EST Fri Jan 1 2010",
       "400 AM EST Fri Jan 1 2010",
       ".SYNOPSIS...",
       "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
       "$$",
#        "FLZ042-012100-",
       "/T.NEW.KTBW.HU.W.0001.100101T0900Z-000000T0000Z/",
#        "Citrus-",
       "400 AM EST Fri Jan 1 2010",
       "...THIS MESSAGE IS FOR TEST PURPOSES ONLY...",
       "...TEST HURRICANE WARNING IN EFFECT TEST...",
       "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
       "$$",
       ],
    },

    {
    "name": "TESTMODE_RFW",
    "productType": "Hazard_RFW_Local",
    "commentary": "Checking Test Mode for RFW",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", 0, 24, "FW.W", ["FLZ149","FLZ050"]),
       ],
    "cmdLineVars" : "{('Select RFW Type', 'rfwType'): [], ('Source for Headline and \\nAffected Area Bullet', 'elevationSource'): 'Grids'}",
    "checkStrings": [
      "WWUS82 KTBW 010900",
      "RFWTBW",
      "TEST...URGENT - FIRE WEATHER MESSAGE...TEST",
      "National Weather Service Tampa Bay Ruskin FL",
      "400 AM EST Fri Jan 1 2010",
      "...|*Overview headline (must edit)*|...",
      ".|*Overview (must edit)*|.",
      "FLZ050-149-011700-",
      "/T.NEW.KTBW.FW.W.0001.100101T0900Z-100102T0500Z/",
      "Pinellas-Coastal Pasco-",
      "400 AM EST Fri Jan 1 2010",
      "...THIS MESSAGE IS FOR TEST PURPOSES ONLY...",
      "...TEST RED FLAG WARNING IN EFFECT UNTIL MIDNIGHT EST TONIGHT FOR |* EVENT TYPE *| FOR FIRE WEATHER ZONES 050 AND 149 TEST...",
      "The National Weather Service in Tampa Bay Ruskin has issued a Test Red Flag Warning...which is in effect until midnight EST tonight.",
#       "|* SEGMENT TEXT GOES HERE *|.",
      "A Red Flag Warning means that critical fire weather conditions are either occurring now....or will shortly.",
      "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
      "$$",

       ],
    },

    {
    "name":"TESTMODE_SAF", 
    "productType":"SAF",
    "commentary": "Checking Test Mode for SAF",
    "cmdLineVars": "{('Product Issuance', 'productIssuance'): 'Morning', ('Issued By', 'issuedBy'): None, ('Number of Periods', 'numPeriods'): 'All'}",
    "createGrids": [("Fcst", "Hazards", "DISCRETE", 0, 24, "WS.W", "all")],
    "comboFlag":  0, 
    "checkStrings": [
       "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE."
      ],
    },

    {
    "name":"TESTMODE_SFT",
    "productType":"SFT",
    "commentary": "Checking Test Mode for SFT",
    "comboFlag": 0,
    "checkStrings": [
      "FPUS62 KTBW 010900",
      "SFTTBW",
      "TEST...Tabular State Forecast for Florida...TEST",
      "National Weather Service Tampa Bay Ruskin FL",
      "400 AM EST Fri Jan 1 2010",
      "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
      "$$",
      ],
    },
    
    {
    "name":"TESTMODE_SRF",
    "productType":"SRF",
    "commentary": "Checking Test Mode for SRF",
    "comboFlag": 0,
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", 0, 24, "SU.W", ["FLC017"]),
       ],
    "checkStrings": [
       "UFUS42 KTBW 010900",
       "SRFABC",
       "TEST...Surfzone Forecast for Florida...TEST",
       "National Weather Service Tampa Bay Ruskin FL",
       "600 AM EST Fri Jan 1 2010",
       ".SYNOPSIS...",
       "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
       "$$",
       "FLZ142-012100-",
       "/T.NEW.KTBW.SU.W.0001.100101T0900Z-100102T0500Z/",
       "Citrus-",
       "400 AM EST Fri Jan 1 2010",
       "...THIS MESSAGE IS FOR TEST PURPOSES ONLY...",
       "...TEST HIGH SURF WARNING IN EFFECT UNTIL MIDNIGHT EST TONIGHT TEST...",
       "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
       ],
    },

    {
    "name":"TESTMODE_SPS", 
    "productType":"SPS_Local",
    "commentary": "Checking Test Mode for SPS",
    "cmdLineVars": "{('Issued By', 'issuedBy'): None}",
    "comboFlag":  1, 
    "combinations": "ZONE",
    "checkStrings": [
       "WWUS82 KTBW 010900",
       "SPSTBW",
       "TEST...Special Weather Statement...TEST",
       "National Weather Service Tampa Bay Ruskin FL",
       "400 AM EST Fri Jan 1 2010",
#        "FLZ042-011100-",
#        "Citrus-",
       "400 AM EST Fri Jan 1 2010",
#        "|* STATEMENT TEXT *|",
       "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
       "$$",
      ],
    },
    {
    "name": "TESTMODE_WCN",
    "productType": "Hazard_WCN_Local",
    "commentary": "Checking Test Mode for WCN",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", 0, 24, "TO.A:46", ["FLC017"]),
       ],
    "checkStrings": [
       "WWUS62 KTBW 010900",
       "WCNTBW",
       "TEST...Watch County Notification for watch 46...TEST",
       "National Weather Service Tampa Bay Ruskin FL",
       "400 AM EST Fri Jan 1 2010",
       "FLC017-020000-",
       "/T.NEW.KTBW.TO.A.0046.100101T0900Z-100102T0500Z/",
       "The National Weather Service has issued Test Tornado Watch 46 in effect until midnight EST tonight for the following areas",
       "In Florida this watch includes 1 county",
       "In west central Florida",
       "Citrus",
       "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
       "$$",
       ],
    },

    {
    "name": "TESTMODE_WSW",
    "productType": "Hazard_WSW_Local",
    "commentary": "Checking Test Mode for WSW",
    "createGrids": [
       ("Fcst", "Hazards", "DISCRETE", -100, 100, "<None>", "all"),
       ("Fcst", "Hazards", "DISCRETE", 0, 24, "WS.W", ["FLZ149","FLZ050"]),
       ],
    "checkStrings": [
      "WWUS42 KTBW 010900",
      "WSWTBW",
      "TEST...URGENT - WINTER WEATHER MESSAGE...TEST",
      "National Weather Service Tampa Bay Ruskin FL",
      "400 AM EST Fri Jan 1 2010",
      "...|*Overview headline (must edit)*|...",
      ".|*Overview (must edit)*|.",
      "FLZ050-149-011700-",
      "/T.NEW.KTBW.WS.W.0001.100101T0900Z-100102T0500Z/",
      "Pinellas-Coastal Pasco-",
      "400 AM EST Fri Jan 1 2010",
      "...THIS MESSAGE IS FOR TEST PURPOSES ONLY...",
      "...TEST WINTER STORM WARNING IN EFFECT UNTIL MIDNIGHT EST TONIGHT TEST...",
      "The National Weather Service in Tampa Bay Ruskin has issued a Test Winter Storm Warning...which is in effect until midnight EST tonight.",
#       "|* SEGMENT TEXT GOES HERE *|.",
      "Precautionary/preparedness actions...",
      "A Winter Storm Warning means significant amounts of snow...sleet...and ice are expected or occurring. Strong winds are also possible. This will make travel very hazardous or impossible.",
      "&&",
      "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
      "$$",
       ],
    },

    {
    "name":"TESTMODE_ZFP", 
    "productType":"ZFP", 
    "commentary": "Checking Test Mode for ZFP",
    "createGrids": [("Fcst", "Hazards", "DISCRETE", 0, 24, "WS.W", "all")],
    "comboFlag": 1, 
    "combinations": "ZONE",
    "checkStrings": [
       "FPUS52 KTBW 010900",
       "ZFPTBW",
       "TEST...Zone Forecast Product for Florida...TEST",
       "National Weather Service Tampa Bay Ruskin FL",
       "400 AM EST Fri Jan 1 2010",
#        "FLZ042-012100-",
       "/T.NEW.KTBW.WS.W.0001.100101T0900Z-100102T0500Z/",
#        "Citrus-",
       "400 AM EST Fri Jan 1 2010",
       "...THIS MESSAGE IS FOR TEST PURPOSES ONLY...",
       "...TEST WINTER STORM WARNING IN EFFECT UNTIL MIDNIGHT EST TONIGHT TEST...",
       ".TODAY...",
       ".TONIGHT...",
       ".SATURDAY...",
       ".SATURDAY NIGHT...",
       ".SUNDAY...",
       ".SUNDAY NIGHT...",
       ".MONDAY...",
       ".MONDAY NIGHT...",
       ".TUESDAY...",
       ".TUESDAY NIGHT...",
       ".WEDNESDAY...",
       ".WEDNESDAY NIGHT...",
       ".THURSDAY...",
       "THIS IS A TEST MESSAGE. DO NOT TAKE ACTION BASED ON THIS TEST MESSAGE.",
       "$$",
       ],
    },   

    {
    "commentary": "Deleting hazard grids.",
    "name": "TESTMODE_Cleanup",
    "productType": None,
    "checkStrings": [],
    "clearHazardsTable": 1,
    },
    ]

       
import TestScript
def testScript(self, dataMgr):
    gridsStartTime = self.getAbsFromLocal(2010, 1, 1, 0, 0)
    drtTime = self.getAbsFromLocal(2010, 1, 1, 4, 0)
    defaults = {
        "gridsStartTime": gridsStartTime,
        "drtTime": drtTime,
        "database": "<site>_GRID__Fcst_00000000_0000",
        "deleteGrids": [("Fcst", "Hazards", "SFC", "all", "all")],
        "publishGrids": 0,
        "decodeVTEC": 0,
        "orderStrings": 1,
        "vtecMode": "T",
        }
    return TestScript.generalTestScript(self, dataMgr, scripts, defaults)




