package com.splendo.kaluga.loadingIndicator

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

/**
 * Interface that defines loading indicator class, which can be shown or dismissed
 */
interface LoadingIndicator {

    /**
     * Style of the Loading Indicator
     */
    enum class Style(val value: Int) {
        /** Light appearance */
        LIGHT(0),
        /** Dark appearance */
        DARK(1),
        /** System appearance */
        SYSTEM(2);

        companion object {
            fun valueOf(value: Int) = values().first { it.value == value }
        }
    }

    /**
     * Interface used to build loading indicator
     */
    interface Builder {

        var style: Style

        /** The style of the loading indicator */
        fun setStyle(style: Style) = apply { this.style = style }

        /** Returns built loading indicator */
        fun build(initialize: Builder.() -> Unit): LoadingIndicator {
            initialize()
            return create()
        }

        /** Returns created loading indicator */
        fun create(): LoadingIndicator
    }

    /**
     * Returns true if indicator is visible
     */
    val isVisible: Boolean

    /**
     * Presents as indicator
     *
     * @param animated Pass `true` to animate the presentation
     * @param completion The block to execute after the presentation finishes
     */
    fun present(animated: Boolean = true, completion: () -> Unit = {}): LoadingIndicator

    /**
     * Dismisses the indicator
     *
     * @param animated Pass `true` to animate the transition
     * @param completion The block to execute after the presentation finishes
     */
    fun dismiss(animated: Boolean = true, completion: () -> Unit = {})
}
