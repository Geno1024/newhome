package g.sw.planet.sws

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import g.sw.planet.R
import g.ufi.GNewHomeReq

class ReqTest : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        with(inflater.inflate(R.layout.sws_reqtest, container, false)) {
            val handler = Handler(context.mainLooper) { msg ->
                with(msg.data) {
                    when
                    {
                        containsKey("GNewHomeRsp") ->
                        {
                            Toast.makeText(context, getString("GNewHomeRsp"), Toast.LENGTH_SHORT).show()
                            true
                        }
                        else ->
                        {
                            false
                        }
                    }
                }
            }

            findViewById<Button>(R.id.sws_reqtest_send).setOnClickListener {
                val stellar = findViewById<EditText>(R.id.sws_reqtest_stellar).text
                val path = findViewById<EditText>(R.id.sws_reqtest_path).text
                val params = findViewById<EditText>(R.id.sws_reqtest_params).text

                Thread {
                    handler.sendMessage(
                        Message().apply {
                            data = Bundle().apply {
                                putString("GNewHomeRsp", GNewHomeReq(
                                    stellar.toString(),
                                    path.toString(),
                                    params.toString().split("\n")
                                ).send())
                            }
                        }
                    )
                }.start()
            }
            rootView
        }
}
