package br.res.automatizado.Configuracao;

import io.restassured.http.ContentType;


public interface Core {

    String APP_BASE_URL = "https://barrigarest.wcaquino.me";
                        //https         //http
    Integer APP_PORTA =  443; // 80;
    String APP_BASE_PATH = "";

    ContentType APP_CONTENT_TYPE = ContentType.JSON;

    Long MAX_TIMEOUT = 10000L;
}
