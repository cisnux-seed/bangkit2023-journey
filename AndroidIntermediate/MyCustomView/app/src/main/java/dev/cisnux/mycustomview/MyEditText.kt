package dev.cisnux.mycustomview

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatEditText

class MyEditText : AppCompatEditText, View.OnTouchListener {
    private var clearButtonImage: Drawable

    init {
        clearButtonImage =
            AppCompatResources.getDrawable(context, R.drawable.ic_close_black_24dp) as Drawable
        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) showClearButton() else hideClearButton()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onTouch(v: View?, event: MotionEvent): Boolean =
        if (compoundDrawables[2] != null) {
            val clearButtonStart: Float
            val clearButtonEnd: Float
            var isClearButtonClicked = false
            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                clearButtonEnd = (clearButtonImage.intrinsicWidth + paddingStart).toFloat()
                when {
                    event.x < clearButtonEnd -> isClearButtonClicked = true
                }
            } else {
                clearButtonStart = (width - paddingEnd - clearButtonImage.intrinsicWidth).toFloat()
                when {
                    event.x > clearButtonStart -> isClearButtonClicked = true
                }
            }
            if (isClearButtonClicked) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        clearButtonImage =
                            AppCompatResources.getDrawable(
                                context,
                                R.drawable.ic_close_black_24dp
                            ) as Drawable
                        showClearButton()
                        true
                    }
                    MotionEvent.ACTION_UP -> {
                        clearButtonImage =
                            AppCompatResources.getDrawable(
                                context,
                                R.drawable.ic_close_black_24dp
                            ) as Drawable
                        when {
                            text != null -> text?.clear()
                        }
                        hideClearButton()
                        true
                    }
                    else -> false
                }
            } else false
        } else false


    private fun showClearButton() =
        setButtonDrawables(endOfTheText = clearButtonImage)

    private fun hideClearButton() =
        setButtonDrawables()


    private fun setButtonDrawables(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }
}