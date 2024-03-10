# APP-FEATURE-TOGGLE-JAVA

<p>
A aplicação foi desenvolvida com o propósito de aprofundar o 
estudo do conceito de feature toggle, 
explorando diversas abordagens para sua implementação. 
Este projeto demonstra a aplicação de toggles por meio da 
configuração no arquivo application.yaml, integração com banco de 
dados e a utilização da ferramenta Unleash.
</p>

### :hammer: Pré-requisitos

- IDE de sua preferência.
- JDK 17.
- Docker e Docker Compose

### 🛠 Detalhes Tecnicos

- Java 17
- Arquitetura baseada em Clean Arch
- Swagger
- MongoDB e PostgreSQL
- Docker e Docker Compose
- Unleash
- Inserção de dados de forma automatica
- Spring Cloud Config

## Documentação da API

- ### Consulta de registros de manutenção de um veículo:

```
GET /v1/maintenance/car/{plate}
```

A requisição precisa do seguinte PathVariable:
| PathVariable | Tipo | Descrição |
| :---------- | :--------- | :---------------------------------- |
| `plate` | `String` | **Obrigatório**. Placa do veículo |

CURL de exemplo:

```
curl -X GET "http://localhost:8080/v1/maintenance/car/ABC-1234" -H "accept: */*" -H "Content-Type: application/json"
```

## Feature Toggle

Feature Toggle, também conhecido como Feature Flags, é uma prática que oferece a capacidade de ativar ou desativar funcionalidades, permitindo realizar rollbacks de maneira mais eficiente, muitas vezes sem a necessidade de redesenhar um deployment ou modificar o código-fonte. Sua implementação proporciona um controle refinado sobre as funcionalidades, possibilitando a liberação gradual de acesso a elas.

Por exemplo, suponha que nossa aplicação dependa de uma chamada REST para uma API crucial para seu funcionamento. Seja por motivos de desempenho, confiabilidade de dados ou até mesmo custo, podemos optar por migrar essa chamada para uma nova API. Nesse cenário, a utilização de um Feature Toggle nos permite alternar entre as APIs, facilitando a coleta de métricas para validar a nova chamada. Além disso, essa abordagem oferece uma solução ágil para um possível rollback, caso a nova API não atenda às expectativas por algum motivo.

## Inserindo dados de forma automatica

Em nossa aplicação, empregaremos a técnica de feature toggle para alternar o acesso entre dois bancos de dados: PostgreSQL e MongoDB. Com o objetivo de simplificar os testes e manter consistência nos dados sem a necessidade de inserções manuais em ambas as bases, desenvolvemos uma classe dedicada. Esta classe realiza a inserção automática de dados a partir de um arquivo JSON.

A classe é devidamente anotada com @Configuration, indicando que será executada durante o início do Spring Boot. O método em questão é anotado com @PostConstructor, assegurando que a execução seja inicializada de maneira automática. Esse conjunto de anotações proporciona uma abordagem eficaz para garantir a integridade e a sincronização dos dados entre os dois bancos de dados no contexto do feature toggle.

Método encarregado de recuperar o JSON e convertê-lo em um objeto:
```
    private List<Maintenance> getMaintenances() {
        try {
            final var path = Paths.get(Main.class.getClassLoader().getResource(json).toURI());
            final var jsonContent = Files.readString(path);

            return objectMapper.readValue(jsonContent, new TypeReference<>() {
            });
        } catch (final URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
```
Inserindo dados em ambas as bases:

```
    @PostConstruct
    public void init() {
        carMaintenanceMongoJPARepository.deleteAll();
        carMaintenancesPostgresJPARepository.deleteAll();

        getMaintenances()
                .forEach(maintenance -> {

                    final var carMaintenanceMongo = CarMaintenanceDocument.builder()
                            .code(maintenance.getCode())
                            .description(maintenance.getDescription())
                            .carPlate(maintenance.getCarPlate())
                            .dataOrigin(MONGO_DB)
                            .date(maintenance.getDate())
                            .build();

                    carMaintenanceMongoJPARepository.save(carMaintenanceMongo);

                    final var carMaintenancePostgres = CarMaintenanceEntity.builder()
                            .code(maintenance.getCode())
                            .description(maintenance.getDescription())
                            .carPlate(maintenance.getCarPlate())
                            .dataOrigin(POSTGRES_SQL)
                            .date(maintenance.getDate())
                            .build();

                    carMaintenancesPostgresJPARepository.save(carMaintenancePostgres);
                });
    }
```

