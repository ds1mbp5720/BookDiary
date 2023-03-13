package com.lee.bookdiary.pickup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.lee.bookdiary.R
import com.lee.bookdiary.base.BaseFragment
import com.lee.bookdiary.databinding.PickupFragmentBinding

class PickupFragment: BaseFragment<PickupFragmentBinding,PickupViewModel>() {
    override val layoutId: Int = R.layout.pickup_fragment
    override val viewModel: PickupViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initObserve() {
        super.initObserve()
    }

    override fun initViews() {
        super.initViews()
    }
}