package modulos.produto;

import dataFactory.ProdutoDataFactory;
import dataFactory.UsuarioDataFactory;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@DisplayName("Testes de API Rest do modulo de Produto ")
public class ProdutoTest {
    private String token;

    @BeforeEach
    public void beforeEach(){
        // Configurando os dados da API Rest da Lojinha
        baseURI= "http://165.227.93.41";
        basePath = "/lojinha";

        // Obter o token do usuario do usario Adim
        //given(Dado)
        this.token = given()
                .contentType(ContentType.JSON)
                .body(UsuarioDataFactory.criarUsuarioAdministrador())
            .when()//when(Quando)
                .post("/v2/login")
            .then()
                //then(Então)
                .extract()
            .path("data.token");
    }

    @Test
    @DisplayName("Validar que o valor do Produto igual a 0.00 nao e permetido")
    public void  testValidarLimitesZeradoProibidosValorProduto(){

        // tentar inserir um produto com valor zero 0.00 e validar que a mensagem de erro foi apresentada e o
        // status code retornado foi 422


       given()
                .contentType(ContentType.JSON)
                .header("token", this.token)
                .body(ProdutoDataFactory.criarProdutoComumComOValorIgualA(0.00))
        .when()
                .post("/v2/produtos")
        .then()
                .assertThat()
                .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                .statusCode(422);





    }

        @Test
        @DisplayName("Validar que o valor do Produto igual a 7000.01 nao e permetido")
        public void  testValidarLimitesMaiorSeteMilProibidosValorProduto(){

            // tentar inserir um produto com valor 7000.01 e validar que a mensagem de erro foi apresentada e o
            // status code retornado foi 422

            given()
                    .contentType(ContentType.JSON)
                    .header("token", this.token)
                    .body(ProdutoDataFactory.criarProdutoComumComOValorIgualA(7000.01))
                .when()
                    .post("/v2/produtos")
                .then()
                    .assertThat()
                    .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                    .statusCode(422);





        }

    }



