# Conversão para Interface Gráfica — Requisitos do Enunciado (Secções 2.1 e 2.2)

## Mapeamento de Requisitos do Enunciado

Abaixo estão **todos os requisitos das secções 2.1 e 2.2** (AP3) com a implementação proposta:

| Req. | Descrição | Grupo? | Implementação |
|------|-----------|--------|---------------|
| **R1** | Completar e corrigir a aplicação do projecto 1 | Não | Manter toda a lógica existente (Model/Controller/DAO) + converter View para Swing |
| **R2** | Utilizadores possuem **foto** (inserir/alterar a qualquer momento; imagem genérica por defeito) | Não | `JLabel` com `ImageIcon` no perfil; `JFileChooser` para upload; imagem default embebida |
| **R3** | No registo enviar **email** a informar que o registo foi realizado | **Sim (grupo)** | Usar `javax.mail` (JavaMail API) para envio de email após registo com sucesso |
| **R4** | Formulários devem ter campo adicional (`JTextField`/`JTextArea`) para **observações/comentários** | Não | Adicionar `JTextArea` de observações em todos os formulários de inserção |
| **R5** | Após login, **caixa de diálogo ou zona em realce** a indicar notificações pendentes | Não | `JOptionPane` ou `JPanel` destacado (cor de fundo diferente) com contagem de notificações |
| **R6** | Imprimir **extracto das acções** de um processo (datas + utilizadores) | **Sim (grupo)** | Gerar relatório com `JTable` dos logs do processo + botão "Imprimir" com `PrinterJob` |
| **R7** | Listagens devem incluir **barras de deslocamento** (scroll) | Não | Todas as `JTable`/`JList` dentro de `JScrollPane` |
| **R8** | Toda a interacção via **JavaSwing** (janelas, painéis, diálogos, menus, tabelas, botões, etc.). Interface intuitiva, limpa e clara. | Não | `JFrame` + `JPanel` + `CardLayout` + `JMenuBar` + `JTable` + `JOptionPane` |
| **R9** | Componentes gráficas **adequadas** à informação (JList para 1 coluna, JTable para várias colunas) | Não | `JTable` com `DefaultTableModel` para listagens multi-coluna; `JList` para seleções simples |
| **R10** | Formulários com **tooltips** de ajuda em cada elemento | Não | `setToolTipText("...")` em todos os `JTextField`, `JButton`, `JComboBox`, etc. |
| **R11** | **NÃO** é necessário mensagens de boas-vindas/encerramento nem estatísticas de execução na GUI | Não | Remover lógica de `info_sistema.dat` e `calcularTempoExecucao()` da versão gráfica |
| **R12** | **NÃO** é necessário implementar graficamente a manipulação de peças e testes | Não | Peças e testes mantêm-se apenas como operações internas (não criar painéis GUI para isso) |
| **R13** | **NÃO** é necessário gestores criarem outros gestores na GUI | Não | Remover "Criar Novo Gestor" do menu gráfico do Gestor |
| **R14** | Código **harmonizado** — mesma funcionalidade implementada de forma idêntica em todo o código | Não | Criar métodos utilitários reutilizáveis para tabelas, formulários, eventos |

---

## Impacto dos Requisitos na Arquitetura

### O que INCLUIR na GUI:
- ✅ Login/Registo com foto (R2) e campo de observações (R4)
- ✅ Notificações em destaque após login (R5)
- ✅ Extracto de acções por processo — impressão (R6, grupo)
- ✅ Email no registo (R3, grupo)
- ✅ Todas as listagens com `JScrollPane` (R7)
- ✅ Tooltips em todos os formulários (R10)
- ✅ Componentes adequadas — JTable para multi-coluna, JList para coluna única (R9)
- ✅ Gestão de utilizadores (ativar/inativar/remover contas)
- ✅ Gestão de reparações (criar, aceitar, atribuir, rejeitar, arquivar)
- ✅ Notificações (ver por categoria, marcar como lidas)
- ✅ Logs (listar, pesquisar)
- ✅ Listagens e pesquisas com ordenação

### O que NÃO incluir na GUI:
- ❌ Mensagens de boas-vindas/encerramento/estatísticas (R11)
- ❌ Manipulação gráfica de peças e testes de operacionalidade (R12)
- ❌ Criação de gestores via GUI (R13)

---

## Proposed Changes

### Estrutura de Ficheiros

