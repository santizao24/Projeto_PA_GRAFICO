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
import model.Notificacao;
import model.Reparacao;
import model.Utilizador;
import util.Validacoes;
import Enums.EstadoUtilizador;
import Enums.CategoriaNotificacao;

/**
 * Painel com as funcionalidades disponíveis para o utilizador do tipo Cliente.
 * Inclui gestão de equipamentos, pedidos de reparação, perfil, notificações
 * e consulta de estado das reparações.
 *
 * @author Santiago e Hugo
 * @version 1.0
 */
public class PainelCliente extends JPanel implements ActionListener {

    private Aplicacao aplicacao;
    private Utilizador utilizadorLogado;
    private ControladorUtilizador cUtilizador;
    private ControladorEquipamento cEquipamento;
    private ControladorReparacao cReparacao;
    private ControladorNotificacao cNotificacao;

    private JPanel painelConteudo;
    private JPanel painelAtualConteudo;
    private JPanel painelFoto;

    private JButton btnInserirEquip, btnPedirRep, btnPerfil, btnListarRep;
    private JButton btnPesquisarRep, btnListarEquip, btnPesquisarEquip;
    private JButton btnNotificacoes, btnRemocao, btnConsultarRep, btnLogout;

    /**
     * Constrói o painel do cliente.
     *
     * @param aplicacao referência para a aplicação principal
     */
    public PainelCliente(Aplicacao aplicacao) {
        this.aplicacao = aplicacao;
        this.cUtilizador = aplicacao.getControladorUtilizador();
        this.cEquipamento = aplicacao.getControladorEquipamento();
        this.cReparacao = aplicacao.getControladorReparacao();
        this.cNotificacao = aplicacao.getControladorNotificacao();
        setLayout(new BorderLayout());

        JPanel painelMenu = new JPanel();
        painelMenu.setLayout(new GridLayout(12, 1));
        painelMenu.setPreferredSize(new Dimension(220, 600));

        JLabel titulo = new JLabel("Menu Cliente");
        painelMenu.add(titulo);

        btnInserirEquip = criarBotaoMenu("Inserir Equipamento", "Registar um novo equipamento");
        btnPedirRep = criarBotaoMenu("Pedir Reparação", "Submeter pedido de reparação");
        btnPerfil = criarBotaoMenu("O Meu Perfil", "Editar os dados do perfil");
        btnListarRep = criarBotaoMenu("Listar Reparações", "Listar pedidos de reparação ordenados");
        btnPesquisarRep = criarBotaoMenu("Pesquisar Reparações", "Pesquisar pedidos de reparação");
        btnListarEquip = criarBotaoMenu("Listar Equipamentos", "Listar os meus equipamentos ordenados");
        btnPesquisarEquip = criarBotaoMenu("Pesquisar Equipamentos", "Pesquisar os meus equipamentos");
        btnNotificacoes = criarBotaoMenu("Notificações", "Ver as minhas notificações");
        btnRemocao = criarBotaoMenu("Solicitar Remoção", "Solicitar remoção da conta");
        btnConsultarRep = criarBotaoMenu("Estado Reparações", "Consultar estado das reparações");
        btnLogout = criarBotaoMenu("Logout", "Terminar sessão e voltar ao login");

        JButton[] botoes = { btnInserirEquip, btnPedirRep, btnPerfil, btnListarRep,
                btnPesquisarRep, btnListarEquip, btnPesquisarEquip, btnNotificacoes,
                btnRemocao, btnConsultarRep, btnLogout };
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
        Object src = e.getSource();

        if (src.equals(btnLogout)) {
            cUtilizador.registarFimSessao(utilizadorLogado.getLogin());
            aplicacao.terminarSessao();
            return;
        }
        if (src.equals(btnInserirEquip))
            mostrarInserirEquipamento();
        else if (src.equals(btnPedirRep))
            mostrarPedirReparacao();
        else if (src.equals(btnPerfil))
            mostrarEditarPerfil();
        else if (src.equals(btnListarRep))
            mostrarListarReparacoes();
        else if (src.equals(btnPesquisarRep))
            mostrarPesquisarReparacoes();
        else if (src.equals(btnListarEquip))
            mostrarListarEquipamentos();
        else if (src.equals(btnPesquisarEquip))
            mostrarPesquisarEquipamentos();
        else if (src.equals(btnNotificacoes))
            mostrarNotificacoes();
        else if (src.equals(btnRemocao))
            solicitarRemocao();
        else if (src.equals(btnConsultarRep))
            mostrarConsultarEstado();
    }

