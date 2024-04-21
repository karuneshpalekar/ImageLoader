package com.karunesh.imageloaderx.core.diskCache

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.Collections


@Suppress("unused")
internal class Journal private constructor(
    private val file: File?,
    private val fileManager: FileManager,
    private val logger: Logger
) {
    private val map: MutableMap<String, Record> = HashMap()
    var totalSize: Long = 0
        private set

    @Throws(IOException::class)
    fun put(record: Record, cacheSize: Long) {
        val fileSize = record.size
        prepare(fileSize, cacheSize)
        put(record)
    }

    private fun put(record: Record) {
        map[record.key] = record
        totalSize += record.size
    }

    operator fun get(key: String): Record? {
        val record = map[key]
        if (record != null) {
            updateTime(record)
        }
        return record
    }

    fun delete(key: String): Record? {
        val record = map.remove(key)
        if (record != null) {
            totalSize -= record.size
        }
        return record
    }

    fun keySet(): Set<String> {
        return Collections.unmodifiableSet(map.keys)
    }

    private fun updateTime(record: Record) {
        val time = System.currentTimeMillis()
        map[record.key] = Record(record, time)
    }

    @Throws(IOException::class)
    private fun prepare(fileSize: Long, cacheSize: Long) {
        if (totalSize + fileSize > cacheSize) {
            val records: MutableList<Record> = ArrayList(map.values)
            Collections.sort<Record>(records, RecordComparator())
            for (c in records.size - 1 downTo 1) {
                val record: Record = records.removeAt(c)
                val nextTotalSize = totalSize - record.size
                fileManager.delete(record.name)
                map.remove(record.key)
                totalSize = nextTotalSize
                if (totalSize + fileSize <= cacheSize) {
                    break
                }
            }
        }
    }

    val journalSize: Long?
        get() = file?.length()

    fun writeJournal() {
        try {
            FileOutputStream(file).use { fileStream ->
                DataOutputStream(BufferedOutputStream(fileStream)).use { stream ->
                    stream.writeShort(JOURNAL_FORMAT_VERSION)
                    stream.writeInt(map.size)
                    for (record in map.values) {
                        stream.writeUTF(record.key)
                        stream.writeUTF(record.name)
                        stream.writeLong(record.time)
                        stream.writeLong(record.size)
                    }
                }
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }

    companion object {
        const val JOURNAL_FORMAT_VERSION = 1
        fun readJournal(fileManager: FileManager, logger: Logger): Journal {
            val file = fileManager.journal()
            val journal = Journal(file, fileManager, logger)
            try {
                FileInputStream(file).use { fileStream ->
                    DataInputStream(BufferedInputStream(fileStream)).use { stream ->
                        val version = stream.readShort().toInt()
                        require(version == JOURNAL_FORMAT_VERSION) { "Invalid journal format version" }
                        val count = stream.readInt()
                        var totalSize: Long = 0
                        for (c in 0 until count) {
                            val key = stream.readUTF()
                            val name = stream.readUTF()
                            val time = stream.readLong()
                            val size = stream.readLong()
                            totalSize += size
                            val record =
                                Record(key, name, time, size)
                            journal.put(record)
                        }
                        journal.totalSize = totalSize

                    }
                }
            } catch (ignored: FileNotFoundException) {
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
            return journal
        }
    }
}