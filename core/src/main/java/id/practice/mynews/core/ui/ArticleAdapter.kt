package id.practice.mynews.core.ui

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import id.practice.mynews.core.R
import id.practice.mynews.core.databinding.ItemArticleRowBinding
import id.practice.mynews.core.domain.model.Article
import id.practice.mynews.core.utils.Tools
import java.util.*

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ListViewHolder>() {

    private var listData = ArrayList<Article>()
    var onItemClick: ((Article) -> Unit)? = null

    fun setData(newListData: List<Article>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_article_row,
                parent,
                false
            )
        )

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemArticleRowBinding.bind(itemView)
        fun bind(data: Article) {

            with(binding) {
                title.text = data.title
                time.text = data.publishedAt?.let { Tools.dateToTimeFormat(it) }
                author.text = data.author

                val requestOptions = RequestOptions()
                requestOptions.placeholder(Tools.randomDrawableColor)
                requestOptions.error(Tools.randomDrawableColor)
                requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
                requestOptions.centerCrop()

                data.urlToImage = data.urlToImage ?: ""

                Glide.with(itemView.context)
                    .load(data.urlToImage)
                    .apply(requestOptions)
                    .listener(object : RequestListener<Drawable?> {
                        override fun onLoadFailed(
                            @Nullable e: GlideException?,
                            model: Any,
                            target: Target<Drawable?>,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressLoadPhoto.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any,
                            target: Target<Drawable?>,
                            dataSource: DataSource,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressLoadPhoto.visibility = View.GONE
                            return false
                        }
                    })
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(img)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}