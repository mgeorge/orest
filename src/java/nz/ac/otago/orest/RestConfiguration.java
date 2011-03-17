package nz.ac.otago.orest;

import java.util.HashMap;
import java.util.Map;
import nz.ac.otago.orest.annotations.Controller;
import nz.ac.otago.orest.controller.RestController;
import nz.ac.otago.orest.formats.JsonFormat;
import nz.ac.otago.orest.formats.RestFormat;
import nz.ac.otago.orest.formats.SimpleFormat;
import nz.ac.otago.orest.formats.XmlFormat;

/**
 *
 * @author mark
 */
public abstract class RestConfiguration {

   private Map<String, RestController<?>> controllers = new HashMap<String, RestController<?>>();
   private Map<String, RestFormat> formats = new HashMap<String, RestFormat>();
   private Map<String, Class> resourceTypes = new HashMap<String, Class>();
   private String defaultContentType = "text/plain";

   public abstract void configure();

   @SuppressWarnings("OverridableMethodCallInConstructor")
   public RestConfiguration() {
      configure();

      // add default formats
      addFormat(new SimpleFormat());
      addFormat(new JsonFormat());
      addFormat(new XmlFormat());
   }

   protected void addController(RestController<?> controller) {
      Controller annotation = controller.getClass().getAnnotation(Controller.class);
      if (annotation == null) {
         // TODO add error message telling user to add annotation
         System.out.println("BUGGER");
      }
      String path = annotation.path();
      controllers.put(path, controller);
   }

   public String checkConfiguration() {
      boolean ok = true;
      StringBuilder builder = new StringBuilder();

      if (resourceTypes.isEmpty()) {
         ok = false;
         builder.append("There are no resource types registered!  ");
      }

      if (controllers.isEmpty()) {
         ok = false;
         builder.append("There are no controllers registered!");
      }

      if(ok) {
         return null;
      } else {
         return builder.toString();
      }

   }

   protected void addResourceType(Class clazz, String type) {
      resourceTypes.put(type, clazz);
   }

   protected void addFormat(RestFormat format) {
      formats.put(format.getContentType(), format);
   }

   public RestFormat getFormat(String contentType) {
      return formats.get(contentType);
   }

   public RestController<?> getController(String path) {
      return controllers.get(path);
   }

   public Class getResourceType(String type) {
      return resourceTypes.get(type);
   }

   public String getDefaultContentType() {
      return defaultContentType;
   }

   public void setDefaultContentType(String defaultContentType) {
      this.defaultContentType = defaultContentType;
   }

   public RestFormat getDefaultFormat() {
      return formats.get(defaultContentType);
   }
}
