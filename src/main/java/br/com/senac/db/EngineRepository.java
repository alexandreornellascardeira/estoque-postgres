package br.com.senac.db;

import br.com.senac.model.Cidade;
import br.com.senac.model.Engine;

import io.agroal.api.AgroalDataSource;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class EngineRepository {

	@Inject
	AgroalDataSource defaultDataSource;

	private static final String EXEC_ENGINE = "{ call ct.\"execEngineCT\"(?,?,?,?,?,?,?,?,?) }";

	/*
	 * Executa o engine do App Mobile..
	 */
	public Engine exec(Engine engine) {

		try {
			Connection connection = defaultDataSource.getConnection();

			// PostgreSQL fecha o cursor se não iniciar uma transação...
			connection.setAutoCommit(false);
		
			CallableStatement callableStatement = connection.prepareCall(EXEC_ENGINE);

			callableStatement.setString(1, engine.getCdSession());
			callableStatement.setInt(2, engine.getId());
			callableStatement.setString(3, engine.getAcao());
			callableStatement.setString(4, engine.getCmdIn());
			callableStatement.setString(5, engine.getDtApp());
			callableStatement.setString(6, engine.getVersao());
			callableStatement.setString(7, engine.getLat());
			callableStatement.setString(8, engine.getLon());
			callableStatement.setString(9, engine.getCdGcm());

			

			try (ResultSet resultSet = (ResultSet) callableStatement.executeQuery()) {
				if (resultSet.next()) {

					engine.setId(resultSet.getInt("ID_CONEXAO"));
					engine.setCdOpcao(resultSet.getString("CD_OPCAO"));
					engine.setCmdOut(resultSet.getString("CMD_OUT"));
					engine.setCmdInf(resultSet.getString("CMD_INF"));

				}

			}

			connection.commit();

			return engine;

		} catch (SQLException e) {
			throw new PersistenceException(e);
		}

	}

}
