package com.example.demo;

import com.example.demo.model.harvested.HarvestedCropDetailsResponse;
import com.example.demo.model.planted.PlantedCropDetailsResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.*;


@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

	@Autowired
	private TestRestTemplate template;

	@Test
	void testPlantedCropsReport() {
		var result = template.getForEntity("/planted/crops/summer", PlantedCropDetailsResponse.class);
		Assertions.assertTrue(result.getStatusCode().is2xxSuccessful());
		System.out.println(result.getBody());
	}

	@Test
	void testHarvestedCropsReport() {
		var result = template.getForEntity("/harvested/crops/summer", HarvestedCropDetailsResponse.class);
		Assertions.assertTrue(result.getStatusCode().is2xxSuccessful());
		System.out.println(result.getBody());
	}

	@Test
	void testSummaryReportForSummer() {
		var plantedResult = template.getForEntity("/planted/crops", PlantedCropDetailsResponse.class);
		var harvestedResult = template.getForEntity("/harvested/crops/summer", HarvestedCropDetailsResponse.class);

		Map<String, List<Crop>> summaries = new HashMap<>();

		plantedResult.getBody().getFarms()
						.forEach(f -> {
							if ("summer".equalsIgnoreCase(f.getSeason())) {
								summaries.computeIfAbsent(f.getName(), k -> {
									List<Crop> crops = new ArrayList<>();
									f.getCrops().forEach(c -> {
										crops.add(new Crop(c.getName(), c.getExpectedAmount(), 0));
									});
									return crops;
								});
							}
						});

		// enrich crops in summaries to add the total harvested amount
		harvestedResult.getBody().getFarms()
						.forEach(f -> {
							if ("summer".equalsIgnoreCase(f.getSeason())) {
								// summaries.computeIfPresent()
							}
						});




		summaries.forEach((k,v) -> System.out.println(k + " " + v));
	}

	record Summary(String farm, List<Crop> crops){}
	class Crop {
		private String name;
		int expected;
		int totalAmount;

		public Crop(String name, int expected, int totalAmount) {
			this.name = name;
			this.expected = expected;
			this.totalAmount = totalAmount;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getExpected() {
			return expected;
		}

		public void setExpected(int expected) {
			this.expected = expected;
		}

		public int getTotalAmount() {
			return totalAmount;
		}

		public void setTotalAmount(int totalAmount) {
			this.totalAmount = totalAmount;
		}
	}

}
