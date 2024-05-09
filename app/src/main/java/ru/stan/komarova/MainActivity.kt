package ru.stan.komarova

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ru.stan.komarova.databinding.ActivityMainBinding
import ru.stan.komarova.fragments.HotelsFragment
import ru.stan.komarova.fragments.ProfileFragment
import ru.stan.komarova.fragments.ShortsFragment
import ru.stan.komarova.fragments.SingsFragment
import ru.stan.komarova.fragments.TicketsFragment
import ru.stan.komarova.utils.openFragment
import ru.stan.komarova.viewModel.MyViewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        setBottomNavListener()


        if (savedInstanceState == null) {
            openFragment(TicketsFragment.newInstance())
        }
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