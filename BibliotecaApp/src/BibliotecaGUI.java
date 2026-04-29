import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import modelo.*;
import servico.BibliotecaService;

public class BibliotecaGUI extends JFrame {
    private BibliotecaService biblioteca;
    private JTabbedPane abas;
    
    public BibliotecaGUI() {
        biblioteca = new BibliotecaService();
        inicializarGUI();
        carregarDadosExemplo();
    }
    
    private void inicializarGUI() {
        setTitle("Sistema de Gerenciamento de Biblioteca");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        
        // Cabecalho
        JPanel cabecalho = criarCabecalho();
        painelPrincipal.add(cabecalho, BorderLayout.NORTH);
        
        // Abas
        abas = new JTabbedPane();
        abas.addTab("Acervo", criarAbaAcervo());
        abas.addTab("Usuarios", criarAbaUsuarios());
        abas.addTab("Emprestimos", criarAbaEmprestimos());
        abas.addTab("Adicionar Material", criarAbaAdicionarMaterial());
        abas.addTab("Registrar Usuario", criarAbaRegistrarUsuario());
        
        painelPrincipal.add(abas, BorderLayout.CENTER);
        
        // Rodape
        JPanel rodape = criarRodape();
        painelPrincipal.add(rodape, BorderLayout.SOUTH);
        
        add(painelPrincipal);
        setVisible(true);
    }
    
    private JPanel criarCabecalho() {
        JPanel cabecalho = new JPanel();
        cabecalho.setBackground(new Color(25, 103, 210));
        cabecalho.setPreferredSize(new Dimension(1000, 80));
        cabecalho.setLayout(new BorderLayout());
        
        JLabel titulo = new JLabel("BIBLIOTECA DIGITAL");
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        cabecalho.add(titulo, BorderLayout.WEST);
        
        return cabecalho;
    }
    
    private JPanel criarAbaAcervo() {
        JPanel aba = new JPanel(new BorderLayout());
        
        // Painel de busca
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBusca.setBackground(new Color(240, 240, 240));
        
        JLabel labelBusca = new JLabel("Buscar: ");
        JTextField campoBusca = new JTextField(20);
        JButton botaoBuscar = new JButton("Buscar");
        botaoBuscar.setBackground(new Color(25, 103, 210));
        botaoBuscar.setForeground(Color.WHITE);
        
        JButton botaoLimpar = new JButton("Mostrar Todos");
        botaoLimpar.setBackground(new Color(76, 175, 80));
        botaoLimpar.setForeground(Color.WHITE);
        
        painelBusca.add(labelBusca);
        painelBusca.add(campoBusca);
        painelBusca.add(botaoBuscar);
        painelBusca.add(botaoLimpar);
        
        aba.add(painelBusca, BorderLayout.NORTH);
        
        // Tabela de materiais
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Tipo");
        modelo.addColumn("Titulo");
        modelo.addColumn("Autor");
        modelo.addColumn("Detalhes Especificos");
        modelo.addColumn("Status");
        
        JTable tabela = new JTable(modelo);
        tabela.setFont(new Font("Arial", Font.PLAIN, 12));
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabela.setRowHeight(25);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(200);
        
        JScrollPane scroll = new JScrollPane(tabela);
        aba.add(scroll, BorderLayout.CENTER);
        
        // Acao botao buscar
        botaoBuscar.addActionListener(e -> {
            String termo = campoBusca.getText();
            atualizarTabelaAcervo(modelo, termo);
        });
        
        // Acao botao limpar
        botaoLimpar.addActionListener(e -> {
            campoBusca.setText("");
            atualizarTabelaAcervo(modelo, "");
        });
        
        atualizarTabelaAcervo(modelo, "");
        
        return aba;
    }
    
