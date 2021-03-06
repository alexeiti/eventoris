<%@taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

</head>

<body>
	<header>
		<div class="main-navigation">
			<div class="container clearfix">
				<div class="grid_3 margin-padding-clear">
					<div class="logo">
						<a href="index.htm"> <img src="resources/img/logo_03.png" />
						</a>
					</div>
				</div>
				<div class="grid_4 margin-padding-clear">
					<nav id="menu" class="nav">
						<ul>
						<% String active = request.getParameter("active"); 
							if(active.equals("event")){
						%>
							<li ><a href="<c:url value="index.htm"/>"><i
									class="icon-home-outline"></i>Home</a></li>
							<li><a class="active" href="<c:url value="events.htm"/>"><i
									class="icon-calendar-outlilne"></i>Evenimente</a></li>
							<%} else if(active.equals("home")){%>
							<li ><a class="active" href="<c:url value="index.htm"/>"><i class="icon-home-outline"></i>Home</a></li>
							<li><a href="<c:url value="events.htm"/>"><i class="icon-calendar-outlilne"></i>Evenimente</a></li>
							<%} else if(active.equals("none")){%>
							<li ><a href="<c:url value="index.htm"/>"><i class="icon-home-outline"></i>Home</a></li>
							<li><a href="<c:url value="events.htm"/>"><i class="icon-calendar-outlilne"></i>Evenimente</a></li>
					<%} %>
							<!-- <li><a href="#"><i class="icon-camera-outline"></i>Galerie</a></li> -->
						</ul>
					</nav>
				</div>
				<div class="grid_4 margin-padding-clear">
					<div class="search">
						<form action="searchevent.htm">
							<input type="text" placeholder="Caută eveniment"
								name="search_text" id="search">
							<button type="submit">
								<i class="icon-search-outline"></i>
							</button>
						</form>
					</div>

					<div class = "log-out">
					<sec:authorize access="hasRole('ROLE_USER')">
						<!-- For login user -->
						<c:url value="/j_spring_security_logout" var="logoutUrl" />
						<form action="${logoutUrl}" method="post" id="logoutForm">
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
						</form>
						<script>
							function formSubmit() {
								document.getElementById("logoutForm").submit();
							}
						</script>
						<c:if test="${pageContext.request.userPrincipal.name != null}">
							  <a href="profile" style="color:#000000;"><sec:authentication property="principal.mainName" /> <sec:authentication property="principal.familyName" /></a> |
							  <a href="javascript:formSubmit()" > Logout</a>
						</c:if>
					</sec:authorize>
					</div>
				</div>
			</div>
		</div>
	</header>