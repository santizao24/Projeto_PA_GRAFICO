<!--
NOTA: Avaliação Periódica 3 (componente gráfica). Nomenclatura de entrega:
LEI_PA_2026_AP3_Nome-Apelido_2026-06-xx.rar
As Figuras 3-1 (Modelo ER) e a imagem da estrutura de ficheiros devem ser inseridas como no relatório anterior;
toda a imagem tem de ter legenda e um texto a descrever/explicar a imagem (exigência do modelo).
-->

# Relatório de Projeto
## Programação Aplicada
### Avaliação Periódica 3

**Escola Superior de Tecnologia e Gestão — Politécnico de Coimbra**

**Autor(es):**
André Santiago
Hugo Nobre

**Data:** junho, 2026

---

## Resumo

O presente relatório descreve o desenvolvimento de um **Sistema de Gestão de Oficina**, uma aplicação desenvolvida em Java[1] com **interface gráfica** construída com a biblioteca **Java Swing**[8] e persistência de dados numa base de dados MySQL[2]. Esta versão constitui a evolução do sistema, anteriormente disponibilizado apenas em modo de consola, dotando-o de uma interface gráfica para interação com o utilizador. O sistema permite a gestão de processos de reparação de equipamentos, englobando o registo e autenticação de utilizadores (Gestores, Funcionários e Clientes), a gestão do ciclo de vida de reparações, a foto de perfil dos utilizadores, o envio de email de confirmação no registo, e um sistema de notificações e auditoria. A arquitetura adotada segue o padrão MVC (Model-View-Controller), complementado por uma camada de acesso a dados baseada no padrão DAO (Data Access Object), assegurando uma separação clara de responsabilidades. A interface gráfica recorre a contentores e gestores de posicionamento (JFrame, JPanel, BorderLayout, GridLayout, FlowLayout), componentes Swing (JTable, JList, JComboBox, JTextField), caixas de diálogo (JOptionPane, JFileChooser), menus, ajuda contextual (tooltips), barras de deslocamento (JScrollPane), impressão de extratos e gestão de eventos através de listeners.

## Palavras-chave

Gestão de Oficina, Java, Java Swing, GUI, MVC, DAO, MySQL, Reparações, JDBC, Programação Orientada a Objetos

---

## Índice

- Resumo
- Lista de Figuras
- Lista de Tabelas
- Lista de Acrónimos
- 1. Introdução
- 2. Objetivos e Metodologias
  - 2.1. Ferramentas e Tecnologias
  - 2.2. Planeamento
- 3. Trabalho Desenvolvido
  - 3.1. Requisitos Implementados
  - 3.2. Classes e Packages
  - 3.3. Algoritmos
  - 3.4. Estruturas de Dados
  - 3.5. Armazenamento de Dados
  - 3.6. Procedimentos de Teste
- 4. Conclusões
  - 4.1. Forças
  - 4.2. Limitações
  - 4.3. Trabalho Futuro
- 5. Referências
  - 5.1. Lista de Referências
- 6. Anexos

---

## Lista de Figuras

- Figura 3-1 - Modelo ER
- Figura 3-2 - Estrutura de Ficheiros do Projeto

## Lista de Tabelas

- Tabela 2-1 - Ferramentas Utilizadas
- Tabela 2-2 - Planeamento Models
- Tabela 2-3 - Planeamento Enums
- Tabela 2-4 - Planeamento Controlador
- Tabela 2-5 - Planeamento DAO
- Tabela 2-6 - Planeamento View
- Tabela 2-7 - Planeamento Util
- Tabela 2-8 - Planeamento Geral
- Tabela 3-1 - Estado dos Requisitos
- Tabela 3-2 - Package model
- Tabela 3-3 - Package enums
- Tabela 3-4 - Package controlador
- Tabela 3-5 - Package repositorio
- Tabela 3-6 - Package view
- Tabela 3-7 - Package util
- Tabela 3-8 - Tabelas da Base de Dados
- Tabela 3-9 - Ficheiros Auxiliares

## Lista de Acrónimos

| Acrónimo | Significado |
|---|---|
| MVC | Model-View-Controller |
| DAO | Data Access Object |
| CRUD | Create, Read, Update, Delete |
| JDBC | Java Database Connectivity |
| GUI | Graphical User Interface |
| AWT | Abstract Window Toolkit |
| L&F | Look and Feel |
| ER | Entidade-Relacionamento |
| POO | Programação Orientada a Objetos |
| SQL | Structured Query Language |
| SKU | Stock Keeping Unit |

