package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import controlador.ControladorNotificacao;
import controlador.ControladorReparacao;
import controlador.ControladorUtilizador;
import model.Notificacao;
import model.Reparacao;
import model.Utilizador;
import util.Validacoes;
import Enums.EstadoReparacao;
import Enums.EstadoUtilizador;
import Enums.CategoriaNotificacao;

/**
 * Painel com as funcionalidades disponíveis para o utilizador do tipo
 * Funcionário.
 * Inclui gestão de reparações atribuídas, perfil, notificações e consultas.
 * A manipulação de peças e testes NÃO é incluída na GUI (R12).
 *
 * @author Santiago e Hugo
 * @version 1.0
 */
public class PainelFuncionario extends JPanel implements ActionListener {

    private AplicacaoGUI aplicacao;
    private Utilizador utilizadorLogado;
    private ControladorUtilizador cUtilizador;
    private ControladorReparacao cReparacao;
    private ControladorNotificacao cNotificacao;

    private JPanel painelConteudo;
    private JPanel painelAtualConteudo;

    private JButton btnPedidosNovos, btnEmCurso, btnPerfil, btnListarRep;
    private JButton btnPesquisarRep, btnNotificacoes, btnRemocao, btnLogout;

    /**
     * Constrói o painel do funcionário.
     * 
     * @param aplicacao referência para a aplicação principal
     */
    public PainelFuncionario(AplicacaoGUI aplicacao) {
        this.aplicacao = aplicacao;
        this.cUtilizador = aplicacao.getControladorUtilizador();
        this.cReparacao = aplicacao.getControladorReparacao();
        this.cNotificacao = aplicacao.getControladorNotificacao();
        setLayout(new BorderLayout());

        JPanel painelMenu = new JPanel();
        painelMenu.setLayout(new BoxLayout(painelMenu, BoxLayout.Y_AXIS));
        painelMenu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelMenu.setPreferredSize(new Dimension(220, 0));

        JLabel titulo = new JLabel("Menu Funcionário");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelMenu.add(titulo);
        painelMenu.add(Box.createVerticalStrut(10));

        btnPedidosNovos = criarBotaoMenu("Pedidos Atribuídos", "Ver pedidos de reparação atribuídos");
        btnEmCurso = criarBotaoMenu("Reparações em Curso", "Trabalhar em reparações em andamento");
        btnPerfil = criarBotaoMenu("O Meu Perfil", "Editar dados pessoais");
        btnListarRep = criarBotaoMenu("Listar Reparações", "Listar reparações ordenadas");
        btnPesquisarRep = criarBotaoMenu("Pesquisar Reparações", "Pesquisar reparações");
        btnNotificacoes = criarBotaoMenu("Notificações", "Ver as minhas notificações");
        btnRemocao = criarBotaoMenu("Solicitar Remoção", "Solicitar remoção da conta");
        btnLogout = criarBotaoMenu("Logout", "Terminar sessão");

        JButton[] botoes = { btnPedidosNovos, btnEmCurso, btnPerfil, btnListarRep,
                btnPesquisarRep, btnNotificacoes, btnRemocao, btnLogout };
        int idx = 0;
        while (idx < botoes.length) {
            painelMenu.add(botoes[idx]);
            painelMenu.add(Box.createVerticalStrut(3));
            idx++;
        }
        add(painelMenu, BorderLayout.WEST);

        // Painel de conteúdo dinâmico (navegação por remove/add/revalidate/repaint)
        painelConteudo = new JPanel(new BorderLayout());
        painelConteudo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelAtualConteudo = new JPanel();
        painelAtualConteudo.add(new JLabel("Selecione uma opção do menu.", SwingConstants.CENTER));
        painelConteudo.add(painelAtualConteudo, BorderLayout.CENTER);
        add(painelConteudo, BorderLayout.CENTER);
    }

    public void setUtilizadorLogado(Utilizador u) {
        this.utilizadorLogado = u;
    }

