# Guião do Vídeo — LEI_PA (Sistema de Gestão de Oficina)

Alvo: < 7 min, < 50 MB, com narração. Ordem pensada para ser gravada **de uma só vez**.

---

## A. PREPARAÇÃO ANTES DE GRAVAR (não gravar isto)

1. **MySQL ligado** e base de dados criada de raiz: correr o `crebass.sql` (BD vazia, sem dados).
2. **`configbd.txt`** ao lado do JAR já com os parâmetros certos (ou deixa propositadamente errado para mostrares o "Configurar BD" — ver passo 2).
3. **Pasta `lib` ao lado do JAR** e testar o duplo-clique uma vez (fechar a seguir).
4. **Pré-criar 1 Funcionário ATIVO** (para haver a quem atribuir a reparação) — para poupar tempo no vídeo.
   - Faz isto com a app ANTES de gravar: regista um Funcionário e confirma que fica ATIVO.
   - Anota o username/password dele.
5. **Email real e acessível** para o registo do Cliente (vais mostrar a chegada do email). Deixa o webmail/Gmail aberto noutro separador.
6. **Foto** à mão (um .png/.jpg qualquer) para o passo de "alterar foto".
7. Gravador: 1080p ou 720p, **15–20 fps chega**, microfone ligado. Para ficar < 50 MB: 720p + bitrate baixo, ou cortar no fim. (Game Bar: Win+G).

> Dica: ensaia uma vez sem gravar. As transições de login/logout são o que come tempo — sê rápido nelas.

---

## B. PONTOS A NARRAR COM HONESTIDADE (importante)

- **Requisitos:** "Todos os requisitos da Avaliação Periódica (R1–R14) foram implementados. Os requisitos da época de exame (R15–R27: comunicação em rede, aprovação automática por código, estatísticas, exportação CSV e edição da instituição) não faziam parte desta avaliação e não foram desenvolvidos."
- **Limitações** (dizer no fecho): "as passwords são guardadas em texto simples e as credenciais do serviço de email estão no código — são as melhorias futuras identificadas."
- **Aprovação de conta:** na nossa implementação a conta fica **ATIVA automaticamente** e o **gestor é notificado** do novo registo; o gestor mantém o controlo do estado das contas através do **Ativar/Inativar**. (Narra assim — não digas que há aprovação manual obrigatória.)
- **NIF único:** é UNIQUE na base de dados; ao tentar um 2.º registo com o mesmo NIF o sistema **rejeita o registo**. (A mensagem no ecrã é genérica — "username ou email já em uso" — mas a causa pode ser o NIF; menciona isto.)
- **Foto de um utilizador:** mostra-se a foto via **"O Meu Perfil"** do gestor (alterar + ver). A foto por defeito (`fotos/geral.png`) demonstra-se no registo do cliente.

---

## C. DIVISÃO DE TRABALHO (preencher/confirmar)

> Sugestão a confirmar por ti (André Santiago e Hugo Nobre). Ajusta à realidade:
- **André Santiago:** camada de Validações, Controladores (Utilizador/Reparação), Views de Login/Registo/Cliente, serviço de email.
- **Hugo Nobre:** camada DAO/Repositório, Views de Gestor/Funcionário, Notificações, Logs, extrato (impressão).
- Modelo de dados, arquitetura MVC+DAO e testes manuais: em conjunto.

---

## D. GUIÃO (cena a cena, com narração)

### 0) Abertura — Introdução  (~0:00–0:40)
**Mostrar:** título da app / slide simples ou o relatório aberto.
**Narração:**
> "Boa tarde. Este é o Sistema de Gestão de Oficina, desenvolvido em Java Swing com MySQL, arquitetura MVC e camada DAO, por André Santiago e Hugo Nobre. Todos os requisitos da Avaliação Periódica, do R1 ao R14, foram implementados. Os requisitos da época de exame — R15 a R27 — não faziam parte desta avaliação e não foram desenvolvidos. Quanto à divisão de trabalho: [DIZER A DIVISÃO]."

