package modelos;

public class Produto {
    private Long id;
    private String nome;
    private String descricao;
    private double preco;
    private int estoque;
    private boolean ativo;

    public Produto(Long id, String nome, String descricao, double preco, int estoque) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.ativo = true;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
    public int getEstoque() { return estoque; }
    public void setEstoque(int estoque) { this.estoque = estoque; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    @Override
    public String toString() {
        return id + " | " + nome + " | R$" + String.format("%.2f", preco) + " | estoque: " + estoque + " | " + (ativo ? "Ativo" : "Inativo");
    }
}
