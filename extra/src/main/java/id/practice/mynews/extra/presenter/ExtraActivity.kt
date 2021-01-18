package id.practice.mynews.extra.presenter

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import id.practice.mynews.core.ui.ArticleAdapter
import id.practice.mynews.extra.R
import id.practice.mynews.extra.databinding.ActivityExtraBinding
import id.practice.mynews.extra.di.extraModule
import id.practice.mynews.presentation.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class ExtraActivity : AppCompatActivity() {
    private val viewModel: ExtraViewModel by viewModel()
    private lateinit var binding: ActivityExtraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExtraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(extraModule)

        title = getString(R.string.favorite)

        val articleAdapter = ArticleAdapter()
        articleAdapter.onItemClick = {
            val intent = Intent(this,
                Class.forName("id.practice.mynews.presentation.detail.DetailActivity")
            )
            intent.putExtra(DetailActivity.EXTRA_ITEM, it)

            startActivity(intent)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
        }

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = articleAdapter
            itemAnimator = DefaultItemAnimator()
        }


        binding.swipeRefreshLayout.isRefreshing = true
        binding.extraComponentError.root.visibility = View.GONE

        viewModel.favorites.observe(this, {
            binding.swipeRefreshLayout.isRefreshing = false

            if (it.isNotEmpty()) {
                articleAdapter.setData(it)
                binding.topHeadlines.visibility = View.VISIBLE
            } else {
                showEmptyMessage()
                binding.topHeadlines.visibility = View.INVISIBLE
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite_option_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.localization_btn -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showEmptyMessage() {
        if (binding.extraComponentError.root.visibility == View.GONE) {
            binding.extraComponentError.root.visibility = View.VISIBLE
        }

        binding.extraComponentError.errorImage.setImageResource(R.drawable.no_result)
        binding.extraComponentError.errorTitle.text = getString(R.string.empty_title)
        binding.extraComponentError.errorMessage.text = getString(R.string.empty_message)
    }
}