---

## 1. Introdução

Nas oficinas de reparação de equipamentos, a gestão eficaz de todas as reparações é um desafio. A falta de um sistema informático conduz frequentemente a perdas de informação, atrasos na comunicação entre funcionários e clientes, e dificuldades no acompanhamento dos processos. Este projeto procura automatizar e centralizar todas estas operações. O sistema foi desenvolvido no âmbito da unidade curricular de Programação Aplicada, com o objetivo de aplicar os conceitos de Programação Orientada a Objetos (POO), o padrão MVC (Model-View-Controller), o acesso a base de dados através de JDBC[3] e o desenvolvimento de **interfaces gráficos com Java Swing**[8]. A aplicação permite três perfis de utilização – Gestor, Funcionário e Cliente – cada um com permissões e funcionalidades diferentes.

Esta entrega corresponde à componente gráfica do projeto (Avaliação Periódica 3) e parte da aplicação previamente desenvolvida em modo de consola, completando-a e corrigindo-a (R1). Toda a interação passa a ser feita através de janelas, painéis, tabelas, formulários, menus e caixas de diálogo, em substituição da introdução de comandos por linha de texto (R8).

O projeto desenvolvido apresenta uma solução sobre o ciclo de vida de uma reparação, desde a submissão do pedido pelo cliente até ao arquivo pelo gestor, passando pela atribuição a funcionários e conclusão da reparação. O sistema integra também notificações, registo de logs e gestão de contas com diferentes estados.

---

## 2. Objetivos e Metodologias

### Objetivos

O objetivo principal deste projeto consiste no desenvolvimento de uma aplicação **gráfica** em Java que gere as operações de uma oficina de reparação de equipamentos. Tendo por base os requisitos do enunciado, os objetivos específicos incluem:

- Completar e corrigir a aplicação iniciada no projeto anterior, dotando-a de interface gráfica (R1, R8);
- Implementar um sistema de autenticação e autorização com três perfis distintos (Gestor, Funcionário, Cliente);
- Permitir a definição/alteração de foto de perfil, com uma imagem genérica por defeito (R2);
- Enviar um email de confirmação no momento do registo (R3);
- Incluir um campo de observações nos formulários de inserção de dados (R4);
- Alertar o utilizador para a existência de notificações após a autenticação (R5);
- Permitir a impressão de um extrato de um processo de reparação (R6);
- Apresentar as listagens em componentes adequados (JTable/JList) com barras de deslocamento (R7, R9);
- Disponibilizar ajuda contextual (tooltips) nos formulários (R10);
- Gerir o ciclo de vida completo de uma reparação (criado, aceite, decorrer, finalizado, rejeitado e arquivado);
- Garantir persistência de dados através de uma base de dados relacional e validação robusta dos dados de entrada;
- Manter o padrão MVC e assegurar um código harmonizado e consistente (R14).

### Metodologia

O desenvolvimento seguiu uma abordagem iterativa, onde cada funcionalidade foi implementada de forma incremental, testada e inserida no sistema antes de avançar para a seguinte. Esta abordagem permitiu-nos identificar e corrigir erros, garantindo a estabilidade da aplicação. Tendo como base a versão de consola já existente, manteve-se a lógica de negócio (controladores e DAOs) e substituiu-se a camada de apresentação por uma interface gráfica. Antes do desenvolvimento foram acordadas normas de codificação comuns (R14), e antes da submissão todo o código foi revisto e harmonizado.

### 2.1. Ferramentas e Tecnologias

**Tabela 2-1 - Ferramentas Utilizadas**

| Ferramenta/Tecnologia | Finalidade |
|---|---|
| Java[1] | Linguagem de programação |
| Java Swing[8] | Biblioteca para construção da interface gráfica |
| FlatLaf[9] | Template de Look & Feel (tema) da interface |
| JavaMail[10] | Envio de emails de confirmação de registo |
| Power Designer[4] | Desenho do Modelo ER e arquitetura da Base de Dados |
| HeidiSQL[5] | Administração da base de dados, execução de scripts SQL e monitorização de dados reais |
| MySQL[2] | Sistema de gestão de base de dados |
| Visual Studio Code[6] | IDE escolhido para desenvolvimento do projeto |
| Git[7] | Sistema de controlo de versões |
| JavaDoc | Geração automática de documentação técnica a partir dos comentários feitos no código |

