package br.com.senac.db;

import br.com.senac.model.Deposito;
import io.agroal.api.AgroalDataSource;

import java.sql.Types;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class DepositoRepository {
     
    
  @Inject
  AgroalDataSource defaultDataSource;

  private static final String FIND_BY_ID = "{? = call ws.BUSCAR_DEPOSITO(?) }";

  private static final String GET_DEPOSITO_BY_ID_ESTADO  = "{? = call ws.BUSCAR_DEPOSITO_POR_ID_ESTADO(?) }";

  private static final String INCLUIR_DEPOSITO = "{call ws.INCLUIR_DEPOSITO(?,?,?,?,?) }";

  private static final String ALTERAR_DEPOSITO = "{call ws.ALTERAR_DEPOSITO(?,?,?,?,?) }";

  private static final String EXCLUIR_DEPOSITO = "{call ws.EXCLUIR_DEPOSITO(?,?) }";

   

  /*
  Pesquisa produto por ID...
  */
  public Deposito findById(int id) {

    try (Connection connection = defaultDataSource.getConnection();

        CallableStatement callableStatement = connection.prepareCall(FIND_BY_ID)) {

    	//PostgreSQL fecha o cursor se não iniciar uma transação...  
        connection.setAutoCommit(false);

      callableStatement.setInt(1, id);

      // Set the Out Parameter type to be of type CURSOR
      callableStatement.registerOutParameter(2, Types.REF_CURSOR);
      

      callableStatement.execute(); // Execute the statement

      // Cast the returned parameter, (defined as type, OracleTypes.CURSOR)
      // to a JDBC result-set.

      Deposito deposito = null;
      
      try (ResultSet resultSet = (ResultSet) callableStatement.getObject(2)) {
        if (resultSet.next()) {

        	deposito= new Deposito(
              resultSet.getInt("ID"),
              resultSet.getString("CD_DEPOSITO"),
              resultSet.getString("DS_DEPOSITO"),
              resultSet.getInt("ID_CIDADE"),
              resultSet.getString("DS_CIDADE"),
              resultSet.getString("CD_ESTADO")
          );

        }

      }
      
      connection.commit();
      
      return deposito;

    } catch (SQLException e) {
      throw new PersistenceException(e);
    }
     
  }

  /*
  Retorna lista de produtos de um fornecedor....
  */
  public List<Deposito> findByIdEstado(int id) {
      
    List<Deposito> result = new ArrayList<>();
    
    try (Connection connection = defaultDataSource.getConnection();
    
        CallableStatement callableStatement = connection.prepareCall(GET_DEPOSITO_BY_ID_ESTADO)) {
      
    	
    	//PostgreSQL fecha o cursor se não iniciar uma transação...  
        connection.setAutoCommit(false);

          callableStatement.setObject(1, id);

          // Set the Out Parameter type to be of type CURSOR
          callableStatement.registerOutParameter(2, Types.REF_CURSOR);
         
   
          callableStatement.execute(); // Execute the statement
   
         // Cast the returned parameter, (defined as type, OracleTypes.CURSOR)
         // to a JDBC result-set. 
         
        ResultSet resultSet = (ResultSet)callableStatement.getObject(2);
        
        while (resultSet.next()) {
              
            
                    
            Deposito deposito = new Deposito(
                    resultSet.getInt("ID"),
                    resultSet.getString("CD_DEPOSITO"),
                    resultSet.getString("DS_DEPOSITO"),
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
  Insere novo depósito...
  */
  public Deposito insert(Deposito deposito) {

    try (Connection connection = defaultDataSource.getConnection();

        CallableStatement callableStatement = connection.prepareCall(INCLUIR_DEPOSITO)) {
          
      // cd_deposito
      callableStatement.setString(1, deposito.getCd());
      
     // ds_deposito
      callableStatement.setString(2, deposito.getDs());

      // id_cidade
      callableStatement.setInt(3, deposito.getIdCidade());

      // id
      callableStatement.registerOutParameter(4, java.sql.Types.INTEGER);

      // ret_msg
      callableStatement.registerOutParameter(5, java.sql.Types.VARCHAR);

      // executa package...
      callableStatement.executeUpdate();

      deposito.setId(callableStatement.getInt(4));

      // Salva mensagem...
      deposito.setLastMsg(callableStatement.getString(5));

    } catch (SQLException e) {
      throw new PersistenceException(e);
    }

    return deposito;
  }

  public Deposito update(Deposito deposito) {

    try (Connection connection = defaultDataSource.getConnection();

        CallableStatement callableStatement = connection.prepareCall(ALTERAR_DEPOSITO)) {

       // id
       callableStatement.setInt(1, deposito.getId());
 
       // cd
       callableStatement.setString(2, deposito.getCd());
       
       // ds
       callableStatement.setString(3, deposito.getDs());
 
 
       // id_cidade
       callableStatement.setInt(4, deposito.getIdCidade());
 
 
       // ret_msg
       callableStatement.registerOutParameter(5, java.sql.Types.VARCHAR);
 
       // executa package...
       callableStatement.executeUpdate();
  
 
       // Salva mensagem...
       deposito.setLastMsg(callableStatement.getString(5));

    } catch (SQLException e) {
      throw new PersistenceException(e);
    }
    return deposito;
  }

  public String delete(int id) {

    try (Connection connection = defaultDataSource.getConnection();

        CallableStatement callableStatement = connection.prepareCall(EXCLUIR_DEPOSITO)) {

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
