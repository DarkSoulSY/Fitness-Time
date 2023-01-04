package com.example.fitnesstime.validation

import android.content.Context
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast

class Validation {
    companion object{
        public fun validateInput(view: EditText, context: Context) : Boolean
        {
            if (view.text.toString().isBlank()) {
                Toast.makeText(context, "${view.hint} is empty!", Toast.LENGTH_SHORT).show()
                return false
            }
                return true
        }

    }
}