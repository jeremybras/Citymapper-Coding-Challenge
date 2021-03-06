package com.citymapper.codingchallenge.stoppoints

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.citymapper.codingchallenge.R

class StopPointsAdapter(
    private var stopPoints: List<StopPointModel>,
    private val listener: StopPointListener
) : RecyclerView.Adapter<StopPointViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): StopPointViewHolder {
        return StopPointViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_stoppoint, parent, false
            ),
            listener
        )
    }

    override fun getItemCount(): Int = stopPoints.size

    override fun onBindViewHolder(holder: StopPointViewHolder, position: Int) {
        holder.bind(stopPoints[position])
    }

    fun updateData(stopPoints: List<StopPointModel>) {
        this.stopPoints = stopPoints
        notifyDataSetChanged()
    }
}

class StopPointViewHolder(
    itemView: View,
    private val listener: StopPointListener
) : RecyclerView.ViewHolder(itemView) {
    fun bind(stopPoint: StopPointModel) {
        itemView.setOnClickListener {
            listener.onStopPointClicked(stopPoint)
        }
        itemView.findViewById<TextView>(R.id.stopPointTitleTextView).text = stopPoint.name

        with(stopPoint.arrivalTimes) {
            if (this.isNotEmpty()) {
                itemView.findViewById<TextView>(R.id.firstArrivalTimeTextView).text = this[0].time
                itemView.findViewById<TextView>(R.id.secondArrivalTimeTextView).text = this[1].time
                itemView.findViewById<TextView>(R.id.thirdArrivalTimeTextView).text = this[2].time
            }
        }
    }
}

interface StopPointListener {
    fun onStopPointClicked(stopPoint: StopPointModel)
}