### 1) Arranque — JAR  (~0:40–1:00)
**Ação:** duplo-clique no `Projeto_PA_GRAFICO.jar`.
**Narração:**
> "Arranco a aplicação diretamente a partir do ficheiro JAR."

### 2) Definir credenciais da BD  (~1:00–1:25)
**Ação:** menu **Ficheiro → Configurar BD** (ou botão "Configurar BD" no login). Preencher IP `127.0.0.1`, Porto `3306`, Nome da BD, Username, Password → **Guardar**.
**Narração:**
> "Defino as credenciais de acesso à base de dados — IP, porto, nome da base de dados, utilizador e palavra-passe. Os parâmetros ficam guardados no ficheiro configbd.txt."

> (Se a BD não tiver gestor, a app pede já o **Primeiro Gestor**.)
**Ação:** preencher Nome/Email/Username/Password do gestor → OK → na pergunta da foto escolher **"Não"**.
**Narração:**
> "Como é o primeiro arranque, crio o gestor inicial. Não escolho foto, por isso o sistema atribui a **imagem por defeito**."

### 3) Cliente cria nova conta  (~1:25–2:25)
**Ação:** no login, botão **Registar**. Tipo = **Cliente**.
- **Passar o rato pelos campos** para mostrar os **tooltips**.
**Narração:** "No registo, todos os campos têm ajuda contextual por tooltip."
- Escrever email inválido (ex.: `joao`) + tentar **Registar** → erro de email.
**Narração:** "O email é validado no formato designacao@entidade.dominio."
- Corrigir email; pôr telefone inválido (ex.: `123`) → **Registar** → erro de telefone.
**Narração:** "O telefone tem de ter 9 dígitos e começar por 2, 3 ou 9."
- Pôr NIF inválido (ex.: `12`) → erro de NIF; corrigir.
**Narração:** "O NIF tem de ter 9 dígitos."
- **Não** escolher foto. Preencher tudo válido (usar **um email real teu**) → **Registar** → sucesso.
**Narração:** "Sem foto selecionada, fica a foto por defeito. Registo concluído."
- **Demonstrar unicidade:** Registar **outra vez** com o **mesmo email/NIF** → erro.
**Narração:** "O email e o NIF são únicos na base de dados — o sistema rejeita o registo duplicado."

### 4) Gestor é notificado e gere a conta  (~2:25–3:00)
**Ação:** login como **Gestor** → aparece o alerta **"Tens N notificações não lidas"**.
**Narração:** "Ao autenticar, o gestor é alertado para notificações pendentes."
**Ação:** menu **Notificações** → categoria **REGISTO** → **Ver**.
**Narração:** "O gestor foi notificado do novo registo do cliente."
**Ação:** menu **Ativar/Inativar** → selecionar o cliente.
**Narração:** "A conta fica ativa automaticamente; ainda assim, o gestor controla o estado das contas aqui, podendo ativá-las ou inativá-las."

### 5) Gestor lista/ordena, altera dados e foto, pesquisa  (~3:00–3:50)
**Ação:** menu **Listagens/Pesquisas** → opção **Utilizadores** → **Executar** (lista ordenada por nome).
**Narração:** "Listagem de utilizadores ordenada por nome."
**Ação:** menu **Editar Utilizadores** → selecionar o cliente na tabela → preencher Nome, Username, Email e Password → **Guardar** → sucesso.
**Narração:** "O gestor pode alterar os dados de qualquer utilizador." (Nota: tens de preencher os 4 campos, incluindo a password.)
**Ação:** menu **O Meu Perfil** → clicar na foto → escolher imagem → **Foto atualizada**.
**Narração:** "Aqui altero e visualizo a foto de um utilizador."
**Ação:** menu **Listagens/Pesquisas** → opção **Pesquisar Utilizadores** → escrever **parte do nome** → **Executar**.
**Narração:** "Pesquisa de utilizador por nome incompleto."

