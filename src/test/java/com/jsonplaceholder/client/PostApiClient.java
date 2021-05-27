package com.jsonplaceholder.client;

import com.jsonplaceholder.client.data.Post;
import io.restassured.response.Response;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class PostApiClient {
    Properties prop;

    public PostApiClient() throws IOException {
        prop = new Properties();
        FileInputStream fp = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/config.properties");
        prop.load(fp);
    }

    public Response getPosts() {
        //String URL = prop.getProperty("url");
        return given().when()
                .get(prop.getProperty("url"))
                .then()
                //.statusCode(200)
                .log().all().extract().response();
    }

    public Response createPost(Post post) {
        return given()
                .contentType("application/json; charset=utf-8")
                .body(post)
                .when()
                .post(prop.getProperty("url"))
                .then()
                //.statusCode(201)
                .extract().response();
    }

    public Response updatePost(Post post) {
        return given()
                .contentType("application/json; charset=utf-8")
                .body(post)
                .when()
                .put(prop.getProperty("url1"))
                .then()
                //.statusCode(201)
                .extract().response();

    }

    public Response deletePost() {
        return given()
                .when()
                .delete(prop.getProperty("url1"))
                .then()
                .statusCode(200)
                .extract().response();
    }
}