```
src/view/
├── AplicacaoGUI.java        [NEW]  — JFrame principal, CardLayout, JMenuBar
├── PainelLogin.java          [NEW]  — Ecrã de login
├── PainelRegisto.java        [NEW]  — Ecrã de registo (Func/Cliente + foto + observações)
├── PainelCliente.java        [NEW]  — Menu/funcionalidades do Cliente
├── PainelFuncionario.java    [NEW]  — Menu/funcionalidades do Funcionário
├── PainelGestor.java         [NEW]  — Menu/funcionalidades do Gestor
├── DialogoConfigBD.java      [NEW]  — JDialog para configurar BD
├── DialogoPerfil.java        [NEW]  — JDialog para editar perfil + foto
├── DialogoExtracto.java      [NEW]  — JDialog para extracto de processo (R6)
├── Utilitarios.java          [NEW]  — Métodos comuns: criar tabela, criar formulário (R14)
├── Aplicacao.java            [KEEP] — Versão consola (backup, não chamada)
├── ViewCliente.java          [KEEP] — Versão consola (backup)
├── ViewFuncionario.java      [KEEP] — Versão consola (backup)
└── ViewGestor.java           [KEEP] — Versão consola (backup)
```

---

### Detalhe por Ficheiro

#### [NEW] `AplicacaoGUI.java` — Janela Principal
**Satisfaz: R1, R8, R11**

```
extends JFrame implements ActionListener
```

- `main()` que lança a GUI (substitui o main da consola)
- `CardLayout` no `contentPane` para trocar painéis (Login → Menu Cliente/Func/Gestor)
- `JMenuBar` com:
  - Menu "Ficheiro" → "Configurar BD", "Sair"
  - Menu "Ajuda" → "Sobre"
- `BorderLayout`: NORTH=menu, CENTER=painel dinâmico, SOUTH=barra de estado
- `setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)`
- **SEM** mensagens de boas-vindas, estatísticas ou tempo de execução (R11)
- Verificação do `configbd.txt` no arranque (se não existir → `DialogoConfigBD`)
- Verificação se existe gestor (se não → diálogo de criação de 1º gestor)

---

#### [NEW] `PainelLogin.java` — Ecrã de Login
**Satisfaz: R1, R5, R8, R10**

- `JTextField` username + `JPasswordField` password
- `JButton` "Entrar", "Registar", "Configurar BD"
- Tooltips (R10): `"Introduza o seu username"`, `"Introduza a sua password"`, etc.
- Após login bem-sucedido:
  - **R5**: Se houver notificações → `JOptionPane.showMessageDialog()` com ícone de aviso e contagem
  - Se conta PENDENTE/REJEITADA/INATIVA → `JOptionPane` informativo (R22 aplica-se em exame, mas incluímos por consistência)
- Redireciona para o painel correto (Cliente/Funcionário/Gestor) via `CardLayout`

---

#### [NEW] `PainelRegisto.java` — Ecrã de Registo
**Satisfaz: R2, R3(grupo), R4, R8, R9, R10**

- `JComboBox` para tipo (Funcionário/Cliente)
- Campos comuns: Nome, Email, Username, Password
- **R2 — Foto**: `JLabel` com preview da imagem + `JButton` "Escolher Foto" que abre `JFileChooser` com filtro de imagens. Se nenhuma foto → usa imagem genérica
- **R4 — Observações**: `JTextArea` dentro de `JScrollPane` para comentários
- Campos dinâmicos conforme tipo:
  - Funcionário: NIF, Telefone, Morada, Especialização (`JComboBox` 1-5), Data Início
  - Cliente: NIF, Telefone, Morada, Setor Atividade, Escalão (`JComboBox` A-D)
- **R3 (grupo) — Email**: Após registo com sucesso → enviar email via `javax.mail`
- **R10 — Tooltips**: Em TODOS os campos (`"NIF: 9 dígitos numéricos"`, `"Email: formato X@Y.Z"`, etc.)
- Validações com `JOptionPane` para erros

---

#### [NEW] `PainelCliente.java` — Menu do Cliente
**Satisfaz: R1, R4, R5, R7, R8, R9, R10**

