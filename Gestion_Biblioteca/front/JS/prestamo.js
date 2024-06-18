const url = "http://localhost:8080/api/v1/prestamo/";

function listarPrestamo() {
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
                celdaId.innerText = result[i]["id_prestamo"];
                trRegistro.appendChild(celdaId);

                let celdaPrestamo = document.createElement("td");
                celdaPrestamo.innerText = result[i]["fecha_prestamo"];
                trRegistro.appendChild(celdaPrestamo);

                let celdaDevolucion = document.createElement("td");
                celdaDevolucion.innerText = result[i]["fecha_devolucion"];
                trRegistro.appendChild(celdaDevolucion);

                let celdaLibro = document.createElement("td");
                celdaLibro.innerText = result[i]["libro"] ? result[i]["libro"].titulo : "-";
                trRegistro.appendChild(celdaLibro);

                let celdaUsuario = document.createElement("td");
                celdaUsuario.innerText = result[i]["usuario"] ? result[i]["usuario"].nombre : "-";
                trRegistro.appendChild(celdaUsuario);

                let celdaEstado = document.createElement("td");
                celdaEstado.innerText = result[i]["estado"];
                trRegistro.appendChild(celdaEstado);

                let celdaOpcion = document.createElement("td");

                let botonEditarPrestamo = document.createElement("button");
                botonEditarPrestamo.value = result[i]["id_prestamo"];
                botonEditarPrestamo.innerHTML = '<i class="fas fa-keyboard"></i>';
                botonEditarPrestamo.className = "btn btn-warning editar-libro me-1";
                botonEditarPrestamo.onclick = function(e) {
                    $('#exampleModal').modal('show');
                    consultarPrestamoID(this.value);
                };
                celdaOpcion.appendChild(botonEditarPrestamo);

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

function consultarPrestamoID(id) {
    $.ajax({
        url: url + id,
        type: "GET",
        success: function(result) {
            document.getElementById("id_prestamo").value = result["id_prestamo"];
            document.getElementById("fecha_prestamo").value = result["fecha_prestamo"];
            document.getElementById("fecha_devolucion").value = result["fecha_devolucion"];
            document.getElementById("libro").value = result["libro"] ? result["libro"].id_libro : "";
            document.getElementById("usuario").value = result["usuario"] ? result["usuario"].id_usuario : "";
            document.getElementById("estado").value = result["estado"];
        },
        error: function(error) {
            console.error("Error al consultar el préstamo:", error);
        }
    });
}


function registrarPrestamo() {
    let formData = {
        "fecha_prestamo": document.getElementById("fecha_prestamo").value,
        "fecha_devolucion": document.getElementById("fecha_devolucion").value,
        "libro": document.getElementById("libro").value,
        "usuario": document.getElementById("usuario").value,
        "estado": document.getElementById("estado").value
    };

    let camposValidos = validarCampos();

    if (camposValidos) {
        $.ajax({
            url: url,
            type: "POST",
            data: formData,
            success: function(result) {
                Swal.fire({
                    title: "¡Excelente!",
                    text: "Se guardó correctamente",
                    icon: "success"
                });
                limpiarPrestamo();
            },
            error: function(error) {
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
    return validarFecha_prestamo(document.getElementById("fecha_prestamo")) &&
           validarFecha_devolucion(document.getElementById("fecha_devolucion")) &&
           validarLibro(document.getElementById("libro")) &&
           validarUsuario(document.getElementById("usuario")) &&
           validarEstado(document.getElementById("estado"));
}

function validarFecha_prestamo(cuadroNumero) {
    var valor = cuadroNumero.value;
    var valido = true;
    if (valor.length < 1 || valor.length > 20) {
        valido = false;
    }

    cuadroNumero.className = valido ? "form-control is-valid" : "form-control is-invalid";
    return valido;
}

function validarFecha_devolucion(cuadroNumero) {
    var valor = cuadroNumero.value;
    var valido = true;
    if (valor.length < 1 || valor.length > 100) {
        valido = false;
    }

    cuadroNumero.className = valido ? "form-control is-valid" : "form-control is-invalid";
    return valido;
}

function validarLibro(cuadroNumero) {
    var valor = cuadroNumero.value;
    var valido = true;
    if (valor.length < 1 || valor.length > 100) {
        valido = false;
    }

    cuadroNumero.className = valido ? "form-control is-valid" : "form-control is-invalid";
    return valido;
}

function validarUsuario(cuadroNumero) {
    var valor = cuadroNumero.value;
    var valido = true;
    if (valor.length < 1 || valor.length > 200) {
        valido = false;
    }

    cuadroNumero.className = valido ? "form-control is-valid" : "form-control is-invalid";
    return valido;
}

function validarEstado(cuadroNumero) {
    var valor = cuadroNumero.value;
    var valido = true;
    if (valor.length < 1 || valor.length > 200) {
        valido = false;
    }

    cuadroNumero.className = valido ? "form-control is-valid" : "form-control is-invalid";
    return valido;
}

function limpiarPrestamo() {
    document.getElementById("fecha_prestamo").value = "";
    document.getElementById("fecha_devolucion").value = "";
    document.getElementById("libro").value = "";
    document.getElementById("usuario").value = "";
    document.getElementById("estado").value = "";
    validarCampos();
}
function cargarLibros() {
    let urlLibros = "http://localhost:8080/api/v1/libro/";
  
    $.ajax({
      url: urlLibros,
      type: "GET",
      success: function(result) {
        let libro = document.getElementById("libro");
        libro.innerHTML = "";
        for (let i = 0; i < result.length; i++) {
          let optionLibro = document.createElement("option");
          optionLibro.value = result[i]["id_libro"];
          optionLibro.innerText = result[i]["titulo"];
          libro.appendChild(optionLibro);
        }
      },
      error: function(error) {
        console.error("Error al cargar libros:", error);
      }
    });
  }
  
  function cargarUsuario() {
    let urlusuario = "http://localhost:8080/api/v1/usuario/";
  
    $.ajax({
      url: urlusuario,
      type: "GET",
      success: function(result) {
        let usuario = document.getElementById("usuario");
        usuario.innerHTML = "";
        for (let i = 0; i < result.length; i++) {
          let optionUsuario = document.createElement("option");
          optionUsuario.value = result[i]["id_usuario"];
          optionUsuario.innerText = result[i]["nombre"];
          usuario.appendChild(optionUsuario);
        }
      },
      error: function(error) {
        console.error("Error al cargar usuario:", error);
      }
    });
  }