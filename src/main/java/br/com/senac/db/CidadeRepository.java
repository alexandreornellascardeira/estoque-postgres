package br.com.senac.db;

import br.com.senac.model.Cidade;

import io.agroal.api.AgroalDataSource;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CidadeRepository {

  @Inject
  AgroalDataSource defaultDataSource;

  private static final String FIND_BY_ID = "{? = call ws.BUSCAR_CIDADE(?) }";

  private static final String GET_CIDADE_BY_ID_ESTADO = "{? = call ws.BUSCA_CIDADE_POR_ID_ESTADO(?) }";

  private static final String INCLUIR_CIDADE = "{call ws.INCLUIR_CIDADE(?,?,?,?)}";

  private static final String ALTERAR_CIDADE = "{call ws.ALTERAR_CIDADE(?,?,?,?)}";

  private static final String EXCLUIR_CIDADE = "{call ws.EXCLUIR_CIDADE(?,?)}";
  
  /*
   * Pesquisa produto por ID...
   */
  public Cidade findById(int id) {

    try (Connection connection = defaultDataSource.getConnection();

        CallableStatement callableStatement = connection.prepareCall(FIND_BY_ID)) {

    	//PostgreSQL fecha o cursor se não iniciar uma transação...  
        connection.setAutoCommit(false);

        
      // Set the Out Parameter type to be of type CURSOR
    	

      callableStatement.setObject(1, id);
      callableStatement.registerOutParameter(2, Types.REF_CURSOR);

      callableStatement.execute(); // Execute the statement

      // Cast the returned parameter, (defined as type, OracleTypes.CURSOR)
      // to a JDBC result-set.

      Cidade cidade=null;
      
      try (ResultSet resultSet = (ResultSet) callableStatement.getObject(2)) {
        if (resultSet.next()) {

          cidade = new Cidade(
              resultSet.getInt("ID"),
              resultSet.getString("DS_CIDADE"),
              resultSet.getInt("ID_ESTADO"),
              resultSet.getString("CD_ESTADO"));

        }

      }

      connection.commit();
      
      return cidade;
      
    } catch (SQLException e) {
      throw new PersistenceException(e);
    }
     
  }

  /*
   * Retorna lista de cidade de um estado....
   */
  public List<Cidade> findByIdEstado(int id) {

    List<Cidade> result = new ArrayList<>();

    try (Connection connection = defaultDataSource.getConnection();

        CallableStatement callableStatement = connection.prepareCall(GET_CIDADE_BY_ID_ESTADO)) {

    	//PostgreSQL fecha o cursor se não iniciar uma transação...  
        connection.setAutoCommit(false);

      // Set the Out Parameter type to be of type CURSOR
      callableStatement.setObject(1, id);
      callableStatement.registerOutParameter(2, Types.REF_CURSOR);

      callableStatement.execute(); // Execute the statement

      // Cast the returned parameter, (defined as type, OracleTypes.CURSOR)
      // to a JDBC result-set.

      ResultSet resultSet = (ResultSet) callableStatement.getObject(2);

      while (resultSet.next()) {

        Cidade cidade = new Cidade(
            resultSet.getInt("ID"),
            resultSet.getString("DS_CIDADE"),
            resultSet.getInt("ID_ESTADO"),
            resultSet.getString("CD_ESTADO"));

        // System.out.println(produto.toString());
        result.add(cidade);

      }
      
      connection.commit();

      return result;

    } catch (SQLException e) {
    	
    
      throw new PersistenceException("Erro ao listar cidades por estado:" + e.getMessage(), e.getCause());
    }

  }

  /*
   * Insere novo depósito...
   */
  public Cidade insert(Cidade cidade) {

    try (Connection connection = defaultDataSource.getConnection();

        CallableStatement callableStatement = connection.prepareCall(INCLUIR_CIDADE)) {

      // ds
      callableStatement.setString(1, cidade.getDs());

      // idEstado
      callableStatement.setInt(2, cidade.getIdEstado());

      // id
      callableStatement.registerOutParameter(3, java.sql.Types.INTEGER);

      // ret_msg
      callableStatement.registerOutParameter(4, java.sql.Types.VARCHAR);

      // executa package...
      callableStatement.executeUpdate();

      cidade.setId(callableStatement.getInt(3));

      // Salva mensagem...
      cidade.setLastMsg(callableStatement.getString(4));

    } catch (SQLException e) {
      throw new PersistenceException(e);
    }

    return cidade;
  }

  public Cidade update(Cidade cidade) {

    try (Connection connection = defaultDataSource.getConnection();

        CallableStatement callableStatement = connection.prepareCall(ALTERAR_CIDADE)) {

      // id
      callableStatement.setInt(1, cidade.getId());

      // ds
      callableStatement.setString(2, cidade.getDs());

      // ds_deposito
      callableStatement.setInt(3, cidade.getIdEstado());

      // ret_msg
      callableStatement.registerOutParameter(4, java.sql.Types.VARCHAR);

      // executa package...
      callableStatement.executeUpdate();

      // Salva mensagem...
      cidade.setLastMsg(callableStatement.getString(4));

    } catch (SQLException e) {
      throw new PersistenceException(e);
    }
    return cidade;
  }

  public String delete(int id) {

    try (Connection connection = defaultDataSource.getConnection();

        CallableStatement callableStatement = connection.prepareCall(EXCLUIR_CIDADE)) {

      // id
      callableStatement.setInt(1, id);

      // ret_msg
      callableStatement.registerOutParameter(2, java.sql.Types.VARCHAR);

      // executa package...
      callableStatement.executeUpdate();

      // Salva mensagem...
      return callableStatement.getString(2);

    } catch (SQLException e) {
      throw new PersistenceException(e);

    }

  }
}
