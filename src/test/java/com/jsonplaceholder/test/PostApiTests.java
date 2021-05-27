package com.jsonplaceholder.test;

import com.jsonplaceholder.client.PostApiClient;
import com.jsonplaceholder.client.data.Post;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.Arrays;

public class PostApiTests {

    PostApiClient postApiClient;
    @BeforeMethod
    public void setup() throws IOException {
        postApiClient = new PostApiClient();
    }
    @Test
    public void testGetAllPosts() {
        Response response = postApiClient.getPosts();
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(200, actualStatusCode);

    }
    @Test
    public void testGetAllPosts2() {
        Response response = postApiClient.getPosts();

        int actualStatusCode = response.getStatusCode();

        Assert.assertEquals(200, actualStatusCode);

        Post[] postArray = response.as(Post[].class);

        Arrays.stream(postArray).forEach(System.out::println);

        Assert.assertTrue(postArray.length > 0);
    }

    @Test
    public void testCreatePost() {
        Post post = new Post();
        post.setUserId(200);
        post.setTitle("Foo1");
        post.setBody("Bar1");

        Response response = postApiClient.createPost(post);
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(201, actualStatusCode);
        Post actualPost = response.as(Post.class);
        Assert.assertEquals(post.getUserId(), actualPost.getUserId());
        Assert.assertEquals(post.getTitle(), actualPost.getTitle());
        Assert.assertEquals(post.getBody(), actualPost.getBody());
    }

    @Test
    public void testUpdate() {
        Post post = new Post();
        post.setUserId(1);
        post.setTitle("Foo1");
        post.setBody("Bar1");
        post.setId(101);
        Response response = postApiClient.createPost(post);
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(201, actualStatusCode);
        Post actualPost = response.as(Post.class);
        Assert.assertEquals(post.getTitle(), actualPost.getTitle());
        Assert.assertEquals(post.getBody(), actualPost.getBody());
        Assert.assertEquals(post.getId(), actualPost.getId());
        String actualcontenttype = response.getHeader("X-Content-Type-Options");
        Assert.assertEquals("nosniff",actualcontenttype);
        String actualvary = response.getHeader("Vary");
        System.out.println("actual vary is:"+ actualvary);
        Assert.assertEquals("Origin, X-HTTP-Method-Override, Accept-Encoding",actualvary);

    }
    @Test
    public void testDelete() {
        Response response = postApiClient.deletePost();
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(200, actualStatusCode);
        String actualcontent = response.getHeader("Connection");
        Assert.assertEquals("keep-alive",actualcontent);
        String actualserver = response.getHeader("Server");
        Assert.assertEquals("cloudflare",actualserver);
    }

}
