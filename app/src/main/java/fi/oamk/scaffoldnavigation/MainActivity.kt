package fi.oamk.scaffoldnavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fi.oamk.scaffoldnavigation.ui.theme.ScaffoldNavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScaffoldNavigationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ScaffoldApp()
                }
            }
        }
    }
}

@Composable
fun SubScreenAppBar(title:String, navController: NavController){
    TopAppBar(
        title = {Text(title)},
        navigationIcon = { IconButton(onClick = {navController.navigateUp()}) {
            Icon(Icons.Filled.ArrowBack, contentDescription = null)
        }}
    )
}

@Composable
fun MainAppBar(title: String, navController: NavController){
    var expanded by remember{ mutableStateOf(false) }
    TopAppBar(
        title = {Text(title)},
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Menu, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = { expanded = !expanded }) {
                Icon(Icons.Filled.MoreVert, contentDescription = null)
            }
            DropdownMenu(expanded = expanded,
                onDismissRequest = { expanded = false }) {
                DropdownMenuItem(onClick = { navController.navigate("info") }) {
                    Text(text = "Info")
                }
                DropdownMenuItem(onClick = { navController.navigate("settings") }) {
                    Text(text = "Settings")
                }
            }
        }
    )
}

@Composable
fun HomeScreen(navController: NavController){
    Scaffold(
        topBar = { MainAppBar("My app",navController)},
        content =  {padding -> Text("This is only a test",modifier = Modifier.padding(padding))},
        bottomBar = { BottomAppBar() {
            Text(text = "Bottom bar")
        }}
    )
}
@Composable
fun InfoScreen(navController: NavController){
    Scaffold(
        topBar = {SubScreenAppBar(title = "Info screen", navController = navController)},
        content = {paddingValues -> Text(text = "Info content",modifier = Modifier.padding(paddingValues)) },
        bottomBar = { BottomAppBar() {
            Text(text = "Bottom bar")
        }}
    )
}
@Composable
fun SettingsScreen(navController: NavController){
    Scaffold(
        topBar = {SubScreenAppBar(title = "Settings screen", navController = navController)},
        content = {paddingValues -> Text(text = "Settings content",modifier = Modifier.padding(paddingValues)) },
        bottomBar = { BottomAppBar() {
            Text(text = "Bottom bar")
        }}
    )
}

@Composable
fun ScaffoldApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home"
    ){
        composable(route = "home"){
            HomeScreen(navController = navController)
        }
        composable(route = "info"){
            InfoScreen(navController = navController)
        }
        composable(route="settings"){
            SettingsScreen(navController = navController)
        }
    }
}