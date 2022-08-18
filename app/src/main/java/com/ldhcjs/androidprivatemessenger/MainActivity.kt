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
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.*
import com.ldhcjs.androidprivatemessenger.fcm.FirebaseCloudMsgManager
import java.util.*
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val tag: String = MainActivity::class.java.simpleName

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val button: Button = findViewById(R.id.button)

        //FirebaseApp.initializeApp(applicationContext)

        FirebaseCloudMsgManager.init()

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(tag, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            var token: String? = task.result
            FirebaseCloudMsgManager.tmp_token = token.toString()


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
                R.id.nav_home, R.id.nav_chat, R.id.nav_slideshow
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

    fun onClickPush(v: View) {
        val hashMap: HashMap<String, String> = HashMap<String, String>()
        hashMap[FirebaseCloudMsgManager.TITLE] = "test title"
        hashMap[FirebaseCloudMsgManager.MSG] = "test msg"
        hashMap[FirebaseCloudMsgManager.NAME] = "test msg"
        hashMap[FirebaseCloudMsgManager.TIME] = Util.getCurrentHour()
        hashMap[FirebaseCloudMsgManager.TOKEN] = FirebaseCloudMsgManager.tmp_token

        FirebaseCloudMsgManager.sendFcmObj(FirebaseCloudMsgManager.getFcmObj(hashMap))
    }

}