### 6) Cliente cria pedido de reparação  (~3:50–4:25)
**Ação:** logout → login como **Cliente**.
**Ação:** menu **Inserir Equipamento** → Marca, Modelo, Data de fabrico, Lote → **Registar Equipamento** (mostra o SKU gerado).
**Narração:** "O cliente regista o equipamento; o SKU é gerado automaticamente e é único."
**Ação:** menu **Pedir Reparação** → selecionar o equipamento na tabela → escrever observações → **Pedir Reparação** → sucesso.
**Narração:** "Submete o pedido de reparação para esse equipamento."

### 7) Gestor aprova e atribui funcionário  (~4:25–4:45)
**Ação:** logout → login **Gestor** → menu **Gerir Reparações** → selecionar o pedido → escolher o **Funcionário** na combo → **Aceitar e Atribuir** → sucesso.
**Narração:** "O gestor é notificado do pedido, aprova-o e atribui-o a um funcionário."

### 8) Funcionário aceita e finaliza  (~4:45–5:15)
**Ação:** logout → login **Funcionário** → alerta de notificação → menu **Pedidos Atribuídos** → selecionar → **Aceitar e Iniciar**.
**Narração:** "O funcionário é notificado, aceita o processo e inicia a reparação."
**Ação:** menu **Reparações em Curso** → selecionar → **Custo (€)** + Observações → **Concluir Reparação** → sucesso.
**Narração:** "Conclui a reparação, registando o custo e o tempo decorrido. O cliente é notificado da conclusão."

### 9) Gestor lista por intervalo de datas e pesquisa processo  (~5:15–5:45)
**Ação:** logout → login **Gestor** → menu **Listagens/Pesquisas** → opção **Reparações por Data** → **Data inicial** e **Data final** → **Executar**.
**Narração:** "O gestor lista todos os processos de um intervalo de tempo, ordenados por data."
**Ação:** opção **Pesquisar Reparações** → escrever o **número da reparação** → **Executar**.
**Narração:** "E pesquisa um processo específico pelo seu número."

### 10) Grupos — Extrato + Email  (~5:45–6:25)
**Ação:** logout → login **Cliente** → menu **Listar Reparações** → **Listar** → selecionar a reparação finalizada → **Imprimir Extrato** → na caixa de impressão, escolher "Microsoft Print to PDF" (ou impressora) → mostrar o resultado.
**Narração:** "O cliente imprime o extrato da reparação, com o histórico, o custo e o estado."
**Ação:** mudar para o separador do **webmail/Gmail** → abrir o email **"Confirmação de Registo"**.
**Narração:** "E aqui está o email de confirmação enviado automaticamente após o registo do cliente."

### 11) Encerramento — Logs  (~6:25–6:55)
**Ação:** voltar à app como **Gestor** → menu **Logs** → **Todos** (mostra o log da aplicação) → escrever um **username** → **Pesquisar** (log de um utilizador específico).
**Narração:** "Por fim, o registo de auditoria: o log completo da aplicação e o log filtrado por um utilizador específico."
**Narração de fecho:**
> "Em resumo, todos os requisitos da Avaliação Periódica foram implementados. Como melhorias futuras identificámos a encriptação de passwords e a externalização das credenciais de email. Obrigado."

---

## E. CHECKLIST FINAL (requisitos da prof. cobertos)
- [x] Requisitos implementados/não implementados (cena 0 + 11)
- [x] Divisão de trabalho (cena 0)
- [x] Arranque por JAR (cena 1)
- [x] Credenciais da BD (cena 2)
- [x] Cliente cria conta + tooltips + validação email/telefone + foto por defeito + email/NIF únicos (cena 3)
- [x] Gestor notificado + gestão da conta (cena 4)
- [x] Gestor lista com ordenação + altera dados + altera/mostra foto (cena 5)
- [x] Gestor pesquisa por nome incompleto (cena 5)
- [x] Cliente cria pedido de reparação (cena 6)
- [x] Gestor aprova + atribui funcionário (cena 7)
- [x] Funcionário aceita + processo finalizado (cena 8)
- [x] Gestor lista/ordena por intervalo de datas (cena 9)
- [x] Gestor pesquisa processo específico (cena 9)
- [x] Imprimir extrato (cena 10)
- [x] Envio de email após registo (cena 10)
- [x] Ver log da aplicação e de utilizador específico (cena 11)
