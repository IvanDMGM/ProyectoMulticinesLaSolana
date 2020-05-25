package net.azarquiel.multicineslasolana.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rowpelicula.view.*
import net.azarquiel.multicineslasolana.model.Pelicula

/**
 * Created by pacopulido on 9/10/18.
 */
class CustomAdapter(val context: Context,
                    val layout: Int
                    ) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private var dataList: List<Pelicula> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    internal fun setPelicula(peliculas: List<Pelicula>) {
        this.dataList = peliculas
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Pelicula){
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            itemView.tvTitulo.text = dataItem.Titulo
            Picasso.get().load(dataItem.Poster).into(itemView.ivpelicula)
            itemView.tvFecha.text = dataItem.Fecha
            itemView.tag = dataItem
        }

    }
}