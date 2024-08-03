package com.asc.politicalscorecard;

import com.asc.politicalscorecard.databases.DatabaseService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;

@Component
class ApplicationStartup {
	private final DatabaseService databaseService;

    @Autowired
    public ApplicationStartup(DatabaseService databaseService) {
		System.out.println("Creating ApplicationStartup");
        this.databaseService = databaseService;
    }

    @PostConstruct
    public void onStartup() {
		System.out.println("Hit onStartup");
        databaseService.performDatabaseOperation();
    }
}

@SpringBootApplication
@ComponentScan // Note: If you do not use the @Component or @Configuration decorator, the app will not load whatever you made.
public class PoliticalScorecardApplication {

	public static void main(String[] args) {
		SpringApplication.run(PoliticalScorecardApplication.class, args);
		System.out.println("Hit the main function.");
        //PlanetService planetService = new PlanetService(planetDAO);

        // Create a planet
        //PlanetDTO planet = new PlanetDTO("1", "Earth");
        //planetService.createPlanet(planet);

        // Read a planet
        //PlanetDTO retrievedPlanet = planetService.getPlanet("1");
        //System.out.println("Retrieved Planet: " + retrievedPlanet);

        // Update a planet
        //retrievedPlanet.setPlanetName("New Earth");
        //planetService.updatePlanet(retrievedPlanet);

        // Delete a planet
        //planetService.deletePlanet("1");

		
	}

	@RestController
	@RequestMapping("")
	class baseController
	{
		// This endpoint serves the purpose of acting as a test for if the application in general is accessible.
		@GetMapping("")
		public String politicalScorecardTestEndpoint()
		{
			return "Successfully hit the Political Scorecard Backend!";
		}
	}
}

