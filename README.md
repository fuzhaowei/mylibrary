# mylibrary
平时开发框架,自用

私有域使用方法：

    1.在res下新建文件夹xml，再新建xml文件file_paths.xml，内容：
```
<?xml version="1.0" encoding="utf-8"?>
<paths>
    <external-path
        name="name"
        path="." />
</paths>
```
     2.清单文件中注册
```
<provider
            android:name="android.support.v4.content.FileProvider"
            android:authorities="***.****.*****.fileprovider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/file_paths" />
</provider>
```
     3.使用样例
```
 HttpUtil.Build()
                .AskLoad("http://192.168.150.105:8080/tzhbwx/apkload", "jinhua.apk", object : OnLoadingListener {
                    override fun onSuccess(file: File) {
                        if (file.exists()) {
                            val intent = Intent(Intent.ACTION_VIEW)
                            val data =
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                        FileProvider.getUriForFile(this@MainActivity, "***.****.*****.fileprovider", file)
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
```
如8.0以上版本安装apk无反应，添加权限 <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />


```
implementation 'com.fuzhaowei:mylibrary:1.0.1'
```
