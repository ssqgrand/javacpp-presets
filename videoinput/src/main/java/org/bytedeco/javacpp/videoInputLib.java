// Targeted by JavaCPP version 0.11

package org.bytedeco.javacpp;

import java.nio.*;
import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.annotation.*;

public class videoInputLib extends org.bytedeco.javacpp.presets.videoInputLib {
    static { Loader.load(); }

@Name("std::vector<std::string>") public static class StringVector extends Pointer {
    static { Loader.load(); }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public StringVector(Pointer p) { super(p); }
    public StringVector(BytePointer ... array) { this(array.length); put(array); }
    public StringVector()       { allocate();  }
    public StringVector(long n) { allocate(n); }
    private native void allocate();
    private native void allocate(@Cast("size_t") long n);
    public native @Name("operator=") @ByRef StringVector put(@ByRef StringVector x);

    public native long size();
    public native void resize(@Cast("size_t") long n);

    @Index public native @StdString BytePointer get(@Cast("size_t") long i);
    public native StringVector put(@Cast("size_t") long i, BytePointer value);

    public StringVector put(BytePointer ... array) {
        if (size() != array.length) { resize(array.length); }
        for (int i = 0; i < array.length; i++) {
            put(i, array[i]);
        }
        return this;
    }
}

// Parsed from <videoInput.h>

// #ifndef _VIDEOINPUT
// #define _VIDEOINPUT

//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
//THE SOFTWARE.

//////////////////////////////////////////////////////////
//Written by Theodore Watson - theo.watson@gmail.com    //
//Do whatever you want with this code but if you find   //
//a bug or make an improvement I would love to know!    //
//														//
//Warning This code is experimental 					//
//use at your own risk :)								//
//////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////
/*                     Shoutouts

Thanks to:

		   Dillip Kumar Kara for crossbar code.
		   Zachary Lieberman for getting me into this stuff
		   and for being so generous with time and code.
		   The guys at Potion Design for helping me with VC++
		   Josh Fisher for being a serious C++ nerd :)
		   Golan Levin for helping me debug the strangest
		   and slowest bug in the world!

		   And all the people using this library who send in
		   bugs, suggestions and improvements who keep me working on
		   the next version - yeah thanks a lot ;)

*/
/////////////////////////////////////////////////////////



// #include <stdlib.h>
// #include <stdio.h>
// #include <math.h>
// #include <string.h>
// #include <wchar.h>
// #include <string>
// #include <vector>

//this is for TryEnterCriticalSection
// #ifndef _WIN32_WINNT
// 	#   define _WIN32_WINNT 0x400
// #endif
// #include <windows.h>


//Example Usage
/*
	//create a videoInput object
	videoInput VI;

	//Prints out a list of available devices and returns num of devices found
	int numDevices = VI.listDevices();

	int device1 = 0;  //this could be any deviceID that shows up in listDevices
	int device2 = 1;  //this could be any deviceID that shows up in listDevices

	//if you want to capture at a different frame rate (default is 30)
	//specify it here, you are not guaranteed to get this fps though.
	//VI.setIdealFramerate(dev, 60);

	//setup the first device - there are a number of options:

	VI.setupDevice(device1); 						  //setup the first device with the default settings
	//VI.setupDevice(device1, VI_COMPOSITE); 			  //or setup device with specific connection type
	//VI.setupDevice(device1, 320, 240);				  //or setup device with specified video size
	//VI.setupDevice(device1, 320, 240, VI_COMPOSITE);  //or setup device with video size and connection type

	//VI.setFormat(device1, VI_NTSC_M);					//if your card doesn't remember what format it should be
														//call this with the appropriate format listed above
														//NOTE: must be called after setupDevice!

	//optionally setup a second (or third, fourth ...) device - same options as above
	VI.setupDevice(device2);

	//As requested width and height can not always be accomodated
	//make sure to check the size once the device is setup

	int width 	= VI.getWidth(device1);
	int height 	= VI.getHeight(device1);
	int size	= VI.getSize(device1);

	unsigned char * yourBuffer1 = new unsigned char[size];
	unsigned char * yourBuffer2 = new unsigned char[size];

	//to get the data from the device first check if the data is new
	if(VI.isFrameNew(device1)){
		VI.getPixels(device1, yourBuffer1, false, false);	//fills pixels as a BGR (for openCV) unsigned char array - no flipping
		VI.getPixels(device1, yourBuffer2, true, true); 	//fills pixels as a RGB (for openGL) unsigned char array - flipping!
	}

	//same applies to device2 etc

	//to get a settings dialog for the device
	VI.showSettingsWindow(device1);


	//Shut down devices properly
	VI.stopDevice(device1);
	VI.stopDevice(device2);
*/


//////////////////////////////////////   VARS AND DEFS   //////////////////////////////////


//STUFF YOU DON'T CHANGE

//videoInput defines
public static final double VI_VERSION =	 0.200;
public static final int VI_MAX_CAMERAS =  20;
public static final int VI_NUM_TYPES =    19; //DON'T TOUCH
public static final int VI_NUM_FORMATS =  18; //DON'T TOUCH

//defines for setPhyCon - tuner is not as well supported as composite and s-video
public static final int VI_COMPOSITE = 0;
public static final int VI_S_VIDEO =   1;
public static final int VI_TUNER =     2;
public static final int VI_USB =       3;
public static final int VI_1394 =		 4;

//defines for formats
public static final int VI_NTSC_M =	0;
public static final int VI_PAL_B =	1;
public static final int VI_PAL_D =	2;
public static final int VI_PAL_G =	3;
public static final int VI_PAL_H =	4;
public static final int VI_PAL_I =	5;
public static final int VI_PAL_M =	6;
public static final int VI_PAL_N =	7;
public static final int VI_PAL_NC =	8;
public static final int VI_SECAM_B =	9;
public static final int VI_SECAM_D =	10;
public static final int VI_SECAM_G =	11;
public static final int VI_SECAM_H =	12;
public static final int VI_SECAM_K =	13;
public static final int VI_SECAM_K1 =	14;
public static final int VI_SECAM_L =	15;
public static final int VI_NTSC_M_J =	16;
public static final int VI_NTSC_433 =	17;

// added by gameover
public static final int VI_MEDIASUBTYPE_RGB24 =   0;
public static final int VI_MEDIASUBTYPE_RGB32 =   1;
public static final int VI_MEDIASUBTYPE_RGB555 =  2;
public static final int VI_MEDIASUBTYPE_RGB565 =  3;
public static final int VI_MEDIASUBTYPE_YUY2 =    4;
public static final int VI_MEDIASUBTYPE_YVYU =    5;
public static final int VI_MEDIASUBTYPE_YUYV =    6;
public static final int VI_MEDIASUBTYPE_IYUV =    7;
public static final int VI_MEDIASUBTYPE_UYVY =    8;
public static final int VI_MEDIASUBTYPE_YV12 =    9;
public static final int VI_MEDIASUBTYPE_YVU9 =    10;
public static final int VI_MEDIASUBTYPE_Y411 =    11;
public static final int VI_MEDIASUBTYPE_Y41P =    12;
public static final int VI_MEDIASUBTYPE_Y211 =    13;
public static final int VI_MEDIASUBTYPE_AYUV =    14;
public static final int VI_MEDIASUBTYPE_Y800 =    15;
public static final int VI_MEDIASUBTYPE_Y8 =      16;
public static final int VI_MEDIASUBTYPE_GREY =    17;
public static final int VI_MEDIASUBTYPE_MJPG =    18;

//allows us to directShow classes here with the includes in the cpp
@Opaque public static class ICaptureGraphBuilder2 extends Pointer {
    /** Empty constructor. */
    public ICaptureGraphBuilder2() { }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public ICaptureGraphBuilder2(Pointer p) { super(p); }
}
@Opaque public static class IGraphBuilder extends Pointer {
    /** Empty constructor. */
    public IGraphBuilder() { }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public IGraphBuilder(Pointer p) { super(p); }
}
@Opaque public static class IBaseFilter extends Pointer {
    /** Empty constructor. */
    public IBaseFilter() { }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public IBaseFilter(Pointer p) { super(p); }
}
@Opaque public static class IAMCrossbar extends Pointer {
    /** Empty constructor. */
    public IAMCrossbar() { }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public IAMCrossbar(Pointer p) { super(p); }
}
@Opaque public static class IMediaControl extends Pointer {
    /** Empty constructor. */
    public IMediaControl() { }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public IMediaControl(Pointer p) { super(p); }
}
@Opaque public static class ISampleGrabber extends Pointer {
    /** Empty constructor. */
    public ISampleGrabber() { }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public ISampleGrabber(Pointer p) { super(p); }
}
@Opaque public static class IMediaEventEx extends Pointer {
    /** Empty constructor. */
    public IMediaEventEx() { }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public IMediaEventEx(Pointer p) { super(p); }
}
@Opaque public static class IAMStreamConfig extends Pointer {
    /** Empty constructor. */
    public IAMStreamConfig() { }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public IAMStreamConfig(Pointer p) { super(p); }
}
@Opaque public static class _AMMediaType extends Pointer {
    /** Empty constructor. */
    public _AMMediaType() { }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public _AMMediaType(Pointer p) { super(p); }
}
@Opaque public static class SampleGrabberCallback extends Pointer {
    /** Empty constructor. */
    public SampleGrabberCallback() { }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public SampleGrabberCallback(Pointer p) { super(p); }
}

//keeps track of how many instances of VI are being used
//don't touch
public static native int comInitCount(); public static native void comInitCount(int comInitCount);


////////////////////////////////////////   VIDEO DEVICE   ///////////////////////////////////

@NoOffset public static class videoDevice extends Pointer {
    static { Loader.load(); }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public videoDevice(Pointer p) { super(p); }
    /** Native array allocator. Access with {@link Pointer#position(int)}. */
    public videoDevice(int size) { allocateArray(size); }
    private native void allocateArray(int size);
    @Override public videoDevice position(int position) {
        return (videoDevice)super.position(position);
    }


