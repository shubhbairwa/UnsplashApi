package com.example.unsplashapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashapi.databinding.UnsplashPhotoLoadStateFooterBinding

class UnsplashLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<UnsplashLoadStateAdapter.LoadStateViewHolder>() {


    inner class LoadStateViewHolder(private val binding: UnsplashPhotoLoadStateFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.buttonRetry.setOnClickListener {
                retry.invoke()
            }

        }


        fun bind(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                buttonRetry.isVisible = loadState !is LoadState.Loading
                textViewError.isVisible = loadState !is LoadState.Loading
            }
        }

    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)

    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            UnsplashPhotoLoadStateFooterBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }
}