package com.HoaEight.hoaeight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.HoaEight.hoaeight.databinding.FragmentScoreBinding

class ScoreFragment : Fragment() {

    private var _binding: FragmentScoreBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userName = arguments?.getString(Constant.USER_NAME) ?: "User"
        val correctAnswers = arguments?.getInt(Constant.CORRECT_ANSWERS) ?: 0
        val totalQuestions = arguments?.getInt(Constant.TOTAL_QUESTIONS) ?: 10
        val gameStatus = arguments?.getString("GAME_STATUS") ?: "finished"

        // Show user's name and score
        binding.tvUsername.text = userName
        binding.tvScore.text = "You got $correctAnswers out of $totalQuestions correct."

        // Display why the game ended
        binding.tvStatus.text = when (gameStatus) {
            "lives" -> "You ran out of lives 💔"
            "time" -> "Time’s up! ⏰"
            else -> "You completed all questions 🎉"
        }

        // Button to return to main user screen
        binding.btnFinish.setOnClickListener {
            findNavController().navigate(R.id.action_Score_to_User)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
