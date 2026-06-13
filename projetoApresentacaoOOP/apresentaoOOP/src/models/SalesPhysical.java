package models;

public class SalesPhysical extends Offer {
    private String customerName;
    private String shippingAddress;
    private String postalCode;

    public SalesPhysical(Long id, Product product, int quantity,
                         String customerName, String shippingAddress, String postalCode) {
        super(id, product, quantity);
        this.customerName = customerName;
        this.shippingAddress = shippingAddress;
        this.postalCode = postalCode;
    }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    @Override
    public String toString() {
        return "[Physical] " + super.toString() + " | " + customerName + " | " + shippingAddress;
    }
}
