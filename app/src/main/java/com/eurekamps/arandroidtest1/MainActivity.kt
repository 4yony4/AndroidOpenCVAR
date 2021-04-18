package com.eurekamps.arandroidtest1



import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc


class MainActivity : AppCompatActivity() {
    lateinit var imageView2:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView2=findViewById(R.id.imageView2)
        OpenCVLoader.initDebug()

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.icon1)
        val bitmapRes=makeGray(bitmap)
        imageView2.setImageBitmap(bitmapRes)



    }

    fun makeGray(bitmap: Bitmap) : Bitmap {

        // Create OpenCV mat object and copy content from bitmap
        val mat = Mat()

        Utils.bitmapToMat(bitmap, mat)
        //Utils.bitmapToMat(bitmap, mat)

        // Convert to grayscale
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2GRAY)

        // Make a mutable bitmap to copy grayscale image
        val grayBitmap = bitmap.copy(bitmap.config, true)
        Utils.matToBitmap(mat, grayBitmap)

        return grayBitmap
    }
}