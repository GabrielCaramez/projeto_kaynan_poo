package modelo;

import java.time.LocalDate;
import java.util.UUID;

public class Emprestimo {
    private String id;
    private String idMaterial;
    private String nomeUsuario;
    private LocalDate dataEmprestimo;
    private LocalDate dataPrevistaDevolucao;
    private LocalDate dataDevolucao;
    private boolean ativo;

    public Emprestimo(String idMaterial, String nomeUsuario, int diasDisponivel) {
        this.id = UUID.randomUUID().toString();
        this.idMaterial = idMaterial;
        this.nomeUsuario = nomeUsuario;
        this.dataEmprestimo = LocalDate.now();
        this.dataPrevistaDevolucao = LocalDate.now().plusDays(diasDisponivel);
        this.dataDevolucao = null;
        this.ativo = true;
    }

    public String getId() {
        return id;
    }

    public String getIdMaterial() {
        return idMaterial;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDate getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void finalizarEmprestimo() {
        this.dataDevolucao = LocalDate.now();
        this.ativo = false;
    }

    public boolean estaAtrasado() {
        return ativo && LocalDate.now().isAfter(dataPrevistaDevolucao);
    }

    @Override
    public String toString() {
        return "Emprestimo {" +
                "id='" + id + '\'' +
                ", material='" + idMaterial + '\'' +
                ", usuario='" + nomeUsuario + '\'' +
                ", emprestadoEm=" + dataEmprestimo +
                ", devolverEm=" + dataPrevistaDevolucao +
                ", ativo=" + ativo +
                '}';
    }
}
