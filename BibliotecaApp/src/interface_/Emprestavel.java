package interface_;

public interface Emprestavel {
    boolean podeSerEmprestado();
    void registrarEmprestimo(String nomeUsuario);
    void registrarDevolucao();
    int getDiasDisponivel();
}
