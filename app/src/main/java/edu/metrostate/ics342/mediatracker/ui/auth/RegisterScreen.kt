package edu.metrostate.ics342.mediatracker.ui.auth
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedSecureTextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SecureTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: RegisterViewModel = viewModel()
) {
    val displayName    by viewModel.displayName.collectAsState()
    val username    by viewModel.username.collectAsState()
    val email       by viewModel.email.collectAsState()
    val password    by viewModel.password.collectAsState()
    val confirmPassword    by viewModel.confirmPassword.collectAsState()
    val passwordState = rememberTextFieldState()
    val confirmPasswordState = rememberTextFieldState()

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imageModifier = Modifier
            .size(60.dp)
            .background(
                MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(15.dp)
        Image(
            painter = painterResource(id = edu.metrostate.ics342.mediatracker.R.drawable.logo),
            contentDescription = "Login Screen Logo",
            modifier = imageModifier,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
        Spacer(Modifier.height(15.dp))

        Text(
            stringResource(edu.metrostate.ics342.mediatracker.R.string.create_account),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        Text(
            stringResource(edu.metrostate.ics342.mediatracker.R.string.create_account_tagline),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )


        Spacer(Modifier.height(40.dp))



        ////first input text Display name
        OutlinedTextField(
            value = displayName,
            onValueChange = viewModel::onDisplayNameChange,
            label = { Text(stringResource(edu.metrostate.ics342.mediatracker.R.string.register_display_name)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value         = username,
            onValueChange = viewModel::onUsernameChange,
            label         = { Text(stringResource(edu.metrostate.ics342.mediatracker.R.string.register_username)) },
            singleLine    = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction    = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value         = email,
            onValueChange = viewModel::onEmailChange,
            label         = { Text(stringResource(edu.metrostate.ics342.mediatracker.R.string.register_email)) },
            singleLine    = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction    = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
        )

        Spacer(Modifier.height(12.dp))

        OutlinedSecureTextField(
            state = passwordState,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {Text("Password")},
            shape = RoundedCornerShape(8.dp),
        )

        Spacer(Modifier.height(12.dp))

        OutlinedSecureTextField(
            state = confirmPasswordState,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {Text("Confirm Password")},
            shape = RoundedCornerShape(8.dp),
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick  = {
                focusManager.clearFocus()
                viewModel.onRegisterClick(
                )},
            enabled  = true,
            modifier = Modifier.fillMaxWidth().height(48.dp)
        )
        { Text(stringResource(edu.metrostate.ics342.mediatracker.R.string.register_prompt_clickable))
        }
    }
}


@Preview
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(
        onRegisterSuccess = {},
        onNavigateToLogin = {}
    )
}