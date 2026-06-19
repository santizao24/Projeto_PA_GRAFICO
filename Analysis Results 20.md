# Analysis Results 20
## Estado atual do Ambiente Gráfico (Java Swing) após alinhamento com os materiais da disciplina

**Âmbito:** ficheiros de `ProjetoGrafico/src/view/`
**Base de comparação (exclusiva):** LEI_PA_Cap_06.0, 06.1, 06.2, 06.3, 06.4
**Estado da compilação:** compila **sem erros e sem avisos** (`javac -Xlint:unchecked` → saída vazia, exit 0).

Este documento descreve **como o código está agora**, depois de aplicadas as correções para o aproximar dos exemplos do professor, e indica as duas opções conscientes que foram mantidas por decisão (FlatLaf e genéricos).

Ficheiros:
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

## 1. Convenções adotadas (consistência — "as coisas iguais sempre da mesma maneira")

| Tema | Forma única usada em todo o projeto |
|---|---|
| Empilhar componentes na vertical | `new GridLayout(0, 1)` (menus e formulários) |
| Menus laterais | `GridLayout(0,1)` + `setPreferredSize(new Dimension(220, 600))` + `criarBotaoMenu(...)` idêntico |
| Foto de perfil | sempre num **campo** `private JPanel painelFoto;` (igual nos 4 painéis) |
| Combos | leitura sempre por `getSelectedItem()` + `if/equals` |
| Eventos | `actionPerformed(...)` **sem** `@Override` (centralizado ou classe anónima) |
| Mensagens/diálogos | `JOptionPane` via `Utilitarios.mostrarErro/Sucesso/Info/confirmar` |
| Tabelas | criadas/atualizadas via `Utilitarios.criarTabela` / `atualizarTabela` |
| Cópia de ficheiros | delegada em `util.GestorFicheiros` (fora da `view`) |
| Fonte na impressão | `"Helvetica"` |

---

## 2. Correções aplicadas (alinhadas com o professor)

### 2.1 — `final JPanel[]` → campo `painelFoto`
**Ficheiros:** [PainelCliente.java](ProjetoGrafico/src/view/PainelCliente.java), [PainelFuncionario.java](ProjetoGrafico/src/view/PainelFuncionario.java), [PainelGestor.java](ProjetoGrafico/src/view/PainelGestor.java)
Antes usava-se o idioma `final JPanel[] painelFotoRef = new JPanel[1]` para aceder ao painel dentro de uma classe anónima (não lecionado). Agora usa-se um **atributo da classe**, tal como o [PainelRegisto.java](ProjetoGrafico/src/view/PainelRegisto.java) já fazia.
```java
private JPanel painelFoto;
...
painelFoto = Utilitarios.criarPainelFoto(utilizadorLogado.getFotoPath(), new ActionListener() {
    public void actionPerformed(ActionEvent ev) {
        ...
        Utilitarios.atualizarImagemPainel(painelFoto, novaFoto);
    }
});
p.add(painelFoto);
```

### 2.2 — Cópia de ficheiros fora da `view`
**Ficheiro:** [Utilitarios.java](ProjetoGrafico/src/view/Utilitarios.java)
O `escolherFicheiroImagem(...)` deixou de fazer cópia com `FileInputStream`/`FileOutputStream`/buffer (matéria de I/O, fora dos capítulos de GUI). Agora só abre o `JFileChooser` (Cap. 06.3) e delega a cópia no `util.GestorFicheiros.guardarFotoPerfil(...)`.
```java
if (fc.showOpenDialog(pai) == JFileChooser.APPROVE_OPTION) {
    File ficheiroOrigem = fc.getSelectedFile();
    String nomeOriginal = ficheiroOrigem.getName();
    String extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf('.'));
    String caminhoDestino = "fotos" + File.separator + "user_" + idUtilizador + extensao;
    if (util.GestorFicheiros.guardarFotoPerfil(ficheiroOrigem, caminhoDestino)) {
        return caminhoDestino;
    }
    mostrarErro(pai, "Erro ao copiar a foto.");
}
```
(Imports `FileInputStream`/`FileOutputStream`/`IOException` removidos da `view`.)

