package rocks.zipcode.atm.bank;

/**
 * @author ZipCodeWilmington
 */
public abstract class Account {

    private AccountData accountData;

    public Account(AccountData accountData) {
        this.accountData = accountData;
    }

    public AccountData getAccountData() {
        return accountData;
    }

    public void deposit(double amount, String balanceType) {
        if (amount > 0) {
                updateBalance(getBalance(balanceType) + amount, balanceType);
        }
    }

    public boolean withdraw(double amount, String balanceType) {
        if (amount > 0 && canWithdraw(amount, balanceType)) {
            updateBalance(getBalance(balanceType) - amount, balanceType);
            return true;
        } else {
            return false;
        }
    }

    protected boolean canWithdraw(double amount, String BalanceType) {
            return getBalance(BalanceType) >= amount;
    }

    public double getBalance(String balanceType) {
        return accountData.getBalance(balanceType);
    }

    private void updateBalance(double newBalance, String balanceType) {
        if (balanceType.equals("Checking"))
            accountData = new AccountData(accountData.getId(), accountData.getUserName(), accountData.getPassword(), accountData.getName(), accountData.getEmail(),
                newBalance, accountData.getBalance("Saving"));
        if (balanceType.equals("Saving"))
            accountData = new AccountData(accountData.getId(), accountData.getUserName(), accountData.getPassword(), accountData.getName(), accountData.getEmail(),
                    accountData.getBalance("Checking"), newBalance);
    }

    public void changePassword(String newPassword) {
        accountData = new AccountData(accountData.getId(), accountData.getUserName(), newPassword, accountData.getName(), accountData.getEmail(),
                accountData.getBalance("Checking"), accountData.getBalance("Saving"));

    }
}

