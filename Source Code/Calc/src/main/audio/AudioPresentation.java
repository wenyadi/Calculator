package main.audio;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
 
public class AudioPresentation {

	/**
	 * Example of how to list all the known voices.
	 */
	public void Speak(String input) {

		String voiceName = "kevin16";

		System.out.println();
		System.out.println("Using voice: " + voiceName);

		/*
		 * The VoiceManager manages all the voices for FreeTTS.
		 */
		VoiceManager voiceManager = VoiceManager.getInstance();
		Voice helloVoice = voiceManager.getVoice(voiceName);

		if (helloVoice == null) {
			System.err.println("Cannot find a voice named " + voiceName + ".  Please specify a different voice.");
			System.exit(1);
		}

		/*
		 * Allocates the resources for the voice.
		 */
		helloVoice.allocate();

		// Get current size of heap in bytes
		long heapSize = Runtime.getRuntime().totalMemory();
		System.out.println("heap size: " + heapSize);
		// Get maximum size of heap in bytes. The heap cannot grow beyond this
		// size.// Any attempt will result in an OutOfMemoryException.
		long heapMaxSize = Runtime.getRuntime().maxMemory();
		System.out.println("heap MaxSize: " + heapMaxSize);
		// Get amount of free memory within the heap in bytes. This size will
		// increase // after garbage collection and decrease as new objects are
		// created.
		long heapFreeSize = Runtime.getRuntime().freeMemory();
		System.out.println("heap FreeSize: " + heapFreeSize);

		/*
		 * Synthesize speech.
		 */
		helloVoice.speak(input.replaceAll("\\^", "power of").replaceAll("\\*", "multiply by").replaceAll("\\-", "minus"));
		/*
		 * Clean up and leave.
		 */
		helloVoice.deallocate();

	}
}
