package com.arif.busschedule.daos


import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import com.arif.busschedule.BusSchedule



@Dao
interface ScheduleDao {
    @Insert
    suspend fun addSchedule(busSchedule: BusSchedule)

    @Update
    suspend fun updateSchedule(busSchedule: BusSchedule)

    @Delete
    suspend fun deleteSchedule(busSchedule: BusSchedule)


    @Query("SELECT * FROM tbl_schedule")
    fun getAllSchedules() : LiveData<List<BusSchedule>>

    @Query("select * from tbl_schedule where id = :id")
    fun getScheduleById(id: Long) : LiveData<BusSchedule>
}