    /**
     * Cria um botão para o menu lateral com texto, tooltip e listener.
     *
     * @param texto   texto do botão
     * @param tooltip texto de ajuda ao passar o rato
     * @return botão configurado
     */
    private JButton criarBotaoMenu(String texto, String tooltip) {
        JButton btn = new JButton(texto);
        btn.setToolTipText(tooltip);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(200, 30));
        btn.addActionListener(this);
        return btn;
    }

    /**
     * Processa os eventos dos botões do menu lateral.
     *
     * @param e evento de ação gerado pelo botão clicado
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (utilizadorLogado == null)
            return;
        Object src = e.getSource();
        if (src.equals(btnLogout)) {
            cUtilizador.registarFimSessao(utilizadorLogado.getLogin());
            aplicacao.terminarSessao();
            return;
        }
        if (src.equals(btnPedidosNovos))
            mostrarPedidosAtribuidos();
        else if (src.equals(btnEmCurso))
            mostrarEmCurso();
        else if (src.equals(btnPerfil))
            mostrarPerfil();
        else if (src.equals(btnListarRep))
            mostrarListarRep();
        else if (src.equals(btnPesquisarRep))
            mostrarPesquisarRep();
        else if (src.equals(btnNotificacoes))
            mostrarNotificacoes();
        else if (src.equals(btnRemocao))
            solicitarRemocao();
    }

    /**
     * Apresenta os pedidos de reparação ACEITES (novos pedidos atribuídos).
     */
    private void mostrarPedidosAtribuidos() {
        JPanel p = new JPanel(new BorderLayout(5, 5));
        p.setBorder(BorderFactory.createTitledBorder("Pedidos de Reparação Atribuídos"));

        ArrayList<Reparacao> lista = cReparacao.listarReparacoesPorEstado(
                utilizadorLogado.getIdUtilizador(), EstadoReparacao.ACEITE.name());
        JScrollPane scrollTabela = Utilitarios.criarTabela(
                new String[] { "ID", "Número", "Data Criação", "Estado" },
                converterReparacoes(lista));
        p.add(scrollTabela, BorderLayout.CENTER);

        JPanel painelBtns = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAceitar = new JButton("Aceitar e Iniciar");
        btnAceitar.setToolTipText("Aceitar o pedido e iniciar a reparação");
        btnAceitar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                int id = Utilitarios.obterIdSelecionado(scrollTabela);
                if (id == -1) {
                    Utilitarios.mostrarErro(PainelFuncionario.this, "Selecione um pedido!");
                    return;
                }
                cReparacao.gerirEstadoReparacao(id, utilizadorLogado.getIdUtilizador(),
                        EstadoReparacao.DECORRER.name(), utilizadorLogado.getLogin());
                Utilitarios.mostrarSucesso(PainelFuncionario.this, "Reparação iniciada!");
                mostrarPedidosAtribuidos();
            }
        });
        JButton btnRejeitar = new JButton("Rejeitar");
        btnRejeitar.setToolTipText("Rejeitar o pedido e devolver ao gestor");
        btnRejeitar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                int id = Utilitarios.obterIdSelecionado(scrollTabela);
                if (id == -1) {
                    Utilitarios.mostrarErro(PainelFuncionario.this, "Selecione um pedido!");
                    return;
                }
                cReparacao.rejeitarReparacaoPorFuncionario(id, utilizadorLogado.getIdUtilizador(),
                        utilizadorLogado.getLogin());
                Utilitarios.mostrarSucesso(PainelFuncionario.this, "Pedido rejeitado e devolvido ao gestor.");
                mostrarPedidosAtribuidos();
            }
        });
        painelBtns.add(btnAceitar);
        painelBtns.add(btnRejeitar);
        p.add(painelBtns, BorderLayout.SOUTH);
        trocarConteudo(p, "pedidosNovos");
    }

    /**
     * Apresenta os pedidos de reparação atualmente EM_CURSO ou a AGUARDAR_PECA.
     */
    private void mostrarEmCurso() {
        JPanel p = new JPanel(new BorderLayout(5, 5));
        p.setBorder(BorderFactory.createTitledBorder("Reparações em Curso (sem peças/testes na GUI — R12)"));

        ArrayList<Reparacao> lista = cReparacao.listarReparacoesPorEstado(
                utilizadorLogado.getIdUtilizador(), EstadoReparacao.DECORRER.name());
        JScrollPane scrollTabela = Utilitarios.criarTabela(
                new String[] { "ID", "Número", "Data Criação", "Estado" },
                converterReparacoes(lista));
        p.add(scrollTabela, BorderLayout.CENTER);

        JPanel painelConclusao = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField campoCusto = new JTextField(8);
        campoCusto.setToolTipText("Custo final da reparação em euros");
        JTextArea campoObs = new JTextArea(2, 20);
        campoObs.setToolTipText("Observações sobre o trabalho realizado (R4)");
        campoObs.setLineWrap(true);
        JButton btnConcluir = new JButton("Concluir Reparação");
        btnConcluir.setToolTipText("Finalizar a reparação selecionada");

        painelConclusao.add(new JLabel("Custo (€):"));
        painelConclusao.add(campoCusto);
        painelConclusao.add(new JLabel("Obs.:"));
        painelConclusao.add(new JScrollPane(campoObs));
        painelConclusao.add(btnConcluir);

        btnConcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                int id = Utilitarios.obterIdSelecionado(scrollTabela);
                if (id == -1) {
                    Utilitarios.mostrarErro(PainelFuncionario.this, "Selecione uma reparação!");
                    return;
                }
                double custo;
                try {
                    custo = Double.parseDouble(campoCusto.getText());
                } catch (NumberFormatException ex) {
                    Utilitarios.mostrarErro(PainelFuncionario.this, "Custo inválido!");
                    return;
                }

                // Procurar a reparação na lista
                Reparacao repSelecionada = null;
                Iterator<Reparacao> it = lista.iterator();
                while (it.hasNext()) {
                    Reparacao r = it.next();
                    if (r.getIdReparacao() == id) {
                        repSelecionada = r;
                        break;
                    }
                }
                if (repSelecionada != null) {
                    cReparacao.concluirReparacaoFinal(repSelecionada, custo,
                            campoObs.getText(), utilizadorLogado.getLogin());
                    Utilitarios.mostrarSucesso(PainelFuncionario.this, "Reparação concluída com sucesso!");
                    mostrarEmCurso();
                }
            }
        });
        p.add(painelConclusao, BorderLayout.SOUTH);
        trocarConteudo(p, "emCurso");
    }

    /**
     * Apresenta o painel de edição do perfil do funcionário.
     */
    private void mostrarPerfil() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createTitledBorder("O Meu Perfil"));

        // Foto de perfil (R2)
        final JPanel[] painelFotoRef = new JPanel[1];
        painelFotoRef[0] = Utilitarios.criarPainelFoto(utilizadorLogado.getFotoPath(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                String novaFoto = Utilitarios.escolherFicheiroImagem(PainelFuncionario.this,
                        utilizadorLogado.getIdUtilizador());
                if (novaFoto != null) {
                    boolean ok = cUtilizador.atualizarFoto(utilizadorLogado.getIdUtilizador(),
                            novaFoto, utilizadorLogado.getLogin());
                    if (ok) {
                        utilizadorLogado.setFotoPath(novaFoto);
                        Utilitarios.atualizarImagemPainel(painelFotoRef[0], novaFoto);
                        Utilitarios.mostrarSucesso(PainelFuncionario.this, "Foto atualizada com sucesso!");
                    }
                }
            }
        });
        p.add(painelFotoRef[0]);

        JTextField cEmail = new JTextField(utilizadorLogado.getEmail(), 20);
        JPasswordField cPass = new JPasswordField(20);
        JTextField cTel = new JTextField(20);
        JTextField cMorada = new JTextField(20);

        p.add(new JLabel("Nome: " + utilizadorLogado.getNome()));
        p.add(Box.createVerticalStrut(5));
        p.add(Utilitarios.criarCampoFormulario("Novo Email:", cEmail, "Novo endereço de email"));
        p.add(Box.createVerticalStrut(5));
        p.add(Utilitarios.criarCampoFormulario("Nova Password:", cPass, "Nova palavra-passe"));
        p.add(Box.createVerticalStrut(5));
        p.add(Utilitarios.criarCampoFormulario("Novo Telefone:", cTel, "Novo telefone (9 dígitos)"));
        p.add(Box.createVerticalStrut(5));
        p.add(Utilitarios.criarCampoFormulario("Nova Morada:", cMorada, "Nova morada"));
        p.add(Box.createVerticalStrut(10));

        JButton btnGuardar = new JButton("Guardar Alterações");
        btnGuardar.setToolTipText("Guardar as alterações ao perfil");
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                String email = cEmail.getText();
                String pass = new String(cPass.getPassword());
                if (!Validacoes.emailValido(email)) {
                    Utilitarios.mostrarErro(PainelFuncionario.this, "Email inválido!");
                    return;
                }
                if (pass.isEmpty()) {
                    Utilitarios.mostrarErro(PainelFuncionario.this, "Password não pode estar vazia!");
                    return;
                }
                boolean ok = cUtilizador.atualizarPerfilFuncionario(utilizadorLogado.getIdUtilizador(),
                        email, pass, cTel.getText(), cMorada.getText(), utilizadorLogado.getLogin());
                if (ok)
                    Utilitarios.mostrarSucesso(PainelFuncionario.this, "Perfil atualizado!");
                else
                    Utilitarios.mostrarErro(PainelFuncionario.this, "Erro ao atualizar perfil.");
            }
        });
        p.add(btnGuardar);
        trocarConteudo(p, "perfil");
    }

    /**
     * Apresenta o painel com a listagem de todas as reparações associadas ao
     * funcionário.
     */
    private void mostrarListarRep() {
        JPanel p = new JPanel(new BorderLayout(5, 5));
        p.setBorder(BorderFactory.createTitledBorder("Listar Reparações"));

        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnListar = new JButton("Listar");
        btnListar.setToolTipText("Listar reparações");
        filtros.add(btnListar);
        p.add(filtros, BorderLayout.NORTH);

        JScrollPane scrollTabela = Utilitarios.criarTabela(
                new String[] { "ID", "Número", "Data", "Estado" }, new Object[][] {});
        p.add(scrollTabela, BorderLayout.CENTER);

        btnListar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                ArrayList<Reparacao> lista = cReparacao.listarReparacoesFuncionarioOrdenadas(
                        utilizadorLogado.getIdUtilizador(), 1, true);
                Utilitarios.atualizarTabela(scrollTabela, new String[] { "ID", "Número", "Data", "Estado" },
                        converterReparacoes(lista));
            }
        });
        trocarConteudo(p, "listarRep");
    }

    /**
     * Apresenta o painel para pesquisar reparações associadas ao funcionário.
     */
    private void mostrarPesquisarRep() {
        JPanel p = new JPanel(new BorderLayout(5, 5));
        p.setBorder(BorderFactory.createTitledBorder("Pesquisar Reparações"));

        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<String> comboCrit = new JComboBox<>(new String[] { "Número", "Estado" });
        comboCrit.setToolTipText("Critério de pesquisa");
        JTextField campoTermo = new JTextField(15);
        campoTermo.setToolTipText("Termo a pesquisar");
        JButton btnPesq = new JButton("Pesquisar");
        btnPesq.setToolTipText("Executar pesquisa");
        filtros.add(new JLabel("Critério:"));
        filtros.add(comboCrit);
        filtros.add(campoTermo);
        filtros.add(btnPesq);
        p.add(filtros, BorderLayout.NORTH);

        JScrollPane scrollTabela = Utilitarios.criarTabela(
                new String[] { "ID", "Número", "Data", "Estado" }, new Object[][] {});
        p.add(scrollTabela, BorderLayout.CENTER);

        btnPesq.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                ArrayList<Reparacao> res = cReparacao.pesquisarReparacoesFuncionario(
                        utilizadorLogado.getIdUtilizador(), comboCrit.getSelectedIndex() + 1,
                        campoTermo.getText());
                Utilitarios.atualizarTabela(scrollTabela, new String[] { "ID", "Número", "Data", "Estado" },
                        converterReparacoes(res));
            }
        });
        trocarConteudo(p, "pesqRep");
    }

    /**
     * Apresenta o dashboard de notificações do funcionário.
     */
    private void mostrarNotificacoes() {
        JPanel p = new JPanel(new BorderLayout(5, 5));
        p.setBorder(BorderFactory.createTitledBorder("Notificações"));

        ArrayList<Notificacao> lista = cNotificacao.obterNotificacoes(utilizadorLogado.getIdUtilizador());
        Object[][] dados = new Object[lista.size()][3];
        Iterator<Notificacao> it = lista.iterator();
        int i = 0;
        while (it.hasNext()) {
            Notificacao n = it.next();
            dados[i] = new Object[] { n.getDataCriacao(), n.getEstado(), n.getMensagem() };
            i++;
        }
        JScrollPane scrollTabela = Utilitarios.criarTabela(new String[] { "Data", "Estado", "Mensagem" }, dados);
        p.add(scrollTabela, BorderLayout.CENTER);

        JButton btnMarcar = new JButton("Marcar Todas como Lidas");
        btnMarcar.setToolTipText("Marcar notificações como lidas");
        btnMarcar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                cNotificacao.marcarComoLidas(utilizadorLogado.getIdUtilizador());
                Utilitarios.mostrarSucesso(PainelFuncionario.this, "Notificações marcadas como lidas!");
                mostrarNotificacoes();
            }
        });
        p.add(btnMarcar, BorderLayout.SOUTH);
        trocarConteudo(p, "notifs");
    }

    /**
     * Solicita a remoção da conta do funcionário, notificando os gestores.
     */
    private void solicitarRemocao() {
        if (Utilitarios.confirmar(this, "Tens a certeza que queres solicitar a remoção da conta?")) {
            cUtilizador.mudarEstadoConta(utilizadorLogado.getIdUtilizador(), EstadoUtilizador.AGUARDA_REMOCAO,
                    utilizadorLogado.getLogin(), "Funcionário solicitou remoção da conta.");
            cNotificacao.gerarNotificacaoParaGestores(
                    "O Funcionário '" + utilizadorLogado.getLogin() + "' solicitou a remoção.",
                    CategoriaNotificacao.REMOCAO);
            Utilitarios.mostrarInfo(PainelFuncionario.this, "Pedido submetido! Sessão terminada.");
            aplicacao.terminarSessao();
        }
    }

    private Object[][] converterReparacoes(ArrayList<Reparacao> lista) {
        Object[][] dados = new Object[lista.size()][4];
        Iterator<Reparacao> it = lista.iterator();
        int i = 0;
        while (it.hasNext()) {
            Reparacao r = it.next();
            dados[i] = new Object[] { r.getIdReparacao(), r.getNumReparacao(), r.getDataCriacao(), r.getEstado() };
            i++;
        }
        return dados;
    }

    /**
     * Troca o painel de conteúdo visível usando remove/add/revalidate/repaint.
     *
     * @param novoPainel painel a apresentar
     * @param nome       nome identificador do painel
     */
    private void trocarConteudo(JPanel novoPainel, String nome) {
        painelConteudo.remove(painelAtualConteudo);
        painelAtualConteudo = novoPainel;
        painelConteudo.add(painelAtualConteudo, BorderLayout.CENTER);
        painelConteudo.revalidate();
        painelConteudo.repaint();
    }
}
