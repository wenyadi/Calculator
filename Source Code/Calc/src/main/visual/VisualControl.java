package main.visual;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import main.audio.AudioPresentation;

public class VisualControl implements ActionListener {
	JTextArea textArea = new JTextArea();
	JTextField textField = new JTextField();
	VisualAbstraction visualAbstraction = new VisualAbstraction();
	AudioPresentation audioPresentation = new AudioPresentation();

	public void textFieldProcess(final JTextArea textArea) {
		this.textArea = textArea;
		textArea.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				System.out.println("remove");
				String input = textArea.getText();
				System.out.println(input);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				System.out.println("insert");
				String input = textArea.getText();
				System.out.println(input);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				System.out.println("changed");
				String input = textArea.getText();
				System.out.println(input);
			}
		});
	}

	public void setTextField(JTextField input) {
		this.textField = input;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if (command.equals("0") || command.equals("1") || command.equals("2") || command.equals("3") || command.equals("4")
				|| command.equals("5") || command.equals("6") || command.equals("7") || command.equals("8") || command.equals("9")) {
			int number = Integer.parseInt(command);
			textArea.append(Integer.toString(number));
			audioPresentation.Speak(Integer.toString(number));
		} else if (command.equals("(")) {
			textArea.append("(");
			audioPresentation.Speak("Open parenthese");
		} else if (command.equals(")")) {
			textArea.append(")");
			audioPresentation.Speak("Close parenthese");
		} else if (command.equals("+")) {
			textArea.append("+");
			audioPresentation.Speak("plus");
		} else if (command.equals("-")) {
			textArea.append("-");
			audioPresentation.Speak("minus");
		} else if (command.equals("*")) {
			textArea.append("*");
			audioPresentation.Speak("multiply");
		} else if (command.equals("^")) {
			textArea.append("^");
			audioPresentation.Speak("power of");
		} else if (command.equals("=")) {
			String result = null;
			if (!textArea.getText().contains("=")) {
				if (textArea.getText().trim().length() > 0) {
					result = visualAbstraction.eval(textArea.getText());
					textArea.append("=");
					audioPresentation.Speak(textArea.getText());
					textArea.append(result);
					audioPresentation.Speak(result.replaceAll("\\-", "negative").replaceAll("\\.0", ""));
				}
				textField.setText("$$ " + textArea.getText().replaceAll("\\^(\\-?\\d+)", "^ { $1 }") + " $$");
			}

		} else if (command.equals("C")) {
			textArea.setText("");
		} else if (command.equals("Del")) {
			System.out.println("Del");
			audioPresentation.Speak("Delete");
			try {
				textArea.setText(textArea.getText().substring(0, textArea.getText().length() - 1));
			} catch (StringIndexOutOfBoundsException exception) {
			}
		} else if (command.equals("Quit")) {
			System.out.println("Quit");
			System.exit(0);
		} else {
			System.err.println("No such button");
		}
	}
}
 