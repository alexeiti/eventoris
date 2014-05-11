<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <title><fmt:message key="title"/></title>
  <style>
    .error { color: red; }
  </style>  
</head>
<body>
<h1><fmt:message key="addevent.heading"/></h1>
<form:form method="post" commandName="createEvent">
  <table width="95%" bgcolor="f8f8ff" border="0" cellspacing="0" cellpadding="5">
    <tr>
      <td align="right" width="20%">Title:</td>
        <td width="20%">
          <form:input path="title"/>
        </td>
        <td width="60%">
          <form:errors path="title" cssClass="error"/>
        </td>
    </tr>
     <tr>
      <td align="right" width="20%">Description:</td>
        <td width="20%">
          <form:input path="description"/>
        </td>
        <td width="60%">
          <form:errors path="description" cssClass="error"/>
        </td>
    </tr>
  </table>
  <br>
  <input type="submit" align="center" value="Execute">
</form:form>
<a href="<c:url value="eventlist.htm"/>">Home</a>
</body>
</html>