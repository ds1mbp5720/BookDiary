package com.lee.bookdiary.pickup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.lee.bookdiary.R
import com.lee.bookdiary.base.BaseFragment
import com.lee.bookdiary.databinding.PickupFragmentBinding
import com.lee.bookdiary.pickup.adapter.PickupAdapter

class PickupFragment: BaseFragment<PickupFragmentBinding,PickupViewModel>() {
    override val layoutId: Int = R.layout.pickup_fragment
    override val viewModel: PickupViewModel by viewModels()

    private lateinit var pickupAdapter: PickupAdapter

    companion object {
        const val TAG = "PickupFragment"
        fun newInstance() = PickupFragment()
    }

    override fun initObserve() {
        super.initObserve()
    }

    override fun initViews() {
        super.initViews()
    }
}