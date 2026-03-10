package com.example.mytrackerapp.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytrackerapp.data.model.ExpenseModel
import com.example.mytrackerapp.databinding.ActivityMainBinding
import com.example.mytrackerapp.databinding.DialogAddExpenseBinding
import com.example.mytrackerapp.ui.adapter.ExpenseAdapter
import com.example.mytrackerapp.ui.viewmodel.ExpenseViewModel
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ExpenseViewModel
    private lateinit var adapter: ExpenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]


        adapter = ExpenseAdapter { expense ->
            viewModel.delete(expense)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)


        viewModel.allExpenses.observe(this) {
            adapter.setExpenses(it)
        }

        viewModel.totalAmount.observe(this) { total ->
            binding.tvTotal.text = "Toplam: ₺${String.format("%.2f", total ?: 0.0)}"
        }

        binding.fabAdd.setOnClickListener {
            showAddDialog()
        }
    }

    private fun showAddDialog() {

        val dialogBinding = DialogAddExpenseBinding.inflate(LayoutInflater.from(this))

        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .create()


        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialogBinding.btnSave.setOnClickListener {
            val title = dialogBinding.etTitle.text.toString()
            val amountStr = dialogBinding.etAmount.text.toString()
            val amount = amountStr.toDoubleOrNull() ?: 0.0
            val category = dialogBinding.etCategory.text.toString()
            val date = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())

            if (title.isNotEmpty() && amount > 0) {
                val expense = ExpenseModel(title = title, amount = amount, category = category, date = date)


                viewModel.insert(expense)

                dialog.dismiss()
            } else {
                if (title.isEmpty()) dialogBinding.etTitle.error = "Açıklama boş olamaz"
                if (amount <= 0) dialogBinding.etAmount.error = "Geçerli bir tutar girin"
            }
        }

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}