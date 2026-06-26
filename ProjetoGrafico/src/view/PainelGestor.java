package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import controlador.ControladorEquipamento;
import controlador.ControladorNotificacao;
import controlador.ControladorReparacao;
import controlador.ControladorUtilizador;
import model.Equipamento;
import model.Log;
import model.Notificacao;
import model.Reparacao;
import model.Utilizador;
import Enums.CategoriaNotificacao;
import Enums.EstadoReparacao;
import Enums.EstadoUtilizador;
import Enums.TipoUtilizador;
import util.Validacoes;

/**
 * Painel com as funcionalidades disponíveis para o utilizador do tipo Gestor.
 * Inclui gestão de contas, reparações, arquivamento, listagens, notificações,
 * logs de auditoria e pedidos de remoção de conta.
 *
 * @author Santiago e Hugo
 * @version 1.0
 */
public class PainelGestor extends JPanel implements ActionListener {

    private Aplicacao aplicacao;
    private Utilizador utilizadorLogado;
    private ControladorUtilizador cUtilizador;
    private ControladorReparacao cReparacao;
    private ControladorEquipamento cEquipamento;
    private ControladorNotificacao cNotificacao;
    private JPanel painelConteudo;
    private JPanel painelAtualConteudo;
    private JPanel painelFoto;
    private JButton btnGerirRep, btnArquivar, btnEditarUsers;
    private JButton btnListagens, btnNotifs, btnLogs, btnToggleContas;
    private JButton btnPedidosRemocao, btnMinhaRemocao, btnPerfil, btnLogout;

    /**
     * Constrói o painel do gestor.
     *
     * @param aplicacao referência para a aplicação principal
     */
    public PainelGestor(Aplicacao aplicacao) {
        this.aplicacao = aplicacao;
        this.cUtilizador = aplicacao.getControladorUtilizador();
        this.cReparacao = aplicacao.getControladorReparacao();
        this.cEquipamento = aplicacao.getControladorEquipamento();
        this.cNotificacao = aplicacao.getControladorNotificacao();
        setLayout(new BorderLayout());

        JPanel painelMenu = new JPanel();
        painelMenu.setLayout(new GridLayout(12, 1));
        painelMenu.setPreferredSize(new Dimension(220, 600));

        JLabel titulo = new JLabel("Menu Gestor");
        painelMenu.add(titulo);

        btnGerirRep = criarBotaoMenu("Gerir Reparações", "Atribuir reparações a funcionários");
        btnArquivar = criarBotaoMenu("Arquivar Processos", "Arquivar reparações finalizadas");
        btnEditarUsers = criarBotaoMenu("Editar Utilizadores", "Editar dados de utilizadores");
        btnListagens = criarBotaoMenu("Listagens/Pesquisas", "Listar e pesquisar dados");
        btnNotifs = criarBotaoMenu("Notificações", "Dashboard de notificações");
        btnLogs = criarBotaoMenu("Logs", "Consultar registos de auditoria");
        btnToggleContas = criarBotaoMenu("Ativar/Inativar", "Alterar estado de contas");
        btnPedidosRemocao = criarBotaoMenu("Pedidos Remoção", "Gerir pedidos de remoção");
        btnPerfil = criarBotaoMenu("O Meu Perfil", "Editar os dados do perfil");
        btnMinhaRemocao = criarBotaoMenu("Minha Remoção", "Solicitar remoção da minha conta");
        btnLogout = criarBotaoMenu("Logout", "Terminar sessão");

        JButton[] botoes = { btnGerirRep, btnArquivar, btnEditarUsers,
                btnListagens, btnNotifs, btnLogs, btnToggleContas, btnPedidosRemocao,
                btnPerfil, btnMinhaRemocao, btnLogout };
        int idx = 0;
        while (idx < botoes.length) {
            painelMenu.add(botoes[idx]);
            idx++;
        }
        add(painelMenu, BorderLayout.WEST);

        painelConteudo = new JPanel(new BorderLayout());
        painelAtualConteudo = new JPanel();
        painelAtualConteudo.add(new JLabel("Selecione uma opção do menu."));
        painelConteudo.add(painelAtualConteudo, BorderLayout.CENTER);
        add(painelConteudo, BorderLayout.CENTER);
    }

