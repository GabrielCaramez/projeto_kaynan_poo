SISTEMA DE GERENCIAMENTO DE BIBLIOTECA

Projeto de demonstracao dos principais principios de Programacao Orientada a Objetos em Java.

═══════════════════════════════════════════════════════════════════════════════

RESUMO - PRINCIPIOS SOLID UTILIZADOS

✓ S (Single Responsibility)      - Cada classe tem uma unica responsabilidade
✓ O (Open/Closed)                - Sistema aberto para extensao, fechado para modificacao
✓ L (Liskov Substitution)        - Subclasses podem substituir a superclasse Material
✓ I (Interface Segregation)      - Interfaces especificas (Emprestavel, Buscavel)
✓ D (Dependency Inversion)       - Dependencias em abstraoes, nao em implementacoes

═══════════════════════════════════════════════════════════════════════════════

DESCRICAO

Trata-se de um sistema completo de gerenciamento de biblioteca que permite registrar materiais 
(livros, revistas, DVDs), usuarios, realizar emprestimos, devolutivas e buscas. O projeto foi 
desenvolvido seguindo rigorosamente os principios e boas praticas de POO.

PRINCIPIOS DE POO APLICADOS

1. ENCAPSULAMENTO

O encapsulamento garante que os dados internos das classes sao protegidos e acessados apenas 
atraves de metodos publicos controlados.

Exemplo: A classe Usuario encapsula seus atributos privados (nome, email, cpf) e fornece 
apenas metodos publicos para acessar e modificar esses dados. A lista de emprestimos ativos 
e encapsulada, permitindo que apenas o servico possa modificar o estado do usuario.

Beneficio: Protege a integridade dos dados e permite mudancas internas na implementacao sem 
afetar classes consumidoras.

2. HERANCA

A heranca permite que subclasses herdem comportamentos e atributos de uma classe base, 
promovendo reutilizacao de codigo.

Estrutura:
- Material (classe abstrata base)
  - Livro (herda de Material)
  - Revista (herda de Material)
  - DVD (herda de Material)

A classe Material define o comportamento comum para todos os tipos de materiais (emprestimo, 
devolucao, busca), enquanto as subclasses (Livro, Revista, DVD) especializam com atributos 
e comportamentos unicos.

Exemplo: Todos os materiais herdam o metodo registrarEmprestimo() de Material, mas cada 
tipo de material pode ter seu proprio periodo de emprestimo (diasDisponivel).

Beneficio: Reduz duplicacao de codigo e facilita a manutencao e extensao do sistema.

3. POLIMORFISMO

O polimorfismo permite que objetos de diferentes tipos sejam tratados atraves de uma 
interface comum. Neste projeto, utiliza-se polimorfismo parametrico e dinamico.

Exemplo: Na BibliotecaService, a lista de acervo armazena objetos do tipo Material, mas 
contém instancias de Livro, Revista e DVD. Chamadas a metodos como podeSerEmprestado() 
ou getTipo() sao executadas de forma apropriada para cada tipo especifico.

Implementacao do metodo getTipo():
- Livro retorna "Livro"
- Revista retorna "Revista"
- DVD retorna "DVD"

Beneficio: Permite criar codigo generico que funciona com multiplos tipos, facilitando 
extensoes futuras sem modificar codigo existente.

4. ABSTRACAO

A abstracao permite ocultar complexidade e expor apenas as funcionalidades essenciais. 
Este projeto utiliza classes abstratas e interfaces.

Classes Abstratas:
- Material: Define contratos para todos os materiais, incluindo o metodo abstrato getTipo()
  que cada subclasse deve implementar.

Interfaces:
- Emprestavel: Define contrato para metodos relacionados a emprestimo
  (podeSerEmprestado, registrarEmprestimo, registrarDevolucao, getDiasDisponivel)
- Buscavel: Define contrato para metodos de busca
  (contem, getIdentificador)

Beneficio: Facilita a compressao da estrutura do sistema e permite trocar implementacoes 
sem quebrar o codigo cliente.

5. PRINCIPIOS SOLID APLICADOS

5.1 Single Responsibility Principle (SRP) ✓

