/**
 * 
 */
package Lr_5;

/**
 * 
 */
public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
	/*
	 * JFrame frame = new JFrame("My Swing Application");
	 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); frame.setSize(400,
	 * 300);
	 * 
	 * JLabel label = new JLabel("Hello, Swing!", SwingConstants.CENTER);
	 * frame.getContentPane().add(label);
	 * 
	 * frame.setVisible(true);
	 */

	var filePath = "LR_4_Summary.log"; // Путь к файлу
	var statsList = Parser.parseFile(filePath);

	var app = new GraphApp(statsList);
	app.setVisible(true);
    }

}
