package students.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import nz.ac.otago.orest.annotations.Controller;
import nz.ac.otago.orest.controller.RestController;
import students.domain.Student;

/**
 *
 * @author mark
 */
@Controller(path = "students")
public final class StudentDAO implements RestController<Student> {

   private static Map<Integer, Student> students = new HashMap<Integer, Student>();

   public StudentDAO() {
      if (students.isEmpty()) {
         create(new Student(1234, "Jim"));
         create(new Student(4321, "Bob"));
      }
   }

   public void create(Student student) {
      students.put(student.getId(), student);
   }

   public void update(String id, Student update) {
      if (students.containsKey(new Integer(id))) {
         Student student = get(id);

         if(update.getId() != null) {
            student.setId(update.getId());
         }

         if(update.getName() != null) {
            student.setName(update.getName());
         }
         
      } else {
         create(update);
      }
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
