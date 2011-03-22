package orest;

import chat.controller.ChatController;
import nz.ac.otago.orest.RestConfiguration;
import students.dao.StudentDAO;
import students.domain.Student;

/**
 *
 * @author mark
 */
public class Configuration extends RestConfiguration {


   public void configure() {
      super.addController(new StudentDAO());
      super.addResourceType(Student.class, "student");

      super.addController(new ChatController());


   }

}
