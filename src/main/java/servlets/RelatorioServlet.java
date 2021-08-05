package servlets;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;

import connection.ConexaoUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Servlet implementation class RelatorioServlet
 */
@WebServlet("/RelatorioServlet")
public class RelatorioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	   

    public RelatorioServlet() {
        super();
   
    }


	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		
		geraRelatorio(request, response);
		
	
	}


	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			
		geraRelatorio(request, response);
		
	}
	
	protected void geraRelatorio(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			
			try {
				
			
			
			String acao = request.getParameter("acao");
			String nomearquivo = request.getParameter("nomearquivo");
		
			String erro = "";
			/*Caso o report necessite de parâmetros*/
			//String empresa = request.getParameter("empresa"); // vêm do name (post) ou da URL (get);
			
			/*Caminho relativo do arquivo jasper*/
			String caminhoJasper = "WEB-INF/report/"+nomearquivo+".jasper";
			
			/*caminho relativo do arquivo de img do ícone*/
			String caminhoIcone = "WEB-INF/icons/X.png";
			 
			//HashMap<String, Object> param = new HashMap<String, Object>();
			//param.put("EMPRESA", empresa);
			
			byte[] bytes = null;
			
			ServletContext context = getServletContext();
			
			try {
				JasperReport relatorio = (JasperReport) JRLoader.loadObjectFromFile(
						context.getRealPath(caminhoJasper));
				
				if(!acao.equals("imprimirOs")) {
				
				bytes = JasperRunManager.runReportToPdf(relatorio, null, ConexaoUtil.getConnection());
				
				}else if(acao.equals("imprimirOs")) {
					
		
				InputStream is = new FileInputStream(context.getRealPath(caminhoIcone));
				
				BufferedImage image = ImageIO.read(is);
				
				//File sourceimage = new File("source.gif");
		        //image = ImageIO.read(sourceimage);
				
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("os", request.getParameter("idostoprint"));
				param.put("logo", image );
				
				
				bytes = JasperRunManager.runReportToPdf(relatorio, param, ConexaoUtil.getConnection());
					
				}
				
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
					if(!acao.equals("imprimirOs")) {
					RequestDispatcher redireciona = request.getRequestDispatcher("principal/relatorios.jsp");
					request.setAttribute("msg", erro);
					redireciona.forward(request, response);
					
					}else if(acao.equals("imprimirOs")) {
						RequestDispatcher redireciona = request.getRequestDispatcher("principal/os.jsp");
						request.setAttribute("msg", erro);
						redireciona.forward(request, response);
						
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redireciona = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redireciona.forward(request, response);
		}

	}
}