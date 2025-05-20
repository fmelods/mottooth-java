# Mottooth API

Sistema inteligente de localização utilizando beacons Bluetooth Low Energy (BLE) para rastreamento de motocicletas em pátios.

## Descrição do Projeto

O Mottooth é uma solução desenvolvida para a empresa Mottu, que permite o rastreamento preciso de motocicletas em pátios utilizando beacons Bluetooth. O sistema registra a localização das motos, monitora o nível de bateria dos beacons e gerencia as movimentações das motocicletas.

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.2.3
- Spring Data JPA
- Spring Web
- Spring Validation
- Spring Cache
- H2 Database (desenvolvimento)
- Oracle Database (produção)
- Lombok
- Swagger/OpenAPI

## Requisitos

- Java 17 ou superior
- Maven 3.6 ou superior
- Oracle Database (opcional, para produção)

## Como Executar

1. Clone o repositório:
git clone https://github.com/seu-usuario/mottooth.git
cd mottooth

2. Compile e execute o projeto:
mvn spring-boot:run

3. Acesse a aplicação:
   - API: http://localhost:8080/api
   - Swagger UI: http://localhost:8080/swagger-ui.html
   - H2 Console: http://localhost:8080/h2-console (JDBC URL: jdbc:h2:mem:mottooth, Username: sa, Password: password)

## Configuração do Banco de Dados

### H2 (Desenvolvimento)
O projeto está configurado para usar o H2 Database em memória por padrão. Não é necessária nenhuma configuração adicional.

### Oracle (Produção)
Para usar o Oracle Database, descomente as configurações no arquivo `application.properties` e ajuste as credenciais conforme necessário:

spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe
spring.datasource.username=mottooth
spring.datasource.password=mottooth
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
spring.jpa.database-platform=org.hibernate.dialect.OracleDialect

## Estrutura do Projeto

- `src/main/java/br/com/fiap/mottooth/`
  - `controller/`: Controladores REST
  - `service/`: Camada de serviço
  - `repository/`: Interfaces de repositório JPA
  - `model/`: Entidades JPA
  - `dto/`: Objetos de transferência de dados
  - `exception/`: Tratamento de exceções
  - `config/`: Configurações do Spring

## Endpoints da API

### Motos

- `GET /api/motos`: Lista todas as motos (suporta paginação e filtros)
- `GET /api/motos/{id}`: Busca uma moto pelo ID
- `GET /api/motos/placa/{placa}`: Busca uma moto pela placa
- `POST /api/motos`: Cadastra uma nova moto
- `PUT /api/motos/{id}`: Atualiza uma moto existente
- `DELETE /api/motos/{id}`: Remove uma moto

### Beacons

- `GET /api/beacons`: Lista todos os beacons (suporta paginação e filtros)
- `GET /api/beacons/{id}`: Busca um beacon pelo ID
- `GET /api/beacons/uuid/{uuid}`: Busca um beacon pelo UUID
- `POST /api/beacons`: Cadastra um novo beacon
- `PUT /api/beacons/{id}`: Atualiza um beacon existente
- `DELETE /api/beacons/{id}`: Remove um beacon

### Localizações

- `GET /api/localizacoes`: Lista todas as localizações (suporta paginação e filtros)
- `GET /api/localizacoes/{id}`: Busca uma localização pelo ID
- `GET /api/localizacoes/moto/{motoId}/ultima`: Busca a última localização de uma moto
- `POST /api/localizacoes`: Cadastra uma nova localização
- `PUT /api/localizacoes/{id}`: Atualiza uma localização existente
- `DELETE /api/localizacoes/{id}`: Remove uma localização

## Recursos Implementados

- CRUD completo para as entidades Moto, Beacon e Localização
- Relacionamentos entre entidades
- Validação de campos com Bean Validation
- Paginação e ordenação de resultados
- Busca por parâmetros
- Cache para otimização de requisições
- Tratamento centralizado de erros
- Utilização de DTOs
- Documentação da API com Swagger/OpenAPI
- Suporte a Oracle Database para ambiente de produção

## Equipe

- Arthur Ramos dos Santos – RM558798
- Felipe Melo de Sousa – RM556099
- Robert Daniel da Silva Coimbra – RM555881
