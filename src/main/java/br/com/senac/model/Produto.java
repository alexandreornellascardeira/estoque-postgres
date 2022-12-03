package br.com.senac.model;

 
import java.util.Date;
 
/**
 *
 * @author Alexandre Ornellas
 */

public class Produto {

   
    
    
  private int id;
  private String cd;
  private String ds;    
  private boolean inativo;
  private Date dtInativo;
  private int  qtMinima;
  private int idFornecedor;
  private String dsFornecedor;
  private String lastMsg;

  public Produto(
          int id, 
          String cd, 
          String ds, 
          boolean inativo, 
          Date dtInativo, 
          int qtMinima,
          int idFornecedor,
          String dsFornecedor
         ) 
  {
    this.id = id;
    this.cd = cd;
    this.ds = ds;
    this.inativo = inativo;
    this.dtInativo = dtInativo;
    this.qtMinima = qtMinima;
    this.idFornecedor = idFornecedor;
    this.dsFornecedor = dsFornecedor;
    
  }

  
  public String toString(){
    return new StringBuffer("cd: ").append(cd).append(" ds: ").append(ds).toString() ;

  }

  public Produto() {
  }

  public int getId() {
    return id;
  }

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
     * @return the inativo
     */
    public boolean isInativo() {
        return inativo;
    }

    /**
     * @param inativo the inativo to set
     */
    public void setInativo(boolean inativo) {
        this.inativo = inativo;
    }

    /**
     * @return the dtInativo
     */
    public Date getDtInativo() {
        return dtInativo;
    }

    /**
     * @param dtInativo the dtInativo to set
     */
    public void setDtInativo(Date dtInativo) {
        this.dtInativo = dtInativo;
    }

    /**
     * @return the qtMinima
     */
    public int getQtMinima() {
        return qtMinima;
    }

    /**
     * @param qtMinima the qtMinima to set
     */
    public void setQtMinima(int qtMinima) {
        this.qtMinima = qtMinima;
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
     * @return the idFornecedor
     */
    public int getIdFornecedor() {
        return idFornecedor;
    }

    /**
     * @param idFornecedor the idFornecedor to set
     */
    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
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
}
