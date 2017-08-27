<%--
  Created by IntelliJ IDEA.
  User: Lishion
  Date: 2017/7/9
  Time: 19:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>${END}</title>
  </head>
  <body>
  <form method="get" action="test" >
      <input type="text" name="x">
      <input type="text" name="user.name">
      <input type="text" name="user.sex">
      <input type="text" name="user.age">
      <input type="submit" >
  </form>
  </body>
<script src="../asset/js/jquery-1.11.2.min.js"></script>
<script>
   $.ajax({
       url:"world",
       dataType:"json",
       success:function (x) {

       }
   })
</script>
</html>
