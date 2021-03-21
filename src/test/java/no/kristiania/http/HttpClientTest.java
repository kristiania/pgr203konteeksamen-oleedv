package no.kristiania.http;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpClientTest {


    @Test
    void shouldReadStatusCode() throws IOException {
        HttpClientResponse response = makeEchoRequest("/echo?status=401");
        assertEquals(response.getStatusCode(), 401);
    }

    @Test
    void shouldReturnHeaders() throws IOException {
        HttpClientResponse response = makeEchoRequest("/echo?content-type=text/css");
        assertThat(response.getHeaders()).containsEntry("content-type", "text/css; charset=utf-8");
    }

    @Test
    void shouldReadBody() throws IOException {
        HttpClientResponse response = makeEchoRequest("/echo?body=hello+world");
        assertThat(response.getHeaders()).containsEntry("content-length", "15");
        assertThat(response.getContentLength()).isEqualTo(15);
        assertThat(response.getBody()).isEqualTo("hello world");
    }

    private HttpClientResponse makeEchoRequest(String requestTarget) throws IOException {
        HttpClient client = new HttpClient("urlecho.appspot.com", 80, requestTarget);
        return client.executeRequest("GET");
    }

    @Test
    void shouldExecuteRequests() throws IOException {
        HttpClientResponse response = makeEchoRequest("/echo");
        assertEquals(response.getStatusCode(), 200);
    }

    @Test
    void shouldReadBodyAgain() throws IOException {
        HttpClient client = new HttpClient("urlecho.appspot.com", 80, "/echo?body=Hello");
        HttpClientResponse response = client.executeRequest("GET");
        assertEquals("Hello", response.getBody());
    }

    @Test
    void shouldReadFailureStatusCode() throws IOException {
        HttpClient client = new HttpClient("urlecho.appspot.com", 80, "/echo?status=401");
        HttpClientResponse response = client.executeRequest("GET");
        assertEquals(401, response.getStatusCode());
    }
}
