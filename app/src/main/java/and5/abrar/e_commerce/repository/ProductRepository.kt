package and5.abrar.e_commerce.repository

import and5.abrar.e_commerce.model.category.GetCategorySellerItem
import and5.abrar.e_commerce.network.ApiService
import javax.inject.Inject


class ProductRepository@Inject constructor(private val api : ApiService) {
    suspend fun getSellerCategory(): List<GetCategorySellerItem>{
        return api.GetCategory()
    }


}