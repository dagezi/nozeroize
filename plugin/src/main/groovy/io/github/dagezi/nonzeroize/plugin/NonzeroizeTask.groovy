package io.github.dagezi.nonzeroize.plugin

import com.android.build.gradle.api.ApplicationVariant
import com.android.builder.model.SourceProvider
import io.github.dagezi.nonzeroize.Nonzeroize
import org.gradle.api.DefaultTask
import org.gradle.api.logging.Logging
import org.gradle.api.tasks.TaskAction

import java.util.function.Function
import java.util.stream.Stream

class NonzeroizeTask extends DefaultTask {
    ApplicationVariant variant

    File outputDir

    @TaskAction
    void run() {
        Nonzeroize beWinding = new Nonzeroize(Logging.getLogger(NonzeroizeTask.class.getName()))
        outputDir.mkdirs()
        variant.sourceSets.stream()
                .flatMap(new Function<SourceProvider, Stream>() {
            @Override
            Stream apply(SourceProvider sourceProvider) {
                return sourceProvider.resDirectories.stream()
            }
        }).forEach { File resDir ->
            if (resDir == outputDir) return
            project.fileTree(
                    dir: resDir,
                    include: "drawable*/*.xml"
            ).forEach { File inputFile ->
                File outputFile = new File(outputDir, inputFile.getName())
                beWinding.handle(inputFile, outputFile)
            }
        }
    }

}
