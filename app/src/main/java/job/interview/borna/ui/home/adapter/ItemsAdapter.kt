package job.interview.borna.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import job.interview.borna.R
import job.interview.borna.data.Item

class ItemsAdapter(
    private val callBack: (Item) -> Unit
) : RecyclerView.Adapter<ItemsAdapter.BaseViewHolder<*>>() {

    private var items = ArrayList<Item>()

    companion object {
        private const val TYPE_INCOME = 0
        private const val TYPE_OUTCOME = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            TYPE_INCOME -> {
                IncomeViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_income, parent, false),
                    callBack
                )
            }
            TYPE_OUTCOME -> {
                OutcomeViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_outcome, parent, false),
                    callBack
                )
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        return if (item.value > 0) {
            TYPE_INCOME
        } else {
            TYPE_OUTCOME
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is IncomeViewHolder -> holder.bind(items[position])
            is OutcomeViewHolder -> holder.bind(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun resetList(items: List<Item>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }
}