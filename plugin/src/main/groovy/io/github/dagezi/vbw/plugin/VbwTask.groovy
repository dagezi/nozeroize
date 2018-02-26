package io.github.dagezi.vbw.plugin

import com.android.build.gradle.api.ApplicationVariant
import com.android.builder.model.SourceProvider
import io.github.dagezi.vbw.BeWinding
import org.gradle.api.DefaultTask
import org.gradle.api.logging.Logging
import org.gradle.api.tasks.TaskAction

import java.util.function.Function
import java.util.stream.Stream

class VbwTask extends DefaultTask {
    ApplicationVariant variant

    File outputDir

    @TaskAction
    void run() {
        BeWinding beWinding = new BeWinding(Logging.getLogger("VbwPlugin"))
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
