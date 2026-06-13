# Framework de CLI Dinamico com Plugins

Versao: 0.1

Linguagem: Java

Dependencias: Nenhuma

Armazenamento: Em Memoria

Interface: CLI / Console

Foco em Padroes:

* OOP
* Padrao Repository
* Padrao Command
* Arquitetura de Plugins
* Composicao Dinamica de Menu

---

# 1. Proposito

Construir uma aplicacao Java pequena que demonstre um framework CLI dinamico.

A aplicacao deve suportar:

* Geracao dinamica de menu
* Registro runtime de funcionalidades
* Modulos de funcionalidade baseados em plugins
* Persistencia em memoria
* Abstração de repositorio
* Zero bibliotecas externas

O dominio de negocio usado para demonstracao e um sistema de vendas simples.

O framework em si deve permanecer independente de dominio.

---

# 2. Objetivos de Design

## Objetivos Primarios

* Nenhuma implementacao de menu com switch/case
* Nenhuma numeracao de menu hardcoded
* Adicao de comandos em tempo de execucao
* Adicao de plugins em tempo de execucao
* Arquitetura simples
* Codigo-base educacional
* Facil expansao futura

## Objetivos Secundarios

* Simular sistemas reais de plugins
* Manter modulos isolados
* Suportar futuras camadas de persistencia
* Suportar futuras permissoes
* Suportar futuros eventos

---

# 3. Arquitetura de Alto Nivel

Principal
↓
Mecanismo de Menu
↓
Plugins
↓
Funcionalidades (Comandos)
↓
Repositorio
↓
BancoDeDadosEmMemoria

---

# 4. Estrutura de Pacotes

src/

├── aplicacao
│   └── Principal.java
│
├── modelos
│   ├── Produto.java
│   ├── Oferta.java
│   ├── VendaFisica.java
│   └── VendaDigital.java
│
├── dados
│   └── BancoDeDadosEmMemoria.java
│
├── repositorio
│   └── Repositorio.java
│
├── menu
│   ├── Menu.java
│   ├── FuncionalidadeMenu.java
│   ├── Plugin.java
│   ├── ContextoFuncionalidade.java
│   └── RegistroPlugin.java
│
├── plugins
│   ├── PluginProduto.java
│   ├── PluginVenda.java
│   ├── PluginDepuracao.java
│   ├── PluginAdmin.java
│   └── CarregadorPluginRuntime.java
│
├── utilitarios
│   ├── LeitorConsole.java
│   ├── GeradorId.java
│   └── Impressora.java
│
└── eventos
    ├── BarramentoEventos.java
    ├── Evento.java
    └── ouvintes

---

# 5. Modelo de Dominio

## Produto

Representa um produto disponivel para venda.

Campos:

Long id
String nome
String descricao
double preco
int estoque
boolean ativo

---

## Oferta

Classe base.

Campos:

Long id
Produto produto
int quantidade
LocalDateTime criadaEm

---

## VendaFisica

Estende Oferta.

Campos:

String nomeCliente
String enderecoEntrega
String codigoPostal

---

## VendaDigital

Estende Oferta.

Campos:

String nomeCliente
String email
String chaveDownload

---

# 6. BancoDeDadosEmMemoria

Proposito:

Armazenar todos os dados da aplicacao.

Regras:

* Apenas dados
* Sem logica de negocios
* Sem validacao
* Sem busca
* Sem filtragem

Estrutura:

class BancoDeDadosEmMemoria {

```
ArrayList<Produto> produtos;

ArrayList<Oferta> ofertas;

ArrayList<VendaFisica> vendasFisicas;

ArrayList<VendaDigital> vendasDigitais;
```

}

---

# 7. Camada de Repositorio

Proposito:

Ponto unico de acesso a persistencia.

Menu e plugins nunca tocam colecoes do banco de dados diretamente.

Responsabilidades:

salvarProduto()

buscarProdutoPorId()

listarTodosProdutos()

deletarProduto()

atualizarProduto()

salvarVendaFisica()

salvarVendaDigital()

listarTodasVendasFisicas()

