package com.example.movienow.activity

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.movienow.R
import com.example.movienow.data.local.AppSharePreferences
import com.example.movienow.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actions_button, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.action_dark_mode ->  handleChangeTheme()
            R.id.action_fav -> {
                Navigation.findNavController(this, R.id.container_fragment).navigate(R.id.favoriteMoviesFragment)
                return true
            }
            else -> {return true}
        }
    }

    private fun handleChangeTheme(): Boolean {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.choose_theme)
        val styles = arrayOf("Light Mode", "Dark Mode", "System Default")
        val checkedItem = AppSharePreferences(this).darkMode

        builder.setSingleChoiceItems(styles, checkedItem){
                dialog, which ->
            when(which){
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    AppSharePreferences(this).darkMode = 0
                    dialog.dismiss()
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    AppSharePreferences(this).darkMode = 1
                    dialog.dismiss()
                }
                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    AppSharePreferences(this).darkMode = 2
                    dialog.dismiss()
                }
            }
        }

        val dialog = builder.create()
        dialog.show()
        return true
    }
}