package com.bitcodetech.passwordmanager1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bitcodetech.passwordmanager1.databinding.PasswordDetailsFragmentBinding
import com.bitcodetech.passwordmanager1.databinding.PasswordViewBinding

class PasswordDetailsFragment:Fragment() {
    private lateinit var binding: PasswordDetailsFragmentBinding
    var password:Password?=null

    interface OnPasswordActionListener{
        fun onPasswordEdit(newPassword:Password,oldPassword: Password)
        fun onPasswordDelete(password: Password)
    }

    var onPasswordActionListener:OnPasswordActionListener?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.password_details_fragment,null)
        binding= PasswordDetailsFragmentBinding.bind(view)

        initData()
        setupListeners()

        return view
    }

    private fun setupListeners(){
        binding.btnDeletePassword.setOnClickListener {
            onPasswordActionListener?.onPasswordDelete(password!!)
            removeCurrentFragment()
        }

        binding.btnEditPassword.setOnClickListener {
            addEditPasswordDetailsFragment(password!!)
            removeCurrentFragment()
        }
    }


    private fun addEditPasswordDetailsFragment(password: Password){
        val editPasswordDetailsFragment=EditPasswordDetailsFragment()

        val input=Bundle()
        input.putSerializable("password",password)
        editPasswordDetailsFragment.arguments=input

        parentFragmentManager.beginTransaction()
            .add(R.id.mainContainer,editPasswordDetailsFragment)
            .addToBackStack(null)
            .commit()

        editPasswordDetailsFragment.onPasswordDetailsEditListener=object :EditPasswordDetailsFragment.OnPasswordDetailsEditListener{
            override fun onPasswordEdit(newPassword: Password) {
                onPasswordActionListener?.onPasswordEdit(password,newPassword)
            }
        }
    }

    private fun removeCurrentFragment(){
        parentFragmentManager.beginTransaction()
            .remove(this)
            .commit()
    }

    private fun initData(){
        if(arguments!=null && requireArguments().containsKey("password")){
            password=requireArguments().getSerializable("password")as Password

            binding.password=password
        }
    }
}