### 2.2. Planeamento

**Tabela 2-2 - Planeamento Models**

| Models | Tarefa | Santiago | Hugo |
|---|---|:---:|:---:|
| | Utilizador.java | X | |
| | Cliente.java | X | |
| | Reparacao.java | X | |
| | Funcionario.java | | X |
| | Equipamento.java | | X |
| | Notificacao.java | | X |
| | Log.java | | X |

**Tabela 2-3 - Planeamento Enums**

| Enums | Tarefa | Santiago | Hugo |
|---|---|:---:|:---:|
| | EstadoNotificacao, CategoriaNotificacao | X | |
| | EstadoReparacao, TipoUtilizador, EstadoUtilizador | | X |

**Tabela 2-4 - Planeamento Controlador**

| Controlador | Tarefa | Santiago | Hugo |
|---|---|:---:|:---:|
| | ControladorUtilizador — autenticação e registo (login, registarGestor, registarFuncionario, existeGestor) | X | |
| | ControladorUtilizador — gestão de contas e perfis (mudarEstadoConta, atualizarPerfil, apagarDados) | | X |
| | ControladorUtilizador — listagens e pesquisas (listarOrdenados, pesquisar, obterPorId, logs) | X | |
| | ControladorReparacao — criação e gestão de estados (registarPedido, gerirEstado, rejeitarPorFuncionario) | X | |
| | ControladorReparacao — conclusão e arquivamento (concluirReparacao, arquivar, verificarAtrasos) | | X |
| | ControladorReparacao — listagens e pesquisas (listarOrdenadas, pesquisar, porCliente, porFuncionario, porData) | | X |
| | ControladorEquipamento | X | |
| | ControladorNotificacao | | X |
| | ControladorLog | | X |

**Tabela 2-5 - Planeamento DAO**

| DAO | Tarefa | Santiago | Hugo |
|---|---|:---:|:---:|
| | UtilizadorDAO — inserção, login e validação (inserir, validarLogin, existeGestor) | X | |
| | UtilizadorDAO — gestão de estados e perfis (atualizarEstado, atualizar, remover) | | X |
| | UtilizadorDAO — listagens e pesquisas (listarAtivos, ordenar, pesquisar) | | X |
| | ReparacaoDAO — inserção e gestão de estados (inserir, contarReparacoes, atualizarEstado, registarRejeicao) | X | |
| | ReparacaoDAO — conclusão e arquivamento (concluir, listarConcluidas, arquivar, obterAtrasadas) | X | |
| | ReparacaoDAO — listagens e pesquisas (listarOrdenadas, pesquisar, porCliente, porFuncionario, porData) | | X |
| | EquipamentoDAO | X | |
| | NotificacaoDAO | | X |
| | LogDAO | X | |

**Tabela 2-6 - Planeamento View**

| View | Tarefa | Santiago | Hugo |
|---|---|:---:|:---:|
| | PainelLogin.java — autenticação (campos, validação, redireccionamento) | X | |
| | PainelGestor.java — menu, gerir e arquivar reparações, editar utilizadores | X | |
| | PainelGestor.java — listagens/pesquisas, notificações, logs, estados de conta, pedidos de remoção e perfil | X | |
| | PainelFuncionario.java — pedidos atribuídos, reparações em curso, perfil, notificações | X | |
| | ExtractoReparacao.java — impressão do extrato (Printable/PrinterJob) | X | |
| | Aplicacao.java — janela principal (JFrame), barra de menus, navegação entre painéis e Look & Feel | | X |
| | Utilitarios.java — componentes reutilizáveis (tabelas, listas, formulários, diálogos, foto de perfil) | | X |
| | PainelRegisto.java — registo de utilizadores (campos dinâmicos Cliente/Funcionário, foto) | | X |
| | PainelCliente.java — equipamentos, pedidos de reparação, perfil, listagens, notificações e remoção | | X |
| | DialogoConfigBD.java — diálogo de configuração da base de dados | | X |

> Conforme o R8 do enunciado (trabalhos em grupo), a camada **View** foi repartida de forma equilibrada (≈ 50/50) entre os dois autores, tendo em conta a dimensão e complexidade de cada classe.

**Tabela 2-7 - Planeamento Util**

| Util | Tarefa | Santiago | Hugo |
|---|---|:---:|:---:|
| | Validacoes.java | X | |
| | ServicoEmail.java | X | |
| | ConexaoBD.java | | X |
| | GestorFicheiros.java | | X |

