package com.agilie.aninterface

import android.content.Context
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agilie.aninterface.interfaceinteraction.R
import com.agilie.mobileeastergift.User
import kotlinx.android.synthetic.main.layout_user.view.*


class UsersAdapter(var userList: List<User>, var addNewUserListener: AddNewUserListener,
                   var context: Context) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindData(userList[position].name, userList[position].imageId, userList.lastIndex == position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int)
            = ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.layout_user, parent, false))

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(name: String, imageId: Int, lastPosition: Boolean) {
            itemView.userName.text = name
            itemView.userImage.setImageBitmap(BitmapFactory.decodeResource(context.resources, imageId))

            if (lastPosition) {
                itemView.userItemLayout.setOnClickListener { addNewUserListener.addNewUser() }
            }
        }
    }

    interface AddNewUserListener {
        fun addNewUser()
    }
}