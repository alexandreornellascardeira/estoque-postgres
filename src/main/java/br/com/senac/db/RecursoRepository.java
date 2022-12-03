package br.com.senac.db;

import br.com.senac.model.Recurso;
 
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
public class RecursoRepository {
     
    
  @Inject
  AgroalDataSource defaultDataSource;

  private static final String FIND_BY_ID = "{? = call ct.BUSCAR_RECURSO(?) }";

  private static final String GET_RECURSO_BY_ID_LOGIN = "{? = call ct.BUSCAR_RECURSO_POR_LOGIN(?) }";

  private static final String INCLUIR_RECURSO = "{call ct.INCLUIR_RECURSO(?,?,?,?,?,?,?)}";
   
  private static final String ALTERAR_RECURSO = "{call ct.ALTERAR_RECURSO(?,?,?,?,?,?,?)}";
   
  private static final String EXCLUIR_RECURSO= "{call ct.EXCLUIR_RECURSO(?,?)}";
  
  private static final String INCLUIR_RECURSO_LOGIN = "{call ct.INCLUIR_RECURSO_LOGIN(?,?,?,?)}";
   
  private static final String EXCLUIR_RECURSO_LOGIN = "{call ct.EXCLUIR_RECURSO_LOGIN(?,?,?)}";
  

  /*
  Pesquisa produto por ID...
  */
  public Recurso findById(int id) {

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

      Recurso recurso = null;
      
      try (ResultSet resultSet = (ResultSet) callableStatement.getObject(2)) {
        if (resultSet.next()) {

           recurso = new Recurso(
              resultSet.getInt("ID"),
              resultSet.getString("CD_RECURSO"),
              resultSet.getString("TP_ICONE"),
              resultSet.getString("msg_menu"),
    		  resultSet.getString("msg_submenu"),
			  resultSet.getString("sql_procedure")
          );
          

           
                 
        }
        
        connection.commit();
        
        return recurso;

      }

    } catch (SQLException e) {
      throw new PersistenceException(e);
    }
     
  }

  /*
  Retorna lista de recursoes localizados em uma UF....
  */
  public List<Recurso> findByIdLogin(int id) {
      
    List<Recurso> result = new ArrayList<>();
    
    try (Connection connection = defaultDataSource.getConnection();
    
        CallableStatement callableStatement = connection.prepareCall(GET_RECURSO_BY_ID_LOGIN)) {
      
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
              
            
                    
            Recurso  recurso = new Recurso(
                    resultSet.getInt("ID"),
                    resultSet.getString("CD_RECURSO"),
                    resultSet.getString("TP_ICONE"),
                    resultSet.getString("msg_menu"),
          		  resultSet.getString("msg_submenu"),
      			  resultSet.getString("sql_procedure")
                );
                

                  //System.out.println(produto.toString());
                  result.add(recurso);
            
           
          }
        
          connection.commit();


          return result;
      
    } catch (SQLException e) {
      throw new PersistenceException("Erro ao listar recursos por login: "+e.getMessage(), e.getCause());
    }
    
  }

  /*
  Insere novo depósito...
  */
  public Recurso insert(Recurso recurso) {

    try (Connection connection = defaultDataSource.getConnection();

        CallableStatement callableStatement = connection.prepareCall(INCLUIR_RECURSO)) {
 
      // cd Recurso
      callableStatement.setString(1, recurso.getCdRecurso());

      // tpIcone 
      callableStatement.setString(2, recurso.getTpIcone());

      // msgMenu 
      callableStatement.setString(3, recurso.getMsgMenu());
      
      // msgSubMenu 
      callableStatement.setString(4, recurso.getMsgSubMenu());
      
      // sqlProcedure 
      callableStatement.setString(5, recurso.getSqlProcedure());

      // id
      callableStatement.registerOutParameter(6, java.sql.Types.INTEGER);

      // ret_msg
      callableStatement.registerOutParameter(7, java.sql.Types.VARCHAR);

      // executa package...
      callableStatement.executeUpdate();

      recurso.setId(callableStatement.getInt(6));

      // Salva mensagem...
      recurso.setLastMsg(callableStatement.getString(7));
      

    } catch (SQLException e) {
      throw new PersistenceException(e);
    }

    return recurso;
  }

  public Recurso update(Recurso recurso) {

    try (Connection connection = defaultDataSource.getConnection();

        CallableStatement callableStatement = connection.prepareCall(ALTERAR_RECURSO)) {
 

        // id
      callableStatement.setInt(1,recurso.getId());

      // cd 
      callableStatement.setString(2, recurso.getCdRecurso());

      // icone 
      callableStatement.setString(3, recurso.getTpIcone());

   // menu 
      callableStatement.setString(4, recurso.getMsgMenu());

   // submenu 
      callableStatement.setString(5, recurso.getMsgSubMenu());

   // sqlprocedure 
      callableStatement.setString(6, recurso.getSqlProcedure());

      

      // ret_msg
      callableStatement.registerOutParameter(7, java.sql.Types.VARCHAR);
 
       // executa package...
       callableStatement.executeUpdate();
  
 
       // Salva mensagem...
       recurso.setLastMsg(callableStatement.getString(7));

    } catch (SQLException e) {
      throw new PersistenceException(e);
    }
    return recurso;
  }

  public String delete(int id) {

    try (Connection connection = defaultDataSource.getConnection();

        CallableStatement callableStatement = connection.prepareCall(EXCLUIR_RECURSO)) {


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
  
  

  /*
  Atribui recurso a um login...
  */
  public String insertRecursoLogin(int idRecurso, int idLogin ) {

    try (Connection connection = defaultDataSource.getConnection();

        CallableStatement callableStatement = connection.prepareCall(INCLUIR_RECURSO_LOGIN)) {
 
      // recurso
      callableStatement.setInt(1, idRecurso);

      // login 
      callableStatement.setInt(2, idLogin);
      
      // id
      callableStatement.registerOutParameter(3, java.sql.Types.INTEGER);

      // ret_msg
      callableStatement.registerOutParameter(4, java.sql.Types.VARCHAR);

      // executa package...
      callableStatement.executeUpdate();

      

      // Retorna mensagem do processamento..
      return callableStatement.getString(4);
      

    } catch (SQLException e) {
      throw new PersistenceException(e);
    }
 
  }

  

  /*
  Atribui recurso a um login...
  */
  public String excluirRecursoLogin(int idRecurso, int idLogin ) {

    try (Connection connection = defaultDataSource.getConnection();

        CallableStatement callableStatement = connection.prepareCall(EXCLUIR_RECURSO_LOGIN)) {
 
      // recurso
      callableStatement.setInt(1, idRecurso);

      // login 
      callableStatement.setInt(2, idLogin);
      
     
      // ret_msg
      callableStatement.registerOutParameter(3, java.sql.Types.VARCHAR);

      // executa package...
      callableStatement.executeUpdate();

      

      // Retorna mensagem do processamento..
      return callableStatement.getString(3);
      

    } catch (SQLException e) {
      throw new PersistenceException(e);
    }
 
  }
}
