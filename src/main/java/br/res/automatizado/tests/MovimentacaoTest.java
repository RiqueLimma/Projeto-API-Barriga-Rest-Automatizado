package br.res.automatizado.tests;

import br.res.automatizado.Configuracao.BaseTest;
import br.res.automatizado.utils.Commons;
import br.res.automatizado.utils.Movimentacao;
import br.res.automatizado.utils.DataUtils;
import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class MovimentacaoTest extends BaseTest {
    private static Movimentacao getMovimentacaoValida() {
        Movimentacao mov = new Movimentacao();
        mov.setConta_id(Commons.getIdContaPeloNome("Conta para movimentacoes"));
        mov.setDescricao("Descricao da movimentacao");
        mov.setEnvolvido("Envolvido da mov");
        mov.setTipo("REC");
        mov.setData_transacao(DataUtils.getDataDiferencaDias(-1));
        mov.setData_pagamento(DataUtils.getDataDiferencaDias(5));
        mov.setValor(100f);
        mov.setStatus(true);
        return mov;
    }

    @Test
    public void deveInserirMovimentacaoComSucesso(){
        Movimentacao mov = getMovimentacaoValida();

         given()
                    .body(mov)
                .when()
                    .post("/transacoes")
                .then()
                    .statusCode(201)
                    .log().all();
    }

    @Test
    public void deveValidarCamposObrigatorioMovimentacao(){
        given()
                    .body("{}")
                .when()
                    .post("/transacoes")
                .then()
                   .statusCode(400)
                    .body("$", hasSize(8))
                    .body("msg", hasItems(
                        "Data da Movimentação é obrigatório",
                        "Data do pagamento é obrigatório",
                        "Descrição é obrigatório",
                        "Interessado é obrigatório",
                        "Valor é obrigatório",
                        "Valor deve ser um número",
                        "Conta é obrigatório",
                        "Situação é obrigatório"
                ));
    }

    @Test
    public void naoDeveInserirMovimentacaoComDataFutura(){
        Movimentacao mov = getMovimentacaoValida();
        mov.setData_transacao(DataUtils.getDataDiferencaDias(2));
        given()
                .body(mov)
                .when()
                .post("/transacoes")
                .then()
                .statusCode(400)
                .body("$", hasSize(1))
                .body("msg", hasItems("Data da Movimentação deve ser menor ou igual à data atual"));
    }

    @Test
    public void naoDeveRemoverContaComMovimentcao(){
        Integer CONTA_ID = Commons.getIdContaPeloNome("Conta com movimentacao");
        given()
                    .pathParam("id", CONTA_ID)
                .when()
                    .delete("/contas/{id}")
                .then()
                    .statusCode(500)
                    .body("constraint", is("transacoes_conta_id_foreign"));
    }

    @Test
    public void deveRemoverMovimentacao(){
        Integer MOV_ID = Commons.getIdMovPeloDescricao("Movimentacao para exclusao");
        given()
                    .pathParam("id", MOV_ID)
                .when()
                    .delete("/transacoes/{id}")
                .then()
                    .statusCode(204);
    }
}
