// backend/HospitalServer.java
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.*;

public class HospitalServer {
    static List<String> staff = new ArrayList<>();
    static List<String> reception = new ArrayList<>();
    static List<String> pharmacy = new ArrayList<>();
    static List<String> billing = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/staff", new SimpleHandler(staff));
        server.createContext("/reception", new SimpleHandler(reception));
        server.createContext("/pharmacy", new SimpleHandler(pharmacy));
        server.createContext("/billing", new SimpleHandler(billing));

        server.setExecutor(null);
        System.out.println("Hospital server running on http://localhost:8080");
        server.start();
    }

    static class SimpleHandler implements HttpHandler {
        private List<String> store;
        public SimpleHandler(List<String> store) { this.store = store; }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "";
            if ("GET".equals(exchange.getRequestMethod())) {
                response = String.join(",", store);
            } else if ("POST".equals(exchange.getRequestMethod())) {
                byte[] data = exchange.getRequestBody().readAllBytes();
                String item = new String(data);
                store.add(item);
                response = "Added: " + item;
            }
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
