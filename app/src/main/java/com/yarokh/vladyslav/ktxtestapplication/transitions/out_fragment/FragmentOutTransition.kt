package com.yarokh.vladyslav.ktxtestapplication.transitions.out_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.yarokh.vladyslav.ktxtestapplication.R
import com.yarokh.vladyslav.ktxtestapplication.databinding.FragmentTransitionOutBinding

class FragmentOutTransition: Fragment() {

    private lateinit var binding: FragmentTransitionOutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransitionOutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivTransition.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                binding.ivTransition to "iv_transition"
            )
            val action = FragmentOutTransitionDirections.fromFragmentOutTransitionToFragmentInTransition("https://cdn.motor1.com/images/mgl/ME9zY/s1/2018-mercedes-amg-s65-review.webp")
            findNavController().navigate(action, extras)
        }

        Glide.with(requireContext()).load("https://cdn.motor1.com/images/mgl/ME9zY/s1/2018-mercedes-amg-s65-review.webp").into(binding.ivTransition)
    }
}