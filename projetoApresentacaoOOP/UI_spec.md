# Especificacao da Interface Desktop (Windows 11)

Versao: 0.1 | Toolkit: Java Swing (incluido no JDK, zero dependencias)

---

## 1. Abordagem

Envolver o mecanismo de menu CLI existente em uma janela Swing. Nenhuma logica de negocios se move — plugins e comandos permanecem intocados. A camada de interface e uma casca fina.

---

## 2. Novo Pacote

```
src/
  iu/
    JanelaApp.java       # JFrame com menu + painel
```

Apenas 1 novo arquivo. Use `SwingUtilities.invokeLater` em `Principal.java` para lancar.

---

## 3. JanelaApp

```java
// Pseudocodigo
class JanelaApp extends JFrame {
    JPanel painelBotoes;
    JTextArea saida;
    Menu menu;

    JanelaApp(Menu menu) {
        this.menu = menu;
        setTitle("Sistema de Vendas");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        painelBotoes = new JPanel();
        JScrollPane scroll = new JScrollPane(painelBotoes);

        saida = new JTextArea(20, 60);
        saida.setEditable(false);
        JScrollPane saidaScroll = new JScrollPane(saida);

        add(scroll, BorderLayout.WEST);
        add(saidaScroll, BorderLayout.CENTER);

        reconstruirBotoes();
    }

    void reconstruirBotoes() {
        painelBotoes.removeAll();
        for (FuncionalidadeMenu f : menu.getFuncionalidades()) {
            JButton btn = new JButton(f.getRotulo());
            btn.addActionListener(e -> {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PrintStream ps = new PrintStream(baos);
                PrintStream old = System.out;
                System.setOut(ps);
                f.executar();
                System.setOut(old);
                saida.append(baos.toString());
            });
            painelBotoes.add(btn);
        }
        painelBotoes.revalidate();
        painelBotoes.repaint();
    }

    void atualizar() { reconstruirBotoes(); }
}
```

---

## 4. Redirecionando I/O de Plugins

Comandos usam `System.out.println` — intercepte via `System.setOut(PrintStream)` envolvendo um `ByteArrayOutputStream`. Isso evita tocar em qualquer codigo de plugin.

---

## 5. Mudanca no Principal.java

```java
SwingUtilities.invokeLater(() -> {
    JanelaApp janela = new JanelaApp(menu);
    janela.setVisible(true);
});
```

Envolva a configuracao do contexto como esta; adicione a inicializacao da GUI apos o registro de plugins.

---

## 6. Atualizacao Runtime

Apos `menu.carregarPlugin(...)` ou `menu.adicionarFuncionalidade(...)`, chame `janela.atualizar()` para regenerar o painel de botoes. Passe a referencia da janela atraves de `ContextoFuncionalidade` ou um callback.

---

## 7. Resumo

| Arquivo | LOC |
|---|---|
| `JanelaApp.java` | ~80 |
| `Principal.java` diff | +5 linhas |

Total adicionado: ~85 linhas. Zero novas dependencias. Todos os padroes existentes preservados.
