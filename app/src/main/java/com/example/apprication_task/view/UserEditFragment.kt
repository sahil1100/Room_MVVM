package com.example.apprication_task.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.apprication_task.R
import com.example.apprication_task.databinding.FragmentUserEditBinding
import com.example.apprication_task.viewmodel.UserListViewModel
import kotlinx.coroutines.launch

class UserEditFragment : Fragment() {

    lateinit var binding: FragmentUserEditBinding
    var userId: Int = 0
    lateinit var userListViewModel: UserListViewModel
    var image: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user_edit, container, false)

        userListViewModel = ViewModelProvider(this)[UserListViewModel::class.java]

        if (arguments != null) {
            userId = requireArguments().getInt("userId", 0)
            Log.e("Sahilll", "dataaa :: $userId")
        }

        lifecycleScope.launch {
            userListViewModel.getUserById(userId)
        }

        userListViewModel.userData.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                image = user.image
                Glide.with(this).load(image).into(binding.imgView)
                binding.firstname.setText(user.firstname)
                binding.lastname.setText(user.lastname)
                binding.email.setText(user.email)
                binding.number.setText(user.number)
            }
        })

        binding.savebutton.setOnClickListener {

            userListViewModel.updateUser(
                userId,
                binding.firstname.text.toString(),
                binding.lastname.text.toString(),
                binding.email.text.toString(),
                binding.number.text.toString(),
                image!!
            )

            findNavController().navigate(R.id.action_userEditFragment_to_userListFragment)
        }

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

}