package ie.wit.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize
@IgnoreExtraProperties
@Parcelize
data class FinanceModel(var uid: String? ="",
                        val financemethod: String = "N/A",
                        val amount: Int = 0,
                        val financename: String = "N/A",
                        var email: String? = "joe@bloggs.com") : Parcelable
{
@Exclude
fun toMap(): Map<String, Any?> {
    return mapOf(
        "uid" to uid,
        "financemethod" to financemethod,
        "amount" to amount,
        "financename" to financename,
        "email" to email
    )
}
}
