package com.example.mytrackerapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mytrackerapp.data.model.ExpenseModel


@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expense: ExpenseModel)

    @Delete
    suspend fun delete(expense: ExpenseModel)

    @Query("SELECT * FROM expense_table ORDER BY id DESC")
    fun getAllExpenses(): LiveData<List<ExpenseModel>>


    @Query("SELECT COALESCE(SUM(amount), 0.0) FROM expense_table")
    fun getTotalAmount(): LiveData<Double>
}