package cyou.ma.bartender.di

import cyou.ma.bartender.api.CocktailApi
import cyou.ma.bartender.database.localIngredient.LocalIngredientDao
import cyou.ma.bartender.database.userIngredient.UserIngredientDao
import cyou.ma.bartender.repo.IngredientsRepo
import cyou.ma.bartender.repo.IngredientsRepoImp
import cyou.ma.bartender.repo.SearchRepo
import cyou.ma.bartender.repo.SearchRepoImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModules {

    @Singleton
    @Provides
    fun provideCocktailDbApi(): CocktailApi {
        return Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(CocktailApi::class.java)
    }

    @Singleton
    @Provides
    fun provideSearchRepo(api: CocktailApi): SearchRepo {
        return SearchRepoImp(api)
    }

    @Singleton
    @Provides
    fun provideIngredientRepo(
        api: CocktailApi,
        localIngredientDao: LocalIngredientDao,
        userIngredientDao: UserIngredientDao
    ): IngredientsRepo = IngredientsRepoImp(api, localIngredientDao, userIngredientDao)
}

