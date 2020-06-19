package dk.quan.plandayhr.ui.employees

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dk.quan.plandayhr.R
import dk.quan.plandayhr.data.models.EmployeesData
import kotlinx.android.synthetic.main.employees_item.view.*


class EmployeesAdapter(
    private val clickListeners: ClickListeners
) :
    PagedListAdapter<EmployeesData, EmployeesAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(
                view,
                R.layout.employees_item,
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            getItem(position)?.let { bind(it) }
        }
    }

    inner class ViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: EmployeesData) {
            itemView.setOnClickListener {
                clickListeners.onItemClicked(item)
            }
            binding.root.firstName.text = item.firstName
            binding.root.lastName.text = item.lastName
        }
    }

    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<EmployeesData>() {
                override fun areItemsTheSame(
                    oldItem: EmployeesData,
                    newItem: EmployeesData
                ) = oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: EmployeesData,
                    newItem: EmployeesData
                ) = oldItem.equals(newItem)
            }
    }
}

interface ClickListeners {
    fun onItemClicked(employee: EmployeesData)
}

