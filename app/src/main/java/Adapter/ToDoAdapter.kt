package Adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View;
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_app.AddNewTask
import com.example.to_do_app.MainActivity
import com.example.to_do_app.R
import Model.appModel
import Utils.DataBaseHelper

class ToDoAdapter(private val myDB: DataBaseHelper, val context: Context) :
    RecyclerView.Adapter<ToDoAdapter.MyViewHolder>() {

    private var mList: List<appModel> = ArrayList()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mCheckBox: CheckBox = itemView.findViewById(R.id.checkbox2)

        init {
            mCheckBox.setOnClickListener {
                val position = adapterPosition
                val item = mList[position]
                if (mCheckBox.isChecked) {
                    myDB.updateStatus(item.id, 1)
                } else {
                    myDB.updateStatus(item.id, 0)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.task_layout, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = mList[position]
        holder.mCheckBox.text = item.task
        holder.mCheckBox.isChecked = toBoolean(item.status)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setTasks(mList: List<appModel>) {
        this.mList = mList
        notifyDataSetChanged()
    }

    private fun toBoolean(num: Int): Boolean {
        return num != 0
    }

    fun deleteTask(position: Int) {
        val item = mList[position]
        myDB.deleteTask(item.id)
        mList = mList.toMutableList().apply { removeAt(position) }
        notifyItemRemoved(position)
    }

    fun editItem(position: Int) {
        val item = mList[position]

        val bundle = Bundle().apply {
            putInt("id", item.id)
            putString("task", item.task)
        }

        val task = AddNewTask()
        task.arguments = bundle
        task.show((context as MainActivity).supportFragmentManager, task.tag)
    }
}