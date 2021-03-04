package com.kaval.android.myapplication.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import org.koin.core.component.KoinComponent

abstract class BaseViewModel(appContext: Application)
    : AndroidViewModel(appContext), KoinComponent