# FieldOps — Etapa 4

Projeto de portfólio em Java 21 que simula um sistema corporativo de gestão de
operações de campo. O objetivo é desenvolver gradualmente o cadastro de
colaboradores, veículos e equipes, o acompanhamento de ordens de serviço e
indicadores operacionais.

**Status: em desenvolvimento — Etapa 4 concluída.** Atualmente, o projeto contém
a estrutura Maven, entidades de colaboradores, veículos e equipes, repositórios
em memória, serviços de cadastro e uma interface gráfica desktop em Swing.

A primeira versão será desenvolvida em Java puro, sem frameworks e sem banco
de dados, com foco em compreender os fundamentos e as regras de negócio.

## Habilidades em desenvolvimento

Praticadas na etapa atual:

- Java 21 e Programação Orientada a Objetos: classes, objetos e construtores.
- Encapsulamento: atributos privados, getters e ausência de setters desnecessários.
- Enums para representar cargos, turnos e situações do colaborador.
- Validação de dados e uso de `IllegalArgumentException`.
- Organização em packages e separação entre entidade e ponto de entrada.
- Collections com `ArrayList` para armazenamento em memória.
- `Optional` para representar buscas que podem não encontrar resultado.
- Serviço com regras de negócio para impedir ID e matrícula duplicados.
- Composição de objetos: uma equipe possui líder, membros e veículo.
- Validação de placas e nomes duplicados por meio de serviços.
- Interface gráfica desktop com Swing.
- Separação entre dados iniciais, regras de negócio e camada visual.
- Maven para configuração e compilação do projeto.
- Git e GitHub para versionamento e documentação da evolução.

Planejadas para as próximas etapas:

- `HashMap` para consultas mais diretas em repositórios.
- Exceptions específicas de negócio.
- Entrada de dados e navegação por menu no terminal.
- `LocalDateTime` e `Duration` para acompanhar o atendimento de ordens.
- Streams para consultas, agregações e indicadores operacionais.

## Evolução prevista

- [x] Estrutura Maven, Employee, enums e demonstração inicial.
- [x] Cadastro e listagem de colaboradores em memória.
- [x] Gestão de veículos e composição de equipes.
- [x] Interface gráfica inicial para substituir a saída no terminal.
- [ ] Criação, despacho e acompanhamento de ordens de serviço.
- [ ] Dashboard operacional interativo na interface.

## Executar

Pré-requisitos: JDK 21 e Maven disponíveis no terminal. Confira com
`java -version` e `mvn -version`.

```shell
mvn compile
java -cp target/classes com.fieldops.application.Main
```

Ao executar o `Main`, o sistema abre uma janela desktop com menu lateral,
dashboard, colaboradores, veículos e equipes.

No IntelliJ, abra o `pom.xml` como projeto Maven (ou use a opção de adicionar
como projeto Maven). Configure o SDK do projeto e o JDK do Maven para Java 21.
Execute o método `main` de `com.fieldops.application.Main`.

### Ambiente local preparado

Nesta máquina, o Temurin JDK 21 foi baixado para `.tools/jdk-21.0.12.1+1`
e registrado no IntelliJ como `FieldOps Java 21`. A pasta `.tools` é ignorada
pelo Git. O projeto aponta para esse SDK e inclui a configuração de execução
`FieldOps`. Se a IDE já estava aberta, reabra o projeto para carregar as alterações.

O Java e o Maven não foram adicionados ao PATH global. Para compilar e executar
no PowerShell desta máquina, a partir da pasta do projeto:

```powershell
$env:JAVA_HOME = "$PWD/.tools/jdk-21.0.12.1+1"
& 'C:/Program Files/JetBrains/IntelliJ IDEA 2026.1/plugins/maven/lib/maven3/bin/mvn.cmd' "-Dmaven.repo.local=$PWD/target/maven-cache" compile
& "$env:JAVA_HOME/bin/java.exe" -cp target/classes com.fieldops.application.Main
```

Verificações realizadas com Java 21: compilação, execução do `Main`, preservação
de uma matrícula com zeros iniciais e rejeição de ID zero, nome vazio e status
nulo. Também foram validados o cadastro e a listagem de colaboradores em memória.
Na Etapa 3, também foram validados o cadastro de veículos e a composição de uma
equipe com líder, membros e veículo. Os experimentos ficam em `target`, fora do
código da aplicação.

Na Etapa 4, a saída principal no terminal foi substituída por uma interface
gráfica em Swing. O terminal ainda pode ser usado para compilar e iniciar o
programa, mas a interação visual acontece na janela do sistema.

Saída esperada:

