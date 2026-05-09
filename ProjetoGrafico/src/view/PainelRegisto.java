package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import util.Validacoes;

/**
 * Painel de registo de novos utilizadores no sistema.
 * Permite registar funcionários ou clientes, apresentando campos
 * dinâmicos conforme o tipo selecionado. Inclui campo de observações (R4)
 * e tooltips de ajuda em todos os campos (R10).
 *
 * @author Santiago e Hugo
 * @version 1.0
 */
public class PainelRegisto extends JPanel implements ActionListener {

    private AplicacaoGUI aplicacao;
    private JTextField campoNome;
    private JTextField campoEmail;
    private JTextField campoUsername;
    private JPasswordField campoPassword;
    private JComboBox<String> comboTipo;
    private JTextArea campoObservacoes;

    // Campos Funcionário
    private JTextField campoNifFunc;
    private JTextField campoTelFunc;
    private JTextField campoMoradaFunc;
    private JComboBox<String> comboEspecializacao;
    private JTextField campoDataInicioFunc;

    // Campos Cliente
    private JTextField campoNifCliente;
    private JTextField campoTelCliente;
    private JTextField campoMoradaCliente;
    private JTextField campoSetor;
    private JComboBox<String> comboEscalao;

    private JPanel painelCamposEspecificos;
    private JPanel painelFuncionario;
    private JPanel painelCliente;

    private JButton btnRegistar;
    private JButton btnVoltar;

