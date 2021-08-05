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
                                                    <div class="card-header">
                                                        <h4>Cadastro de Ordem de Serviço</h4>
                                                        
                                                            
                                                        
                                                    </div>
                                                    <div class="card-block">
                                                        <h4 class="sub-title">Dados da OS</h4>
                                                        
                                                        <form id="formOS" action="<%= request.getContextPath() %>/ServletOsController" onsubmit= "return check_form()" method="post"  accept-charset="utf-8">
                                                        
                                                        	<input type="hidden" name="acao" id="acao" value="">
                                                        	<input type="hidden" name="nomearquivo" id="nomearquivo" value="">
                                                        	<input type="hidden" name="idostoprint" id="idostoprint" value="">
                                                        
                                                            <div class="form-group row">
                                                                <label class="col-sm-2 col-form-label">ID</label>
                                                                <div class="col-sm-10">
                                                                    <input type="text" id="id" name="id" class="form-control" readonly="readonly" required="required"
                                                                    placeholder="ID" value="${os.id}">
                                                                </div>
                                                            </div>
                                                            <div class="form-group row">
                                                                <label class="col-sm-2 col-form-label">Data</label>
                                                                <div class="col-sm-10">
                                                                    <input type="text" id="data" name="data" class="form-control"
                                                                    placeholder="Data" readonly="readonly" required="required" value="${os.data}">
                                                                </div>
                                                            </div>
                                                            <div class="form-group row">
                                                                    <label class="col-sm-2 col-form-label">Tipo</label>
                                                                    <div class="col-sm-10">
                                                                        <select id="tipo" name="tipo" class="form-control" required="required">
                                                                            <option value="opt1">OS</option>
                                                                            <option value="opt2">Orçamento</option>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                           
                                                              <div class="form-group row">
                                                                    <label class="col-sm-2 col-form-label">Situação</label>
                                                                    <div class="col-sm-10">
                                                                        <select id="situacao" name="situacao" class="form-control" required="required">
                                                                            <option value="opt1">Entrega OK</option>
                                                                            <option value="opt2">Aguardando Peças</option>
                                                                            <option value="opt3">Na Bancada</option>
                                                                            <option value="opt4">Aguardando Aprovação</option>
                                                                            <option value="opt5">Cliente não resgatou o produto</option>
                                                   
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            <div class="form-group row">
                                                                <label class="col-sm-2 col-form-label">Equipamento</label>
                                                                <div class="col-sm-10">
                                                                    <input type="text" id="equipamento" name="equipamento" class="form-control"
                                                                    placeholder="Equipamento" required="required" value="${os.equipamento}">
                                                                </div>
                                                            </div>
                                                         
                                                             <div class="form-group row">
                                                                <label class="col-sm-2 col-form-label">Defeito</label>
                                                                <div class="col-sm-10">
                                                                    <input type="text" id="defeito" name="defeito" class="form-control"
                                                                    placeholder="Defeito" required="required" value="${os.defeito}">
                                                                </div>
                                                            </div>
                                                    
                                              
                                                            <div class="form-group row">
                                                                <label class="col-sm-2 col-form-label">Serviço</label>
                                                                    <div class="col-sm-10">
                                                                        <input type="text" id="servico" name="servico" class="form-control"
                                                                        placeholder="Serviço" required="required" value="${os.servico}">
                                                                    </div>
                                                            </div>
                                                            
                                                            <div class="form-group row">
                                                                <label class="col-sm-2 col-form-label">ID Técnico</label>
                                                                <div class="col-sm-10">
                                                                    <input type="text" id="idtecnico" name="idtecnico" class="form-control"
                                                                    placeholder="ID Técnico" required="required" readonly="readonly"  value="${idTec}">
                                                                </div>
                                                            </div>
                                                            
                                                             <div class="form-group row">
                                                                <label class="col-sm-2 col-form-label">Técnico</label>
                                                                <div class="col-sm-10">
                                                                    <input type="text" id="nometecnico" name="nometecnico" class="form-control"
                                                                    placeholder="Técnico" required="required" readonly="readonly"  value="${nomeTec}">
                                                                </div>
                                                            </div>
                                                             <div class="form-group row">
                                                                <label class="col-sm-2 col-form-label">Valor</label>
                                                                <div class="col-sm-10">
                                                                    <input type="number" onchange="setTwoNumberDecimal" id="valor" name="valor" min="0" max="9999999" step="0.01" class="form-control"
                                                                    placeholder="Valor" required="required" value="${os.valor}">
                                                                </div>
                                                            </div>
                                                             <div class="form-group row">
                                                                <label class="col-sm-2 col-form-label">ID Cliente</label>
                                                                <div class="col-sm-10">
                                                                    <input type="text" id="idcliente" name="idcliente" class="form-control"
                                                                    placeholder="ID Cliente" readonly="readonly" required="required" value="${idCli}">
                                                                </div>
                                                            </div>
                                                             <div class="form-group row">
                                                                <label class="col-sm-2 col-form-label">Cliente</label> 
                                                                <div class="col-sm-10" >
                                                                    <input type="text" id="nomecliente" name="nomecliente" class="form-control"
                                                                    placeholder="Cliente" readonly="readonly" required="required" value="${nomeCli}">
                                                                </div>
                                                            </div>
                                                            <button type="button" class="btn btn-primary waves-effect waves-light" onclick="limparNovaOs();">Novo</button>
												            <button type="submit" class="btn btn-success waves-effect waves-light">Salvar</button>
												            <button type="button" class="btn btn-danger waves-effect waves-light" onclick="criarDeleteComAjax();">Excluir</button>
												            <button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#exampleBuscarOS">Buscar OS</button>
												            <button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#exampleBuscarCliente">Buscar Cliente</button>
												            <button type="button" class="btn btn-primary waves-effect waves-light" data-toggle="modal" data-target="#exampleBuscarTecnico">Buscar Técnico</button>
												            <button type="button" class="btn btn-primary waves-effect waves-light" onclick="imprimirOs();">IMPRIMIR OS</button>
                                                            
                                                     </form>
                                                            
                                                     </div>
                                                   </div>
                                                 <!-- Basic Form Inputs card end -->
                                                 <!-- Input Grid card start -->
                                                     
                                                </div>
                                             </div>
                                                
                                         <span id="msg">${msg}</span>


										<div style="height: 300px; overflow: scroll;">
											<table class="table" id="ostab">
												<thead>
													<tr>
														<th scope="col">ID_OS</th>
														<th scope="col">Equipamento</th>
														<th scope="col">Data</th>
														<th scope="col">Ver</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${allOs}" var="os">
													<tr>
													<td><c:out value="${os.id}"></c:out></td>
													<td><c:out value="${os.equipamento}"></c:out></td>
													<td><c:out value="${os.data}"></c:out></td>
													<td><a class="btn btn-success" href="<%= request.getContextPath() %>/ServletOsController?acao=buscareditar&id=${os.id}">Detalhes</a></td>
													</tr>
													</c:forEach>


												</tbody>
											</table>
										</div>


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


