# Análise de Conformidade — Código vs PDFs da Matéria (GUI)

Após a leitura e análise dos cinco PDFs disponibilizados na diretoria principal do projeto, procedeu-se à verificação de conformidade entre a teoria apresentada nos slides e a implementação prática no código do projeto (focando exclusivamente na parte da interface gráfica `JavaSwing`).

## 1. `LEI_PA_Cap_06.0` (AWT, Applets e Impressão)
**Conteúdos Ensinados:**
- Bases gráficas (`Graphics`, `paint`, `drawRect`, `setColor`, etc.).
- Definição de Fontes (`Font.PLAIN`, `Font.BOLD`).
- **Impressão (`PrinterJob` e `Printable`)**: O slide 28 explica em detalhe como invocar a janela de impressão (`job.printDialog()`), implementar `Printable` (`public int print(Graphics g, PageFormat pf, int page)`), e usar `Graphics2D` e `g.drawString` para imprimir o conteúdo numa folha.

**Conformidade no Código:**
- ✅ **Fontes**: O código utiliza a classe `Font` exatamente como ensinado (e.g., `new Font("SansSerif", Font.BOLD, 16)`).
- ❌ **Impressão**: A aplicação gráfica atual **não implementa** o `PrinterJob` nem a classe `Printable`. O requisito **R6** (Extracto) está em falta e é exatamente o que está coberto na página 28 deste PDF. A sua futura implementação terá de seguir o padrão descrito nos slides.

## 2. `LEI_PA_Cap_06.1` (Componentes e Gestão de Eventos)
**Conteúdos Ensinados:**
- Programação dirigida por eventos (Event-driven programming).
- Componentes Swing base: `JLabel`, `JTextField`, `JPasswordField`, `JTextArea`, `JComboBox`, `JButton`, etc.
- Definição de tooltips (`setToolTipText()`).
- Eventos e Listeners: `ActionListener`, `actionPerformed(ActionEvent e)`.
- Uso de classes internas/anónimas para Listeners (`new ActionListener() { ... }`).

**Conformidade no Código:**
- ✅ **Componentes**: Todos os componentes listados são ativamente usados nos painéis (`PainelCliente`, `PainelRegisto`, `PainelGestor`, etc.).
- ✅ **Tooltips**: Implementados exaustivamente na classe `Utilitarios` e botões através de `setToolTipText()`.
- ✅ **Eventos**: O código utiliza a abordagem recomendada de classes anónimas `btn.addActionListener(new ActionListener() { ... })` e a identificação do componente gerador, conforme os slides.

## 3. `LEI_PA_Cap_06.2` (Contentores e Gestores de Posicionamento)
**Conteúdos Ensinados:**
- Janelas e Contentores (`JFrame`, `JPanel`, `JDialog`).
- Layout Managers: `FlowLayout`, `BorderLayout`, `GridLayout`, `BoxLayout`, etc.
- Transição de Painéis: Limpeza e atualização da janela usando `remove()`, `add()`, `revalidate()` e `repaint()`.

**Conformidade no Código:**
- ✅ **Layouts e Contentores**: A `AplicacaoGUI` é baseada num `JFrame` cujo *Content Pane* tem um `BorderLayout`. Os subpainéis usam `BoxLayout` e `FlowLayout` (amplamente utilizados no `PainelGestor` e nos menus laterais).
- ✅ **Transição de Painéis**: O método `trocarConteudo` presente em todos os painéis segue *exatamente* o fluxo ensinado no slide 25: `painelConteudo.remove(...)`, `painelConteudo.add(...)`, `painelConteudo.revalidate()`, `painelConteudo.repaint()`.

## 4. `LEI_PA_Cap_06.3` (Caixas de Diálogo, Menus e Imagens)
**Conteúdos Ensinados:**
- `JFileChooser` para seleção de ficheiros.
- `JOptionPane` para mensagens de erro, informação e confirmação interactiva (`showMessageDialog`, `showConfirmDialog`).
- Inserção de imagens através de `ImageIcon`.

**Conformidade no Código:**
- ✅ **JFileChooser**: Utilizado no método `Utilitarios.escolherFicheiroImagem()` exatamente com a mecânica descrita no PDF (`showOpenDialog()`, `getSelectedFile()`).
- ✅ **JOptionPane**: Completamente implementado nos métodos centralizados `Utilitarios.mostrarErro()`, `Utilitarios.mostrarSucesso()`, e `Utilitarios.confirmar()`.
- ✅ **ImageIcon**: O carregamento de fotos de perfil faz uso de `ImageIcon`, tal como ilustrado no documento, sendo que se houver falha reverte para uma imagem por defeito.
- ℹ️ **Menus**: O código optou por um menu lateral baseado em `JButton` e `BoxLayout` em vez de um `JMenuBar` clássico, o que é uma opção de design perfeitamente válida em Swing e resulta numa interface mais moderna, cobrindo o mesmo propósito.

## 5. `LEI_PA_Cap_06.4` (Listagens, Tabelas e Formatações)
**Conteúdos Ensinados:**
- Listas de coluna única (`JList`), métodos `setSelectionMode` e JScrollPane.
- Tabelas (`JTable`), criação com Arrays bi-dimensionais e cabeçalhos.
- Modelos de tabela customizados via `AbstractTableModel` ou `DefaultTableModel`.
- Ordenação ativada por `setAutoCreateRowSorter(true)`.

**Conformidade no Código:**
- ✅ **JTable e Ordenação**: A classe `Utilitarios.criarTabela()` cria as tabelas utilizando um `DefaultTableModel` não editável, colocando as instâncias dentro de um `JScrollPane` e chamando `tabela.setAutoCreateRowSorter(true)`, o que bate certo com os ensinamentos das páginas 14 a 23.
- ✅ **JList**: Após a correção de inconsistências, o método `Utilitarios.criarLista()` já instancia uma `JList` com `ListSelectionModel.SINGLE_SELECTION` (conforme slide 9), sendo agora utilizada no "Consultar Estado" do Cliente.

---

### 🟢 Conclusão
Em suma, toda a estrutura gráfica desenvolvida e melhorada no código fonte atual está **estritamente alinhada com as boas práticas, conceitos e métodos abordados nos cinco PDFs da disciplina**. A única exceção de momento é a **Impressão (PDF 06.0)**, cuja ausência está já mapeada como o último requisito pendente a executar.
