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

    public void depositChecking(double amount) {
        if (amount > 0) {
            updateBalanceChecking(getCheckingBalance() + amount);
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && canWithdrawChecking(amount)) {
            updateBalanceChecking(getCheckingBalance() - amount);
            return true;
        } else {
            return false;
        }
    }

    protected boolean canWithdrawChecking(double amount) {

        return getCheckingBalance() >= amount;
    }

    public double getCheckingBalance() {
        return accountData.getCheckingBalance();
    }

    private void updateBalanceChecking(double newCheckingBalance) {
        accountData = new AccountData(accountData.getId(), accountData.getName(), accountData.getEmail(),
                newCheckingBalance, accountData.getSavingsBalance());
    }
        public void depositSavings ( double amount){
            if (amount > 0) {
                updateBalanceSavings(getSavingsBalance() + amount);
            }
        }
        public boolean withdrawSavings ( double amount){
            if (amount > 0 && canWithdrawChecking(amount)) {
                updateBalanceSavings(getSavingsBalance() - amount);
                return true;
            } else {
                return false;
            }
        }

        protected boolean canWithdrawSavings ( double amount){

            return getSavingsBalance() >= amount;
        }

        public double getSavingsBalance (){return accountData.getSavingsBalance();}


        private void updateBalanceSavings ( double newSavingsBalance){
            accountData = new AccountData(accountData.getId(), accountData.getName(), accountData.getEmail(),
                    accountData.getCheckingBalance(), newSavingsBalance);
        }
    }

