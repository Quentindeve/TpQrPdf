package me.quentin.tp_qrpdf.views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;

/**
 * Main view of the application.
 */
public class MainView extends JFrame {

	private JPanel contentPane;
	private JTextField inputTextField;
	private JButton validateButton;

	public MainView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		inputTextField = new JTextField();
		inputTextField.setBounds(12, 12, 426, 62);
		contentPane.add(inputTextField);
		inputTextField.setColumns(10);

		validateButton = new JButton("Générer");
		validateButton.setBounds(12, 165, 426, 95);
		contentPane.add(validateButton);
	}

	/**
	 * Sets the event listener of the validation button
	 * 
	 * @param e The new event listener to use
	 */
	public void setButtonEventListener(MouseAdapter e) {
		this.validateButton.addMouseListener(e);
	}

	/**
	 * @return Text contained in the input field.
	 */
	public String getText() {
		return this.inputTextField.getText();
	}
}
