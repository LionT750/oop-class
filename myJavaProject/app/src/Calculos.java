public abstract class Calculos {
    public static double soma(double a, double b) {
        return a + b;
    }

    public static double subtracao(double a, double b) {
        return a - b;
    }

    public static double multiplicacao(double a , double b){
        return a * b;
    }

    public static double divisao(double a, double b)
    {
       if (b != 0) 
       {
           return a / b;
       }
       else {
         while (b == 0)
            {
                System.out.println("Divisions by zero aren't allowed, try another number");
                b = InputReader.readNumber();
            }
          return a / b;
       }
    }
}
