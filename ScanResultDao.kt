package com.example.plantdetector.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ScanResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScan(scan: ScanResult): Long

    @Query("SELECT * FROM scan_results ORDER BY timestamp DESC")
    fun getAllScans(): Flow<List<ScanResult>>

    @Query("SELECT * FROM scan_results WHERE id = :id")
    suspend fun getScanById(id: Long): ScanResult?

    @Query("DELETE FROM scan_results WHERE id = :id")
    suspend fun deleteScan(id: Long)

    @Query("DELETE FROM scan_results")
    suspend fun deleteAllScans()

    @Query("SELECT COUNT(*) FROM scan_results WHERE isHealthy = 0")
    fun getDiseasedCount(): Flow<Int>
}
