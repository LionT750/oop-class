import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {

        List<Cliente> clientes = new ArrayList<Cliente>();

        Cliente clienteUm = new Cliente("Carlos", true,"Tubarão", LocalDate.of(1997, 12, 25));
        clienteUm.podeComprarFiado();

        Cliente clienteDois = new Cliente("Lucas", false, "Fortaleza", LocalDate.of(1992, 12, 25));
        clienteDois.podeComprarFiado();

        Cliente clienteTres = new Cliente("Marcos", true, "Belém", LocalDate.of(2002, 12, 25));
        clienteTres.podeComprarFiado();

        Cliente clienteQuatro = new Cliente("João", true,"Salvador", LocalDate.of(1995, 03, 17));
        clienteQuatro.podeComprarFiado();






        
        clientes.add(clienteUm);
        clientes.add(clienteDois);
        clientes.add(clienteTres);
        clientes.add(clienteQuatro);

        for (Cliente cliente : clientes) {
            System.out.println("Nome cliente " + cliente.getName());
        }
    }
}
