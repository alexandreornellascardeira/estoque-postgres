package br.com.senac.db;

import br.com.senac.model.ReposicaoFornecedor; 
import io.agroal.api.AgroalDataSource;
import io.quarkus.logging.Log;


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
public class ReposicaoFornecedorRepository {
    
  @Inject
  AgroalDataSource defaultDataSource;

  private static final String REL_REPOSICAO_FORNECEDOR = "{? = call ws.relProdutoReposicaoFornecedor(?,?) }";

    
  /*
  Retorna lista de produtos de um fornecedor....
  */
  public List<ReposicaoFornecedor> getReposicaoFornecedor(int idDeposito, int idProduto) {
      
    boolean debug=true;
      
    List<ReposicaoFornecedor> result = new ArrayList<>();
    
    
    if (debug) {
        
        Log.info(
            "Executando "+REL_REPOSICAO_FORNECEDOR+": "+
                "ID Depósito: "+  Integer.toString(idProduto)+" "+
                "ID Produto: "+  Integer.toString(idProduto)+
                "....");

    }

    try (Connection connection = defaultDataSource.getConnection();
    
        
        CallableStatement callableStatement = connection.prepareCall(REL_REPOSICAO_FORNECEDOR)) {
      
          // Set the Out Parameter type to be of type CURSOR
         
          callableStatement.setObject(1, idDeposito);
          callableStatement.setObject(2, idProduto);

          callableStatement.registerOutParameter(3, Types.REF_CURSOR);
          
   
          callableStatement.execute(); // Execute the statement
   
         // Cast the returned parameter, (defined as type, OracleTypes.CURSOR)
         // to a JDBC result-set. 
         
        ResultSet resultSet = (ResultSet)callableStatement.getObject(3);
        
        while (resultSet.next()) {
              
            
                    
            ReposicaoFornecedor reposicaoFornecedor =    new ReposicaoFornecedor(

                resultSet.getInt("ID_DEPOSITO"),
                resultSet.getString("CD_DEPOSITO"),
                resultSet.getString("DS_DEPOSITO"),
                resultSet.getString("DS_CIDADE"),
                resultSet.getString("CD_ESTADO"),
                resultSet.getString("CD_FORNECEDOR"),
                resultSet.getString("DS_FORNECEDOR"),
                resultSet.getString("CD_PRODUTO"),
                resultSet.getString("DS_PRODUTO"),
                resultSet.getInt("QT_ATUAL"),
                resultSet.getInt("QT_MINIMA"),
                resultSet.getFloat("VL_PRECO_MEDIO")
            );

            //System.out.println(posicaoEstoque.toString());
            result.add(reposicaoFornecedor);
            
           
          }


          return result;
      
    } catch (SQLException e) {
      throw new PersistenceException("Erro ao listar a reposição fornecedor! [ "+e.getMessage()+" ]", e.getCause());
    }
    
  }
}
