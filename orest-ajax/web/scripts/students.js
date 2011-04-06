var client = new RestClient("http://localhost:8080/orest-example-services/rest/");

function Student(id, name) {
   this.id = id;
   this.name = name;
}
Student.prototype.constructor = Student;


function getStudentList() {
   client.getCollection("students", displayStudentList);
}

/**
 * Displays a list of product IDs in a list (select element named 'products')
 */
function displayStudentList(studentList) {
   // get the list component using its name property
   var select = document.studentForm.students;

   // clear list so we can refresh it
   select.innerHTML="";

   // add IDs to list
   for(i in studentList) {
      select.add(new Option(studentList[i], studentList[i]), null);
   }
}

function getStudent() {
   var id = getSelectedStudent();
   client.get("students/"+id, displayStudent);
}

function displayStudent(student) {
   var form = document.studentForm;
   form.id.value = student.id;
   form.name.value = student.name;
}

function remove() {
   var id = getSelectedStudent();
   client.remove("students/"+id, refreshStudentList);
}

function update() {
   var id = getSelectedStudent();
   var form = document.studentForm;

   var student = new Student(form.id.value, form.name.value);

   client.update("students/" + id, student, refreshStudentList);
}

function create() {
   var form = document.studentForm;

   var student = new Student(form.id.value, form.name.value);

   client.create("students", student, refreshStudentList);
}

function getSelectedStudent() {
   var form = document.studentForm;
   var select = form.students;
   var id = select.options[select.selectedIndex].text;
   return id;
}

function refreshStudentList() {
   var select = document.studentForm.students;

   // clear select list to remove old data
   select.innerHTML="";

   // get an up to date list
   getStudentList();
}