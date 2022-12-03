package br.com.senac;

import br.com.senac.db.FornecedorRepository;
import br.com.senac.model.Fornecedor;
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

@Path("/fornecedor")
public class FornecedorResource {

  //Acesso ao token informado no HEADER ...
  @HeaderParam("token") String token;
  
  //Acesso ao banco de dados...
  private final FornecedorRepository fornecedorRepository;

  public FornecedorResource(FornecedorRepository fornecedorRepository) {
    this.fornecedorRepository = fornecedorRepository;
  }
  
  //Controle de acesso via token...
  private void validaToken()throws Exception{

    if(token==null || !token.equals("0123456789")){
      throw new Exception("Token inválido!");
    }

  }

  //Lista fornecedores localizados em uma UF...   
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/findByIdEstado/{id}")
  public List<Fornecedor> findByIdEstado(int id) throws Exception {

    //validaToken();
    return fornecedorRepository.findByIdEstado(id);
  }

  //Obtêm um produto...
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Fornecedor get(@PathParam("id") int id) throws Exception {

    validaToken();
    return fornecedorRepository.findById(id);
  }

  //Incluir fornecedor...
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Fornecedor post(Fornecedor fornecedor) throws Exception {

    Log.info(fornecedor.toString());
    
     
    validaToken();
    return fornecedorRepository.insert(
        fornecedor
    );

  }

  //Atualizar fornecedor...
  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  public Fornecedor put(Fornecedor fornecedor) throws Exception {

    Log.info(fornecedor.toString());
    validaToken();
    return fornecedorRepository.update(fornecedor);
  }

  //Excluir produto...
  @DELETE
  @Path("/{id}")
  @Produces(MediaType.TEXT_PLAIN)
  public String delete(@PathParam("id") int id )throws Exception {

    validaToken();
    return fornecedorRepository.delete(id);
  }
}
