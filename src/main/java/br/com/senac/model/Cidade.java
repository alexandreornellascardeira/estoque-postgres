package br.com.senac.model;


/**
 *
 * @author Alexandre Ornellas
 */
public class Cidade {

    public Cidade(int id, String dsCidade, int idEstado, String cdEstado) {
        this.id = id;
        this.ds = dsCidade;
        this.idEstado = idEstado;
        this.cdEstado = cdEstado;
    
    }
    
    public Cidade(){}
    
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
     * @return the idEstado
     */
    public int getIdEstado() {
        return idEstado;
    }

    /**
     * @param idEstado the idEstado to set
     */
    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
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
    
    private int id;
    private String ds;
    private int idEstado;
    private String cdEstado;
    private String lastMsg;
}
