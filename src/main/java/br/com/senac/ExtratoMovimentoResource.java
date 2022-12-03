package br.com.senac;


import br.com.senac.db.ExtratoMovimentoRepository;
import br.com.senac.model.ExtratoMovimento;
import java.util.List;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Alexandre Ornellas
 */

@Path("/extrato_movimento")
public class ExtratoMovimentoResource {
  
//Acesso ao token informado no HEADER ...
  @HeaderParam("token") String token;
  
  //Acesso ao banco de dados...
  private final ExtratoMovimentoRepository extratoMovimentoRepository;

  public ExtratoMovimentoResource(ExtratoMovimentoRepository extratoMovimentoRepository) {
    this.extratoMovimentoRepository = extratoMovimentoRepository;
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
  public List<ExtratoMovimento> post(ExtratoMovimento extratoMovimento) throws Exception {

    validaToken();
    return extratoMovimentoRepository.getExtratoMovimento(
        extratoMovimento.getDtInicial(),
        extratoMovimento.getDtFinal(),
        extratoMovimento.getIdDeposito(),
        extratoMovimento.getIdProduto()
         
    );

  }  
}
