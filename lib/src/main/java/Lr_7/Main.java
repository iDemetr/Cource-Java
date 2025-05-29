package Lr_7;

/**
 * 
 */
public class Main {

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
	var safetyString = new SafetyString(500);
	var viewer = new MultiThreadWorkView();
	var controller = new MultyThreadController(safetyString, viewer);

	controller.run();
    }

}
