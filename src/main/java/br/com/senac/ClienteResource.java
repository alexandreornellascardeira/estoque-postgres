package br.com.senac;

import br.com.senac.db.ClienteRepository;
import br.com.senac.model.Cliente;
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

@Path("/cliente")
public class ClienteResource {

  //Acesso ao token informado no HEADER ...
  @HeaderParam("token") String token;
  
  //Acesso ao banco de dados...
  private final ClienteRepository clienteRepository;

  public ClienteResource(ClienteRepository clienteRepository) {
    this.clienteRepository = clienteRepository;
  }
  
  /**
   * Controle de acesso via token...
   * @throws Exception
   */
  private void validaToken()throws Exception{

    if(token==null || !token.equals("0123456789")){
      throw new Exception("Token inválido!");
    }

  }

  /**
   * Lista clientes localizados em uma UF...   
   * @param id
   * @return
   * @throws Exception
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/findByIdEstado/{id}")
  public List<Cliente> findByIdEstado(int id) throws Exception {

    //validaToken();
    return clienteRepository.findByIdEstado(id);
  }

  /**
   * Pesquisa cliente pelo CNPJ/CPF...   
   * @param id
   * @return
   * @throws Exception
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/findByCd/{cd}")
  public Cliente findByIdCd(String cd) throws Exception {

    //validaToken();
    return clienteRepository.findByCd(cd);
  }

  
  /**
   * Obtêm um cliente...
   * @param id
   * @return
   * @throws Exception
   */
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Cliente get(@PathParam("id") int id) throws Exception {

    validaToken();
    return clienteRepository.findById(id);
  }

  /**
   * Incluir cliente...
   * @param cliente
   * @return
   * @throws Exception
   */
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Cliente post(Cliente cliente) throws Exception {

    Log.info(cliente.toString());
    
     
    validaToken();
    return clienteRepository.insert(
        cliente
    );

  }

  //Atualizar fornecedor...
  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  public Cliente put(Cliente cliente) throws Exception {

    validaToken();
    return clienteRepository.update(cliente);
  }

  //Excluir produto...
  @DELETE
  @Path("/{id}")
  @Produces(MediaType.TEXT_PLAIN)
  public String delete(@PathParam("id") int id )throws Exception {

    validaToken();
    return clienteRepository.delete(id);
  }
}
