package com.example.to_do_app

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Canvas
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_app.R
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import Adapter.ToDoAdapter

class RecyclerViewTouchHelper(private val adapter: ToDoAdapter) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        if (direction == ItemTouchHelper.RIGHT) {
            val builder = AlertDialog.Builder(adapter.context)
            builder.setTitle("Delete Task")
            builder.setMessage("Are You Sure ?")
            builder.setPositiveButton("Yes") { dialog, which ->
                adapter.deleteTask(position)
            }
            builder.setNegativeButton("Cancel") { dialog, which ->
                adapter.notifyItemChanged(position)
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        } else {
            adapter.editItem(position)
        }
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            .addSwipeLeftBackgroundColor(ContextCompat.getColor(adapter.context, R.color.primaryDark))
            .addSwipeLeftActionIcon(R.drawable.ic_baseline_edit)
            .addSwipeRightBackgroundColor(Color.RED)
            .addSwipeRightActionIcon(R.drawable.ic_baseline_delete)
            .create()
            .decorate()
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}