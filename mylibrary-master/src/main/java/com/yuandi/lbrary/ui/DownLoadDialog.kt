package com.yuandi.lbrary.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.content.FileProvider
import android.view.View
import com.yuandi.lbrary.R
import com.yuandi.lbrary.base.BaseDialog
import com.yuandi.lbrary.network.HttpUtil
import com.yuandi.lbrary.network.OnLoadingListener
import kotlinx.android.synthetic.main.dialog_download.*
import java.io.File

/**
 * Created by EdgeDi
 * 2018/4/23 13:41
 */
class DownLoadDialog(activity: Activity, val content: String?, val url: String?) : BaseDialog(activity) {

    override fun layout() = R.layout.dialog_download

    override fun initUI() {
        unload_content.text = content?.replace("\\n", "\n")
    }

    override fun setListener() {
        findViewById<View>(R.id.down_load_text).setOnClickListener {
            DownLoad()
            cancel()
        }
        findViewById<View>(R.id.dialog_cancel).setOnClickListener {
            cancel()
        }
        findViewById<View>(R.id.dialog_finish).setOnClickListener {
            cancel()
        }
    }

    private fun DownLoad() {
        HttpUtil.Build().AskLoad(url!!,
                "",
                object : OnLoadingListener {
                    override fun onSuccess(file: File) {
                        if (file.exists()) {
                            val intent = Intent(Intent.ACTION_VIEW)
                            val data =
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                        FileProvider.getUriForFile(getContext(), "com.yuandi.lbrary.fileprovider", file)
                                    } else {
                                        Uri.fromFile(file)
                                    }
                            intent.setDataAndType(data, "application/vnd.android.package-archive")
                            getContext().startActivity(intent)
                        } else {
                            ToastShow("下载出错")
                        }
                    }

                    override fun onError(e: String) {
                        ToastShow("网络错误")
                    }

                    override fun onProgress(progress: Float) {

                    }

                })
    }

}