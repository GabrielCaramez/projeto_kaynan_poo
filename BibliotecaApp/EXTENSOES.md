EXEMPLOS DE EXTENSAO DO PROJETO

Este arquivo demonstra como o projeto pode ser facilmente estendido para adicionar novas 
funcionalidades mantendo os principios de POO.

EXEMPLO 1: ADICIONAR UM NOVO TIPO DE MATERIAL

Problema: Adicionar suporte para Audiolivros

Solucao: Criar uma nova classe que herda de Material (sem modificar nenhuma classe existente)

---

package modelo;

public class Audiolivro extends Material {
    private String narrador;
    private int duracao;
    private String formato;

    public Audiolivro(String titulo, String autor, String id, String narrador, 
                      int duracao, String formato) {
        super(titulo, autor, id, 21);
        this.narrador = narrador;
        this.duracao = duracao;
        this.formato = formato;
    }

    public String getNarrador() {
        return narrador;
    }

    public int getDuracao() {
        return duracao;
    }

    public String getFormato() {
        return formato;
    }

    @Override
    public String getTipo() {
        return "Audiolivro";
    }

    @Override
    public String toString() {
        return "Audiolivro {" +
                "titulo='" + getTitulo() + '\'' +
                ", autor='" + getAutor() + '\'' +
                ", narrador='" + narrador + '\'' +
                ", duracao=" + duracao + " minutos" +
                ", formato='" + formato + '\'' +
                ", disponivel=" + !isEmprestado() +
                '}';
    }
}

---

Uso no Main:

Audiolivro audiolivro = new Audiolivro(
    "Clean Code",
    "Robert C. Martin",
    "A001",
    "Mark Deklin",
    540,
    "MP3"
);
biblioteca.adicionarMaterial(audiolivro);

Vantagem: A classe BibliotecaService funciona automaticamente com Audiolivro sem 
nenhuma modificacao, porque trabalha com abstraoes (Material).

EXEMPLO 2: ADICIONAR SISTEMA DE MULTAS

Problema: Aplicar multa quando usuario nao devolve no prazo

Solucao: Estender a classe Emprestimo com calculo de multa

---

package modelo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class Emprestimo {
    private String id;
    private String idMaterial;
    private String nomeUsuario;
    private LocalDate dataEmprestimo;
    private LocalDate dataPrevistaDevolucao;
    private LocalDate dataDevolucao;
    private boolean ativo;
    private double multa;
    private static final double MULTA_DIARIA = 5.0;

    public Emprestimo(String idMaterial, String nomeUsuario, int diasDisponivel) {
        this.id = UUID.randomUUID().toString();
        this.idMaterial = idMaterial;
        this.nomeUsuario = nomeUsuario;
        this.dataEmprestimo = LocalDate.now();
        this.dataPrevistaDevolucao = LocalDate.now().plusDays(diasDisponivel);
        this.dataDevolucao = null;
        this.ativo = true;
        this.multa = 0.0;
    }

    public boolean estaAtrasado() {
        return ativo && LocalDate.now().isAfter(dataPrevistaDevolucao);
    }

    public double calcularMulta() {
        if (!ativo && dataDevolucao != null && 
            dataDevolucao.isAfter(dataPrevistaDevolucao)) {
            long diasAtrasados = ChronoUnit.DAYS.between(dataPrevistaDevolucao, dataDevolucao);
            multa = diasAtrasados * MULTA_DIARIA;
        }
        return multa;
    }

    public double getMulta() {
        return multa;
    }

    public void finalizarEmprestimo() {
        this.dataDevolucao = LocalDate.now();
        this.ativo = false;
        this.multa = calcularMulta();
    }
}

---

EXEMPLO 3: ADICIONAR SISTEMA DE RESERVAS

Problema: Permitir que usuarios reservem materiais que estao emprestados

Solucao: Criar interface Reservavel e classe Reserva

---

package interface_;

public interface Reservavel {
    void adicionarReserva(String nomeUsuario);
    void removerReserva(String nomeUsuario);
    java.util.List<String> getReservas();
}

---

package modelo;

import interface_.Reservavel;
import java.util.ArrayList;
import java.util.List;

public abstract class Material implements Emprestavel, Buscavel, Reservavel {
    // ... codigo anterior ...
    protected List<String> reservas;

    public Material(String titulo, String autor, String id, int diasDisponivel) {
        // ... codigo anterior ...
        this.reservas = new ArrayList<>();
    }

    @Override
    public void adicionarReserva(String nomeUsuario) {
        if (!reservas.contains(nomeUsuario)) {
            reservas.add(nomeUsuario);
            System.out.println("Reserva adicionada: " + titulo + " reservado por " + nomeUsuario);
        }
    }

    @Override
    public void removerReserva(String nomeUsuario) {
        reservas.remove(nomeUsuario);
    }

    @Override
    public List<String> getReservas() {
        return new ArrayList<>(reservas);
    }
}

---

EXEMPLO 4: ADICIONAR CATEGORIAS

Problema: Organizar materiais em categorias

Solucao: Criar uma classe Categoria que agrupa materiais

---

package modelo;

import java.util.ArrayList;
import java.util.List;

public class Categoria {
    private String nome;
    private String descricao;
    private List<String> idsMateria;

    public Categoria(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.idsMateria = new ArrayList<>();
    }

    public void adicionarMaterial(String idMaterial) {
        if (!idsMateria.contains(idMaterial)) {
            idsMateria.add(idMaterial);
        }
    }

    public String getNome() {
        return nome;
    }

    public List<String> getMateriais() {
        return new ArrayList<>(idsMateria);
    }

    @Override
    public String toString() {
        return "Categoria {nome='" + nome + "', materiais=" + idsMateria.size() + '}';
    }
}

---

EXEMPLO 5: ADICIONAR FILTROS AVANCADOS

Problema: Buscar materiais com multiplos criterios

Solucao: Criar classe de filtro

---

package servico;

import modelo.Material;
import java.util.List;
import java.util.stream.Collectors;

public class FiltroMaterial {
    private String termo;
    private boolean apenasDisponiveis;
    private String tipo;

    public FiltroMaterial comTermo(String termo) {
        this.termo = termo;
        return this;
    }

    public FiltroMaterial apenasDisponiveis(boolean disponivel) {
        this.apenasDisponiveis = disponivel;
        return this;
    }

    public FiltroMaterial comTipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public List<Material> aplicar(List<Material> materiais) {
        return materiais.stream()
            .filter(m -> termo == null || m.contem(termo))
            .filter(m -> !apenasDisponiveis || m.podeSerEmprestado())
            .filter(m -> tipo == null || m.getTipo().equals(tipo))
            .collect(Collectors.toList());
    }
}

---

Uso:

FiltroMaterial filtro = new FiltroMaterial()
    .comTermo("Java")
    .apenasDisponiveis(true)
    .comTipo("Livro");

List<Material> resultado = filtro.aplicar(biblioteca.getAcervo());

---

BENEFICIOS DA EXTENSIBILIDADE

1. Open/Closed Principle: Aberto para extensao, fechado para modificacao
2. Sem quebra de compatibilidade: Codigo legado continua funcionando
3. Isolamento: Novas funcoes sao isoladas em novas classes
4. Reutilizacao: Codigo existente e reutilizado pelas extensoes
5. Testabilidade: Extensoes podem ser testadas independentemente

CONCLUSAO

A arquitetura bem pensada do Sistema de Gerenciamento de Biblioteca torna trivial adicionar 
novas funcionalidades sem comprometer o codigo existente. Esta e a essencia do desenvolvimento 
orientado a objetos bem feito.
