package com.github.frwang96.verikintellij.services

import com.intellij.openapi.project.Project
import com.github.frwang96.verikintellij.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
