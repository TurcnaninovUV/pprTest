package com.pprtest.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pprtest.adapter.NumberAdapter
import com.pprtest.databinding.FragmentFeedBinding
import com.pprtest.viewmodels.NumberViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FeedFragment : Fragment() {

    private val viewModel: NumberViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)
        val adapter = NumberAdapter()

        binding.container.adapter = adapter
        val layoutManager = GridLayoutManager(
            context, 2,
            RecyclerView.VERTICAL, false
        )
        binding.container.layoutManager = layoutManager

        viewModel.fibonacciData.observe(viewLifecycleOwner) { listFibonacciNumber ->
            adapter.submitList(listFibonacciNumber)
        }

        viewModel.simpleData.observe(viewLifecycleOwner) { listSimpleNumber ->
            adapter.submitList(listSimpleNumber)
        }

        binding.fibonacci.setOnClickListener {
            viewModel.loadListNumbersFibonacci()
            binding.container.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (layoutManager.childCount + layoutManager.findFirstCompletelyVisibleItemPosition() >= adapter.itemCount) {
                        viewModel.loadListNumbersFibonacci()
                    }

                }
            })
        }

        binding.simple.setOnClickListener {
            viewModel.loadListSimpleNumbers()
            binding.container.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (layoutManager.childCount + layoutManager.findFirstCompletelyVisibleItemPosition() >= adapter.itemCount) {
                        viewModel.loadListSimpleNumbers()
                    }

                }
            })
        }

        return binding.root
    }

}