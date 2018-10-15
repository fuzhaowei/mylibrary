package com.yuandi.lbrary.network

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.Buffer
import okio.BufferedSink
import okio.Okio
import java.io.File

/**
 * Created by EdgeDi
 * 2018/8/15 15:06
 */
class MineRequestBody(val file: File, private val onLengthListener: OnLengthListener?, val type: MediaType, val file_name: String) : RequestBody() {

    override fun contentType() = type

    override fun contentLength(): Long = file.length()

    override fun writeTo(sink: BufferedSink?) {
        if (onLengthListener !== null) {
            onLengthListener.Count(contentLength())
            val source = Okio.source(file)
            val buffer = Buffer()
            var length = 0L
            var count = source.read(buffer, 2048)
            while (count != -1L) {
                sink?.write(buffer, count)
                length += count
                count = source.read(buffer, 2048)
                onLengthListener.Length(length)
            }
        }
    }
}
