package br.com.senac.db;

import br.com.senac.model.Movimento;
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
public class MovimentoRepository {

	@Inject
	AgroalDataSource defaultDataSource;

	private static final String FIND_BY_ID = "{? = call ws.BUSCAR_MOVIMENTO(?) }";

	private static final String GET_MOVIMENTO_BY_TP_MOVIMENTO = "{? = call ws.BUSCAR_MOVIMENTO_POR_TPMOVIMENTO(?) }";

	private static final String INCLUIR_MOVIMENTO = "{call ws.INCLUIR_MOVIMENTO(?,?,?,?,?)}";

	private static final String ALTERAR_MOVIMENTO = "{call ws.ALTERAR_MOVIMENTO(?,?,?,?,?)}";

	private static final String EXCLUIR_MOVIMENTO = "{call ws.EXCLUIR_MOVIMENTO(?,?)}";

	/*
	 * Pesquisa produto por ID...
	 */
	public Movimento findById(int id) {

		try (Connection connection = defaultDataSource.getConnection();

				CallableStatement callableStatement = connection.prepareCall(FIND_BY_ID)) {

			// PostgreSQL fecha o cursor se não iniciar uma transação...
			connection.setAutoCommit(false);

			callableStatement.setObject(1, id);

			// Set the Out Parameter type to be of type CURSOR
			callableStatement.registerOutParameter(2, Types.REF_CURSOR);

			callableStatement.execute(); // Execute the statement

			// Cast the returned parameter, (defined as type, OracleTypes.CURSOR)
			// to a JDBC result-set.

			Movimento movimento = null;

			try (ResultSet resultSet = (ResultSet) callableStatement.getObject(2)) {
				if (resultSet.next()) {

					movimento = new Movimento(resultSet.getInt("ID"), resultSet.getInt("ID_DEPOSITO"),
							resultSet.getInt("ID_CLIENTE"), resultSet.getInt("TP_MOVIMENTO"),
							resultSet.getString("DS_DEPOSITO"), resultSet.getString("DS_CLIENTE"),
							resultSet.getString("DS_TP_MOVIMENTO"), resultSet.getDouble("VL_TOTAL_MOVIMENTO"));

				}

			}

			connection.commit();
			return movimento;

		} catch (SQLException e) {
			throw new PersistenceException(e);
		}

	}

	/*
	 * Retorna lista de movimentos de um tipo....
	 */
	public List<Movimento> findByTpMovimento(int id) {

		List<Movimento> result = new ArrayList<>();

		try (Connection connection = defaultDataSource.getConnection();

				CallableStatement callableStatement = connection.prepareCall(GET_MOVIMENTO_BY_TP_MOVIMENTO)) {

			// PostgreSQL fecha o cursor se não iniciar uma transação...
			connection.setAutoCommit(false);

			callableStatement.setObject(1, id);
			// Set the Out Parameter type to be of type CURSOR
			callableStatement.registerOutParameter(2, Types.REF_CURSOR);

			callableStatement.execute(); // Execute the statement

			// Cast the returned parameter, (defined as type, OracleTypes.CURSOR)
			// to a JDBC result-set.

			ResultSet resultSet = (ResultSet) callableStatement.getObject(2);

			while (resultSet.next()) {

				Movimento movimento = new Movimento(resultSet.getInt("ID"), resultSet.getInt("ID_DEPOSITO"),
						resultSet.getInt("ID_CLIENTE"), resultSet.getInt("TP_MOVIMENTO"),
						resultSet.getString("DS_DEPOSITO"), resultSet.getString("DS_CLIENTE"),
						resultSet.getString("DS_TP_MOVIMENTO"), resultSet.getDouble("VL_TOTAL_MOVIMENTO"));

				// System.out.println(produto.toString());
				result.add(movimento);

			}

			connection.commit();

			return result;

		} catch (SQLException e) {
			throw new PersistenceException("Erro ao listar os movimentos: " + e.getMessage(), e.getCause());
		}

	}

	/*
	 * Insere novo depósito...
	 */
	public Movimento insert(Movimento movimento) {

		try (Connection connection = defaultDataSource.getConnection();

				CallableStatement callableStatement = connection.prepareCall(INCLUIR_MOVIMENTO)) {

			// id_deposito
			callableStatement.setInt(1, movimento.getIdDeposito());

			// tp_movimento
			callableStatement.setInt(2, movimento.getIdTpMovimento());

			// id_cliente
			callableStatement.setInt(3, movimento.getIdCliente());

			// id
			callableStatement.registerOutParameter(4, java.sql.Types.INTEGER);

			// ret_msg
			callableStatement.registerOutParameter(5, java.sql.Types.VARCHAR);

			// executa package...
			callableStatement.executeUpdate();

			movimento.setId(callableStatement.getInt(4));

			// Salva mensagem...
			movimento.setLastMsg(callableStatement.getString(5));

		} catch (SQLException e) {
			throw new PersistenceException(e);
		}

		return movimento;
	}

	public Movimento update(Movimento movimento) {

		try (Connection connection = defaultDataSource.getConnection();

				CallableStatement callableStatement = connection.prepareCall(ALTERAR_MOVIMENTO)) {

			// id
			callableStatement.setInt(1, movimento.getId());

			// id_deposito
			callableStatement.setInt(2, movimento.getIdDeposito());

			// tp_movimento
			callableStatement.setInt(3, movimento.getIdTpMovimento());

			// id_cliente
			callableStatement.setInt(4, movimento.getIdCliente());

			// ret_msg
			callableStatement.registerOutParameter(5, java.sql.Types.VARCHAR);

			// executa package...
			callableStatement.executeUpdate();

			// Salva mensagem...
			movimento.setLastMsg(callableStatement.getString(5));

		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		return movimento;
	}

	public String delete(int id) {

		try (Connection connection = defaultDataSource.getConnection();

				CallableStatement callableStatement = connection.prepareCall(EXCLUIR_MOVIMENTO)) {

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
