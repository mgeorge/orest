package orest;

import chat.controller.ChatController;
import chat.domain.Message;
import nz.ac.otago.orest.RestConfiguration;
import students.controller.StudentController;
import students.domain.Student;

/**
 *
 * @author mark
 */
public class Configuration extends RestConfiguration {


   public void configure() {
      super.addController(new StudentController());
      super.addController(new ChatController());
      super.addResourceType(Student.class, "student");
      super.addResourceType(Message.class, "message");
   }

}
