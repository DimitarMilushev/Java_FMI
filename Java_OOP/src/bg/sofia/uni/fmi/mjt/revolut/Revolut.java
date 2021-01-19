package bg.sofia.uni.fmi.mjt.revolut;

import bg.sofia.uni.fmi.mjt.revolut.account.Account;
import bg.sofia.uni.fmi.mjt.revolut.card.Card;
import bg.sofia.uni.fmi.mjt.revolut.card.PhysicalCard;
import bg.sofia.uni.fmi.mjt.revolut.card.VirtualOneTimeCard;

import java.time.LocalDate;

public class Revolut implements RevolutAPI {

    private static final String TOP_LEVEL_BANNED_DOMAIN = ".biz";
    private Account[] accounts;
    private Card[] cards;

    public Revolut(Account[] accounts, Card[] cards)
    {
        this.accounts = accounts;
        this.cards = cards;
    }

    private boolean isPinValid(Card card, int pin)
    {
        if(String.valueOf(pin).length() == 4 && card.checkPin(pin))
            return true;

        return false;
    }


    private boolean isAmountValid(double amount) { return amount > 0; }

    private boolean isCurrencyValid(String currency) {
        if(currency.equals("BGR") || currency.equals("EUR"))
            return true;

        return false;
    }

    private boolean isCardValid(Card card)
    {
        if(card.isBlocked() || card.getExpirationDate() != LocalDate.now())
            return false;

        return true;
    }

    boolean isCardRightType(Card card, String type) { return card.getType().equals(type); }

    boolean isUrlBanned(String url)
    {
        String[] tokens = url.split("\\.");
        String domain = tokens[tokens.length - 1];

        if(domain.equals(TOP_LEVEL_BANNED_DOMAIN)) return false;

        return true;
    }

    @Override
    public boolean pay(Card card, int pin, double amount, String currency) {

            if(!isCardValid(card)) return false;

            if(!isCardRightType(card, PhysicalCard.TYPE)) return false;

            if(!isPinValid(card, pin)) return false;

            if(!(card.checkPin(pin))) return false;

            if(!isAmountValid(amount)) return false;

            if(!isCurrencyValid(currency)) return false;

            for (Account acc : accounts)
            {
                if(acc.getCurrency().equals(currency))
                {
                    if(acc.getAmount() >= amount)
                    {
                        acc.setAmount(acc.getAmount() - amount);
                        return true;
                    }
                }
            }

            return false;
    }

    @Override
    public boolean payOnline(Card card, int pin, double amount, String currency, String shopURL) {

        if(!isPinValid(card, pin)) return false;

        if(!isCardValid(card)) return false;

        if(!isUrlBanned(shopURL)) return false;

        for (Account acc : accounts)
        {
            if(acc.getCurrency().equals(currency))
            {
                if(acc.getAmount() >= amount)
                {
                    acc.setAmount(acc.getAmount() - amount);
                    if(card.getType().equals(VirtualOneTimeCard.TYPE)) {
                        card.block();
                    }

                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean addMoney(Account account, double amount) {
        return false;
    }

    @Override
    public boolean transferMoney(Account from, Account to, double amount) {
        return false;
    }

    @Override
    public double getTotalAmount() {
        return 0;
    }
}
