import java.math.BigDecimal;

enum CarneBrand {
    FRIBOI("Friboi"),
    SWIFT("Swift"),
    SEARA("Seara"),
    SADIA("Sadia"),
    AURORA("Aurora"),
    MARFRIG("Marfrig"),
    MINERVA("Minerva"),
    MASTERBOI("Masterboi"),
    FRISA("Frisa"),
    ESTRELA("Estrela");

    private final String brandName;

    private CarneBrand(final String brandName) {
        this.brandName = brandName;
    }

    public String getBrandName() {
        return brandName;
    }
}


public class Carne extends Produto {
    private double weightPerPackage;
    private double stockWeightKg;
    private CarneBrand brandName;

    public Carne(String name, BigDecimal price, double stock, double weightPerPackage, double stockWeightKg, CarneBrand brandName) {
        super(name, price, stock);
        this.weightPerPackage = weightPerPackage;
        this.stockWeightKg = stockWeightKg;
        this.brandName = brandName;
    }

    public double getWeightPerPackage() {
        return weightPerPackage;
    }

    public void setWeightPerPackage(double weightPerPackage) {
        this.weightPerPackage = weightPerPackage;
    }

    public double getStockWeight() {
        return stockWeightKg;
    }

    public void setStockWeight(double stockWeightKg) {
        this.stockWeightKg = stockWeightKg;
    }

    @Override
    public String showStock(){
        double totalWeight = getStockWeight();
        return (totalWeight + " Kg");
    }

    public CarneBrand getBrandName() {
        return brandName;
    }

    public void setBrandName(CarneBrand brandName) {
        this.brandName = brandName;
    }
}
