package br.com.senac.db;


import br.com.senac.model.ExtratoMovimento; 
import io.agroal.api.AgroalDataSource;
import io.quarkus.logging.Log;


import java.sql.Types;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ExtratoMovimentoRepository {
    
    
  @Inject
  AgroalDataSource defaultDataSource;

  private static final String REL_EXTRATO_MOVIMENTO = "{? = call ws.relExtratoMovimentacaoEstoque(?,?,?,?) }";

    
  /*
  Retorna lista de produtos de um fornecedor....
  */
  public List<ExtratoMovimento> getExtratoMovimento(String ini, String fim, int idDeposito, int idProduto) {
      
    boolean debug=true;
      
    List<ExtratoMovimento> result = new ArrayList<>();
    
    
    if (debug) {
        
        Log.info(
            "Executando "+REL_EXTRATO_MOVIMENTO+": "+
                "ID Depósito: "+  Integer.toString(idProduto)+" "+
                "ID Produto: "+  Integer.toString(idProduto)+
                "....");

    }

    try (Connection connection = defaultDataSource.getConnection();
    
        
        CallableStatement callableStatement = connection.prepareCall(REL_EXTRATO_MOVIMENTO)) {
          
    	//PostgreSQL fecha o cursor se não iniciar uma transação...  
        connection.setAutoCommit(false);

          callableStatement.setObject(1, ini);
          callableStatement.setObject(2, fim);
          callableStatement.setObject(3, idDeposito);
          callableStatement.setObject(4, idProduto);

           // Set the Out Parameter type to be of type CURSOR
          callableStatement.registerOutParameter(5, Types.REF_CURSOR);
          
   
          callableStatement.execute(); // Execute the statement
   
         // Cast the returned parameter, (defined as type, OracleTypes.CURSOR)
         // to a JDBC result-set. 
         
        ResultSet resultSet = (ResultSet)callableStatement.getObject(5);
        
        while (resultSet.next()) {
             
            //Log.info("getExtratoMovimento: Ini: " + ini + " Fim: "+fim);
           
                    
            ExtratoMovimento extratoMovimento = new ExtratoMovimento(
                ini,
                fim,
                resultSet.getInt("ID_DEPOSITO"),
                resultSet.getInt("ID_PRODUTO"), 
                resultSet.getString("DS_DEPOSITO"),
                new SimpleDateFormat("dd/MM/yyyy").format(resultSet.getDate("DT_INCLUSAO")),
                resultSet.getInt("ID_MOVIMENTO"),
                resultSet.getString("CD_PRODUTO"),
                resultSet.getString("DS_PRODUTO"),
                resultSet.getString("DS_CLIENTE"), 
                resultSet.getInt("ID_CLIENTE"), 
                resultSet.getInt("QT_PRODUTO"),
                resultSet.getFloat("VL_UNITARIO"),
                resultSet.getFloat("VL_TOTAL_ITEM"),
                resultSet.getInt("QT_UTILIZADA"),
                resultSet.getInt("QT_SALDO"),
                resultSet.getString("DS_TP_MOVIMENTO")
            );
            
            
            //System.out.println(posicaoEstoque.toString());
            result.add(extratoMovimento);
            
           
          }

         connection.commit();

          return result;
      
    } catch (SQLException e) {
      throw new PersistenceException("Erro ao listar o extrato do movimento! [ "+e.getMessage()+" ]", e.getCause());
    }
    
  }
    
}
