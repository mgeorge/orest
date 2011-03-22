package nz.ac.otago.orest.formats;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nz.ac.otago.orest.ORestException;
import nz.ac.otago.orest.RestRequest;
import nz.ac.otago.orest.resource.RestResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Uses xstream.
 *
 *
 * @author mark
 */
public class XmlFormat implements RestFormat {

   private final Logger logger = getLogger();

   private XStream mapper = getMapper();

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
      Pattern pattern = Pattern.compile(getRootPattern());
      Matcher matcher = pattern.matcher(data);
      matcher.find();
      String root = matcher.group(1);
      logger.debug("Using '{}' as root", root);


      Class resourceType = request.getConfiguration().getResourceType(root);
      if (resourceType == null) {
         throw new ORestException("There is no resource type registered for '" + root + "'", 412);
      }

      logger.debug("Aliasing '{}' with '{}'", root, resourceType.getName());
      mapper.alias(root, resourceType);

      logger.debug("Deserialising resource with xstream");
      RestResource resource = (RestResource) mapper.fromXML(data);

      return resource;
   }

   public String getContentType() {
      return "text/xml";
   }

   public Collection<String> deserialiseCollection(String data, RestRequest request) {
      // if there is no data then return an empty collection
      if (data == null || data.isEmpty()) {
         return new HashSet<String>();
      }

      mapper.alias(request.getRoot(), List.class);

      mapper.alias("id", String.class);

      Collection<String> coll = (Collection<String>) mapper.fromXML(data);

      return coll;
   }

   protected String getRootPattern() {
      return "<\\s*?(\\S+?)\\s*?>.*";
   }

   protected XStream getMapper() {
      return new XStream(new DomDriver());
   }

   protected Logger getLogger() {
       return LoggerFactory.getLogger(XmlFormat.class);
   }

}
