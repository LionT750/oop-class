package menu;

import java.util.Scanner;
import repositorio.Repositorio;

public class ContextoFuncionalidade {
    public Repositorio repositorio;
    public Menu menu;
    public Scanner scanner;
    public RegistroPlugin registroPlugin;

    public ContextoFuncionalidade(Repositorio repositorio, Menu menu, Scanner scanner,
                                   RegistroPlugin registroPlugin) {
        this.repositorio = repositorio;
        this.menu = menu;
        this.scanner = scanner;
        this.registroPlugin = registroPlugin;
    }
}
