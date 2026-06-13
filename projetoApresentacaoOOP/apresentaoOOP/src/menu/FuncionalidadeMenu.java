package menu;

public interface FuncionalidadeMenu {
    String getId();
    String getRotulo();
    String getDescricao();
    void executar();
    default int ordem() { return 0; }
}
