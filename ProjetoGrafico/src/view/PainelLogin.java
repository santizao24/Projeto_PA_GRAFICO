package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Utilizador;
import Enums.EstadoUtilizador;

/**
 * Painel de autenticação do sistema de gestão de oficina.
 * Permite ao utilizador inserir as credenciais (username e password)
 * para aceder ao sistema. Após autenticação, verifica notificações
 * pendentes e apresenta um alerta ao utilizador (R5).
 *
 * @author Santiago e Hugo
 * @version 1.0
 */
public class PainelLogin extends JPanel implements ActionListener {

    private AplicacaoGUI aplicacao;
    private JTextField campoUsername;
    private JPasswordField campoPassword;
    private JButton btnEntrar;
    private JButton btnRegistar;
    private JButton btnConfigBD;

    /**
     * Constrói o painel de login.
     *
     * @param aplicacao referência para a aplicação principal
     */
    public PainelLogin(AplicacaoGUI aplicacao) {
        this.aplicacao = aplicacao;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titulo = new JLabel("Sistema de Gestão de Oficina");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titulo, gbc);

        JLabel subtitulo = new JLabel("Autenticação");
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridy = 1;
        add(subtitulo, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;

        JLabel lblUsername = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(lblUsername, gbc);

        campoUsername = new JTextField(20);
        campoUsername.setToolTipText("Introduza o seu nome de utilizador");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(campoUsername, gbc);

        gbc.anchor = GridBagConstraints.EAST;
        JLabel lblPassword = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(lblPassword, gbc);

        campoPassword = new JPasswordField(20);
        campoPassword.setToolTipText("Introduza a sua palavra-passe");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(campoPassword, gbc);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnEntrar = new JButton("Entrar");
        btnEntrar.setToolTipText("Efetuar login no sistema");
        btnEntrar.addActionListener(this);
        btnRegistar = new JButton("Registar");
        btnRegistar.setToolTipText("Criar uma nova conta no sistema");
        btnRegistar.addActionListener(this);
        btnConfigBD = new JButton("Configurar BD");
        btnConfigBD.setToolTipText("Configurar os parâmetros de acesso à base de dados");
        btnConfigBD.addActionListener(this);
        painelBotoes.add(btnEntrar);
        painelBotoes.add(btnRegistar);
        painelBotoes.add(btnConfigBD);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(painelBotoes, gbc);
    }

    /**
     * Processa os eventos dos botões Entrar, Registar e Configurar BD.
     * No caso de login bem-sucedido, verifica notificações pendentes (R5).
     *
     * @param e evento de ação gerado
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnEntrar) {
            String user = campoUsername.getText().trim();
            String pass = new String(campoPassword.getPassword());

            if (user.isEmpty() || pass.isEmpty()) {
                Utilitarios.mostrarErro(this, "Preencha o username e a password!");
                return;
            }

            Utilizador utilizador = aplicacao.getControladorUtilizador().efetuarLogin(user, pass);

            if (utilizador != null) {
                if (utilizador.getEstado() == EstadoUtilizador.ATIVO) {
                    aplicacao.getControladorUtilizador().registarInicioSessao(user);

                    // R5 - Verificar notificações após autenticação
                    int naoLidas = aplicacao.getControladorNotificacao()
                            .contarNaoLidas(utilizador.getIdUtilizador());
                    if (naoLidas > 0) {
                        JOptionPane.showMessageDialog(this,
                                "Tens " + naoLidas + " notificação(ões) não lida(s)!",
                                "Notificações Pendentes",
                                JOptionPane.WARNING_MESSAGE);
                    }

                    aplicacao.autenticar(utilizador);
                    limparCampos();
                } else if (utilizador.getEstado() == EstadoUtilizador.PENDENTE) {
                    Utilitarios.mostrarInfo(this, "Conta aguardando ativação pelo Gestor.");
                } else if (utilizador.getEstado() == EstadoUtilizador.REJEITADO) {
                    Utilitarios.mostrarInfo(this, "Conta com registo Recusado!");
                } else if (utilizador.getEstado() == EstadoUtilizador.INATIVO) {
                    Utilitarios.mostrarInfo(this, "A tua conta encontra-se INATIVA. Contacta um gestor.");
                } else if (utilizador.getEstado() == EstadoUtilizador.AGUARDA_REMOCAO) {
                    Utilitarios.mostrarInfo(this,
                            "A tua conta está a aguardar remoção. Contacta um gestor se queres reverter.");
                } else if (utilizador.getEstado() == EstadoUtilizador.REMOVIDA) {
                    Utilitarios.mostrarInfo(this, "Esta conta foi removida do sistema.");
                } else {
                    Utilitarios.mostrarInfo(this,
                            "Conta com estado desconhecido: " + utilizador.getEstado());
                }
            } else {
                Utilitarios.mostrarErro(this, "Credenciais inválidas.");
            }
        } else if (e.getSource() == btnRegistar) {
            aplicacao.mostrarPainel("registo");
        } else if (e.getSource() == btnConfigBD) {
            DialogoConfigBD dialogo = new DialogoConfigBD(aplicacao);
            dialogo.setVisible(true);
        }
    }

    /**
     * Limpa os campos de texto do formulário de login.
     */
    private void limparCampos() {
        campoUsername.setText("");
        campoPassword.setText("");
    }
}
