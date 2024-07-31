package com.example.crudbiblioteca

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject

class adapter(
    private val recCard: JSONArray,
    private val context: Context
) : RecyclerView.Adapter<adapter.MyHolder>() {

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tituloo: TextView = itemView.findViewById(R.id.tituloo)
        val autors: TextView = itemView.findViewById(R.id.autors)
        val edits: ImageView = itemView.findViewById(R.id.edits)
        val delate: ImageView = itemView.findViewById(R.id.delate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.activity_recycler_libro, parent, false)
        return MyHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val libro = recCard.getJSONObject(position)

        holder.tituloo.text = libro.optString("titulo", "Título no disponible")
        holder.autors.text = libro.optString("nombre_autor", "Autor no disponible")

        holder.edits.setOnClickListener {
            onclickEditar?.invoke(libro)
        }

        holder.delate.setOnClickListener {
            onclickEliminar?.invoke(libro)
        }
    }

    override fun getItemCount(): Int {
        return recCard.length()
    }

    var onclickEditar: ((JSONObject) -> Unit)? = null
    var onclickEliminar: ((JSONObject) -> Unit)? = null
}
