package de.monticore.lang.montisecarc.psi

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import de.monticore.lang.montisecarc.MSAFileType
import de.monticore.lang.montisecarc.MSALanguage
import de.monticore.lang.montisecarc.psi.util.childOfType

/**
 *  Copyright 2016 thomasbuning

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

object MSAElementFactory {

    fun createPortInstanceName(project: Project, name: String): MSAPortInstanceName {

        val file = createFile(project, name)
        return file.firstChild as MSAPortInstanceName
    }

    // ToDo: Anders erzeugen!
    fun createIdentifier(project: Project, name: String): PsiElement =
            createFromText<MSAPortElement>(project, "mod $name;")!!.javaClassReference!!

    private inline fun <reified T : MSACompositeElement> createFromText(project: Project, code: String): T? =
            PsiFileFactory.getInstance(project)
                    .createFileFromText("DUMMY.rs", MSALanguage.instance, code)
                    ?.childOfType<T>()

    fun createFile(project: Project, text: String): MSAFile {
        val name = "dummy.secarc"
        return PsiFileFactory.getInstance(project).createFileFromText(name, MSAFileType.instance, text) as MSAFile
    }

    fun createImport(project: Project, path: String): MSAImportDeclaration {

        val file = createFile(project, "import $path;\n")
        return file.firstChild as MSAImportDeclaration
    }

    fun createConnectSourceName(project: Project, newName: String): MSAConnectSource {
        val file = createFile(project, newName)
        return file.firstChild as MSAConnectSource
    }

    fun createConnectTargetName(project: Project, newName: String): MSAConnectTarget{
        val file = createFile(project, newName)
        return file.firstChild as MSAConnectTarget
    }
}