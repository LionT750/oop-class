package menu;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import utilitarios.Impressora;

public class Menu {
    private List<FuncionalidadeMenu> funcionalidades;
    private boolean executando;
    private Scanner scanner;
    private RegistroPlugin registroPlugin;
    private Runnable aoAlterar;

    public Menu(Scanner scanner, RegistroPlugin registroPlugin) {
        this.funcionalidades = new ArrayList<>();
        this.executando = false;
        this.scanner = scanner;
        this.registroPlugin = registroPlugin;
    }

    public void setAoAlterar(Runnable r) { this.aoAlterar = r; }

    public void adicionarFuncionalidade(FuncionalidadeMenu funcionalidade) {
        funcionalidades.add(funcionalidade);
        if (aoAlterar != null) aoAlterar.run();
    }

    public void removerFuncionalidade(String id) {
        Iterator<FuncionalidadeMenu> it = funcionalidades.iterator();
        while (it.hasNext()) {
            if (it.next().getId().equals(id)) {
                it.remove();
                if (aoAlterar != null) aoAlterar.run();
                return;
            }
        }
    }

    public boolean temFuncionalidade(String id) {
        return funcionalidades.stream().anyMatch(f -> f.getId().equals(id));
    }

    private List<FuncionalidadeMenu> getOrdenadas() {
        List<FuncionalidadeMenu> ordenadas = new ArrayList<>(funcionalidades);
        ordenadas.sort(Comparator.comparingInt(FuncionalidadeMenu::ordem));
        return ordenadas;
    }

    public List<FuncionalidadeMenu> getFuncionalidades() {
        return getOrdenadas();
    }

    public void limparFuncionalidades() {
        funcionalidades.clear();
    }

    public void carregarPlugin(Plugin plugin) {
        registroPlugin.registrarPlugin(plugin);
        for (FuncionalidadeMenu f : plugin.getFuncionalidades()) {
            adicionarFuncionalidade(f);
        }
        Impressora.sucesso("Plugin carregado: " + plugin.getNome());
    }

    public void descarregarPlugin(String pluginId) {
        Plugin plugin = registroPlugin.getPlugin(pluginId);
        if (plugin != null) {
            for (FuncionalidadeMenu f : plugin.getFuncionalidades()) {
                removerFuncionalidade(f.getId());
            }
            registroPlugin.desregistrarPlugin(pluginId);
            Impressora.sucesso("Plugin descarregado: " + plugin.getNome());
        } else {
            Impressora.erro("Plugin nao encontrado: " + pluginId);
        }
    }

    public void parar() {
        this.executando = false;
    }

    public void executar() {
        executando = true;
        while (executando) {
            Impressora.separador();
            Impressora.cabecalho("MENU");
            List<FuncionalidadeMenu> ordenadas = getOrdenadas();
            for (int i = 0; i < ordenadas.size(); i++) {
                FuncionalidadeMenu f = ordenadas.get(i);
                System.out.println((i + 1) + " - " + f.getRotulo());
            }
            System.out.print("Escolha: ");

            int escolha;
            try {
                escolha = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opcao invalida.");
                continue;
            }

            if (escolha >= 1 && escolha <= ordenadas.size()) {
                FuncionalidadeMenu selecionada = ordenadas.get(escolha - 1);
                Impressora.cabecalho(selecionada.getRotulo());
                selecionada.executar();
            } else if (escolha == 0) {
                parar();
            } else {
                System.out.println("Opcao invalida.");
            }
        }
        System.out.println("Encerrando. Ate logo!");
    }
}
