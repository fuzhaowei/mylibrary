package com.yuandi.lbrary.network

import android.os.Environment
import android.os.Handler
import android.os.Looper
import com.yuandi.lbrary.util.JsonUtil
import okhttp3.*
import org.jetbrains.anko.doAsync
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * Created by EdgeDi
 * 2018/8/3 14:27
 */
object HttpUtil {

    /**
     * 上传文件TYPE，二进制流
     * 大部分上传文件为二进制流，但具体还需询问后台
     */
    val BINARY_SYSTEM = MediaType.parse("application/octet-stream")!!

    /**
     *  域名
     *  需要http://||https://开头，否则会报错
     */
    @Volatile
    private var base_url = "http://www.baidu.com"

    /**
     * jsonTYPE
     */
    private val json = MediaType.parse("application/json; charset=utf-8")

    /**
     * 存放get请求和post请求的参数
     */
    val get_map = hashMapOf<String, String>()

    /**
     * 存放头部
     */
    val head_map = hashMapOf<String, String>()

    /**
     * 存放上传文件
     */
    val upload_mep = hashMapOf<String, MineRequestBody>()

    private val client by lazy {
        OkHttpClient.Builder()
                .addInterceptor(TrendsUrlInterceptor())
                .build()
    }

    private var retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
                .baseUrl(base_url)
                .client(client)
                .build()
        TrendsUrl.setUrl(base_url)
    }

    /**
     *  @param key 文件key
     *  @param file 上传的文件
     *  @param onLengthListener 进度监听，可传可不传
     *  @param type 文件类型，默认二进制流，可传可不传
     *  @param file_name 文件名，默认等于文件名，如有特殊需求可改变,可传可不传
     */
    fun putKeyUpload(key: String, file: File, onLengthListener: OnLengthListener? = null, type: MediaType = BINARY_SYSTEM, file_name: String = file.name): HttpUtil {
        val value = MineRequestBody(file, onLengthListener, type, file_name)
        upload_mep[key] = value
        return this
    }

    fun putHead(key: String, value: Any): HttpUtil {
        head_map.put(key, value.toString())
        return this
    }

    /**
     * 将参数添加进键值队，在Ask之前调用
     * @key
     * @value
     */
    fun putKeyCode(key: String, value: Any): HttpUtil {
        get_map.put(key, value.toString())
        return this
    }

    /**
     * 更改域名
     * @url 域名
     */
    fun setBase_Url(url: String): HttpUtil {
        TrendsUrl.setUrl(url)
        return this
    }

    private val class_build = HttpFactory()

    fun Build() = class_build

    private val servce by lazy { retrofit.create(HttpService::class.java) }

    /**
     * 添加头部请求
     */
    fun head(url: String) = servce.Head(url, get_map, head_map)

    /**
     * get请求
     */
    fun get(url: String) = servce.GET(url, get_map)

    /**
     * post请求
     */
    fun post(url: String) = servce.POST(url, get_map)

    /**
     * json请求
     */
    fun json(url: String, json: JSONObject) =
            servce.JSON(url, RequestBody.create(HttpUtil.json, json.toString()), get_map)

    /**
     * json请求
     */
    fun json(url: String, json: JSONArray) =
            servce.JSON(url, RequestBody.create(HttpUtil.json, json.toString()), get_map)

    /**
     * 上传文件
     */
    fun upload(url: String, body: MultipartBody) = servce.UPLOAD(url, body)

    /**
     * 下载文件
     */
    fun load(url: String) = servce.LOAD(url, get_map)

    class HttpFactory {

        lateinit var call: Call<ResponseBody>

        /**
         * 外部调用get
         * @url 接口
         * @onDataListener 请求数据回调
         */
        @Synchronized
        inline fun <reified T : Any> AskGet(url: String, onDataListener: OnDataListener<T>) {
            call = get(url)
            Asynchronous(call, onDataListener)
        }

        /**
         * 外部调用post
         * 传参同上
         */
        @Synchronized
        inline fun <reified T : Any> AskPost(url: String, onDataListener: OnDataListener<T>) {
            call = post(url)
            Asynchronous(call, onDataListener)
        }

        /**
         * 外部调用head
         * 传参同上
         */
        @Synchronized
        inline fun <reified T : Any> AskHead(url: String, onDataListener: OnDataListener<T>) {
            call = head(url)
            Asynchronous(call, onDataListener)
        }

        /**
         * 外部调用json
         * @url 接口
         * @json JSONObject对象
         * @onDataListener 请求数据回调
         */
        @Synchronized
        inline fun <reified T : Any> AskJson(url: String, json: JSONObject, onDataListener: OnDataListener<T>) {
            call = json(url, json)
            Asynchronous(call, onDataListener)
        }

        @Synchronized
        inline fun <reified T : Any> AskJson(url: String, json: JSONArray, onDataListener: OnDataListener<T>) {
            call = json(url, json)
            Asynchronous(call, onDataListener)
        }

        /**
         * 外部调用上传
         * 传参同get
         */
        @Synchronized
        inline fun <reified T : Any> AskUpload(url: String, onDataListener: OnDataListener<T>) {
            val build = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
            if (upload_mep.size > 0) {
                upload_mep.forEach { t, u ->
                    build.addFormDataPart(t, u.file_name, u)
                }
            }
            if (get_map.size > 0) {
                get_map.forEach { t, u ->
                    build.addFormDataPart(t, u)
                }
            }
            call = upload(url, build.build())
            Asynchronous(call, onDataListener)
        }

        /**
         * 外部调用下载
         * @param url 接口
         * @param name 文件名
         * @param filetype 文件类型
         * @param onLoadingListener 下载结果回调
         * 错误回调和下载完成回调均在主线程，进度则在子线程，方便操作
         */
        @Synchronized
        fun AskLoad(url: String, name: String, onLoadingListener: OnLoadingListener, filetype: String = ".apk") {
            val request = Request.Builder()
                    .url(url)
                    .build()
            client.newCall(request).enqueue(object : okhttp3.Callback {
                override fun onFailure(call: okhttp3.Call?, e: IOException?) {
                    handler.post { onLoadingListener.onError(e.toString()) }
                }

                override fun onResponse(call: okhttp3.Call?, response: okhttp3.Response?) {
                    if (response != null) {
                        val buf = ByteArray(2048)
                        val path =
                                /**isExistDir(saveDir)*/
                                Environment.getExternalStorageDirectory()
                        val input = response.body()?.byteStream()
                        val length = response.body()?.contentLength()
                        val file = File(path, name + filetype)
                        val output = FileOutputStream(file)
                        var sum = 0L
                        var len = input?.read(buf)?.toLong()
                        try {
                            while (len != -1L) {
                                output.write(buf, 0, len!!.toInt())
                                sum += len
                                val progress = sum * 1.0f / length!! * 100
                                onLoadingListener.onProgress(progress)
                                len = input?.read(buf)?.toLong()
                            }
                            output.flush()
                            handler.post {
                                onLoadingListener.onSuccess(file)
                            }
                        } catch (e: Exception) {
                            handler.post { onLoadingListener.onError(e.toString()) }
                        } finally {
                            input?.close()
                            output.close()
                        }
                    } else {
                        handler.post { onLoadingListener.onError("数据为空") }
                    }
                }
            })
        }

        /**
         * 在页面destroy时调用，结束还在请求的call
         */
        fun Cancel() {
            call.cancel()
        }

        /**
         * 发起请求
         */
        protected inline fun <reified T : Any> Asynchronous(call: Call<ResponseBody>, onDataListener: OnDataListener<T>) {
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                    doAsync {
                        val string = response?.body()?.string()
                        if (response?.code() != 200) {
                            handler.post { onDataListener.Error("网络错误,返回code:${response?.code()},返回数据:$string") }
                            return@doAsync
                        }
                        if (string.isNullOrEmpty()) {
                            handler.post { onDataListener.Error("数据请求为空") }
                            return@doAsync
                        }
                        try {
                            val instance = T::class.java.newInstance()
                            if (instance is String) {
                                handler.post { onDataListener.Success(string as T) }
                            } else {
                                val format = JsonUtil.fromJson(string!!, T::class.java)
                                if (format == null) {
                                    handler.post { onDataListener.Error("数据解析错误，请debug查看数据") }
                                } else {
                                    handler.post { onDataListener.Success(format as T) }
                                }
                            }
                        } catch (e: Exception) {
                            handler.post { onDataListener.Error("Exception:" + e.toString()) }
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                    handler.post { onDataListener.Error("onFailure:" + t.toString()) }
                }

            })
            closeData()
        }

        fun closeData() {
            if (get_map.size > 0)
                get_map.clear()
            if (upload_mep.size > 0)
                upload_mep.clear()
            if (head_map.size > 0)
                head_map.clear()
        }

        val handler = object : Handler(Looper.getMainLooper()) {}
    }

}
