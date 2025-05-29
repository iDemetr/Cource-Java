package Lr_7;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class MultyThreadController {

    SafetyString safetyString;
    // SharedResource tmp = new SharedResource();
    MultiThreadWorkView view;

    int countThreads = 10;

    List<Thread> pullThreadsReades;
    List<Thread> pullThreadsWriteses;

    /**
     * 
     * @param str
     * @param viewer
     */
    public MultyThreadController(SafetyString str, MultiThreadWorkView viewer) {
	safetyString = str;
	view = viewer;

	pullThreadsReades = new ArrayList<>();
	for (int i = 0; i < countThreads; i++) {
	    final int threadIndex = i; // Создаем новую переменную, которая будет final
	    var thread = new Thread(() -> {
		String v = "";
		// try {
		v = safetyString.read();
		// v = tmp.read();
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		view.showReaded("reader thread [" + threadIndex + "]", v);
	    });
	    // thread.setPriority(Thread.MAX_PRIORITY);
	    pullThreadsReades.add(thread);
	}

	pullThreadsWriteses = new ArrayList<>();
	for (int i = 0; i < countThreads; i++) {
	    final int threadIndex = i; // Создаем новую переменную, которая будет final
	    var thread = new Thread(() -> {
		// try {
		// tmp.write("[" + threadIndex + "]\n");
		safetyString.write("[" + threadIndex + "]\n");
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		var v = "writer thread [" + threadIndex + "]\n";
		view.showWrited("writer thread [" + threadIndex + "]", v);
	    });
	    // thread.setPriority(Thread.MAX_PRIORITY);
	    pullThreadsWriteses.add(thread);
	}
    }

    /**
     * 
     */
    public void run() {
	view.showHelloMessage();

	for (int i = 0; i < countThreads; i++) {
	    pullThreadsWriteses.get(i).start();
	    pullThreadsReades.get(i).start();
	}

	view.showByByMessage();
    }
}
