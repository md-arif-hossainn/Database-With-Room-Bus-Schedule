package com.arif.busschedule.repos

import androidx.lifecycle.LiveData
import com.arif.busschedule.BusSchedule
import com.arif.busschedule.daos.ScheduleDao


class ScheduleLocalRepository(private val dao: ScheduleDao) {
    suspend fun addSchedule(busSchedule: BusSchedule) = dao.addSchedule(busSchedule)

    suspend fun updateSchedule(busSchedule: BusSchedule) = dao.updateSchedule(busSchedule)

    suspend fun deleteSchedule(busSchedule: BusSchedule) = dao.deleteSchedule(busSchedule)

    fun getAllSchedules() : LiveData<List<BusSchedule>> = dao.getAllSchedules()

    fun getScheduleById(id: Long) : LiveData<BusSchedule> = dao.getScheduleById(id)
}