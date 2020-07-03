package uz.alex.its.beverlee.model;

public class Card {
    //cardType = {master = true \ visa = false;}
    private final boolean isMaster;
    private final String cardNo;
    private final String cardExpDate;
    private final String cardUsername;

    public Card(boolean isMaster, String cardNo, String cardExpDate, String cardUsername) {
        this.isMaster = isMaster;
        this.cardNo = cardNo;
        this.cardExpDate = cardExpDate;
        this.cardUsername = cardUsername;
    }

    public boolean isMaster() {
        return isMaster;
    }

    public String getCardNo() {
        return cardNo;
    }

    public String getCardExpDate() {
        return cardExpDate;
    }

    public String getCardUsername() {
        return cardUsername;
    }
}