<!-- Modal -->
<div class="modal fade" id="exampleBuscarTecnico" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Buscar Técnico </h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
    <div class="modal-body">

		<div class="input-group mb-3">
		  <input type="text" class="form-control" placeholder="Nome do Técnico" aria-label="nome" id="nomeTec" aria-describedby="basic-addon2">
		  <div class="input-group-append">
		    <button class="btn btn-success" type="button" onclick="buscaTecnicoAjax();">Buscar</button>
		  </div>
	</div>
	<div style="height: 300px; overflow: scroll; ">
		<table class="table" id="tbtecResult">
		  <thead>
		    <tr>
		      <th scope="col">ID</th>
		      <th scope="col">Nome</th>
		      <th scope="col" id="ver">Ver</th>
		    </tr>
		  </thead>
		  <tbody>
		  
		  
		  
		  
		  </tbody>
		</table>
	</div>
	</div>
	 <span id="msgResultTec"></span>
      <div class="modal-footer">
        <button type="button" id="close-modal-tecnico" class="btn btn-secondary" data-dismiss="modal">Fechar</button>
      </div>
     
    </div>
  </div>
</div>




<!-- Modal -->
<div class="modal fade" id="exampleBuscarCliente" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" >
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Buscar Cliente por Nome</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
    <div class="modal-body">

		<div class="input-group mb-3">
		  <input type="text" class="form-control" placeholder="nº ID" aria-label="nome" id="nomeCli" aria-describedby="basic-addon2">
		  <div class="input-group-append">
		    <button class="btn btn-success" type="button" onclick="buscaClienteAjax();">Buscar</button>
		    
		  </div>
	</div>
	<div style="height: 300px; overflow: scroll; ">
		<table class="table" id="tbcliResult">
		  <thead>
		    <tr>
		      <th scope="col">ID_CLI</th>
		      <th scope="col">Nome</th>
		      <th scope="col" id="ver">Ver</th>
		    </tr>
		  </thead>
		  <tbody>
		  
		  
		  </tbody>
		</table>
	</div>
	
	</div>
	 <span id="msgResultCli"></span>
      <div class="modal-footer">
        <button type="button" id="close-modal-cliente" class="btn btn-secondary" data-dismiss="modal">Fechar</button>
      </div>
     
    </div>
  </div>
