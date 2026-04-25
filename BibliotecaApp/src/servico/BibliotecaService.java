package servico;

import modelo.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BibliotecaService {
    private List<Material> acervo;
    private List<Usuario> usuarios;
    private List<Emprestimo> emprestimos;

    public BibliotecaService() {
        this.acervo = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.emprestimos = new ArrayList<>();
    }

    public void adicionarMaterial(Material material) {
        if (material != null && !acervo.contains(material)) {
            acervo.add(material);
            System.out.println("Material adicionado: " + material.getTitulo());
        }
    }

    public void registrarUsuario(Usuario usuario) {
        if (usuario != null && !usuarios.contains(usuario)) {
            usuarios.add(usuario);
            System.out.println("Usuario registrado: " + usuario.getNome());
        }
    }

    public Optional<Material> buscarMaterialPorId(String id) {
        return acervo.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();
    }

    public Optional<Usuario> buscarUsuarioPorNome(String nome) {
        return usuarios.stream()
                .filter(u -> u.getNome().equalsIgnoreCase(nome))
                .findFirst();
    }

    public List<Material> buscarMaterialPorTermo(String termo) {
        return acervo.stream()
                .filter(m -> m.contem(termo))
                .collect(Collectors.toList());
    }

    public List<Material> listarMateriasDiponiveis() {
        return acervo.stream()
                .filter(Material::podeSerEmprestado)
                .collect(Collectors.toList());
    }

    public List<Material> listarMateriasEmprestadas() {
        return acervo.stream()
                .filter(Material::isEmprestado)
                .collect(Collectors.toList());
    }

    public boolean realizarEmprestimo(String idMaterial, String nomeUsuario) {
        Optional<Material> material = buscarMaterialPorId(idMaterial);
        Optional<Usuario> usuario = buscarUsuarioPorNome(nomeUsuario);

        if (material.isEmpty() || usuario.isEmpty()) {
            System.out.println("Erro: Material ou usuario nao encontrado");
            return false;
        }

        Material mat = material.get();
        Usuario usr = usuario.get();

        if (!mat.podeSerEmprestado()) {
            System.out.println("Erro: Material nao esta disponivel");
            return false;
        }

        mat.registrarEmprestimo(nomeUsuario);
        usr.adicionarEmprestimo(idMaterial);
        
        Emprestimo emp = new Emprestimo(idMaterial, nomeUsuario, mat.getDiasDisponivel());
        emprestimos.add(emp);
        
        return true;
    }

    public boolean realizarDevolucao(String idMaterial, String nomeUsuario) {
        Optional<Material> material = buscarMaterialPorId(idMaterial);
        Optional<Usuario> usuario = buscarUsuarioPorNome(nomeUsuario);

        if (material.isEmpty() || usuario.isEmpty()) {
            System.out.println("Erro: Material ou usuario nao encontrado");
            return false;
        }

        Material mat = material.get();
        Usuario usr = usuario.get();

        if (!mat.isEmprestado()) {
            System.out.println("Erro: Material nao foi emprestado");
            return false;
        }

        mat.registrarDevolucao();
        usr.removerEmprestimo(idMaterial);

        Optional<Emprestimo> emprestimo = emprestimos.stream()
                .filter(e -> e.getIdMaterial().equals(idMaterial) && 
                           e.getNomeUsuario().equals(nomeUsuario) &&
                           e.isAtivo())
                .findFirst();

        if (emprestimo.isPresent()) {
            Emprestimo emp = emprestimo.get();
            emp.finalizarEmprestimo();
            
            if (emp.estaAtrasado()) {
                System.out.println("Aviso: Emprestimo foi devolvido com atraso!");
            }
        }

        return true;
    }

    public void listarAcervo() {
        System.out.println("\n=== ACERVO DA BIBLIOTECA ===");
        if (acervo.isEmpty()) {
            System.out.println("Nenhum material no acervo");
            return;
        }
        acervo.forEach(System.out::println);
    }

    public void listarUsuarios() {
        System.out.println("\n=== USUARIOS REGISTRADOS ===");
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuario registrado");
            return;
        }
        usuarios.forEach(System.out::println);
    }

    public void listarEmprestimos() {
        System.out.println("\n=== EMPRESTIMOS ATIVOS ===");
        List<Emprestimo> ativos = emprestimos.stream()
                .filter(Emprestimo::isAtivo)
                .collect(Collectors.toList());
        
        if (ativos.isEmpty()) {
            System.out.println("Nenhum emprestimo ativo");
            return;
        }
        ativos.forEach(System.out::println);
    }

    public void listarEmprestimosAtrasados() {
        System.out.println("\n=== EMPRESTIMOS ATRASADOS ===");
        List<Emprestimo> atrasados = emprestimos.stream()
                .filter(Emprestimo::estaAtrasado)
                .collect(Collectors.toList());
        
        if (atrasados.isEmpty()) {
            System.out.println("Nenhum emprestimo atrasado");
            return;
        }
        atrasados.forEach(System.out::println);
    }

    public int getTotalMateriais() {
        return acervo.size();
    }

    public int getTotalUsuarios() {
        return usuarios.size();
    }

    public int getTotalEmprestimosAtivos() {
        return (int) emprestimos.stream()
                .filter(Emprestimo::isAtivo)
                .count();
    }

    public List<Material> getAcervo() {
        return new ArrayList<>(acervo);
    }

    public List<Usuario> getUsuarios() {
        return new ArrayList<>(usuarios);
    }

    public List<Emprestimo> getEmprestimosAtivos() {
        return emprestimos.stream()
                .filter(Emprestimo::isAtivo)
                .collect(Collectors.toList());
    }
}
