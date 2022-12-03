package br.com.senac.model;


/**
 *
 * @author Alexandre Ornellas
 */

public class Deposito {
    
    
    
    private int id;
    private String cd;
    private String ds;
    
    private int idCidade;
    
    private String dsCidade;
    private String cdEstado;
    
    private String lastMsg;
    
    
    
    public Deposito(
         int id,
         String cd,
         String ds,
         int idCidade,
         String dsCidade,
         String cdEstado
         

    ){
        
        this.id=id;
        this.cd=cd;
        this.ds=ds;
        this.idCidade =idCidade;
        this.dsCidade=dsCidade;
        this.cdEstado =cdEstado;
    }
    
    public Deposito(){}
    
    @Override
     public String toString(){
        return new StringBuffer("Depósito - cd: ").append(cd).append(" ds: ").append(ds).append(" idCidade: ").append(idCidade).toString() ;
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
     * @return the cd
     */
    public String getCd() {
        return cd;
    }

    /**
     * @param cd the cd to set
     */
    public void setCd(String cd) {
        this.cd = cd;
    }

    /**
     * @return the ds
     */
    public String getDs() {
        return ds;
    }

    /**
     * @param ds the ds to set
     */
    public void setDs(String ds) {
        this.ds = ds;
    }

    /**
     * @return the idCidade
     */
    public int getIdCidade() {
        return idCidade;
    }

    /**
     * @param idCidade the idCidade to set
     */
    public void setIdCidade(int idCidade) {
        this.idCidade = idCidade;
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
    

    
      
    

    

}
