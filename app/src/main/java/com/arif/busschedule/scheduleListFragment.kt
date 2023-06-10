package com.arif.busschedule

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arif.busschedule.customdialoges.CustomAlertDialog
import com.arif.busschedule.databinding.FragmentNewScheduleBinding
import com.arif.busschedule.databinding.FragmentScheduleListBinding
import com.arif.busschedule.db.ScheduleDB
import com.arif.busschedule.viewmodels.ScheduleViewModel
import com.google.android.material.snackbar.Snackbar


class scheduleListFragment : Fragment() {

    private lateinit var binding: FragmentScheduleListBinding
    private val viewModel: ScheduleViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      binding = FragmentScheduleListBinding.inflate(inflater,container,false)
        val scheduleAdapter = ScheduleAdapter(::onMenuItemClicked,::updateFavorite) //adapter obj creat korsi.ekne ekne recycler view show korb
        binding.scheduleRV.layoutManager = LinearLayoutManager(requireActivity())
        binding.scheduleRV.adapter = scheduleAdapter
        //scheduleAdapter.submitList(scheduleList)
        viewModel.getAllSchedules().observe(viewLifecycleOwner) {scheduleList ->
            scheduleAdapter.submitList(scheduleList)
        }
//        scheduleAdapter.submitList(
//            ScheduleDB.getDB(requireActivity())
//            .getScheduleDao()
//            .getAllSchedules())

        //hide fab button
        binding.scheduleRV.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.d("ScheduleListFragment", "dx: $dx ,dy:$dy")
                if(dy>0){
                    binding.fab.hide()
                }
                if (dy<0){
                    binding.fab.show()
                }
            }
        })

        
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_schedulLisgtFragment_to_newScheduleFragment)
        }
        return binding.root
    }

    private fun updateFavorite(schedule: BusSchedule) {
        viewModel.updateSchedule(schedule)
    }

    private fun onMenuItemClicked(schedule: BusSchedule, action: RowAction) {
        when(action) {
            RowAction.EDIT -> {
                val bundle = bundleOf("id" to schedule.id)
                findNavController().navigate(R.id.action_schedulLisgtFragment_to_newScheduleFragment, bundle)
            }
            RowAction.DELETE -> {
                CustomAlertDialog(
                    icon = R.drawable.ic_baseline_delete_24,
                    title = "Delete ${schedule.name}?",
                    body = "Are you sure to delete this item?",
                    positiveButtonText = "YES",
                    negativeButtonText = "CANCEL",
                ){
                    viewModel.deleteSchedule(schedule)
                    val snackbar = Snackbar.make(binding.scheduleRV, "Deleted", Snackbar.LENGTH_LONG)
                    snackbar.setAction("UNDO") {
                        viewModel.addSchedule(schedule)
                    }
                    snackbar.show()
                }.show(childFragmentManager, null)
            }
            else -> {

            }
        }
    }

}