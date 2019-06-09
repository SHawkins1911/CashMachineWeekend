package rocks.zipcode.atm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import javax.swing.text.ComponentView;

/**
 * @author ZipCodeWilmington
 */
public class CashMachineApp extends Application {

    Menu menu1 = new Menu("Account");
    Menu menu2 = new Menu("Help");



    private CashMachine cashMachine = new CashMachine(new Bank());
    TextField idField = new TextField();
    TextField nameField = new TextField();
    TextField mailField = new TextField();
    TextField balanceField = new TextField();
    TextField amountField = new TextField();
    ComboBox ComboBox = new ComboBox();
    MenuBar menuBar = new MenuBar();

    Button btnLogin = new Button("Login");
    Button btnDeposit = new Button("Deposit");
    Button btnWithdraw = new Button("Withdraw");
    Button btnLogout = new Button("Logout");

    Text idMessage = new Text("");
    Text odMessage = new Text("");
    Text withdrawMessage = new Text("");

    private Parent createContentGrid(){
        VBox root = new VBox(10, menuBar);
        menuBar.getMenus().add(menu1);
        MenuItem menuItem1 = new MenuItem("Create Acccount");
        MenuItem menuItem2 = new MenuItem("Change Password");
        MenuItem menuItem3 = new MenuItem("Check Profile");

        menu1.getItems().add(menuItem1);
        menu1.getItems().add(menuItem2);
        menu1.getItems().add(menuItem3);

        menuBar.getMenus().add(menu2);
        MenuItem menu2Item1 = new MenuItem("Contact Steffun");
        MenuItem menu2Item2 = new MenuItem("Contact Anish");
        MenuItem menu2Item3 = new MenuItem("Contact Joanna");

        menu2.getItems().add(menu2Item1);
        menu2.getItems().add(menu2Item2);
        menu2.getItems().add(menu2Item3);



        ComboBox.getItems().add("Checking");
        ComboBox.getItems().add("Savings");
        ComboBox.getItems().add("Choice 3");








        GridPane grid = new GridPane();
        root.getChildren().add(grid);
        grid.setPrefSize(250,300);
 //       grid.setGridLinesVisible(true);
        grid.setHgap(10);
        grid.setVgap(5);

        TextArea areaInfo = new TextArea();
        areaInfo.setEditable(false);

        nameField.setEditable(false);
        mailField.setEditable(false);
        balanceField.setEditable(false);
        amountField.setEditable(true);

        setDisable(true);

        btnLogin.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                cashMachine.login(id);
            } catch (Exception ex) { }
            if (cashMachine.getAccountData() != null){
            setDisable(false);

            nameField.setText(cashMachine.getAccountData().getName());
            mailField.setText(cashMachine.getAccountData().getEmail());
            cashMachine.setCurrentBalanceType("Checking");
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

        grid.add(new Text("ID:"),       0,0);
        grid.add(idField,               1,0,2,1);
        grid.add(idMessage,             1,1,2,1);
        grid.add(btnLogin,              1,2);
        grid.add(btnLogout,             2,2);
        grid.add(new Text("Name:"),     0,3);
        grid.add(nameField,             1,3,2,1);
        grid.add(new Text("Email:"),    0,4);
        grid.add(mailField,             1,4,2,1);
        grid.add(new Text("Type:"),     0,5);
        grid.add(ComboBox, 1,5,2,1);
        grid.add(new Text("Balance:"),  0,6,1,1);
        grid.add(balanceField,          1,6,2,1);
        grid.add(odMessage,             1,7,2,1);
        grid.add(new Text("Amount:"),   0,8);
        grid.add(amountField,           1,8,2,1);
        grid.add(withdrawMessage,       1,9,2,1);
        grid.add(btnDeposit,            1,10);
        grid.add(btnWithdraw,           2,10);

        grid.setAlignment(Pos.CENTER);

        ColumnConstraints textColumnRight = new ColumnConstraints();
        textColumnRight.setHalignment(HPos.RIGHT);
        grid.getColumnConstraints().add(textColumnRight);

        GridPane.setHalignment(btnLogin,HPos.CENTER);
        GridPane.setHalignment(btnLogout,HPos.CENTER);

        GridPane.setHalignment(btnDeposit,HPos.CENTER);
        GridPane.setHalignment(btnWithdraw,HPos.CENTER);

        return root;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(createContentGrid()));

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void setDisable(Boolean value) {
        idField.setEditable(value);
        btnLogin.setDisable(!value);
        btnDeposit.setDisable(value);
        btnWithdraw.setDisable(value);
        btnLogout.setDisable(value);

        nameField.setDisable(value);
        mailField.setDisable(value);
        ComboBox.setDisable(value);
        balanceField.setDisable(value);
        amountField.setDisable(value);

    }
}
