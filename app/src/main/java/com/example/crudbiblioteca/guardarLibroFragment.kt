package com.example.crudbiblioteca

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.crudbiblioteca.config.config
import java.lang.Exception

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
    private lateinit var textAutor:EditText
    private lateinit var txtIsbn:EditText
    private lateinit var txtGenero:EditText
    private lateinit var txtejemplares:EditText
    private lateinit var txtejemplaresocupados:EditText

    private lateinit var  id:String

    fun guardarLibro(){
        try {
            if (id==""){//se crea el libro
                //se crea la peticion
                val request= object :StringRequest(
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
        return inflater.inflate(R.layout.fragment_guardar_libro, container, false)
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