## Primeira Implementação.

Branch da implementação: https://github.com/leonardodantas/app-feature-toggle-java

A implementação inicial é bastante simples, fazendo uso da anotação @Value para extrair do arquivo application.yaml o valor correspondente à variável. Dessa forma, determinamos se a busca será realizada no PostgreSQL ou no MongoDB.

```
    @Value("${database.mongo.active:false}")
    private boolean databaseMongo;

    public Collection<CarMaintenance> findByCarPlate(final String carPlate) {
        if (databaseMongo) {
            return carMaintenanceMongoRepository.findByCarPlate(carPlate);
        }
        return carMaintenancePostgresRepository.findByCarPlate(carPlate);
    }
```

Optamos por definir um valor padrão para a variável, de modo que, se não estiver presente no arquivo de configuração, assumirá o valor de false.


A implementação inicial tem seus pontos positivos e negativos a serem considerados.

Em relação aos pontos positivos, destaca-se a simplicidade da abordagem. A forma direta e fácil de entender facilita a manutenção do código. Além disso, a facilidade de manuseio é evidente, visto que o valor padrão predefinido evita falhas caso a variável não esteja configurada no arquivo, mantendo um comportamento consistente.

Contudo, há pontos negativos a serem ponderados. A rigidez na atualização é uma consideração importante, pois a falta de flexibilidade pode exigir um novo deployment da aplicação para alternar entre os bancos de dados. Além disso, a prática de alternância entre releases, embora seja prática para situações com duas releases distintas, pode se tornar menos eficiente em cenários que demandam alterações frequentes. Isso ocorre devido à necessidade de ações manuais para realizar a mudança do toggle.

## Segunda implementação.

Branch da implementação: https://github.com/leonardodantas/app-feature-toggle-java/tree/feature/utilizando-banco-para-config

Na segunda abordagem, optamos por utilizar uma fonte externa para armazenar e recuperar as configurações, sendo duas opções interessantes uma API externa ou um banco de dados. Neste caso específico, escolhemos o MongoDB para gerenciar e recuperar nossas configurações.

A lógica de implementação foi estruturada da seguinte maneira:

```
    public Collection<CarMaintenance> findByCarPlate(final String carPlate) {

        final var config = findConfig.findByProperty(DATABASE_MONGO_ACTIVE_PROPERTY)
                .orElseThrow();

        if (config.value()) {
            return carMaintenanceMongoRepository.findByCarPlate(carPlate);
        }
        return carMaintenancePostgresRepository.findByCarPlate(carPlate);
    }
```

Agora, introduzimos uma constante DATABASE_MONGO_ACTIVE_PROPERTY cujo valor é armazenado em um banco de dados. Nessa implementação específica, temos acesso ao Swagger por meio do endereço http://localhost:8080/swagger-ui/index.html, que permite visualizar os endpoints para ativar ou desativar a propriedade DATABASE_MONGO_ACTIVE_PROPERTY.

Em termos positivos, a flexibilidade dinâmica é notável, pois a escolha de uma fonte externa possibilita alterações nas configurações em tempo real, sem a necessidade de um novo deployment. Além disso, o gerenciamento centralizado das configurações é facilitado com o acesso ao Swagger, permitindo visualizar e modificar os endpoints de ativação ou desativação.

Entretanto, é importante considerar a complexidade adicional introduzida por essa abordagem, principalmente se não for estritamente necessária para os requisitos do projeto. Além disso, a dependência do MongoDB ou de uma API externa pode apresentar potenciais pontos únicos de falha ou requerer considerações adicionais em termos de disponibilidade e desempenho que 
podem ser resolvidos com a utilização de cache.

