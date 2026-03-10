package com.example.mytrackerapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense_table")
data class ExpenseModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val amount: Double,
    val category: String,
    val date: String
)