package modelo;

public class Revista extends Material {
    private int numero;
    private String mes;
    private int ano;

    public Revista(String titulo, String autor, String id, int numero, 
                   String mes, int ano) {
        super(titulo, autor, id, 7);
        this.numero = numero;
        this.mes = mes;
        this.ano = ano;
    }

    public int getNumero() {
        return numero;
    }

    public String getMes() {
        return mes;
    }

    public int getAno() {
        return ano;
    }

    @Override
    public String getTipo() {
        return "Revista";
    }

    @Override
    public String toString() {
        return "Revista {" +
                "titulo='" + getTitulo() + '\'' +
                ", numero=" + numero +
                ", mes='" + mes + '\'' +
                ", ano=" + ano +
                ", disponivel=" + !isEmprestado() +
                '}';
    }
}
