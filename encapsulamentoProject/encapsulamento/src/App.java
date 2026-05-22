import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        Pessoa p = new Pessoa();
        p.setNome("Carlos");
        int idade = -50;
        while (idade < 0)
        {
            System.out.println("Entre como a idade da pessoa");
            idade = sc.nextInt();
            if (idade < 0)
                System.out.println("Idade inválida");
        }
        p.setIdade(idade);
        System.out.println("Nome : " + p.getNome() + " \nidade: " + p.getIdade());
        
        sc.close();
    }
}
