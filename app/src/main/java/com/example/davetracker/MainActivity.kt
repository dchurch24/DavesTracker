package com.example.davetracker

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.davetracker.ui.home.HomeFragment
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.net.URL

class MainActivity() : AppCompatActivity(), SensorEventListener, Communicator, Parcelable {

    private lateinit var appBarConfiguration: AppBarConfiguration

    var running = false
    var sensorManager:SensorManager? = null
    var STEP_COUNTER_RQ = 101

    var percentage = 100;

    constructor(parcel: Parcel) : this() {
        running = parcel.readByte() != 0.toByte()
        STEP_COUNTER_RQ = parcel.readInt()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        checkForPermissions(android.Manifest.permission.ACTIVITY_RECOGNITION,"Step Sensor",STEP_COUNTER_RQ)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val fab: FloatingActionButton = findViewById(R.id.fab)

        fab.setOnClickListener {
            val intentIntegrator = IntentIntegrator(this@MainActivity)
            intentIntegrator.setBeepEnabled(true)
            intentIntegrator.setCameraId(0)
            intentIntegrator.setPrompt("SCAN")
            intentIntegrator.setBarcodeImageEnabled(false)
            intentIntegrator.initiateScan()
        }
        Thread(Runnable {
            this@MainActivity.runOnUiThread(java.lang.Runnable {
                val seek = findViewById<SeekBar>(R.id.seekBar)
                seek?.setOnSeekBarChangeListener(object :
                    SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seek: SeekBar,
                        progress: Int, fromUser: Boolean
                    )
                    {

                    }
                    override fun onStartTrackingTouch(seek: SeekBar) {

                    }
                    override fun onStopTrackingTouch(seek: SeekBar) {

                        Toast.makeText(
                            this@MainActivity,
                            "Progress is: " + seek.progress + "%",
                            Toast.LENGTH_SHORT
                        ).show()
                        percentage = seek.progress
                    }
                })
            })
        }).start()

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)
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
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("MainActivity", "Scanned")
                Toast.makeText(this, "Scanned -> " + result.contents, Toast.LENGTH_SHORT)
                    .show()
                text_home.text = String.format("Scanned Result: %s", result)

                // ****************************************************************************** Get OpenFoodFacts data for product.
                val thread = Thread(Runnable {
                    try {
                        var json = URL("https://world.openfoodfacts.org/api/v0/product/" + result.contents.toString() + ".json").readText()
                        val mapper = jacksonObjectMapper()
                        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                        var products: Json4Kotlin_Base = mapper.readValue(json)
                        var product_name = products.product!!.product_name
                        var calories = products.product!!.nutriments?.energy_value
                        var barcode = result.contents.toString()
                        Thread(Runnable {
                            this@MainActivity.runOnUiThread(java.lang.Runnable {
                                this.text_food.text = product_name
                                this.text_calories.text = calories.toString() + " calories per 100g"
                                this.text_barcode.text = barcode

                                var percent = percentage.toString()

                                this.button_add_food.visibility = View.VISIBLE
                                button_add_food.setOnClickListener {
                                    Toast.makeText(this, "Send to API" + result.contents, Toast.LENGTH_SHORT)
                                        .show()
                                    val thread = Thread(Runnable {
                                        try {
                                            percent = percentage.toString()
                                            var json = URL("http://www.cidb.co.uk:5001/api/food/SaveScanned/1/$barcode/$product_name/$calories/$percent").readText()
                                        } catch (e: Exception) {
                                            e.printStackTrace()
                                        }
                                    }).start()
                                }
                                val fragment1 = HomeFragment()
                                supportFragmentManager.beginTransaction().replace(R.id.nav_view, fragment1).commit()
                            })
                        }).start()

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                })
                thread.start()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    // ****************************************************************************** Step counter specific.

    override fun onResume() {
        super.onResume()
        running = true
        var stepsSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepsSensor == null) {
            Toast.makeText(this, "No Step Counter Sensor !", Toast.LENGTH_SHORT).show()
        } else {
            sensorManager?.registerListener(this, stepsSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        running = false
        //Toast.makeText(this, "Step Counter Paused !", Toast.LENGTH_SHORT).show()
        sensorManager?.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //TODO("Not yet implemented")
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (running) {
            var step_count = event.values[0]
            Thread(Runnable {
                this@MainActivity.runOnUiThread(java.lang.Runnable {
                    this.text_step_count.setTextSize(30F);
                    this.text_step_count.text = step_count.toString()
                })
            }).start()
        }
    }

// ****************************************************************************** step counter permissions

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkForPermissions(permission: String, name: String, requestCode:Int){
        when {
            ContextCompat.checkSelfPermission(
                applicationContext,
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(applicationContext, "$name permission granted", Toast.LENGTH_SHORT)
                    .show()
            }
            shouldShowRequestPermissionRationale(permission)-> showDialog(permission,name,requestCode)
            else -> ActivityCompat.requestPermissions(this, arrayOf(permission),requestCode)

        }
    }

    private fun showDialog(permission:String,name:String,requestCode:Int){
        val builder = AlertDialog.Builder(this)
        builder.apply{
            setMessage("Permission to access your $name is required for this app.")
            setTitle("Permission required")
            setPositiveButton("OK") { dialog, which -> ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission),requestCode)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        fun innerCheck(name:String) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "$name permission refused", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "$name permission granted", Toast.LENGTH_SHORT).show()
            }
            when (requestCode) {
                STEP_COUNTER_RQ -> innerCheck("step counter")
            }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (running) 1 else 0)
        parcel.writeInt(STEP_COUNTER_RQ)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MainActivity> {
        override fun createFromParcel(parcel: Parcel): MainActivity {
            return MainActivity(parcel)
        }

        override fun newArray(size: Int): Array<MainActivity?> {
            return arrayOfNulls(size)
        }
    }

    override fun passDataCom(editext_input: String) {
        val bundle = Bundle()
        bundle.putString("input_txt",editext_input)

        val transaction = this.supportFragmentManager.beginTransaction()
        val frag2 = HomeFragment()
        frag2.arguments = bundle

        transaction.replace(R.id.nav_view, frag2)
        transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
    }
}
