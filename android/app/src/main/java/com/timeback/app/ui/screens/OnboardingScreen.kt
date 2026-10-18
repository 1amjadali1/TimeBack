package com.timeback.app.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.timeback.app.data.PreferencesManager
import com.timeback.app.ui.components.MascotBrainView
import com.timeback.app.ui.theme.BgMidnight
import com.timeback.app.ui.theme.CreamAccent
import com.timeback.app.ui.theme.InkDark
import com.timeback.app.ui.theme.LineBorder
import com.timeback.app.ui.theme.SurfaceDark
import com.timeback.app.ui.theme.TextMuted
import com.timeback.app.ui.theme.TextPrimary
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    prefsManager: PreferencesManager,
    onFinishOnboarding: () -> Unit
) {
    var step by remember { mutableIntStateOf(1) }
    val scope = rememberCoroutineScope()

    // Question 1: What do you do? (Single)
    var selectedRole by remember { mutableStateOf("Working") }

    // Question 2: Where do you scroll the most? (Multi)
    val selectedPlatforms = remember { mutableStateListOf("Instagram Reels", "YouTube Shorts") }

    // Question 3: What do you want that time back for? (Multi)
    val selectedReasons = remember { mutableStateListOf("Work", "Peace of mind") }

    // Question 4: After a long scroll, how do you feel? (Multi)
    val selectedFeelings = remember { mutableStateListOf("Guilty", "Can't focus") }

    // Question 5: When do you scroll the most? (Single)
    var selectedScrollTime by remember { mutableStateOf("Late at night") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgMidnight)
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            // Step counter indicator
            Text(
                text = "$step of 5",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextMuted,
                modifier = Modifier.padding(top = 12.dp, bottom = 24.dp)
            )

            // Dynamic Question View with Speech Bubble Mascot
            AnimatedContent(
                targetState = step,
                transitionSpec = {
                    (slideInHorizontally { it } + fadeIn()).togetherWith(slideOutHorizontally { -it } + fadeOut())
                },
                label = "OnboardingStep"
            ) { currentStep ->
                when (currentStep) {
                    1 -> OnboardingQuestionSingle(
                        question = "What do you do?",
                        options = listOf("School student", "College student", "Working", "Other"),
                        selectedOption = selectedRole,
                        onOptionSelected = { selectedRole = it }
                    )
                    2 -> OnboardingQuestionMulti(
                        question = "Where do you scroll the most?",
                        subtext = "Pick all that apply",
                        options = listOf("Instagram Reels", "YouTube Shorts", "Snapchat", "Facebook", "Moj / Josh / ShareChat"),
                        selectedOptions = selectedPlatforms
                    )
                    3 -> OnboardingQuestionMulti(
                        question = "What do you want that time back for?",
                        subtext = "Pick all that apply",
                        options = listOf("Studies", "Work", "Sleep", "Fitness", "Family", "Peace of mind"),
                        selectedOptions = selectedReasons
                    )
                    4 -> OnboardingQuestionMulti(
                        question = "After a long scroll, how do you feel?",
                        subtext = "Pick all that apply",
                        options = listOf("Guilty", "Tired", "Can't focus", "I sleep late"),
                        selectedOptions = selectedFeelings
                    )
                    5 -> OnboardingQuestionSingle(
                        question = "When do you scroll the most?",
                        options = listOf("Morning, in bed", "During study or work", "After dinner", "Late at night"),
                        selectedOption = selectedScrollTime,
                        onOptionSelected = { selectedScrollTime = it }
                    )
                }
            }
        }

        // Bottom Continue Button
        Button(
            onClick = {
                if (step < 5) {
                    step++
                } else {
                    scope.launch {
                        prefsManager.saveOnboardingAnswers(
                            role = selectedRole,
                            platforms = selectedPlatforms.toSet(),
                            timeReasons = selectedReasons.toSet(),
                            feelings = selectedFeelings.toSet(),
                            scrollTime = selectedScrollTime
                        )
                        onFinishOnboarding()
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CreamAccent),
            shape = RoundedCornerShape(999.dp)
        ) {
            Text(
                text = "Continue",
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                color = InkDark
            )
        }
    }
}

@Composable
fun OnboardingQuestionSingle(
    question: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Column {
        SpeechBubbleHeader(question = question)
        Spacer(modifier = Modifier.height(28.dp))

        options.forEach { option ->
            val isSelected = option == selectedOption
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceDark)
                    .border(1.dp, if (isSelected) CreamAccent else LineBorder, RoundedCornerShape(16.dp))
                    .clickable { onOptionSelected(option) }
                    .padding(horizontal = 16.dp, vertical = 18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = isSelected,
                    onClick = { onOptionSelected(option) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = CreamAccent,
                        unselectedColor = TextMuted
                    )
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = option,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }
        }
    }
}

@Composable
fun OnboardingQuestionMulti(
    question: String,
    subtext: String,
    options: List<String>,
    selectedOptions: MutableList<String>
) {
    Column {
        SpeechBubbleHeader(question = question)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = subtext,
            fontSize = 13.sp,
            color = TextMuted,
            modifier = Modifier.padding(start = 64.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        options.forEach { option ->
            val isSelected = selectedOptions.contains(option)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceDark)
                    .border(1.dp, if (isSelected) CreamAccent else LineBorder, RoundedCornerShape(16.dp))
                    .clickable {
                        if (isSelected) selectedOptions.remove(option) else selectedOptions.add(option)
                    }
                    .padding(horizontal = 16.dp, vertical = 18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CreamAccent else Color.Transparent)
                        .border(2.dp, if (isSelected) CreamAccent else TextMuted, RoundedCornerShape(6.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    if (isSelected) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = InkDark,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = option,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }
        }
    }
}

@Composable
fun SpeechBubbleHeader(question: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Cute Mascot Face
        MascotBrainView(count = 0, modifier = Modifier.size(52.dp))

        Spacer(modifier = Modifier.width(12.dp))

        // White Speech Bubble with Tail
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
                .padding(horizontal = 18.dp, vertical = 14.dp)
        ) {
            Text(
                text = question,
                color = InkDark,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 15.sp
            )
        }
    }
}
