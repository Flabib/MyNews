package id.practice.mynews.extra.presenter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import id.practice.mynews.core.data.Resource
import id.practice.mynews.core.ui.ArticleAdapter
import id.practice.mynews.extra.R
import id.practice.mynews.extra.databinding.ActivityExtraBinding
import id.practice.mynews.extra.di.extraModule
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

        val articleAdapter = ArticleAdapter()
        articleAdapter.onItemClick = {
            Toast.makeText(this, it.title, Toast.LENGTH_SHORT).show()
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
                            showErrorMessage(R.drawable.oops, "Oops..",
                                """
                                Network failure, Please Try Again!
                                
                                ${it.message}
                                """.trimIndent()
                            )
                        }
                    }
                    is Resource.Error -> {
                        binding.topHeadlines.visibility = View.INVISIBLE
                        binding.swipeRefreshLayout.isRefreshing = false
                        showErrorMessage(
                            R.drawable.no_result, "No Result",
                            """
                            Please Try Again!
                            
                            ${it.message}
                            """.trimIndent()
                        )
                    }
                }
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