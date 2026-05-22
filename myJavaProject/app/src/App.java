public class App {
    

    public static void main(String[] args) throws Exception {


            double numbers[] = new double[2];
            
            for (int i = 0; i < 2; i++)
            {
                numbers[i] = InputReader.readNumber();
            }
            
            int operationNumber = 0;

            while(true)
            {
                while(true)
                {
                    System.out.println("Selecione a operação desejada digitando o número referente : 1- Soma, 2- Subtracão, 3- Multiplicação,4- Divisão ou 5 para sair : ");
                    operationNumber = (int)(InputReader.readNumber());
                    if (operationNumber > 0 && operationNumber < 6)
                        break;
                    else
                        System.out.println("Essa operação não existe, tente novamente");
                }
                switch (operationNumber) {
                    case 1 -> System.out.println("O resultado da operação soma foi: " + Calculos.soma(numbers[0], numbers[1]));
                    case 2 -> System.out.println("O resultado da operação subtração foi: " + Calculos.subtracao(numbers[0], numbers[1]));
                    case 3 -> System.out.println("O resultado da operação multiplicação foi: " + Calculos.multiplicacao(numbers[0], numbers[1]));
                    case 4 -> System.out.println("O resultado da operação divisão foi: " + Calculos.divisao(numbers[0], numbers[1]));
                }
                
                if (operationNumber == 5)
                {    
                    AppCloser.defaultRoutine();
                    break;
                }
            } 
    }
}







































interface VocalBehaviors {

    public void shout();
    public void talk();
    
}

final class Bat implements VocalBehaviors {

    public void shout() {
        System.out.println("cryyyyyyyyy");
    }

    public void talk() {
        System.out.println("talking...");
    }
}