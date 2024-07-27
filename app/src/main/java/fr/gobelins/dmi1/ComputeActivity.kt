package fr.gobelins.dmi1

import android.annotation.SuppressLint
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
    private lateinit var btnCompute: Button

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.compute_activity)

        val operation = intent.getStringExtra("operation") ?: "ADD"

        firstOperand = findViewById(R.id.first_operand)
        secondOperand = findViewById(R.id.second_operand)
        resultTextView = findViewById(R.id.result)
        btnCompute = findViewById(R.id.btn_compute)

        btnCompute.setOnClickListener {
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
                "SUBTRACT" -> num1 - num2
                "MULTIPLY" -> num1 * num2
                "DIVIDE" -> {
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