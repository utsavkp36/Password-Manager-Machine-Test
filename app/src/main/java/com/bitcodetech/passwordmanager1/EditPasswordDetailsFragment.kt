package com.bitcodetech.passwordmanager1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bitcodetech.passwordmanager1.databinding.EditPasswordDetailsFragmentBinding

class EditPasswordDetailsFragment: Fragment() {

    private lateinit var binding:EditPasswordDetailsFragmentBinding
    private var password:Password?=null

    interface OnPasswordDetailsEditListener{
        fun onPasswordEdit(newPassword: Password)
    }
    var onPasswordDetailsEditListener:OnPasswordDetailsEditListener?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.edit_password_details_fragment,null)
        binding= EditPasswordDetailsFragmentBinding.bind(view)

        initData()
        setupListener()

        return view
    }

    private fun setupListener(){
        onPasswordDetailsEditListener?.onPasswordEdit(
            Password(
                binding.edtWebUrl.text.toString(),
                binding.edtUsername.text.toString(),
                binding.edtPassword.text.toString()
            )
        )
        removeCurrentFragment()
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