package no.kristiania.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class FileHttpController implements HttpController {
    private static final Logger logger = LoggerFactory.getLogger(FileHttpController.class);
    private final HttpServer httpServer;

    public FileHttpController(HttpServer httpServer) {
        this.httpServer = httpServer;
    }

    @Override
    public void handle(String requestAction, String requestPath, Map<String, String> query, String body, OutputStream outputStream) throws IOException {
        File file = new File(httpServer.getFileLocation() + requestPath);
        logger.debug("Requesting file {}", file);

        if (file.isDirectory()) {
            file = new File(file, "index.html");
        }

        if (file.exists()) {
            long length = file.length();
            outputStream.write(("HTTP/1.1 200 OK\r\n" +
                    "Content-Length: " + length + "\r\n" +
                    "Connection: close\r\n" +
                    "\r\n").getBytes());
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                fileInputStream.transferTo(outputStream);
            }

        } else {
            outputStream.write(("HTTP/1.1 404 Not found\r\n" +
                    "Content-Length: 9\r\n" +
                    "Connenction: close\r\n" +
                    "\r\n" +
                    "Not found").getBytes());
        }
    }
}
