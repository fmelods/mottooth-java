#!/bin/bash

# Script completo para deploy da aplicação Mottooth no Azure
# Autor: Equipe Mottooth
# Data: 2025

echo "============================================"
echo "    DEPLOY COMPLETO MOTTOOTH - AZURE       "
echo "============================================"

# Verificar se todos os scripts existem
SCRIPTS=("azure-vm-create.sh" "docker-install.sh" "run-app.sh" "azure-cleanup.sh")

for script in "${SCRIPTS[@]}"; do
    if [ ! -f "$script" ]; then
        echo "❌ Script $script não encontrado!"
        echo "Certifique-se de que todos os scripts estão no mesmo diretório."
        exit 1
    fi
done

echo "✅ Todos os scripts encontrados!"
echo ""

# Menu de opções
while true; do
    echo "Escolha uma opção:"
    echo "1) Criar VM no Azure"
    echo "2) Instalar Docker na VM (execute após conectar-se à VM)"
    echo "3) Executar aplicação na VM (execute após conectar-se à VM)"
    echo "4) Limpar recursos do Azure"
    echo "5) Deploy completo automatizado"
    echo "6) Sair"
    echo ""
    read -p "Digite sua opção (1-6): " option

    case $option in
        1)
            echo "🚀 Criando VM no Azure..."
            chmod +x azure-vm-create.sh
            ./azure-vm-create.sh
            echo ""
            echo "📋 Próximos passos:"
            echo "1. Conecte-se à VM via SSH"
            echo "2. Execute a opção 2 deste script na VM"
            echo ""
            ;;
        2)
            echo "🐳 Instalando Docker..."
            chmod +x docker-install.sh
            ./docker-install.sh
            echo ""
            echo "📋 Próximos passos:"
            echo "1. Saia da VM: exit"
            echo "2. Reconecte-se via SSH"
            echo "3. Execute a opção 3 deste script"
            echo ""
            ;;
        3)
            echo "🚀 Executando aplicação..."
            chmod +x run-app.sh
            ./run-app.sh
            echo ""
            echo "✅ Aplicação executando!"
            echo "Teste no navegador e depois execute a opção 4 para limpar."
            echo ""
            ;;
        4)
            echo "🧹 Limpando recursos..."
            chmod +x azure-cleanup.sh
            ./azure-cleanup.sh
            echo ""
            ;;
        5)
            echo "🤖 Deploy completo automatizado..."
            echo "Este modo executará todos os passos automaticamente."
            echo "⚠️  Certifique-se de ter o Azure CLI configurado!"
            echo ""
            read -p "Prosseguir? (s/n): " confirm

            if [[ $confirm == [sS] ]]; then
                echo "🚀 Iniciando deploy completo..."

                echo "Passo 1: Criando VM..."
                chmod +x azure-vm-create.sh
                ./azure-vm-create.sh

                if [ $? -eq 0 ]; then
                    echo "✅ VM criada! Agora conecte-se à VM e execute os passos 2 e 3."
                    echo ""
                    echo "📋 COMANDOS PARA EXECUTAR NA VM:"
                    echo "ssh azureuser@<IP_DA_VM>"
                    echo "git clone -b lolo https://github.com/fmelods/mottooth-java.git"
                    echo "cd mottooth-java/scripts"
                    echo "./docker-install.sh"
                    echo "exit && ssh azureuser@<IP_DA_VM>"
                    echo "cd mottooth-java/scripts"
                    echo "./run-app.sh"
                else
                    echo "❌ Erro na criação da VM"
                fi
            fi
            ;;
        6)
            echo "👋 Saindo..."
            exit 0
            ;;
        *)
            echo "❌ Opção inválida! Digite um número de 1 a 6."
            ;;
    esac

    echo ""
    read -p "Pressione Enter para continuar..."
    echo ""
done