package rocks.zipcode.atm.bank;

import rocks.zipcode.atm.ActionResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZipCodeWilmington
 */
public class Bank {

    private Map<String, Account> accounts = new HashMap<>();
    private Integer nextIdCreation = 3000;

    public Bank() {
        accounts.put("example1", new BasicAccount(new AccountData(
                1000, "example1","pass", "Example 1", "example1@gmail.com", 500, 500)));

        accounts.put("example2", new PremiumAccount(new AccountData(
                2000, "example2","pass","Example 2", "example2@gmail.com", 200, 500)));
    }

    public ActionResult<AccountData> getAccountByUsername(String username) {
        Account account = accounts.get(username);

        if (account != null) {
            return ActionResult.success(account.getAccountData());
        } else {
            return ActionResult.fail("No account with username: "+username);
        }
    }

    public ActionResult<AccountData> deposit(AccountData accountData, double amount, String balanceType) {
        Account account = accounts.get(accountData.getUserName());
        account.deposit(amount, balanceType);

        return ActionResult.success(account.getAccountData());
    }

    public ActionResult<AccountData> withdraw(AccountData accountData, double amount, String balanceType) {
        Account account = accounts.get(accountData.getUserName());
        boolean ok = account.withdraw(amount, balanceType);

        if (ok) {
            return ActionResult.success(account.getAccountData());
        } else {
            return ActionResult.fail("Withdraw failed: " + amount + ". Account has: " + account.getBalance(balanceType));
        }
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public ActionResult<AccountData> addAccountTest(String userName, String password, String name, String mail, String type){
        if (accounts.containsKey(userName))
        {
            System.out.println("username dupe");
            return ActionResult.fail("username " + userName +" already existed");}
        else {
            if (type.equals("Basic"))
                accounts.put(userName, new BasicAccount(new AccountData(
                        nextIdCreation, userName, password, name, mail, 0, 0)));
            if (type.equals("Premium"))
                accounts.put(userName, new PremiumAccount(new AccountData(
                        nextIdCreation, userName, password, name, mail, 0, 0)));
            nextIdCreation+=1000;
            return ActionResult.success(accounts.get(userName).getAccountData());
        }
    }

    public ActionResult<AccountData> changePassword(AccountData accountData, String newPassword) {
        Account account = accounts.get(accountData.getUserName());
        account.changePassword(newPassword);

        return ActionResult.success(account.getAccountData());
    }
}