</div>





<!-- Modal -->
<div class="modal fade" id="exampleBuscarOS" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Buscar OS por ID</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
    <div class="modal-body">

		<div class="input-group mb-3">
		  <input type="text" class="form-control" placeholder="nº ID" aria-label="nome" id="idBuscaOS" aria-describedby="basic-addon2">
		  <div class="input-group-append">
		    <button class="btn btn-success" type="button" onclick="buscaOsAjax();">Buscar</button>
		  </div>
	</div>
	<div style="height: 300px; overflow: scroll; ">
		<table class="table" id="tbosResult">
		  <thead>
		    <tr>
		      <th scope="col">ID_OS</th>
		      <th scope="col">Nome</th>
		      <th scope="col" id="ver">Ver</th>
		    </tr>
		  </thead>
		  <tbody>
		  
		  
		  </tbody>
		</table>
	</div>
	</div>
	 <span id="msgResultOs"></span>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fechar</button>
      </div>
     
    </div>
  </div>
</div>

<script type="text/javascript">


window.onload = function () { // chama a função carregaCombos assim que a tela é totalmente carregada!
    carregaCombos(); 
   
}

	
function setTwoNumberDecimal(event){
	this.value = parseFloat(this.value).toFixed(2);
}


function check_form(){
	
	var idtec = document.getElementById("idtecnico").value;
	var nometec = document.getElementById("nometecnico").value;
	var idcli = document.getElementById("idcliente").value;
	var nomecli = document.getElementById("nomecliente").value;
	var arraychecklist = [idtec, nometec , idcli , nomecli]; 
	var count = 0;
	var valid = false;
	
	for(p=0;p<arraychecklist.length;p++){
		if(arraychecklist[p] != null && arraychecklist[p] != "" && arraychecklist[p].trim() != ""){
			count = count + 1;
		}
	}
	
	if(count < arraychecklist.length){
		alert("Busque um técnico e/ou um cliente para completar a operação!");
		valid = false;
		}else{
			if(count = arraychecklist.length){
			valid = true;
			}
		}
		
		return valid;
	}
	



function verEditar(id){
	
	
	var urlAction = document.getElementById("formOS").action;
	
	window.location.href = urlAction + "?acao=buscareditar&id="+id; // usa method GET para redirecionar! Retorna com a resposta da ServletOsController

	
}