    /**
     * Apresenta o painel de inserção de um novo equipamento no sistema.
     */
    private void mostrarInserirEquipamento() {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(6, 1));
        p.setBorder(BorderFactory.createTitledBorder("Inserir Novo Equipamento"));

        JTextField cMarca = new JTextField(20);
        JTextField cModelo = new JTextField(20);
        JTextField cDataFab = new JTextField(20);
        JTextField cLote = new JTextField(20);
        JTextArea cObs = new JTextArea(3, 20);

        p.add(Utilitarios.criarCampoFormulario("Marca:", cMarca, "Marca do equipamento"));
        p.add(Utilitarios.criarCampoFormulario("Modelo:", cModelo, "Código do modelo do equipamento"));
        p.add(Utilitarios.criarCampoFormulario("Data Fabrico:", cDataFab,
                "Data de fabrico (dd/MM/yyyy ou yyyy-MM-dd)"));
        p.add(Utilitarios.criarCampoFormulario("Lote:", cLote, "Identificação do lote de fabrico"));
        p.add(Utilitarios.criarCampoFormulario("Observações:", new JScrollPane(cObs), "Comentários adicionais"));

        JButton btnSubmeter = new JButton("Registar Equipamento");
        btnSubmeter.setToolTipText("Submeter o registo do equipamento");
        btnSubmeter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                String marca = cMarca.getText();
                String modelo = cModelo.getText();
                if (marca.isEmpty() || modelo.isEmpty()) {
                    Utilitarios.mostrarErro(PainelCliente.this, "Marca e Modelo são obrigatórios!");
                    return;
                }
                if (cEquipamento.modeloExiste(modelo)) {
                    Utilitarios.mostrarErro(PainelCliente.this,
                            "Já existe um equipamento com o código de modelo '" + modelo + "'.");
                    return;
                }

                String dataNormalizada = Validacoes.normalizarData(cDataFab.getText());
                if (dataNormalizada == null) {
                    Utilitarios.mostrarErro(PainelCliente.this,
                            "Data de fabrico inválida! Use formatos como dd/MM/yyyy ou yyyy-MM-dd.");
                    return;
                }

