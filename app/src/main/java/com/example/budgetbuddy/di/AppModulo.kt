package com.example.budgetbuddy.di

import android.app.Application
import androidx.room.Room
import androidx.room.Update
import com.example.budgetbuddy.data.data_source.database.ApplicationDatabase
import com.example.budgetbuddy.data.repository.BudgetRepositoryImpl
import com.example.budgetbuddy.data.repository.ClientRepositoryImpl
import com.example.budgetbuddy.data.repository.ProductRepositoryImpl
import com.example.budgetbuddy.domain.model.Client
import com.example.budgetbuddy.domain.model.Product
import com.example.budgetbuddy.domain.repository.IBuggetRepository
import com.example.budgetbuddy.domain.repository.IRepository
import com.example.budgetbuddy.domain.use_case.budget.BudgetUseCases
import com.example.budgetbuddy.domain.use_case.budget.GetBudgetUseCase
import com.example.budgetbuddy.domain.use_case.budget.GetBudgetsProductsUseCase
import com.example.budgetbuddy.domain.use_case.budget.GetBudgetsUseCase
import com.example.budgetbuddy.domain.use_case.budget.InsertBudgetUseCase
import com.example.budgetbuddy.domain.use_case.budget.InsertProductBudgetUseCase
import com.example.budgetbuddy.domain.use_case.client.ClientUseCases
import com.example.budgetbuddy.domain.use_case.client.DeleteClientUseCase
import com.example.budgetbuddy.domain.use_case.client.GetClientUseCase
import com.example.budgetbuddy.domain.use_case.client.GetClientsUseCase
import com.example.budgetbuddy.domain.use_case.client.InsertClientUseCase
import com.example.budgetbuddy.domain.use_case.client.UpdateClientUseCase
import com.example.budgetbuddy.domain.use_case.product.DeleteProductUseCase
import com.example.budgetbuddy.domain.use_case.product.GetProductUseCase
import com.example.budgetbuddy.domain.use_case.product.GetProductsUseCase
import com.example.budgetbuddy.domain.use_case.product.InsertProductUseCase
import com.example.budgetbuddy.domain.use_case.product.ProductUseCases
import com.example.budgetbuddy.domain.use_case.product.UpdateProductUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModulo {


    @Provides
    @Singleton
    fun provideDatabase(application: Application): ApplicationDatabase{
        return Room.databaseBuilder(
            application,
            ApplicationDatabase::class.java,
            ApplicationDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideProductRepository(db: ApplicationDatabase): ProductRepositoryImpl{
        return ProductRepositoryImpl(db.productDao)
    }

    @Provides
    @Singleton
    fun provideClientRepository(db: ApplicationDatabase): ClientRepositoryImpl{
        return ClientRepositoryImpl(db.clientDao)
    }

    @Provides
    @Singleton
    fun provideBudgetRepository(db: ApplicationDatabase): BudgetRepositoryImpl{
        return BudgetRepositoryImpl(db.budgetDao)
    }

    @Provides
    @Singleton
    fun provideProductUseCases(productRepository: IRepository<Product, String>): ProductUseCases{
        return ProductUseCases(
            insertProductUseCase = InsertProductUseCase(productRepository),
            getProductUseCase = GetProductUseCase(productRepository),
            getProductsUseCase = GetProductsUseCase(productRepository),
            updateProductUseCase = UpdateProductUseCase(productRepository),
            deleteProductUseCase = DeleteProductUseCase(productRepository)
        )
    }

    @Provides
    @Singleton
    fun provideClientUseCases(clientRepository: IRepository<Client, String>): ClientUseCases{
        return ClientUseCases(
            insertClientUseCase = InsertClientUseCase(clientRepository),
            getClientUseCase = GetClientUseCase(clientRepository),
            getClientsUseCase = GetClientsUseCase(clientRepository),
            updateClientUseCase = UpdateClientUseCase(clientRepository),
            deleteClientUseCase = DeleteClientUseCase(clientRepository)
        )
    }

    @Provides
    @Singleton
    fun provideBudgetUseCases(budgetRepository: IBuggetRepository, clientRepository: IRepository<Client, String>, productRepository: IRepository<Product, String>): BudgetUseCases{
        return BudgetUseCases(
            insertBudgetUseCase = InsertBudgetUseCase(budgetRepository, clientRepository),
            getBudgetUseCase = GetBudgetUseCase(budgetRepository),
            getBudgetsUseCase = GetBudgetsUseCase(budgetRepository),
            getBudgetsProductsUseCase = GetBudgetsProductsUseCase(budgetRepository),
            insertBudgetProductUseCase = InsertProductBudgetUseCase(budgetRepository, productRepository)
        )
    }
}