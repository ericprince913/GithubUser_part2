package com.example.githubuser_submission2

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubuser_submission2.databinding.DetailuserBinding
import com.google.android.material.tabs.TabLayoutMediator

class detailActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_PERSON = "extra_person"

        @StringRes
        private val TAB_TITTLE = intArrayOf(R.string.tab_text_1, R.string.tab_text_2)
    }

    private lateinit var binding: DetailuserBinding
    private lateinit var viewModel: detailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailuserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (supportActionBar != null) {
            (supportActionBar as ActionBar).title = "Detail User"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0f

        val username = intent.getStringExtra(EXTRA_PERSON)
        val bundle = Bundle()
        bundle.putString(EXTRA_PERSON,username)

        viewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(detailViewModel::class.java)
        viewModel.setUserDetail(username)
        viewModel.getUserDetail().observe(this){ detailDataUser ->
            if (detailDataUser!=null){
                binding.apply {
                    namauser.text = detailDataUser.name
                    usernameuser.text = detailDataUser.login
                    tampilfollower.text = "${detailDataUser.followers} \n Followers"
                    tampilfollowing.text = "${detailDataUser.following} \n Following"
                    tempattinggal.text = detailDataUser.location
                    kantor.text = detailDataUser.company
                    repository.text = "${detailDataUser.publicRepos.toString()} \n repository"

                    Glide.with(this@detailActivity)
                        .load(detailDataUser.avatarUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(fotouser)
                }
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this,bundle)
        binding.apply {
            viewPager2.adapter = sectionsPagerAdapter
            TabLayoutMediator(tabLayout1,viewPager2){tab,position ->
                tab.text = resources.getString(TAB_TITTLE[position])
            }.attach()
        }

    }



}