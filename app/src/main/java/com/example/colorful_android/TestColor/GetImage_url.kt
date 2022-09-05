package com.example.colorful_android.TestColor

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.SystemClock
import androidx.transition.Transition

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import java.io.File
import java.io.FileOutputStream
import java.util.*

class GetImage_url {

//    fun imageUrlToCacheFileAsync(context: Context, url: String): Single<File> {
//
//        return Single.create { emitter ->
//            Glide.with(context)
//                .asBitmap()
//                .load(url)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .into(object : CustomTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
//                    override fun onResourceReady(
//                        resource: Bitmap,
//                        transition: Transition<in Bitmap>?
//                    ) {
//                        val newFile = File(
//                            context.cacheDir.path,
//                            Random(SystemClock.currentThreadTimeMillis()).nextLong().toString()
//                        ).apply {
//                            createNewFile()
//                        }
//                        FileOutputStream(newFile).use {
//                            resource.compress(Bitmap.CompressFormat.JPEG, 100, it)
//                        }
//                        emitter.onSuccess(newFile)
//                    }
//
//                    override fun onLoadCleared(placeholder: Drawable?) {
////                        emitter.onError(ImageLoadException())
//                    }
//
//                    override fun onLoadFailed(errorDrawable: Drawable?) {
////                        emitter.onError(ImageLoadException())
//                    }
//                })
//        }
//    }

}