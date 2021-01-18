package id.practice.mynews.presentation.detail

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import id.practice.mynews.R
import id.practice.mynews.core.domain.model.Article
import id.practice.mynews.core.utils.Tools
import id.practice.mynews.databinding.ActivityDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel


class DetailActivity : AppCompatActivity() {
    private val viewModel: DetailViewModel by viewModel()
    private lateinit var binding: ActivityDetailBinding
    private var favoriteState: Boolean = false

    companion object {
        const val EXTRA_ITEM = "extra_item"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
        )

        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = ""

        val extraItem = intent.getParcelableExtra<Article>(EXTRA_ITEM)

        if (extraItem != null) {
            viewModel.id = extraItem.articleId
            viewModel.getArticleByID().observe(this, {
                val requestOptions = RequestOptions()
                requestOptions.error(Tools.randomDrawableColor)

                updateFavoriteUI(it?.isFavorite == true)

                it?.urlToImage = it?.urlToImage ?: ""
                Glide.with(this)
                    .load(it?.urlToImage)
                    .apply(requestOptions)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.img)

                binding.title.text = it.title

                it.content?.let { content ->
                    val newContent = content.substring(0, content.lastIndexOf(" "))
                    binding.content.text = newContent.substring(0, newContent.lastIndexOf(" "))
                }

                it.publishedAt?.let { publishedAt ->
                    binding.date.text = Tools.dateFormat(publishedAt)
                    binding.time.text = String.format(getString(R.string.time_text),
                        it.sourceName, it.author, Tools.dateToTimeFormat(publishedAt)
                    )
                }

                it.url.let { url ->
                    binding.readMore.setOnClickListener {
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(browserIntent)
                    }
                }
            })

        }
    }

    private fun updateFavoriteUI(isFavorite: Boolean) {
        favoriteState = isFavorite
        invalidateOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_option_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
            R.id.action_favorite -> {
                viewModel.setFavorite(!favoriteState)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val menuItem = menu?.findItem(R.id.action_favorite)

        if (favoriteState) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorited)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite)
        }

        return super.onPrepareOptionsMenu(menu)
    }
}