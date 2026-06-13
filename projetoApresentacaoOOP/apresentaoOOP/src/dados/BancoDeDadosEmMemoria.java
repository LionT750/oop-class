package dados;

import java.util.ArrayList;
import java.util.List;
import modelos.Oferta;
import modelos.Produto;
import modelos.VendaDigital;
import modelos.VendaFisica;

public class BancoDeDadosEmMemoria {
    private static BancoDeDadosEmMemoria instancia;

    public List<Produto> produtos = new ArrayList<>();
    public List<Oferta> ofertas = new ArrayList<>();
    public List<VendaFisica> vendasFisicas = new ArrayList<>();
    public List<VendaDigital> vendasDigitais = new ArrayList<>();

    private BancoDeDadosEmMemoria() {}

    public static BancoDeDadosEmMemoria getInstancia() {
        if (instancia == null) {
            instancia = new BancoDeDadosEmMemoria();
        }
        return instancia;
    }
}
