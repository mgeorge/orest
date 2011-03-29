package students;

import nz.ac.otago.orest.client.BadResponse;
import nz.ac.otago.orest.client.RestClient;
import nz.ac.otago.orest.client.RestResponse;
import students.domain.Student;

/**
 *
 * @author mark
 */
public class StudentClient {

   public static void main(String[] args) {
      RestClient c = new RestClient("http://localhost:8080/orest-example-services/rest/");
      c.addResourceType(Student.class, "student");

      System.out.println("\n-- Testing post");
      c.post("students", new Student(7744, "Fred"));
      System.out.println(c.getCollection("students"));

      System.out.println("\n-- Testing get with content-type");

      RestResponse r = c.get("students/7744", "application/json");
      System.out.println(r.getBody());
      System.out.println(r.getStatus());
      System.out.println(r.getMessage());
      System.out.println(r.getContentType());

      System.out.println("\n-- Testing get");

      Student student = (Student) c.get("students/7744");
      System.out.println(student);

      System.out.println("\n-- Testing getCollection");

      System.out.println(c.getCollection("students"));

      System.out.println("\n-- Testing put");

      c.put("students/7744", new Student(null, "Bub"));
      student = (Student) c.get("students/7744");
      System.out.println(student);

      System.out.println("\n-- Testing delete");

      c.delete("students/7744");

      System.out.println(c.getCollection("students"));

      System.out.println("\n-- Testing post to wrong URL");

      try {
         c.post("students/1234", new Student(444, "Jim"));
      } catch (BadResponse ex) {
         System.out.println(ex.getMessage());
      }

   }
}
