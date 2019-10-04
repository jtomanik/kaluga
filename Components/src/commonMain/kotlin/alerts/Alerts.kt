package com.splendo.kaluga.alerts

import kotlinx.coroutines.CompletionHandler

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

data class Alert(
    val identifier: Identifier,
    val title: String?,
    val message: String?,
    val actions: List<Action>
) {

    interface Identifier

    data class Action(
        val title: String,
        val style: Style = Style.DEFAULT,
        val handler: () -> Unit
    ) {
        enum class Style {
            DEFAULT,
            DESTRUCTIVE
        }
    }
}

interface AlertActions {
    fun show(alert: Alert, animated: Boolean = true, completion: (() -> Unit)?)
    fun dismiss(identifier: Alert.Identifier, animated: Boolean = true)
}

abstract class AlertPresenter: AlertActions
