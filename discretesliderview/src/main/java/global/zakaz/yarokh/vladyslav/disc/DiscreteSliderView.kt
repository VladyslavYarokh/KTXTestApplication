package global.zakaz.yarokh.vladyslav.disc

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.marginBottom
import androidx.core.view.marginStart
import com.google.android.material.slider.Slider
import com.google.android.material.textview.MaterialTextView

class DiscreteSliderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var selectedValue: Int? = null
    private var initialized: Boolean = false

    init {
        val view = View.inflate(context, R.layout.discrete_slider_view, this)
        attrs?.let {
            val a = context.obtainStyledAttributes(it, R.styleable.DiscreteSliderView)
            val title = a.getString(R.styleable.DiscreteSliderView_ds_title)
            val startValue = a.getFloat(R.styleable.DiscreteSliderView_startValue, 1.0f)
            val endValue = a.getFloat(R.styleable.DiscreteSliderView_endValue, 10.0f)
            val step = a.getFloat(R.styleable.DiscreteSliderView_step, 1.0f)
            view.findViewById<MaterialTextView>(R.id.tvSliderTitle).text = title
            view.findViewById<MaterialTextView>(R.id.tvSliderDesc).text = "1 - ${context.getString(R.string.never)}, 10 - ${context.getString(R.string.orders_feedback_recommend_likelihood_great)}"
            view.findViewById<Slider>(R.id.discreteSlider).apply {
                valueFrom = startValue
                valueTo = endValue
                stepSize = step
                trackInactiveTintList = ColorStateList.valueOf(context.getColor(R.color.grey_light))
                trackActiveTintList = ColorStateList.valueOf(context.getColor(R.color.green))
                thumbTintList = ColorStateList.valueOf(context.getColor(R.color.grey_light))
            }

            createIntervals(endValue.toInt(), view)
            a.recycle()
        }

        view.findViewById<Slider>(R.id.discreteSlider).addOnChangeListener { _, value, _ ->
            selectedValue = value.toInt()
            onRedrawIndicatorState(value.toInt(), view)
        }
    }

    private fun createIntervals(size: Int, view: View){
        for (itemPosition in 1 until size + 1) {
            val container: View = LayoutInflater.from(context).inflate(R.layout.discrete_slider_tick_item, null) as View
            val tvTickItem = container.findViewById(R.id.tvTickItem) as TextView
            tvTickItem.apply {
                text = itemPosition.toString()
                layoutParams = getLayoutParams(itemPosition)
            }
            view.findViewById<LinearLayout>(R.id.llIntervals).addView(tvTickItem)
        }
    }

    private fun getLayoutParams(position: Int): LayoutParams {
        val weight: Float = when(position){
            10 -> 0f
            else -> 1f
        }
        return LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT, weight
        )
    }

    private fun onRedrawIndicatorState(position: Int, view: View){
        view.findViewById<LinearLayout>(R.id.llIntervals).children.forEach {
            val textView = it as TextView
            if(textView.text.equals(position.toString())) {
                textView.apply {
                    textSize = 17f
                    setTextColor(context.getColor(R.color.green))
                }
            } else {
                textView.apply {
                    textSize = 13f
                    setTextColor(context.getColor(R.color.grey_time))
                }
            }
        }

        if(!initialized) {
            view.findViewById<Slider>(R.id.discreteSlider).apply {
                thumbTintList = ColorStateList.valueOf(context.getColor(R.color.green))
            }
        }
    }
}