package ru.stan.komarova.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.stan.komarova.R

fun Fragment.openFragment(fragment: Fragment){
    (activity as AppCompatActivity).supportFragmentManager
        .beginTransaction()
        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        .replace(R.id.placeHolder, fragment).commit()
}

fun AppCompatActivity.openFragment(fragment: Fragment){
    supportFragmentManager
        .beginTransaction()
        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        .replace(R.id.placeHolder, fragment).commit()
}