listarTodasVendasDigitais()

contarProdutos()

contarVendas()

limparProdutos()

limparVendas()

reiniciarBancoDeDados()

Implementacoes Futuras:

RepositorioMemoria
RepositorioArquivo
RepositorioSql
RepositorioApi

---

# 8. Contexto de Funcionalidade

Container de dependencias compartilhado.

class ContextoFuncionalidade {

```
Repositorio repositorio;

Menu menu;

Scanner scanner;

RegistroPlugin registroPlugin;
```

}

Proposito:

Fornecer dependencias para funcionalidades sem globais.

---

# 9. Interface FuncionalidadeMenu

Toda acao executavel do menu implementa esta interface.

public interface FuncionalidadeMenu {

```
String getId();

String getRotulo();

String getDescricao();

void executar();
```

}

Exemplos:

criar-produto
listar-produtos
deletar-produto
criar-venda
sair

---

# 10. Padrao Command

Cada item de menu e representado por um objeto.

Menu nunca contem logica de negocios.

Menu apenas executa:

selecionada.executar();

Beneficios:

* Sem switch
* Sem cadeias de if
* Aberto para extensao
* Integracao facil de plugins

---

# 11. Interface Plugin

public interface Plugin {

```
String getId();

String getNome();

String getDescricao();

List<FuncionalidadeMenu> getFuncionalidades();
```

}

Proposito:

Fornecer grupos de funcionalidades.

---

# 12. Registro de Plugins

Responsavel por gerenciar plugins.

Responsabilidades:

registrarPlugin()

desregistrarPlugin()

getPlugin()

getTodosPlugins()

carregarFuncionalidadesPlugin()

Prevenir IDs de plugin duplicados.

---

# 13. Estrutura de Plugin

Estrutura recomendada:

public class PluginProduto implements Plugin {

```
class CriarProduto
        implements FuncionalidadeMenu {}

class ListarProdutos
        implements FuncionalidadeMenu {}

class DeletarProduto
        implements FuncionalidadeMenu {}
```

}

Motivo:

Plugin torna-se autocontido.

Comandos vivem com seu modulo.

Mais proximo de arquitetura real de plugins.

---

# 14. Mecanismo de Menu

Campos:

List<FuncionalidadeMenu> funcionalidades;

boolean executando;

Responsabilidades:

carregarFuncionalidades()

carregarPlugin()

adicionarFuncionalidade()

removerFuncionalidade()

limparFuncionalidades()

parar()

executar()

---

# 15. Geracao Automatica de Menu

Opcoes de menu geradas dinamicamente.

Exemplo:

1 - Criar Produto
2 - Listar Produtos
3 - Criar Venda Fisica
4 - Carregar Plugin de Depuracao
5 - Sair

Gerado diretamente da lista de funcionalidades.

Nenhuma numeracao hardcoded.

---

# 16. Registro Runtime de Funcionalidades

Qualquer comando pode registrar outro comando.

Exemplo:

CarregarComandoDepuracao

Executa:

menu.adicionarFuncionalidade(
new MostrarEstatisticasDepuracao()
);

Nova opcao aparece imediatamente.

Nenhum reinicio necessario.

---

# 17. Carregamento Runtime de Plugins

Qualquer comando pode carregar um plugin.

Exemplo:

CarregarPluginAdminComando

Executa:

menu.carregarPlugin(
new PluginAdmin(contexto)
);

Menu atualiza automaticamente.

---

# 18. Plugin de Produto

Responsabilidades:

Criar Produto

Listar Produtos

Buscar Produto

Atualizar Produto

Deletar Produto

Desativar Produto

Contar Produtos

---

# 19. Plugin de Venda

Responsabilidades:

Criar Venda Fisica

Criar Venda Digital

Listar Vendas Fisicas

Listar Vendas Digitais

Listar Todas as Vendas

Buscar Venda

Contar Vendas

---

# 20. Plugin de Depuracao

Responsabilidades:

Mostrar Contagem de Produtos

Mostrar Contagem de Vendas

Mostrar Plugins Registrados

