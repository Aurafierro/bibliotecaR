var url = "http://localhost:8080/api/v1/usuario/";


document.addEventListener("DOMContentLoaded", function() {
  document.getElementById("nombre").addEventListener("keypress", soloLetras);
  document.getElementById("descripcion_casa").addEventListener("keypress", soloLetras);
  document.getElementById("genero").addEventListener("keypress", soloLetras);
  document.getElementById("correo").addEventListener("keypress", soloCorreoDireccion);
  document.getElementById("direccion").addEventListener("keypress", soloCorreoDireccion);

  function soloLetras(event){
    console.log("Llave presionada: " + event.key);
    console.log("Código tecla: " + event.keyCode);
  
    const letrasPermitidas = [
      "a","b","c","d","e","f","g","h","i","j","k","l","m","n","p",
      "q","r","s","t","u","v","x","y","w","o","z","ñ","Ñ",
      "A","B","C","D","E","F","G","H","I","J","K","L",
      "M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z", " ",
      "á",  "é",  "í",  "ó",  "ú",  "Á",  "É",  "Í",  "Ó",  "Ú"
    ];
  
    if (!letrasPermitidas.includes(event.key)){
      event.preventDefault();
    }
  }

  function soloCorreoDireccion(event){
    console.log("Llave presionada: " + event.key);
    console.log("Código tecla: " + event.keyCode);

    const letrasPermitidas = [
      "a","b","c","d","e","f","g","h","i","j","k","l","m","n","p",
      "q","r","s","t","u","v","x","y","w","o","z","ñ","Ñ",
      "A","B","C","D","E","F","G","H","I","J","K","L",
      "M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z", " ",
      "á",  "é",  "í",  "ó",  "ú",  "Á",  "É",  "Í",  "Ó",  "Ú"
    ];
    const caracteresPermitidos=[
      '@','_','-','.','0','1','2','3','4','5','6','7','8','9'
    ];

    if (!(letrasPermitidas.includes(event.key) || caracteresPermitidos.includes(event.key))){
      event.preventDefault();
    }
  }
});
document.addEventListener("DOMContentLoaded", function() {
  document.getElementById("telefono").addEventListener("keypress", soloNumeros);
  

  function soloNumeros(event){
    console.log("Llave presionada: " + event.key);
    console.log("Código tecla: " + event.keyCode);
  
    const numeroPermitidos = [
      "1","2","3","4","5","6","7","8","9","0"
    ];
  
    if (!(numeroPermitidos.includes(event.key))){
      event.preventDefault();
      return;
    }
  }
});




