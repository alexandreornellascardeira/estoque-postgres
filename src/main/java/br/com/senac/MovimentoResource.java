package br.com.senac;

import br.com.senac.db.MovimentoRepository;
import br.com.senac.model.Movimento;
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

@Path("/movimento")
public class MovimentoResource {

  //Acesso ao token informado no HEADER ...
  @HeaderParam("token") String token;
  
  //Acesso ao banco de dados...
  private final MovimentoRepository movimentoRepository;

  public MovimentoResource(MovimentoRepository movimentoRepository) {
    this.movimentoRepository = movimentoRepository;
  }
  
  //Controle de acesso via token...
  private void validaToken()throws Exception{

    if(token==null || !token.equals("0123456789")){
      throw new Exception("Token inválido!");
    }

  }

  //Lista movimento de um tipo...   
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/findByTpMovimento/{id}")
  public List<Movimento> findByTpMovimento(int id) throws Exception {

    //validaToken();
    return movimentoRepository.findByTpMovimento(id);
  }

  //Obtêm um movimento...
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Movimento get(@PathParam("id") int id) throws Exception {

    validaToken();
    return movimentoRepository.findById(id);
  }

  //Incluir movimento...
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Movimento post(Movimento movimento) throws Exception {

    Log.info(movimento.toString());
    
    validaToken();
    return movimentoRepository.insert(
        movimento
    );

  }

  //Atualizar movimento...
  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  public Movimento put(Movimento movimento) throws Exception {

    validaToken();
    return movimentoRepository.update(movimento);
  }

  //Excluir movimento...
  @DELETE
  @Path("/{id}")
  @Produces(MediaType.TEXT_PLAIN)
  public String delete(@PathParam("id") int id )throws Exception {

    validaToken();
    return movimentoRepository.delete(id);
  }
}
