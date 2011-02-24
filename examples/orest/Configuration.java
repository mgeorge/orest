package orest;

import nz.ac.otago.orest.RestConfiguration;
import students.dao.StudentDAO;

/**
 *
 * @author mark
 */
public class Configuration extends RestConfiguration {


   public void configure() {
      super.addController(new StudentDAO());
      super.setDefaultContentType("text/xml");
   }

}
