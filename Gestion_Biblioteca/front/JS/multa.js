const url = "http://localhost:8080/api/v1/multa/";

function listarMulta() {
  var capturarFiltro = document.getElementById("inputSearch").value;
  var urlLocal = url;
  if (capturarFiltro !== "") {
    urlLocal += "busquedafiltro/" + capturarFiltro;
  }
  $.ajax({
    url: urlLocal,
    type: "GET",
    success: function(result) {
      console.log("Datos obtenidos del servidor:", result);

      var cuerpoTabla = document.getElementById("cuerpoTabla");
      cuerpoTabla.innerHTML = "";

      for (var i = 0; i < result.length; i++) {
        // Crear elementos
        let trRegistro = document.createElement("tr"); 
        let celdaId = document.createElement("td");
        let celdaUsuario  = document.createElement("td");
        let celdaEstadoPresta  = document.createElement("td");
        let celdaFechaMulta  = document.createElement("td");
        let celdaValorMulta  = document.createElement("td");
        let celdaEstadoMulta  = document.createElement("td");
        
        // Asignar valores
        celdaId.innerText = result[i]["id_multa"];
        celdaEstadoPresta.innerText = result[i]["estado"]["estado"];
        celdaFechaMulta.innerText = result[i]["fecha_multa"];
        celdaValorMulta.innerText = result[i]["valor_multa"];
        celdaUsuario.innerText = result[i]["usuario"]["nombre"];
        celdaEstadoMulta.innerText = result[i]["estado_multa"];
        
        // Añadir celdas a la fila
        trRegistro.appendChild(celdaId);
        trRegistro.appendChild(celdaUsuario);
        trRegistro.appendChild(celdaEstadoPresta);
        trRegistro.appendChild(celdaFechaMulta);
        trRegistro.appendChild(celdaValorMulta);
        trRegistro.appendChild(celdaEstadoMulta);
        
        // Crear y añadir el botón de edición
        let celdaOpcion = document.createElement("td");
        let botonEditarMulta = document.createElement("button");
        botonEditarMulta.value = result[i]["id_multa"];
        botonEditarMulta.innerHTML = '<i class="fas fa-keyboard"></i>';
        botonEditarMulta.className = "btn btn-warning editar-multa me-1";
        botonEditarMulta.onclick = function(e) {
          $('#exampleModal').modal('show');
          consultarMultaID(this.value);
        };
        celdaOpcion.appendChild(botonEditarMulta);
        trRegistro.appendChild(celdaOpcion);
        cuerpoTabla.appendChild(trRegistro);
      }
    },
    error: function(error) {
      console.error("Error en la petición:", error);
      alert("Error en la petición " + error);
    }
  });
}

function registrarMulta() {

  let fecha_multa = document.getElementById("fecha_multa").value;
  let valor_multa = document.getElementById("valor_multa").value;
  let estado=document.getElementById("estado").value;
  let usuario = document.getElementById("usuario").value;
  let estado_multa = document.getElementById("estado_multa").value;

  let formData = {
    estado: estado,
    fecha_multa: fecha_multa,
    valor_multa: valor_multa,
    usuario: usuario,
    estado_multa: estado_multa
  };

  if (validarCampos()) {
    $.ajax({
      url: url,
      type: "POST",
      data: formData,
      success: function (result) {
        Swal.fire({
          position: "center",
          icon: "success",
          title: "¡Se ha registrado correctamente!",
          showConfirmButton: false,
          timer: 1500
        });
      },
      error: function (error) {
        Swal.fire("Alerta", "¡Error al guardar! " + error.responseText, "warning");
      },
    });
  } else {
    Swal.fire("Error", "¡Faltan campos por llenar!", "error");
  }
}

function validarCampos() {
  var usuario = document.getElementById("usuario");
  return validarUsuario(usuario);
}

function validarUsuario(cuadroNumero) {
  var valor = cuadroNumero.value;
  var valido = true;
  if (valor.length < 1 || valor.length > 100) {
    valido = false
  }

  if (valido) {
    cuadroNumero.className = "form-control is-valid";
  } else {
    cuadroNumero.className = "form-control is-invalid";
  }
  return valido;
}


function validarCampos() {
  var estado = document.getElementById("estado");
  return validarEstado(estado);
}

function validarEstado(cuadroNumero) {
  var valor = cuadroNumero.value;
  var valido = true;
  if (valor.length < 1 || valor.length > 100) {
    valido = false
  }

  if (valido) {
    cuadroNumero.className = "form-control is-valid";
  } else {
    cuadroNumero.className = "form-control is-invalid";
  }
  return valido;
}


function validarCampos() {
  var valor_multa = document.getElementById("valor_multa");
  return validarMulta(valor_multa);
}

