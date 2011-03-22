package students.domain;

import nz.ac.otago.orest.resource.RestResource;

/**
 *
 * @author mark
 */
public class Student implements RestResource {

   private Integer id;
   private String name;
//   private Paper paper;

   public Student() {
   }

   public Student(Integer id, String name) {
      this.id = id;
      this.name = name;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   @Override
   public String toString() {
      return id + ", " + name;
   }

   public String getResourceId() {
      return String.valueOf(id);
   }

}
