package com.zybooks.templateapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.TextView
import androidx.core.view.GestureDetectorCompat
import java.util.*

class TouchGesturesExample : AppCompatActivity() {

    private lateinit var gestureDetector: GestureDetectorCompat
    private lateinit var textViewDisplayOutput : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touch_gestures_example)
        textViewDisplayOutput = findViewById(R.id.textViewTouchGesturesOutput)
        var arrayOfStrings = arrayOf("a", "b", "c")
        var indexPosition = 0
        fun indexTicker(index : Int): Int {
            if (index > 2)
            {
                indexPosition = 0
                return 0
            }
            indexPosition++
            return index
        }
        gestureDetector = GestureDetectorCompat(this,
            object: GestureDetector.OnGestureListener {

                override fun onDown(e: MotionEvent): Boolean {

                    arrayOfStrings[indexTicker(indexPosition)] = "onDown\n"
                    textViewDisplayOutput.text = Arrays.toString(arrayOfStrings)
                    return true
                }

                override fun onShowPress(e: MotionEvent) {
                    arrayOfStrings[indexTicker(indexPosition)] = "onShowPress\n"
                    textViewDisplayOutput.text = Arrays.toString(arrayOfStrings)
                }

                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    arrayOfStrings[indexTicker(indexPosition)] = "onSingleTapUp\n"
                    textViewDisplayOutput.text = Arrays.toString(arrayOfStrings)
                    return false
                }

                override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float,
                                      distanceY: Float): Boolean {
                    arrayOfStrings[indexTicker(indexPosition)] = "onScroll\n"
                    textViewDisplayOutput.text = Arrays.toString(arrayOfStrings)
                    return false
                }

                override fun onLongPress(e: MotionEvent) {
                    arrayOfStrings[indexTicker(indexPosition)] = "onLongPress\n"
                    textViewDisplayOutput.text = Arrays.toString(arrayOfStrings)
                }

                override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float,
                                     velocityY: Float): Boolean {
                    arrayOfStrings[indexTicker(indexPosition)] = "onFling\n"
                    textViewDisplayOutput.text = Arrays.toString(arrayOfStrings)
                    return true
                }
            })
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }
}