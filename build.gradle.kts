plugins { id("java") }

repositories { mavenCentral() }

dependencies {
    testImplementation("io.cucumber:cucumber-java:7.18.0")
    testImplementation("io.cucumber:cucumber-testng:7.18.0")
    testImplementation("org.testng:testng:7.10.2")

    testImplementation("io.rest-assured:rest-assured:5.4.0")
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation("com.aventstack:extentreports:5.1.1")

    testRuntimeOnly("org.slf4j:slf4j-simple:2.0.13")
}

tasks.register<JavaExec>("cucumber") {
    group = "verification"
    description = "Roda Cucumber via TestNG suite"

    classpath = sourceSets["test"].runtimeClasspath

    mainClass.set("org.testng.TestNG")
    args = listOf("src/test/resources/testng.xml")
}

tasks.named("check") {
    dependsOn(tasks.named("cucumber"))
}