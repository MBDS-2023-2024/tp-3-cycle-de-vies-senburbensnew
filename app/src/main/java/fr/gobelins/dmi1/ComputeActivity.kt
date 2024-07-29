package fr.gobelins.dmi1

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class ComputeActivity : AppCompatActivity() {
    private lateinit var firstOperand:TextInputEditText
    private lateinit var secondOperand:TextInputEditText
    private lateinit var resultTextView: TextView
    private lateinit var operationView: TextView
    private lateinit var btnCompute: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.compute_activity)

        firstOperand = findViewById(R.id.first_operand)
        secondOperand = findViewById(R.id.second_operand)
        resultTextView = findViewById(R.id.result)
        btnCompute = findViewById(R.id.btn_compute)
        operationView = findViewById(R.id.operation_textview)

        val operation = intent.getStringExtra("operation") ?: "ADD"
        operationView.text = "operation : " + operation

        btnCompute.setOnClickListener {
            resultTextView.text = ""
            val firstNumber = firstOperand.text.toString()
            val secondNumber = secondOperand.text.toString()

            val num1 = firstNumber.toDoubleOrNull()
            val num2 = secondNumber.toDoubleOrNull()

            if (num1 == null || num2 == null) {
                Toast.makeText(this, "Veuillez entrer des nombres valides", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val result = when (operation) {
                "ADD" -> num1 + num2
                "SUBS" -> num1 - num2
                "MULT" -> num1 * num2
                "DIV" -> {
                    if (num2 == 0.0) {
                        Toast.makeText(this, "Division par zéro impossible", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    } else {
                        num1 / num2
                    }
                }
                else -> {
                    Toast.makeText(this, "Opération non supportée", Toast.LENGTH_SHORT).show()
                }
            }

            resultTextView.text = "Résultat: $result"
        }
    }
}