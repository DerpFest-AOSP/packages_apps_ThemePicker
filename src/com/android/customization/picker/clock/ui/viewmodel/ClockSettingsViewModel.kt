/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.customization.picker.clock.ui.viewmodel

import android.content.Context
import android.graphics.Color
import com.android.customization.picker.clock.domain.interactor.ClockPickerInteractor
import com.android.customization.picker.clock.shared.ClockSize
import com.android.customization.picker.color.ui.viewmodel.ColorOptionViewModel
import com.android.wallpaper.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

/** View model for the clock settings screen. */
class ClockSettingsViewModel(val context: Context, val interactor: ClockPickerInteractor) {

    enum class Tab {
        COLOR,
        SIZE,
    }

    val colorOptions: Flow<List<ColorOptionViewModel>> =
        interactor.selectedClockColor.map { selectedColor ->
            buildList {
                // TODO (b/241966062) Change design of the placeholder for default theme color
                add(
                    ColorOptionViewModel(
                        color0 = Color.TRANSPARENT,
                        color1 = Color.TRANSPARENT,
                        color2 = Color.TRANSPARENT,
                        color3 = Color.TRANSPARENT,
                        contentDescription = "description",
                        isSelected = selectedColor == null,
                        onClick =
                            if (selectedColor == null) {
                                null
                            } else {
                                { interactor.setClockColor(null) }
                            },
                    )
                )
                COLOR_LIST.forEach { color ->
                    add(
                        ColorOptionViewModel(
                            color0 = color,
                            color1 = color,
                            color2 = color,
                            color3 = color,
                            contentDescription = "description",
                            isSelected = selectedColor == color,
                            onClick =
                                if (selectedColor == color) {
                                    null
                                } else {
                                    { interactor.setClockColor(color) }
                                },
                        )
                    )
                }
            }
        }

    val selectedClockSize: Flow<ClockSize> = interactor.selectedClockSize

    fun setClockSize(size: ClockSize) {
        interactor.setClockSize(size)
    }

    private val _selectedTabPosition = MutableStateFlow(Tab.COLOR)
    val selectedTabPosition: StateFlow<Tab> = _selectedTabPosition.asStateFlow()
    val tabs: Flow<List<ClockSettingsTabViewModel>> =
        selectedTabPosition.map {
            listOf(
                ClockSettingsTabViewModel(
                    name = context.resources.getString(R.string.clock_color),
                    isSelected = it == Tab.COLOR,
                    onClicked =
                        if (it == Tab.COLOR) {
                            null
                        } else {
                            { _selectedTabPosition.tryEmit(Tab.COLOR) }
                        }
                ),
                ClockSettingsTabViewModel(
                    name = context.resources.getString(R.string.clock_size),
                    isSelected = it == Tab.SIZE,
                    onClicked =
                        if (it == Tab.SIZE) {
                            null
                        } else {
                            { _selectedTabPosition.tryEmit(Tab.SIZE) }
                        }
                ),
            )
        }

    companion object {
        // TODO (b/241966062) The color integers here are temporary for dev purposes. We need to
        //                    finalize the overridden colors.
        val COLOR_LIST = listOf(-2563329, -8775, -1777665, -5442872)
    }
}