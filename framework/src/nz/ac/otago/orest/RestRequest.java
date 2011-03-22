package nz.ac.otago.orest;

import javax.servlet.http.HttpServletRequest;
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
   private HttpServletRequest request;
   private HttpServletResponse response;
   private RestConfiguration configuration;
   private String resourceId;
   private RestFormat format;

   public RestRequest(String root, RestController<?> controller, HttpServletRequest request, HttpServletResponse response, RestFormat format, RestConfiguration configuration) {
      this.root = root;
      this.controller = controller;
      this.request = request;
      this.response = response;
      this.format = format;
      this.configuration = configuration;
   }

   public RestFormat getFormat() {
      return format;
   }

   public HttpServletRequest getServletRequest() {
      return request;
   }

   public HttpServletResponse getServletResponse() {
      return response;
   }

   public RestController<?> getController() {
      return controller;
   }

   public RestConfiguration getConfiguration() {
      return this.configuration;
   }

   public String getRoot() {
      return root;
   }

   public void setRoot(String root) {
      this.root = root;
   }

   public String getResourceId() {
      return resourceId;
   }

   void setResourceId(String id) {
      this.resourceId = id;
   }
}
