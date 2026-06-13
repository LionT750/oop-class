package repositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import dados.BancoDeDadosEmMemoria;
import modelos.Oferta;
import modelos.Produto;
import modelos.VendaDigital;
import modelos.VendaFisica;

public class Repositorio {
    private static Repositorio instancia;
    private BancoDeDadosEmMemoria db;

    private Repositorio() {
        this.db = BancoDeDadosEmMemoria.getInstancia();
    }

    public static Repositorio getInstancia() {
        if (instancia == null) {
            instancia = new Repositorio();
        }
        return instancia;
    }

    public void salvarProduto(Produto produto) {
        db.produtos.add(produto);
    }

    public Optional<Produto> buscarProdutoPorId(Long id) {
        return db.produtos.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public List<Produto> listarTodosProdutos() {
        return new ArrayList<>(db.produtos);
    }

    public void deletarProduto(Produto produto) {
        db.produtos.remove(produto);
    }

    public void atualizarProduto(Produto atualizado) {
        for (int i = 0; i < db.produtos.size(); i++) {
            if (db.produtos.get(i).getId().equals(atualizado.getId())) {
                db.produtos.set(i, atualizado);
                return;
            }
        }
    }

    public int contarProdutos() {
        return db.produtos.size();
    }

    public void limparProdutos() {
        db.produtos.clear();
    }

    public void salvarVendaFisica(VendaFisica venda) {
        db.vendasFisicas.add(venda);
        db.ofertas.add(venda);
    }

    public void salvarVendaDigital(VendaDigital venda) {
        db.vendasDigitais.add(venda);
        db.ofertas.add(venda);
    }

    public List<VendaFisica> listarTodasVendasFisicas() {
        return new ArrayList<>(db.vendasFisicas);
    }

    public List<VendaDigital> listarTodasVendasDigitais() {
        return new ArrayList<>(db.vendasDigitais);
    }

    public List<Oferta> listarTodasVendas() {
        return new ArrayList<>(db.ofertas);
    }

    public Optional<Oferta> buscarVendaPorId(Long id) {
        return db.ofertas.stream().filter(s -> s.getId().equals(id)).findFirst();
    }

    public int contarVendas() {
        return db.ofertas.size();
    }

    public void limparVendas() {
        db.ofertas.clear();
        db.vendasFisicas.clear();
        db.vendasDigitais.clear();
    }

    public void reiniciarBancoDeDados() {
        db.produtos.clear();
        db.ofertas.clear();
        db.vendasFisicas.clear();
        db.vendasDigitais.clear();
    }
}
