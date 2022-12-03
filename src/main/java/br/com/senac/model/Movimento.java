package br.com.senac.model;


/**
 *
 * @author Alexandre Ornellas
 */

public class Movimento {

    /**
     * @return the vlTotalMovimento
     */
    public double getVlTotalMovimento() {
        return vlTotalMovimento;
    }

    /**
     * @param vlTotalMovimento the vlTotalMovimento to set
     */
    public void setVlTotalMovimento(double vlTotalMovimento) {
        this.vlTotalMovimento = vlTotalMovimento;
    }

  
    
    private int id;
    private int idDeposito;
    private int idCliente;
    private int idTpMovimento;
    private String dsDeposito;
    private String dsCliente;
    private String dsTpMovimento;
    private double vlTotalMovimento;
    private String lastMsg;
    
    
    
    public Movimento(
         int id,
         int idDeposito,
         int idCliente,
         int idTpMovimento,
         String dsDeposito,
         String dsCliente,
         String dsTpMovimento,
         double vlTotalMovimento
         

    ){
        
        this.id=id;
        this.idDeposito=idDeposito;
        this.idCliente=idCliente;
        this.idTpMovimento =idTpMovimento;
        this.dsDeposito = dsDeposito;
        this.dsCliente = dsCliente;
        this.dsTpMovimento = dsTpMovimento;
        this.vlTotalMovimento=vlTotalMovimento;
    }
    
    public Movimento(){}
    
    @Override
     public String toString(){
        return new StringBuffer("Movimento - id: ").append(id).append(" idDeposito: ").append(getIdDeposito()).append(" idCliente: ").append(getIdCliente()).append(" idTpMovimento: ").append(getIdTpMovimento()).toString() ;
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
    public void setId(int id) {
        this.id = id;
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
     * @return the idTpMovimento
     */
    public int getIdTpMovimento() {
        return idTpMovimento;
    }

    /**
     * @param idTpMovimento the idTpMovimento to set
     */
    public void setIdTpMovimento(int idTpMovimento) {
        this.idTpMovimento = idTpMovimento;
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
