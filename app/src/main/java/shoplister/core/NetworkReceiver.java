package shoplister.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.widget.Toast;

// Requirement 7: BroadcastReceiver - Network Activity
// Display ON/OFF toast on connectivity change
public class NetworkReceiver extends BroadcastReceiver
{
    private boolean changed = false;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE"))
        {
            boolean connected = networkExists(context);
            String  text      = connected ? "Network ON" : "Network OFF";

            if(!connected) changed = true;

            if(changed) Toast.makeText(context, text, Toast.LENGTH_LONG).show();
        }
    }

    private boolean networkExists(Context context)
    {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network             nw      = manager.getActiveNetwork();

        if (nw == null) return false;

        try {
            NetworkInfo info = manager.getActiveNetworkInfo();
            return info != null && info.isConnected();
        }
        catch (Exception e) {}

        NetworkCapabilities nc = manager.getNetworkCapabilities(nw);
        return nc != null && (nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || nc.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                || nc.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH));

    }

    public static void registerReceiver()
    {
        BroadcastReceiver br     = new NetworkReceiver();
        IntentFilter              filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        Instance.context.registerReceiver(br, filter);
    }
}

