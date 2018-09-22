
var resolution=8;
var brightness=100;
var contrast=5;
var mode=0;
var flip=0;
var fps=0;




//////

var R320_240=8;
var R640_480=32;
var R800_600=48;
var ptz_type=0;	
if(top.client_minor==4) ptz_type=1;
var PTZ_STOP=1;
var TILT_UP=0;
var TILT_UP_STOP=1;
var TILT_DOWN=2;
var TILT_DOWN_STOP=3;
var PAN_LEFT=4;
var PAN_LEFT_STOP=5;
var PAN_RIGHT=6;
var PAN_RIGHT_STOP=7;
var PTZ_LEFT_UP=90;
var PTZ_RIGHT_UP=91;
var PTZ_LEFT_DOWN=92;
var PTZ_RIGHT_DOWN=93;
var PTZ_CENTER=25;
var PTZ_VPATROL=26;
var PTZ_VPATROL_STOP=27;
var PTZ_HPATROL=28;
var PTZ_HPATROL_STOP=29;
var PTZ_PELCO_D_HPATROL=20;
var PTZ_PELCO_D_HPATROL_STOP=21;
var IO_ON=94;
var IO_OFF=95;
function decoder_control(command,ip,userPass)
{
//	console.log("decoder_control " +command +"IP=" +ip);
	action_zone.location=ip+'/decoder_control.cgi?'+userPass+'&command='+command;
}
function camera_control(param,value,ip,userPass)
{
	//console.log("camera_control param=" +param +" value="+value +"IP=" +ip);
    action_zone.location=ip+'/camera_control.cgi?'+userPass+'&param='+param+'&value='+value;
}


function switchon_onclick(v,ip,userPass)
{
    decoder_control(v,ip,userPass);
}
function up_onmousedown(ip,userPass) 
{
	decoder_control(TILT_UP,ip,userPass);
}
function up_onmouseup(ip,userPass) 
{
	decoder_control(TILT_UP_STOP,ip,userPass);
}
function down_onmousedown(ip,userPass) 
{
	decoder_control(TILT_DOWN,ip,userPass);
}
function down_onmouseup(ip,userPass) 
{
	decoder_control(TILT_DOWN_STOP,ip,userPass);	
}
function left_onmousedown(ip,userPass) 
{
	decoder_control(PAN_LEFT,ip,userPass);
}
function left_onmouseup(ip,userPass) 
{
	decoder_control(PAN_LEFT_STOP,ip,userPass);	
}
function right_onmousedown(ip,userPass) 
{
	decoder_control(PAN_RIGHT,ip,userPass);
}
function right_onmouseup(ip,userPass) 
{
	decoder_control(PAN_RIGHT_STOP,ip,userPass);
}
function leftup_onmousedown(ip,userPass) 
{
	decoder_control(PTZ_LEFT_UP,ip,userPass);
}
function leftup_onmouseup(ip,userPass) 
{
	decoder_control(PTZ_STOP,ip,userPass);
}
function rightup_onmousedown(ip,userPass) 
{	
	decoder_control(PTZ_RIGHT_UP,ip,userPass);
}
function rightup_onmouseup(ip,userPass) 
{
	decoder_control(PTZ_STOP,ip,userPass);
}
function leftdown_onmousedown(ip,userPass) 
{
	decoder_control(PTZ_LEFT_DOWN,ip,userPass);
}
function leftdown_onmouseup(ip,userPass) 
{
	decoder_control(PTZ_STOP,ip,userPass);
}
function rightdown_onmousedown(ip,userPass) 
{
	decoder_control(PTZ_RIGHT_DOWN,ip,userPass);
}
function rightdown_onmouseup(ip,userPass) 
{
	decoder_control(PTZ_STOP,ip,userPass);
}
function center_onclick(ip,userPass) 
{
	decoder_control(PTZ_CENTER,ip,userPass);
}
function gocenter(ip,userPass)
{
    center_onclick(ip,userPass);
}
function vpatrol_onclick(ip,userPass)
{
   decoder_control(PTZ_VPATROL,ip,userPass);
}

function vpatrolstop_onclick(ip,userPass)
{
   decoder_control(PTZ_VPATROL_STOP,ip,userPass);
}
function hpatrol_onclick(ip,userPass) 
{
	decoder_control(PTZ_HPATROL,ip,userPass);
}
function hpatrolstop_onclick(ip,userPass) 
{
	decoder_control(PTZ_HPATROL_STOP,ip,userPass);
}
function set_resolution(v,valor,ip,obj,controles,userPass)
{
	
	obj 	  = document.getElementById(obj);
	controles =  document.getElementById(controles);
   // camera_control(v,valor,ip,userPass); 
    if(R320_240 == valor)
    {
    	obj.width  = 380;
    	obj.height = 270;
    	controles.width  = 360;
    }
    else if(R640_480 == valor)
    {
    	obj.width  = 640;
    	obj.height = 480;
    	controles.width  = 640;
    }
    else if(R800_600 == valor){
    	obj.width  = 800;
    	obj.height = 600;
    	controles.width  = 800;
    }
    else
    {
    	obj.width  = 380;
    	obj.height = 270;
    	controles.width  = 360;
    }
}
function default_all(resolution_sel,brightness_input,contrast_input,ip,obj,controles,userPass)
{	
	resolution_sel.selectedIndex=0;
//	mode_sel.selectedIndex=0;
	brightness_input.value=6;
	contrast_input.value=4;
	
	set_brightness(brightness_input,ip,userPass);
	set_contrast(contrast_input,ip,userPass);
	
	set_resolution(0,R320_240,ip,obj,controles,userPass);
	
   //  set_resolution(3,0,ip);
   // set_resolution(0,8,ip);
	
}

function set_brightness(obj,ip,userPass)
{  
	val=obj.value;
	camera_control(1,val*16,ip,userPass);	
}

function set_contrast(obj,ip,userPass)
{	
	val=obj.value;	
	camera_control(2,val,ip,userPass);			
}

function plus_brightness(obj,ip,userPass)
{
    
	val=obj.value;
	if (val++<15)
	{
		obj.value=val;	
		camera_control(1,val*16,ip,userPass);
	}
}

function minus_brightness(obj,ip,userPass)
{

	val=obj.value;
	if (val-->0)
	{
		obj.value = val;
	    camera_control(1,val*16,ip,userPass);
	}
		
}
function plus_contrast(obj,ip,userPass)
{
	
	val=obj.value;
	if (val++<6)
	{
		obj.value=val;
		camera_control(2,val,ip,userPass);
	}
			
}
function minus_contrast(obj,ip,userPass)
{

	val=obj.value;
	if (val-->0)
	{
		obj.value=val;
		camera_control(2,val,ip,userPass);
	}	
	
}



