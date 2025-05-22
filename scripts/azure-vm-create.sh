#!/bin/bash

# Script para criar VM no Azure
# Autor: Equipe Mottooth
# Data: 2025

# Variáveis do projeto
RESOURCE_GROUP="mottooth-java-rg"
LOCATION="eastus"
VM_NAME="mottooth-java-vm"
VM_SIZE="Standard_B1s"  # VM econômica para desenvolvimento
ADMIN_USERNAME="azureuser"
DNS_NAME_LABEL="mottooth-java-$RANDOM"
NSG_NAME="$VM_NAME-nsg"
PORT=8080  # Porta da aplicação Spring Boot

echo "============================================"
echo "       CRIAÇÃO DA VM AZURE - MOTTOOTH      "
echo "============================================"

# Fazer login no Azure
echo "Fazendo login no Azure..."
az login

# Criar grupo de recursos
echo "Criando grupo de recursos $RESOURCE_GROUP em $LOCATION..."
az group create --name $RESOURCE_GROUP --location $LOCATION

if [ $? -eq 0 ]; then
    echo "✅ Grupo de recursos criado com sucesso!"
else
    echo "❌ Erro ao criar grupo de recursos"
    exit 1
fi

# Criar VM Linux
echo "Criando VM Linux $VM_NAME..."
az vm create \
  --resource-group $RESOURCE_GROUP \
  --name $VM_NAME \
  --image Ubuntu2204 \
  --admin-username $ADMIN_USERNAME \
  --generate-ssh-keys \
  --public-ip-address-dns-name $DNS_NAME_LABEL \
  --nsg $NSG_NAME \
  --size $VM_SIZE

if [ $? -eq 0 ]; then
    echo "✅ VM criada com sucesso!"
else
    echo "❌ Erro ao criar VM"
    exit 1
fi

# Abrir porta para a aplicação
echo "Abrindo porta $PORT para acesso externo..."
az vm open-port \
  --resource-group $RESOURCE_GROUP \
  --name $VM_NAME \
  --port $PORT \
  --priority 1001

if [ $? -eq 0 ]; then
    echo "✅ Porta $PORT aberta com sucesso!"
else
    echo "❌ Erro ao abrir porta"
    exit 1
fi

# Obter IP público da VM
PUBLIC_IP=$(az vm show -d -g $RESOURCE_GROUP -n $VM_NAME --query publicIps -o tsv)

echo ""
echo "============================================"
echo "           VM CRIADA COM SUCESSO!           "
echo "============================================"
echo "Nome da VM: $VM_NAME"
echo "Grupo de Recursos: $RESOURCE_GROUP"
echo "IP Público: $PUBLIC_IP"
echo "DNS: $DNS_NAME_LABEL.$LOCATION.cloudapp.azure.com"
echo "Usuário: $ADMIN_USERNAME"
echo "Porta da aplicação: $PORT"
echo ""
echo "Para conectar via SSH, use o comando:"
echo "ssh $ADMIN_USERNAME@$PUBLIC_IP"
echo ""
echo "Para acessar a aplicação após o deploy:"
echo "http://$PUBLIC_IP:$PORT"
echo "============================================"