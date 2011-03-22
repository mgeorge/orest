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
   private String resourceId;
   private RestFormat format;

   public RestRequest(String root, RestController<?> controller, HttpServletRequest request, HttpServletResponse response, RestFormat format) {
      this.root = root;
      this.controller = controller;
      this.request = request;
      this.response = response;
      this.format = format;
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
      RestSession session = (RestSession) request.getSession().getAttribute("session");
      RestConfiguration configuration = session.getConfiguration();
      return configuration;
   }

   public String getRoot() {
      return root;
   }

   public String getResourceId() {
      return resourceId;
   }

   void setResourceId(String id) {
      this.resourceId = id;
   }
}
