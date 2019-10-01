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

import com.splendo.kaluga.location.Location
import com.splendo.kaluga.location.LocationFlowable
import com.splendo.kaluga.log.debug
import kotlinx.coroutines.*
import platform.CoreLocation.CLLocationManager
import platform.UIKit.UILabel

class KotlinNativeFramework {
    private val loc = LocationFlowable()

    fun hello() = com.splendo.kaluga.example.shared.helloCommon()

    fun location(label:UILabel, locationManager: CLLocationManager) {
        loc.addCLLocationManager()

       runBlocking {
           MainScope()
                   .launch {
                       debug("main..")
                       loc.flow().collect {
                           debug("collecting...")
                           label.text = "received location: $it"
                           debug("location: $it")
                       }
                       debug("bye main")
                   }

        }
        debug("proceed executing after launching coroutine")

    }
}