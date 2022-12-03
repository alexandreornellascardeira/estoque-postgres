package br.com.senac.db;

import br.com.senac.model.Fornecedor;
 
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
public class FornecedorRepository {
     
    
  @Inject
  AgroalDataSource defaultDataSource;

  private static final String FIND_BY_ID = "{? = call ws.BUSCAR_FORNECEDOR(?) }";

  private static final String GET_FORNECEDOR_BY_ID_ESTADO  = "{? = call ws.BUSCAR_FORNECEDOR_POR_ID_ESTADO(?) }";

  private static final String INCLUIR_FORNECEDOR = "{call ws.INCLUIR_FORNECEDOR(?,?,?,?,?)}";
   
  private static final String ALTERAR_FORNECEDOR = "{call ws.ALTERAR_FORNECEDOR(?,?,?,?,?)}";
   
  private static final String EXCLUIR_FORNECEDOR = "{call ws.EXCLUIR_FORNECEDOR(?,?)}";
   

  /*
  Pesquisa produto por ID...
  */
  public Fornecedor findById(int id) {

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

      Fornecedor fornecedor = null;
      
      try (ResultSet resultSet = (ResultSet) callableStatement.getObject(2)) {
        if (resultSet.next()) {

           fornecedor = new Fornecedor(
              resultSet.getInt("ID"),
              resultSet.getString("CD_FORNECEDOR"),
              resultSet.getString("DS_FORNECEDOR"),
              resultSet.getInt("ID_CIDADE"),
              resultSet.getString("DS_CIDADE"),
              resultSet.getString("CD_ESTADO")
          );
          

           
                 
        }
        
        connection.commit();
        
        return fornecedor;

      }

    } catch (SQLException e) {
      throw new PersistenceException(e);
    }
     
  }

  /*
  Retorna lista de fornecedores localizados em uma UF....
  */
  public List<Fornecedor> findByIdEstado(int id) {
      
    List<Fornecedor> result = new ArrayList<>();
    
    try (Connection connection = defaultDataSource.getConnection();
    
        CallableStatement callableStatement = connection.prepareCall(GET_FORNECEDOR_BY_ID_ESTADO)) {
      
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
              
            
                    
            Fornecedor fornecedor = new Fornecedor(
                    resultSet.getInt("ID"),
                    resultSet.getString("CD_FORNECEDOR"),
                    resultSet.getString("DS_FORNECEDOR"),
                    resultSet.getInt("ID_CIDADE"),
                    resultSet.getString("DS_CIDADE"),
                    resultSet.getString("CD_ESTADO")
                  );

                  //System.out.println(produto.toString());
                  result.add(fornecedor);
            
           
          }
        
          connection.commit();


          return result;
      
    } catch (SQLException e) {
      throw new PersistenceException("Erro ao listar produtos por fornecedor!", e.getCause());
    }
    
  }

  /*
  Insere novo depósito...
  */
  public Fornecedor insert(Fornecedor fornecedor) {

    try (Connection connection = defaultDataSource.getConnection();

        CallableStatement callableStatement = connection.prepareCall(INCLUIR_FORNECEDOR)) {
 
      // cd 
      callableStatement.setString(1, fornecedor.getCd());

      // ds 
      callableStatement.setString(2, fornecedor.getDs());

      // id_cidade
      callableStatement.setInt(3, fornecedor.getIdCidade());


      // id
      callableStatement.registerOutParameter(4, java.sql.Types.INTEGER);

      // ret_msg
      callableStatement.registerOutParameter(5, java.sql.Types.VARCHAR);

      // executa package...
      callableStatement.executeUpdate();

      fornecedor.setId(callableStatement.getInt(4));

      // Salva mensagem...
      fornecedor.setLastMsg(callableStatement.getString(5));
      

    } catch (SQLException e) {
      throw new PersistenceException(e);
    }

    return fornecedor;
  }

  public Fornecedor update(Fornecedor fornecedor) {

    try (Connection connection = defaultDataSource.getConnection();

        CallableStatement callableStatement = connection.prepareCall(ALTERAR_FORNECEDOR)) {
 

        // id
      callableStatement.setInt(1,fornecedor.getId());

      // cd 
      callableStatement.setString(2, fornecedor.getCd());

      // ds 
      callableStatement.setString(3, fornecedor.getDs());

      // id_cidade
      callableStatement.setInt(4, fornecedor.getIdCidade());

      // ret_msg
      callableStatement.registerOutParameter(5, java.sql.Types.VARCHAR);
 
       // executa package...
       callableStatement.executeUpdate();
  
 
       // Salva mensagem...
       fornecedor.setLastMsg(callableStatement.getString(5));

    } catch (SQLException e) {
      throw new PersistenceException(e);
    }
    return fornecedor;
  }

  public String delete(int id) {

    try (Connection connection = defaultDataSource.getConnection();

        CallableStatement callableStatement = connection.prepareCall(EXCLUIR_FORNECEDOR)) {


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