**Tabela 2-8 - Planeamento Geral**

| Geral | Tarefa | Santiago | Hugo |
|---|---|:---:|:---:|
| | Design da Base de Dados | X | X |
| | Documentação Javadoc | X | X |
| | Testes e Debugging | X | X |
| | Relatório Final | X | X |

---

## 3. Trabalho Desenvolvido

### 3.1. Requisitos Implementados

A tabela seguinte indica o estado de cada requisito da Avaliação Periódica (R1 a R14). Os requisitos específicos para a época de exame (R15 a R27 — comunicação em rede, aprovação automática por código, estatísticas, exportação CSV e edição da instituição) não fazem parte desta avaliação e, por isso, não foram desenvolvidos.

**Tabela 3-1 - Estado dos Requisitos**

| Req. | Descrição (resumida) | Estado |
|---|---|---|
| R1 | Completar e corrigir a aplicação iniciada no projeto 1 | Implementado |
| R2 | Foto de perfil dos utilizadores (inserir/alterar) com imagem genérica por defeito | Implementado |
| R3 | Envio de email a confirmar o registo (grupos) | Implementado |
| R4 | Campo adicional de observações nos formulários de inserção | Implementado |
| R5 | Realce/diálogo a indicar notificações após autenticação | Implementado |
| R6 | Impressão de um extrato de um processo (datas e dados do processo) (grupos) | Implementado |
| R7 | Listagens com barras de deslocamento (JScrollPane) | Implementado |
| R8 | Toda a interação realizada através de interface gráfica (Java Swing) | Implementado |
| R9 | Componentes adequadas à informação (JList/JTable, não texto simples) | Implementado |
| R10 | Ajuda contextual (tooltips) nos elementos dos formulários | Implementado |
| R11 | Não apresentar boas-vindas/encerramento nem estatísticas de utilização | Não aplicável (dispensado pelo enunciado) |
| R12 | Não implementar graficamente a manipulação de peças e testes | Não aplicável (dispensado); a lógica de peças/testes foi removida da aplicação |
| R13 | Gestores não criam outros gestores pela interface gráfica | Não aplicável (dispensado); apenas a criação do 1.º gestor no arranque |
| R14 | Código harmonizado e consistente | Implementado |

Todos os requisitos aplicáveis da Avaliação Periódica foram implementados com sucesso. Destacam-se a interface gráfica completa (Java Swing), a foto de perfil, o email de confirmação de registo, o alerta de notificações após login, a impressão de extratos, as listagens em tabelas com barras de deslocamento e a ajuda contextual (tooltips).

### 3.2. Classes e Packages

O projeto está organizado em 6 packages que seguem a arquitetura **MVC** acrescida de uma camada de acesso a dados **(DAOs)**.

#### Package model

Contém as classes que representam as entidades do domínio do problema:

**Tabela 3-2 - Package model**

| Classe | Descrição |
|---|---|
| Utilizador | Classe base que representa um utilizador genérico. Contém atributos comuns: ID, username, password, nome, email, tipo, estado e foto. |
| Cliente | Estende Utilizador. Adiciona atributos específicos do perfil de cliente: NIF, telefone, morada, sector de atividade e escalão (A, B, C, D). |
| Funcionario | Estende Utilizador. Adiciona atributos específicos do perfil de funcionário: NIF, telefone, morada, especialização (1-5) e data de início de atividade. |
| Equipamento | Representa um equipamento registado no sistema. Contém: ID, ID do proprietário, marca, modelo, código SKU, data de fabrico e lote. |
| Reparacao | Representa um processo de reparação. Contém todo o ciclo de vida: número de reparação, datas (criação, início, fim), tempo decorrido, custo, estado e observações. |
| Log | Registo de auditoria do sistema. Contém: data, hora, username e descrição da ação realizada. |
| Notificacao | Representa uma notificação do sistema. Contém: destinatário, mensagem, data de criação, estado (POR_LER/LIDA) e categoria. |

#### Package enums

Define as enumerações que garantem a segurança de tipos nos atributos de estado e tipo:

**Tabela 3-3 - Package enums**

| Enumeração | Valores |
|---|---|
| TipoUtilizador | GESTOR, FUNCIONARIO, CLIENTE, INDEFINIDO |
| EstadoUtilizador | PENDENTE, ATIVO, INATIVO, AGUARDA_REMOCAO, REMOVIDA, REJEITADO |
| EstadoReparacao | CRIADO, ACEITE, DECORRER, FINALIZADO, REJEITADO, ARQUIVADO |
| EstadoNotificacao | POR_LER, LIDA |
| CategoriaNotificacao | REGISTO, REPARACAO, STOCK, PRAZO, REJEICAO, REMOCAO, GERAL |

