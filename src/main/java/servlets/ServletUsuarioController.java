package servlets;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;


import dao.DaoFactory;
import dao.UsuarioDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Usuario;

@WebServlet(urlPatterns = {"/ServletUsuarioController"})
public class ServletUsuarioController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	
	private UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();

    public ServletUsuarioController() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	try {	
		
		 String acao  = request.getParameter("acao");
		 
		 if (acao != null && !acao.isEmpty() && acao.equals("deletar")) {
			 
			 String idUser = request.getParameter("id");
			 
			 usuarioDao.deletarUser(idUser);
			 
			 request.setAttribute("msg", "Excluido com sucesso!");
			 request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
		 }
		 else if (acao != null && !acao.isEmpty() && acao.equals("deletarajax")) {
				 
				 String idUser = request.getParameter("id");
				 
				 usuarioDao.deletarUser(idUser);
				 
				 response.getWriter().write("Excluido com sucesso!");
				 
		 }
		 
		 else if (acao != null && !acao.isEmpty() && acao.equals("buscarajax")) {
			 
			 String nomeBusca = request.getParameter("nomeBusca");
			 
			 List<Usuario> dadosJsonUser =  usuarioDao.consultaUsuarioList(nomeBusca);
			 
			 ObjectMapper mapper = new ObjectMapper();
			 
			 String json = mapper.writeValueAsString(dadosJsonUser);
			 
			 response.getWriter().write(json);
			 
		 }
		 
		 else if (acao != null && !acao.isEmpty() && acao.equals("buscareditar")) {
			
			    String id = request.getParameter("id");
			 
			     Usuario usuario = usuarioDao.consultaUsuarioID(id);
			 
			 
			    request.setAttribute("msg", "Usuário em edição");
				request.setAttribute("usuario", usuario);
			 	List<Usuario> allUsu = usuarioDao.listar();
			 	request.setAttribute("allUsu", allUsu);
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
		 }
		 else if (acao != null && !acao.isEmpty() && acao.equals("listarUsuarios")) {
			 
			 	List<Usuario> allUsu = usuarioDao.listar();
			 	request.setAttribute("allUsu", allUsu);
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
		 }
		 
				 
			 
		 else {
			 request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
		 }
		 
		}catch(	java.sql.SQLIntegrityConstraintViolationException e1) {
			e1.printStackTrace();
			response.getWriter().write("Não é possível excluir este usuário pois ele está vinculado à uma ou mais Ordens de Serviço.");
			
	    }catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("principal/cliente.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		String id = request.getParameter("id");
		String nome = request.getParameter("nome");
		String telefone = request.getParameter("telefone");
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String perfil = request.getParameter("perfil");
	
		
		Usuario usuario = new Usuario();
		
		try {
			
		usuario.setId(id != null && !id.isEmpty() ? Integer.parseInt(id) : null); // condicao ternária
		usuario.setNome(nome);
		usuario.setFone(telefone);
		usuario.setLogin(login);
		usuario.setSenha(senha);
		usuario.setPerfil(perfil);
		
		
		
		if (!usuarioDao.loginExiste(usuario.getLogin()) && usuario.getId() == null) { // só grava novos usuarios;
		//insert
		usuario = usuarioDao.gravarUsuario(usuario); // grava e retorna o mesmo objeto vindo de uma consulta;
		
		request.setAttribute("msg", "Usuário cadastrado com sucesso!");
	 	List<Usuario> allUsu = usuarioDao.listar();
	 	request.setAttribute("allUsu", allUsu);
		
		
		}else {
			
			if(usuarioDao.loginExiste(usuario.getLogin()) && usuario.getId() != null || 
					!usuarioDao.loginExiste(usuario.getLogin()) && usuario.getId() != null) {
			//update
			usuario = usuarioDao.gravarUsuario(usuario);
			request.setAttribute("msg", "Usuário atualizado com sucesso!");
		 	List<Usuario> allUsu = usuarioDao.listar();
		 	request.setAttribute("allUsu", allUsu);
	
				
			}else {
				if(usuarioDao.loginExiste(login)) {
			
			request.setAttribute("msg", "O login '" + usuario.getLogin() +"' já está em uso! Por favor tente novamente!");
				}
			
			}
		}
		
		request.setAttribute("usuario", usuario);
	 	List<Usuario> allUsu = usuarioDao.listar();
	 	request.setAttribute("allUsu", allUsu);
		RequestDispatcher redirecionar = request.getRequestDispatcher("principal/usuario.jsp");
		redirecionar.forward(request, response);
		
		}catch(	java.sql.SQLIntegrityConstraintViolationException e1) {
			// CASO O ADM FORNEÇA UM LOGIN PARA EDITAR QUE JÁ ESTEJA NO BANCO, DEVERÁ REDIRECIONAR PARA A MESMA
			  // PÁGINA E CAPTURAR O ERRO DO BANCO, POIS LOGIN É UNIQUE.
			e1.printStackTrace();
			request.setAttribute("usuario", usuario);
			RequestDispatcher redirecionar = request.getRequestDispatcher("principal/usuario.jsp");
			request.setAttribute("msg", "Este login já está em uso! Por favor escolha outro login para o cadastro!");
			redirecionar.forward(request, response);

		
			}catch(Exception e) {
				
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);

		}
	}


}