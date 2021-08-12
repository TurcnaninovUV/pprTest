package com.pprtest.ui


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pprtest.R
import com.pprtest.adapter.NumberAdapter
import com.pprtest.databinding.FragmentFeedBinding
import com.pprtest.viewmodels.NumberViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FeedFragment : Fragment() {

    private var lastNumberFibonacci = -1
    private var lastNumberSimple = -1

    private val viewModel: NumberViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)
        val adapter = NumberAdapter()

        binding.container.adapter = adapter

        viewModel.fibonacciData.observe(viewLifecycleOwner) { listFibonacciNumber ->
            adapter.submitList(listFibonacciNumber)
        }

        viewModel.simpleData.observe(viewLifecycleOwner) { listSimpleNumber ->
            adapter.submitList(listSimpleNumber)
        }

        var flagThreeColumns = true

        fun layoutManagerFabric(): GridLayoutManager {
            return if (flagThreeColumns) {
                GridLayoutManager(
                        context, 2,
                        RecyclerView.VERTICAL, false
                )
            } else {
                GridLayoutManager(
                        context, 3,
                        RecyclerView.VERTICAL, false
                )
            }
        }
        binding.container.layoutManager = layoutManagerFabric()

        binding.threeColumns.setOnClickListener {
            flagThreeColumns = !flagThreeColumns
            if (!flagThreeColumns) binding.threeColumns.text = "Две колонки"
            else binding.threeColumns.text = "Три колонки"
            binding.container.layoutManager = layoutManagerFabric()
            viewModel.threeOrTwoColumnsList(flagThreeColumns)
        }

        binding.fibonacci.setOnClickListener {
            lastNumberSimple = (binding.container.layoutManager as GridLayoutManager)
                    .findFirstCompletelyVisibleItemPosition()
            if (lastNumberFibonacci != -1) (binding.container.layoutManager as GridLayoutManager)
                    .scrollToPosition(lastNumberFibonacci)
            viewModel.loadListNumbersFibonacci()
            binding.container.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if ((binding.container.layoutManager as GridLayoutManager).childCount +
                            (binding.container.layoutManager as GridLayoutManager)
                                    .findFirstCompletelyVisibleItemPosition() >= adapter.itemCount) {
                        viewModel.loadListNumbersFibonacci()
                    }
                }
            })
        }


        binding.simple.setOnClickListener {
            lastNumberFibonacci = (binding.container.layoutManager as GridLayoutManager)
                    .findFirstCompletelyVisibleItemPosition()
            if (lastNumberSimple != -1) (binding.container.layoutManager as GridLayoutManager)
                    .scrollToPosition(lastNumberSimple)
            viewModel.loadListSimpleNumbers()
            binding.container.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if ((binding.container.layoutManager as GridLayoutManager).childCount +
                            (binding.container.layoutManager as GridLayoutManager)
                                    .findFirstCompletelyVisibleItemPosition() >= adapter.itemCount) {
                        viewModel.loadListSimpleNumbers()
                    }
                }
            })
        }

        return binding.root
    }

}