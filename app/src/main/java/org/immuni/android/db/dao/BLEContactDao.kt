package org.immuni.android.db.dao

import androidx.room.Dao
import androidx.room.Query
import java.util.*
import kotlinx.coroutines.flow.Flow
import org.immuni.android.db.entity.BLEContactEntity
import org.immuni.android.db.entity.BLEEvent
import org.immuni.android.db.entity.SLOTS_PER_CONTACT_RECORD
import org.immuni.android.db.entity.dateToRelativeTimestamp
import org.immuni.android.util.log
import org.koin.core.KoinComponent

@Dao
interface BLEContactDao : BaseDao<BLEContactEntity>, KoinComponent {
    @Query("SELECT * FROM ble_contact_table")
    suspend fun getAll(): List<BLEContactEntity>

    @Query("SELECT * FROM ble_contact_table")
    fun getAllFlow(): Flow<List<BLEContactEntity>>

    @Query("SELECT COUNT(DISTINCT btId) FROM ble_contact_table")
    suspend fun getAllDistinctBtIdsCount(): Int

    @Query("SELECT * FROM ble_contact_table WHERE btId=:btId ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestByBtId(btId: String): BLEContactEntity?

    @Query("SELECT * FROM ble_contact_table WHERE timestamp > :start AND timestamp <= :end")
    suspend fun getAllBetweenTimestamps(start: Long, end: Long): List<BLEContactEntity>

    @Query("SELECT COUNT() FROM ble_contact_table")
    suspend fun getAllBtIdsCount(): Int

    @Query("SELECT DISTINCT btId FROM ble_contact_table")
    suspend fun getAllDistinctBtIds(): List<String>

    @Query("DELETE FROM ble_contact_table WHERE timestamp < :timestamp")
    suspend fun removeOlderThan(timestamp: Long)
}

/**
 * Insert a new contact into the record blob.
 */
suspend fun BLEContactDao.addContact(btId: String, txPower: Int, rssi: Int, date: Date, slots: Int = SLOTS_PER_CONTACT_RECORD) {

    var entry = this.getLatestByBtId(btId)
    if (entry == null) {
        entry = BLEContactEntity(btId = btId, timestamp = date)
    } else {
        val relativeTimestamp = dateToRelativeTimestamp(referenceDate = entry.timestamp, now = date)
        if (relativeTimestamp > slots - 1) {
            log("creating a new entry because relativeTimestamp is: $relativeTimestamp")
            entry = BLEContactEntity(btId = btId, timestamp = date)
        }
    }

    entry.events += BLEEvent(
        relativeTimestamp = dateToRelativeTimestamp(referenceDate = entry.timestamp, now = date),
        txPower = txPower,
        rssi = rssi
    ).toByteArray()

    this.insert(entry)
}