    private void atualizarTabelaAcervo(DefaultTableModel modelo, String filtro) {
        modelo.setRowCount(0);
        
        List<Material> materiais;
        if (filtro.isEmpty()) {
            materiais = biblioteca.getAcervo();
        } else {
            materiais = biblioteca.buscarMaterialPorTermo(filtro);
        }
        
        for (Material m : materiais) {
            String status = m.podeSerEmprestado() ? "Disponivel" : "Emprestado";
            String detalhes = "";
            
            if (m instanceof Livro) {
                Livro livro = (Livro) m;
                detalhes = "ISBN: " + (livro.getIsbn().isEmpty() ? "N/A" : livro.getIsbn()) + 
                           " | Paginas: " + (livro.getNumeroPaginas() == 0 ? "N/A" : livro.getNumeroPaginas()) +
                           " | Editora: " + (livro.getEditora().isEmpty() ? "N/A" : livro.getEditora());
            } else if (m instanceof Revista) {
                Revista revista = (Revista) m;
                detalhes = "Numero: " + (revista.getNumero() == 0 ? "N/A" : revista.getNumero()) + 
                           " | Mes: " + (revista.getMes().isEmpty() ? "N/A" : revista.getMes()) +
                           " | Ano: " + (revista.getAno() == 0 ? "N/A" : revista.getAno());
            } else if (m instanceof DVD) {
                DVD dvd = (DVD) m;
                detalhes = "Diretor: " + (dvd.getDiretor().isEmpty() ? "N/A" : dvd.getDiretor()) + 
                           " | Duracao: " + (dvd.getDuracao() == 0 ? "N/A" : dvd.getDuracao() + " min") +
                           " | Genero: " + (dvd.getGenero().isEmpty() ? "N/A" : dvd.getGenero());
            }
            
            modelo.addRow(new Object[]{
                m.getId(),
                m.getTipo(),
                m.getTitulo(),
                m.getAutor(),
                detalhes,
                status
            });
        }
    }
    
    private JPanel criarAbaUsuarios() {
        JPanel aba = new JPanel(new BorderLayout());
        
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Nome");
        modelo.addColumn("Email");
        modelo.addColumn("CPF");
        modelo.addColumn("Emprestimos Ativos");
        
        JTable tabela = new JTable(modelo);
        tabela.setFont(new Font("Arial", Font.PLAIN, 12));
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabela.setRowHeight(25);
        
        JScrollPane scroll = new JScrollPane(tabela);
        aba.add(scroll, BorderLayout.CENTER);
        
        // Botao atualizar
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBotoes.setBackground(new Color(240, 240, 240));
        
        JButton botaoAtualizar = new JButton("Atualizar");
        botaoAtualizar.setBackground(new Color(25, 103, 210));
        botaoAtualizar.setForeground(Color.WHITE);
        botaoAtualizar.addActionListener(e -> atualizarTabelaUsuarios(modelo));
        
        painelBotoes.add(botaoAtualizar);
        aba.add(painelBotoes, BorderLayout.NORTH);
        
        atualizarTabelaUsuarios(modelo);
        
        return aba;
    }
    
    private void atualizarTabelaUsuarios(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        
        for (Usuario u : biblioteca.getUsuarios()) {
            modelo.addRow(new Object[]{
                u.getNome(),
                u.getEmail(),
                u.getCpf(),
                u.getTotalEmprestimos()
            });
        }
    }
    
