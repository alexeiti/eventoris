<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<%@ include file="/WEB-INF/jsp/include_links.jsp"%>

 <!-- Do not show page if logged in -->
 	   <c:if test="${pageContext.request.userPrincipal.name != null}">
			 <c:redirect url="/myevents.htm"/>
		</c:if>
		
<title>Eventoris | Creare cont</title>

<jsp:include page="/WEB-INF/jsp/include_menu.jsp">
	<jsp:param name = "active" value ="none"/>
</jsp:include>
<section>
	<div class="form-block">
		<form:form method="post" commandName="createUser">
		
			<i class = "icon-user-add-outline"></i>
			<h1>Înregistrare</h1><br>
		
			<form:input path="familyName" placeholder="Nume"/>
			<form:errors path="familyName" cssClass="error" />
		
			<form:input path="name" placeholder="Prenume"/>
			<form:errors path="name" cssClass="error" />
		
			<form:input path="email" placeholder="Email"/>
			<form:errors path="email" cssClass="error" />
					
			<form:input type="password" path="password" placeholder="Parola"/>
			<form:errors path="password" cssClass="error" />
			
			<form:input type="password" path="passwordRepeat" placeholder="Parola repetată"/>
			<form:errors path="passwordRepeat" cssClass="error" />
	 	
			<br>
			<button type="submit">Ok</button><br>
		</form:form>
	</div>
</section>
<%@ include file="/WEB-INF/jsp/include_footer.jsp"%>
<%@ include file="/WEB-INF/jsp/include_scripts.jsp"%>
