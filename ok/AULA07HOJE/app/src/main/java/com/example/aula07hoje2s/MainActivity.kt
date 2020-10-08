package com.example.aula07hoje2s

import android.graphics.Bitmap
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}


fun btdDownloadOnClick(view: View) {

}

    class TaskDownloadImagen ( var imgPapaiPIG: ImageView, var imgPepaPIG: ImageView, var progress: ProgressBar)
        : AsyncTask<String, Int, List<Bitmap>>() {
        override fun doInBackground(vararg params: String?): List<Bitmap> {
            TODO("Not yet implemented")

        }
    }