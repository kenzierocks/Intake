plugins {
    `java-library`
}

dependencies {
    api("com.google.guava:guava:18.0")
    api("com.google.code.findbugs:jsr305:3.0.0")
    testImplementation("junit:junit:4.11")
    testImplementation("org.hamcrest:hamcrest-library:1.2.1")
}