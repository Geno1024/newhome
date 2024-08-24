package g.sw.planet

import android.os.Bundle
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
        val navView = findViewById<NavigationView>(R.id.planet_navigation)
        val navController = findNavController(R.id.planet_fragment)
        navView.setupWithNavController(navController)

        Toast.makeText(this, getAppSize().toString(), Toast.LENGTH_LONG).show()
    }

    override fun onSupportNavigateUp(): Boolean
    {
        val navController = findNavController(R.id.planet_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
