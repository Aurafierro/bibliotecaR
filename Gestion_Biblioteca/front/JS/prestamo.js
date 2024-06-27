var url = "http://localhost:8080/api/v1/prestamo/";
var consultaUsuarioFinalizada = false;



function listarPrestamo() {
  //METODO PARA LISTAR LOS CLIENTES
  //SE CREA LA PETICION AJAX
  var capturarFiltro = document.getElementById("inputSearch").value;
  var urlLocal = url;
  if (capturarFiltro != "") {
    urlLocal += "busquedafiltro/" + capturarFiltro;
  }
  $.ajax({
    url: urlLocal,
    type: "GET",
    success: function (result) {
      //success: funcion que se ejecuta
      //cuando la peticion tiene exito
      console.log(result);

      var cuerpoTabla = document.getElementById("cuerpoTabla");
      //Se limpia el cuepro de la tabla
      cuerpoTabla.innerHTML = "";
      //se hace un ciclo que recorra l arreglo con los datos
      for (var i = 0; i < result.length; i++) {
        //UNA ETIQUETA tr por cada registro
        var trResgistro = document.createElement("tr");

        var celdaId = document.createElement("td");
        let celdaIdLibro = document.createElement("td")
        let celdaIdUsuario = document.createElement("td")
        let celdaFechaPrestamo = document.createElement("td")
        let celdaFechaDevolucion = document.createElement("td")

        let celdaEstado = document.createElement("td")


        let celdaOpcion = document.createElement("td");
        let botonEditarPrestamo = document.createElement("button")
        botonEditarPrestamo.value = result[i]["id_prestamo"]
        botonEditarPrestamo.innerHTML = "Editar"
        botonEditarPrestamo.onclick = function (e) {
          $('#exampleModal').modal('show');
          CargarFormulario();
          consultarPrestamoID(this.value);
        }

        botonEditarPrestamo.className = "btn btn-warning editar_prestamo"

        celdaId.innerText = result[i]["id_prestamo"];
        celdaIdLibro.innerText = result[i]["libro"]["titulo"];
        celdaIdUsuario.innerText = result[i]["usuario"]["nombre"];
        celdaFechaPrestamo.innerText = result[i]["fecha_prestamo"];
        celdaFechaDevolucion.innerText = result[i]["fecha_devolucion"];
        celdaEstado.innerText = result[i]["estado"];


        trResgistro.appendChild(celdaId);
        trResgistro.appendChild(celdaIdLibro);
        trResgistro.appendChild(celdaIdUsuario);
        trResgistro.appendChild(celdaFechaPrestamo);
        trResgistro.appendChild(celdaFechaDevolucion);
        trResgistro.appendChild(celdaEstado);

        celdaOpcion.appendChild(botonEditarPrestamo);
        trResgistro.appendChild(celdaOpcion)


        cuerpoTabla.appendChild(trResgistro);

      }
    },
    error: function (error) {

      alert("Error en la petición " + error);
    }
  })
}

function consultarPrestamoID(id) {
  //CargarFormulario();
  //alert(id);
  $.ajax({
    url: url + id,
    type: "GET",
    success: function (result) {
      document.getElementById("id_prestamo").value = result["id_prestamo"];
      document.getElementById("id_libro").value = result["libro"]["id_libro"];
      var contadorRepeticiones=0;
      while (!consultaUsuarioFinalizada) {
        console.log("esperando a los usuario");
        contadorRepeticiones++;
        if(contadorRepeticiones>=20){
          alert("Error al consultar vuelve a intentarlo");
          break;
        }
      }
      document.getElementById("id_usuario").value = result["usuario"]["id_usuario"];
      document.getElementById("fecha_prestamo").value = result["fecha_prestamo"];
      document.getElementById("fecha_devolucion").value = result["fecha_devolucion"];

      document.getElementById("estado").value = result["estado"];
    }
  });
}


