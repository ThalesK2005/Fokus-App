# Fokus - Gerenciador de Tarefas (Projeto M3)

Aplicativo Android nativo desenvolvido como Projeto Final da disciplina de Programação para Dispositivos Móveis. O objetivo é oferecer uma solução simples e eficiente para organização pessoal, utilizando as tecnologias mais modernas de desenvolvimento Android.

## 📱 Sobre o Projeto

O **Fokus** permite que usuários gerenciem suas tarefas diárias, organizem eventos em um calendário e sincronizem seus dados na nuvem, garantindo que cada usuário tenha acesso privado às suas informações.

O projeto foi construído seguindo estritamente as diretrizes de arquitetura do Google e os requisitos da avaliação M3.

## 🚀 Funcionalidades

* **Autenticação:** Login e Cadastro de usuários via Firebase Auth.
* **Recuperação de Senha:** Envio de e-mail para redefinição de senha.
* **Gestão de Tarefas:** Criar, Editar e Excluir tarefas.
* **Categorização:** Classificação de tarefas por cor (Trabalho, Pessoal, Reuniões, Planos).
* **Calendário:** Visualização mensal de eventos e tarefas.
* **Privacidade:** Dados segregados por usuário (cada um vê apenas suas tarefas).
* **Persistência Local:** Funciona offline e sincroniza via Room Database.

## 🛠 Tecnologias e Arquitetura

O projeto segue a arquitetura **MVVM (Model-View-ViewModel)** e o padrão **Single-Activity**.

* **Linguagem:** Kotlin
* **Interface (UI):** Jetpack Compose (Material Design 3)
* **Navegação:** Jetpack Navigation Compose
* **Banco de Dados Local:** Room (SQLite)
* **Backend / Autenticação:** Firebase Authentication & Firestore
* **Injeção de Dependência:** Manual (via ViewModel Factory/Instanciação direta)
* **Assincronismo:** Kotlin Coroutines & Flow

## 📂 Estrutura do Projeto

A organização de pacotes favorece a separação de responsabilidades:

* `ui/`: Contém as telas (Screens), componentes visuais e temas.
* `viewmodel/`: Gerenciamento de estado e lógica de negócios (comunicação entre UI e Dados).
* `data/`: Configuração do Room Database e DAOs.
* `model/`: Classes de dados (Entities).

## 🔧 Como rodar o projeto

1.  Clone este repositório.
2.  Abra o projeto no **Android Studio**.
3.  Aguarde a sincronização do Gradle.
4.  Execute o app em um Emulador ou Dispositivo Físico (Android 8.0+).

> **Nota:** O arquivo `google-services.json` está incluído para fins de avaliação acadêmica, permitindo a conexão com o projeto Firebase de teste.

---
**Desenvolvido por:** Thales Kuroishi
**Disciplina:** Programação para Dispositivos Móveis - Univali