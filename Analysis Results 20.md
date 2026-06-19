# Analysis Results 20
## Análise do Ambiente Gráfico (Java Swing) vs. Materiais da Disciplina

**Âmbito:** apenas os ficheiros de `ProjetoGrafico/src/view/`
**Base de comparação (exclusiva):** LEI_PA_Cap_06.0, 06.1, 06.2, 06.3, 06.4
**Critério:** marcar tudo o que não aparece, não é explicado, não é usado nos exemplos, ou é mais avançado do que o apresentado nos slides. Sempre que houver mais do que uma forma, considera-se correta a mais simples e mais próxima dos exemplos do professor.

Ficheiros analisados:
- [Aplicacao.java](ProjetoGrafico/src/view/Aplicacao.java)
- [Utilitarios.java](ProjetoGrafico/src/view/Utilitarios.java)
- [PainelLogin.java](ProjetoGrafico/src/view/PainelLogin.java)
- [PainelRegisto.java](ProjetoGrafico/src/view/PainelRegisto.java)
- [DialogoConfigBD.java](ProjetoGrafico/src/view/DialogoConfigBD.java)
- [PainelCliente.java](ProjetoGrafico/src/view/PainelCliente.java)
- [PainelFuncionario.java](ProjetoGrafico/src/view/PainelFuncionario.java)
- [PainelGestor.java](ProjetoGrafico/src/view/PainelGestor.java)
- [ExtractoReparacao.java](ProjetoGrafico/src/view/ExtractoReparacao.java)

---

## 0. Resumo executivo

O projeto está, na sua **arquitetura geral**, muito alinhado com os materiais:
- `JFrame` principal + `JPanel` trocados por `remove/add/revalidate/repaint` (Cap. 06.2).
- `JMenuBar`/`JMenu`/`JMenuItem` (Cap. 06.3).
- `JOptionPane` (mensagem, confirmação, e formulário via `Object[]`) (Cap. 06.3).
- `JFileChooser` (Cap. 06.3).
- `JTable` com `DefaultTableModel`, `isCellEditable`, `getColumnClass`, `setAutoCreateRowSorter`, `setSelectionMode` (Cap. 06.4).
- `JList`, `JScrollPane`, `TitledBorder`, `setToolTipText`, `setPreferredSize` (Cap. 06.1/06.4).
- Impressão com `Printable`/`PrinterJob`/`Graphics2D`/`translate`/`drawString` (Cap. 06.0).

As diferenças encontradas são, na maioria, **pequenas modernizações de sintaxe** (genéricos, `@Override`) e **dois ou três idiomas/configurações** não demonstrados nos slides. Abaixo estão **todas** as diferenças, mesmo as mínimas, ordenadas por ficheiro, com o nível de risco.

Contagem rápida de risco:
- **Muito Alto:** 0
- **Alto:** 1 (cópia de ficheiros por streams dentro da view)
- **Médio:** 4
- **Baixo:** 12

---

## 1. Look & Feel — FlatLaf / FlatDarculaLaf

**Onde:** arranque da aplicação.
**Ficheiro:** [Aplicacao.java](ProjetoGrafico/src/view/Aplicacao.java) — linhas 9 e 52.
**Código exato:**
```java
import com.formdev.flatlaf.FlatDarculaLaf;
...
FlatDarculaLaf.setup();
```
**Aparece nos capítulos?** Sim.
**Em que capítulo?** Cap. 06.4 (slide 10), que mostra literalmente `import com.formdev.flatlaf.FlatDarculaLaf;` e `FlatDarculaLaf.setup();`.
**Porque pode parecer acima do nível:** apesar de estar nos slides, o FlatLaf é apresentado como template **externo opcional** (requer importar um `.jar`), enquanto o template assumido como base/“por defeito” nos materiais é o **Metal/CrossPlatform** (`UIManager.getCrossPlatformLookAndFeelClassName()`). É a escolha estética mais “profissional” do projeto e a única dependência externa.
**Parâmetros realmente necessários:** nenhum L&F é obrigatório para cumprir requisitos.
**O que pode ser removido:** a dependência externa FlatLaf, se se quiser ficar 100% dentro da biblioteca-padrão.
**Versão simplificada compatível com os exemplos do professor (Cap. 06.4, slide 8):**
```java
try {
    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
} catch (Exception e) { e.printStackTrace(); }
```
ou simplesmente **remover** a linha (a aplicação fica com o L&F por defeito).
**Risco:** **Baixo** (está nos slides, mas é a opção mais avançada).

