package fzw.yuandi.mylibrary

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.yuandi.lbrary.network.HttpUtil
import com.yuandi.lbrary.network.OnLoadingListener
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        HttpUtil.Build()
                .AskLoad("http://192.168.150.105:8080/tzhbwx/apkload", "jinhua.apk", object : OnLoadingListener {
                    override fun onSuccess(file: File) {
                        if (file.exists()) {
                            val intent = Intent(Intent.ACTION_VIEW)
                            val data =
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                        FileProvider.getUriForFile(this@MainActivity, "fzw.yuandi.mylibrary.fileprovider", file)
                                    } else {
                                        Uri.fromFile(file)
                                    }
                            intent.setDataAndType(data, "application/vnd.android.package-archive")
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@MainActivity, "下载出错", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onError(e: String) {
                        Log.e("onError", e)
                    }

                    override fun onProgress(progress: Float) {
                        Log.e("onProgress", progress.toString())
                    }

                })
    }
}