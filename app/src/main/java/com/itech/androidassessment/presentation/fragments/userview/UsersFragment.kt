package com.itech.androidassessment.presentation.fragments.userview

import UsersAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itech.androidassessment.R
import com.itech.androidassessment.data.database.DatabaseUser
import com.itech.androidassessment.data.model.User
import com.itech.androidassessment.databinding.FragmentUsersBinding
import com.itech.androidassessment.utils.ClickListener
import com.itech.androidassessment.utils.SignUpBottomSheetDialog


class UsersFragment : Fragment(), ClickListener {
    private lateinit var _binding: FragmentUsersBinding
    private lateinit var options:NavOptions
    private val binding get() = _binding!!
    lateinit var listener: ClickListener

    private val viewModel: UsersViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, UsersViewModel.Factory(activity.application))
            .get(UsersViewModel::class.java)
    }

    /**
     * RecyclerView Adapter for converting a list of Video to cards.
     */
    private var viewModelAdapter: UsersAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }

        viewModel.userlist.observe(viewLifecycleOwner, Observer<List<DatabaseUser>> { users ->
            users?.apply {
                viewModelAdapter?.userlist = users
            }
        })

        val bottomSheet : SignUpBottomSheetDialog = SignUpBottomSheetDialog.newInstance()

        binding.addUserFab.setOnClickListener {
            bottomSheet.show(parentFragmentManager,"add user")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUsersBinding.inflate(inflater, container, false)

        listener = this

        viewModelAdapter = UsersAdapter(listener)

        binding.root.findViewById<RecyclerView>(R.id.rv_users) .apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }

        return binding.root
    }

    override fun onItemClick(view: View, user: DatabaseUser) {
        var bundle:Bundle? = Bundle()
        bundle?.putLong("id",user.id!!)
        findNavController().navigate(R.id.profile_dest, bundle, options)
//        val action: NavDirections = UsersFragmentDirections.actionUsersDestToProfileDest(user.id)
//        findNavController().navigate(action)
    }

}