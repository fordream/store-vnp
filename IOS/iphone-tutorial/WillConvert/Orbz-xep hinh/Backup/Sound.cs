/*
 * Created by SharpDevelop.
 * User: jroam
 * Date: 7/7/2004
 * Time: 3:58 PM
 *
 * System-wide errors are logged through the "Error Log" in the GameState class.
 * I always have some sort of central error handling "equipment" in my code...in this case
 * its quite rudimentary.  Anyways....
 * Sound.cs will simply log the errors there rather than throw exceptions - because if the sound
 * doesn't load then whatever...the game will still go on...
 * 
 * I use the MCI interface for MIDI files (since directly using the MIDI Mapper requires a lot of work)
 * but not for WAV files.  Therefore, only WAV files up to about 100k and not needing A3M(sp?) are supported because they are
 * loaded directly into memory and the WAV mapper doesn't support more on its own (MCI allows more...but I don't need it right now)
 * There's plenty of documentation on MSDN - though its all in C/C++ (you have to do more
 * unmanaged calls to use MCI for WAV too)
 * 
 * http://msdn.microsoft.com/library/default.asp?url=/library/en-us/multimed/htm/_win32_playing_a_midi_file.asp
 * they have good wave documentation if you search around too...
 * You may want to look into Threading with Unmanaged code too..
 * 
 * Jon Davis has a WAV project on PlanetSourceCode.com that was the first unmanaged code
 * I looked at before researching it on MSDN - I borrowed some lines from him to get started and
 * wanted to give props where deserved.
*/

using System;
using System.IO;
using System.Text;
using System.Threading;
using System.Runtime.InteropServices;
using System.Reflection;

namespace Orbz
{
	[ StructLayout( LayoutKind.Sequential, CharSet=CharSet.Ansi )]
	public struct MCI_OPEN_PARMS {
		public int dwCallback;
		public int wDeviceID;
		public string lpstrDeviceType;
		public string lpstrElementName;
		public string lpstrAlias;
	}
	[ StructLayout( LayoutKind.Sequential, CharSet=CharSet.Ansi )]
	public struct MCI_STATUS_PARMS {
		public int dwCallback;
		public int dwReturn;
		public int dwItem;
		public int dwTrack;
}
	[ StructLayout( LayoutKind.Sequential, CharSet=CharSet.Auto )]

	public class Sound
	{
		#region MIDI variables/.dll references
		//references
		[ DllImport( "winmm.dll", EntryPoint="mciSendCommandA", CharSet=CharSet.Ansi )]
		private static extern int mciSendCommand(int wDeviceID, int uMessage, int dwParam1, IntPtr dwParam2);
		[ DllImport( "winmm.dll", EntryPoint="mciSendCommandA", CharSet=CharSet.Ansi )]
		private static extern int mciSendCommandOpen(int wDeviceID, int uMessage, int dwParam1, ref MCI_OPEN_PARMS dwParam2);
		[ DllImport( "winmm.dll", EntryPoint="mciSendCommandA", CharSet=CharSet.Ansi )]
		private static extern int mciSendCommandStatus(int wDeviceID, int uMessage, int dwParam1, ref MCI_STATUS_PARMS dwParam2);
		
		//constants
		private const int MCI_WAIT = 2;
		private const int MCI_STATUS_MODE = 4;
		private const int MCI_STATUS_ITEM = 256;
		private const int MCI_STRING_OFFSET = 512;
		private const int MCI_OPEN_ELEMENT = 512;
		private const int MCI_OPEN = 2051;
		private const int MCI_CLOSE = 2052;
		private const int MCI_PLAY = 2054;
		private const int MCI_STOP = 2056;
		private const int MCI_STATUS = 2068;
		private const int MCI_OPEN_TYPE = 8192;
		private const int MCI_MODE_PLAY = (MCI_STRING_OFFSET + 14);
		
		//class info
		private static int deviceID;
		private static bool isOpen = false;
		private static bool isOpening = false;
		private static bool bAbort = false;
		private static bool bLoop = true;
		private static bool bResource = false;
		private static string fPath;
		private static string resource;
		private static Thread midiThread;
		#endregion
		
