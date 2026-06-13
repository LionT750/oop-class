package iu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import menu.FuncionalidadeMenu;
import menu.Menu;
import utilitarios.LeitorConsole;

public class JanelaApp extends JFrame {
    private JPanel painelBotoes;
    private JTextArea saida;
    private Menu menu;

    public JanelaApp(Menu menu) {
        this.menu = menu;
        setTitle("Sistema de Vendas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        painelBotoes = new JPanel();
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(painelBotoes);
        scroll.setPreferredSize(new Dimension(340, 0));

        saida = new JTextArea();
        saida.setEditable(false);
        saida.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane saidaScroll = new JScrollPane(saida);

        add(scroll, BorderLayout.WEST);
        add(saidaScroll, BorderLayout.CENTER);

        reconstruirBotoes();
        setSize(900, 600);
        setLocationRelativeTo(null);
    }

    public void reconstruirBotoes() {
        painelBotoes.removeAll();
        List<FuncionalidadeMenu> funcs = menu.getFuncionalidades();

        int maxW = 0;
        int n = funcs.size();
        JButton[] botoes = new JButton[n];
        for (int i = 0; i < n; i++) {
            botoes[i] = new JButton(funcs.get(i).getRotulo());
            botoes[i].setFont(new Font("Segoe UI", Font.PLAIN, 11));
            maxW = Math.max(maxW, botoes[i].getPreferredSize().width);
        }
        for (int i = 0; i < n; i++) {
            JButton b = botoes[i];
            Dimension size = new Dimension(maxW, b.getPreferredSize().height);
            b.setPreferredSize(size);
            b.setMinimumSize(size);
            b.setMaximumSize(size);
            b.setAlignmentX(0.5f);
            int idx = i;
            b.addActionListener(e -> executarComando(funcs.get(idx)));
            painelBotoes.add(b);
            painelBotoes.add(Box.createRigidArea(new Dimension(0, 3)));
        }
        painelBotoes.revalidate();
        painelBotoes.repaint();
    }

    private void executarComando(FuncionalidadeMenu f) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos) {
            public void write(byte[] buf, int off, int len) {
                super.write(buf, off, len);
                String s = new String(buf, off, len);
                javax.swing.SwingUtilities.invokeLater(() -> {
                    saida.append(s);
                    saida.setCaretPosition(saida.getDocument().getLength());
                });
            }
        };
        PrintStream old = System.out;
        System.setOut(ps);
        try {
            f.executar();
        } catch (LeitorConsole.Cancelado e) {
            saida.append("Cancelado.\n");
        } catch (Exception e) {
            e.printStackTrace(ps);
        }
        System.setOut(old);
    }
}