DESCRICAO: Cada classe tem uma unica responsabilidade bem definida.

APLICACAO NO PROJETO:
- Material.java: Gerenciar dados e estado do material (titulo, autor, disponibilidade)
- Usuario.java: Gerenciar dados do usuario (nome, email, cpf) e sua lista de emprestimos
- Emprestimo.java: Rastrear os detalhes de um emprestimo especifico (datas, usuario, material)
- BibliotecaService.java: Orquestrar operacoes entre as classes (adicionar, emprestar, devolver)
- Livro.java, Revista.java, DVD.java: Especializar Material com atributos e comportamentos unicos

BENEFICIOS:
- Codigo mais legivel e compreensivel
- Facilita testes unitarios independentes
- Mudancas em uma responsabilidade nao afetam outras classes
- Permite reutilizacao de classes em diferentes contextos

EXEMPLO DE CODIGO:
  public class Material {  // Responsavel apenas por dados do material
      protected String id;
      protected String titulo;
      protected boolean emprestado;
      // ... getters e setters
  }
  
  public class BibliotecaService {  // Responsavel apenas por orquestracao
      public void realizarEmprestimo(String idMaterial, String nomeUsuario) { ... }
      public void realizarDevolucao(String idMaterial, String nomeUsuario) { ... }
  }

---

5.2 Open/Closed Principle (OCP) ✓

DESCRICAO: Software deve estar aberto para extensao mas fechado para modificacao.

APLICACAO NO PROJETO:
- Material e uma classe abstrata que define o contrato para todos os materiais
- Para adicionar um novo tipo (ex: Audiolivro), basta criar uma classe herdando de Material
- Nao e necessario modificar Material, BibliotecaService ou outras classes existentes
- BibliotecaService trabalha com List<Material>, portanto aceita qualquer subclasse

ESTRUTURA:
  public abstract class Material { ... }  // Fechado para modificacao
  
  public class Livro extends Material { ... }  // Extensoes possiveis
  public class Revista extends Material { ... }
  public class DVD extends Material { ... }
  public class Audiolivro extends Material { ... }  // Novo tipo sem alterar nada!

BENEFICIOS:
- Reducao de efeitos colaterais ao adicionar novas funcionalidades
- Codigo mais seguro e menos propenso a bugs
- Facilita evolucao e manutencao do sistema

---

5.3 Liskov Substitution Principle (LSP) ✓

DESCRICAO: Objetos de subclasses devem poder substituir objetos da superclasse sem quebrar 
o funcionamento do programa.

APLICACAO NO PROJETO:
- Qualquer subclasse de Material (Livro, Revista, DVD) pode ser usada onde Material e esperado
- Todos os metodos abstratos sao implementados consistentemente em cada subclasse
- BibliotecaService trata todos os materiais uniformemente via polymorfismo
- Cada material respeita o contrato definido em Material

EXEMPLO NA PRATICA:
  List<Material> acervo = new ArrayList<>();
  acervo.add(new Livro(...));          // OK
  acervo.add(new Revista(...));        // OK
  acervo.add(new DVD(...));            // OK
  
  for(Material m : acervo) {
      if(m.podeSerEmprestado()) {     // Funciona para todos!
          m.registrarEmprestimo(usuario);
      }
  }

BENEFICIOS:
- Uniformidade no tratamento de diferentes tipos
- Facilita adicionar novos tipos sem quebrar codigo existente
- Reduz necessidade de type casting ou verificacoes de tipo

---

5.4 Interface Segregation Principle (ISP) ✓

DESCRICAO: Clientes nao devem ser forcados a depender de interfaces que nao usam. 
Prefira multiplas interfaces especificas.

APLICACAO NO PROJETO:
- Interface Emprestavel.java: Define apenas operacoes de emprestimo
  (podeSerEmprestado(), registrarEmprestimo(), registrarDevolucao(), getDiasDisponivel())
  
- Interface Buscavel.java: Define apenas operacoes de busca
  (contem(termo), getIdentificador())

- Material implementa ambas as interfaces, mas apenas os metodos relevantes sao definidos
- Nao existe uma interface gigante com todos os metodos

