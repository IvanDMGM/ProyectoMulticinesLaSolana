package net.azarquiel.multicineslasolana.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RatingBar
import androidx.appcompat.widget.SearchView

import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import net.azarquiel.multicineslasolana.R
import net.azarquiel.multicineslasolana.adapter.CustomAdapter
import net.azarquiel.multicineslasolana.model.Pelicula

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    companion object {
        const val TAG = "MulticineLaSolana"
    }

    private lateinit var searchView: SearchView
    private lateinit var adapter:CustomAdapter
    private lateinit var db: FirebaseFirestore
    private var peliculas: ArrayList<Pelicula> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = FirebaseFirestore.getInstance()
        initRV()
        setListener()

    }

    private fun initRV() {
        adapter = CustomAdapter(this, R.layout.rowpelicula)
        rvpeliculas.adapter = adapter
        rvpeliculas.layoutManager = LinearLayoutManager(this)
    }

    private fun setListener() {
        val docRef = db.collection("Pelicula")
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                documentToList(snapshot.documents)
                adapter.setPelicula(peliculas)
            } else {
                Log.d(TAG, "Current data: null")
            }
        }
    }

    private fun documentToList(documents: List<DocumentSnapshot>) {
        peliculas.clear()
        documents.forEach { d ->
            val Descripcion = if (d["Descripcion"]!=null) (d["Descripcion"] as String) else ""
            val Fecha = if (d["Fecha"]!=null) (d["Fecha"] as String) else ""
            val Poster = if (d["Poster"]!=null) (d["Poster"] as String) else ""
            val Titulo = if (d["Titulo"]!=null) (d["Titulo"] as String) else ""

            peliculas.add(Pelicula(Descripcion = Descripcion, Fecha = Fecha, Poster = Poster, Titulo = Titulo))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        // ************* <Filtro> ************

        //LUPA PARA BUSCAR
        val searchItem = menu.findItem(R.id.search)
        searchView = searchItem.actionView as SearchView
        searchView.setQueryHint("Search...")
        searchView.setOnQueryTextListener(this)
        // ************* </Filtro> ************

        return true
    }

    // ************* <Filtro> ************
    override fun onQueryTextChange(query: String): Boolean {
        val original = ArrayList<Pelicula>(peliculas)
        adapter.setPelicula(original.filter { pelicula -> pelicula.Titulo.contains(query) })
        return false
    }

    override fun onQueryTextSubmit(text: String): Boolean {
        return false
    }

    fun onClickPelicula(v: View){
        val peliculapulsada = v.tag as Pelicula
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("pelicula", peliculapulsada)
        startActivity(intent)
    }

}
