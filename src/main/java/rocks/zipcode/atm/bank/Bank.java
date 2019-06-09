package rocks.zipcode.atm.bank;

import rocks.zipcode.atm.ActionResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZipCodeWilmington
 */
public class Bank {

    private Map<String, Account> accounts = new HashMap<>();

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
        Account account = accounts.get(accountData.getId());
        account.deposit(amount, balanceType);

        return ActionResult.success(account.getAccountData());
    }

    public ActionResult<AccountData> withdraw(AccountData accountData, double amount, String balanceType) {
        Account account = accounts.get(accountData.getId());
        boolean ok = account.withdraw(amount, balanceType);

        if (ok) {
            return ActionResult.success(account.getAccountData());
        } else {
            return ActionResult.fail("Withdraw failed: " + amount + ". Account has: " + account.getBalance(balanceType));
        }
    }

    public void addAccount(Integer id,String userName, String password, String name, String mail, String type){
        if (type.equals("Basic"))
           accounts.put(userName, new BasicAccount(new AccountData(
                id, userName, password, name, mail, 0, 0)));
        if (type.equals("Premium"))
            accounts.put(userName, new PremiumAccount(new AccountData(
                    id, userName, password, name, mail, 0, 0)));
    }
}
