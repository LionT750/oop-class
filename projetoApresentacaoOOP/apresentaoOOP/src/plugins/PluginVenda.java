package plugins;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import menu.ContextoFuncionalidade;
import menu.FuncionalidadeMenu;
import menu.Plugin;
import modelos.Oferta;
import modelos.Produto;
import modelos.VendaDigital;
import modelos.VendaFisica;
import repositorio.Repositorio;
import utilitarios.LeitorConsole;
import utilitarios.GeradorId;
import utilitarios.Impressora;

public class PluginVenda implements Plugin {
    private LeitorConsole leitor;
    private Repositorio repo;

    public PluginVenda(ContextoFuncionalidade contexto) {
        this.leitor = new LeitorConsole(contexto.scanner);
        this.repo = contexto.repositorio;
    }

    @Override
    public String getId() { return "plugin-venda"; }

    @Override
    public String getNome() { return "Gerenciamento de Vendas"; }

    @Override
    public String getDescricao() { return "Gerenciar vendas (fisicas e digitais)"; }

    @Override
    public List<FuncionalidadeMenu> getFuncionalidades() {
        return Arrays.asList(
            new CriarVendaFisica(),
            new CriarVendaDigital(),
            new ListarVendasFisicas(),
            new ListarVendasDigitais(),
            new ListarTodasVendas(),
            new BuscarVenda(),
            new ContarVendas()
        );
    }

    private Produto selecionarProduto() {
        List<Produto> produtos = repo.listarTodosProdutos();
        if (produtos.isEmpty()) {
            Impressora.erro("Nenhum produto disponivel.");
            return null;
        }
        System.out.println("Produtos disponiveis:");
        for (Produto p : produtos) {
            System.out.println("  " + p.getId() + " - " + p.getNome() + " (R$" + String.format("%.2f", p.getPreco()) + ") estoque: " + p.getEstoque());
        }
        Impressora.prompt("Digite o ID do produto da lista acima (0 para cancelar): ");
        long productId = leitor.lerInt();
        if (productId == 0) { System.out.println("Cancelado."); return null; }
        Optional<Produto> opt = repo.buscarProdutoPorId(productId);
        if (opt.isEmpty()) {
            Impressora.erro("Produto nao encontrado.");
            return null;
        }
        return opt.get();
    }

    private class CriarVendaFisica implements FuncionalidadeMenu {
        public String getId() { return "criar-venda-fisica"; }
        public String getRotulo() { return "Criar Venda Fisica"; }
        public String getDescricao() { return "Criar uma nova venda fisica"; }
        public int ordem() { return 1; }
        public void executar() {
            Produto produto = selecionarProduto();
            if (produto == null) return;

            Impressora.prompt("Quantidade (max " + produto.getEstoque() + " disponivel, 0 para cancelar): ");
            int quantidade = leitor.lerInt();
            if (quantidade == 0) { System.out.println("Cancelado."); return; }
            if (quantidade > produto.getEstoque()) {
                Impressora.erro("Apenas " + produto.getEstoque() + " unidades em estoque. Venda cancelada.");
                return;
            }
            if (quantidade < 0) {
                Impressora.erro("A quantidade deve ser positiva.");
                return;
            }
            Impressora.prompt("Nome do cliente: ");
            String nomeCliente = leitor.lerString();
            Impressora.prompt("Endereco de entrega (rua, numero): ");
            String endereco = leitor.lerString();
            Impressora.prompt("Codigo postal (ex.: 12345-678): ");
            String codigoPostal = leitor.lerString();

            produto.setEstoque(produto.getEstoque() - quantidade);
            repo.atualizarProduto(produto);

            VendaFisica venda = new VendaFisica(GeradorId.proximoIdOferta(), produto, quantidade,
                                                  nomeCliente, endereco, codigoPostal);
            repo.salvarVendaFisica(venda);
            Impressora.sucesso("Venda fisica #" + venda.getId() + " criada.");
        }
    }

