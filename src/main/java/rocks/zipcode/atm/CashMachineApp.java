package rocks.zipcode.atm;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import rocks.zipcode.atm.bank.Bank;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * @author ZipCodeWilmington
 */
public class CashMachineApp extends Application {

    Stage mainWindow;
    Stage newAccountWindow;

    Menu menu1 = new Menu("Account");
    Menu menu2 = new Menu("Help");

    private CashMachine cashMachine = new CashMachine(new Bank());
    TextField idField = new TextField();
    TextField userNameField = new TextField();
    TextField passwordField = new TextField();
    TextField nameField = new TextField();
    TextField mailField = new TextField();
    TextField balanceField = new TextField();
    TextField amountField = new TextField();
    ComboBox balanceTypeBox = new ComboBox();

    MenuBar menuBar = new MenuBar();
    MenuItem menuItem1 = new MenuItem("Create Account");
    MenuItem menuItem2 = new MenuItem("Change Password");
    MenuItem menuItem3 = new MenuItem("Check Profile");

    MenuItem menu2Item1 = new MenuItem("Contact Steffun");
    MenuItem menu2Item2 = new MenuItem("Contact Anish");
    MenuItem menu2Item3 = new MenuItem("Contact Joanna");


    Button btnLogin = new Button("Login");
    Button btnDeposit = new Button("Deposit");
    Button btnWithdraw = new Button("Withdraw");
    Button btnLogout = new Button("Logout");

    Text idMessage = new Text("");
    Text odMessage = new Text("");
    Text withdrawMessage = new Text("");

    private Parent createContentGrid(){
        VBox root = new VBox(10, menuBar);
        root.setPrefSize(275,450);
        menuBar.getMenus().add(menu1);


        menu1.getItems().add(menuItem1);
        menu1.getItems().add(menuItem2);
        menu1.getItems().add(menuItem3);

        menuBar.getMenus().add(menu2);
        MenuItem menu2Item1 = new MenuItem("Contact Stefun");
        MenuItem menu2Item2 = new MenuItem("Contact Anish");
        MenuItem menu2Item3 = new MenuItem("Contact Joanna");

        menu2.getItems().add(menu2Item1);
        menu2.getItems().add(menu2Item2);
        menu2.getItems().add(menu2Item3);

        menuItem1.setOnAction(e -> {
            newAccountWindow = new Stage();
            newAccountWindow.setScene(new Scene(createNewAccountWindow()));
            newAccountWindow.setTitle("Create a new account");
            newAccountWindow.setOnCloseRequest(s -> {
                mainWindow.show();
            });
            newAccountWindow.show();
            mainWindow.hide();


        });

        balanceTypeBox.getItems().add("Checking");
        balanceTypeBox.getItems().add("Saving");
//        balanceTypeBox.getItems().add("Choice 3");

        balanceTypeBox.setOnAction(e -> {
            cashMachine.setCurrentBalanceType(balanceTypeBox.getValue().toString());
            balanceField.setText(cashMachine.getAccountData().
                    getBalanceString(cashMachine.getCurrentBalanceType()));
        });

        GridPane grid = new GridPane();
        root.getChildren().add(grid);

        TextArea areaInfo = new TextArea();
        areaInfo.setEditable(false);

        nameField.setEditable(false);
        mailField.setEditable(false);
        balanceField.setEditable(false);
        amountField.setEditable(true);

        setDisable(true);

        btnLogin.setOnAction(e -> {
            try {
                String username = userNameField.getText();
                cashMachine.login(username);
            } catch (Exception ex) { }
            if (cashMachine.getAccountData() != null){
            setDisable(false);

            idField.setText(cashMachine.getAccountData().getId().toString());
            nameField.setText(cashMachine.getAccountData().getName());
            mailField.setText(cashMachine.getAccountData().getEmail());
            balanceTypeBox.setValue("Checking");
            balanceField.setText(cashMachine.getAccountData().
                    getBalanceString(cashMachine.getCurrentBalanceType()));
            idMessage.setText("");
            }
            else idMessage.setText("No such ID");


        });

        btnDeposit.setOnAction(e -> {
            Double pastAmount = cashMachine.getAccountData().getBalance(cashMachine.getCurrentBalanceType());
            try {
                Double amount = Double.parseDouble(amountField.getText());
                cashMachine.deposit(amount);
            } catch (Exception ex){ }
            balanceField.setText(cashMachine.getAccountData().
                    getBalanceString(cashMachine.getCurrentBalanceType()));
            if (cashMachine.getAccountData().getBalance(cashMachine.getCurrentBalanceType()) >= 0)
                odMessage.setText("");
            if (pastAmount == cashMachine.getAccountData().getBalance(cashMachine.getCurrentBalanceType()))
                withdrawMessage.setText("Deposit failed");
            else
                withdrawMessage.setText("");

        });


        btnWithdraw.setOnAction(e -> {
            Double pastAmount = cashMachine.getAccountData().getBalance(cashMachine.getCurrentBalanceType());
            try {
            Double amount = Double.parseDouble(amountField.getText());
            cashMachine.withdraw(amount);
            } catch (Exception ex){ }
            balanceField.setText(cashMachine.getAccountData().
                    getBalanceString(cashMachine.getCurrentBalanceType()));
            if (cashMachine.getAccountData().getBalance(cashMachine.getCurrentBalanceType()) < 0)
                odMessage.setText("This account is overdrawn");
            if (pastAmount == cashMachine.getAccountData().getBalance(cashMachine.getCurrentBalanceType()))
                withdrawMessage.setText("Withdraw failed");
            else
                withdrawMessage.setText("");

        });

        btnLogout.setOnAction(e -> {
            cashMachine.exit();

            setDisable(true);

            idField.clear();
            nameField.clear();
            mailField.clear();
            balanceField.clear();
            amountField.clear();

            idMessage.setText("");
            odMessage.setText("");
            withdrawMessage.setText("");
        });

        grid.add(new Text("Username:"), 0,0);
        grid.add(userNameField,         1,0,2,1);
        grid.add(new Text("Password:"), 0,1);
        grid.add(passwordField,         1,1,2,1);
        grid.add(idMessage,             1,2,2,1);
        grid.add(btnLogin,              1,3);
        grid.add(btnLogout,             2,3);
        grid.add(new Text("ID:"),       0,4);
        grid.add(idField,               1,4,2,1);
        grid.add(new Text("Name:"),     0,5);
        grid.add(nameField,             1,5,2,1);
        grid.add(new Text("Email:"),    0,6);
        grid.add(mailField,             1,6,2,1);
        grid.add(new Text("Type:"),     0,7);
        grid.add(balanceTypeBox,        1,7,2,1);
        grid.add(new Text("Balance:"),  0,8,1,1);
        grid.add(balanceField,          1,8,2,1);
        grid.add(odMessage,             1,9,2,1);
        grid.add(new Text("Amount:"),   0,10);
        grid.add(amountField,           1,10,2,1);
        grid.add(withdrawMessage,       1,11,2,1);
        grid.add(btnDeposit,            1,12);
        grid.add(btnWithdraw,           2,12);

        grid.setAlignment(Pos.CENTER);

        ColumnConstraints textColumnRight = new ColumnConstraints();
        textColumnRight.setHalignment(HPos.RIGHT);
        grid.getColumnConstraints().add(textColumnRight);
        //       grid.setGridLinesVisible(true);
        grid.setHgap(10);
        grid.setVgap(5);


        GridPane.setHalignment(btnLogin,HPos.CENTER);
        GridPane.setHalignment(btnLogout,HPos.CENTER);

        GridPane.setHalignment(btnDeposit,HPos.CENTER);
        GridPane.setHalignment(btnWithdraw,HPos.CENTER);

        return root;
    }

