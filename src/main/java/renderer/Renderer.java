package renderer;

import static org.lwjgl.opengl.GL46C.*;


public class Renderer {

    public void prepare() {
        glClearColor(0, 1, 0, 1);
        glClear(GL_COLOR_BUFFER_BIT);//очищаем буфер пикселей
    }

    public void render(RawModel model) {
        glBindVertexArray(model.getVaoId());//начало метода
        glEnableVertexAttribArray(0);
        glDrawArrays(GL_TRIANGLES, 0, model.getVertexCount());
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);//конец метода
    }
}
