package de.monticore.lang.montisecarc.parser

import com.intellij.testFramework.ParsingTestCase
import de.monticore.lang.montisecarc.MSAParserDefinition

/**
 * Copyright 2017 thomasbuning
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


class ParserTests : ParsingTestCase("", "secarc", MSAParserDefinition()) {

    fun testParsingTestData() {
        doTest(true)
    }

    fun testCashDeskSystem() {
        doTest(true)
    }

    override fun getTestDataPath(): String {
        return "testData/parser"
    }

    override fun skipSpaces(): Boolean {
        return false
    }

    override fun includeRanges(): Boolean {
        return true
    }
}