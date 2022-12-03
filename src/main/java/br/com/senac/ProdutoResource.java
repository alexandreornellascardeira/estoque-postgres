package br.com.senac;

import br.com.senac.db.ProdutoRepository;
import br.com.senac.model.Produto;
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
 * @author Alexandre Ornellas
 */

@Path("/produto")
public class ProdutoResource {

  //Acesso ao token informado no HEADER ...
  @HeaderParam("token") String token;
  
  //Acesso ao banco de dados...
  private final ProdutoRepository produtoRepository;

  public ProdutoResource(ProdutoRepository produtoRepository) {
    this.produtoRepository = produtoRepository;
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
  @Path("/findByIdFornecedor/{id}")
  public List<Produto> findByIdFornecedor(int id) throws Exception {

    //validaToken();
    return produtoRepository.findByIdFornecedor(id);
  }

  //Obtêm um produto...
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Produto get(@PathParam("id") int id) throws Exception {

    validaToken();
    return produtoRepository.findById(id);
  }

  //Incluir produto...
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Produto post(Produto produto) throws Exception {

    validaToken();
    return produtoRepository.insert(
        produto
    );

  }

  //Atualizar produto...
  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  public Produto put(Produto produto) throws Exception {

    validaToken();
    return produtoRepository.update(produto);
  }

  //Excluir produto...
  @DELETE
  @Path("/{id}")
  @Produces(MediaType.TEXT_PLAIN)
  public String delete(@PathParam("id") int id )throws Exception {

    validaToken();
    return produtoRepository.delete(id);
  }
}