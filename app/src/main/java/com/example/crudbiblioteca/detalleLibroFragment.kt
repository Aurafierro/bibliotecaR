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
    private lateinit var lblEjemplaresocupados: TextView
    private lateinit var lbldescripion: TextView
    private lateinit var btnEditar: Button
    private lateinit var btneliminar:Button
    private var id:String="27a1fd54-1368-4e40-8e18-f9817f66f3c4"

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
                    lblEjemplaresocupados.setText(response.getInt("num_ejemplares_ocupados").toString())

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
        lblEjemplaresocupados=view.findViewById(R.id.lblejEmplaresocupados)
        lbldescripion=view.findViewById(R.id.lbldescripion)
        btnEditar=view.findViewById(R.id.btnEditar)
        btnEditar.setOnClickListener{editar()}
        btneliminar=view.findViewById(R.id.btneliminar)
        btneliminar.setOnClickListener{eliminar()}

        return view
    }
fun editar(){

}
    fun eliminar(){

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