/*
 Copyright (c) 2020. Splendo Consulting B.V. The Netherlands

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

package com.splendo.kaluga.bluetooth.scanner

import com.splendo.kaluga.base.typedMap
import com.splendo.kaluga.bluetooth.UUID
import com.splendo.kaluga.bluetooth.device.*
import com.splendo.kaluga.permissions.Permissions
import com.splendo.kaluga.state.StateRepoAccesor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import platform.CoreBluetooth.*
import platform.Foundation.NSError
import platform.Foundation.NSNumber
import platform.darwin.NSObject
import platform.darwin.dispatch_get_main_queue

actual class Scanner internal constructor(autoEnableBluetooth: Boolean,
                                          permissions: Permissions,
                                          stateRepoAccesor: StateRepoAccesor<ScanningState>,
                                          coroutineScope: CoroutineScope)
    : BaseScanner(permissions, stateRepoAccesor, coroutineScope)  {

    class Builder(override val autoEnableBluetooth: Boolean, private val permissions: Permissions) : BaseScanner.Builder {

        override fun create(stateRepoAccessor: StateRepoAccesor<ScanningState>, coroutineScope: CoroutineScope): Scanner {
            return Scanner(autoEnableBluetooth, permissions, stateRepoAccessor, coroutineScope)
        }
    }

    @Suppress("CONFLICTING_OVERLOADS")
    private val centralManagerDelegate = object : NSObject(), CBCentralManagerDelegateProtocol {

        override fun centralManager(central: CBCentralManager, didDiscoverPeripheral: CBPeripheral, advertisementData: Map<Any?, *>, RSSI: NSNumber) {
            super.centralManager(central, didDiscoverPeripheral, advertisementData, RSSI)

            discoverPeripheral(central, didDiscoverPeripheral, advertisementData.typedMap(), RSSI.intValue)
        }

        override fun centralManagerDidUpdateState(central: CBCentralManager) {
            when (central.state) {
                CBCentralManagerStatePoweredOn -> bluetoothEnabled()
                else -> bluetoothDisabled()
            }
        }

        override fun centralManager(central: CBCentralManager, didConnectPeripheral: CBPeripheral) {
            super.centralManager(central, didConnectPeripheral)

            val connectionManager = connectionManagerMap[didConnectPeripheral.identifier] ?: return
            connectionManager.didConnect()
        }

        override fun centralManager(central: CBCentralManager, didDisconnectPeripheral: CBPeripheral, error: NSError?) {
            super.centralManager(central, didDisconnectPeripheral= didDisconnectPeripheral, error = error)

            val connectionManager = connectionManagerMap[didDisconnectPeripheral.identifier] ?: return
            connectionManager.didConnect()
        }

        override fun centralManager(central: CBCentralManager, didFailToConnectPeripheral: CBPeripheral, error: NSError?) {
            super.centralManager(central, didFailToConnectPeripheral = didFailToConnectPeripheral, error = error)

            val connectionManager = connectionManagerMap[didFailToConnectPeripheral.identifier] ?: return
            connectionManager.didConnect()
        }
    }

    private val mainCentralManager: CBCentralManager
    private val centralManagers = emptyList<CBCentralManager>().toMutableList()
    private var connectionManagerMap = emptyMap<Identifier, DeviceConnectionManager>().toMutableMap()

    init {
        val options = mapOf<Any?, Any>(CBCentralManagerOptionShowPowerAlertKey to autoEnableBluetooth)
        mainCentralManager = CBCentralManager(null, dispatch_get_main_queue(), options)
    }

    override fun scanForDevices(filter: Set<UUID>) {
        connectionManagerMap.clear()

        if (filter.isEmpty()) {
            val centralManager = CBCentralManager(centralManagerDelegate, dispatch_get_main_queue())
            centralManagers.add(centralManager)
            centralManager.scanForPeripheralsWithServices(null, null)
        }

        filter.map { it.uuid }.forEach {
            val centralManager = CBCentralManager(centralManagerDelegate, dispatch_get_main_queue())
            centralManagers.add(centralManager)
            centralManager.scanForPeripheralsWithServices(listOf(it), null)
        }
    }

    override fun stopScanning() {
        centralManagers.forEach {
            when(it.state) {
                CBCentralManagerStatePoweredOn -> it.stopScan()
            }
        }
        centralManagers.clear()
    }

    override fun startMonitoringBluetooth() {
        connectionManagerMap.clear()
        mainCentralManager.delegate = centralManagerDelegate
    }

    override fun stopMonitoringBluetooth() {
        connectionManagerMap.clear()
        mainCentralManager.delegate = null
    }

    private fun discoverPeripheral(central: CBCentralManager, peripheral: CBPeripheral, advertisementDataMap: Map<String, Any>, rssi: Int) {
        if (central == mainCentralManager)
            return
        // Since multiple managers may discover device, make sure even is only triggered once
        if (connectionManagerMap.containsKey(peripheral.identifier))
            return
        launch {
            when (val state = stateRepoAccessor.currentState()) {
                is ScanningState.Enabled.Scanning -> {
                    val advertisementData = AdvertisementData(advertisementDataMap)
                    val deviceInfo = DeviceInfoHolder(peripheral, central, advertisementData)
                    val device = Device(0, deviceInfo, rssi, DeviceConnectionManager.Builder(central))
                    connectionManagerMap[device.identifier] = device.deviceConnectionManager
                    state.discoverDevices(device)
                }
                else -> state.logError(Error("Discovered Device while not scanning"))
            }
        }
    }


}