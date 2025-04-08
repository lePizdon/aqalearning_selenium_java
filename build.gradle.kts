plugins {
    id("java")
}

group = "org.learning"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("ch.qos.logback:logback-classic:1.5.16")
    implementation("io.github.bonigarcia:webdrivermanager:5.9.2")
    implementation("com.codeborne:selenide:7.7.1")
    implementation("org.seleniumhq.selenium:selenium-java:4.28.1")
    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")
    testCompileOnly("org.projectlombok:lombok:1.18.36")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.36")
    testImplementation("io.github.bonigarcia:webdrivermanager:5.9.2")
    testImplementation("org.slf4j:slf4j-api:2.0.16")
    testImplementation("ch.qos.logback:logback-classic:1.5.16")
    testImplementation("io.qameta.allure:allure-junit5:2.29.1")
    testImplementation("com.codeborne:selenide:7.7.1")
    testImplementation("org.seleniumhq.selenium:selenium-java:4.28.1")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:4.0.0-M1")
}

tasks.test {
    useJUnitPlatform()
}