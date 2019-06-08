package rocks.zipcode.atm;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import rocks.zipcode.atm.bank.Bank;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author ZipCodeWilmington
 */
public class CashMachineApp extends Application {

    private CashMachine cashMachine = new CashMachine(new Bank());
    TextField idField = new TextField();
    TextField nameField = new TextField();
    TextField mailField = new TextField();
    TextField balanceField = new TextField();
    TextField amountField = new TextField();

    Button btnLogin = new Button("Login");
    Button btnDeposit = new Button("Deposit");
    Button btnWithdraw = new Button("Withdraw");
    Button btnLogout = new Button("Logout");

    Text idMessage = new Text("");
    Text odMessage = new Text("");
    Text withdrawMessage = new Text("");

    private Parent createContentGrid(){
        GridPane grid = new GridPane();
        grid.setPrefSize(250,300);
        //grid.setGridLinesVisible(true);
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
            balanceField.setText(cashMachine.getAccountData().getBalnaceString());
                idMessage.setText("");
            }
            else idMessage.setText("No such ID");


        });

        btnDeposit.setOnAction(e -> {

            Double amount = Double.parseDouble(amountField.getText());
            cashMachine.deposit(amount);
            balanceField.setText(cashMachine.getAccountData().getBalnaceString());
            if (cashMachine.getAccountData().getBalance() >= 0)
                odMessage.setText("");
        });


        btnWithdraw.setOnAction(e -> {
            Double amount = Double.parseDouble(amountField.getText());
            cashMachine.withdraw(amount);
            balanceField.setText(cashMachine.getAccountData().getBalnaceString());
            if (cashMachine.getAccountData().getBalance() < 0)
                odMessage.setText("This account is overdrawn");
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
        grid.add(idField,               1,0,3,1);
        grid.add(idMessage,             1,1,3,1);
        grid.add(btnLogin,              1,2);
        grid.add(btnLogout,             2,2);
        grid.add(new Text("Name:"),     0,3);
        grid.add(nameField,             1,3,3,1);
        grid.add(new Text("Email:"),    0,4);
        grid.add(mailField,             1,4,3,1);
        grid.add(new Text("Balance:"),  0,5,1,1);
        grid.add(balanceField,          1,5,3,1);
        grid.add(odMessage,             1,6,3,1);
        grid.add(new Text("Amount:"),   0,7);
        grid.add(amountField,           1,7,3,1);
        grid.add(withdrawMessage,       1,8);
        grid.add(btnDeposit,            1,9);
        grid.add(btnWithdraw,           2,9);

        grid.setAlignment(Pos.CENTER);

        ColumnConstraints textColumnRight = new ColumnConstraints();
        textColumnRight.setHalignment(HPos.RIGHT);
        grid.getColumnConstraints().add(textColumnRight);

        GridPane.setHalignment(btnLogin,HPos.CENTER);
        GridPane.setHalignment(btnLogout,HPos.CENTER);

        return grid;
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
        balanceField.setDisable(value);
        amountField.setDisable(value);

    }
}
