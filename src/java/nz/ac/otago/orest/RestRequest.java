package nz.ac.otago.orest;

import javax.servlet.http.HttpServletResponse;
import nz.ac.otago.orest.controller.RestController;
import nz.ac.otago.orest.formats.RestFormat;

/**
 *
 * @author mark
 */
public class RestRequest {

   private String root;
   private RestController<?> controller;
   private HttpServletResponse response;
   private RestFormat format;

   public RestRequest(String root, RestController<?> controller, HttpServletResponse response, RestFormat format) {
      this.root = root;
      this.controller = controller;
      this.response = response;
      this.format = format;
   }

   public RestFormat getFormat() {
      return format;
   }

   public HttpServletResponse getResponse() {
      return response;
   }

   public RestController<?> getController() {
      return controller;
   }

   public String getRoot() {
      return root;
   }
}
