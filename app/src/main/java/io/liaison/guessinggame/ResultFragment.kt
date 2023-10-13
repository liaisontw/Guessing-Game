package io.liaison.guessinggame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
//import io.liaison.guessinggame.databinding.FragmentResultBinding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.ComposeView

class ResultFragment : Fragment() {
    //No view binding
    //private var _binding: FragmentResultBinding? = null
    //private val binding get() = _binding!!
    lateinit var viewModel: ResultViewModel
    lateinit var viewModelFactory: ResultViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //_binding = FragmentResultBinding.inflate(inflater, container, false).apply {
        val result = ResultFragmentArgs.fromBundle(requireArguments()).result
        viewModelFactory = ResultViewModelFactory(result)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ResultViewModel::class.java)

        return ComposeView(requireContext()).apply{
            /*composeView.*/setContent {
                MaterialTheme {
                    Surface {
                        view?.let { ResultFragmentContent(it, viewModel)}
                    }
                }
            }
        }
        /* No view binding
        val view = binding.root

        val result = ResultFragmentArgs.fromBundle(requireArguments()).result
        viewModelFactory = ResultViewModelFactory(result)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ResultViewModel::class.java)

        //binding.wonLost.text = viewModel.result
        binding.resultViewModel = viewModel
        binding.newGameButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_resultFragment_to_gameFragment)
        }
        return view
        */
    }

    /* No view binding
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    */

    @Composable
    fun ResultText(result: String) {
        Text(text = result,
            fontSize = 28.sp,
            textAlign = TextAlign.Center )
    }
    @Composable
    fun NewGameButton(clicked: () -> Unit) {
        Button(onClick = clicked) {
            Text("Start New Game")
        }
    }
    @Composable
    fun ResultFragmentContent(view: View, viewModel: ResultViewModel) {
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            ResultText(viewModel.result)
            NewGameButton {
                view.findNavController()
                    .navigate(R.id.action_resultFragment_to_gameFragment)
            }
        }
    }
}