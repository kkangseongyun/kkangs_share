package com.example.app_13

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app_13.databinding.ItemPickBinding

class PickHolder(val binding: ItemPickBinding) : RecyclerView.ViewHolder(binding.root)

class PickAdapter(val context: Context, val datas: MutableList<Uri>) : RecyclerView.Adapter<PickHolder>() {

    override fun getItemCount(): Int {
        return datas.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PickHolder {
        return PickHolder(ItemPickBinding.inflate(
            LayoutInflater.from(
                parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PickHolder, position: Int) {
        try {
            val option = BitmapFactory.Options()
            option.inSampleSize = 10
            // 이미지 로딩
            var inputStream = context.contentResolver.openInputStream(datas[position])
            val bitmap = BitmapFactory.decodeStream(inputStream, null, option)
            inputStream!!.close()
            bitmap?.let {
                holder.binding.itemImageView.setImageBitmap(bitmap)
            } ?: let {
                Log.d("kkang", "${datas[position]} ... bitmap null")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}