```text
=== FIELDOPS ===
Registered employees: 4
Registered vehicles: 2
Registered teams: 1

--- Employees ---
ID: 1
Name: Ana Silva
Registration: 00123
Role: FIELD_TECHNICIAN
Work shift: MORNING
Status: AVAILABLE

ID: 2
Name: Bruno Costa
Registration: 00124
Role: TEAM_LEADER
Work shift: AFTERNOON
Status: TRAINING

ID: 3
Name: Carla Souza
Registration: 00125
Role: SUPERVISOR
Work shift: MORNING
Status: AVAILABLE

ID: 4
Name: Ronald Santana
Registration: 00126
Role: SUPERVISOR
Work shift: MORNING
Status: AVAILABLE

--- Vehicles ---
ID: 1
Plate: FOP-1024
Model: Fiat Fiorino
Type: VAN
Status: AVAILABLE

ID: 2
Plate: FOP-2048
Model: Volkswagen Delivery
Type: TRUCK
Status: MAINTENANCE

--- Teams ---
ID: 1
Name: Equipe Norte
Leader: Bruno Costa
Vehicle: FOP-1024
Members: 2
```

## Organização

```text
src/main/java/com/fieldops/
  application/Main.java
  application/FieldOpsData.java
  application/SampleData.java
  ui/FieldOpsFrame.java
  model/Employee.java
  model/Team.java
  model/Vehicle.java
  enums/EmployeeStatus.java
  enums/JobRole.java
  enums/VehicleStatus.java
  enums/VehicleType.java
  enums/WorkShift.java
  service/EmployeeService.java
  service/TeamService.java
  service/VehicleService.java
  service/package-info.java
  repository/EmployeeRepository.java
  repository/TeamRepository.java
  repository/VehicleRepository.java
  repository/package-info.java
  exception/package-info.java
```

Os arquivos `package-info.java` documentam a finalidade dos packages. Exceptions
específicas de negócio ainda serão implementadas em etapas futuras.

## Decisões para entender e explicar

- `Employee` representa um colaborador e protege seus dados com atributos
  privados. Getters permitem leitura sem permitir alterações arbitrárias.
- Os campos são `final` nesta etapa. Futuras alterações de situação deverão
  acontecer por operações explícitas de negócio.
- O ID é um `long` positivo, informado manualmente por enquanto. A matrícula é
  uma `String`, preservando zeros à esquerda e permitindo letras.
- O construtor exige os seis campos e rejeita dados básicos inválidos com
  `IllegalArgumentException`. Textos são verificados com `isBlank()` e têm
  espaços nas extremidades removidos com `strip()`.
- `requireText` centraliza a validação usada por nome e matrícula. É privado
  porque é um detalhe interno, e estático porque não depende de uma instância.
- Enums limitam os cargos, turnos e estados a valores conhecidos. Os cargos
  e turnos iniciais são hipóteses de domínio a revisar conforme a operação.
- A entidade não verifica matrícula duplicada: isso exige acesso ao conjunto
  de colaboradores e agora é responsabilidade de `EmployeeService`.
- `EmployeeRepository` armazena os colaboradores em uma `ArrayList` e devolve
  uma cópia imutável com `List.copyOf()` para proteger a lista interna.
- `EmployeeService` impede cadastro com ID ou matrícula já existentes.
- `Vehicle` representa um veículo operacional e normaliza a placa para letras
  maiúsculas.
- `VehicleService` impede cadastro com ID ou placa já existentes.
- `Team` compõe objetos já existentes: líder, membros e veículo.
- `TeamService` impede cadastro com ID ou nome de equipe já existentes.
- `SampleData` monta os dados iniciais em memória para a interface abrir com
  informações de exemplo.
- `FieldOpsFrame` cria a janela visual do sistema com dashboard e tabelas.
- `Main` inicia a interface gráfica. Não contém regras de negócio.
- O Maven usa `release 21` e UTF-8 para explicitar a versão alvo e a codificação.

## Exercícios de leitura

1. Explique a diferença entre a classe `Employee` e o objeto criado com `new`.
2. Explique por que `this.name` recebe o resultado da validação do parâmetro.
3. Experimente uma matrícula `"00042"` e observe que os zeros são mantidos.
4. Experimente nome em branco, ID zero ou status nulo, um por vez: o construtor
   deverá lançar uma exception e impedir a criação do objeto inválido.
5. Tente cadastrar dois colaboradores com a mesma matrícula e observe a validação
   feita por `EmployeeService`.
6. Tente cadastrar dois veículos com a mesma placa e observe a validação feita
   por `VehicleService`.
7. Tente criar uma equipe sem membros e observe a validação do construtor de
   `Team`.

As próximas etapas introduzirão formulários de cadastro na interface, ordens de
serviço, fluxo de despacho e exceptions específicas, somente após a revisão desta
etapa.
