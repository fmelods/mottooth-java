#!/bin/bash

# Script para executar a aplicação Mottooth na VM Azure
# Autor: Equipe Mottooth
# Data: 2025

# Variáveis da aplicação
APP_NAME="mottooth-java"
IMAGE_NAME="mottooth-java:1.0"
CONTAINER_NAME="mottooth-app"
APP_PORT=8080
REPO_URL="https://github.com/fmelods/mottooth-java.git"
BRANCH="lolo"

echo "============================================"
echo "      EXECUTANDO APLICAÇÃO MOTTOOTH        "
echo "============================================"

# Verificar se Docker está instalado
if ! command -v docker &> /dev/null; then
    echo "❌ Docker não está instalado!"
    echo "Execute primeiro o script docker-install.sh"
    exit 1
fi

# Verificar se Git está instalado
if ! command -v git &> /dev/null; then
    echo "🔄 Instalando Git..."
    sudo apt update
    sudo apt install git -y
fi

# Clonar ou atualizar o repositório
if [ -d "$APP_NAME" ]; then
    echo "🔄 Atualizando repositório existente..."
    cd $APP_NAME
    git pull origin $BRANCH
else
    echo "🔄 Clonando repositório da branch $BRANCH..."
    git clone -b $BRANCH $REPO_URL
    cd $APP_NAME
fi

if [ $? -eq 0 ]; then
    echo "✅ Código fonte obtido com sucesso!"
else
    echo "❌ Erro ao obter código fonte"
    exit 1
fi

# Verificar se Dockerfile existe
if [ ! -f "Dockerfile" ]; then
    echo "❌ Dockerfile não encontrado!"
    exit 1
fi

# Parar container se estiver rodando
if [ "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
    echo "🔄 Parando container existente..."
    docker stop $CONTAINER_NAME
    docker rm $CONTAINER_NAME
fi

# Construir a imagem Docker
echo "🔄 Construindo imagem Docker..."
docker build -t $IMAGE_NAME .

if [ $? -eq 0 ]; then
    echo "✅ Imagem construída com sucesso!"
else
    echo "❌ Erro ao construir imagem"
    exit 1
fi

# Executar o container
echo "🔄 Iniciando container da aplicação..."
docker run -d \
  --name $CONTAINER_NAME \
  -p $APP_PORT:$APP_PORT \
  --restart unless-stopped \
  $IMAGE_NAME

if [ $? -eq 0 ]; then
    echo "✅ Container iniciado com sucesso!"
else
    echo "❌ Erro ao iniciar container"
    exit 1
fi

# Aguardar alguns segundos para a aplicação inicializar
echo "🔄 Aguardando inicialização da aplicação..."
sleep 15

# Verificar se o container está rodando
if [ "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
    echo "✅ Container está executando!"
else
    echo "❌ Container não está executando!"
    echo "Logs do container:"
    docker logs $CONTAINER_NAME
    exit 1
fi

# Obter IP público da VM
PUBLIC_IP=$(curl -s ifconfig.me)

echo ""
echo "============================================"
echo "        APLICAÇÃO EXECUTANDO COM SUCESSO!  "
echo "============================================"
echo "Container: $CONTAINER_NAME"
echo "Imagem: $IMAGE_NAME"
echo "Porta: $APP_PORT"
echo "IP Público: $PUBLIC_IP"
echo ""
echo "URLs de acesso:"
echo "🌐 Aplicação: http://$PUBLIC_IP:$APP_PORT"
echo "📚 Swagger UI: http://$PUBLIC_IP:$APP_PORT/swagger-ui.html"
echo "🗄️  H2 Console: http://$PUBLIC_IP:$APP_PORT/h2-console"
echo ""
echo "Comandos úteis:"
echo "- Ver logs: docker logs $CONTAINER_NAME"
echo "- Parar app: docker stop $CONTAINER_NAME"
echo "- Iniciar app: docker start $CONTAINER_NAME"
echo "- Status: docker ps"
echo "============================================"

# Mostrar logs finais
echo ""
echo "📋 Últimas linhas do log da aplicação:"
docker logs --tail 10 $CONTAINER_NAME