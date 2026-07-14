package com.remcalendar.data.repository

import com.remcalendar.data.dao.AnniversaryDao
import com.remcalendar.data.model.Anniversary
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AnniversaryRepository @Inject constructor(
    private val anniversaryDao: AnniversaryDao
) {
    fun getAllAnniversaries(): Flow<List<Anniversary>> = anniversaryDao.getAllAnniversaries()

    suspend fun getAnniversaryById(id: Long): Anniversary? = anniversaryDao.getAnniversaryById(id)

    fun searchAnniversaries(query: String): Flow<List<Anniversary>> =
        anniversaryDao.searchAnniversaries(query)

    suspend fun insertAnniversary(anniversary: Anniversary): Long =
        anniversaryDao.insertAnniversary(anniversary)

    suspend fun updateAnniversary(anniversary: Anniversary) =
        anniversaryDao.updateAnniversary(anniversary)

    suspend fun deleteAnniversary(anniversary: Anniversary) =
        anniversaryDao.deleteAnniversary(anniversary)

    suspend fun deleteAnniversaryById(id: Long) = anniversaryDao.deleteAnniversaryById(id)

    suspend fun updateTopStatus(id: Long, isTop: Boolean) =
        anniversaryDao.updateTopStatus(id, isTop)
}
