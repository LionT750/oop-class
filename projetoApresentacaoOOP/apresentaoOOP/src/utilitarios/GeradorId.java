package utilitarios;

import java.util.concurrent.atomic.AtomicLong;

public class GeradorId {
    private static final AtomicLong contadorProduto = new AtomicLong(1);
    private static final AtomicLong contadorOferta = new AtomicLong(1);

    public static long proximoIdProduto() {
        return contadorProduto.getAndIncrement();
    }

    public static long proximoIdOferta() {
        return contadorOferta.getAndIncrement();
    }
}
