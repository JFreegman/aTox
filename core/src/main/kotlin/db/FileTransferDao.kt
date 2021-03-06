package ltd.evilcorp.core.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ltd.evilcorp.core.vo.FileTransfer

@Dao
internal interface FileTransferDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(fileTransfer: FileTransfer): Long

    @Delete
    fun delete(fileTransfer: FileTransfer)

    @Query("SELECT * FROM file_transfers WHERE public_key == :publicKey")
    fun load(publicKey: String): Flow<List<FileTransfer>>

    @Query("UPDATE file_transfers SET progress = :progress WHERE id == :id")
    fun updateProgress(id: Int, progress: Long)

    @Query("UPDATE file_transfers SET destination = :destination WHERE id == :id")
    fun setDestination(id: Int, destination: String)
}
