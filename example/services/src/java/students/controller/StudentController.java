package students.controller;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import nz.ac.otago.orest.annotations.Controller;
import nz.ac.otago.orest.controller.RestController;
import students.domain.Student;

/**
 *
 * @author mark
 */
@Controller(path = "students")
public final class StudentController implements RestController<Student> {

   private static Map<Integer, Student> students = new TreeMap<Integer, Student>();

   public StudentController() {
      if (students.isEmpty()) {
         create(new Student(1234, "Jim"));
         create(new Student(4321, "Bob"));
      }
   }

   public void create(Student student) {
      students.put(student.getId(), student);
   }

   public void update(String id, Student update) {
      delete(id);
      create(update);
   }

   public Collection<Student> getAll() {
      return students.values();
   }

   public Student get(String id) {
      Student s = students.get(new Integer(id));
      return s;
   }

   public void delete(String id) {
      students.remove(new Integer(id));
   }
}
