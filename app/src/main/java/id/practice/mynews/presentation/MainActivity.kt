package id.practice.mynews.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
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

        with (binding.rv) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = articleAdapter
            itemAnimator = DefaultItemAnimator()
        }

        viewModel.articles.observe(this, {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> return@observe
                    is Resource.Success -> {
                        articleAdapter.setData(it.data)
                    }
                    is Resource.Error -> return@observe
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