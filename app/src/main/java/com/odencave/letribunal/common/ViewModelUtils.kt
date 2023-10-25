@file:Suppress("UNCHECKED_CAST")

package com.odencave.letribunal.common

import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras

fun <T: ViewModelStoreOwner> CreationExtras.getActivity(): T {
    return (this[VIEW_MODEL_STORE_OWNER_KEY] as T)
}