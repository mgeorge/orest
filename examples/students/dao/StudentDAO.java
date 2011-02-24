package students.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import nz.ac.otago.orest.annotations.Controller;
import nz.ac.otago.orest.controller.RestController;
import nz.ac.otago.orest.util.UpdateHelper;
import students.domain.Student;

/**
 *
 * @author mark
 */
@Controller(path="students")
public final class StudentDAO implements RestController<Student> {

   private static Map<String, Student> students = new HashMap<String, Student>();

   public StudentDAO() {
      if(students.isEmpty()) {
         create(new Student(1234, "Jim"));
         create(new Student(4321, "Bob"));
      }
   }

   public void create(Student student) {
      students.put(String.valueOf(student.getId()), student);
   }

   public void update(String id, Map<String,Object> tuples) {
      Student student = get(id);

      new UpdateHelper().update(student, tuples);
   }

   public Collection<Student> getAll() {
      return students.values();
   }

   public Student get(String id) {
      Student s = students.get(id);
      return s;
   }

   public void delete(String id) {
      System.out.println("*****DELETE CALLED with ID " + id);
      students.remove(id);
   }

}
