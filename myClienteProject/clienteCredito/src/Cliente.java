import java.time.LocalDate;
import java.time.Period;

public class Cliente {
    private boolean temDivida;
    private String name;
    private String cidadeNatal;
    private LocalDate birthDate;
    private int age;

    public Cliente(String name, boolean temDivida, String cidadeNatal, LocalDate birthDate) {
        this.temDivida = temDivida;
        this.name = name;
        this.cidadeNatal = cidadeNatal;
        this.birthDate = birthDate;
        this.age = setAge();
    }

    public Cliente() {
    }

    public int getAge() {
        return age;
    }


    public String getName() {
        return name;
    }


    private int setAge() {
        LocalDate currenDate = LocalDate.now();
        Period age = Period.between(birthDate, currenDate);
        return age.getYears();
    }


    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }


    public String getCidadeNatal() {
        return cidadeNatal;
    }


    public void setCidadeNatal(String cidadeNatal) {
        this.cidadeNatal = cidadeNatal;
    }


    public void setName(String name)
    {
        this.name = name;
    }

    public void setTemDivida(boolean temDivida)
    {
           this.temDivida = temDivida;
    }

    public void podeComprarFiado(){
        boolean endividado = this.temDivida;
        printStatusCredito(endividado);
    }

    private void printStatusCredito(boolean endividado) {
        if (!endividado)
            System.out.println("Crédito aprovado. O cliente " + getName() + " nascido em " + getCidadeNatal() + " que possui  " + getAge() + " anos pode comprar");
        else
            System.out.println("Crédito negado. O cliente " + getName() + " nascido em " + getCidadeNatal() + " que possui  " + getAge() + " anos não pode comprar");
    }
}
