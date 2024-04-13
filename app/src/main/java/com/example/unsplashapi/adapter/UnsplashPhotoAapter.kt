package com.example.unsplashapi.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.unsplashapi.data.UnsplashPhoto
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashapi.R
import com.example.unsplashapi.databinding.ItemUnsplashPhotoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL


private const val TAG = "UnsplashPhotoAapter"

class UnsplashPhotoAdapter :
    PagingDataAdapter<UnsplashPhoto, UnsplashPhotoAdapter.PhotoViewHolder>(PHOTO_COMPARATOR) {

    inner class PhotoViewHolder(private val binding: ItemUnsplashPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: UnsplashPhoto) {
            binding.apply {
                // Set placeholder image while loading
                imageView.setImageResource(R.drawable.placeholder_image)

                // Load image asynchronously
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val bitmap = loadImages(photo.urls.regular.toString())
                        withContext(Dispatchers.Main) {
                            imageView.setImageBitmap(bitmap)
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Exception loading image: $e")
                        // If an error occurs, set error image
                        withContext(Dispatchers.Main) {
                            imageView.setImageResource(R.drawable.ic_error)
                        }
                    }
                }
                textViewUserName.text = photo.user.username



            }
        }
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<UnsplashPhoto>() {
            override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean =
                oldItem.id == newItem.id


            override fun areContentsTheSame(
                oldItem: UnsplashPhoto,
                newItem: UnsplashPhoto
            ): Boolean = oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            ItemUnsplashPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun loadImages(imageUrl: String): Bitmap? {
        return try {
            val conn = URL(imageUrl).openConnection()
            conn.connect()
            val inputStream = conn.getInputStream()
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            bitmap


        } catch (e: Exception) {
            Log.e(TAG, "Exception $e")
            null
        }
    }

}