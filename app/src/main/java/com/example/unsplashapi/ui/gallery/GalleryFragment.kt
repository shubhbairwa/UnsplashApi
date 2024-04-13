package com.example.unsplashapi.ui.gallery

import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.example.unsplashapi.R
import com.example.unsplashapi.adapter.UnsplashLoadStateAdapter
import com.example.unsplashapi.adapter.UnsplashPhotoAdapter
import com.example.unsplashapi.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.

 * create an instance of this fragment.
 */

@AndroidEntryPoint
class GalleryFragment : Fragment() {
    private val viewModel by viewModels<GalleryViewModel>()
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!


    lateinit var connectivityManager: ConnectivityManager
    private lateinit var dialog: AlertDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGalleryBinding.bind(view)
        // Initialize ConnectivityManager
        connectivityManager =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // Register BroadcastReceiver to monitor internet connection changes
        requireActivity().registerReceiver(
            networkReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )

        // Initialize and show the dialog
        showInternetDialog()

        val adapter = UnsplashPhotoAdapter()

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = UnsplashLoadStateAdapter { adapter.retry() },
                footer = UnsplashLoadStateAdapter { adapter.retry() },

                )
        }

        if (isInternetAvailable()) {
            viewModel.photos.observe(viewLifecycleOwner) {
                adapter.submitData(viewLifecycleOwner.lifecycle, it)
            }

        }


    }


    val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (!isInternetAvailable()) {
                // If internet connection is lost, show the dialog
                dialog.show()
            } else {
                // If internet connection is restored, dismiss the dialog
                dialog.dismiss()
            }
        }
    }

    fun isInternetAvailable(): Boolean {
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun showInternetDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("No Internet Connection")
        builder.setMessage("Please check your internet connection and try again.")
        builder.setPositiveButton("Retry") { _: DialogInterface, _: Int ->
            if (isInternetAvailable()) {
                dialog.dismiss()
            }
        }
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        requireActivity().unregisterReceiver(networkReceiver)
    }


}