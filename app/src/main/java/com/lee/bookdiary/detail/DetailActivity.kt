package com.lee.bookdiary.detail

import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lee.bookdiary.R
import com.lee.bookdiary.base.BaseActivity
import com.lee.bookdiary.data.BookInfo
import com.lee.bookdiary.databinding.DetailActivityBinding
import com.lee.bookdiary.search.getDateString
import jp.wasabeef.glide.transformations.BlurTransformation


class DetailActivity : BaseActivity<DetailActivityBinding, DetailViewModel>() {
    override val layoutId = R.layout.detail_activity
    override val viewModel: DetailViewModel by viewModels()
    private lateinit var bookInfo: BookInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initViews() {
        super.initViews()
        bookInfo = intent.getParcelableExtra("bookInfo")!!
        viewModel.setBookInfo(bookInfo)
    }

    override fun initObserve() {
        super.initObserve()
        viewModel.bookInfoLiveData.observe(this){
            setBookInfo(it)
        }
    }
    private fun setBookInfo(bookInfo: BookInfo){

        Glide.with(this@DetailActivity)
            .load(bookInfo.thumbnail)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(22)))
            .placeholder(R.drawable.ic_baseline_menu_book_24)
            .error(R.drawable.ic_baseline_menu_book_24)
            .into(dataBinding.iwBackground)


        Glide.with(this@DetailActivity)
            .load(bookInfo.thumbnail)
            .placeholder(R.drawable.ic_baseline_menu_book_24)
            .error(R.drawable.ic_baseline_menu_book_24)
            .into(dataBinding.iwThumbnail)

        with(dataBinding){
            twBookTitle.text = bookInfo.title
            twAuthor.text = if (bookInfo.authors.isNotEmpty()) bookInfo.authors[0] else null
            twTranslator.text = if (bookInfo.translators.isNotEmpty()) bookInfo.translators[0] else null
            twPublisher.text = bookInfo.publisher
            twPrice.text = String.format(getString(R.string.won), bookInfo.price)
            twStatus.text = bookInfo.status
            twReleaseDate.text = getDateString(bookInfo.datetime, getString(R.string.iso_date_format), getString(R.string.date_format))
            twBookDescription.text = String.format(getString(R.string.book_contents), bookInfo.contents)
        }

    }
}