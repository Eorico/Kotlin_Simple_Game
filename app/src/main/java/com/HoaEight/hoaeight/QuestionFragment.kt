package com.HoaEight.hoaeight

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.HoaEight.hoaeight.databinding.FragmentQuestionBinding

class QuestionFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentQuestionBinding? = null
    private val binding get() = _binding!!

    private var mCurrentPosition: Int = 1
    private var mQuestionList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswer: Int = 0
    private var mLives: Int = 3

    private lateinit var mUsername: String
    private var countDownTimer: CountDownTimer? = null
    private var timeLeft: Long = 60000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mUsername = arguments?.getString(Constant.USER_NAME) ?: "User"
        mQuestionList = Constant.getQuestions()

        setQuestion()

        binding.tvOptionOne.setOnClickListener(this)
        binding.tvOptionTwo.setOnClickListener(this)
        binding.tvOptionThree.setOnClickListener(this)
        binding.tvOptionFour.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)
    }

    private fun startTimer() {
        countDownTimer?.cancel()
        timeLeft = 60000

        countDownTimer = object : CountDownTimer(timeLeft, 1000) {
            override fun onTick(millSToFinished: Long) {
                timeLeft = millSToFinished
                val secs = (timeLeft/1000).toInt()
                binding.tvTimer.text = "Time: ${secs}"
            }
            override fun onFinish() {
                loseLife()
            }
        }.start()
    }

    private fun loseLife() {
        mLives--
        updateLiveDisplay()

        if (mLives <=0 ) {
            goToScore()
        } else {
            mCurrentPosition++
            if (mCurrentPosition <= mQuestionList!!.size) {
                setQuestion()
            } else {
                goToScore()
            }
        }
    }

    private fun updateLiveDisplay() {
        val hearts = "❤".repeat(mLives)
        binding.tvLives.text = hearts
    }

    private fun setQuestion() {
        defaultOptionsView()
        val question = mQuestionList!![mCurrentPosition - 1]

        binding.progressBar.progress = mCurrentPosition
        binding.tvProgress.text = "$mCurrentPosition/${binding.progressBar.max}"

        binding.tvQuestion.text = question.question
        binding.ivImage.setImageResource(question.image)
        binding.tvOptionOne.text = question.optionOne
        binding.tvOptionTwo.text = question.optionTwo
        binding.tvOptionThree.text = question.optionThree
        binding.tvOptionFour.text = question.optionFour

        updateLiveDisplay()
        startTimer()

        if (mCurrentPosition == mQuestionList!!.size) {
            binding.btnSubmit.text = "FINISH"
        } else {
            binding.btnSubmit.text = "SUBMIT"
        }
    }

    private fun defaultOptionsView() {
        val options = arrayListOf(
            binding.tvOptionOne,
            binding.tvOptionTwo,
            binding.tvOptionThree,
            binding.tvOptionFour,
        )

        for (choices in options) {
            choices.setTextColor(Color.parseColor("#000000"))
            choices.typeface = Typeface.DEFAULT
            choices.background = ContextCompat.getDrawable(requireContext(), R.drawable.default_option_border_bg)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_option_one -> selectedOptionView(binding.tvOptionOne, 1)
            R.id.tv_option_two -> selectedOptionView(binding.tvOptionTwo, 2)
            R.id.tv_option_three -> selectedOptionView(binding.tvOptionThree, 3)
            R.id.tv_option_four -> selectedOptionView(binding.tvOptionFour, 4)
            R.id.btn_submit -> handleSubmit()
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(requireContext(), R.drawable.selected_option_border_bg)
    }

    private fun handleSubmit() {
        val question = mQuestionList?.get(mCurrentPosition - 1)

        // If button currently says NEXT -> advance to next question
        if (binding.btnSubmit.text == "NEXT") {
            countDownTimer?.cancel()
            mCurrentPosition++
            if (mCurrentPosition <= mQuestionList!!.size) {
                setQuestion()
            } else {
                goToScore()
            }
            return
        }

        // If user hasn't selected an option
        if (mSelectedOptionPosition == 0) {
            Toast.makeText(requireContext(), "Magsagot ka muna boy!", Toast.LENGTH_SHORT).show()
            return
        }

        // At this point the user selected an option and pressed SUBMIT (or FINISH)
        countDownTimer?.cancel()

        // Check answer
        if (question!!.correctAnswer != mSelectedOptionPosition) {
            // wrong
            answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
            // show the correct answer as well so user sees it
            answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

            // give a short delay so user can see feedback, then either move on or finish
            binding.btnSubmit.postDelayed({
                loseLife() // loseLife will advance question or goToScore if lives == 0
            }, 900)

            // reset selection so double-click doesn't trigger anything
            mSelectedOptionPosition = 0
            return
        } else {
            // correct
            mCorrectAnswer++
            answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

            // if this was last question -> show feedback then go to score
            if (mCurrentPosition == mQuestionList!!.size) {
                binding.btnSubmit.postDelayed({
                    goToScore()
                }, 900)
                mSelectedOptionPosition = 0
                return
            }

            // otherwise show feedback briefly, then go to next question
            binding.btnSubmit.postDelayed({
                mCurrentPosition++
                if (mCurrentPosition <= mQuestionList!!.size) {
                    setQuestion()
                } else {
                    goToScore()
                }
            }, 900)

            mSelectedOptionPosition = 0
            return
        }
    }



    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> binding.tvOptionOne.background = ContextCompat.getDrawable(requireContext(), drawableView)
            2 -> binding.tvOptionTwo.background = ContextCompat.getDrawable(requireContext(), drawableView)
            3 -> binding.tvOptionThree.background = ContextCompat.getDrawable(requireContext(), drawableView)
            4 -> binding.tvOptionFour.background = ContextCompat.getDrawable(requireContext(), drawableView)
        }
    }

    private fun goToScore() {
        countDownTimer?.cancel()
        val bundle = Bundle().apply {
            putString(Constant.USER_NAME, mUsername)
            putInt(Constant.CORRECT_ANSWERS, mCorrectAnswer)
            putInt(Constant.TOTAL_QUESTIONS, mQuestionList!!.size)
        }
        findNavController().navigate(R.id.action_Question_to_Score, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
        _binding = null
    }
}

