package com.lee.bookdiary.pickup.adapter

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ItemHelperCallBack(
    private val itemMoveListener: OnItemMoveListener
) : ItemTouchHelper.Callback() {
    private var isMoved = false

    interface OnItemMoveListener {
        fun onItemMove(fromPosition: Int, toPosition: Int)
        fun onEndDrag()
    }

    /**
     * 어느 방향으로 움직일지에 따라서 flag 받는것을 정의
     * 드래그는 위, 아래 액션이기 때문에 up, down 을 넘겨줌
     */
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        return makeMovementFlags(dragFlags, 0)
    }

    /**
     * 어느 위치에서 어느 위치로 변경하는지
     */
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        itemMoveListener.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        isMoved = true
        return true
    }

    /**
     * 좌우 스와이프
     */
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        if (isMoved) {
            isMoved = false
            itemMoveListener.onEndDrag()
        }
    }
}