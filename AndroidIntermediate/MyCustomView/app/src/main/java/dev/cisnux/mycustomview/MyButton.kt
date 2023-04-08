package dev.cisnux.mycustomview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat

class MyButton : AppCompatButton {
    private lateinit var enabledBackground: Drawable
    private lateinit var disabledBackground: Drawable
    private var txtColor: Int = 0

    init {
        txtColor =
            ContextCompat.getColor(context, androidx.appcompat.R.color.background_material_light)
        AppCompatResources.getDrawable(context, R.drawable.bg_button)?.let(::enabledBackground::set)
        AppCompatResources.getDrawable(context, R.drawable.bg_button_disable)
            ?.let(::disabledBackground::set)

    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    // Metode onDraw() digunakan untuk mengcustom button ketika enable dan disable
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // Mengubah background dari Button
        background = if(isEnabled) enabledBackground else disabledBackground
        // Mengubah warna text pada button
        setTextColor(txtColor)
        // Mengubah ukuran text pada button
        textSize = 12f
        // Menjadikan object pada button menjadi center
        gravity = Gravity.CENTER
        // Mengubah text pada button pada kondisi enable dan disable
        text = if (isEnabled) "Submit" else "Isi dulu"
    }
}