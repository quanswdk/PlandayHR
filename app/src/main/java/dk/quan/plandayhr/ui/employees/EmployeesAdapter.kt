package dk.quan.plandayhr.ui.employees

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import dk.quan.plandayhr.R
import dk.quan.plandayhr.data.models.EmployeesData
import kotlinx.android.synthetic.main.employees_item.view.*

class EmployeesAdapter(private val clickListeners: ClickListeners) :
    RecyclerView.Adapter<EmployeesAdapter.ViewHolder>() {

    var enployees: List<EmployeesData> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

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

    override fun getItemCount() = enployees.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            bind(enployees[position])
        }
    }

    inner class ViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: EmployeesData) {
            itemView.setOnClickListener {
                clickListeners.onItemClicked(item.id)
            }
            binding.root.firstName.text = item.firstName
            binding.root.lastName.text = item.lastName
        }
    }
}

interface ClickListeners {
    fun onItemClicked(id: Int)
}