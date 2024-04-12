package com.example.apprication_task.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apprication_task.R
import com.example.apprication_task.entity.UserEntity
import com.example.apprication_task.models.Users
import com.example.apprication_task.view.UserListFragment

class UserListAdapter2(val mContext: UserListFragment, var users: List<UserEntity>): RecyclerView.Adapter<UserListAdapter2.MyViewHolder>() {

    inner class MyViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        var username = view.findViewById<TextView>(R.id.username)
        var email = view.findViewById<TextView>(R.id.email)
        var imgView = view.findViewById<ImageView>(R.id.imgView)
        var mLayout = view.findViewById<LinearLayout>(R.id.mLayout)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserListAdapter2.MyViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(
        holder: UserListAdapter2.MyViewHolder,
        position: Int
    ) {

        var data = users[position]

        holder.username.text = data.firstname
        holder.email.text = data.email

        Glide.with(mContext).load(data.image).into(holder.imgView)

        holder.mLayout.setOnClickListener {
            val args = Bundle()
            args.putInt("userId", data.id.toInt())
            NavHostFragment.findNavController(mContext)
                .navigate(R.id.action_userListFragment_to_userDetailFragment, args)
        }

    }
}