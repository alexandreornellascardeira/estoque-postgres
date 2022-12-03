package br.com.senac;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.senac.model.Cliente;
import br.com.senac.model.Deposito;
import br.com.senac.model.Fornecedor;
import br.com.senac.model.Movimento;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MovimentoResourceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginResourceTest.class);

	// TODO: Implementar método de tokenização ...
	private static final String token = "0123456789";

	public static Movimento movimento = null;
	 

	@Test
	@Order(3)
	void testFindByTpMovimento() {

		LOGGER.info("Teste: MovimentoResource.FindByTpMovimento [ Encontrar movimentos através do tipo de movimento ] ...");
		
		int tpMovimento = 0;
		
		Movimento[] movimentosOK = given().contentType(ContentType.JSON).header("token", token).when()
				.get("movimento/findByTpMovimento/" + tpMovimento).then().log().all().statusCode(200).extract()
				.as(Movimento[].class);

		assertNotNull(movimentosOK, "Valida se o movimento foi encontrado.");

		LOGGER.info("Qtd. de movimentos encontrados: " + movimentosOK.length + " do tipo " + tpMovimento + ".");

	}

	@Test
	@Order(2)
	void testGet() {
		
		LOGGER.info("Teste: MovimentoResource.get [ Encontrar movimento através do ID ] ...");
		
		Movimento movimentoOK=given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .when()
         .get("movimento/"+movimento.getId())
         .then()
         .log().all()
		 .statusCode(200).extract().as(Movimento.class);

		
		
		assertNotNull(movimentoOK, "Valida se o movimento foi encontrado.");
		
		LOGGER.info("Dados do movimento encontrado: " + movimentoOK.toString() + ".");
		
		if(movimentoOK!=null) movimento = movimentoOK;
	}

	/**
	 * Testa inserção de movimento de entrada e em seguida um movimento de saída.
	 * Para gerar o movimento de entrada, cadastra um fornecedor e um depósito antes.
	 */
	@Test
	@Order(1)
	void testPost() {
		
		LOGGER.info("Teste: MovimentoResource.insert...");
		
	 	//Cria um novo fornecedor...
		
		FornecedorResourceTest fornecedorResourceTest = new FornecedorResourceTest();
		fornecedorResourceTest.testInsert();
		
		Fornecedor fornecedor = FornecedorResourceTest.fornecedor;
		
		
		//Cria um novo depósito...
		DepositoResourceTest depositoResourceTest = new DepositoResourceTest();
		depositoResourceTest.testPost();
		
		Deposito deposito = DepositoResourceTest.deposito;
		
		
		//Pesquisa cadastro de cliente para o respectivo fornecedor....
		ClienteResourceTest clienteResourceTest = new ClienteResourceTest();
		ClienteResourceTest.cd = fornecedor.getCd();
				
		clienteResourceTest.testFindByCd();
		
		Cliente cliente = ClienteResourceTest.cliente;
		
		//Cria um novo movimento...
		movimento = new Movimento(
	         0,
	         deposito.getId(),
	         cliente.getId(),
	         0,//Entrada por Nota Fiscal...
	         deposito.getDs(),
	         cliente.getDs(),
	         "Entrada por Nota Fiscal",
	         0
	         

	    );
		

		LOGGER.info("Dados do novo movimento: " +movimento.toString());

		Movimento movimentoCreated = given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .body(movimento)
         .when()
         .post("movimento")
         .then()
         .log().all()
		 .statusCode(200).extract().as(Movimento.class);

		
		
		assertNotNull(movimentoCreated, "Valida se o novo movimento não é nulo.");
		assertTrue(movimentoCreated.getId() > 0, "Valida se o ID do novo movimento está definido.");
		
		LOGGER.info("ID do novo movimento: " + movimentoCreated.getId() + ".");
		
		movimento = movimentoCreated;
	}

	@Test
	@Order(4)
	void testPut() {

		LOGGER.info("Teste: MovimentoResource.put [ Alterar dados do movimento ] ...");
		
		LOGGER.info("Movimento antes da alteração: " +movimento.toString());
		
		movimento.setVlTotalMovimento(new Random().nextDouble());
		
		 

		Movimento movimentoOK=given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .body(movimento)
		 .when()
         .put("movimento")
         .then()
         .log().all()
		 .statusCode(200).extract().as(Movimento.class);

		
		
		assertNotNull(movimentoOK, "Valida se o movimento foi alterado.");
		
		LOGGER.info("Dados do movimento alterado: " + movimentoOK.toString() + ".");
		
		if(movimentoOK!=null)  movimento = movimentoOK;
	}

	@Test
	@Order(5)
	void testDelete() {
		LOGGER.info("Teste: MovimentoResource.delete [ Excluir movimento ] ...");

		LOGGER.info("Movimento antes da exclusão: " +movimento.toString());
		

		String response=given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .when()
         .delete("movimento/" + movimento.getId())
         .then()
         .log().all()
		 .statusCode(200).extract().asString();

		LOGGER.info("Resposta da exclusão: " + response + ".");
		
		//Valida se o movimento foi excluído...
		given()
				 .contentType(ContentType.JSON)
				 .header("token", token)
				 .when()
		         .get("movimento/"+movimento.getId())
		         .then()
		         .log().all()
		         .assertThat().statusCode(204);
		
	
		
		
	}

}
