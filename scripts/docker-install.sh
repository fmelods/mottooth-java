#!/bin/bash

# Script para instalar Docker na VM Ubuntu
# Autor: Equipe Mottooth
# Data: 2025

echo "============================================"
echo "       INSTALAÇÃO DO DOCKER - MOTTOOTH     "
echo "============================================"

# Atualizar os pacotes do sistema
echo "🔄 Atualizando lista de pacotes..."
sudo apt-get update

if [ $? -eq 0 ]; then
    echo "✅ Pacotes atualizados com sucesso!"
else
    echo "❌ Erro ao atualizar pacotes"
    exit 1
fi

# Instalar dependências necessárias
echo "🔄 Instalando dependências..."
sudo apt-get install -y \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg \
    lsb-release

if [ $? -eq 0 ]; then
    echo "✅ Dependências instaladas com sucesso!"
else
    echo "❌ Erro ao instalar dependências"
    exit 1
fi

# Adicionar a chave GPG oficial do Docker
echo "🔄 Adicionando chave GPG do Docker..."
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg

if [ $? -eq 0 ]; then
    echo "✅ Chave GPG adicionada com sucesso!"
else
    echo "❌ Erro ao adicionar chave GPG"
    exit 1
fi

# Configurar o repositório stable do Docker
echo "🔄 Configurando repositório do Docker..."
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

if [ $? -eq 0 ]; then
    echo "✅ Repositório configurado com sucesso!"
else
    echo "❌ Erro ao configurar repositório"
    exit 1
fi

# Atualizar os pacotes novamente
echo "🔄 Atualizando pacotes com o novo repositório..."
sudo apt-get update

# Instalar Docker Engine
echo "🔄 Instalando Docker Engine..."
sudo apt-get install -y docker-ce docker-ce-cli containerd.io

if [ $? -eq 0 ]; then
    echo "✅ Docker Engine instalado com sucesso!"
else
    echo "❌ Erro ao instalar Docker Engine"
    exit 1
fi

# Adicionar usuário atual ao grupo docker para executar sem sudo
echo "🔄 Configurando permissões do Docker..."
sudo usermod -aG docker $USER

if [ $? -eq 0 ]; then
    echo "✅ Permissões configuradas com sucesso!"
else
    echo "❌ Erro ao configurar permissões"
    exit 1
fi

# Iniciar e habilitar o serviço Docker
echo "🔄 Iniciando serviço Docker..."
sudo systemctl start docker
sudo systemctl enable docker

# Verificar a instalação
echo "🔄 Verificando a instalação do Docker..."
DOCKER_VERSION=$(sudo docker --version)

if [ $? -eq 0 ]; then
    echo "✅ Docker verificado com sucesso!"
    echo "Versão instalada: $DOCKER_VERSION"
else
    echo "❌ Erro ao verificar Docker"
    exit 1
fi

echo ""
echo "============================================"
echo "        DOCKER INSTALADO COM SUCESSO!      "
echo "============================================"
echo "Versão: $DOCKER_VERSION"
echo ""
echo "⚠️  IMPORTANTE:"
echo "Para usar o Docker sem sudo, você precisa:"
echo "1. Sair da sessão SSH atual: exit"
echo "2. Conectar-se novamente via SSH"
echo ""
echo "Comandos úteis:"
echo "- Verificar status: docker --version"
echo "- Listar containers: docker ps"
echo "- Listar imagens: docker images"
echo "============================================"