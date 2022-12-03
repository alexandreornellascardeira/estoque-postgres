package br.com.senac.db;

import br.com.senac.model.Produto;
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
public class ProdutoRepository {

	@Inject
	AgroalDataSource defaultDataSource;

	private static final String FIND_BY_ID = "{? = call ws.BUSCAR_PRODUTO(?) }";

	private static final String FIND_BY_ID_FORNECEDOR = "{? = call ws.BUSCAR_PRODUTO_BY_ID_FORNECEDOR(?) }";

	private static final String INCLUIR_PRODUTO = "{call ws.INCLUIR_PRODUTO(?,?,?,?,?,?,?)}";

	private static final String ALTERAR_PRODUTO = "{call ws.ALTERAR_PRODUTO(?,?,?,?,?,?,?)}";

	private static final String EXCLUIR_PRODUTO = "{call ws.EXCLUIR_PRODUTO(?,?)}";

	/*
	 * Pesquisa produto por ID...
	 */
	public Produto findById(int id) {

		try (Connection connection = defaultDataSource.getConnection();

				CallableStatement callableStatement = connection.prepareCall(FIND_BY_ID)) {

			// PostgreSQL fecha o cursor se não iniciar uma transação...
			connection.setAutoCommit(false);

			// Set the Out Parameter type to be of type CURSOR

			callableStatement.setInt(1, id);
			callableStatement.registerOutParameter(2, Types.REF_CURSOR);

			callableStatement.execute(); // Execute the statement

			// Cast the returned parameter, (defined as type, OracleTypes.CURSOR)
			// to a JDBC result-set.

			try (ResultSet resultSet = (ResultSet) callableStatement.getObject(2)) {
				if (resultSet.next()) {

					return new Produto(resultSet.getInt("ID"), resultSet.getString("CD_PRODUTO"),
							resultSet.getString("DS_PRODUTO"), resultSet.getBoolean("TP_INATIVO"),
							resultSet.getDate("DT_INATIVO"), resultSet.getInt("QT_MINIMA"),
							resultSet.getInt("ID_FORNECEDOR"), resultSet.getString("DS_FORNECEDOR"));

				}

				connection.commit();

			}

		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		return null;
	}

	/*
	 * Retorna lista de produtos de um fornecedor....
	 */
	public List<Produto> findByIdFornecedor(int id) {

		List<Produto> result = new ArrayList<>();

		try (Connection connection = defaultDataSource.getConnection();

				CallableStatement callableStatement = connection.prepareCall(FIND_BY_ID_FORNECEDOR)) {

			// PostgreSQL fecha o cursor se não iniciar uma transação...
			connection.setAutoCommit(false);

			// Set the Out Parameter type to be of type CURSOR

			callableStatement.setInt(1, id);
			callableStatement.registerOutParameter(2, Types.REF_CURSOR);

			callableStatement.execute(); // Execute the statement

			// Cast the returned parameter, (defined as type, OracleTypes.CURSOR)
			// to a JDBC result-set.

			ResultSet resultSet = (ResultSet) callableStatement.getObject(2);

			while (resultSet.next()) {

				Produto produto = new Produto(resultSet.getInt("ID"), resultSet.getString("CD_PRODUTO"),
						resultSet.getString("DS_PRODUTO"), resultSet.getBoolean("TP_INATIVO"),
						resultSet.getDate("DT_INATIVO"), resultSet.getInt("QT_MINIMA"),
						resultSet.getInt("ID_FORNECEDOR"), resultSet.getString("DS_FORNECEDOR"));

				// System.out.println(produto.toString());
				result.add(produto);

			}

			connection.commit();
			return result;

		} catch (SQLException e) {
			throw new PersistenceException("Erro ao listar produtos por fornecedor!" + e.getMessage(), e.getCause());
		}

	}

	/*
	 * Insere novo produto...
	 */
	public Produto insert(Produto produto) {

		try (Connection connection = defaultDataSource.getConnection();

				CallableStatement callableStatement = connection.prepareCall(INCLUIR_PRODUTO)) {

			// cd_produto
			callableStatement.setString(1, produto.getCd());

			// ds_produto
			callableStatement.setString(2, produto.getDs());

			// qt_minima
			callableStatement.setInt(3, produto.getQtMinima());

			// tp_inativo
			callableStatement.setBoolean(4, produto.isInativo());

			// id_fornecedor
			callableStatement.setInt(5, produto.getIdFornecedor());

			// id
			callableStatement.registerOutParameter(6, java.sql.Types.INTEGER);

			// ret_msg
			callableStatement.registerOutParameter(7, java.sql.Types.VARCHAR);

			// executa package...
			callableStatement.executeUpdate();

			produto.setId(callableStatement.getInt(6));

			// Salva mensagem...
			produto.setLastMsg(callableStatement.getString(7));

		} catch (SQLException e) {
			throw new PersistenceException(e);
		}

		return produto;
	}

	public Produto update(Produto produto) {

		try (Connection connection = defaultDataSource.getConnection();

				CallableStatement callableStatement = connection.prepareCall(ALTERAR_PRODUTO)) {

			// id
			callableStatement.setInt(1, produto.getId());

			// cd_produto
			callableStatement.setString(2, produto.getCd());

			// ds_produto
			callableStatement.setString(3, produto.getDs());

			// qt_minima
			callableStatement.setInt(4, produto.getQtMinima());

			// tp_inativo
			callableStatement.setBoolean(5, produto.isInativo());

			// id_fornecedor
			callableStatement.setInt(6, produto.getIdFornecedor());

			// ret_msg
			callableStatement.registerOutParameter(7, java.sql.Types.VARCHAR);

			// executa package...
			callableStatement.executeUpdate();

			// Salva mensagem...
			produto.setLastMsg(callableStatement.getString(7));

		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		return produto;
	}

	public String delete(int id) {

		try (Connection connection = defaultDataSource.getConnection();

				CallableStatement callableStatement = connection.prepareCall(EXCLUIR_PRODUTO)) {

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
