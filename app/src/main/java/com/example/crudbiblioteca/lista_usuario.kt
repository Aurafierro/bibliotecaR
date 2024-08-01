package com.example.crudbiblioteca

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

import com.example.crudbiblioteca.config.config

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [lista_usuario.newInstance] factory method to
 * create an instance of this fragment.
 */
class lista_usuario : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_lista_usuario, container, false)


        cargar_usuario()

        return view
    }


    private fun cargar_usuario() {
        val request = JsonArrayRequest(
            Request.Method.GET,
            config.urlusuario,
            null,
            { response ->
                val recycler = requireView().findViewById<RecyclerView>(R.id.listaUsuario)
                recycler.layoutManager = LinearLayoutManager(requireContext())
                val adapter = adapter(response, requireContext())

                adapter.onclickEditar = { usuario ->
                    val bundle = Bundle().apply {
                        putString("id_usuario", usuario.getString("id_usuario"))
                    }
                    val fragment = fragment_detalle_usuario().apply {
                        arguments = bundle
                    }
                    requireFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit()
                }

                adapter.onclickEliminar = { usuario ->
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setMessage("Desea eliminar este Registro")
                        .setPositiveButton("Si") { _, _ ->
                            EliminarUsuario(usuario.getString("id_usuario")) {
                                cargar_usuario() // Actualiza la lista después de eliminar
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

    fun EliminarUsuario(id: String, onSuccess: () -> Unit) {
        val request = JsonObjectRequest(
            Request.Method.DELETE,
            config.urlusuario + id,
            null,
            { response ->
                Toast.makeText(context, "Usuario eliminado correctamente", Toast.LENGTH_LONG).show()
                onSuccess()
            },
            { error ->
                Toast.makeText(context, "Error al eliminar el usuario", Toast.LENGTH_LONG).show()
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
         * @return A new instance of fragment lista_usuario.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            lista_usuario().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}