---

## 2. Genéricos em componentes Swing (`JComboBox<String>`, `JList<String>`, `JComboBox<>`, `Class<?>`)

**Onde:** todos os painéis e a classe utilitária.
**Ficheiros / exemplos:**
- [PainelRegisto.java:28](ProjetoGrafico/src/view/PainelRegisto.java#L28) `private JComboBox<String> comboTipo;`
- [PainelRegisto.java:85](ProjetoGrafico/src/view/PainelRegisto.java#L85) `comboTipo = new JComboBox<>(new String[] { "Funcionário", "Cliente" });`
- [Utilitarios.java:56](ProjetoGrafico/src/view/Utilitarios.java#L56) `public static javax.swing.JList<String> criarLista(...)`
- [Utilitarios.java:37](ProjetoGrafico/src/view/Utilitarios.java#L37) `public Class<?> getColumnClass(int columnIndex)`
- [PainelCliente.java:415](ProjetoGrafico/src/view/PainelCliente.java#L415), [PainelGestor.java:260](ProjetoGrafico/src/view/PainelGestor.java#L260), etc.

**Código exato (representativo):**
```java
JComboBox<String> comboCrit = new JComboBox<>(new String[] { "Marca", "Código de Modelo" });
JList<String> lista = new javax.swing.JList<>(itens);
```
**Aparece nos capítulos?** Não (na forma com genéricos).
**Em que capítulo?** Os slides usam sempre **tipos crus (raw types)**: `JComboBox myComboBox = new JComboBox(arrayWithData[])` (Cap. 06.1, slide 26) e `JList list = new JList(data)` (Cap. 06.4, slide 13). O `getColumnClass` nos slides é `public Class getColumnClass(int c)` (Cap. 06.4, slide 34), sem `<?>`.
**Porque pode parecer acima do nível:** o uso de genéricos parametrizados em componentes Swing e o operador diamante `<>` são uma forma mais moderna do que a apresentada.
**Parâmetros realmente necessários:** o array de dados.
**O que pode ser removido:** os parâmetros de tipo `<String>` e `<>`, e o wildcard `<?>`.
**Versão simplificada compatível com os exemplos do professor:**
```java
JComboBox comboCrit = new JComboBox(new String[] { "Marca", "Código de Modelo" });
JList lista = new JList(itens);
public Class getColumnClass(int columnIndex) { ... }
```
**Risco:** **Baixo-Médio** (modernização visível e transversal a quase todos os ficheiros).

---

## 3. Truque do array `final` de tamanho 1 para aceder a componente dentro de classe anónima

**Onde:** painéis de perfil (Cliente, Funcionário, Gestor).
**Ficheiros:**
- [PainelCliente.java:267-268](ProjetoGrafico/src/view/PainelCliente.java#L267-L268)
- [PainelFuncionario.java:269-270](ProjetoGrafico/src/view/PainelFuncionario.java#L269-L270)
- [PainelGestor.java:847-848](ProjetoGrafico/src/view/PainelGestor.java#L847-L848)

**Código exato:**
```java
final JPanel[] painelFotoRef = new JPanel[1];
painelFotoRef[0] = Utilitarios.criarPainelFoto(utilizadorLogado.getFotoPath(), new ActionListener() {
    public void actionPerformed(ActionEvent ev) {
        ...
        Utilitarios.atualizarImagemPainel(painelFotoRef[0], novaFoto);
    }
});
```
**Aparece nos capítulos?** Não.
**Em que capítulo?** Nenhum. As classes anónimas aparecem (Cap. 06.1, slide 49), mas **sem** este idioma de “array final de 1 posição” para contornar a captura de variáveis `final`.
**Porque pode parecer acima do nível:** é um *workaround* idiomático avançado (capturar/mutar uma referência dentro de uma classe anónima) que não é ensinado.
**Parâmetros realmente necessários:** apenas a referência ao painel da foto.
**O que pode ser removido:** o array; substituir por um **campo da classe** (atributo `private JPanel painelFoto;`), exatamente como já é feito em [PainelRegisto.java:31](ProjetoGrafico/src/view/PainelRegisto.java#L31) (`private JPanel painelFoto;`).
**Versão simplificada compatível:**
```java
// como atributo da classe:
private JPanel painelFoto;
...
painelFoto = Utilitarios.criarPainelFoto(utilizadorLogado.getFotoPath(), new ActionListener() {
    public void actionPerformed(ActionEvent ev) {
        Utilitarios.atualizarImagemPainel(painelFoto, novaFoto);
    }
});
```
**Risco:** **Médio** (idioma não trivial, claramente acima do que foi lecionado, e inconsistente com o próprio projeto que noutro sítio usa atributo).

---

## 4. Cópia de ficheiros com `FileInputStream`/`FileOutputStream` e buffer dentro da camada de interface

**Onde:** escolha da foto de perfil.
**Ficheiro:** [Utilitarios.java:243-278](ProjetoGrafico/src/view/Utilitarios.java#L243-L278).
**Código exato (excerto):**
```java
FileInputStream fis = new FileInputStream(ficheiroOrigem);
FileOutputStream fos = new FileOutputStream(ficheiroDestino);
byte[] buffer = new byte[1024];
int bytesLidos;
while ((bytesLidos = fis.read(buffer)) > 0) {
    fos.write(buffer, 0, bytesLidos);
}
```
**Aparece nos capítulos?** Não.
**Em que capítulo?** Nenhum dos 5 capítulos de Interfaces Gráficos. O `JFileChooser` em si está no Cap. 06.3 (slides 6-11), mas a **lógica de cópia de ficheiros por streams com buffer** é matéria de I/O, não de GUI, e não consta destes capítulos.
**Porque pode parecer acima do nível (no contexto gráfico):** mistura I/O de baixo nível na camada `view`; os capítulos de GUI só mostram **abrir** o `JFileChooser` e obter o `File` (`getSelectedFile()`), nunca copiar conteúdos.
**Parâmetros realmente necessários (do ponto de vista GUI):** apenas o `JFileChooser` + `getSelectedFile()`.
**O que pode ser removido:** o bloco de cópia por streams pode sair da `view` (mover para uma classe utilitária de ficheiros, como já existe `util.GestorFicheiros` usado em [PainelRegisto.java:294](ProjetoGrafico/src/view/PainelRegisto.java#L294)).
**Versão simplificada compatível com os exemplos do professor (Cap. 06.3, slide 11):**
```java
JFileChooser fc = new JFileChooser();
fc.setDialogTitle("Escolher Foto de Perfil");
if (fc.showOpenDialog(pai) == JFileChooser.APPROVE_OPTION) {
    File ficheiro = fc.getSelectedFile();
    // delegar a cópia para fora da view
}
```
**Risco:** **Alto** — é o ponto onde a `view` mais se afasta dos exemplos do professor (I/O com buffer não pertence a estes capítulos e nem é necessário filtrar a lógica de cópia aqui).

---

## 5. `GridBagLayout` usado apenas como “centralizador” (sem `GridBagConstraints`)

**Onde:** centragem do bloco de login.
**Ficheiro:** [PainelLogin.java:74-76](ProjetoGrafico/src/view/PainelLogin.java#L74-L76).
**Código exato:**
```java
JPanel painelCentral = new JPanel(new GridBagLayout());
painelCentral.add(painelCabecalho);
add(painelCentral, BorderLayout.CENTER);
```
**Aparece nos capítulos?** Parcialmente.
**Em que capítulo?** Cap. 06.2 (slides 10-11 e 17) **menciona** o `GridBagLayout` como um dos 6 gestores, mas descreve-o como grelha bidimensional com **conjunto de restrições** — e **não há nenhum exemplo de código** dele nos slides.
**Porque pode parecer acima do nível:** usar `GridBagLayout` como truque para centrar um único painel (sem `GridBagConstraints`) é um idioma comum mas não demonstrado; o slide associa este layout a “restrições”, sugerindo complexidade.
**Parâmetros realmente necessários:** centrar o formulário (objetivo estético, não requisito).
**O que pode ser removido:** o `GridBagLayout`.
**Versão simplificada compatível com os exemplos do professor (Cap. 06.2, slide 14):**
```java
JPanel painelCentral = new JPanel(new FlowLayout());
painelCentral.add(painelCabecalho);
add(painelCentral, BorderLayout.CENTER);
```
**Risco:** **Médio** (layout mencionado mas nunca exemplificado; o uso é “só para centrar”).

---

## 6. `BoxLayout` usado de forma intensiva (apenas descrito, nunca exemplificado em código nos slides)

**Onde:** menus laterais e formulários verticais.
**Ficheiros/exemplos:**
- [PainelCliente.java:60](ProjetoGrafico/src/view/PainelCliente.java#L60) `painelMenu.setLayout(new BoxLayout(painelMenu, BoxLayout.Y_AXIS));`
- [Utilitarios.java:202](ProjetoGrafico/src/view/Utilitarios.java#L202)
- [PainelRegisto.java:65](ProjetoGrafico/src/view/PainelRegisto.java#L65), [DialogoConfigBD.java:39](ProjetoGrafico/src/view/DialogoConfigBD.java#L39), [PainelFuncionario.java:57](ProjetoGrafico/src/view/PainelFuncionario.java#L57), [PainelGestor.java:61](ProjetoGrafico/src/view/PainelGestor.java#L61)

**Código exato (representativo):**
```java
painelMenu.setLayout(new BoxLayout(painelMenu, BoxLayout.Y_AXIS));
```
**Aparece nos capítulos?** Parcialmente.
**Em que capítulo?** Cap. 06.2 (slides 10-11 e 19) lista o `BoxLayout` e indica as variáveis `X_AXIS`/`Y_AXIS`/`LINE_AXIS`/`PAGE_AXIS`, **mas não apresenta exemplo de código**. Os exemplos completos do professor usam `FlowLayout`, `BorderLayout` e `GridLayout` (Cap. 06.2, slide 22).
**Porque pode parecer acima do nível:** está descrito mas não exemplificado; o construtor `new BoxLayout(container, eixo)` (que exige passar o próprio container) é menos óbvio do que os layouts demonstrados.
**Parâmetros realmente necessários:** dispor os botões/campos em coluna.
**O que pode ser removido:** substituível pelo `GridLayout` (uma coluna), efetivamente demonstrado nos slides.
**Versão simplificada compatível (Cap. 06.2, slide 16 — `GridLayout`):**
```java
JPanel painelMenu = new JPanel(new GridLayout(0, 1)); // 1 coluna, n linhas
```
**Risco:** **Baixo** (é referido nos slides; só não há exemplo de código).

---

## 7. Fonte `"SansSerif"` na impressão (fora da lista de fontes dos slides)

**Onde:** extrato de reparação impresso.
**Ficheiro:** [ExtractoReparacao.java](ProjetoGrafico/src/view/ExtractoReparacao.java) — linhas 58, 65, 69, 77, 81, 91, 95, 101, 108, 112, 121.
**Código exato:**
```java
g.setFont(new Font("SansSerif", Font.BOLD, 18));
g.setFont(new Font("SansSerif", Font.PLAIN, 11));
g.setFont(new Font("SansSerif", Font.ITALIC, 9));
```
**Aparece nos capítulos?** Não (o nome `"SansSerif"`).
**Em que capítulo?** A classe `Font` e a sintaxe `new Font(nome, estilo, tamanho)` estão no Cap. 06.0 (slides 24-25) e Cap. 06.1 (slides 31-32), mas a **lista de fontes apresentada** é: `TimesRoman`, `Courier`, `Terminal`, `Helvetica`, `Dialog`, `Symbol`. `"SansSerif"` não consta.
**Porque pode parecer acima do nível:** usa um nome de fonte fora do conjunto ensinado.
**Parâmetros realmente necessários:** o estilo (`Font.BOLD/PLAIN/ITALIC`) e o tamanho — esses estão corretos.
**O que pode ser removido:** o nome `"SansSerif"`, trocando por um dos nomes dos slides.
**Versão simplificada compatível (Cap. 06.0, slide 26):**
```java
g.setFont(new Font("Helvetica", Font.BOLD, 18));
```
**Risco:** **Baixo**.

---

## 8. `@Override` sistemático em métodos de listeners e modelos

**Onde:** praticamente todos os `actionPerformed`, `isCellEditable`, `getColumnClass`.
**Ficheiros:** todos os painéis (ex.: [PainelCliente.java:125-126](ProjetoGrafico/src/view/PainelCliente.java#L125-L126), [Utilitarios.java:31](ProjetoGrafico/src/view/Utilitarios.java#L31)).
**Código exato:**
```java
@Override
public void actionPerformed(ActionEvent e) { ... }
```
**Aparece nos capítulos?** Quase nunca.
**Em que capítulo?** Os exemplos dos slides escrevem os métodos **sem** anotação `@Override` (Cap. 06.1, slides 47-49). A anotação só surge **uma vez** no Cap. 06.4 (slide 36, no `getColumnClass`).
**Porque pode parecer acima do nível:** uso consistente de uma boa prática que os exemplos do professor praticamente não usam.
**Parâmetros realmente necessários:** nenhum (a anotação é opcional).
**O que pode ser removido:** todas as `@Override` (sem efeito funcional).
**Versão simplificada compatível:**
```java
public void actionPerformed(ActionEvent e) { ... }
```
**Risco:** **Baixo**.

---

## 9. `getSelectedIndex()` numa `JComboBox` para mapear critérios de pesquisa

**Onde:** pesquisas em vários painéis.
**Ficheiros:** [PainelCliente.java:435](ProjetoGrafico/src/view/PainelCliente.java#L435), [PainelCliente.java:510](ProjetoGrafico/src/view/PainelCliente.java#L510), [PainelFuncionario.java:389](ProjetoGrafico/src/view/PainelFuncionario.java#L389).
**Código exato:**
```java
cReparacao.pesquisarReparacoesCliente(..., comboCrit.getSelectedIndex() + 1, ...);
```
**Aparece nos capítulos?** Parcialmente.
**Em que capítulo?** Para `JComboBox` os slides mostram `getSelectedItem()` (Cap. 06.1, slide 26). O `getSelectedIndex()` é apresentado para **`JList`** (Cap. 06.4, slide 13), não para a combo box.
**Porque pode parecer acima do nível:** aplicar `getSelectedIndex()` à combo box é uma extrapolação (correta, mas não demonstrada para este componente).
**Parâmetros realmente necessários:** saber qual a opção escolhida.
**O que pode ser removido:** o `getSelectedIndex()` pode dar lugar a `getSelectedItem()` + comparação de String, como nos slides.
**Versão simplificada compatível (Cap. 06.1, slide 26):**
```java
String crit = (String) comboCrit.getSelectedItem();
if (crit.equals("Número da Reparação")) { ... }
```
**Risco:** **Baixo**.

---

## 10. Redimensionamento de imagem com `getScaledInstance(..., Image.SCALE_SMOOTH)`

**Onde:** miniatura da foto de perfil.
**Ficheiro:** [Utilitarios.java:216](ProjetoGrafico/src/view/Utilitarios.java#L216) e [Utilitarios.java:299](ProjetoGrafico/src/view/Utilitarios.java#L299).
**Código exato:**
```java
Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
```
**Aparece nos capítulos?** Sim.
**Em que capítulo?** Cap. 06.3 (slide 34): `image.getScaledInstance(100,100,Image.SCALE_DEFAULT)`. O slide menciona explicitamente `SCALE_SMOOTH` como alternativa que **privilegia qualidade em detrimento da velocidade**, indicando `SCALE_DEFAULT` como “o mais comum”.
**Porque pode parecer acima do nível:** escolheu-se a variante de maior qualidade em vez da “mais comum” mostrada no exemplo.
**Parâmetros realmente necessários:** largura, altura e um algoritmo de escala.
**O que pode ser removido:** trocar `SCALE_SMOOTH` por `SCALE_DEFAULT` para coincidir com o exemplo principal.
**Versão simplificada compatível (Cap. 06.3, slide 34):**
```java
Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
```
**Risco:** **Baixo** (está nos slides; é só a variante menos “comum”).

---

## 11. `setMaximumSize(...)` em botões e painéis de formulário

**Onde:** botões de menu e `criarCampoFormulario`.
**Ficheiros:** [PainelCliente.java:115](ProjetoGrafico/src/view/PainelCliente.java#L115), [PainelFuncionario.java:107](ProjetoGrafico/src/view/PainelFuncionario.java#L107), [PainelGestor.java:116](ProjetoGrafico/src/view/PainelGestor.java#L116), [Utilitarios.java:108](ProjetoGrafico/src/view/Utilitarios.java#L108).
**Código exato:**
```java
btn.setMaximumSize(new Dimension(200, 30));
painel.setMaximumSize(new Dimension(400, 30));
```
**Aparece nos capítulos?** Sim.
**Em que capítulo?** Cap. 06.4 (slide 62) menciona `setMinimumSize()` e `setMaximumSize()` como métodos para definir tamanhos mínimo/máximo. (`setPreferredSize` é o realçado nos slides.)
**Porque pode parecer acima do nível:** o `setMaximumSize` é necessário sobretudo por causa do `BoxLayout` (ver ponto 6); ao simplificar o layout, estes ajustes deixam de ser precisos.
**Parâmetros realmente necessários:** tamanho preferido do componente (`setPreferredSize`).
**O que pode ser removido:** o `setMaximumSize` (se o `BoxLayout` for substituído por `GridLayout`, deixa de ser necessário).
**Versão simplificada compatível (Cap. 06.4, slide 61 / Cap. 06.1, slide 35):**
```java
btn.setPreferredSize(new Dimension(200, 30));
```
**Risco:** **Baixo**.

---

## 12. `setPreferredSize(new Dimension(220, 0))` (altura 0) nos menus laterais

**Onde:** painel de menu lateral.
**Ficheiros:** [PainelCliente.java:61](ProjetoGrafico/src/view/PainelCliente.java#L61), [PainelFuncionario.java:58](ProjetoGrafico/src/view/PainelFuncionario.java#L58), [PainelGestor.java:62](ProjetoGrafico/src/view/PainelGestor.java#L62).
**Código exato:**
```java
painelMenu.setPreferredSize(new Dimension(220, 0));
```
**Aparece nos capítulos?** Parcialmente.
**Em que capítulo?** `setPreferredSize(Dimension)` está no Cap. 06.1 (slide 35) e Cap. 06.4 (slide 61). O **valor 0 na altura** (truque para o `BorderLayout.WEST` só respeitar a largura) não é demonstrado.
**Porque pode parecer acima do nível:** usar altura 0 deliberadamente é um detalhe idiomático não ensinado.
**Parâmetros realmente necessários:** a largura pretendida do menu.
**O que pode ser removido:** o valor `0` pode ser uma altura real, ou pode deixar-se o `BorderLayout` gerir o tamanho.
**Versão simplificada compatível:**
```java
painelMenu.setPreferredSize(new Dimension(220, 600));
```
**Risco:** **Baixo**.

---

## 13. Tooltips em `JMenuItem`

**Onde:** menu Ficheiro/Ajuda.
**Ficheiro:** [Aplicacao.java:74](ProjetoGrafico/src/view/Aplicacao.java#L74), 77, 84.
**Código exato:**
```java
menuConfigBD.setToolTipText("Configurar ligação à base de dados");
```
**Aparece nos capítulos?** Parcialmente.
**Em que capítulo?** `setToolTipText` está no Cap. 06.1 (slide 37), mas aplicado a **componentes** (ex.: `JTextField`). Os exemplos de menus (Cap. 06.3, slide 24) usam `setMnemonic(...)`, **não** tooltips em `JMenuItem`.
**Porque pode parecer acima do nível:** combinar tooltips com itens de menu não é demonstrado (e os slides associam aos menus a técnica diferente do mnemónico, que aqui não é usada).
**Parâmetros realmente necessários:** o texto do item de menu.
**O que pode ser removido:** os `setToolTipText` nos itens de menu.
**Versão simplificada compatível (Cap. 06.3, slide 24):**
```java
JMenuItem menuConfigBD = new JMenuItem("Configurar BD"); // sem tooltip
```
**Risco:** **Baixo**.

---

## 14. Uso muito extenso de tooltips em (quase) todos os componentes

**Onde:** praticamente todos os campos, botões e combos de todos os painéis.
**Ficheiros:** transversal (ex.: [PainelRegisto.java:86](ProjetoGrafico/src/view/PainelRegisto.java#L86), [Utilitarios.java:104](ProjetoGrafico/src/view/Utilitarios.java#L104), etc.).
**Código exato (representativo):**
```java
campoUsername.setToolTipText("Introduza o seu nome de utilizador");
```
**Aparece nos capítulos?** Sim (a técnica).
**Em que capítulo?** Cap. 06.1 (slide 37).
**Porque pode parecer acima do nível:** não é a técnica em si (essa está nos slides), mas a **densidade**: o slide mostra tooltips como recurso pontual; o projeto aplica-os de forma quase universal. É uma personalização extra, não estritamente necessária aos requisitos.
**Parâmetros realmente necessários:** nenhum (decorativo/ajuda).
**O que pode ser removido:** a maioria dos tooltips, mantendo eventualmente só os essenciais.
**Versão simplificada compatível:** manter o componente sem `setToolTipText` quando o rótulo já é claro.
**Risco:** **Baixo**.

---

## 15. Barra de estado (`JLabel` com border) no `SOUTH` do content pane

**Onde:** janela principal.
**Ficheiro:** [Aplicacao.java:103-105](ProjetoGrafico/src/view/Aplicacao.java#L103-L105).
**Código exato:**
```java
barraEstado = new JLabel(" Bem-vindo ao Sistema de Gestão de Oficina");
barraEstado.setBorder(BorderFactory.createLineBorder(Color.GRAY));
cont.add(barraEstado, BorderLayout.SOUTH);
```
**Aparece nos capítulos?** Sim (todos os elementos).
**Em que capítulo?** `JLabel` (Cap. 06.1), `BorderFactory.createLineBorder(Color.black)` (Cap. 06.4, slide 49), `BorderLayout.SOUTH` (Cap. 06.2, slide 15), `Color.GRAY` (cores pré-definidas — Cap. 06.0/06.1).
**Porque pode parecer acima do nível:** o **conceito** de “barra de estado” não é um exemplo dos slides, embora seja apenas a composição de elementos lecionados. É uma personalização não pedida pelos requisitos.
**Parâmetros realmente necessários:** nenhum (funcionalidade extra).
**O que pode ser removido:** toda a barra de estado, se se quiser minimalismo.
**Versão simplificada compatível:** manter (já usa só elementos dos slides) ou remover.
**Risco:** **Baixo** (tudo é matéria dada; é “extra” mas não avançado).

---

## 16. `Iterator` explícito em vez de ciclos simples para preencher tabelas (observação de contexto)

**Onde:** conversão de listas para `Object[][]`.
**Ficheiros:** transversal (ex.: [PainelCliente.java:223-229](ProjetoGrafico/src/view/PainelCliente.java#L223-L229), [PainelGestor.java:182-192](ProjetoGrafico/src/view/PainelGestor.java#L182-L192)).
**Código exato:**
```java
Iterator<Equipamento> it = meusEquip.iterator();
while (it.hasNext()) { Equipamento eq = it.next(); ... }
```
**Aparece nos capítulos?** Não é tema dos capítulos de GUI.
**Em que capítulo?** Nenhum (é matéria de coleções, não de Interfaces Gráficas). Os exemplos de `JTable` dos slides usam diretamente arrays/`Vector` literais (Cap. 06.4, slides 22-23).
**Porque pode parecer acima do nível (no contexto GUI):** não afeta a parte gráfica; apenas se nota que a preparação dos dados é mais elaborada do que os exemplos do professor (que usam dados estáticos).
**Parâmetros realmente necessários:** os dados em `Object[][]` para a `JTable`.
**O que pode ser removido:** nada do lado gráfico; é apenas observação.
**Versão simplificada compatível:** manter (está fora do âmbito dos 5 capítulos).
**Risco:** **Baixo** (não é elemento gráfico).

---

## 17. Confirmação de itens **alinhados** vs. exemplos do professor (sem diferença — apenas para registo)

Os seguintes elementos foram verificados e **estão dentro** dos materiais, pelo que **não** carecem de alteração:

| Elemento | Ficheiro(s) | Capítulo que cobre |
|---|---|---|
| `JFrame` + `getContentPane()` + `BorderLayout` | Aplicacao | 06.1 (slide 15-20), 06.2 |
| Troca de painéis `remove/add/revalidate/repaint` | Aplicacao, todos os painéis | 06.2 (slides 25-28) |
| `JMenuBar`/`JMenu`/`JMenuItem`/`addSeparator`/`setJMenuBar` | Aplicacao | 06.3 (slides 22-25) |
| `JOptionPane.showMessageDialog` | Utilitarios, Aplicacao, PainelLogin | 06.3 (slide 14) |
| `JOptionPane.showConfirmDialog(... Object[] ...)` | Aplicacao (criarPrimeiroGestor) | 06.3 (slide 17) |
| `JOptionPane.showConfirmDialog(YES_NO_OPTION)` | Utilitarios.confirmar | 06.3 (slide 15) |
| `JFileChooser` + `showOpenDialog` + `getSelectedFile` + `APPROVE_OPTION` | Utilitarios, PainelRegisto | 06.3 (slides 6-11) |
| `JDialog` modal `super(pai, titulo, true)` + `dispose()` | DialogoConfigBD | 06.3 (slides 19-20) |
| `JTable` + `DefaultTableModel` + `isCellEditable` + `getColumnClass` | Utilitarios | 06.4 (slides 33-36) |
| `setAutoCreateRowSorter(true)` | Utilitarios | 06.4 (slide 28) |
| `setSelectionMode(ListSelectionModel.SINGLE_SELECTION)` | Utilitarios | 06.4 (slide 26) |
| `getSelectedRow()` + `getValueAt()` | Utilitarios | 06.4 (slides 27, 25) |
| `setModel` + cast `(DefaultTableModel) getModel()` | Utilitarios | 06.4 (slide 37) |
| `JList` + `JScrollPane` | Utilitarios, PainelCliente | 06.4 (slides 13-17) |
| `TitledBorder` / `createTitledBorder` / `createLineBorder` | todos os painéis | 06.4 (slides 49-50) |
| `JTextArea(linhas, colunas)` + `JScrollPane` | PainelRegisto, PainelCliente, PainelFuncionario | 06.4 (slide 56) |
| `JPasswordField` + `getPassword()` | PainelLogin, etc. | 06.1 (slide 24) |
| `JComboBox.addItem(...)` | PainelGestor | 06.4 (slide 31) |
| `ActionListener` centralizado (`implements`) + classes anónimas | todos | 06.1 (slides 44-49) |
| `Printable`/`PrinterJob`/`Graphics2D`/`translate`/`getImageableX/Y`/`drawString`/`NO_SUCH_PAGE`/`PAGE_EXISTS` | ExtractoReparacao | 06.0 (slide 28) |
| `drawLine`, `Font`, `setFont` (na impressão) | ExtractoReparacao | 06.0 (slides 15, 23-26) |
| `DecimalFormat("0.00")` | ExtractoReparacao | 06.4 (slides 74-75) |
| `ImageIcon`, `setIcon`, foto em `JLabel` | Utilitarios | 06.3 (slides 33, 44-45) |
| `setSize`, `setLocationRelativeTo(null)`, `setResizable`, `setDefaultCloseOperation` | Aplicacao, DialogoConfigBD | 06.4 (slide 4), 06.2 (slide 34) |

---

## 18. Plano de simplificação recomendado (mínimo necessário)

Por ordem de prioridade (impacto em “chamar a atenção” vs. esforço):

1. **(Alto)** Tirar a cópia de ficheiros por streams de dentro da `view` — ponto **4**.
2. **(Médio)** Substituir o array `final [1]` por atributo da classe — ponto **3**.
3. **(Médio)** Trocar `GridBagLayout` por `FlowLayout` no login — ponto **5**.
4. **(Médio)** Trocar `BoxLayout` por `GridLayout(0,1)` nos menus/formulários — ponto **6** (remove também a necessidade do `setMaximumSize` — ponto 11).
5. **(Baixo)** Remover genéricos das componentes Swing — ponto **2**.
6. **(Baixo)** `SansSerif` → `Helvetica`/`TimesRoman` — ponto **7**.
7. **(Baixo)** `SCALE_SMOOTH` → `SCALE_DEFAULT` — ponto **10**.
8. **(Baixo)** Reduzir tooltips e remover `@Override` — pontos **8, 13, 14**.
9. **(Baixo)** Opcional: FlatLaf → Metal/CrossPlatform ou remover — ponto **1**.

Aplicando 1-4, a interface fica praticamente indistinguível dos exemplos dos capítulos 06.0–06.4, mantendo todos os requisitos funcionais.

---

*Nota metodológica:* toda a marcação “Não Encontrado / Acima do Nível” foi feita exclusivamente por comparação com os 5 capítulos. Itens que aparecem nos slides (mesmo que como opção mais avançada, como FlatLaf ou `SCALE_SMOOTH`) foram classificados com risco baixo e assinalados como “aparece no capítulo X”. Itens fora dos 5 capítulos (cópia de ficheiros por streams, idioma do array final, genéricos) foram os que receberam maior risco.
