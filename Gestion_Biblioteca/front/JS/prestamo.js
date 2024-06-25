
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
            //se crea una etiqueta tr por cada registro
            let trRegistro = document.createElement("tr"); 
            let celdaId = document.createElement("td");
            let celdaPrestamo  = document.createElement("td");
            let celdaDevolucion  = document.createElement("td");
            let celdaLibro  = document.createElement("td");
            let celdaUsuario  = document.createElement("td");
            let celdaEstado  = document.createElement("td");
            
    
            //almacenamos en valor
    
            celdaId.innerText = result[i]["id_prestamo"];
            celdaPrestamo.innerText = result[i]["fecha_prestamo"];
            celdaDevolucion.innerText = result[i]["fecha_devolucion"];
           
            celdaLibro.innerText = nombre_completo_libro =   result[i]["libro"]["titulo"]
    
              celdaUsuario.innerText = nombre_completo = result[i]["usuario"]["nombre"] 
            celdaEstado.innerText = result[i]["estado"];
           
    
           
    
            trRegistro.appendChild(celdaId);
            trRegistro.appendChild(celdaPrestamo);
            trRegistro.appendChild(celdaDevolucion);
            trRegistro.appendChild(celdaLibro);
            trRegistro.appendChild(celdaUsuario);
          
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
    //que es Cors
    function registrarPrestamo() {
      let fecha_prestamo = document.getElementById("fecha_prestamo").value;
      let fecha_devolucion = document.getElementById("fecha_devolucion").value;
      let libro = document.getElementById("libro").value;
      let usuario = document.getElementById("usuario").value;
  
      let estado = document.getElementById("estado").value;
    
      let formData = {
        fecha_prestamo: fecha_prestamo,
        fecha_devolucion: fecha_devolucion,
        libro: libro,
        usuario: usuario,
        estado,estado
      
      };
    
      if (validarCampos()) {
        $.ajax({
          url: url,
          type: "POST",
          data: formData,
          success: function (result) {
         //   alert("se guardó correctamente");
            
          Swal.fire({
            position: "center",
            icon: "success",
            title: "¡Se ha registrado correctamente!",
            showConfirmButton: false,
            timer: 1500
          });
    
          },
          error: function (error) {
           // alert("error al guardar".error);
            Swal.fire("Alerta", "¡Error al guardar! "+error.responseText, "warning");
          },
        });
      } else {
        Swal.fire("Error", "¡Faltan campos por llenar!", "error");
      }
    }
    
    
    function consultarPrestamoID(id_prestamo){
      //alert(id);
      $.ajax({
          url:url+id_prestamo,
          type:"GET",
          success: function(result){
              document.getElementById("id_prestamo").value=result["id_prestamo"];
              document.getElementById("fecha_prestamo").value=result["fecha_prestamo"];
              document.getElementById("fecha_devolucion").value=result["fecha_devolucion"];
              
              document.getElementById("libro").value=result[ "libro"]["id_libro"];
              document.getElementById("usuario").value=result[ "usuario"]["id_usuario"];
              document.getElementById("estado").value=result[ "estado"];
    
          }
      });
    }
    function filtro(){
      //alert(id);
      $.ajax({
          url:url+busquedafiltro,
          type:"GET",
          success: function(result){
              //document.getElementById("id_ingreso").value=result["id_ingreso"];
             
    
          }
      });
    }
    function CargarFormulario() {
      cargarLibro();
      cargarUsuario();
    }
    //funcion para traer los medicos
    function cargarLibro() {
      let urlLibro = "http://localhost:8080/api/v1/libro/";
    
      $.ajax({
        url: urlLibro,
        type: "GET",
        success: function (result) {
          let libro = document.getElementById("libro");
          libro.innerHTML = "";
          for (let i = 0; i < result.length; i++) {
            let nombreLibro = document.createElement("option");
            nombreLibro.value = result[i]["id_libro"];
            nombreLibro.innerText = nombre_completo_libro = result[i]["titulo"];
            libro.appendChild(nombreLibro);
          }
        },
      });
    }
    //funcion para traer los pacientes
    function cargarUsuario() {
      let urlusuario = "http://localhost:8080/api/v1/usuario/";
    
      $.ajax({
        url: urlusuario,
        type: "GET",
        success: function (result) {
          let usuario = document.getElementById("usuario");
          usuario.innerHTML = "";
          for (let i = 0; i < result.length; i++) {
            let nombreusuario = document.createElement("option");
            nombreusuario.value = result[i]["id_usuario"];
            nombreusuario.innerText = nombre_completo =  result[i]["nombre"];
            usuario.appendChild(nombreusuario);
          }
        },
      });
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
    
    function actualizarPrestamo() {
      var id_prestamo=document.getElementById("id_prestamo").value;
      consultarPrestamoID(id_ingreso);
      let formData = {
       // "id_prestamo" : document.getElementById("id_ingreso").value,
        "fecha_prestamo": document.getElementById("fecha_prestamo").value,
        "fecha_devolucion" : document.getElementById("fecha_devolucion").value,
        "libro" : document.getElementById("libro").value,
        "usuario" : document.getElementById( "usuario" ).value,
        "estado" : document.getElementById("estado").value
        
      };
     
      
    
    
      if(validarCampos()){
      $.ajax({
          url: url+id_prestamo,
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
              
              //CargarFormulario();
              listarPrestamo(); //Lista los médicos después de actualizar
          },
          error: function(error) {
              Swal.fire("Error", "Error al guardar", "error");
          }  
      });
      }
      else{
          Swal.fire({
              title: "Error!",
              text: "complete los campos correctamente",
              icon: "error"
          });
          }
    }
    

    
   
    
    
