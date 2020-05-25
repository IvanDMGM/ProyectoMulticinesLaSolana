package net.azarquiel.multicineslasolana.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import net.azarquiel.multicineslasolana.R

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String) {
    Picasso.get().load(url).into(this)
}
