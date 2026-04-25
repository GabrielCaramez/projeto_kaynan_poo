SISTEMA DE GERENCIAMENTO DE BIBLIOTECA

Projeto de demonstracao dos principais principios de Programacao Orientada a Objetos em Java.

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

5. PRINCIPIOS SOLID

5.1 Single Responsibility Principle (SRP)

Cada classe tem uma unica responsabilidade:
- Material: Gerenciar dados e estado do material
- Usuario: Gerenciar dados e emprestimos do usuario
- Emprestimo: Rastrear detalhes de um emprestimo
- BibliotecaService: Orquestrar operacoes entre classes

5.2 Open/Closed Principle (OCP)

O sistema esta aberto para extensao mas fechado para modificacao. Para adicionar um novo 
tipo de material (ex: Audiolivro), basta criar uma nova classe herdando de Material sem 
modificar classes existentes.

5.3 Liskov Substitution Principle (LSP)

Qualquer subclasse de Material pode ser usada onde Material e esperado. A substituicao de 
um Livro por um DVD na lista de acervo nao quebra o funcionamento do sistema.

5.4 Interface Segregation Principle (ISP)

Interfaces pequenas e especificas foram criadas (Emprestavel e Buscavel) em vez de uma 
interface grande. Classes implementam apenas as interfaces relevantes.

5.5 Dependency Inversion Principle (DIP)

BibliotecaService depende de abstraoes (Material, Usuario) e nao de implementacoes concretas. 
Isso permite trocar implementacoes sem afetar o servico.

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

Os principios SOLID garantem que o codigo seja robusto e preparado para evolucoes futuras, 
mantendo baixo o acoplamento e alta a coesao entre as classes.