function verEditarCliOs(id, nome){
	
	
	document.getElementById("nomecliente").value = nome; 
	
	document.getElementById("idcliente").value = id; 
	
	document.getElementById("close-modal-cliente").click();
	

	
}

function verEditarTecOs(id, nome){
	
	
	
	document.getElementById("close-modal-tecnico").click();
	
	document.getElementById("idtecnico").value = id;
	
	document.getElementById("nometecnico").value = nome; 
	

}

function carregaCombos(){
	
	
	
	
	var comboSituacao = document.getElementById("situacao");
	
	var comboTipo = document.getElementById("tipo");
	
	var sit = '<%=request.getAttribute("cboSituacao")%>';
	
	var tipo =  '<%=request.getAttribute("cboTipo")%>';
	

	
	for (p = 0; p < comboSituacao.length; p++){ 
	
		if( sit == comboSituacao.options[p].text){
			comboSituacao.selectedIndex = p;
			
		}
	}
	
	for (p = 0; p < comboTipo.length; p++){ 
		
		if( tipo == comboTipo.options[p].text){
			comboTipo.selectedIndex = p;
			
		}
	}

}

function criarDelete(){
	
	// por isso se justifica a criação do campo hidden para acao, 
	// pois ao utilizarmos a função delete sem ajax, setamos o campo
	// escondido para o valor deletar e submetemos o form via get; 
	// ao chegar na Servlet, o campo é capturado dentro do doGet pelo
	// comando String id = request.getParameter("id");
	
	if(confirm("Deseja realmente exluir esta OS?")){
		document.getElementById("formOS").method = 'get';
		document.getElementById("acao").value = 'deletar';
		document.getElementById("formOS").submit();
	}
}

