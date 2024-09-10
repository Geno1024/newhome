package g.sw.planet.sws

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import g.sw.planet.R

class ReqTest : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        with(inflater.inflate(R.layout.sws_reqtest, container, false)) {
            findViewById<Button>(R.id.sws_reqtest_params_add).setOnClickListener {
                findViewById<LinearLayout>(R.id.sws_reqtest_params_inside).apply params@ {
                    addView(
                        RelativeLayout(context).apply line@ {
                            val delBtn = Button(context).apply delbtn@ {
                                id = View.generateViewId()
                                text = context.getString(R.string.sws_reqtest_param_del)
                                setOnClickListener {
                                    this@params.removeView(this@line)
                                }
                            }
                            addView(
                                delBtn,
                                RelativeLayout.LayoutParams(
                                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                                    RelativeLayout.LayoutParams.WRAP_CONTENT
                                ).apply {
                                    addRule(RelativeLayout.ALIGN_PARENT_END)
                                }
                            )
                            addView(
                                EditText(context).apply {

                                },
                                RelativeLayout.LayoutParams(
                                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                                    RelativeLayout.LayoutParams.WRAP_CONTENT
                                ).apply {
                                    addRule(RelativeLayout.ALIGN_PARENT_START)
                                    addRule(RelativeLayout.ALIGN_PARENT_LEFT)
                                    addRule(RelativeLayout.START_OF, delBtn.id)
                                    addRule(RelativeLayout.LEFT_OF, delBtn.id)
                                }
                            )
                        },
                        childCount - 1
                    )
                }
            }
            rootView
        }
}
