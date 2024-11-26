package com.example.calmease.network


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

data class GoodMemory(
    val id: Long = 0,
    val title: String,
    val description: String,
    val memoryDateTime: String?,
    val image: ByteArray?,
    val userId: Int
)

class MemoryDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_MEMORIES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_DATE_TIME TEXT,
                $COLUMN_IMAGE BLOB,
                $COLUMN_USER_ID INTEGER
            )
        """
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MEMORIES")
        onCreate(db)
    }

    fun insertMemory(memory: GoodMemory): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, memory.title)
            put(COLUMN_DESCRIPTION, memory.description)
            put(COLUMN_DATE_TIME, memory.memoryDateTime)
            put(COLUMN_IMAGE, memory.image)
            put(COLUMN_USER_ID, memory.userId)
        }
        return db.insert(TABLE_MEMORIES, null, values).also {
            db.close()
        }
    }

    fun getAllMemories(): List<GoodMemory> {
        val db = readableDatabase
        val memories = mutableListOf<GoodMemory>()
        val cursor = db.rawQuery("SELECT * FROM $TABLE_MEMORIES", null)
        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val memory = GoodMemory(
                        id = it.getLong(it.getColumnIndexOrThrow(COLUMN_ID)),
                        title = it.getString(it.getColumnIndexOrThrow(COLUMN_TITLE)),
                        description = it.getString(it.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                        memoryDateTime = it.getString(it.getColumnIndexOrThrow(COLUMN_DATE_TIME)),
                        image = it.getBlob(it.getColumnIndexOrThrow(COLUMN_IMAGE)),
                        userId = it.getInt(it.getColumnIndexOrThrow(COLUMN_USER_ID))
                    )
                    memories.add(memory)
                } while (it.moveToNext())
            }
        }
        db.close()
        return memories
    }

    companion object {
        const val DATABASE_NAME = "memory_database.db"
        const val DATABASE_VERSION = 1

        const val TABLE_MEMORIES = "good_memories"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_DATE_TIME = "memory_date_time"
        const val COLUMN_IMAGE = "image"
        const val COLUMN_USER_ID = "user_id"
    }
}
