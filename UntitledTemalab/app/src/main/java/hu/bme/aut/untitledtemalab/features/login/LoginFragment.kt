package hu.bme.aut.untitledtemalab.features.login

import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.untitledtemalab.R
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewModel()
        observeResponse()
        setLoginBehaviour()
        setRegisterTextBehaviour()
    }

    private fun setLoginBehaviour() {
        btnLogin.setOnClickListener {
            viewModel.successfulLoginHappened = false
            viewModel.attemptToLoginUser(etEmail.text.toString(), etPassword.text.toString())
        }
    }

    private fun setRegisterTextBehaviour(){
        tvRegister.setOnClickListener{
            LoginFragmentDirections.actionLoginFragmentToRegisterFragment().let{
                findNavController().navigate(it)
            }
        }
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    private fun observeResponse() {
        viewModel.loginResponse.observe(viewLifecycleOwner) { response ->
            clearUIErrors()
            when {
                response.error is Exception -> handleError(response.error)
                response.userId !is Long -> handleError(IllegalStateException(LoginViewModel.ERROR_SERVER))
                !viewModel.successfulLoginHappened -> loginUser(response.userId)
            }
        }
    }

    private fun loginUser(userId: Long) {
        Log.i("Freelancer", "Successful login! UserId: $userId")
        viewModel.successfulLoginHappened = true
        LoginFragmentDirections.actionLoginFragmentToMainMenuFragment(userId).let { action ->
            findNavController().navigate(action)
        }
    }

    private fun handleError(exception: Exception) {
        Log.i("Freelancer", exception.localizedMessage ?: "Error without message!")
        when (exception.message) {
            LoginViewModel.ERROR_NETWORK -> Snackbar.make(
                this.requireView(), getString(R.string.network_error),
                Snackbar.LENGTH_SHORT
            )
                .setAction(getString(R.string.open_wifi_settings)) {
                    startActivity(Intent(WifiManager.ACTION_PICK_WIFI_NETWORK))
                }.show()
            LoginViewModel.ERROR_SERVER -> Snackbar.make(
                requireView(),
                getString(R.string.server_error),
                Snackbar.LENGTH_SHORT
            ).show()
            LoginViewModel.ERROR_INVALID_FORMAT -> invalidFormatWasGiven()
            LoginViewModel.ERROR_INVALID_DATA -> invalidDataWasGiven()
        }
    }

    private fun clearUIErrors() {
        etEmail.error = null
        etPassword.error = null
    }

    private fun invalidFormatWasGiven() {
        Snackbar.make(
            requireView(),
            getString(R.string.invalid_format_given_error),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun invalidDataWasGiven() {
        textInputLayout2.error = getString(R.string.error_msg_email_invalid)
        textInputLayout3.error = getString(R.string.error_msg_password_invalid)
        Snackbar.make(
            requireView(),
            getString(R.string.invalid_login_data_error),
            Snackbar.LENGTH_SHORT
        ).show()
    }

}