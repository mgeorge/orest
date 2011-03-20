package nz.ac.otago.orest.NOLONGERUSED;

import nz.ac.otago.orest.util.PropertyUtils;
import java.util.Map;

/**
 *
 * @author mark
 */
public class UpdateHelper {

   public void update(Object objectToUpdate, Map<String, Object> tuplesToUpdate) {

      for (Map.Entry<String, Object> attribute : tuplesToUpdate.entrySet()) {
         
         String key = attribute.getKey();
         Object value = attribute.getValue();

         PropertyUtils.setProperty(objectToUpdate, key, value);

      }

   }

}
