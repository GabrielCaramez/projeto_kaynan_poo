import modelo.*;
import servico.BibliotecaService;

public class Main {
    public static void main(String[] args) {
        BibliotecaService biblioteca = new BibliotecaService();

        System.out.println("=== SISTEMA DE GERENCIAMENTO DE BIBLIOTECA ===\n");

        adicionarMateriais(biblioteca);
        registrarUsuarios(biblioteca);

        biblioteca.listarAcervo();
        biblioteca.listarUsuarios();

        realizarEmprestimos(biblioteca);
        biblioteca.listarEmprestimos();

        realizarBuscas(biblioteca);

        realizarDevolucoes(biblioteca);
        biblioteca.listarEmprestimos();

        exibirEstatisticas(biblioteca);
    }

    private static void adicionarMateriais(BibliotecaService biblioteca) {
        System.out.println("--- Adicionando materiais a biblioteca ---");
        
        Livro livro1 = new Livro(
            "Clean Code",
            "Robert C. Martin",
            "L001",
            "978-0132350884",
            464,
            "Prentice Hall"
        );

        Livro livro2 = new Livro(
            "Design Patterns",
            "Gang of Four",
            "L002",
            "978-0201633610",
            395,
            "Addison-Wesley"
        );

        Revista revista1 = new Revista(
            "Java Magazine",
            "Oracle",
            "R001",
            125,
            "Abril",
            2025
        );

        DVD dvd1 = new DVD(
            "The Matrix",
            "Wachowski",
            "D001",
            "Wachowski",
            136,
            "Ficcao Cientifica"
        );

        biblioteca.adicionarMaterial(livro1);
        biblioteca.adicionarMaterial(livro2);
        biblioteca.adicionarMaterial(revista1);
        biblioteca.adicionarMaterial(dvd1);
        System.out.println();
    }

    private static void registrarUsuarios(BibliotecaService biblioteca) {
        System.out.println("--- Registrando usuarios ---");
        
        Usuario usuario1 = new Usuario("João Silva", "joao@email.com", "123.456.789-00");
        Usuario usuario2 = new Usuario("Maria Santos", "maria@email.com", "987.654.321-00");
        Usuario usuario3 = new Usuario("Pedro Oliveira", "pedro@email.com", "456.789.123-00");

        biblioteca.registrarUsuario(usuario1);
        biblioteca.registrarUsuario(usuario2);
        biblioteca.registrarUsuario(usuario3);
        System.out.println();
    }

    private static void realizarEmprestimos(BibliotecaService biblioteca) {
        System.out.println("--- Realizando emprestimos ---");
        
        biblioteca.realizarEmprestimo("L001", "João Silva");
        biblioteca.realizarEmprestimo("R001", "Maria Santos");
        biblioteca.realizarEmprestimo("D001", "Pedro Oliveira");
        System.out.println();
    }

    private static void realizarBuscas(BibliotecaService biblioteca) {
        System.out.println("--- Buscando materiais ---");
        
        String termo = "Design";
        var resultados = biblioteca.buscarMaterialPorTermo(termo);
        
        System.out.println("Resultados da busca por '" + termo + "':");
        resultados.forEach(m -> System.out.println("  - " + m.getTitulo() + " por " + m.getAutor()));
        System.out.println();
    }

    private static void realizarDevolucoes(BibliotecaService biblioteca) {
        System.out.println("--- Realizando devolucoes ---");
        
        biblioteca.realizarDevolucao("L001", "João Silva");
        biblioteca.realizarDevolucao("R001", "Maria Santos");
        System.out.println();
    }

    private static void exibirEstatisticas(BibliotecaService biblioteca) {
        System.out.println("\n=== ESTATISTICAS DA BIBLIOTECA ===");
        System.out.println("Total de materiais: " + biblioteca.getTotalMateriais());
        System.out.println("Total de usuarios: " + biblioteca.getTotalUsuarios());
        System.out.println("Emprestimos ativos: " + biblioteca.getTotalEmprestimosAtivos());
        System.out.println("Materiais disponiveis: " + biblioteca.listarMateriasDiponiveis().size());
    }
}
