package br.com.senac;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Locale;
import java.util.Random;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javafaker.Faker;

import br.com.senac.model.Cidade;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CidadeResourceTest {
	
private static final Logger LOGGER = LoggerFactory.getLogger(LoginResourceTest.class);
	
	//TODO: Implementar método de tokenização ...
	private static final String token ="0123456789"; 
	
	private static Cidade novaCidade=null;
	

	@Test
	@Order(3)
	void testFindByIdEstado() {
		

		LOGGER.info("Teste: CidadeResource.FindByIdEstado [ Encontrar cidadeS através do ID da UF ] ...");
		
		 Cidade[] cidadesOK=given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .when()
         .get("cidade/findByIdEstado/"+novaCidade.getIdEstado())
         .then()
         .log().all()
		 .statusCode(200).extract().as(Cidade[].class);

		
		
		assertNotNull(cidadesOK, "Valida se a cidade foi encontrada.");
		
		LOGGER.info("Qtd. Cidades encontradas: " + cidadesOK.length + " na UF "+novaCidade.getCdEstado()+".");
		
		 
		
		
	}

	@Test
	@Order(2)
	void testGet() {
		

		LOGGER.info("Teste: CidadeResource.get [ Encontrar cidade através do ID ] ...");
		
		Cidade cidadeOK=given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .when()
         .get("cidade/"+novaCidade.getId())
         .then()
         .log().all()
		 .statusCode(200).extract().as(Cidade.class);

		
		
		assertNotNull(cidadeOK, "Valida se a cidade foi encontrada.");
		
		LOGGER.info("Dados da cidade encontrada: " + cidadeOK.toString() + ".");
		
		if(cidadeOK!=null) novaCidade = cidadeOK;
		
		
	}

	@Test
	@Order(1)
	void testPost() {
		

		LOGGER.info("Teste: CidadeResource.post ( Inserir nova Cidade...");
		
		//Define ID de cidade aleatoriamente...
		Random random=new Random();
		
		int idCidade =  random.nextInt(5570);
		
		  //Retorna dados da cidade...
		Cidade cidade = given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .when()
         .get("cidade/" + idCidade)
         .then()
         .log().all()
		 .statusCode(200).extract().as(Cidade.class);
				
         Faker faker = Faker.instance(Locale.US);
		        
		        
		//Cria nova cidade...
		
	    novaCidade=new Cidade(
				 0,
				 Faker.instance().address().city(),
				 cidade.getIdEstado(),
				 cidade.getCdEstado());
				
	     
	    
		LOGGER.info("Dados da nova cidade: " +novaCidade.toString());

		Cidade cidadeCreated = given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .body(novaCidade)
         .when()
         .post("cidade")
         .then()
         .log().all()
		 .statusCode(200).extract().as(Cidade.class);

		
		
		assertNotNull(cidadeCreated, "Valida se a nova cidade não é nula.");
		assertTrue(cidadeCreated.getId() > 0, "Valida se o ID da nova cidade está definido.");
		
		LOGGER.info("ID da nova cidade: " + cidadeCreated.getId() + ".");
		
		novaCidade = cidadeCreated;
		
	}

	@Test
	@Order(4)
	void testPut() {
		
		LOGGER.info("Teste: CidadeResource.put [ Alterar dados da Cidade ] ...");
		
		LOGGER.info("Cidade antes da alteração: " +novaCidade.toString());
		
		novaCidade.setDs(Faker.instance().address().cityName());
		
	

		Cidade cidadeOK=given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .body(novaCidade)
		 .when()
         .put("cidade")
         .then()
         .log().all()
		 .statusCode(200).extract().as(Cidade.class);

		
		
		assertNotNull(cidadeOK, "Valida se a cidade foi alterada.");
		
		LOGGER.info("Dados da cidade alterada: " + cidadeOK.toString() + ".");
		
		if(cidadeOK!=null)  novaCidade = cidadeOK;
	}

	@Test
	@Order(5)
	void testDelete() {
		
		LOGGER.info("Teste: CidadeResource.delete [ Excluir cidade ] ...");
		 

		LOGGER.info("Cidade antes da exclusão: " +novaCidade.toString());
		
		

		String response=given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .when()
         .delete("cidade/" + novaCidade.getId())
         .then()
         .log().all()
		 .statusCode(200).extract().asString();
		
		

		LOGGER.info("Resposta da exclusão: " + response + ".");
		
		//Verifica se a exclusão foi realizada...
		
		 given()
				 .contentType(ContentType.JSON)
				 .header("token", token)
				 .when()
		         .get("cidade/"+novaCidade.getId())
		         .then()
		         .log().all()
		         .assertThat().statusCode(204);

		 
		
	}

}
