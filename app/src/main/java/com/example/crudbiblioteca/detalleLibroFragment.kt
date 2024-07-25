package com.example.crudbiblioteca

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.crudbiblioteca.config.config
import com.example.crudbiblioteca.models.libro
import com.google.gson.Gson
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [detalleLibroFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class detalleLibroFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private lateinit var lbllibro: TextView
    private lateinit var lblautor: TextView
    private lateinit var lbLisbn: TextView
    private lateinit var lblgenero: TextView
    private lateinit var lblejemplares: TextView
    private lateinit var lblejemplaresocupados: TextView
    private lateinit var lbldescripion: TextView
    private lateinit var btnEditar: Button
    private lateinit var btneliminar:Button
    private var id:String=""

    fun  consultarLibro(){
        if (id!=""){

            var request= JsonObjectRequest(
                Request.Method.GET, //método de la petición
                config.urllibro+id, //url
                null, //parámetros
                {response->
                    val gson= Gson()
                    val libro: libro =gson.fromJson(response.toString(), libro::class.java)
                    lblautor.setText(response.getString(libro.nombre_autor))
                    lbllibro.setText(response.getString(libro.titulo))
                    lbLisbn.setText(response.getString(libro.isbn))
                    lblgenero.setText(response.getString(libro.genero))
                    lblejemplares.setText(response.getInt("num_ejemplares_disponibles").toString())
                    lblejemplaresocupados.setText(response.getInt("num_ejemplares_ocupados").toString())

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)


        }
    }



    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_detalle_libro, container, false)
        lbllibro=view.findViewById(R.id.lbllibro)
        lblautor=view.findViewById(R.id.lblautor)
        lbLisbn=view.findViewById(R.id.lbLisbn)
        lblgenero=view.findViewById(R.id.lblgenero)
        lblejemplares=view.findViewById(R.id.lblejemplares)
        lblejemplaresocupados=view.findViewById(R.id.lblejemplaresocupados)
        lbldescripion=view.findViewById(R.id.lbldescripion)
        btnEditar=view.findViewById(R.id.btnEditar)
        btnEditar.setOnClickListener{editar()}
        btneliminar=view.findViewById(R.id.btneliminar)
        btneliminar.setOnClickListener{eliminar()}
        consultarLibro()

        return view
    }

fun editar(){
    var parametros = JSONObject()
    parametros.put("nombre_autor", lblautor.text.toString())
    parametros.put("titulo", lbllibro.text.toString())
    parametros.put("isbn", lbLisbn.text.toString())
    parametros.put("genero", lbLisbn.text.toString())
    parametros.put("num_ejemplares_disponibles", lblejemplares.text.toString())
    parametros.put("num_ejemplares_ocupados", lblejemplaresocupados.text.toString())

    var request = JsonObjectRequest(
        Request.Method.PUT, //metodo de la peticion
        config.urllibro + id, //url
        parametros, //parametros
        { response ->
            Toast.makeText(context, "Se actualizo correctamente", Toast.LENGTH_LONG).show()
        },
        { error ->
            Toast.makeText(context, "Error al actualizar", Toast.LENGTH_LONG).show()
        }//error en la petición
    )
    var queue = Volley.newRequestQueue(context)
    queue.add(request)
}

    fun eliminar(){
        if (id != "") {
            val request = JsonObjectRequest(
                Request.Method.DELETE, // Método DELETE para eliminar el recurso
                config.urllibro + id, // URL del recurso con el ID del libro a eliminar
                null, // No se envían parámetros en el cuerpo para DELETE
                { response ->
                    Toast.makeText(context, "Libro eliminado correctamente", Toast.LENGTH_LONG).show()
                    // Aquí puedes implementar lógica adicional después de eliminar el libro
                },
                { error ->
                    Toast.makeText(context, "Error al eliminar el libro", Toast.LENGTH_LONG).show()
                    // Manejo de errores en la petición DELETE
                }
            )
            val queue = Volley.newRequestQueue(context)
            queue.add(request)
        } else {
            Toast.makeText(context, "No hay id válido para eliminar el libro", Toast.LENGTH_LONG).show()
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment detalleLibroFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            detalleLibroFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}