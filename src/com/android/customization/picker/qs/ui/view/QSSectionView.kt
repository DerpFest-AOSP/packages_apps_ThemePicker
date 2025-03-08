package com.android.customization.picker.qs.ui.view
import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import com.android.wallpaper.picker.SectionView

class QSSectionView(
    context: Context?,
    attrs: AttributeSet?,
) :
    SectionView(
        context,
        attrs,
    ) {
        // Interface for configuration change callbacks
        interface ConfigurationChangeListener {
            fun onConfigurationChanged(newConfig: Configuration)
        }

        private var configChangeListener: ConfigurationChangeListener? = null

        fun setConfigurationChangeListener(listener: ConfigurationChangeListener) {
            configChangeListener = listener
        }

        override fun onConfigurationChanged(newConfig: Configuration) {
            super.onConfigurationChanged(newConfig)
            configChangeListener?.onConfigurationChanged(newConfig)
        }
    }