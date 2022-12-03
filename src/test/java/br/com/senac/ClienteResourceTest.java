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
import br.com.senac.model.Cliente;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClienteResourceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginResourceTest.class);

	// TODO: Implementar método de tokenização ...
	private static final String token = "0123456789";

	public static Cliente cliente = null;
	public static String cd =null;
	
	private static Cidade cidade =null;

	@Test
	@Order(3)
	void testFindByIdEstado() {

		LOGGER.info("Teste: ClienteResource.FindByIdEstado [ Encontrar clientes através do ID da UF ] ...");

		Cliente[] clientesOK = given().contentType(ContentType.JSON).header("token", token).when()
				.get("cliente/findByIdEstado/" + cidade.getIdEstado()).then().log().all().statusCode(200).extract()
				.as(Cliente[].class);

		assertNotNull(clientesOK, "Valida se a cidade foi encontrada.");

		LOGGER.info("Qtd. Cidades encontradas: " + clientesOK.length + " na UF " + cidade.getCdEstado() + ".");

	}
	
	@Test
	@Order(3)
	void testFindByCd() {

		LOGGER.info("Teste: ClienteResource.FindByCd [ Encontrar clientes através do CPF/CNPJ  ] ...");

		Cliente clienteOK = given().contentType(ContentType.JSON).header("token", token).when()
				.get("cliente/findByCd/" + cd).then().log().all().statusCode(200).extract()
				.as(Cliente.class);

		assertNotNull(clienteOK, "Valida se o cliente foi encontrado.");

		LOGGER.info("Dados do cliente encontrado: " + clienteOK.toString());
		
		if(clienteOK!=null) cliente = clienteOK;

	}


	@Test
	@Order(2)
	void testGet() {
		

		LOGGER.info("Teste: ClienteResource.get [ Encontrar cliente através do ID ] ...");
		
		Cliente clienteOK=given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .when()
         .get("cliente/"+cliente.getId())
         .then()
         .log().all()
		 .statusCode(200).extract().as(Cliente.class);

		
		
		assertNotNull(clienteOK, "Valida se o cliente foi encontrado.");
		
		LOGGER.info("Dados do cliente encontrado: " + clienteOK.toString() + ".");
		
		if(clienteOK!=null) cliente = clienteOK;
	}

	@Test
	@Order(1)
	void testPost() {
LOGGER.info("Teste: ClienteResource.insert...");
		
	 	
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
        
		
		//Prepara os dados do novo cliente....
		
        Faker faker = Faker.instance(new Locale("pt-BR"));
        
	    cliente=new Cliente(
				 0,
				 fakeCNPJ.toString(),
				 faker.funnyName().name(),
				 idCidade,
				 cidade.getDs(),
		         cidade.getCdEstado());
				
		LOGGER.info("Dados do novo cliente: " +cliente.toString());

		Cliente clienteCreated = given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .body(cliente)
         .when()
         .post("cliente")
         .then()
         .log().all()
		 .statusCode(200).extract().as(Cliente.class);

		
		
		assertNotNull(clienteCreated, "Valida se o novo cliente não é nulo.");
		assertTrue(clienteCreated.getId() > 0, "Valida se o ID do novo cliente está definido.");
		
		LOGGER.info("ID do novo cliente: " + clienteCreated.getId() + ".");
		
		cliente = clienteCreated;
		
		cd = clienteCreated.getCd();
	}

	@Test
	@Order(4)
	void testPut() {
		
		LOGGER.info("Teste: ClienteResource.put [ Alterar dados do cliente ] ...");
		
		LOGGER.info("Cliente antes da alteração: " +cliente.toString());
		
		cliente.setDs(Faker.instance(new Locale("pt-BR")).funnyName().name());
		
		 

		Cliente clienteOK=given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .body(cliente)
		 .when()
         .put("cliente")
         .then()
         .log().all()
		 .statusCode(200).extract().as(Cliente.class);

		
		
		assertNotNull(clienteOK, "Valida se o cliente foi alterado.");
		
		LOGGER.info("Dados do cliente alterado: " + clienteOK.toString() + ".");
		
		if(clienteOK!=null)  cliente = clienteOK;
	}

	@Test
	@Order(5)
	void testDelete() {
		
		LOGGER.info("Teste: ClienteResource.delete [ Excluir cliente ] ...");

		LOGGER.info("Cliente antes da exclusão: " +cliente.toString());
		

		String response=given()
		 .contentType(ContentType.JSON)
		 .header("token", token)
		 .when()
         .delete("cliente/" + cliente.getId())
         .then()
         .log().all()
		 .statusCode(200).extract().asString();


		LOGGER.info("Resposta da exclusão: " + response + ".");
	
		
		//Verifica se a exclusão foi efetivada...
		given()
				 .contentType(ContentType.JSON)
				 .header("token", token)
				 .when()
		         .get("cliente/"+cliente.getId())
		         .then()
		         .log().all()
		         .assertThat().statusCode(204);
	
		
		
	}

}
