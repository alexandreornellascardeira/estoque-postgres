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

import br.com.senac.model.Login;
import br.com.senac.model.Recurso;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RecursoResourceTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RecursoResourceTest.class);

	// TODO: Implementar método de tokenização ...
	private static final String token = "0123456789";

	public static Recurso recurso = null;
	 
	public static Login login = null;
	
	
	@Test
	@Order(1)
	void testPost() {
		
		LOGGER.info("Teste: RecursoResource.insert...");
		
	 	
		 
		//Prepara os dados do novo recurso....
		
        Faker faker = Faker.instance(new Locale("pt-BR"));
        
        //Simula criação de recurso que poderia ser utilizado em uma livraria...
        
        String genre=faker.book().genre();
        
	    recurso=new Recurso(
				 0,
				 genre,
				 "cellphone-play",
				 faker.book().author(),
				 faker.book().title(),
		         "Stored_Analisa_Acao_"+genre.replace(" ","_"));
				
		LOGGER.info("Dados do novo recurso: " +recurso.toString());

		Recurso recursoCreated = given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .body(recurso)
         .when()
         .post("recurso")
         .then()
         .log().all()
		 .statusCode(200).extract().as(Recurso.class);

		
		
		assertNotNull(recursoCreated, "Valida se o novo recurso não é nulo.");
		assertTrue(recursoCreated.getId() > 0, "Valida se o ID do novo recurso está definido.");
		
		LOGGER.info("ID do novo recurso: " + recursoCreated.getId() + ".");
		
		recurso = recursoCreated;
	}

	@Test
	@Order(2)
	void testGet() {
		

		LOGGER.info("Teste: RecursoResource.get [ Encontrar recurso através do ID ] ...");
		
		Recurso recursoOK=given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .when()
         .get("recurso/"+recurso.getId())
         .then()
         .log().all()
		 .statusCode(200).extract().as(Recurso.class);

		
		
		assertNotNull(recursoOK, "Valida se o recurso foi encontrado.");
		
		LOGGER.info("Dados do recurso encontrado: " + recursoOK.toString() + ".");
		
		if(recursoOK!=null) recurso = recursoOK;
	}

	@Test
	@Order(3)
	void atribuirRecurso() {
		
		LOGGER.info("Teste: RecursoResource.atribuir...");
		
		
		LoginResourceTest loginResourceTest = new LoginResourceTest();
		loginResourceTest.testNovo();
		
		login = LoginResourceTest.login;
		
		
		 recurso.setIdLogin(login.getId());
		 
		LOGGER.info("Dados do login: " +login.toString());

		String msg = given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .body(recurso)
         .when()
         .post("recurso/atribuir")
         .then()
         .log().all()
		 .statusCode(200).extract().asString();

		LOGGER.info("Mensagem: " + msg + ".");
		
		assertNotNull(msg, "Valida se a mensagem de processamento não é nula.");
		
		
		
		assertTrue(msg.equals("OK") , "Valida se a mensagem de processamento é igual a OK.");
		
		 
		
		
	}
	


	@Test
	@Order(4)
	void testFindByIdLogin() {

		LOGGER.info("Teste: RecursoResource.FindByIdLogin [ Encontrar recursos através do ID de Login ] ...");

		Recurso[] recursosOK = given().contentType(ContentType.JSON).header("token", token).when()
				.get("recurso/findByIdLogin/" + login.getId()).then().log().all().statusCode(200).extract()
				.as(Recurso[].class);

		assertNotNull(recursosOK, "Valida se o recurso foi encontrado.");

		LOGGER.info("Qtd. Recursos encontrados: " + recursosOK.length + " para o login " + login.getLogin() + ".");

	}

	
	@Test
	@Order(5)
	void testPut() {
		
		LOGGER.info("Teste: RecursoResource.put [ Alterar dados do recurso ] ...");
		
		LOGGER.info("Recurso antes da alteração: " +recurso.toString());
		
		Faker faker = Faker.instance(new Locale("pt-BR"));
	    
		recurso.setMsgSubMenu(faker.book().title());
		
		 
		Recurso recursoOK=given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .body(recurso)
		 .when()
         .put("recurso")
         .then()
         .log().all()
		 .statusCode(200).extract().as(Recurso.class);

		
		
		assertNotNull(recursoOK, "Valida se o recurso foi alterado.");
		
		LOGGER.info("Dados do recurso alterado: " + recursoOK.toString() + ".");
		
		if(recursoOK!=null)  recurso = recursoOK;
	}
	
	@Test
	@Order(6)
	void desatribuirRecurso() {
		
		LOGGER.info("Teste: RecursoResource.desatribuir...");
		
		
		 
		LOGGER.info("Recurso: " +recurso.toString());

		String msg = given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .body(recurso)
         .when()
         .post("recurso/desatribuir")
         .then()
         .log().all()
		 .statusCode(200).extract().asString();

		LOGGER.info("Mensagem: " + msg + ".");
		
		assertNotNull(msg, "Valida se a mensagem de processamento não é nula.");
		
		
		
		assertTrue(msg.equals("OK") , "Valida se a mensagem de processamento é igual a OK.");
		
		 
		
		
	}
	

	@Test
	@Order(7)
	void testDelete() {
		
		LOGGER.info("Teste: RecursoResource.delete [ Excluir recurso ] ...");

		LOGGER.info("Recurso antes da exclusão: " +recurso.toString());
		

		String response=given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .body(recurso)
		 .when()
         .delete("recurso/" + recurso.getId())
         .then()
         .log().all()
		 .statusCode(200).extract().asString();



		LOGGER.info("Resposta da exclusão: " + response + ".");

		//Valida se o login foi excluído...
		given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .when()
         .get("recurso/"+recurso.getId())
         .then()
         .log().all()
         .assertThat().statusCode(204);

		
		
	}

}
