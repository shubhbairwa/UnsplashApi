package com.example.unsplashapi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.unsplashapi.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
   /*  lateinit var connectivityManager: ConnectivityManager
    private lateinit var dialog: AlertDialog
*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      /*  // Initialize ConnectivityManager
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // Register BroadcastReceiver to monitor internet connection changes
        registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        // Initialize and show the dialog
        showInternetDialog()*/
    }


/*     val networkReceiver = object : BroadcastReceiver() {
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
        val builder = AlertDialog.Builder(this)
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
    }*/

    override fun onDestroy() {
        super.onDestroy()
        // Unregister the BroadcastReceiver when the activity is destroyed

    }
}