    private JPanel criarAbaEmprestimos() {
        JPanel aba = new JPanel(new BorderLayout());
        
        // Painel superior com opcoes
        JPanel painelOpcoes = new JPanel(new GridLayout(2, 1));
        painelOpcoes.setBackground(new Color(240, 240, 240));
        painelOpcoes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Painel de emprestimo
        JPanel painelEmprestar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelMaterial = new JLabel("ID Material:");
        JTextField campoMaterial = new JTextField(10);
        JLabel labelUsuario = new JLabel("Usuario:");
        JTextField campoUsuario = new JTextField(15);
        JButton botaoEmprestar = new JButton("Emprestar");
        botaoEmprestar.setBackground(new Color(25, 103, 210));
        botaoEmprestar.setForeground(Color.WHITE);
        
        painelEmprestar.add(labelMaterial);
        painelEmprestar.add(campoMaterial);
        painelEmprestar.add(labelUsuario);
        painelEmprestar.add(campoUsuario);
        painelEmprestar.add(botaoEmprestar);
        
        // Painel de devolucao
        JPanel painelDevolver = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelMaterial2 = new JLabel("ID Material:");
        JTextField campoMaterial2 = new JTextField(10);
        JLabel labelUsuario2 = new JLabel("Usuario:");
        JTextField campoUsuario2 = new JTextField(15);
        JButton botaoDevolver = new JButton("Devolver");
        botaoDevolver.setBackground(new Color(76, 175, 80));
        botaoDevolver.setForeground(Color.WHITE);
        
        painelDevolver.add(labelMaterial2);
        painelDevolver.add(campoMaterial2);
        painelDevolver.add(labelUsuario2);
        painelDevolver.add(campoUsuario2);
        painelDevolver.add(botaoDevolver);
        
        painelOpcoes.add(painelEmprestar);
        painelOpcoes.add(painelDevolver);
        
        aba.add(painelOpcoes, BorderLayout.NORTH);
        
        // Tabela de emprestimos
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Material");
        modelo.addColumn("Usuario");
        modelo.addColumn("Data Emprestimo");
        modelo.addColumn("Data Devolucao Prevista");
        modelo.addColumn("Status");
        
        JTable tabela = new JTable(modelo);
        tabela.setFont(new Font("Arial", Font.PLAIN, 11));
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        tabela.setRowHeight(25);
        
        JScrollPane scroll = new JScrollPane(tabela);
        aba.add(scroll, BorderLayout.CENTER);
        
        // Acoes dos botoes
        botaoEmprestar.addActionListener(e -> {
            String idMat = campoMaterial.getText().trim();
            String usr = campoUsuario.getText().trim();
            
            if (idMat.isEmpty() || usr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (biblioteca.realizarEmprestimo(idMat, usr)) {
                JOptionPane.showMessageDialog(this, "Emprestimo realizado com sucesso!", 
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                campoMaterial.setText("");
                campoUsuario.setText("");
                atualizarTabelaEmprestimos(modelo);
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao realizar emprestimo!", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        botaoDevolver.addActionListener(e -> {
            String idMat = campoMaterial2.getText().trim();
            String usr = campoUsuario2.getText().trim();
            
            if (idMat.isEmpty() || usr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (biblioteca.realizarDevolucao(idMat, usr)) {
                JOptionPane.showMessageDialog(this, "Devolucao registrada com sucesso!", 
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                campoMaterial2.setText("");
                campoUsuario2.setText("");
                atualizarTabelaEmprestimos(modelo);
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao registrar devolucao!", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        atualizarTabelaEmprestimos(modelo);
        
        return aba;
    }
    
    private void atualizarTabelaEmprestimos(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        
        for (Emprestimo emp : biblioteca.getEmprestimosAtivos()) {
            String status = emp.estaAtrasado() ? "ATRASADO" : "Ativo";
            modelo.addRow(new Object[]{
                emp.getId().substring(0, 8) + "...",
                emp.getIdMaterial(),
                emp.getNomeUsuario(),
                emp.getDataEmprestimo(),
                emp.getDataPrevistaDevolucao(),
                status
            });
        }
    }
    
    private JPanel criarAbaAdicionarMaterial() {
        JPanel aba = new JPanel();
        aba.setLayout(new BoxLayout(aba, BoxLayout.Y_AXIS));
        aba.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Tipo de material
        JPanel painelTipo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelTipo = new JLabel("Tipo de Material:");
        String[] tipos = {"Livro", "Revista", "DVD"};
        JComboBox<String> comboTipo = new JComboBox<>(tipos);
        comboTipo.setPreferredSize(new Dimension(150, 30));
        painelTipo.add(labelTipo);
        painelTipo.add(comboTipo);
        
        // Campos comuns
        JPanel painelComuns = new JPanel(new GridLayout(4, 2, 10, 10));
        
        JLabel labelId = new JLabel("ID:");
        JTextField campoId = new JTextField();
        JLabel labelTitulo = new JLabel("Titulo:");
        JTextField campoTitulo = new JTextField();
        JLabel labelAutor = new JLabel("Autor:");
        JTextField campoAutor = new JTextField();
        
        painelComuns.add(labelId);
        painelComuns.add(campoId);
        painelComuns.add(labelTitulo);
        painelComuns.add(campoTitulo);
        painelComuns.add(labelAutor);
        painelComuns.add(campoAutor);
        
        // Painel dinamico para campos especificos
        JPanel painelEspecifico = new JPanel(new GridLayout(3, 2, 10, 10));
        painelEspecifico.setBorder(BorderFactory.createTitledBorder("Campos Especificos"));
        
        JLabel label1 = new JLabel("ISBN / Numero / Diretor:");
        JTextField campo1 = new JTextField();
        JLabel label2 = new JLabel("Paginas / Mes / Duracao:");
        JTextField campo2 = new JTextField();
        JLabel label3 = new JLabel("Editora / Ano / Genero:");
        JTextField campo3 = new JTextField();
        
        painelEspecifico.add(label1);
        painelEspecifico.add(campo1);
        painelEspecifico.add(label2);
        painelEspecifico.add(campo2);
        painelEspecifico.add(label3);
        painelEspecifico.add(campo3);
        
        // Botao adicionar
        JButton botaoAdicionar = new JButton("Adicionar Material");
        botaoAdicionar.setBackground(new Color(76, 175, 80));
        botaoAdicionar.setForeground(Color.WHITE);
        botaoAdicionar.setFont(new Font("Arial", Font.BOLD, 14));
        botaoAdicionar.setPreferredSize(new Dimension(200, 40));
        
        botaoAdicionar.addActionListener(e -> {
            String tipo = (String) comboTipo.getSelectedItem();
            String id = campoId.getText().trim();
            String titulo = campoTitulo.getText().trim();
            String autor = campoAutor.getText().trim();
            String especifico1 = campo1.getText().trim();
            String especifico2 = campo2.getText().trim();
            String especifico3 = campo3.getText().trim();
            
            if (id.isEmpty() || titulo.isEmpty() || autor.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha os campos obrigatorios!", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                Material material = null;
                
                if (tipo.equals("Livro")) {
                    if (especifico1.isEmpty() && especifico2.isEmpty() && especifico3.isEmpty()) {
                        material = new Livro(titulo, autor, id);
                    } else {
                        int paginas = especifico2.isEmpty() ? 0 : Integer.parseInt(especifico2);
                        material = new Livro(titulo, autor, id, especifico1, paginas, especifico3);
                    }
                } else if (tipo.equals("Revista")) {
                    if (especifico1.isEmpty() && especifico2.isEmpty() && especifico3.isEmpty()) {
                        material = new Revista(titulo, autor, id);
                    } else {
                        int numero = especifico1.isEmpty() ? 0 : Integer.parseInt(especifico1);
                        int ano = especifico3.isEmpty() ? 0 : Integer.parseInt(especifico3);
                        material = new Revista(titulo, autor, id, numero, especifico2, ano);
                    }
                } else if (tipo.equals("DVD")) {
                    if (especifico1.isEmpty() && especifico2.isEmpty() && especifico3.isEmpty()) {
                        material = new DVD(titulo, autor, id);
                    } else {
                        int duracao = especifico2.isEmpty() ? 0 : Integer.parseInt(especifico2);
                        material = new DVD(titulo, autor, id, especifico1, duracao, especifico3);
                    }
                }
                
                if (material != null) {
                    biblioteca.adicionarMaterial(material);
                    JOptionPane.showMessageDialog(this, "Material adicionado com sucesso!", 
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    campoId.setText("");
                    campoTitulo.setText("");
                    campoAutor.setText("");
                    campo1.setText("");
                    campo2.setText("");
                    campo3.setText("");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Valores numericos invalidos!", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        aba.add(painelTipo);
        aba.add(Box.createVerticalStrut(10));
        aba.add(painelComuns);
        aba.add(Box.createVerticalStrut(10));
        aba.add(painelEspecifico);
        aba.add(Box.createVerticalStrut(20));
        aba.add(botaoAdicionar);
        aba.add(Box.createVerticalGlue());
        
        return aba;
    }
    
    private JPanel criarAbaRegistrarUsuario() {
        JPanel aba = new JPanel();
        aba.setLayout(new BoxLayout(aba, BoxLayout.Y_AXIS));
        aba.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        JPanel painelFormulario = new JPanel(new GridLayout(3, 2, 15, 15));
        
        JLabel labelNome = new JLabel("Nome:");
        labelNome.setFont(new Font("Arial", Font.BOLD, 12));
        JTextField campoNome = new JTextField();
        campoNome.setPreferredSize(new Dimension(300, 30));
        
        JLabel labelEmail = new JLabel("Email:");
        labelEmail.setFont(new Font("Arial", Font.BOLD, 12));
        JTextField campoEmail = new JTextField();
        campoEmail.setPreferredSize(new Dimension(300, 30));
        
        JLabel labelCpf = new JLabel("CPF:");
        labelCpf.setFont(new Font("Arial", Font.BOLD, 12));
        JTextField campoCpf = new JTextField();
        campoCpf.setPreferredSize(new Dimension(300, 30));
        
        painelFormulario.add(labelNome);
        painelFormulario.add(campoNome);
        painelFormulario.add(labelEmail);
        painelFormulario.add(campoEmail);
        painelFormulario.add(labelCpf);
        painelFormulario.add(campoCpf);
        
        JButton botaoRegistrar = new JButton("Registrar Usuario");
        botaoRegistrar.setBackground(new Color(76, 175, 80));
        botaoRegistrar.setForeground(Color.WHITE);
        botaoRegistrar.setFont(new Font("Arial", Font.BOLD, 14));
        botaoRegistrar.setPreferredSize(new Dimension(200, 40));
        
        botaoRegistrar.addActionListener(e -> {
            String nome = campoNome.getText().trim();
            String email = campoEmail.getText().trim();
            String cpf = campoCpf.getText().trim();
            
            if (nome.isEmpty() || email.isEmpty() || cpf.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Usuario usuario = new Usuario(nome, email, cpf);
            biblioteca.registrarUsuario(usuario);
            JOptionPane.showMessageDialog(this, "Usuario registrado com sucesso!", 
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            
            campoNome.setText("");
            campoEmail.setText("");
            campoCpf.setText("");
        });
        
        aba.add(painelFormulario);
        aba.add(Box.createVerticalStrut(30));
        aba.add(botaoRegistrar);
        aba.add(Box.createVerticalGlue());
        
        return aba;
    }
    
    private JPanel criarRodape() {
        JPanel rodape = new JPanel();
        rodape.setBackground(new Color(200, 200, 200));
        
        JLabel labelInfo = new JLabel("Sistema de Gerenciamento de Biblioteca v1.0 - Desenvolvido com POO em Java");
        labelInfo.setFont(new Font("Arial", Font.PLAIN, 11));
        labelInfo.setForeground(new Color(50, 50, 50));
        
        rodape.add(labelInfo);
        
        return rodape;
    }
    
    private void carregarDadosExemplo() {
        Livro livro1 = new Livro("Clean Code", "Robert C. Martin", "L001", "978-0132350884", 464, "Prentice Hall");
        Livro livro2 = new Livro("Design Patterns", "Gang of Four", "L002", "978-0201633610", 395, "Addison-Wesley");
        Revista revista1 = new Revista("Java Magazine", "Oracle", "R001", 125, "Abril", 2025);
        DVD dvd1 = new DVD("The Matrix", "Wachowski", "D001", "Wachowski", 136, "Ficcao Cientifica");
        
        biblioteca.adicionarMaterial(livro1);
        biblioteca.adicionarMaterial(livro2);
        biblioteca.adicionarMaterial(revista1);
        biblioteca.adicionarMaterial(dvd1);
        
        Usuario usuario1 = new Usuario("Joao Silva", "joao@email.com", "123.456.789-00");
        Usuario usuario2 = new Usuario("Maria Santos", "maria@email.com", "987.654.321-00");
        Usuario usuario3 = new Usuario("Pedro Oliveira", "pedro@email.com", "456.789.123-00");
        
        biblioteca.registrarUsuario(usuario1);
        biblioteca.registrarUsuario(usuario2);
        biblioteca.registrarUsuario(usuario3);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BibliotecaGUI());
    }
}
