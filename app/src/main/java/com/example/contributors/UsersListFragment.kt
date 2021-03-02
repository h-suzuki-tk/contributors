package com.example.contributors

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eclipsesource.json.Json
import com.eclipsesource.json.JsonArray
import kotlinx.coroutines.*

class UsersListFragment : Fragment() {

    companion object {
        const val ONCE_LOAD_NUM = 20
    }

    private lateinit var _http_client : HTTP
    private lateinit var _url : String
    private lateinit var _users_adapter : UsersListAdapter
    private lateinit var _users : JsonArray
    private var _isLoading   : Boolean = true
    private var _isAllLoaded : Boolean = false
    private var _load_count  : Int     = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _url = arguments?.getString("url") ?: throw NullPointerException()
        Log.d("hoge", _url)

        _http_client = (requireActivity() as? MainActivity)?._http_client ?: throw NullPointerException()
        _users_adapter = UsersListAdapter(requireActivity())
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {

        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_users_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progress_bar : ProgressBar
        val users_list   : RecyclerView

        progress_bar = view.findViewById(R.id.prog_bar)
        users_list = view.findViewById(R.id.recycler_view)

        GlobalScope.launch {
            initLoadUsers().let { _isAllLoaded
                Handler(Looper.getMainLooper()).post {
                    progress_bar.visibility = View.GONE
                }
            }
        }

        users_list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(ItemDecoration(requireContext()))
            adapter = _users_adapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1)
                            && (!_isLoading && !_isAllLoaded) ) {
                        progress_bar.visibility = View.VISIBLE
                        GlobalScope.launch {
                            loadUsers().let { _isAllLoaded
                                Handler(Looper.getMainLooper()).post {
                                    progress_bar.visibility = View.GONE
                                }
                            }
                        }
                    }
                }
            })
        }
    }

    override fun onDetach() {
        super.onDetach()
    }

    private suspend fun initLoadUsers() : Boolean = withContext(Dispatchers.Default) {
        var isAllLoaded = false

        _isLoading = true
        async {
            _http_client.get(_url)
        }.await().let { buf ->
            Json.parse(buf).let { jsonvalue ->
                if ( !(jsonvalue is JsonArray)) {
                    toastGetInfError()
                } else {
                    _users = jsonvalue.asArray()
                    if (loadUsers()) { isAllLoaded = true }
                }
            }
            _isLoading = false
        }

        isAllLoaded
    }

    private suspend fun loadUsers() : Boolean = withContext(Dispatchers.Default) {
        var isAllLoaded = false

        _isLoading = true
        repeat( ONCE_LOAD_NUM ) {
            if ( _load_count >= _users.size() ) {
                isAllLoaded = true
                return@repeat
            }
            _users[_load_count++].asObject().let { obj ->
                User.read( _http_client, obj.getString("url", null) ).let { user ->
                    if ( user != null ) {
                        Handler(Looper.getMainLooper()).post {
                            _users_adapter.add(user)
                            _users_adapter.notifyItemInserted(_users_adapter.itemCount - 1)
                        }
                    } else {
                        toastGetInfError()
                        isAllLoaded = true
                        return@repeat
                    }
                }
            }
        }
        _isLoading = false

        isAllLoaded
    }

    private fun toastGetInfError() {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, "情報の取得に失敗しました。しばらく経ってから再度お試しください。", Toast.LENGTH_LONG).show()
        }
    }

}