    private class CriarVendaDigital implements FuncionalidadeMenu {
        public String getId() { return "criar-venda-digital"; }
        public String getRotulo() { return "Criar Venda Digital"; }
        public String getDescricao() { return "Criar uma nova venda digital"; }
        public int ordem() { return 1; }
        public void executar() {
            Produto produto = selecionarProduto();
            if (produto == null) return;

            Impressora.prompt("Quantidade (max " + produto.getEstoque() + " disponivel, 0 para cancelar): ");
            int quantidade = leitor.lerInt();
            if (quantidade == 0) { System.out.println("Cancelado."); return; }
            if (quantidade > produto.getEstoque()) {
                Impressora.erro("Apenas " + produto.getEstoque() + " unidades em estoque. Venda cancelada.");
                return;
            }
            if (quantidade < 0) {
                Impressora.erro("A quantidade deve ser positiva.");
                return;
            }
            Impressora.prompt("Nome do cliente: ");
            String nomeCliente = leitor.lerString();
            Impressora.prompt("Email do cliente: ");
            String email = leitor.lerString();
            String chaveDownload = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            produto.setEstoque(produto.getEstoque() - quantidade);
            repo.atualizarProduto(produto);

            VendaDigital venda = new VendaDigital(GeradorId.proximoIdOferta(), produto, quantidade,
                                                    nomeCliente, email, chaveDownload);
            repo.salvarVendaDigital(venda);
            Impressora.sucesso("Venda digital #" + venda.getId() + " criada. Chave de download: " + chaveDownload);
        }
    }

    private class ListarVendasFisicas implements FuncionalidadeMenu {
        public String getId() { return "listar-vendas-fisicas"; }
        public String getRotulo() { return "Listar Vendas Fisicas"; }
        public String getDescricao() { return "Listar todas as vendas fisicas"; }
        public int ordem() { return 1; }
        public void executar() {
            List<VendaFisica> vendas = repo.listarTodasVendasFisicas();
            if (vendas.isEmpty()) {
                System.out.println("Nenhuma venda fisica.");
                return;
            }
            for (VendaFisica v : vendas) {
                System.out.println(v);
            }
            System.out.println("Total: " + vendas.size());
        }
    }

    private class ListarVendasDigitais implements FuncionalidadeMenu {
        public String getId() { return "listar-vendas-digitais"; }
        public String getRotulo() { return "Listar Vendas Digitais"; }
        public String getDescricao() { return "Listar todas as vendas digitais"; }
        public int ordem() { return 1; }
        public void executar() {
            List<VendaDigital> vendas = repo.listarTodasVendasDigitais();
            if (vendas.isEmpty()) {
                System.out.println("Nenhuma venda digital.");
                return;
            }
            for (VendaDigital v : vendas) {
                System.out.println(v);
            }
            System.out.println("Total: " + vendas.size());
        }
    }

    private class ListarTodasVendas implements FuncionalidadeMenu {
        public String getId() { return "listar-todas-vendas"; }
        public String getRotulo() { return "Listar Todas as Vendas"; }
        public String getDescricao() { return "Listar todas as vendas"; }
        public int ordem() { return 1; }
        public void executar() {
            List<Oferta> vendas = repo.listarTodasVendas();
            if (vendas.isEmpty()) {
                System.out.println("Nenhuma venda.");
                return;
            }
            for (Oferta v : vendas) {
                System.out.println(v);
            }
            System.out.println("Total: " + vendas.size());
        }
    }

    private class BuscarVenda implements FuncionalidadeMenu {
        public String getId() { return "buscar-venda"; }
        public String getRotulo() { return "Buscar Venda"; }
        public String getDescricao() { return "Buscar uma venda por ID"; }
        public int ordem() { return 1; }
        public void executar() {
            Impressora.prompt("ID da venda para buscar (0 para cancelar): ");
            long id = leitor.lerInt();
            if (id == 0) { System.out.println("Cancelado."); return; }
            Optional<Oferta> opt = repo.buscarVendaPorId(id);
            if (opt.isPresent()) {
                System.out.println(opt.get());
            } else {
                Impressora.erro("Venda nao encontrada.");
            }
        }
    }

    private class ContarVendas implements FuncionalidadeMenu {
        public String getId() { return "contar-vendas"; }
        public String getRotulo() { return "Contar Vendas"; }
        public String getDescricao() { return "Mostrar numero de vendas"; }
        public int ordem() { return 1; }
        public void executar() {
            System.out.println("Total de vendas: " + repo.contarVendas());
        }
    }
}
