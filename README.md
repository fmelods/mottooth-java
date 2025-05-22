# Mottooth - Sistema de Rastreamento de Motocicletas

## Integrantes do Grupo
- Arthur Ramos dos Santos – RM558798
- Felipe Melo de Sousa – RM556099
- Robert Daniel da Silva Coimbra – RM555881

## Descrição do Projeto

O Mottooth é um sistema de rastreamento de motocicletas que utiliza tecnologia Beacon para monitoramento em tempo real. O sistema permite o gerenciamento completo de motocicletas, beacons e localizações, oferecendo uma solução robusta para empresas de segurança veicular.

### Principais Funcionalidades
- Cadastro e gerenciamento de motocicletas
- Controle de beacons e suas baterias
- Rastreamento de localização em tempo real
- Gestão de clientes e modelos
- Sistema de pátios e movimentações
- Cache para otimização de performance
- Documentação automática da API com Swagger

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.3**
- **Spring Data JPA**
- **Spring Web**
- **Banco de dados H2** (em memória)
- **Bean Validation**
- **Cache (Spring Cache)**
- **Swagger/OpenAPI 3**
- **Docker**
- **Azure VM**

## Arquitetura do Sistema

O sistema utiliza uma arquitetura em camadas:
- **Controller**: Endpoints REST
- **Service**: Lógica de negócio
- **Repository**: Acesso a dados
- **Model**: Entidades JPA
- **DTO**: Transferência de dados
- **Config**: Configurações da aplicação

## Entidades Principais

### Relacionamentos
- **Moto** ↔ **Beacon** (One-to-One)
- **Moto** ↔ **Cliente** (Many-to-One)
- **Moto** ↔ **ModeloMoto** (Many-to-One)
- **Moto** ↔ **Localização** (One-to-Many)
- **Beacon** ↔ **ModeloBeacon** (Many-to-One)

## Endpoints da API

### Motocicletas
- `GET /api/motos` - Lista todas as motocicletas
- `GET /api/motos/{id}` - Busca motocicleta por ID
- `POST /api/motos` - Cadastra nova motocicleta
- `PUT /api/motos/{id}` - Atualiza motocicleta
- `DELETE /api/motos/{id}` - Remove motocicleta

### Beacons
- `GET /api/beacons` - Lista todos os beacons
- `GET /api/beacons/{id}` - Busca beacon por ID
- `POST /api/beacons` - Cadastra novo beacon
- `PUT /api/beacons/{id}` - Atualiza beacon
- `DELETE /api/beacons/{id}` - Remove beacon

### Localizações
- `GET /api/localizacoes` - Lista todas as localizações
- `GET /api/localizacoes/{id}` - Busca localização por ID
- `POST /api/localizacoes` - Cadastra nova localização
- `PUT /api/localizacoes/{id}` - Atualiza localização
- `DELETE /api/localizacoes/{id}` - Remove localização

## Instalação e Execução

### Pré-requisitos
- Azure CLI instalado
- Docker instalado
- JDK 17 (para desenvolvimento local)
- Maven (para desenvolvimento local)

### Desenvolvimento Local

1. Clone o repositório:
```bash
git clone https://github.com/fmelods/mottooth-java.git
cd mottooth-java
```

2. Execute a aplicação:
```bash
./mvnw spring-boot:run
```

3. Acesse a aplicação:
- API: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui.html
- H2 Console: http://localhost:8080/h2-console

### Deploy na Azure com Docker

#### 1. Provisionar VM no Azure

```bash
#!/bin/bash
# azure-vm-create.sh

RESOURCE_GROUP="mottooth-java-rg"
LOCATION="eastus"
VM_NAME="mottooth-java-vm"
VM_SIZE="Standard_B1s"
ADMIN_USERNAME="azureuser"
DNS_NAME_LABEL="mottooth-java-$RANDOM"
NSG_NAME="$VM_NAME-nsg"
PORT=8080

# Login no Azure
az login

# Criar grupo de recursos
az group create --name $RESOURCE_GROUP --location $LOCATION

# Criar VM Linux
az vm create \
  --resource-group $RESOURCE_GROUP \
  --name $VM_NAME \
  --image Ubuntu2204 \
  --admin-username $ADMIN_USERNAME \
  --generate-ssh-keys \
  --public-ip-address-dns-name $DNS_NAME_LABEL \
  --nsg $NSG_NAME \
  --size $VM_SIZE

# Abrir porta 8080
az vm open-port \
  --resource-group $RESOURCE_GROUP \
  --name $VM_NAME \
  --port $PORT \
  --priority 1001

echo "VM criada com sucesso!"
echo "IP público: $(az vm show -d -g $RESOURCE_GROUP -n $VM_NAME --query publicIps -o tsv)"
```

