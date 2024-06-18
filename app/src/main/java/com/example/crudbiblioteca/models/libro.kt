package com.example.crudbiblioteca.models

data class libro(

    //se agregan los elemenods que maneja la api

    var id_libro:String,
    var titulo:String,
    var nombre_autor:String,
    var isbn:String,
    var genero:String,
    var num_ejemplares_disponibles:Int,
    var nime_ejemplares_ocupados:Int

)
