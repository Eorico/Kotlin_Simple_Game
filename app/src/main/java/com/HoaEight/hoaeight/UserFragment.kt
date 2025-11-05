package com.HoaEight.hoaeight

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.HoaEight.hoaeight.databinding.FragmentUserBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStart.setOnClickListener {
            val userName = binding.etName.text.toString()
            if (userName.isNotEmpty()) {
                val bundle = Bundle().apply {
                    putString(Constant.USER_NAME, userName)
                }
                findNavController().navigate(R.id.action_User_to_Question, bundle)
            } else {
                Toast.makeText(requireContext(), "Pakilagay ang pangalan mo muna!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}