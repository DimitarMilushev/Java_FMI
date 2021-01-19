package bg.sofia.uni.fmi.mjt.revolut.account;

public class EURAccount extends Account{

    public static final String CURRENCY = "EUR";

    public EURAccount(String IBAN)
    {
        super(IBAN, 0);
    }

    public EURAccount(String IBAN, int amount)
    {
        super(IBAN, amount);
    }

    @Override
    public String getCurrency() {
        return CURRENCY;
    }
}
