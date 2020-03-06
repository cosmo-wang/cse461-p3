import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Server {
    public static final QueryProcessor qp = new QueryProcessor();
    public static final String HTML_HEAD = "<html><head>\n" +
                                            "<title>Monster Hunter Index</title>\n" +
                                            "<style>\n" +
                                            "table {\n" +
                                            "font-family: arial, sans-serif;\n" +
                                            "border-collapse: collapse;\n" +
                                            "width: 100%;\n" +
                                            "}\n" +
                                            "td, th {\n" +
                                            "border: 1px solid #dddddd;\n" +
                                            "text-align: left;\n" +
                                            "padding: 8px;\n" +
                                            "}\n" +
                                            "tr:nth-child(even) {\n" +
                                            "background-color: #dddddd;\n" +
                                            "}\n" +
                                            "</style>\n" +
                                            "</head>\n" + 
                                            "<body>\n" + 
                                            "<center style=\"font-size:500%;\">\n" + 
                                            "Monster Hunter Index" + 
                                            "</center>\n" + 
                                            "<p>\n" + 
                                            "<div style=\"height:20px;\"></div>\n" + 
                                            "<center>\n" + 
                                            "<form action=\"/query\" method=\"get\">\n" + 
                                            "<input type=\"text\" size=30 name=\"terms\" />\n" + 
                                            "<input type=\"submit\" value=\"Search\" />\n" + 
                                            "</form>\n" + 
                                            "</center></p>\n";
    public static final String HTML_END = "</body></html>";

    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 2387), 0);
            server.createContext("/", new HomeHandler());
            server.createContext("/query", new QueryHandler());
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            System.err.println("Failed to create server");
            e.printStackTrace();
        }
    }

    static class HomeHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            printRequest(t);
            byte[] response = (HTML_HEAD + HTML_END).getBytes();
            t.sendResponseHeaders(200, response.length);
            OutputStream os = t.getResponseBody();
            os.write(response);
            os.close();
        }
    }

    static class QueryHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            printRequest(t);
            String[] urlQuery = URLDecoder.decode(t.getRequestURI().getQuery(), StandardCharsets.UTF_8).split("=");
            if (urlQuery.length != 2) {
                this.sendInvalidQueryResponse(t);
            }
            String res = qp.processQuery(urlQuery[1]);
            byte[] response = (HTML_HEAD
                               + "<div>\n"
                               + res
                               + "</div>\n"
                               + HTML_END).getBytes();
            t.sendResponseHeaders(200, response.length);
            OutputStream os = t.getResponseBody();
            os.write(response);
            os.close();
        }

        private void sendInvalidQueryResponse(HttpExchange t) throws IOException {
            byte[] response = (HTML_HEAD
                            + "<div>Invalid Request. Correct Query: &lt;type&gt;:&lt;queryword&gt;</div>"
                            + HTML_END).getBytes();
            t.sendResponseHeaders(400, response.length);
            OutputStream os = t.getResponseBody();
            os.write(response);
            os.close();
        }
    }

    private static void printRequest(HttpExchange t) {
        Headers headers = t.getRequestHeaders();
        for (String key: headers.keySet()) {
            System.out.println(key + ": " + headers.get(key));
        }
    }
}