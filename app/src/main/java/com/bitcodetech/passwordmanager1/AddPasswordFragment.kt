package com.bitcodetech.passwordmanager1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bitcodetech.passwordmanager1.databinding.AddPasswordFragmentBinding

class AddPasswordFragment:Fragment() {

    private lateinit var binding:AddPasswordFragmentBinding

    interface OnPasswordAddListener{
        fun onPasswordAdded(password: Password)
    }
    var onPasswordAddListener:OnPasswordAddListener?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.add_password_fragment,null)
        binding= AddPasswordFragmentBinding.bind(view)

        setupListeners()

        return view
    }

    private fun setupListeners(){
        binding.btnSavePassword.setOnClickListener {
            val password = Password(
                binding.edtWebUrl.text.toString(),
                binding.edtUsername.text.toString(),
                binding.edtPassword.text.toString()
            )
            onPasswordAddListener?.onPasswordAdded(password)
            removeCurrentFragment()
        }
    }

    private fun removeCurrentFragment(){
        parentFragmentManager.beginTransaction()
            .remove(this)
            .commit()
    }
}