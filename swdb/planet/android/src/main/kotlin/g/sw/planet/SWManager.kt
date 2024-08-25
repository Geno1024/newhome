package g.sw.planet

import android.content.Context
import android.os.Environment
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
}
