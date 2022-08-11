package com.example.githubuser_submission2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser_submission2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: searchViewModel
    private lateinit var adapter: userAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = userAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : userAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ItemsItem) {
                Intent(this@MainActivity, detailActivity::class.java).also {
                    it.putExtra(detailActivity.EXTRA_PERSON, data.login)
                    startActivity(it)
                }
            }
        })
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(searchViewModel::class.java)
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter

            btnSearch.setOnClickListener{
                cariUser()
            }
        }
        viewModel.getSearchUsers().observe(this) {
            if (it != null) {
                showLoading(false)
                adapter.setUserList(it)
            }
        }
    }

    private fun cariUser(){
        binding.apply {
            showLoading(true)
            val query = searchUsername.text.toString()
            if (query.isEmpty()) return
            viewModel.setSearchUsers(query)
        }
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }


}