package plugins;

import java.util.Arrays;
import java.util.List;
import menu.ContextoFuncionalidade;
import menu.FuncionalidadeMenu;
import menu.Plugin;
import menu.RegistroPlugin;
import repositorio.Repositorio;
import utilitarios.LeitorConsole;
import utilitarios.Impressora;

public class PluginAdmin implements Plugin {
    private ContextoFuncionalidade contexto;
    private LeitorConsole leitor;
    private Repositorio repo;
    private menu.Menu menu;
    private RegistroPlugin registroPlugin;

    public PluginAdmin(ContextoFuncionalidade contexto) {
        this.contexto = contexto;
        this.leitor = new LeitorConsole(contexto.scanner);
        this.repo = contexto.repositorio;
        this.menu = contexto.menu;
        this.registroPlugin = contexto.registroPlugin;
    }

    @Override
    public String getId() { return "plugin-admin"; }

    @Override
    public String getNome() { return "Ferramentas Admin"; }

    @Override
    public String getDescricao() { return "Operacoes administrativas"; }

    @Override
    public List<FuncionalidadeMenu> getFuncionalidades() {
        return Arrays.asList(
            new LimparProdutos(),
            new LimparVendas(),
            new ReiniciarBancoDados(),
            new DescarregarPlugin(),
            new DesabilitarComando(),
            new HabilitarComando()
        );
    }

    private class LimparProdutos implements FuncionalidadeMenu {
        public String getId() { return "limpar-produtos"; }
        public String getRotulo() { return "Limpar Produtos"; }
        public String getDescricao() { return "Remover todos os produtos"; }
        public int ordem() { return 5; }
        public void executar() {
            Impressora.prompt("Remover todos os " + repo.contarProdutos() + " produtos? Digite 'sim' para confirmar: ");
            String confirmacao = contexto.scanner.nextLine().trim().toLowerCase();
            if (confirmacao.equals("sim")) {
                repo.limparProdutos();
                Impressora.sucesso("Todos os produtos foram limpos.");
            } else {
                System.out.println("Cancelado.");
            }
        }
    }

    private class LimparVendas implements FuncionalidadeMenu {
        public String getId() { return "limpar-vendas"; }
        public String getRotulo() { return "Limpar Vendas"; }
        public String getDescricao() { return "Remover todas as vendas"; }
        public int ordem() { return 5; }
        public void executar() {
            Impressora.prompt("Remover todas as " + repo.contarVendas() + " vendas? Digite 'sim' para confirmar: ");
            String confirmacao = contexto.scanner.nextLine().trim().toLowerCase();
            if (confirmacao.equals("sim")) {
                repo.limparVendas();
                Impressora.sucesso("Todas as vendas foram limpas.");
            } else {
                System.out.println("Cancelado.");
            }
        }
    }

    private class ReiniciarBancoDados implements FuncionalidadeMenu {
        public String getId() { return "reiniciar-bd"; }
        public String getRotulo() { return "Reiniciar Banco de Dados"; }
        public String getDescricao() { return "Reiniciar banco de dados inteiro"; }
        public int ordem() { return 5; }
        public void executar() {
            Impressora.prompt("Reiniciar banco de dados inteiro (produtos + vendas)? Digite 'sim' para confirmar: ");
            String confirmacao = contexto.scanner.nextLine().trim().toLowerCase();
            if (confirmacao.equals("sim")) {
                repo.reiniciarBancoDeDados();
                Impressora.sucesso("Banco de dados reiniciado.");
            } else {
                System.out.println("Cancelado.");
            }
        }
    }

