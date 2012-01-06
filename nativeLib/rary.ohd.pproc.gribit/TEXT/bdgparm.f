      block data bdgparm
c
      character*62 t128a(13),t128b(15),t128c(15),t128d(15),t128e(15),
     +             t128f(15),t128g(15),t128h(15),t128i(10)
      include 'grib_pm'      
C
C    ================================= RCS keyword statements ==========
      CHARACTER*68                RCSKW1,RCSKW2
      COMMON / RCSBDGPARM       / RCSKW1,RCSKW2
      DATA                        RCSKW1,RCSKW2 /                      '
     .$Source: /fs/hseb/ob82/ohd/pproc/src/gribit/RCS/bdgparm.f,v $
     . $',                                                             '
     .$Id: bdgparm.f,v 1.1 2006/10/19 16:06:04 dsa Exp $
     . $' /
C    ===================================================================
C
c
      equivalence (gtab128(128),t128a(1)),(gtab128(141),t128b(1)),
     +  (gtab128(156),t128c(1)),(gtab128(171),t128d(1)),
     +  (gtab128(186),t128e(1)),(gtab128(201),t128f(1)),
     +  (gtab128(216),t128g(1)),(gtab128(231),t128h(1)),
     +  (gtab128(246),t128i(1))
c      
c         gribparm file - 03/06/2001 
c        
c         modified by David T. Miller, RSIS, Sept 2007
c           added parameters 232-235 for EMPE


c-1:-1:-1:0      TABLE 0 - NATIONAL/INTERNATIONAL ORIGINATING CENTERS
      data num0 / 10 /
      data gtab0/ 
     +'07:US National Weather Service - NCEP (WMC)                   ',
     +'08:US National Weather Service - NWSTG (WMC)                  ',
     +'09:US National Weather Service - Other (Field Stations)       ',
     +'53:Canadian Meteorological Service - Montreal (RSMC)          ',
     +'55:San Francisco                                              ',
     +'57:US Air Force - Global Weather Center                       ',
     +'58:US Navy - Fleet Numerical Oceanography Center              ',
     +'59:NOAA Forecast Systems Lab, Boulder CO                      ',
     +'60:National Center for Atmospheric Research (NCAR), Boulder CO',
     +'64:Honolulu                                                   '/

c-1:9:-1:A      TABLE A - GENERATING PROCESS OR MODEL - ORIGINATING CENTER 9
      data numa / 20 /
      data gtaba/
     +'150:NWS River Forecast System (NWSRFS)                       ',
     +'151:NWS Flash Flood Guidance System (NWSFFGS)                ',
     +'152:Quantitative Precipitation Estimation (QPE) - 1 hr dur   ',
     +'154:Quantitative Precipitation Estimation (QPE) - 6 hr dur   ', 
     +'155:Quantitative Precipitation Estimation (QPE) - 24hr dur   ',
     +'156:Process 1 (P1) Precipitation Estimation - automatic      ',
     +'157:Process 1 (P1) Precipitation Estimation - manual         ',
     +'158:Process 2 (P2) Precipitation Estimation - automatic      ',
     +'159:Process 2 (P2) Precipitation Estimation - manual         ',
     +'160:Multisensor Precipitation Estimation (MPE) - automatic   ',
     +'161:Multisensor Precipitation Estimation (MPE) - manual      ',
     +'165:Enhanced MPE - automatic                                 ',
     +'166:Bias Enhanced MPE - automatic                            ',
     +'170:Post Analysis of Precipitation Estimation (aggregate)    ',
     +'171:XNAV Aggregate Precipitation Estimation                  ',
     +'172:Mountain Mapper Precipitation Estimation                 ',
     +'180:Quantitative Precipitation Forecast (QPF)                ',
     +'185:NOHRSC_OPPS                                              ',
     +'190:Satellite Autoestimator Precipitation                    ',
     +'191:Satellite Interactive Flash Flood Analyzer (IFFA)        '/

c-1:9:-1:C      TABLE C - SUB-CENTERS FOR CENTER 9  US NWS FIELD STATIONS
      data num9c / 137 /
