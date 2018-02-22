package chapter2.web;

final public class Response {
    private String account;
    private String asset;
    private long amount;
    private String purpose;

    public Response(final String account, final String asset, final long amount, final String purpose) {
        this.account = account;
        this.asset = asset;
        this.amount = amount;
        this.purpose = purpose;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(final String account) {
        this.account = account;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(final String asset) {
        this.asset = asset;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(final long amount) {
        this.amount = amount;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(final String purpose) {
        this.purpose = purpose;
    }
}
