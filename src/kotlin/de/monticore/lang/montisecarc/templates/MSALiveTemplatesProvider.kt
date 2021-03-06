package de.monticore.lang.montisecarc.templates

import com.intellij.codeInsight.template.impl.DefaultLiveTemplatesProvider

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
class MSALiveTemplatesProvider : DefaultLiveTemplatesProvider {
    override fun getDefaultLiveTemplateFiles(): Array<out String> = arrayOf(
            "/de/monticore/lang/montisecarc/liveTemplates/components",
            "/de/monticore/lang/montisecarc/liveTemplates/ports",
            "/de/monticore/lang/montisecarc/liveTemplates/access",
            "/de/monticore/lang/montisecarc/liveTemplates/accesscontrol",
            "/de/monticore/lang/montisecarc/liveTemplates/identity",
            "/de/monticore/lang/montisecarc/liveTemplates/trustlevel",
            "/de/monticore/lang/montisecarc/liveTemplates/trustlevelrelation",
            "/de/monticore/lang/montisecarc/liveTemplates/clearanceFor"
    )

    override fun getHiddenLiveTemplateFiles(): Array<out String>? = null
}