package ie.wit.models;

interface FinanceStore {
    fun findAll() : List<FinanceModel>
    fun findIncome() : List<FinanceModel>
    fun findSpending() : List<FinanceModel>
    fun findTotalIncome(): Int
    fun findTotalSpending(): Int
    fun totalSaved(): Int
    fun findById(id: Long) : FinanceModel?
    fun create(finance: FinanceModel)

}