		public videoDevice() { allocate(); }
		private native void allocate();
		public native void setSize(int w, int h);
		public native void NukeDownstream(IBaseFilter pBF);
		public native void destroyGraph();

		public native int videoSize(); public native videoDevice videoSize(int videoSize);
		public native int width(); public native videoDevice width(int width);
		public native int height(); public native videoDevice height(int height);
		public native int tryWidth(); public native videoDevice tryWidth(int tryWidth);
		public native int tryHeight(); public native videoDevice tryHeight(int tryHeight);

		public native ICaptureGraphBuilder2 pCaptureGraph(); public native videoDevice pCaptureGraph(ICaptureGraphBuilder2 pCaptureGraph);	// Capture graph builder object
		public native IGraphBuilder pGraph(); public native videoDevice pGraph(IGraphBuilder pGraph);					// Graph builder object
	    public native IMediaControl pControl(); public native videoDevice pControl(IMediaControl pControl);				// Media control object
		public native IBaseFilter pVideoInputFilter(); public native videoDevice pVideoInputFilter(IBaseFilter pVideoInputFilter);  		// Video Capture filter
		public native IBaseFilter pGrabberF(); public native videoDevice pGrabberF(IBaseFilter pGrabberF);
		public native IBaseFilter pDestFilter(); public native videoDevice pDestFilter(IBaseFilter pDestFilter);
		public native IAMStreamConfig streamConf(); public native videoDevice streamConf(IAMStreamConfig streamConf);
		public native ISampleGrabber pGrabber(); public native videoDevice pGrabber(ISampleGrabber pGrabber);    			// Grabs frame
		public native @Cast("AM_MEDIA_TYPE*") _AMMediaType pAmMediaType(); public native videoDevice pAmMediaType(_AMMediaType pAmMediaType);

