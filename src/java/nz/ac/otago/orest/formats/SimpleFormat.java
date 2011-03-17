package nz.ac.otago.orest.formats;

import java.util.Collection;
import nz.ac.otago.orest.RestRequest;
import nz.ac.otago.orest.formats.RestFormat;
import nz.ac.otago.orest.resource.RestResource;

/**
 *
 * @author mark
 */
public class SimpleFormat implements RestFormat {

   public String serialiseResource(RestResource resource, RestRequest request) {
      return resource.toString();
   }

   public String serialiseCollection(Collection<? extends RestResource> collection, RestRequest request) {
      StringBuilder builder = new StringBuilder();

      for (RestResource resource : collection) {
         builder.append(resource.getResourceId());
         builder.append("\n");
      }
      
      return builder.toString();
   }

   public RestResource deserialiseResource(String data, RestRequest request) {
      throw new UnsupportedOperationException("The plain text serialiser currently only produces data - it can't consume it.");
   }


   public String getContentType() {
      return "text/plain";
   }

}
