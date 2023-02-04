package com.example.fitnesstime.utilities

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import java.util.regex.Matcher
import java.util.regex.Pattern

class Validator {
    companion object{
        fun validateInput(view: EditText, context: Context) : Boolean
        {
            if (view.text.toString().isBlank()) {
                Toast.makeText(context, "${view.hint} is empty!", Toast.LENGTH_SHORT).show()
                return false
            }
                return true
        }
        fun isValidPassword(password: String, view: View): Boolean {
            val pattern: Pattern
            val passwordPattern =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
            pattern = Pattern.compile(passwordPattern)

            val matcher: Matcher = pattern.matcher(password)

            if (!matcher.matches())
                Snackbar.make(view, "Password must include one number," +
                        "one capital and small letter Alphabet," +
                        "\none special character @ # '\' $ % ^ & + =", Snackbar.LENGTH_LONG).setTextMaxLines(4)
                    .show()


            return matcher.matches()
        }
    }
}