		public native IMediaEventEx pMediaEvent(); public native videoDevice pMediaEvent(IMediaEventEx pMediaEvent);

		public native @ByRef @Cast("GUID*") Pointer videoType(); public native videoDevice videoType(Pointer videoType);
		public native @Cast("long") int formatType(); public native videoDevice formatType(int formatType);

		public native SampleGrabberCallback sgCallback(); public native videoDevice sgCallback(SampleGrabberCallback sgCallback);

		public native @Cast("bool") boolean tryDiffSize(); public native videoDevice tryDiffSize(boolean tryDiffSize);
		public native @Cast("bool") boolean useCrossbar(); public native videoDevice useCrossbar(boolean useCrossbar);
		public native @Cast("bool") boolean readyToCapture(); public native videoDevice readyToCapture(boolean readyToCapture);
		public native @Cast("bool") boolean sizeSet(); public native videoDevice sizeSet(boolean sizeSet);
		public native @Cast("bool") boolean setupStarted(); public native videoDevice setupStarted(boolean setupStarted);
		public native @Cast("bool") boolean specificFormat(); public native videoDevice specificFormat(boolean specificFormat);
		public native @Cast("bool") boolean autoReconnect(); public native videoDevice autoReconnect(boolean autoReconnect);
		public native int nFramesForReconnect(); public native videoDevice nFramesForReconnect(int nFramesForReconnect);
		public native @Cast("unsigned long") int nFramesRunning(); public native videoDevice nFramesRunning(int nFramesRunning);
		public native int connection(); public native videoDevice connection(int connection);
		public native int storeConn(); public native videoDevice storeConn(int storeConn);
		public native int myID(); public native videoDevice myID(int myID);
		public native @Cast("long") int requestedFrameTime(); public native videoDevice requestedFrameTime(int requestedFrameTime); //ie fps

