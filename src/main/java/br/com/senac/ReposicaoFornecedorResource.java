package br.com.senac;


import br.com.senac.db.ReposicaoFornecedorRepository;
import br.com.senac.model.ReposicaoFornecedor;
import java.util.List;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Alexandre Ornellas
 */

@Path("/reposicao_fornecedor")
public class ReposicaoFornecedorResource {
    
  //Acesso ao token informado no HEADER ...
  @HeaderParam("token") String token;
  
  //Acesso ao banco de dados...
  private final ReposicaoFornecedorRepository reposicaoFornecedorRepository;

  public ReposicaoFornecedorResource(ReposicaoFornecedorRepository reposicaoFornecedorRepository) {
    this.reposicaoFornecedorRepository = reposicaoFornecedorRepository;
  }
  
  //Controle de acesso via token...
  private void validaToken()throws Exception{

    if(token==null || !token.equals("0123456789")){
      throw new Exception("Token inválido!");
    }

  }

  
  //Lista de posição de estoque
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public List<ReposicaoFornecedor> post(ReposicaoFornecedor reposicaoFornecedor) throws Exception {

    validaToken();
    return reposicaoFornecedorRepository.getReposicaoFornecedor(
        reposicaoFornecedor.getIdDeposito(),
        reposicaoFornecedor.getIdProduto()
         
    );

  }
    
}
