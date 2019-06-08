package rocks.zipcode.atm;

import javafx.scene.control.*;
import rocks.zipcode.atm.bank.Bank;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.FlowPane;

/**
 * @author ZipCodeWilmington
 */
public class CashMachineApp extends Application {
    Menu menu1 = new Menu("Switch Accounts");
    Menu menu2 = new Menu("Create New Account");
    Menu menu3 = new Menu("Help");

    private TextField field = new TextField();
    private CashMachine cashMachine = new CashMachine(new Bank());
    MenuBar menuBar = new MenuBar();

    private Parent createContent() {
        VBox vbox = new VBox(10, menuBar);
        menuBar.getMenus().add(menu1);
        MenuItem menuItem1 = new MenuItem("Account 1");
        MenuItem menuItem2 = new MenuItem("Account 2");
        MenuItem menuItem3 = new MenuItem("Account 3");
        MenuItem menuItem4 = new MenuItem("Account 4");
        MenuItem menuItem5 = new MenuItem("Account 5");

        menu1.getItems().add(menuItem1);
        menu1.getItems().add(menuItem2);
        menu1.getItems().add(menuItem3);
        menu1.getItems().add(menuItem4);
        menu1.getItems().add(menuItem5);

        menuBar.getMenus().add(menu2);
        MenuItem menu2Item1 = new MenuItem("Account Number");
        MenuItem menu2Item2 = new MenuItem("Bluh Bluh Bluh");

        menu2.getItems().add(menu2Item1);
        menu2.getItems().add(menu2Item2);

        menuBar.getMenus().add(menu3);
        MenuItem menu3Item1 = new MenuItem("Contact Steffun");
        MenuItem menu3Item2 = new MenuItem("Contact Anish");
        MenuItem menu3Item3 = new MenuItem("Contact Joanna");

        menu3.getItems().add(menu3Item1);
        menu3.getItems().add(menu3Item2);
        menu3.getItems().add(menu3Item3);




        vbox.setPrefSize(600, 600);



        TextArea areaInfo = new TextArea();

        Button btnSubmit = new Button("Set Account ID");
        btnSubmit.setOnAction(e -> {
            int id = Integer.parseInt(field.getText());
            cashMachine.login(id);

            areaInfo.setText(cashMachine.toString());
        });

        Button btnDeposit = new Button("Deposit");
        btnDeposit.setOnAction(e -> {
            int amount = Integer.parseInt(field.getText());
            cashMachine.deposit(amount);

            areaInfo.setText(cashMachine.toString());
        });

        Button btnWithdraw = new Button("Withdraw");
        btnWithdraw.setOnAction(e -> {
            int amount = Integer.parseInt(field.getText());
            cashMachine.withdraw(amount);

            areaInfo.setText(cashMachine.toString());
        });

        Button btnExit = new Button("Exit");
        btnExit.setOnAction(e -> {
            cashMachine.exit();

            areaInfo.setText(cashMachine.toString());
        });

        FlowPane flowpane = new FlowPane();

        flowpane.getChildren().add(btnSubmit);
        flowpane.getChildren().add(btnDeposit);
        flowpane.getChildren().add(btnWithdraw);
        flowpane.getChildren().add(btnExit);
        vbox.getChildren().addAll(field, flowpane, areaInfo);
        return vbox;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(createContent()));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

