package br.com.senac.db;

import br.com.senac.model.Login;
import io.agroal.api.AgroalDataSource;
 
import java.sql.Types;

import java.sql.CallableStatement;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException; 

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class LoginRepository {

  @Inject
  AgroalDataSource defaultDataSource;

  private static final String FIND_BY_ID = "{? = call ws.BUSCAR_LOGIN(?) }";
  
  private static final String FIND_BY_SESSION = "{? = call ws.BUSCAR_LOGIN_POR_SESSAO(?) }";

  private static final String VERIFICAR_LOGIN = "{call ws.CRIAR_SESSAO_USUARIO(?,?,?,?,?) }";

  private static final String INCLUIR_LOGIN = "{call ws.INCLUIR_LOGIN(?,?,?,?,?,?,?)}";

  private static final String ALTERAR_LOGIN = "{call ws.ALTERAR_LOGIN(?,?,?,?,?,?,?)}";

  private static final String EXCLUIR_LOGIN = "{call ws.EXCLUIR_LOGIN(?,?)}";
  
  

  /*
  Pesquisa produto por ID...
  */
  public Login findById(int id) {

    try (Connection connection = defaultDataSource.getConnection();

        CallableStatement callableStatement = connection.prepareCall(FIND_BY_ID)) {

    	//PostgreSQL fecha o cursor se não iniciar uma transação...  
        connection.setAutoCommit(false);

      callableStatement.setObject(1, id);

      // Set the Out Parameter type to be of type CURSOR
      callableStatement.registerOutParameter(2, Types.REF_CURSOR);
      

      callableStatement.execute(); // Execute the statement

      // Cast the returned parameter, (defined as type, OracleTypes.CURSOR)
      // to a JDBC result-set.

      Login login = null;
      
      try (ResultSet resultSet = (ResultSet) callableStatement.getObject(2)) {
        if (resultSet.next()) {

          login =  new Login(
              resultSet.getInt("ID"),
              resultSet.getString("CD_LOGIN"),
              resultSet.getString("CD_PASSWORD"),
              resultSet.getString("DS_LOGIN"),
              null,
              resultSet.getString("CD_EMAIL"),
              resultSet.getString("URL_PROFILE"));
          
        }

      }
      
      connection.commit();
      
     return login;

    } catch (SQLException e) {
      throw new PersistenceException(e);
    }
    
  }
  
  

  /*
  Pesquisa produto por ID...
  */
  public Login findByCdSession(String session) {

    try (Connection connection = defaultDataSource.getConnection();

        CallableStatement callableStatement = connection.prepareCall(FIND_BY_SESSION)) {

    	//PostgreSQL fecha o cursor se não iniciar uma transação...  
        connection.setAutoCommit(false);

        callableStatement.setString(1, session);

      // Set the Out Parameter type to be of type CURSOR
      callableStatement.registerOutParameter(2, Types.REF_CURSOR);
      

      callableStatement.execute(); // Execute the statement

      // Cast the returned parameter, (defined as type, OracleTypes.CURSOR)
      // to a JDBC result-set.

      
      Login login = null;
      
      try (ResultSet resultSet = (ResultSet) callableStatement.getObject(2)) {
        if (resultSet.next()) {

          login = new Login(
              resultSet.getInt("ID"),
              resultSet.getString("CD_LOGIN"),
              resultSet.getString("CD_PASSWORD"),
              resultSet.getString("DS_LOGIN"),
              null,
              resultSet.getString("CD_EMAIL"),
              resultSet.getString("URL_PROFILE"));
          
        }

      }
      
      connection.commit();
      
      return login;

    } catch (SQLException e) {
      throw new PersistenceException(e);
    }
     
  }


  /*
  Retorna login através do ID de sessão....
  */
  public Login validaLogin(Login login) {
      
    
    try (Connection connection = defaultDataSource.getConnection();
    
        CallableStatement callableStatement = connection.prepareCall(VERIFICAR_LOGIN)) {
      
           
          callableStatement.setObject(1, login.getLogin());
          callableStatement.setObject(2, login.getPassword());
        
          // p_cd_session
          callableStatement.registerOutParameter(3, java.sql.Types.VARCHAR);
          
           // p_id
          callableStatement.registerOutParameter(4, java.sql.Types.INTEGER);
   
          
          // p_mensagem
          callableStatement.registerOutParameter(5, java.sql.Types.VARCHAR);
   
          callableStatement.execute(); // Execute the statement
   
         // Cast the returned parameter, (defined as type, OracleTypes.CURSOR)
         // to a JDBC result-set. 
          
          
          //Retornar outros dados do usuário...
         if(callableStatement.getString(5).equals("OK")) {
         	 login= findById(callableStatement.getInt(4));
          }
         
         login.setSession(callableStatement.getString(3));
         login.setId(callableStatement.getInt(4));
         login.setLastMsg(callableStatement.getString(5));
         return login;
        
        
      
    } catch (SQLException e) {
      //throw new PersistenceException("Erro ao validar o login!" + e.getMessage(), e.getCause());
      login.setLastMsg("Usuário ou senha não confere!");
      return login;
    }
    
  }

  /*
  Insere novo produto...
  */
  public Login insert(Login login) {

    try (Connection connection = defaultDataSource.getConnection();

        CallableStatement callableStatement = connection.prepareCall(INCLUIR_LOGIN)) {


      // cd_login
      callableStatement.setString(1, login.getLogin());

      // cd_password
      callableStatement.setString(2, login.getPassword());

      // ds_login
      callableStatement.setString(3, login.getNome());
      
   // email
      callableStatement.setString(4, login.getEmail());
      
   // url_Profile
      callableStatement.setString(5, login.getUrlProfile() );

      // id
      callableStatement.registerOutParameter(6, java.sql.Types.INTEGER);

      // ret_msg
      callableStatement.registerOutParameter(7, java.sql.Types.VARCHAR);

      // executa package...
      callableStatement.executeUpdate();

      login.setId(callableStatement.getInt(6));

      // Salva mensagem...
      login.setLastMsg(callableStatement.getString(7));

    } catch (SQLException e) {
      throw new PersistenceException(e);
    }

    return login;
  }

  public Login update(Login login) {

    try (Connection connection = defaultDataSource.getConnection();

        CallableStatement callableStatement = connection.prepareCall(ALTERAR_LOGIN)) {


       // id
       callableStatement.setInt(1, login.getId());
 
       // cd_login
       callableStatement.setString(2, login.getLogin());

       // cd_password
       callableStatement.setString(3, login.getPassword());

        // ds_nome
        callableStatement.setString(4, login.getNome());
        
     // email
        callableStatement.setString(5, login.getEmail());
        
        // url_profile
        callableStatement.setString(6, login.getUrlProfile() );

       // ret_msg
       callableStatement.registerOutParameter(7, java.sql.Types.VARCHAR);
 
       // executa package...
       callableStatement.executeUpdate();
  
 
       // Salva mensagem...
       login.setLastMsg(callableStatement.getString(7));

    } catch (SQLException e) {
      throw new PersistenceException(e);
    }
    return login;
  }

  public String delete(int id) {

    try (Connection connection = defaultDataSource.getConnection();

        CallableStatement callableStatement = connection.prepareCall(EXCLUIR_LOGIN)) {

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
