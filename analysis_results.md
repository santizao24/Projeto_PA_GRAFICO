# Análise da Interface Gráfica — Requisitos do Enunciado + Inconsistências

## 📊 Estado Geral dos Requisitos (GUI)

| Req. | Descrição | Estado | Notas |
|------|-----------|--------|-------|
| **R1** | Completar e corrigir aplicação | ✅ OK | Toda a lógica Model/Controller/DAO mantida |
| **R2** | Foto de utilizador | ✅ OK | `JFileChooser` + `ImageIcon` + `geral.png` default |
| **R3** | Email no registo (grupo) | ✅ OK | `javax.mail` via `ServicoEmail` em Thread separada |
| **R4** | Campo observações nos formulários | ✅ OK | Presente nos formulários. Implementado envio para os logs. |
| **R5** | Notificações em destaque após login | ✅ OK | `JOptionPane.WARNING_MESSAGE` com contagem |
| **R6** | Extracto de ações + Impressão (grupo) | ❌ **EM FALTA** | Não existe `DialogoExtracto.java` nem `PrinterJob` |
| **R7** | Barras de deslocamento (scroll) | ✅ OK | Todas as tabelas e listas estão dentro de `JScrollPane` |
| **R8** | Toda a interação via JavaSwing | ✅ OK | `JFrame` + `JPanel` + menus + tabelas + diálogos |
| **R9** | Componentes adequadas (JList vs JTable) | ✅ OK | `JList` usada na funcionalidade "Estado das Minhas Reparações" |
| **R10** | Tooltips em todos os elementos | ✅ OK | Todos os botões e campos têm `setToolTipText()` |
| **R11** | SEM boas-vindas/estatísticas na GUI | ✅ OK | Não existe |
| **R12** | SEM peças/testes na GUI | ✅ OK | Apenas operações internas |
| **R13** | SEM criar gestores na GUI | ✅ OK | Apenas 1º gestor no arranque |
| **R14** | Código harmonizado | ✅ OK | Inconsistências identificadas foram corrigidas |

---

## ❌ Funcionalidades em Falta Total

### 1. R6 — Extracto das Ações + Impressão (REQUISITO DE GRUPO)

> [!CAUTION]
> Este é um **requisito de grupo** obrigatório e está completamente por implementar!

**O que falta:**
- Ficheiro [DialogoExtracto.java](file:///c:/Users/HP/OneDrive%20-%20ESTGOH/Ambiente%20de%20Trabalho/Projeto_PA_GRAFICO/ProjetoGrafico/src/view/DialogoExtracto.java) — **NÃO EXISTE**
- Adicionar botões "Extracto" nos painéis (e.g. Perfil ou Tabela de Ações).
- `PrinterJob` não é usado em nenhum ficheiro do projeto
- O padrão do professor: `implements Printable` + `PrinterJob` + `Graphics2D` + `g.drawString()`

---

## ✅ Inconsistências Resolvidas

### I1. Conversão de Reparações
✅ **Resolvido:** O `PainelCliente` agora utiliza um método centralizado `converterReparacoes()` igual ao Funcionário e ao Gestor, removendo a duplicação `inline`.

### I2/I3/I4/I5. Javadoc Ausente ou Inconsistente
✅ **Resolvido:** Javadoc adicionado a `setUtilizadorLogado`, métodos `getControlador*` na `AplicacaoGUI`, `trocarConteudo` e `converterReparacoes` em todos os painéis.

### I6. Formulário de edição do Gestor (Utilitarios.criarCampoFormulario)
✅ **Resolvido:** O `mostrarEditarUsers()` no `PainelGestor` foi atualizado para usar `Utilitarios.criarCampoFormulario()` de modo a ser visualmente coerente com os restantes formulários.

### I7. Mensagem "Nome: ... (não editável)"
✅ **Resolvido:** Uniformizado em `PainelFuncionario` e `PainelCliente`.

### I8. Mensagem de remoção
✅ **Resolvido:** Todos os painéis usam agora a mensagem de confirmação detalhada de 3 linhas avisando sobre o apagamento irreversível.

### I9. Mensagem de sucesso após marcar notificações
✅ **Resolvido:** Todos os painéis usam "Notificações marcadas como lidas!".

### I10. Validação de telefone no perfil
✅ **Resolvido:** Adicionada validação de 9 dígitos ao atualizar o perfil no `PainelFuncionario`.

### I11. Título do TitledBorder nas Notificações
✅ **Resolvido:** Atualizado para "As Minhas Notificações" / "Dashboard de Notificações" de forma coerente.

### I12. Notificações do Cliente/Funcionário (Filtro)
✅ **Informação:** Mantido o comportamento atual, uma vez que o cliente tem menos fluxo de notificações que o gestor, sendo o filtro desnecessário na sua interface.

### I13 / R4. Campo de observações nos formulários
✅ **Resolvido:** O conteúdo de `observacoes` agora é devidamente passado dos formulários de *Registo* e *Inserção de Equipamentos* para os respetivos controladores e gravado nos Logs do Gestor, não sendo "texto morto" na interface gráfica.

### I14. Gestor — Mensagens de erro genéricas
✅ **Resolvido:** Alterado de "Selecione!" para "Selecione uma conta!", e as mensagens de erro em `EditarUsers` agora são descritivas ("Email ou Username pode já estar em uso").

---

## 📋 Resumo de Prioridades Restantes

### 🔴 Crítico (obrigatório para entrega)
1. **R6 — DialogoExtracto.java** + botão "Extracto" nos painéis + `PrinterJob` com padrão do professor
