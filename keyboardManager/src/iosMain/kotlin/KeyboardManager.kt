package com.splendo.kaluga.keyboardManager

import platform.UIKit.UIApplication
import platform.UIKit.UIView
import platform.objc.sel_registerName

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

actual data class KeyboardView(val view: UIView)

actual class KeyboardManagerBuilder(private val application: UIApplication = UIApplication.sharedApplication) : BaseKeyboardManagerBuilder() {
    override fun create() = KeyboardInterface(application)
}

actual class KeyboardInterface(private val application: UIApplication) : BaseKeyboardManager() {

    override fun show(keyboardView: KeyboardView) {
        if (keyboardView.view.canBecomeFirstResponder) {
            keyboardView.view.becomeFirstResponder()
        }
    }

    override fun dismiss() {
        application.sendAction(sel_registerName("resignFirstResponder"), null, null, null)
    }
}