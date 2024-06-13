plugins {
    id("java")
    id("application")
}

group = "com.distribuida"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

   //implementation("org.hibernate:hibernate-core:6.5.2.Final")


    implementation("org.apache.openwebbeans:openwebbeans-impl:4.0.2")
    implementation("org.eclipse.persistence:eclipselink:4.0.3")
    // https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc
    implementation("org.xerial:sqlite-jdbc:3.46.0.0")

    // https://mvnrepository.com/artifact/io.helidon.webserver/helidon-webserver-context
    implementation("io.helidon.webserver:helidon-webserver-context:4.0.9")
    implementation("io.helidon.webserver:helidon-webserver:4.0.9")

    implementation("com.google.code.gson:gson:2.11.0")





}

tasks.jar {
    manifest {
        attributes(
            mapOf("Main-Class" to "com.distribuida.Main",
                "Class-Path" to configurations.runtimeClasspath
                    .get()
                    .joinToString(separator = " ") { file ->
                        "${file.name}"
                    })
        )
    }
}
tasks.test {
    useJUnitPlatform()
}