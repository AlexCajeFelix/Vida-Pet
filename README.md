# 🐾 Sistema de Gestão PetVida

## 📌 Introdução

### 🎯 Objetivo
O sistema tem como objetivo fornecer uma plataforma completa de gestão para petshops/clinicas, permitindo:
- Agendamento de serviços 🗓️
- Venda de produtos 🛍️
- Comunicação entre donos e veterinários 💬
- Acompanhamento do histórico dos pets 🐶📋
- Auditoria de ações e gerenciamento de logs 🔍

### 👥 Público-Alvo
- 🐾 Donos de Pets
- 🩺 Veterinários
- 🏢 Funcionários do Petshop
- 👨‍💼 Administradores

### 🏗️ Visão Geral do Sistema
O sistema permitirá:
✅ Gerenciamento de pets 🐶🐱
✅ Agendamento de serviços 🗓️
✅ Venda de produtos e serviços adicionais 🛒
✅ Comunicação via chat entre donos e veterinários 📩
✅ Auditoria de ações e logs 📊
✅ Registro completo do histórico dos pets 📖

---

## ⚙️ Funcionalidades Principais

### 📝 Cadastro de Usuários
- **👤 Dono do Pet**: Cadastro de informações pessoais, pets e histórico de serviços.
- **🩺 Veterinário**: Cadastro com especialidades e agenda.
- **💼 Funcionário**: Acesso a registros e agendamentos.
- **🔑 Administrador**: Controle total sobre os dados do sistema.

### 🐶 Gerenciamento de Pets
- Cadastro de pets com dados detalhados (nome, idade, raça, etc.).
- Histórico de consultas e serviços realizados.
- Acompanhamento de saúde e comportamento.

### 📅 Agenda e Agendamento de Serviços
- Agenda individual para cada veterinário.
- Agendamento de serviços como consultas, vacinas, banhos e tosas.
- Integração com o marketplace para incluir produtos/serviços no agendamento.

### 🏥 Histórico
- Registro detalhado de serviços realizados.
- Prescrições médicas e recomendações dos veterinários.
- Histórico de compras de produtos do marketplace.

### 🔍 Auditoria e Logs
- Registro de todas as ações realizadas no sistema.
- Controle de acesso e ações administrativas.
- Auditoria de alterações e exclusões no sistema.

### 💬 Chat de Comunicação
- Mensagens entre donos de pets e veterinários.
- Notificações sobre agendamentos, atualizações e cuidados do pet.

### 🛒 Marketplace de Produtos e Serviços
- Venda de produtos (ração, brinquedos, medicamentos, etc.).
- Compra de serviços adicionais (pacotes de banho, tosa, vacinação).
- Integração com agendamentos.

---

## 📌 Requisitos

### ✅ Requisitos Funcionais
- Cadastro de usuários (donos, veterinários, funcionários).
- Agendamento de serviços.
- Marketplace para compra de produtos e serviços.
- Notificações e lembretes para agendamentos.
- Chat em tempo real entre donos e veterinários.

### 🔒 Requisitos Não Funcionais
- **Segurança**: Proteção de dados sensíveis.
- **Escalabilidade**: Suporte para um grande número de usuários.
- **Desempenho**: Sistema rápido e responsivo.
- **Backup**: Garantia de recuperação de dados.

---

## 🛠️ Tecnologias Utilizadas
- **Frontend**: React Native 📱
- **Backend**: Spring Boot (Java) ☕
- **Banco de Dados**: MySQL 🗄️
- **Comunicação**: WebSockets para chat em tempo real 🔄
- **Autenticação**: JWT 🔑
- **Armazenamento de Arquivos**: Amazon S3 📂

---

## 🔄 Fluxo de Trabalho
1️⃣ **Cadastro de Dono de Pet**: Criação de conta e adição de informações dos pets.
2️⃣ **Agendamento de Serviços**: Escolha de veterinário, seleção de serviço e agendamento.
3️⃣ **Compra de Produtos/Serviços**: Inclusão de itens no marketplace durante o agendamento.
4️⃣ **Execução do Serviço**: Veterinário realiza e registra a consulta.
5️⃣ **Chat e Acompanhamento**: Comunicação entre donos e veterinários.

---
## 🎭 Casos de Uso

### 🏥 Caso de Uso 1: Agendamento de Consulta
- Dono escolhe veterinário disponível e serviço.
- O sistema envia confirmação por e-mail/SMS 📩.

### 🛒 Caso de Uso 2: Compra de Produtos no Marketplace
- Dono adiciona produtos ao carrinho e finaliza a compra 💰.
- Pagamento processado online 💳.

### 💬 Caso de Uso 3: Comunicação via Chat
- Dono inicia conversa com o veterinário para tirar dúvidas antes da consulta 🗨️.

---