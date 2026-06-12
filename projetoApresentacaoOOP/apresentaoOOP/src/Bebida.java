import java.math.BigDecimal;

enum BebidaBrand {
    SKOL("Skol"),
    BRAHMA("Brahma"),
    ANTARCTICA("Antarctica"),
    ITAIPAVA("Itaipava"),
    AMSTEL("Amstel"),
    HEINEKEN("Heineken"),
    COCA_COLA("Coca-Cola"),
    GUARANA_ANTARCTICA("Guaraná Antarctica"),
    SUVINIL("Dois Cunhados"), // Example soda/brand
    KUAT("Kuat"),
    DEL_VALLE("Del Valle"),
    DOLLY("Dolly");


    private final String brandName;

    private BebidaBrand(final String brandName) {
        this.brandName = brandName;
    }

    public String getBrandName() {
        return brandName;
    }
}

public class Bebida extends Produto {
    private double volumePerBottle;
    private double volumeStock;
    private BebidaBrand brandName;


    public Bebida(String name, BigDecimal price, double stock, double volumePerBottle, double volumeStock, BebidaBrand brandName) {
        super(name, price, stock);
        this.volumePerBottle = volumePerBottle;
        this.volumeStock = volumeStock;
        this.brandName = brandName;
    }


    public double getVolumePerBottle() {
        return volumePerBottle;
    }


    public void setVolumePerBottle(double volumePerBottle) {
        this.volumePerBottle = volumePerBottle;
    }


    public double getVolumeStock() {
        return volumeStock;
    }

    @Override
    public String showStock(){
        int bottles = (int) (volumeStock / volumePerBottle);
        return (bottles + " garrafas");
    }

    public void setVolumeStock(double volumeStock) {
        this.volumeStock = volumeStock;
    }


    public BebidaBrand getBrandName() {
        return brandName;
    }


    public void setBrandName(BebidaBrand brandName) {
        this.brandName = brandName;
    }
}
