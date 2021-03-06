package de.monticore.lang.montisecarc.generator.graph

import com.intellij.psi.PsiElement
import com.intellij.util.containers.isNullOrEmpty
import de.monticore.lang.montisecarc.generator.FreeMarker
import de.monticore.lang.montisecarc.generator.MSAGenerator
import de.monticore.lang.montisecarc.psi.MSAComponentDeclaration
import de.monticore.lang.montisecarc.psi.MSATokenElementTypes

/**
 * Copyright 2016 thomasbuning
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
class ComponentInstanceGenerator : MSAGenerator() {

    companion object {

        fun createComponentIdentifier(componentDeclaration: MSAComponentDeclaration): String {

            if (componentDeclaration.instanceName.isNotEmpty()) {
                return componentDeclaration.qualifiedName.replace(".", "_") + "_" + componentDeclaration.instanceName
            }

            return componentDeclaration.qualifiedName.replace(".", "_")
        }
    }

    override fun generate(psiElement: PsiElement): String? {

        if (psiElement is MSAComponentDeclaration) {
            /**
             * Check if component is generic these cannot be generated
             */
            val typeVariableDeclarationList = psiElement.componentSignature?.componentNameWithType?.typeParameters?.typeVariableDeclarationList
            if(!typeVariableDeclarationList.isNullOrEmpty()) {
                return null
            }
            if (psiElement.instanceName.isNotEmpty()) {
                val model = mutableMapOf<String, Any>()

                //<@node id="${id}" instance_name="${instance_name}"><#list extra_arguments!{} as attrName, attrVal>, ${attrName}:"${attrVal}"</#list></@node>
                val componentIdentifier = createComponentIdentifier(psiElement)
                model.put("id", componentIdentifier)
                model.put("instance_name", psiElement.instanceName)

                val extras = mutableMapOf<String, String>()
                extras.put("type_name", psiElement.qualifiedName)
                extras.put("element_offset", psiElement.textOffset.toString())
                extras.put("file_path", psiElement.containingFile.virtualFile.canonicalPath.orEmpty())
                val accessRoles = psiElement.componentBody?.accessStatementList?.map { it.roleNameList.map { "'${it.text}'" }.joinToString() }?.joinToString()
                extras.put("access_roles", accessRoles.orEmpty())


                val configurationStatementList = psiElement.componentBody?.configurationStatementList
                if (!configurationStatementList.isNullOrEmpty()) {

                    val config = configurationStatementList!!.first().id?.text
                    if (!config.isNullOrEmpty()) {
                        extras.put("configuration", config!!)
                    }
                }
                val cpeStatementList = psiElement.componentBody?.cpeStatementList
                if (!cpeStatementList.isNullOrEmpty()) {

                    val cpe = cpeStatementList!!.first().string?.text
                    if (!cpe.isNullOrEmpty()) {
                        extras.put("cpe", cpe!!.replace("\"", ""))
                    }
                }

                val accessControlStatementList = psiElement.componentBody?.accessControlStatementList
                if (!accessControlStatementList.isNullOrEmpty()) {

                    val findChildByType = accessControlStatementList!!.first().node.findChildByType(MSATokenElementTypes.ON)
                    if (findChildByType != null) {

                        extras.put("access_control", "on")
                    } else {

                        extras.put("access_control", "off")
                    }
                }

                model.put("extra_arguments", extras)

                return FreeMarker(this.javaClass).generateModelOutput("ToGraph/InstanceMacro.ftl", model)
            }
        }
        return null
    }
}
