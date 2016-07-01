package mtrade;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomNameGenerator {
	
	String[] adjectives = {
			"able",
			"abnormal",
			"absent",
			"absolute",
			"abstract",
			"abundant",
			"academic",
			"acceptable"
	};
	
	String[] noums = {
			"call",
			"calorie",
			"camera",
			"camp",
			"campaign",
			"cancer",
			"candidate",
			"candle",
			"cane",
	};
	
	public String get() {
		Random random = new Random();
		int aPosition = random.nextInt(adjectives.length);
		int nPosition = random.nextInt(noums.length);
		return adjectives[aPosition] + noums[nPosition];
	}

}
