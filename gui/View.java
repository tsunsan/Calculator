package gui;

import exception.MixedFractionException;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;

/**
 * The View class represents the graphical user interface (GUI) for the Fraction Calculator application.
 * It extends the JFrame class and includes methods to create and manage the UI components.
 *
 * @author Junsel Fabe
 * @version 1
 */
public class View extends JFrame {

    Controller controller;
    private ImageTextField inputField;

    /**
     * Sets the controller instance for the View.
     *
     * @param controller The controller instance to be set.
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Displays the Fraction Calculator UI.
     *
     * @throws IOException         If an I/O error occurs.
     * @throws FontFormatException If an error occurs during font formatting.
     */
    public void showUI() throws IOException, FontFormatException {
        controller.view.createJFrame();
    }

    /**
     * Creates and displays the JFrame for the Fraction Calculator UI.
     *
     * @throws IOException           If an I/O error occurs.
     * @throws FontFormatException   If an error occurs during font formatting.
     */
    public void createJFrame() throws IOException, FontFormatException {
        JFrame jFrame = new JFrame();
        jFrame.setTitle("Fraction Calculator");
        ImageIcon icoImage = createScaledImageIcon("/assets/pics/icon.png");
        jFrame.setIconImage(icoImage.getImage());
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setContentPane(contentPane());
        jFrame.pack();
        jFrame.setMinimumSize(new Dimension(311, 431));
        jFrame.setResizable(false);
        jFrame.setSize(311, 431);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    /**
     * Creates the content pane for the Fraction Calculator UI.
     *
     * @return The JPanel representing the content pane.
     * @throws IOException           If an I/O error occurs.
     * @throws FontFormatException   If an error occurs during font formatting.
     */
    public JPanel contentPane() throws IOException, FontFormatException {
        JPanel contentPane = new JPanel(new BorderLayout(5, 5));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        JTextField inputField = inputField();
        JPanel buttonPanel = buttonPanel();
        contentPane.add(inputField, BorderLayout.NORTH);
        contentPane.add(buttonPanel, BorderLayout.CENTER);
        setContentPane(contentPane);
        return contentPane;
    }

    /**
     * Creates the input field for the Fraction Calculator UI.
     *
     * @return The JTextField representing the input field.
     * @throws IOException           If an I/O error occurs.
     * @throws FontFormatException   If an error occurs during font formatting.
     */
    public JTextField inputField() throws IOException, FontFormatException {
        ImageIcon javaImage = createScaledImageIcon("/assets/pics/audio.png");
        inputField = new ImageTextField(javaImage);
        inputField.setEditable(false);
        inputField.setDocument((new LengthRestrictedDocument(15)));
        InputStream is = getClass().getResourceAsStream("/assets/fonts/digital-7.ttf");
        Font calculatorFont = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(is)).deriveFont(40f);
        inputField.setFont(calculatorFont);
        inputField.setBackground(new Color(212, 226, 227));
        inputField.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(10, 10, 10, 10)));
        inputField.setHorizontalAlignment(JTextField.RIGHT);
        return inputField;
    }

    /**
     * Creates the button panel for the Fraction Calculator UI.
     *
     * @return The JPanel representing the button panel.
     */
    public JPanel buttonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 5, 5));
        buttonPanel.setPreferredSize(new Dimension(325, 300));
        addButtonsToPanel(buttonPanel);
        return buttonPanel;
    }

    /**
     * Adds buttons to the specified button panel.
     *
     * @param buttonPanel The JPanel to which buttons will be added.
     */
    public void addButtonsToPanel(JPanel buttonPanel) {
        ImageIcon offImage = createScaledImageIcon("/assets/pics/switch-off.png");
        ImageIcon onImage = createScaledImageIcon("/assets/pics/switch-on.png");
        ImageIcon onVoice = createScaledImageIcon("/assets/pics/audio.png");
        ImageIcon offVoice = createScaledImageIcon("/assets/pics/no-audio.png");
        JToggleButton powerButton = new JToggleButton(offImage, false);
        JToggleButton voiceButton = new JToggleButton(onVoice, false);
        final Boolean[] checkSound = new Boolean[1];
        checkSound[0] = true;


        for (AbstractButton button : Arrays.asList(
                new JButton("7"), new JButton("8"), new JButton("9"), new JButton("÷"),
                new JButton("4"), new JButton("5"), new JButton("6"), new JButton("*"),
                new JButton("1"), new JButton("2"), new JButton("3"), new JButton("-"),
                new JButton("0"), new JButton("C"), new JButton("="), new JButton("+"),
                powerButton, voiceButton, new JButton("←"),
                new JButton("x" + diagonalFraction(1, 2))
        )) {
            button.setFont(new Font("Arial Unicode MS", Font.BOLD, 20));
            button.setForeground(Color.white);

            if (button.getText().matches("[+\\-*÷]")) {
                MyDocumentListener myDocumentListener = new MyDocumentListener((JButton) button, inputField, "[+\\-*÷]");
                inputField.getDocument().addDocumentListener(myDocumentListener);
            }

            if (button.getText().equals("=")) {
                MyDocumentListener myDocumentListener = new MyDocumentListener((JButton) button, inputField, "=");
                inputField.getDocument().addDocumentListener(myDocumentListener);
            }

            if (button.getText().equals("x" + diagonalFraction(1, 2))) {
                MyDocumentListener myDocumentListener = new MyDocumentListener((JButton) button, inputField, "x" + diagonalFraction(1, 2));
                inputField.getDocument().addDocumentListener(myDocumentListener);
            }

            if (button.getText().matches("C")) {
                MyDocumentListener myDocumentListener = new MyDocumentListener((JButton) button, inputField, "C");
                inputField.getDocument().addDocumentListener(myDocumentListener);
            }

            if (button.getText().matches("←")) {
                MyDocumentListener myDocumentListener = new MyDocumentListener((JButton) button, inputField, "←");
                inputField.getDocument().addDocumentListener(myDocumentListener);
            }

            if (button.equals(voiceButton)) {
                voiceButton.addItemListener(e -> {
                    if (voiceButton.isSelected()) {
                        playSound("/assets/sounds/speaker-click.wav", checkSound[0]);
                        turnOffVoice();
                        checkSound[0] = false;
                    } else {
                        turnOnVoice();
                        checkSound[0] = true;
                        playSound("/assets/sounds/speaker-click.wav", true);
                    }
                });
            }

            if (!(button instanceof JToggleButton)) {
                button.addActionListener(e -> {
                    String command = button.getText();
                    if (command.matches("[0-9]+|[+\\-*÷]")) {
                        if (command.equals("÷")) {
                            command = "/";
                        }
                        setResult(inputField.getText() + command);
                        playSound("/assets/sounds/button-click.wav", checkSound[0]);
                    } else if (command.equals("=")) {


                        if (inputField.getText().contains("⁄")) {
                            try {
                                controller.handleFraction(inputField.getText());
                                playSound("/assets/sounds/result-click.wav", checkSound[0]);
                            } catch (MixedFractionException exception) {
                                setResult(exception.getMessage());
                                playSound("/assets/sounds/error-click.wav", checkSound[0]);
                            }
                        } else {
                            try {
                                controller.handleCalculation(inputField.getText());
                                playSound("/assets/sounds/result-click.wav", checkSound[0]);
                            } catch (MixedFractionException exception) {
                                setResult(exception.getMessage());
                                playSound("/assets/sounds/error-click.wav", checkSound[0]);
                            }
                        }

                    } else if (command.equals("C")) {
                        setResult("");
                        playSound("/assets/sounds/clear-click.wav", checkSound[0]);
                    }
                });
            }

            if (button.getText().equals("←")) {
                button.addActionListener(e -> {
                    String textFieldText = inputField.getText();
                    char[] chars = textFieldText.toCharArray();
                    if (chars.length > 0) {
                        StringBuilder sb = new StringBuilder(textFieldText);
                        sb.deleteCharAt(chars.length - 1);
                        setResult(sb.toString());
                        playSound("/assets/sounds/back-click.wav", checkSound[0]);
                    }
                });
            }


            if (button.getText().equals("x" + diagonalFraction(1, 2))) {
                button.addActionListener(e -> {
                    playSound("/assets/sounds/fraction-click.wav", checkSound[0]);
                    String[] options = {"OK", "Cancel"};
                    CustomJOptionPane customJOptionPane = null;
                    try {
                        customJOptionPane = new CustomJOptionPane();
                        String numeratorStr = customJOptionPane.showInputDialog(null, "Enter the numerator:", "Fraction", options);
                        playSound("/assets/sounds/fraction-click.wav", checkSound[0]);
                        String denominatorStr = customJOptionPane.showInputDialog(null, "Enter the denominator:", "Fraction", options);
                        int numerator = Integer.parseInt(numeratorStr);
                        int denominator = Integer.parseInt(denominatorStr);
                        if (denominator == 0) {
                            Objects.requireNonNull(customJOptionPane).showMessageDialog(null, "Denominator cannot be zero!", "Error");
                            playSound("/assets/sounds/error-click.wav", checkSound[0]);
                        }
                        Font inputFieldFont = new Font("Arial Unicode MS", Font.PLAIN, 40);
                        inputField.setFont(inputFieldFont);
                        setResult(inputField.getText() + diagonalFraction(numerator, denominator));
                    } catch (NumberFormatException exception) {
                        playSound("/assets/sounds/error-click.wav", checkSound[0]);
                        Objects.requireNonNull(customJOptionPane).showMessageDialog(null, "Only digits are allowed!", "Error");
                    } catch (IndexOutOfBoundsException exception) {
                        playSound("/assets/sounds/error-click.wav", checkSound[0]);
                        Objects.requireNonNull(customJOptionPane).showMessageDialog(null, "Please input both numerator and denominator!", "Error");
                    }
                });
            }

            if (button.equals(powerButton)) {
                powerButton.addItemListener(e -> {
                    if (powerButton.isSelected()) {
                        playSound("/assets/sounds/off-click.wav", checkSound[0]);
                        enableButtons(buttonPanel);
                        setResult("");
                    } else {
                        playSound("/assets/sounds/on-click.wav", checkSound[0]);
                        disableButtons(buttonPanel);
                        Timer timer = new Timer(35, new ActionListener() {
                            private float alpha = 1.0f;

                            @Override
                            public void actionPerformed(ActionEvent e) {
                                alpha -= 0.1f;
                                if (alpha <= 0) {
                                    ((Timer) e.getSource()).stop();
                                    inputField.setForeground(null);
                                    setResult("OFF");
                                } else {
                                    inputField.setForeground(new Color(inputField.getForeground().getRed(), inputField.getForeground().getGreen(), inputField.getForeground().getBlue(), (int) (alpha * 255)));
                                }
                            }
                        });
                        timer.start();
                    }
                });
            } else {
                button.setEnabled(false);
            }

            if (button.getText().equals("=") || button.getText().equals("C")) {
                button.setBackground(new Color(0xE65100));
            } else if (button.getText().equals("+") || button.getText().equals("-") || button.getText().equals("*") || button.getText().equals("÷")) {
                button.setBackground(new Color(243, 243, 241));
                button.setForeground(Color.BLACK);
            } else if (button.getText().equals("+/-") || button.getText().equals(("x" + diagonalFraction(1, 2)))) {
                button.setBackground(new Color(0x9E9E9E));
            } else if (button.getText().equals("←")) {
                button.setBackground(new Color(175, 67, 76));
            } else if (button.getIcon() != null && button.getIcon().equals(offImage)) {
                button.setBackground(new Color(231, 139, 73));
                button.setSelectedIcon(onImage);
                setResult("OFF");
            } else if (button.getIcon() != null && button.getIcon().equals(onVoice)) {
                button.setBackground(new Color(27, 58, 139));
                button.setSelectedIcon(offVoice);
            } else if (button.getText().matches("[0-9]")) {
                button.setBackground(new Color(0x424242));
            }

            button.setPreferredSize(new Dimension(55, 55));
            button.setUI(new StyledButtonUI());
            buttonPanel.add(button);
        }
    }

    /**
     * Turns off the voice icon in the input field.
     */
    private void turnOffVoice() {
        ImageIcon icon = createScaledImageIcon("/assets/pics/no-audio.png");
        inputField.setImage(icon);
        inputField.revalidate();
        inputField.repaint();
    }

    /**
     * Turns on the voice icon in the input field.
     */
    private void turnOnVoice() {
        ImageIcon icon = createScaledImageIcon("/assets/pics/audio.png");
        inputField.setImage(icon);
        inputField.revalidate();
        inputField.repaint();
    }

    /**
     * Disables buttons in the specified panel.
     *
     * @param panel The JPanel containing buttons to be disabled.
     */
    private void disableButtons(JPanel panel) {
        for (Component comp : panel.getComponents()) {
            AbstractButton button = (AbstractButton) comp;
            if (comp instanceof JToggleButton) {
                button.setEnabled(true);
            } else if (comp != null) {
                button.setEnabled(false);
            }
        }
    }

    /**
     * Enables buttons in the specified panel.
     *
     * @param panel The JPanel containing buttons to be enabled.
     */
    private void enableButtons(JPanel panel) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof AbstractButton button) {
                button.setEnabled(true);
            }
        }
    }

    /**
     * Plays a sound specified by the file path.
     *
     * @param soundFilePath The file path of the sound file.
     * @param checkSound    A boolean indicating whether sound is enabled.
     */
    private void playSound(String soundFilePath, Boolean checkSound) {
        if (checkSound) {
            try {
                InputStream inputStream = getClass().getResourceAsStream(soundFilePath);
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(inputStream));
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
                System.out.println("Error playing sound file: " + ex.getMessage());
            }
        }
    }

    /**
     * Generates a formatted string representation of a diagonal fraction.
     *
     * @param numerator   The numerator of the fraction.
     * @param denominator The denominator of the fraction.
     * @return The formatted string representation of the diagonal fraction.
     */
    public String diagonalFraction(int numerator, int denominator) {
        char[] numeratorDigits = new char[]{
                '⁰', '¹', '²', '³', '⁴',
                '⁵', '⁶', '⁷', '⁸', '⁹'};
        char[] denominatorDigits = new char[]{
                '₀', '₁', '₂', '₃', '₄',
                '₅', '₆', '₇', '₈', '₉'};
        char fractionSlash = '⁄';

        if (denominator == 0) {
            return "";
        }

        if (numerator == 0) {
            return "0";
        }
        StringBuilder numeratorStr = new StringBuilder();
        while (numerator > 0) {
            numeratorStr.insert(0, numeratorDigits[numerator % 10]);
            numerator = numerator / 10;
        }
        StringBuilder denominatorStr = new StringBuilder();
        while (denominator > 0) {
            denominatorStr.insert(0, denominatorDigits[denominator % 10]);
            denominator = denominator / 10;
        }
        return " " + numeratorStr + fractionSlash + denominatorStr;
    }

    /**
     * Creates a scaled ImageIcon from the specified image path.
     *
     * @param path The path to the image file.
     * @return The scaled ImageIcon.
     */
    public ImageIcon createScaledImageIcon(String path) {
        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource(path)));
        Image scaledImg = image.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }

    /**
     * Sets the result in the input field.
     *
     * @param res The result to be set in the input field.
     */
    public void setResult(String res) {
        this.inputField.setText(res);
    }

}

