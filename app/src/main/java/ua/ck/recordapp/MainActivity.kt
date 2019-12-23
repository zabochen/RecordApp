package ua.ck.recordapp

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var myMediaRecorder: MediaRecorder? = null

    private var fileDirPath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUi()
    }

    private fun setUi() {

        // Disable Stop & Play Buttons
        button_stopRecord.isEnabled = false
        button_playRecord.isEnabled = false

        this.fileDirPath = this.getExternalFilesDir(null)?.absolutePath + "/recording.3gp"

        button_startRecord.setOnClickListener {
            this.myMediaRecorder = MediaRecorder()
            this.myMediaRecorder!!.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setAudioChannels(1)
                setAudioEncodingBitRate(16 * 44100)
                setAudioSamplingRate(44100)
                setOutputFile(fileDirPath)
            }

            try {
                this.myMediaRecorder!!.apply {
                    prepare()
                    start()
                }
            } catch (e: Exception) {

            }

            // Disable Stop & Play Buttons
            button_startRecord.isEnabled = false
            button_stopRecord.isEnabled = true
            button_playRecord.isEnabled = false
        }

        button_stopRecord.setOnClickListener {
            this.myMediaRecorder!!.apply {
                stop()
                release()
            }
            this.myMediaRecorder = null

            // Disable Stop
            button_startRecord.isEnabled = true
            button_stopRecord.isEnabled = false
            button_playRecord.isEnabled = true
        }

        button_playRecord.setOnClickListener {
            button_startRecord.isEnabled = false
            button_stopRecord.isEnabled = false
            button_playRecord.isEnabled = false


            val mediaPlayer = MediaPlayer()

            try {
                mediaPlayer.apply {
                    setDataSource(fileDirPath)
                    prepare()
                    start()
                }
                mediaPlayer.setOnCompletionListener {
                    button_startRecord.isEnabled = true
                    button_stopRecord.isEnabled = false
                    button_playRecord.isEnabled = true
                }
            } catch (e: Exception) {
            }
        }
    }
}
