package plugins;

import java.util.Arrays;
import java.util.List;
import menu.ContextoFuncionalidade;
import menu.FuncionalidadeMenu;
import menu.Plugin;
import menu.RegistroPlugin;
import repositorio.Repositorio;
import utilitarios.Impressora;

public class PluginDepuracao implements Plugin {
    private Repositorio repo;
    private menu.Menu menu;
    private ContextoFuncionalidade contexto;

    public PluginDepuracao(ContextoFuncionalidade contexto) {
        this.contexto = contexto;
        this.repo = contexto.repositorio;
        this.menu = contexto.menu;
    }

    @Override
    public String getId() { return "plugin-depuracao"; }

    @Override
    public String getNome() { return "Ferramentas de Depuracao"; }

    @Override
    public String getDescricao() { return "Ferramentas de depuracao e estatisticas"; }

    @Override
    public List<FuncionalidadeMenu> getFuncionalidades() {
        return Arrays.asList(
            new MostrarContagemProdutos(),
            new MostrarContagemVendas(),
            new MostrarPluginsRegistrados(),
            new MostrarComandosCarregados(),
            new MostrarEstadoBancoDados()
        );
    }

    private class MostrarContagemProdutos implements FuncionalidadeMenu {
        public String getId() { return "depuracao-contagem-produtos"; }
        public String getRotulo() { return "Mostrar Contagem de Produtos"; }
        public String getDescricao() { return "Exibir numero de produtos"; }
        public int ordem() { return 4; }
        public void executar() {
            System.out.println("Produtos no banco de dados: " + repo.contarProdutos());
        }
    }

    private class MostrarContagemVendas implements FuncionalidadeMenu {
        public String getId() { return "depuracao-contagem-vendas"; }
        public String getRotulo() { return "Mostrar Contagem de Vendas"; }
        public String getDescricao() { return "Exibir numero de vendas"; }
        public int ordem() { return 4; }
        public void executar() {
            System.out.println("Vendas no banco de dados: " + repo.contarVendas());
        }
    }

    private class MostrarPluginsRegistrados implements FuncionalidadeMenu {
        public String getId() { return "depuracao-plugins"; }
        public String getRotulo() { return "Mostrar Plugins Registrados"; }
        public String getDescricao() { return "Listar todos os plugins registrados"; }
        public int ordem() { return 4; }
        public void executar() {
            RegistroPlugin registro = contexto.registroPlugin;
            List<Plugin> plugins = registro.getTodosPlugins();
            if (plugins.isEmpty()) {
                System.out.println("Nenhum plugin registrado.");
                return;
            }
            for (Plugin p : plugins) {
                System.out.println(p.getId() + " | " + p.getNome() + " - " + p.getDescricao());
            }
        }
    }

    private class MostrarComandosCarregados implements FuncionalidadeMenu {
        public String getId() { return "depuracao-comandos"; }
        public String getRotulo() { return "Mostrar Comandos Carregados"; }
        public String getDescricao() { return "Listar todos os comandos carregados"; }
        public int ordem() { return 4; }
        public void executar() {
            List<FuncionalidadeMenu> funcs = menu.getFuncionalidades();
            if (funcs.isEmpty()) {
                System.out.println("Nenhum comando carregado.");
                return;
            }
            for (FuncionalidadeMenu f : funcs) {
                System.out.println(f.getId() + " | " + f.getRotulo() + " - " + f.getDescricao());
            }
            System.out.println("Total: " + funcs.size());
        }
    }

    private class MostrarEstadoBancoDados implements FuncionalidadeMenu {
        public String getId() { return "depuracao-estado-bd"; }
        public String getRotulo() { return "Mostrar Estado do Banco de Dados"; }
        public String getDescricao() { return "Despejar estado completo do banco de dados"; }
        public int ordem() { return 4; }
        public void executar() {
            Impressora.separador();
            System.out.println("=== DESPEJO DO BANCO DE DADOS ===");
            System.out.println("Produtos (" + repo.contarProdutos() + "):");
            for (var p : repo.listarTodosProdutos()) {
                System.out.println("  " + p);
            }
            System.out.println("Vendas Fisicas (" + repo.listarTodasVendasFisicas().size() + "):");
            for (var v : repo.listarTodasVendasFisicas()) {
                System.out.println("  " + v);
            }
            System.out.println("Vendas Digitais (" + repo.listarTodasVendasDigitais().size() + "):");
            for (var v : repo.listarTodasVendasDigitais()) {
                System.out.println("  " + v);
            }
            Impressora.separador();
        }
    }
}
