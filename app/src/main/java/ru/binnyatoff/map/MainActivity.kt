package ru.binnyatoff.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import ru.binnyatoff.map.databinding.ActivityMainBinding
import ru.binnyatoff.map.screens.history.HistoryFragment
import ru.binnyatoff.map.screens.map.MapFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        //BottomNavigation
        binding.bottomNavigation.setOnNavigationItemReselectedListener { item ->
            when(item.itemId) {
                R.id.map -> {
                    supportFragmentManager.commit {
                        replace(R.id.fragment_container, MapFragment())
                    }
                }
                R.id.history -> {
                    supportFragmentManager.commit {
                        replace(R.id.fragment_container, HistoryFragment())
                    }
                }
            }
        }
    }
}