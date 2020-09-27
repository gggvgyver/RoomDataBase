package com.hks.roomdatabase.fragment.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hks.roomdatabase.R
import com.hks.roomdatabase.model.User
import com.hks.roomdatabase.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_update, container, false)
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.update_firstName_et.setText(args.currentUser.firstName)
        view.update_lastName_et.setText(args.currentUser.lastName)
        view.update_age_et.setText(args.currentUser.age.toString())

        view.update_button.setOnClickListener {
            updateItem()
        }

        // 메뉴추가
        setHasOptionsMenu(true)

        return view
    }

    private fun updateItem() {
        val firstName = update_firstName_et.text.toString()
        val lastName = update_lastName_et.text.toString()
        val age = Integer.parseInt(update_age_et.text.toString())

        if(inputCheck(firstName, lastName, update_age_et.text)){
            val updatedUser = User(args.currentUser.id, firstName, lastName, age)
            mUserViewModel.updateUser(updatedUser)
            Toast.makeText(requireContext(), "수정완료", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "입력요망", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete) {
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("예") {_, _  ->
            mUserViewModel.deleteUser(args.currentUser)
            Toast.makeText(requireContext(), "${args.currentUser.firstName} 삭제 완료", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("아니오") {_, _ -> }
        builder.setTitle("삭제 ${args.currentUser.firstName}?")
        builder.setMessage("${args.currentUser.firstName} 삭제하시겠습니까?")
        builder.create().show()
    }
}