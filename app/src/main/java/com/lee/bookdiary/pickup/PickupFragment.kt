package com.lee.bookdiary.pickup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lee.bookdiary.R
import com.lee.bookdiary.base.BaseFragment
import com.lee.bookdiary.databinding.PickupFragmentBinding
import com.lee.bookdiary.dialog.DialogMessage
import com.lee.bookdiary.pickup.adapter.PickupAdapter
import com.lee.bookdiary.pickup.data.PickupBookRoomDatabase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PickupFragment: BaseFragment<PickupFragmentBinding,PickupViewModel>() {
    override val layoutId: Int = R.layout.pickup_fragment
    override val viewModel: PickupViewModel by viewModels()
    lateinit var db: PickupBookRoomDatabase
    private val pickupAdapter by lazy { PickupAdapter() }

    companion object {
        const val TAG = "PickupFragment"
        fun newInstance() = PickupFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = context?.let { PickupBookRoomDatabase.getDatabase(it) }!!
        viewModel.getPickupBookList(db)
        pickupAdapter.setViewModel(viewModel)
    }

    override fun initObserve() {
        super.initObserve()
        viewModel.myPickupList.observe(this){
            pickupAdapter.clearList()
            pickupAdapter.setList(it)
        }
        viewModel.deleteClick.observe(this){ data ->
            DialogMessage("찜을 해제하시겠습니까?",getString(R.string.str_confirm),getString(R.string.str_cancel)).onRightBtn{
                viewModel.deletePickupBook(data.id)
                val position = pickupAdapter.getItems().indexOfFirst { it.id == data.id }
                if (position >-1) pickupAdapter.removeItem(position)
            }.show(parentFragmentManager,"")
        }
    }

    override fun initViews() {
        super.initViews()
        setPickupBookRecycler()
    }

    private fun setPickupBookRecycler(){
        dataBinding.recyclerPickup.layoutManager = LinearLayoutManager(requireContext())
        dataBinding.recyclerPickup.itemAnimator = null
        dataBinding.recyclerPickup.adapter = pickupAdapter
    }
}