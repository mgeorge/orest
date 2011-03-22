package students;

import nz.ac.otago.orest.client.BadResponse;
import students.domain.Student;
import nz.ac.otago.orest.client.RestClient;
import nz.ac.otago.orest.client.RestResponse;

/**
 *
 * @author mark
 */
public class StudentClient {

   public static void main(String[] args) {
      RestClient c = new RestClient("http://localhost:8080/orest-example-services/rest/");
      c.addResourceType(Student.class, "student");

      Student s = new Student(555, "Da Devil");

      try {
         c.post("students/1234", s);
      } catch (BadResponse ex) {
         System.out.println(ex.getMessage());
      }

//      RestResponse r = c.get("students/1234", "application/json");
//      System.out.println(r.getBody());
//      System.out.println(r.getCode());
//      System.out.println(r.getMessage());
//      System.out.println(r.getContentType());
//
//      RestResource res = c.get("students/1234");
//      System.out.println(res);
//
      System.out.println(c.getCollection("students"));
//
//      c.delete("students/1234");
//
//      System.out.println(c.getCollection("students"));

   }
}