function listarUsuario() {
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
                var trRegistro = document.createElement("tr");

                let celdaId = document.createElement("td");
                celdaId.innerText = result[i]["id_usuario"];
                trRegistro.appendChild(celdaId);

                let celdaNombre = document.createElement("td");
                celdaNombre.innerText = result[i]["nombre"];
                trRegistro.appendChild(celdaNombre);

                let celdaCorreo = document.createElement("td");
                celdaCorreo.innerText = result[i]["correo"];
                trRegistro.appendChild(celdaCorreo);

                let celdaDireccion = document.createElement("td");
                celdaDireccion.innerText = result[i]["direccion"];
                trRegistro.appendChild(celdaDireccion);

                let celdaTipoUsuario = document.createElement("td");
                celdaTipoUsuario.innerText = result[i]["tipo_usuario"];
                trRegistro.appendChild(celdaTipoUsuario);

                

                let celdaOpcion = document.createElement("td");

                let botonEditarUsuario = document.createElement("button");
                botonEditarUsuario.value = result[i]["id_usuario"];
                botonEditarUsuario.innerHTML = '<i class="fas fa-keyboard"></i>';
                botonEditarUsuario.className = "btn btn-warning editar-libro me-1";
                botonEditarUsuario.onclick = function(e) {
                    $('#exampleModal').modal('show');
                    consultarUsuarioID(this.value);
                };
                celdaOpcion.appendChild(botonEditarUsuario);

                let botonEliminarUsuario = document.createElement("button");
                botonEliminarUsuario.value = result[i]["id_usuario"];
                botonEliminarUsuario.className = "btn btn-danger eliminar-libro ms-1";
                botonEliminarUsuario.innerHTML = '<i class="fas fa-trash-alt"></i>';
                botonEliminarUsuario.onclick = function(e) {
                    eliminarUsuario(this.value);
                };
                celdaOpcion.appendChild(botonEliminarUsuario);

                let botonVerMas = document.createElement("button");
                botonVerMas.innerText = "Ver más";
                botonVerMas.className = "btn btn-info ver-mas mt-1 ms-1";
                botonVerMas.onclick = (function (usuario) {
                  return function (e) {
                    llenarModal(usuario);
                    $('#examplModal').modal('show');
                  };
                })(result[i]);
                
                celdaOpcion.appendChild(botonVerMas);
                
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

function llenarModal(usuario) {
    document.getElementById("telefono").innerText = usuario.telefono;
    document.getElementById("descripcion_casa").innerText =usuario.descripcion_casa;
}


function eliminarUsuario(id) {
  console.log("Iniciando eliminación para ID:", id);
  Swal.fire({
    title: "¿Estás seguro de eliminar este usuario?",
    text: "No podrás revertir esto!",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Sí, eliminar!",
    cancelButtonText: "No, cancelar!"
  }).then((result) => {
    if (result.isConfirmed) {
      $.ajax({
        url: url + id,
        type: "DELETE",
        success: function () {
          console.log("Usuario eliminado con éxito, ID:", id);
          Swal.fire(
            "Eliminado!",
            "El Usuario ha sido eliminado.",
            "success"
          );
          listarUsuario();
        },
        error: function (error) {
          console.error("Error al eliminar el Usuario:", error);
          Swal.fire("Error", "Error al eliminar el Usuario: " + error.responseText, "error");
        }
      });
    } else {
      console.log("Eliminación cancelada");
    }
  });
}

function consultarUsuarioID(id) {
  $.ajax({
    url: url + id,
    type: "GET",
    success: function (result) {
      document.getElementById("id_usuario").value = result["id_usuario"];
      document.getElementById("nombre").value = result["nombre"];
      document.getElementById("correo").value = result["correo"];
      document.getElementById("direccion").value = result["direccion"];
      document.getElementById("tipo_usuario").value = result["tipo_usuario"];
    }
  });
}

function registrarUsuario() {
  let nombre = document.getElementById("nombre").value.trim();
  let correo = document.getElementById("correo").value.trim();
  let direccion = document.getElementById("direccion").value.trim();
  let telefono = document.getElementById("telefono").value.trim();
  let descripcion_casa = document.getElementById("descripcion_casa").value.trim();
  let tipo_usuario = document.getElementById("tipo_usuario").value.trim();

  // Validar que al menos uno de los campos requeridos esté lleno
  if (nombre === "" && correo === "" && direccion === "" && telefono === "" && descripcion_casa === "" && tipo_usuario === "") {
    Swal.fire({
      icon: 'error',
      title: 'Campos vacíos',
      text: 'Todos los campos son obligatorios. Por favor, llénelos correctamente.',
      confirmButtonText: 'Entendido'
    });
    return;
  }

  // Validar que todos los campos requeridos estén llenos
  let camposValidos = true;
  let camposRequeridos = [nombre, correo, direccion, telefono, descripcion_casa, tipo_usuario];

  camposRequeridos.forEach(function (campo) {
    if (campo === "") {
      camposValidos = false;
      return false;
    }
  });

  // Validar correo electrónico completo usando la función validarCorreo()
  if (!validarCorreo(correo)) {
    Swal.fire({
      icon: 'error',
      title: 'Correo electrónico incompleto',
      text: 'Por favor, ingrese un correo válido.',
      confirmButtonText: 'Entendido'
    });
    return;
  }
  
  // Si todos los campos están llenos, proceder con la solicitud AJAX
  if (camposValidos) {
    let formData = {
      "nombre": nombre,
      "correo": correo,
      "direccion": direccion,
      "telefono": telefono,
      "descripcion_casa": descripcion_casa,
      "tipo_usuario": tipo_usuario
    };

    $.ajax({
      url: "http://localhost:8080/api/v1/usuario/",
      type: "POST",
      contentType: "application/json",
    data: JSON.stringify(formData),
      success: function (result) {
        Swal.fire({
          title: "¡Excelente!",
          text: "Se guardó correctamente",
          icon: "success"
        });
        limpiarUsuario(); // Suponiendo que tienes una función para limpiar el formulario
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

// Función para validar el formato del correo electrónico
function validarCorreo(correo) {
  let partesCorreo = correo.split('@');
  return partesCorreo.length === 2 && partesCorreo[1].indexOf('.') !== -1;
}

//nombre
function validarCampos() {
  var nombre = document.getElementById("nombre");
  return validarNombre(nombre);
}

function validarNombre(cuadroNumero) {
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

//DIRECCION
function validarCampos() {
  var direccion = document.getElementById("direccion");
  return validarDireccion(direccion);
}

function validarDireccion(cuadroNumero) {
  var valor = cuadroNumero.value;
  var valido = true;
  if (valor.length < 1 || valor.length > 50) {
    valido = false
  }

  if (valido) {
    cuadroNumero.className = "form-control is-valid";
  } else {
    cuadroNumero.className = "form-control is-invalid";
  }
  return valido;
}






//telefono
function validarCampos() {
  var telefono = document.getElementById("telefono");
  return validarTelefono(telefono);
}

function validarTelefono(cuadroNumero) {
  var valor = cuadroNumero.value;
  var valido = true;
  if (valor.length < 1 || valor.length > 11) {
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
  var descripcion_casa = document.getElementById("descripcion_casa");
  return validarDescripcion(descripcion_casa);
}


function validarDescripcion(cuadroNumero) {
  var valor = cuadroNumero.value;
  var valido = true;
  if (valor.length < 1 || valor.length > 200) {
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
    var tipo_usuario = document.getElementById("tipo_usuario");
    return validarTipoUsuario(tipo_usuario);
  }
  
  
  function validarTipoUsuario(cuadroNumero) {
    var valor = cuadroNumero.value;
    var valido = true;
    if (valor.length < 1 || valor.length > 200) {
      valido = false
    }
  
    if (valido) {
      cuadroNumero.className = "form-control is-valid";
    } else {
      cuadroNumero.className = "form-control is-invalid";
    }
    return valido;
  }

function validarDescripcion(cuadroNumero) {
  var valor = cuadroNumero.value;
  var valido = true;
  if (valor.length < 1 || valor.length > 200) {
    valido = false
  }

  if (valido) {
    cuadroNumero.className = "form-control is-valid";
  } else {
    cuadroNumero.className = "form-control is-invalid";
  }
  return valido;
}

//2.Crear petición que actualice la información del usuario


function actualizarUsuario() {
    var id_usuario = document.getElementById("id_usuario").value;

    // Obtener los valores de los campos
    var nombre = document.getElementById("nombre").value;
    var correo = document.getElementById("correo").value;
    var direccion = document.getElementById("direccion").value;
    var telefono = document.getElementById("telefono").value;
    var descripcion_casa = document.getElementById("descripcion_casa").value;
    var tipo_usuario = document.getElementById("tipo_usuario").value;

    // Crear el objeto formData con los valores
    let formData = {
        "nombre": nombre,
        "correo": correo,
        "direccion": direccion,
        "telefono": telefono,
        "descripcion_casa": descripcion_casa,
        "tipo_usuario": tipo_usuario
    };

    // Realizar la petición AJAX si todos los campos son válidos
    $.ajax({
        url: url + id_usuario,
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(formData),
        success: function(result) {
            // Manejar la respuesta exitosa según necesites
            Swal.fire({
                title: "¡Excelente!",
                text: "Se guardó correctamente",
                icon: "success"
            });
            // Puedes hacer algo adicional como recargar la lista de libros
            listarUsuario();
        },
        error: function(error) {
            // Manejar el error de la petición
            Swal.fire({
                title: "¡Error!",
                text: "LLene todos los caampos",
                icon: "error"
            });
        }
    });
}

  

