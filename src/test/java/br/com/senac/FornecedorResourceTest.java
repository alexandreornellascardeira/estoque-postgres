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
import br.com.senac.model.Fornecedor;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FornecedorResourceTest {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginResourceTest.class);
	
	//TODO: Implementar método de tokenização ...
	private static final String token ="0123456789"; 
	
	public static Fornecedor fornecedor=null;
	private static Cidade cidade =null;
	
	
	@Test
	@Order(2)
	void testGet() {

		LOGGER.info("Teste: FornecedorResource.get [ Encontrar fornecedor através do ID ] ...");
		
		Fornecedor fornecedorOK=given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .when()
         .get("fornecedor/"+fornecedor.getId())
         .then()
         .log().all()
		 .statusCode(200).extract().as(Fornecedor.class);

		
		
		assertNotNull(fornecedorOK, "Valida se o fornecedor foi encontrado.");
		
		LOGGER.info("Dados do fornecedor encontrado: " + fornecedorOK.toString() + ".");
		
		if(fornecedorOK!=null) fornecedor = fornecedorOK;
		
	}

	@Test
	@Order(3)
	void testFindByIdEstado() {
		

		LOGGER.info("Teste: FornecedorResource.findByIdEstado [ Encontrar fornecedores de uma UF ] ...");
		
		Fornecedor [] fornecedoresOK=given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .when()
         .get("fornecedor/findByIdEstado/"+cidade.getIdEstado())
         .then()
         .log().all()
		 .statusCode(200).extract().as(Fornecedor[].class);

		
		
		assertNotNull(fornecedoresOK, "Valida se foi retornada uma lista de fornecedores.");
		
		LOGGER.info("Fornecedores encontrados: " + fornecedoresOK.length + ".");
		
		for(int i=0; i<=fornecedoresOK.length-1; i++){
			
			Fornecedor fornecedor =fornecedoresOK[i];
			
			LOGGER.info(fornecedor.getDs()+" - " +fornecedor.getDsCidade()+ " - "+ fornecedor.getCdEstado() );
			
		}
		
		 
		
	}

	@Test
	@Order(1)
	void testInsert() {
		

		LOGGER.info("Teste: FornecedorResource.insert...");
		
	 	
		//Define ID de cidade aleatoriamente...
		Random random=new Random();
		
		int idCidade =  random.nextInt(5570);
		
		StringBuffer fakeCNPJ=new StringBuffer();
		
		for (int i=0; i<14; i++) {
			
			fakeCNPJ.append(random.nextInt(9));
			
		}
		
		
        Faker faker = Faker.instance(new Locale("pt-BR"));
        
        //Retorna dados da cidade...
		cidade = given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .when()
         .get("cidade/" + idCidade)
         .then()
         .log().all()
		 .statusCode(200).extract().as(Cidade.class);
        
	    fornecedor=new Fornecedor(
				 0,
				 fakeCNPJ.toString(),
				 faker.company().name(),
				 idCidade,
				 cidade.getDs(),
		         cidade.getCdEstado());
				
		LOGGER.info("Dados do novo fornecedor: " +fornecedor.toString());

		Fornecedor fornecedorCreated = given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .body(fornecedor)
         .when()
         .post("fornecedor")
         .then()
         .log().all()
		 .statusCode(200).extract().as(Fornecedor.class);

		
		
		assertNotNull(fornecedorCreated, "Valida se o novo fornecedor não é nulo.");
		assertTrue(fornecedorCreated.getId() > 0, "Valida se o ID do novo fornecedor está definido.");
		
		LOGGER.info("ID do novo fornecedor: " + fornecedorCreated.getId() + ".");
		
		fornecedor = fornecedorCreated;
		
	}

	@Test
	@Order(4)
	void testUpdate() {
		
		LOGGER.info("Teste: FornecedorResource.put [ Alterar dados do fornecedor ] ...");
		
		LOGGER.info("Fornecedor antes da alteração: " +fornecedor.toString());
		
		fornecedor.setDs(Faker.instance(new Locale("pt-BR")).company().name());
		
		 

		Fornecedor fornecedorOK=given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .body(fornecedor)
		 .when()
         .put("fornecedor")
         .then()
         .log().all()
		 .statusCode(200).extract().as(Fornecedor.class);

		
		
		assertNotNull(fornecedorOK, "Valida se o fornecedor foi alterado.");
		
		LOGGER.info("Dados do fornecedor alterado: " + fornecedorOK.toString() + ".");
		
		if(fornecedorOK!=null)  fornecedor = fornecedorOK;
	}

	@Test
	@Order(4)
	void testDelete() {
		

		LOGGER.info("Teste: FornecedorResource.delete [ Excluir fornecedor ] ...");

		LOGGER.info("Fornecedor antes da exclusão: " +fornecedor.toString());
		

		String response=given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .body(fornecedor)
		 .when()
         .delete("fornecedor/" + fornecedor.getId())
         .then()
         .log().all()
		 .statusCode(200).extract().asString();


		LOGGER.info("Resposta da exclusão: " + response + ".");
		
		//Valida se o fornecedor foi excluído...
		given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .when()
         .get("fornecedor/"+fornecedor.getId())
         .then()
         .log().all()
         .assertThat().statusCode(204);


		 
		
		
	}

}
