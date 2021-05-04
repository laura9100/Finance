package ie.wit.main

import android.app.Application
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

import ie.wit.models.FinanceModel
import ie.wit.models.FinanceStore


class FinanceApp : Application() {

//    lateinit var financesStore: FinanceStore

    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference

    override fun onCreate() {
        super.onCreate()
      //  financesStore = FinanceMemStore()
      // financesStore = FinanceJSONStore(applicationContext)
        Log.v("Finance","Finance App started")
    }


}