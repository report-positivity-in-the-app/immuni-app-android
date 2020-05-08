package org.immuni.android.db.dao

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface RawDao {

    @RawQuery
    suspend fun checkpoint(
        supportSQLiteQuery: SupportSQLiteQuery = SimpleSQLiteQuery("pragma wal_checkpoint(full)")
    ): Int
}
