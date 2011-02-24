package nz.ac.otago.orest.formats;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import nz.ac.otago.orest.RestRequest;
import nz.ac.otago.orest.RestSession;
import nz.ac.otago.orest.resource.RestResource;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mark
 */
public class JsonFormat implements RestFormat {

   private final static Logger logger = LoggerFactory.getLogger(RestSession.class);

   private ObjectMapper mapper;

   public JsonFormat() {
      mapper = new ObjectMapper();
      mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
   }

   public String formatResource(RestResource resource, RestRequest request) {
      String json = "";
      try {
         json = mapper.writeValueAsString(resource);
      } catch (IOException ex) {
         logger.error("Error marshalling object with Jackson", ex);
      }
      return json;
   }

   public String formatCollection(Collection<? extends RestResource> collection, RestRequest request) {
      List<String> ids = new ArrayList<String>();

      for (RestResource resource : collection) {
         ids.add(resource.getStringId());
      }

      String json = "";
      try {
         json = mapper.writeValueAsString(ids);
      } catch (IOException ex) {
         logger.error("Error marshalling object with Jackson", ex);
      }
      return json;
   }

   public String getContentType() {
      return "application/json";
   }

}
