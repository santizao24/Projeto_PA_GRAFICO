package controlador;

import java.util.Random;

import model.Equipamento;
import model.Utilizador;

import java.util.ArrayList;

import repositorio.EquipamentoDAO;

/**
 * Controlador responsável pela lógica de negócio relacionada com equipamentos.
 * Faz a ponte entre a camada de apresentação (View) e a camada de acesso a dados (DAO)
 * para operações de registo, listagem, pesquisa e validação de equipamentos.
 *
 * @author Santiago
 * @version 1.0
 */
public class ControladorEquipamento {

    private EquipamentoDAO eDao = new EquipamentoDAO();
    private ControladorLog cLog = new ControladorLog();

    /**
     * Regista um novo equipamento associado a um cliente.
     *
     * @param clienteLogado  utilizador (cliente) proprietário do equipamento
     * @param marca          marca do equipamento
     * @param codModelo      código do modelo do equipamento
     * @param codSKU         código SKU único gerado para o equipamento
     * @param dataFabrico    data de fabrico do equipamento
     * @param lote           identificação do lote de fabrico
     * @param usernameLogado username do utilizador autenticado (para registo de log)
     */
    public void registarEquipamento(Utilizador clienteLogado, String marca, String codModelo, int codSKU,
            String dataFabrico, String lote, String usernameLogado) {
        Equipamento eq = new Equipamento(0, clienteLogado.getIdUtilizador(), marca, codModelo, codSKU, dataFabrico,
                lote);

        eDao.inserirEquipamento(clienteLogado, eq);
        cLog.registarAcao(usernameLogado, "Registou um novo equipamento (SKU: " + codSKU + ").");
    }

    /**
     * Lista todos os equipamentos pertencentes a um cliente.
     *
     * @param idCliente identificador do cliente
     * @return lista de equipamentos do cliente
     */
    public ArrayList<Equipamento> listarEquipamentos(int idCliente) {
        return eDao.listarEquipamentosCliente(idCliente);
    }

    /**
     * Gera um código SKU único que não existe na base de dados.
     *
     * @return código SKU único gerado aleatoriamente
     */
    public int gerarSkuUnico() {
        Random rd = new Random();
        int novoSku;

        do {
            novoSku = rd.nextInt(1000000) + 1;
        } while (eDao.skuExiste(novoSku));

        return novoSku;
    }

    /**
     * Verifica se um código SKU já existe na base de dados.
     *
     * @param sku código SKU a verificar
     * @return {@code true} se o SKU já existir, {@code false} caso contrário
     */
    public boolean skuExiste(int sku) {
        return eDao.skuExiste(sku);
    }

    /**
     * Verifica se um código de modelo já existe na base de dados.
     *
     * @param codigoModelo código de modelo a verificar
     * @return {@code true} se o modelo já existir, {@code false} caso contrário
     */
    public boolean modeloExiste(String codigoModelo) {
        return eDao.modeloExiste(codigoModelo);
    }

    /**
     * Pesquisa equipamentos de todos os clientes com base num critério e termo de pesquisa.
     *
     * @param escolha critério de pesquisa (1 = marca, 2 = código de modelo)
     * @param termo   termo de pesquisa parcial
     * @return lista de equipamentos que correspondem aos critérios
     */
    public ArrayList<Equipamento> pesquisarEquipamentos(int escolha, String termo) {
        return eDao.pesquisarEquipamentos(escolha, termo);
    }

    /**
     * Lista os equipamentos de um cliente ordenados por um critério.
     *
     * @param IdCliente  identificador do cliente
     * @param escolha    critério de ordenação (1 = marca, 2 = código SKU)
     * @param ascendente {@code true} para ordem crescente, {@code false} para decrescente
     * @return lista de equipamentos ordenados
     */
    public ArrayList<Equipamento> listarEquipamentosClienteOrdenados(int IdCliente, int escolha, boolean ascendente) {
        return eDao.listarEquipamentosClienteOrdenados(IdCliente, escolha, ascendente);
    }

    /**
     * Pesquisa equipamentos de um cliente específico com base num critério e termo.
     *
     * @param IdCliente identificador do cliente
     * @param escolha   critério de pesquisa (1 = marca, 2 = código SKU)
     * @param termo     termo de pesquisa parcial
     * @return lista de equipamentos que correspondem aos critérios
     */
    public ArrayList<Equipamento> pesquisarEquipamentosCliente(int IdCliente, int escolha, String termo) {
        return eDao.pesquisarEquipamentosCliente(IdCliente, escolha, termo);
    }
}