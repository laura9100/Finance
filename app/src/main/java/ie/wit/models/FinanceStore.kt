package ie.wit.models;

interface FinanceStore {
    fun findAll() : List<FinanceModel>
    fun findIncome() : List<FinanceModel>
    fun findSpending() : List<FinanceModel>
    fun findById(id: Long) : FinanceModel?
    fun create(finance: FinanceModel)

}