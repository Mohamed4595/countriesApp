package com.mhmd.countriesapp.framework.presentation.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.mhmd.countriesapp.R
import com.mhmd.countriesapp.framework.presentation.theme.CountriesAppTheme
import com.mhmd.countriesapp.framework.presentation.theme.StarColor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@ExperimentalComposeUiApi
class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                CountriesAppTheme(
                    isNetworkAvailable = true,
                    isNoStatusBar = true
                ) {
                    val scale = remember {
                        androidx.compose.animation.core.Animatable(0f)
                    }
                    // Animation
                    LaunchedEffect(key1 = true) {
                        scale.animateTo(
                            targetValue = 0.6f,
                            // tween Animation
                            animationSpec = tween(
                                durationMillis = 800,
                                easing = {
                                    OvershootInterpolator(5f).getInterpolation(it)
                                }
                            )
                        )
                        // Customize the delay time
                        delay(1000L)

                    }

                    // Image
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,

                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxSize().padding(top = 50.dp, bottom = 20.dp)
                            .background(color = MaterialTheme.colors.background)
                    ) {
                        // Change the logo
                        Card(
                            modifier = Modifier.width(100.dp).height(100.dp),
                            shape = RoundedCornerShape(100),
                            backgroundColor = MaterialTheme.colors.primary,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.mega_logo),
                                contentDescription = "",
                                alignment = Alignment.Center,
                                modifier = Modifier.scale(scale.value).width(60.dp).height(60.dp)
                            )
                        }
                        Column(
                            verticalArrangement = Arrangement.Bottom,
                                    horizontalAlignment = Alignment.CenterHorizontally,

                            ) {
                            Text(
                                text = stringResource(R.string.welcomeToMegaTrust),
                                style = MaterialTheme.typography.h6,
                                color = StarColor,

                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)

                            )

                            Button(
                                onClick = {
                                    findNavController().navigate(
                                        R.id.action_splashFragment_to_countriesListFragment
                                    )
                                },
                                modifier = Modifier.fillMaxWidth(.5f),
                                        shape = RoundedCornerShape(20), // = 50% percent
                                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                            ) {
                                Text(
                                    text = stringResource(R.string.letsStart),
                                    style = MaterialTheme.typography.button,
                                    color = MaterialTheme.colors.onPrimary,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}
