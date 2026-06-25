package ui;

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
import menu.Menu;
import menu.MenuFunctionality;
import utils.ConsoleReader;

public class AppWindow extends JFrame {
    private JPanel buttonPanel;
    private JTextArea output;
    private Menu menu;

    public AppWindow(Menu menu) {
        this.menu = menu;
        setTitle("Sales System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(buttonPanel);
        scroll.setPreferredSize(new Dimension(260, 0));

        output = new JTextArea();
        output.setEditable(false);
        output.setFont(new Font("Consolas", Font.PLAIN, 13));
        JScrollPane outScroll = new JScrollPane(output);

        add(scroll, BorderLayout.WEST);
        add(outScroll, BorderLayout.CENTER);

        rebuildButtons();
        setSize(900, 600);
        setLocationRelativeTo(null);
    }

    public void rebuildButtons() {
        buttonPanel.removeAll();
        List<MenuFunctionality> funcs = menu.getFunctionalities();

        int maxW = 0;
        int n = funcs.size();
        JButton[] btns = new JButton[n];
        for (int i = 0; i < n; i++) {
            btns[i] = new JButton(funcs.get(i).getLabel());
            maxW = Math.max(maxW, btns[i].getPreferredSize().width);
        }
        for (int i = 0; i < n; i++) {
            JButton b = btns[i];
            Dimension size = new Dimension(maxW, b.getPreferredSize().height);
            b.setPreferredSize(size);
            b.setMinimumSize(size);
            b.setMaximumSize(size);
            b.setAlignmentX(0.5f);
            int idx = i;
            b.addActionListener(e -> executeCommand(funcs.get(idx)));
            buttonPanel.add(b);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        }
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    private void executeCommand(MenuFunctionality f) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos) {
            public void write(byte[] buf, int off, int len) {
                super.write(buf, off, len);
                String s = new String(buf, off, len);
                javax.swing.SwingUtilities.invokeLater(() -> {
                    output.append(s);
                    output.setCaretPosition(output.getDocument().getLength());
                });
                    }
                };
                PrintStream old = System.out;
                System.setOut(ps);
                try {
                    f.execute();
                } catch (ConsoleReader.Cancelled e) {
                    output.append("Cancelled.\n");
                } catch (Exception e) {
                    e.printStackTrace(ps);
                }
                System.setOut(old);
    }
}