    /**
     * Constrói o painel de registo de utilizadores.
     *
     * @param aplicacao referência para a aplicação principal
     */
    public PainelRegisto(AplicacaoGUI aplicacao) {
        this.aplicacao = aplicacao;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Título
        JLabel titulo = new JLabel("Registo de Novo Utilizador", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        // Painel central com scroll
        JPanel painelFormulario = new JPanel();
        painelFormulario.setLayout(new BoxLayout(painelFormulario, BoxLayout.Y_AXIS));

        // Campos comuns
        campoNome = new JTextField(20);
        campoEmail = new JTextField(20);
        campoUsername = new JTextField(20);
        campoPassword = new JPasswordField(20);
        comboTipo = new JComboBox<>(new String[] { "Funcionário", "Cliente" });
        comboTipo.setToolTipText("Selecione o tipo de utilizador a registar");
        comboTipo.addActionListener(this);

        painelFormulario.add(Utilitarios.criarCampoFormulario("Nome:", campoNome,
                "Nome completo do utilizador"));
        painelFormulario.add(Box.createVerticalStrut(5));
        painelFormulario.add(Utilitarios.criarCampoFormulario("Email:", campoEmail,
                "Endereço de email no formato designacao@entidade.dominio"));
        painelFormulario.add(Box.createVerticalStrut(5));
        painelFormulario.add(Utilitarios.criarCampoFormulario("Username:", campoUsername,
                "Nome de utilizador para autenticação no sistema"));
        painelFormulario.add(Box.createVerticalStrut(5));
        painelFormulario.add(Utilitarios.criarCampoFormulario("Password:", campoPassword,
                "Palavra-passe para acesso ao sistema"));
        painelFormulario.add(Box.createVerticalStrut(5));
        painelFormulario.add(Utilitarios.criarCampoFormulario("Tipo de Utilizador:", comboTipo,
                "Selecione Funcionário ou Cliente"));
        painelFormulario.add(Box.createVerticalStrut(10));

        // Campos específicos (dinâmicos via remove/add/revalidate/repaint)
        painelCamposEspecificos = new JPanel(new BorderLayout());
        construirPainelFuncionario();
        construirPainelCliente();
        painelCamposEspecificos.add(painelFuncionario, BorderLayout.CENTER);
        painelFormulario.add(painelCamposEspecificos);

        // R4 - Campo de observações
        painelFormulario.add(Box.createVerticalStrut(10));
        campoObservacoes = new JTextArea(3, 20);
        campoObservacoes.setLineWrap(true);
        campoObservacoes.setWrapStyleWord(true);
        JScrollPane scrollObs = new JScrollPane(campoObservacoes);
        painelFormulario.add(Utilitarios.criarCampoFormulario("Observações:", scrollObs,
                "Observações ou comentários adicionais sobre o registo (R4)"));

        JScrollPane scrollFormulario = new JScrollPane(painelFormulario);
        scrollFormulario.setBorder(null);
        add(scrollFormulario, BorderLayout.CENTER);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRegistar = new JButton("Registar");
        btnRegistar.setToolTipText("Submeter o registo do novo utilizador");
        btnRegistar.addActionListener(this);
        btnVoltar = new JButton("Voltar");
        btnVoltar.setToolTipText("Voltar ao ecrã de login");
        btnVoltar.addActionListener(this);
        painelBotoes.add(btnRegistar);
        painelBotoes.add(btnVoltar);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    /**
     * Constrói e configura os campos específicos para o registo de um Funcionário.
     */
    private void construirPainelFuncionario() {
        painelFuncionario = new JPanel();
        painelFuncionario.setLayout(new BoxLayout(painelFuncionario, BoxLayout.Y_AXIS));

        campoNifFunc = new JTextField(20);
        campoTelFunc = new JTextField(20);
        campoMoradaFunc = new JTextField(20);
        comboEspecializacao = new JComboBox<>(new String[] { "1", "2", "3", "4", "5" });
        campoDataInicioFunc = new JTextField(20);

        painelFuncionario.add(Utilitarios.criarCampoFormulario("NIF:", campoNifFunc,
                "Número de Identificação Fiscal com 9 dígitos numéricos"));
        painelFuncionario.add(Box.createVerticalStrut(5));
        painelFuncionario.add(Utilitarios.criarCampoFormulario("Telefone:", campoTelFunc,
                "Número de telefone com 9 dígitos (começa por 2, 3 ou 9)"));
        painelFuncionario.add(Box.createVerticalStrut(5));
        painelFuncionario.add(Utilitarios.criarCampoFormulario("Morada:", campoMoradaFunc,
                "Morada completa do funcionário"));
        painelFuncionario.add(Box.createVerticalStrut(5));
        painelFuncionario.add(Utilitarios.criarCampoFormulario("Especialização:", comboEspecializacao,
                "Nível de especialização de 1 (mínimo) a 5 (máximo)"));
        painelFuncionario.add(Box.createVerticalStrut(5));
        painelFuncionario.add(Utilitarios.criarCampoFormulario("Data Início:", campoDataInicioFunc,
                "Data de início de atividade no formato YYYY-MM-DD"));
    }

    /**
     * Constrói e configura os campos específicos para o registo de um Cliente.
     */
    private void construirPainelCliente() {
        painelCliente = new JPanel();
        painelCliente.setLayout(new BoxLayout(painelCliente, BoxLayout.Y_AXIS));

        campoNifCliente = new JTextField(20);
        campoTelCliente = new JTextField(20);
        campoMoradaCliente = new JTextField(20);
        campoSetor = new JTextField(20);
        comboEscalao = new JComboBox<>(new String[] { "A", "B", "C", "D" });

        painelCliente.add(Utilitarios.criarCampoFormulario("NIF:", campoNifCliente,
                "Número de Identificação Fiscal com 9 dígitos numéricos"));
        painelCliente.add(Box.createVerticalStrut(5));
        painelCliente.add(Utilitarios.criarCampoFormulario("Telefone:", campoTelCliente,
                "Número de telefone com 9 dígitos (começa por 2, 3 ou 9)"));
        painelCliente.add(Box.createVerticalStrut(5));
        painelCliente.add(Utilitarios.criarCampoFormulario("Morada:", campoMoradaCliente,
                "Morada completa do cliente"));
        painelCliente.add(Box.createVerticalStrut(5));
        painelCliente.add(Utilitarios.criarCampoFormulario("Setor de Atividade:", campoSetor,
                "Setor de atividade profissional do cliente"));
        painelCliente.add(Box.createVerticalStrut(5));
        painelCliente.add(Utilitarios.criarCampoFormulario("Escalão:", comboEscalao,
                "Escalão do cliente: A, B, C ou D"));
    }

    /**
     * Processa os eventos dos botões e da ComboBox de tipo de utilizador.
     *
     * @param e evento de ação gerado
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == comboTipo) {
            String tipoSel = (String) comboTipo.getSelectedItem();
            painelCamposEspecificos.removeAll();
            if (tipoSel.equals("Funcionário")) {
                painelCamposEspecificos.add(painelFuncionario, BorderLayout.CENTER);
            } else {
                painelCamposEspecificos.add(painelCliente, BorderLayout.CENTER);
            }
            painelCamposEspecificos.revalidate();
            painelCamposEspecificos.repaint();
        } else if (e.getSource() == btnRegistar) {
            efetuarRegisto();
        } else if (e.getSource() == btnVoltar) {
            limparCampos();
            aplicacao.mostrarPainel("login");
        }
    }

    /**
     * Valida os campos preenchidos, cria as instâncias adequadas (Cliente ou Funcionário)
     * e submete os dados ao controlador para registo no sistema.
     */
    private void efetuarRegisto() {
        String nome = campoNome.getText().trim();
        String email = campoEmail.getText().trim();
        String user = campoUsername.getText().trim();
        String pass = new String(campoPassword.getPassword());

        if (nome.isEmpty()) {
            Utilitarios.mostrarErro(this, "O nome não pode estar vazio!");
            return;
        }
        if (!Validacoes.emailValido(email)) {
            Utilitarios.mostrarErro(this,
                    "Formato de email inválido! Use o formato: designacao@entidade.dominio");
            return;
        }
        if (user.isEmpty()) {
            Utilitarios.mostrarErro(this, "O username não pode estar vazio!");
            return;
        }
        if (pass.isEmpty()) {
            Utilitarios.mostrarErro(this, "A password não pode estar vazia!");
            return;
        }

        String tipo = (String) comboTipo.getSelectedItem();
        boolean sucesso;

        if (tipo.equals("Funcionário")) {
            String nif = campoNifFunc.getText().trim();
            String tel = campoTelFunc.getText().trim();
            String morada = campoMoradaFunc.getText().trim();
            String dataInicio = campoDataInicioFunc.getText().trim();

            if (!Validacoes.nifValido(nif)) {
                Utilitarios.mostrarErro(this, "O NIF tem de ter exatamente 9 dígitos numéricos!");
                return;
            }
            if (!Validacoes.telefoneValido(tel)) {
                Utilitarios.mostrarErro(this,
                        "Formato de telefone inválido! (9 dígitos, começa por 2, 3 ou 9)");
                return;
            }

            int esp = Integer.parseInt((String) comboEspecializacao.getSelectedItem());

            sucesso = aplicacao.getControladorUtilizador().registarFuncionario(
                    nome, email, user, pass, nif, tel, morada, esp, dataInicio);
        } else {
            String nif = campoNifCliente.getText().trim();
            String tel = campoTelCliente.getText().trim();
            String morada = campoMoradaCliente.getText().trim();
            String setor = campoSetor.getText().trim();
            String escalao = (String) comboEscalao.getSelectedItem();

            if (!Validacoes.nifValido(nif)) {
                Utilitarios.mostrarErro(this, "O NIF tem de ter exatamente 9 dígitos numéricos!");
                return;
            }
            if (!Validacoes.telefoneValido(tel)) {
                Utilitarios.mostrarErro(this,
                        "Formato de telefone inválido! (9 dígitos, começa por 2, 3 ou 9)");
                return;
            }

            sucesso = aplicacao.getControladorUtilizador().registarCliente(
                    nome, email, user, pass, nif, tel, morada, setor, escalao);
        }

        if (sucesso) {
            Utilitarios.mostrarSucesso(this, "Registo efetuado! Aguarde aprovação do Gestor.");
            limparCampos();
            aplicacao.mostrarPainel("login");
        } else {
            Utilitarios.mostrarErro(this,
                    "Erro no registo! O username ou email especificado já se encontra em uso.");
        }
    }

    /**
     * Limpa todos os campos do formulário após um registo bem sucedido
     * ou cancelamento.
     */
    private void limparCampos() {
        campoNome.setText("");
        campoEmail.setText("");
        campoUsername.setText("");
        campoPassword.setText("");
        campoObservacoes.setText("");
        campoNifFunc.setText("");
        campoTelFunc.setText("");
        campoMoradaFunc.setText("");
        campoDataInicioFunc.setText("");
        campoNifCliente.setText("");
        campoTelCliente.setText("");
        campoMoradaCliente.setText("");
        campoSetor.setText("");
    }
}
