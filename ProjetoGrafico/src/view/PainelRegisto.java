package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import model.Utilizador;
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
    private File fotoSelecionada = null;
    private JPanel painelFoto;

    private JTextField campoNifFunc;
    private JTextField campoTelFunc;
    private JTextField campoMoradaFunc;
    private JComboBox<String> comboEspecializacao;
    private JTextField campoDataInicioFunc;

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
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Registo de Novo Utilizador");
        add(titulo, BorderLayout.NORTH);

        JPanel painelFormulario = new JPanel();
        painelFormulario.setLayout(new BoxLayout(painelFormulario, BoxLayout.Y_AXIS));

        painelFoto = Utilitarios.criarPainelFoto(null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                JFileChooser fc = new JFileChooser();
                fc.setDialogTitle("Escolher Foto para o Novo Perfil");

                if (fc.showOpenDialog(PainelRegisto.this) == JFileChooser.APPROVE_OPTION) {
                    fotoSelecionada = fc.getSelectedFile();
                    Utilitarios.atualizarImagemPainel(painelFoto, fotoSelecionada.getAbsolutePath());
                }
            }
        });
        painelFormulario.add(painelFoto);

        campoNome = new JTextField(20);
        campoEmail = new JTextField(20);
        campoUsername = new JTextField(20);
        campoPassword = new JPasswordField(20);
        comboTipo = new JComboBox<>(new String[] { "Funcionário", "Cliente" });
        comboTipo.setToolTipText("Selecione o tipo de utilizador a registar");
        comboTipo.addActionListener(this);

        painelFormulario.add(Utilitarios.criarCampoFormulario("Nome:", campoNome,
                "Nome completo do utilizador"));
        painelFormulario.add(Utilitarios.criarCampoFormulario("Email:", campoEmail,
                "Endereço de email no formato designacao@entidade.dominio"));
        painelFormulario.add(Utilitarios.criarCampoFormulario("Username:", campoUsername,
                "Nome de utilizador para autenticação no sistema"));
        painelFormulario.add(Utilitarios.criarCampoFormulario("Password:", campoPassword,
                "Palavra-passe para acesso ao sistema"));
        painelFormulario.add(Utilitarios.criarCampoFormulario("Tipo de Utilizador:", comboTipo,
                "Selecione Funcionário ou Cliente"));

        painelCamposEspecificos = new JPanel(new BorderLayout());
        construirPainelFuncionario();
        construirPainelCliente();
        painelCamposEspecificos.add(painelFuncionario, BorderLayout.CENTER);
        painelFormulario.add(painelCamposEspecificos);

        campoObservacoes = new JTextArea(3, 20);
        JScrollPane scrollObs = new JScrollPane(campoObservacoes);
        painelFormulario.add(Utilitarios.criarCampoFormulario("Observações:", scrollObs,
                "Observações ou comentários adicionais sobre o registo"));

        JScrollPane scrollFormulario = new JScrollPane(painelFormulario);
        scrollFormulario.setBorder(null);
        add(scrollFormulario, BorderLayout.CENTER);

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
        painelFuncionario.add(Utilitarios.criarCampoFormulario("Telefone:", campoTelFunc,
                "Número de telefone com 9 dígitos (começa por 2, 3 ou 9)"));
        painelFuncionario.add(Utilitarios.criarCampoFormulario("Morada:", campoMoradaFunc,
                "Morada completa do funcionário"));
        painelFuncionario.add(Utilitarios.criarCampoFormulario("Especialização:", comboEspecializacao,
                "Nível de especialização de 1 (mínimo) a 5 (máximo)"));
        painelFuncionario.add(Utilitarios.criarCampoFormulario("Data Início:", campoDataInicioFunc,
                "Data de início de atividade (dd/MM/yyyy ou yyyy-MM-dd)"));
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
        painelCliente.add(Utilitarios.criarCampoFormulario("Telefone:", campoTelCliente,
                "Número de telefone com 9 dígitos (começa por 2, 3 ou 9)"));
        painelCliente.add(Utilitarios.criarCampoFormulario("Morada:", campoMoradaCliente,
                "Morada completa do cliente"));
        painelCliente.add(Utilitarios.criarCampoFormulario("Setor de Atividade:", campoSetor,
                "Setor de atividade profissional do cliente"));
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
        if (e.getSource().equals(comboTipo)) {
            String tipoSel = (String) comboTipo.getSelectedItem();
            painelCamposEspecificos.removeAll();
            if (tipoSel.equals("Funcionário")) {
                painelCamposEspecificos.add(painelFuncionario, BorderLayout.CENTER);
            } else {
                painelCamposEspecificos.add(painelCliente, BorderLayout.CENTER);
            }
            painelCamposEspecificos.revalidate();
            painelCamposEspecificos.repaint();
        } else if (e.getSource().equals(btnRegistar)) {
            efetuarRegisto();
        } else if (e.getSource().equals(btnVoltar)) {
            limparCampos();
            aplicacao.mostrarPainel("login");
        }
    }

    /**
     * Valida os campos preenchidos, cria as instâncias adequadas (Cliente ou
     * Funcionário)
     * e submete os dados ao controlador para registo no sistema.
     */
    private void efetuarRegisto() {
        String nome = campoNome.getText();
        String email = campoEmail.getText();
        String user = campoUsername.getText();
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
            String nif = campoNifFunc.getText();
            String tel = campoTelFunc.getText();
            String morada = campoMoradaFunc.getText();
            String dataInicio = campoDataInicioFunc.getText();

            if (!Validacoes.nifValido(nif)) {
                Utilitarios.mostrarErro(this, "O NIF tem de ter exatamente 9 dígitos numéricos!");
                return;
            }
            if (!Validacoes.telefoneValido(tel)) {
                Utilitarios.mostrarErro(this,
                        "Formato de telefone inválido! (9 dígitos, começa por 2, 3 ou 9)");
                return;
            }

            String dataNormalizada = Validacoes.normalizarData(dataInicio);
            if (dataNormalizada == null) {
                Utilitarios.mostrarErro(this, "Data de início inválida! Use formatos como dd/MM/yyyy ou yyyy-MM-dd.");
                return;
            }

            int esp = Integer.parseInt((String) comboEspecializacao.getSelectedItem());

            String obs = campoObservacoes.getText();
            sucesso = aplicacao.getControladorUtilizador().registarFuncionario(
                    nome, email, user, pass, nif, tel, morada, esp, dataNormalizada, obs);
        } else {
            String nif = campoNifCliente.getText();
            String tel = campoTelCliente.getText();
            String morada = campoMoradaCliente.getText();
            String setor = campoSetor.getText();
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

            String obs = campoObservacoes.getText();
            sucesso = aplicacao.getControladorUtilizador().registarCliente(
                    nome, email, user, pass, nif, tel, morada, setor, escalao, obs);
        }

        if (sucesso) {
            if (fotoSelecionada != null) {
                Utilizador uNovo = aplicacao.getControladorUtilizador().efetuarLogin(user, pass);
                if (uNovo != null) {
                    String nomeOriginal = fotoSelecionada.getName();
                    String extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf('.'));
                    String nomeDestino = "user_" + uNovo.getIdUtilizador() + extensao;
                    File destino = new File("fotos", nomeDestino);
                    try {
                        File pastaFotos = new File("fotos");
                        if (!pastaFotos.exists())
                            pastaFotos.mkdirs();
                        Files.copy(fotoSelecionada.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        aplicacao.getControladorUtilizador().atualizarFoto(uNovo.getIdUtilizador(),
                                "fotos" + File.separator + nomeDestino, "Sistema");
                    } catch (IOException ex) {
                    }
                }
            } else {
                Utilizador uNovo = aplicacao.getControladorUtilizador().efetuarLogin(user, pass);
                if (uNovo != null) {
                    aplicacao.getControladorUtilizador().atualizarFoto(uNovo.getIdUtilizador(), "fotos/geral.png",
                            "Sistema");
                }
            }
            Utilitarios.mostrarSucesso(this, "Registo efetuado com sucesso! Já pode iniciar sessão.");
            limparCampos();
            fotoSelecionada = null;
            Utilitarios.atualizarImagemPainel(painelFoto, null);
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