    public Parent createNewAccountWindow(){
        GridPane grid = new GridPane();

        TextField newIdField = new TextField();
        TextField newNameField = new TextField();
        TextField newEmailField = new TextField();

        ToggleGroup accountTypeGroup = new ToggleGroup();
        RadioButton basicButton = new RadioButton("Basic");
        RadioButton premiumButton = new RadioButton("Premium");

        Button cancelBtn = new Button("Cancel");
        Button createBtn = new Button("Create");

        basicButton.setToggleGroup(accountTypeGroup);
        premiumButton.setToggleGroup(accountTypeGroup);

        basicButton.setSelected(true);

        Text idErr = new Text("");
        Text mailErr = new Text("");

        cancelBtn.setOnAction(c -> {
            newAccountWindow.close();
            mainWindow.show();
        });

        createBtn.setOnAction(c -> {
            newAccountWindow.close();
            cashMachine.addAccount(Integer.parseInt(newIdField.getText()),
                    "placeHolder",
                    "placeHolder",
                    newNameField.getText(),
                    newEmailField.getText(),
                    "Basic");
            mainWindow.show();
        });

        grid.add(new Text("ID:"),       0,0);
        grid.add(newIdField,            1,0,2,1);
        grid.add(idErr,                 1,1,2,1);
        grid.add(new Text("Name:"),     0,2);
        grid.add(newNameField,          1,2,2,1);
        grid.add(new Text("Email:"),    0,3);
        grid.add(newEmailField,         1,3,2,1);
        grid.add(mailErr,               1,4,2,1);
        grid.add(new Text("Type:"),     0,5);
        grid.add(basicButton,           1,5);
        grid.add(premiumButton,         2,5);
        grid.add(cancelBtn,             1,6);
        grid.add(createBtn,             2,6);
        ColumnConstraints textColumnRight = new ColumnConstraints();
        textColumnRight.setHalignment(HPos.RIGHT);
        grid.getColumnConstraints().add(textColumnRight);
        //       grid.setGridLinesVisible(true);
        grid.setPrefSize(250,225);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(5);

        return grid;
    }


    @Override
    public void start(Stage stage) throws Exception {
        mainWindow = stage;
        mainWindow.setScene(new Scene(createContentGrid()));

        mainWindow.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void setDisable(Boolean value) {

        userNameField.setEditable(value);
        passwordField.setEditable(value);
        btnLogin.setDisable(!value);
        btnDeposit.setDisable(value);

        idField.setDisable(value);
        nameField.setDisable(value);
        mailField.setDisable(value);
        balanceTypeBox.setDisable(value);
        balanceField.setDisable(value);
        amountField.setDisable(value);
        btnWithdraw.setDisable(value);
        btnLogout.setDisable(value);

    }
}
