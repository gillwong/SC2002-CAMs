package cams;

import java.util.Scanner;

import cams.view.DisplayController;

/**
 * The CAMs application's entry point.
 *
 * @author Gillbert Susilo Wong
 * @author Juan Frederick
 * @author Karl Devlin Chau
 * @author Pascalis Pandey
 * @author Trang Nguyen
 * @version 1.0
 * @since 2023-11-23
 */
public class App {
    /**
     * Starts and runs the CAMs application.
     *
     * @param args command line arguments. Unused for this application.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DisplayController menuController = DisplayController.getInstance();
        menuController.displayCurrent();
    }
}
