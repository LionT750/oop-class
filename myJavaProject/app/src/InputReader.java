import java.util.Scanner;

public abstract class InputReader {
    
    private static Scanner reader = new Scanner(System.in);

    public static double readNumber() {

       System.out.println("Digite o número: "); 
       while (!reader.hasNextDouble()) {
            System.out.println("Por favor, entre com um valor numérico válido");
            reader.next(); 
       }
       double result = reader.nextDouble();
       return result;
    }

}
