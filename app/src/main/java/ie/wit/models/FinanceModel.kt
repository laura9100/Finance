package ie.wit.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FinanceModel(var id: Long = 0,
                        val financemethod: String = "N/A",
                        val amount: Int = 0,
                        val financename: String = "N/A") : Parcelable

