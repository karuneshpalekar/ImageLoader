package com.karunesh.imageloaderx.core.diskCache


internal class Record(val key: String, val name: String, val time: Long, val size: Long) {

    constructor(record: Record, time: Long) : this(record.key, record.name, time, record.size)

    override fun equals(value: Any?): Boolean {
        if (this === value) return true
        if (value == null || javaClass != value.javaClass) return false
        val record = value as Record
        if (time != record.time) return false
        if (size != record.size) return false
        return if (key != record.key) false else name == record.name
    }

    override fun hashCode(): Int {
        var result = key.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + (time xor (time ushr 32)).toInt()
        result = 31 * result + (size xor (size ushr 32)).toInt()
        return result
    }
}