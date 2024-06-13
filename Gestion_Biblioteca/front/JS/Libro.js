var url = "http://localhost:8080/api/v1/libro/";

$(document).ready(function() {
    console.log("Página cargada, ejecutando listarLibro...");
    listarLibro();
});

function listarLibro() {
    var capturarFiltro = document.getElementById("inputSearch").value;
    var urlLocal = url;
    if (capturarFiltro != "") {
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
                celdaId.innerText = result[i]["id_libro"];
                trRegistro.appendChild(celdaId);

                let celdaTitulo = document.createElement("td");
                celdaTitulo.innerText = result[i]["titulo"];
                trRegistro.appendChild(celdaTitulo);

                let celdaAutor = document.createElement("td");
                celdaAutor.innerText = result[i]["nombre_autor"];
                trRegistro.appendChild(celdaAutor);

                let celdaIsbn = document.createElement("td");
                celdaIsbn.innerText = result[i]["isbn"];
                trRegistro.appendChild(celdaIsbn);

                let celdaGenero = document.createElement("td");
                celdaGenero.innerText = result[i]["genero"];
                trRegistro.appendChild(celdaGenero);

                let celdaEjemplares = document.createElement("td");
                celdaEjemplares.innerText = result[i]["num_ejemplares_disponibles"];
                trRegistro.appendChild(celdaEjemplares);

                let celdaOcupado = document.createElement("td");
                celdaOcupado.innerText = result[i]["num_ejemplares_ocupados"];
                trRegistro.appendChild(celdaOcupado);

                let celdaOpcion = document.createElement("td");

                let botonEditarLibro = document.createElement("button");
                botonEditarLibro.value = result[i]["id_libro"];
                botonEditarLibro.innerHTML = '<i class="fas fa-keyboard"></i>';
                botonEditarLibro.className = "btn btn-warning editar-libro me-1";
                botonEditarLibro.onclick = function(e) {
                    $('#exampleModal').modal('show');
                    consultarLibroID(this.value);
                };
                celdaOpcion.appendChild(botonEditarLibro);

                let botonEliminarLibro = document.createElement("button");
                botonEliminarLibro.value = result[i]["id_libro"];
                botonEliminarLibro.className = "btn btn-danger eliminar-libro ms-1";
                botonEliminarLibro.innerHTML = '<i class="fas fa-trash-alt"></i>';
                botonEliminarLibro.onclick = function(e) {
                    eliminarLibro(this.value);
                };
                celdaOpcion.appendChild(botonEliminarLibro);

                let botonVerMas = document.createElement("button");
                botonVerMas.innerText = "Ver más";
                botonVerMas.className = "btn btn-info ver-mas mt-1 ms-1";
                botonVerMas.onclick = (function (libro) {
                  return function (e) {
                    llenarModal(libro);
                    $('#examplModal').modal('show'); // Corregir el selector para mostrar el modal correcto
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

function llenarModal(libro) {
   
    document.getElementById('id_libro').value = libro.id_libro || '';
    document.getElementById('titulo').value = libro.titulo || '';
    document.getElementById('nombre_autor').value = libro.nombre_autor || '';
    document.getElementById('isbn').value = libro.isbn || '';
    document.getElementById('genero').value = libro.genero || '';
    document.getElementById('num_ejemplares_disponibles').value = libro.num_ejemplares_disponibles || '';
    document.getElementById('num_ejemplares_ocupados').value = libro.num_ejemplares_ocupados || '';
}


function eliminarLibro(id) {
  console.log("Iniciando eliminación para ID:", id);
  Swal.fire({
    title: "¿Estás seguro?",
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
          console.log("Libro eliminado con éxito, ID:", id);
          Swal.fire(
            "Eliminado!",
            "El libro ha sido eliminado.",
            "success"
          );
          listarLibro();
        },
        error: function (error) {
          console.error("Error al eliminar el libro:", error);
          Swal.fire("Error", "Error al eliminar el libro: " + error.responseText, "error");
        }
      });
    } else {
      console.log("Eliminación cancelada");
    }
  });
}

function consultarLibroID(id) {
  $.ajax({
    url: url + id,
    type: "GET",
    success: function (result) {
      document.getElementById("id_libro").value = result["id_libro"];
      document.getElementById("titulo").value = result["titulo"];
      document.getElementById("nombre_autor").value = result["nombre_autor"];
      document.getElementById("isbn").value = result["isbn"];
      document.getElementById("genero").value = result["genero"];
      document.getElementById("num_ejemplares_disponibles").value = result["num_ejemplares_disponibles"];
      document.getElementById("num_ejemplares_ocupados").value = result["num_ejemplares_ocupados"];
    }
  });
}

function registrarLibro() {
  let formData = {
    "titulo": document.getElementById("titulo").value,
    "nombre_autor": document.getElementById("nombre_autor").value,
    "isbn": document.getElementById("isbn").value,
    "genero": document.getElementById("genero").value,
    "num_ejemplares_disponibles": document.getElementById("num_ejemplares_disponibles").value,
    "num_ejemplares_ocupados": document.getElementById("num_ejemplares_ocupados").value
  };

  let camposValidos = true;
  let camposRequeridos = [
    "titulo",
    "nombre_autor",
    "isbn",
    "genero",
    "num_ejemplares_disponibles",
    "num_ejemplares_ocupados"
  ];

  camposRequeridos.forEach(function (campo) {
    let valorCampo = document.getElementById(campo).value.trim();
    if (valorCampo === "") {
      camposValidos = false;
      return false; 
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
        limpiarLibro();
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
//TITULO
function validarCampos() {
  var titulo = document.getElementById("titulo_");
  return validarTituloLibro(titulo);
}

function validarTituloLibro(cuadroNumero) {
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

//NOMBRE DEL AUTRO
function validarCampos() {
  var nombre_autor = document.getElementById("nombre_autor");
  return validarNombre(nombre_autor);
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

//ISBN

//GENRO


//NUMERO DE EJEMPLARES DISPONIBLES
function validarCampos() {
  var num_ejemplares_disponibles = document.getElementById("num_ejemplares_disponibles");
  return validarNumeroEjem(num_ejemplares_disponibles);
}

function validarNumeroEjem(cuadroNumero) {
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

//NUMERO DE EJEMPARES OCUPADOS
function validarCampos() {
  var num_ejemplares_ocupados = document.getElementById("num_ejemplares_ocupados");
  return validarNumeroOcupado(num_ejemplares_ocupados);
}

function validarNumeroOcupado(cuadroNumero) {
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

//2.Crear petición que actualice la información del medico


function actualizarLibro() {
    var id_libro = document.getElementById("id_libro").value;

    // Obtener los valores de los campos
    var titulo = document.getElementById("titulo").value;
    var nombre_autor = document.getElementById("nombre_autor").value;
    var isbn = document.getElementById("isbn").value;
    var genero = document.getElementById("genero").value;
    var num_ejemplares_disponibles = document.getElementById("num_ejemplares_disponibles").value;
    var num_ejemplares_ocupados = document.getElementById("num_ejemplares_ocupados").value;

    
    // Crear el objeto formData con los valores
    let formData = {
        "titulo": titulo,
        "nombre_autor": nombre_autor,
        "isbn": isbn,
        "genero": genero,
        "num_ejemplares_disponibles": num_ejemplares_disponibles,
        "num_ejemplares_ocupados": num_ejemplares_ocupados
    };

    // Realizar la petición AJAX si todos los campos son válidos
    $.ajax({
        url: url + id_libro,
        type: "PUT",
        data: formData,
        success: function(result) {
            // Manejar la respuesta exitosa según necesites
            Swal.fire({
                title: "¡Excelente!",
                text: "Se guardó correctamente",
                icon: "success"
            });
            // Puedes hacer algo adicional como recargar la lista de libros
            listarLibros();
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

  


function validarTituloLibro(cuadroNumero) {
    var valor = cuadroNumero.value;
    var valido = valor.length >= 1 && valor.length <= 100;
    actualizarEstadoCampo(valido, "titulo");
    return valido;
}

function validarNombre(cuadroNumero) {
    var valor = cuadroNumero.value;
    var valido = valor.length >= 1 && valor.length <= 100;
    actualizarEstadoCampo(valido, "nombre_autor");
    return valido;
}

function validarIsbn(cuadroNumero) {
    var valor = cuadroNumero.value;
    var valido = valor.length >= 1 && valor.length <= 13;
    actualizarEstadoCampo(valido, "isbn");
    return valido;
}

function validarGenero(cuadroNumero) {
    var valor = cuadroNumero.value;
    var valido = valor.length >= 4 && valor.length <= 15;
    actualizarEstadoCampo(valido, "genero");
    return valido;
}

function validarNumeroEjem(cuadroNumero) {
    var valor = cuadroNumero.value;
    var valido = valor.length >= 1 && valor.length <= 100;
    actualizarEstadoCampo(valido, "num_ejemplares_disponibles");
    return valido;
}

function validarNumeroOcupado(cuadroNumero) {
    var valor = cuadroNumero.value;
    var valido = valor.length >= 1 && valor.length <= 200;
    actualizarEstadoCampo(valido, "num_ejemplares_ocupados");
    return valido;
}

function actualizarEstadoCampo(valido, campoId) {
    var campo = document.getElementById(campoId);
    campo.className = valido ? "form-control is-valid" : "form-control is-invalid";
}