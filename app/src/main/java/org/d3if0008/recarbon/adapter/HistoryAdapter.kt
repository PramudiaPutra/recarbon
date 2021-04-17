package org.d3if0008.recarbon.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import org.d3if0008.recarbon.R
import org.d3if0008.recarbon.data.HistoryData
import org.d3if0008.recarbon.databinding.RecyclerHistoryItemBinding
import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter(private val data: List<HistoryData>)
    : RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){

    private var tgl = mutableListOf<String>()
    private var tanggal = mutableListOf<String>()

    init {
        sortingDate()
    }

    private fun sortingDate(){
        for (index in data){
            Log.d("HistoryAdapter", "Isi Data Tanggal" + index.date)
            formatDate(index.date).let {
                tgl.add(it)
            }
        }

        for (index in tgl){
            if (tanggal.contains(index)){
                tanggal.add("")
            }else{
                tanggal.add(index)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataV = data[position]
        holder.bind(dataV,position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerHistoryItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(private val binding: RecyclerHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(historyData: HistoryData, position: Int) = with(binding){
            if (tanggal[position].isEmpty()){
                dateItem.isGone
            }else{
                dateItem.isVisible

                setItemDate(tanggal[position]).let {
                    when(it){
                        in 0..24->{
                            dateItem.text = root.context.getString(R.string.hari_ini)
                        }
                        in 25..48->{
                            dateItem.text = root.context.getString(R.string.kemarin)
                        }
                        else->{
                            dateItem.text = tanggal[position]
                        }
                    }
                }
            }

            iconHistory.setImageResource(historyData.icon)
            titleHistory.text = historyData.title
            timeHistory.text = historyData.time
            descriptionHistory.text = historyData.description
            carbonHistory.text = historyData.carbon

        }
    }

    fun setItemDate(date: String?): Long {
        val tanggal = formatDate2(date)
        val currentDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Calendar.getInstance().time)
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        Log.e("HistoryAdapter", "Ini tanggal : $tanggal")
        try {
            val date1 = simpleDateFormat.parse(tanggal)!!
            val date2 = simpleDateFormat.parse(currentDate)!!
            val diff = date2.time - date1.time
            val seconds: Long = diff / 1000
            val minutes = seconds / 60
            val hours = minutes / 60
            return (hours)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return 0
    }


    private fun formatDate(inputDate: String?): String{
        Log.e("Input Date", "Kie:$inputDate")
        val parsed: Date?
        val dateInput = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+07:00'", Locale.getDefault())
        val dateOutput = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        dateInput.timeZone = TimeZone.getTimeZone("Asia/Jakarta")
        dateOutput.timeZone = TimeZone.getDefault()
        try {
            parsed = inputDate?.let { dateInput.parse(it) }
            parsed?.let {
                return dateOutput.format(it)
            }
        }catch (e: Exception){
            Log.e("HistoryAdapter Format", e.toString())
        }
        return ""
    }

    private fun formatDate2(inputDate: String?): String{
        Log.e("Input Date", "Kie:$inputDate")
        val parsed: Date?
        val dateInput = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val dateOutput = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        dateOutput.timeZone = TimeZone.getDefault()
        try {
            parsed = inputDate?.let { dateInput.parse(it) }
            parsed?.let {
                return dateOutput.format(it)
            }
        }catch (e: Exception){
            Log.e("HistoryAdapter Format", e.toString())
        }
        return ""
    }
}