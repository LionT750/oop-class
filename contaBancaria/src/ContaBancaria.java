public class ContaBancaria {
    private String titular;
    private double saldo;
    private double limiteSaque;
    
    public ContaBancaria() {
    
    }

    public void build(String titular, double limiteSaque ) {
        
        if (getTitular() != null)
        {
            System.out.println("Conta já registrada. O método build deve ser usado apenas para registrar novas contas");
            return;
        }

        if (titular.isBlank() || titular.isEmpty() || titular == null)
         {
            System.out.println("Nome inválido. Registro cancelado");
            return;
         }

         if (limiteSaque <= 0)
         {
            System.out.println("Limite de saque inválido. Registro cancelado");
            return;
         }

         setTitular(titular);
         setLimiteSaque(limiteSaque);
         setSaldo(0.0);
         System.out.println("Conta registrada com sucesso");

    }

    private String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
            this.titular = titular;
    }

    private double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    private double getLimiteSaque() {
        return limiteSaque;
    }

    public void setLimiteSaque(double limiteSaque) {
        this.limiteSaque = limiteSaque;
    }

    public void depositar(double valor) {
        if (valor < 0)
        {
            System.out.println("O valor de depósito precisa ser maior que 0");
            return;
        }

        saldo += valor;

    };

    public void sacar(double valor){
        if (valor <= 0)
        {
            System.out.println("O valor do saque precisa ser maior que 0");
            return;
        }

        if (valor > saldo)
        {
            System.out.println("O valor do saque foi maior que o disponível em saldo");
            return;
        }

        if (valor > limiteSaque)
        {
            System.out.println("O valor do saque excedeu seu limite de saque diário");
            return;
        }

        saldo -= valor;
        System.out.println("Saque realizado com sucesso");
    };


    public void exibirDados() {
        System.out.println("Titular da conta : " + getTitular());
        System.out.println("Saldo bancário: " + getSaldo());
        System.out.println("Limite de Saque: " + getLimiteSaque());

    };




}
