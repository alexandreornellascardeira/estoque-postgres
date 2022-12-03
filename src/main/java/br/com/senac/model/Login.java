package br.com.senac.model;


/**
 *
 * @author Alexandre Ornellas
 */

public class Login {

    
    private int id;
    private String login;
    private String password;
    private String lastPassword;
    private String nome;
    private String lastMsg;
    private String session;
   
    private String urlProfile;
    private String email;
     
    public Login(
         int id,
         String login,
         String password,
         String nome,
         String lastPassword,
         String email,
         String urlProfile
         

    ){
        
        this.id=id;
        this.login=login;
        this.password=password;
        this.nome =nome;
        this.lastPassword =lastPassword;
        this.email = email;
        this.urlProfile =urlProfile;
        
    }
    
    public Login() {
        
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
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the cdLogin to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the cdPassword to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the Nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the Nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
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
     * @return the lastPassword
     */
    public String getLastPassword() {
        return lastPassword;
    }

    /**
     * @param lastPassword the lastPassword to set
     */
    public void setLastPassword(String lastPassword) {
        this.lastPassword = lastPassword;
    }
    
    public String toString(){
        return new StringBuffer("login: ").append(login).append(" pass: ").append(password).toString() ;
    }

    /**
     * @return the session
     */
    public String getSession() {
        return session;
    }

    /**
     * @param session the session to set
     */
    public void setSession(String session) {
        this.session = session;
    }

	 
	public String getUrlProfile() {
		return urlProfile;
	}

	public void setUrlProfile(String urlProfile) {
		this.urlProfile = urlProfile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    

}
