package br.com.senac;

import br.com.senac.db.RecursoRepository;
import br.com.senac.model.Recurso;
import io.quarkus.logging.Log;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 *
 * @author Alexandre Ornellas
 */

@Path("/recurso")
public class RecursoResource {

  //Acesso ao token informado no HEADER ...
  @HeaderParam("token") String token;
  
  //Acesso ao banco de dados...
  private final RecursoRepository recursoRepository;

  public RecursoResource(RecursoRepository recursoRepository) {
    this.recursoRepository = recursoRepository;
  }
  
  /**Controle de acesso via token...
   * 
   * @throws Exception
   */
  private void validaToken()throws Exception{

    if(token==null || !token.equals("0123456789")){
      throw new Exception("Token inválido!");
    }

  }

  /**Lista recursos localizados em uma UF...   
   * 
   * @param id
   * @return
   * @throws Exception
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/findByIdLogin/{id}")
  public List<Recurso> findByIdLogin(int id) throws Exception {

    //validaToken();
    return recursoRepository.findByIdLogin(id);
  }

  /**Obtêm um recurso...
   * 
   * @param id
   * @return
   * @throws Exception
   */
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Recurso get(@PathParam("id") int id) throws Exception {

    validaToken();
    return recursoRepository.findById(id);
  }

  /**Incluir recurso...
   * 
   * @param recurso
   * @return
   * @throws Exception
   */
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Recurso post(Recurso recurso) throws Exception {

    Log.info(recurso.toString());
    
     
    validaToken();
    return recursoRepository.insert(
        recurso
    );

  }

  //Atualizar recurso...
  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  public Recurso put(Recurso recurso) throws Exception {

    validaToken();
    return recursoRepository.update(recurso);
  }

  //Excluir recurso...
  @DELETE
  @Path("/{id}")
  @Produces(MediaType.TEXT_PLAIN)
  public String delete(@PathParam("id") int id )throws Exception {

    validaToken();
    return recursoRepository.delete(id);
  }
  
  //Atribuir recurso a um login...
  @POST
  @Produces(MediaType.TEXT_PLAIN)
  @Path("/atribuir")
  public String atribuir(Recurso recurso) throws Exception {

    Log.info(recurso.toString());
    
     
    validaToken();
    return recursoRepository.insertRecursoLogin(
        recurso.getId(),
        recurso.getIdLogin()
    );

  }
  
//Atribuir recurso a um login...
  @POST
  @Produces(MediaType.TEXT_PLAIN)
  @Path("/desatribuir")
  public String desatribuir(Recurso recurso) throws Exception {

    Log.info(recurso.toString());
    
     
    validaToken();
    return recursoRepository.excluirRecursoLogin(
        recurso.getId(),
        recurso.getIdLogin()
    );

  }
}
