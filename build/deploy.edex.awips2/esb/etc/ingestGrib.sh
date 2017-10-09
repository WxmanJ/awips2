#!/bin/bash
# See definitions in /awips2/edex/conf/resources/com.raytheon.edex.plugin.grib.properties

let "MAX_MEM = GRIB_DECODE_THREADS * 576"
let "GRIB_MAX_GRID_POINTS = GRIB_DECODE_THREADS * 25000000"
let "METADATA_POOL_MAX = GRIB_DECODE_THREADS * 4"

export INIT_MEM=128 # in Meg
export MAX_MEM
export GRIB_DECODE_THREADS
export GRIB_MAX_GRID_POINTS
export GRID_PERSIST_THREADS
export GRID_POSTPROCESS_THREADS=1
export GRID_MAX_PERSIST_MEMORY_IN_MB
export METADATA_POOL_MAX
export EDEX_DEBUG_PORT=5007
export EDEX_JMX_PORT=1618
export MGMT_PORT=9603