function actualizarPrestamo() {
  var id_prestamo = document.getElementById("id_prestamo").value
  let formData = {
    "usuario": document.getElementById("id_usuario").value,
    "libro": document.getElementById("id_libro").value,
    "fecha_prestamo": document.getElementById("fecha_prestamo").value,
    "fecha_devolucion": document.getElementById("fecha_devolucion").value,
    "estado": document.getElementById("estado").value

  };
  function validarCampos() {
    // Obtener los valores de los campos
    var id_libro = document.getElementById("id_libro").value;
    var id_usuario = document.getElementById("id_usuario").value;
    var fecha_prestamo = document.getElementById("fecha_prestamo").value;
    var fecha_devolucion = document.getElementById("fecha_devolucion").value;
    var estado = document.getElementById("estado").value;


    // Verificar si algún campo está vacío
    if (id_libro === '' || id_usuario === '' || fecha_prestamo === '' || fecha_devolucion === '' || estado === '') {
      return false; // Al menos un campo está vacío
    } else {
      return true; // Todos los campos están llenos
    }
  }

  if (validarCampos()) {
    $.ajax({
      url: url + id_prestamo,
      type: "PUT",
      data: formData,
      success: function (result) {
        // Manejar la respuesta exitosa según necesites
        Swal.fire({
          title: "¡Excelente!",
          text: "Se guardó correctamente",
          icon: "success"
        });
        // Puedes hacer algo adicional como recargar la lista de médicos
        listarPrestamo();
      },
      error: function (error) {
        // Manejar el error de la petición
        Swal.fire({
          title: "¡Error!",
          text: "No se guardó",
          icon: "error"
        });
      }
    });
  } else {
    Swal.fire({
      title: "¡Error!",
      text: "Llene todos los campos correctamente",
      icon: "error"
    });
  }
}

function registrarPrestamo() {

  let formData = {
    "libro": document.getElementById("id_libro").value,
    "usuario": document.getElementById("id_usuario").value,
    "fecha_prestamo": document.getElementById("fecha_prestamo").value,
    "fecha_devolucion": document.getElementById("fecha_devolucion").value,
    "estado": document.getElementById("estado").value
  };


  // let fecha_ingresoDate = new Date(formData.fecha_ingreso);
  //let fecha_salidaDate = new Date(formData.fecha_salida);

  //if (fecha_salidaDate < fecha_ingresoDate) {
  /* Swal.fire({
     title: "Error.",
     text: "La fecha de salida no puede ser anterior a la fecha de ingreso.",
     icon: "error"
   });
   return; // Detener la ejecución de la función
 }
 
 */
  let camposValidos = true;
  let camposRequeridos = [
    "id_libro",
    "id_usuario",
    "fecha_prestamo",
    "fecha_devolucion",
    "estado"
  ];

  camposRequeridos.forEach(function (campo) {
    let valorCampo = document.getElementById(campo).value.trim();
    if (valorCampo === "") {
      camposValidos = false;
      return false; // Terminar la iteración si se encuentra un campo vacío
    }
  });

  if (camposValidos) {
    $.ajax({
      url: url,
      type: "POST",
      data: formData,
      success: function (result) {
        Swal.fire({
          title: "¡Excelente!",
          text: "Se guardó correctamente",
          icon: "success"
        });
        limpiarPrestamo();
      },
      error: function (error) {
        Swal.fire("Error", "Error al guardar, " + error.responseText, "error");
      },
    });

  } else {
    Swal.fire({
      title: "¡Error!",
      text: "Llene todos los campos correctamente",
      icon: "error"
    });
  }

}

function validarCampos() {
  var id_libro = document.getElementById("id_libro");
  return validarIdLibro(id_libro);
}
function validarIdLibro(cuadroNumero) {

  var valor = cuadroNumero.value;
  var valido = true;
  if (valor.length < 1 || valor.length > 200) {
    valido = false
  }

  if (valido) {
    //cuadro de texto cumple
    cuadroNumero.className = "form-control is-valid";
  } else {
    //cuadro de texto no cumple
    cuadroNumero.className = "form-control is-invalid";
  }
  return valido;

}

