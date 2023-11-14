package com.example.chanluliao_project1

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.AsyncTask

// This is the code to compute the HeartRate using a real android phone
// But I don't have a real phone so I comment all the code
// The code starts from 73 lines.

//public fun computeRate(videoUri: Uri, context: Context):String{
//    var retriever = MediaMetadataRetriever()
//    var frameList = ArrayList<Bitmap>()
//    var result:String ?= null
//
//    try {
//
//        retriever.setDataSource(context, videoUri)
//        var duration =
//            retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_FRAME_COUNT)
//        var aduration = duration!!.toInt()
//        var i = 10
//        while (i < aduration) {
//            val bitmap = retriever.getFrameAtIndex(i)
//            frameList.add(bitmap!!)
//            i += 5
//        }
//    } catch (m_e: Exception) {
//    } finally {
//        retriever?.release()
//        var redBucket: Long = 0
//        var pixelCount: Long = 0
//        val a = mutableListOf<Long>()
//        for (i in frameList) {
//            redBucket = 0
//            for (y in 450 until 650) {
//                for (x in 150 until 350) {
//                    val c: Int = i.getPixel(x, y)
//                    pixelCount++
//                    redBucket += Color.red(c) + Color.blue(c) + Color.green(c)
//                }
//            }
//            a.add(redBucket)
//        }
//        val b = mutableListOf<Long>()
//        for (i in 0 until a.lastIndex - 5) {
//            var temp =
//                (a.elementAt(i) + a.elementAt(i + 1) + a.elementAt(i + 2) + a.elementAt(
//                    i + 3
//                ) + a.elementAt(
//                    i + 4
//                )) / 4
//            b.add(temp)
//        }
//        var x = b.elementAt(0)
//        var count = 0
//        for (i in 1 until b.lastIndex) {
//            var p = b.elementAt(i.toInt())
//            if ((p - x) > 3500) {
//                count = count + 1
//            }
//            x = b.elementAt(i.toInt())
//        }
//        val rate = ((count.toFloat() / 45) * 60).toInt()
//        result = (rate / 2).toString()
//    }
//    return result
//}
//
class SlowTask(private val videoUri: Uri, private val context: Context) :
    AsyncTask<String, String, String?>() {

    override fun doInBackground(vararg params: String?): String? {
        var retriever = MediaMetadataRetriever()
        var frameList = ArrayList<Bitmap?>()
        var result:String ?= null

        try {
            retriever.setDataSource(context, videoUri)
//            // this is for real phone
//            val duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_FRAME_COUNT)
//            val totalFrames = duration?.toLong()?.div(1000)?.toInt() ?: 0
//
//            for (i in 1000 until totalFrames step 5000) {
//                val bitmap = retriever.getFrameAtTime(i.toLong() * 1000, MediaMetadataRetriever.OPTION_CLOSEST)
//                frameList.add(bitmap)
//            }
            val duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_FRAME_COUNT)?.toInt() ?: 0
            val frameInterval = 5
//            var i = 10
//            while(i < duration )
            for (i in 10 until duration step frameInterval) {
                val bitmap = retriever.getFrameAtIndex(i)
                frameList.add(bitmap!!)
            }
        } catch (m_e: Exception) {
            // m_e.printStackTrace()
        } finally {
            retriever.release()
            var redBucket: Long = 0
            var pixelCount: Long = 0
            val a = mutableListOf<Long>()

            for (i in frameList) {
                redBucket = 0
                // specific pixel block for certain video
                // please modify it if you use different video
                for (y in 200 until 400) {
                    for (x in 100 until 200) {
                        val c: Int = i!!.getPixel(x, y)
                        pixelCount++
                        redBucket += Color.red(c) + Color.blue(c) + Color.green(c)
                    }
                }
                a.add(redBucket)

            }

            val b = mutableListOf<Long>()

            for (i in 0 until a.lastIndex - 5) {
                val temp =
                    (a[i] + a[i + 1] + a[i + 2] + a[i + 3] + a[i + 4]) / 4
                b.add(temp)
            }

            var x = b[0]
            var count = 0

            for (i in 1 until b.lastIndex) {
                var p = b[i]
                // Threshold 3500
                // please modify it if you use different video
                if ((p - x) > 1000) {
                    count = count + 1
                }
                x = b[i]
            }

            var rate = ((count.toFloat() / 45) * 60).toInt()
            result = (rate / 2).toString()
            // return (rate / 2).toString()
            // return b.toString()

        }
        return result
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        (context as MainActivity).updateHeartRate(result ?: "N/A")
    }
}

