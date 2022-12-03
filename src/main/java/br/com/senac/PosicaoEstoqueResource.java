package br.com.senac;


import br.com.senac.db.PosicaoEstoqueRepository;
import br.com.senac.model.PosicaoEstoque;
import java.util.List;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Alexandre Ornellas
 */

@Path("/posicao_estoque")
public class PosicaoEstoqueResource {
    
  //Acesso ao token informado no HEADER ...
  @HeaderParam("token") String token;
  
  //Acesso ao banco de dados...
  private final PosicaoEstoqueRepository posicaoEstoqueRepository;

  public PosicaoEstoqueResource(PosicaoEstoqueRepository posicaoEstoqueRepository) {
    this.posicaoEstoqueRepository = posicaoEstoqueRepository;
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
  public List<PosicaoEstoque> post(PosicaoEstoque posicaoEstoque) throws Exception {

    validaToken();
    return posicaoEstoqueRepository.getPosicaoEstoque(
        posicaoEstoque.getIdDeposito(),
        posicaoEstoque.getIdProduto()
         
    );

  }
    
}
