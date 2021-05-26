package hpandro.java.infosec.md5.util

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DATABASENAME = "HashData.db"
val TABLEMD5NAME = "MD5"
val COL_HASH1 = "hash1"
val COL_HASH2 = "hash2"
val COL_HASH3 = "hash3"
val COL_HASH4 = "hash4"
val COL_HASH5 = "hash5"
val COL_HASH6 = "hash6"
val COL_HASH7 = "hash7"
val COL_HASH8 = "hash8"
val COL_ID = "id"

val createMD4HashTable =
    "CREATE TABLE $TABLEMD5NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,$COL_HASH1 VARCHAR(256),$COL_HASH2 VARCHAR(256),$COL_HASH3 VARCHAR(256),$COL_HASH4 VARCHAR(256),$COL_HASH5 VARCHAR(256),$COL_HASH6 VARCHAR(256),$COL_HASH7 VARCHAR(256),$COL_HASH8 VARCHAR(256) )"

class DataBaseHandler(var context: Context) : SQLiteOpenHelper(
    context, DATABASENAME, null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createMD4HashTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $createMD4HashTable")
        onCreate(db)
    }

    fun insertHashData(
        tableName: String,
        hash1: String,
        hash2: String,
        hash3: String,
        hash4: String,
        hash5: String,
        hash6: String,
        hash7: String,
        hash8: String
    ) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_HASH1, hash1)
        contentValues.put(COL_HASH2, hash2)
        contentValues.put(COL_HASH3, hash3)
        contentValues.put(COL_HASH4, hash4)
        contentValues.put(COL_HASH5, hash5)
        contentValues.put(COL_HASH6, hash6)
        contentValues.put(COL_HASH7, hash7)
        contentValues.put(COL_HASH8, hash8)
        val result = database.insert(tableName, null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Hash store failed!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Hash store success!", Toast.LENGTH_SHORT).show()
        }
    }
}