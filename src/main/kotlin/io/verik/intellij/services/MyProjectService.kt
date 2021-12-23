package io.verik.intellij.services

import com.intellij.openapi.project.Project
import io.verik.intellij.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
