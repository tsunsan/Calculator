package gui;

import exception.MixedFractionException;

import java.awt.*;
import java.io.IOException;

/**
 * The Controller class acts as the intermediary between the Model and View in the Fraction Calculator GUI application.
 * It manages user input, invokes necessary calculations, and updates the UI accordingly.
 *
 * @author Junsel Fabe
 * @version 1
 */
public class Controller {

    Model model;
    View view;

    /**
     * Constructs a new Controller with the specified Model and View.
     *
     * @param model The data model for the Fraction Calculator.
     * @param view  The graphical user interface for the Fraction Calculator.
     */
    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Handles the input of a converted fraction, performs calculations, and updates the UI with the result.
     *
     * @param converted The input string representing a fraction to be processed.
     * @throws MixedFractionException If a mixed fraction is encountered during calculation.
     */
    public void handleFraction(String converted) throws MixedFractionException {
        String result = this.model.calculateFraction(converted);
        String result2 = this.model.calculateProblem(result, 'x');
        double decimalFraction = Double.parseDouble(result2);
        result2 = this.model.decimalToMixedFraction(decimalFraction);
        this.view.setResult(result2);
    }

    /**
     * Handles the input of a mathematical problem, performs calculations, and updates the UI with the result.
     *
     * @param problem The input string representing a mathematical problem to be solved.
     * @throws MixedFractionException If a mixed fraction is encountered during calculation.
     */
    public void handleCalculation(String problem) throws MixedFractionException {
        String result = this.model.calculateProblem(problem, 'y');
        this.view.setResult(result);
    }

    /**
     * Starts the Fraction Calculator application, displaying the graphical user interface.
     *
     * @throws IOException         If an I/O error occurs while starting the application.
     * @throws FontFormatException If an error occurs while loading fonts for the UI.
     */
    public void start() throws IOException, FontFormatException {
        this.view.showUI();
    }

}
