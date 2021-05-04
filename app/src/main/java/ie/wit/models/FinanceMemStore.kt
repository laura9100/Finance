//package ie.wit.models
//
//import android.util.Log
//import ie.wit.R
//import ie.wit.fragments.FinanceFragment
//
//var lastId = 0L
//
//internal fun getId(): Long {
//    return lastId++
//}
//
//class FinanceMemStore : FinanceStore {
//
//        val finances = ArrayList<FinanceModel>()
//
//        override fun findAll(): List<FinanceModel> {
//            return finances
//        }
//    override fun findTotalIncome() : Int {
//        var total: Int
//        total = 0
//        for(finance in finances){
//            if(finance.financemethod.equals("Income"))
//                total += finance.amount
//        }
//        return total
//    }
//    override fun findTotalSpending() : Int {
//        var total: Int
//        total = 0
//        for(finance in finances){
//            if(finance.financemethod.equals("Spending"))
//                total += finance.amount
//        }
//        return total
//    }
//    override fun totalSaved() : Int {
//        var total: Int
//        total = findTotalIncome() - findTotalSpending()
//        return total
//    }
//
//
//    override fun findIncome() : List<FinanceModel> {
//        var financeList = mutableListOf<FinanceModel>()
//        for (finance in finances) {
//            if (finance.financemethod.equals("Income"))
//                financeList.add(finance)
//        }
//        return financeList
//    }
//    override fun findSpending() : List<FinanceModel> {
//        var financeList = mutableListOf<FinanceModel>()
//        for (finance in finances) {
//            if (finance.financemethod.equals("Spending"))
//                financeList.add(finance)
//        }
//        return financeList
//    }
//
//        override fun findById(id:Long) : FinanceModel? {
//            val foundFinance: FinanceModel? = finances.find { it.id == id }
//            return foundFinance
//        }
//
//        override fun create(finance: FinanceModel) {
//            finance.id = getId()
//            finances.add(finance)
//            logAll()
//        }
//
//        fun logAll() {
//            Log.v("Finance","** Finance List **")
//            finances.forEach { Log.v("Finance","${it}") }
//        }
//    }
