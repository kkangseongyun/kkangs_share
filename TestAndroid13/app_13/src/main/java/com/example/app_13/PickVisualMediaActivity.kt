package com.example.app_13

import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.app_13.databinding.ActivityPickVisualMediaBinding

class PickVisualMediaActivity : AppCompatActivity() {
    lateinit var binding: ActivityPickVisualMediaBinding

    val datas = mutableListOf<Uri>()
    val adapter = PickAdapter(this, datas)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPickVisualMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = adapter


        //gallery request launcher..................
        val requestGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        )
        {
            try {
                if (it.data!!.data != null) {
                    Log.d("kkang", "select one..................")
                    datas.add(it.data!!.data!!)
                    adapter.notifyDataSetChanged()
                } else {
                    if (it.data!!.clipData != null) {
                        val mClipData: ClipData = it.data!!.clipData!!
                        Log.d("kkang", "select count: ${mClipData.itemCount}")
                        for (i in 0 until mClipData.itemCount) {
                            val item = mClipData.getItemAt(i)
                            datas.add(item.uri)
                        }
                        adapter.notifyDataSetChanged()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        binding.button.setOnClickListener {
            //gallery app........................
            val intent = Intent(
                Intent.ACTION_GET_CONTENT,//extra_allow_multiple 이려면 action 문자열이 action_get_content
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            requestGalleryLauncher.launch(intent)
        }

        // Registers a photo picker activity launcher in single-select mode.
        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                // Callback is invoked after the user selects a media item or closes the
                // photo picker.
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: $uri")
                    datas.clear()
                    datas.add(uri)
                    adapter.notifyDataSetChanged()
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }


        binding.button2.setOnClickListener {
            //PickVisualMedia
            // Launch the photo picker and allow the user to choose images and videos.
//            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))

            // Launch the photo picker and allow the user to choose only images.
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

            // Launch the photo picker and allow the user to choose only videos.
//            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))

            // Launch the photo picker and allow the user to choose only images/videos of a
            // specific MIME type, such as GIFs.
//            val mimeType = "image/gif"
//            pickMedia.launch(
//                PickVisualMediaRequest(
//                    ActivityResultContracts.PickVisualMedia.SingleMimeType(
//                        mimeType
//                    )
//                )
//            )
        }


        // Registers a photo picker activity launcher in multi-select mode.
        // In this example, the app allows the user to select up to 5 media files.
        val pickMultipleMedia =
            registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(3)) { uris ->
                // Callback is invoked after the user selects media items or closes the
                // photo picker.
                if (uris.isNotEmpty()) {
                    Log.d("PhotoPicker", "Number of items selected: ${uris.size}")
                    datas.clear()
                    uris.forEach {
                        datas.add(it)
                    }
                    adapter.notifyDataSetChanged()
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }
        binding.button3.setOnClickListener {
            //PickMultipleVisualMedia
            pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        }
        binding.button4.setOnClickListener {
            //EXTRA_PICK_IMAGES_MAX
            val maxNumPhotosAndVideos = 3
            val intent = Intent(MediaStore.ACTION_PICK_IMAGES)
            intent.putExtra(MediaStore.EXTRA_PICK_IMAGES_MAX, maxNumPhotosAndVideos)
            startActivityForResult(intent, 10)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            // Handle error
            return
        }
        when (requestCode) {
            10 -> {
                // Get photo picker response for multi select.
                var i = 0
                var currentUri: Uri
                datas.clear()
                while (i < data?.clipData!!.itemCount) {
                    currentUri = data.clipData!!.getItemAt(i).uri
                    datas.add(currentUri)
                    i++
                }
                adapter.notifyDataSetChanged()
                return
            }
        }
    }


}