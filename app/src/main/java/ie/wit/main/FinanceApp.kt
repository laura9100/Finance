package ie.wit.main

import android.app.Application
import android.net.Uri
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference


class FinanceApp : Application() {

    lateinit var storage: StorageReference
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference
    lateinit var userImage: Uri


    override fun onCreate() {
        super.onCreate()
        Log.v("Finance","Finance App started")
    }


}