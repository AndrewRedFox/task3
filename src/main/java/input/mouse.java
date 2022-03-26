package input;

import core.WindowManager;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

public class mouse {

    private static boolean[] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];

    private static double mouseX;
    private static double mouseY;

    public static double getMouseX() {
        return mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }

    public static boolean buttonDown(int keyId) {//врнет тру если мы нажмем на клавишу
        return GLFW.glfwGetMouseButton(WindowManager.getWindow().getThisWindow(), keyId) == GLFW_PRESS;
    }

    public static boolean buttonPressed(int keyId) {
        return buttonDown(keyId) && !buttons[keyId];
    }

    public static boolean buttonReleased(int keyId) {
        return !buttonDown(keyId) && buttons[keyId];
    }

    public static void handleMouseInput() {
        for (int i = 0; i < GLFW.GLFW_MOUSE_BUTTON_LAST; i++) {
            buttons[i] = buttonDown(i);
        }
    }

    public static void setMouseCallbacks(long window) {
        setCursorPosCallback(window);
    }

    public static void setCursorPosCallback(long window) {
        GLFW.glfwSetCursorPosCallback(window, new GLFWCursorPosCallbackI() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                mouseX = xpos;
                mouseY = ypos;
            }
        });
    }

}
