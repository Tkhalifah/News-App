package com.tahadroid.newsapp.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.google.android.material.tabs.TabLayout
import com.tahadroid.newsapp.R
import com.tahadroid.newsapp.data.remote.ApiManager
import com.tahadroid.newsapp.pojo.headlines.HeadlinesResponse
import com.tahadroid.newsapp.pojo.sources.SourcesItem
import com.tahadroid.newsapp.pojo.sources.SourcesResponse
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


const val API_KEY = "ac231a0a018d4182aca5404ee67a2b0a"

class MainActivity : AppCompatActivity() {
    private lateinit var sourcesAdapter: SourcesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sourcesAdapter = SourcesAdapter { view, articlesItem, i ->
        }
        sourcesRecyclerView.adapter = sourcesAdapter
        getSources()

    }



    private fun getSources() {
        ApiManager.apiServices.getSources(API_KEY).enqueue(object : Callback<SourcesResponse> {
            override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<SourcesResponse>,
                response: Response<SourcesResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.sources?.let { setupTabLayout(it) }
                }

            }

        })
    }
    private fun setupTabLayout(it: List<SourcesItem>) {
        for (item in it) {
            val tabItem = tabLayout.newTab()
            tabItem.text = item.name
            tabItem.tag = item.id
            tabLayout.addTab(tabItem)
        }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {
                getHeadlines(tab)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                getHeadlines(tab)
            }

        })
        tabLayout.selectTab(tabLayout.getTabAt(0))
    }
    private fun getHeadlines(tab: TabLayout.Tab) {
        ApiManager.apiServices.getHeadlines(tab.tag.toString(), API_KEY)
            .enqueue(object : Callback<HeadlinesResponse> {
                override fun onFailure(call: Call<HeadlinesResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<HeadlinesResponse>,
                    response: Response<HeadlinesResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.articles?.let {
                            sourcesAdapter.swapData(it)
                        }
                    }
                }

            })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater =menuInflater
        inflater.inflate(R.menu.menu_search,menu)
        val manager=getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem =menu?.findItem(R.id.search)
        val searchView =searchItem?.actionView as SearchView
        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                searchView.setQuery("",false)
                searchItem.collapseActionView()
                Toast.makeText(this@MainActivity,"Looking for $query",Toast.LENGTH_LONG).show()

                getSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Toast.makeText(this@MainActivity,"Looking for $newText",Toast.LENGTH_LONG).show()
                getSearch(newText)
                return false
            }

        })
        return true
    }

    private fun getSearch(query: String?) {
        ApiManager.apiServices.searchAbout(query!!, API_KEY)
            .enqueue(object : Callback<HeadlinesResponse> {
                override fun onFailure(call: Call<HeadlinesResponse>, t: Throwable) {
                    Toast.makeText(
                        this@MainActivity,
                        "Looking for $query failed",
                        Toast.LENGTH_LONG
                    ).show()

                }

                override fun onResponse(
                    call: Call<HeadlinesResponse>,
                    response: Response<HeadlinesResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.articles?.let {
                            sourcesAdapter.swapData(it)
                        }
                    }
                }

            })
    }
}
