package de.monticore.lang.montisecarc.generator.graph

import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
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
class ComponentInstancePortElementGenerator : MSAGenerator() {
    override fun generate(psiElement: PsiElement): Any? {

        if (psiElement is MSAComponentInstanceDeclaration) {
            val _componentName = psiElement.componentNameWithTypeProjectionList.last().componentName
            if (_componentName.references.isNotEmpty()) {

                val componentName = _componentName.references[0].resolve()
                val componentDeclaration = PsiTreeUtil.getParentOfType(componentName, MSAComponentDeclaration::class.java)
                if (componentDeclaration != null && componentDeclaration is MSAComponentDeclaration) {

                    val portElementNodes = mutableListOf<String>()
                    val connectors = mutableListOf<String>()
                    for (msaPortDeclaration in componentDeclaration.componentBody?.portDeclarationList.orEmpty()) {

                        for (msaPortElement in msaPortDeclaration.portElementList) {
                            val portElementNode = PortElementGenerator.createPortElementNode(msaPortElement)
                            portElementNodes.add(portElementNode)
                            val portIdentifier = PortElementGenerator.createPortIdentifiers(msaPortElement)

                            psiElement.componentInstanceNameList
                                    .map { ComponentInstanceInstanceGenerator.createComponentInstanceIdentifier(psiElement, it.name) }
                                    .forEach {
                                        val componentInstanceIdentifier = it
                                        connectors.addAll(portIdentifier.map { PortElementConnectorGenerator.getModel(msaPortElement.direction, componentInstanceIdentifier, it) })
                                    }
                        }
                    }
                    return Pair(portElementNodes, connectors)
                }
            }
        }
        return null
    }

}