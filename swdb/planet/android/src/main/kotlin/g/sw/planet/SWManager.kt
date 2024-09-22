package g.sw.planet

import android.content.Context
import android.os.Environment
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import java.io.File

class SWManager(val thiz: Context)
{
    fun listAllPlugins() = File("${Environment.getExternalStorageDirectory()}/NewHome")
        .listFiles { file ->
            true
        }
        ?.map { file ->
            file.name
        }
        ?.toList()

    fun load(plugin: String, view: ConstraintLayout)
    {
        val target = File("${Environment.getExternalStorageDirectory()}/NewHome/$plugin")
        view.addView(LinearLayout(thiz).apply {
            addView(TextView(thiz).apply {
                text = target.absolutePath
            })
        })
        println(plugin)
    }
}
