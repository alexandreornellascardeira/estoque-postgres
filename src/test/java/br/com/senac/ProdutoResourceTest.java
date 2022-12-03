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

import br.com.senac.model.Fornecedor;
import br.com.senac.model.Produto;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProdutoResourceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginResourceTest.class);

	// TODO: Implementar método de tokenização ...
	private static final String token = "0123456789";

	public static Produto produto = null;

	@Test
	@Order(3)
	void testFindByIdFornecedor() {

		LOGGER.info("Teste: ProdutoResource.findByIdFornecedor [ Listar produtos de um fornecedor ] ...");

		LOGGER.info("Fornecedor: " + produto.getIdFornecedor());

		Produto[] produtosOK = given().contentType(ContentType.JSON).header("token", token).when()
				.get("produto/findByIdFornecedor/" + produto.getIdFornecedor()).then().log().all().statusCode(200)
				.extract().as(Produto[].class);

		assertNotNull(produtosOK, "Valida se o produto foi encontrado.");

		LOGGER.info("Qtd. Produtos encontrados: " + produtosOK.length + " do fornecedor " + produto.getIdFornecedor()
				+ ".");
	}

	@Test
	@Order(2)
	void testGet() {
		
		LOGGER.info("Teste: ProdutoResource.get [ Buscar produto por um ID ] ...");

		LOGGER.info("ID do produto: " + produto.getId());

		Produto produtoOK = given().contentType(ContentType.JSON).header("token", token).when()
				.get("produto/" + produto.getId()).then().log().all().statusCode(200).extract().as(Produto.class);

		assertNotNull(produtoOK, "Valida se o produto foi encontrado.");

		LOGGER.info("Dados do produto encontrado: " + produtoOK.toString() + ".");

		if (produtoOK != null)
			produto = produtoOK;
	}
	
	/**
	 * Testa inclusão de produto. 
	 * Para criar um novo produto é necessário informar o fornecedor.
	 * Este teste cria um novo fornecedor para incluir o novo produto.  
	 */
	@Test
	@Order(1)
	void testPost() {
		
		LOGGER.info("Teste: ProdutoResource.insert...");

		//Inclui novo fornecedor...
		FornecedorResourceTest fornecedorResourceTest = new FornecedorResourceTest();
		fornecedorResourceTest.testInsert();
		
		Fornecedor fornecedor = FornecedorResourceTest.fornecedor;
		

		// Prepara os dados do novo produto....

		Faker faker = Faker.instance(new Locale("pt-BR"));

		produto = new Produto(
				0, 
				faker.code().isbn13(true), 
			 	faker.commerce().productName(), 
				false, 
				null,
				new Random().nextInt(10),
				fornecedor.getId(),
				fornecedor.getDs());
		 
        

		LOGGER.info("Dados do novo produto: " + produto.toString());

		Produto produtoCreated = given().contentType(ContentType.JSON).header("token", token).body(produto).when()
				.post("produto").then().log().all().statusCode(200).extract().as(Produto.class);

		assertNotNull(produtoCreated, "Valida se o novo produto não é nulo.");
		assertTrue(produtoCreated.getId() > 0, "Valida se o ID do novo produto está definido.");

		LOGGER.info("ID do novo produto: " + produtoCreated.getId() + ".");

		produto = produtoCreated;
	}

	@Test
	@Order(4)
	void testPut() {

		LOGGER.info("Teste: ProdutooResource.put [ Alterar dados do produto ] ...");
		
		LOGGER.info("Produto antes da alteração: " +produto.toString());
		
		produto.setDs(Faker.instance().commerce().productName());
		
		 

		Produto produtoOK=given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .body(produto)
		 .when()
         .put("produto")
         .then()
         .log().all()
		 .statusCode(200).extract().as(Produto.class);

		
		
		assertNotNull(produtoOK, "Valida se o produto foi alterado.");
		
		LOGGER.info("Dados do produto alterado: " + produtoOK.toString() + ".");
		
		if(produtoOK!=null)  produto = produtoOK;
	}

	@Test
	@Order(5)
	void testDelete() {
		LOGGER.info("Teste: ProdutoResource.delete [ Excluir produto ] ...");

		LOGGER.info("Produto antes da exclusão: " +produto.toString());
		

		String response=given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .body(produto)
		 .when()
         .delete("produto/" + produto.getId())
         .then()
         .log().all()
		 .statusCode(200).extract().asString();

		LOGGER.info("Resposta da exclusão: " + response + ".");
		
		//Valida se o produto foi excluído...

		given().contentType(ContentType.JSON).header("token", token).when()
				.get("produto/" + produto.getId()).then().log().all()
				.assertThat().statusCode(204);

	
	}

}
