package ru.com.model

import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private var glSurfaceView: GLSurfaceView? = null
    private var isRendererSet = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        glSurfaceView = GLSurfaceView(this)
        glSurfaceView?.setEGLContextClientVersion(2)
        glSurfaceView?.setRenderer(MyRenderer(this))
        glSurfaceView?.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
        isRendererSet = true

        setContentView(glSurfaceView)
    }

    override fun onPause() {
        super.onPause()
        if (isRendererSet) {
            glSurfaceView?.onPause()
        }
    }

    override fun onResume() {
        super.onResume()
        if (isRendererSet) {
            glSurfaceView?.onResume()
        }
    }
}