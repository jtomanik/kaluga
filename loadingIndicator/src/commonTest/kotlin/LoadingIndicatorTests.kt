package com.splendo.kaluga.loadingIndicator

import kotlin.test.Test
import kotlin.test.assertEquals

/*

Copyright 2019 Splendo Consulting B.V. The Netherlands

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

class LoadingIndicatorTests {

    class MockLoadingIndicator: LoadingIndicator {
        lateinit var onPresentCalled: () -> Unit
        lateinit var onDismissCalled: () -> Unit

        override val isVisible: Boolean = false
        override fun present(animated: Boolean, completion: () -> Unit): LoadingIndicator {
            onPresentCalled()
            return this
        }

        override fun dismiss(animated: Boolean, completion: () -> Unit) {
            onDismissCalled()
        }
    }

    class MockBuilder: LoadingIndicator.Builder {
        override var style = LoadingIndicator.Style.SYSTEM
        override fun create(): LoadingIndicator = MockLoadingIndicator()
    }

    @Test
    fun testBuilder() {
        val builder = MockBuilder()
        assertEquals(builder.style, LoadingIndicator.Style.SYSTEM)
        builder.build {
            setStyle(LoadingIndicator.Style.CUSTOM)
        }
        assertEquals(builder.style, LoadingIndicator.Style.CUSTOM)
    }
}