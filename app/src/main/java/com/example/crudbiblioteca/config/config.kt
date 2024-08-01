package com.example.crudbiblioteca.config

class config {
    //se crea una url estatica para consultar sin instanciar

    //el metodo companion object sirve para almacenar las variables estaticas

    companion object{
        val urlBase="http://10.192.66.60:8080/api/v1/"
        val urllibro=urlBase+"libro/"
        val urlusuario= urlBase+"usuario/"
    }
}