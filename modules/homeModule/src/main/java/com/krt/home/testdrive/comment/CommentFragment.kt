package com.krt.home.testdrive.comment

import android.arch.lifecycle.Observer
import android.text.SpannableStringBuilder
import android.view.View
import com.krt.base.ext.getColor
import com.krt.base.ext.postEvent
import com.krt.business.ext.toCustom
import com.krt.frame.ext.setVisible
import com.krt.frame.ext.showToast
import com.krt.frame.frame.fragment.BaseLceFragment
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.frame.frame.toolbar.style.ToolBarStyle
import com.krt.frame.frame.toolbar.style.ToolBarViewStyle
import com.krt.home.R
import com.krt.home.testdrive.list.TestDriveListConfig
import com.krt.home.testdrive.list.eventbus.TestDriveRefreshEventBus
import kotlinx.android.synthetic.main.home_fragment_comment.*
import org.jetbrains.anko.bundleOf

class CommentFragment : BaseLceFragment<CommentViewModel>() {

    var actionCommentSuccess: ((Int) -> Unit)? = null

    private var position: Int = -1
    private var bookNo: String? = null

    override fun initToolBarConfig(): ToolBarConfig =
            ToolBarConfig(
                    R.layout.home_fragment_comment,
                    toolBarStyle = ToolBarStyle.NORMAL,
                    middleTitle = "评价",
                    rightViewStyle = ToolBarViewStyle.TEXT,
                    rightViewTextFontColor = getColor(R.color.base_white),
                    rightViewText = "提交",
                    rightViewClickListener =
                    {
                        save()
                    }
            ).toCustom(
                    customAll = true
            )

    override fun initViewModelLiveData() {
        viewModel?.commentSuccessLiveData?.observe(this, Observer {
            showToast("评价成功")
            actionCommentSuccess?.invoke(position)
            postEvent(TestDriveRefreshEventBus(TestDriveListConfig.ALL))
            postEvent(TestDriveRefreshEventBus(TestDriveListConfig.TOCOMMENT))
            postEvent(TestDriveRefreshEventBus(TestDriveListConfig.FINISH))
            this.pop()
        })

        viewModel?.resultDataLiveData?.observe(this, Observer {
            it?.let {
                appearance.setCurrentStatCount(it.appearance ?: 0)
                interior.setCurrentStatCount(it.interior ?: 0)
                passengerRoom.setCurrentStatCount(it.passengerRoom ?: 0)
                comfortDegree.setCurrentStatCount(it.comfortDegree ?: 0)
                safety.setCurrentStatCount(it.safety ?: 0)
                practicability.setCurrentStatCount(it.practicability ?: 0)
                powerSteering.setCurrentStatCount(it.powerSteering ?: 0)
                comprehensive.setCurrentStatCount(it.comprehensive ?: 0)
                service.setCurrentStatCount(it.service ?: 0)
                influenceBuy.setCurrentStatCount(it.influenceBuy ?: 0)
                recommend.setCurrentStatCount(it.recommend ?: 0)

                comment_text.text = SpannableStringBuilder(it.evaluation ?: "")
            }
        })
    }

    override fun initView() {
        position = arguments?.getInt(POSITION) ?: -1
        bookNo = arguments?.getString(BOOK_NO) ?: ""

        val commentFinished = arguments?.getBoolean(COMMENT_FINISHED) ?: false
        if (commentFinished) {
            toolBarConfigHelper.getRightTextView()?.setVisible(View.GONE)
            comment_text.isEnabled = false
            viewModel?.loadData(bookNo!!)
        }
    }

    private fun save() {
        val bookNo = arguments?.getString(BOOK_NO)
        if (bookNo == null) {
            showToast("找不到单号")
            return
        }

        val appearance = appearance.getCurrentStartCount()
        if (appearance == 0) {
            showToast("您第1题还未做评价")
            return
        }

        val interior = interior.getCurrentStartCount()
        if (interior == 0) {
            showToast("您第2题还未做评价")
            return
        }

        val passengerRoom = passengerRoom.getCurrentStartCount()
        if (passengerRoom == 0) {
            showToast("您第3题还未做评价")
            return
        }

        val comfortDegree = comfortDegree.getCurrentStartCount()
        if (comfortDegree == 0) {
            showToast("您第4题还未做评价")
            return
        }

        val safety = safety.getCurrentStartCount()
        if (safety == 0) {
            showToast("您第5题还未做评价")
            return
        }

        val practicability = practicability.getCurrentStartCount()
        if (practicability == 0) {
            showToast("您第6题还未做评价")
            return
        }

        val powerSteering = powerSteering.getCurrentStartCount()
        if (powerSteering == 0) {
            showToast("您第7题还未做评价")
            return
        }

        val comprehensive = comprehensive.getCurrentStartCount()
        if (comprehensive == 0) {
            showToast("您第8题还未做评价")
            return
        }

        val service = service.getCurrentStartCount()
        if (service == 0) {
            showToast("您第9题还未做评价")
            return
        }

        val influenceBuy = influenceBuy.getCurrentStartCount()
        if (influenceBuy == 0) {
            showToast("您第10题还未做评价")
            return
        }

        val recommend = recommend.getCurrentStartCount()
        if (recommend == 0) {
            showToast("您第11题还未做评价")
            return
        }

        val commentText = comment_text.text.toString().trim()

        viewModel?.save(
                bookNo,
                appearance,
                interior,
                passengerRoom,
                comfortDegree,
                safety,
                practicability,
                powerSteering,
                comprehensive,
                service,
                influenceBuy,
                recommend,
                commentText
        )
    }


    companion object {

        private const val BOOK_NO = "book_no"
        private const val POSITION = "position"
        private const val COMMENT_FINISHED = "comment_finished"


        fun newInstance(bookNo: String, position: Int, commentFinished: Boolean): CommentFragment {
            val fragment = CommentFragment()
            fragment.arguments =
                    bundleOf(Pair(BOOK_NO, bookNo), Pair(POSITION, position), Pair(COMMENT_FINISHED, commentFinished))
            return fragment
        }
    }
}