plugins {
    java
    id("org.springframework.boot") version "3.3.1"
    id("io.spring.dependency-management") version "1.1.5"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
}

group = "com.maxim"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral();
    maven (
        url ="https://repo.cuba-platform.com/content/groups/work/")
}

extra["snippetsDir"] = file("build/generated-snippets")

dependencies {
    // YARG dependencies
    implementation("com.haulmont.yarg:yarg:2.2.14")
    implementation("com.jayway.jsonpath:json-path:2.4.0")
    implementation("javax.xml.bind:jaxb-api:2.3.0")
    implementation("javax.activation:activation:1.1")
    implementation("org.glassfish.jaxb:jaxb-runtime:2.3.2")

    //PortgreSQL
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql:42.7.3")


    //MongoDB
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

    //Starters
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    //Documentation
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.test {
    outputs.dir(project.extra["snippetsDir"]!!)
}

tasks.asciidoctor {
    inputs.dir(project.extra["snippetsDir"]!!)
    dependsOn(tasks.test)
}

