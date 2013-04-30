import java.io.BufferedReader;
import java.io.FileReader;

public class Statistics {
	
	static BitSetBank bsb;
	public boolean isFramework(String fName) {

		return false;
	}

	// air:338416
	public boolean isAdobeAir(String line) {
		return line.contains("air.");
	}

	// appinventor: 154486
	public boolean isAppInventor(String line) {
		return line.contains("appinventor.");
	}

	// phonegap: 117692
	public boolean isPhoneGap(String line) {
		return line.contains("phonegap.");
	}

	// wallpaper: 718292
	public boolean isWallpaper(String line) {
		return line.contains("wallpaper");
	}

	public void processSimilarityOutput(String inputFile) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(inputFile));
			String currentLine;

			int count = 0;
			int t = 0;
			boolean found = false;
			while ((currentLine = in.readLine()) != null) {
				if (found && currentLine.startsWith("-t")) {
					t = Integer.parseInt(currentLine.substring(currentLine
							.indexOf(':') + 2));
					count += t;
					found = false;
				}
				if (this.isWallpaper(currentLine)) {
					// System.out.println(currentLine);
					found = true;
					// count++;
				}
			}
			System.out.println(count);
			// Close buffered reader
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// com.custom.lwp.GoodRiver-1,com.custom.lwp.cubmom-1,(1.0 1.0)
	public void processStatistics(String inputFile) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(inputFile));
			String currentLine;

			int count = 0;
			int t = 0;
			while ((currentLine = in.readLine()) != null) {
				if (currentLine.startsWith("-"))
					continue;

				String[] apps = currentLine.split(",");
				AppVector av1 = bsb.bitSetsHashMap.get(apps[0]);
				AppVector av2 = bsb.bitSetsHashMap.get(apps[1]);
				
				
			}
			// Close buffered reader
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		bsb = new BitSetBank();
		bsb.readAllFromSerial("/home/peter/github/mobile-computing/db/bitSetMap_d_15_s_8");
		
		System.out.println(bsb.bitSetsHashMap.size());
		
		Statistics bs = new Statistics();
		//bs.processStatistics("/home/peter/columbia/mob/output.txt");
	}
}
