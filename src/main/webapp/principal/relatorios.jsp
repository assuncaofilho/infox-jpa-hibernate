<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
    
    
<!DOCTYPE html>
<html lang="en">


<jsp:include page="head.jsp"></jsp:include>

  <body>
  <!-- Pre-loader start -->
  
  <jsp:include page="theme-loader.jsp"></jsp:include>
  
  <!-- Pre-loader end -->
  <div id="pcoded" class="pcoded">
      <div class="pcoded-overlay-box"></div>
      <div class="pcoded-container navbar-wrapper">
          
          <jsp:include page="navbar.jsp"></jsp:include>

          <div class="pcoded-main-container">
              <div class="pcoded-wrapper">
                  
                  <jsp:include page="navbarmainmenu.jsp"></jsp:include>
                  
                  <div class="pcoded-content">
                      <!-- Page-header start -->
                      
                      <jsp:include page="page-header.jsp"></jsp:include>
                      
                      <!-- Page-header end -->
                        <div class="pcoded-inner-content">
                            <!-- Main-body start -->
                            <div class="main-body">
                                <div class="page-wrapper">
                                    <!-- Page-body start -->
                                    <div class="page-body">
                                              
                                           <div class="row">
                                            <div class="col-sm-12">
                                                <!-- Basic Form Inputs card start -->
                                                <div class="card">
                                                    <div class="card-block">
                                                        <h4 class="sub-title">Relatórios InfoX</h4>
		                                              
          												 <form class="form-material" action="<%= request.getContextPath() %>/RelatorioServlet" method="post" id="formRel" accept-charset="utf-8" target="_self">
          												 
          												 	<input type="hidden" name="nomearquivo" id="nomearquivo" value="">
          												 	<input type="hidden" name="acao" id="acao" value="">
          												 
                                                                     
                                                           	<button type="button" class="btn btn-primary waves-effect waves-light" onclick="gerarRelatorioClientes();">Rel. de Clientes</button>
												            <button type="button" class="btn btn-success waves-effect waves-light" onclick="gerarRelatorioServicos()">Rel. de Serviços</button>
												            
                                                        </form> 
                                                   
                                                </div>
                                                </div>
                                                </div>
                                                </div>
                                                
                                                <span id="msg">${msg}</span>
                                                
               
                                                
                                    </div>
                                    <!-- Page-body end -->
                                </div>
                                <div id="styleSelector"> </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
   
   
<jsp:include page="javascripfile.jsp"></jsp:include>


<script type="text/javascript">



function gerarRelatorioClientes(){
	
	
		document.getElementById("formRel").method = 'get';
		document.getElementById("nomearquivo").value = 'Clientes';
		document.getElementById("acao").value = 'relcli';
		document.getElementById("formRel").submit();
	
}

function gerarRelatorioServicos(){
	
	
	document.getElementById("formRel").method = 'get';
	document.getElementById("nomearquivo").value = 'Servicos';
	document.getElementById("acao").value = 'relserv';
	document.getElementById("formRel").submit();

}




</script>


</body>

</html>
    