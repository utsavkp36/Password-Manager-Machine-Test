package com.bitcodetech.passwordmanager1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bitcodetech.passwordmanager1.databinding.PasswordViewBinding

class PasswordsAdapter(private val passwords:ArrayList<Password>): Adapter<PasswordsAdapter.PasswordViewHolder>(){

    interface OnPasswordViewClickListener{
        fun onPasswordViewClicked(passwordsAdapter: PasswordsAdapter,view:View,password: Password,
        position: Int)
    }

    var onPasswordViewClickListener:OnPasswordViewClickListener?=null

    inner class PasswordViewHolder(view: View):RecyclerView.ViewHolder(view){
        val binding:PasswordViewBinding

        init {
            binding= PasswordViewBinding.bind(view)

            view.setOnClickListener {
                onPasswordViewClickListener?.onPasswordViewClicked(
                    this@PasswordsAdapter,
                    view,
                    passwords[adapterPosition],
                    adapterPosition
                )
            }
        }
    }

    override fun getItemCount()=passwords.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordViewHolder {
        return PasswordViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.password_view,null))
    }

    override fun onBindViewHolder(holder: PasswordViewHolder, position: Int) {
        holder.binding.password=passwords[position]
    }
}