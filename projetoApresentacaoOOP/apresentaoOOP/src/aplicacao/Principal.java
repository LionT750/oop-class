package aplicacao;

import java.util.Scanner;
import javax.swing.SwingUtilities;
import menu.ContextoFuncionalidade;
import menu.FuncionalidadeMenu;
import menu.Menu;
import menu.RegistroPlugin;
import plugins.PluginAdmin;
import plugins.PluginDepuracao;
import plugins.PluginProduto;
import plugins.CarregadorPluginRuntime;
import plugins.PluginVenda;
import plugins.PluginDadosSemente;
import repositorio.Repositorio;
import iu.JanelaApp;

public class Principal {
    public static void main(String[] args) {
        Repositorio repositorio = Repositorio.getInstancia();
        RegistroPlugin registroPlugin = new RegistroPlugin();
        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu(scanner, registroPlugin);
        ContextoFuncionalidade contexto = new ContextoFuncionalidade(repositorio, menu, scanner, registroPlugin);

        registroPlugin.registrarPlugin(new PluginProduto(contexto));
        registroPlugin.registrarPlugin(new PluginVenda(contexto));
        registroPlugin.registrarPlugin(new PluginDadosSemente(contexto));

        CarregadorPluginRuntime carregador = new CarregadorPluginRuntime(contexto);
        carregador.registrarPluginDisponivel("plugin-produto", () -> new PluginProduto(contexto));
        carregador.registrarPluginDisponivel("plugin-venda", () -> new PluginVenda(contexto));
        carregador.registrarPluginDisponivel("plugin-dados-semente", () -> new PluginDadosSemente(contexto));
        carregador.registrarPluginDisponivel("plugin-depuracao", () -> new PluginDepuracao(contexto));
        carregador.registrarPluginDisponivel("plugin-admin", () -> new PluginAdmin(contexto));
        carregador.marcarCarregado("plugin-produto");
        carregador.marcarCarregado("plugin-venda");
        carregador.marcarCarregado("plugin-dados-semente");
        registroPlugin.registrarPlugin(carregador);

        for (var p : registroPlugin.getTodosPlugins()) {
            for (var f : p.getFuncionalidades()) {
                menu.adicionarFuncionalidade(f);
            }
        }

        menu.adicionarFuncionalidade(new FuncionalidadeMenu() {
            public String getId() { return "sair"; }
            public String getRotulo() { return "Sair"; }
            public String getDescricao() { return "Sair da aplicacao"; }
            public int ordem() { return 100; }
            public void executar() { System.exit(0); }
        });

        SwingUtilities.invokeLater(() -> {
            JanelaApp janela = new JanelaApp(menu);
            menu.setAoAlterar(janela::reconstruirBotoes);
            janela.setVisible(true);
        });
    }
}
