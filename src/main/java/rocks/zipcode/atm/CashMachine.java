package rocks.zipcode.atm;

import rocks.zipcode.atm.bank.AccountData;
import rocks.zipcode.atm.bank.Bank;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author ZipCodeWilmington
 */
public class CashMachine {

    private final Bank bank;
    private AccountData accountData = null;
    private String currentBalanceType;

    public CashMachine(Bank bank) {
        this.bank = bank;
    }

    private Consumer<AccountData> update = data -> {
        accountData = data;
    };


    public void login(String username, String password) {
        tryCall(
                () -> bank.getAccountByUsername(username),
                update
        );
        if (!accountData.getPassword().equals(password))
            accountData = null;
    }

    public void deposit(double amount) {
        if (accountData != null) {
            tryCall(
                    () -> bank.deposit(accountData, amount, currentBalanceType),
                    update
            );
        }
    }

    public void withdraw(double amount) {
        if (accountData != null) {
            tryCall(
                    () -> bank.withdraw(accountData, amount, currentBalanceType),
                    update
            );
        }
    }

    public void exit() {
        if (accountData != null) {
            accountData = null;
        }
    }

    @Override
    public String toString() {
        return accountData != null ? accountData.toString() : "Try account 1000 or 2000 and click submit.";
    }

    private <T> void tryCall(Supplier<ActionResult<T> > action, Consumer<T> postAction) {
        try {
            ActionResult<T> result = action.get();
            if (result.isSuccess()) {
                T data = result.getData();
                postAction.accept(data);
            } else {
                String errorMessage = result.getErrorMessage();
                throw new RuntimeException(errorMessage);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            throw new RuntimeException();
        }
    }

    public AccountData getAccountData() {
        return accountData;
    }

    public String getCurrentBalanceType() {
        return currentBalanceType;
    }

    public void setCurrentBalanceType(String currentBalanceType) {
        this.currentBalanceType = currentBalanceType;
    }

    public void addAccount(String userName, String password, String name, String mail, String type){
        tryCall( () -> bank.addAccountTest(userName,password,name,mail,type),
                data -> accountData = null
        );
    }

    public Boolean isUsernameExist(String username){
        return bank.getAccounts().containsKey(username);
    }

    public void changePassword(String newPassword) {
        if (accountData != null) {
            tryCall(
                    () -> bank.changePassword(accountData,newPassword),
                    update
            );
        }
    }
}
