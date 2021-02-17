<%@page import="model.Usuario"%>
<%@page import="model.Carrito"%>
<%@page import="model.Camiseta"%>
<%@page import="java.util.List"%>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="deLaFlorBonillaAlvaro.Probador"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>DSS Probador</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<link rel="stylesheet" href="assets/css/main.css" />
	</head>
	<body class="homepage is-preload">
		<div id="page-wrapper">

			<!-- Header -->
				<section id="header">
					<div class="container">

						<!-- Logo -->
							<h1 id="logo"><a href="index.html">DSS Probador</a></h1>
							<p>Practica 2 - Modo cliente</p>
							<c:if test="${carrito != null}">
								<p>Seleccione usuario (actualmente: <%out.println(Probador.user);%>)</p>
							</c:if>
							<c:if test="${carrito == null}">
								<p>No hay ningun usuario seleccionado. Elija uno abajo</p>
							</c:if>							
							<div class="row aln-center">
								<form action="${pageContext.request.contextPath}/selectClient" method="post" name="user">
									</br>
									<select onchange="this.form.submit()"name="user">
										<option value="---">---</option>
										<%
											List<Usuario> usuarios = Probador.obtenerUsuarios();
											for (Usuario usuario : usuarios) {
										%>		
										<option value=<%out.println(usuario.getDni());%>><%out.println(usuario.getDni());%></option>
										<%							
											}
		 								%>
									</select>
								</form>
							</div>
						<!-- Nav -->
							<nav id="nav">
								<ul>
									<li><a href="#" class="fab fa-youtube">Video tutorial</a></li>
									<li>
										<a href="#" class="icon solid fa-cog"><span>Opciones</span></a>
										<ul>
											<li><a href="admin">Modo administrador</a></li>
										</ul>
									</li>
								</ul>
							</nav>

					</div>
				</section>

			<!-- Features -->
				<section id="features">
					<div class="container">
						<header>
							<h2>Camisetas disponibles <strong>Elija una</strong>!</h2>
						</header>
						<% 
							List<Camiseta> camisetas = Probador.getCamisetasDisponibles();
						%>
						<% if (camisetas.isEmpty()) { %>
							<header>
								<h2>Vaya, <strong>no hay nada</strong> ninguna camiseta disponible!</h2>
							</header>						
						<%
							}
						%>
						<div class="row aln-center">
						<%
							for (Camiseta camiseta: camisetas) {
						%>
							<div class="col-4 col-6-medium col-12-small">

								<!-- Feature -->
									<section>
										<a href="#" class="image featured"><img style="height: 400px;object-fit: cover;" src=<%out.println(camiseta.getImage());%> alt="" /></a>
										<header>
											<h3><%out.println(camiseta.getModelo());%></h3>
										</header>
										<p>El precio de esta camiseta es de <strong><%out.println(camiseta.getPrecio());%> euros</strong></p>
									</section>
									<c:if test="${carrito.id != null}">
										<div class="col-12">
											<ul class="actions">
												<li><a href="reservar?idUsuario=${carrito.usuario.dni}&idCamiseta=<%out.println(camiseta.getId());%>&modo=true" class="button icon solid fa-file">Reservar</a></li>
											</ul>
										</div>
									</c:if>
							</div>
						<%
							}
						%>
					</div>
				</section>
			<!-- Banner -->
				<section id="banner">
					<div class="container">
						<p>Aqui tiene <strong>su carrito</strong>.<br />
						Puede eliminar camisetas si lo desea.</p>
					</div>
				</section>
<section id="features">
					<div class="container">
						<header>
							<h2>Camisetas reservadas por usted <strong>Cambio de cuenta disponible arriba</strong>!</h2>
						</header>
						</br>
						<c:if test="${carrito != null && empty carrito.camisetas}">
							<header>
								<h2>Vaya, <strong>no hay nada</strong> en su carrito!</h2>
							</header>						
						</c:if>
						<c:if test="${carrito == null}">
							<header>
								<h2>Vaya, <strong>no hay ninguna cuenta seleccionada</strong> Elija su cuenta arriba!</h2>
							</header>	
						</c:if>
						<div class="row aln-center">
							<c:forEach items="${carrito.camisetas}" var="camiseta2">
								<div class="col-4 col-6-medium col-12-small">
		
									<!-- Feature -->
										<section>
											<a href="#" class="image featured"><img style="height: 400px;object-fit: cover;" src="${camiseta2.image}" alt="" /></a>
											<header>
												<h3>${camiseta2.modelo}</h3>
											</header>
											<p>El precio de esta camiseta es de <strong>${camiseta2.precio} euros</strong></p>
										</section>
										<div class="col-12">
											<ul class="actions">
												<li><a href="reservar?idUsuario=${carrito.usuario.dni}&idCamiseta=${camiseta2.id}&modo=false" class="button icon solid fa-file">Borrar reserva</a></li>
											</ul>
										</div>
								</div>
							</c:forEach>
					</div>
				</section>
			
			<!-- Footer -->
				<section id="footer">
					<div id="copyright" class="container">
						<ul class="links">
							<li>DSS. Master Oficial de Ingenieria Informatica</li><li>Alumno: Alvaro de la Flor Bonilla</li>
						</ul>
					</div>
				</section>

		</div>

		<!-- Scripts -->
			<script src="assets/js/jquery.min.js"></script>
			<script src="assets/js/jquery.dropotron.min.js"></script>
			<script src="assets/js/browser.min.js"></script>
			<script src="assets/js/breakpoints.min.js"></script>
			<script src="assets/js/util.js"></script>
			<script src="assets/js/main.js"></script>

	</body>
</html>