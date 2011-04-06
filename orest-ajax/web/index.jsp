<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

   <head>
      <title>Manage Products</title>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <link rel="stylesheet" href="css/forms.css" type="text/css" />
      <script src="scripts/ORestClient.js" type="text/javascript"></script>
      <script src="scripts/students.js" type="text/javascript"></script>
   </head>

   <body onload="getStudentList()">

      <form name="studentForm" action="#">

         <fieldset><legend>Available Students</legend>

            <select name="students" size="5" onclick="getStudent()" class="fullwidth">
               <option>Loading students...</option>
            </select>

         </fieldset>

         <fieldset><legend>Edit Student</legend>
            <label>ID:</label><input type="text" name="id"/>
            <label>Name:</label><input type="text" name="name" />
            <div class="buttongroup">
               <button type="reset" onclick="update()">Update</button>
               <button type="reset" onclick="remove()">Delete</button>
               <button type="reset" onclick="create()">Add Student</button>
            </div>
         </fieldset>

      </form>

   </body>

</html>
