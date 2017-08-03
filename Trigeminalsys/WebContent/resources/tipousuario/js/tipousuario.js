TRIGEMINAL.tipousuario = new Object();

$(document).ready(function() {
			TRIGEMINAL.tipousuario.cadastrar = function() {

				// Validações formulário
				if (document.getElementById("descricao").value == "") {
					alert("Todos os campos são obrigatórios de preenchimento!");
					return false;
				}else{
					
					var newCont = new Object();
					newCont.cod_tipo_usuario = $("#idTipoUsuario").val();
					newCont.tu_descricao = $("#descricao").val();

					var cfg = {

							url : "rest/tipoUsuarioRest/addTipoUsuario",
							data : newCont,
							success : function(msg) {

								var cfg = {
										title : "Mensagem",
										height : 250,
										width : 400,
										modal : true,
										buttons : {
											"Ok" : function() {
												$(this).dialog("close");
											}
										}
								};

								$("#msg").html(msg);
								$("#msg").dialog(cfg);

								TRIGEMINAL.tipousuario.buscar();
							},
							error : function(err) {
								alert("Erro ao cadastrar um novo contato'': "
										+ err.responseText);
							}
					}; 

					TRIGEMINAL.ajax.post(cfg);
				}// Fecha o else
			};// Fecha a funcao TRIGEMINAL.tipousuario.cadastrar()


			TRIGEMINAL.tipousuario.buscar = function() {
				var valorBusca = $("#consultarTipoUsuario").val();
				TRIGEMINAL.tipousuario.exibirTipoUsuarios(undefined, valorBusca);
			};// Fecha método TRIGEMINAL.tipousuario.buscar()
		

			TRIGEMINAL.tipousuario.exibirTipoUsuarios = function(listaDeTipoUsuarios, valorBusca){

				var html = "<table class='table'>";
				html += "<tr><th>Descricao</th></tr>";
				if (listaDeTipoUsuarios != undefined && listaDeTipoUsuarios.length > 0 && listaDeTipoUsuarios[0].cod_tipo_usuario != undefined) {
					for (var i = 0; i < listaDeTipoUsuarios.length; i++) {
						html += "<tr>"
							+ "<td>"
							+ listaDeTipoUsuarios[i].tu_descricao
							+ "</td>"
							+ "<td>"
							+ "<a class='link' onclick='TRIGEMINAL.tipousuario.editarTipoUsuario("+listaDeTipoUsuarios[i].cod_tipo_usuario + ")'>Editar</a>"
							+ "<a class='link' onclick='TRIGEMINAL.tipousuario.deletarTipoUsuario("+ listaDeTipoUsuarios[i].cod_tipo_usuario + ")'>Deletar</a>" + "</td>" + "</tr>";
					}
				} else {
					if (listaDeTipoUsuarios == undefined || (listaDeTipoUsuarios != undefined && listaDeTipoUsuarios.length > 0)) {
						if (valorBusca == "") {
							valorBusca = null;
						}

						var cfg = {
								type : "POST",
								url : "rest/tipoUsuarioRest/buscarTipoUsuariosPorDescricao/"
									+ valorBusca,
									success : function(listaDeTipoUsuarios) {
										TRIGEMINAL.tipousuario
										.exibirTipoUsuarios(listaDeTipoUsuarios);
									},
									error : function(err) {
										alert("Erro ao consultar os tipos de usuarios: "
												+ err.responseText);
									}
						};

						TRIGEMINAL.ajax.post(cfg);

					} else {
						html += "<tr><td colspan='3'>Nenhum registro encontrado</td></tr>";
					}
				}
				html += "</table>";
				console.log(html);
				$("#resultadoTipoUsuarios").html(html);
			};// Fecha a declaração do método exibirTipoUsuarios()

			TRIGEMINAL.tipousuario.exibirTipoUsuarios(undefined, "");
			
			TRIGEMINAL.tipousuario.confirmarExclusao = function(cod_tipo_usuario){
				var cfg = {
						title : "Comfirmar exclusão",
						height : 250,
						width : 400,
						modal : true,
						buttons : {
							"Ok" : function() {
								TRIGEMINAL.tipousuario.deletarTipoUsuario(cod_tipo_usuario);
								$(this).dialog("close");
							},
						"Cancelar" : function() {
					$(this).dialog("close");
							}	
						}	 	
						
				};
				$("#msg").html("Deseja excluir este Tipo de Usuario?");
				$("#msg").dialog(cfg);
				
			}

			TRIGEMINAL.tipousuario.deletarTipoUsuario = function(cod_tipo_usuario) {
				
				console.log("DELETANDO - " + cod_tipo_usuario);


				var cfg = {
						type : "POST",
						url : "rest/tipoUsuarioRest/deletarTipoUsuario/" +cod_tipo_usuario,
						success : function(msg) {
							var cfg = {
									title : "Mensagem",
									height : 250,
									width : 400,
									modal : true,
									buttons : {
										"Ok" : function() {
											$(this).dialog("close");
										}
									}
							};
							$("#msg").html(msg);
							$("#msg").dialog(cfg);

							TRIGEMINAL.tipousuario.buscar();
						},
						error : function(err) {

							alert("Erro ao deletar o contato: "
									+ err.responseText);
						}
				};

				TRIGEMINAL.ajax.post(cfg);
			}

			TRIGEMINAL.tipousuario.editarTipoUsuario = function(cod_tipo_usuario){
				console.log(cod_tipo_usuario);
				
				var cfg = {
						type:"POST",
						url: "rest/tipoUsuarioRest/buscarTipoUsuarioPeloCodigo/"+cod_tipo_usuario,
						success:function (tipousuario){
							$("#descricaoEdit").val(tipousuario.tu_descricao);
							$("#idTipoUsuarioEdit").val(tipousuario.cod_tipo_usuario);

							TRIGEMINAL.tipousuario.exibirEdicao(tipousuario);
						},
						error: function (err){
							alert("Erro ao editar Tipo de Usuario: " + err.responseText);
						}
				}
				TRIGEMINAL.ajax.post(cfg);
			} 
			TRIGEMINAL.tipousuario.exibirEdicao = function(tipousuario){

				var cfg = {
						title: "Editar Tipo de Usuario",
						height: 400,
						width: 550,
						modal: true,
						buttons: {
							"Salvar": function() {
								var dialog = this;
								var newTipoUsuario = new Object();
								newTipoUsuario.cod_tipo_usuario = $("#idTipoUsuarioEdit").val();
								newTipoUsuario.tu_descricao = $("#descricaoEdit").val();

								console.log("EXIBIR EDICAO -- " + newTipoUsuario.cod_tipo_usuario);

								var cfg = {
										type: "POST",
										url: "rest/tipoUsuarioRest/editarTipoUsuario",
										data: newTipoUsuario,
										success: function (data) {
											$( dialog ).dialog("close");
											// zera o campo da senha
	

											TRIGEMINAL.tipousuario.buscar();// Atualiza a tabela dos
									// contatos
										},
										error: function (err) {
											alert("Erro ao editar o TIpo Usuario: " + err.responseText);
										}
								};
								TRIGEMINAL.ajax.post(cfg);


							},
							"Cancelar" : function() {
								$(this).dialog("close");
							}
						},
						close : function() {
						}
				};
				$("#editarTipoUsuario").dialog(cfg);
			};
		});// Fecha documento ready
