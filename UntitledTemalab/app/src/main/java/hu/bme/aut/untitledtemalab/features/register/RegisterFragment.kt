package hu.bme.aut.untitledtemalab.features.register

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
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.etEmail
import kotlinx.android.synthetic.main.fragment_register.etPassword

/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : Fragment() {

    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRegisterButtonBehaviour()
        handleCargoInputEnabledState()
        observeRegistrationResponse()
    }

    private fun handleCargoInputEnabledState() {
        rgUserRole.setOnCheckedChangeListener { radioGroup, _ ->
            if (radioGroup.checkedRadioButtonId == radioSender.id)
                tilMaxCargoSize.isEnabled = false
            if (radioGroup.checkedRadioButtonId == radioTransporter.id)
                tilMaxCargoSize.isEnabled = true
        }
    }

    private fun setRegisterButtonBehaviour() {
        btnRegister.setOnClickListener {
            viewModel.successfulRegisterHappened = false
            if (rgUserRole.checkedRadioButtonId == radioSender.id || rgUserRole.checkedRadioButtonId == radioTransporter.id) {
                if (validCargoMaxSizeGiven()) {
                    viewModel.attemptToRegisterUser(
                        etEmail.text.toString(),
                        etPassword.text.toString(),
                        when (rgUserRole.checkedRadioButtonId) {
                            radioTransporter.id -> true
                            else -> false
                        },
                        if (tilMaxCargoSize.isEnabled)
                            etMaximumCargo.text.toString().toInt()
                        else
                            0
                    )
                } else {
                    Snackbar.make(
                        requireView(),
                        getString(R.string.error_invalid_cargo_size_registration),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            } else
                Snackbar.make(
                    requireView(),
                    getString(R.string.registration_error_no_role_selected),
                    Snackbar.LENGTH_SHORT
                ).show()
        }
    }

    private fun validCargoMaxSizeGiven(): Boolean {
        try {
            if (etMaximumCargo.text.toString() != "")
                etMaximumCargo.text.toString().toInt()
        } catch (exception: Exception) {
            Snackbar.make(
                requireView(),
                R.string.error_invalid_cargo_size_registration,
                Snackbar.LENGTH_SHORT
            ).show()
            return false
        }
        return !tilMaxCargoSize.isEnabled || (etMaximumCargo.text.toString() != "" && etMaximumCargo.text.toString()
            .toInt() >= 0)
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
    }

    private fun observeRegistrationResponse() {
        viewModel.registrationResponseMessage.observe(viewLifecycleOwner) { response ->
            clearUIErrors()
            when (response) {
                RegisterViewModel.MESSAGE_SUCCESSFUL_REGISTRATION -> {
                    if (!viewModel.successfulRegisterHappened)
                        loginRegisteredUser()
                }
                else -> handleError(IllegalStateException(response))
            }
        }
    }

    private fun loginRegisteredUser() {
        viewModel.successfulRegisterHappened = true
        viewModel.loginRegisteredUser(
            { userId: Long -> onAutoLoginSuccess(userId) },
            { onAutoLoginFailed() })
    }

    private fun onAutoLoginSuccess(userId: Long) {
        Log.i("Freelancer", "Successful login! UserId: $userId")
        RegisterFragmentDirections.actionRegisterFragmentToMainMenuFragment(userId).let {
            findNavController().navigate(it)
        }
    }

    private fun onAutoLoginFailed() {
        Log.i("Freelancer", "After successful registration, the auto-login failed!")
        Snackbar.make(
            requireView(),
            getString(R.string.registration_auto_login_fail_message),
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun handleError(exception: Exception) {
        Log.i("Freelancer", exception.localizedMessage ?: "Exception was thrown without message!")
        when (exception.message) {
            RegisterViewModel.MESSAGE_NETWORK_ERROR -> Snackbar.make(
                this.requireView(), getString(R.string.network_error),
                Snackbar.LENGTH_SHORT
            )
                .setAction(getString(R.string.open_wifi_settings)) {
                    startActivity(Intent(WifiManager.ACTION_PICK_WIFI_NETWORK))
                }.show()
            RegisterViewModel.MESSAGE_SERVER_ERROR -> Snackbar.make(
                requireView(),
                getString(R.string.server_error),
                Snackbar.LENGTH_SHORT
            ).show()
            RegisterViewModel.MESSAGE_INVALID_INPUT -> invalidInputWasGiven()
            RegisterViewModel.MESSAGE_ALREADY_USED_EMAIL -> alreadyUsedEmailWasGiven()
            RegisterViewModel.MESSAGE_INCORRECT_EMAIL_FORMAT -> incorrectEmailFormatWasGiven()
        }
    }

    private fun clearUIErrors() {
        etEmail.error = null
        etPassword.error = null
    }

    private fun invalidInputWasGiven() {
        Snackbar.make(
            requireView(),
            getString(R.string.error_reg_given_data_invalid),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun alreadyUsedEmailWasGiven() {
        textInputLayout4.error = getString(R.string.error_reg_already_used_email)
    }

    private fun incorrectEmailFormatWasGiven() {
        textInputLayout4.error = getString(R.string.error_reg_invaid_email_format)
    }
}