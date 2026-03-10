package com.example.mytrackerapp.repository


import androidx.lifecycle.LiveData
import com.example.mytrackerapp.data.dao.ExpenseDao
import com.example.mytrackerapp.data.model.ExpenseModel

class ExpenseRepository(private val dao: ExpenseDao) {

    val allExpenses: LiveData<List<ExpenseModel>> = dao.getAllExpenses()
    val totalAmount: LiveData<Double> = dao.getTotalAmount()

    suspend fun insert(expense: ExpenseModel) {
        dao.insert(expense)
    }

    suspend fun delete(expense: ExpenseModel) {
        dao.delete(expense)
    }
}