### 2.3 — `GridBagLayout` → `FlowLayout`
**Ficheiro:** [PainelLogin.java](ProjetoGrafico/src/view/PainelLogin.java)
O `GridBagLayout` (usado só para centrar, nunca exemplificado nos slides) deu lugar ao `FlowLayout` (Cap. 06.2, slide 14).
```java
JPanel painelCentral = new JPanel(new FlowLayout());
painelCentral.add(painelCabecalho);
add(painelCentral, BorderLayout.CENTER);
```

### 2.4 — `BoxLayout` → `GridLayout(0, 1)`
**Ficheiros:** Utilitarios, PainelRegisto, DialogoConfigBD, PainelCliente, PainelFuncionario, PainelGestor
O `BoxLayout` (descrito mas sem exemplo de código nos slides) foi substituído por `GridLayout` com 1 coluna (Cap. 06.2, slide 16).
```java
painel.setLayout(new GridLayout(0, 1)); // n linhas, 1 coluna
```

### 2.5 — Fonte `"SansSerif"` → `"Helvetica"`
**Ficheiro:** [ExtractoReparacao.java](ProjetoGrafico/src/view/ExtractoReparacao.java)
`"SansSerif"` não consta da lista de fontes dos slides; passou a `"Helvetica"` (Cap. 06.0, slide 26).
```java
g.setFont(new Font("Helvetica", Font.BOLD, 18));
```

### 2.6 — `@Override` removido
**Ficheiros:** todos
Os exemplos do professor escrevem os métodos de eventos/modelos **sem** a anotação (Cap. 06.1, slides 47-49). Foi removida em todo o lado.
```java
public void actionPerformed(ActionEvent e) { ... }
```

### 2.7 — `getSelectedIndex()` → `getSelectedItem()`
**Ficheiros:** PainelCliente, PainelFuncionario, PainelGestor
Todas as `JComboBox` passam a ler o valor com `getSelectedItem()` e comparação por `equals` (Cap. 06.1, slide 26), incluindo o painel de Listagens do Gestor (8 opções).
```java
String crit = (String) comboCrit.getSelectedItem();
int criterio = 1;
if (crit.equals("Estado")) {
    criterio = 2;
}
```
No Gestor:
```java
String sel = (String) comboTipo.getSelectedItem();
if (sel.equals("Utilizadores")) {
    ...
} else if (sel.equals("Reparações")) {
    ...
}
```

### 2.8 — `Image.SCALE_SMOOTH` → `Image.SCALE_DEFAULT`
**Ficheiro:** [Utilitarios.java](ProjetoGrafico/src/view/Utilitarios.java)
Passou para a variante apresentada como "a mais comum" (Cap. 06.3, slide 34).
```java
Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
```

### 2.9 — `setMaximumSize(...)` removido
**Ficheiros:** Utilitarios, PainelCliente, PainelFuncionario, PainelGestor
Deixou de ser necessário com o `GridLayout`. Os botões de menu ficam só com texto, tooltip e listener.
```java
JButton btn = new JButton(texto);
btn.setToolTipText(tooltip);
btn.addActionListener(this);
return btn;
```

### 2.10 — `new Dimension(220, 0)` → `new Dimension(220, 600)`
**Ficheiros:** PainelCliente, PainelFuncionario, PainelGestor
Removido o truque da altura `0`.
```java
painelMenu.setPreferredSize(new Dimension(220, 600));
```

### 2.11 — Tooltips em `JMenuItem` removidos
**Ficheiro:** [Aplicacao.java](ProjetoGrafico/src/view/Aplicacao.java)
Nos menus os slides usam `setMnemonic`, não tooltips (Cap. 06.3, slide 24). Os itens de menu ficam só com texto + listener.
```java
menuConfigBD = new JMenuItem("Configurar BD");
menuConfigBD.addActionListener(this);
```

