package com.arif.busschedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arif.busschedule.databinding.ScheduleRowBinding

class ScheduleAdapter(val menuItemCallback: (BusSchedule, RowAction) -> Unit,
                      val favoriteCallback: (BusSchedule) -> Unit
) :
    ListAdapter<BusSchedule,ScheduleAdapter.ScheduleViewHolder>(ScheduleDiffUtil()) {

    //1st view holder
    class ScheduleViewHolder(val binding : ScheduleRowBinding): //ekne receive hosce ScheduleViewHolder(binding)
            RecyclerView.ViewHolder(binding.root) {
        //for dataset i creat a method bind
                fun bind(busScheduls: BusSchedule){
                    //ekne joma hoise holder.bind(schedule) theke ese
                    //ekne sob data ase ekn theke layout file a pass kporte hobe
                   // binding.rowBusNameTV.text = busScheduls.name eve single kore kore korbo nah
                    binding.schedule = busScheduls //busScheduler obja ta layout file a pss kore disi pura ta akn sob pawa jabe besi code ar kora laglo nahc

                }
            }

    //2nd difutil dorkar data update howar somye eita dorkar.pura list jayte referesh korte nah hoye
    //2ta item same kina content same kina
    class ScheduleDiffUtil: DiffUtil.ItemCallback<BusSchedule>(){
        override fun areItemsTheSame(oldItem: BusSchedule, newItem: BusSchedule): Boolean {
          return oldItem.id == newItem.id //new iteam add or remove er junno
        }

        override fun areContentsTheSame(oldItem: BusSchedule, newItem: BusSchedule): Boolean {
           return oldItem == newItem //iteam   update korar junno
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
       //jto gulo item creat korbe totabr call hobe.screen a jto gulo dhorbe tar theke 4-5 ta besi

        val binding = ScheduleRowBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )
        return ScheduleViewHolder(binding) //binding pass korsi
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
       //jto gulo view holadr ase tar moddie data set korbe
        val schedule: BusSchedule = getItem(position)

        /*if (schedule.favorite){
            holder.binding.rowFavourite.setImageResource(R.drawable.ic_baseline_favorite_red)
        }else{
            holder.binding.rowFavourite.setImageResource(R.drawable.ic_baseline_favorite_gray)
        }*///before click
        holder.bind(schedule) //pass korsi bind method a
        holder.binding.rowFavourite.setOnClickListener {
            schedule.favorite = !schedule.favorite //true thakle false,false thakle true
           /* if (schedule.favorite){
                holder.binding.rowFavourite.setImageResource(R.drawable.ic_baseline_favorite_red)
            }else{
                holder.binding.rowFavourite.setImageResource(R.drawable.ic_baseline_favorite_gray)
            }*///after click
            holder.bind(schedule) //abro pass kortesi load howar por
            favoriteCallback(schedule)

        }

        val menuIV = holder.binding.menuIV
        menuIV.setOnClickListener {
            val popupMenu = PopupMenu(menuIV.context, menuIV)
            popupMenu.menuInflater.inflate(R.menu.row_menu, popupMenu.menu)
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener {menuItem ->
                val action: RowAction = when(menuItem.itemId) {
                    R.id.item_edit -> RowAction.EDIT
                    R.id.item_delete -> RowAction.DELETE
                    else -> RowAction.NONE
                }
                menuItemCallback(schedule, action)
                true
            }
        }
    }
}

enum class RowAction {
    EDIT, DELETE, NONE
}