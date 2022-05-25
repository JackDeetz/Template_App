package com.zybooks.templateapp

import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts

class ImplicitIntentExample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_implicit_intent_example)




    }



    fun dialButtonClicked(view: View) {
        val phoneNumber = Uri.parse("tel:111-222-3333")
        val intent = Intent(Intent.ACTION_DIAL, phoneNumber)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }

    }

    fun mapAddressButtonClicked(view: View) {
        val location = Uri.parse("geo:0,0?q=1600+Pennsylvania+Ave+NW,+Washington,+DC")
        val intent = Intent(Intent.ACTION_VIEW, location)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    fun implicitIntentChooserButtonClicked(view: View) {
        val intent = Intent(Intent.ACTION_SEND)

// Supply extra that is plain text
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Helpful website")
        intent.putExtra(Intent.EXTRA_TEXT, "https://stackoverflow.com/")

// If at least one app can handle intent, allow user to choose
        if (intent.resolveActivity(packageManager) != null) {
            val chooser = Intent.createChooser(intent, "Share URL")
            startActivity(chooser)
        }

    }
    fun getImageButtonClicked(view: View) {
        getImageContent.launch("image/*")
    }

    private val getImageContent = registerForActivityResult(
        ActivityResultContracts.GetContent()) { uri: Uri? ->
        val src = ImageDecoder.createSource(contentResolver, uri!!)
        val bitmap = ImageDecoder.decodeBitmap(src)
        val imageView = findViewById<ImageView>(R.id.imageViewShowGetImage)
        imageView.setImageBitmap(bitmap)
    }

    //doesn't work on api 25, Image Decoder still works.
    fun getImageDeprecatedButtonClicked(view: View) {
        val getImageContent = registerForActivityResult(
            ActivityResultContracts.GetContent()) { uri: Uri? ->
                val bitmap = if (Build.VERSION.SDK_INT < 28) {
                    @Suppress("DEPRECATION")
                    MediaStore.Images.Media.getBitmap(contentResolver, uri)
                }
                else {
                    val src = ImageDecoder.createSource(contentResolver, uri!!)
                    ImageDecoder.decodeBitmap(src)
                }
                val imageView = findViewById<ImageView>(R.id.imageViewShowGetImage)
                imageView.setImageBitmap(bitmap)
            }
    }

}