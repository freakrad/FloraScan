package com.example.florascan.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.example.florascan.R

class PasswordEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs), View.OnTouchListener {


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }


    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.toString().length < 8) {
            setError(context.getString(R.string.min_pass), null)
        } else {
            error = null
        }
    }

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        return true
    }

}