package me.quentin.tp_qrpdf.views;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import me.quentin.tp_qrpdf.models.AlignedImage;
import me.quentin.tp_qrpdf.models.AlignedImage.Alignment;
import me.quentin.tp_qrpdf.models.DocumentGeneratorConfig;
import me.quentin.tp_qrpdf.views.components.ColorPicker;
import me.quentin.tp_qrpdf.views.components.FilePicker;
import me.quentin.tp_qrpdf.views.components.FontList;

/**
 * Main view of the application.
 */
public class MainView extends JFrame {

	private JPanel contentPane;
	private JTextField inputTextField;
	private JButton validateButton;
	private FontList fontList;
	private JLabel fontLabel;
	private JLabel fontColorLabel;
	private ColorPicker fontColorPicker;
	private ColorPicker qrForegroundColorPicker;
	private JLabel QrCodeForegroundLabel;
	private JLabel qrCodeBackgroundLabel;
	private ColorPicker qrBackgroundColorPicker;
	private JButton imagePickerButton;
	private JProgressBar importProgressBar;
	private JList<AlignedImage> additionalImagesList;
	private JScrollPane scrollPane;
	private JLabel lblAlignment;	
	private JComboBox<Alignment> alignmentComboBox;
	private DefaultListModel<AlignedImage> additionalImagesModel;
	
	public MainView() {		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 804, 412);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		inputTextField = new JTextField();
		inputTextField.setBounds(12, 12, 286, 26);
		contentPane.add(inputTextField);
		inputTextField.setColumns(10);

		validateButton = new JButton("Générer");
		validateButton.setBounds(12, 277, 286, 95);
		contentPane.add(validateButton);
		
		fontList = new FontList();
		fontList.setBounds(12, 113, 137, 26);
		contentPane.add(fontList);
		
		fontLabel = new JLabel("Font Style");
		fontLabel.setBounds(12, 84, 137, 17);
		contentPane.add(fontLabel);
		
		fontColorPicker = new ColorPicker(Color.BLACK);
		fontColorPicker.setBounds(12, 199, 137, 27);
		contentPane.add(fontColorPicker);
		
		fontColorLabel = new JLabel("Font color");
		fontColorLabel.setBounds(12, 170, 137, 17);
		contentPane.add(fontColorLabel);
		
		qrForegroundColorPicker = new ColorPicker(Color.BLACK);
		qrForegroundColorPicker.setBounds(161, 113, 137, 27);
		contentPane.add(qrForegroundColorPicker);
		
		QrCodeForegroundLabel = new JLabel("QR Code Foreground");
		QrCodeForegroundLabel.setBounds(161, 84, 137, 17);
		contentPane.add(QrCodeForegroundLabel);
		
		qrCodeBackgroundLabel = new JLabel("QR Code Background");
		qrCodeBackgroundLabel.setBounds(161, 170, 137, 17);
		contentPane.add(qrCodeBackgroundLabel);
		
		qrBackgroundColorPicker = new ColorPicker(Color.WHITE);
		qrBackgroundColorPicker.setBounds(161, 199, 137, 27);
		contentPane.add(qrBackgroundColorPicker);

		imagePickerButton = new FilePicker((picker, filepath) -> {
			System.out.println("Importing " + filepath);
			try {
				var image = picker.readFile((accumulator, totalSize) -> {
					importProgressBar.setValue((int) ((accumulator / totalSize) * 100));
				});
				System.out.println("Imported " + filepath);
				additionalImagesModel.addElement(new AlignedImage(image, Alignment.CENTER, filepath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		imagePickerButton.setBounds(342, 277, 286, 95);
		contentPane.add(imagePickerButton);
		
		importProgressBar = new JProgressBar();
		importProgressBar.setBounds(342, 251, 286, 14);
		contentPane.add(importProgressBar);
		
		additionalImagesList = new JList<>();
		additionalImagesList.setBounds(342, 12, 1, 1);
		additionalImagesModel = new DefaultListModel<>();
		additionalImagesList.setModel(additionalImagesModel);
		additionalImagesList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				var selected = additionalImagesList.getSelectedValue().getAlignment();
				alignmentComboBox.setSelectedItem(selected);
			}
		});
		
		scrollPane = new JScrollPane(additionalImagesList);
		scrollPane.setBounds(342, 12, 286, 227);
		contentPane.add(scrollPane);
		
		lblAlignment = new JLabel("Alignment");
		lblAlignment.setBounds(647, 12, 76, 17);
		contentPane.add(lblAlignment);
		
		alignmentComboBox = new JComboBox<>();
		for (var variant : Alignment.values()) {
			alignmentComboBox.addItem(variant);
		}
		
		alignmentComboBox.addItemListener(new ItemListener() {
			@Override
		    public void itemStateChanged(ItemEvent event) {
		       if (event.getStateChange() == ItemEvent.SELECTED) {
		    	   var image = additionalImagesList.getSelectedValue();
		    	   var alignment = (Alignment) alignmentComboBox.getSelectedItem();
		    	   image.setAlignment(alignment);
		       }
		    }       
		});
		
		alignmentComboBox.setBounds(646, 41, 95, 26);
		contentPane.add(alignmentComboBox);
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

	/**
	 * @return The Document Generation Config the user has set up.
	 * @throws IllegalArgumentException If the QR code background color is the same as the QR code foreground color.
	 */
	public DocumentGeneratorConfig getGenerationConfig() throws IllegalArgumentException {
		if (qrForegroundColorPicker.getColor() == qrBackgroundColorPicker.getColor())
			throw new IllegalArgumentException("QR code background color is the same as the QR code foreground color.");

		var iter = additionalImagesModel.elements().asIterator();
		var list = new ArrayList<AlignedImage>();
		iter.forEachRemaining(list::add);
		return new DocumentGeneratorConfig(fontList.getSelectedFont(), fontColorPicker.getColor(), qrForegroundColorPicker.getColor(), qrBackgroundColorPicker.getColor(), Alignment.CENTER, list);
	}
}
