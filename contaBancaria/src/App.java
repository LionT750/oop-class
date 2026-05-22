import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        ContaBancaria conta1 = new ContaBancaria();
        conta1.build("",  1000);
        // should fail
        conta1.build("Cleiton da silva", 1000);
        conta1.depositar(1000);
        conta1.depositar(3000);

        conta1.sacar(-500);
        //should fail
        conta1.sacar(0);
        //should fail
        conta1.sacar(3000);
        //should fail

        conta1.sacar(500);
        //should work
        conta1.sacar(200);

        conta1.depositar(700);

        conta1.exibirDados();

        ContaBancaria conta2 = new ContaBancaria();
        ContaBancaria conta3 = new ContaBancaria();

        conta2.build("Lucas das neves Matheus", 40000);
        conta2.build("Roberta campos de nascimento", 1500);
        //should fail
        conta3.build("Roberta campos de nascimento", 1500);
        conta2.depositar(1000000);
        conta3.depositar(5000);

        List<ContaBancaria> contas = new ArrayList<ContaBancaria>(3);
        contas.add(conta1);
        contas.add(conta2);
        contas.add(conta3);
        
        for (ContaBancaria contaBancaria : contas) {
            contaBancaria.exibirDados();
        }
    }
}
