package ie.wit.models
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import ie.wit.helpers.exists
import ie.wit.helpers.read
import ie.wit.helpers.write
import org.jetbrains.anko.AnkoLogger
import java.util.*

val JSON_FILE = "finances.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<FinanceModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class FinanceJSONStore : FinanceStore, AnkoLogger {

    val context: Context
    var finances = mutableListOf<FinanceModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<FinanceModel> {
        return finances
    }

    override fun create(finance: FinanceModel) {
        finance.id = generateRandomId()
        finances.add(finance)
        serialize()
    }
    override fun findIncome() : List<FinanceModel> {
        var financeList = mutableListOf<FinanceModel>()
        for (finance in finances) {
            if (finance.financemethod.equals("Income"))
                financeList.add(finance)
        }
        return financeList
    }
    override fun findSpending() : List<FinanceModel> {
        var financeList = mutableListOf<FinanceModel>()
        for (finance in finances) {
            if (finance.financemethod.equals("Spending"))
                financeList.add(finance)
        }
        return financeList
    }

    override fun findTotalIncome() : Int {
        var total: Int
        total = 0
        for(finance in finances){
            if(finance.financemethod.equals("Income"))
                total += finance.amount
        }
        return total
    }
    override fun findTotalSpending() : Int {
        var total: Int
        total = 0
        for(finance in finances){
            if(finance.financemethod.equals("Spending"))
                total += finance.amount
        }
        return total
    }
    override fun totalSaved() : Int {
        var total: Int
        total = findTotalIncome() - findTotalSpending()
        return total
    }

    override fun findById(id:Long) : FinanceModel? {
        val foundFinance: FinanceModel? = finances.find { it.id == id }
        return foundFinance
    }


//    override fun update(recipe: RecipeModel) {
//        val recipeList = findAll() as ArrayList<RecipeModel>
//        var foundRecipe: RecipeModel? = recipeList.find { p -> p.id == recipe.id }
//        if (foundRecipe != null) {
//            foundRecipe.title = recipe.title
//            foundRecipe.description = recipe.description
//            foundRecipe.image = recipe.image
//            foundRecipe.lat = recipe.lat
//            foundRecipe.lng = recipe.lng
//            foundRecipe.zoom = recipe.zoom
//            foundRecipe.country=recipe.country
//            foundRecipe.method = recipe.method
//        }
//        serialize()
//    }
//
//    override fun delete(recipe: RecipeModel) {
//        recipes.remove(recipe)
//        serialize()
//    }


    private fun serialize() {
        val jsonString = gsonBuilder.toJson(finances, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        finances = Gson().fromJson(jsonString, listType)
    }
}