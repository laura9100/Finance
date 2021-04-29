package ie.wit.main

import android.app.Application
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import ie.wit.models.FinanceJSONStore
import ie.wit.models.FinanceMemStore
import ie.wit.models.FinanceModel
import ie.wit.models.FinanceStore


class FinanceApp : Application() {

    lateinit var financesStore: FinanceStore

    lateinit var auth: FirebaseAuth

    override fun onCreate() {
        super.onCreate()
        financesStore = FinanceMemStore()
        financesStore = FinanceJSONStore(applicationContext)
        Log.v("Finance","Finance App started")
    }


}