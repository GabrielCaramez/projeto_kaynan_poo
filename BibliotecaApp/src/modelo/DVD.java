package modelo;

public class DVD extends Material {
    private String diretor;
    private int duracao;
    private String genero;

    public DVD(String titulo, String autor, String id, String diretor, 
               int duracao, String genero) {
        super(titulo, autor, id, 7);
        this.diretor = diretor;
        this.duracao = duracao;
        this.genero = genero;
    }

    public String getDiretor() {
        return diretor;
    }

    public int getDuracao() {
        return duracao;
    }

    public String getGenero() {
        return genero;
    }

    @Override
    public String getTipo() {
        return "DVD";
    }

    @Override
    public String toString() {
        return "DVD {" +
                "titulo='" + getTitulo() + '\'' +
                ", diretor='" + diretor + '\'' +
                ", duracao=" + duracao + " minutos" +
                ", genero='" + genero + '\'' +
                ", disponivel=" + !isEmprestado() +
                '}';
    }
}
