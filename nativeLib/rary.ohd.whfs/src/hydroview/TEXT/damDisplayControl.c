/*
** Generated by X-Designer
*/
/*
**LIBS: -lXm -lXt -lX11
*/

#include <stdlib.h>
#include <X11/Xatom.h>
#include <X11/Intrinsic.h>
#include <X11/Shell.h>

#include <Xm/Xm.h>
#include <Xm/DialogS.h>
#include <Xm/Form.h>
#include <Xm/Label.h>
#include <Xm/PushB.h>
#include <Xm/RowColumn.h>
#include <Xm/Separator.h>
#include <Xm/TextF.h>
#include <Xm/ToggleB.h>
#include <Xm/CascadeBG.h>
#include <Xm/LabelG.h>


#include "ffgcontrol.h"

Widget damDisplayControlDS = (Widget) NULL;
Widget damDisplayControlFO = (Widget) NULL;
Widget dam_closeDisplayPB = (Widget) NULL;
Widget dam_clearDisplayPB = (Widget) NULL;
Widget dam_mapDisplayPB = (Widget) NULL;
Widget damDisplayOptionFO = (Widget) NULL;
Widget damOptionLA = (Widget) NULL;
Widget damDisplayIdTB = (Widget) NULL;
Widget damDisplayNameTB = (Widget) NULL;
Widget damDisplayIconTB = (Widget) NULL;
Widget damFilterOperatorOM = (Widget) NULL;
Widget damFilterOperatorCB = (Widget) NULL;
Widget damFilterOperatorPDM = (Widget) NULL;
Widget damGreaterThanPB = (Widget) NULL;
Widget damEqualsPB = (Widget) NULL;
Widget DamLessThanPB = (Widget) NULL;
Widget damAllPB = (Widget) NULL;
Widget damFilterValueTE = (Widget) NULL;
Widget damLatCenterTE = (Widget) NULL;
Widget damLonCenterTE = (Widget) NULL;
Widget damLatOffsetTE = (Widget) NULL;
Widget damLonOffsetTE = (Widget) NULL;
Widget enableLatLonTB = (Widget) NULL;



