package com.example.contributors

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import coil.api.load
import coil.transform.CircleCropTransformation
import kotlinx.android.synthetic.main.fragment_user_detail.*
import java.lang.NullPointerException

class UserDetailFragment : Fragment() {

    companion object {
        const val NON_DATA_STR = "no data"
        const val NON_DATA_INT = 0
    }

    lateinit var _user : User

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _user = arguments?.getParcelable("user") ?: throw NullPointerException()

        (requireActivity() as? MainActivity)?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_user_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val avatar : ImageView
        val name : TextView
        val login : TextView

        val n_repos : TextView
        val n_gists : TextView
        val n_following : TextView
        val n_followers : TextView
        val n_following_layout : LinearLayout
        val n_followers_layout : LinearLayout

        val company : TextView
        val blog : TextView
        val location : TextView
        val email : TextView
        val twitter : TextView
        val created_at : TextView
        val updated_at : TextView

        avatar = view.findViewById(R.id.avatar)
        name = view.findViewById(R.id.name)
        login = view.findViewById(R.id.login)

        n_repos = view.findViewById(R.id.n_repos)
        n_gists = view.findViewById(R.id.n_gists)
        n_following = view.findViewById(R.id.n_following)
        n_followers = view.findViewById(R.id.n_followers)
        n_following_layout = view.findViewById(R.id.following_layout)
        n_followers_layout = view.findViewById(R.id.followers_layout)

        company = view.findViewById(R.id.company)
        blog = view.findViewById(R.id.blog)
        location = view.findViewById(R.id.location)
        email = view.findViewById(R.id.email)
        twitter = view.findViewById(R.id.twitter)
        created_at = view.findViewById(R.id.created_at)
        updated_at = view.findViewById(R.id.updated_at)

        avatar.load(_user.element(ELEMENTS.AVATAR_URL)) {
            transformations(CircleCropTransformation())
        }
        name.text = _user.element(ELEMENTS.NAME) ?: NON_DATA_STR
        login.text = _user.element(ELEMENTS.LOGIN) ?: NON_DATA_STR

        n_repos.text = _user.element(ELEMENTS.PUBLIC_REPOS) ?: NON_DATA_INT.toString()
        n_gists.text = _user.element(ELEMENTS.PUBLIC_GISTS) ?: NON_DATA_INT.toString()
        n_following.text = _user.element(ELEMENTS.FOLLOWING) ?: NON_DATA_INT.toString()
        n_followers.text = _user.element(ELEMENTS.FOLLOWERS) ?: NON_DATA_INT.toString()
        n_following_layout.apply {
            setOnClickListener {
                (requireActivity() as? MainActivity)?.replaceFragment(UsersListFragment().apply {
                    arguments = Bundle().apply {
                        putString("url", _user.element(ELEMENTS.FOLLOWING_URL))
                    }
                })
            }
        }
        n_followers_layout.apply {
            setOnClickListener {
                (requireActivity() as? MainActivity)?.replaceFragment(UsersListFragment().apply {
                    arguments = Bundle().apply {
                        putString("url", _user.element(ELEMENTS.FOLLOWERS_URL))
                    }
                })
            }
        }

        company.text = _user.element(ELEMENTS.BLOG) ?: NON_DATA_STR
        blog.text = _user.element(ELEMENTS.LOCATION) ?: NON_DATA_STR
        location.text = _user.element(ELEMENTS.LOCATION) ?: NON_DATA_STR
        email.text = _user.element(ELEMENTS.EMAIL) ?: NON_DATA_STR
        twitter.text = _user.element(ELEMENTS.TWITTER_USERNAME) ?: NON_DATA_STR
        created_at.text = _user.element(ELEMENTS.CREATED_AT) ?: NON_DATA_STR
        updated_at.text = _user.element(ELEMENTS.UPDATED_AT) ?: NON_DATA_STR

    }

    override fun onDetach() {
        super.onDetach()

        (requireActivity() as? MainActivity)?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            setHomeButtonEnabled(false)
        }
    }

}