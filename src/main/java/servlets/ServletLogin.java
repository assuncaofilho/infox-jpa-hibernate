package servlets;

import java.io.IOException;

import dao.DaoFactory;
import dao.UsuarioDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Usuario;



@WebServlet(urlPatterns = {"/principal/ServletLogin", "/ServletLogin"}) /*Mapeamento de URL que vem da tela*/
public class ServletLogin extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();

  
    public ServletLogin() {
    }


    /*Recebe os dados pela url em parametros*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String acao = request.getParameter("acao");
		
		if(acao != null &&  !acao.isEmpty() && acao.equals("logout")) {
			request.getSession().invalidate(); //invalida a sessao
			RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");
			redirecionar.forward(request, response);
		}
		else {
		doPost(request, response);
		}
	}

	
	/*recebe os dados enviados por um formulario*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String url = request.getParameter("url");
		
		try {
		
				if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
					
					Usuario usuario = new Usuario();
					usuario.setLogin(login);
					usuario.setSenha(senha);
					
					if (usuarioDao.validarAutenticacao(usuario)) { /*Simulando login*/
						
						usuario = usuarioDao.consultaUsuario(login);
						request.getSession().setAttribute("usuario", usuario);
						request.getSession().setAttribute("usuariologin", usuario.getLogin());
						
						
						
						if (url == null || url.equals("null")) {
							url = "principal/principal.jsp";
						}
						
						RequestDispatcher redirecionar = request.getRequestDispatcher(url);
						redirecionar.forward(request, response);
						
					}else {
						RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");
						request.setAttribute("msg", "Informe o login e senha corretamente!");
						redirecionar.forward(request, response);
					}
					
				}else {
					RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");
					request.setAttribute("msg", "Informe o login e senha para prosseguir!");
					redirecionar.forward(request, response);
				}
		
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
		
	}

}
