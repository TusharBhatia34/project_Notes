package com.example.project2_notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.project2_notesapp.ui.theme.Project2_NotesAppTheme

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            Database::class.java,
            "Names.db"
        ).build()
    }

    private val viewModel by viewModels<ViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory{
                override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                    return ViewModel(db.dao) as T
                }
            }
        }
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Project2_NotesAppTheme {
                val state by viewModel.state.collectAsState()
                val navController = rememberNavController()
                NavGraph(navController = navController,state,viewModel::onEvent )

            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Project2_NotesAppTheme {

    }
}