package aav

import android.app.Activity
import android.os.Bundle
import android.widget.TextView

class VersionInfoActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_version_info)

        val view = findViewById<TextView>(R.id.versionInfoText)
        val versionInfo = "APPLICATION_ID: ${BuildConfig.APPLICATION_ID}\n" +
            "BUILD_TYPE: ${BuildConfig.BUILD_TYPE}\n" +
            "DEBUG: ${BuildConfig.DEBUG}\n" +
            "FLAVOR: ${BuildConfig.FLAVOR}\n" +
            "VERSION_CODE: ${BuildConfig.VERSION_CODE}\n" +
            "VERSION_NAME: ${BuildConfig.VERSION_NAME}\n"
        view.text = versionInfo
    }
}
