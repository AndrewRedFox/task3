package renderer;

import static org.lwjgl.opengl.GL46C.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class shader {
    public int shaderId;

    public shader(String vertexShader, String fragmentShader) {

        Map<Integer, String> shaderSources = new HashMap<Integer, String>(2);
        shaderSources.put(1, this.readFile(vertexShader));
        shaderSources.put(2, this.readFile(fragmentShader));
        this.compile(shaderSources);

    }

    public void compile(Map<Integer, String> shaderSources) {
        int program = glCreateProgram();//возвращает id программы
        int shaderIdIndex = 1;
        List<Integer> shadersId = new ArrayList<Integer>();

        for (int i = 0; i < shaderSources.size(); i++) {
            int type = i == 0 ? GL_VERTEX_SHADER : i == 1 ? GL_FRAGMENT_SHADER : -1;
            String source = shaderSources.get(shaderIdIndex);
            //this.createShader(type, source, program, shaderIdIndex);
            int shader = glCreateShader(type);
            glShaderSource(shader, source);
            glCompileShader(shader);

            int iscompiled = 0;
            iscompiled = glGetShaderi(shader, GL_COMPILE_STATUS);
            if (iscompiled == 0) {
                int maxLenght = 0;
                String infoLog = "";

                maxLenght = glGetShaderi(shader, GL_INFO_LOG_LENGTH);
                infoLog = glGetShaderInfoLog(shader, maxLenght);

                glDeleteShader(shader);
                String st = type == 0 ? "Vertex Shader" : "Fragment Shader";
                System.out.println("Not compile " + st + ": " + infoLog);
                System.exit(-1);
            }
            glAttachShader(program, shader);
            shaderIdIndex++;
        }
        glLinkProgram(program);
        int isLinked = 0;
        isLinked = glGetProgrami(program, GL_LINK_STATUS);
        if (isLinked == 0) {
            int maxLenght = 0;
            String infoLog = "";

            maxLenght = glGetProgrami(program, GL_INFO_LOG_LENGTH);
            infoLog = glGetProgramInfoLog(program, maxLenght);

            for (int shaderId : shadersId) {
                glDetachShader(program, shaderId);
            }

            for (int shaderId : shadersId) {
                glDeleteShader(shaderId);
            }

            System.out.println("Not compile shader program! " + ": " + infoLog);
            System.exit(-1);
        }

        for (int shaderId : shadersId) {
            glDetachShader(program, shaderId);
        }
        this.shaderId = program;


    }

    public void createShader(int type, String source, int program, int shaderIdIndex) {
        int shader = glCreateShader(type);
        glShaderSource(shader, source);
        glCompileShader(shader);

        int iscompiled = 0;
        iscompiled = glGetShaderi(shader, GL_COMPILE_STATUS);
        if (iscompiled == 0) {
            int maxLenght = 0;
            String infoLog = "";

            maxLenght = glGetShaderi(shader, GL_INFO_LOG_LENGTH);
            infoLog = glGetShaderInfoLog(shader, maxLenght);

            glDeleteShader(shader);
            String st = type == 0 ? "Vertex Shader" : "Fragment Shader";
            System.out.println("Not compile " + st + ": " + infoLog);
            System.exit(-1);
        }
        glAttachShader(program, shader);
        shaderIdIndex++;
    }

    public String readFile(String file) {
        boolean appendSlashes = false;
        boolean returnInOneLine = false;
        StringBuilder shaderSource = new StringBuilder();
        try {
            InputStream in = Class.class.getResourceAsStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line);
                if (appendSlashes) shaderSource.append("//");
                if (!returnInOneLine) shaderSource.append("\n");
            }
            reader.close();

            return shaderSource.toString();
        } catch (IOException e) {
            System.out.println("This file " + file + "cound be read!");
            e.printStackTrace();
            Runtime.getRuntime().exit(-1);
        }
        return "[Reading Error]: This file " + file + "cound be read!";
    }

    public void bind() {
        glUseProgram(this.shaderId);
    }

    public void unbind() {
        glUseProgram(0);
    }
}
