package com.example.crudbiblioteca

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.crudbiblioteca.config.config
import org.json.JSONObject
import java.lang.Exception
import com.android.volley.Request.Method
import com.example.crudbiblioteca.models.libro
import com.google.gson.Gson

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [guardarLibroFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class guardarLibroFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private lateinit var txtTitulo:EditText
    private lateinit var txtAutor:EditText
    private lateinit var txtIsbn:EditText
    private lateinit var txtGenero:EditText
    private lateinit var txtejemplares:EditText
    private lateinit var txtejemplaresocupados:EditText
    private lateinit var txtdescripcion:EditText

    private lateinit var btnGuardar:Button
    //traer id cualquiera
    private  var  id:String=""

    fun edit(view: View, application: Context){

        var intent= Intent(application,listaLibroFragment::class.java)
        startActivity(intent)
    }
    //metodo encargado de traer la informacion del libro


    fun  consultarLibro(){
        if (id!=""){

            var request=JsonObjectRequest(
                Method.GET, //método de la petición
                config.urllibro+id, //url
                null, //parámetros
                {response->
                    val gson=Gson()
                    val libro:libro=gson.fromJson(response.toString(),libro::class.java)
                    txtAutor.setText(libro.nombre_autor)
                },
                {error->
                    Toast.makeText(
                        context,
                        "Error al consultar",
                        Toast.LENGTH_LONG
                    ).show()
                }
            )
            var queue=Volley.newRequestQueue(context)
            queue.add(request)
        }
    }

    fun guardarLibro(){
        try {
            if (id==""){//se crea el libro
                //se crea la peticion
              /*  val request= object :StringRequest(
                    Request.Method.POST,
                    config.urllibro,
                    //el listenerel metodo que se ejecuta cuando lapeticion es correcta.
                    Response.Listener {
                                      Toast.makeText(context, "se guardó correctamente",
                        Toast.LENGTH_LONG).show()
                    },
                    //El ErrorListener se ejecuta cuando la petición genera error
                    Response.ErrorListener {  }
                )
                {
                    override fun getParams(): Map<String, String>{
                        val parametros = HashMap<String,String>()
                        parametros.put("titulo",txtTitulo.text.toString())
                        parametros.put("nombre_autor",txtAutor.text.toString())
                        parametros.put("isbn",txtIsbn.text.toString())
                        parametros.put("genero",txtGenero.text.toString())
                        parametros.put("num_ejemplares_disponibles",txtejemplares.text.toString())
                        parametros.put("num_ejemplares_ocupados",txtejemplaresocupados.text.toString())
                        return parametros
                    }
                }*/
                var parametros= JSONObject()
                parametros.put("titulo",txtTitulo.text.toString())
                parametros.put("nombre_autor",txtAutor.text.toString())
                parametros.put("isbn",txtIsbn.text.toString())
                parametros.put("genero",txtGenero.text.toString())
                parametros.put("num_ejemplares_disponibles",txtejemplares.text.toString())
                parametros.put("num_ejemplares_ocupados",txtejemplaresocupados.text.toString())
                parametros.put("descripcion",txtdescripcion.text.toString())

                var request= JsonObjectRequest(
                    Request.Method.POST,
                    config.urllibro,
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


        }catch (error:Exception){

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
        val view= inflater.inflate(R.layout.fragment_guardar_libro, container, false)
        txtTitulo=view.findViewById(R.id.txtTitulo)
        txtAutor=view.findViewById(R.id.txtAutor)
        txtIsbn=view.findViewById(R.id.txtIsbn)
        txtGenero=view.findViewById(R.id.txtGenero)
        txtejemplares=view.findViewById(R.id.txtejemplares)
        txtejemplaresocupados=view.findViewById(R.id.txtejemplaresocupados)
        txtdescripcion=view.findViewById(R.id.txtdescripcion)
        btnGuardar=view.findViewById(R.id.btnGuardar)
        btnGuardar.setOnClickListener{
            guardarLibro()
        }
        consultarLibro()
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment guardarLibroFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            guardarLibroFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}