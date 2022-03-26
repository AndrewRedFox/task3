package task3.test;

import core.WindowManager;
import input.keyboard;
import input.mouse;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class Launcher {

    private WindowManager manager;

    public void run() {
        init();
    }

    private void init() {
        this.manager = new WindowManager(720, 480, "negr");
        manager.init();
        update();
    }

    public FloatBuffer storeDataInBuffer(float[] data) {//хранит bufferFloat и возвращает buffer с данными
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
        buffer.put(data);//суем данные
        buffer.flip();

        return buffer;
    }

    private void update() {

        float[] v_positions =
                {
                        0.0f, 0.5f, 0f, -0.5f, -0.5f, 0f, 0.5f, -0.5f, 0f
                };

        int vaoId = GL30.glGenVertexArrays();//сгенерировали id для Vao
        GL30.glBindVertexArray(vaoId);//связали id Vao

        int vboId = GL30.glGenBuffers();//сгенерировали id для Vbo
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboId);//связали id Vbo

        FloatBuffer buffer = this.storeDataInBuffer(v_positions);
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, buffer, GL30.GL_STATIC_DRAW);//тип  рисовки
        //GL30.glVertexPointer(0,3, GL11.GL_FLOAT,false);
        GL30.glVertexPointer(0, 3, 0, GL11.GL_FLOAT);
        MemoryUtil.memFree(buffer);//очищает буфер
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboId);//отвязываем буфер
        GL30.glBindVertexArray(vaoId);//отвязываем буфер

        while (!GLFW.glfwWindowShouldClose(WindowManager.getWindow().getThisWindow())) {
            if (keyboard.keyPressed(GLFW.GLFW_KEY_A)) {
                System.out.println("Клавиша А нажата");
            }
            if (mouse.buttonPressed(GLFW_MOUSE_BUTTON_1)) {
                System.out.println("ЛКМ нажата");
            }
            System.out.println(mouse.getMouseX() + " " + mouse.getMouseY());
            keyboard.handleKeyboardInput();//обработчик нажатий клавиатуры
            mouse.handleMouseInput();//обработчик нажатий мыши
            //пререндер
            GL11.glClearColor(0, 1, 0, 1);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);//очищаем буфер пикселей

            GL30.glBindVertexArray(vaoId);//начало метода
            GL30.glEnableVertexAttribArray(0);
            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, v_positions.length / 3);//последнее это число вершин
            //GL11.glDrawElements(GL11.GL_TRIANGLES,20,GL11.GL_UNSIGNED_INT,0);

            GL30.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(vaoId);//конец метода


            manager.loop();
            //rendering
        }
        glfwDestroyWindow(WindowManager.getWindow().getThisWindow());
    }

}
