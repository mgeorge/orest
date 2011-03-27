package nz.ac.otago.orest.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nz.ac.otago.orest.RestConfiguration;
import nz.ac.otago.orest.RestRequest;
import nz.ac.otago.orest.formats.JsonFormat;
import nz.ac.otago.orest.formats.RestFormat;
import nz.ac.otago.orest.resource.RestResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mark
 */
public class RestClient {

   private final static Logger logger = LoggerFactory.getLogger(JsonFormat.class);
   private String orestURL;
   private RestConfiguration config = new ClientConfiguration();
   private RestFormat defaultFormat = config.getDefaultFormat();
   private String defaultContentType = defaultFormat.getContentType();
   private RestRequest request = new RestRequest(null, null, null, null, defaultFormat, config);

   public RestClient(String orestURL) {
      this.orestURL = orestURL;
   }

   public RestResponse get(String url, String contentType) {
      return sendRequest(url, "GET", contentType, null);
   }

   public RestResource get(String url) {
      RestResponse r = get(url, defaultContentType);
      return defaultFormat.deserialiseResource(r.getBody(), request);
   }

   public Collection<String> getCollection(String url) {
      RestResponse r = get(url, defaultContentType);
      request.setRoot(url);
      Collection<String> collection = defaultFormat.deserialiseCollection(r.getBody(), request);
      return collection;
   }

   public void delete(String url) {
      sendRequest(url, "DELETE", defaultContentType, null);
   }

   public void post(String url, RestResource resource) {
      String body = defaultFormat.serialiseResource(resource, request);
      sendRequest(url, "POST", defaultContentType, body);
   }

   public void put(String url, RestResource resource) {
      String body = defaultFormat.serialiseResource(resource, request);
      sendRequest(url, "PUT", defaultContentType, body);
   }

   public void addResourceType(Class clazz, String type) {
      config.addResourceType(clazz, type);
   }

   private RestResponse sendRequest(String url, String method, String contentType, String body) {

      HttpURLConnection conn = null;

      RestResponse response = new RestResponse();

      try {

         URL earl = new URL(orestURL + "/" + url);
         conn = (HttpURLConnection) earl.openConnection();
         conn.setRequestMethod(method);
         conn.addRequestProperty("content-type", contentType);

         if (body != null) {
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            PrintWriter writer = new PrintWriter(os);
            writer.write(body);
            writer.close();
            os.close();
         }

         conn.connect();

         response.setBody(readStream(conn.getInputStream()));
         response.setContentType(conn.getContentType());

      } catch (Exception ex) {
         try {
            response.setStatus(conn.getResponseCode());
            response.setMessage(conn.getResponseMessage());

            String errorBody = readStream(conn.getErrorStream());

            // Tomcat specific.  Extract error message from HTML.
            Matcher matcher = Pattern.compile("<h1>(.*?)</h1>").matcher(errorBody);
            if (matcher.find()) {
               response.setBody(matcher.group(1));
            } else {
               // if no match then use entire error message
               response.setBody(errorBody);
            }

            throw new BadResponse(response);

         } catch (IOException ioex) {
            // WTF?  Sun sure loved throwing checked exceptions...
            logger.error("Error getting response details", ioex);
         }
      }

      try {
         response.setStatus(conn.getResponseCode());
         response.setMessage(conn.getResponseMessage());
      } catch (IOException ex) {
         logger.error("Error getting response details", ex);
      }

      conn.disconnect(); // Holy crap! - didn't have to catch an exception!
      return response;
   }

   private String readStream(InputStream stream) {
      try {
         BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
         StringBuilder sb = new StringBuilder();
         String line = reader.readLine();
         while (line != null) {
            sb.append(line);
            sb.append(System.getProperty("line.separator"));
            line = reader.readLine();
         }
         reader.close();

         return sb.toString().trim();
      } catch (IOException ex) {
         throw new RuntimeException("Error reading stream", ex);
      }
   }
}