/**
 * The ImageTextField class extends JTextField and includes an ImageIcon for displaying images within the text field.
 */
class ImageTextField extends JTextField {
    private ImageIcon imageIcon;

    /**
     * Creates an ImageTextField with the specified ImageIcon.
     *
     * @param icon The ImageIcon to be displayed in the text field.
     */
    public ImageTextField(ImageIcon icon) {
        super();
        this.imageIcon = icon;
    }

    /**
     * Paints the component, including the ImageIcon.
     *
     * @param g The Graphics object.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int imageX = 5;
        int imageY = (getHeight() - imageIcon.getIconHeight()) / 2;
        imageIcon.paintIcon(this, g, imageX, imageY);
    }

    /**
     * Sets the ImageIcon for the ImageTextField.
     *
     * @param icon The ImageIcon to be set.
     */
    public void setImage(ImageIcon icon) {
        this.imageIcon = icon;
    }

}

/**
 * The StyledButtonUI class extends BasicButtonUI and provides a custom appearance for buttons.
 */
class StyledButtonUI extends BasicButtonUI {

    /**
     * Installs the UI for the specified component.
     *
     * @param c The component for which to install the UI.
     */
    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        AbstractButton button = (AbstractButton) c;
        button.setOpaque(false);
        button.setBorder(new EmptyBorder(5, 15, 5, 15));
    }

    /**
     * Paints the button component with a custom background.
     *
     * @param g The Graphics object.
     * @param c The component to be painted.
     */
    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        paintBackground(g, b, b.getModel().isPressed() ? 2 : 0);
        super.paint(g, c);
    }

    /**
     * Paints the background of the button with rounded corners.
     *
     * @param g        The Graphics2D object.
     * @param c        The button component.
     * @param yOffset  The y-offset for painting.
     */
    private void paintBackground(Graphics g, JComponent c, int yOffset) {
        Dimension size = c.getSize();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (c instanceof JToggleButton) {
            yOffset += 2;
        }

        g.setColor(c.getBackground().darker());
        g.fillRoundRect(0, yOffset, size.width, size.height - yOffset, 10, 10);
        g.setColor(c.getBackground());
        g.fillRoundRect(0, yOffset, size.width, size.height + yOffset - 5, 10, 10);
    }
}

