package com.splendo.kaluga.loadingIndicator

import platform.UIKit.*

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


class IOSLoadingIndicator private constructor(private val view: UIViewController, private val controller: UIViewController) : LoadingIndicator {

    private class DefaultView: UIViewController(null, null) {
        override fun viewDidLoad() {
            super.viewDidLoad()
            view.backgroundColor = UIColor.blackColor
        }
    }

    class Builder(private val controller: UIViewController) : LoadingIndicator.Builder {
        override fun create() = IOSLoadingIndicator(DefaultView(), controller)
    }

    init {
        view.apply {
            modalPresentationStyle = UIModalPresentationOverFullScreen
            modalTransitionStyle = UIModalTransitionStyleCrossDissolve
        }
    }

    override val isVisible get() = view.presentingViewController != null

    override fun present(animated: Boolean, completion: () -> Unit): LoadingIndicator = apply {
        controller.presentViewController(view, animated, completion)
    }

    override fun dismiss(animated: Boolean, completion: () -> Unit) {
        view.presentingViewController?.dismissViewControllerAnimated(animated, completion)
    }
}
