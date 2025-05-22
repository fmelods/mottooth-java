#!/bin/bash

# Script para limpar recursos da Azure
# Autor: Equipe Mottooth
# Data: 2025

# Variáveis do projeto (devem ser as mesmas usadas na criação)
RESOURCE_GROUP="mottooth-java-rg"
VM_NAME="mottooth-java-vm"

echo "============================================"
echo "       LIMPEZA DE RECURSOS AZURE           "
echo "============================================"

# Verificar se o Azure CLI está instalado
if ! command -v az &> /dev/null; then
    echo "❌ Azure CLI não está instalado!"
    echo "Instale o Azure CLI antes de executar este script."
    exit 1
fi

# Verificar se está logado no Azure
echo "🔄 Verificando login no Azure..."
az account show > /dev/null 2>&1

if [ $? -ne 0 ]; then
    echo "❌ Você não está logado no Azure!"
    echo "Execute: az login"
    exit 1
fi

# Verificar se o grupo de recursos existe
echo "🔄 Verificando se o grupo de recursos existe..."
az group show --name $RESOURCE_GROUP > /dev/null 2>&1

if [ $? -ne 0 ]; then
    echo "⚠️  Grupo de recursos '$RESOURCE_GROUP' não encontrado!"
    echo "Pode já ter sido removido ou o nome está incorreto."
    exit 0
fi

# Listar recursos no grupo
echo "📋 Recursos encontrados no grupo '$RESOURCE_GROUP':"
az resource list --resource-group $RESOURCE_GROUP --output table

echo ""
echo "⚠️  ATENÇÃO: OPERAÇÃO IRREVERSÍVEL!"
echo "============================================"
echo "Isso irá DELETAR PERMANENTEMENTE:"
echo "- Grupo de recursos: $RESOURCE_GROUP"
echo "- VM: $VM_NAME"
echo "- Todos os discos, interfaces de rede, IPs públicos"
echo "- Todos os recursos associados"
echo "- TODOS OS DADOS serão perdidos!"
echo "============================================"
echo ""

# Confirmação de segurança
read -p "Digite 'DELETAR' (em maiúsculas) para confirmar a remoção: " CONFIRMATION

if [ "$CONFIRMATION" != "DELETAR" ]; then
    echo "❌ Operação cancelada pelo usuário."
    echo "Para confirmar, digite exatamente: DELETAR"
    exit 0
fi

echo ""
read -p "Tem certeza absoluta? Digite 'sim' para prosseguir: " FINAL_CONFIRMATION

if [ "$FINAL_CONFIRMATION" != "sim" ]; then
    echo "❌ Operação cancelada pelo usuário."
    exit 0
fi

# Executar a remoção
echo ""
echo "🔄 Iniciando remoção do grupo de recursos..."
echo "⏳ Isso pode levar alguns minutos..."

az group delete --name $RESOURCE_GROUP --yes --no-wait

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Comando de remoção executado com sucesso!"
    echo ""
    echo "============================================"
    echo "         RECURSOS SENDO REMOVIDOS          "
    echo "============================================"
    echo "⏳ A remoção está em andamento em background."
    echo "📝 Use este comando para verificar o status:"
    echo "   az group show --name $RESOURCE_GROUP"
    echo ""
    echo "📸 IMPORTANTE PARA A ENTREGA:"
    echo "   Tire um PRINT desta tela como evidência"
    echo "   da remoção dos recursos para o relatório!"
    echo ""
    echo "🕐 Status atual: REMOVENDO..."
    echo "📅 Data/Hora: $(date)"
    echo "👤 Usuário: $(az account show --query user.name -o tsv)"
    echo "🏷️  Grupo removido: $RESOURCE_GROUP"
    echo "============================================"
else
    echo "❌ Erro ao executar comando de remoção!"
    echo "Verifique suas permissões e tente novamente."
    exit 1
fi

# Aguardar alguns segundos e verificar
echo ""
echo "🔄 Verificando status da remoção..."
sleep 5

az group show --name $RESOURCE_GROUP > /dev/null 2>&1

if [ $? -ne 0 ]; then
    echo "✅ Grupo de recursos removido completamente!"
    echo ""
    echo "============================================"
    echo "         LIMPEZA CONCLUÍDA COM SUCESSO!     "
    echo "============================================"
    echo "✅ Todos os recursos foram removidos"
    echo "💰 Não haverá mais cobrança destes recursos"
    echo "📝 Evidência da remoção registrada"
    echo "============================================"
else
    echo "⏳ Remoção ainda em andamento..."
    echo "Execute o comando abaixo para verificar:"
    echo "az group show --name $RESOURCE_GROUP"
fi