package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Classe utilitária com métodos reutilizáveis para a interface gráfica.
 * Garante a harmonização do código ao centralizar a criação de
 * componentes comuns como tabelas, campos de formulário e diálogos.
 *
 * @author Santiago e Hugo
 * @version 1.0
 */
public class Utilitarios {

    /**
     * Cria uma JTable configurada com modelo não editável e ordenação automática.
     * 
     * @param colunas array com os nomes das colunas
     * @param dados matriz bidimensional com os dados da tabela
     * @return a JTable configurada e pronta a usar
     */
    public static javax.swing.JTable criarTabela(String[] colunas, Object[][] dados) {
        javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(dados, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (getRowCount() > 0 && getValueAt(0, columnIndex) != null) {
                    return getValueAt(0, columnIndex).getClass();
                }
                return Object.class;
            }
        };
        javax.swing.JTable tabela = new javax.swing.JTable(modelo);
        tabela.setAutoCreateRowSorter(true);
        tabela.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        return tabela;
    }

    /**
     * Cria uma JList simples com um conjunto de itens de texto.
     * 
     * @param itens array com os itens a mostrar na lista
     * @return a JList parametrizada com strings
     */
    public static javax.swing.JList<String> criarLista(String[] itens) {
        javax.swing.JList<String> lista = new javax.swing.JList<>(itens);
        lista.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        return lista;
    }



    /**
     * Atualiza os dados de uma JTable existente dentro de um JScrollPane.
     *
     * @param tabela a JTable a analisar
     * @param colunas    nomes das colunas
     * @param dados      novos dados para a tabela
     */
    public static void atualizarTabela(JTable tabela, String[] colunas, Object[][] dados) {
        if (tabela != null) {
            DefaultTableModel modelo = new DefaultTableModel(dados, colunas) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }

                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    if (getRowCount() > 0 && getValueAt(0, columnIndex) != null) {
                        return getValueAt(0, columnIndex).getClass();
                    }
                    return Object.class;
                }
            };
            tabela.setModel(modelo);
            tabela.setAutoCreateRowSorter(true);
        }
    }

    /**
     * Cria um JPanel com um JLabel e um componente de input lado a lado,
     * aplicando um tooltip de ajuda ao componente.
     *
     * @param textoLabel texto da label descritiva
     * @param campo      componente de input (JTextField, JComboBox, etc.)
     * @param tooltip    texto de ajuda que aparece ao passar o rato
     * @return JPanel contendo a label e o campo
     */
    public static JPanel criarCampoFormulario(String textoLabel, JComponent campo, String tooltip) {
        JPanel painel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(textoLabel);
        label.setPreferredSize(new Dimension(180, 25));
        campo.setToolTipText(tooltip);
        painel.add(label, BorderLayout.WEST);
        painel.add(campo, BorderLayout.CENTER);
        painel.setMaximumSize(new Dimension(400, 30));
        return painel;
    }

    /**
     * Apresenta uma mensagem de erro ao utilizador usando JOptionPane.
     *
     * @param pai componente pai para o diálogo
     * @param msg mensagem de erro a apresentar
     */
    public static void mostrarErro(Component pai, String msg) {
        JOptionPane.showMessageDialog(pai, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Apresenta uma mensagem de sucesso ao utilizador usando JOptionPane.
     *
     * @param pai componente pai para o diálogo
     * @param msg mensagem de sucesso a apresentar
     */
    public static void mostrarSucesso(Component pai, String msg) {
        JOptionPane.showMessageDialog(pai, msg, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Apresenta uma mensagem informativa ao utilizador usando JOptionPane.
     *
     * @param pai componente pai para o diálogo
     * @param msg mensagem informativa a apresentar
     */
    public static void mostrarInfo(Component pai, String msg) {
        JOptionPane.showMessageDialog(pai, msg, "Informação", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Apresenta um diálogo de confirmação (Sim/Não) ao utilizador.
     *
     * @param pai componente pai para o diálogo
     * @param msg mensagem de confirmação
     * @return true se o utilizador selecionar "Sim", false caso contrário
     */
    public static boolean confirmar(Component pai, String msg) {
        int resultado = JOptionPane.showConfirmDialog(pai, msg, "Confirmação", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        return resultado == JOptionPane.YES_OPTION;
    }

    /**
     * Obtém o ID (primeira coluna) da linha selecionada numa JTable.
     *
     * @param tabela a JTable a analisar
     * @return o valor da primeira coluna da linha selecionada, ou -1 se nada
     *         estiver selecionado
     */
    public static int obterIdSelecionado(JTable tabela) {
        if (tabela != null && tabela.getSelectedRow() != -1) {
            Object valor = tabela.getValueAt(tabela.getSelectedRow(), 0);
            if (valor instanceof Integer) {
                return (Integer) valor;
            }
            try {
                return Integer.parseInt(valor.toString());
            } catch (NumberFormatException e) {
                return -1;
            }
        }
        return -1;
    }

    /**
     * Obtém o valor de uma coluna específica da linha selecionada numa JTable.
     *
     * @param tabela a JTable a analisar
     * @param coluna     índice da coluna (0-based)
     * @return o valor da coluna como String, ou null se nada estiver selecionado
     */
    public static String obterValorSelecionado(JTable tabela, int coluna) {
        if (tabela != null && tabela.getSelectedRow() != -1) {
            Object valor = tabela.getValueAt(tabela.getSelectedRow(), coluna);
            return valor != null ? valor.toString() : null;
        }
        return null;
    }

    /**
     * Cria um JPanel com a foto de perfil e um botão para alterar.
     * Se o caminho da foto for null ou inválido, mostra uma imagem genérica.
     *
     * @param fotoPath  caminho relativo da foto (pode ser null)
     * @param onAlterar ActionListener para o botão "Alterar Foto"
     * @return JPanel contendo a foto e o botão
     */
    public static JPanel criarPainelFoto(String fotoPath, ActionListener onAlterar) {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

        JLabel lblFoto = new JLabel();
        lblFoto.setPreferredSize(new Dimension(100, 100));
        lblFoto.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        String pathAtual = fotoPath;
        if (pathAtual == null || pathAtual.isEmpty() || !new File(pathAtual).exists()) {
            pathAtual = "fotos/geral.png";
        }

        File ficheiro = new File(pathAtual);
        if (ficheiro.exists()) {
            ImageIcon icon = new ImageIcon(pathAtual);
            Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            lblFoto.setIcon(new ImageIcon(img));
            lblFoto.setText("");
        } else {
            lblFoto.setIcon(null);
            lblFoto.setText("Sem Foto");
        }

        JButton btnAlterar = new JButton("Alterar Foto");
        btnAlterar.setToolTipText("Clique para escolher uma foto de perfil");
        btnAlterar.addActionListener(onAlterar);

        painel.add(lblFoto);
        painel.add(btnAlterar);

        return painel;
    }

    /**
     * Abre um JFileChooser para o utilizador selecionar uma imagem.
     * Copia o ficheiro para a pasta "fotos/" do projeto e retorna o caminho.
     *
     * @param pai          componente pai para o diálogo
     * @param idUtilizador identificador do utilizador (para nomear o ficheiro)
     * @return caminho relativo da foto copiada (ex: fotos/user_42.png), ou null se
     *         cancelar
     */
    public static String escolherFicheiroImagem(Component pai, int idUtilizador) {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Escolher Foto de Perfil");

        int resultado = fc.showOpenDialog(pai);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File ficheiroOrigem = fc.getSelectedFile();
            try {
                File pastaFotos = new File("fotos");
                if (!pastaFotos.exists()) {
                    pastaFotos.mkdirs();
                }

                String nomeOriginal = ficheiroOrigem.getName();
                String extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf('.'));

                String nomeDestino = "user_" + idUtilizador + extensao;
                File ficheiroDestino = new File(pastaFotos, nomeDestino);
                FileInputStream fis = new FileInputStream(ficheiroOrigem);
                FileOutputStream fos = new FileOutputStream(ficheiroDestino);
                byte[] buffer = new byte[1024];
                int bytesLidos;
                while ((bytesLidos = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, bytesLidos);
                }
                fis.close();
                fos.close();

                return "fotos" + File.separator + nomeDestino;
            } catch (IOException e) {
                mostrarErro(pai, "Erro ao copiar a foto: " + e.getMessage());
                return null;
            }
        }
        return null;
    }

    /**
     * Atualiza a imagem apresentada num painel de foto.
     *
     * @param painelFoto JPanel criado por criarPainelFoto
     * @param fotoPath   novo caminho da foto
     */
    public static void atualizarImagemPainel(JPanel painelFoto, String fotoPath) {
        Component[] componentes = painelFoto.getComponents();
        int i = 0;
        while (i < componentes.length) {
            Component c = componentes[i];
            if (c instanceof JLabel) {
                JLabel lbl = (JLabel) c;
                String pathAtual = fotoPath;
                if (pathAtual == null || pathAtual.isEmpty() || !new File(pathAtual).exists()) {
                    pathAtual = "fotos/geral.png";
                }
                if (new File(pathAtual).exists()) {
                    ImageIcon icon = new ImageIcon(pathAtual);
                    Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    lbl.setIcon(new ImageIcon(img));
                    lbl.setText("");
                } else {
                    lbl.setIcon(null);
                    lbl.setText("Sem Foto");
                }
                break;
            }
            i++;
        }
    }
}
