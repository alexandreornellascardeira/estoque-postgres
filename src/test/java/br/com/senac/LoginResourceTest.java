package br.com.senac;

import io.quarkus.test.junit.QuarkusTest;


import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.senac.model.Login;

import io.restassured.http.ContentType;

import com.github.javafaker.Faker;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoginResourceTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginResourceTest.class);
	
	//TODO: Implementar método de tokenização ...
	private static final String token ="0123456789"; 
	
	public static Login login=null;
	
	//String para identificar sessão do usuário...
	private static String session =null;
	
	private static String senha = null;
	
	
	@Test
	@Order(1)    
	void testNovo() {
		
		LOGGER.info("Teste: LoginResource.novo...");
		
		String email = Faker.instance().internet().emailAddress();
		
		login=new Login(
				 0,
				 email,
				 Faker.instance().internet().password(10, 20),
				 Faker.instance().name().fullName(),
		         null,
		         email,
		         //20221103 Problemas com exigência de autenticação no AWS...
		         //Faker.instance().avatar().image()
		         "https://robohash.org/"+email+"?set=set5"
		         );
				
	    senha = login.getPassword();
	    
		LOGGER.info("Dados do novo login: " +login.toString());

		Login loginCreated = given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .body(login)
         .when()
         .post("login/novo")
         .then()
         .log().all()
		 .statusCode(200).extract().as(Login.class);

		
		
		assertNotNull(loginCreated, "Valida se o novo login não é nulo.");
		assertTrue(loginCreated.getId() > 0, "Valida se o ID do novo login está definido.");
		
		LOGGER.info("ID do novo login: " + loginCreated.getId() + ".");
		
		login = loginCreated;
	}
	
	@Test
	@Order(2)    
	void testPost() {
		
		LOGGER.info("Teste: LoginResource.post [ Efetuar login ] ...");
		
		LOGGER.info("Dados do login: " +login.toString());

		Login loginOK=given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .body(login)
         .when()
         .post("login")
         .then()
         .log().all()
		 .statusCode(200).extract().as(Login.class);

		
		
		assertNotNull(loginOK, "Valida se o login foi efetuado com sucesso.");
		
		LOGGER.info("Mensagem: " + loginOK.getLastMsg() + ".");
		
		assertTrue(loginOK.getLastMsg().equals("OK"), "Valida OK.");
		
		//Salva sessão do usuário...
		session = loginOK.getSession();
		
		
		
		login = loginOK;
		
	}

	@Test
	@Order(3)    
	void testFindByCdSession() {
		
		
		LOGGER.info("Teste: LoginResource.findByCdSession [ Encontrar login através de uma sessão ] ...");
		
		LOGGER.info("Sessão: " +session);

		Login loginOK=given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .when()
         .get("login/loadSession/"+session)
         .then()
         .log().all()
		 .statusCode(200).extract().as(Login.class);

		
		
		assertNotNull(loginOK, "Valida se o login foi encontrado.");
		
		LOGGER.info("Dados do login encontrado: " + loginOK.toString() + ".");
		
		if(loginOK!=null) login = loginOK;
		
	}
	
	@Test
	@Order(3)    
	void testFindById() {
		
		
		LOGGER.info("Teste: LoginResource.findById [ Encontrar login através do ID ] ...");
		
		LOGGER.info("ID: " +login.getId());

		Login loginOK=given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .when()
         .get("login/"+login.getId())
         .then()
         .log().all()
		 .statusCode(200).extract().as(Login.class);

		
		
		assertNotNull(loginOK, "Valida se o login foi encontrado.");
		
		LOGGER.info("Dados do login encontrado: " + loginOK.toString() + ".");
		
		if(loginOK!=null) login = loginOK;
		
	}


	@Test
	@Order(5)    
	void testPut() {
		
		
		LOGGER.info("Teste: LoginResource.put [ Alterar dados do login ] ...");
	
		LOGGER.info("Login antes da alteração: " +login.toString());
		
		
		login.setPassword(Faker.instance().internet().password(10, 20));
		login.setLastPassword(senha);
		 

		Login loginOK=given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .body(login)
		 .when()
         .put("login")
         .then()
         .log().all()
		 .statusCode(200).extract().as(Login.class);

		
		
		assertNotNull(loginOK, "Valida se o login foi alterado.");
		
		LOGGER.info("Dados do login alterado: " + loginOK.toString() + ".");
		
		if(loginOK!=null)  login = loginOK;
		
	}
	
	@Test
	@Order(6)    
	void testDelete() {
		
		
		LOGGER.info("Teste: LoginResource.delete [ Excluir login ] ...");
		 

		LOGGER.info("Login antes da exclusão: " +login.toString());
		
		

		String response=given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .body(login)
		 .when()
         .delete("login/" + login.getId())
         .then()
         .log().all()
		 .statusCode(200).extract().asString();

		LOGGER.info("Resposta da exclusão: " + response + ".");
		
		//Valida se o login foi excluído...
		given()
				 .contentType(ContentType.JSON)
				 .header("token", token)
				 .when()
		         .get("login/"+login.getId())
		         .then()
		         .log().all()
		         .assertThat().statusCode(204);


		 
		
		
		
	}
	
	

}
