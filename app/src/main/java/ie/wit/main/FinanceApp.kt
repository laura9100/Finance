package ie.wit.main

import android.app.Application
import android.net.Uri
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference

import ie.wit.models.FinanceModel
import ie.wit.models.FinanceStore


class FinanceApp : Application() {

//    lateinit var financesStore: FinanceStore
    lateinit var storage: StorageReference
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference
    lateinit var userImage: Uri


    override fun onCreate() {
        super.onCreate()
      //  financesStore = FinanceMemStore()
      // financesStore = FinanceJSONStore(applicationContext)
        Log.v("Finance","Finance App started")
    }


}