package ru.stan.komarova

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.stan.komarova.databinding.ActivityMainBinding
import ru.stan.komarova.fragments.HotelsFragment
import ru.stan.komarova.fragments.ProfileFragment
import ru.stan.komarova.fragments.ShortsFragment
import ru.stan.komarova.fragments.SingsFragment
import ru.stan.komarova.fragments.TicketsFragment
import ru.stan.komarova.utils.openFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBottomNavListener()
        openFragment(TicketsFragment.newInstance())
    }

    private fun setBottomNavListener() {
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.tickets -> {
                    openFragment(TicketsFragment.newInstance())
                }

                R.id.hotel -> {
                    openFragment(HotelsFragment.newInstance())
                }

                R.id.shorts -> {
                    openFragment(ShortsFragment.newInstance())
                }

                R.id.sings -> {
                    openFragment(SingsFragment.newInstance())
                }

                R.id.profile -> {
                    openFragment(ProfileFragment.newInstance())
                }
            }
            true
        }
    }
}