package br.res.automatizado.Configuracao;

import org.hamcrest.Matchers;
import org.junit.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class BaseTest implements Core {

    @BeforeClass
    public static void setup(){
        System.out.println("Link no ar!");
        RestAssured.baseURI = APP_BASE_URL;
        RestAssured.port = APP_PORTA;
        RestAssured.basePath = APP_BASE_PATH;

        //Passando o content-type json
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.setContentType(APP_CONTENT_TYPE);
        RestAssured.requestSpecification = reqBuilder.build();

        //Comando para colocar o maximo de tempo do teste
        ResponseSpecBuilder  resBuilder = new ResponseSpecBuilder();
        resBuilder.expectResponseTime(Matchers.lessThan(MAX_TIMEOUT));

        //Comando para mostrar os fail
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @BeforeClass
    public static void login() {
        Map<String, String> login = new HashMap<>();
        login.put("email", "luis@teste");
        login.put("senha", "123456");

        String TOKEN = given()
                .body(login)
                .when()
                .post("/signin")
                .then()
                .statusCode(200)
                .extract().path("token");


        RestAssured.requestSpecification.header("Authorization", "JWT " + TOKEN);

        RestAssured.get("/reset").then().statusCode(200);
    }
}
