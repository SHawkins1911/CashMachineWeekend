package rocks.zipcode.atm;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import rocks.zipcode.atm.bank.Bank;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.FlowPane;

/**
 * @author ZipCodeWilmington
 */
public class CashMachineApp extends Application {

    private TextField field = new TextField();
    private CashMachine cashMachine = new CashMachine(new Bank());

    private Parent createContent() {
        VBox vbox = new VBox(10);
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

    private Parent createContentGrid(){
        GridPane grid = new GridPane();
        grid.setPrefSize(250,300);
        //   grid.setGridLinesVisible(true);
        grid.setHgap(10);
        grid.setVgap(5);

        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField mailField = new TextField();
        TextField balanceField = new TextField();
        TextField amountField = new TextField();

        Text[] textFieldLabel = {new Text("ID:"),new Text("Name:"), new Text("Email:"),
                new Text("Balance:"), new Text("Amount:")};

        Text[] textFieldMessage = {new Text("IDError"),new Text("OverDraft"),
                new Text("AmountError")};



        TextArea areaInfo = new TextArea();
        areaInfo.setEditable(false);

        Button btnLogin = new Button("Login");
        Button btnDeposit = new Button("Deposit");
        Button btnWithdraw = new Button("Withdraw");
        Button btnLogout = new Button("Logout");

        nameField.setEditable(false);
        mailField.setEditable(false);
        balanceField.setEditable(false);
        amountField.setEditable(true);

        nameField.setDisable(true);
        mailField.setDisable(true);
        balanceField.setDisable(true);
        amountField.setDisable(true);

        btnDeposit.setDisable(true);
        btnWithdraw.setDisable(true);
        btnLogout.setDisable(true);

        btnLogin.setOnAction(e -> {
            int id = Integer.parseInt(idField.getText());
            cashMachine.login(id);

            idField.setEditable(false);
            btnDeposit.setDisable(false);
            btnWithdraw.setDisable(false);
            btnLogout.setDisable(false);

            nameField.setDisable(false);
            mailField.setDisable(false);
            balanceField.setDisable(false);
            amountField.setDisable(false);

//            areaInfo.setText(cashMachine.toString());
        });

        btnDeposit.setOnAction(e -> {
            int amount = Integer.parseInt(field.getText());
            cashMachine.deposit(amount);

            areaInfo.setText(cashMachine.toString());
        });


        btnWithdraw.setOnAction(e -> {
            int amount = Integer.parseInt(field.getText());
            cashMachine.withdraw(amount);

            areaInfo.setText(cashMachine.toString());
        });

        btnLogout.setOnAction(e -> {
            cashMachine.exit();

            nameField.setDisable(true);
            mailField.setDisable(true);
            balanceField.setDisable(true);
            amountField.setDisable(true);

            btnDeposit.setDisable(true);
            btnWithdraw.setDisable(true);
            btnLogout.setDisable(true);
//            areaInfo.setText(cashMachine.toString());
        });


        grid.add(textFieldLabel[0],     0,0);
        grid.add(idField,               1,0,3,1);
        grid.add(textFieldMessage[0],   1,1,3,1);
        grid.add(btnLogin,              1,2);
        grid.add(btnLogout,             2,2);
        grid.add(textFieldLabel[1],     0,3);
        grid.add(nameField,             1,3,3,1);
        grid.add(textFieldLabel[2],     0,4);
        grid.add(mailField,             1,4,3,1);
        grid.add(textFieldLabel[3],     0,5,1,1);
        grid.add(balanceField,          1,5,3,1);
        grid.add(textFieldMessage[1],   1,6,3,1);
        grid.add(textFieldLabel[4],     0,7);
        grid.add(amountField,           1,7,3,1);
        grid.add(textFieldMessage[2],   1,8,3,1);
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
//        stage.setScene(new Scene(createContent()));
        stage.setScene(new Scene(createContentGrid()));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