ESTRUTURA:
  public interface Emprestavel {
      boolean podeSerEmprestado();
      void registrarEmprestimo(String usuario);
      void registrarDevolucao();
      int getDiasDisponivel();
  }
  
  public interface Buscavel {
      boolean contem(String termo);
      String getIdentificador();
  }
  
  public abstract class Material implements Emprestavel, Buscavel { ... }

BENEFICIOS:
- Interfaces mais claras e focadas
- Facilita compreensao dos contratos
- Reduz acoplamento entre classes
- Permite implementar apenas o necessario

---

5.5 Dependency Inversion Principle (DIP) ✓

DESCRICAO: Depend de abstraoes, nao de implementacoes concretas. Classes de alto nivel 
nao devem depender de classes de baixo nivel.

APLICACAO NO PROJETO:
- BibliotecaService depende de abstraoes:
  * List<Material> (abstrata) e nao de List<Livro>, List<DVD>, etc
  * Usuario (classe modelo) e nao de implementacoes concretas
  * As operacoes sao executadas via metodos abstratos de Material
  
- BibliotecaService nao conhece ou instancia Livro, Revista ou DVD diretamente
- Novos tipos de Material podem ser adicionados sem afetar BibliotecaService

EXEMPLO:
  // CORRETO (depende de abstracao):
  public class BibliotecaService {
      private List<Material> acervo;  // Material e abstrato
      
      public void adicionarMaterial(Material m) {  // Aceita qualquer Material
          acervo.add(m);
      }
  }
  
  // INCORRETO (depende de implementacoes):
  public class BibliotecaService {
      private List<Livro> livros;
      private List<Revista> revistas;
      private List<DVD> dvds;
      // Muito acoplado e dificil de estender!
  }

BENEFICIOS:
- Reducao de acoplamento entre classes
- Facilita testes (pode usar mocks/stubs)
- Permite trocar implementacoes facilmente
- Codigo mais flexivel e escalavel

ESTRUTURA DO PROJETO

BibliotecaApp/
  src/
    modelo/
      Material.java          - Classe abstrata base para todos os materiais
      Livro.java             - Implementacao de Livro
      Revista.java           - Implementacao de Revista
      DVD.java               - Implementacao de DVD
      Usuario.java           - Modelo de Usuario
      Emprestimo.java        - Modelo de Emprestimo
    interface_/
      Emprestavel.java       - Interface para operacoes de emprestimo
      Buscavel.java          - Interface para operacoes de busca
    servico/
      BibliotecaService.java - Servico principal que orquestra as operacoes
    Main.java                - Classe principal com demonstracao de uso

FUNCIONALIDADES PRINCIPAIS

1. Gerenciamento de Materiais
   - Adicionar diferentes tipos de materiais (Livro, Revista, DVD)
   - Buscar materiais por ID ou termo
   - Listar materiais disponiveis ou emprestados

2. Gerenciamento de Usuarios
   - Registrar novos usuarios
   - Rastrear emprestimos ativos por usuario

3. Sistema de Emprestimo/Devolucao
   - Emprestar materiais para usuarios
   - Registrar devolucoes
   - Controlar prazos de devolucao
   - Detectar emprestimos atrasados

4. Busca e Filtros
   - Buscar materiais por termo (titulo ou autor)
   - Filtrar materiais por status (disponivel/emprestado)
   - Listar emprestimos ativos ou atrasados

PADROES DE DESIGN UTILIZADOS

1. Strategy Pattern
   BibliotecaService encapsula diferentes estrategias de operacao (emprestimo, busca, etc.)

2. Template Method Pattern
   Material define a estrutura basica enquanto subclasses customizam comportamentos

3. Service Locator Pattern
   BibliotecaService atua como localizador de recursos (materiais, usuarios)

COMO EXECUTAR