void create_damDisplayControlDS (Widget parent)
{
	Widget children[19];      /* Children to manage */
	Arg al[64];                    /* Arg List */
	register int ac = 0;           /* Arg Count */
	XmString xmstrings[16];    /* temporary storage for XmStrings */
	Widget label3 = (Widget)NULL;
	Widget label2 = (Widget)NULL;
	Widget label5 = (Widget)NULL;
	Widget label6 = (Widget)NULL;
	Widget label7 = (Widget)NULL;
	Widget label8 = (Widget)NULL;
	Widget separator1 = (Widget)NULL;
	Widget separator2 = (Widget)NULL;

	XtSetArg(al[ac], XmNwidth, 300); ac++;
	XtSetArg(al[ac], XmNheight, 300); ac++;
	XtSetArg(al[ac], XmNallowShellResize, FALSE); ac++;
	XtSetArg(al[ac], XmNminWidth, 300); ac++;
	XtSetArg(al[ac], XmNminHeight, 300); ac++;
	XtSetArg(al[ac], XmNmaxWidth, 300); ac++;
	XtSetArg(al[ac], XmNmaxHeight, 300); ac++;
	XtSetArg(al[ac], XmNbaseWidth, 300); ac++;
	XtSetArg(al[ac], XmNbaseHeight, 300); ac++;
	damDisplayControlDS = XmCreateDialogShell ( parent, "damDisplayControlDS", al, ac );
	ac = 0;
	XtSetArg(al[ac], XmNwidth, 300); ac++;
	XtSetArg(al[ac], XmNheight, 300); ac++;
	xmstrings[0] = XmStringCreateLtoR ( "Dam Display Control", (XmStringCharSet)XmFONTLIST_DEFAULT_TAG );
	XtSetArg(al[ac], XmNdialogTitle, xmstrings[0]); ac++;
	XtSetArg(al[ac], XmNnoResize, TRUE); ac++;
	XtSetArg(al[ac], XmNresizePolicy, XmRESIZE_NONE); ac++;
	XtSetArg(al[ac], XmNautoUnmanage, FALSE); ac++;
	damDisplayControlFO = XmCreateForm ( damDisplayControlDS, "ffg_displayControlFO", al, ac );
	ac = 0;
	XmStringFree ( xmstrings [ 0 ] );
	xmstrings[0] = XmStringCreateLtoR ( "Close", (XmStringCharSet)XmFONTLIST_DEFAULT_TAG );
	XtSetArg(al[ac], XmNlabelString, xmstrings[0]); ac++;
	dam_closeDisplayPB = XmCreatePushButton ( damDisplayControlFO, "dam_closeDisplayPB", al, ac );
	ac = 0;
	XmStringFree ( xmstrings [ 0 ] );
	XtSetArg(al[ac], XmNwidth, 60); ac++;
	XtSetArg(al[ac], XmNheight, 30); ac++;
	xmstrings[0] = XmStringCreateLtoR ( "Clear Data", (XmStringCharSet)XmFONTLIST_DEFAULT_TAG );
	XtSetArg(al[ac], XmNlabelString, xmstrings[0]); ac++;
	dam_clearDisplayPB = XmCreatePushButton ( damDisplayControlFO, "dam_clearDisplayPB", al, ac );
	ac = 0;
	XmStringFree ( xmstrings [ 0 ] );
	XtSetArg(al[ac], XmNwidth, 60); ac++;
	XtSetArg(al[ac], XmNheight, 30); ac++;
	xmstrings[0] = XmStringCreateLtoR ( "Map Data", (XmStringCharSet)XmFONTLIST_DEFAULT_TAG );
	XtSetArg(al[ac], XmNlabelString, xmstrings[0]); ac++;
	dam_mapDisplayPB = XmCreatePushButton ( damDisplayControlFO, "dam_mapDisplayPB", al, ac );
	ac = 0;
	XmStringFree ( xmstrings [ 0 ] );
	damDisplayOptionFO = XmCreateForm ( damDisplayControlFO, "damDisplayOptionFO", al, ac );
	xmstrings[0] = XmStringCreateLtoR ( "Display:", (XmStringCharSet)XmFONTLIST_DEFAULT_TAG );
	XtSetArg(al[ac], XmNlabelString, xmstrings[0]); ac++;
	damOptionLA = XmCreateLabel ( damDisplayOptionFO, "damOptionLA", al, ac );
	ac = 0;
	XmStringFree ( xmstrings [ 0 ] );
	xmstrings[0] = XmStringCreateLtoR ( "Id", (XmStringCharSet)XmFONTLIST_DEFAULT_TAG );
	XtSetArg(al[ac], XmNlabelString, xmstrings[0]); ac++;
	damDisplayIdTB = XmCreateToggleButton ( damDisplayOptionFO, "damDisplayIdTB", al, ac );
	ac = 0;
	XmStringFree ( xmstrings [ 0 ] );
	xmstrings[0] = XmStringCreateLtoR ( "Name", (XmStringCharSet)XmFONTLIST_DEFAULT_TAG );
	XtSetArg(al[ac], XmNlabelString, xmstrings[0]); ac++;
	damDisplayNameTB = XmCreateToggleButton ( damDisplayOptionFO, "damDisplayNameTB", al, ac );
	ac = 0;
	XmStringFree ( xmstrings [ 0 ] );
	xmstrings[0] = XmStringCreateLtoR ( "Icon", (XmStringCharSet)XmFONTLIST_DEFAULT_TAG );
	XtSetArg(al[ac], XmNlabelString, xmstrings[0]); ac++;
	damDisplayIconTB = XmCreateToggleButton ( damDisplayOptionFO, "damDisplayIconTB", al, ac );
	ac = 0;
	XmStringFree ( xmstrings [ 0 ] );
	xmstrings[0] = XmStringCreateLtoR ( "Filter by:", (XmStringCharSet)XmFONTLIST_DEFAULT_TAG );
	XtSetArg(al[ac], XmNlabelString, xmstrings[0]); ac++;
	label3 = XmCreateLabel ( damDisplayControlFO, "label3", al, ac );
	ac = 0;
	XmStringFree ( xmstrings [ 0 ] );
	damFilterOperatorOM = XmCreateOptionMenu ( damDisplayControlFO, "damFilterOperatorOM", al, ac );
	damFilterOperatorCB = XmOptionButtonGadget ( damFilterOperatorOM );
	damFilterOperatorPDM = XmCreatePulldownMenu ( damFilterOperatorOM, "damFilterOperatorPDM", al, ac );
	xmstrings[0] = XmStringCreateLtoR ( "greater than", (XmStringCharSet)XmFONTLIST_DEFAULT_TAG );
	XtSetArg(al[ac], XmNlabelString, xmstrings[0]); ac++;
	damGreaterThanPB = XmCreatePushButton ( damFilterOperatorPDM, "damGreaterThanPB", al, ac );
	ac = 0;
	XmStringFree ( xmstrings [ 0 ] );
	xmstrings[0] = XmStringCreateLtoR ( "equal to", (XmStringCharSet)XmFONTLIST_DEFAULT_TAG );
	XtSetArg(al[ac], XmNlabelString, xmstrings[0]); ac++;
	damEqualsPB = XmCreatePushButton ( damFilterOperatorPDM, "damEqualsPB", al, ac );
	ac = 0;
	XmStringFree ( xmstrings [ 0 ] );
	xmstrings[0] = XmStringCreateLtoR ( "less than", (XmStringCharSet)XmFONTLIST_DEFAULT_TAG );
	XtSetArg(al[ac], XmNlabelString, xmstrings[0]); ac++;
	DamLessThanPB = XmCreatePushButton ( damFilterOperatorPDM, "DamLessThanPB", al, ac );
	ac = 0;
	XmStringFree ( xmstrings [ 0 ] );
	xmstrings[0] = XmStringCreateLtoR ( "All", (XmStringCharSet)XmFONTLIST_DEFAULT_TAG );
	XtSetArg(al[ac], XmNlabelString, xmstrings[0]); ac++;
	damAllPB = XmCreatePushButton ( damFilterOperatorPDM, "damAllPB", al, ac );
	ac = 0;
	XmStringFree ( xmstrings [ 0 ] );
	XtSetArg(al[ac], XmNmaxLength, 8); ac++;
	XtSetArg(al[ac], XmNcolumns, 8); ac++;
	damFilterValueTE = XmCreateTextField ( damDisplayControlFO, "damFilterValueTE", al, ac );
	ac = 0;
	xmstrings[0] = XmStringCreateLtoR ( "Dam Volume\n(acre-ft.)", (XmStringCharSet)XmFONTLIST_DEFAULT_TAG );
	XtSetArg(al[ac], XmNlabelString, xmstrings[0]); ac++;
	label2 = XmCreateLabel ( damDisplayControlFO, "label2", al, ac );
	ac = 0;
	XmStringFree ( xmstrings [ 0 ] );
	XtSetArg(al[ac], XmNmaxLength, 6); ac++;
	damLatCenterTE = XmCreateTextField ( damDisplayControlFO, "damLatCenterTE", al, ac );
	ac = 0;
	XtSetArg(al[ac], XmNmaxLength, 6); ac++;
	damLonCenterTE = XmCreateTextField ( damDisplayControlFO, "damLonCenterTE", al, ac );
	ac = 0;
	XtSetArg(al[ac], XmNmaxLength, 6); ac++;
	damLatOffsetTE = XmCreateTextField ( damDisplayControlFO, "damLatOffsetTE", al, ac );
	ac = 0;
	XtSetArg(al[ac], XmNmaxLength, 6); ac++;
	damLonOffsetTE = XmCreateTextField ( damDisplayControlFO, "damLonOffsetTE", al, ac );
	ac = 0;
	xmstrings[0] = XmStringCreateLtoR ( "Latitude", (XmStringCharSet)XmFONTLIST_DEFAULT_TAG );
	XtSetArg(al[ac], XmNlabelString, xmstrings[0]); ac++;
	label5 = XmCreateLabel ( damDisplayControlFO, "label5", al, ac );
	ac = 0;
	XmStringFree ( xmstrings [ 0 ] );
	xmstrings[0] = XmStringCreateLtoR ( "Longitude", (XmStringCharSet)XmFONTLIST_DEFAULT_TAG );
	XtSetArg(al[ac], XmNlabelString, xmstrings[0]); ac++;
	label6 = XmCreateLabel ( damDisplayControlFO, "label6", al, ac );
	ac = 0;
	XmStringFree ( xmstrings [ 0 ] );
	xmstrings[0] = XmStringCreateLtoR ( "Center Point", (XmStringCharSet)XmFONTLIST_DEFAULT_TAG );
	XtSetArg(al[ac], XmNlabelString, xmstrings[0]); ac++;
	label7 = XmCreateLabel ( damDisplayControlFO, "label7", al, ac );
	ac = 0;
	XmStringFree ( xmstrings [ 0 ] );
	xmstrings[0] = XmStringCreateLtoR ( "Offset", (XmStringCharSet)XmFONTLIST_DEFAULT_TAG );
	XtSetArg(al[ac], XmNlabelString, xmstrings[0]); ac++;
	label8 = XmCreateLabel ( damDisplayControlFO, "label8", al, ac );
	ac = 0;
	XmStringFree ( xmstrings [ 0 ] );
	separator1 = XmCreateSeparator ( damDisplayControlFO, "separator1", al, ac );
	separator2 = XmCreateSeparator ( damDisplayControlFO, "separator2", al, ac );
	xmstrings[0] = XmStringCreateLtoR ( "Enable", (XmStringCharSet)XmFONTLIST_DEFAULT_TAG );
	XtSetArg(al[ac], XmNlabelString, xmstrings[0]); ac++;
	enableLatLonTB = XmCreateToggleButton ( damDisplayControlFO, "enableLatLonTB", al, ac );
	ac = 0;
	XmStringFree ( xmstrings [ 0 ] );


	XtSetArg(al[ac], XmNtopAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNtopOffset, 265); ac++;
	XtSetArg(al[ac], XmNbottomAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNbottomOffset, -295); ac++;
	XtSetArg(al[ac], XmNleftAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNleftOffset, 115); ac++;
	XtSetArg(al[ac], XmNrightAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNrightOffset, -175); ac++;
	XtSetValues ( dam_closeDisplayPB,al, ac );
	ac = 0;

	XtSetArg(al[ac], XmNtopAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNtopOffset, 265); ac++;
	XtSetArg(al[ac], XmNbottomAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNbottomOffset, -295); ac++;
	XtSetArg(al[ac], XmNleftAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNleftOffset, 200); ac++;
	XtSetArg(al[ac], XmNrightAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNrightOffset, -285); ac++;
	XtSetValues ( dam_clearDisplayPB,al, ac );
	ac = 0;

	XtSetArg(al[ac], XmNtopAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNtopOffset, 265); ac++;
	XtSetArg(al[ac], XmNbottomAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNbottomOffset, -295); ac++;
	XtSetArg(al[ac], XmNleftAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNleftOffset, 15); ac++;
	XtSetArg(al[ac], XmNrightAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNrightOffset, -100); ac++;
	XtSetValues ( dam_mapDisplayPB,al, ac );
	ac = 0;

	XtSetArg(al[ac], XmNtopAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNtopOffset, 220); ac++;
	XtSetArg(al[ac], XmNbottomAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNbottomOffset, -250); ac++;
	XtSetArg(al[ac], XmNleftAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNleftOffset, 5); ac++;
	XtSetArg(al[ac], XmNrightAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNrightOffset, -295); ac++;
	XtSetValues ( damDisplayOptionFO,al, ac );
	ac = 0;

	XtSetArg(al[ac], XmNtopAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNtopOffset, 5); ac++;
	XtSetArg(al[ac], XmNbottomAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNbottomOffset, -25); ac++;
	XtSetArg(al[ac], XmNleftAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNleftOffset, 5); ac++;
	XtSetArg(al[ac], XmNrightAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNrightOffset, -70); ac++;
	XtSetValues ( label3,al, ac );
	ac = 0;

	XtSetArg(al[ac], XmNtopAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNtopOffset, 30); ac++;
	XtSetArg(al[ac], XmNbottomAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNbottomOffset, -65); ac++;
	XtSetArg(al[ac], XmNleftAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNleftOffset, 95); ac++;
	XtSetArg(al[ac], XmNrightAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNrightOffset, -220); ac++;
	XtSetValues ( damFilterOperatorOM,al, ac );
	ac = 0;

	XtSetArg(al[ac], XmNtopAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNtopOffset, 30); ac++;
	XtSetArg(al[ac], XmNbottomAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNbottomOffset, -65); ac++;
	XtSetArg(al[ac], XmNleftAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNleftOffset, 225); ac++;
	XtSetArg(al[ac], XmNrightAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNrightOffset, -295); ac++;
	XtSetValues ( damFilterValueTE,al, ac );
	ac = 0;

	XtSetArg(al[ac], XmNtopAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNtopOffset, 30); ac++;
	XtSetArg(al[ac], XmNbottomAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNbottomOffset, -65); ac++;
	XtSetArg(al[ac], XmNleftAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNleftOffset, 5); ac++;
	XtSetArg(al[ac], XmNrightAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNrightOffset, -90); ac++;
	XtSetValues ( label2,al, ac );
	ac = 0;

	XtSetArg(al[ac], XmNtopAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNtopOffset, 115); ac++;
	XtSetArg(al[ac], XmNbottomAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNbottomOffset, -150); ac++;
	XtSetArg(al[ac], XmNleftAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNleftOffset, 5); ac++;
	XtSetArg(al[ac], XmNrightAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNrightOffset, -85); ac++;
	XtSetValues ( damLatCenterTE,al, ac );
	ac = 0;

	XtSetArg(al[ac], XmNtopAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNtopOffset, 160); ac++;
	XtSetArg(al[ac], XmNbottomAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNbottomOffset, -195); ac++;
	XtSetArg(al[ac], XmNleftAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNleftOffset, 5); ac++;
	XtSetArg(al[ac], XmNrightAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNrightOffset, -85); ac++;
	XtSetValues ( damLonCenterTE,al, ac );
	ac = 0;

	XtSetArg(al[ac], XmNtopAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNtopOffset, 115); ac++;
	XtSetArg(al[ac], XmNbottomAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNbottomOffset, -150); ac++;
	XtSetArg(al[ac], XmNleftAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNleftOffset, 155); ac++;
	XtSetArg(al[ac], XmNrightAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNrightOffset, -210); ac++;
	XtSetValues ( damLatOffsetTE,al, ac );
	ac = 0;

	XtSetArg(al[ac], XmNtopAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNtopOffset, 160); ac++;
	XtSetArg(al[ac], XmNbottomAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNbottomOffset, -195); ac++;
	XtSetArg(al[ac], XmNleftAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNleftOffset, 155); ac++;
	XtSetArg(al[ac], XmNrightAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNrightOffset, -210); ac++;
	XtSetValues ( damLonOffsetTE,al, ac );
	ac = 0;

	XtSetArg(al[ac], XmNtopAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNtopOffset, 120); ac++;
	XtSetArg(al[ac], XmNbottomAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNbottomOffset, -145); ac++;
	XtSetArg(al[ac], XmNleftAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNleftOffset, 85); ac++;
	XtSetArg(al[ac], XmNrightAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNrightOffset, -155); ac++;
	XtSetValues ( label5,al, ac );
	ac = 0;

	XtSetArg(al[ac], XmNtopAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNtopOffset, 165); ac++;
	XtSetArg(al[ac], XmNbottomAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNbottomOffset, -190); ac++;
	XtSetArg(al[ac], XmNleftAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNleftOffset, 85); ac++;
	XtSetArg(al[ac], XmNrightAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNrightOffset, -155); ac++;
	XtSetValues ( label6,al, ac );
	ac = 0;

	XtSetArg(al[ac], XmNtopAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNtopOffset, 95); ac++;
	XtSetArg(al[ac], XmNbottomAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNbottomOffset, -115); ac++;
	XtSetArg(al[ac], XmNleftAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNleftOffset, 5); ac++;
	XtSetArg(al[ac], XmNrightAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNrightOffset, -85); ac++;
	XtSetValues ( label7,al, ac );
	ac = 0;

	XtSetArg(al[ac], XmNtopAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNtopOffset, 95); ac++;
	XtSetArg(al[ac], XmNbottomAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNbottomOffset, -115); ac++;
	XtSetArg(al[ac], XmNleftAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNleftOffset, 155); ac++;
	XtSetArg(al[ac], XmNrightAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNrightOffset, -210); ac++;
	XtSetValues ( label8,al, ac );
	ac = 0;

	XtSetArg(al[ac], XmNtopAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNtopOffset, 250); ac++;
	XtSetArg(al[ac], XmNbottomAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNbottomOffset, -265); ac++;
	XtSetArg(al[ac], XmNleftAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNleftOffset, 5); ac++;
	XtSetArg(al[ac], XmNrightAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNrightOffset, -295); ac++;
	XtSetValues ( separator1,al, ac );
	ac = 0;

	XtSetArg(al[ac], XmNtopAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNtopOffset, 205); ac++;
	XtSetArg(al[ac], XmNbottomAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNbottomOffset, -220); ac++;
	XtSetArg(al[ac], XmNleftAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNleftOffset, 5); ac++;
	XtSetArg(al[ac], XmNrightAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNrightOffset, -295); ac++;
	XtSetValues ( separator2,al, ac );
	ac = 0;

	XtSetArg(al[ac], XmNtopAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNtopOffset, 140); ac++;
	XtSetArg(al[ac], XmNbottomAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNbottomOffset, -170); ac++;
	XtSetArg(al[ac], XmNleftAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNleftOffset, 220); ac++;
	XtSetArg(al[ac], XmNrightAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNrightOffset, -285); ac++;
	XtSetValues ( enableLatLonTB,al, ac );
	ac = 0;

	XtSetArg(al[ac], XmNtopAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNtopOffset, 5); ac++;
	XtSetArg(al[ac], XmNbottomAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNbottomOffset, -30); ac++;
	XtSetArg(al[ac], XmNleftAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNleftOffset, 5); ac++;
	XtSetArg(al[ac], XmNrightAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNrightOffset, -60); ac++;
	XtSetValues ( damOptionLA,al, ac );
	ac = 0;

	XtSetArg(al[ac], XmNtopAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNtopOffset, 5); ac++;
	XtSetArg(al[ac], XmNbottomAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNbottomOffset, -30); ac++;
	XtSetArg(al[ac], XmNleftAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNleftOffset, 70); ac++;
	XtSetArg(al[ac], XmNrightAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNrightOffset, -130); ac++;
	XtSetValues ( damDisplayIdTB,al, ac );
	ac = 0;

	XtSetArg(al[ac], XmNtopAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNtopOffset, 5); ac++;
	XtSetArg(al[ac], XmNbottomAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNbottomOffset, -30); ac++;
	XtSetArg(al[ac], XmNleftAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNleftOffset, 145); ac++;
	XtSetArg(al[ac], XmNrightAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNrightOffset, -205); ac++;
	XtSetValues ( damDisplayNameTB,al, ac );
	ac = 0;

	XtSetArg(al[ac], XmNtopAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNtopOffset, 5); ac++;
	XtSetArg(al[ac], XmNbottomAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNbottomOffset, -30); ac++;
	XtSetArg(al[ac], XmNleftAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNleftOffset, 220); ac++;
	XtSetArg(al[ac], XmNrightAttachment, XmATTACH_OPPOSITE_FORM); ac++;
	XtSetArg(al[ac], XmNrightOffset, -280); ac++;
	XtSetValues ( damDisplayIconTB,al, ac );
	ac = 0;
	children[ac++] = damOptionLA;
	children[ac++] = damDisplayIdTB;
	children[ac++] = damDisplayNameTB;
	children[ac++] = damDisplayIconTB;
	XtManageChildren(children, ac);
	ac = 0;
	children[ac++] = damGreaterThanPB;
	children[ac++] = damEqualsPB;
	children[ac++] = DamLessThanPB;
	children[ac++] = damAllPB;
	XtManageChildren(children, ac);
	ac = 0;
	XtSetArg(al[ac], XmNsubMenuId, damFilterOperatorPDM); ac++;
	XtSetValues(damFilterOperatorCB, al, ac );
	ac = 0;
	children[ac++] = dam_closeDisplayPB;
	children[ac++] = dam_clearDisplayPB;
	children[ac++] = dam_mapDisplayPB;
	children[ac++] = damDisplayOptionFO;
	children[ac++] = label3;
	children[ac++] = damFilterOperatorOM;
	children[ac++] = damFilterValueTE;
	children[ac++] = label2;
	children[ac++] = damLatCenterTE;
	children[ac++] = damLonCenterTE;
	children[ac++] = damLatOffsetTE;
	children[ac++] = damLonOffsetTE;
	children[ac++] = label5;
	children[ac++] = label6;
	children[ac++] = label7;
	children[ac++] = label8;
	children[ac++] = separator1;
	children[ac++] = separator2;
	children[ac++] = enableLatLonTB;
	XtManageChildren(children, ac);
	ac = 0;
}

