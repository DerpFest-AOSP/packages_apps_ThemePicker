package com.android.customization.picker.qs

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import androidx.lifecycle.LifecycleOwner
import com.android.customization.picker.qs.ui.binder.QSSectionBinder
import com.android.customization.picker.qs.ui.viewmodel.QSSectionViewModel
import com.android.customization.picker.qs.ui.view.QSSectionView

import com.android.themepicker.R
import com.android.wallpaper.model.CustomizationSectionController

/** Controls a section with UI that lets the user toggle QS settings. */
class QSSectionController(
    private val viewModel: QSSectionViewModel,
    private val lifecycleOwner: LifecycleOwner,
) : CustomizationSectionController<QSSectionView> {

    override fun isAvailable(context: Context): Boolean {
        return true
    }

    @SuppressLint("InflateParams") // We don't care that the parent is null.
    override fun createView(context: Context): QSSectionView {
        val view =
            LayoutInflater.from(context)
                .inflate(
                    R.layout.qs_section,
                    /* parent= */ null,
                ) as QSSectionView

        QSSectionBinder.bind(
            view = view,
            viewModel = viewModel,
            lifecycleOwner = lifecycleOwner,
        )

        return view
    }
}
