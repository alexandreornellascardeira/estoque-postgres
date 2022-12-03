package br.com.senac;
 

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
 

import br.com.senac.db.EngineRepository; 
import br.com.senac.model.Engine;

@Path("/engine")
public class EngineResource {
	
	//Acesso ao token informado no HEADER ...
	  @HeaderParam("token") String token;
	  
	  //Acesso ao banco de dados...
	  private final EngineRepository engineRepository;

	  public EngineResource(EngineRepository engineRepository) {
	    this.engineRepository = engineRepository;
	  }
	  
	  //Controle de acesso via token...
	  private void validaToken()throws Exception{

	    if(token==null || !token.equals("0123456789")){
	      throw new Exception("Token inválido!");
	    }

	  }

	  
	  //Engine do App Mobile
	  @POST
	  @Produces(MediaType.APPLICATION_JSON)
	  public Engine post(Engine engine) throws Exception {

	    validaToken();
	    return engineRepository.exec(engine);
	    

	  }
	  

}
