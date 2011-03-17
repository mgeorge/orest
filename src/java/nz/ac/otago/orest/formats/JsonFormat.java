package nz.ac.otago.orest.formats;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nz.ac.otago.orest.RestRequest;
import nz.ac.otago.orest.resource.RestResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mark
 */
public class JsonFormat implements RestFormat {

   private final static Logger logger = LoggerFactory.getLogger(JsonFormat.class);
   private XStream mapper;

   public JsonFormat() {
      mapper = new XStream(new JettisonMappedXmlDriver());
   }

   public String serialiseResource(RestResource resource, RestRequest request) {
      mapper.alias(resource.getClass().getSimpleName().toLowerCase(), resource.getClass());
      logger.debug("Serialising resource with xstream");
      String xml = mapper.toXML(resource);
      return xml;
   }

   public String serialiseCollection(Collection<? extends RestResource> collection, RestRequest request) {
      mapper.alias(request.getRoot(), List.class);
      mapper.alias("id", String.class);

      List<String> ids = new ArrayList<String>();

      for (RestResource resource : collection) {
         ids.add(resource.getResourceId());
      }

      logger.debug("Serialising resource collection with xstream");
      String xml = mapper.toXML(ids);
      return xml;
   }

   public RestResource deserialiseResource(String data, RestRequest request) {
      // need to get the root element so we can set the correct alias for xstream
      Pattern pattern = Pattern.compile("\\{\\s*?\"(.*?)\"\\s*?:.*");
      Matcher matcher = pattern.matcher(data);
      matcher.matches();
      String root = matcher.group(1);
      logger.debug("Using '{}' as root", root);


      Class resourceType = request.getConfiguration().getResourceType(root);

      logger.debug("Aliasing '{}' with '{}'", root, resourceType.getName());
      mapper.alias(root, resourceType);

      logger.debug("Deserialising resource with xstream");
      RestResource resource = (RestResource) mapper.fromXML(data);

      return resource;
   }

   public String getContentType() {
      return "application/json";
   }
}