function imprimirOs(){
	
	var idOs = document.getElementById("id").value;
	
	if(idOs != null && idOs != "" && idOs.trim() != "" ){
		
		if(confirm("Deseja realmente imprimir esta OS?")){
			document.getElementById("formOS").method = 'get';
			document.getElementById("formOS").target = '_self';
			document.getElementById("acao").value = 'imprimirOs';
			document.getElementById("formOS").action = '<%=request.getContextPath()%>/ServletOsController';
			document.getElementById("idostoprint").value = idOs;
			document.getElementById("nomearquivo").value = 'Os';
			document.getElementById("formOS").submit();

			}
		} else {
			alert("Busque ou cadastre uma OS para imprimir.");
		}
	}

	function criarDeleteComAjax() {

		//var count = 0;

		//for(p=0;p<elementos.legth;p++){
		//	if(elementos[p].value != null && elementos[p].value != "" && elementos[p].trim() != ""){
		//		count = count + 1;
		//	}
		//}

		var idOs = document.getElementById("id").value;

		if (idOs != null && idOs != "" && idOs.trim() != "") {

			if (confirm("Deseja realmente excluir esta OS?")) {

				var urlAction = document.getElementById("formOS").action;
				var id = document.getElementById("id").value;

				$.ajax({

					method : "get",
					url : urlAction,
					data : "id=" + id + "&acao=deletarajax",
					success : function(response) {

						limparNovaOs();
						document.getElementById("msg").textContent = "";
						alert(response);

					}

				}).fail(function(xhr, status, errorThrown) {
					alert("Erro ao excluir OS por ID: " + xhr.responseText);
				});

			}

		} else {
			alert("Busque uma OS para ser excluída!");
		}

	}

	function limparNovaOs() {
		var elementos = document.getElementById("formOS").elements;

		for (p = 0; p < elementos.length; p++) {
			elementos[p].value = "";
		}

		document.getElementById("tipo").selectedIndex = 0; //prevenindo problemas de campo vazio!
		document.getElementById("situacao").selectedIndex = 0;

		document.getElementById("msg").textContent = "";
	}

	function buscaTecnicoAjax() {

		var nomeTec = document.getElementById("nomeTec").value;

		var urlAction = document.getElementById("formOS").action;

		if (nomeTec != null && nomeTec != "" && nomeTec.trim() != "") {

			$
					.ajax(
							{

								method : "get",
								url : urlAction,
								data : "nomeTec=" + nomeTec
										+ "&acao=buscartecnicoajax",
								success : function(response) {

									//alert(response);

									var json = JSON.parse(response);

									//alert(json.id);

									//console.info(json);

									//alert(json[0].nome);

									if (json.id == null) {
										document.getElementById("msgResultTec").textContent = ""
												+ json.length
												+ " resultado(s) para esta busca.";
									}

									$('#tbtecResult > tbody > tr').remove();

									for (var p = 0; p < json.length; p++) {

										$('#tbtecResult > tbody')
												.append(
														'<tr> <td>'
																+ json[p].id
																+ '</td> <td>'
																+ json[p].nome
																+ '</td><td><button type="button" class="btn btn-info" onclick="verEditarTecOs(\''
																+ json[p].id
																+ '\',\''
																+ json[p].nome
																+ '\');">Selecionar</button></td></tr>');

									}
								}

							}).fail(
							function(xhr, status, errorThrown) {
								alert("Erro ao buscar técnico por nome: "
										+ xhr.responseText);
							});

		}
	}

	function buscaOsAjax() {

		var idBuscaOS = document.getElementById("idBuscaOS").value;

		var urlAction = document.getElementById("formOS").action;

		if (idBuscaOS != null && idBuscaOS != "" && idBuscaOS.trim() != "") {

			$
					.ajax(
							{

								method : "get",
								url : urlAction,
								data : "idBuscaOS=" + idBuscaOS
										+ "&acao=buscarajax",
								success : function(response) {

									//alert(response);

									var json = JSON.parse(response);

									//alert(json.id);

									//console.info(json);

									//alert(json[0].nome);

									if (json.id == null) {
										document.getElementById("msgResultOs").textContent = " 0 resultado(s) para esta busca.";
									}

									$('#tbosResult > tbody > tr').remove();

									$('#tbosResult > tbody')
											.append(
													'<tr> <td>'
															+ json.id
															+ '</td> <td>'
															+ json.equipamento
															+ '</td><td><button type="button" class="btn btn-info" onclick="verEditar('
															+ json.id
															+ ');">Selecionar</button></td></tr>');

								}

							}).fail(
							function(xhr, status, errorThrown) {
								alert("Erro ao buscar usuário por nome: "
										+ xhr.responseText);
							});

		}
	}

	function buscaClienteAjax() {

		var nomeCli = document.getElementById("nomeCli").value;

		var urlAction = document.getElementById("formOS").action;

		if (nomeCli != null && nomeCli != "" && nomeCli.trim() != "") {

			$
					.ajax(
							{

								method : "get",
								url : urlAction,
								data : "nomeCli=" + nomeCli
										+ "&acao=buscarclienteajax",
								success : function(response) {

									//alert(response);

									var json = JSON.parse(response);

									//alert(json.id);

									//console.info(json);

									//alert(json[0].nome);

									document.getElementById("msgResultCli").textContent = " "
											+ json.length
											+ " resultado(s) para esta busca.";

									$('#tbcliResult > tbody > tr').remove();

									for (p = 0; p < json.length; p++) {

										$('#tbcliResult > tbody')
												.append(
														'<tr> <td>'
																+ json[p].id
																+ '</td> <td>'
																+ json[p].nome
																+ '</td><td><button type="button" class="btn btn-info" onclick="verEditarCliOs(\''
																+ json[p].id
																+ '\',\''
																+ json[p].nome
																+ '\');">Selecionar</button></td></tr>');
									}

								}

							}).fail(
							function(xhr, status, errorThrown) {
								alert("Erro ao buscar cliente por nome: "
										+ xhr.responseText);
							});

		}
	}
</script>


</body>

</html>
    