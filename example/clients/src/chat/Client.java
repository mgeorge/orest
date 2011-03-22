/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author geoma48p
 */
public class Client {

    class Response {

        private Integer code;
        private String message;
        private String contentType;
        private String body;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }

    private Response sendRequest(String url, String method, String contentType) {

        HttpURLConnection conn = null;

        Response response = new Response();

        try {

            URL earl = new URL(url);
            conn = (HttpURLConnection) earl.openConnection();
            conn.setRequestMethod(method);
            conn.addRequestProperty("content-type", contentType);
            conn.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.getProperty("line.separator"));
                line = reader.readLine();
            }

            response.setBody(sb.toString());
            response.setContentType(conn.getContentType());

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        try {
            response.setCode(conn.getResponseCode());
            response.setMessage(conn.getResponseMessage());
        } catch (IOException ex) {
            // WTF?  Sun sure loved throwing checked exceptions...
            throw new RuntimeException(ex);
        }

        return response;
    }

    public Response get(String url, String contentType) {
        Response r = sendRequest(url, "GET", contentType);
        return r;
    }

    public static void main(String[] args) throws Exception {
        Client c = new Client();
        Response r = c.get("http://localhost:8080/orest-example/rest/students/1234", "application/json");
        System.out.println(r.getBody());
        System.out.println(r.getCode());
        System.out.println(r.getMessage());
        System.out.println(r.getContentType());
    }
}
