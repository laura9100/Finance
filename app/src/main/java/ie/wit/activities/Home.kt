package ie.wit.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import ie.wit.R
import ie.wit.fragments.FavouritesFragment
import ie.wit.fragments.FinanceFragment
import ie.wit.fragments.IncomeFragment
import ie.wit.fragments.SpendingFragment
import ie.wit.main.FinanceApp
import ie.wit.utils.*
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.home.*
import kotlinx.android.synthetic.main.nav_header_home.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class Home : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    lateinit var ft: FragmentTransaction
    lateinit var app: FinanceApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        setSupportActionBar(toolbar)
        app = application as FinanceApp

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Welcome to our app",
                Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }

        navView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.getHeaderView(0).imageView
            .setOnClickListener { showImagePicker(this,1) }

        navView.getHeaderView(0).nav_header_email.text = app.auth.currentUser?.email

        navView.getHeaderView(0).nav_header_name.text = app.auth.currentUser?.displayName

        Picasso.get().load(app.auth.currentUser?.photoUrl)
            .resize(180, 180)
            .transform(CropCircleTransformation())
            .into(navView.getHeaderView(0).imageView, object : Callback {
                override fun onSuccess() {
                    // Drawable is ready
                    uploadImageView(app,navView.getHeaderView(0).imageView)
                }
                override fun onError(e: Exception) {}
            })

        checkExistingPhoto(app,this)


        ft = supportFragmentManager.beginTransaction()


        val fragment = FinanceFragment.newInstance()
        ft.replace(R.id.homeFrame, fragment)
        ft.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_finance -> navigateTo(FinanceFragment.newInstance())
            R.id.nav_income -> navigateTo(IncomeFragment.newInstance())
            R.id.nav_spending -> navigateTo(SpendingFragment.newInstance())
            R.id.nav_favourites -> navigateTo(FavouritesFragment.newInstance())
            R.id.nav_sign_out -> signOut()


            else -> toast("You Selected Something Else")
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_finance -> toast("You Selected Finance")
            R.id.action_income -> toast("You Selected Income")
            R.id.action_spending -> toast("You Selected Spending")
            R.id.action_favourites -> toast("You Selected Favourites")

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
         else
            super.onBackPressed()
    }

    private fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.homeFrame, fragment)
            .addToBackStack(null)
            .commit()
    }
    private fun signOut() {
        app.googleSignInClient.signOut().addOnCompleteListener(this) {
            app.auth.signOut()
            startActivity<Login>()
            finish()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                if (data != null) {
                    writeImageRef(app,readImageUri(resultCode, data).toString())
                    Picasso.get().load(readImageUri(resultCode, data).toString())
                        .resize(180, 180)
                        .transform(CropCircleTransformation())
                        .into(navView.getHeaderView(0).imageView, object : Callback {
                            override fun onSuccess() {
                                // Drawable is ready
                                uploadImageView(app,navView.getHeaderView(0).imageView)
                            }
                            override fun onError(e: Exception) {}
                        })
                }
            }
        }
    }
}