1. Compilar:
   javac -d bin src/interface_/*.java src/modelo/*.java src/servico/*.java src/Main.java

2. Executar:
   java -cp bin Main

EXEMPLO DE SAIDA

=== SISTEMA DE GERENCIAMENTO DE BIBLIOTECA ===

--- Adicionando materiais a biblioteca ---
Material adicionado: Clean Code
Material adicionado: Design Patterns
Material adicionado: Java Magazine
Material adicionado: The Matrix

--- Registrando usuarios ---
Usuario registrado: Joao Silva
Usuario registrado: Maria Santos
Usuario registrado: Pedro Oliveira

=== ACERVO DA BIBLIOTECA ===
Livro {titulo='Clean Code', autor='Robert C. Martin', isbn='978-0132350884', paginas=464, editora='Prentice Hall', disponivel=true}
Livro {titulo='Design Patterns', autor='Gang of Four', isbn='978-0201633610', paginas=395, editora='Addison-Wesley', disponivel=true}
Revista {titulo='Java Magazine', numero=125, mes='Abril', ano=2025, disponivel=true}
DVD {titulo='The Matrix', diretor='Wachowski', duracao=136 minutos, genero='Ficcao Cientifica', disponivel=true}

--- Realizando emprestimos ---
Emprestimo registrado: Clean Code para Joao Silva
Emprestimo registrado: Java Magazine para Maria Santos
Emprestimo registrado: The Matrix para Pedro Oliveira

=== EMPRESTIMOS ATIVOS ===
Emprestimo {id='...', material='L001', usuario='Joao Silva', emprestadoEm=2026-04-24, devolverEm=2026-05-08, ativo=true}

--- Realizando devolucoes ---
Devolucao registrada: Clean Code devolvido por Joao Silva
Devolucao registrada: Java Magazine devolvido por Maria Santos

=== ESTATISTICAS DA BIBLIOTECA ===
Total de materiais: 4
Total de usuarios: 3
Emprestimos ativos: 1
Materiais disponiveis: 3

BENEFICIOS DA ARQUITETURA POO

1. Mantenibilidade: Codigo organizado em classes com responsabilidades claras
2. Extensibilidade: Novos tipos de materiais podem ser adicionados facilmente
3. Reutilizacao: Comportamentos comuns sao herdados de Material
4. Testabilidade: Cada classe pode ser testada isoladamente
5. Flexibilidade: O sistema pode evoluir sem quebrar codigo existente
6. Legibilidade: Codigo autoexplicativo com nomes significativos

POSSIBILIDADES DE EXTENSAO

1. Adicionar sistema de multas por atraso
2. Implementar sistema de reservas
3. Adicionar categorias/generos para materiais
4. Criar sistema de recomendacoes
5. Implementar persistencia em banco de dados
6. Adicionar autenticacao de usuarios
7. Criar interface grafica (GUI)
8. Implementar API REST para acesso remoto

CONCLUSAO

Este projeto demonstra como aplicar os principios fundamentais de Programacao Orientada 
a Objetos em uma aplicacao real. Atraves da combinacao de encapsulamento, heranca, 
polimorfismo e abstracao, criamos um sistema flexivel, reutilizavel e facilmente mantenivel.

OS 5 PRINCIPIOS SOLID APLICADOS NESTE PROJETO:

1. Single Responsibility Principle (SRP)
   Cada classe possui uma responsabilidade bem definida e coesa, facilitando manutencao
   e teste isolado de componentes.

2. Open/Closed Principle (OCP)
   O sistema pode ser estendido com novos tipos de material (Audiolivro, Mapa, etc)
   sem necessidade de modificar classes existentes.

3. Liskov Substitution Principle (LSP)
   Qualquer Material pode substituir outro em tempo de execucao sem quebrar o sistema,
   permitindo uso uniforme via polimorfismo.

4. Interface Segregation Principle (ISP)
   Interfaces focadas (Emprestavel, Buscavel) evitam forcas clientes a implementar
   metodos desnecessarios.

5. Dependency Inversion Principle (DIP)
   BibliotecaService depende de abstraoes (Material), facilitando testes e evolucao
   sem acoplamento com implementacoes concretas.

O projeto evidencia como aplicar SOLID resulta em:
- Codigo mais limpo e organizado
- Manutencao facilitada
- Extensoes sem risco de quebra
- Testes mais eficientes
- Reutilizacao de componentes
- Reducao de acoplamento e aumento de coesao
