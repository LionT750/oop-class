import java.util.concurrent.TimeUnit;

public abstract class AppCloser {
    public static void defaultRoutine() throws InterruptedException{
        System.out.println("Encerrando sistema..");
        if (Math.random() > 0.5)
        {
            System.out.println("Encontramos problemas, não desligue seu computador..");
            TimeUnit.SECONDS.sleep(5);
            System.out.println("Encerrado com sucesso.");
            throw new InterruptedException();
            
        }
        else
            System.out.println("Encerrado com sucesso");
                    
    }
}
