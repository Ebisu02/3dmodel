package ru.com.model

import android.content.Context
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import ru.com.model.gl.GLObject
import ru.com.model.gl.IGLObject
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class MyRenderer(ctx: Context) : GLSurfaceView.Renderer {
    private val objects: MutableList<IGLObject> = mutableListOf()
    private val bgColorR: Float = 0.0f
    private val bgColorG: Float = 0.0f
    private val bgColorB: Float = 0.0f
    private val bgColorA: Float = 1.0f

    init {
        val baseShader = Utils.inputStreamToString(ctx.assets.open("shaders/base_shader.vert"))
        val colorShader = Utils.inputStreamToString(ctx.assets.open("shaders/color_shader.frag"))
        val textureShader =
            Utils.inputStreamToString(ctx.assets.open("shaders/texture_shader.frag"))

        // stol
        val table = GLObject.fromInputStream(
            baseShader,
            textureShader,
            ctx.assets.open("models/table/table.obj"),
            ctx.assets.open("models/table/table.jpg")
        )
        table.setPosition(0.0f, -1.0f, -9f)
        table.rotateZ = 90.0f
        table.rotateX = 180.0f
        table.rotateY = 250.0f
        table.setScale(0.012f, 0.01f, 0.01f)

        // arbuz
        val wmelon = GLObject.fromInputStream(
            baseShader,
            textureShader,
            ctx.assets.open("models/watermelon/watermelon.obj"),
            ctx.assets.open("models/watermelon/watermelon.jpg")
        )
        wmelon.rotateY = 250.0f
        wmelon.setPosition(0.70f, -0.1f, -7f)
        wmelon.setScale(0.012f, 0.01f, 0.01f)

        // whiskey
        val whiskey = GLObject.fromInputStream(
            baseShader,
            colorShader,
            ctx.assets.open("models/drink/glass.obj"),
        )
        whiskey.rotateY = 250.0f
        whiskey.setPosition(0.30f, -0.30f, -7f)
        whiskey.setScale(0.04f, 0.04f, 0.04f)

        // grusha
        val pear = GLObject.fromInputStream(
            baseShader,
            textureShader,
            ctx.assets.open("models/pear/pear.obj"),
            ctx.assets.open("models/pear/pear.jpg")
        )
        pear.rotateX = 270.0f
        pear.setPosition(0.10f, -0.20f, -8f)
        pear.setScale(0.02f, 0.02f, 0.02f)

        // banana
        val banana = GLObject.fromInputStream(
            baseShader,
            textureShader,
            ctx.assets.open("models/banana/banana.obj"),
            ctx.assets.open("models/banana/banana.jpg")
        )
        banana.rotateY = 270.0f
        banana.setPosition(-0.10f, -0.20f, -8f)
        banana.setScale(0.002f, 0.002f, 0.002f)

        // pumpkin
        val pumpkin = GLObject.fromInputStream(
            baseShader,
            textureShader,
            ctx.assets.open("models/pumpkin/pumkin.obj"),
            ctx.assets.open("models/pumpkin/pumkin.jpg")
        )
        pumpkin.rotateX = 270.0f
        pumpkin.setPosition(-0.60f, -0.30f, -8f)
        pumpkin.setScale(0.015f, 0.015f, 0.015f)

        // sveta
        val sun = GLObject.fromInputStream(
            baseShader,
            colorShader,
            ctx.assets.open("models/candle/candle.obj")
        )
        sun.setPosition(0.0f, -2.0f, 0f)
        sun.setScale(0.005f, 0.005f, 0.005f)

        objects.add(table)
        objects.add(wmelon)
        objects.add(banana)
        objects.add(pear)
        objects.add(pumpkin)
        objects.add(whiskey)
        objects.add(sun)
    }

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        glEnable(GL_DEPTH_TEST)
        glDepthFunc(GL_LEQUAL)

        glEnable(GL_BLEND)
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)

        for (obj in objects) {
            obj.onSurfaceCreated(gl, config)
        }
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        glViewport(0, 0, width, height)

        for (obj in objects) {
            obj.onSurfaceChanged(gl, width, height)
        }
    }

    override fun onDrawFrame(gl: GL10) {
        glClearColor(bgColorR, bgColorG, bgColorB, bgColorA)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        val sun = (objects[objects.size - 1] as GLObject)

        for (obj in objects) {
            //            (obj as GLObject).rotateY += 1f
            obj.setLightDirection(sun.x - obj.x, sun.y + 1f - obj.y, sun.z - obj.z)
            obj.onDrawFrame(gl)
        }
    }
}