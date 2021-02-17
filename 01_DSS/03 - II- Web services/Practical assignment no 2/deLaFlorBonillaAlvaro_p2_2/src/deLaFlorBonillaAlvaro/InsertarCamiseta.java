package deLaFlorBonillaAlvaro;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Camiseta;

/**
 * Servlet implementation class InsertarCamiseta
 */
@WebServlet("/insertarCamiseta")
public class InsertarCamiseta extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertarCamiseta() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean exception = false;
		try {
			String id = request.getParameter("id");
			String modelo = request.getParameter("modelo");
			String talla = request.getParameter("talla");
			Double precio = Double.parseDouble(request.getParameter("precio"));
			Boolean vendida = false;
			String image = request.getParameter("image");
			Boolean check = id != null && modelo != null && talla != null && precio != null && vendida != null && image != null;
			if (check) {
				exception = Probador.sendJsonPost("http://localhost:8080/deLaFlorBonillaAlvaro/api/camiseta", "{\r\n"
						+ "\"id\":\"" + id + "\",\r\n"
						+ "\"modelo\":\"" + modelo + "\",\r\n"
						+ "\"talla\":\"" + talla+ "\",\r\n"
						+ "\"precio\":" + precio + ",\r\n"
						+ "\"vendida\":false,\r\n"
						+ "\"image\":\"" + image + "\"\r\n"
						+ "}");
			} else {
				exception = true;
			}
		} catch (Exception e) {
			exception = true;
		}
		if (exception) {
			request.setAttribute("errorCamiseta", "Se ha producido un error insertando la camiseta");
		} else {
			request.setAttribute("errorCamiseta", "Se ha insertado la camiseta");
		}
		List<Camiseta> camisetas = Probador.getCamisetas();
		request.setAttribute("camisetas", camisetas);
		request.getRequestDispatcher("admin.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
