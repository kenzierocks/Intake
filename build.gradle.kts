plugins {
    id("com.jfrog.artifactory") version "4.9.3" apply false
}

if (!project.hasProperty("artifactory_contextUrl"))
    ext["artifactory_contextUrl"] = "http://localhost"
if (!project.hasProperty("artifactory_user"))
    ext["artifactory_user"] = "guest"
if (!project.hasProperty("artifactory_password"))
    ext["artifactory_password"] = ""

subprojects {
    apply(plugin = "java")
    apply(plugin = "maven")
    apply(plugin = "checkstyle")
    apply(plugin = "com.jfrog.artifactory")
    group = "com.sk89q.intake"
    version = "4.2-SNAPSHOT"

    configure<CheckstyleExtension> {
        configFile = rootProject.projectDir.resolve("config/checkstyle/checkstyle.xml")
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_6
        targetCompatibility = JavaVersion.VERSION_1_6
    }

    repositories {
        maven { url = uri("http://maven.sk89q.com/repo/") }
        mavenCentral()
    }

    val sourcesJar = tasks.register<Jar>("sourcesJar") {
        dependsOn("classes")
        archiveClassifier.set("sources")
        from(project.the<JavaPluginConvention>().sourceSets.get("main").allSource)
    }

    val javadocJar = tasks.register<Jar>("javadocJar") {
        from(tasks.named("javadoc"))
        archiveClassifier.set("javadoc")
    }

    artifacts {
        add("archives", tasks.named("jar"))
        add("archives", sourcesJar)
        add("archives", javadocJar)
    }

    tasks.named("build").configure {
        dependsOn("checkstyleMain", "checkstyleTest", sourcesJar, javadocJar)
    }

    // artifactory configuration is not Kt-friendly... do it in Groovy.
    apply(from = rootProject.file("gradle/artifactory.gradle"))
}
