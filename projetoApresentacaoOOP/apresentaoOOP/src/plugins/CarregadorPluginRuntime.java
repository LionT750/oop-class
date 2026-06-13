package plugins;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import menu.ContextoFuncionalidade;
import menu.FuncionalidadeMenu;
import menu.Plugin;
import utilitarios.LeitorConsole;
import utilitarios.Impressora;

public class CarregadorPluginRuntime implements Plugin {
    private ContextoFuncionalidade contexto;
    private LeitorConsole leitor;
    private Map<String, FabricaPlugin> catalogo;
    private Set<String> carregados;

    public CarregadorPluginRuntime(ContextoFuncionalidade contexto) {
        this.contexto = contexto;
        this.leitor = new LeitorConsole(contexto.scanner);
        this.catalogo = new LinkedHashMap<>();
        this.carregados = new HashSet<>();
    }

    public void registrarPluginDisponivel(String id, FabricaPlugin fabrica) {
        catalogo.put(id, fabrica);
    }

    public void marcarCarregado(String pluginId) {
        carregados.add(pluginId);
    }

    public void marcarDescarregado(String pluginId) {
        carregados.remove(pluginId);
    }

    @Override
    public String getId() { return "carregador-runtime"; }

    @Override
    public String getNome() { return "Carregador de Plugins"; }

    @Override
    public String getDescricao() { return "Carregar e gerenciar plugins em tempo de execucao"; }

    @Override
    public List<FuncionalidadeMenu> getFuncionalidades() {
        return Arrays.asList(
            new CarregarPlugin(),
            new DescarregarPlugin(),
            new ListarPlugins()
        );
    }

    public interface FabricaPlugin {
        Plugin criar();
    }

    private class CarregarPlugin implements FuncionalidadeMenu {
        public String getId() { return "carregar-plugin"; }
        public String getRotulo() { return "Carregar Plugin"; }
        public String getDescricao() { return "Carregar um plugin por selecao"; }
        public int ordem() { return 3; }
        public void executar() {
            List<Map.Entry<String, FabricaPlugin>> disponiveis = new ArrayList<>();
            for (var entrada : catalogo.entrySet()) {
                if (!carregados.contains(entrada.getKey())) {
                    disponiveis.add(entrada);
                }
            }
            if (disponiveis.isEmpty()) {
                System.out.println("Nenhum plugin disponivel para carregar.");
                return;
            }
            System.out.println("Plugins disponiveis:");
            for (int i = 0; i < disponiveis.size(); i++) {
                Plugin stub = disponiveis.get(i).getValue().criar();
                System.out.println("  " + (i + 1) + " - " + stub.getNome());
            }
            Impressora.prompt("Digite o numero para carregar (0 para cancelar): ");
            int escolha = leitor.lerInt();
            if (escolha == 0) {
                System.out.println("Cancelado.");
                return;
            }
            if (escolha < 1 || escolha > disponiveis.size()) {
                Impressora.erro("Escolha invalida.");
                return;
            }
            Plugin plugin = disponiveis.get(escolha - 1).getValue().criar();
            carregados.add(plugin.getId());
            contexto.menu.carregarPlugin(plugin);
        }
    }

    private class DescarregarPlugin implements FuncionalidadeMenu {
        public String getId() { return "runtime-descarregar-plugin"; }
        public String getRotulo() { return "Descarregar Plugin"; }
        public String getDescricao() { return "Descarregar um plugin por ID"; }
        public int ordem() { return 3; }
        public void executar() {
            Impressora.prompt("Digite o ID do plugin para descarregar (use 'Listar Plugins' primeiro para ver os IDs): ");
            String pluginId = leitor.lerString();
            if (pluginId.isEmpty()) {
                System.out.println("Cancelado.");
                return;
            }
            carregados.remove(pluginId);
            contexto.menu.descarregarPlugin(pluginId);
        }
    }

    private class ListarPlugins implements FuncionalidadeMenu {
        public String getId() { return "listar-plugins-carregados"; }
        public String getRotulo() { return "Listar Plugins"; }
        public String getDescricao() { return "Mostrar todos os plugins carregados"; }
        public int ordem() { return 3; }
        public void executar() {
            var plugins = contexto.registroPlugin.getTodosPlugins();
            if (plugins.isEmpty()) {
                System.out.println("Nenhum plugin carregado.");
                return;
            }
            for (Plugin p : plugins) {
                System.out.println(p.getId() + " | " + p.getNome() + " - " + p.getDescricao());
            }
        }
    }
}
