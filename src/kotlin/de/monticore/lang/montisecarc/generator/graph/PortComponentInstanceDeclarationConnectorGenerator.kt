package de.monticore.lang.montisecarc.generator.graph

import com.intellij.psi.PsiElement
import com.intellij.util.containers.isNullOrEmpty
import de.monticore.lang.montisecarc.generator.FreeMarker
import de.monticore.lang.montisecarc.generator.MSAGenerator
import de.monticore.lang.montisecarc.psi.MSAComponentDeclaration

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
class PortComponentInstanceDeclarationConnectorGenerator : MSAGenerator() {

    private fun getModel(direction: String, componentIdentifier: String, portIdentifier: String): String {

        val connector_model = mutableMapOf<String, Any>()
        if (direction == "OUT") {

            connector_model.put("start_port", componentIdentifier)
            connector_model.put("target_port", portIdentifier)
            connector_model.put("relationship_type", ":OUTGOING")
        } else {

            connector_model.put("start_port", portIdentifier)
            connector_model.put("target_port", componentIdentifier)
            connector_model.put("relationship_type", ":INGOING")
        }

        return FreeMarker(this.javaClass).generateModelOutput("ToGraph/ConnectorMacro.ftl", connector_model)
    }

    override fun generate(psiElement: PsiElement): Any? {

        if (psiElement is MSAComponentDeclaration) {

            val msaComponentDeclaration = psiElement

            /**
             * Check if component is generic these cannot be generated
             */
            val typeVariableDeclarationList = msaComponentDeclaration.componentSignature?.componentNameWithType?.typeParameters?.typeVariableDeclarationList
            if(!typeVariableDeclarationList.isNullOrEmpty()) {
                return null
            }

            if (msaComponentDeclaration.instanceName.isNotEmpty()) {

                val componentIdentifier = ComponentInstanceGenerator.createComponentIdentifier(msaComponentDeclaration)
                val portConnectorModels = msaComponentDeclaration.componentBody?.portDeclarationList?.flatMap {

                    it.portElementList.flatMap {

                        val portIdentifier = PortElementGenerator.createPortIdentifiers(it)
                        val direction = it.direction
                        portIdentifier.map {
                            getModel(direction, componentIdentifier, it)
                        }
                    }
                }
                return portConnectorModels ?: emptyList<String>()
            }
        }
        return null
    }

}