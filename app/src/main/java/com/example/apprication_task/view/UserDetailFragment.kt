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
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.apprication_task.R
import com.example.apprication_task.adapter.UserListAdapter
import com.example.apprication_task.databinding.FragmentUserDetailBinding
import com.example.apprication_task.models.ResponseModel
import com.example.apprication_task.models.Users
import com.example.apprication_task.models.userRequest
import com.example.apprication_task.network.ApiInterface
import com.example.apprication_task.network.RetrofitInstance
import com.example.apprication_task.repository.UserListRepository
import com.example.apprication_task.viewmodel.UserListViewModel
import kotlinx.coroutines.launch

class UserDetailFragment : Fragment() {

    lateinit var binding: FragmentUserDetailBinding

    lateinit var userListViewModel: UserListViewModel
    lateinit var apiInterface: ApiInterface
    var userId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user_detail, container, false)

        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface::class.java)

        userListViewModel = ViewModelProvider(this)[UserListViewModel::class.java]

        if (arguments != null) {
            userId = requireArguments().getInt("userId", 0)
            Log.e("Sahilll", "dataaa :: $userId")
        }

        lifecycleScope.launch {
            userListViewModel.getUserById(userId)
        }

        userListViewModel.userData.observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                Glide.with(this).load(user.image).into(binding.imgView)
                binding.firstname.setText(user.firstname)
                binding.lastname.setText(user.lastname)
                binding.email.setText(user.email)
                binding.number.setText(user.number)
            } else {
                userListViewModel.userDetailResponse.observe(viewLifecycleOwner, userDetailObserver)
                var userRequest = userRequest(userId)
                userListViewModel.callUserDetailApi(UserListRepository(), apiInterface, userRequest)
            }
        })

        binding.imgBack.setOnClickListener {

            findNavController().popBackStack()
        }

        binding.editbutton.setOnClickListener {

            val args = Bundle()
            args.putInt("userId", userId)
            findNavController().navigate(R.id.action_userDetailFragment_to_userEditFragment, args)
        }

        return binding.root
    }

    suspend fun getUserData() {

    }

    //API response
    private var userDetailObserver: Observer<Users> = Observer {

        try {

            Log.e("Sahilll", "DAtaa :: " + it.email)

            Glide.with(this).load(it.image).into(binding.imgView)
            binding.firstname.text = "First Name : " + it.firstName
            binding.lastname.text = "Last Name : " + it.lastName
            binding.email.text = "Email : " + it.email
            binding.number.text = "Number : " + it.phone

        } catch (e: Exception) {
        }

    }
}