#### 2. Instalar Docker na VM

```bash
#!/bin/bash
# docker-install.sh

# Atualizar pacotes
sudo apt-get update

# Instalar dependências
sudo apt-get install -y \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg \
    lsb-release

# Adicionar chave GPG do Docker
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg

# Configurar repositório do Docker
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

# Instalar Docker Engine
sudo apt-get update
sudo apt-get install -y docker-ce docker-ce-cli containerd.io

# Adicionar usuário ao grupo docker
sudo usermod -aG docker $USER

echo "Docker instalado com sucesso!"
```

#### 3. Executar aplicação na VM

```bash
#!/bin/bash
# run-app.sh

# Clonar repositório
git clone -b lolo https://github.com/fmelods/mottooth-java.git
cd mottooth-java

# Construir imagem Docker
docker build -t mottooth-java:1.0 .

# Executar container
docker run -d -p 8080:8080 --name mottooth-app mottooth-java:1.0

# Verificar status
docker ps

echo "Aplicação executando na porta 8080"
```

#### 4. Limpar recursos

```bash
#!/bin/bash
# azure-cleanup.sh

RESOURCE_GROUP="mottooth-java-rg"

echo "ATENÇÃO: Isso irá deletar todos os recursos do grupo $RESOURCE_GROUP"
read -p "Digite 'sim' para confirmar: " CONFIRMATION

if [ "$CONFIRMATION" = "sim" ]; then
    az group delete --name $RESOURCE_GROUP --yes
    echo "Recursos removidos com sucesso!"
else
    echo "Operação cancelada."
fi
```

## Dockerfile

```dockerfile
# Etapa de build
FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /workspace/app

# Instala Maven
RUN apk add --no-cache maven

# Copia arquivos para build
COPY pom.xml .
COPY src src

# Compila o projeto
RUN mvn package -DskipTests

# Extrai dependências
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# Etapa de runtime
FROM eclipse-temurin:17-jre-alpine
VOLUME /tmp

ARG DEPENDENCY=/workspace/app/target/dependency

COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# Entrada da aplicação
ENTRYPOINT ["java","-cp","app:app/lib/*","br.com.fiap.mottooth.MottothApplication"]

# Usuário não-root para segurança
RUN addgroup -S spring && adduser -S spring -G spring
USER spring
```

## Recursos Implementados

### Dados de Teste
O sistema inclui dados de teste automaticamente carregados:
- 2 modelos de motocicletas
- 2 modelos de beacons
- 2 clientes
- 2 motocicletas
- 2 beacons associados às motos
- 2 pátios
- 3 localizações de exemplo

### Cache
Sistema de cache configurado para otimização de consultas frequentes.

### Validação
Validação completa de dados com Bean Validation.

### Tratamento de Erros
Sistema centralizado de tratamento de exceções.

### Documentação
API totalmente documentada com Swagger/OpenAPI 3.

## Acesso à Aplicação

- **API Base**: `http://IP_PUBLICO:8080`
- **Swagger UI**: `http://IP_PUBLICO:8080/swagger-ui.html`
- **H2 Console**: `http://IP_PUBLICO:8080/h2-console`
    - JDBC URL: `jdbc:h2:mem:mottooth`
    - User: `sa`
    - Password: (vazio)

## Scripts Azure CLI

Todos os scripts para automação estão disponíveis na pasta `/scripts` do repositório:
- `azure-vm-create.sh` - Criação da VM
- `docker-install.sh` - Instalação do Docker
- `run-app.sh` - Execução da aplicação
- `azure-cleanup.sh` - Limpeza de recursos

## Licença

Este projeto foi desenvolvido para fins acadêmicos na FIAP.