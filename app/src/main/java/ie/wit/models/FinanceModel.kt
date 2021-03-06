package ie.wit.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize
@IgnoreExtraProperties
@Parcelize
data class FinanceModel(var uid: String? ="",
                        var financemethod: String = "N/A",
                        var amount: Int = 0,
                        var financename: String = "N/A",
                        var profilepic: String = "",
                        var isfavourite: Boolean = false,
                        var email: String? = "joe@bloggs.com") : Parcelable
{
@Exclude
fun toMap(): Map<String, Any?> {
    return mapOf(
        "uid" to uid,
        "financemethod" to financemethod,
        "amount" to amount,
        "financename" to financename,
        "profilepic" to profilepic,
        "isfavourite" to isfavourite,
        "email" to email
    )
}
}
