package io.github.dagezi.vbw.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.Task

class VbwPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.afterEvaluate {
            def android = project.extensions.findByType(AppExtension)
            if (!android) {
                throw new Exception(
                        "Not an Android application; you forget `apply plugin: 'com.android.application`?")
            }

            android.applicationVariants.all { ApplicationVariant variant ->
                def generatedResDir = getGeneratedResDir(project, variant)
                android.sourceSets.findByName(variant.name).res.srcDir(generatedResDir)

                def name = "nonZeroizeVector${capitalize(variant.name)}"
                def task = project.task(name, type: VbwTask) as VbwTask
                task.variant = variant
                task.outputDir = generatedResDir

                def generateResources = project.
                        getTasksByName("generate${capitalize(variant.name)}Resources", false)
                generateResources.forEach { Task t ->
                    t.dependsOn(task)
                }
            }
        }
    }

    static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    static File getGeneratedResDir(Project project, ApplicationVariant variant) {
        return new File(project.buildDir,
                "generated/nonzeroize/res/${variant.name}")
    }
}