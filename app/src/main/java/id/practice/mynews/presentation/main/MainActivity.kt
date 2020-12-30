package id.practice.mynews.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import id.practice.mynews.R
import id.practice.mynews.core.data.Resource
import id.practice.mynews.core.ui.ArticleAdapter
import id.practice.mynews.databinding.ActivityMainBinding
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

//        binding.btnExtra.setOnClickListener {
//            try {
//                installExtraModule()
//            } catch (e: Exception){
//                Toast.makeText(this, "Module not found", Toast.LENGTH_SHORT).show()
//            }
//        }
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

    private fun installExtraModule() {
        val splitInstallManager = SplitInstallManagerFactory.create(this)
        val moduleExtra = "extra"
        if (splitInstallManager.installedModules.contains(moduleExtra)) {
            moveToExtraActivity()
            Toast.makeText(this, "Open module", Toast.LENGTH_SHORT).show()
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule(moduleExtra)
                .build()
            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    Toast.makeText(this, "Success installing module", Toast.LENGTH_SHORT).show()
                    moveToExtraActivity()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error installing module", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun moveToExtraActivity() {
        startActivity(Intent(this, Class.forName("id.practice.mynews.extra.ExtraActivity")))
    }
}