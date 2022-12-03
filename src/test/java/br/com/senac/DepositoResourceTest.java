package br.com.senac;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import br.com.senac.model.Deposito; 
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DepositoResourceTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginResourceTest.class);

	// TODO: Implementar método de tokenização ...
	private static final String token = "0123456789";

	public static Deposito deposito = null;
	
	private static Cidade cidade =null;

	@Test
	@Order(3)
	void testFindByIdEstado() {

		LOGGER.info("Teste: DepositoResource.FindByIdEstado [ Encontrar depositos através do ID da UF ] ...");

		Deposito[] depositosOK = given().contentType(ContentType.JSON).header("token", token).when()
				.get("deposito/findByIdEstado/" + cidade.getIdEstado()).then().log().all().statusCode(200).extract()
				.as(Deposito[].class);

		assertNotNull(depositosOK, "Valida se a cidade foi encontrada.");

		LOGGER.info("Qtd. Cidades encontradas: " + depositosOK.length + " na UF " + cidade.getCdEstado() + ".");

	}

	@Test
	@Order(2)
	void testGet() {
		

		LOGGER.info("Teste: DepositoResource.get [ Encontrar deposito através do ID ] ...");
		
		Deposito depositoOK=given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .when()
         .get("deposito/"+deposito.getId())
         .then()
         .log().all()
		 .statusCode(200).extract().as(Deposito.class);

		
		
		assertNotNull(depositoOK, "Valida se o deposito foi encontrado.");
		
		LOGGER.info("Dados do deposito encontrado: " + depositoOK.toString() + ".");
		
		if(depositoOK!=null) deposito = depositoOK;
	}

	@Test
	@Order(1)
	void testPost() {
		LOGGER.info("Teste: DepositoResource.insert...");
		
	 	
		//Define ID de cidade aleatoriamente...
		Random random=new Random();
		
		int idCidade =  random.nextInt(5570);
		
		
		//Cria um CNPJ falso...
		StringBuffer fakeCNPJ=new StringBuffer();
		
		for (int i=0; i<14; i++) {
			
			fakeCNPJ.append(random.nextInt(9));
			
		}
		
        
        //Retorna dados da cidade...
		cidade = given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .when()
         .get("cidade/" + idCidade)
         .then()
         .log().all()
		 .statusCode(200).extract().as(Cidade.class);
        
		
		//Prepara os dados do novo deposito....
		
        Faker faker = Faker.instance(new Locale("pt-BR"));
        
	    deposito=new Deposito(
				 0,
				 fakeCNPJ.toString(),
				 faker.company().name(),
				 idCidade,
				 cidade.getDs(),
		         cidade.getCdEstado());
				
		LOGGER.info("Dados do novo deposito: " +deposito.toString());

		Deposito depositoCreated = given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .body(deposito)
         .when()
         .post("deposito")
         .then()
         .log().all()
		 .statusCode(200).extract().as(Deposito.class);

		
		
		assertNotNull(depositoCreated, "Valida se o novo deposito não é nulo.");
		assertTrue(depositoCreated.getId() > 0, "Valida se o ID do novo deposito está definido.");
		
		LOGGER.info("ID do novo deposito: " + depositoCreated.getId() + ".");
		
		deposito = depositoCreated;
	}

	@Test
	@Order(4)
	void testPut() {
		
		LOGGER.info("Teste: DepositoResource.put [ Alterar dados do deposito ] ...");
		
		LOGGER.info("Deposito antes da alteração: " +deposito.toString());
		
		deposito.setDs(Faker.instance(new Locale("pt-BR")).company().name());
		
		 

		Deposito depositoOK=given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .body(deposito)
		 .when()
         .put("deposito")
         .then()
         .log().all()
		 .statusCode(200).extract().as(Deposito.class);

		
		
		assertNotNull(depositoOK, "Valida se o deposito foi alterado.");
		
		LOGGER.info("Dados do deposito alterado: " + depositoOK.toString() + ".");
		
		if(depositoOK!=null)  deposito = depositoOK;
	}

	@Test
	@Order(5)
	void testDelete() {
		
		LOGGER.info("Teste: DepositoResource.delete [ Excluir deposito ] ...");

		LOGGER.info("Deposito antes da exclusão: " +deposito.toString());
		

		String response=given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .body(deposito)
		 .when()
         .delete("deposito/" + deposito.getId())
         .then()
         .log().all()
		 .statusCode(200).extract().asString();



		LOGGER.info("Resposta da exclusão: " + response + ".");

		//Valida se o depósito foi excluído...
		given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .when()
         .get("deposito/"+deposito.getId())
         .then()
         .log().all()
         .assertThat().statusCode(204);

		
		
	}

}
