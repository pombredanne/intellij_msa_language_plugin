package de.monticore.lang.montisecarc.generator.graph

import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import de.monticore.lang.montisecarc.generator.FreeMarker
import de.monticore.lang.montisecarc.generator.MSAGenerator
import de.monticore.lang.montisecarc.psi.MSAComponentDeclaration
import de.monticore.lang.montisecarc.psi.MSAComponentInstanceDeclaration

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
class ComponentInstanceDeclarationConnectorGenerator : MSAGenerator() {

    private fun getModel(relationship: String, startIdentifier: String, targetIdentifier: String): String {

        val connector_model = mutableMapOf<String, Any>()
        connector_model.put("start_port", startIdentifier)
        connector_model.put("target_port", targetIdentifier)
        connector_model.put("relationship_type", ":$relationship")

        return FreeMarker(this.javaClass).generateModelOutput("ToGraph/ConnectorMacro.ftl", connector_model)
    }

    override fun generate(psiElement: PsiElement): Any? {

        if(psiElement is MSAComponentInstanceDeclaration) {

            val msaComponentName = psiElement.componentNameWithTypeProjectionList.last().componentName.references[0].resolve()

            val msaComponentDeclaration = PsiTreeUtil.getParentOfType(msaComponentName, MSAComponentDeclaration::class.java)

            if (msaComponentDeclaration != null && msaComponentDeclaration is MSAComponentDeclaration) {

                val componentIdentifier = ComponentDeclarationGenerator.createComponentIdentifier(msaComponentDeclaration)

                return psiElement.componentInstanceNameList.flatMap {
                    val componentInstanceIdentifier = ComponentInstanceInstanceGenerator.createComponentInstanceIdentifier(psiElement, it.name)

                    listOf(
                            getModel("INSTANCE_OF", componentInstanceIdentifier, componentIdentifier),
                            getModel("DEFINES", componentIdentifier, componentInstanceIdentifier)
                    )
                }
            }
        }
        return null
    }
}
