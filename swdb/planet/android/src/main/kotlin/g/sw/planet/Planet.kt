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
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.internal.NavigationMenu
import com.google.android.material.navigation.NavigationView
import java.io.File

class Planet : AppCompatActivity()
{
    private lateinit var  appBarConfiguration: AppBarConfiguration
    private fun getAppSize() = File(applicationInfo.sourceDir).length()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planet)
        setSupportActionBar(findViewById(R.id.planet_appbar_toolbar))
        val rootDrawer = findViewById<DrawerLayout>(R.id.planet_root)
        val navView = findViewById<NavigationView>(R.id.planet_navigation)
        val navController = findNavController(R.id.planet_fragment)

        getFilePermission()

        initNavBar()
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.planet_nav_reqtest, R.id.planet_nav_devinfo),
            rootDrawer
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        Toast.makeText(this, getAppSize().toString(), Toast.LENGTH_LONG).show()
    }

    private fun initNavBar()
    {
        initNavBarHeader()
//        initNavBarMenu()
    }

    private fun initNavBarHeader() = findViewById<NavigationView>(R.id.planet_navigation)
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

    private fun initNavBarMenu() = (findViewById<NavigationView>(R.id.planet_navigation)
        .menu as NavigationMenu)
        .apply {
            SWManager(this@Planet).listAllPlugins()?.forEachIndexed { index, plugin ->
                add(0, 0x31410000 + index, index, plugin)
            }
        }

    override fun onSupportNavigateUp(): Boolean
    {
        val navController = findNavController(R.id.planet_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
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
