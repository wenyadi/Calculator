package main;

import javax.swing.JFrame;

import main.audio.AudioControl;
import main.audio.AudioPresentation;
import main.visual.VisualPresentation;
import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.recognizer.Recognizer.State;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;

/**
 * 
 */
 
public class MainControl {

	public static void main(String[] args) {

		@SuppressWarnings("unused")
		AudioControl audioControl;

		VisualPresentation window = new VisualPresentation();
		AudioPresentation audio = new AudioPresentation();

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("Performs Calculation");

		ConfigurationManager cm;
		if (args.length > 0) {
			cm = new ConfigurationManager(args[0]);
		} else {
			cm = new ConfigurationManager(AudioControl.class.getResource("/main/config/control.config.xml"));
		}

		Recognizer recognizer = (Recognizer) cm.lookup("recognizer");
		recognizer.allocate();

		// start the microphone or exit if the programm if this is not possible
		Microphone microphone = (Microphone) cm.lookup("microphone");
		if (!microphone.startRecording()) {
			System.out.println("Cannot start microphone.");
			recognizer.deallocate();
			System.exit(1);
		}

		String greeting = "Welcome to use Tric Speech Calculator. \n" + "Start Speech recognition. Please Choose mode: "
				+ "VOICE MODE or GRAPHIC MODE or PLEASE QUIT";

		int greetingCount = 0;

		// loop the recognition until the programm exits.
		while (recognizer.getState() == State.READY) {
			if (greetingCount < 1) {
				System.out.println(greeting);
				audio.Speak(greeting);
				window.getTextField().setText(greeting);
				greetingCount++;
			}
			Result result = recognizer.recognize();

			if (result != null) {
				String resultText = result.getBestFinalResultNoFiller();
				System.out.println("You said: " + resultText + '\n');
				if (resultText.equalsIgnoreCase("graphic mode")) {
					microphone.stopRecording();
					recognizer.deallocate();
				} else if (resultText.equalsIgnoreCase("voice mode")) {
					microphone.stopRecording();
					recognizer.deallocate();
					audioControl = new AudioControl(window);
				} else if (resultText.equalsIgnoreCase("please quit")) {
					System.exit(0);
				} else {
					System.err.println("I didn't hear what you said. \n");
				}
			} else {
				System.out.println("I can't hear what you said.\n");
			}
		}

	}
}