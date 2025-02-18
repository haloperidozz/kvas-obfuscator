import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

abstract class GenerateBuildConfigTask : DefaultTask() {
    @get:Input
    abstract val objectName: Property<String>

    @get:Input
    abstract val packageName: Property<String>

    @get:Input
    abstract val properties: MapProperty<String, String>

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    @TaskAction
    fun generateBuildConfig() {
        val buildConfig = TypeSpec.objectBuilder(objectName.get())

        properties.get().forEach { (key, value) ->
            buildConfig.addProperty(
                PropertySpec.builder(key.uppercase(), String::class)
                    .initializer("%S", value)
                    .addModifiers(KModifier.CONST)
                    .build()
            )
        }

        val file = FileSpec.builder(packageName.get(), objectName.get())
            .addType(buildConfig.build())
            .build()

        file.writeTo(outputDirectory.get().asFile)
    }
}