		public native @Cast("char") byte nDeviceName(int i); public native videoDevice nDeviceName(int i, byte nDeviceName);
		@MemberGetter public native @Cast("char*") BytePointer nDeviceName();
		public native @Cast("WCHAR") char wDeviceName(int i); public native videoDevice wDeviceName(int i, char wDeviceName);
		@MemberGetter public native @Cast("WCHAR*") CharPointer wDeviceName();

		public native @Cast("unsigned char*") BytePointer pixels(); public native videoDevice pixels(BytePointer pixels);
		public native @Cast("char*") BytePointer pBuffer(); public native videoDevice pBuffer(BytePointer pBuffer);

}




//////////////////////////////////////   VIDEO INPUT   /////////////////////////////////////



@NoOffset public static class videoInput extends Pointer {
    static { Loader.load(); }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public videoInput(Pointer p) { super(p); }
    /** Native array allocator. Access with {@link Pointer#position(int)}. */
    public videoInput(int size) { allocateArray(size); }
    private native void allocateArray(int size);
    @Override public videoInput position(int position) {
        return (videoInput)super.position(position);
    }

		public videoInput() { allocate(); }
		private native void allocate();

		//turns off console messages - default is to print messages
		public static native void setVerbose(@Cast("bool") boolean _verbose);

		//this allows for multithreaded use of VI ( default is single threaded ).
		//call this before any videoInput calls. 
		//note if your app has other COM calls then you should set VIs COM usage to match the other COM mode 
		public static native void setComMultiThreaded(@Cast("bool") boolean bMulti);

		//Functions in rough order they should be used.
		public static native int listDevices(@Cast("bool") boolean silent/*=false*/);
		public static native int listDevices();
		public static native @ByVal StringVector getDeviceList(); 

		//needs to be called after listDevices - otherwise returns NULL
		public static native @Cast("char*") BytePointer getDeviceName(int deviceID);
		public static native int getDeviceIDFromName(@Cast("char*") BytePointer name);
		public static native int getDeviceIDFromName(@Cast("char*") ByteBuffer name);
		public static native int getDeviceIDFromName(@Cast("char*") byte[] name);

		//choose to use callback based capture - or single threaded
		public native void setUseCallback(@Cast("bool") boolean useCallback);

		//call before setupDevice
		//directshow will try and get the closest possible framerate to what is requested
		public native void setIdealFramerate(int deviceID, int idealFramerate);

		//some devices will stop delivering frames after a while - this method gives you the option to try and reconnect
		//to a device if videoInput detects that a device has stopped delivering frames.
		//you MUST CALL isFrameNew every app loop for this to have any effect
		public native void setAutoReconnectOnFreeze(int deviceNumber, @Cast("bool") boolean doReconnect, int numMissedFramesBeforeReconnect);

		//Choose one of these four to setup your device
		public native @Cast("bool") boolean setupDevice(int deviceID);
		public native @Cast("bool") boolean setupDevice(int deviceID, int w, int h);

		//These two are only for capture cards
		//USB and Firewire cameras souldn't specify connection
		public native @Cast("bool") boolean setupDevice(int deviceID, int connection);
		public native @Cast("bool") boolean setupDevice(int deviceID, int w, int h, int connection);

