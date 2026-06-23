# Analysis Results 22
## Bugs, Inconsistências e Verificação de Requisitos (Enunciado AP3)

**Âmbito:** todo o projeto `ProjetoGrafico/src` (model, enums, controlador, repositorio, util, view)
**Base:** enunciado LEI_PA_AP3 (apenas R1–R14 da Avaliação Periódica; R15–R27 são de Exame e não se aplicam)
**Estado da compilação:** `javac` (JDK 16) → **EXIT=0** (compila sem erros).
**Importante:** este documento **apenas identifica**; nenhum código foi alterado.

Legenda de severidade: **Alto** (afeta funcionalidade/decisão de projeto) · **Médio** (comportamento errado/confuso) · **Baixo** (limpeza/eficiência/estilo).

---

## 1. Bugs funcionais

### B1 — Pesquisa de equipamentos do Cliente por "Código de Modelo" filtra na realidade por SKU · **Médio**
**Onde:** [PainelCliente.java](ProjetoGrafico/src/view/PainelCliente.java) (mostrarPesquisarEquipamentos) → [ControladorEquipamento.java](ProjetoGrafico/src/controlador/ControladorEquipamento.java#L125) → [EquipamentoDAO.java:343-346](ProjetoGrafico/src/repositorio/EquipamentoDAO.java#L343-L346).
O combo do cliente tem as opções `{ "Marca", "Código de Modelo" }` e mapeia "Código de Modelo" → `criterio = 2`. Mas o DAO faz:
```java
String coluna = (escolha == 1) ? "E_MARCA" : "E_CODIGO_SKU"; // escolha 2 = SKU, não MODELO
```
Ou seja, ao pesquisar por **"Código de Modelo"** o sistema filtra por **SKU** → resultados errados/vazios.
**Correção possível:** usar `E_CODIGO_MODELO` no critério 2, ou mudar o rótulo do combo para "SKU".

### B2 — Registo cria contas como ATIVO, mas o Javadoc diz PENDENTE e a GUI tem fluxo de aprovação · **Alto (decisão de projeto)**
**Onde:** [ControladorUtilizador.java:89-103](ProjetoGrafico/src/controlador/ControladorUtilizador.java#L89-L103) e [121-135](ProjetoGrafico/src/controlador/ControladorUtilizador.java#L121-L135).
O código regista Funcionário/Cliente com `EstadoUtilizador.ATIVO` (e o email diz "conta já ATIVA"), mas:
- o **Javadoc** desses métodos diz "com o estado **PENDENTE**";
- o painel do Gestor **"Ativar Contas"** ([PainelGestor.java](ProjetoGrafico/src/view/PainelGestor.java) → `obterContasPendentes()` → `WHERE U_ESTADO = 'PENDENTE' OR 'REJEITADO'`) **nunca mostra contas PENDENTE** (porque nenhuma é criada nesse estado);
- o ramo `EstadoUtilizador.PENDENTE` no [PainelLogin.java:113](ProjetoGrafico/src/view/PainelLogin.java#L113) ("Conta aguardando ativação pelo Gestor") torna-se **código morto**.
**Nota enunciado:** a aprovação manual (R21) é requisito **de Exame**, não da Periódica, logo a ativação automática é aceitável — mas então o fluxo de aprovação manual residual fica inconsistente. **Decidir:** ou (a) registar como PENDENTE e usar o painel "Ativar Contas"; ou (b) assumir ativação automática e remover/declarar o painel "Ativar Contas" e o ramo PENDENTE. Em qualquer caso, **corrigir o Javadoc**.

### B3 — Imagem de perfil por defeito (R2) não existe · **Médio**
**Onde:** [Utilitarios.java](ProjetoGrafico/src/view/Utilitarios.java) (criarPainelFoto / atualizarImagemPainel) usam `"fotos/geral.png"` como imagem genérica.
**Verificação:** a pasta `fotos/` e o ficheiro `fotos/geral.png` **não existem** no projeto. Quando um utilizador não tem foto, em vez da imagem genérica aparece o texto **"Sem Foto"**.
**Impacto no enunciado:** o R2 exige "caso o utilizador não defina a sua foto, deve existir uma imagem por defeito/genérica no sistema". **Falta incluir o ficheiro** `fotos/geral.png` (não é alteração de código — é adicionar o recurso).

### B4 — `verificarReparacoesAtrasadas` só memoriza a data quando há atrasos · **Baixo**
**Onde:** [ControladorReparacao.java:394-411](ProjetoGrafico/src/controlador/ControladorReparacao.java#L394-L411).
`ultimaVerificacaoAtrasos = dataHoje;` está **dentro** do `if (!atrasadas.isEmpty())`. Se não houver atrasos, a flag não é gravada e a consulta repete-se a cada login do gestor (ineficiência). Além disso a flag é `static` em memória, pelo que o "máximo uma vez por dia" só vale dentro da mesma execução.

---

## 2. Inconsistências (harmonização — R14)

### I1 — `alterarEstadoUtilizador` concatena o valor na SQL · **Baixo**
[UtilizadorDAO.java:424](ProjetoGrafico/src/repositorio/UtilizadorDAO.java#L424): `"... SET U_ESTADO = '" + estado + "' WHERE ..."`. É seguro (valor fixo "ATIVO"/"REJEITADO"), mas **inconsistente** com `atualizarEstadoUtilizador` ([linha 1016](ProjetoGrafico/src/repositorio/UtilizadorDAO.java#L1016)) que usa `SET U_ESTADO = ?`. O R14 pede a mesma abordagem em todo o lado.

### I2 — Nomes de colunas de telefone inconsistentes · **Baixo**
Cliente usa `NUM_TELEFONE_` (com underscore final, sem prefixo) — [UtilizadorDAO.java:64](ProjetoGrafico/src/repositorio/UtilizadorDAO.java#L64), [588](ProjetoGrafico/src/repositorio/UtilizadorDAO.java#L588) — enquanto Funcionário usa `F_NUM_TELEFONE` — [85](ProjetoGrafico/src/repositorio/UtilizadorDAO.java#L85), [660](ProjetoGrafico/src/repositorio/UtilizadorDAO.java#L660). Convenção de nomes incoerente no schema/queries.

### I3 — Métodos de consola não usados na GUI (código morto) · **Baixo**
[Validacoes.java:25-57](ProjetoGrafico/src/util/Validacoes.java#L25-L57): `lerInteiro(Scanner)` e `lerDouble(Scanner)` + `import java.util.Scanner` são restos da versão de consola. A GUI lê com `JTextField`/`Double.parseDouble`. Devem ser removidos.

### I4 — Exceções SQL silenciadas em todo o lado · **Baixo/Médio**
Praticamente todos os `catch (SQLException e) { }` dos DAOs estão **vazios** (ex.: [UtilizadorDAO.java:195](ProjetoGrafico/src/repositorio/UtilizadorDAO.java#L195), e dezenas de outros). Erros de base de dados ficam invisíveis (dificulta a depuração e mascara falhas). É consistente como padrão, mas é uma prática problemática — pelo menos um `e.printStackTrace()` ajudaria na defesa.

### I5 — Controladores duplicam instâncias em vez de reutilizar · **Baixo**
A `Aplicacao` cria os controladores e passa-os aos painéis, mas cada controlador volta a instanciar os seus (`new ControladorNotificacao()`, `new ControladorLog()`, `new XDAO()`) — ex.: [ControladorUtilizador.java:24-26](ProjetoGrafico/src/controlador/ControladorUtilizador.java#L24-L26), [ControladorReparacao.java:29-33](ProjetoGrafico/src/controlador/ControladorReparacao.java#L29-L33). Não é bug, mas multiplica objetos e é inconsistente com a ideia de controladores partilhados.

### I6 — Parâmetro `nome` não utilizado em `trocarConteudo` · **Baixo**
Nos 3 painéis ([PainelCliente](ProjetoGrafico/src/view/PainelCliente.java), [PainelFuncionario](ProjetoGrafico/src/view/PainelFuncionario.java), [PainelGestor](ProjetoGrafico/src/view/PainelGestor.java)) o método `trocarConteudo(JPanel novoPainel, String nome)` recebe `nome` mas nunca o usa.

### I7 — Javadoc desatualizado · **Baixo**
- `registarFuncionario`/`registarCliente` dizem "estado PENDENTE" mas criam ATIVO (ver B2).
- O cabeçalho de [ControladorReparacao.java](ProjetoGrafico/src/controlador/ControladorReparacao.java) refere "gestão de peças, testes de operacionalidade" — existem na classe mas **não estão expostos na GUI** (R12); convém esclarecer no Javadoc.

### I8 — (Assumido) Genéricos nas componentes Swing · **Informativo**
Os componentes usam genéricos (`JComboBox<String>`, `JList<String>`, `Class<?>`) enquanto os slides usam tipos crus. Foi uma **opção deliberada** (evitar avisos *unchecked*). Ver detalhe em [Analysis Results 20.md](Analysis Results 20.md). Não é bug.

### I9 — Pesquisa de equipamentos do Gestor só por Marca · **Baixo**
[PainelGestor.java](ProjetoGrafico/src/view/PainelGestor.java) (mostrarListagens, opção "Pesquisar Equipamentos") chama `cEquipamento.pesquisarEquipamentos(1, termo)` com o critério fixo em `1` (Marca). Não permite pesquisar por modelo/SKU. Limitação, não erro.

---

## 3. Verificação de Requisitos do Enunciado (R1–R14)

| Req. | Descrição (resumida) | Estado | Observações |
|---|---|---|---|
| R1 | Completar/corrigir a app do projeto 1 | ✅ Implementado | GUI sobre a base existente |
| R2 | Foto de utilizador (inserir/alterar) + imagem por defeito | ⚠️ Parcial | Upload/alteração OK; **falta o ficheiro `fotos/geral.png`** (B3) |
| R3 | Email a confirmar o registo | ✅ Implementado | `ServicoEmail` chamado no registo (cliente e funcionário) |
| R4 | Campo de observações nos formulários de inserção | ✅ Implementado | Registo, inserir equipamento, pedir reparação, concluir reparação |
| R5 | Realce/diálogo de notificações após login | ✅ Implementado | `JOptionPane` no login com nº de não lidas |
| R6 | Imprimir extrato das ações de um processo (datas e utilizadores) | ✅ Implementado | "Histórico de Ações" (cliente/funcionário + datas) no extrato |
| R7 | Listagens com barras de deslocamento | ✅ Implementado | Tabelas em `JScrollPane` |
| R8 | Toda a interação via interface gráfica | ✅ Implementado | Java Swing |
| R9 | Componentes adequadas (JList/JTable) | ✅ Implementado | `JTable` nas listagens, `JList` no estado |
| R10 | Ajuda contextual (tooltips) | ✅ Implementado | `setToolTipText` nos campos/botões |
| R11 | Não mostrar boas-vindas/estatísticas | ✅ Conforme | Sem estatísticas; `info_sistema.dat` não usado na GUI |
| R12 | Não implementar peças/testes graficamente | ✅ Conforme | Lógica existe na camada de negócio, não exposta |
| R13 | Gestores não criam gestores pela GUI | ✅ Conforme | Apenas criação do 1.º gestor no arranque |
| R14 | Código harmonizado/consistente | ⚠️ A rever | Ver inconsistências I1–I7 |

**Conclusão de requisitos:** todos os requisitos aplicáveis estão implementados, **exceto o R2 que está incompleto** por falta da imagem genérica (`fotos/geral.png`). O R14 (harmonização) tem pontos a limpar.

---

## 4. Alinhamento com a matéria (Cap. 06.0–06.4)

A parte gráfica já foi analisada em detalhe no [Analysis Results 20.md](Analysis Results 20.md) e está **alinhada** com os capítulos: `BorderLayout`/`GridLayout`/`FlowLayout`, troca de painéis (`remove/add/revalidate/repaint`), `JOptionPane`, `JFileChooser`, `JTable`/`DefaultTableModel`, `JList`, `JScrollPane`, `TitledBorder`, impressão `Printable`, menus, tooltips.

Único desvio consciente face aos slides: **genéricos** nas componentes Swing (os slides usam tipos crus) — mantido para o projeto não ter avisos *unchecked* (I8). O FlatLaf está nos slides (Cap. 06.4), logo é conforme.

---

## 5. Resumo / Prioridades sugeridas

| # | Item | Severidade | Tipo |
|---|---|---|---|
| B2 | Registo ATIVO vs fluxo de aprovação/Javadoc | Alto (decisão) | Bug/coerência |
| B1 | Pesquisa equip. cliente "Modelo" filtra por SKU | Médio | Bug |
| B3 | Falta `fotos/geral.png` (R2) | Médio | Requisito/recurso |
| B4 | Flag de atrasos só grava com atrasos | Baixo | Bug menor |
| I1–I7 | Harmonização (SQL, código morto, exceções, Javadoc, params) | Baixo | R14 |
| I9 | Pesquisa equip. do gestor só por marca | Baixo | Limitação |

**Nada disto impede a aplicação de executar** (compila e corre). Os pontos com maior impacto na avaliação são **B2** (coerência do fluxo de contas + Javadoc), **B1** (resultado de pesquisa errado), **B3** (R2 incompleto) e a **harmonização R14** (I1–I7).

---

*Documento gerado apenas para análise. Diz quais destes pontos queres que corrija e eu altero o código.*
