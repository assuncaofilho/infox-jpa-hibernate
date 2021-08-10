package servlets;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import com.fasterxml.jackson.databind.ObjectMapper;

import connection.ConexaoUtil;
import dao.ClienteDao;
import dao.DaoFactory;
import dao.OsDao;
import dao.UsuarioDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Cliente;
import model.Os;
import model.Usuario;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;


@WebServlet(urlPatterns = {"/ServletOsController"})
public class ServletOsController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	
	private OsDao osDao = DaoFactory.createOsDao();
	private ClienteDao clienteDao = DaoFactory.createClienteDao();
	private UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();
	
	
	
	private String dataOsFormatada(String dataOs) {
	     
		try {
	     Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dataOs);
	     
	     String dataOsFormatted = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date);
	     
	     return dataOsFormatted;
	     
		}catch(Exception e) {
			throw new RuntimeException();
		}
		
	}
	
	
	
	/*private List<Os> atualizarOs() {

		try {

			List<Os> allOs = osDao.listar();

			List<Os> osFormatada = new ArrayList<Os>();

			for (int i = 0; i < allOs.size(); i++) {

				String dataBanco = allOs.get(i).getData();

				String dataFormatada = dataOsFormatada(dataBanco);

				allOs.get(i).setData(dataFormatada);

				osFormatada.add(allOs.get(i));
			}

			return osFormatada;
			
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}*/

    public ServletOsController() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	try {	
		
		 String acao  = request.getParameter("acao");
		 
		 if (acao != null && !acao.isEmpty() && acao.equals("deletar")) {
			 
			 String id = request.getParameter("id");
			 
			 osDao.excluir(id);
			 
			 request.setAttribute("msg", "OS excluída com sucesso!");
			 request.getRequestDispatcher("principal/os.jsp").forward(request, response);
		 }
		 else if (acao != null && !acao.isEmpty() && acao.equals("deletarajax")) {
				 
				 String id = request.getParameter("id");
				 
				 
				 osDao.excluir(id);
				 
				 
				 response.getWriter().write(" Os excluída com sucesso!");
				 
		 }
		 
		 else if (acao != null && !acao.isEmpty() && acao.equals("buscarajax")) {
			 
			 String id = request.getParameter("idBuscaOS");
			 
			 Os jsonOs =  osDao.pesquisar(id);
			 
			 ObjectMapper mapper = new ObjectMapper();
			 
			 String json = mapper.writeValueAsString(jsonOs);
			 
			 response.getWriter().write(json);
			 
		 }
		 
		 else if (acao != null && !acao.isEmpty() && acao.equals("buscareditar")) {
			
			    String id = request.getParameter("id");
			 
			     Os os = osDao.pesquisar(id);
			     
			     os.setData(dataOsFormatada(os.getData()));
			     
			     Cliente cliente = clienteDao.consultaID(os.getIdcli().toString());
			     
			     Usuario tecAtual = usuarioDao.consultaUsuarioID(os.getIdtec().toString());
			     
			    List<Os> allOs = osDao.listar();
			    request.setAttribute("allOs", allOs);
			 
			    request.setAttribute("msg", "OS em edição");
				request.setAttribute("os", os);
				request.setAttribute("idTec", os.getIdtec());
				request.setAttribute("nomeTec", tecAtual.getNome());
				request.setAttribute("cboTipo", os.getTipo());
				request.setAttribute("cboSituacao", os.getSituacao());
				request.setAttribute("nomeCli", cliente.getNome());
				request.setAttribute("idCli", cliente.getId());
				
				request.getRequestDispatcher("principal/os.jsp").forward(request, response);
				
				
		 }else if (acao != null && !acao.isEmpty() && acao.equals("listarOs")) {
			 	
			 	List<Os> allOs = osDao.listar();
			 	request.setAttribute("allOs", allOs);
			 	request.getRequestDispatcher("principal/os.jsp").forward(request, response);
			 
			 
		 }
		 
		 else if (acao != null && !acao.isEmpty() && acao.equals("buscartecnicoajax")) {
				
				 String nomeTec = request.getParameter("nomeTec");
				 
				 List<Usuario> listaUsuario =  usuarioDao.consultaUsuarioList(nomeTec);
				 
				 ObjectMapper mapper = new ObjectMapper();
				 
				 String json = mapper.writeValueAsString(listaUsuario);
				 
				 response.getWriter().write(json);
		 }
		 
		 
		 
		 else if (acao != null && !acao.isEmpty() && acao.equals("buscarclienteajax")) {
				
			    String nomeCli = request.getParameter("nomeCli");
			     
			     List<Cliente> clienteJSon = clienteDao.consultaListar(nomeCli);
				 
				 ObjectMapper mapper = new ObjectMapper();
				 
				 String json = mapper.writeValueAsString(clienteJSon);
				 
				 response.getWriter().write(json);
		 }
		 else if (acao != null && !acao.isEmpty() && acao.equals("imprimirOs")) {
				
				
				
				String nomearquivo = request.getParameter("nomearquivo");
			
				String erro = "";
				
				/*Caminho relativo do arquivo jasper*/
				String caminhoJasper = "WEB-INF/report/"+nomearquivo+".jasper";
				
				/*caminho relativo do arquivo de img do ícone*/
				String caminhoIcone = "WEB-INF/icons/X.png";
				 
				
				byte[] bytes = null;
				
				ServletContext context = getServletContext();
				
				//String caminhoAbsoluto = context.getRealPath(caminhoIcone);
				//String caminhoRequest = request.getContextPath();
				
				try {
					JasperReport relatorio = (JasperReport) JRLoader.loadObjectFromFile(
							context.getRealPath(caminhoJasper));
					
	
						
			
					InputStream is = new FileInputStream(context.getRealPath(caminhoIcone));
					
					BufferedImage image = ImageIO.read(is);
					
					HashMap<String, Object> param = new HashMap<String, Object>();
					param.put("os", request.getParameter("idostoprint"));
					param.put("logo", image );
					
					
					bytes = JasperRunManager.runReportToPdf(relatorio, param, ConexaoUtil.getConnection());
					
					
				} catch (JRException e) {
					e.printStackTrace();
					erro = e.getMessage();
				} finally {

					if (bytes != null) {
						response.setContentType("application/pdf");
						response.setContentLength(bytes.length);

						ServletOutputStream sos = response.getOutputStream();
						sos.write(bytes);
						sos.flush();
						sos.close();
						
					} else {
						
							RequestDispatcher redireciona = request.getRequestDispatcher("principal/os.jsp");
							request.setAttribute("msg", erro);
							redireciona.forward(request, response);
							
						}
					}
				}

		 
		 else {
			 List<Os> allOs = osDao.listar();
		     request.setAttribute("allOs", allOs);
			 request.getRequestDispatcher("principal/os.jsp").forward(request, response);
		 }
		 
		
		 
		 
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String id = request.getParameter("id");
		String data = request.getParameter("data");
		String tipo = request.getParameter("tipo");
		String situacao = request.getParameter("situacao");
		String equipamento = request.getParameter("equipamento");
		String defeito = request.getParameter("defeito");
		String servico = request.getParameter("servico");
		Double valor = Double.parseDouble(request.getParameter("valor"));
		String idcliente = request.getParameter("idcliente");
		String idtecnico = request.getParameter("idtecnico");
		String tecnico = request.getParameter("nometecnico");
	
		
		Os os = new Os();
		
		try {
		
		os.setId(id != null && !id.isEmpty() ? Integer.parseInt(id) : null); // condicao ternária
		os.setData(data != null && !data.isEmpty() ? data : null);
		
		switch (tipo) {
		case "opt1":
			os.setTipo("OS");
			break;
		case "opt2":
			os.setTipo("Orçamento");
			break;	
		}
		
		 switch (situacao) {
		    case "opt1":
		      os.setSituacao("Entrega OK");
		      break;
		    case "opt2":
			      os.setSituacao("Aguardando Peças");
			      break;
		    case "opt3":
			      os.setSituacao("Na Bancada");
			      break;
		    case "opt4":
			      os.setSituacao("Aguardando Aprovação");
			      break;
		    case "opt5":
			      os.setSituacao("Cliente não resgatou o produto");
			      break;
		 }
		
		os.setEquipamento(equipamento);
		os.setDefeito(defeito);
		os.setServico(servico);		
		os.setValor(valor);
		os.setIdcli(Integer.parseInt(idcliente));
		os.setIdtec(Integer.parseInt(idtecnico));
		
		
		if (os.getId() == null) { // nova os e cliente carregado na tela
		//insert
		os = osDao.cadastrar(os); // grava e retorna o mesmo objeto vindo de uma consulta;
		
		request.setAttribute("msg", "OS cadastrada com sucesso!");
		
		os.setData(dataOsFormatada(os.getData()));
		
		request.setAttribute("os", os);
		request.setAttribute("nomeTec", tecnico);
		request.setAttribute("idTec", os.getIdtec());
		request.setAttribute("idCli", os.getIdcli());
		request.setAttribute("nomeCli", clienteDao.consultaID(os.getIdcli().toString()).getNome());
		request.setAttribute("cboTipo", os.getTipo());
		request.setAttribute("cboSituacao", os.getSituacao());
		List<Os> allOs = osDao.listar();
	    request.setAttribute("allOs", allOs);
		RequestDispatcher redirecionar = request.getRequestDispatcher("principal/os.jsp");
		redirecionar.forward(request, response);
		
		
		}else {
			
			if(os.getId() != null) {
			//update
			os = osDao.cadastrar(os);
			request.setAttribute("msg", "OS atualizada com sucesso!");
			
			os.setData(dataOsFormatada(os.getData()));
			
			request.setAttribute("os", os);
			request.setAttribute("nomeTec", tecnico);
			request.setAttribute("idTec", os.getIdtec());
			request.setAttribute("idCli", os.getIdcli());
			request.setAttribute("nomeCli", clienteDao.consultaID(os.getIdcli().toString()).getNome());
			request.setAttribute("cboTipo", os.getTipo());
			request.setAttribute("cboSituacao", os.getSituacao());
			List<Os> allOs = osDao.listar();
		    request.setAttribute("allOs", allOs);
			RequestDispatcher redirecionar = request.getRequestDispatcher("principal/os.jsp");
			redirecionar.forward(request, response);
	
				
			}else {
				if(os.getId() == null && os.getIdcli() == null && os.getIdtec() == null) {
			
			request.setAttribute("msg", "Vincule um cliente e/ou técnico para cadastrar a OS!");
			
					} 
				
				}
			}
			
			
				
			}
			catch(Exception e) {
				
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("principal/os.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);

		}
	}

}