		#region WAV variables/.dll references
		[DllImport("winmm.dll")]
    	private static extern int sndPlaySoundA(string lpszSoundName, int uFlags);
    	[DllImport("winmm.dll")]
    	private static extern int PlaySound(byte[] pszSound, Int16 hMod, long fdwSound);
   		const int SND_ASYNC = 1;
    	const int SND_NODEFAULT = 2;
    	const int SND_MEMORY = 4;
    	const int SND_LOOP = 8;
    	const int SND_NOSTOP = 16;
		#endregion

		#region MIDI methods
		//launches midi thread
		public static void PlayMidi(string path, bool loop)
		{
			bLoop = loop;
			fPath = path;
			StopMidi();
			isOpening = true;
			
			midiThread = new Thread(new ThreadStart(MIDI));
			midiThread.IsBackground = true;
			midiThread.Start();
			bAbort = false;
			Thread.Sleep(0);
		}

		public static void PlayMidi(string src, string tempPath ,bool loop)
		{
			StopMidi();
			bResource = true;
			resource = src;
			PlayMidi(tempPath, loop);
		}

		//kills MIDI thread	
		public static void StopMidi()
		{
			while (isOpening) { 
				#region Bug Fix Info
				//bug fix (7/19/04):
				//issue: rapidly toggling pause state crashes sound
				//cause: not enough time between events for sound thread to fully open MIDI
				// device, causing Stop() to not execute and the following Play() to attempt
				// to use an already busy resource (ie: crash)
				//resolution: isOpening flag introduced - Stop() will wait for previous play calls that
				// have already been initiated to finish before attempting to halt sound thread.  Causes some
				// ugly hiccups if user goes nuts with pause buttons - but gets the job done.
				#endregion
			}
			if (isOpen) {
				bAbort = true;
				if (midiThread != null) 
					midiThread.Join();
			}
		}
		
		//tells whether the device is busy or not
		public static bool MidiActive()
		{
			return (isOpen || isOpening);
		}
		
		//thread that plays the music
		private static void MIDI()
		{
			try {
				//start playing
				if (bResource) {
					File.Delete(fPath);
					LoadResource( resource , fPath );
				}
				
				while (!bAbort) {
					if (!IsPlayingMidi()) {
						CloseMidiDevice();
						OpenMidiDevice();	
						if (mciSendCommand(deviceID, MCI_PLAY, 0, IntPtr.Zero) != 0)
							throw new Exception("File failed to play.");
						if (!bLoop) {
							while (!bAbort && IsPlayingMidi()) {Thread.Sleep(500);}
							break;
						}							
					}
				}
			}
			catch (Exception e) {
				GameState.AppendToLog(e.Message + e.StackTrace);	
				CloseMidiDevice();
			}
			finally {
				CloseMidiDevice();
				if (bResource) {
					File.Delete(fPath);
					bResource = false;
				}
				isOpen = false;
			}
		}
		
		//opens the midi device for playback
		private static void OpenMidiDevice() {
			try {
				MCI_OPEN_PARMS mciOpenParms = new MCI_OPEN_PARMS();
				mciOpenParms.lpstrDeviceType = "sequencer";
				mciOpenParms.lpstrElementName = fPath;
				if (mciSendCommandOpen(0, MCI_OPEN, MCI_OPEN_TYPE | MCI_OPEN_ELEMENT, ref mciOpenParms) != 0)
					throw new Exception("Could not open MIDI Device.");
				deviceID = mciOpenParms.wDeviceID;
				isOpen = true;
				isOpening = false;
			}
			catch (Exception e) {
				GameState.AppendToLog(e.Message + "\n" + e.StackTrace);	
			}
		}
		
		//closes the MIDI device and frees allocated memory
		private static void CloseMidiDevice() {
			try{
				if (isOpen) {
					mciSendCommand(deviceID, MCI_CLOSE, 0, IntPtr.Zero);
				}
			}
			catch (Exception e) {
				GameState.AppendToLog("Failure to close MIDI device\n" + e.StackTrace);
			}
		}
			
