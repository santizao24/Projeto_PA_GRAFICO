package view;

import java.awt.*;
import java.awt.print.*;
import java.text.DecimalFormat;

import model.Reparacao;

/**
 * Classe responsável pela formatação e impressão do extrato de uma reparação.
 * Implementa a interface {@link Printable} para gerar e renderizar um documento
 * paginado com os detalhes estruturados do processo de reparação.
 *
 * @author Santiago e Hugo
 * @version 1.0
 */
public class ExtractoReparacao implements Printable {

    private Reparacao reparacao;
    private String nomeCliente;
    private String nomeFuncionario;
    private String equipamentoInfo;

    /**
     * Constrói um extrato de reparação com os dados necessários para impressão.
     *
     * @param aReparacao       objeto Reparacao com os dados do processo
     * @param aNomeCliente     nome do cliente proprietário do equipamento
     * @param aNomeFuncionario nome do funcionario que executou a reparação
     * @param aEquipamentoInfo informação do equipamento (marca e modelo)
     */
    public ExtractoReparacao(Reparacao aReparacao, String aNomeCliente, String aNomeFuncionario,
            String aEquipamentoInfo) {
        reparacao = aReparacao;
        nomeCliente = aNomeCliente;
        nomeFuncionario = aNomeFuncionario;
        equipamentoInfo = aEquipamentoInfo;
    }

    /**
     * Método de impressão invocado pelo PrinterJob.
     * Define o conteúdo a imprimir na folha usando Graphics2D e drawString,
     * conforme o padrão.
     *
     * @param g    contexto gráfico para desenhar o conteúdo
     * @param pf   formato da página (dimensões e margens)
     * @param page número da página a imprimir (0-indexed)
     * @return PAGE_EXISTS se a página existe, NO_SUCH_PAGE caso contrário
     * @throws PrinterException se ocorrer um erro durante a impressão
     */
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
        if (page > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        int y = 40;

        g.setFont(new Font("Helvetica", Font.BOLD, 18));
        g.drawString("Extrato de Reparação", 150, y);
        y = y + 15;

        g.drawLine(50, y, 500, y);
        y = y + 30;

        g.setFont(new Font("Helvetica", Font.BOLD, 12));
        g.drawString("Dados do Processo", 50, y);
        y = y + 25;

        g.setFont(new Font("Helvetica", Font.PLAIN, 11));
        g.drawString("Nº Processo: " + reparacao.getNumReparacao(), 70, y);
        y = y + 20;
        g.drawString("Cliente: " + nomeCliente, 70, y);
        y = y + 20;
        g.drawString("Reparação efetuada por: " + nomeFuncionario, 70, y);
        y = y + 20;
        g.drawString("Equipamento: " + equipamentoInfo, 70, y);
        y = y + 30;

        g.setFont(new Font("Helvetica", Font.BOLD, 12));
        g.drawString("Histórico de Ações", 50, y);
        y = y + 25;

        g.setFont(new Font("Helvetica", Font.PLAIN, 11));
        g.drawString("Pedido submetido por " + nomeCliente + " em "
                + (reparacao.getDataCriacao() != null ? reparacao.getDataCriacao() : "N/A"), 70, y);
        y = y + 20;
        if (reparacao.getDataInicio() != null) {
            g.drawString("Reparação iniciada por " + nomeFuncionario + " em " + reparacao.getDataInicio(), 70, y);
            y = y + 20;
        }
        if (reparacao.getDataFim() != null) {
            g.drawString("Reparação concluída por " + nomeFuncionario + " em " + reparacao.getDataFim(), 70, y);
            y = y + 20;
        }
        y = y + 10;

        g.setFont(new Font("Helvetica", Font.BOLD, 12));
        g.drawString("Resumo", 50, y);
        y = y + 25;

        g.setFont(new Font("Helvetica", Font.PLAIN, 11));
        g.drawString("Estado: " + reparacao.getEstado(), 70, y);
        y = y + 20;
        g.drawString("Tempo Decorrido: " + reparacao.getTempoDecorrido() + " minutos", 70, y);
        y = y + 20;

        g.setFont(new Font("Helvetica", Font.BOLD, 13));
        DecimalFormat formatoMoeda = new DecimalFormat("0.00");
        g.drawString("Custo Total: " + formatoMoeda.format(reparacao.getCusto()) + " EUR", 70, y);
        y = y + 20;

        if (reparacao.getObservacoes() != null && !reparacao.getObservacoes().isEmpty()) {
            y = y + 10;
            g.setFont(new Font("Helvetica", Font.BOLD, 12));
            g.drawString("Observações", 50, y);
            y = y + 25;

            g.setFont(new Font("Helvetica", Font.PLAIN, 11));
            g.drawString(reparacao.getObservacoes(), 70, y);
            y = y + 20;
        }

        y = y + 10;
        g.drawLine(50, y, 500, y);
        y = y + 20;

        g.setFont(new Font("Helvetica", Font.ITALIC, 9));
        g.drawString("Sistema de Gestão de Oficina - Extrato gerado automaticamente", 100, y);

        return PAGE_EXISTS;
    }

    /**
     * Inicia o processo de impressão do extrato, configurando o trabalho de
     * impressão
     * e exibindo a caixa de diálogo nativa para o utilizador confirmar a operação.
     * Em caso de aprovação, o documento é enviado para a impressora selecionada.
     *
     * @param pai componente pai para mensagens de erro
     */
    public void imprimir(Component pai) {
        PrinterJob job = PrinterJob.getPrinterJob();

        job.setPrintable(this);

        boolean ok = job.printDialog();

        if (ok) {
            try {
                job.print();
            } catch (PrinterException ex) {
                Utilitarios.mostrarErro(pai, "Erro na impressão: " + ex.getMessage());
            }
        }
    }
}
