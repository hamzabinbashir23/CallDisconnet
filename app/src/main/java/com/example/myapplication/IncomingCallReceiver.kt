package com.example.myapplication

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import androidx.annotation.RequiresApi
import android.os.Build
import android.content.Intent
import com.example.myapplication.ITelephony
import android.telephony.TelephonyManager
import android.telecom.TelecomManager
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.widget.Toast
import java.lang.Exception

class IncomingCallReceiver : BroadcastReceiver() {
    @RequiresApi(api = Build.VERSION_CODES.P)
    override fun onReceive(context: Context, intent: Intent) {
        var telephonyService: ITelephony
        try {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            val number = intent.extras!!.getString(TelephonyManager.EXTRA_INCOMING_NUMBER)
            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING, ignoreCase = true)) {
                val telecomManager =
                    context.getSystemService(Context.TELECOM_SERVICE) as TelecomManager
                val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                try {
                    //        Method m = tm.getClass().getDeclaredMethod("getITelephony");

                    //         m.setAccessible(true);
                    //         telephonyService = (ITelephony) m.invoke(tm);

                    //          if ((number != null)) {
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ANSWER_PHONE_CALLS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return
                    }
                    telecomManager.endCall()
                    Toast.makeText(context, "Ending the call from: $number", Toast.LENGTH_SHORT)
                        .show()
                    //          }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                Toast.makeText(context, "Ring $number", Toast.LENGTH_SHORT).show()
            }
            if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK, ignoreCase = true)) {
                Toast.makeText(context, "Answered $number", Toast.LENGTH_SHORT).show()
            }
            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE, ignoreCase = true)) {
                Toast.makeText(context, "Idle $number", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}