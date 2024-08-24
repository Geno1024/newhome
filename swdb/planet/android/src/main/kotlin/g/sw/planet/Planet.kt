package g.sw.planet

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import java.io.File

class Planet : AppCompatActivity()
{
    private fun getAppSize() = File(applicationInfo.sourceDir).length()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planet)
        getFilePermission()

        val navView = findViewById<NavigationView>(R.id.planet_navigation)
        val navController = findNavController(R.id.planet_fragment)
        navView.setupWithNavController(navController)

        Toast.makeText(this, getAppSize().toString(), Toast.LENGTH_LONG).show()

        findViewById<NavigationView>(R.id.planet_navigation)
            .getHeaderView(0)
            .apply {
                findViewById<TextView>(R.id.planet_nav_header_version)
                    .text = with(packageManager.getPackageInfo(packageName, 0)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                    {
                        "$versionName ($longVersionCode)"
                    }
                    else
                    {
                        "$versionName ($versionCode)"
                    }
                }
                findViewById<ImageView>(R.id.planet_nav_header_icon)
                    .setImageDrawable(packageManager.getPackageInfo(packageName, 0).applicationInfo?.loadIcon(packageManager))
            }
    }

    override fun onSupportNavigateUp(): Boolean
    {
        val navController = findNavController(R.id.planet_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun getFilePermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        {
            if (!Environment.isExternalStorageManager())
            {
                startActivity(
                    Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                        setData(Uri.parse("package:$packageName"))
                    }
                )
            }
        }
    }
}
