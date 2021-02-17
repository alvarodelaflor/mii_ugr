<%@page import="model.Usuario"%>
<%@page import="model.Carrito"%>
<%@page import="model.Camiseta"%>
<%@page import="java.util.List"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML>
<html>
<head>
<title>DSS Probador</title>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="assets/css/main.css" />
</head>
<body class="homepage is-preload">
	<div id="page-wrapper">

		<!-- Header -->
		<section id="header">
			<div class="container">

				<!-- Logo -->
				<h1 id="logo">
					<a href="index.html">DSS Probador</a>
				</h1>
				<p>Practica 2 - Modo administrador</p>
				<!-- Nav -->
				<nav id="nav">
					<ul>
						<li><a href="#" class="fab fa-youtube">Video tutorial</a></li>
						<li><a href="#" class="icon solid fa-cog"><span>Opciones</span></a>
							<ul>
								<li><a href="${pageContext.request.contextPath}">Modo
										cliente</a></li>
								<li><a href="#">Reset</a></li>
								<li><a href="#">Insertar nueva prenda</a></li>
							</ul></li>
					</ul>
				</nav>

			</div>
		</section>

		<!-- Features -->
		<section id="features">
			<div class="container">
				<header>
					<h2>Camisetas en el sistema</h2>
					<c:if test="${empty camisetas}">
						<p>
							No hay <strong>ninguna camiseta</strong> en el sistema
						</p>
					</c:if>
				</header>
				<div class="row aln-center">
					<c:forEach items="${camisetas}" var="camiseta">
						<div class="col-4 col-6-medium col-12-small">

							<!-- Feature -->
							<section>
								<a href="#" class="image featured"><img
									style="height: 400px; object-fit: cover;"
									src="${camiseta.image}" alt="" /></a>
								<header>
									<h3>${camiseta.modelo}</h3>
								</header>
								<p>
									El precio de esta camiseta es de <strong>
										${camiseta.precio}euros</strong>
								</p>
								<c:if test="${camiseta.vendida}">Ten cuidado, esta camiseta <strong
										style="color: red">esta reservada</strong>
								</c:if>
								<c:if test="${!camiseta.vendida}">Esta camiseta <strong
										style="color: green">no esta reservada</strong>
								</c:if>
							</section>
							<div class="col-12">
								<ul class="actions">
									<li><a href="borrarCamiseta?idCamiseta=${camiseta.id}"
										class="button icon solid fa-file">Borrar camiseta</a></li>
								</ul>
							</div>
						</div>
					</c:forEach>
				</div>
		</section>
		<!-- Banner -->
		<section id="banner">
			<div class="container">
				<p>
					Puede insertar <strong>nuevas camisetas</strong>.<br /> Rellene el
					siguiente formulario.
				</p>
			</div>
		</section>
		<!-- Footer -->
		<section id="footer">
			<div class="container">
				<div class="row">
					<div class="col-12 col-12-medium">
						<section>
							<form method="post" action="insertarCamiseta">
								<div class="row gtr-50">
									<div class="col-6 col-12-small">
										<input name="id" placeholder="ID" type="text" />
									</div>
									<div class="col-6 col-12-small">
										<input name="modelo" placeholder="Modelo" type="text" />
									</div>
									<div class="col-6 col-12-small">
										<input name="talla" placeholder="Talla" type="text" />
									</div>
									<div class="col-6 col-12-small">
										<input name="precio" placeholder="Precio" type="number" />
									</div>									
									<div class="col-12">
										<textarea name="image" placeholder="URL de la imagen"></textarea>
									</div>
									<div class="col-12">
										<button type="submit"
											class="form-button-submit button icon solid fa-envelope">Insertar camiseta</button>
									</div>
									<p>
										${errorCamiseta}
									</p>
								</div>
							</form>
						</section>
					</div>

				</div>
			</div>
			<div id="copyright" class="container">
				<ul class="links">
					<li>DSS. Master Oficial de Ingenieria Informatica</li>
					<li>Alumno: Alvaro de la Flor Bonilla</li>
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