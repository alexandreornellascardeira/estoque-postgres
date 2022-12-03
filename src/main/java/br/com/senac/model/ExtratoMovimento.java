package br.com.senac.model;

//import java.sql.Date;

public class ExtratoMovimento {

    /**
     * @return the dtInicial
     */
    public String getDtInicial() {
        return dtInicial;
    }

    /**
     * @param dtInicial the dtInicial to set
     */
    public void setDtInicial(String dtInicial) {
        this.dtInicial = dtInicial;
    }

    /**
     * @return the dtFinal
     */
    public String getDtFinal() {
        return dtFinal;
    }

    /**
     * @param dtFinal the dtFinal to set
     */
    public void setDtFinal(String dtFinal) {
        this.dtFinal = dtFinal;
    }

    /**
     * @return the idProduto
     */
    public int getIdProduto() {
        return idProduto;
    }

    /**
     * @param idProduto the idProduto to set
     */
    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    /**
     * @return the idDeposito
     */
    public int getIdDeposito() {
        return idDeposito;
    }

    /**
     * @param idDeposito the idDeposito to set
     */
    public void setIdDeposito(int idDeposito) {
        this.idDeposito = idDeposito;
    }

    /**
     * @return the cdDeposito
     */
    public String getDsDeposito() {
        return dsDeposito;
    }

    /**
     * @param dsDeposito the cdDeposito to set
     */
    public void setDsDeposito(String dsDeposito) {
        this.dsDeposito = dsDeposito;
    }

    /**
     * @return the dtInclusao
     */
    public String getDtInclusao() {
        return dtInclusao;
    }

    /**
     * @param dtInclusao the dtInclusao to set
     */
    public void setDtInclusao(String dtInclusao) {
        this.dtInclusao = dtInclusao;
    }

    /**
     * @return the idMovimento
     */
    public int getIdMovimento() {
        return idMovimento;
    }

    /**
     * @param idMovimento the idMovimento to set
     */
    public void setIdMovimento(int idMovimento) {
        this.idMovimento = idMovimento;
    }

    /**
     * @return the cdProduto
     */
    public String getCdProduto() {
        return cdProduto;
    }

    /**
     * @param cdProduto the cdProduto to set
     */
    public void setCdProduto(String cdProduto) {
        this.cdProduto = cdProduto;
    }

    /**
     * @return the dsProduto
     */
    public String getDsProduto() {
        return dsProduto;
    }

    /**
     * @param dsProduto the dsProduto to set
     */
    public void setDsProduto(String dsProduto) {
        this.dsProduto = dsProduto;
    }

    /**
     * @return the dsCliente
     */
    public String getDsCliente() {
        return dsCliente;
    }

    /**
     * @param dsCliente the dsCliente to set
     */
    public void setDsCliente(String dsCliente) {
        this.dsCliente = dsCliente;
    }

    /**
     * @return the idCliente
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * @param idCliente the idCliente to set
     */
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    /**
     * @return the qtProduto
     */
    public int getQtProduto() {
        return qtProduto;
    }

    /**
     * @param qtProduto the qtProduto to set
     */
    public void setQtProduto(int qtProduto) {
        this.qtProduto = qtProduto;
    }

    /**
     * @return the vlUnitario
     */
    public float getVlUnitario() {
        return vlUnitario;
    }

    /**
     * @param vlUnitario the vlUnitario to set
     */
    public void setVlUnitario(float vlUnitario) {
        this.vlUnitario = vlUnitario;
    }

    /**
     * @return the vlTotalItem
     */
    public float getVlTotalItem() {
        return vlTotalItem;
    }

    /**
     * @param vlTotalItem the vlTotalItem to set
     */
    public void setVlTotalItem(float vlTotalItem) {
        this.vlTotalItem = vlTotalItem;
    }

    /**
     * @return the qtUtilizada
     */
    public int getQtUtilizada() {
        return qtUtilizada;
    }

    /**
     * @param qtUtilizada the qtUtilizada to set
     */
    public void setQtUtilizada(int qtUtilizada) {
        this.qtUtilizada = qtUtilizada;
    }

    /**
     * @return the qtSaldo
     */
    public int getQtSaldo() {
        return qtSaldo;
    }

    /**
     * @param qtSaldo the qtSaldo to set
     */
    public void setQtSaldo(int qtSaldo) {
        this.qtSaldo = qtSaldo;
    }

    private String dtInicial;
    private String dtFinal;
    private int idProduto;

    private int idDeposito;
    private String dsDeposito;
    private String dtInclusao;
    private int idMovimento;
    private String cdProduto;
    private String dsProduto;
    private String dsCliente;
    private int idCliente;
    private int qtProduto;
    private float vlUnitario;
    private float vlTotalItem;
    private int qtUtilizada;
    private int qtSaldo;
    private String dsTpMovimento;

    public ExtratoMovimento(
            String dtInicial,
            String dtFinal,
            int idDeposito,
            int idProduto,
            String dsDeposito,
            String dtInclusao,
            int idMovimento,
            String cdProduto,
            String dsProduto,
            String dsCliente,
            int idCliente,
            int qtProduto,
            float vlUnitario,
            float vlTotalItem,
            int qtUtilizada,
            int qtSaldo,
            String dsTpMovimento
    ) {

        this.dtInicial = dtInicial;
        this.dtFinal = dtFinal;
        this.idDeposito = idDeposito;
        this.idProduto = idProduto;
        this.dsDeposito = dsDeposito;
        this.dtInclusao = dtInclusao;
        this.idMovimento = idMovimento;
        this.cdProduto = cdProduto;
        this.dsProduto = dsProduto;
        this.dsCliente = dsCliente;
        this.idCliente = idCliente;
        this.qtProduto = qtProduto;
        this.vlUnitario = vlUnitario;
        this.vlTotalItem = vlTotalItem;
        this.qtUtilizada = qtUtilizada;
        this.qtSaldo = qtSaldo;
        this.dsTpMovimento = dsTpMovimento;
    }

    public ExtratoMovimento() {
    }

    /**
     * @return the dsTpMovimento
     */
    public String getDsTpMovimento() {
        return dsTpMovimento;
    }

    /**
     * @param dsTpMovimento the dsTpMovimento to set
     */
    public void setDsTpMovimento(String dsTpMovimento) {
        this.dsTpMovimento = dsTpMovimento;
    }

}
