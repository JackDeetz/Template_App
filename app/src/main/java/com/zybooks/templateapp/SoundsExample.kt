package com.zybooks.templateapp

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt

class SoundsExample : AppCompatActivity()
{
    var mediaPlayer: MediaPlayer? = null
    var soundPool: SoundPool? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sounds_example)

        soundPool = SoundPool(6, AudioManager.STREAM_MUSIC, 0)
        //loads audio file into soundID 1
        soundPool!!.load(this.baseContext, R.raw.sherry_audio_two, 1)
        //loads audio file into soundID 2
        soundPool!!.load(this.baseContext, R.raw.sherry_audio, 1)
    }

    fun mediaPlayerButtonClicked(view: View)
    {
        if (mediaPlayer == null)
        {
            mediaPlayer = MediaPlayer.create(this, R.raw.sherry_audio)
            mediaPlayer?.start()
        }
        else
        {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    fun soundPoolButtonClicked(view: View)
    {
        // Load a sound from a raw resource
        // Play splat sound
        var success = soundPool?.play(nextInt(1,3),  // sound ID
            1f,       // left volume (0.0 to 1.0)
            1f,       // right volume (0.0 to 1.0)
            0,        // priority (0 = lowest)
            0,        // loop (0 = no loop, -1 = loop forever)
            1f)       // playback rate (0.5 to 2.0 with 1.0 = normal)
        var textViewResults : TextView = findViewById(R.id.textView40)
        textViewResults.text = success.toString()
    }
}