function validarMulta(cuadroNumero) {
  var valor = cuadroNumero.value;
  var valido = true;
  if (valor.length < 1 || valor.length > 100) {
    valido = false
  }

  if (valido) {
    cuadroNumero.className = "form-control is-valid";
  } else {
    cuadroNumero.className = "form-control is-invalid";
  }
  return valido;
}

function validarCampos() {
  var fecha_multa = document.getElementById("fecha_multa");
  return validarFechaMulta(fecha_multa);
}

function validarFechaMulta(cuadroNumero) {
  var valor = cuadroNumero.value;
  var valido = true;
  if (valor.length < 1 || valor.length > 100) {
    valido = false
  }

  if (valido) {
    cuadroNumero.className = "form-control is-valid";
  } else {
    cuadroNumero.className = "form-control is-invalid";
  }
  return valido;
}

function CargarFormulario() {
  cargarEstado();
  cargarUsuario();
}

function cargarEstado() {
  let urlPrestamo = "http://localhost:8080/api/v1/prestamo/";

  $.ajax({
    url: urlPrestamo,
    type: "GET",
    success: function (result) {
      let estado = document.getElementById("estado");
      estado.innerHTML = "";
      for (let i = 0; i < result.length; i++) {
        let option = document.createElement("option");
        option.value = result[i]["id_prestamo"];
        option.innerText = result[i]["estado"];
        estado.appendChild(option);
      }
    },
    error: function (error) {
      console.error("Error al cargar el estado del préstamo:", error);
    }
  });
}

function cargarUsuario() {
  let urlUsuario = "http://localhost:8080/api/v1/usuario/";

  $.ajax({
    url: urlUsuario,
    type: "GET",
    success: function (result) {
      let usuario = document.getElementById("usuario");
      usuario.innerHTML = "";
      for (let i = 0; i < result.length; i++) {
        let nombreUsuario = document.createElement("option");
        nombreUsuario.value = result[i]["id_usuario"];
        nombreUsuario.innerText = result[i]["nombre"];
        usuario.appendChild(nombreUsuario);
      }
    },
    error: function (error) {
      console.error("Error al cargar los usuarios:", error);
    }
  });
}


function registrarMulta() {
  if (validarCampos()) {
    let formData = {
      estado: document.getElementById("estado").value,
      fecha_multa: document.getElementById("fecha_multa").value,
      valor_multa: document.getElementById("valor_multa").value,
      usuario: document.getElementById("usuario").value,
      estado_multa: document.getElementById("estado_multa").value
    };

    $.ajax({
      url: "http://localhost:8080/api/v1/multa/",
      type: "POST",
      data: formData,
      success: function (result) {
        Swal.fire({
          position: "center",
          icon: "success",
          title: "¡Se ha registrado correctamente!",
          showConfirmButton: false,
          timer: 1500
        });
      },
      error: function (error) {
        Swal.fire("Alerta", "¡Error al guardar! " + error.responseText, "warning");
      },
    });
  } else {
    Swal.fire("Error", "¡Faltan campos por llenar!", "error");
  }
}
function actualizarMulta() {
  var id_multa = document.getElementById("id_multa").value;
  consultarMultaID(id_multa);
  let formData = {
    estado: document.getElementById("estado").value,
    fecha_multa: document.getElementById("fecha_multa").value,
    valor_multa: document.getElementById("valor_multa").value,
    usuario: document.getElementById("usuario").value,
    estado_multa: document.getElementById("estado_multa").value
  };

  if (validarCampos()) {
    $.ajax({
      url: url + id_multa,
      type: "PUT",
      data: formData,
      success: function(result) {
        Swal.fire({
          title: "Excelente",
          text: "Su registro se actualizó correctamente",
          icon: "success"
        });
        
        var modal = document.getElementById("exampleModal"); 
        modal.style.display = "hide";
        
        listarMulta();
      },
      error: function(error) {
        Swal.fire("Error", "Error al guardar", "error");
      }  
    });
  } else {
    Swal.fire({
      title: "Error!",
      text: "complete los campos correctamente",
      icon: "error"
    });
  }
}

function consultarMultaID(id_multa) {
  $.ajax({
    url: url + id_multa,
    type: "GET",
    success: function(result) {
      document.getElementById("id_multa").value = result["id_prestamo"];
      document.getElementById("fecha_multa").value = result["fecha_multa"];
      document.getElementById("valor_multa").value = result["valor_multa"];
      document.getElementById("estado").value = result["estado"]["id_prestamo"];
      document.getElementById("usuario").value = result["usuario"]["id_usuario"];
      document.getElementById("estado_multa").value = result["estado_multa"];
    },
    error: function(error) {
      console.error("Error al consultar la multa por ID:", error);
    }
  });
}