ccc      data gtab9c/
      equivalence (gtab9c(1),misc)
      equivalence (gtab9c(17),wfos1)
      equivalence (gtab9c(115),wfos2)
      character*62 misc(16)/
     +'150:KTUA:Arkansas-Red River RFC Tulsa OK                      ',
     +'151:PACR:Alaska-Pacific RFC Anchorage AK                      ',
     +'152:KSTR:Colorado Basin RFC Salt Lake City UT                 ',
     +'153:KRSA:California-Nevada RFC Sacramento CA                  ',
     +'154:KORN:Lower Mississippi RFC Slidel LA                      ',
     +'155:KRHA:Middle Atlantic RFC State College PA                 ',
     +'156:KKRF:Missouri Basin RFC Pleasant Hill MO                  ',
     +'157:KMSR:North Central RFC  Chanhassen MN                     ',
     +'158:KTAR:Northeast RFC Taunton MA                             ',
     +'159:KPTR:Northwest RFC Portland OR                            ',
     +'160:KTIR:Ohio Basin RFC Wilmington OH                         ',
     +'161:KALR:Southeast RFC Peachtree GA                           ',
     +'162:KFWR:West Gulf RFC Fort Worth TX                          ',
     +'163:NOHR:Chanhassen MN                                        ',
     +'170:KNES:Satellite Analysis Branch                            ',
     +'200:KOHD:Office of Hydrologic Development                     '/
      character*62 wfos1(98)/
     +'001:KABR:Aberdeen SD WFO                                      ',
     +'002:KALY:Albany  NY WFO                                       ',
     +'003:KABQ:Albuquerque  NM WFO                                  ',
     +'004:KAMA:Amarillo  TX WFO                                     ',
     +'005:PAFC:Anchorage  AK WFO                                    ',
     +'006:KFFC:Atlanta  GA WFO                                      ',
     +'007:KEWX:Austin/San Antonio  TX WFO                           ', 
     +'008:KLWX:Baltimore  MD/Washington  DC WFO                     ',
     +'009:KBYZ:Billings  MT WFO                                     ',
     +'010:KBGM:Binghamton  NY WFO                                   ',
     +'011:KBMX:Birmingham  AL WFO                                   ',
     +'012:KBIS:Bismarck  ND WFO                                     ',
     +'013:KBOI:Boise  ID WFO                                        ',
     +'014:KBOX:Boston  MA WFO                                       ',
     +'015:KBRO:Brownsville  TX WFO                                  ',
     +'016:KBUF:Buffalo  NY WFO                                      ',
     +'017:KBTV:Burlington  VT WFO                                   ',
     +'018:KILX:Central Illinois WFO                                 ',
     +'019:KCTP:Central Pennsylvania WFO                             ',
     +'020:KCHS:Charleston  SC WFO                                   ',
     +'021:KRLX:Charleston  WV WFO                                   ',
     +'022:KCYS:Cheyenne  WY WFO                                     ',
     +'023:KLOT:Chicago  IL WFO                                      ',
     +'024:KILN:Cincinnati  OH  WFO                                  ',
     +'025:KCLE:Cleveland  OH WFO                                    ',
     +'026:KCAE:Columbia  SC WFO                                     ',
     +'027:KCRP:Corpus Christi  TX WFO                               ',
     +'028:KFWD:Dallas/Ft. Worth  TX WFO                             ',
     +'029:KBOU:Denver/Boulder  CO WFO                               ',
     +'030:KDMX:Des Moines  IA WFO                                   ',
     +'031:KDTX:Detroit  MI WFO                                      ',
     +'032:KDDC:Dodge City  KS WFO                                   ',
     +'033:KDLH:Duluth  MN WFO                                       ',
     +'034:KFGF:Eastern North Dakota WFO                             ',
     +'035:KEPZ:El Paso  TX WFO                                      ',
     +'036:KLKN:Elko  NV WFO                                         ',
     +'037:KEKA:Eureka  CA WFO                                       ',
     +'038:PAFG:Fairbanks  AK WFO                                    ',
     +'039:KFGZ:Flagstaff  AZ WFO                                    ',
     +'040:KGGW:Glasgow  MT WFO                                      ',
     +'041:KGLD:Goodland  KS WFO                                     ',
     +'042:KGJT:Grand Junction  CO WFO                               ',
     +'043:KGRR:Grand Rapids  MI WFO                                 ',
     +'044:KTFX:Great Falls  MT WFO                                  ',
     +'045:KGRB:Green Bay  WI WFO                                    ',
     +'046:KGSP:Greenville/Spartanburg  SC WFO                       ',
     +'047:PGUA:Guam WFO                                             ',
     +'048:KGID:Hastings  NE WFO                                     ',
     +'049:PHFO:Honolulu  HI WFO                                     ',
     +'050:KHGX:Huston/Galveston  TX WFO                             ',
     +'051:KIND:Indianapolis  IN WFO                                 ',
     +'052:KJKL:Jackson  KY WFO                                      ',
     +'053:KJAN:Jackson  MS WFO                                      ',
     +'054:KJAX:Jacksonville  FL WFO                                 ',
     +'055:PAJK:Juneau  AK WFO                                       ',
     +'056:KEAX:Kansas City  MO WFO                                  ',
     +'057:KMRX:Knoxville/TriCities  TN W                            ',
     +'058:KARX:LaCrosse  WI WFO                                     ',
     +'059:KLCH:Lake Charles  LA WFO                                 ',
     +'060:KVEF:Las Vegas  NV WFO                                    ',
     +'061:KLZK:Little Rock  AR WFO                                  ',
     +'062:KLOX:Los Angeles  CA WFO                                  ',
     +'063:KLMK:Louisville  KY WFO                                   ',
     +'064:KLUB:Lubbock  TX WFO                                      ',
     +'065:KMQT:Marquette  MI WFO                                    ',
     +'066:KMFR:Medford  OR WFO                                      ',
     +'067:KMLB:Melbourne  FL WFO                                    ',
     +'068:KMEG:Memphis  TN WFO                                      ',
     +'069:KMFL:Miami  FL WFO                                        ',
     +'070:KMAF:Midland/Odessa  TX WFO                               ',
     +'071:KMKX:Milwaukee  WI WFO                                    ',
     +'072:KMPX:Minneapolis/StPaul  MN                               ',
     +'073:KMSO:Missoula  MT WFO                                     ',
     +'074:KMOB:Mobile  AL WFO                                       ',
     +'075:KMHX:Morehead City  NC WFO                                ',
     +'076:KOHX:Nashville  TN WFO                                    ',
     +'077:KAPX:North Central Lower Michigan WFO                     ',
     +'078:KLIX:New Orleans  LA WFO                                  ',
     +'079:KOKX:New York City  NY WFO                                ',
     +'080:KIWX:Northern Indiana WFO                                 ',
     +'081:KLBF:North Platte  NE WFO                                 ',
     +'082:KOUN:Norman/Oklahoma City  OK WFO                         ',
     +'083:KOAX:Omaha  NB WFO                                        ',
     +'084:KPAH:Paducah  KY WFO                                      ',
     +'085:KPDT:Pendleton  OR WFO                                    ',
     +'086:KPHI:Philadelphia  PA WFO                                 ',
     +'087:KPSR:Phoenix  AZ WFO                                      ',
     +'088:KPBZ:Pittsburgh  PA WFO                                   ',
     +'089:KPIH:Pocatello/Idaho Falls  ID WFO                        ',
     +'090:KGYX:Portland  ME WFO                                     ',
     +'091:KPQR:Portland  OR WFO                                     ',
     +'092:KPUB:Pueblo  CO WFO                                       ',
     +'093:KDVN:Quad Cities  IA WFO                                  ',
     +'094:KRAH:Raleigh/Durham  NC WFO                               ',
     +'095:KUNR:Rapid City  IA WFO                                   ',
     +'096:KREV:Reno  NV WFO                                         ',
     +'097:KRIW:Riverton  WY WFO                                     ',
     +'098:KRNK:Roanoke  VA WFO                                      '/
      character*62 wfos2(23)/
     +'099:KSTO:Sacramento Valley  CA WFO                            ',
     +'100:KSLC:Salt Lake City  UT WFO                               ',
     +'101:KSJT:San Angelo  TX WFO                                   ',
     +'102:KSGX:San Diego  CA WFO                                    ',
     +'103:KMTR:San Francisco  CA WFO                                ',
     +'104 KHNX:San Joaquin  CA WFO                                  ',
     +'105:TSJU:San Juan PR WFO                                      ',
     +'106:KSEW:Seattle/Tacoma  WA WFO                               ',
     +'107:KSHV:Shreveport  LA WFO                                   ',
     +'108:KFSD:Sioux Falls  SD WFO                                  ',
     +'109:KOTX:Spokane  WA WFO                                      ',
     +'110:KSGF:Springfield  MO WFO                                  ',
     +'111:KLSX:St. Louis  MO WFO                                    ',
     +'112:KTAE:Tallahassee  FL WFO                                  ',
     +'113:KTBW:Tampa Bay  FL WFO                                    ',
     +'114:KTOP:Topeka  KS WFO                                       ',
     +'115:KTWC:Tucson  AZ WFO                                       ',
     +'116:KTSA:Tulsa  OK WFO                                        ',
     +'117:KAKQ:Wakefield  VA WFO                                    ',
     +'118:KICT:Wichita  KS WFO                                      ',
     +'119:KILM:Wilmington  DE WFO                                   ',
     +'120:KCAR:Caribou ME WFO                                       ',
     +'121:KEYW:Key West FL WFO                                      '/