    private class DescarregarPlugin implements FuncionalidadeMenu {
        public String getId() { return "descarregar-plugin"; }
        public String getRotulo() { return "Descarregar Plugin"; }
        public String getDescricao() { return "Descarregar um plugin registrado"; }
        public int ordem() { return 5; }
        public void executar() {
            List<Plugin> plugins = registroPlugin.getTodosPlugins();
            if (plugins.isEmpty()) {
                System.out.println("Nenhum plugin para descarregar.");
                return;
            }
            System.out.println("Plugins atualmente carregados:");
            for (int i = 0; i < plugins.size(); i++) {
                Plugin p = plugins.get(i);
                System.out.println("  " + (i + 1) + " - " + p.getNome() + " (" + p.getId() + ")");
            }
            Impressora.prompt("Digite o numero do plugin para descarregar (0 para cancelar): ");
            int escolha = leitor.lerInt();
            if (escolha == 0) { System.out.println("Cancelado."); return; }
            if (escolha < 1 || escolha > plugins.size()) {
                Impressora.erro("Escolha invalida.");
                return;
            }
            String alvoId = plugins.get(escolha - 1).getId();
            for (Plugin p : registroPlugin.getTodosPlugins()) {
                if (p instanceof CarregadorPluginRuntime) {
                    ((CarregadorPluginRuntime) p).marcarDescarregado(alvoId);
                    break;
                }
            }
            menu.descarregarPlugin(alvoId);
        }
    }

    private class DesabilitarComando implements FuncionalidadeMenu {
        public String getId() { return "desabilitar-comando"; }
        public String getRotulo() { return "Desabilitar Comando"; }
        public String getDescricao() { return "Remover um comando do menu"; }
        public int ordem() { return 5; }
        public void executar() {
            List<FuncionalidadeMenu> funcs = menu.getFuncionalidades();
            if (funcs.isEmpty()) {
                System.out.println("Nenhum comando para desabilitar.");
                return;
            }
            System.out.println("Comandos atualmente ativos:");
            for (int i = 0; i < funcs.size(); i++) {
                FuncionalidadeMenu f = funcs.get(i);
                System.out.println("  " + (i + 1) + " - " + f.getRotulo() + " (" + f.getId() + ")");
            }
            Impressora.prompt("Digite o numero do comando para desabilitar (0 para cancelar): ");
            int escolha = leitor.lerInt();
            if (escolha == 0) { System.out.println("Cancelado."); return; }
            if (escolha < 1 || escolha > funcs.size()) {
                Impressora.erro("Escolha invalida.");
                return;
            }
            menu.removerFuncionalidade(funcs.get(escolha - 1).getId());
            Impressora.sucesso("Comando desabilitado.");
        }
    }

    private class HabilitarComando implements FuncionalidadeMenu {
        public String getId() { return "habilitar-comando"; }
        public String getRotulo() { return "Habilitar Comando"; }
        public String getDescricao() { return "Adicionar um comando de um plugin de volta ao menu"; }
        public int ordem() { return 5; }
        public void executar() {
            List<Plugin> plugins = registroPlugin.getTodosPlugins();
            boolean encontrado = false;
            System.out.println("Comandos disponiveis dos plugins:");
            for (Plugin p : plugins) {
                for (FuncionalidadeMenu f : p.getFuncionalidades()) {
                    if (!menu.temFuncionalidade(f.getId())) {
                        System.out.println(f.getId() + " - " + f.getRotulo() + " (" + p.getNome() + ")");
                        encontrado = true;
                    }
                }
            }
            if (!encontrado) {
                System.out.println("Nenhum comando desabilitado disponivel.");
                return;
            }
            Impressora.prompt("Digite o ID do comando (acima) para reabilitar (vazio para cancelar): ");
            String id = leitor.lerString();
            if (id.isEmpty()) { System.out.println("Cancelado."); return; }
            for (Plugin p : plugins) {
                for (FuncionalidadeMenu f : p.getFuncionalidades()) {
                    if (f.getId().equals(id) && !menu.temFuncionalidade(f.getId())) {
                        menu.adicionarFuncionalidade(f);
                        Impressora.sucesso("Comando habilitado: " + f.getRotulo());
                        return;
                    }
                }
            }
            Impressora.erro("Comando nao encontrado ou ja habilitado.");
        }
    }
}
