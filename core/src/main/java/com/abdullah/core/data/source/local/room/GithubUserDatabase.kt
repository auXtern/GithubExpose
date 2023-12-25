package com.abdullah.core.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abdullah.core.data.source.local.entity.GithubUserEntity
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(entities = [GithubUserEntity::class], version = 1, exportSchema = false)
abstract class GithubUserDatabase : RoomDatabase() {
    abstract fun githubUserDao(): GithubUserDao

    companion object {
        private const val Database_NAME = "GithubUser.db"

        @Volatile
        private var INSTANCE: GithubUserDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): GithubUserDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    val passphrase: ByteArray = SQLiteDatabase.getBytes("expose@2023".toCharArray())
                    val factory = SupportFactory(passphrase)
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GithubUserDatabase::class.java,
                        Database_NAME
                    ).fallbackToDestructiveMigration()
                        .openHelperFactory(factory)
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}