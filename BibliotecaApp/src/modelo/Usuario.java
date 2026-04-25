package modelo;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nome;
    private String email;
    private String cpf;
    private List<String> emprestimosAtivos;

    public Usuario(String nome, String email, String cpf) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.emprestimosAtivos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public void adicionarEmprestimo(String idMaterial) {
        emprestimosAtivos.add(idMaterial);
    }

    public void removerEmprestimo(String idMaterial) {
        emprestimosAtivos.remove(idMaterial);
    }

    public List<String> getEmprestimosAtivos() {
        return new ArrayList<>(emprestimosAtivos);
    }

    public int getTotalEmprestimos() {
        return emprestimosAtivos.size();
    }

    @Override
    public String toString() {
        return "Usuario {" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", cpf='" + cpf + '\'' +
                ", emprestimosAtivos=" + emprestimosAtivos.size() +
                '}';
    }
}
