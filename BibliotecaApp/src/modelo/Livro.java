package modelo;

public class Livro extends Material {
    private String isbn;
    private int numeroPaginas;
    private String editora;

    public Livro(String titulo, String autor, String id, String isbn, 
                 int numeroPaginas, String editora) {
        super(titulo, autor, id, 14);
        this.isbn = isbn;
        this.numeroPaginas = numeroPaginas;
        this.editora = editora;
    }

    public Livro(String titulo, String autor, String id) {
        super(titulo, autor, id, 14);
        this.isbn = "";
        this.numeroPaginas = 0;
        this.editora = "";
    }

    public String getIsbn() {
        return isbn;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public String getEditora() {
        return editora;
    }

    @Override
    public String getTipo() {
        return "Livro";
    }

    @Override
    public String toString() {
        return "Livro {" +
                "titulo='" + getTitulo() + '\'' +
                ", autor='" + getAutor() + '\'' +
                ", isbn='" + isbn + '\'' +
                ", paginas=" + numeroPaginas +
                ", editora='" + editora + '\'' +
                ", disponivel=" + !isEmprestado() +
                '}';
    }
}