#### Package controlador

Os controladores implementam a lógica e servem como intermediários entre a camada view e a camada repositório.

**Tabela 3-4 - Package controlador**

| Classe | Responsabilidade |
|---|---|
| ControladorUtilizador | Autenticação, registo de utilizadores (Gestor, Funcionário, Cliente), envio de email de confirmação, gestão de contas, edição de perfis, atualização de foto, listagem/pesquisa de utilizadores e consulta de logs. |
| ControladorEquipamento | Registo de equipamentos, geração de SKU único, listagem, pesquisa e ordenação de equipamentos. |
| ControladorReparacao | Ciclo de vida completo da reparação: criação de pedidos, gestão de estados, atribuição a funcionários, conclusão, arquivamento, verificação de atrasos e pesquisa por intervalo de datas. |
| ControladorNotificacao | Geração de notificações individuais, listagem, contagem de não lidas e marcação como lidas, incluindo filtragem por categorias. |
| ControladorLog | Registo automático de ações de auditoria com data e hora, listagem e pesquisa de logs. |

#### Package repositorio

Os DAOs implementam as operações CRUD (Create, Read, Update, Delete) diretamente na base de dados:

**Tabela 3-5 - Package repositorio**

| Classe | Responsabilidade |
|---|---|
| UtilizadorDAO | Inserção com transação (tabelas UTILIZADOR + CLIENTE/FUNCIONARIO), validação de login, listagens com filtros, atualizações de perfil e remoção de dados. |
| EquipamentoDAO | CRUD de equipamentos, verificação de SKU/modelo duplicados, pesquisas e ordenações. |
| ReparacaoDAO | Gestão de reparações na BD: inserção, atualização de estado, conclusão, arquivamento, pesquisa por múltiplos critérios e cálculo de atrasos. |
| NotificacaoDAO | CRUD de notificações com filtros por utilizador, estado e categoria. |
| LogDAO | Inserção e consulta de registos de auditoria. |

Todos os DAOs seguem o mesmo padrão:

- Abertura de conexão;
- Execução da query com PreparedStatement;
- Fecho de recursos no bloco finally.

#### Package view

A interface gráfica está dividida em classes específicas, desenvolvidas com **Java Swing**. A janela principal (JFrame) aloja painéis (JPanel) que são trocados conforme o perfil e a ação do utilizador (remove/add/revalidate/repaint):

**Tabela 3-6 - Package view**

| Classe | Responsabilidade |
|---|---|
| Aplicacao | Ponto de entrada (main). Janela principal (JFrame) com barra de menus (Ficheiro/Ajuda). Gere a navegação entre painéis, o Look & Feel e a verificação inicial (configuração da BD e criação do primeiro Gestor). |
| Utilitarios | Classe utilitária com componentes reutilizáveis: criação de tabelas (JTable), listas (JList), campos de formulário, caixas de diálogo (JOptionPane), seleção/redimensionamento de foto (JFileChooser) e mensagens ao utilizador. |
| PainelLogin | Painel de autenticação: introdução de username e password, login, acesso ao registo e à configuração da BD; alerta de notificações pendentes após login (R5). |
| PainelRegisto | Registo de novos utilizadores, com campos dinâmicos consoante o tipo (Cliente/Funcionário), upload de foto (R2), campo de observações (R4) e validação dos dados. |
| PainelCliente | Menu do Cliente: inserir equipamentos, pedir reparações, editar perfil, listar/pesquisar equipamentos e reparações, ver notificações, consultar estado e solicitar remoção. |
| PainelFuncionario | Menu do Funcionário: ver/aceitar/rejeitar pedidos atribuídos, concluir reparações em curso, editar perfil, notificações e solicitar remoção. |
| PainelGestor | Menu do Gestor: gerir pedidos de reparação, arquivar processos, editar utilizadores, listagens/pesquisas, notificações, logs, ativar/inativar contas e gestão de remoções. |
| DialogoConfigBD | Caixa de diálogo (JDialog) para configurar os parâmetros de acesso à base de dados (IP, porto, nome, utilizador, password), gravando no ficheiro configbd.txt. |
| ExtractoReparacao | Geração e impressão do extrato de uma reparação (R6), implementando a interface Printable (PrinterJob, Graphics2D). |

