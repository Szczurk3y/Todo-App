package com.example.innovateapprecruitmenttest.model

import android.widget.CalendarView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingListener
import java.util.*

class CalendarViewBindingAdapter {
    @BindingAdapter(value = ["android:onSelectedDayChange", "android:dateAttrChanged"], requireAll = false)
    fun setListeners(view: CalendarView, onDayChange: CalendarView.OnDateChangeListener?, attrChange: InverseBindingListener?) {
        if (attrChange == null) {
            view.setOnDateChangeListener(onDayChange)
        } else {
            view.setOnDateChangeListener(object : CalendarView.OnDateChangeListener {
                override fun onSelectedDayChange(view: CalendarView, year: Int, month: Int, dayOfMonth: Int) {
                    onDayChange?.onSelectedDayChange(view, year, month, dayOfMonth)
                    val instance = Calendar.getInstance()
                    instance.set(year, month, dayOfMonth)
                    view.setDate(instance.timeInMillis)
                    attrChange.onChange()
                }

            })
        }
    }
}