package com.example.iswara.customview

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import com.example.iswara.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class TextInputLayoutEmail : TextInputLayout {

    val isError get() = !TextUtils.isEmpty(this.error)

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }

    fun setValidation(childEditText: EditText?) {
        childEditText?.addTextChangedListener(object  : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                error = if(!isValidEmail(s)) context.getString(R.string.invalid_email) else {
                    isErrorEnabled = false
                    null
                }
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })
    }

    private fun isValidEmail(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}