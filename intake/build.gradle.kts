plugins {
    java
    maven
    checkstyle
}

dependencies {
    implementation("com.google.guava:guava:18.0")
    implementation("com.google.code.findbugs:jsr305:3.0.0")
    testImplementation("junit:junit:4.11")
    testImplementation("org.hamcrest:hamcrest-library:1.2.1")
}