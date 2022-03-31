package renderer;

public class staticShader extends shaderProgram {

    private static final String VERTEX_FILE= "shaders/vertex.vert";
    private static final String FRAGMENT_FILE= "shaders/fragment.frag";

    public staticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttributes(0,"position");
    }
}
