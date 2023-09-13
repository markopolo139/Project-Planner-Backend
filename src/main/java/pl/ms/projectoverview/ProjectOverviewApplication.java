package pl.ms.projectoverview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//TODO:
// scheduling to sent notification (use spring scheduling with function searching for projects with deadlines and notification enabled)
// In services (for project (plan)) remember to set up user after conversion from app to persistence entity
@SpringBootApplication
public class ProjectOverviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectOverviewApplication.class, args);
	}

}
