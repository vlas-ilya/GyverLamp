package ru.vlasilya.ledlamp.application.features.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.vlasilya.ledlamp.application.ui.utils.viewmodel.ComposeViewModel
import ru.vlasilya.ledlamp.infrastructure.network.NetworkScanner
import ru.vlasilya.ledlamp.infrastructure.services.SettingsService

class SettingsViewModel(
    private val settingsService: SettingsService,
    private val networkScanner: NetworkScanner
) : ComposeViewModel() {
    val lamps: MutableLiveData<List<Pair<String, Int>>> = MutableLiveData()
    val ipAddress: MutableLiveData<String> = MutableLiveData()
    val port: MutableLiveData<Int> = MutableLiveData()
    val needToSave: MutableLiveData<Boolean> = MutableLiveData(false)

    override fun onResume() {
        viewModelScope.launch(Dispatchers.IO) {
            val settings = settingsService.get { it }
            withContext(Dispatchers.Main) {
                ipAddress.value = settings.getIpAddress()
                port.value = settings.getPort()
                viewModelScope.launch(Dispatchers.IO) {
                    val ipList = networkScanner.scan(settings.getPort())
                    withContext(Dispatchers.Main) {
                        lamps.value = ipList
                    }
                }
            }
        }
    }

    fun changeIpAddress(ipAddress: String) {
        this.needToSave.value = true
        this.ipAddress.value = ipAddress
    }

    fun onChangePort(port: Int) {
        this.needToSave.value = true
        this.port.value = port
    }

    fun save() {
        this.needToSave.value = false
        viewModelScope.launch(Dispatchers.IO) {
            settingsService.run { settings ->
                ipAddress.value?.also { settings.setIpAddress(it) }
                port.value?.also { settings.setPort(it) }
            }
        }
    }

    fun refreshIpList(port: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val ipList = networkScanner.scan(port)
            withContext(Dispatchers.Main) {
                lamps.value = ipList
            }
        }
    }
}
