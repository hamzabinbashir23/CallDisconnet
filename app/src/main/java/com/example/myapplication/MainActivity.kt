package com.example.myapplication

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telecom.TelecomManager
import android.telephony.TelephonyManager
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.model.GetUserReponse
import com.example.myapplication.model.VerifictionNumberModel
import com.example.myapplication.model.VerifictionNumberModelItem
import com.example.myapplication.viewmodel.GetUserViewModel
import com.example.myapplication.viewmodel.GetVerificationViewModel
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception


@AndroidEntryPoint
class MainActivity : AppCompatActivity(),View.OnClickListener {
    @RequiresApi(api = Build.VERSION_CODES.P)

    private val PERMISSION_REQUEST_READ_PHONE_STATE = 1

    private lateinit var viewModel: GetUserViewModel

    private lateinit var viewModelVerificationNumber: GetVerificationViewModel

    private var viewNumberList: ArrayList<VerifictionNumberModelItem>? = null

    private var receiver: BroadcastReceiver? = null
    private lateinit var edt_number : EditText
    private lateinit var progressBar : ProgressBar
    private var getUserReponse : GetUserReponse? = null
    private lateinit var client_id_input_layout : TextInputLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edt_number = findViewById(R.id.edt_mobileNumber)
        progressBar = findViewById(R.id.pb_verify)
        client_id_input_layout = findViewById(R.id.client_id_input_layout)


        intiliaze()
        setListner()
        initObservables()

       // Toast.makeText(this, "Started the app", Toast.LENGTH_SHORT).show()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED || checkSelfPermission(
                    Manifest.permission.CALL_PHONE
                ) == PackageManager.PERMISSION_DENIED || checkSelfPermission(
                    Manifest.permission.ANSWER_PHONE_CALLS
                ) == PackageManager.PERMISSION_DENIED
            ) {
                val permissions = arrayOf<String>(
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.ANSWER_PHONE_CALLS
                )
                requestPermissions(permissions, PERMISSION_REQUEST_READ_PHONE_STATE)
            }
        }

        val filter = IntentFilter()
        filter.addAction("android.intent.action.PHONE_STATE")

        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                //do something based on the intent's action
                var telephonyService: ITelephony
                try {
                    val state = intent?.getStringExtra(TelephonyManager.EXTRA_STATE)
                    val number = intent?.extras!!.getString(TelephonyManager.EXTRA_INCOMING_NUMBER)
                    if (state.equals(TelephonyManager.EXTRA_STATE_RINGING, ignoreCase = true)) {
                        val telecomManager =
                            context?.getSystemService(Context.TELECOM_SERVICE) as TelecomManager
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
                            var flag : Boolean = false
                            for (i in viewNumberList?.indices!!) {
                                println(viewNumberList!![i])
                                if(viewNumberList!![i].verificationNumList == number){
                                    flag = true
                                    break
                                }
                            }

                            if(flag){
                                telecomManager.endCall()
                                Toast.makeText(context, "Ending the call from: $number", Toast.LENGTH_SHORT)
                                    .show()
                                viewModel.setUserStatus(getUserReponse!!.phoneNumber)
                                edt_number.visibility = View.VISIBLE
                                call_end.visibility = View.VISIBLE
                                client_id_input_layout.visibility = View.VISIBLE
                                progressBar.visibility = View.INVISIBLE
                            }
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
        registerReceiver(receiver, filter)

    }

    override fun onDestroy() {
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        super.onDestroy()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_READ_PHONE_STATE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        this,
                        "Permission granted: $PERMISSION_REQUEST_READ_PHONE_STATE",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        "Permission NOT granted: $PERMISSION_REQUEST_READ_PHONE_STATE",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return
            }
        }
    }


    private fun initObservables() {

//        viewModel.getUser()
        viewNumberList = ArrayList()
        viewModelVerificationNumber =  ViewModelProvider(this).get(GetVerificationViewModel::class.java)

        viewModelVerificationNumber.getVerificationNumber()

        viewModel.getUserMLD.observe(this) {

          //  it
            if(it !=null){
                getUserReponse = it
                Toast.makeText(this,"Register Successfully",Toast.LENGTH_SHORT).show()
            }
         //   progressBar.visibility = View.INVISIBLE
        }

        viewModelVerificationNumber.getUserMLD.observe(this) { response ->


//            for (i in response.data.indices) {
//                viewNumberList?.add(VerifictionNumberModel(response))
//            }
            if(response!=null)
            viewNumberList?.addAll(response)

        }

    }
    private fun intiliaze() {

        viewModel =  ViewModelProvider(this).get(GetUserViewModel::class.java)

    }

    private fun setListner() {

        call_end.setOnClickListener(this)
    }
    override fun onClick(v: View) {

        when(v.id){
            call_end.id->{
                if(!edt_number.text?.equals("")!!) {
                    viewModel.getUser(edt_number.text.toString())
                    progressBar.visibility = View.VISIBLE
                    edt_number.visibility = View.INVISIBLE
                    call_end.visibility = View.INVISIBLE
                    client_id_input_layout.visibility = View.INVISIBLE
                }
            }
        }
    }

}