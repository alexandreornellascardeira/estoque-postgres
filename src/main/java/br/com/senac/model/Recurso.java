package br.com.senac.model;


/**
 *
 * @author Alexandre Ornellas
 */

public class Recurso {
    
    
    
    private int id;
    private String cdRecurso;
    private String tpIcone;
    
    
    private String msgMenu;
    private String msgSubMenu;
    private String sqlProcedure;
    private int idLogin;
    
    private String lastMsg;
    
    
    
    public Recurso(
         int id,
         String cdRecurso,
         String tpIcone ,
         String msgMenu,
         String msgSubMenu,
         String sqlProcedure
         

    ){
        
        this.id=id;
        this.cdRecurso=cdRecurso;
        this.tpIcone=tpIcone;
        
        this.msgMenu=msgMenu;
        this.msgSubMenu=msgSubMenu;
        this.sqlProcedure=sqlProcedure;
         
    }
    
    public Recurso(){}
    
    @Override
     public String toString(){
        return new StringBuffer("Recurso - cd: ").append(cdRecurso).append(" Menu: ").append(msgMenu).append(" SubMenu: ").append(msgSubMenu).toString() ;
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
    public String getCdRecurso() {
        return cdRecurso;
    }

    /**
     * @param cd the cd to set
     */
    public void setCdRecurso(String cdRecurso) {
        this.cdRecurso = cdRecurso;
    }

    /**
     * @return the ds
     */
    public String getTpIcone() {
        return tpIcone;
    }

    /**
     * @param ds the ds to set
     */
    public void setTpIcone(String tpIcone) {
        this.tpIcone = tpIcone;
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

	public String getMsgMenu() {
		return msgMenu;
	}

	public void setMsgMenu(String msgMenu) {
		this.msgMenu = msgMenu;
	}

	public String getMsgSubMenu() {
		return msgSubMenu;
	}

	public void setMsgSubMenu(String msgSubMenu) {
		this.msgSubMenu = msgSubMenu;
	}

	public String getSqlProcedure() {
		return sqlProcedure;
	}

	public void setSqlProcedure(String sqlProcedure) {
		this.sqlProcedure = sqlProcedure;
	}

	public int getIdLogin() {
		return idLogin;
	}

	public void setIdLogin(int idLogin) {
		this.idLogin = idLogin;
	}
    

    
      
    

    

}
