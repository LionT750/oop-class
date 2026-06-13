package models;

public class SalesDigital extends Offer {
    private String customerName;
    private String email;
    private String downloadKey;

    public SalesDigital(Long id, Product product, int quantity,
                        String customerName, String email, String downloadKey) {
        super(id, product, quantity);
        this.customerName = customerName;
        this.email = email;
        this.downloadKey = downloadKey;
    }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDownloadKey() { return downloadKey; }
    public void setDownloadKey(String downloadKey) { this.downloadKey = downloadKey; }

    @Override
    public String toString() {
        return "[Digital] " + super.toString() + " | " + customerName + " | " + email;
    }
}
