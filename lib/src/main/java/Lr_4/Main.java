package Lr_4;

/**
 * 
 */
public class Main {

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {

	Logger.log("------------------------------------------------------------------------------", "");
	Logger.log("Start program", "");
	Logger.log("------------------------------------------------------------------------------", "");

	int[] sizes = { 10, 100, 1000, 10000, 100000 };
	for (int size : sizes) {
	    Logger.log("------------------------------------------------------------------------------", "yellow");
	    Logger.log("Testing with size: " + size, "yellow");
	    Logger.log("------------------------------------------------------------------------------", "yellow");

	    Test.testArrayList(size);
	    Test.testHashMap(size);
	}

	Logger.log("------------------------------------------------------------------------------", "");
	Logger.log("Finish program", "");
	Logger.log("Total errors: " + Logger.getErrorCount(), "");
	Logger.log("------------------------------------------------------------------------------", "");
    }

}
