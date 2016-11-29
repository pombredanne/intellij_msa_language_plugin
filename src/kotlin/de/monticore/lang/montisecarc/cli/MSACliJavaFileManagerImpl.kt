package de.monticore.lang.montisecarc.cli

import com.intellij.core.CoreJavaFileManager
import com.intellij.psi.PsiManager
import de.monticore.lang.montisecarc.cli.index.JvmDependenciesIndex
import kotlin.properties.Delegates

/**
 * Copyright 2016 Thomas Buning
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
class MSACliJavaFileManagerImpl(myPsiManager: PsiManager) : CoreJavaFileManager(myPsiManager) {
    private var index: JvmDependenciesIndex by Delegates.notNull()

    fun initIndex(packagesCache: JvmDependenciesIndex) {
        this.index = packagesCache
    }
}