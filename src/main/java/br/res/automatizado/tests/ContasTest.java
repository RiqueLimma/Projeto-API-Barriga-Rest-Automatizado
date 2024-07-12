package br.res.automatizado.tests;

import br.res.automatizado.Configuracao.BaseTest;
import br.res.automatizado.utils.Commons;
import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class ContasTest extends BaseTest {
    @Test
    public void deveIncluirContaComSucesso(){
                given()
                            .body("{ \"nome\": \"Conta inserida\" }")
                        .when()
                            .post("/contas")
                        .then()
                            .statusCode(201);
    }

    @Test
    public void deveAlterarContaComSucesso(){
        Integer CONTA_ID = Commons.getContaPeloNome("Conta para alterar");
        given()
                    .body("{ \"nome\": \"Conta alterada\" }")
                    .pathParam("id", CONTA_ID)
                .when()
                    .put("/contas/{id}")
                .then()
                    .statusCode(200)
                    .body("nome", is("Conta alterada"));
    }

    @Test
    public void naoDeveInserirContaComMesmoNome(){
        given()
                    .body("{ \"nome\": \"Conta mesmo nome\" }")
                .when()
                    .post("/contas")
                .then()
                    .statusCode(400)
                    .body("error" ,is("Já existe uma conta com esse nome!"));
    }
}
