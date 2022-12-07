package com.example.deborahrimei_3015579_trackingapp.user

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.deborahrimei_3015579_trackingapp.R
import com.example.deborahrimei_3015579_trackingapp.database.DatabaseOperations
import com.example.deborahrimei_3015579_trackingapp.database.DatabaseQueries
import com.example.deborahrimei_3015579_trackingapp.database.UtilsCast
import java.util.*


class UserProfileActivity : AppCompatActivity() {

    //variables layout
    lateinit var imageView: ImageView
    lateinit var captureButton: Button
    lateinit var nameEditText : EditText
    lateinit var identityEditText : EditText
    lateinit var numberHabits: TextView
    lateinit var totalCompleted: TextView
    lateinit var startDate: TextView
    

    //variables
    lateinit var user: User
    val REQUEST_IMAGE_CAPTURE = 0
    private val PERMISSION_REQUEST_CODE: Int = 101
    private var photoPath: String? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        //get variables view
        imageView = findViewById(R.id.profile_imageview_picture)
        nameEditText = findViewById(R.id.profile_et_name)
        identityEditText = findViewById(R.id.profile_et_identity)
        numberHabits = findViewById(R.id.profile_tv_habits)
        totalCompleted = findViewById(R.id.profile_tv_totalCompleted)
        startDate= findViewById(R.id.profile_tv_startDate)

        var userName :String
        var identity: String
        var userPicture : ByteArray
        var userStartDate: String

        // open database to retrieve information
        var dbo = DatabaseOperations(this)
        val cursor = dbo.getUser(dbo)
        with(cursor) {
            while (moveToNext()) {
                userName = getString(getColumnIndexOrThrow(DatabaseQueries.UserTable.COLUMN_USER_NAME))
                identity = getString(getColumnIndexOrThrow(DatabaseQueries.UserTable.COLUMN_USER_IDENTITY))
                userPicture =
                    getBlob(getColumnIndexOrThrow(DatabaseQueries.UserTable.COLUMN_USER_PICTURE))
                userStartDate = getString(getColumnIndexOrThrow(DatabaseQueries.UserTable.COLUMN_USER_TOTAL_COMPLETED))
                user = User(userName, identity, userPicture,0, 0)

                if(!userPicture.toString(Charsets.UTF_8).equals("Pic")){
                    imageView.setImageBitmap(UtilsCast.getImage(userPicture))}

                nameEditText.setText(userName)
                identityEditText.setText(identity)
                dbo.close()
            }
        }

        captureButton = findViewById(R.id.profile_btn_editPicture)
        captureButton.setOnClickListener(View.OnClickListener {
            if (checkPersmission()) takePicture() else requestPermission()
        })
    }

    private fun checkPersmission(): Boolean {
        return (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            ), PERMISSION_REQUEST_CODE
        )
    }

    private fun takePicture() {
//        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        val intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        val file: File = createFile()
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)

    }

//    private fun createFile(): File {
//        // Create an image file name
//        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//
//        return File.createTempFile(
//            "JPEG_${timeStamp}_",
//            ".jpg",
//            storageDir
//        ).apply {
//            // Save a file: path for use with ACTION_VIEW intents
//            photoPath = absolutePath
//        }
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> {
                Log.d("PHOTO", "RESULT CODE - $resultCode AND DATA ${data != null}")
                if (resultCode == Activity.RESULT_OK && data != null) {

                    var bitmap: Bitmap = data.extras?.get("data") as Bitmap
                    user.picture = UtilsCast.getBytes(bitmap)

                    var dbo = DatabaseOperations(this)
                    dbo.updateUser(dbo, user)
                    imageView.setImageBitmap(bitmap)

                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {

                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                ) {

                    takePicture()

                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }

            else -> {

            }
        }
    }
}