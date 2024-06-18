package com.example.crudbiblioteca.models

data class libro(
    var id_libro:String,
    var titulo:String,
    var nombre_autor:String,
    var isbn:String,
    var genero:String,
    var num_ejemplares_dispodibles:Int,
    var num_ejemplares_ocupados:Int
)
