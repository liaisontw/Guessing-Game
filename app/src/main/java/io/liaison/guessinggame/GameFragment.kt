package io.liaison.guessinggame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
//import io.liaison.guessinggame.databinding.FragmentGameBinding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.em
import androidx.compose.ui.res.stringResource

//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Surface
//import androidx.compose.material.Button
//import androidx.compose.material.Text



class GameFragment : Fragment() {
    //private var _binding: FragmentGameBinding? =null
    //private val binding get() = _binding!!
    lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        viewModel.gameOver.observe(viewLifecycleOwner, Observer { newValue ->
            if (newValue) {
                val action = GameFragmentDirections
                    .actionGameFragmentToResultFragment(viewModel.wonLostMessage())
                view?.findNavController()?.navigate(action)
            }
        })

        //_binding = FragmentGameBinding.inflate(inflater, container, false).apply{
        return ComposeView(requireContext()).apply {
        /*composeView.*/setContent {
                MaterialTheme {
                    Surface {
                        GameFragmentContent(viewModel)
                    }
                }
            }
        }
    }

    /*
    val view = binding.root
    viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

    binding.gameViewModel = viewModel
    binding.lifecycleOwner = viewLifecycleOwner
    viewModel.gameOver.observe(viewLifecycleOwner, Observer { newValue ->
        if (newValue) {
        val action = GameFragmentDirections
            .actionGameFragmentToResultFragment(viewModel.wonLostMessage())
        view.findNavController().navigate(action)
        }
    })

    binding.guessButton.setOnClickListener() {
    viewModel.makeGuess(binding.guess.text.toString().uppercase())
    binding.guess.text = null
    }
    return view
    }
    //override fun onDestroyView() {
//super.onDestroyView()
//_binding = null
//}
    */
}

@Composable
fun FinishGameButton(clicked: () -> Unit) {
    Button(onClick = clicked) {
        Text("Finish Game")
    }
}
@Composable
fun EnterGuess(guess: String, changed: (String) -> Unit) {
    TextField(value = guess,
        onValueChange = changed,
        label = { Text(text = "Guess a letter") })
}
@Composable
fun GuessButton(clicked: () -> Unit) {
    Button(onClick = clicked) {
        Text("Guess")
    }
}
@Composable
fun IncorrectGuessText(viewModel: GameViewModel) {
    val incorrectGuesses = viewModel.incorrectGuesses.observeAsState()
    incorrectGuesses.value?.let {
        Text(stringResource(R.string.incorrect_guesses, it))
    }
}
@Composable
fun LivesLeftText(viewModel: GameViewModel) {
    val livesLeft = viewModel.livesLeft.observeAsState()
    livesLeft.value?.let {
        Text(stringResource(R.string.lives_left, it))
    }
}
@Composable
fun SecretWordDisplay(viewModel: GameViewModel) {
    val display = viewModel.secretWordDisplay.observeAsState()
    display.value?.let {
        Text(text=it,
            letterSpacing = 0.1.em,
            fontSize = 36.sp
        )
    }
}
@Composable
fun GameFragmentContent(viewModel: GameViewModel) {
    val guess = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {
            SecretWordDisplay(viewModel)
        }
        LivesLeftText(viewModel)
        IncorrectGuessText(viewModel)
        EnterGuess(guess.value) { guess.value = it }

        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GuessButton {
                viewModel.makeGuess(guess.value.uppercase())
                guess.value = ""
            }
            FinishGameButton {
                viewModel.finishGame()
            }
        }
    }
}
