import aQute.bnd.gradle.BundleTaskExtension
import org.gradle.jvm.tasks.Jar

plugins {
    id("biz.aQute.bnd.builder")
}

tasks.withType<Jar>().configureEach {
    val bundleExt = extensions.findByName(BundleTaskExtension.NAME) as? BundleTaskExtension
        ?: extensions.create(BundleTaskExtension.NAME, BundleTaskExtension::class.java, this)

    val createdByValue = project.findProperty("manifest.createdBy") as? String
        ?: "${System.getProperty("java.version")} (${System.getProperty("java.vendor")})"

    bundleExt.setBnd("""
        Bundle-Name: ${'$'}{task.archiveBaseName}
        Bundle-SymbolicName: ${'$'}{task.archiveBaseName}
        Bundle-Version: ${'$'}{project.version}
        Created-By: $createdByValue

        -noextraheaders: true
    """.trimIndent())
}
