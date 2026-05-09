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

    private AplicacaoGUI aplicacao;
    private Utilizador utilizadorLogado;
    private ControladorUtilizador cUtilizador;
    private ControladorEquipamento cEquipamento;
    private ControladorReparacao cReparacao;
    private ControladorNotificacao cNotificacao;

    private JPanel painelConteudo;
    private JPanel painelAtualConteudo;

    private JButton btnInserirEquip, btnPedirRep, btnPerfil, btnListarRep;
    private JButton btnPesquisarRep, btnListarEquip, btnPesquisarEquip;
    private JButton btnNotificacoes, btnRemocao, btnConsultarRep, btnLogout;

    /**
     * Constrói o painel do cliente.
     *
     * @param aplicacao referência para a aplicação principal
     */
    public PainelCliente(AplicacaoGUI aplicacao) {
        this.aplicacao = aplicacao;
        this.cUtilizador = aplicacao.getControladorUtilizador();
        this.cEquipamento = aplicacao.getControladorEquipamento();
        this.cReparacao = aplicacao.getControladorReparacao();
        this.cNotificacao = aplicacao.getControladorNotificacao();
        setLayout(new BorderLayout());

        // Menu lateral
        JPanel painelMenu = new JPanel();
        painelMenu.setLayout(new BoxLayout(painelMenu, BoxLayout.Y_AXIS));
        painelMenu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelMenu.setPreferredSize(new Dimension(220, 0));

        JLabel titulo = new JLabel("Menu Cliente");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelMenu.add(titulo);
        painelMenu.add(Box.createVerticalStrut(10));

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

    /**
     * Define o utilizador autenticado.
     * 
     * @param u utilizador logado
     */
    public void setUtilizadorLogado(Utilizador u) {
        this.utilizadorLogado = u;
    }

    private JButton criarBotaoMenu(String texto, String tooltip) {
        JButton btn = new JButton(texto);
        btn.setToolTipText(tooltip);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(200, 30));
        btn.addActionListener(this);
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (utilizadorLogado == null)
            return;
        Object src = e.getSource();

        if (src == btnLogout) {
            cUtilizador.registarFimSessao(utilizadorLogado.getLogin());
            aplicacao.terminarSessao();
            return;
        }
        if (src == btnInserirEquip)
            mostrarInserirEquipamento();
        else if (src == btnPedirRep)
            mostrarPedirReparacao();
        else if (src == btnPerfil)
            mostrarEditarPerfil();
        else if (src == btnListarRep)
            mostrarListarReparacoes();
        else if (src == btnPesquisarRep)
            mostrarPesquisarReparacoes();
        else if (src == btnListarEquip)
            mostrarListarEquipamentos();
        else if (src == btnPesquisarEquip)
            mostrarPesquisarEquipamentos();
        else if (src == btnNotificacoes)
            mostrarNotificacoes();
        else if (src == btnRemocao)
            solicitarRemocao();
        else if (src == btnConsultarRep)
            mostrarConsultarEstado();
    }

    // --- Inserir Equipamento ---
    private void mostrarInserirEquipamento() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createTitledBorder("Inserir Novo Equipamento"));

        JTextField cMarca = new JTextField(20);
        JTextField cModelo = new JTextField(20);
        JTextField cDataFab = new JTextField(20);
        JTextField cLote = new JTextField(20);
        JTextArea cObs = new JTextArea(3, 20);
        cObs.setLineWrap(true);

        p.add(Utilitarios.criarCampoFormulario("Marca:", cMarca, "Marca do equipamento"));
        p.add(Box.createVerticalStrut(5));
        p.add(Utilitarios.criarCampoFormulario("Modelo:", cModelo, "Código do modelo do equipamento"));
        p.add(Box.createVerticalStrut(5));
        p.add(Utilitarios.criarCampoFormulario("Data Fabrico:", cDataFab, "Data de fabrico (YYYY-MM-DD)"));
        p.add(Box.createVerticalStrut(5));
        p.add(Utilitarios.criarCampoFormulario("Lote:", cLote, "Identificação do lote de fabrico"));
        p.add(Box.createVerticalStrut(5));
        p.add(Utilitarios.criarCampoFormulario("Observações:", new JScrollPane(cObs), "Comentários adicionais (R4)"));
        p.add(Box.createVerticalStrut(10));

        JButton btnSubmeter = new JButton("Registar Equipamento");
        btnSubmeter.setToolTipText("Submeter o registo do equipamento");
        btnSubmeter.addActionListener(new ActionListener() { @Override
            public void actionPerformed(ActionEvent ev) {
            String marca = cMarca.getText().trim();
            String modelo = cModelo.getText().trim();
            if (marca.isEmpty() || modelo.isEmpty()) {
                Utilitarios.mostrarErro(PainelCliente.this, "Marca e Modelo são obrigatórios!");
                return;
            }
            if (cEquipamento.modeloExiste(modelo)) {
                Utilitarios.mostrarErro(PainelCliente.this, "Já existe um equipamento com o código de modelo '" + modelo + "'.");
                return;
            }
            int codSKU = cEquipamento.gerarSkuUnico();
            cEquipamento.registarEquipamento(utilizadorLogado, marca, modelo, codSKU,
                    cDataFab.getText().trim(), cLote.getText().trim(), utilizadorLogado.getLogin());
            Utilitarios.mostrarSucesso(PainelCliente.this, "Equipamento registado com sucesso! (SKU: " + codSKU + ")");
            }
        });
        p.add(btnSubmeter);

        trocarConteudo(p, "inserirEquip");
    }

    // --- Pedir Reparação ---
    private void mostrarPedirReparacao() {
        JPanel p = new JPanel(new BorderLayout(5, 5));
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
        JScrollPane scrollTabela = Utilitarios.criarTabela(
                new String[] { "ID", "Marca", "Modelo", "SKU" }, dados);
        p.add(scrollTabela, BorderLayout.CENTER);

        JPanel painelAcao = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextArea cObs = new JTextArea(2, 30);
        cObs.setToolTipText("Observações sobre o pedido de reparação (R4)");
        painelAcao.add(new JLabel("Observações:"));
        painelAcao.add(new JScrollPane(cObs));
        JButton btnPedir = new JButton("Pedir Reparação");
        btnPedir.setToolTipText("Submeter pedido para o equipamento selecionado");
        btnPedir.addActionListener(new ActionListener() { @Override
            public void actionPerformed(ActionEvent ev) {
            int idEq = Utilitarios.obterIdSelecionado(scrollTabela);
            if (idEq == -1) {
                Utilitarios.mostrarErro(PainelCliente.this, "Selecione um equipamento da tabela!");
                return;
            }
            cReparacao.registarNovoPedido(idEq, utilizadorLogado.getLogin());
            Utilitarios.mostrarSucesso(PainelCliente.this, "Pedido de reparação submetido com sucesso!");
            }
        });
        painelAcao.add(btnPedir);
        p.add(painelAcao, BorderLayout.SOUTH);

        trocarConteudo(p, "pedirRep");
    }

    // --- Editar Perfil ---
    private void mostrarEditarPerfil() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createTitledBorder("O Meu Perfil"));

        JTextField cEmail = new JTextField(utilizadorLogado.getEmail(), 20);
        JPasswordField cPass = new JPasswordField(20);
        JTextField cTel = new JTextField(20);
        JTextField cMorada = new JTextField(20);

        p.add(new JLabel("Nome: " + utilizadorLogado.getNome() + "  (não editável)"));
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
        btnGuardar.addActionListener(new ActionListener() { @Override
            public void actionPerformed(ActionEvent ev) {
            String email = cEmail.getText().trim();
            String pass = new String(cPass.getPassword());
            String tel = cTel.getText().trim();
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
                    email, pass, tel, cMorada.getText().trim(), utilizadorLogado.getLogin());
            if (ok)
                Utilitarios.mostrarSucesso(PainelCliente.this, "Perfil atualizado com sucesso!");
            else
                Utilitarios.mostrarErro(PainelCliente.this, "Erro ao atualizar. Email pode já estar em uso.");
            }
        });
        p.add(btnGuardar);

        trocarConteudo(p, "perfil");
    }

    // --- Listar Reparações ---
    private void mostrarListarReparacoes() {
        JPanel p = new JPanel(new BorderLayout(5, 5));
        p.setBorder(BorderFactory.createTitledBorder("Listar Pedidos de Reparação"));

        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<String> comboCrit = new JComboBox<>(new String[] { "Data de Criação", "Número da Reparação" });
        comboCrit.setToolTipText("Critério de ordenação da listagem");
        JComboBox<String> comboOrdem = new JComboBox<>(new String[] { "Crescente", "Decrescente" });
        comboOrdem.setToolTipText("Direção da ordenação");
        JButton btnListar = new JButton("Listar");
        btnListar.setToolTipText("Aplicar ordenação e listar reparações");
        filtros.add(new JLabel("Ordenar por:"));
        filtros.add(comboCrit);
        filtros.add(comboOrdem);
        filtros.add(btnListar);
        p.add(filtros, BorderLayout.NORTH);

        JScrollPane scrollTabela = Utilitarios.criarTabela(
                new String[] { "ID", "Número", "Data", "Estado" }, new Object[][] {});
        p.add(scrollTabela, BorderLayout.CENTER);

        btnListar.addActionListener(new ActionListener() { @Override
            public void actionPerformed(ActionEvent ev) {
            int escolha = comboCrit.getSelectedIndex() + 1;
            boolean asc = comboOrdem.getSelectedIndex() == 0;
            ArrayList<Reparacao> lista = cReparacao.listarReparacoesClienteOrdenadas(
                    utilizadorLogado.getIdUtilizador(), escolha, asc);
            Object[][] dados = new Object[lista.size()][4];
            Iterator<Reparacao> it = lista.iterator();
            int i = 0;
            while (it.hasNext()) {
                Reparacao r = it.next();
                dados[i] = new Object[] { r.getIdReparacao(), r.getNumReparacao(), r.getDataCriacao(), r.getEstado() };
                i++;
            }
            Utilitarios.atualizarTabela(scrollTabela, new String[] { "ID", "Número", "Data", "Estado" }, dados);
            }
        });

        trocarConteudo(p, "listarRep");
    }

    // --- Pesquisar Reparações ---
    private void mostrarPesquisarReparacoes() {
        JPanel p = new JPanel(new BorderLayout(5, 5));
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

        JScrollPane scrollTabela = Utilitarios.criarTabela(
                new String[] { "ID", "Número", "Data", "Estado" }, new Object[][] {});
        p.add(scrollTabela, BorderLayout.CENTER);

        btnPesq.addActionListener(new ActionListener() { @Override
            public void actionPerformed(ActionEvent ev) {
            ArrayList<Reparacao> res = cReparacao.pesquisarReparacoesCliente(
                    utilizadorLogado.getIdUtilizador(), comboCrit.getSelectedIndex() + 1,
                    campoTermo.getText().trim());
            Object[][] dados = new Object[res.size()][4];
            Iterator<Reparacao> it = res.iterator();
            int i = 0;
            while (it.hasNext()) {
                Reparacao r = it.next();
                dados[i] = new Object[] { r.getIdReparacao(), r.getNumReparacao(), r.getDataCriacao(), r.getEstado() };
                i++;
            }
            Utilitarios.atualizarTabela(scrollTabela, new String[] { "ID", "Número", "Data", "Estado" }, dados);
            }
        });

        trocarConteudo(p, "pesqRep");
    }

    // --- Listar Equipamentos ---
    private void mostrarListarEquipamentos() {
        JPanel p = new JPanel(new BorderLayout(5, 5));
        p.setBorder(BorderFactory.createTitledBorder("Listar Equipamentos"));

        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<String> comboCrit = new JComboBox<>(new String[] { "Marca", "Código de Modelo" });
        comboCrit.setToolTipText("Critério de ordenação");
        JComboBox<String> comboOrdem = new JComboBox<>(new String[] { "Crescente", "Decrescente" });
        comboOrdem.setToolTipText("Direção da ordenação");
        JButton btnListar = new JButton("Listar");
        btnListar.setToolTipText("Aplicar ordenação e listar equipamentos");
        filtros.add(new JLabel("Ordenar por:"));
        filtros.add(comboCrit);
        filtros.add(comboOrdem);
        filtros.add(btnListar);
        p.add(filtros, BorderLayout.NORTH);

        JScrollPane scrollTabela = Utilitarios.criarTabela(
                new String[] { "ID", "Marca", "Modelo", "SKU" }, new Object[][] {});
        p.add(scrollTabela, BorderLayout.CENTER);

        btnListar.addActionListener(new ActionListener() { @Override
            public void actionPerformed(ActionEvent ev) {
            ArrayList<Equipamento> lista = cEquipamento.listarEquipamentosClienteOrdenados(
                    utilizadorLogado.getIdUtilizador(), comboCrit.getSelectedIndex() + 1,
                    comboOrdem.getSelectedIndex() == 0);
            Object[][] dados = new Object[lista.size()][4];
            Iterator<Equipamento> it = lista.iterator();
            int i = 0;
            while (it.hasNext()) {
                Equipamento eq = it.next();
                dados[i] = new Object[] { eq.getIdEquipamento(), eq.getMarca(), eq.getModelo(), eq.getSku() };
                i++;
            }
            Utilitarios.atualizarTabela(scrollTabela, new String[] { "ID", "Marca", "Modelo", "SKU" }, dados);
            }
        });

        trocarConteudo(p, "listarEquip");
    }

    // --- Pesquisar Equipamentos ---
    private void mostrarPesquisarEquipamentos() {
        JPanel p = new JPanel(new BorderLayout(5, 5));
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

        JScrollPane scrollTabela = Utilitarios.criarTabela(
                new String[] { "ID", "Marca", "Modelo", "SKU" }, new Object[][] {});
        p.add(scrollTabela, BorderLayout.CENTER);

        btnPesq.addActionListener(new ActionListener() { @Override
            public void actionPerformed(ActionEvent ev) {
            ArrayList<Equipamento> res = cEquipamento.pesquisarEquipamentosCliente(
                    utilizadorLogado.getIdUtilizador(), comboCrit.getSelectedIndex() + 1,
                    campoTermo.getText().trim());
            Object[][] dados = new Object[res.size()][4];
            Iterator<Equipamento> it = res.iterator();
            int i = 0;
            while (it.hasNext()) {
                Equipamento eq = it.next();
                dados[i] = new Object[] { eq.getIdEquipamento(), eq.getMarca(), eq.getModelo(), eq.getSku() };
                i++;
            }
            Utilitarios.atualizarTabela(scrollTabela, new String[] { "ID", "Marca", "Modelo", "SKU" }, dados);
            }
        });

        trocarConteudo(p, "pesqEquip");
    }

    // --- Notificações ---
    private void mostrarNotificacoes() {
        JPanel p = new JPanel(new BorderLayout(5, 5));
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
        JScrollPane scrollTabela = Utilitarios.criarTabela(new String[] { "Data", "Estado", "Mensagem" }, dados);
        p.add(scrollTabela, BorderLayout.CENTER);

        JButton btnMarcar = new JButton("Marcar Todas como Lidas");
        btnMarcar.setToolTipText("Marcar todas as notificações pendentes como lidas");
        btnMarcar.addActionListener(new ActionListener() { @Override
            public void actionPerformed(ActionEvent ev) {
            cNotificacao.marcarComoLidas(utilizadorLogado.getIdUtilizador());
            Utilitarios.mostrarSucesso(PainelCliente.this, "Notificações marcadas como lidas!");
            mostrarNotificacoes();
            }
        });
        p.add(btnMarcar, BorderLayout.SOUTH);

        trocarConteudo(p, "notifs");
    }

    // --- Solicitar Remoção ---
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

    // --- Consultar Estado ---
    private void mostrarConsultarEstado() {
        JPanel p = new JPanel(new BorderLayout(5, 5));
        p.setBorder(BorderFactory.createTitledBorder("Estado das Minhas Reparações"));

        ArrayList<Reparacao> lista = cReparacao.obterMinhasReparacoes(utilizadorLogado.getIdUtilizador());
        Object[][] dados = new Object[lista.size()][2];
        Iterator<Reparacao> it = lista.iterator();
        int i = 0;
        while (it.hasNext()) {
            Reparacao r = it.next();
            dados[i] = new Object[] { r.getNumReparacao(), r.getEstado() };
            i++;
        }
        JScrollPane scrollTabela = Utilitarios.criarTabela(new String[] { "Nº Processo", "Estado" }, dados);
        p.add(scrollTabela, BorderLayout.CENTER);

        trocarConteudo(p, "consultarEstado");
    }

    /**
     * Troca o painel de conteúdo visível usando remove/add/revalidate/repaint.
     */
    private void trocarConteudo(JPanel novoPainel, String nome) {
        painelConteudo.remove(painelAtualConteudo);
        painelAtualConteudo = novoPainel;
        painelConteudo.add(painelAtualConteudo, BorderLayout.CENTER);
        painelConteudo.revalidate();
        painelConteudo.repaint();
    }
}



