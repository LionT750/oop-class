package modelos;

public class VendaDigital extends Oferta {
    private String nomeCliente;
    private String email;
    private String chaveDownload;

    public VendaDigital(Long id, Produto produto, int quantidade,
                         String nomeCliente, String email, String chaveDownload) {
        super(id, produto, quantidade);
        this.nomeCliente = nomeCliente;
        this.email = email;
        this.chaveDownload = chaveDownload;
    }

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getChaveDownload() { return chaveDownload; }
    public void setChaveDownload(String chaveDownload) { this.chaveDownload = chaveDownload; }

    @Override
    public String toString() {
        return "[Digital] " + super.toString() + " | " + nomeCliente + " | " + email;
    }
}
