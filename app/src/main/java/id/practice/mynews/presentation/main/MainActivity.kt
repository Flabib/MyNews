package id.practice.mynews.presentation.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import id.practice.mynews.R
import id.practice.mynews.core.data.Resource
import id.practice.mynews.core.ui.ArticleAdapter
import id.practice.mynews.databinding.ActivityMainBinding
import id.practice.mynews.presentation.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val articleAdapter = ArticleAdapter()
        articleAdapter.onItemClick = {
            val intent = Intent(this, DetailActivity::class.java)
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

        viewModel.articles.observe(this, {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> {
                        binding.swipeRefreshLayout.isRefreshing = true
                        binding.componentError.root.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        articleAdapter.setData(it.data)
                        binding.swipeRefreshLayout.isRefreshing = false
                        binding.topHeadlines.visibility = View.VISIBLE

                        if (it.data == null) {
                            showErrorMessage(
                                R.drawable.oops, getString(R.string.template_oops_title),
                                String.format(getString(R.string.template_oops_message), it.message)
                            )
                        }
                    }
                    is Resource.Error -> {
                        binding.topHeadlines.visibility = View.INVISIBLE
                        binding.swipeRefreshLayout.isRefreshing = false
                        showErrorMessage(
                            R.drawable.no_result, getString(R.string.template_empty_title),
                            String.format(getString(R.string.template_empty_message), it.message)
                        )
                    }
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_option_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.localization_btn -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
            R.id.favorite_btn -> {
                val uri = Uri.parse("mynews://extra")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showErrorMessage(imageView: Int, title: String, message: String) {
        if (binding.componentError.root.visibility == View.GONE) {
            binding.componentError.root.visibility = View.VISIBLE
        }

        binding.componentError.errorImage.setImageResource(imageView)
        binding.componentError.errorTitle.text = title
        binding.componentError.errorMessage.text = message
        binding.componentError.btnRetry.setOnClickListener { }
    }
}