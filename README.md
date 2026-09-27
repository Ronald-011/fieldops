# FieldOps — Etapa 1

Projeto de portfólio em Java 21 que simula um sistema corporativo de gestão de
operações de campo. O objetivo é desenvolver gradualmente o cadastro de
colaboradores, veículos e equipes, o acompanhamento de ordens de serviço e
indicadores operacionais.

**Status: em desenvolvimento — Etapa 1 concluída.** Atualmente, o projeto contém
a estrutura Maven, a entidade `Employee`, seus enums e uma demonstração via
terminal. As demais funcionalidades ainda não estão implementadas.

A primeira versão será desenvolvida em Java puro, sem frameworks e sem banco
de dados, com foco em compreender os fundamentos e as regras de negócio.

## Habilidades em desenvolvimento

Praticadas na etapa atual:

- Java 21 e Programação Orientada a Objetos: classes, objetos e construtores.
- Encapsulamento: atributos privados, getters e ausência de setters desnecessários.
- Enums para representar cargos, turnos e situações do colaborador.
- Validação de dados e uso de `IllegalArgumentException`.
- Organização em packages e separação entre entidade e ponto de entrada.
- Maven para configuração e compilação do projeto.
- Git e GitHub para versionamento e documentação da evolução.

Planejadas para as próximas etapas:

- Collections (`ArrayList` e `HashMap`) e repositórios em memória.
- Serviços com regras de negócio e exceptions específicas.
- Entrada de dados e navegação por menu no terminal.
- `LocalDateTime` e `Duration` para acompanhar o atendimento de ordens.
- Streams para consultas, agregações e indicadores operacionais.

## Evolução prevista

- [x] Estrutura Maven, Employee, enums e demonstração inicial.
- [ ] Cadastro e listagem de colaboradores em memória.
- [ ] Gestão de veículos e composição de equipes.
- [ ] Criação, despacho e acompanhamento de ordens de serviço.
- [ ] Dashboard operacional no terminal.

## Executar

Pré-requisitos: JDK 21 e Maven disponíveis no terminal. Confira com
`java -version` e `mvn -version`.

```shell
mvn compile
java -cp target/classes com.fieldops.application.Main
```

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
nulo. Os experimentos ficam em `target`, fora do código da aplicação.

Saída esperada:

```text
=== FIELDOPS ===
ID: 1
Name: Ana Silva
Registration: 00123
Role: FIELD_TECHNICIAN
Work shift: MORNING
Status: AVAILABLE
```

## Organização

```text
src/main/java/com/fieldops/
  application/Main.java
  model/Employee.java
  enums/EmployeeStatus.java
  enums/JobRole.java
  enums/WorkShift.java
  service/package-info.java
  repository/package-info.java
  exception/package-info.java
```

Os arquivos `package-info.java` documentam os packages reservados às próximas
etapas. Ainda não implementam serviços, armazenamento ou exceptions de negócio.

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
  de colaboradores e será responsabilidade de um serviço e repositório.
- `Main` monta um exemplo e apresenta seus dados. Não contém regras de negócio.
- O Maven usa `release 21` e UTF-8 para explicitar a versão alvo e a codificação.

## Exercícios de leitura

1. Explique a diferença entre a classe `Employee` e o objeto criado com `new`.
2. Explique por que `this.name` recebe o resultado da validação do parâmetro.
3. Experimente uma matrícula `"00042"` e observe que os zeros são mantidos.
4. Experimente nome em branco, ID zero ou status nulo, um por vez: o construtor
   deverá lançar uma exception e impedir a criação do objeto inválido.

As próximas etapas introduzirão repositórios, serviços e as outras entidades,
somente após a revisão desta etapa.
