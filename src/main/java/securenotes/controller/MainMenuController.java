package securenotes.controller;

import securenotes.view.MainMenuView;

import java.util.Scanner;
public class MainMenuController {
    private final MainMenuView view;
    private final LoginController loginController;
    private final RegisterNewUserController registerController;
    private  boolean running = true;

    public MainMenuController(MainMenuView view, LoginController loginController, RegisterNewUserController registerController) {
        this.view = view;
        this.loginController = loginController;
        this.registerController = registerController;
    }

    public void start(Scanner readInput) {

        while (running){
            view.show();
            String choice = view.getChoice(readInput);

        switch (choice) {
            case "1" -> loginController.start(readInput);

            default -> {
                System.out.println("Invalid choice, try again!");
                start(readInput);
            }
        }
    }
}
