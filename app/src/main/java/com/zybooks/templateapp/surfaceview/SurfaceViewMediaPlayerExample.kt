package com.zybooks.templateapp.surfaceview

import android.graphics.Point
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.zybooks.templateapp.R

class SurfaceViewMediaPlayerExample : AppCompatActivity(), SurfaceHolder.Callback {

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
    }

    override fun surfaceCreated(holder: SurfaceHolder) {

        val surface = holder.surface
        setupMediaPlayer(surface)
        prepareMediaPlayer()
    }

    private lateinit var mediaPlayer: MediaPlayer
    private var playbackPosition = 0
    private val rtspUrl = "rtsp://184.72.239.149/vod/mp4:BigBuckBunny_175k.mov"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_surface_view_media_player_example)


        val holder = findViewById<SurfaceView>(R.id.mediaPlayerSurfaceView).holder
        holder.addCallback(this)

        findViewById<Button>(R.id.surfaceViewMediaPlayerPlayButton).setOnClickListener{
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
            }
            else {
                prepareMediaPlayer()
            }
        }
    }

    override fun onPause() {
        super.onPause()

        playbackPosition = mediaPlayer.currentPosition
    }

    override fun onStop() {
        mediaPlayer.stop()
        mediaPlayer.release()

        super.onStop()
    }

    private fun createAudioAttributes(): AudioAttributes {
        val builder = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MOVIE)
        return builder.build()
    }

    private fun setupMediaPlayer(surface: Surface) {
        findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
        mediaPlayer = MediaPlayer()
        mediaPlayer.setSurface(surface)
        val audioAttributes = createAudioAttributes()
        mediaPlayer.setAudioAttributes(audioAttributes)
        val uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.high_school_high)
        try {
            mediaPlayer.setDataSource(this, uri)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }

    private fun setSurfaceDimensions(player: MediaPlayer, width: Int, height: Int) {
        if(width > 0 && height > 0) {
            val aspectRatio = height.toFloat() / width.toFloat()
            val screenDimensions = Point()
            windowManager.defaultDisplay.getSize(screenDimensions)
            val surfaceWidth = screenDimensions.x
            val surfaceHeight = (surfaceWidth * aspectRatio).toInt()
            val params = FrameLayout.LayoutParams(surfaceWidth, surfaceHeight)
            findViewById<SurfaceView>(R.id.mediaPlayerSurfaceView).layoutParams = params
            val holder = findViewById<SurfaceView>(R.id.mediaPlayerSurfaceView).holder
            player.setDisplay(holder)
        }
    }

    private fun prepareMediaPlayer() {
        try {
            mediaPlayer.prepareAsync()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }

        mediaPlayer.setOnPreparedListener {
            findViewById<ProgressBar>(R.id.progressBar).visibility = View.INVISIBLE
            mediaPlayer.seekTo(playbackPosition)
            mediaPlayer.start()
        }

        mediaPlayer.setOnVideoSizeChangedListener { player, width, height ->
            setSurfaceDimensions(player, width, height)
        }
    }

}