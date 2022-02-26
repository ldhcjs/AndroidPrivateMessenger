package com.ldhcjs.androidprivatemessenger

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private final val tag: String = MainActivity::class.java.simpleName

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val button: Button = findViewById(R.id.button)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(tag, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            var token: String? = task.result

            // Log and toast
            val msg = getString(R.string.msg_token_fmt, token)
            Log.d(tag, msg)
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun onClickPush(v:View) {
        val header = HashMap<String, String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "key=AAAAHMenarM:APA91bGI0pqsVLM_6M3hK6BXGiUE2QBBHvEkBkW-ZA-tU_COZyGv8Cj9y8W403QclEO5eGJfKIC4ZphLeUsCzAk01tro3xBwI6ofi8uWMMhXw7RP3JmVNJQkIQu1fowrXtqWGo44wIDa"

        val firebaseMessageObject = FirebaseMessageObject()
        val firebaseMessageData = FirebaseMessageData()
        firebaseMessageData.setTitle("Push Msg")
        firebaseMessageData.setBody("This msg is for testing")
        // pixel 3a
//        firebaseMessageObject.setTo("cTtdUU9QQdCq1CkhtdrBsZ:APA91bFUqtvcHPpRu8OiHyfNmJse-zMAy2gZJtGwpqFbak6qBV8wOUt-IDJ9MFqTs8Cl88CTZYfOW-DbKfV6L0fdWmApoBlsI4gyLBSvc_CQS-zYA7dV4EEkgCHy68eVgLiRUlqVop95")
        // pixel 2
        firebaseMessageObject.setTo("fVUID8jCQnaG_B1Y-RaEjZ:APA91bGt-trVNpr8apkhIPB01yuW6RwQqF2UwXws6lbsG2vz3kzKB-wr9Fwc5IXVfK7ZiMwbzi56Mx0KiQbG8cDRE_Kr_-1G3MjsP1T1eS_RA_EmDZWVW0yYb5Wv0cSS12BVzWOUfMu0")
        firebaseMessageObject.setData(firebaseMessageData)

        val okHttpClient = OKHttpManager.getIntance()
        okHttpClient.sendMsg(header, firebaseMessageObject)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io())
            .subscribe(
                { result ->
                    Log.d(tag, "result.size : ${result.getSuccess()}")

                },
                { error -> Log.d(tag, "Error : ${error.localizedMessage}") }
            )
    }

}