package com.newyorktimesreader.presentation.common

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

  override fun onCleared() {
    super.onCleared()
    dispose()
  }

  abstract fun dispose()
}