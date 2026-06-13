package menu;

import java.util.List;

public interface Plugin {
    String getId();
    String getName();
    String getDescription();
    List<MenuFunctionality> getFunctionalities();
}
