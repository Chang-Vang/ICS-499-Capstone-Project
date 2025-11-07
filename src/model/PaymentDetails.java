package edu.metrostate.model;

public class PaymentDetails {

    private String ccNumber;
    private Integer ccv;
    private String exp;

    public PaymentDetails(String ccNumber, String exp, Integer CCV) {
        setCardNumber(ccNumber);
        setExp(exp);
        setCCV(CCV);
    }

    public String getCardNumber() {
        return ccNumber;
    }

    Integer getCCV() {
        return ccv;
    }

    public String getExp() {
        return exp;
    }

    public void setCardNumber(String newValue) {
        // TODO: check limits on a valid CC #, ie 16 digits with or without delimiters
        ccNumber = newValue;
    }

    public void setCCV(int newValue) {
        // TODO: check limits on CCV value, ie 3 - 4 characters
        ccv = newValue;
    }

    public void setExp(String newValue) {
        // TODO: check limits for valid EXP Date
        exp = newValue;
    }

    public boolean validate() {
        // TODO: check with server if CC can be charged
        return false;
    }

    public boolean process(float amountOfPurchase) {
        // TODO: run the cc purchase}
        return false;
    }
}