c
c-1:7:-1:2       TABLE 2 - PARAMETERS & UNITS FOR CENTER 7   US NWS NCEP
      data num2 / 6 /
      data gtab2 /
     +'011:TMP:  Temperature [K]                                     ',
     +'015:TMAX: Maximum temperature [K]                             ',
     +'016:TMIN: Minimum temperature [K]                             ',
     +'061:APCP: Total precipitation [kg/m2]                         ',
     +'065:WEASD:Water equiv. of snow depth [kg/m2]                  ',
     +'066:SNOD: Snow depth [m]                                      '/

c
c-1:9:-1:128     TABLE 128 - PARAMETERS & UNITS FOR CENTER 9  
c                            US NWS FIELD STATIONS
      data num128 /128 /
      data t128a /
     +'128:NONE:undefined                                            ',
     +'129:NONE:undefined                                            ',
     +'130:NONE:undefined                                            ',

     +'131:PRES:Pressure [Pa]                                        ',
     +'132:NONE:undefined                                            ',
     +'133:NONE:undefined                                            ',
     +'134:NONE:undefined                                            ',
     +'135:NONE:undefined                                            ',
     +'136:NONE:undefined                                            ',
     +'137:NONE:undefined                                            ',
     +'138:NONE:undefined                                            ',
     +'139:NONE:undefined                                            ',
     +'140:NONE:undefined                                            '/
