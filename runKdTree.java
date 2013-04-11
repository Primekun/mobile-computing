
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;


public class runKdTree {
	
	static class TimeReport {
		private long start;
		private long readEnd = 0;
		private long insertEnd = 0;
		private long compareEnd = 0;
		
		//public TimeReport() {}
		
		public void startTimer() {
			start = System.currentTimeMillis();
		}
		
		public void endTimerForRead() {
			readEnd = System.currentTimeMillis();
		}
		
		public void endTimerForInsert() {
			insertEnd = System.currentTimeMillis();
		}
		
		public void endTimerForCompare() {
			compareEnd = System.currentTimeMillis();
		}
		
		public void printTimerReport() {
			System.out.println("-----------------------");
			System.out.println("Time for read searial files: " + (readEnd - start));
			System.out.println("Time for insert kdtree: " + (insertEnd - readEnd));
			System.out.println("Time for compare with neighbors: " + (compareEnd - insertEnd));
			System.out.println("Total runtime is: " + (compareEnd - start));
		}
	}
	
	private static final double defaultRadius = 0.1;
	private static final String defaultSerialPath = "results";
	
	private static final String radiusOption = "r";
	private static final String helpOption = "h";
	private static final String inputOption = "i";
	
	private static Options createOptions() {
		Options op = new Options();
		op.addOption(helpOption, "help", false, "print this message and exit");
		op.addOption(inputOption, "input", true, "input path of serial file or folder contains them");
		op.addOption(radiusOption, "radius", true, "specify radius for range search");
		return op;
	}
	
	private static void showHelp(Options options) {
		System.out.println("Current working directory : " + System.getProperty("user.dir"));
	    HelpFormatter h = new HelpFormatter();
	    h.printHelp("help", options);
	    System.exit(-1);
	}
	
	private static double getRadius(CommandLine cmd) {
		String value = cmd.getOptionValue(radiusOption);
		if (value != null)
			return Double.valueOf(value);
		else
			return defaultRadius;
	}
	
	private static String getSerialPath(CommandLine cmd) {
		String path = cmd.getOptionValue(inputOption);
		if (path != null)
			return path;
		else
			return defaultSerialPath;
	}
	
	public static void main(String[] args) {
		
		BitSetBank bsb = new BitSetBank();
		kdtreeCompare kd = new kdtreeCompare();
		TimeReport timer = new TimeReport();
		Options options = createOptions();
		
		try {
			/* parse arguments */
			CommandLineParser parser = new PosixParser();
			CommandLine cmd = parser.parse(options, args);
			
			double radius = getRadius(cmd);
			String path = getSerialPath(cmd);
			System.out.println(path);
			
			timer.startTimer();
		
			/* read serial files and do nearby comparison */
			bsb.readAllFromSerial(path);
			timer.endTimerForRead();
			
			bsb.compareBitSetBank_KDtree(null, null, kd);
			timer.endTimerForInsert();
			
			kd.runKdtreeCompare(radius, bsb);
			timer.endTimerForCompare();
			
			timer.printTimerReport();
			
		} catch (ParseException e1) {
			e1.printStackTrace();
			showHelp(options);
		}
	}
}
