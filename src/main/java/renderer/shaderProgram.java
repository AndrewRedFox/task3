package renderer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.lwjgl.opengl.GL46C.*;

public abstract class shaderProgram {

    private int programId;
    private int vertexShaderId;
    private int fragmentShaderId;

    public shaderProgram(String vertexFile, String fragmentFile) {//constructor
        vertexShaderId = loadShader(vertexFile, GL_VERTEX_SHADER);
        fragmentShaderId = loadShader(fragmentFile, GL_FRAGMENT_SHADER);
        programId=glCreateProgram();
        glAttachShader(programId,vertexShaderId);
        glAttachShader(programId,fragmentShaderId);glLinkProgram(programId);
        glValidateProgram(programId);
        bindAttributes();
    }

    public void start(){
        glUseProgram(programId);
    }

    public void stop(){
        glUseProgram(0);
    }

    public void cleanUp(){
        stop();
        glDetachShader(programId,vertexShaderId);
        glDetachShader(programId,fragmentShaderId);
        glDeleteShader(vertexShaderId);
        glDeleteShader(fragmentShaderId);
        glDeleteProgram(programId);
    }

    protected abstract void bindAttributes();

    protected void bindAttributes(int attribute, String variableName){
        glBindAttribLocation(programId,attribute, variableName);
    }

    private static int loadShader(String file, int type) {
        StringBuilder shaderSource = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Could not read File!");
            e.printStackTrace();
            System.exit(-1);
        }
        int shaderId = glCreateShader(type);
        glShaderSource(shaderId, shaderSource);
        glCompileShader(shaderId);
        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == GL_FALSE) {////////////////////
            System.out.println(glGetShaderInfoLog(shaderId, 500));
            System.err.println("Could not compile shader");
            System.exit(-1);
        }
        return shaderId;
    }


}
