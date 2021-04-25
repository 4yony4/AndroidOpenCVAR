package com.eurekamps.arandroidtest1



import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.SurfaceView
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import org.opencv.android.*
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc


class MainActivity : AppCompatActivity(),CameraBridgeViewBase.CvCameraViewListener2
    /*CameraGLSurfaceView.CameraTextureListener
    CameraBridgeViewBase.CvCameraViewListener2*/  {
    val TAG = "MainActivity"
    lateinit var imageView2:ImageView
    protected var mCamera: CameraBridgeViewBase? = null
    private var decorView: View? = null

    companion object {
        const val SCREEN_WIDTH = 1280
        const val SCREEN_HEIGHT = 720
    }


    val arPermissions= arrayOf(Manifest.permission.CAMERA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initListener()
        //val viewCamara:View=findViewById(R.id.cameraBridgeViewBase)

        //mCamera?.setCvCameraViewListener(this)
        //mCamera?.cameraTextureListener=this

        requestPermissions(arPermissions, 1001);

        /*imageView2=findViewById(R.id.imageView2)
        OpenCVLoader.initDebug()

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.icon1)
        val bitmapRes=makeGray(bitmap)
        imageView2.setImageBitmap(bitmapRes)*/
    }

    private fun initView() {
        /*decorView = window.decorView
        decorView!!.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        mCamera=findViewById(R.id.javaCamera2View)
        mCamera!!.visibility = SurfaceView.VISIBLE
        mCamera!!.setMaxFrameSize(SCREEN_WIDTH, SCREEN_HEIGHT)
        mCamera?.setCameraPermissionGranted()*/

        mCamera=findViewById(R.id.javaCamera2View)
        mCamera?.setCameraPermissionGranted()

        //mSwitchDebugCamera.setOnCheckedChangeListener { _, isChecked -> mShowDebug = isChecked }
    }

    private fun initListener() {
        mCamera!!.setCvCameraViewListener(this)
    }



    override fun onResume() {
        //Super
        super.onResume()

        //Try to init
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mCallback)
    }

    //Create callback
    protected var mCallback: LoaderCallbackInterface = object : BaseLoaderCallback(this) {
        override fun onManagerConnected(status: Int) {
            //If not success, call base method
            if (status != SUCCESS)
                super.onManagerConnected(status)
            else {
                //Log.v(TAG,"ENTRO POR ENABLE CAMERA!!!!!!!!!!!!!!!!!!!!!!!!")
                //Enable camera if connected to library
                mCamera?.enableView()
            }
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1001){

        }

    }

    override fun onCameraViewStarted(width: Int, height: Int) {
        //Log.v(TAG, "-------------->>>>>>>>>>>>>>>>>    onCameraViewStarted" + width + "            " + height)
    }

    override fun onCameraViewStopped() {
        //Log.v(TAG, "-------------->>>>>>>>>>>>>>>>>    onCameraViewStarted")
    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame?): Mat {

        //Get edge from the image
        //Get edge from the image
        /*val result = Mat()
        Imgproc.Canny(inputFrame!!.rgba(), result, 70.0, 100.0)
        return result*/
        val HSV=Mat()
        val result=Mat()

        Imgproc.cvtColor(inputFrame!!.rgba(),HSV,Imgproc.COLOR_RGB2HSV)
        Core.inRange(HSV, Scalar(100.0,38.3,38.3), Scalar(100.0,100.0,100.0),result)
        return result
    }


    override fun onPause() {
        //Disable camera
        super.onPause()
        if (mCamera != null) mCamera!!.disableView()
    }

    override fun onDestroy() {
        //Disable camera
        super.onDestroy()
        if (mCamera != null) mCamera!!.disableView()
    }

}