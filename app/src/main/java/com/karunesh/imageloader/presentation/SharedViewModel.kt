package com.karunesh.imageloader.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.karunesh.imageloader.domain.model.DataItem
import com.karunesh.imageloader.domain.usecase.DataUseCases
import com.karunesh.imageloader.util.PAGE_SIZE
import com.karunesh.imageloaderx.core.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val dataUseCases: DataUseCases
) : ViewModel() {


    private val _dataState: MutableStateFlow<PagingData<DataItem>> =
        MutableStateFlow(value = PagingData.empty())

    val dataState: StateFlow<PagingData<DataItem>>
        get() = _dataState

    var imageLoader: ImageLoader? = null

    private val _uriState: MutableStateFlow<List<String?>> =
        MutableStateFlow(emptyList())

    val uriState: StateFlow<List<String?>>
        get() = _uriState

    private val _errorState: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val errorState: StateFlow<Boolean>
        get() = _errorState

    fun getData() {
        viewModelScope.launch {
            try {
                dataUseCases.getData(pageSize = PAGE_SIZE)
                    .distinctUntilChanged()
                    .cachedIn(viewModelScope)
                    .collect {
                        getThumbnails()
                        _dataState.value = it
                        _errorState.value = false
                    }
            } catch (e: Exception) {
                _errorState.value = true
            }

        }
    }

    fun getThumbnails() {
        viewModelScope.launch {
            try {
                dataUseCases.getThumbnail()
                    .distinctUntilChanged()
                    .collect { thumbnails ->
                        val uriList = thumbnails.map { thumbnail ->
                            thumbnail?.let {
                                "${thumbnail.domain}/${thumbnail.basePath}/${
                                    thumbnail.qualities?.get(
                                        thumbnail.qualities.size - 1
                                    )
                                }/${thumbnail.key}"
                            }
                        }
                        _uriState.value = uriList
                        _errorState.value = false
                    }
            } catch (e: Exception) {
                _errorState.value = true
            }

        }
    }


}