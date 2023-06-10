package com.arif.busschedule.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.arif.busschedule.BusSchedule
import com.arif.busschedule.db.ScheduleDB
import com.arif.busschedule.repos.ScheduleLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScheduleViewModel(application: Application)
    : AndroidViewModel(application){
        private lateinit var repository: ScheduleLocalRepository
        init {
            val dao = ScheduleDB.getDB(application).getScheduleDao()
            repository = ScheduleLocalRepository(dao)
        }

    fun addSchedule(busSchedule: BusSchedule) {
        viewModelScope.launch {
            repository.addSchedule(busSchedule)
        }
    }

    fun updateSchedule(busSchedule: BusSchedule) {
        viewModelScope.launch {
            repository.updateSchedule(busSchedule)
        }
    }

    fun deleteSchedule(busSchedule: BusSchedule) {
        viewModelScope.launch {
            repository.deleteSchedule(busSchedule)
        }
    }

    fun getAllSchedules() : LiveData<List<BusSchedule>> = repository.getAllSchedules()

    fun getScheduleById(id: Long) : LiveData<BusSchedule> = repository.getScheduleById(id)
}