/**
 * The LengthRestrictedDocument class extends PlainDocument and restricts the length of the document.
 */
class LengthRestrictedDocument extends PlainDocument {

    private final int limit;

    /**
     * Creates a LengthRestrictedDocument with the specified character limit.
     *
     * @param limit The character limit for the document.
     */
    public LengthRestrictedDocument(int limit) {
        this.limit = limit;
    }

    /**
     * Inserts the specified string into the document, respecting the character limit.
     *
     * @param offs The offset at which to insert the string.
     * @param str  The string to be inserted.
     * @param a    The AttributeSet.
     * @throws BadLocationException If the insertion location is invalid.
     */
    @Override
    public void insertString(int offs, String str, AttributeSet a)
            throws BadLocationException {
        if (str == null)
            return;

        if ((getLength() + str.length()) <= limit) {
            super.insertString(offs, str, a);
        }
    }
}

/**
 * The CustomJOptionPane class provides custom input and message dialog boxes with specific styling and functionality.
 */
class CustomJOptionPane {

    /**
     * Creates a scaled ImageIcon from the specified image path.
     *
     * @param path The path to the image file.
     * @return The scaled ImageIcon.
     */
    public ImageIcon createScaledImageIcon(String path) {
        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource(path)));
        Image scaledImg = image.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }

    /**
     * Displays an input dialog box with a message, title, and custom options.
     *
     * @param parentComponent The parent component for the dialog box.
     * @param message         The message to be displayed.
     * @param title           The title of the dialog box.
     * @param options         The array of custom options.
     * @return The user's input from the dialog box.
     */
    public String showInputDialog(Component parentComponent, String message, String title, String[] options) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(message);
        JTextField textField = new JTextField(10);
        textField.setDocument((new LengthRestrictedDocument(4)));

        ImageIcon icoImage = createScaledImageIcon("/assets/pics/icon.png");
        JLabel iconLabel = new JLabel(icoImage);

        panel.add(iconLabel);
        panel.add(label);
        panel.add(textField);

        final Object[][] returnValue = new Object[1][1];

        JButton[] buttons = new JButton[options.length];
        for (int i = 0; i < options.length; i++) {
            final int option = i;
            buttons[i] = new JButton(options[i]);
            if (i == 0) {
                buttons[i].setBackground(new Color(78, 135, 82));
            } else {
                buttons[i].setBackground(new Color(227, 82, 82));
            }
            buttons[i].setUI(new StyledButtonUI());
            buttons[i].addActionListener(e -> {
                String text = textField.getText();
                JOptionPane.getRootFrame().dispose();
                returnValue[0] = new Object[]{option, text};
            });
        }

        JOptionPane.showOptionDialog(parentComponent, panel, title, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[0]);

        if (returnValue[0] == null) {
            return null;
        } else {
            Object[] selectedValues = returnValue[0];
            return (String) selectedValues[1];
        }
    }

    /**
     * Displays a message dialog box with a message, title, and an "Okay" button.
     *
     * @param parentComponent The parent component for the dialog box.
     * @param message         The message to be displayed.
     * @param title           The title of the dialog box.
     */
    public void showMessageDialog(Component parentComponent, String message, String title) {
        JPanel panel = new JPanel();
        JLabel messageLabel = new JLabel(message);

        ImageIcon icon = createScaledImageIcon("/assets/pics/icon.png");
        JLabel iconLabel = new JLabel(icon);

        panel.add(iconLabel);
        panel.add(messageLabel);

        JButton button = new JButton("Okay");
        button.addActionListener(e -> JOptionPane.getRootFrame().dispose());
        button.setUI(new StyledButtonUI());
        button.setPreferredSize(new Dimension(75, 25));
        button.setBackground(new Color(227, 82, 82));

        JButton[] options = {button};
        JOptionPane.showOptionDialog(parentComponent, panel, title, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, -1);

    }

}

