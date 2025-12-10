package com.example.ecommerceapp.data.core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.ecommerceapp.model.Model

abstract class BaseManager<T : Model>(
    protected val dao: BaseLocalDataSource<T>
) {
    var items by mutableStateOf(emptyList<T>())
        protected set

    suspend fun findAll() {
        items = dao.findAll()
    }

    suspend fun addItem(item: T) {
        dao.insertOne(item)
        items = items + item
    }

    suspend fun removeItem(item: T) {
        dao.deleteOne(item)
        items = items - item
    }

    suspend fun updateItem(item: T) {
        dao.updateOne(item)
        items = items.map { if (it.id == item.id) item else it }
    }

    suspend fun clear() {
        dao.deleteAll()
        items = emptyList()
    }
}
