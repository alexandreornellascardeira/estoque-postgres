package br.com.senac.db;

import br.com.senac.model.MovimentoProduto;
import io.agroal.api.AgroalDataSource;
import io.quarkus.logging.Log; 


import java.sql.Types;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList; 
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class MovimentoProdutoRepository {
     
    
  @Inject
  AgroalDataSource defaultDataSource;

  private static final String FIND_BY_ID = "{? = call ws.BUSCAR_MOVIMENTO_PRODUTO(?) }";

  private static final String GET_MOVIMENTO_PRODUTO_BY_MOVIMENTO  = "{? = call ws.BUSCAR_MOVIMENTO_PRODUTO_POR_ID_MOVIMENTO(?) }";
   
  private static final String GET_MOVIMENTO_PRODUTO_REF  = "{? = call ws.BUSCAR_MOVIMENTO_PRODUTO_REF(?,?) }";

  private static final String INCLUIR_MOVIMENTO_PRODUTO = "{call ws.INCLUIR_MOVIMENTO_PRODUTO(?,?,?,?,?,?,?)}";

  //private static final String ALTERAR_MOVIMENTO_PRODUTO = "{call ws.ALTERAR_MOVIMENTO_PRODUTO(?,?,?,?,?,?,?)}";
  
  private static final String EXCLUIR_MOVIMENTO_PRODUTO = "{call ws.EXCLUIR_MOVIMENTO_PRODUTO(?,?)}";



  /*
  Pesquisa produto por ID...
  */
  public MovimentoProduto findById(int id) {

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
      MovimentoProduto movimento = null;
      
      try (ResultSet resultSet = (ResultSet) callableStatement.getObject(2)) {
        if (resultSet.next()) {
        	
        	
        	String dateStr = resultSet.getTimestamp("DT_INCLUSAO").toLocalDateTime().toLocalDate().toString();
        	 
        	//20221016 
        	//Date date = Date.valueOf(dateStr);
        	
        	 
        	

          movimento =  new MovimentoProduto(
            resultSet.getInt("ID"),
            resultSet.getInt("ID_MOVIMENTO"),
            dateStr,
            resultSet.getDouble("VL_UNITARIO"),
            resultSet.getInt("ID_PRODUTO"),
            resultSet.getInt("QT_PRODUTO"),
            resultSet.getDouble("VL_TOTAL_ITEM"),        
            resultSet.getInt("QT_UTILIZADA"),
            resultSet.getInt("ID_MOVIMENTO_PRODUTO_REF"),
            resultSet.getInt("TP_MOVIMENTO"),
            resultSet.getString("DS_TP_MOVIMENTO"),
            resultSet.getString("DS_DEPOSITO"),
            resultSet.getString("DS_CLIENTE"),
            resultSet.getInt("QT_SALDO"),
            resultSet.getString("CD_PRODUTO"),
            resultSet.getString("DS_PRODUTO")
          );

        }

      }
      
      connection.commit();
      
      return movimento;
      

    } catch (SQLException e) {
      throw new PersistenceException(e);
    }
     
  }

  /*
  Retorna lista de itens de movimentos ....
  */
  public List<MovimentoProduto> getMovimentoProdutoByIdMovimentoFunc(int id) {
      
    List<MovimentoProduto> result = new ArrayList<>();
    
    try (Connection connection = defaultDataSource.getConnection();
    
        CallableStatement callableStatement = connection.prepareCall(GET_MOVIMENTO_PRODUTO_BY_MOVIMENTO)) {
      
    	//PostgreSQL fecha o cursor se não iniciar uma transação...  
        connection.setAutoCommit(false);
        
        callableStatement.setObject(1, id);

        // Set the Out Parameter type to be of type CURSOR
        callableStatement.registerOutParameter(2, Types.REF_CURSOR);

        callableStatement.execute(); // Execute the statement

        // Cast the returned parameter, (defined as type, OracleTypes.CURSOR)
        // to a JDBC result-set.

        ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
          
        while (resultSet.next()) {
        	 
        	//20221016 
        	
        	String dateStr = resultSet.getTimestamp("DT_INCLUSAO").toLocalDateTime().toLocalDate().toString();
        	 
        	//20221016 
        	//Date date = Date.valueOf(dateStr);
        	
              
            MovimentoProduto movimentoProduto = new MovimentoProduto(
                resultSet.getInt("ID"),
                resultSet.getInt("ID_MOVIMENTO"),
                dateStr,
                resultSet.getDouble("VL_UNITARIO"),
                resultSet.getInt("ID_PRODUTO"),
                resultSet.getInt("QT_PRODUTO"),
                resultSet.getDouble("VL_TOTAL_ITEM"),        
                resultSet.getInt("QT_UTILIZADA"),
                resultSet.getInt("ID_MOVIMENTO_PRODUTO_REF"),
                resultSet.getInt("TP_MOVIMENTO"),
                resultSet.getString("DS_TP_MOVIMENTO"),
                resultSet.getString("DS_DEPOSITO"),
                resultSet.getString("DS_CLIENTE"),
                resultSet.getInt("QT_SALDO"),
                resultSet.getString("CD_PRODUTO"),
                resultSet.getString("DS_PRODUTO")
          
            );

          //System.out.println(produto.toString());
          result.add(movimentoProduto);
        }
          
        
        connection.commit();
        
        return result;
      
    } catch (SQLException e) {
      throw new PersistenceException("Erro ao listar itens do movimento! [ "+e.getMessage()+" ]", e.getCause());
    }
    
  }
  
  /*
  Retorna lista de itens de referências ....
  */
  public List<MovimentoProduto> getMovimentoProdutoRefFunc(int idMovimento, int idProduto) {
      
    List<MovimentoProduto> result = new ArrayList<>();
    
    try (Connection connection = defaultDataSource.getConnection();
    
        CallableStatement callableStatement = connection.prepareCall(GET_MOVIMENTO_PRODUTO_REF)) {
      
    	
    	//PostgreSQL fecha o cursor se não iniciar uma transação...  
        connection.setAutoCommit(false);
        
          callableStatement.setObject(1, idMovimento);
          callableStatement.setObject(2, idProduto);

          // Set the Out Parameter type to be of type CURSOR
          callableStatement.registerOutParameter(3, Types.REF_CURSOR);
          
          callableStatement.execute(); // Execute the statement
   
         // Cast the returned parameter, (defined as type, OracleTypes.CURSOR)
         // to a JDBC result-set. 
         
        ResultSet resultSet = (ResultSet)callableStatement.getObject(3);
        
        while (resultSet.next()) {
        	
        	
        	String dateStr = resultSet.getTimestamp("DT_INCLUSAO").toLocalDateTime().toLocalDate().toString();
        	 
        	//20221016 
        	//Date date = Date.valueOf(dateStr);
        	
              
            MovimentoProduto movimentoProduto = new MovimentoProduto(
                resultSet.getInt("ID"),
                resultSet.getInt("ID_MOVIMENTO"),
                dateStr,
                resultSet.getDouble("VL_UNITARIO"),
                resultSet.getInt("ID_PRODUTO"),
                resultSet.getInt("QT_PRODUTO"),
                resultSet.getDouble("VL_TOTAL_ITEM"),        
                resultSet.getInt("QT_UTILIZADA"),
                resultSet.getInt("ID_MOVIMENTO_PRODUTO_REF"),
                resultSet.getInt("TP_MOVIMENTO"),
                resultSet.getString("DS_TP_MOVIMENTO"),
                resultSet.getString("DS_DEPOSITO"),
                resultSet.getString("DS_CLIENTE") ,
                resultSet.getInt("QT_SALDO"),
                resultSet.getString("CD_PRODUTO"),
                resultSet.getString("DS_PRODUTO")
              );

            //System.out.println(produto.toString());
            result.add(movimentoProduto);

           
          }

        connection.commit();

          return result;
      
    } catch (SQLException e) {
      throw new PersistenceException("Erro ao listar as referências! [ "+e.getMessage()+" ]", e.getCause());
    }
  }

  /*
  Insere novo item de movimento...
  
  
   p_tipo in char,
        p_id in out number,
        p_id_movimento in number,
        p_id_produto in number,
        p_vl_unitario in number,
        p_qt_produto in number, 
        p_id_movimento_produto_ref in number,
        p_mensagem out varchar2
  */
  public MovimentoProduto insert(MovimentoProduto movimentoProduto) {
      
    boolean debug=true;

    try (Connection connection = defaultDataSource.getConnection();

      CallableStatement callableStatement = connection.prepareCall(INCLUIR_MOVIMENTO_PRODUTO)) {

        
       /*
         p_id_movimento integer,
  p_id_produto integer,
  p_vl_unitario numeric,
  p_qt_produto integer,
  p_id_movimento_produto_ref integer,
  out p_id integer,
  out p_mensagem varchar
       */
    	
    	//PostgreSQL fecha o cursor se não iniciar uma transação...  
        connection.setAutoCommit(false);
        

      callableStatement.setInt(1, movimentoProduto.getIdMovimento());

      // p_id_produto
      callableStatement.setInt(2, movimentoProduto.getIdProduto());

      // p_vl_unitario
      callableStatement.setDouble (3, movimentoProduto.getVlUnitario());

      //p_qt_produto
      callableStatement.setInt(4, movimentoProduto.getQtProduto());
      
      //p_id_movimento_produto_ref
      callableStatement.setInt(5, movimentoProduto.getIdMovimentoProdutoRef());


      // id
      callableStatement.registerOutParameter(6, java.sql.Types.INTEGER);
  
      
      // ret_msg
      callableStatement.registerOutParameter(7, java.sql.Types.VARCHAR);


      if (debug) Log.info("Executando "+INCLUIR_MOVIMENTO_PRODUTO+"....");

      // executa package...
      callableStatement.executeUpdate();
      
      if (debug) 
              Log.info("ID retornado "+Integer.toString(callableStatement.getInt(6))+"....");

      movimentoProduto.setId(callableStatement.getInt(6));

      // Salva mensagem...
      movimentoProduto.setLastMsg(callableStatement.getString(7));
      
      if(movimentoProduto.getLastMsg().equals("OK")) {
      	
      	connection.commit();
      }
      else 
      
      {
    	  
    	  connection.rollback();
      }

    } catch (SQLException e) {
    	
    	
      //throw new PersistenceException(e);
      movimentoProduto.setLastMsg(e.getMessage());
    }
    

    return movimentoProduto;
  }

  /*
  public MovimentoProduto update(MovimentoProduto movimentoProduto) {

    try (Connection connection = defaultDataSource.getConnection();

        CallableStatement callableStatement = connection.prepareCall(ALTERAR_MOVIMENTO_PRODUTO)) {
 

       // id
       callableStatement.setInt(1, movimentoProduto.getId());
 
       // p_id_movimento
      callableStatement.setInt(2, movimentoProduto.getIdMovimento());

      // p_id_produto
      callableStatement.setInt(3, movimentoProduto.getIdProduto());

      // p_vl_unitario
      callableStatement.setDouble(4, movimentoProduto.getVlUnitario());

      //p_qt_produto
      callableStatement.setInt(5, movimentoProduto.getQtProduto());
      
      //p_id_movimento_produto_ref
      callableStatement.setInt(6, movimentoProduto.getIdMovimentoProdutoRef());
      
      // ret_msg
      callableStatement.registerOutParameter(7, java.sql.Types.VARCHAR);

      // executa package...
      callableStatement.executeUpdate();

        
       // Salva mensagem...
       movimentoProduto.setLastMsg(callableStatement.getString(7));

    } catch (SQLException e) {
      //throw new PersistenceException(e);
       movimentoProduto.setLastMsg(e.getMessage());
    }
    return movimentoProduto;
  }
*/
  
  /*
  
  public String delete(int id) {

    try (Connection connection = defaultDataSource.getConnection();

        CallableStatement callableStatement = connection.prepareCall(EXCLUIR_MOVIMENTO_PRODUTO)) {

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
  */
  
}
