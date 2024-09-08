package g.sw.planet.sws

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import g.sw.planet.R
import java.net.Inet4Address

class DevInfo : Fragment()
{
    @OptIn(ExperimentalStdlibApi::class)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val r = inflater.inflate(R.layout.sws_devinfo, container, false)
        (context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
            allNetworks.forEach { network ->
                getLinkProperties(network)?.linkAddresses?.forEach { addr ->
                    if (addr.address is Inet4Address)
                        println("$network ${addr.address.address.toHexString()}")
                }
            }
        }
        r.findViewById<TextView>(R.id.sws_devinfo_ip).text = "IP: ${0}"
        r.findViewById<TextView>(R.id.sws_devinfo_mac).text = "MAC: ${
            (context?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)?.apply {
                allNetworks.forEach {
                    getLinkProperties(it)?.linkAddresses?.forEach { addr ->
                        println(addr.address.address.toHexString())
                    }
                }
            }
        }"
        r.findViewById<TextView>(R.id.sws_devinfo_hostname).text = "Hostname: ${0}"
        return r.rootView

    }
}