---

## 3. Opções mantidas por decisão (e porquê são defensáveis)

### 3.1 — FlatLaf / `FlatDarculaLaf` (mantido)
**Ficheiro:** [Aplicacao.java](ProjetoGrafico/src/view/Aplicacao.java)
```java
import com.formdev.flatlaf.FlatDarculaLaf;
...
public static void main(String[] args) {
    FlatDarculaLaf.setup();
    Aplicacao app = new Aplicacao();
    app.iniciar();
}
```
**Justificação:** está **literalmente nos slides** (Cap. 06.4, slide 10), incluindo o import e o `FlatDarculaLaf.setup();`. Usa o `lib/flatlaf-3.5.4.jar`.
**Alternativa "Metal" (também nos slides, slide 8)**, caso se queira sem dependência externa:
```java
UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
```

### 3.2 — Genéricos nas componentes Swing (mantidos)
**Ficheiros:** Utilitarios e painéis
Mantêm-se `JComboBox<String>`, `JList<String>`, `Class<?>` em vez dos tipos crus.
```java
JComboBox<String> comboCrit = new JComboBox<>(new String[] { "Número", "Estado" });
public static javax.swing.JList<String> criarLista(String[] itens) { ... }
```
**Justificação:** os slides usam **tipos crus** (`JComboBox myComboBox = new JComboBox(arrayWithData[])`, Cap. 06.1 slide 26), o que gera avisos *raw type / unchecked* (as "cenas amarelas"). Para o projeto ficar **sem avisos**, optou-se por manter os genéricos.
**Nota:** se o objetivo for ser idêntico aos slides (tipos crus), basta remover `<...>`/`<>` — mas regressam os avisos amarelos. As duas coisas não coexistem.

---

## 4. Itens que já estavam corretos (sem alteração)

- Tooltips em campos/botões — técnica lecionada (Cap. 06.1, slide 37).
- Barra de estado (`JLabel` + `createLineBorder` + `BorderLayout.SOUTH`) — só usa elementos dos slides.
- `Iterator` no preenchimento de tabelas — não é matéria de GUI (coleções), não afeta a interface.
- `JFrame`/`JPanel` + troca por `remove/add/revalidate/repaint` (Cap. 06.2).
- `JMenuBar`/`JMenu`/`JMenuItem`/`addSeparator`/`setJMenuBar` (Cap. 06.3).
- `JOptionPane` (mensagem, confirmação, formulário via `Object[]`) (Cap. 06.3).
- `JFileChooser` (Cap. 06.3); `JDialog` modal `super(pai, titulo, true)` + `dispose()` (Cap. 06.3).
- `JTable` + `DefaultTableModel` + `isCellEditable` + `getColumnClass` + `setAutoCreateRowSorter` + `setSelectionMode` + `getSelectedRow`/`getValueAt` (Cap. 06.4).
- `JList`, `JScrollPane`, `TitledBorder`, `createLineBorder`, `setToolTipText`, `setPreferredSize` (Cap. 06.1/06.4).
- Impressão com `Printable`/`PrinterJob`/`Graphics2D`/`translate`/`drawString`/`drawLine`/`NO_SUCH_PAGE`/`PAGE_EXISTS` (Cap. 06.0, slide 28).
- `DecimalFormat("0.00")` (Cap. 06.4); `ImageIcon`/`getScaledInstance` (Cap. 06.3).
- `setSize`, `setLocationRelativeTo(null)`, `setResizable`, `setDefaultCloseOperation` (Cap. 06.4 / 06.2).

---

## 5. Verificação

```
javac -encoding UTF-8 -Xlint:unchecked -cp "lib/*" -d <out> @sources.txt
→ (sem mensagens)  exit 0
```
Sem erros e sem avisos. Dependência necessária: `lib/flatlaf-3.5.4.jar` (FlatLaf), além de `mysql-connector-j`, `javax.mail` e `activation` já existentes.
