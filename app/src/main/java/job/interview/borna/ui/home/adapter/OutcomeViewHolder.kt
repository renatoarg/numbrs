package job.interview.borna.ui.home.adapter

import android.view.View
import job.interview.borna.convertToCurrency
import job.interview.borna.convertToDate
import job.interview.borna.data.Item
import job.interview.borna.databinding.ItemOutcomeBinding

class OutcomeViewHolder(
    itemView: View,
    private val callBack: (Item) -> Unit
) : ItemsAdapter.BaseViewHolder<Item>(itemView) {

    private val binding = ItemOutcomeBinding.bind(itemView)

    override fun bind(item: Item) {
        binding.apply {
            tvName.text = item.name
            tvValue.text = item.value.convertToCurrency()
            tvDate.text = item.date.convertToDate("dd/MM/yyyy")
            wrapper.setOnClickListener {
                callBack(item)
            }
        }
    }
}
