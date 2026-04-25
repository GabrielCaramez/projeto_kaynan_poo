package modelo;

import interface_.Buscavel;
import interface_.Emprestavel;

public abstract class Material implements Emprestavel, Buscavel {
    private String titulo;
    private String autor;
    private String id;
    private boolean emprestado;
    private String usuarioAtual;
    private int diasDisponivel;

    public Material(String titulo, String autor, String id, int diasDisponivel) {
        this.titulo = titulo;
        this.autor = autor;
        this.id = id;
        this.diasDisponivel = diasDisponivel;
        this.emprestado = false;
        this.usuarioAtual = null;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getId() {
        return id;
    }

    public boolean isEmprestado() {
        return emprestado;
    }

    public String getUsuarioAtual() {
        return usuarioAtual;
    }

    @Override
    public int getDiasDisponivel() {
        return diasDisponivel;
    }

    @Override
    public boolean podeSerEmprestado() {
        return !emprestado;
    }

    @Override
    public void registrarEmprestimo(String nomeUsuario) {
        if (podeSerEmprestado()) {
            this.emprestado = true;
            this.usuarioAtual = nomeUsuario;
            System.out.println("Emprestimo registrado: " + titulo + " para " + nomeUsuario);
        } else {
            System.out.println("Erro: " + titulo + " nao esta disponivel para emprestimo");
        }
    }

    @Override
    public void registrarDevolucao() {
        if (emprestado) {
            System.out.println("Devolucao registrada: " + titulo + " devolvido por " + usuarioAtual);
            this.emprestado = false;
            this.usuarioAtual = null;
        } else {
            System.out.println("Erro: " + titulo + " nao foi emprestado");
        }
    }

    @Override
    public boolean contem(String termo) {
        return titulo.toLowerCase().contains(termo.toLowerCase()) || 
               autor.toLowerCase().contains(termo.toLowerCase());
    }

    @Override
    public String getIdentificador() {
        return id;
    }

    public abstract String getTipo();

    @Override
    public String toString() {
        return getTipo() + " {" +
                "titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", id='" + id + '\'' +
                ", emprestado=" + emprestado +
                ", diasDisponivel=" + diasDisponivel +
                '}';
    }
}
