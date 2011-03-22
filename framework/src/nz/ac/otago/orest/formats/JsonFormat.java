package nz.ac.otago.orest.formats;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mark
 */
public class JsonFormat extends XmlFormat {

   public String getContentType() {
      return "application/json";
   }

   protected String getRootPattern() {
      return "\\{\\s*?\"(.*?)\"\\s*?:.*";
   }

   protected XStream getMapper() {
      return new XStream(new JettisonMappedXmlDriver());
   }

   protected Logger getLogger() {
       return LoggerFactory.getLogger(JsonFormat.class);
   }

}
