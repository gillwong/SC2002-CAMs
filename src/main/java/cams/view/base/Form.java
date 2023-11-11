package cams.view.base;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Base class for all user interface elements which requires user input and runs
 * an action after retrieving them. This base class enables displaying a prompt
 * alongside several input fields which obtains user input. Specific user
 * interface components in this application like <code>LoginForm</code> are
 * implemented as extensions from this base class.
 * <p>
 * To use this base user interface element, extend from or construct this base
 * class, set the prompt and add the appropriate input fields, each with their
 * own label. The order of input fields added corresponds to the order in which
 * they are displayed in standard output.
 * <p>
 * Usage example by extension:
 * 
 * <pre>
 * {@code
 * public class SpecificForm extends Form {
 *     public SpecificForm(Scanner scanner) {
 *         super(scanner);
 *         setTitle("Form title goes here.");
 * 
 *         // Will appear as the first input field
 *         addInput(new TextBox("Label 1", scanner));
 *
 *         // Will appear as the second input field
 *         addInput(new TextBox("Label 2", scanner));
 * 
 *         setAction(new ItemAction() {
 *             public void execute() {
 *                 System.out.println("Getting user inputs completed.");
 *             }
 *         });
 *     }
 * }
 * }
 * </pre>
 * 
 * Usage example by association:
 * 
 * <pre>
 * {@code
 * public class Foo {
 *     public void bar() {
 *         Scanner sc = new Scanner(System.in);
 *         Form form = new Form(sc);
 *         form.setTitle("Form title goes here.");
 * 
 *         // Will appear as the first input field
 *         form.addInput(new TextBox("Label 1", scanner));
 *
 *         // Will appear as the second input field
 *         form.addInput(new TextBox("Label 2", scanner));
 * 
 *         form.setAction(new ItemAction() {
 *             public void execute() {
 *                 System.out.println("Getting user inputs completed.");
 *             }
 *         });
 *     }
 * }
 * }
 * </pre>
 * 
 * @author Gillbert Susilo Wong
 * @author Juan Frederick
 * @author Karl Devlin Chau
 * @author Pascalis Pandey
 * @author Trang Nguyen
 * @version 1.0
 * @since 2023-11-11
 */
public class Form implements Displayable {
    private String title;
    private List<TextBox> inputs;
    private Scanner scanner;
    private ItemAction action;

    /**
     * Constructs an empty form with the scanner to be used to receive user input.
     * 
     * @param scanner scanner for this form
     */
    public Form(Scanner scanner) {
        title = "";
        this.scanner = scanner;
        action = null;
        inputs = new ArrayList<TextBox>();
    }

    /**
     * Constructs an empty form with title/prompt and the scanner to be used to
     * receive user input.
     * 
     * @param title   displayed title
     * @param scanner scanner for this form
     */
    public Form(String title, Scanner scanner) {
        this.title = title;
        this.scanner = scanner;
        action = null;
        inputs = new ArrayList<TextBox>();
    }

    /**
     * Retrieves this form's title.
     * 
     * @return form title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Assigns this form's title.
     * 
     * @param title title to be displayed
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Assigns the action to be run after this form completes retrieving user
     * inputs. This form action should not be empty.
     * 
     * @param action form action
     */
    public void setAction(ItemAction action) {
        this.action = action;
    }

    /**
     * Adds a new user input field to the form. Input fields added to the form
     * cannot be removed afterwards.
     * 
     * @param input labelled form input field
     */
    public void addInput(TextBox input) {
        inputs.add(input);
    }

    /**
     * Retrieves the current values of all input fields inside this form as a map,
     * where the key is the input field label and the value is its corresponding
     * value.
     * 
     * @return map of input field labels to values
     */
    public Map<String, String> getValues() {
        Map<String, String> res = new HashMap<String, String>();
        for (TextBox tb : inputs)
            res.put(tb.getLabel(), tb.getValue());
        return res;
    }

    /**
     * Clears the standard output and displays the form title and the first user
     * input field initially. Waits and captures user input and displays the next
     * user input field afterwards. Repeats this until there areno more input fields
     * left to display.
     * 
     */
    public void display() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println(getTitle());
        for (int i = 0; i < inputs.size(); i++)
            inputs.get(i).display();
        action.execute();
    }
}