- Painel esquerdo com botões de navegação (ou `JMenuBar` interno)
- Painel central que troca conforme a opção selecionada
- **Funcionalidades** (mapeadas das 10 opções da consola, excluindo o que R12 dispensa):
  1. **Inserir Equipamento** — Formulário com `JTextField` (Marca, Modelo, Data Fabrico, Lote) + `JTextArea` observações (R4) + Tooltips (R10)
  2. **Pedir Reparação** — `JTable` com equipamentos do cliente (R9) dentro de `JScrollPane` (R7) + botão "Pedir Reparação" + `JTextArea` observações (R4)
  3. **O Meu Perfil** — Formulário de edição + foto (R2) com `JFileChooser` + `JButton` "Alterar Foto"
  4. **Listar Reparações** — `JTable` (R9) + `JScrollPane` (R7) + `JComboBox` para critério de ordenação
  5. **Pesquisar Reparações** — `JTextField` pesquisa + `JComboBox` critério + `JTable` resultados
  6. **Listar Equipamentos** — `JTable` + `JScrollPane` + `JComboBox` ordenação
  7. **Pesquisar Equipamentos** — `JTextField` + `JComboBox` + `JTable`
  8. **Notificações** — `JTable` com notificações + botão "Marcar como Lidas"
  9. **Solicitar Remoção** — `JOptionPane.showConfirmDialog()` com YES/NO
  10. **Consultar Estado Reparações** — `JTable` (Nº Processo + Estado)
- `JButton` "Logout" → volta ao painel de login

---

#### [NEW] `PainelFuncionario.java` — Menu do Funcionário
**Satisfaz: R1, R4, R5, R6(grupo), R7, R8, R9, R10, R12**

- Mesma estrutura de painel esquerdo + central do Cliente
- **Funcionalidades** (7 opções, **sem** peças/testes na GUI por R12):
  1. **Ver Pedidos Novos** — `JTable` pedidos atribuídos + botões "Aceitar" / "Rejeitar"
  2. **Reparações em Curso** — `JTable` + selecionar → botão "Concluir" (custo + observações) — **SEM inserir peças/testes na GUI** (R12)
  3. **O Meu Perfil** — Formulário edição + foto (R2)
  4. **Listar Reparações** — `JTable` + ordenação
  5. **Pesquisar Reparações** — `JTextField` + `JComboBox` + `JTable`
  6. **Notificações** — `JTable`
  7. **Solicitar Remoção** — `JOptionPane.showConfirmDialog()`
- **R6 (grupo)**: Botão "Extracto do Processo" em reparações → abre `DialogoExtracto` com `JTable` dos logs/ações desse processo + botão "Imprimir"

---

#### [NEW] `PainelGestor.java` — Menu do Gestor
**Satisfaz: R1, R4, R5, R6(grupo), R7, R8, R9, R10, R12, R13**

- **Funcionalidades** (do menu original, **sem** criar gestores por R13, **sem** peças por R12):
  1. **Ativar Contas** — `JTable` contas pendentes + botões "Ativar" / "Rejeitar"
  2. **Gerir Reparações** — `JTable` pendentes + `JComboBox` funcionários + "Aceitar" / "Rejeitar"
  3. ~~Criar Gestor~~ — **REMOVIDO** (R13)
  4. ~~Stock de Peças~~ — **NÃO na GUI** (R12)
  5. **Arquivar Processos** — `JTable` finalizados + botão "Arquivar"
  6. **Editar Utilizadores** — `JTable` + selecionar → formulário edição
  7. **Listagens e Pesquisas** — Sub-painel com:
     - Listar Utilizadores (`JTable` + ordenação)
     - Listar Reparações (`JTable` + ordenação)
     - Reparações Não Finalizadas (`JTable`)
     - Pesquisar Reparações / Equipamentos / Utilizadores (`JTextField` + `JComboBox` + `JTable`)
     - Pesquisar por Data (`JTextField` Data Início/Fim + `JTable`)
     - Consultar todas as reparações (`JTable`)
  8. **Notificações** — Dashboard com `JComboBox` categoria + `JTable` + contadores por categoria
  9. **Logs** — `JTable` + `JTextField` filtro por username
  10. **Ativar/Inativar Contas** — `JTable` + botões toggle
  11. **Pedidos Remoção** — `JTable` + botões "Aceitar" / "Recusar"
  12. **Solicitar Remoção** — `JOptionPane.showConfirmDialog()` (com verificação de gestor único)
- **R6 (grupo)**: Botão "Extracto" nas reparações → `DialogoExtracto`

---

#### [NEW] `DialogoConfigBD.java` — Configuração da BD
**Satisfaz: R1, R10**

- `extends JDialog`
- `JTextField`: IP, Porto, Nome BD, Username + `JPasswordField`: Password
- **R10**: Tooltips em cada campo (`"Ex: localhost"`, `"Ex: 3306"`, etc.)
- `JButton` "Guardar" → grava em `configbd.txt` via `Properties`
- `JButton` "Cancelar"

---

#### [NEW] `DialogoPerfil.java` — Edição de Perfil + Foto
**Satisfaz: R2, R4, R10**

