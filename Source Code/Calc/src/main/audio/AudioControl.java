package main.audio;

import javax.swing.JTextArea;
import javax.swing.JTextField;
 
import main.visual.VisualPresentation;
import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;

public class AudioControl {
	JTextArea textArea = new JTextArea();
	JTextField textField = new JTextField();
	AudioAbstraction audioAbstraction = new AudioAbstraction();
	AudioPresentation audioPresentation = new AudioPresentation();

	public AudioControl(VisualPresentation window) {
		Recognizer recognizer = new Recognizer();
		this.textArea = window.getTextArea();
		this.textField = window.getTextField();

		ConfigurationManager cm;
		cm = new ConfigurationManager(AudioControl.class.getResource("/main/config/calculator.config.xml"));

		recognizer = (Recognizer) cm.lookup("recognizer");
		recognizer.allocate();

		// start the microphone or exit if the programm if this is not possible
		Microphone microphone = (Microphone) cm.lookup("microphone");
		if (!microphone.startRecording()) {
			System.out.println("Cannot start microphone.");
			recognizer.deallocate();
			System.exit(1);
		}

		// loop the recognition until the programm exits.
		while (true) {

			System.out.println("Start speaking.\n");
			System.out.println("Say: <Operation> <digit>");

			Result result = recognizer.recognize();

			if (result != null) {
				String resultText = result.getBestFinalResultNoFiller();
				System.out.println("You said: " + resultText + '\n');
				textField.setText("You said: " + resultText + '\n');
				audioPresentation.Speak("You said " + resultText);

				if (resultText.equalsIgnoreCase("cancel")) {
					textArea.setText("");
				} else if (resultText.equalsIgnoreCase("clear")) {
					textArea.setText("");
				} else if (resultText.equalsIgnoreCase("please quit")) {
					System.out.println("please quit");
					System.exit(0);
				} else if (!textArea.getText().contains("=")) {
					if (resultText.equalsIgnoreCase("delete")) {
						try {
							textArea.setText(textArea.getText().substring(0, textArea.getText().length() - 1));
						} catch (StringIndexOutOfBoundsException exception) {
						}
					} else if (resultText.equalsIgnoreCase("plus")) {
						textArea.append("+");
					} else if (resultText.equalsIgnoreCase("minus") || resultText.equalsIgnoreCase("subtract")
							|| resultText.equalsIgnoreCase("negative")) {
						textArea.append("-");
					} else if (resultText.equalsIgnoreCase("times") || resultText.equalsIgnoreCase("multiply")) {
						textArea.append("*");
					} else if (resultText.equalsIgnoreCase("power of")) {
						textArea.append("^");
					} else if (resultText.equalsIgnoreCase("left parentheses") || resultText.equalsIgnoreCase("open parentheses")) {
						textArea.append("(");
					} else if (resultText.equalsIgnoreCase("right parentheses") || resultText.equalsIgnoreCase("close parentheses")) {
						textArea.append(")");
					} else if (resultText.equalsIgnoreCase("equal")) {
						String answer = null;
						if (textArea.getText().trim().length() > 0) {
							answer = audioAbstraction.eval(textArea.getText());
							textArea.append("=");
							audioPresentation.Speak(textArea.getText());
							textArea.append(answer);
							audioPresentation.Speak(answer.replaceAll("\\-", "negative").replaceAll("\\.0", ""));
						}
						textField.setText("$$ " + textArea.getText().replaceAll("\\^(\\-?\\d+)", "^ { $1 }") + " $$");
						// TODO convert to TEX
					} else {
						textArea.append(audioAbstraction.parser(resultText));
					}
				}
			} else {
				System.err.println("I can't hear what you said.\n");
			}
		}

	}

}
