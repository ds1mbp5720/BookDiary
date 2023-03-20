package com.lee.bookdiary.pickup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lee.bookdiary.R
import com.lee.bookdiary.base.BaseFragment
import com.lee.bookdiary.databinding.PickupFragmentBinding
import com.lee.bookdiary.dialog.DialogMessage
import com.lee.bookdiary.eventbus.BookInfoEvent
import com.lee.bookdiary.pickup.adapter.PickupAdapter
import com.lee.bookdiary.pickup.data.PickupBookRoomDatabase
import com.lee.bookdiary.util.toPickupBookEntity
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

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
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }


    override fun initObserve() {
        super.initObserve()
        viewModel.myPickupList.observe(this){
            pickupAdapter.clearList()
            pickupAdapter.setList(it)
        }
        viewModel.insertClick.observe(this){ data ->
            pickupAdapter.addItem(data)
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
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun onEvent(e: BookInfoEvent){
        viewModel.insertPickupBook(e.bookInfo.toPickupBookEntity())
        pickupAdapter.addItem(e.bookInfo.toPickupBookEntity())
    }
}