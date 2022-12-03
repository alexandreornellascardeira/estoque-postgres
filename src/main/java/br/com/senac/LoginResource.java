package br.com.senac;

import br.com.senac.db.LoginRepository;
import br.com.senac.model.Login;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import io.quarkus.logging.Log;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;

/**
 *
 * @author Alexandre Ornellas
 */

@Path("/login")
public class LoginResource {
    
   


  //Acesso ao token informado no HEADER ...
  @HeaderParam("token") String token;
  
  //Acesso ao banco de dados...
  private final LoginRepository loginRepository;

  public LoginResource(LoginRepository loginRepository) {
    this.loginRepository = loginRepository;
  }
  
  //Controle de acesso via token...
  private void validaToken()throws Exception{

    if(token==null || !token.equals("0123456789")){
      throw new Exception("Token inválido!");
    }

  }

  

  /** 
   * Validar login...
   * @param login
   * @return
   * @throws Exception
   */
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Login post(Login login) throws Exception {

    Log.info(login.toString());
    
    validaToken();
    return loginRepository.validaLogin(
        login
    );

  }
  
  /**
   * Novo login...
   * @param login
   * @return
   * @throws Exception
   */
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/novo")
  public Login novo(Login login) throws Exception {

	Log.info("LoginResource: novo");
    Log.info(login.toString());
    
    validaToken();
    
    return loginRepository.insert(
        login
    );

  }

  
  //Retorna login através do ID de sessão...   
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/loadSession/{session}")
  public Login findByCdSession(String session) throws Exception {

    //Log.info(session);
    validaToken();
    return loginRepository.findByCdSession(session);
  }
  
  //Atualizar senha...
  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  public Login put(Login login) throws Exception {

    validaToken();
    
    Login oldLogin = new Login(
            login.getId(),
            login.getLogin(),
            login.getLastPassword(),
            login.getNome(),
            null,
            login.getEmail(),
            login.getUrlProfile()
    );
    
    Login newLogin = loginRepository.validaLogin(oldLogin);
    
    if(!newLogin.getLastMsg().equals("OK"))
    {
    
        login.setLastMsg("A senha anterior não confere!");
        return login;
    }
    
    return loginRepository.update(login);
  }
  
  //Excluir login...
  @DELETE
  @Path("/{id}")
  @Produces(MediaType.TEXT_PLAIN)
  public String delete(@PathParam("id") int id )throws Exception {

    validaToken();
    return loginRepository.delete(id);
  }
  
  //Pesquisar por ID...
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Login get(@PathParam("id") int id )throws Exception {

    validaToken();
    return loginRepository.findById(id);
  }

  
}