    /**
     * Define o utilizador autenticado.
     * 
     * @param u utilizador logado
     */
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
        btn.addActionListener(this);
        return btn;
    }

    /**
     * Processa os eventos dos botões do menu lateral.
     *
     * @param e evento de ação gerado pelo botão clicado
     */
    public void actionPerformed(ActionEvent e) {
        if (utilizadorLogado == null)
            return;

        if (e.getSource().equals(btnLogout)) {
            cUtilizador.registarFimSessao(utilizadorLogado.getLogin());
            aplicacao.terminarSessao();
            return;
        }
        if (e.getSource().equals(btnGerirRep))
            mostrarGerirReparacoes();
        else if (e.getSource().equals(btnArquivar))
            mostrarArquivar();
        else if (e.getSource().equals(btnEditarUsers))
            mostrarEditarUsers();
        else if (e.getSource().equals(btnListagens))
            mostrarListagens();
        else if (e.getSource().equals(btnNotifs))
            mostrarNotificacoes();
        else if (e.getSource().equals(btnLogs))
            mostrarLogs();
        else if (e.getSource().equals(btnToggleContas))
            mostrarToggleContas();
        else if (e.getSource().equals(btnPedidosRemocao))
            mostrarPedidosRemocao();
        else if (e.getSource().equals(btnPerfil))
            mostrarPerfil();
        else if (e.getSource().equals(btnMinhaRemocao))
            solicitarMinhaRemocao();
    }

    /**
     * Troca o painel de conteúdo visível usando remove/add/revalidate/repaint.
     * 
     * @param novoPainel painel a apresentar
     */
    private void trocarConteudo(JPanel novoPainel) {
        painelConteudo.remove(painelAtualConteudo);
        painelAtualConteudo = novoPainel;
        painelConteudo.add(painelAtualConteudo, BorderLayout.CENTER);
        painelConteudo.revalidate();
        painelConteudo.repaint();
    }

    /**
     * Converte uma lista de reparações para uma matriz de objetos
     * para apresentação numa JTable.
     *
     * @param lista lista de reparações a converter
     * @return matriz de dados com ID, número, data e estado
     */
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
     * Apresenta o painel de gestão de pedidos de reparação pendentes.
     */
    private void mostrarGerirReparacoes() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("Gerir Pedidos de Reparação"));
        ArrayList<Reparacao> pend = cReparacao.listarPedidosPendentes();
        JTable tabela = Utilitarios.criarTabela(new String[] { "ID", "Número", "Data", "Estado" },
                converterReparacoes(pend));
        p.add(new JScrollPane(tabela), BorderLayout.CENTER);
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ArrayList<Utilizador> funcs = cUtilizador.obterFuncionariosAtivos();
        JComboBox<String> comboFunc = new JComboBox<>();
        comboFunc.setToolTipText("Funcionário a atribuir à reparação");
        Iterator<Utilizador> itF = funcs.iterator();
        while (itF.hasNext()) {
            Utilizador f = itF.next();
            comboFunc.addItem(f.getIdUtilizador() + " - " + f.getNome());
        }
        JButton bAceitar = new JButton("Aceitar e Atribuir");
        bAceitar.setToolTipText("Aceitar e atribuir ao funcionário");
        JButton bRejeitar = new JButton("Rejeitar");
        bRejeitar.setToolTipText("Rejeitar o pedido");
        bAceitar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                int id = Utilitarios.obterIdSelecionado(tabela);
                if (id == -1) {
                    Utilitarios.mostrarErro(PainelGestor.this, "Selecione um pedido!");
                    return;
                }
                if (comboFunc.getSelectedItem() == null) {
                    Utilitarios.mostrarErro(PainelGestor.this, "Sem funcionários disponíveis!");
                    return;
                }
                String sel = (String) comboFunc.getSelectedItem();
                int idFunc = Integer.parseInt(sel.split(" - ")[0]);
                cReparacao.gerirEstadoReparacao(id, idFunc, EstadoReparacao.ACEITE.name(), utilizadorLogado.getLogin());
                Utilitarios.mostrarSucesso(PainelGestor.this, "Reparação atribuída!");
                mostrarGerirReparacoes();
            }
        });
        bRejeitar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                int id = Utilitarios.obterIdSelecionado(tabela);
                if (id == -1) {
                    Utilitarios.mostrarErro(PainelGestor.this, "Selecione um pedido!");
                    return;
                }
                cReparacao.gerirEstadoReparacao(id, 0, EstadoReparacao.REJEITADO.name(), utilizadorLogado.getLogin());
                Utilitarios.mostrarSucesso(PainelGestor.this, "Pedido rejeitado!");
                mostrarGerirReparacoes();
            }
        });
        btns.add(new JLabel("Funcionário:"));
        btns.add(comboFunc);
        btns.add(bAceitar);
        btns.add(bRejeitar);
        p.add(btns, BorderLayout.SOUTH);
        trocarConteudo(p);
    }

    /**
     * Apresenta o painel de arquivamento de processos finalizados.
     */
    private void mostrarArquivar() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("Arquivar Processos Finalizados"));
        ArrayList<Reparacao> fin = cReparacao.obterReparacoesProntasAArquivar();
        JTable tabela = Utilitarios.criarTabela(new String[] { "ID", "Número", "Data", "Estado" },
                converterReparacoes(fin));
        p.add(new JScrollPane(tabela), BorderLayout.CENTER);
        JButton bArq = new JButton("Arquivar Selecionado");
        bArq.setToolTipText("Arquivar a reparação selecionada");
        bArq.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                int id = Utilitarios.obterIdSelecionado(tabela);
                if (id == -1) {
                    Utilitarios.mostrarErro(PainelGestor.this, "Selecione uma reparação!");
                    return;
                }
                cReparacao.arquivarReparacao(id);
                Utilitarios.mostrarSucesso(PainelGestor.this, "Processo arquivado!");
                mostrarArquivar();
            }
        });
        p.add(bArq, BorderLayout.SOUTH);
        trocarConteudo(p);
    }

    /**
     * Apresenta o painel de edição de dados de utilizadores.
     */
    private void mostrarEditarUsers() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("Editar Utilizadores"));
        ArrayList<Utilizador> todos = cUtilizador.obterTodosUtilizadores();
        Object[][] d = new Object[todos.size()][5];
        Iterator<Utilizador> it = todos.iterator();
        int i = 0;
        while (it.hasNext()) {
            Utilizador u = it.next();
            d[i] = new Object[] { u.getIdUtilizador(), u.getNome(), u.getLogin(), u.getTipo(), u.getEstado() };
            i++;
        }
        JTable tabela = Utilitarios.criarTabela(new String[] { "ID", "Nome", "Username", "Tipo", "Estado" }, d);
        p.add(new JScrollPane(tabela), BorderLayout.CENTER);
        JPanel form = new JPanel();
        form.setLayout(new GridLayout(5, 1));
        JTextField cNome = new JTextField(10);
        cNome.setToolTipText("Novo nome");
        JTextField cUser = new JTextField(10);
        cUser.setToolTipText("Novo username");
        JTextField cEmail = new JTextField(10);
        cEmail.setToolTipText("Novo email");
        JPasswordField cPass = new JPasswordField(10);
        cPass.setToolTipText("Nova password");
        JButton bSave = new JButton("Guardar");
        bSave.setToolTipText("Guardar alterações");
        bSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                int id = Utilitarios.obterIdSelecionado(tabela);
                if (id == -1) {
                    Utilitarios.mostrarErro(PainelGestor.this, "Selecione uma conta!");
                    return;
                }
                String nome = cNome.getText();
                String email = cEmail.getText();
                String pass = new String(cPass.getPassword());
                String user = cUser.getText();
                if (nome.isEmpty()) {
                    Utilitarios.mostrarErro(PainelGestor.this, "O nome não pode estar vazio!");
                    return;
                }
                if (!Validacoes.emailValido(email)) {
                    Utilitarios.mostrarErro(PainelGestor.this, "Email inválido!");
                    return;
                }
                if (pass.isEmpty()) {
                    Utilitarios.mostrarErro(PainelGestor.this, "Password não pode estar vazia!");
                    return;
                }
                if (user.isEmpty()) {
                    Utilitarios.mostrarErro(PainelGestor.this, "O username não pode estar vazio!");
                    return;
                }
                boolean ok = cUtilizador.atualizarPerfilPeloGestor(id, nome,
                        email, pass, user, utilizadorLogado.getLogin());
                if (ok) {
                    Utilitarios.mostrarSucesso(PainelGestor.this, "Dados atualizados com sucesso!");
                    mostrarEditarUsers();
                } else
                    Utilitarios.mostrarErro(PainelGestor.this,
                            "Erro ao atualizar. Email ou Username pode já estar em uso.");
            }
        });
        form.add(Utilitarios.criarCampoFormulario("Nome:", cNome, "Novo nome"));
        form.add(Utilitarios.criarCampoFormulario("Username:", cUser, "Novo username"));
        form.add(Utilitarios.criarCampoFormulario("Email:", cEmail, "Novo email"));
        form.add(Utilitarios.criarCampoFormulario("Pass:", cPass, "Nova password"));
        JPanel pBtns = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pBtns.add(bSave);
        form.add(pBtns);
        p.add(form, BorderLayout.SOUTH);
        trocarConteudo(p);
    }

    /**
     * Apresenta o painel de listagens e pesquisas com campos dinâmicos.
     * Os campos de pesquisa mostram-se ou escondem-se conforme a opção selecionada.
     */
    private void mostrarListagens() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("Listagens e Pesquisas"));
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<String> comboTipo = new JComboBox<>(new String[] {
                "Utilizadores", "Reparações", "Reparações Não Finalizadas",
                "Pesquisar Reparações", "Pesquisar Utilizadores", "Pesquisar Equipamentos",
                "Reparações por Data", "Todas as Reparações" });
        comboTipo.setToolTipText("Selecione o tipo de listagem ou pesquisa");

        JLabel lblTermo = new JLabel("Termo:");
        JTextField campoTermo = new JTextField(12);
        campoTermo.setToolTipText("Termo de pesquisa");

        JLabel lblDataInicio = new JLabel("Data inicial:");
        JTextField campoDataInicio = new JTextField(10);
        campoDataInicio.setToolTipText("Data inicial (dd/MM/yyyy ou yyyy-MM-dd)");

        JLabel lblDataFim = new JLabel("Data final:");
        JTextField campoDataFim = new JTextField(10);
        campoDataFim.setToolTipText("Data final (dd/MM/yyyy ou yyyy-MM-dd)");

        JButton bExec = new JButton("Executar");
        bExec.setToolTipText("Executar a listagem ou pesquisa");

        top.add(comboTipo);
        top.add(lblTermo);
        top.add(campoTermo);
        top.add(lblDataInicio);
        top.add(campoDataInicio);
        top.add(lblDataFim);
        top.add(campoDataFim);
        top.add(bExec);

        lblTermo.setVisible(false);
        campoTermo.setVisible(false);
        lblDataInicio.setVisible(false);
        campoDataInicio.setVisible(false);
        lblDataFim.setVisible(false);
        campoDataFim.setVisible(false);

        comboTipo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                String sel = (String) comboTipo.getSelectedItem();
                boolean mostrarTermo = sel.equals("Pesquisar Reparações")
                        || sel.equals("Pesquisar Utilizadores")
                        || sel.equals("Pesquisar Equipamentos");
                boolean mostrarDatas = sel.equals("Reparações por Data");

                lblTermo.setVisible(mostrarTermo);
                campoTermo.setVisible(mostrarTermo);
                lblDataInicio.setVisible(mostrarDatas);
                campoDataInicio.setVisible(mostrarDatas);
                lblDataFim.setVisible(mostrarDatas);
                campoDataFim.setVisible(mostrarDatas);

                top.revalidate();
                top.repaint();
            }
        });

        p.add(top, BorderLayout.NORTH);
        JTable tabela = Utilitarios.criarTabela(new String[] { "Resultado" }, new Object[][] {});
        p.add(new JScrollPane(tabela), BorderLayout.CENTER);

        bExec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                String sel = (String) comboTipo.getSelectedItem();
                String termo = campoTermo.getText();
                if (sel.equals("Utilizadores")) {
                    ArrayList<Utilizador> l = cUtilizador.listarUtilizadoresOrdenados(true);
                    Object[][] dd = new Object[l.size()][5];
                    Iterator<Utilizador> it2 = l.iterator();
                    int j = 0;
                    while (it2.hasNext()) {
                        Utilizador u = it2.next();
                        dd[j] = new Object[] { u.getIdUtilizador(), u.getNome(), u.getLogin(), u.getTipo(),
                                u.getEstado() };
                        j++;
                    }
                    Utilitarios.atualizarTabela(tabela, new String[] { "ID", "Nome", "Username", "Tipo", "Estado" },
                            dd);
                } else if (sel.equals("Reparações")) {
                    ArrayList<Reparacao> l = cReparacao.listarReparacoesOrdenadas(1, true);
                    Utilitarios.atualizarTabela(tabela, new String[] { "ID", "Número", "Data", "Estado" },
                            converterReparacoes(l));
                } else if (sel.equals("Reparações Não Finalizadas")) {
                    ArrayList<Reparacao> l = cReparacao.listarReparacoesNaoFinalizadas();
                    Utilitarios.atualizarTabela(tabela, new String[] { "ID", "Número", "Data", "Estado" },
                            converterReparacoes(l));
                } else if (sel.equals("Pesquisar Reparações")) {
                    ArrayList<Reparacao> l;
                    if (termo.isEmpty()) {
                        l = cReparacao.listarReparacoesOrdenadas(1, true);
                    } else {
                        ArrayList<Reparacao> r1 = cReparacao.pesquisarReparacoes(1, termo);
                        ArrayList<Reparacao> r2 = cReparacao.pesquisarReparacoes(2, termo);
                        ArrayList<Reparacao> r3 = cReparacao.pesquisarReparacoes(3, termo);
                        l = new ArrayList<Reparacao>();
                        l.addAll(r1);
                        Iterator<Reparacao> it2 = r2.iterator();
                        while (it2.hasNext()) {
                            Reparacao r = it2.next();
                            boolean existe = false;
                            Iterator<Reparacao> itCheck = l.iterator();
                            while (itCheck.hasNext()) {
                                if (itCheck.next().getIdReparacao() == r.getIdReparacao()) {
                                    existe = true;
                                }
                            }
                            if (!existe)
                                l.add(r);
                        }
                        Iterator<Reparacao> it3 = r3.iterator();
                        while (it3.hasNext()) {
                            Reparacao r = it3.next();
                            boolean existe = false;
                            Iterator<Reparacao> itCheck = l.iterator();
                            while (itCheck.hasNext()) {
                                if (itCheck.next().getIdReparacao() == r.getIdReparacao()) {
                                    existe = true;
                                }
                            }
                            if (!existe)
                                l.add(r);
                        }
                    }
                    Utilitarios.atualizarTabela(tabela, new String[] { "ID", "Número", "Data", "Estado" },
                            converterReparacoes(l));
                } else if (sel.equals("Pesquisar Utilizadores")) {
                    ArrayList<Utilizador> l;
                    if (termo.isEmpty()) {
                        l = cUtilizador.listarUtilizadoresOrdenados(true);
                    } else {
                        l = cUtilizador.pesquisarUtilizadores(1, termo);
                    }
                    Object[][] dd = new Object[l.size()][5];
                    Iterator<Utilizador> it2 = l.iterator();
                    int j = 0;
                    while (it2.hasNext()) {
                        Utilizador u = it2.next();
                        dd[j] = new Object[] { u.getIdUtilizador(), u.getNome(), u.getLogin(), u.getTipo(),
                                u.getEstado() };
                        j++;
                    }
                    Utilitarios.atualizarTabela(tabela, new String[] { "ID", "Nome", "Username", "Tipo", "Estado" },
                            dd);
                } else if (sel.equals("Pesquisar Equipamentos")) {
                    ArrayList<Equipamento> l;
                    if (termo.isEmpty()) {
                        l = cEquipamento.pesquisarEquipamentos(1, "");
                    } else {
                        l = cEquipamento.pesquisarEquipamentos(1, termo);
                    }
                    Object[][] dd = new Object[l.size()][4];
                    Iterator<Equipamento> it2 = l.iterator();
                    int j = 0;
                    while (it2.hasNext()) {
                        Equipamento eq = it2.next();
                        dd[j] = new Object[] { eq.getIdEquipamento(), eq.getMarca(), eq.getModelo(), eq.getSku() };
                        j++;
                    }
                    Utilitarios.atualizarTabela(tabela, new String[] { "ID", "Marca", "Modelo", "SKU" }, dd);
                } else if (sel.equals("Reparações por Data")) {
                    String dataInicio = Validacoes.normalizarData(campoDataInicio.getText());
                    String dataFim = Validacoes.normalizarData(campoDataFim.getText());
                    ArrayList<Reparacao> l;
                    if (dataInicio == null || dataFim == null) {
                        Utilitarios.mostrarErro(PainelGestor.this,
                                "Data inválida! Use formatos como dd/MM/yyyy ou yyyy-MM-dd.");
                        return;
                    } else {
                        l = cReparacao.pesquisarReparacoesPorData(dataInicio, dataFim);
                    }
                    Utilitarios.atualizarTabela(tabela, new String[] { "ID", "Número", "Data", "Estado" },
                            converterReparacoes(l));
                } else if (sel.equals("Todas as Reparações")) {
                    ArrayList<Reparacao> l = cReparacao.obterTodasReparacoes();
                    Utilitarios.atualizarTabela(tabela, new String[] { "ID", "Número", "Data", "Estado" },
                            converterReparacoes(l));
                }
            }
        });
        trocarConteudo(p);
    }

    /**
     * Apresenta o dashboard de notificações.
     * Permite visualizar e marcar notificações como lidas, filtradas por categoria.
     */
    private void mostrarNotificacoes() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("Dashboard de Notificações"));
        int id = utilizadorLogado.getIdUtilizador();
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<String> comboCat = new JComboBox<>(
                new String[] { "REGISTO", "REPARACAO", "STOCK", "PRAZO", "REJEICAO", "REMOCAO", "GERAL" });
        comboCat.setToolTipText("Selecione a categoria de notificações");
        JButton bVer = new JButton("Ver");
        bVer.setToolTipText("Ver notificações da categoria");
        JButton bMarcar = new JButton("Marcar como Lidas");
        bMarcar.setToolTipText("Marcar como lidas");
        top.add(new JLabel("Categoria:"));
        top.add(comboCat);
        top.add(bVer);
        top.add(bMarcar);
        CategoriaNotificacao[] categorias = CategoriaNotificacao.values();
        int idxCat = 0;
        while (idxCat < categorias.length) {
            int n = cNotificacao.contarNaoLidasPorCategoria(id, categorias[idxCat]);
            if (n > 0)
                top.add(new JLabel(" " + categorias[idxCat].name() + ":" + n));
            idxCat++;
        }
        p.add(top, BorderLayout.NORTH);
        JTable tabela = Utilitarios.criarTabela(new String[] { "Data", "Estado", "Mensagem" }, new Object[][] {});
        p.add(new JScrollPane(tabela), BorderLayout.CENTER);
        bVer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                CategoriaNotificacao cat = CategoriaNotificacao.valueOf((String) comboCat.getSelectedItem());
                ArrayList<Notificacao> l = cNotificacao.obterNotificacoesPorCategoria(id, cat);
                Object[][] dd = new Object[l.size()][3];
                Iterator<Notificacao> it2 = l.iterator();
                int j = 0;
                while (it2.hasNext()) {
                    Notificacao n = it2.next();
                    dd[j] = new Object[] { n.getDataCriacao(), n.getEstado(), n.getMensagem() };
                    j++;
                }
                Utilitarios.atualizarTabela(tabela, new String[] { "Data", "Estado", "Mensagem" }, dd);
            }
        });
        bMarcar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                CategoriaNotificacao cat = CategoriaNotificacao.valueOf((String) comboCat.getSelectedItem());
                cNotificacao.marcarComoLidasPorCategoria(id, cat);
                Utilitarios.mostrarSucesso(PainelGestor.this, "Notificações marcadas como lidas!");
                mostrarNotificacoes();
            }
        });
        trocarConteudo(p);
    }

    /**
     * Apresenta os registos de auditoria (logs) do sistema.
     * Permite visualizar todos os logs ou pesquisar por username específico.
     */
    private void mostrarLogs() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("Registos de Auditoria (Logs)"));
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField campoUser = new JTextField(12);
        campoUser.setToolTipText("Username a pesquisar (deixe vazio para todos)");
        JButton bPesq = new JButton("Pesquisar");
        bPesq.setToolTipText("Pesquisar logs por username");
        JButton bTodos = new JButton("Todos");
        bTodos.setToolTipText("Listar todos os logs");
        top.add(new JLabel("Username:"));
        top.add(campoUser);
        top.add(bPesq);
        top.add(bTodos);
        p.add(top, BorderLayout.NORTH);
        JTable tabela = Utilitarios.criarTabela(new String[] { "Data", "Hora", "Utilizador", "Ação" },
                new Object[][] {});
        p.add(new JScrollPane(tabela), BorderLayout.CENTER);
        ActionListener carregarLogs = new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                ArrayList<Log> lista;
                if (ev.getSource().equals(bPesq) && !campoUser.getText().isEmpty())
                    lista = cUtilizador.pesquisarLogsPorUtilizador(campoUser.getText());
                else
                    lista = cUtilizador.listarTodosLogs();
                Object[][] dd = new Object[lista.size()][4];
                Iterator<Log> it2 = lista.iterator();
                int j = 0;
                while (it2.hasNext()) {
                    Log l = it2.next();
                    dd[j] = new Object[] { l.getData(), l.getHora(), l.getUtilizador(), l.getAcao() };
                    j++;
                }
                Utilitarios.atualizarTabela(tabela, new String[] { "Data", "Hora", "Utilizador", "Ação" }, dd);
            }
        };
        bPesq.addActionListener(carregarLogs);
        bTodos.addActionListener(carregarLogs);
        trocarConteudo(p);
    }

    /**
     * Apresenta o painel para alternar o estado de contas (Ativo/Inativo).
     * O Gestor pode suspender (inativar) contas por mau comportamento ou reativar.
     */
    private void mostrarToggleContas() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("Ativar / Inativar Contas"));
        ArrayList<Utilizador> todos = cUtilizador.obterTodosUtilizadores();
        Object[][] d = new Object[todos.size()][4];
        Iterator<Utilizador> it = todos.iterator();
        int i = 0;
        while (it.hasNext()) {
            Utilizador u = it.next();
            d[i] = new Object[] { u.getIdUtilizador(), u.getLogin(), u.getTipo(), u.getEstado() };
            i++;
        }
        JTable tabela = Utilitarios.criarTabela(new String[] { "ID", "Username", "Tipo", "Estado" }, d);
        p.add(new JScrollPane(tabela), BorderLayout.CENTER);
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton bAtiv = new JButton("Tornar ATIVO");
        bAtiv.setToolTipText("Mudar conta para estado ATIVO");
        JButton bInat = new JButton("Tornar INATIVO");
        bInat.setToolTipText("Mudar conta para estado INATIVO");
        bAtiv.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                int id = Utilitarios.obterIdSelecionado(tabela);
                if (id == -1) {
                    Utilitarios.mostrarErro(PainelGestor.this, "Selecione uma conta!");
                    return;
                }
                if (id == utilizadorLogado.getIdUtilizador()) {
                    Utilitarios.mostrarErro(PainelGestor.this, "Não podes alterar a tua própria sessão!");
                    return;
                }
                cUtilizador.mudarEstadoConta(id, EstadoUtilizador.ATIVO, utilizadorLogado.getLogin(),
                        "Ativou conta ID:" + id);
                Utilitarios.mostrarSucesso(PainelGestor.this, "Conta ativada!");
                mostrarToggleContas();
            }
        });
        bInat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                int id = Utilitarios.obterIdSelecionado(tabela);
                if (id == -1) {
                    Utilitarios.mostrarErro(PainelGestor.this, "Selecione uma conta!");
                    return;
                }
                if (id == utilizadorLogado.getIdUtilizador()) {
                    Utilitarios.mostrarErro(PainelGestor.this, "Não podes inativar a tua própria sessão!");
                    return;
                }
                cUtilizador.mudarEstadoConta(id, EstadoUtilizador.INATIVO, utilizadorLogado.getLogin(),
                        "Inativou conta ID:" + id);
                Utilitarios.mostrarSucesso(PainelGestor.this, "Conta inativada!");
                mostrarToggleContas();
            }
        });
        btns.add(bAtiv);
        btns.add(bInat);
        p.add(btns, BorderLayout.SOUTH);
        trocarConteudo(p);
    }

    /**
     * Apresenta os pedidos de remoção de conta solicitados pelos utilizadores.
     * Permite aceitar (e anonimizar/apagar) ou recusar o pedido.
     */
    private void mostrarPedidosRemocao() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("Pedidos de Remoção de Conta"));
        ArrayList<Utilizador> todos = cUtilizador.obterTodosUtilizadores();
        ArrayList<Utilizador> pedidos = new ArrayList<>();
        Iterator<Utilizador> it = todos.iterator();
        while (it.hasNext()) {
            Utilizador u = it.next();
            if (u.getEstado() == EstadoUtilizador.AGUARDA_REMOCAO)
                pedidos.add(u);
        }
        Object[][] d = new Object[pedidos.size()][3];
        Iterator<Utilizador> it2 = pedidos.iterator();
        int i = 0;
        while (it2.hasNext()) {
            Utilizador u = it2.next();
            d[i] = new Object[] { u.getIdUtilizador(), u.getLogin(), u.getTipo() };
            i++;
        }
        JTable tabela = Utilitarios.criarTabela(new String[] { "ID", "Username", "Tipo" }, d);
        p.add(new JScrollPane(tabela), BorderLayout.CENTER);
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton bAceitar = new JButton("Aceitar Remoção");
        bAceitar.setToolTipText("Aceitar e apagar dados");
        JButton bRecusar = new JButton("Recusar");
        bRecusar.setToolTipText("Recusar e devolver ao estado ATIVO");
        bAceitar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                int id = Utilitarios.obterIdSelecionado(tabela);
                if (id == -1) {
                    Utilitarios.mostrarErro(PainelGestor.this, "Selecione uma conta!");
                    return;
                }
                cNotificacao.gerarNotificacao(id, "O teu pedido de remoção foi aceite.", CategoriaNotificacao.GERAL);
                cUtilizador.apagarDadosPessoais(id, utilizadorLogado.getLogin());
                Utilitarios.mostrarSucesso(PainelGestor.this, "Conta removida!");
                mostrarPedidosRemocao();
            }
        });
        bRecusar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                int id = Utilitarios.obterIdSelecionado(tabela);
                if (id == -1) {
                    Utilitarios.mostrarErro(PainelGestor.this, "Selecione uma conta!");
                    return;
                }
                cUtilizador.mudarEstadoConta(id, EstadoUtilizador.ATIVO, utilizadorLogado.getLogin(),
                        "Recusou remoção ID:" + id);
                cNotificacao.gerarNotificacao(id, "O teu pedido de remoção foi recusado.", CategoriaNotificacao.GERAL);
                Utilitarios.mostrarSucesso(PainelGestor.this, "Pedido recusado!");
                mostrarPedidosRemocao();
            }
        });
        btns.add(bAceitar);
        btns.add(bRecusar);
        p.add(btns, BorderLayout.SOUTH);
        trocarConteudo(p);
    }

    /**
     * Apresenta o painel de edição do perfil do gestor.
     * Inclui foto de perfil, email e password — consistente com os outros painéis.
     */
    private void mostrarPerfil() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("O Meu Perfil"));

        painelFoto = Utilitarios.criarPainelFoto(utilizadorLogado.getFotoPath(), new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                String novaFoto = Utilitarios.escolherFicheiroImagem(PainelGestor.this,
                        utilizadorLogado.getIdUtilizador());
                if (novaFoto != null) {
                    boolean ok = cUtilizador.atualizarFoto(utilizadorLogado.getIdUtilizador(),
                            novaFoto, utilizadorLogado.getLogin());
                    if (ok) {
                        utilizadorLogado.setFotoPath(novaFoto);
                        Utilitarios.atualizarImagemPainel(painelFoto, novaFoto);
                        Utilitarios.mostrarSucesso(PainelGestor.this, "Foto atualizada com sucesso!");
                    }
                }
            }
        });
        p.add(painelFoto, BorderLayout.NORTH);

        JTextField cEmail = new JTextField(utilizadorLogado.getEmail(), 20);
        JPasswordField cPass = new JPasswordField(20);

        JPanel campos = new JPanel(new GridLayout(3, 1));
        campos.add(new JLabel("Nome: " + utilizadorLogado.getNome() + "  (não editável)"));
        campos.add(Utilitarios.criarCampoFormulario("Novo Email:", cEmail, "Novo endereço de email"));
        campos.add(Utilitarios.criarCampoFormulario("Nova Password:", cPass, "Nova palavra-passe"));

        JPanel centro = new JPanel(new BorderLayout());
        centro.add(campos, BorderLayout.NORTH);
        p.add(centro, BorderLayout.CENTER);

        JButton btnGuardar = new JButton("Guardar Alterações");
        btnGuardar.setToolTipText("Guardar as alterações ao perfil");
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                String email = cEmail.getText();
                String pass = new String(cPass.getPassword());
                if (!Validacoes.emailValido(email)) {
                    Utilitarios.mostrarErro(PainelGestor.this, "Email inválido!");
                    return;
                }
                if (pass.isEmpty()) {
                    Utilitarios.mostrarErro(PainelGestor.this, "Password não pode estar vazia!");
                    return;
                }
                boolean ok = cUtilizador.atualizarPerfilPeloGestor(utilizadorLogado.getIdUtilizador(),
                        utilizadorLogado.getNome(), email, pass, utilizadorLogado.getLogin(),
                        utilizadorLogado.getLogin());
                if (ok)
                    Utilitarios.mostrarSucesso(PainelGestor.this, "Perfil atualizado com sucesso!");
                else
                    Utilitarios.mostrarErro(PainelGestor.this, "Erro ao atualizar. Email pode já estar em uso.");
            }
        });
        p.add(btnGuardar, BorderLayout.SOUTH);
        trocarConteudo(p);
    }

    /**
     * Solicita a remoção da própria conta (Gestor logado).
     * Verifica se existe pelo menos outro Gestor ativo no sistema antes de permitir
     * a solicitação.
     */
    private void solicitarMinhaRemocao() {
        ArrayList<Utilizador> todos = cUtilizador.obterTodosUtilizadores();
        Iterator<Utilizador> it = todos.iterator();
        int gestoresAtivos = 0;
        while (it.hasNext()) {
            Utilizador u = it.next();
            if (u.getTipo() == TipoUtilizador.GESTOR && u.getEstado() == EstadoUtilizador.ATIVO)
                gestoresAtivos++;
        }
        if (gestoresAtivos <= 1) {
            Utilitarios.mostrarErro(PainelGestor.this, "És o único Gestor ATIVO! Não podes solicitar remoção.");
            return;
        }
        if (Utilitarios.confirmar(this,
                "Ao solicitar a remoção, a tua conta será removida do sistema.\n"
                        + "Os teus dados pessoais serão apagados de forma irreversível.\n"
                        + "Tens a certeza que queres apagar a conta?")) {
            cUtilizador.mudarEstadoConta(utilizadorLogado.getIdUtilizador(), EstadoUtilizador.AGUARDA_REMOCAO,
                    utilizadorLogado.getLogin(), "Gestor solicitou remoção da conta.");
            cNotificacao.gerarNotificacaoParaGestores(
                    "O Gestor '" + utilizadorLogado.getLogin() + "' solicitou remoção.", CategoriaNotificacao.REMOCAO);
            Utilitarios.mostrarInfo(PainelGestor.this, "Pedido submetido! Sessão terminada.");
            aplicacao.terminarSessao();
        }
    }
}
