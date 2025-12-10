package com.app.diploma

import androidx.lifecycle.ViewModel
import com.app.diploma.presentation.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val navigator: Navigator,
) : ViewModel()