- `extends JDialog`
- `JLabel` com `ImageIcon` da foto atual (ou genérica)
- `JButton` "Alterar Foto" → `JFileChooser` com filtro (`.jpg`, `.png`, `.gif`)
- Campos editáveis: Email, Password, Telefone, Morada
- **R4**: `JTextArea` observações
- **R10**: Tooltips em cada campo

---

#### [NEW] `DialogoExtracto.java` — Extracto de Processo (R6, grupo)
**Satisfaz: R6**

- `extends JDialog`
- `JTable` com colunas: Data, Hora, Utilizador, Ação
- Dados filtrados pelos logs do processo selecionado
- `JButton` "Imprimir" → usa `PrinterJob` e `table.print()` do Swing
- `JScrollPane` (R7)

---

#### [NEW] `Utilitarios.java` — Métodos Reutilizáveis (R14)
**Satisfaz: R14**

Classe utilitária para **harmonizar** o código:

```java
public class Utilitarios {
    // Criar JTable padronizada com ScrollPane
    public static JScrollPane criarTabela(String[] colunas, Object[][] dados) { ... }
    
    // Criar campo com label e tooltip
    public static JPanel criarCampoFormulario(String label, JComponent campo, String tooltip) { ... }
    
    // Mostrar mensagem de erro
    public static void mostrarErro(Component pai, String msg) { ... }
    
    // Mostrar mensagem de sucesso
    public static void mostrarSucesso(Component pai, String msg) { ... }
    
    // Confirmar ação (S/N)
    public static boolean confirmar(Component pai, String msg) { ... }
    
    // Atualizar dados da tabela
    public static void atualizarTabela(DefaultTableModel model, String[] colunas, Object[][] dados) { ... }
}
```

---

## Componentes Swing por Requisito

| Requisito | Componentes Swing Usadas |
|-----------|--------------------------|
| R2 (Foto) | `ImageIcon`, `JLabel`, `JFileChooser`, `FileNameExtensionFilter` |
| R3 (Email, grupo) | `javax.mail.Session`, `MimeMessage`, `Transport` |
| R4 (Observações) | `JTextArea`, `JScrollPane` |
| R5 (Notificações) | `JOptionPane.showMessageDialog()` com `WARNING_MESSAGE` |
| R6 (Extracto, grupo) | `JTable`, `JDialog`, `PrinterJob`, `table.print()` |
| R7 (Scroll) | `JScrollPane` em TODAS as `JTable`/`JList` |
| R8 (GUI Swing) | `JFrame`, `JPanel`, `JButton`, `JTextField`, `JComboBox`, etc. |
| R9 (Componentes adequadas) | `JTable`+`DefaultTableModel` para multi-coluna, `JList` para coluna única |
| R10 (Tooltips) | `setToolTipText()` em TODOS os elementos interativos |
| R14 (Harmonização) | Classe `Utilitarios.java` com métodos comuns |

---

## Open Questions

> [!IMPORTANT]
> 1. **Fotos de utilizador (R2)**: As fotos devem ser guardadas na base de dados (como BLOB) ou como ficheiros locais (com o caminho guardado na BD)? A tua BD já tem uma coluna para fotos?
> 2. **Email (R3, grupo)**: Tens um servidor SMTP disponível para envio de emails? (Gmail SMTP, servidor da escola, etc.) Qual o email remetente?
> 3. **Extracto/Impressão (R6, grupo)**: Os logs do processo já estão na BD com referência ao `idReparacao`? Preciso verificar se o `LogDAO` suporta filtrar por processo.
> 4. **Substituir o main()**: Queres que o `main()` do `AplicacaoGUI.java` substitua completamente o `main()` do `Aplicacao.java`, ou manter ambos e escolher qual executar?

---

## Verification Plan

### Compilação e Execução
- Compilar com `javac` sem erros
- Executar `AplicacaoGUI` e verificar que a janela abre

### Testes por Requisito
| Req. | Teste |
|------|-------|
| R2 | Upload de foto + verificar preview + imagem default |
| R3 | Registar utilizador e verificar envio de email |
| R4 | Verificar campo de observações em todos os formulários |
| R5 | Login com notificações pendentes → verificar diálogo/realce |
| R6 | Selecionar processo → ver extracto → imprimir |
| R7 | Listagens com muitos registos → verificar scroll funcional |
| R8 | Navegar por todos os ecrãs e funcionalidades |
| R9 | Verificar JTable em listagens multi-coluna |
| R10 | Hover sobre cada campo → verificar tooltip |
| R14 | Verificar consistência de código entre painéis |
