package modelos;

import java.time.LocalDateTime;

public class Oferta {
    private Long id;
    private Produto produto;
    private int quantidade;
    private LocalDateTime criadaEm;

    public Oferta(Long id, Produto produto, int quantidade) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
        this.criadaEm = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public LocalDateTime getCriadaEm() { return criadaEm; }

    @Override
    public String toString() {
        return id + " | " + produto.getNome() + " x" + quantidade + " | " + criadaEm.toLocalDate();
    }
}