c
      data t128b /
     +'141:TMP:Temperature [K]                                       ',
     +'142:NONE:undefined                                            ',
     +'143:NONE:undefined                                            ',
     +'144:NONE:undefined                                            ',
     +'145:NONE:undefined                                            ',
     +'146:NONE:undefined                                            ',
     +'147:NONE:undefined                                            ',
     +'148:NONE:undefined                                            ',
     +'149:NONE:undefined                                            ',
     +'150:NONE:undefined                                            ',

     +'151:NONE:undefined                                            ',
     +'152:NONE:undefined                                            ',
     +'153:NONE:undefined                                            ',
     +'154:NONE:undefined                                            ',
     +'155:NONE:undefined                                            '/
c
      data t128c /
     +'156:NONE:undefined                                            ',
     +'157:NONE:undefined                                            ',
     +'158:NONE:undefined                                            ',
     +'159:NONE:undefined                                            ',
     +'160:NONE:undefined                                            ',
c
     +'161:WIND:Wind speed [m/s]                                     ',
     +'162:NONE:undefined                                            ',
     +'163:NONE:undefined                                            ',
     +'164:NONE:undefined                                            ',
     +'165:NONE:undefined                                            ',
     +'166:NONE:undefined                                            ',
     +'167:NONE:undefined                                            ',
     +'168:NONE:undefined                                            ',
     +'169:NONE:undefined                                            ',
     +'170:NONE:undefined                                            '/
c
      data t128d /
     +'171:NONE:undefined                                            ',
     +'172:NONE:undefined                                            ',
     +'173:NONE:undefined                                            ',
     +'174:NONE:undefined                                            ',
     +'175:NONE:undefined                                            ',
     +'176:NONE:undefined                                            ',
     +'177:NONE:undefined                                            ',
     +'178:NONE:undefined                                            ',
     +'179:NONE:undefined                                            ',
     +'180:NONE:undefined                                            ',

     +'181:RH:Relative humidity [%]                                  ',
     +'182:NONE:undefined                                            ',
     +'183:NONE:undefined                                            ',
     +'184:NONE:undefined                                            ',
     +'185:NONE:undefined                                            '/
c
      data t128e /
     +'186:NONE:undefined                                            ',
     +'187:NONE:undefined                                            ',
     +'188:NONE:undefined                                            ',
     +'189:NONE:undefined                                            ',
     +'190:NONE:undefined                                            ',
