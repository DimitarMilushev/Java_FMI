package bg.sofia.uni.fmi.mjt.revolut.account;

public class BGNAccount extends  Account{

    public static final String CURRENCY = "BGN";

    public BGNAccount(String IBAN)
    {
        super(IBAN, 0);
    }

    public BGNAccount(String IBAN, int amount)
    {
        super(IBAN, amount);
    }

    @Override
    public String getCurrency() {
        return CURRENCY;
    }
}