		//If you need to you can set your NTSC/PAL/SECAM
		//preference here. if it is available it will be used.
		//see #defines above for available formats - eg VI_NTSC_M or VI_PAL_B
		//should be called after setupDevice
		//can be called multiple times
		public native @Cast("bool") boolean setFormat(int deviceNumber, int format);
		public native void setRequestedMediaSubType(int mediatype); // added by gameover

		//Tells you when a new frame has arrived - you should call this if you have specified setAutoReconnectOnFreeze to true
		public native @Cast("bool") boolean isFrameNew(int deviceID);

		public native @Cast("bool") boolean isDeviceSetup(int deviceID);

		//Returns the pixels - flipRedAndBlue toggles RGB/BGR flipping - and you can flip the image too
		public native @Cast("unsigned char*") BytePointer getPixels(int deviceID, @Cast("bool") boolean flipRedAndBlue/*=true*/, @Cast("bool") boolean flipImage/*=false*/);
		public native @Cast("unsigned char*") BytePointer getPixels(int deviceID);

		//Or pass in a buffer for getPixels to fill returns true if successful.
		public native @Cast("bool") boolean getPixels(int id, @Cast("unsigned char*") BytePointer pixels, @Cast("bool") boolean flipRedAndBlue/*=true*/, @Cast("bool") boolean flipImage/*=false*/);
		public native @Cast("bool") boolean getPixels(int id, @Cast("unsigned char*") BytePointer pixels);
		public native @Cast("bool") boolean getPixels(int id, @Cast("unsigned char*") ByteBuffer pixels, @Cast("bool") boolean flipRedAndBlue/*=true*/, @Cast("bool") boolean flipImage/*=false*/);
		public native @Cast("bool") boolean getPixels(int id, @Cast("unsigned char*") ByteBuffer pixels);
		public native @Cast("bool") boolean getPixels(int id, @Cast("unsigned char*") byte[] pixels, @Cast("bool") boolean flipRedAndBlue/*=true*/, @Cast("bool") boolean flipImage/*=false*/);
		public native @Cast("bool") boolean getPixels(int id, @Cast("unsigned char*") byte[] pixels);

		//Launches a pop up settings window
		//For some reason in GLUT you have to call it twice each time.
		public native void showSettingsWindow(int deviceID);

		//Manual control over settings thanks.....
		//These are experimental for now.
		public native @Cast("bool") boolean setVideoSettingFilter(int deviceID, @Cast("long") int Property, @Cast("long") int lValue, @Cast("long") int Flags/*=NULL*/, @Cast("bool") boolean useDefaultValue/*=false*/);
		public native @Cast("bool") boolean setVideoSettingFilter(int deviceID, @Cast("long") int Property, @Cast("long") int lValue);
		public native @Cast("bool") boolean setVideoSettingFilterPct(int deviceID, @Cast("long") int Property, float pctValue, @Cast("long") int Flags/*=NULL*/);
		public native @Cast("bool") boolean setVideoSettingFilterPct(int deviceID, @Cast("long") int Property, float pctValue);
		public native @Cast("bool") boolean getVideoSettingFilter(int deviceID, @Cast("long") int Property, @Cast("long*") @ByRef IntPointer min, @Cast("long*") @ByRef IntPointer max, @Cast("long*") @ByRef IntPointer SteppingDelta, @Cast("long*") @ByRef IntPointer currentValue, @Cast("long*") @ByRef IntPointer flags, @Cast("long*") @ByRef IntPointer defaultValue);
		public native @Cast("bool") boolean getVideoSettingFilter(int deviceID, @Cast("long") int Property, @Cast("long*") @ByRef IntBuffer min, @Cast("long*") @ByRef IntBuffer max, @Cast("long*") @ByRef IntBuffer SteppingDelta, @Cast("long*") @ByRef IntBuffer currentValue, @Cast("long*") @ByRef IntBuffer flags, @Cast("long*") @ByRef IntBuffer defaultValue);
		public native @Cast("bool") boolean getVideoSettingFilter(int deviceID, @Cast("long") int Property, @Cast("long*") @ByRef int[] min, @Cast("long*") @ByRef int[] max, @Cast("long*") @ByRef int[] SteppingDelta, @Cast("long*") @ByRef int[] currentValue, @Cast("long*") @ByRef int[] flags, @Cast("long*") @ByRef int[] defaultValue);

