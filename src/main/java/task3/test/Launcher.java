package task3.test;
import core.WindowManager;
import input.keyboard;
import input.mouse;
import org.lwjgl.glfw.GLFW;
import renderer.Loader;
import renderer.RawModel;
import renderer.Renderer;
import renderer.staticShader;
import static org.lwjgl.glfw.GLFW.*;

public class Launcher {

    private WindowManager manager;
    Loader loader = new Loader();
    Renderer renderer = new Renderer();
    //staticShader shader = new staticShader();

    public void run() {
        init();
    }

    private void init() {
        this.manager = new WindowManager(720, 480, "negr");
        manager.init();
        update();
    }

    private void update() {

        float[] v_positions =
                {
                        0.5f, 0.5f, 0f, -0.5f, 0.5f, 0f,//1 и 2 вершина
                        -0.5f, -0.5f, 0f, 0.5f, -0.5f, 0f//3 и 4 вершина
                };
        int[] indexies = {0, 1, 2, 0, 2, 3};

        RawModel model = loader.loadToVao(v_positions,indexies);

        while (!GLFW.glfwWindowShouldClose(WindowManager.getWindow().getThisWindow())) {
            if (keyboard.keyPressed(GLFW.GLFW_KEY_A)) {
                System.out.println("Клавиша А нажата");
            }
            if (mouse.buttonPressed(GLFW_MOUSE_BUTTON_1)) {
                System.out.println("ЛКМ нажата");
            }
            //System.out.println(mouse.getMouseX() + " " + mouse.getMouseY());
            keyboard.handleKeyboardInput();//обработчик нажатий клавиатуры
            mouse.handleMouseInput();//обработчик нажатий мыши


            //пререндер
            renderer.prepare();
            //shader.start();
            renderer.render(model);
            //shader.stop();
            manager.loop();
            //rendering
        }
        //shader.cleanUp();
        loader.cleanUp();
        glfwDestroyWindow(WindowManager.getWindow().getThisWindow());
    }

}
