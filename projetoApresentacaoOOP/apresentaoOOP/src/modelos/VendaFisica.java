package modelos;

public class VendaFisica extends Oferta {
    private String nomeCliente;
    private String enderecoEntrega;
    private String codigoPostal;

    public VendaFisica(Long id, Produto produto, int quantidade,
                         String nomeCliente, String enderecoEntrega, String codigoPostal) {
        super(id, produto, quantidade);
        this.nomeCliente = nomeCliente;
        this.enderecoEntrega = enderecoEntrega;
        this.codigoPostal = codigoPostal;
    }

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }
    public String getEnderecoEntrega() { return enderecoEntrega; }
    public void setEnderecoEntrega(String enderecoEntrega) { this.enderecoEntrega = enderecoEntrega; }
    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }

    @Override
    public String toString() {
        return "[Fisica] " + super.toString() + " | " + nomeCliente + " | " + enderecoEntrega;
    }
}
