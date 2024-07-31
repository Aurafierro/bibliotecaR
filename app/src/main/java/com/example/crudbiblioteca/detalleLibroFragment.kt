package com.example.crudbiblioteca

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.crudbiblioteca.config.config
import com.example.crudbiblioteca.models.libro
import com.google.gson.Gson
import org.json.JSONObject

class detalleLibroFragment : Fragment() {

    private lateinit var lbllibro: EditText
    private lateinit var lblautor: EditText
    private lateinit var lbLisbn: EditText
    private lateinit var lblgenero: EditText
    private lateinit var lblejemplares: EditText
    private lateinit var lblejemplaresocupados: EditText
    private lateinit var lbldescripion: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btneliminar: Button
    private var id_libro: String? = null

    fun consultarLibro() {
        id_libro?.let {
            val request = JsonObjectRequest(
                Request.Method.GET,
                config.urllibro + it,
                null,
                { response ->
                    val gson = Gson()
                    val libro: libro = gson.fromJson(response.toString(), libro::class.java)
                    lblautor.setText(response.optString(libro.nombre_autor, "Autor no disponible"))
                    lbllibro.setText(response.optString(libro.titulo, "Título no disponible"))
                    lbLisbn.setText(response.optString(libro.isbn, "ISBN no disponible"))
                    lblgenero.setText(response.optString(libro.genero, "Género no disponible"))
                    lblejemplares.setText(response.optInt("num_ejemplares_disponibles").toString())
                    lblejemplaresocupados.setText(response.optInt("num_ejemplares_ocupados").toString())
                },
                { error ->
                    Toast.makeText(context, "Error al consultar", Toast.LENGTH_LONG).show()
                }
            )
            val queue = Volley.newRequestQueue(context)
            queue.add(request)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id_libro = it.getString("id_libro")
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detalle_libro, container, false)

        lbllibro = view.findViewById(R.id.txtTitulos)
        lblautor = view.findViewById(R.id.txtAutors)
        lbLisbn = view.findViewById(R.id.txtIsbns)
        lblgenero = view.findViewById(R.id.txtGeneros)
        lblejemplares = view.findViewById(R.id.txtejemplaress)
        lblejemplaresocupados = view.findViewById(R.id.txtejemplaresocupadoss)
        lbldescripion = view.findViewById(R.id.txtdescripciones)
        btnGuardar = view.findViewById(R.id.btnGuardar)
        btneliminar = view.findViewById(R.id.btnEditar) // Cambiado a btnEliminar

        btnGuardar.setOnClickListener { editar() }
        btneliminar.setOnClickListener { id_libro?.let { eliminar(it) } }

        id_libro?.let {
            consultarLibro()
        }

        return view
    }

    fun editar() {
        id_libro?.let {
            val parametros = JSONObject().apply {
                put("nombre_autor", lblautor.text.toString())
                put("titulo", lbllibro.text.toString())
                put("isbn", lbLisbn.text.toString())
                put("genero", lblgenero.text.toString())
                put("num_ejemplares_disponibles", lblejemplares.text.toString())
                put("num_ejemplares_ocupados", lblejemplaresocupados.text.toString())
            }

            val request = JsonObjectRequest(
                Request.Method.PUT,
                config.urllibro + it,
                parametros,
                { response ->
                    Toast.makeText(context, "Se actualizó correctamente", Toast.LENGTH_LONG).show()
                },
                { error ->
                    Toast.makeText(context, "Error al actualizar", Toast.LENGTH_LONG).show()
                }
            )
            val queue = Volley.newRequestQueue(context)
            queue.add(request)
        }
    }

    fun eliminar(id: String) {
        val request = JsonObjectRequest(
            Request.Method.DELETE,
            config.urllibro + id,
            null,
            { response ->
                Toast.makeText(context, "Libro eliminado correctamente", Toast.LENGTH_LONG).show()
                // Implementa lógica adicional aquí si es necesario
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
        @JvmStatic
        fun newInstance(id_libro: String) =
            detalleLibroFragment().apply {
                arguments = Bundle().apply {
                    putString("id_libro", id_libro)
                }
            }
    }
}
