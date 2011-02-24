package nz.ac.otago.orest.formats;

import java.util.Collection;
import nz.ac.otago.orest.RestRequest;
import nz.ac.otago.orest.resource.RestResource;

/**
 *
 * @author mark
 */
public class SimpleTextFormat implements RestFormat {

   public String formatResource(RestResource resource, RestRequest request) {
      return resource.toString();
   }

   public String formatCollection(Collection<? extends RestResource> collection, RestRequest request) {
      StringBuilder builder = new StringBuilder();

      for (RestResource resource : collection) {
         builder.append(resource.getStringId());
         builder.append("\n");
      }
      
      return builder.toString();
   }

   public String getContentType() {
      return "text/plain";
   }

}
