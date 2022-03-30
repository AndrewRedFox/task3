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
        glDrawElements(GL_TRIANGLES,model.getVertexCount(),GL_UNSIGNED_INT,0);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);//конец метода
    }
}
