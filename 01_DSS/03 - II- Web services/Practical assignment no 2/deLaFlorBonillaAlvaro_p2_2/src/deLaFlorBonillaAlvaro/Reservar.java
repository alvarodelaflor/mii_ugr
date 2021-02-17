package deLaFlorBonillaAlvaro;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import model.Carrito;

/**
 * Servlet implementation class Reservar
 */
@WebServlet("/reservar")
public class Reservar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Reservar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String modoAux = request.getParameter("modo");
		Boolean modo = Boolean.parseBoolean(request.getParameter("modo"));
		String idUsuario = request.getParameter("idUsuario");
		String idCamiseta = request.getParameter("idCamiseta");
		ClientConfig config = new DefaultClientConfig();
		Client cliente = Client.create(config);
		WebResource servicio = cliente.resource(Probador.getBaseURI());
		if (modo) {
			String preJson = servicio.path("api")
					.path("carrito/" + idUsuario + "$carrito/camiseta/" + idCamiseta)
					.accept(MediaType.APPLICATION_JSON)
					.get(String.class);
		} else if (!modo) {
			String preJson = servicio.path("api")
					.path("carrito/" + idUsuario + "$carrito/camiseta/" + idCamiseta)
					.accept(MediaType.APPLICATION_JSON)
					.delete(String.class);
		}
		Carrito carrito = Probador.obtenerCarritoPorId(idUsuario + "$carrito");
		request.setAttribute("carrito", carrito);
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
