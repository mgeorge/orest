package nz.ac.otago.orest.formats;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import nz.ac.otago.orest.RestRequest;
import nz.ac.otago.orest.RestSession;
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

   private final static Logger logger = LoggerFactory.getLogger(RestSession.class);
   private XStream mapper;

   public XmlFormat() {
      mapper = new XStream(new DomDriver());
   }

   public String formatResource(RestResource resource, RestRequest request) {
      mapper.alias(resource.getClass().getSimpleName().toLowerCase(), resource.getClass());
      String xml = mapper.toXML(resource);
      return xml;
   }

   public String formatCollection(Collection<? extends RestResource> collection, RestRequest request) {

      mapper.alias(request.getRoot(), List.class);
      mapper.alias("id", String.class);

      List<String> ids = new ArrayList<String>();

      for (RestResource resource : collection) {
         ids.add(resource.getStringId());
      }

      String xml = mapper.toXML(ids);
      return xml;
   }

   public String getContentType() {
      return "text/xml";
   }
}
