#!/bin/csh
#
# A script wrapper that is meant to get data for a single radar product
# from the A-II database.  The result is output to stdout as ASCII.
# The first line has the dimensions of the data, the volume scan time,
# 'radial' or 'raster', elevation number, a product description, the tilt
# angle or layer, and the VCP.  The second line contains the level labels,
# and the third line has a partial list of the product dependent parameters.
# If the product is radial, the fourth line contains a list of the
# radial angles.  Otherwise each line after that has data for one
# radial/row, as a sequence of space delimited plain decimal integers.
#
# The product description includes the mnemonic, the resolution, and
# the bit depth.  If the azimuthal resolution is finer than 1 degree,
# the product description will additionally include a note of that.
# The product dependent parameters as defined in an 88D RPG product
# are 28 signed two byte integers.  The list here includes those
# with indices from 17 to 26 (C indexing).
#
# Note that for now, this is only designed to return data for image
# products.
#
# Usage:
#
#  a2gtrad.csh {p} {x} radar msgcode {elev} date time {slop}
#
#    p - A literal p. (optional)
#    x - A literal x. (optional) Expanded set of header information.
#    radar - four character radar id
#    msgcode - RPG message code
#    elev - tilt angle/layer value. defaults to 0.
#    date - yyyy-mm-dd
#    time - hh:mm
#    slop - seconds of slop either side, defaults to 60
#
#   The tilt angles specified are primary tilt angles for a range of tilts. 
#
#  The literal p option means preserve the final version of the python
#  submitted to the UEngine instead of cleaning it up.  The path to the
#  finalized python is /tmp/a2gtradNNNNN.py where NNNNN is a unix process id.
# 
#
set rmpy = yes
set fff = ""
if ( "$1" == "p" ) then
    set rmpy = no
    shift
endif
if ( "$1" == "x" ) then
     set fff = "x"
    shift
endif
if ( "$1" == "p" ) then
    set rmpy = no
    shift
endif
#
# Identify directory this script is in, will be one of the directories we
# search for other files in.
#
set mydir = `dirname $0`
set d1 = `echo $mydir | cut -c1`
if ( "$mydir" == '.' ) then
     set mydir = $PWD
else if ( "$d1" != "/" ) then
     set mydir = $PWD/$mydir
endif
set mydir = `(cd $mydir ; pwd)`
if ( ! $?FXA_HOME ) set FXA_HOME = xxxx
set fxa_home = $FXA_HOME
if ( $?STAGING ) then
    set fxa_home = $STAGING/D-2D
endif
#
# Set up the environment we need to run the UEngine.
#
if ( -e ./UEngine.cshsrc ) then
    set ueenv = ./UEngine.cshsrc
else if ( -e $mydir/UEngine.cshsrc ) then
    set ueenv = $mydir/UEngine.cshsrc
else if ( -e $fxa_home/src/dm/point/UEngine.cshsrc ) then
    set ueenv = $fxa_home/src/dm/point/UEngine.cshsrc
else if ( -e $FXA_HOME/bin/UEngine.cshsrc ) then
    set ueenv = $FXA_HOME/bin/UEngine.cshsrc
else
    bash -c "echo could not find UEngine.cshsrc 1>&2"
    exit
endif
source $ueenv
#
# Locate python stub that we will modify to create the final UEngine script
#
if ( -e ./a2gtradStub.py ) then
    set stubpy = ./a2gtradStub.py
else if ( -e $mydir/a2gtradStub.py ) then
    set stubpy = $mydir/a2gtradStub.py
else if ( -e $fxa_home/src/dm/radar/a2gtradStub.py ) then
    set stubpy = $fxa_home/src/dm/radar/a2gtradStub.py
else if ( -e $FXA_HOME/bin/a2gtradStub.py ) then
    set stubpy = $FXA_HOME/bin/a2gtradStub.py
else
    bash -c "echo could not find a2gtradStub.py 1>&2"
    exit
endif
#
# Locate file that lets us provide a description of the data set.
#
if ( -e ./msgCodeSeds.txt ) then
    set mctrans = $PWD/msgCodeSeds.txt
else if ( -e $mydir/msgCodeSeds.txt ) then
    set mctrans = $mydir/msgCodeSeds.txt
else if ( -e $fxa_home/src/dm/radar/msgCodeSeds.txt ) then
    set mctrans = $fxa_home/src/dm/radar/msgCodeSeds.txt
else if ( -e $FXA_HOME/data/msgCodeSeds.txt ) then
    set mctrans = $FXA_HOME/data/msgCodeSeds.txt
else
    bash -c "echo could not find msgCodeSeds.txt 1>&2"
    exit
endif
#
#
set rrr = $1
set mmm = $2
shift
shift
set ddd = `echo $mmm | sed -f $mctrans | cut '-d|' -f2 `
#
#  Get program that can do math with ascii time string, then use this to
#  properly encode range of times for which we look for data.
#
if ( -x ./gtasUtil ) then
    set gtasUtil = ./gtasUtil
else if ( -x $mydir/gtasUtil ) then
    set gtasUtil = $mydir/gtasUtil
else if ( -x $fxa_home/src/dm/point/gtasUtil ) then
    set gtasUtil = $fxa_home/src/dm/point/gtasUtil
else if ( -x $FXA_HOME/bin/gtasUtil ) then
    set gtasUtil = $FXA_HOME/bin/gtasUtil
else
    bash -c "echo could not find gtasUtil executable 1>&2"
    exit
endif
set eee = `echo $1 | grep -v '.*-'`
if ( "$eee" != "" ) shift
set slop = `echo $3 | grep '[0-9]'`
if ( "$slop" == "" ) set slop = 60
set aaa = `$gtasUtil = $1 $2 -$slop`
set bbb = `$gtasUtil = $1 $2 $slop`
#
#  Modify the text of special tags in stub to create finalized script.
#
set specpy = /tmp/a2gtrad${$}.py
rm -rf $specpy >& /dev/null
touch $specpy
chmod 775 $specpy
if ( "$eee" == "" ) then
  cat $stubpy | sed "s/KKKK/$rrr/g" | sed "s/MMMM/$mmm/g" | \
      sed "s/AAAAA/$aaa/g" | sed "s/BBBBB/$bbb/g" | sed "s/FFF/$fff/g" | \
      sed "s/DDDDD/$ddd/g" | sed 's/^.*EEEE.*$//g' >> $specpy
else
  cat $stubpy | sed "s/KKKK/$rrr/g" | sed "s/MMMM/$mmm/g" | \
      sed "s/AAAAA/$aaa/g" | sed "s/BBBBB/$bbb/g" | sed "s/FFF/$fff/g" | \
      sed "s/DDDDD/$ddd/g" | sed "s/EEEE/$eee/g" >> $specpy
endif
#
#  Submit the temporary python script stripping xml stuff, then remove it
#
cd $UE_BIN_PATH
( uengine -r python < $specpy ) | grep -v '<' | sed 's/&gt;/>/g' | \
   sed 's/&lt;/</g' | grep -v Response
if ( "$rmpy" == "yes" ) rm -rf $specpy >& /dev/null
#
