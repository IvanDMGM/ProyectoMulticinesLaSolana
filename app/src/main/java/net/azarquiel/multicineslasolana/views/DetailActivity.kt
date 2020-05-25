package net.azarquiel.multicineslasolana.views

import android.os.Bundle
import android.widget.RatingBar
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil


import net.azarquiel.multicineslasolana.R

import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import net.azarquiel.multicineslasolana.databinding.ActivityDetailBinding

import net.azarquiel.multicineslasolana.model.Pelicula



class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val pelicula= intent.getSerializableExtra("pelicula") as Pelicula
        var binding: ActivityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        binding.pelicula= pelicula
        rbEstrellas.rating = 2.5F/2



    }

}