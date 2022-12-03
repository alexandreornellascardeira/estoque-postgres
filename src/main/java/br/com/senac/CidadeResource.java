package br.com.senac;

import br.com.senac.db.CidadeRepository;
import br.com.senac.model.Cidade;
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

@Path("/cidade")
public class CidadeResource {

  //Acesso ao token informado no HEADER ...
  @HeaderParam("token") String token;
  
  //Acesso ao banco de dados...
  private final CidadeRepository cidadeRepository;

  public CidadeResource(CidadeRepository cidadeRepository) {
    this.cidadeRepository = cidadeRepository;
  }
  
  //Controle de acesso via token...
  private void validaToken()throws Exception{

    if(token==null || !token.equals("0123456789")){
      throw new Exception("Token inválido!");
    }

  }

  //Lista produtos de um fornecedor...   
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/findByIdEstado/{id}")
  public List<Cidade> findByIdEstado(int id) throws Exception {

    //validaToken();
    return cidadeRepository.findByIdEstado(id);
  }

  //Obtêm um produto...
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Cidade get(@PathParam("id") int id) throws Exception {

    validaToken();
    return cidadeRepository.findById(id);
  }

  //Incluir cidade...
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Cidade post(Cidade cidade) throws Exception {

    validaToken();
    return cidadeRepository.insert(
        cidade
    );

  }

  //Atualizar produto...
  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  public Cidade put(Cidade cidade) throws Exception {

    validaToken();
    return cidadeRepository.update(cidade);
  }

  //Excluir produto...
  @DELETE
  @Path("/{id}")
  @Produces(MediaType.TEXT_PLAIN)
  public String delete(@PathParam("id") int id )throws Exception {

    validaToken();
    return cidadeRepository.delete(id);
  }
}
