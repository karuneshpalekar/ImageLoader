package com.karunesh.imageloaderx.core.diskCache


internal class RecordComparator : Comparator<Record> {
    override fun compare(record1: Record, record2: Record): Int {
        return compare(record2.time, record1.time)
    }

    companion object {
        private fun compare(x: Long, y: Long): Int {
            return if (x < y) -1 else if (x == y) 0 else 1
        }
    }

}