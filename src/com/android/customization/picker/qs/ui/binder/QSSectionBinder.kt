package com.android.customization.picker.qs.ui.binder

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.android.customization.picker.qs.ui.viewmodel.QSSectionViewModel
import com.android.themepicker.R
import com.google.android.material.materialswitch.MaterialSwitch
import kotlinx.coroutines.launch

/**
 * Binds between view and view-model for a section that lets the user control notification settings.
 */
object QSSectionBinder {
    @SuppressLint("UseSwitchCompatOrMaterialCode") // We're using Switch and that's okay for SysUI.
    fun bind(
        view: View,
        viewModel: QSSectionViewModel,
        lifecycleOwner: LifecycleOwner,
    ) {
        val switch: MaterialSwitch = view.requireViewById(R.id.switcher)

        view.setOnClickListener { viewModel.onClicked(switch) }

        lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { switch.isChecked = viewModel.isSwitchOn()  }
            }
        }
    }
}
