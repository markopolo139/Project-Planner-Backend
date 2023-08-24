plugins {
	java
	id("org.springframework.boot") version "3.1.2"
	id("io.spring.dependency-management") version "1.1.2"
}

group = "pl.ms"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-mail")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
	implementation("org.passay:passay:1.6.1")
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")

	implementation("com.fasterxml.jackson.core:jackson-databind:2.13.4.1")
	implementation("com.fasterxml.jackson.core:jackson-core:2.13.3")

	developmentOnly("org.springframework.boot:spring-boot-devtools")

	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
	runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")

	implementation("org.springframework.boot:spring-boot-starter-log4j2")
	configurations {
		all{
			exclude(module = "spring-boot-starter-logging")
		}
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
