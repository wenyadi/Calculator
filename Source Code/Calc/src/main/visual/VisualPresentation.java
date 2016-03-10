package main.visual;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

public class VisualPresentation extends JFrame {

	/**
	 * A Class that operates as the framework for a calculator. No calculations
	 * are performed in this section
	 */

	private static final long serialVersionUID = 3039177149507123548L;

	private JTextArea textArea;
	private JTextField textField;
	VisualControl actionControl;

	/**
	 * Constructor for objects of class GridLayoutExample
	 */
	public VisualPresentation() {

		makeMenuBar(this);

		JPanel contentPane = (JPanel) this.getContentPane();
		contentPane.setLayout(new BorderLayout(8, 8));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));

		this.textArea = new JTextArea();
		this.textArea.setLineWrap(true);
		this.textField = new JTextField();
		
		this.actionControl = new VisualControl();

		/**
		 * Insert a text field and a Label
		 */
		this.actionControl.textFieldProcess(this.textArea);
		this.actionControl.setTextField(this.textField);

		contentPane.setLayout(new GridLayout(2, 2));

		JPanel numberButtonPanel = new JPanel(new GridLayout(4, 4));
		JPanel controlButtonPanel = new JPanel(new GridLayout(5, 4));

		List<JButton> numberButtons = new ArrayList<JButton>();
		List<JButton> controlButtons = new ArrayList<JButton>();

		numberButtons.add(new JButton("1"));
		numberButtons.add(new JButton("2"));
		numberButtons.add(new JButton("3"));
		numberButtons.add(new JButton("4"));

		numberButtons.add(new JButton("5"));
		numberButtons.add(new JButton("6"));
		numberButtons.add(new JButton("7"));
		numberButtons.add(new JButton("8"));

		numberButtons.add(new JButton("9"));
		numberButtons.add(new JButton("0"));
		numberButtons.add(new JButton("("));
		numberButtons.add(new JButton(")"));

		controlButtons.add(new JButton("+"));
		controlButtons.add(new JButton("-"));
		controlButtons.add(new JButton("*"));
		controlButtons.add(new JButton("^"));
		controlButtons.add(new JButton("="));

		controlButtons.add(new JButton("C"));
		controlButtons.add(new JButton("Del"));
		controlButtons.add(new JButton("Quit"));

		for (JButton button : numberButtons) {
			numberButtonPanel.add(button);
			button.addActionListener(this.actionControl);
		}

		for (JButton button : controlButtons) {
			controlButtonPanel.add(button);
			button.addActionListener(this.actionControl);
		}

		contentPane.add(this.textArea, BorderLayout.NORTH);
		contentPane.add(numberButtonPanel, BorderLayout.CENTER);
		contentPane.add(this.textField, BorderLayout.CENTER);
		contentPane.add(controlButtonPanel, BorderLayout.SOUTH);

		this.pack();
		this.setVisible(true);
	}

	/**
	 * Create the main frame's menu bar. The frame that the menu bar should be
	 * added to.
	 */
	private void makeMenuBar(JFrame frame) {
		final int SHORTCUT_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

		JMenuBar menubar = new JMenuBar();
		frame.setJMenuBar(menubar);

		JMenu menu;
		JMenuItem item;

		// create the File menu
		menu = new JMenu("File");
		menubar.add(menu);

		// create the Quit menu with a shortcut "Q" key.
		item = new JMenuItem("Quit");
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menu.add(item);

		// Adds an about menu.
		menu = new JMenu("About");
		menubar.add(menu);

		// Displays
		item = new JMenuItem("Calculator Project");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAbout();
			}
		});
		menu.add(item);

		item = new JMenuItem("Help");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showHelp();
			}
		});
		menu.add(item);

	}

	// Calls the dialog frame with the information about the project.
	private void showAbout() {
		JOptionPane.showMessageDialog(this, "Project", "About Calculator Project", JOptionPane.INFORMATION_MESSAGE);
	}

	String help = "Help" + "\n" + "Input with buttons or keyboard or voice any number from 0 - 999,999" + "\n"
			+ "Operation include * times/multiply  + plus - minus/negative ^ power of" + "\n"
			+ " ( - open parenthese   ) - close parentheses " + "\n" + "Thank You  " + "\n"
			+ "--------------------------------------------------------" + "\n";

	private void showHelp() {
		JOptionPane.showMessageDialog(this, help, "Help", JOptionPane.INFORMATION_MESSAGE);
	}

	public JTextArea getTextArea() {
		return this.textArea;
	}

	public JTextField getTextField() {
		return this.textField;
	}

	public void setTextField(String input) {
		this.textField.setText(input);

	}

}
 