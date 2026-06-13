package menu;

import java.util.List;

public interface Plugin {
    String getId();
    String getNome();
    String getDescricao();
    List<FuncionalidadeMenu> getFuncionalidades();
}
