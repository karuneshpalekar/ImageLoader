package com.karunesh.imageloader.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.karunesh.imageloader.R
import com.karunesh.imageloader.databinding.ItemLayoutBinding
import com.karunesh.imageloader.domain.model.DataItem
import com.karunesh.imageloaderx.ImageLoaderX
import com.karunesh.imageloaderx.core.ImageLoader
import com.karunesh.imageloaderx.util.centerCrop
import com.karunesh.imageloaderx.util.fetch
import com.karunesh.imageloaderx.util.whenError
import com.karunesh.imageloaderx.util.withPlaceholder

class HomeAdapter(private val imageLoader: ImageLoader) :
    PagingDataAdapter<DataItem, HomeAdapter.DataViewHolder>(DATA_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(imageLoader = imageLoader, binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    class DataViewHolder(
        private val imageLoader: ImageLoader,
        private val binding: ItemLayoutBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItem) {
            val white = ContextCompat.getColor(binding.imageView.context, R.color.white)

            val url = with(data.thumbnail) {
                this?.let { thumbnail ->
                    "${thumbnail.domain}/${thumbnail.basePath}/${thumbnail.qualities?.get(thumbnail.qualities.size - 1)}/${thumbnail.key}"
                }
            }

            ImageLoaderX.Builder(binding.imageView, imageLoader)
                .url(url)
                .centerCrop()
                .withPlaceholder(R.drawable.ic_image)
                .whenError(R.drawable.ic_image_remove, white)
                .build()
                .load()

            binding.executePendingBindings()
        }
    }

    companion object {
        private val DATA_COMPARATOR = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean =
                oldItem == newItem
        }
    }
}