package br.com.senac;

import br.com.senac.db.MovimentoProdutoRepository;
import br.com.senac.model.MovimentoProduto;
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

@Path("/item")
public class MovimentoProdutoResource {

  //Acesso ao token informado no HEADER ...
  @HeaderParam("token") String token;
  
  //Acesso ao banco de dados...
  private final MovimentoProdutoRepository movimentoProdutoRepository;

  public MovimentoProdutoResource(MovimentoProdutoRepository movimentoProdutoRepository) {
    this.movimentoProdutoRepository = movimentoProdutoRepository;
  }
  
  //Controle de acesso via token...
  private void validaToken()throws Exception{

    if(token==null || !token.equals("0123456789")){
      throw new Exception("Token inválido!");
    }

  }

  //Lista itens de movimento...   
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/findByMovimento/{id}")
  public List<MovimentoProduto> getMovimentoProdutoByIdMovimentoFunc(int id) throws Exception {

    //validaToken();
    return movimentoProdutoRepository.getMovimentoProdutoByIdMovimentoFunc(id);
  }

  //Lista referências...   
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/findRefs")
  public List<MovimentoProduto> getMovimentoProdutoRefFunc(MovimentoProduto movimentoProduto) throws Exception {

    Log.info("FindRefs: " + movimentoProduto.toString());
        
    //validaToken();
    return movimentoProdutoRepository.getMovimentoProdutoRefFunc(
        movimentoProduto.getIdMovimento(),
        movimentoProduto.getIdProduto()
    );
  }

  
  //Obtêm um movimento...
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public MovimentoProduto get(@PathParam("id") int id) throws Exception {

    validaToken();
    return movimentoProdutoRepository.findById(id);
  }

  //Incluir item de movimento...
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public MovimentoProduto post(MovimentoProduto movimentoProduto) throws Exception {

    Log.info("Incluindo ...."+movimentoProduto.toString());
    
    validaToken();
    return movimentoProdutoRepository.insert(
        movimentoProduto
    );

  }
  /*Apenas inclusão ...Para desfazer um movimento, crie um movimento reverso....
  //Atualizar movimento...
  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  public MovimentoProduto put(MovimentoProduto movimentoProduto) throws Exception {

    validaToken();
    return movimentoProdutoRepository.update(movimentoProduto);
  }*/

  /*
  //Excluir movimento...
  @DELETE
  @Path("/{id}")
  @Produces(MediaType.TEXT_PLAIN)
  public String delete(@PathParam("id") int id )throws Exception {

    validaToken();
    return movimentoProdutoRepository.delete(id);
  }
  
  */
  
  
}