c
     +'191:APCP:Total precipitation [kg/m**2]                        ',
     +'192:EF25M:Cond 25% pcpn amt fractile past 24 hrs [kg/m**2]    ',
     +'193:EF50M:Cond 50% pcpn amt fractile past 24 hrs [kg/m**2]    ',
     +'194:PCTP1:Pct pcpn in 1st 6-h sub-period of 24 hr prd [%]     ',
     +'195:PCTP2:Pct pcpn in 2nd 6-h sub-period of 24 hr prd [%]     ',
     +'196:PCTP3:Pct pcpn in 3rd 6-h sub-period of 24 hr prd [%]     ',
     +'197:PCTP4:Pct pcpn in 4th 6-h sub-period of 24 hr prd [%]     ',
     +'198:POP:Prob of 0.01 in of precip [%]                         ',
     +'199:NONE:undefined                                            ',
     +'200:NONE:undefined                                            '/
c
      data t128f /
     +'201:NONE:undefined                                            ',
     +'202:NONE:undefined                                            ',
     +'203:NONE:undefined                                            ',
     +'204:NONE:undefined                                            ',
     +'205:ASD:Accumulated snow depth [m]                            ',
     +'206:NONE:undefined                                            ',
     +'207:SC:Snow cover [non-dim]                                   ',
     +'208:SCE:Snow cover by elevation [100 m]                       ',
     +'209:SWE:Snow water equivalent [2 cm]                          ',
     +'210:SWEPN:Snow water equivalent percent of normal [%]         ',
c
     +'211:NONE:undefined                                            ',
     +'212:NONE:undefined                                            ',
     +'213:NONE:undefined                                            ',
     +'214:NONE:undefined                                            ',
     +'215:NONE:undefined                                            '/
c
      data t128g /
     +'216:NONE:undefined                                            ',
     +'217:NONE:undefined                                            ',
     +'218:NONE:undefined                                            ',
     +'219:NONE:undefined                                            ',
     +'220:NONE:undefined                                            ',
c
     +'221:FFG01:1-hour flash flood guidance [kg/m2]                 ',
     +'222:FFG03:3-hour flash flood guidance [kg/m2]                 ',
     +'223:FFG06:6-hour flash flood guidance [kg/m2]                 ',
     +'224:FFG12:12-hour flash flood guidance [kg/m2]                ',
     +'225:FFG24:24-hour flash flood guidance [kg/m2]                ',
     +'226:FFR01:1-hour flash flood runoff value [kg/m2]             ',
     +'227:FFR03:3-hour flash flood runoff value [kg/m2]             ',
     +'228:FFR06:6-hour flash flood runoff value [kg/m2]             ',
     +'229:FFR12:12-hour flash flood runoff value [kg/m2]            ',
     +'230:FFR24:24-hour flash flood runoff value [kg/m2]            '/
c
      data t128h /
     +'231:NONE:undefined                                            ',
     +'232:PR:DHR Precip Rate [mm/hr]                                ',
     +'233:PR:Bias DHR Precip Rate [mm/hr]                           ',
     +'234:STPA:Digital Storm Total Precip [kg/m2]                   ',
     +'235:STPA:Digital Storm Total Precip, Bias [kg/m2]             ',
     +'236:NONE:undefined                                            ',
     +'237:QPE01:1-hour Quantitative Precip Estimate [kg/m2]         ',
     +'238:NONE:undefined                                            ',
     +'239:QPE06:6-hour Quantitative Precip Estimate [kg/m2]         ',
     +'240:NONE:undefined                                            ',
c
     +'241:NONE:undefined                                            ',
     +'242:QPE24:24-hour Quantitative Precip Estimate [kg/m2]        ',
     +'243:NONE:undefined                                            ',
     +'244:NONE:undefined                                            ',
     +'245:NONE:undefined                                            '/
c
      data t128i /
     +'246:NONE:undefined                                            ',
     +'247:QPF06:6-hour Quantitative Precipitation Forecast [kg/m2]  ',
     +'248:NONE:undefined                                            ',
     +'249:NONE:undefined                                            ',
     +'250:QPF24:24-hour Quantitative Precipitation Forecast [kg/m2] ',
c
     +'251:NONE:undefined                                            ',
     +'252:NONE:undefined                                            ',
     +'253:NONE:undefined                                            ',
     +'254:NONE:undefined                                            ',
     +'255:NONE:undefined                                            '/
c     
      end
