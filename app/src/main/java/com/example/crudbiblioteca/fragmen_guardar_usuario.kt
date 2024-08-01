package com.example.crudbiblioteca

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.crudbiblioteca.config.config

import com.example.crudbiblioteca.models.usuario
import com.google.gson.Gson
import org.json.JSONObject
import java.lang.Exception


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragmen_guardar_usuario.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragmen_guardar_usuario : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null




    private lateinit var txtNombre: EditText
    private lateinit var textCorreo: EditText
    private lateinit var textDireccion: EditText
    private lateinit var textTelefono: EditText
    private lateinit var textDescripcion: EditText
    private lateinit var textTipoUsuario:Spinner


    private lateinit var btnGuardarr: Button
    private lateinit var btnListaUsuario: Button

    //traer id cualquiera
    private  var  id:String=""



    fun  consultarUsuario(){
        if (id!=""){

            var request= JsonObjectRequest(
                Request.Method.GET, //método de la petición
                config.urlusuario+id , //url
                null, //parámetros
                {response->
                    val gson= Gson()
                    val usuario: usuario =gson.fromJson(response.toString(), usuario::class.java)
                    txtNombre.setText(usuario.nombre)
                },
                {error->
                    Toast.makeText(
                        context,
                        "Error al consultar",
                        Toast.LENGTH_LONG
                    ).show()
                }
            )
            var queue= Volley.newRequestQueue(context)
            queue.add(request)
        }
    }
    fun guardarUsuario(){
        try {
            if (id==""){//se crea el libro

                var parametros= JSONObject()
                parametros.put("nombre", txtNombre.text.toString())
                parametros.put("correo", textCorreo.text.toString())
                parametros.put("direccion", textDireccion.text.toString())
                parametros.put("telefono", textTelefono.text.toString())
                parametros.put("descripcion_casa", textDescripcion.text.toString())
                parametros.put("tipo_usuario", textTipoUsuario.selectedItem.toString())



                var request= JsonObjectRequest(
                    Request.Method.POST,
                    config.urlusuario,
                    parametros,

                    {response->
                        Toast.makeText(
                            context,
                            "Se guardó correctamente",
                            Toast.LENGTH_LONG

                        ).show()
                    },
                    { error ->
                        Toast.makeText(
                            context,
                            "Se generó un error",
                            Toast.LENGTH_LONG)
                            .show()


                    }
                )
                //se crea la cola del trabjao y se añade la petición
                var queue=Volley.newRequestQueue(context)
                //se añade la petición
                queue.add(request)

            }else{//se actualiza el libro



            }


        }catch (error: Exception){

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fragmen_guardar_usuario, container, false)

        txtNombre = view.findViewById(R.id.txtNombre)
        textCorreo = view.findViewById(R.id.textCorreo)
        textDireccion = view.findViewById(R.id.textDireccion)
        textTelefono = view.findViewById(R.id.textTelefono)
        textDescripcion = view.findViewById(R.id.textDescripcion)
        textTipoUsuario = view.findViewById(R.id.textTipoUsuario)

        btnGuardarr = view.findViewById(R.id.btnGuardarr)
        btnListaUsuario = view.findViewById(R.id.btnListaUsuario)

        btnGuardarr.setOnClickListener {
            guardarUsuario()
        }

        btnListaUsuario.setOnClickListener {
            val fragment = lista_usuario()
            fragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, fragment)
                ?.addToBackStack(null)
                ?.commit()
        }

        consultarUsuario()

        return view
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragmen_guardar_usuario.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragmen_guardar_usuario().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}