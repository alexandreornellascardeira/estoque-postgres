package br.com.senac.db;

import br.com.senac.model.Cliente;
import io.agroal.api.AgroalDataSource;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.Types;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ClienteRepository {
     
    
  @Inject
  AgroalDataSource defaultDataSource;

  private static final String FIND_BY_ID = "{? = call ws.BUSCA_CLIENTE_REFCURSOR(?) }";
  
  private static final String FIND_BY_CD = "{? = call ws.BUSCA_CLIENTE_BY_CD(?) }";


  private static final String GET_CLIENTE_BY_ID_ESTADO  = "{? = call ws.BUSCA_CLIENTE_POR_ID_ESTADO_REFCURSOR(?) }";

  private static final String INCLUIR_CLIENTE = "{call ws.INCLUIR_CLIENTE(?,?,?,?,?)}";

  private static final String ALTERAR_CLIENTE= "{call ws.ALTERAR_CLIENTE(?,?,?,?,?)}";

  private static final String EXCLUIR_CLIENTE = "{call ws.EXCLUIR_CLIENTE(?,?)}";

   

  /*
  Pesquisa produto por ID...
  */
  public Cliente findById(int id) {

    try (Connection connection = defaultDataSource.getConnection();

        CallableStatement callableStatement = connection.prepareCall(FIND_BY_ID)) {

    	//PostgreSQL fecha o cursor se não iniciar uma transação...  
        connection.setAutoCommit(false);

      // Set the Out Parameter type to be of type CURSOR
      callableStatement.setInt(1, id);

      callableStatement.registerOutParameter(2, Types.REF_CURSOR);
      
      callableStatement.execute(); // Execute the statement

      // Cast the returned parameter, (defined as type, OracleTypes.CURSOR)
      // to a JDBC result-set.

      Cliente cliente=null;
      
      try (ResultSet resultSet = (ResultSet) callableStatement.getObject(2)) {
        if (resultSet.next()) {

          cliente = new Cliente(
              resultSet.getInt("ID"),
              resultSet.getString("CD_CLIENTE"),
              resultSet.getString("DS_CLIENTE"),
              resultSet.getInt("ID_CIDADE"),
              resultSet.getString("DS_CIDADE"),
              resultSet.getString("CD_ESTADO")
          );

        }
        
        connection.commit();
        
        return cliente;

      }

    } catch (SQLException e) {
      throw new PersistenceException(e);
    }
    
  }
  
  
  /*
  Pesquisa cliente por CPF/CNPJ...
  */
  public Cliente findByCd(String cd) {

    try (Connection connection = defaultDataSource.getConnection();

        CallableStatement callableStatement = connection.prepareCall(FIND_BY_CD)) {

    	//PostgreSQL fecha o cursor se não iniciar uma transação...  
        connection.setAutoCommit(false);

      // Set the Out Parameter type to be of type CURSOR
      callableStatement.setString(1, cd);

      callableStatement.registerOutParameter(2, Types.REF_CURSOR);
      
      callableStatement.execute(); // Execute the statement

      // Cast the returned parameter, (defined as type, OracleTypes.CURSOR)
      // to a JDBC result-set.

      Cliente cliente=null;
      
      try (ResultSet resultSet = (ResultSet) callableStatement.getObject(2)) {
        if (resultSet.next()) {

          cliente = new Cliente(
              resultSet.getInt("ID"),
              resultSet.getString("CD_CLIENTE"),
              resultSet.getString("DS_CLIENTE"),
              resultSet.getInt("ID_CIDADE"),
              resultSet.getString("DS_CIDADE"),
              resultSet.getString("CD_ESTADO")
          );

        }
        
        connection.commit();
        
        return cliente;

      }

    } catch (SQLException e) {
      throw new PersistenceException(e);
    }
    
  }

  /*
  Retorna lista de produtos de um fornecedor....
  */
  public List<Cliente> findByIdEstado(int id) {
      
    List<Cliente> result = new ArrayList<>();
    
    try (Connection connection = defaultDataSource.getConnection();
    
        CallableStatement callableStatement = connection.prepareCall(GET_CLIENTE_BY_ID_ESTADO)) {
      
    	//PostgreSQL fecha o cursor se não iniciar uma transação...  
        connection.setAutoCommit(false);

          callableStatement.setInt(1, id);
   
          // Set the Out Parameter type to be of type CURSOR
          callableStatement.registerOutParameter(2, Types.REF_CURSOR);
          
          callableStatement.execute(); // Execute the statement
   
         // Cast the returned parameter, (defined as type, OracleTypes.CURSOR)
         // to a JDBC result-set. 
         
        ResultSet resultSet = (ResultSet)callableStatement.getObject(2);
        
        while (resultSet.next()) {
              
            
                    
            Cliente deposito = new Cliente(
                    resultSet.getInt("ID"),
                    resultSet.getString("CD_CLIENTE"),
                    resultSet.getString("DS_CLIENTE"),
                    resultSet.getInt("ID_CIDADE"),
                    resultSet.getString("DS_CIDADE"),
                    resultSet.getString("CD_ESTADO")
                  );

                  //System.out.println(produto.toString());
                  result.add(deposito);
            
           
          }

          connection.commit();
          
          return result;
      
    } catch (SQLException e) {
      throw new PersistenceException("Erro ao listar depósitos da UF!", e.getCause());
    }
    
  }

  /*
  Insere novo cliente...
  */
  public Cliente insert(Cliente cliente) {

   

    try (Connection connection = defaultDataSource.getConnection();

        CallableStatement callableStatement = connection.prepareCall(INCLUIR_CLIENTE)) {

        // cd
        callableStatement.setString(1, cliente.getCd());

      // ds
      callableStatement.setString(2, cliente.getDs());

      // id_Cidade
      callableStatement.setInt(3, cliente.getIdCidade());

      // id
      callableStatement.registerOutParameter(4, java.sql.Types.INTEGER);

      // ret_msg
      callableStatement.registerOutParameter(5, java.sql.Types.VARCHAR);

      // executa package...
      callableStatement.executeUpdate();

      cliente.setId(callableStatement.getInt(4));

      // Salva mensagem...
      cliente.setLastMsg(callableStatement.getString(5));

    } catch (SQLException e) {
      throw new PersistenceException(e);
    }

    return cliente;
  }

  public Cliente update(Cliente cliente) {

   
    try (Connection connection = defaultDataSource.getConnection();

        CallableStatement callableStatement = connection.prepareCall(ALTERAR_CLIENTE)) {
 
       // id
       callableStatement.setInt(1, cliente.getId());
 
       // cd_produto
       callableStatement.setString(2 ,cliente.getCd());
 
       // ds_produto
       callableStatement.setString(3, cliente.getDs());
 
       // qt_minima
       callableStatement.setInt(4, cliente.getIdCidade());
 
 
       // ret_msg
       callableStatement.registerOutParameter(5, java.sql.Types.VARCHAR);
 
       // executa package...
       callableStatement.executeUpdate();
  
 
       // Salva mensagem...
       cliente.setLastMsg(callableStatement.getString(5));

    } catch (SQLException e) {
      throw new PersistenceException(e);
    }
    return cliente;
  }

  public String delete(int id) {

    try (Connection connection = defaultDataSource.getConnection();

        CallableStatement callableStatement = connection.prepareCall( EXCLUIR_CLIENTE)) {

    	
       // id
       callableStatement.setInt(1, id);
 
       // ret_msg
       callableStatement.registerOutParameter(2, java.sql.Types.VARCHAR);
 
       // executa package...
       callableStatement.executeUpdate();


      // Salva mensagem...
      return  callableStatement.getString(2);


    } catch (SQLException e) {
      throw new PersistenceException(e);

    }

  }
}
