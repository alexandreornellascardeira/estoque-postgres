package br.com.senac;

import br.com.senac.db.DepositoRepository;
import br.com.senac.model.Deposito;
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

@Path("/deposito")
public class DepositoResource {

  //Acesso ao token informado no HEADER ...
  @HeaderParam("token") String token;
  
  //Acesso ao banco de dados...
  private final DepositoRepository depositoRepository;

  public DepositoResource(DepositoRepository depositoRepository) {
    this.depositoRepository = depositoRepository;
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

  /**Lista depósitos localizados em uma UF...   
   * 
   * @param id
   * @return
   * @throws Exception
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/findByIdEstado/{id}")
  public List<Deposito> findByIdEstado(int id) throws Exception {

    //validaToken();
    return depositoRepository.findByIdEstado(id);
  }

  /**Obtêm um depósito...
   * 
   * @param id
   * @return
   * @throws Exception
   */
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Deposito get(@PathParam("id") int id) throws Exception {

    validaToken();
    return depositoRepository.findById(id);
  }

  /**Incluir depósito...
   * 
   * @param deposito
   * @return
   * @throws Exception
   */
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Deposito post(Deposito deposito) throws Exception {

    Log.info(deposito.toString());
    
     
    validaToken();
    return depositoRepository.insert(
        deposito
    );

  }

  //Atualizar fornecedor...
  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  public Deposito put(Deposito deposito) throws Exception {

    validaToken();
    return depositoRepository.update(deposito);
  }

  //Excluir produto...
  @DELETE
  @Path("/{id}")
  @Produces(MediaType.TEXT_PLAIN)
  public String delete(@PathParam("id") int id )throws Exception {

    validaToken();
    return depositoRepository.delete(id);
  }
}