		public native @Cast("bool") boolean setVideoSettingCamera(int deviceID, @Cast("long") int Property, @Cast("long") int lValue, @Cast("long") int Flags/*=NULL*/, @Cast("bool") boolean useDefaultValue/*=false*/);
		public native @Cast("bool") boolean setVideoSettingCamera(int deviceID, @Cast("long") int Property, @Cast("long") int lValue);
		public native @Cast("bool") boolean setVideoSettingCameraPct(int deviceID, @Cast("long") int Property, float pctValue, @Cast("long") int Flags/*=NULL*/);
		public native @Cast("bool") boolean setVideoSettingCameraPct(int deviceID, @Cast("long") int Property, float pctValue);
		public native @Cast("bool") boolean getVideoSettingCamera(int deviceID, @Cast("long") int Property, @Cast("long*") @ByRef IntPointer min, @Cast("long*") @ByRef IntPointer max, @Cast("long*") @ByRef IntPointer SteppingDelta, @Cast("long*") @ByRef IntPointer currentValue, @Cast("long*") @ByRef IntPointer flags, @Cast("long*") @ByRef IntPointer defaultValue);
		public native @Cast("bool") boolean getVideoSettingCamera(int deviceID, @Cast("long") int Property, @Cast("long*") @ByRef IntBuffer min, @Cast("long*") @ByRef IntBuffer max, @Cast("long*") @ByRef IntBuffer SteppingDelta, @Cast("long*") @ByRef IntBuffer currentValue, @Cast("long*") @ByRef IntBuffer flags, @Cast("long*") @ByRef IntBuffer defaultValue);
		public native @Cast("bool") boolean getVideoSettingCamera(int deviceID, @Cast("long") int Property, @Cast("long*") @ByRef int[] min, @Cast("long*") @ByRef int[] max, @Cast("long*") @ByRef int[] SteppingDelta, @Cast("long*") @ByRef int[] currentValue, @Cast("long*") @ByRef int[] flags, @Cast("long*") @ByRef int[] defaultValue);

		//bool setVideoSettingCam(int deviceID, long Property, long lValue, long Flags = NULL, bool useDefaultValue = false);

		//get width, height and number of pixels
		public native int getWidth(int deviceID);
		public native int getHeight(int deviceID);
		public native int getSize(int deviceID);

		//completely stops and frees a device
		public native void stopDevice(int deviceID);

		//as above but then sets it up with same settings
		public native @Cast("bool") boolean restartDevice(int deviceID);

		//number of devices available
		public native int devicesFound(); public native videoInput devicesFound(int devicesFound);

		public native @Cast("long") int propBrightness(); public native videoInput propBrightness(int propBrightness);
		public native @Cast("long") int propContrast(); public native videoInput propContrast(int propContrast);
		public native @Cast("long") int propHue(); public native videoInput propHue(int propHue);
		public native @Cast("long") int propSaturation(); public native videoInput propSaturation(int propSaturation);
		public native @Cast("long") int propSharpness(); public native videoInput propSharpness(int propSharpness);
		public native @Cast("long") int propGamma(); public native videoInput propGamma(int propGamma);
		public native @Cast("long") int propColorEnable(); public native videoInput propColorEnable(int propColorEnable);
		public native @Cast("long") int propWhiteBalance(); public native videoInput propWhiteBalance(int propWhiteBalance);
		public native @Cast("long") int propBacklightCompensation(); public native videoInput propBacklightCompensation(int propBacklightCompensation);
		public native @Cast("long") int propGain(); public native videoInput propGain(int propGain);

		public native @Cast("long") int propPan(); public native videoInput propPan(int propPan);
		public native @Cast("long") int propTilt(); public native videoInput propTilt(int propTilt);
		public native @Cast("long") int propRoll(); public native videoInput propRoll(int propRoll);
		public native @Cast("long") int propZoom(); public native videoInput propZoom(int propZoom);
		public native @Cast("long") int propExposure(); public native videoInput propExposure(int propExposure);
		public native @Cast("long") int propIris(); public native videoInput propIris(int propIris);
		public native @Cast("long") int propFocus(); public native videoInput propFocus(int propFocus);

}

//  #endif


}
