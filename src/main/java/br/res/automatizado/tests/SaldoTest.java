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

public class SaldoTest extends BaseTest {
    @Test
    public void deveCalcularSaldoContas(){
        Integer CONTA_ID = Commons.getIdContaPeloNome("Conta para saldo");
        given()
                .when()
                .get("/saldo")
                .then()
                .statusCode(200)
                .body("find{it.conta_id == "+CONTA_ID+"}.saldo", is("534.00"))
        ;
    }

}
