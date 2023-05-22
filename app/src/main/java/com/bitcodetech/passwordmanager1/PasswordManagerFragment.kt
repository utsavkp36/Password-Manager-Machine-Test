package com.bitcodetech.passwordmanager1

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bitcodetech.passwordmanager1.databinding.PasswordManagerFragmentBinding

class PasswordManagerFragment:Fragment() {

    private lateinit var binding:PasswordManagerFragmentBinding
    private lateinit var passwordsAdapter: PasswordsAdapter
    private lateinit var passwords:ArrayList<Password>
    private var passwordActionPosition=-1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.password_manager_fragment,null)
        binding=PasswordManagerFragmentBinding.bind(view)

        initData()
        initRecyclerViewAndAdapter()
        setupListeners()

        return view
    }

    private fun setupListeners(){
        passwordsAdapter.onPasswordViewClickListener=object:PasswordsAdapter.OnPasswordViewClickListener{
            override fun onPasswordViewClicked(
                passwordsAdapter: PasswordsAdapter,
                view: View,
                password: Password,
                position: Int
            ) {
                passwordActionPosition=position
                addPasswordDetailsFragment(password)
            }

        }

        binding.btnAddPassword.setOnClickListener {
            addAddPasswordFragment()
        }
    }

    private fun addAddPasswordFragment(){
        val addPasswordFragment=AddPasswordFragment()

        addPasswordFragment.onPasswordAddListener=object :AddPasswordFragment.OnPasswordAddListener{
            override fun onPasswordAdded(password: Password) {
                passwords.add(password)
                passwordsAdapter.notifyItemInserted(passwords.size-1)
            }
        }

        parentFragmentManager.beginTransaction()
            .add(R.id.mainContainer,addPasswordFragment)
            .addToBackStack(null)
            .commit()

    }

    private fun addPasswordDetailsFragment(password: Password){
        val passwordDetailsFragment=PasswordDetailsFragment()

        val input=Bundle()
        input.putSerializable("password",password)
        passwordDetailsFragment.arguments=input

        passwordDetailsFragment.onPasswordActionListener=MyOnPasswordActionListener()

        parentFragmentManager.beginTransaction()
            .add(R.id.mainContainer,passwordDetailsFragment)
            .addToBackStack(null)
            .commit()
    }

    inner class MyOnPasswordActionListener:PasswordDetailsFragment.OnPasswordActionListener{
        override fun onPasswordEdit(newPassword: Password, oldPassword: Password) {
            passwords[passwordActionPosition]=newPassword
            passwordsAdapter.notifyItemChanged(passwordActionPosition)
            passwordActionPosition=-1
        }

        override fun onPasswordDelete(password: Password) {
            passwords.removeAt(passwordActionPosition)
            passwordsAdapter.notifyItemRemoved(passwordActionPosition)
            passwordActionPosition=-1
        }
    }

    private fun initData(){
        passwords= ArrayList()

        passwords.add(
            Password(
                "bitcode.com",
                "utsav12",
                "uegiye32"
            )
        )
        passwords.add(
            Password(
                "amazon.com",
                "utsav2",
                "hs3yar2"
            )
        )
        passwords.add(
            Password(
                "bitcode.com",
                "utsakp34",
                "hsgyu0"
            )
        )
        passwords.add(Password("flipkart.com","sujan32","47251"))
        passwords.add(Password("flipkart.com","suraj52","uywrtv1"))
    }

    private fun initRecyclerViewAndAdapter(){
        binding.recyclerPasswords.layoutManager= LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

        passwordsAdapter=PasswordsAdapter(passwords)
        binding.recyclerPasswords.adapter=passwordsAdapter
    }
}