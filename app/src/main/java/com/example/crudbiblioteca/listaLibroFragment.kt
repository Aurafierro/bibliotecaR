package com.example.crudbiblioteca

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import com.android.volley.Request
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.Volley
import com.example.crudbiblioteca.config.config

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [listaLibroFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class listaLibroFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var view: View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view = inflater. inflate (R.layout.fragment_lista_libro, container, false)

        cargar_libro()
        return  view
    }
    private fun cargar_libro() {
        val request = JsonArrayRequest(
            Request.Method.GET,
            config.urllibro,
            null,
            { response ->
                val recycler = view.findViewById<RecyclerView>(R.id.listaLibro)
                recycler.layoutManager = LinearLayoutManager(requireContext())
                val adapter = adapter(response, requireContext())

                adapter.onclickEditar = { libro ->
                    val bundle = Bundle().apply {
                        putString("id_libro", libro.getString("id_libro"))
                    }
                    val fragment = detalleLibroFragment().apply {
                        arguments = bundle
                    }
                    requireFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit()
                }

                adapter.onclickEliminar = { libro ->
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setMessage("Desea eliminar este Registro")
                        .setPositiveButton("Si") { _, _ ->
                            EliminarLibro(libro.getString("id_libro")) {
                                cargar_libro() // Actualiza la lista después de eliminar
                            }
                        }
                        .setNegativeButton("No", null)
                        .show()
                }
                recycler.adapter = adapter
            },
            { error ->
                Toast.makeText(context, "Error en la consulta", Toast.LENGTH_LONG).show()
            }
        )
        val queue = Volley.newRequestQueue(context)
        queue.add(request)
    }

    fun EliminarLibro(id: String, onSuccess:() -> Unit){

        val request = JsonObjectRequest(
            Request.Method.DELETE, // Método DELETE para eliminar el recurso
            config.urllibro + id, // URL del recurso con el ID del libro a eliminar
            null, // No se envían parámetros en el cuerpo para DELETE
            { response ->
                Toast.makeText(context, "Libro eliminado correctamente", Toast.LENGTH_LONG).show()
                // Aquí puedes implementar lógica adicional después de eliminar el libro
                onSuccess()
            },
            { error ->
                Toast.makeText(context, "Error al eliminar el libro", Toast.LENGTH_LONG).show()
                // Manejo de errores en la petición DELETE
            }
        )
        val queue = Volley.newRequestQueue(context)
        queue.add(request)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment listaLibroFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            listaLibroFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}