package br.res.automatizado.tests;

import br.res.automatizado.Configuracao.BaseTest;
import io.restassured.RestAssured;
import io.restassured.specification.FilterableRequestSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class AuthTest extends BaseTest {
    @Test
    public void naoDeveAcessarAPISemToken(){
        FilterableRequestSpecification req = (FilterableRequestSpecification) RestAssured.requestSpecification;
        req.removeHeader("Authorization");
        given()
                .when()
                .get("/contas")
                .then()
                .statusCode(401);
    }
}
