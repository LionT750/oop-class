package plugins;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import menu.ContextoFuncionalidade;
import menu.FuncionalidadeMenu;
import menu.Plugin;
import modelos.Produto;
import modelos.VendaDigital;
import modelos.VendaFisica;
import repositorio.Repositorio;
import utilitarios.GeradorId;
import utilitarios.Impressora;

public class PluginDadosSemente implements Plugin {
    private Repositorio repo;

    public PluginDadosSemente(ContextoFuncionalidade contexto) {
        this.repo = contexto.repositorio;
    }

    @Override
    public String getId() { return "plugin-dados-semente"; }

    @Override
    public String getNome() { return "Dados Semente"; }

    @Override
    public String getDescricao() { return "Gerar dados de teste"; }

    @Override
    public List<FuncionalidadeMenu> getFuncionalidades() {
        return Arrays.asList(new GerarDadosTeste());
    }

    private class GerarDadosTeste implements FuncionalidadeMenu {
        public String getId() { return "gerar-dados-teste"; }
        public String getRotulo() { return "Gerar Dados de Teste (10 produtos + vendas)"; }
        public String getDescricao() { return "Criar 10 produtos, ofertas e vendas para teste"; }
        public void executar() {
            Produto[] produtos = new Produto[10];
            String[][] dadosProdutos = {
                {"Notebook", "Laptop de alto desempenho", "4599.90", "15"},
                {"Mouse", "Mouse optico sem fio", "89.90", "50"},
                {"Teclado", "Teclado mecanico RGB", "299.99", "30"},
                {"Monitor", "Monitor 4K 27 polegadas", "2499.00", "10"},
                {"Headset", "Fone de ouvido com cancelamento de ruido", "599.90", "20"},
                {"Webcam", "Webcam Full HD", "349.50", "25"},
                {"Tablet", "Tablet Android 10 polegadas", "1899.00", "12"},
                {"Smartwatch", "Relogio fitness inteligente", "1299.00", "18"},
                {"Caixa de Som", "Caixa de som Bluetooth portatil", "449.90", "22"},
                {"Carregador", "Carregador rapido USB-C 65W", "159.00", "40"}
            };

            for (int i = 0; i < 10; i++) {
                produtos[i] = new Produto(
                    GeradorId.proximoIdProduto(),
                    dadosProdutos[i][0],
                    dadosProdutos[i][1],
                    Double.parseDouble(dadosProdutos[i][2]),
                    Integer.parseInt(dadosProdutos[i][3])
                );
                repo.salvarProduto(produtos[i]);
            }
            Impressora.sucesso("10 produtos criados.");

            for (int i = 0; i < 5; i++) {
                int qtd = (i + 1) * 2;
                if (qtd > produtos[i].getEstoque()) qtd = produtos[i].getEstoque();
                produtos[i].setEstoque(produtos[i].getEstoque() - qtd);
                repo.atualizarProduto(produtos[i]);

                VendaFisica venda = new VendaFisica(
                    GeradorId.proximoIdOferta(), produtos[i], qtd,
                    "Cliente " + (i + 1),
                    "Endereco " + (i + 1) + ", Cidade",
                    "000" + (i + 1) + "-000"
                );
                repo.salvarVendaFisica(venda);
            }
            Impressora.sucesso("5 vendas fisicas criadas.");

            for (int i = 5; i < 10; i++) {
                int qtd = (i - 4) * 1;
                if (qtd > produtos[i].getEstoque()) qtd = produtos[i].getEstoque();
                produtos[i].setEstoque(produtos[i].getEstoque() - qtd);
                repo.atualizarProduto(produtos[i]);

                VendaDigital venda = new VendaDigital(
                    GeradorId.proximoIdOferta(), produtos[i], qtd,
                    "Cliente " + (i + 1),
                    "cliente" + (i + 1) + "@teste.com",
                    UUID.randomUUID().toString().substring(0, 8).toUpperCase()
                );
                repo.salvarVendaDigital(venda);
            }
            Impressora.sucesso("5 vendas digitais criadas.");

            Impressora.sucesso("Dados de teste gerados com sucesso!");
        }
    }
}
