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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apprication_task.MyApplication
import com.example.apprication_task.R
import com.example.apprication_task.adapter.UserListAdapter
import com.example.apprication_task.databinding.FragmentUserListBinding
import com.example.apprication_task.entity.UserEntity
import com.example.apprication_task.models.ResponseModel
import com.example.apprication_task.network.ApiInterface
import com.example.apprication_task.network.RetrofitInstance
import com.example.apprication_task.repository.UserListRepository
import com.example.apprication_task.viewmodel.UserListRoomViewModel
import com.example.apprication_task.viewmodel.UserListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.lifecycle.observe
import com.example.apprication_task.adapter.UserListAdapter2
import com.example.apprication_task.repository.UserRepository

class UserListFragment : Fragment() {

    lateinit var binding: FragmentUserListBinding

    lateinit var userListViewModel: UserListViewModel
    lateinit var apiInterface: ApiInterface

    lateinit var adapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user_list, container, false)

        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface::class.java)

        userListViewModel = ViewModelProvider(this)[UserListViewModel::class.java]

        viewLifecycleOwner.lifecycleScope.launch {

            val userDao = MyApplication.database.userDao()
            val userRepository = UserRepository(userDao)

            userListViewModel.getUsersFromDatabase(userRepository).collect { users ->
                if(users.isNotEmpty()){
                    Log.e("Sahillllll", "USers not empty :: " + users)
                    var adapter2 = UserListAdapter2(this@UserListFragment, users)
                    binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    binding.recyclerView.adapter = adapter2
                }else{
                    Log.e("Sahillllll", "USers empty :: " )
                    userListViewModel.userListResponse.observe(viewLifecycleOwner, userListObserver)
                    userListViewModel.callUserListApi(UserListRepository(), apiInterface)
                }
            }
        }

        return binding.root
    }

    //API response
    private var userListObserver: Observer<ResponseModel> = Observer {

        it?.let {
            if (it.users.isNotEmpty()) {
                val users = it.users.map { user ->
                    UserEntity(user.id!!.toLong(), user.firstName, user.lastName, user.email, user.phone, user.image)
                }
                GlobalScope.launch(Dispatchers.IO) {
                    MyApplication.database.userDao().insert(users)
                }

                // Update RecyclerView with the adapter
                adapter = UserListAdapter(this@UserListFragment, it.users)
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.recyclerView.adapter = adapter
            }
        }

    }
}