Conforme o **R12**, a manipulação de peças e testes não é disponibilizada e foi removida da aplicação (as tabelas correspondentes permanecem no esquema da base de dados, mas não são utilizadas). Conforme o **R13**, não é disponibilizada a criação de novos gestores através da interface, sendo apenas criado automaticamente o primeiro gestor no arranque, caso não exista nenhum. O registo de novos utilizadores fica automaticamente ATIVO (ativação automática).

#### Package util

**Tabela 3-7 - Package util**

| Classe | Responsabilidade |
|---|---|
| ConexaoBD | Gestão de conexões à base de dados. Lê as configurações (URL, user, password) a partir do ficheiro configbd.txt. |
| Validacoes | Métodos estáticos para validação de formatos de telefone, NIF, email e normalização de datas. |
| GestorFicheiros | Cópia de ficheiros físicos (fotos de perfil) para a diretoria interna da aplicação, separando a lógica de I/O da interface gráfica. |
| ServicoEmail | Envio de emails de confirmação de registo aos novos utilizadores (R3), através de SMTP, executado numa Thread separada para não bloquear a interface. |

### 3.3. Algoritmos

**Geração de Código SKU Único**
O ControladorEquipamento implementa um algoritmo de geração de códigos SKU aleatórios únicos. É gerado um valor aleatório entre 1 e 1000000 e verificado contra a base de dados num ciclo do-while até que se encontre um valor não existente, garantindo que o código SKU seja único.

**Geração de Número de Reparação**
O ControladorReparacao gera números de reparação combinando o número total de reparações existentes mais um com a data e hora atuais.

**Verificações de Reparações Atrasadas**
No login do gestor, o sistema executa automaticamente uma verificação de todas as reparações ativas com mais de 10 dias desde a criação. Para cada reparação atrasada encontrada, é gerada uma notificação ao gestor na categoria PRAZO.

**Navegação entre painéis**
A janela principal mantém um painel de conteúdo único onde os vários painéis são trocados em tempo de execução. A transição é feita removendo o painel anterior, adicionando o novo e invocando os métodos revalidate() e repaint(), tal como recomendado para a gestão dinâmica de contentores Swing.

**Listagem em tabelas (JTable)**
As listagens são apresentadas em tabelas (JTable) alimentadas por um DefaultTableModel não editável, integradas em painéis de deslocamento (JScrollPane) conforme o R7. A ordenação é garantida automaticamente através do método setAutoCreateRowSorter(true), permitindo ao utilizador ordenar os dados clicando no cabeçalho de cada coluna.

**Foto de Perfil**
O upload da foto de perfil utiliza um JFileChooser para selecionar a imagem; esta é redimensionada (getScaledInstance) e copiada para a pasta interna "fotos/" através do GestorFicheiros. Caso o utilizador não defina uma foto, é usada uma imagem genérica por defeito (fotos/geral.png), conforme o R2.

**Email de Confirmação de Registo**
No registo de um Cliente ou Funcionário, o ControladorUtilizador invoca o ServicoEmail, que envia um email de confirmação ao novo utilizador (R3). O envio é feito numa Thread separada, para não bloquear a interface gráfica.

**Impressão de Extrato**
A impressão do extrato de uma reparação (R6) é feita implementando a interface Printable, desenhando o conteúdo no contexto Graphics2D e enviando o documento para a impressora selecionada na caixa de diálogo nativa (PrinterJob).

### 3.4. Estruturas de Dados

A principal estrutura usada no projeto é o **ArrayList** do Java. Escolhemos usar esta estrutura pois:

- Permite acesso direto por índice, e é útil para mostrar listas;
- A travessia com Iterator, adequada para as operações de listagem e pesquisa;
- Não é necessário saber qual o tamanho antecipadamente, pois os resultados das queries variam;
- Os ResultSet são naturalmente convertidos para ArrayList na camada DAO.

Para além do ArrayList, são utilizadas outras estruturas:

- **Properties** – para leitura e escrita das configurações da base de dados no ficheiro configbd.txt;
- **DefaultTableModel** e matrizes (Object[][]) – para alimentar e atualizar as tabelas (JTable) da interface gráfica.

### 3.5. Armazenamento de Dados

**Base de Dados Relacional**
O sistema utiliza uma base de dados MySQL para garantir a persistência de todos os dados. A comunicação é feita através de JDBC com o conector MySQL.

