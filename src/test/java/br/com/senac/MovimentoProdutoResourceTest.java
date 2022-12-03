package br.com.senac;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.senac.model.Cliente;
import br.com.senac.model.Movimento;
import br.com.senac.model.MovimentoProduto;
import br.com.senac.model.Produto;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MovimentoProdutoResourceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginResourceTest.class);

	// TODO: Implementar método de tokenização ...
	private static final String token = "0123456789";

	private static MovimentoProduto movimentoProduto = null;
	private static Movimento movimento = null;
	
	private static Movimento movimentoSaida =null;

	@Test
	@Order(3)
	void testGetMovimentoProdutoByIdMovimentoFunc() {

		LOGGER.info(
				"Teste: MovimentoProdutoResource.getMovimentoProdutoByIdMovimentoFunc [ Encontrar itens de um movimento ] ...");

		MovimentoProduto[] movimentoProdutosOK = given().contentType(ContentType.JSON).header("token", token).when()
				.get("item/findByMovimento/" + movimentoProduto.getIdMovimento()).then().log().all().statusCode(200)
				.extract().as(MovimentoProduto[].class);

		assertNotNull(movimentoProdutosOK, "Valida se o item de movimento foi encontrado.");

		LOGGER.info("Qtd. de itens encontrados: " + movimentoProdutosOK.length + " do movimento "
				+ movimentoProduto.getIdMovimento() + ".");
	}

	@Test
	@Order(4)
	void testGetMovimentoProdutoRefFunc() {

		LOGGER.info(
				"Teste: MovimentoProdutoResource.getMovimentoProdutoRefFunc [ Encontrar referências de um movimento ] ...");

		MovimentoProduto[] movimentoProdutosOK = given().contentType(ContentType.JSON).header("token", token)
				.body(movimentoProduto).when().post("item/findRefs/").then().log().all().statusCode(200).extract()
				.as(MovimentoProduto[].class);

		assertNotNull(movimentoProdutosOK, "Valida se o item de movimento foi encontrado.");

		LOGGER.info("Qtd. de referências encontradas: " + movimentoProdutosOK.length + ".");

	}

	@Test
	@Order(2)
	void testGet() {

		LOGGER.info("Teste: MovimentoProdutoResource.get[ Retornar dados do item de movimento ] ...");

		MovimentoProduto movimentoProdutoOK = given().contentType(ContentType.JSON).header("token", token).when()
				.get("item/" + movimentoProduto.getId()).then().log().all().statusCode(200).extract()
				.as(MovimentoProduto.class);

		assertNotNull(movimentoProdutoOK, "Valida se o item de movimento foi encontrado.");

		LOGGER.info("Dados do item: " + movimentoProdutoOK.toString() + ".");
	}

	/**
	 * Testa a inclusão de item de movimento. Para isso, cria um movimento de
	 * entrada e um novo produto antes.
	 */
	@Test
	@Order(1)
	void testPost() {
		LOGGER.info("Teste: MovimentoProdutoResource.insert...");

		// Gera movimento de entrada...

		MovimentoResourceTest movimentoResourceTest = new MovimentoResourceTest();
		movimentoResourceTest.testPost();

	    movimento = MovimentoResourceTest.movimento;

		// Cria um novo produto...
		ProdutoResourceTest produtoResourceTest = new ProdutoResourceTest();
		produtoResourceTest.testPost();

		Produto produto = ProdutoResourceTest.produto;


		// Cria novo item de movimento...
		movimentoProduto = new MovimentoProduto();

		movimentoProduto.setIdMovimento(movimento.getId()).
		setIdProduto(produto.getId()).setVlUnitario(new Random().nextDouble()).setQtProduto(new Random().nextInt(10));

		if (movimentoProduto.getQtProduto()<=0) {
			movimentoProduto.setQtProduto(1);
		}
		
		if(movimentoProduto.getVlUnitario()<=0) {
			movimentoProduto.setVlUnitario(1.5*movimentoProduto.getQtProduto());
		}
		
		 
		
		LOGGER.info("Dados do novo item de movimento: " + movimentoProduto.toString());

		MovimentoProduto movimentoProdutoCreated = given().contentType(ContentType.JSON).header("token", token).body(movimentoProduto).when()
				.post("item").then().log().all().statusCode(200).extract().as(MovimentoProduto.class);

		assertNotNull(movimentoProdutoCreated, "Valida se o novo item de movimento não é nulo.");
		assertTrue(movimentoProdutoCreated.getId() > 0, "Valida se o ID do novo item de movimento está definido.");

		LOGGER.info("ID do novo item de movimento: " + movimentoProdutoCreated.getId() + ".");

		movimentoProduto = movimentoProdutoCreated;
	}

	/*
	@Test
	@Order(5)
	void testPut() {
		
		LOGGER.info("Teste: MovimentoProdutoResource.put [ Alterar dados do item de movimento ] ...");
		
		LOGGER.info("Item de movimento antes da alteração: " +movimentoProduto.toString());
		
		movimentoProduto.setVlUnitario(new Random().nextDouble());
		
		 

		MovimentoProduto movimentoProdutoOK=given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .body(movimentoProduto)
		 .when()
         .put("item")
         .then()
         .log().all()
		 .statusCode(200).extract().as(MovimentoProduto.class);

		
		
		assertNotNull(movimentoProdutoOK, "Valida se o item de movimento foi alterado.");
		
		LOGGER.info("Dados do item de movimento alterado: " + movimentoProdutoOK.toString() + ".");
		
		if(movimentoProdutoOK!=null)  movimentoProduto = movimentoProdutoOK;
	}*/

	/*
	@Test
	@Order(6)
	void testDelete() {
		
		LOGGER.info("Teste: MovimentoProdutoResource.delete [ Excluir item de movimento ] ...");

		LOGGER.info("Movimento antes da exclusão: " +movimentoProduto.toString());
		

		String response=given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .when()
         .delete("item/" + movimentoProduto.getId())
         .then()
         .log().all()
		 .statusCode(200).extract().asString();

		
		assertNotNull(response, "Valida se o item de movimento foi excluído.");
		
		LOGGER.info("Resposta da exclusão: " + response + ".");
	}*/

	
	/**
	 * 
	 */
	@Test
	@Order(7)
	void testSaida() {
		
		LOGGER.info("Teste: Gerar movimento de saída ...");
		

		LOGGER.info("Movimento de entrada: " + movimento);
		
		
		//Gera um cliente para executar um movimento de saída...
		ClienteResourceTest clienteResourceTest=new ClienteResourceTest();
		clienteResourceTest.testPost();
		
		Cliente clienteSaida = ClienteResourceTest.cliente;
		
		movimentoSaida = new Movimento();
		
		movimentoSaida.setIdCliente(clienteSaida.getId());
		movimentoSaida.setIdDeposito(movimento.getIdDeposito());
		
		movimentoSaida.setIdTpMovimento(movimento.getIdTpMovimento()+2);
		
		movimentoSaida.setVlTotalMovimento(movimento.getVlTotalMovimento());
		
		

		LOGGER.info("Dados do novo movimento de saída: " +movimentoSaida.toString());

		Movimento movimentoSaidaCreated = given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .body(movimentoSaida)
         .when()
         .post("movimento")
         .then()
         .log().all()
		 .statusCode(200).extract().as(Movimento.class);

		
		
		assertNotNull(movimentoSaidaCreated, "Valida se o novo movimento de saída não é nulo.");
		assertTrue(movimentoSaidaCreated.getId() > 0, "Valida se o ID do novo movimento de saída está definido.");
		
		LOGGER.info("ID do novo movimento de saída: " + movimentoSaidaCreated.getId() + ".");
		
		movimentoSaida = movimentoSaidaCreated;
		
		
	}
	
	@Test
	@Order(8)
	void testSaidaItem() {
		
		
		MovimentoProduto movimentoProdutoSaida = new MovimentoProduto();
		movimentoProdutoSaida.setCdProduto(movimentoProduto.getCdProduto());
		movimentoProdutoSaida.setIdProduto(movimentoProduto.getIdProduto());
		movimentoProdutoSaida.setIdMovimento(movimentoSaida.getId());
		movimentoProdutoSaida.setIdMovimentoProdutoRef(movimentoProduto.getId());
		movimentoProdutoSaida.setVlUnitario(movimentoProduto.getVlUnitario());
		movimentoProdutoSaida.setQtProduto(movimentoProduto.getQtProduto());
		
		movimentoProdutoSaida.setTpMovimento(movimentoSaida.getIdTpMovimento());
		
		
		MovimentoProduto movimentoProdutoSaidaCreated=given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .body(movimentoProdutoSaida)
		 .when()
         .post("item")
         .then()
         .log().all()
		 .statusCode(200).extract().as(MovimentoProduto.class);

		
		
		assertNotNull(movimentoProdutoSaidaCreated, "Valida se o novo item de movimento de saída não é nulo.");
		assertTrue(movimentoProdutoSaidaCreated.getId() > 0, "Valida se o ID do novo item de movimento de saída está definido.");

		LOGGER.info("ID do novo item de movimento de Saída: " + movimentoProdutoSaidaCreated.getId() + ".");

		
	}
}
