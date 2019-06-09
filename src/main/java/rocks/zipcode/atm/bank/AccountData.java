package rocks.zipcode.atm.bank;

/**
 * @author ZipCodeWilmington
 */
public final class AccountData {

    private final int id;
    private final String name;
    private final String email;

    private final double checkingBalance;
    private final double savingsBalance;




    AccountData(int id, String name, String email, double checkingBalance, double savingsBalance) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.checkingBalance = checkingBalance;
        this.savingsBalance = savingsBalance;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    public double getCheckingBalance() {
        return checkingBalance;
    }

    public double getSavingsBalance() {
        return savingsBalance;
    }

    public double getTotalBalance() {return savingsBalance + checkingBalance;}

    public String getBalnaceString() {return String.format("%.2f",getTotalBalance());}

    public String getCheckingBalanceString(){return String.format("%.2f",getCheckingBalance());}

    public String getSavingsBalanceString(){return String.format("%.2f",getSavingsBalance());}

    @Override
    public String toString() {
        return "Account id: " + id + '\n' +
                "Name: " + name + '\n' +
                "Email: " + email + '\n' +
                "Checking Balance: " + String.format("%.2f",checkingBalance) + '\n' +
                "Savings Balance: " + String.format("%.2f",savingsBalance)+ '\n' +
                "Total Account Balance: " + String.format("%.2f",getTotalBalance());
    }
}