**Tabelas da Base de dados**
O modelo relacional é composto pelas seguintes tabelas.

**Tabela 3-8 - Tabelas da Base de Dados**

| Tabela | Descrição |
|---|---|
| UTILIZADOR | Dados base de todos os utilizadores (nome, username, email, password, tipo, estado, foto) |
| CLIENTE | Dados específicos de clientes (NIF, telefone, morada, sector, escalão) — FK para UTILIZADOR |
| FUNCIONARIO | Dados específicos de funcionários (NIF, telefone, morada, especialização, data início) — FK para UTILIZADOR |
| EQUIPAMENTO | Equipamentos registados — FK para UTILIZADOR (proprietário) |
| REPARACAO | Processos de reparação — FK para EQUIPAMENTO e UTILIZADOR (funcionário) |
| PECA | Peças em armazém (legado do modelo de dados; não utilizada pela aplicação) |
| REPARACAO_PECA | Associação reparação–peças (legado; não utilizada pela aplicação) |
| TESTE_OPERACIONALIDADE | Testes de reparações (legado; não utilizada pela aplicação) |
| NOTIFICACAO | Notificações do sistema — FK para UTILIZADOR (destinatário) |
| LOGS | Registos de auditoria (data, hora, utilizador, ação) |
| REPARACAO_REJEITADA | Registo de rejeições de reparações por funcionários — FK para REPARACAO e UTILIZADOR |

> As tabelas PECA, REPARACAO_PECA e TESTE_OPERACIONALIDADE fazem parte do modelo de dados original mas não são manipuladas pela aplicação gráfica (R12).

**Figura 3-1 - Modelo ER**
*(inserir aqui o diagrama de Entidade-Relacionamento, acompanhado de legenda e de um texto a descrever a imagem, conforme exigência do modelo)*

**Configuração da Base de dados**
A ligação à base de dados é configurada dinamicamente através do ficheiro **configbd.txt**, que armazena o URL de conexão, username e password no formato **Properties** do Java. A classe ConexaoBD é responsável por ler estas configurações e fornecer conexões às classes DAO. Esta configuração pode ser feita diretamente na interface gráfica através do diálogo DialogoConfigBD.

**Ficheiros Auxiliares**

**Tabela 3-9 - Ficheiros Auxiliares**

| Ficheiro | Descrição |
|---|---|
| configbd.txt | Configurações de acesso à base de dados (formato Properties) |
| fotos/ | Diretoria onde são guardadas as fotos de perfil dos utilizadores (inclui a imagem genérica por defeito) |

**Estrutura de Ficheiros do Projeto**
Para obter a estrutura de ficheiros do projeto, abriu-se um terminal na pasta "src" e executou-se o comando `tree /F`.

**Figura 3-2 - Estrutura de Ficheiros do Projeto**

```
controlador
    ControladorEquipamento.java
    ControladorLog.java
    ControladorNotificacao.java
    ControladorReparacao.java
    ControladorUtilizador.java
Enums
    CategoriaNotificacao.java
    EstadoNotificacao.java
    EstadoReparacao.java
    EstadoUtilizador.java
    TipoUtilizador.java
model
    Cliente.java
    Equipamento.java
    Funcionario.java
    Log.java
    Notificacao.java
    Reparacao.java
    Utilizador.java
repositorio
    EquipamentoDAO.java
    LogDAO.java
    NotificacaoDAO.java
    ReparacaoDAO.java
    UtilizadorDAO.java
util
    ConexaoBD.java
    GestorFicheiros.java
    ServicoEmail.java
    Validacoes.java
view
    Aplicacao.java
    DialogoConfigBD.java
    ExtractoReparacao.java
    PainelCliente.java
    PainelFuncionario.java
    PainelGestor.java
    PainelLogin.java
    PainelRegisto.java
    Utilitarios.java
```

### 3.6. Procedimentos de Teste

Os testes adotados foram de natureza manual, à medida que íamos implementando requisitos íamos testando o código para tal.

**Testes de autenticação e gestão de contas**
- Registo de utilizadores com dados válidos e inválidos (email, NIF, telefone);
- Verificação do envio do email de confirmação após o registo (R3);
- Login com credenciais corretas e incorretas;
- Verificação de comportamento para contas em diferentes estados;
- Criação de primeiro Gestor na primeira execução;
- Navegação entre os painéis consoante o perfil autenticado.