function validarCampos() {
  var id_usuario = document.getElementById("id_usuario");
  return validarIdusuario(id_usuario);
}
function validarIdusuario(cuadroNumero) {

  var valor = cuadroNumero.value;
  var valido = true;
  if (valor.length < 1 || valor.length > 100) {
    valido = false
  }

  if (valido) {
    //cuadro de texto cumple
    cuadroNumero.className = "form-control is-valid";
  } else {
    //cuadro de texto no cumple
    cuadroNumero.className = "form-control is-invalid";
  }
  return valido;

}


function validarCampos() {
  var fecha_prestamo = document.getElementById("fecha_prestamo");
  return validarFecha_prestamo(fecha_prestamo);
}
function validarFecha_prestamo(cuadroNumero) {

  var valor = cuadroNumero.value;
  var valido = true;
  if (valor.length < 1 || valor.length > 20) {
    valido = false
  }

  if (valido) {
    //cuadro de texto cumple
    cuadroNumero.className = "form-control is-valid";
  } else {
    //cuadro de texto no cumple
    cuadroNumero.className = "form-control is-invalid";
  }
  return valido;

}

function validarCampos() {
  var fecha_devolucion = document.getElementById("fecha_devolucion");
  return validarFecha_devolucion(fecha_devolucion);
}
function validarFecha_devolucion(cuadroNumero) {

  var valor = cuadroNumero.value;
  var valido = true;
  if (valor.length < 1 || valor.length > 20) {
    valido = false
  }

  if (valido) {
    //cuadro de texto cumple
    cuadroNumero.className = "form-control is-valid";
  } else {
    //cuadro de texto no cumple
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
  if (valor.length < 1 || valor.length > 20) {
    valido = false
  }

  if (valido) {
    //cuadro de texto cumple
    cuadroNumero.className = "form-control is-valid";
  } else {
    //cuadro de texto no cumple
    cuadroNumero.className = "form-control is-invalid";
  }
  return valido;

}
function limpiarIngreso() {
  document.getElementById("id_libro").value = "";
  document.getElementById("id_usuario").value = "";
  document.getElementById("fecha_prestamo").value = "";
  document.getElementById("fecha_devolucion").value = "";

  document.getElementById("estado").value = "";
}

function CargarFormulario() {
  cargarUsuario();
  cargarLibro();
}

function cargarLibro() {
  let urlLibro = "http://localhost:8080/api/v1/libro/";

  $.ajax({
    url: urlLibro,
    type: "GET",
    success: function (result) {
      let libro = document.getElementById("id_libro");
      libro.innerHTML = "";
      let seleccioneOpcion = document.createElement("option");
      seleccioneOpcion.value = ""; seleccioneOpcion.innerText = "Seleccione una opción";
      libro.appendChild(seleccioneOpcion);

      for (let i = 0; i < result.length; i++) {
        let nombreLibro = document.createElement("option");
        nombreLibro.value = result[i]["id_libro"];
        nombreLibro.innerText = nombre_completo_libro =
          result[i]["titulo"];
        libro.appendChild(nombreLibro);
      }
    },
  });
}
function cargarUsuario() {
  let urlUsuario = "http://localhost:8080/api/v1/usuario/";
  consultaUsuarioFinalizada = false;
  $.ajax({
    url: urlUsuario,
    type: "GET",
    success: function (result) {
      let usuario = document.getElementById("id_usuario");
      usuario.innerHTML = "";
      let seleccioneOpcion = document.createElement("option");
      seleccioneOpcion.value = ""; seleccioneOpcion.innerText = "Seleccione una opción";
      usuario.appendChild(seleccioneOpcion);
      for (let i = 0; i < result.length; i++) {
        let nombreusuario = document.createElement("option");
        nombreusuario.value = result[i]["id_usuario"];
        nombreusuario.innerText = nombre_completo_usuario =
          result[i]["nombre"];
        usuario.appendChild(nombreusuario);
      }
      consultaUsuarioFinalizada = true;
    },
  });
}