/**
 * The MyDocumentListener class implements the DocumentListener interface to track changes in a document (e.g., JTextField).
 */
class MyDocumentListener implements DocumentListener {
    private final JButton button;
    private final JTextField inputField;
    private final String buttonText;

    /**
     * Creates a document listener with the specified button, input field, and button text.
     *
     * @param button     The button associated with the document listener.
     * @param inputField The input field associated with the document listener.
     * @param buttonText The text displayed on the button associated with the document listener.
     */
    public MyDocumentListener(JButton button, JTextField inputField, String buttonText) {
        this.button = button;
        this.inputField = inputField;
        this.buttonText = buttonText;
    }

    /**
     * Called when text is inserted into the document.
     *
     * @param e The DocumentEvent representing the change.
     */
    @Override
    public void insertUpdate(DocumentEvent e) {
        checkButtonEnabled();
    }

    /**
     * Called when text is removed from the document.
     *
     * @param e The DocumentEvent representing the change.
     */
    @Override
    public void removeUpdate(DocumentEvent e) {
        checkButtonEnabled();
    }

    /**
     * Called when attributes of the document change.
     *
     * @param e The DocumentEvent representing the change.
     */
    @Override
    public void changedUpdate(DocumentEvent e) {
        checkButtonEnabled();
    }

    /**
     * Checks the state of the associated button based on the content of the input field.
     */
    private void checkButtonEnabled() {
        View view = new View();
        String text = inputField.getText();
        char lastChar = text.length() > 0 ? text.charAt(text.length() - 1) : 'F';
        if (buttonText.equals("C")) {
            button.setEnabled(text.length() > 1 && lastChar != 'F');
        } else if (buttonText.equals("=")) {
            button.setEnabled(text.contains("+") || text.contains("-") || text.contains("/") || text.contains("*"));
        } else if (buttonText.equals("←")) {
            button.setEnabled(text.length() > 0 && lastChar != 'F');
        } else if (buttonText.equals("x" + view.diagonalFraction(1, 2))) {
            String s = Character.toString(lastChar);
            button.setEnabled(!s.matches("[₀-₉]"));
        } else {
            button.setEnabled(lastChar != '+' && lastChar != '-' && lastChar != '*' && lastChar != '/' && lastChar != 'F');
        }
    }
}





