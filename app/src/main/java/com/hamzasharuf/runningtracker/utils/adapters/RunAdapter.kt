package com.hamzasharuf.runningtracker.utils.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hamzasharuf.runningtracker.R
import com.hamzasharuf.runningtracker.data.database.entities.Run
import com.hamzasharuf.runningtracker.utils.common.getFormattedStopWatchTimee
import kotlinx.android.synthetic.main.item_run.view.*
import java.text.SimpleDateFormat
import java.util.*

class RunAdapter(private val onClickListener: OnClickListener) : RecyclerView.Adapter<RunAdapter.RunViewHolder>() {

    inner class RunViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){}

    val diffCallback = object : DiffUtil.ItemCallback<Run>() {
        override fun areItemsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Run>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        return RunViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_run,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val run = differ.currentList[position]
        holder.itemView.apply {

            val calendar = Calendar.getInstance().apply {
                timeInMillis = run.timestamp
            }
            val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
            val date = dateFormat.format(calendar.time)
            tvDate.text = date

            val avgSpeed = "${run.avgSpeedInKMH}km/h"
            tvAvgSpeed.text = avgSpeed

            val distanceInKm = "${run.distanceInMeters / 1000f}km"
            tvDistance.text = distanceInKm

            val time = getFormattedStopWatchTimee(run.timeInMillis)
            tvTime.text = time

            val caloriesBurned = "${run.caloriesBurned}kcal"
            tvCalories.text = caloriesBurned

            rootLayout.setOnClickListener {
                onClickListener.onClick(run,date, avgSpeed, distanceInKm, time, caloriesBurned)
            }
        }
    }

    // TODO : Improve because it sucks
    fun interface OnClickListener{
        fun onClick(
            run: Run,
            date: String,
            speed: String,
            distance: String,
            time: String,
            calories: String
        )
    }
}
