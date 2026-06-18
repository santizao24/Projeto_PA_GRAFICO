package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Diálogo para configuração dos parâmetros de acesso à base de dados.
 * Permite ao utilizador definir o IP, porto, nome da base de dados,
 * username e password, gravando as configurações no ficheiro configbd.txt.
 *
 * @author Santiago e Hugo
 * @version 1.0
 */
public class DialogoConfigBD extends JDialog implements ActionListener {

    private JTextField campoIP;
    private JTextField campoPorto;
    private JTextField campoNomeBD;
    private JTextField campoUser;
    private JPasswordField campoPassword;
    private JButton btnGuardar;
    private JButton btnCancelar;

    /**
     * Constrói o diálogo de configuração da base de dados.
     *
     * @param pai janela pai do diálogo
     */
    public DialogoConfigBD(JFrame pai) {
        super(pai, "Configuração da Base de Dados", true);
        setSize(450, 320);
        setLocationRelativeTo(pai);
        setResizable(false);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));

        campoIP = new JTextField(20);
        campoPorto = new JTextField(20);
        campoNomeBD = new JTextField(20);
        campoUser = new JTextField(20);
        campoPassword = new JPasswordField(20);

        painelPrincipal.add(Utilitarios.criarCampoFormulario("IP do Servidor:", campoIP,
                "Endereço IP do servidor (ex: localhost ou 127.0.0.1)"));
        painelPrincipal.add(Utilitarios.criarCampoFormulario("Porto:", campoPorto,
                "Porto de ligação ao servidor (ex: 3306)"));
        painelPrincipal.add(Utilitarios.criarCampoFormulario("Nome da Base de Dados:", campoNomeBD,
                "Nome da base de dados a utilizar"));
        painelPrincipal.add(Utilitarios.criarCampoFormulario("Username:", campoUser,
                "Nome de utilizador para acesso à base de dados"));
        painelPrincipal.add(Utilitarios.criarCampoFormulario("Password:", campoPassword,
                "Palavra-passe para acesso à base de dados"));

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGuardar = new JButton("Guardar");
        btnGuardar.setToolTipText("Guardar as configurações no ficheiro configbd.txt");
        btnGuardar.addActionListener(this);
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setToolTipText("Cancelar e fechar esta janela");
        btnCancelar.addActionListener(this);
        painelBotoes.add(btnGuardar);
        painelBotoes.add(btnCancelar);

        Container cont = getContentPane();
        cont.setLayout(new BorderLayout());
        cont.add(painelPrincipal, BorderLayout.CENTER);
        cont.add(painelBotoes, BorderLayout.SOUTH);
    }

    /**
     * Processa os eventos dos botões Guardar e Cancelar.
     *
     * @param e evento de ação gerado
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnGuardar)) {
            String ip = campoIP.getText();
            String porto = campoPorto.getText();
            String bd = campoNomeBD.getText();
            String user = campoUser.getText();
            String password = new String(campoPassword.getPassword());

            if (ip.isEmpty() || porto.isEmpty() || bd.isEmpty() || user.isEmpty()) {
                Utilitarios.mostrarErro(this, "Todos os campos são obrigatórios!");
                return;
            }

            String url = "jdbc:mysql://" + ip + ":" + porto + "/" + bd;
            try {
                util.ConexaoBD.guardarConfiguracao(url, user, password);
                Utilitarios.mostrarSucesso(this,
                        "Parâmetros de acesso guardados com sucesso no ficheiro 'configbd.txt'!");
                dispose();
            } catch (Exception ex) {
                Utilitarios.mostrarErro(this, "Erro ao gravar as configurações: " + ex.getMessage());
            }
        } else if (e.getSource().equals(btnCancelar)) {
            dispose();
        }
    }
}
