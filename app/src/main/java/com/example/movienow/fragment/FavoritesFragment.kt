package com.example.movienow.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movienow.R
import com.example.movienow.databinding.FragmentFavoritesBinding
import com.example.movienow.databinding.FragmentMovieBinding


class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}