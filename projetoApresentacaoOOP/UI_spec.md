# Desktop UI Spec (Windows 11)

Version: 0.1 | Toolkit: Java Swing (included in JDK, zero dependencies)

---

## 1. Approach

Wrap the existing CLI menu engine in a Swing window. No business logic moves — plugins and commands remain untouched. The UI layer is a thin shell.

---

## 2. New Package

```
src/
  ui/
    AppWindow.java       # JFrame with menu + panel
    OutputPanel.java     # JTextArea for command output
```

Only 2 new files. Use `SwingUtilities.invokeLater` in `Main.java` to launch.

---

## 3. AppWindow

```java
// Pseudocode
class AppWindow extends JFrame {
    JPanel buttonPanel;
    JTextArea output;
    Menu menu;

    AppWindow(Menu menu) {
        this.menu = menu;
        setTitle("Sales System");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        buttonPanel = new JPanel(new GridLayout(0, 1));
        JScrollPane scroll = new JScrollPane(buttonPanel);

        output = new JTextArea(20, 60);
        output.setEditable(false);
        JScrollPane outScroll = new JScrollPane(output);

        add(scroll, BorderLayout.WEST);
        add(outScroll, BorderLayout.CENTER);

        rebuildButtons();
    }

    void rebuildButtons() {
        buttonPanel.removeAll();
        for (MenuFunctionality f : menu.getFunctionalities()) {
            JButton btn = new JButton(f.getLabel());
            btn.addActionListener(e -> {
                // Redirect System.out to output area
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PrintStream ps = new PrintStream(baos);
                PrintStream old = System.out;
                System.setOut(ps);
                f.execute();
                System.setOut(old);
                output.append(baos.toString());
            });
            buttonPanel.add(btn);
        }
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    // Call this after any runtime registration
    void refresh() { rebuildButtons(); }
}
```

---

## 4. OutputPanel (optional — inline in AppWindow)

Just a `JTextArea` with a helper:

```java
void append(String text) { output.append(text + "\n"); }
void clear() { output.setText(""); }
```

---

## 5. Redirecting Plugin I/O

Commands use `System.out.println` — intercept via `System.setOut(PrintStream)` wrapping a `ByteArrayOutputStream`. This avoids touching any plugin code.

---

## 6. Main.java Change

```java
SwingUtilities.invokeLater(() -> {
    AppWindow window = new AppWindow(menu);
    window.setVisible(true);
});
```

Wrap the context wiring as-is; add the GUI launch after plugin registration.

---

## 7. Runtime Refresh

After `menu.loadPlugin(...)` or `menu.addFunctionality(...)`, call `window.refresh()` to regenerate the button panel. Pass the window ref through `FunctionalityContext` or a callback.

---

## 8. Summary

| File | LOC |
|---|---|
| `AppWindow.java` | ~80 |
| `Main.java` diff | +5 lines |

Total added: ~85 lines. Zero new dependencies. All existing patterns preserved.
