package plugins;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import menu.ContextoFuncionalidade;
import menu.FuncionalidadeMenu;
import menu.Plugin;
import modelos.Produto;
import repositorio.Repositorio;
import utilitarios.LeitorConsole;
import utilitarios.GeradorId;
import utilitarios.Impressora;

public class PluginProduto implements Plugin {
    private LeitorConsole leitor;
    private Repositorio repo;

    public PluginProduto(ContextoFuncionalidade contexto) {
        this.leitor = new LeitorConsole(contexto.scanner);
        this.repo = contexto.repositorio;
    }

    @Override
    public String getId() { return "plugin-produto"; }

    @Override
    public String getNome() { return "Gerenciamento de Produtos"; }

    @Override
    public String getDescricao() { return "Operacoes CRUD para produtos"; }

    @Override
    public List<FuncionalidadeMenu> getFuncionalidades() {
        return Arrays.asList(
            new CriarProduto(),
            new ListarProdutos(),
            new BuscarProduto(),
            new AtualizarProduto(),
            new DeletarProduto(),
            new DesativarProduto(),
            new ContarProdutos()
        );
    }

    private class CriarProduto implements FuncionalidadeMenu {
        public String getId() { return "criar-produto"; }
        public String getRotulo() { return "Criar Produto"; }
        public String getDescricao() { return "Criar um novo produto"; }
        public int ordem() { return 2; }
        public void executar() {
            Impressora.prompt("Nome do produto: ");
            String nome = leitor.lerString();
            Impressora.prompt("Descricao (breve): ");
            String descricao = leitor.lerString();
            Impressora.prompt("Preco (ex.: 29,99): ");
            double preco = leitor.lerDouble();
            Impressora.prompt("Estoque inicial (unidades): ");
            int estoque = leitor.lerInt();

            Produto produto = new Produto(GeradorId.proximoIdProduto(), nome, descricao, preco, estoque);
            repo.salvarProduto(produto);
            Impressora.sucesso("Produto #" + produto.getId() + " criado.");
        }
    }

    private class ListarProdutos implements FuncionalidadeMenu {
        public String getId() { return "listar-produtos"; }
        public String getRotulo() { return "Listar Produtos"; }
        public String getDescricao() { return "Listar todos os produtos"; }
        public int ordem() { return 2; }
        public void executar() {
            List<Produto> produtos = repo.listarTodosProdutos();
            if (produtos.isEmpty()) {
                System.out.println("Nenhum produto encontrado.");
                return;
            }
            for (Produto p : produtos) {
                System.out.println(p);
            }
            System.out.println("Total: " + produtos.size());
        }
    }

    private class BuscarProduto implements FuncionalidadeMenu {
        public String getId() { return "buscar-produto"; }
        public String getRotulo() { return "Buscar Produto"; }
        public String getDescricao() { return "Buscar produto por ID"; }
        public int ordem() { return 2; }
        public void executar() {
            Impressora.prompt("ID do produto para buscar (0 para cancelar): ");
            long id = leitor.lerInt();
            if (id == 0) { System.out.println("Cancelado."); return; }
            Optional<Produto> opt = repo.buscarProdutoPorId(id);
            if (opt.isPresent()) {
                System.out.println(opt.get());
                Produto p = opt.get();
                System.out.println("  Descricao: " + p.getDescricao());
                System.out.println("  Estoque: " + p.getEstoque());
                System.out.println("  Ativo: " + p.isAtivo());
            } else {
                Impressora.erro("Nenhum produto com ID " + id + " encontrado.");
            }
        }
    }

    private class AtualizarProduto implements FuncionalidadeMenu {
        public String getId() { return "atualizar-produto"; }
        public String getRotulo() { return "Atualizar Produto"; }
        public String getDescricao() { return "Atualizar detalhes do produto"; }
        public int ordem() { return 2; }
        public void executar() {
            Impressora.prompt("ID do produto para atualizar (0 para cancelar): ");
            long id = leitor.lerInt();
            if (id == 0) { System.out.println("Cancelado."); return; }
            Optional<Produto> opt = repo.buscarProdutoPorId(id);
            if (opt.isEmpty()) {
                Impressora.erro("Nenhum produto com ID " + id + " encontrado.");
                return;
            }
            Produto produto = opt.get();
            System.out.println("Atual: " + produto);
            Impressora.prompt("Novo nome (Enter = manter \"" + produto.getNome() + "\"): ");
            String nome = leitor.lerStringOpcional();
            if (nome.isEmpty()) nome = produto.getNome();
            Impressora.prompt("Nova descricao (Enter = manter \"" + produto.getDescricao() + "\"): ");
            String descricao = leitor.lerStringOpcional();
            if (descricao.isEmpty()) descricao = produto.getDescricao();
            Impressora.prompt("Novo preco (Enter = manter " + produto.getPreco() + "): ");
            String precoStr = leitor.lerStringOpcional();
            double preco = precoStr.isEmpty() ? produto.getPreco() : Double.parseDouble(precoStr);
            Impressora.prompt("Novo estoque (Enter = manter " + produto.getEstoque() + "): ");
            String estoqueStr = leitor.lerStringOpcional();
            int estoque = estoqueStr.isEmpty() ? produto.getEstoque() : Integer.parseInt(estoqueStr);

            produto.setNome(nome);
            produto.setDescricao(descricao);
            produto.setPreco(preco);
            produto.setEstoque(estoque);
            repo.atualizarProduto(produto);
            Impressora.sucesso("Produto atualizado.");
        }
    }

    private class DeletarProduto implements FuncionalidadeMenu {
        public String getId() { return "deletar-produto"; }
        public String getRotulo() { return "Deletar Produto"; }
        public String getDescricao() { return "Deletar um produto"; }
        public int ordem() { return 2; }
        public void executar() {
            Impressora.prompt("ID do produto para deletar (0 para cancelar): ");
            long id = leitor.lerInt();
            if (id == 0) { System.out.println("Cancelado."); return; }
            Optional<Produto> opt = repo.buscarProdutoPorId(id);
            if (opt.isEmpty()) {
                Impressora.erro("Nenhum produto com ID " + id + " encontrado.");
                return;
            }
            repo.deletarProduto(opt.get());
            Impressora.sucesso("Produto deletado.");
        }
    }

    private class DesativarProduto implements FuncionalidadeMenu {
        public String getId() { return "desativar-produto"; }
        public String getRotulo() { return "Desativar Produto"; }
        public String getDescricao() { return "Alternar status ativo do produto"; }
        public int ordem() { return 2; }
        public void executar() {
            Impressora.prompt("ID do produto (0 para cancelar): ");
            long id = leitor.lerInt();
            if (id == 0) { System.out.println("Cancelado."); return; }
            Optional<Produto> opt = repo.buscarProdutoPorId(id);
            if (opt.isEmpty()) {
                Impressora.erro("Nenhum produto com ID " + id + " encontrado.");
                return;
            }
            Produto p = opt.get();
            p.setAtivo(!p.isAtivo());
            repo.atualizarProduto(p);
            Impressora.sucesso("Produto agora esta " + (p.isAtivo() ? "Ativo" : "Inativo"));
        }
    }

    private class ContarProdutos implements FuncionalidadeMenu {
        public String getId() { return "contar-produtos"; }
        public String getRotulo() { return "Contar Produtos"; }
        public String getDescricao() { return "Mostrar numero de produtos"; }
        public int ordem() { return 2; }
        public void executar() {
            System.out.println("Total de produtos: " + repo.contarProdutos());
        }
    }
}
