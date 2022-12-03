package br.com.senac.model;

/**
 *
 * @author Alexandre Ornellas 
 */
public class PosicaoEstoque {

    private int idDeposito;
    private int idProduto;
    private String cdDeposito;
    private String dsDeposito;
    private String dsCidade;
    private String cdEstado;
    private String cdFornecedor;
    private String dsFornecedor;
    private String cdProduto;
    private String dsProduto;
    private int QtAtual;
    private int QtMinima;
    private float vlEstoquePrecoMedio;
    private String lastMsg;

    public PosicaoEstoque() {

    }

    public PosicaoEstoque
    (
        int idDeposito,
        String cdDeposito,
        String dsDeposito,
        String dsCidade,
        String cdEstado,
        String cdFornecedor,
        String dsFornecedor,
        String cdProduto,
        String dsProduto,
        int QtAtual,
        int QtMinima,
        float vlEstoquePrecoMedio

    ){
    
        
        this.idDeposito=idDeposito;
        this.cdDeposito=cdDeposito;
        this.dsDeposito=dsDeposito;
        this.dsCidade=dsCidade;
        this.cdEstado=cdEstado;
        this.cdFornecedor=cdFornecedor;
        this.dsFornecedor=dsFornecedor;
        this.cdProduto=cdProduto;
        this.dsProduto=dsProduto;
        this.QtAtual=QtAtual;
        this.QtMinima=QtMinima;
        this.vlEstoquePrecoMedio=vlEstoquePrecoMedio;
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
    public String getCdDeposito() {
        return cdDeposito;
    }

    /**
     * @param cdDeposito the cdDeposito to set
     */
    public void setCdDeposito(String cdDeposito) {
        this.cdDeposito = cdDeposito;
    }

    /**
     * @return the dsDeposito
     */
    public String getDsDeposito() {
        return dsDeposito;
    }

    /**
     * @param dsDeposito the dsDeposito to set
     */
    public void setDsDeposito(String dsDeposito) {
        this.dsDeposito = dsDeposito;
    }

    /**
     * @return the dsCidade
     */
    public String getDsCidade() {
        return dsCidade;
    }

    /**
     * @param dsCidade the dsCidade to set
     */
    public void setDsCidade(String dsCidade) {
        this.dsCidade = dsCidade;
    }

    /**
     * @return the cdEstado
     */
    public String getCdEstado() {
        return cdEstado;
    }

    /**
     * @param cdEstado the cdEstado to set
     */
    public void setCdEstado(String cdEstado) {
        this.cdEstado = cdEstado;
    }

    /**
     * @return the cdFornecedor
     */
    public String getCdFornecedor() {
        return cdFornecedor;
    }

    /**
     * @param cdFornecedor the cdFornecedor to set
     */
    public void setCdFornecedor(String cdFornecedor) {
        this.cdFornecedor = cdFornecedor;
    }

    /**
     * @return the dsFornecedor
     */
    public String getDsFornecedor() {
        return dsFornecedor;
    }

    /**
     * @param dsFornecedor the dsFornecedor to set
     */
    public void setDsFornecedor(String dsFornecedor) {
        this.dsFornecedor = dsFornecedor;
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
     * @return the QtAtual
     */
    public int getQtAtual() {
        return QtAtual;
    }

    /**
     * @param QtAtual the QtAtual to set
     */
    public void setQtAtual(int QtAtual) {
        this.QtAtual = QtAtual;
    }

    /**
     * @return the QtMinima
     */
    public int getQtMinima() {
        return QtMinima;
    }

    /**
     * @param QtMinima the QtMinima to set
     */
    public void setQtMinima(int QtMinima) {
        this.QtMinima = QtMinima;
    }

    /**
     * @return the vlEstoquePrecoMedio
     */
    public float getVlEstoquePrecoMedio() {
        return vlEstoquePrecoMedio;
    }

    /**
     * @param vlEstoquePrecoMedio the vlEstoquePrecoMedio to set
     */
    public void setVlEstoquePrecoMedio(float vlEstoquePrecoMedio) {
        this.vlEstoquePrecoMedio = vlEstoquePrecoMedio;
    }

    /**
     * @return the lastMsg
     */
    public String getLastMsg() {
        return lastMsg;
    }

    /**
     * @param lastMsg the lastMsg to set
     */
    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
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

}
