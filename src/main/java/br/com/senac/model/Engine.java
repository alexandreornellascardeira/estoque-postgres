package br.com.senac.model;

/**
 * Parâmetros utilizados pelo aplicativo...
 * @author alexandreornellascardeira
 *
 */
public class Engine {
	
	
	private int id;
	private  String acao;
	private  String cmdIn;
	private  String dtApp;
	private  String versao;
	private  String lat;
	private  String lon;
	private String cdGcm;
	
	private String cdOpcao;
	private String cdSession;
	private String cmdOut;
	private String cmdInf;
	
	public Engine() {}
	
	
	public Engine( 
	  int id,
	  String cdSession,
	  String acao,
	  String cmdIn,
	  String dtApp,
	  String versao,
	  String lat,
	  String lon,
	  String cdGcm,
	  String cdOpcao,
	  String cmdOut,
	  String cmdInf) {
		
		
		this.id=id;
		
		this.cdSession = cdSession;
		this.acao= acao;
		this.cmdIn=cmdIn;
		this.dtApp=dtApp;
	
		this.versao=versao;
		this.lat=lat;
		this.lon=lon;
		this.cdGcm=cdGcm;
		this.cdOpcao = cdOpcao;
		this.cmdOut =cmdOut;
		this.cmdInf = cmdInf;
		
	}
	
	
	 @Override
     public String toString(){
        return new StringBuffer("Engine - id: ").append(id).append(" session: ").append(cdSession).append(" acao: ").append(acao).append(" cmdin: ").append(cmdIn).append(" cmdout: ").append(cmdOut).append(" cmdinf: ").append(cmdInf).append(" cdopcao: ").append(cdOpcao).toString() ;
    }

	 
	 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	public String getCmdIn() {
		return cmdIn;
	}
	public void setCmdIn(String cmdIn) {
		this.cmdIn = cmdIn;
	}
	public String getDtApp() {
		return dtApp;
	}
	public void setDtApp(String dtApp) {
		this.dtApp = dtApp;
	}
	public String getVersao() {
		return versao;
	}
	public void setVersao(String versao) {
		this.versao = versao;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public String getCdGcm() {
		return cdGcm;
	}
	public void setCdGcm(String cdGcm) {
		this.cdGcm = cdGcm;
	}


	public String getCdOpcao() {
		return cdOpcao;
	}


	public void setCdOpcao(String cdOpcao) {
		this.cdOpcao = cdOpcao;
	}


	public String getCdSession() {
		return cdSession;
	}


	public void setCdSession(String cdSession) {
		this.cdSession = cdSession;
	}


	public String getCmdOut() {
		return cmdOut;
	}


	public void setCmdOut(String cmdOut) {
		this.cmdOut = cmdOut;
	}


	public String getCmdInf() {
		return cmdInf;
	}


	public void setCmdInf(String cmdInf) {
		this.cmdInf = cmdInf;
	}
	
	

}
