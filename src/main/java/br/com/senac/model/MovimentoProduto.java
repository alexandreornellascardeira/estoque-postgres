package br.com.senac.model;

import java.sql.Date;


/**
 *
 * @author Alexandre Ornellas
 */

public class MovimentoProduto {

   

    

    private int id;
    private int idMovimento;
    private String dtInclusao;
    private double vlUnitario;
    private int idProduto;
    private int qtProduto;
    private double vlTotalItem;
    private int qtUtilizada;
    private int idMovimentoProdutoRef;
    private int tpMovimento;
    private String dsTpMovimento;
    private String dsDeposito;
    private String dsCliente;
    private String lastMsg;
    private int qtSaldo;
    private String cdProduto;
    private String dsProduto;
    
    
    
    public MovimentoProduto(
         
        int id,
        int idMovimento,
        String dtInclusao,
        double vlUnitario,
        int idProduto,
        int qtProduto,
        double vlTotalItem,        
        int qtUtilizada,
        int idMovimentoProdutoRef,
        int tpMovimento,
        String dsTpMovimento,
        String dsDeposito,
        String dsCliente,
        int qtSaldo,
        String cdProduto,
        String dsProduto
         
            

    ){
        
        this.id =id;
        this.idMovimento=idMovimento;
        this.dtInclusao=dtInclusao;
        this.vlUnitario=vlUnitario;
        this.idProduto=idProduto;
        this.qtProduto=qtProduto;
        this.vlTotalItem=vlTotalItem;
        this.qtUtilizada=qtUtilizada;
        this.idMovimentoProdutoRef=idMovimentoProdutoRef;
        this.tpMovimento=tpMovimento;
        this.dsTpMovimento=dsTpMovimento;
        this.dsDeposito=dsDeposito;
        this.dsCliente=dsCliente; 
        this.qtSaldo = qtSaldo;
        this.cdProduto = cdProduto;
        this.dsProduto = dsProduto;
        
         
    }
    
    public MovimentoProduto(){}
    
    @Override
     public String toString(){
        return new StringBuffer("MovimentoProduto - id: ").append(id).append(" idMovimento: ").append(idMovimento).append(" idProduto: ").append(idProduto).append(" qtProduto: ").append(qtProduto).append(" vlUnitario: ").append(vlUnitario).append(" Ref: ").append(idMovimentoProdutoRef).toString() ;
    }

    
      
    
 /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public MovimentoProduto setId(int id) {
        this.id = id;
        return this;
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
    public MovimentoProduto setIdMovimento(int idMovimento) {
        this.idMovimento = idMovimento;
        return this;
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
    public MovimentoProduto setDtInclusao(String dtInclusao) {
        this.dtInclusao = dtInclusao;
        return this;
    }

    /**
     * @return the vlUnitario
     */
    public double getVlUnitario() {
        return vlUnitario;
    }

    /**
     * @param vlUnitario the vlUnitario to set
     */
    public MovimentoProduto setVlUnitario(double vlUnitario) {
        this.vlUnitario = vlUnitario;
        return this;
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
    public MovimentoProduto setIdProduto(int idProduto) {
        this.idProduto = idProduto;
        return this;
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
    public MovimentoProduto setQtProduto(int qtProduto) {
        this.qtProduto = qtProduto;
        return this;
    }

    /**
     * @return the vlTotalItem
     */
    public double getVlTotalItem() {
        return vlTotalItem;
    }

    /**
     * @param vlTotalItem the vlTotalItem to set
     */
    public MovimentoProduto setVlTotalItem(double vlTotalItem) {
        this.vlTotalItem = vlTotalItem;
        return this;
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
    public MovimentoProduto setQtUtilizada(int qtUtilizada) {
        this.qtUtilizada = qtUtilizada;
        return this;
    }

    /**
     * @return the idMovimentoProdutoRef
     */
    public int getIdMovimentoProdutoRef() {
        return idMovimentoProdutoRef;
    }

    /**
     * @param idMovimentoProdutoRef the idMovimentoProdutoRef to set
     */
    public MovimentoProduto setIdMovimentoProdutoRef(int idMovimentoProdutoRef) {
        this.idMovimentoProdutoRef = idMovimentoProdutoRef;
        return this;
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
    public MovimentoProduto setDsTpMovimento(String dsTpMovimento) {
        this.dsTpMovimento = dsTpMovimento;
        return this;
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
    public MovimentoProduto setDsDeposito(String dsDeposito) {
        this.dsDeposito = dsDeposito;
        return this;
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
    public MovimentoProduto setDsCliente(String dsCliente) {
        this.dsCliente = dsCliente;
        return this;
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
    public MovimentoProduto setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
        return this;
    }

    /**
     * @return the tpMovimento
     */
    public int getTpMovimento() {
        return tpMovimento;
    }

    /**
     * @param tpMovimento the tpMovimento to set
     */
    public MovimentoProduto setTpMovimento(int tpMovimento) {
        this.tpMovimento = tpMovimento;
        return this;
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
    public MovimentoProduto setQtSaldo(int qtSaldo) {
        this.qtSaldo = qtSaldo;
        return this;
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
    public MovimentoProduto setCdProduto(String cdProduto) {
        this.cdProduto = cdProduto;
        return this;
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
    public MovimentoProduto setDsProduto(String dsProduto) {
        this.dsProduto = dsProduto;
        return this;
    }
    

}
