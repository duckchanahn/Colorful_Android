package com.colorful.colorful_android.TestColor

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