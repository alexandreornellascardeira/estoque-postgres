package br.com.senac;


import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javafaker.Faker;

import br.com.senac.model.Engine;
import br.com.senac.model.Login;
import br.com.senac.model.Recurso;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EngineResourceTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EngineResourceTest.class);

	// TODO: Implementar método de tokenização ...
	private static final String token = "0123456789";

	public static Engine engine = null;
	private static Login login  = null;
	  
	
	@Test
	@Order(1)
	void testLogin() {
		
		LOGGER.info("Teste: EngineResouce [ login ]...");
		
		 
		LOGGER.info("Efetuando login utilizando o usuário DevEstoque... ");
		

		LoginResourceTest loginResourceTest = new LoginResourceTest();
		
	    login = new Login();
		login.setLogin("DevEstoque");
		login.setPassword("12345");
		

		LoginResourceTest.login = login;
		
		loginResourceTest.testPost();
		
		login = LoginResourceTest.login;
		
		
		assertNotNull(login , "Valida se o login não é nulo.");
		assertTrue(!login.getSession().equals(""), "Valida se o login foi realizado com sucesso.");
		
		
		LOGGER.info("Dados do login: " +login.toString());
		
		
		
	}
	
	
	@Test
	@Order(2)
	void testNovaConexao() {
		
		LOGGER.info("Teste: EngineResouce [ Nova Conexão ]...");
		
		 
		//Tenta criar uma nova conexão para o recurso "Teste 1"...
		engine =new Engine();
		
	 	
		engine.setCdSession(login.getSession());
		
		//Função de testes no aplicativo...
		engine.setCmdIn("Teste 1"); 
		engine.setAcao("novaConexao");

		engine.setVersao("1.0");
		
				
		LOGGER.info("Tentando acessar o recurso: " +engine.toString());

		Engine createdEngine= given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .body(engine)
         .when()
         .post("engine")
         .then()
         .log().all()
		 .statusCode(200).extract().as(Engine.class);

		
		
		assertNotNull(createdEngine, "Valida se o novo engine não é nulo.");
		assertTrue(createdEngine.getId() > 0, "Valida se o ID do novo engine está definido.");
		
		LOGGER.info("ID do novo engine: " + createdEngine.getId() + ".");
		LOGGER.info(createdEngine.toString());
		engine = createdEngine;
	}
	
	@Test
	@Order(3)
	void testPasso1() {
		
		LOGGER.info("Teste: EngineResouce [ Passo 1 ]...");
		
		 
		//Tenta executar o passo 1 da função "Teste 1"...
		
		//Função de testes no aplicativo...
		engine.setCmdIn("Digitando alguma coisa..."); 
		engine.setAcao("analisaAcao");

	 	
				
		LOGGER.info("Executando passo 1: " +engine.toString());

		Engine createdEngine= given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .body(engine)
         .when()
         .post("engine")
         .then()
         .log().all()
		 .statusCode(200).extract().as(Engine.class);

		
		
		assertNotNull(createdEngine, "Valida se o novo engine não é nulo.");
		assertTrue(createdEngine.getId() > 0, "Valida se o ID do novo engine está definido.");
		
		LOGGER.info("ID do novo engine: " + createdEngine.getId() + ".");
		LOGGER.info(createdEngine.toString());
		engine = createdEngine;
	}

	@Test
	@Order(4)
	void testPasso2() {
		
		LOGGER.info("Teste: EngineResouce [ Passo 2 ]...");
		
		 
		//Tenta criar uma nova conexão para o recurso "Teste 1"...
		
		//Função de testes no aplicativo...
		engine.setCmdIn("Digitando outra coisa..."); 
		engine.setAcao("analisaAcao");

				
		LOGGER.info("Executando passo 2: " +engine.toString());

		Engine createdEngine= given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .body(engine)
         .when()
         .post("engine")
         .then()
         .log().all()
		 .statusCode(200).extract().as(Engine.class);

		
		
		assertNotNull(createdEngine, "Valida se o novo engine não é nulo.");
		assertTrue(createdEngine.getId() > 0, "Valida se o ID do novo engine está definido.");
		
		LOGGER.info("ID do novo engine: " + createdEngine.getId() + ".");
		LOGGER.info(createdEngine.toString());
		engine = createdEngine;
	}

	
	
}