**Testes de interface gráfica**
- Troca de painéis (login, registo, menus) sem perda de estado;
- Apresentação e ordenação de dados em tabelas (JTable) com barras de deslocamento (R7);
- Alerta de notificações após autenticação (R5);
- Caixas de diálogo de erro, sucesso e confirmação (JOptionPane);
- Seleção e atualização de foto de perfil, incluindo a imagem por defeito (R2);
- Verificação dos tooltips de ajuda nos formulários (R10);
- Impressão do extrato de uma reparação (R6).

**Testes de gestão de equipamentos**
- Inserção de equipamentos com código de modelo duplicado;
- Validação da geração de SKU único;
- Listagens ordenadas e pesquisas parciais.

**Testes de ciclo de vida da reparação**
- Submissão de pedido → Aprovação pelo gestor → Atribuição a Funcionário → Aceitação → Conclusão → Arquivar;
- Cenário de rejeição por funcionário e reatribuição;
- Verificação de reparações atrasadas.

**Testes de validação de dados**
- Introdução de valores não numéricos em campos inteiros e decimais;
- Validação de formato de email (designacao@entidade.dominio);
- Validação de NIF;
- Validação de telefone.

**Testes de transações**
- Inserção de utilizador com username ou email duplicado;
- Verificação do rollback em operações multi-tabela (inserção de UTILIZADOR + CLIENTE/FUNCIONARIO).

---

## 4. Conclusões

Este projeto atingiu com sucesso o objetivo principal de desenvolver um sistema de gestão de oficina funcional e completo, **dotado de uma interface gráfica**, aplicando os princípios de Programação Orientada a Objetos e o padrão MVC com uma camada de acesso a dados DAO. Todos os requisitos aplicáveis da Avaliação Periódica foram implementados com sucesso.

### 4.1. Forças

O sistema desenvolvido apresenta diversas forças. Em primeiro lugar, a arquitetura MVC complementada com a camada DAO assegura uma separação clara de responsabilidades. A **interface gráfica** desenvolvida em Java Swing melhora significativamente a usabilidade face à versão de consola, oferecendo navegação por painéis, tabelas ordenáveis com barras de deslocamento, formulários com ajuda contextual (tooltips) e caixas de diálogo. A validação robusta de todos os dados de entrada constitui outro ponto forte, uma vez que o sistema impede a introdução de valores inválidos em campos como email, NIF e telefone, evitando erros e inconsistências na base de dados. O código foi ainda harmonizado para garantir consistência (R14).

### 4.2. Limitações

Apesar dos resultados alcançados, o sistema apresenta algumas limitações. Não é implementada encriptação de passwords, sendo estas armazenadas em texto simples na base de dados. As credenciais do serviço de email encontram-se definidas diretamente no código, o que não é recomendável do ponto de vista da segurança. Por fim, os testes realizados foram exclusivamente manuais, não existindo testes automatizados.

### 4.3. Trabalho Futuro

Encriptação de passwords; externalização das credenciais do serviço de email para um ficheiro de configuração; introdução de testes automatizados; e melhorias adicionais de usabilidade na interface gráfica.

---

## 5. Referências

### 5.1. Lista de Referências

[1] Java. [Online]. Disponível em: https://www.java.com/pt-BR/
[2] MySQL. [Online]. Disponível em: https://www.mysql.com/
[3] JDBC. [Online]. Disponível em: https://docs.oracle.com/javase/tutorial/jdbc/index.html
[4] PowerDesigner. [Online]. Disponível em: https://www.powerdesigner.biz/
[5] HeidiSQL. [Online]. Disponível em: https://www.heidisql.com/download.php
[6] Visual Studio Code. [Online]. Disponível em: https://code.visualstudio.com/download
[7] GitHub Desktop. [Online]. Disponível em: https://desktop.github.com/download/
[8] Java Swing — The Java Tutorial (Creating a GUI With Swing). [Online]. Disponível em: https://docs.oracle.com/javase/tutorial/uiswing/index.html
[9] FlatLaf — Flat Look and Feel. [Online]. Disponível em: https://www.formdev.com/flatlaf/
[10] JavaMail / Jakarta Mail. [Online]. Disponível em: https://javaee.github.io/javamail/

---

## 6. Anexos

Em anexo encontram-se o **manual do utilizador** (descrição do funcionamento da aplicação), o diagrama de Entidade-Relacionamento da base de dados, o script SQL de criação da base de dados e a documentação técnica gerada automaticamente através do JavaDoc.
