package de.monticore.lang.montisecarc.formatting

import com.intellij.formatting.Alignment
import com.intellij.lang.ASTNode
import com.intellij.psi.codeStyle.CommonCodeStyleSettings
import de.monticore.lang.montisecarc.psi.MSACompositeElementTypes

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

class MSAAlignmentProcessor {

    companion object {
        fun createChildAlignment(myNode: ASTNode, cmSettings: CommonCodeStyleSettings): Alignment? {

            val elementType = myNode.elementType
            val myBaseAlignment = Alignment.createAlignment()

            if(elementType === MSACompositeElementTypes.PORT_ELEMENT) {

                if(cmSettings.ALIGN_MULTILINE_PARAMETERS) {

                    return myBaseAlignment
                }
            }

            return null
        }
    }
}
