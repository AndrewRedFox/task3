package renderer;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL46C.*;

public class Loader {

    private List<Integer> Vaos = new ArrayList<>();
    private List<Integer> Vbos = new ArrayList<>();

    public RawModel loadToVao(float[] positions) {
        int vaoId = createVao();
        storeDataInAttribute(0, positions);
        unbindVao();
        return new RawModel(vaoId, positions.length / 3);
    }

    public void cleanUp() {
        for (int vao : Vaos) {
            glDeleteVertexArrays(vao);
        }
        for (int vbo : Vbos) {
            glDeleteBuffers(vbo);
        }
    }

    private int createVao() {
        int vaoId = glGenVertexArrays();
        Vaos.add(vaoId);
        glBindVertexArray(vaoId);
        return vaoId;
    }

    private void storeDataInAttribute(int attributeNumber, float[] data) {
        int vboId = glGenBuffers();
        Vbos.add(vboId);
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(attributeNumber, 3, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    private void unbindVao() {
        glBindVertexArray(0);
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }


}
