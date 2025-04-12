package com.example.cumulora.entry

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cumulora.data.local.sharedpref.SharedPreferenceHelper
import com.example.cumulora.utils.CURRENT_LANG
import com.example.cumulora.utils.LANG
import java.util.Locale

class MainViewModel(val pref : SharedPreferenceHelper) : ViewModel(){

    fun applyLanguage(context: Context) {
        CURRENT_LANG = pref.getData(LANG, "en")
//        pref.saveData(LANG, languageCode)
        val locale = Locale(CURRENT_LANG)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}

