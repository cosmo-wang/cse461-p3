import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import utils.Utils;

public class Server {
    public static final QueryProcessor qp = new QueryProcessor();
    public static final String TEMPLATE = readFile("template.html");
    public static final String INSERTION_DELIMITER = "<!-- INSERT HERE -->";

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java Server <portnum>");
            return;
        }
        try {
            InetAddress ip = InetAddress.getLocalHost();
            String hostname = ip.getHostName();
            int port = Utils.parseInt(args[0]);
            if (port == -1) {
                System.err.println("Invalid port number. Port number must be an integer.");
                return;
            }
            try {
                System.out.println("Starting server on " + hostname + ":" + port);
                HttpServer server = HttpServer.create(new InetSocketAddress(hostname, port), 0);
                server.createContext("/", new HomeHandler());
                server.createContext("/img", new ImageHandler());
                server.createContext("/search", new SearchHandler());
                server.setExecutor(null);
                server.start();
            } catch (IOException e) {
                System.err.println("Failed to create server");
                e.printStackTrace();
            }
        } catch (UnknownHostException e) {
            System.err.println("Failed to resolve current hostname.");
            e.printStackTrace();
        }
    }

    static class HomeHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            byte[] response = TEMPLATE.getBytes();
            t.sendResponseHeaders(200, response.length);
            OutputStream os = t.getResponseBody();
            os.write(response);
            os.close();
        }
    }

    static class ImageHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String path = t.getRequestURI().getPath();
            try {
                byte[] response = readImage(path.substring(1, path.length()));
                Headers h = t.getResponseHeaders();
                if (path.endsWith("jpg")) {
                    h.add("Content-type","image/jpeg");
                } else if (path.endsWith("png")) {
                    h.add("Content-type","image/png");
                } else if (path.endsWith("svg")) {
                    h.add("Content-type","image/svg+xml");
                }
                t.sendResponseHeaders(200, response.length);
                OutputStream os = t.getResponseBody();
                os.write(response);
                os.close();
            } catch (IOException e) {
                sendInvalidQueryResponse(t);
            }
        }
    }

    static class SearchHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String clientIp = t.getRemoteAddress().getAddress().toString();
            String[] urlValues = URLDecoder.decode(t.getRequestURI().getQuery(), StandardCharsets.UTF_8).split("&");
            if (urlValues.length != 2) {
                sendInvalidQueryResponse(t);
            }
            String type = urlValues[0].substring(urlValues[0].indexOf("=") + 1, urlValues[0].length());
            String[] queries = urlValues[1].substring(urlValues[1].indexOf("=") + 1, urlValues[1].length()).split(",");
            System.out.println("Client " + clientIp + " searched for "
                                + Arrays.toString(queries).replace('[', '\"').replace(']', '\"') + " in " + type);
            String res = "";
            for (String query : queries) {
                try {
                    String curRes = qp.processQuery(type, query.trim());
                    if (curRes == null) {
                        sendInvalidQueryResponse(t);
                    }
                    res += curRes;
                } catch (Exception e) {
                    sendInvalidQueryResponse(t);
                    e.printStackTrace();
                    return;
                }
            }
            byte[] response = insertResult(res, INSERTION_DELIMITER);
            t.sendResponseHeaders(200, response.length);
            OutputStream os = t.getResponseBody();
            os.write(response);
            os.close();
        }
    }

    private static void sendInvalidQueryResponse(HttpExchange t) throws IOException {
        byte[] response = insertResult("<div class=\"resultHeader\">Invalid Query Format. Seperate multiple queries by commas.</div>", INSERTION_DELIMITER);
        t.sendResponseHeaders(400, response.length);
        OutputStream os = t.getResponseBody();
        os.write(response);
        os.close();
    }

    private static String readFile(String filePath) {
      StringBuilder contentBuilder = new StringBuilder();
      try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8)) {
          stream.forEach(s -> contentBuilder.append(s).append("\n"));
      } catch (IOException e) {
          e.printStackTrace();
      }
      return contentBuilder.toString();
    }

    private static byte[] readImage(String imagepath) throws IOException {
        File file = new File(imagepath);
        byte[] response = new byte[(int)file.length()];
        FileInputStream is = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(is);
        bis.read(response, 0, response.length);
        bis.close();
        return response;
    }

    private static byte[] insertResult(String res, String delimiter) {
        String[] split = TEMPLATE.split(delimiter);
        return (split[0] + res + split[1]).getBytes();
    }
}