		//returns whether MIDI device is playing a MIDI file	
		private static bool IsPlayingMidi() {
			bool bResult = false;
			try {
				if (isOpen) {
					MCI_STATUS_PARMS mciStatusParms = new MCI_STATUS_PARMS();
					mciStatusParms.dwItem = MCI_STATUS_MODE;
					if (mciSendCommandStatus(deviceID, MCI_STATUS, MCI_WAIT | MCI_STATUS_ITEM, ref mciStatusParms) != 0)
						throw new Exception("Failed to determine device status.");
					if (mciStatusParms.dwReturn == MCI_MODE_PLAY)
						bResult = true;
				}
			}
			catch (Exception e) {
				GameState.AppendToLog(e.Message + "\n" + e.StackTrace);
				bResult = false;
			}
			
			return bResult;
		}
		#endregion

		#region WAV methods
		//plays a .wav file
		public static int PlayWavFromFile(string fPath, bool bSynchronous,
						bool bNoDefault, bool bLoop, bool bNoStop) 
		{
			int ops = 0;
			try {
				if (!System.IO.File.Exists(fPath)) {
					throw new System.IO.FileNotFoundException("Sound file doesn't exist.");
				}
				else {
					if (!bSynchronous)
						ops = SND_ASYNC;
					if (bNoDefault) 
						ops += SND_NODEFAULT;
					if (bLoop) 
						ops += SND_LOOP;
					if (bNoStop) 
						ops += SND_NOSTOP;
					return sndPlaySoundA(fPath, ops);
				}
			}
			catch (Exception e) {
				GameState.AppendToLog(e.Message + "\n" + e.StackTrace);
				return -1;
			}
		}
		
		//plays a .wav file when sent as a byte array
		public static int PlayByteArray(byte[] audio, bool bSynchronous,
						bool bNoDefault, bool bLoop, bool bNoStop) {
			int ops = SND_MEMORY;
			if (!bSynchronous) 
				ops += SND_ASYNC;
			if (bNoDefault) 
				ops += SND_NODEFAULT;
			if (bLoop) 
				ops += SND_LOOP;
			if (bNoStop) 
				ops += SND_NOSTOP;
			try {
				return PlaySound(audio, 0, ops);
			}
			catch (Exception e) {
				GameState.AppendToLog(e.Message + "\n" + e.StackTrace);
				return -1;
			}
    	}
    	
    	//plays a .wav from a stream
    	public static int PlayStream( System.IO.Stream audio, bool bSynchronous,
    	                                bool bNoDefault, bool bLoop, bool bNoStop) {
    	    try {
	   			byte[] a = new byte[audio.Length];
			
				for (int i=0; i<audio.Length; i++)
					a[i] = (byte)audio.ReadByte();
	
	    	    return PlayByteArray( a, bSynchronous, bNoDefault, bLoop, bNoStop );
    	    }
    	    catch (Exception e) {
    	    	GameState.AppendToLog(e.Message + "\n" + e.StackTrace);
    	    	return -1;
    	    }
    	}
		#endregion
	
		#region Multi-Use Functions
		//writes the contents of a stream to a file
		private static void WriteStreamToFile(Stream s, string fpath)
		{	
			try {
				File.Delete(fpath);
				FileStream fs = new FileStream(fpath, FileMode.Create);
				BinaryWriter bw = new BinaryWriter(fs);
				
				for (int i=0; i<s.Length; i++)
				{	
					bw.Write( (byte)s.ReadByte() );
				}
				
				bw.Close();
				fs.Close();
			}
			catch (Exception e) {
				GameState.AppendToLog("Could not write temporary midi.\n" + e.Message + e.StackTrace);
			}
		}
		
		//caches a resource to the disk
		private static void LoadResource( string src, string fpath )
		{
			WriteStreamToFile( Assembly.GetEntryAssembly().GetManifestResourceStream(src), fpath);
		}
		#endregion
	}
}
