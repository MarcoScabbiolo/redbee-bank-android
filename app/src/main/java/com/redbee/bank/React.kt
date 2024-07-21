package com.redbee.bank

import android.app.Application
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.facebook.react.BuildConfig
import com.facebook.react.PackageList
import com.facebook.react.ReactApplication
import com.facebook.react.ReactFragment
import com.facebook.react.ReactNativeHost
import com.facebook.react.ReactPackage
import com.facebook.react.defaults.DefaultReactNativeHost
import com.facebook.soloader.SoLoader

class ReactApplicationActivity : ReactApplication {
    // Reliance on this mutable property can be avoided by implementing ReactApplication directly in MainActivity.
    // But using interface implementation by delegation is cool and polluting the activity is not.
    var application: Application? = null

    override val reactNativeHost: ReactNativeHost by lazy {
        object : DefaultReactNativeHost(application!!) {
            override fun getUseDeveloperSupport() = BuildConfig.DEBUG

            override fun getPackages(): List<ReactPackage> = PackageList(application).packages.toMutableList()
        }
    }
}

fun ReactApplication.onCreateRnActivity(application: Application) {
    if (this is ReactApplicationActivity) {
        this.application = application
    }
    SoLoader.init(application, false)
}

enum class RNModule {
    Transfer,
}

private val LocalFragmentManager =
    staticCompositionLocalOf<FragmentManager> {
        error("LocalFragmentManager not provided")
    }

@Composable
fun ReactModuleManager(
    activity: FragmentActivity,
    content: @Composable () -> Unit,
) {
    val supportManager = remember { activity.supportFragmentManager }

    CompositionLocalProvider(LocalFragmentManager provides supportManager, content)
}

@Composable
fun ReactModule(
    module: RNModule,
    modifier: Modifier = Modifier,
    componentName: String = module.name,
) {
    val fragmentManager = LocalFragmentManager.current

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { _ ->
            ReactFragment
                .Builder()
                .setComponentName(componentName)
                .build()
                .also {
                    fragmentManager
                        .beginTransaction()
                        .add(it, componentName)
                        .commitNow()
                }.requireView()
        },
    )
}
