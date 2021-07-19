package com.pprtest.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pprtest.adapter.NumberAdapter
import com.pprtest.databinding.FragmentFeedBinding
import com.pprtest.viewmodels.NumberViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : Fragment() {

    private val viewModel: NumberViewModel by viewModels(ownerProducer = ::requireParentFragment)

//    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)
        val adapter = NumberAdapter()

        binding.container.adapter = adapter


        binding.finobacci.setOnClickListener {
            viewModel.fibonacciData.observe(viewLifecycleOwner) { fibonacciNumber ->
                viewModel.loadNumbersFibonacci()
                adapter.submitList(fibonacciNumber)
            }
        }

        binding.simple.setOnClickListener {
            viewModel.simpleData.observe(viewLifecycleOwner) { number ->
                viewModel.loadNumbers()
                adapter.submitList(number)
            }
        }

//        viewPager = binding.viewpager
//
//        val pagerAdapter =
//        viewPager.adapter = pagerAdapter
//
//        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
//            tab.text = if (position == 1) getString(R.string.fibonacci_numbers)
//            else getString(R.string.prime_numbers)
//        }.attach()


        return binding.root
    }
}