package com.example.colorgenerator

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.content.FileProvider
import androidx.core.graphics.applyCanvas
import com.example.colorgenerator.models.ColorLock
import com.example.colorgenerator.models.navigation.MainNavRoutes
import com.example.colorgenerator.navigation.MainNavHost
import com.example.colorgenerator.ui.components.fab.MenuIconAnimator
import com.example.colorgenerator.ui.components.fab.MenuList
import com.example.colorgenerator.viewmodel.ColorViewModel
import com.example.colorgenerator.viewmodel.NavigationViewModel
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.Exception
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    private lateinit var sensorManager: SensorManager
    private var accelerometerListener: SensorEventListener? = null
    private var accelerometerSensor: Sensor? = null
    private var mAccel = 0f
    private var mAccelCurrent = SensorManager.GRAVITY_EARTH
    private var mAccelLast = SensorManager.GRAVITY_EARTH

    private lateinit var colorViewModel: ColorViewModel
    private lateinit var navigationViewModel: NavigationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) ?: null

        colorViewModel = ColorViewModel(application)
        navigationViewModel = NavigationViewModel()

        colorViewModel.allColorsLiveData.observe(this) { colorList ->
            if (colorList.isNullOrEmpty()) {
                colorViewModel.updateAllColors()
                return@observe
            }

            val colorOne =
                colorList.find { it.colorName == "colorOne" } ?: throw Exception("Color not found")
            val colorTwo =
                colorList.find { it.colorName == "colorTwo" } ?: throw Exception("Color not found")
            val colorThree = colorList.find { it.colorName == "colorThree" }
                ?: throw Exception("Color not found")
            val colorFour =
                colorList.find { it.colorName == "colorFour" } ?: throw Exception("Color not found")
            val colorFive =
                colorList.find { it.colorName == "colorFive" } ?: throw Exception("Color not found")


            colorViewModel.colorList[0] = ColorLock(colorOne.value, colorOne.isLocked)
            colorViewModel.colorList[1] = ColorLock(colorTwo.value, colorTwo.isLocked)
            colorViewModel.colorList[2] = ColorLock(colorThree.value, colorThree.isLocked)
            colorViewModel.colorList[3] = ColorLock(colorFour.value, colorFour.isLocked)
            colorViewModel.colorList[4] = ColorLock(colorFive.value, colorFive.isLocked)
        }

        setContent {
            val systemUiController = rememberSystemUiController()
            var isLoading by remember { mutableStateOf(true) }
            val allColors by colorViewModel.allColorsLiveData.observeAsState()
            val scaffoldState = rememberScaffoldState()

            configureAccelerometer { colorViewModel.updateAllColors() }

            ConfigureApp(systemUiController, colorViewModel, isLoading)

            if (allColors == null || isLoading) {
                navigationViewModel.setRoute(MainNavRoutes.Loading.routeName)

                LaunchedEffect(Unit) {
                    delay(1000)
                    isLoading = false
                    navigationViewModel.setRoute(MainNavRoutes.RandomGenerator.routeName)
                }
            } else {
                navigationViewModel.setRoute(MainNavRoutes.RandomGenerator.routeName)
            }

            Scaffold(
                modifier = Modifier,
                scaffoldState = scaffoldState,
                floatingActionButton = {
                    if (isLoading) return@Scaffold

                    val view = LocalView.current
                    val context = LocalContext.current
                    var isOpen by remember { mutableStateOf<Boolean?>(null) }

                    Box {
                        FloatingActionButton(
                            onClick = { isOpen = isOpen != true },
                            modifier = Modifier.align(Alignment.BottomEnd),
                            backgroundColor = Color(0xFF257683)
                        ) {
                            MenuIconAnimator(isOpen)
                        }

                        MenuList(isOpen) { itemName ->
                            when (itemName) {
                                MainNavRoutes.RandomGenerator.menuName -> navigationViewModel.setRoute(
                                    MainNavRoutes.RandomGenerator.routeName
                                )
                                "Share" -> shareApp(context, view)
                            }
                        }
                    }
                }
            ) {
                MainNavHost(
                    modifier = Modifier.fillMaxSize(),
                    navigationViewModel = navigationViewModel,
                    colorViewModel = colorViewModel,
                    scaffoldState = scaffoldState
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (::sensorManager.isInitialized && accelerometerListener != null) {
            sensorManager.registerListener(
                accelerometerListener,
                accelerometerSensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    override fun onPause() {
        super.onPause()

        if (::colorViewModel.isInitialized) {
            with(colorViewModel) {
                for (i in 0 until colorList.size) {
                    insertColor(i)
                }
            }
        }

        if (::sensorManager.isInitialized && accelerometerListener != null) {
            sensorManager.unregisterListener(
                accelerometerListener,
            )
        }
    }

    private fun configureAccelerometer(onShake: () -> Unit) {
        if (accelerometerSensor != null) {
            accelerometerListener = object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    if (event == null) return

                    val x = event.values[0]
                    val y = event.values[1]
                    val z = event.values[2]

                    mAccelLast = mAccelCurrent
                    mAccelCurrent = sqrt(x * x + y * y + z * z)

                    val delta = mAccelCurrent - mAccelLast
                    mAccel = mAccel * 0.9f + delta * 0.1f

                    if (mAccel > 0.5f || mAccel < -0.5f) {
                        onShake()
                    }
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
            }

            sensorManager.registerListener(
                accelerometerListener,
                accelerometerSensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    private fun shareApp(context: Context, view: View) {
        val image =
            Bitmap.createBitmap(
                view.width,
                view.height,
                Bitmap.Config.ARGB_8888
            )
                .applyCanvas { view.draw(this) }

        val imagesFolder = File(context.cacheDir, "images")
        try {
            imagesFolder.mkdirs()
            val file = File(imagesFolder, "shared_image.png")
            val stream = FileOutputStream(file)
            image.compress(Bitmap.CompressFormat.PNG, 90, stream)
            stream.flush()
            stream.close()
            val uri = FileProvider.getUriForFile(
                context,
                BuildConfig.APPLICATION_ID + ".provider",
                file
            )

            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.type = "image/png"
            val shareIntent = Intent.createChooser(intent, null)
            context.startActivity(shareIntent)
        } catch (e: IOException) {
            Log.d(
                "Error",
                "IOException while trying to write file for sharing: " + e.message
            )
        }
    }
}

@Composable
fun ConfigureApp(
    uiController: SystemUiController,
    colorViewModel: ColorViewModel,
    isLoading: Boolean
) {
    val animatedColorOne = animateColorAsState(Color(colorViewModel.colorList[0].value))
    val animatedColorFive = animateColorAsState(Color(colorViewModel.colorList[4].value))
    uiController.setStatusBarColor(
        color = if (isLoading) Color(0xFF257683) else animatedColorOne.value,
        darkIcons = false
    )
    uiController.setNavigationBarColor(
        color = if (isLoading) Color(0xFF257683) else animatedColorFive.value,
        navigationBarContrastEnforced = true
    )
}