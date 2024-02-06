package com.ananda.sales;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TimerTask;

public class BackEndTimer extends TimerTask {

	static Process p = null;
	static String cmdCheckIfAppRunning = "lsof -i :7777";
	static String cmdStartApp = "serve -s build --no-clipboard -l 7777";
	
	
	static String s;

	@Override
	public void run() {
		runBackend();

	}

	public static void runBackend() {

		Runtime run = Runtime.getRuntime();
		// The best possible I found is to construct a command which you want to execute
		// as a string and use that in exec. If the batch file takes command line
		// arguments
		// the command can be constructed a array of strings and pass the array as input
		// to
		// the exec method. The command can also be passed externally as input to the
		// method.

		try {
			p = Runtime.getRuntime().exec(cmdCheckIfAppRunning);
			BufferedReader check = new BufferedReader(new InputStreamReader(p.getInputStream()));
			if ((s = check.readLine()) != null) {
				System.out.println("line: " + s);
				System.out.println("process running: " + s);
			} else {
				p = Runtime.getRuntime().exec(cmdStartApp);
				BufferedReader start = new BufferedReader(new InputStreamReader(p.getInputStream()));
				System.out.println("restarting the process: ");
				if ((s = start.readLine()) != null) {
					System.out.println("restarted: "+s);
				}
			}
			
			p.waitFor();
			System.out.println("exit: " + p.exitValue());
			p.destroy();

		} catch (IOException e) {
			// e.printStackTrace();
			System.out.println("ERROR.RUNNING.CMD");

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("ERROR. UNABLE TO RUN THE COMMAND");
		} finally {
			p.destroy();
		}

	}

}
