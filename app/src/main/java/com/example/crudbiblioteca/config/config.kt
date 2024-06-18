package com.example.crudbiblioteca.config

class config {
    //se crea una url estatica para consultar sin instanciar

    //el metodo companion object sirve para almacenar las variables estaticas

    companion object{
        val urlBase="http://192.168.177.229:8080/"
        val urllibro=urlBase+"libro/"
    }
}