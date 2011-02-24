package nz.ac.otago.orest;

import nz.ac.otago.orest.enums.HttpMethod;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

import nz.ac.otago.orest.controller.RestController;
import nz.ac.otago.orest.formats.RestFormat;
import nz.ac.otago.orest.resource.RestResource;
import orest.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestSession {

   private final static Logger logger = LoggerFactory.getLogger(RestSession.class);
   private Configuration config;

   public void setConfiguration(Configuration config) {
      this.config = config;
   }

   void processRequest(String path, HttpMethod method, HttpServletResponse response, String contentType) throws Exception {

      RestFormat format = config.getFormat(contentType);
      if (format == null) {
         format = config.getDefaultFormat();
      }

      String[] pathElements = path.split("/");
      String root = pathElements[1];

      RestController<?> controller = config.getController(root);

      RestRequest restRequest = new RestRequest(root, controller, response, format);

      if (pathElements.length > 2) {

         String id = pathElements[2];
         logger.debug("Using resource ID '{}'", id);

         if (method == HttpMethod.DELETE) {
            logger.debug("Calling delete({}) on controller", id);
            controller.delete(id);
         } else if (method == HttpMethod.POST) {
            // TODO implement POST/create interaction
            logger.debug("Calling create on controller");
            controller.create(null);
         } else if (method == HttpMethod.PUT) {
            // TODO implement PUT/update interaction
            logger.debug("Calling update on controller");
            controller.update(id, null);
         } else if (method == HttpMethod.GET) {

            requestResource(restRequest, id);
         }

      } else if (method == HttpMethod.GET) {
         // if root selected (since there is no ID
         requestRoot(restRequest);
      } else {
         logger.error("Method '{}' and Path '{}' do not make sense.", method, path);
         response.sendError(405, String.format("Method '%1s' can not be used with path '%2s'", method, path));
      }
   }

   private void requestRoot(RestRequest restRequest) throws Exception {
      logger.debug("Getting all resources for controller");

      Collection<? extends RestResource> col = restRequest.getController().getAll();

      RestFormat format = restRequest.getFormat();
      HttpServletResponse response = restRequest.getResponse();

      response.getWriter().println(format.formatCollection(col, restRequest));
   }

   private void requestResource(RestRequest restRequest, String id) throws Exception {
      logger.debug("Getting single resource with ID '{}'", id);
      RestResource resource = restRequest.getController().get(id);
      if (resource != null) {
         RestFormat format = restRequest.getFormat();
         HttpServletResponse response = restRequest.getResponse();
         String responseString = format.formatResource(resource, restRequest);
         response.setContentType(format.getContentType());
         response.getWriter().println(responseString);
      } else {
         restRequest.getResponse().sendError(404, String.format("Resource with id '%1s' does not exist", id));
      }
   }
}
