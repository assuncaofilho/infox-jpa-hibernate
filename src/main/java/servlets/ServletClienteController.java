package servlets;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.ClienteDao;
import dao.DaoFactory;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Cliente;


@WebServlet(urlPatterns = {"/ServletClienteController"})
public class ServletClienteController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	
	private ClienteDao clienteDao = DaoFactory.createClienteDao();

    public ServletClienteController() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	try {	
		
		 String acao  = request.getParameter("acao");
		 
		 if (acao != null && !acao.isEmpty() && acao.equals("deletar")) {
			 
			 String idUser = request.getParameter("id");
			 
			 clienteDao.deletar(idUser);
			 
			 request.setAttribute("msg", "Excluido com sucesso!");
			 request.getRequestDispatcher("principal/cliente.jsp").forward(request, response);
		 }
		 else if (acao != null && !acao.isEmpty() && acao.equals("deletarajax")) {
				 
				 String idUser = request.getParameter("id");
				 
				 clienteDao.deletar(idUser);
				 
				 response.getWriter().write("Excluido com sucesso!");
				 
		 }
		 
		 else if (acao != null && !acao.isEmpty() && acao.equals("buscarajax")) {
			 
			 String nomeBusca = request.getParameter("nomeBusca");
			 
			 List<Cliente> dadosJsonUser =  clienteDao.consultaListar(nomeBusca);
			 
			 ObjectMapper mapper = new ObjectMapper();
			 
			 String json = mapper.writeValueAsString(dadosJsonUser);
			 
			 response.getWriter().write(json);
			 
		 }
		 
		 else if (acao != null && !acao.isEmpty() && acao.equals("buscareditar")) {
			
			    String id = request.getParameter("id");
			 
			     Cliente cliente = clienteDao.consultaID(id);
			 
			 
			    request.setAttribute("msg", "Cliente em edição");
				List<Cliente> allCli = clienteDao.listar();
				request.setAttribute("allCli", allCli);
				request.setAttribute("cliente", cliente);
				request.getRequestDispatcher("principal/cliente.jsp").forward(request, response);
		 }
		 
		 else if (acao != null && !acao.isEmpty() && acao.equals("listarClientes")) {
			 
			 List<Cliente> allCli = clienteDao.listar();
			 request.setAttribute("allCli", allCli);
			 request.getRequestDispatcher("principal/cliente.jsp").forward(request, response);
			 
			 
		 }
		 
		 else {
			 request.getRequestDispatcher("principal/cliente.jsp").forward(request, response);
		 }
		 
		
		 
		}catch(	java.sql.SQLIntegrityConstraintViolationException e1) {
			e1.printStackTrace();
			response.getWriter().write("Não é possível excluir este cliente pois ele está vinculado à uma ou mais Ordens de Serviço.");
			
	    }catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("principal/cliente.jsp").forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		String id = request.getParameter("id");
		String nome = request.getParameter("nome");
		String end = request.getParameter("end");
		String fone = request.getParameter("fone");
		String email = request.getParameter("email");
	
		
		Cliente cliente = new Cliente();
		
		try {
		
		cliente.setId(id != null && !id.isEmpty() ? Integer.parseInt(id) : null); // condicao ternária
		cliente.setNome(nome);
		cliente.setEnd(end);
		cliente.setFone(fone);
		cliente.setEmail(email);
		
		
		if (!clienteDao.emailExiste(cliente.getEmail()) && cliente.getId() == null) { // só grava novos clientes;
		//insert
		cliente = clienteDao.gravar(cliente); // grava e retorna o mesmo objeto vindo de uma consulta;
		
		request.setAttribute("msg", "Cliente cadastrado com sucesso!");
		List<Cliente> allCli = clienteDao.listar();
		request.setAttribute("allCli", allCli);
		
		
		}else {
			
			if(clienteDao.emailExiste(cliente.getEmail()) && cliente.getId() != null ||
					!clienteDao.emailExiste(cliente.getEmail()) && cliente.getId() != null) {
			//update
			cliente = clienteDao.gravar(cliente);
			request.setAttribute("msg", "Cliente atualizado com sucesso!");
			List<Cliente> allCli = clienteDao.listar();
			request.setAttribute("allCli", allCli);
	
				
			}else {
				if(clienteDao.emailExiste(email)) {
			
			request.setAttribute("msg", "O email '" + cliente.getEmail() +"' já está em uso! Por favor tente novamente!");
			
				} 
			
			}
		}
		
		request.setAttribute("cliente", cliente);
		List<Cliente> allCli = clienteDao.listar();
		request.setAttribute("allCli", allCli);
		RequestDispatcher redirecionar = request.getRequestDispatcher("principal/cliente.jsp");
		redirecionar.forward(request, response);
		
			}catch(	java.sql.SQLIntegrityConstraintViolationException e1) {
				// CASO O CLIENTE FORNEÇA UM EMAIL PARA EDITAR QUE JÁ ESTEJA NO BANCO, DEVERÁ REDIRECIONAR PARA A MESMA
				  // PÁGINA E CAPTURAR O ERRO DO BANCO, POIS EMAIL É UNIQUE.
				e1.printStackTrace();
				request.setAttribute("cliente", cliente);
				RequestDispatcher redirecionar = request.getRequestDispatcher("principal/cliente.jsp");
				request.setAttribute("msg", "Este email já está em uso! Por favor escolha outro email para o cadastro!");
				redirecionar.forward(request, response);
				
			}
			catch(Exception e) {
				
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("principal/cliente.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);

		}
	}

}