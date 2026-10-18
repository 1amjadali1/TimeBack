package com.timeback.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SportsMma
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.timeback.app.data.PreferencesManager
import com.timeback.app.ui.screens.BattleScreen
import com.timeback.app.ui.screens.BlockerSettingsScreen
import com.timeback.app.ui.screens.HomeScreen
import com.timeback.app.ui.screens.OnboardingScreen
import com.timeback.app.ui.screens.PaywallScreen
import com.timeback.app.ui.screens.PermissionsSetupScreen
import com.timeback.app.ui.screens.ProfileScreen
import com.timeback.app.ui.theme.BgMidnight
import com.timeback.app.ui.theme.CreamAccent
import com.timeback.app.ui.theme.InkDark
import com.timeback.app.ui.theme.SurfaceDark
import com.timeback.app.ui.theme.TextMuted
import com.timeback.app.ui.theme.TimeBackTheme

class MainActivity : ComponentActivity() {

    private lateinit var prefsManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefsManager = PreferencesManager(applicationContext)

        setContent {
            TimeBackTheme {
                val isOnboardingCompleted by prefsManager.isOnboardingCompletedFlow.collectAsState(initial = false)
                var permissionsWalkthroughPassed by remember { mutableStateOf(false) }

                when {
                    !isOnboardingCompleted -> {
                        OnboardingScreen(
                            prefsManager = prefsManager,
                            onFinishOnboarding = {
                                permissionsWalkthroughPassed = false
                            }
                        )
                    }
                    !permissionsWalkthroughPassed -> {
                        PermissionsSetupScreen(
                            context = this@MainActivity,
                            onAllPermissionsSet = {
                                permissionsWalkthroughPassed = true
                            }
                        )
                    }
                    else -> {
                        MainAppNavigation(prefsManager = prefsManager, context = this@MainActivity)
                    }
                }
            }
        }
    }
}

sealed class Screen(val title: String, val icon: ImageVector) {
    object Home : Screen("Home", Icons.Default.Home)
    object Blocker : Screen("Limits", Icons.Default.Block)
    object Battle : Screen("Battle", Icons.Default.SportsMma)
    object Plus : Screen("Plus", Icons.Default.Star)
    object Profile : Screen("Account", Icons.Default.Person)
}

@Composable
fun MainAppNavigation(prefsManager: PreferencesManager, context: MainActivity) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val items = listOf(Screen.Home, Screen.Blocker, Screen.Battle, Screen.Plus, Screen.Profile)

    Scaffold(
        containerColor = BgMidnight,
        bottomBar = {
            NavigationBar(containerColor = SurfaceDark) {
                items.forEachIndexed { index, screen ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = InkDark,
                            selectedTextColor = CreamAccent,
                            indicatorColor = CreamAccent,
                            unselectedIconColor = TextMuted,
                            unselectedTextColor = TextMuted
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Modifier.padding(innerPadding).let {
            when (selectedTab) {
                0 -> HomeScreen(context = context, prefsManager = prefsManager)
                1 -> BlockerSettingsScreen(prefsManager = prefsManager)
                2 -> BattleScreen(context = context, prefsManager = prefsManager)
                3 -> PaywallScreen()
                4 -> ProfileScreen(
                    context = context,
                    prefsManager = prefsManager,
                    onNavigateToPaywall = { selectedTab = 3 }
                )
            }
        }
    }
}