                int codSKU = cEquipamento.gerarSkuUnico();
                cEquipamento.registarEquipamento(utilizadorLogado, marca, modelo, codSKU, dataNormalizada,
                        cLote.getText(), utilizadorLogado.getLogin(), cObs.getText());
                Utilitarios.mostrarSucesso(PainelCliente.this,
                        "Equipamento registado com sucesso! (SKU: " + codSKU + ")");
            }
        });
        p.add(btnSubmeter);

        trocarConteudo(p, "inserirEquip");
    }

    /**
     * Apresenta o painel para pedir a reparação de um equipamento associado ao
     * cliente.
     */
    private void mostrarPedirReparacao() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("Pedir Reparação"));

        ArrayList<Equipamento> meusEquip = cEquipamento.listarEquipamentos(utilizadorLogado.getIdUtilizador());
        Object[][] dados = new Object[meusEquip.size()][4];
        Iterator<Equipamento> it = meusEquip.iterator();
        int i = 0;
        while (it.hasNext()) {
            Equipamento eq = it.next();
            dados[i] = new Object[] { eq.getIdEquipamento(), eq.getMarca(), eq.getModelo(), eq.getSku() };
            i++;
        }
        JTable tabela = Utilitarios.criarTabela(
                new String[] { "ID", "Marca", "Modelo", "SKU" }, dados);
        p.add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel painelAcao = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextArea cObs = new JTextArea(2, 30);
        cObs.setToolTipText("Observações sobre o pedido de reparação");
        painelAcao.add(new JLabel("Observações:"));
        painelAcao.add(new JScrollPane(cObs));
        JButton btnPedir = new JButton("Pedir Reparação");
        btnPedir.setToolTipText("Submeter pedido para o equipamento selecionado");
        btnPedir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                int idEq = Utilitarios.obterIdSelecionado(tabela);
                if (idEq == -1) {
                    Utilitarios.mostrarErro(PainelCliente.this, "Selecione um equipamento da tabela!");
                    return;
                }
                cReparacao.registarNovoPedido(idEq, utilizadorLogado.getLogin(), cObs.getText());
                Utilitarios.mostrarSucesso(PainelCliente.this, "Pedido de reparação submetido com sucesso!");
            }
        });
        painelAcao.add(btnPedir);
        p.add(painelAcao, BorderLayout.SOUTH);

        trocarConteudo(p, "pedirRep");
    }

    /**
     * Apresenta o painel de edição do perfil (alterar password).
     */
    private void mostrarEditarPerfil() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("O Meu Perfil"));

        painelFoto = Utilitarios.criarPainelFoto(utilizadorLogado.getFotoPath(), new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                String novaFoto = Utilitarios.escolherFicheiroImagem(PainelCliente.this,
                        utilizadorLogado.getIdUtilizador());
                if (novaFoto != null) {
                    boolean ok = cUtilizador.atualizarFoto(utilizadorLogado.getIdUtilizador(),
                            novaFoto, utilizadorLogado.getLogin());
                    if (ok) {
                        utilizadorLogado.setFotoPath(novaFoto);
                        Utilitarios.atualizarImagemPainel(painelFoto, novaFoto);
                        Utilitarios.mostrarSucesso(PainelCliente.this, "Foto atualizada com sucesso!");
                    }
                }
            }
        });
        p.add(painelFoto, BorderLayout.NORTH);

        JTextField cEmail = new JTextField(utilizadorLogado.getEmail(), 20);
        JPasswordField cPass = new JPasswordField(20);
        JTextField cTel = new JTextField(20);
        JTextField cMorada = new JTextField(20);

        JPanel campos = new JPanel(new GridLayout(5, 1));
        campos.add(new JLabel("Nome: " + utilizadorLogado.getNome() + "  (não editável)"));
        campos.add(Utilitarios.criarCampoFormulario("Novo Email:", cEmail, "Novo endereço de email"));
        campos.add(Utilitarios.criarCampoFormulario("Nova Password:", cPass, "Nova palavra-passe"));
        campos.add(Utilitarios.criarCampoFormulario("Novo Telefone:", cTel, "Novo telefone (9 dígitos)"));
        campos.add(Utilitarios.criarCampoFormulario("Nova Morada:", cMorada, "Nova morada"));

        JPanel centro = new JPanel(new BorderLayout());
        centro.add(campos, BorderLayout.NORTH);
        p.add(centro, BorderLayout.CENTER);

        JButton btnGuardar = new JButton("Guardar Alterações");
        btnGuardar.setToolTipText("Guardar as alterações ao perfil");
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                String email = cEmail.getText();
                String pass = new String(cPass.getPassword());
                String tel = cTel.getText();
                if (!Validacoes.emailValido(email)) {
                    Utilitarios.mostrarErro(PainelCliente.this, "Email inválido!");
                    return;
                }
                if (pass.isEmpty()) {
                    Utilitarios.mostrarErro(PainelCliente.this, "Password não pode estar vazia!");
                    return;
                }
                if (!Validacoes.telefoneValido(tel)) {
                    Utilitarios.mostrarErro(PainelCliente.this, "Telefone inválido!");
                    return;
                }
                boolean ok = cUtilizador.atualizarPerfilCliente(utilizadorLogado.getIdUtilizador(),
                        email, pass, tel, cMorada.getText(), utilizadorLogado.getLogin());
                if (ok)
                    Utilitarios.mostrarSucesso(PainelCliente.this, "Perfil atualizado com sucesso!");
                else
                    Utilitarios.mostrarErro(PainelCliente.this, "Erro ao atualizar. Email pode já estar em uso.");
            }
        });
        p.add(btnGuardar, BorderLayout.SOUTH);

        trocarConteudo(p, "perfil");
    }

    /**
     * Apresenta o painel com a listagem de todos os pedidos de reparação do
     * cliente.
     */
    private void mostrarListarReparacoes() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("Listar Pedidos de Reparação"));

        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnListar = new JButton("Listar");
        btnListar.setToolTipText("Listar reparações");
        JButton btnImprimir = new JButton("Imprimir Extrato");
        btnImprimir.setToolTipText("Imprimir extrato da reparação selecionada");
        filtros.add(btnListar);
        filtros.add(btnImprimir);
        p.add(filtros, BorderLayout.NORTH);

        JTable tabela = Utilitarios.criarTabela(
                new String[] { "ID", "Número", "Data", "Estado" }, new Object[][] {});
        p.add(new JScrollPane(tabela), BorderLayout.CENTER);

        btnListar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                ArrayList<Reparacao> lista = cReparacao.listarReparacoesClienteOrdenadas(
                        utilizadorLogado.getIdUtilizador(), 1, true);
                Utilitarios.atualizarTabela(tabela, new String[] { "ID", "Número", "Data", "Estado" },
                        converterReparacoes(lista));
            }
        });

        btnImprimir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                int idSel = Utilitarios.obterIdSelecionado(tabela);
                if (idSel == -1) {
                    Utilitarios.mostrarErro(PainelCliente.this,
                            "Selecione uma reparação na tabela para imprimir o extrato.");
                    return;
                }
                ArrayList<Reparacao> todas = cReparacao.obterMinhasReparacoes(
                        utilizadorLogado.getIdUtilizador());
                Reparacao repSel = null;
                Iterator<Reparacao> it = todas.iterator();
                while (it.hasNext()) {
                    Reparacao r = it.next();
                    if (r.getIdReparacao() == idSel) {
                        repSel = r;
                        break;
                    }
                }
                if (repSel == null) {
                    Utilitarios.mostrarErro(PainelCliente.this, "Reparação não encontrada.");
                    return;
                }

                String equipInfo = "ID Equip: " + repSel.getIdEquipamento();
                ArrayList<Equipamento> equips = cEquipamento.listarEquipamentos(
                        utilizadorLogado.getIdUtilizador());
                Iterator<Equipamento> itEq = equips.iterator();
                while (itEq.hasNext()) {
                    Equipamento eq = itEq.next();
                    if (eq.getIdEquipamento() == repSel.getIdEquipamento()) {
                        equipInfo = eq.getMarca() + " - " + eq.getModelo() + " (SKU: " + eq.getSku() + ")";
                        break;
                    }
                }

                String nomeFunc = "Não atribuído";
                if (repSel.getIdUtilizador() > 0) {
                    Utilizador func = cUtilizador.obterUtilizadorPorId(repSel.getIdUtilizador());
                    if (func != null) {
                        nomeFunc = func.getNome();
                    }
                }

                ExtractoReparacao extrato = new ExtractoReparacao(repSel, utilizadorLogado.getNome(), nomeFunc,
                        equipInfo);
                extrato.imprimir(PainelCliente.this);
            }
        });

        trocarConteudo(p, "listarRep");
    }

    /**
     * Apresenta o painel para pesquisar reparações do cliente por termo.
     */
    private void mostrarPesquisarReparacoes() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("Pesquisar Reparações"));

        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<String> comboCrit = new JComboBox<>(new String[] { "Número da Reparação", "Estado" });
        comboCrit.setToolTipText("Critério de pesquisa");
        JTextField campoTermo = new JTextField(15);
        campoTermo.setToolTipText("Termo a pesquisar (pesquisa parcial)");
        JButton btnPesq = new JButton("Pesquisar");
        btnPesq.setToolTipText("Executar pesquisa");
        filtros.add(new JLabel("Critério:"));
        filtros.add(comboCrit);
        filtros.add(campoTermo);
        filtros.add(btnPesq);
        p.add(filtros, BorderLayout.NORTH);

        JTable tabela = Utilitarios.criarTabela(
                new String[] { "ID", "Número", "Data", "Estado" }, new Object[][] {});
        p.add(new JScrollPane(tabela), BorderLayout.CENTER);

        btnPesq.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                String crit = (String) comboCrit.getSelectedItem();
                int criterio = 1;
                if (crit.equals("Estado")) {
                    criterio = 2;
                }
                ArrayList<Reparacao> res = cReparacao.pesquisarReparacoesCliente(
                        utilizadorLogado.getIdUtilizador(), criterio,
                        campoTermo.getText());
                Utilitarios.atualizarTabela(tabela, new String[] { "ID", "Número", "Data", "Estado" },
                        converterReparacoes(res));
            }
        });

        trocarConteudo(p, "pesqRep");
    }

    /**
     * Apresenta o painel com a listagem de todos os equipamentos do cliente.
     */
    private void mostrarListarEquipamentos() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("Listar Equipamentos"));

        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnListar = new JButton("Listar");
        btnListar.setToolTipText("Listar equipamentos");
        filtros.add(btnListar);
        p.add(filtros, BorderLayout.NORTH);

        JTable tabela = Utilitarios.criarTabela(
                new String[] { "ID", "Marca", "Modelo", "SKU" }, new Object[][] {});
        p.add(new JScrollPane(tabela), BorderLayout.CENTER);

        btnListar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                ArrayList<Equipamento> lista = cEquipamento.listarEquipamentosClienteOrdenados(
                        utilizadorLogado.getIdUtilizador(), 1, true);
                Object[][] dados = new Object[lista.size()][4];
                Iterator<Equipamento> it = lista.iterator();
                int i = 0;
                while (it.hasNext()) {
                    Equipamento eq = it.next();
                    dados[i] = new Object[] { eq.getIdEquipamento(), eq.getMarca(), eq.getModelo(), eq.getSku() };
                    i++;
                }
                Utilitarios.atualizarTabela(tabela, new String[] { "ID", "Marca", "Modelo", "SKU" }, dados);
            }
        });

        trocarConteudo(p, "listarEquip");
    }

    /**
     * Apresenta o painel para pesquisar equipamentos do cliente por termo.
     */
    private void mostrarPesquisarEquipamentos() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("Pesquisar Equipamentos"));

        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<String> comboCrit = new JComboBox<>(new String[] { "Marca", "Código de Modelo" });
        comboCrit.setToolTipText("Critério de pesquisa");
        JTextField campoTermo = new JTextField(15);
        campoTermo.setToolTipText("Termo a pesquisar (pesquisa parcial)");
        JButton btnPesq = new JButton("Pesquisar");
        btnPesq.setToolTipText("Executar pesquisa");
        filtros.add(new JLabel("Critério:"));
        filtros.add(comboCrit);
        filtros.add(campoTermo);
        filtros.add(btnPesq);
        p.add(filtros, BorderLayout.NORTH);

        JTable tabela = Utilitarios.criarTabela(
                new String[] { "ID", "Marca", "Modelo", "SKU" }, new Object[][] {});
        p.add(new JScrollPane(tabela), BorderLayout.CENTER);

        btnPesq.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                String crit = (String) comboCrit.getSelectedItem();
                int criterio = 1;
                if (crit.equals("Código de Modelo")) {
                    criterio = 2;
                }
                ArrayList<Equipamento> res = cEquipamento.pesquisarEquipamentosCliente(
                        utilizadorLogado.getIdUtilizador(), criterio,
                        campoTermo.getText());
                Object[][] dados = new Object[res.size()][4];
                Iterator<Equipamento> it = res.iterator();
                int i = 0;
                while (it.hasNext()) {
                    Equipamento eq = it.next();
                    dados[i] = new Object[] { eq.getIdEquipamento(), eq.getMarca(), eq.getModelo(), eq.getSku() };
                    i++;
                }
                Utilitarios.atualizarTabela(tabela, new String[] { "ID", "Marca", "Modelo", "SKU" }, dados);
            }
        });

        trocarConteudo(p, "pesqEquip");
    }

    /**
     * Apresenta o dashboard de notificações do cliente.
     * Permite ler e marcar notificações como lidas.
     */
    private void mostrarNotificacoes() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("As Minhas Notificações"));

        ArrayList<Notificacao> lista = cNotificacao.obterNotificacoes(utilizadorLogado.getIdUtilizador());
        Object[][] dados = new Object[lista.size()][3];
        Iterator<Notificacao> it = lista.iterator();
        int i = 0;
        while (it.hasNext()) {
            Notificacao n = it.next();
            dados[i] = new Object[] { n.getDataCriacao(), n.getEstado(), n.getMensagem() };
            i++;
        }
        JTable tabela = Utilitarios.criarTabela(new String[] { "Data", "Estado", "Mensagem" }, dados);
        p.add(new JScrollPane(tabela), BorderLayout.CENTER);

        JButton btnMarcar = new JButton("Marcar Todas como Lidas");
        btnMarcar.setToolTipText("Marcar todas as notificações pendentes como lidas");
        btnMarcar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                cNotificacao.marcarComoLidas(utilizadorLogado.getIdUtilizador());
                Utilitarios.mostrarSucesso(PainelCliente.this, "Notificações marcadas como lidas!");
                mostrarNotificacoes();
            }
        });
        p.add(btnMarcar, BorderLayout.SOUTH);

        trocarConteudo(p, "notifs");
    }

    /**
     * Permite ao cliente solicitar a remoção da sua conta.
     * O estado muda para AGUARDA_REMOCAO e o Gestor recebe uma notificação.
     */
    private void solicitarRemocao() {
        boolean confirma = Utilitarios.confirmar(this,
                "Ao solicitar a remoção, a tua conta será removida do sistema.\n"
                        + "Os teus dados pessoais serão apagados de forma irreversível.\n"
                        + "Tens a certeza que queres apagar a conta?");
        if (confirma) {
            cUtilizador.mudarEstadoConta(utilizadorLogado.getIdUtilizador(), EstadoUtilizador.AGUARDA_REMOCAO,
                    utilizadorLogado.getLogin(), "Cliente solicitou a remoção da conta.");
            cNotificacao.gerarNotificacaoParaGestores(
                    "O Cliente '" + utilizadorLogado.getLogin() + "' solicitou a remoção da sua conta.",
                    CategoriaNotificacao.REMOCAO);
            Utilitarios.mostrarInfo(PainelCliente.this, "Pedido submetido! A sessão vai ser terminada.");
            aplicacao.terminarSessao();
        }
    }

    /**
     * Apresenta o painel para consultar rapidamente o estado de uma reparação
     * através do seu número de identificação.
     */
    private void mostrarConsultarEstado() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("Estado das Minhas Reparações"));

        ArrayList<Reparacao> lista = cReparacao.obterMinhasReparacoes(utilizadorLogado.getIdUtilizador());
        String[] dados = new String[lista.size()];
        Iterator<Reparacao> it = lista.iterator();
        int i = 0;
        while (it.hasNext()) {
            Reparacao r = it.next();
            dados[i] = "Processo Nº " + r.getNumReparacao() + "  ->  Estado: " + r.getEstado();
            i++;
        }
        JList<String> listaSwing = Utilitarios.criarLista(dados);
        p.add(new JScrollPane(listaSwing), BorderLayout.CENTER);

        trocarConteudo(p, "consultarEstado");
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