Mostrar Comandos Carregados

Mostrar Estado do Banco de Dados

---

# 21. Plugin Admin

Responsabilidades:

Limpar Produtos

Limpar Vendas

Reiniciar Banco de Dados

Descarregar Plugin

Desabilitar Comando

Habilitar Comando

---

# 22. Funcionalidade Sair

Responsabilidades:

Parar o loop da aplicacao.

Implementacao:

System.exit(0);

---

# 23. Plugin Carregador de Plugins

Plugin especial responsavel por carregar outros plugins.

Comandos:

Carregar Plugin de Depuracao

Carregar Plugin Admin

Descarregar Plugin

Listar Plugins

Isso demonstra extensibilidade em tempo de execucao.

---

# 24. Sistema de Eventos (Fase 2 Opcional)

Introduzir barramento de eventos leve.

Tipos de Evento:

EventoProdutoCriado

EventoProdutoDeletado

EventoVendaCriada

EventoPluginCarregado

EventoPluginDescarregado

EventoComandoExecutado

Beneficios:

Baixo acoplamento.

---

# 25. Barramento de Eventos

Responsabilidades:

publicar()

inscrever()

desinscrever()

Plugins podem reagir a eventos.

Exemplo:

PluginEstatisticas escuta EventoVendaCriada.

---

# 26. Sistema de Permissoes (Fase 2 Opcional)

Papeis:

ADMIN

USUARIO

DEPURACAO

CONVIDADO

Extensao de funcionalidade:

cargoExigido()

Menu esconde comandos nao autorizados.

---

# 27. Plugins Baseados em Arquivo (Futuro)

Atual:

menu.carregarPlugin(
new PluginVenda(contexto)
);

Futuro:

plugins/

venda.plugin

depuracao.plugin

admin.plugin

Framework varre pasta.

Descobre plugins.

Registra automaticamente.

Simula plataformas reais de plugins.

---

# 28. Classes Utilitarias

LeitorConsole

Proposito:
Validacao de entrada

Metodos:

lerInt()

lerString()

lerDouble()

---

Impressora

Proposito:
Saida de console consistente

Metodos:

cabecalho()

separador()

erro()

sucesso()

---

GeradorId

Proposito:
Gerar IDs

Metodos:

proximoIdProduto()

proximoIdOferta()

---

# 29. Fluxo de Inicializacao

1. Criar Banco de Dados

2. Criar Repositorio

3. Criar RegistroPlugin

4. Criar Menu

5. Criar Contexto

6. Criar Plugins

7. Registrar Plugins

8. Carregar Funcionalidades

9. Iniciar Menu

Fluxo Pseudo:

Principal
↓
BancoDeDados
↓
Repositorio
↓
Contexto
↓
Plugins
↓
Menu.carregarPlugin(...)
↓
Menu.executar()

---

# 30. Criterios de Sucesso

O projeto e bem-sucedido quando demonstra:

✓ Java OOP

✓ Heranca

✓ Encapsulamento

✓ Padrao Repository

✓ Padrao Command

✓ Padrao Plugin

✓ Geracao Dinamica de Menu

✓ Registro Runtime de Comandos

✓ Registro Runtime de Plugins

✓ Persistencia em Memoria

✓ Separacao de Responsabilidades

✓ Sem Bibliotecas Externas

✓ Sem Menu Baseado em Switch

✓ Arquitetura Extensivel

---

# 31. Metas Estendidas

Fase 2

* Barramento de Eventos
* Permissoes
* Historico de Comandos
* Comandos Desfazer
* Pasta de Descoberta de Plugins
* Salvar em Arquivo
* Importar / Exportar Dados

Fase 3

* Carregamento de plugins baseado em reflexao
* Registro de comandos orientado por anotacoes
* Menus aninhados
* Comandos agendados
* Container leve de injecao de dependencia

A arquitetura final deve parecer menos com um aplicativo de vendas e mais com uma plataforma de aplicativos em miniatura capaz de plugins que vem com um modulo de vendas.

(Fim do arquivo - total 822 linhas)
