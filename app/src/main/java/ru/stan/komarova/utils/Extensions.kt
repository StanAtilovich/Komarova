package ru.stan.komarova.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.stan.komarova.R



fun AppCompatActivity.openFragment(fragment: Fragment){
    supportFragmentManager
        .beginTransaction()
        .replace(R.id.placeHolder, fragment)
        .commit()
}

