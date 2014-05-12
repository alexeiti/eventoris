<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file="/WEB-INF/jsp/include_links.jsp" %>

<title>Eventoris | Home</title>

<%@ include file="/WEB-INF/jsp/include_menu.jsp" %>

	<div id="stick-menu">
		<div class="container clearfix">
			<div class="grid_12 margin-padding-clear">
			<nav>
				<ul class="navigation">
					<li data-slide="1">Home</li>
					<li data-slide="2">Ultimele Evenimente</li>
					<li data-slide="3">Top Evenimente</li>
					<li data-slide="4">Ultimele Noutăţi</li>
				</ul>
				</nav>
			</div>

		</div>
	</div>

	<div class="slide" id="slide1" data-slide="1" data-stellar-background-ratio="0.2">
		<div class="container clearfix">
			<div id="content" class="grid_6">
				<div class="intro-text">
					<h1>Hai cu noi</h1>
					<ul>
						<li>să ne distrăm</li>
						<li>să fim mai buni</li>
						<li>să fim prieteni</li>
					</ul>
					
					<button>Înregistrează-te</button>
				</div>
			</div>
			<div class="grid_6 omega">

			</div>

		</div>
	</div>



	<div class="slide" id="slide2" data-slide="2" data-stellar-background-ratio="0.2">
		<div class="container clearfix">
			<div id="content" class="grid_12">
				<div class="slide-title">
					<h1>Ultimele Evenimente</h1>
				</div>
			</div>
		</div>
		<div class="container clearfix">
			<div class="grid_12">
				<div class = "last-events">
					<p>

					</p>
				</div>
			</div>
		</div>
	</div>



	<div class="slide" id="slide3" data-slide="3" data-stellar-background-ratio="0.5">
		<div class="container clearfix">
			<div id="content" class="grid_12">
				<div class="slide-title">
					<h1>Top Evenimente</h1>
				</div>
			</div>
		</div>
		<div class="container clearfix">
			<div id="content" class="grid_12">
				<div class = "top-events">

				</div>
			</div>
		</div>
	</div>



	<div class="slide" id="slide4" data-slide="4" data-stellar-background-ratio="0.2">
		<div class="container clearfix">
			<div id="content" class="grid_12">
				<div class="slide-title">
					<h1>Ultimele Noutăţi</h1>
				</div>
			</div>
		</div>
	</div>
	
<%@ include file="/WEB-INF/jsp/include_footer.jsp" %>
<%@ include file="/WEB-INF/jsp/include_scripts.jsp" %>