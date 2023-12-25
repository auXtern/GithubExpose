package com.abdullah.githubexpose.presentation.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdullah.core.presentation.UserAdapter
import com.abdullah.core.data.Resource
import com.abdullah.core.domain.model.GithubUser
import com.abdullah.githubexpose.databinding.FragmentFollowBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private var position: Int? = 0
    private var username: String? = null

    private val followViewModel : FollowViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollow.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvFollow.addItemDecoration(itemDecoration)

        followViewModel.getFollow(username!!, position == 1).observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Error -> {
                        showLoading(false)
                        Toast.makeText(
                            requireActivity(),
                            "Terjadi kesalahan " + result.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is Resource.Loading -> {
                        showLoading(true)
                    }
                    is Resource.Success -> {
                        showLoading(false)
                        setFollows(result.data)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        followViewModel.getFollow(username!!, position == 1)
    }

    private fun setFollows(githubUserList: List<GithubUser>?) {
        binding.rvFollow.visibility = View.VISIBLE
        if(githubUserList.isNullOrEmpty()){
            binding.llError.visibility = View.VISIBLE
            binding.rvFollow.adapter = null
        }else{
            binding.llError.visibility = View.GONE
            val adapter = UserAdapter()
            adapter.submitList(githubUserList)
            binding.rvFollow.adapter = adapter
            adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: GithubUser) {}
            })
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_POSITION = "fragment_position"
        const val ARG_USERNAME = "fragment_username"
    }
}