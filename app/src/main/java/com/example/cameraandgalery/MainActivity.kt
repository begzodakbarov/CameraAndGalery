package com.example.cameraandgalery

import android.app.Instrumentation.ActivityResult
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.cameraandgalery.adapters.MyPagerAdapter
import com.example.cameraandgalery.databinding.ActivityMainBinding
import com.example.cameraandgalery.databinding.ItemDialogBinding
import com.example.cameraandgalery.db.MyDbHelper
import com.example.cameraandgalery.models.MyImage
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    lateinit var myDbHelper: MyDbHelper
    lateinit var myPagerAdapter: MyPagerAdapter
    private val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        myDbHelper = MyDbHelper(this)
        myPagerAdapter = MyPagerAdapter()
        myPagerAdapter.list.addAll(myDbHelper.getAllImages())

        binding.apply {
            myViewPager.adapter = myPagerAdapter
            btnAdd.setOnClickListener {
            showDialog()
            }
        }
    }
    lateinit var itemDialog:ItemDialogBinding
        fun showDialog() {
            val dialog = BottomSheetDialog(this)
             itemDialog = ItemDialogBinding.inflate(layoutInflater)
            dialog.setContentView(itemDialog.root)
            dialog.show()
            itemDialog.videoDialog.setOnClickListener {
                myImageContent.launch("video/*")
            }
            itemDialog.btnSave.setOnClickListener {
                if (filePath!="" && itemDialog.edtDialog.text.isNotBlank()){
                    val myImage = MyImage(itemDialog.edtDialog.text.toString(),filePath)
                    myDbHelper.addImage(myImage)
                    Toast.makeText(this, "Saqlandi", Toast.LENGTH_SHORT).show()
                    dialog.cancel()
                    myPagerAdapter.list.add(myImage)
                    myPagerAdapter.notifyDataSetChanged()

                }else{
                    Toast.makeText(this, "Avval ma'lumotlarni to'liq kiriting", Toast.LENGTH_SHORT).show()
                }
            }
        }
             var filePath = ""
             val myImageContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
                    if (it == null) {
                        return@registerForActivityResult
                    }
                    Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "$it: ")

                    val tittle = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                    val inputStream = contentResolver.openInputStream(it)
                    val file = File(filesDir, "$tittle.mp4")
                    val fileOutputStream = FileOutputStream(file)
                    inputStream?.copyTo(fileOutputStream)
                    inputStream?.close()
                    fileOutputStream.close()

                    filePath = file.absolutePath
                  itemDialog.videoDialog.setVideoURI(it)
                 itemDialog.videoDialog.start()
                }
        }