## Terceira implementação.

Branch da implementação: https://github.com/leonardodantas/app-feature-toggle-java/tree/feature/utilizando-unleash

Na terceira abordagem, incorporamos a ferramenta Unleash, um gerenciador de feature toggle que se destaca por sua interface amigável e simples. Essa ferramenta permite a ativação e desativação de toggles de maneira fácil e prática. Suas informações são armazenadas em um banco de dados PostgreSQL, e uma documentação abrangente está disponível em https://docs.getunleash.io/. Além disso, a inicialização da ferramenta pode ser feita por meio do Docker Compose.

A implementação com o Unleash é apresentada no seguinte trecho de código:
```
    public Collection<CarMaintenance> findByCarPlate(final String carPlate) {
        if (unleash.isEnabled(DATABASE_MONGO_ACTIVE_TOGGLE)) {
            return carMaintenanceMongoRepository.findByCarPlate(carPlate);
        }
        return carMaintenancePostgresRepository.findByCarPlate(carPlate);
    }
```
Nesta implementação, introduzimos a dependência do Unleash, que oferece o método isEnabled. Ao passar o nome da configuração, esse método retorna true ou false de acordo com o gerenciador. Caso a propriedade não exista, o valor false é retornado por padrão.

Para configurar o uso do Unleash no projeto, é necessário adicionar a dependência ao arquivo de configuração do Maven:

```
		<dependency>
			<groupId>io.getunleash</groupId>
			<artifactId>unleash-client-java</artifactId>
			<version>9.2.0</version>
		</dependency>
```
Além disso, é necessário criar um bean para indicar ao Spring como desejamos utilizar o Unleash:
```
    @Bean
    @Primary
    public Unleash unleash() {
        UnleashConfig config = UnleashConfig.builder()
                .appName(unleashProperties.getAppName())
                .instanceId(unleashProperties.getInstanceId())
                .unleashAPI(unleashProperties.getUnleashAPI())
                .apiKey(unleashProperties.getApiKey())
                .synchronousFetchOnInitialisation(unleashProperties.isSynchronousFetchOnInitialisation())
                .build();

        return new DefaultUnleash(config);
    }
```
O Unleash apresenta uma interface amigável e flexibilidade dinâmica, permitindo ativar/desativar toggles em tempo real e armazenar configurações de forma centralizada no PostgreSQL. Sua documentação extensa e integração com Docker simplificam o processo. No entanto, introduz uma dependência externa, demanda curva de aprendizado, pode ser excessivo para projetos menores e requer configuração adicional, o que deve ser considerado com base nas necessidades do projeto e na capacidade da equipe.

## Spring Cloud Config.

Utilizamos o Spring Cloud Config para centralizarmos nossas configurações
no seguinte repositorio do github https://github.com/leonardodantas/properties.

Para acessar as configurações com sucesso precisamos iniciar em nossa maquina a
seguinte aplicação https://github.com/leonardodantas/app-server-config-java.

## Tecnologias

<div style="display: inline_block">
  <img align="center" alt="java" src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white" />
  <img align="center" alt="spring" src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white" />
  <img align="center" alt="swagger" src="https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white" />
  <img align="center" alt="swagger" src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white" />
  <img align="center" alt="swagger" src="https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white" />
  <img align="center" alt="swagger" src="https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white" />
</div>

### :sunglasses: Autor

Criado por Leonardo Rodrigues Dantas.

[![Linkedin Badge](https://img.shields.io/badge/-Leonardo-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/leonardo-rodrigues-dantas/)](https://www.linkedin.com/in/leonardo-rodrigues-dantas/)
[![Gmail Badge](https://img.shields.io/badge/-leonardordnt1317@gmail.com-c14438?style=flat-square&logo=Gmail&logoColor=white&link=mailto:leonardordnt1317@gmail.com)](mailto:leonardordnt1317@gmail.com)

## Licença

Este projeto esta sobe a licença MIT.
