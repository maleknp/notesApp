package com.example.dbproject

import android.app.LauncherActivity
import android.graphics.Paint.Align
import android.graphics.drawable.Icon
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dbproject.destinations.FirstScreenDestination
import com.example.dbproject.destinations.SecondScreenDestination
import com.example.dbproject.destinations.searchNoteDestination
import com.example.dbproject.destinations.showNoteDestination
import com.example.dbproject.model.Contact
import com.example.dbproject.ui.AddContactViewModel
import com.example.dbproject.ui.ContactsListViewModel
import com.example.dbproject.ui.theme.DbProjectTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.selects.select


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DbProjectTheme {

                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF252525))) {
                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
                // A surface container using the 'background' color from the theme
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
@Destination(start = true)
fun FirstScreen(nav: DestinationsNavigator,contactsListViewModel: ContactsListViewModel= hiltViewModel(),
                addContactViewModel: AddContactViewModel= hiltViewModel()) {

    val colorList: List<Color> = listOf(Color(0xFFFD99FF), Color(0xFFFF9E9E), Color(0xFF91F48F), Color(0xFFFFF599), Color(0xFF9EFFFF), Color(0xFFB69CFF))
    val openDialog = remember { mutableStateOf(false)  }
    var selectedItem by remember{mutableStateOf(-1)}

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column() {
            Text(
                text = "Notes",
                fontSize = 43.sp,
                fontWeight = FontWeight.SemiBold,
                color = androidx.compose.ui.graphics.Color.White,
            )
        }
        Column() {
            Row(modifier = Modifier
                .padding(0.dp,10.dp,0.dp,0.dp),) {

            Column() {
                Button(
                    onClick = { nav.navigate(searchNoteDestination) },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF3B3B3B),
                        contentColor = androidx.compose.ui.graphics.Color.White
                    ),
                    modifier = Modifier
                        .align(Alignment.End)
                        .width(48.dp)
                        .height(48.dp),
                    shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp),
                    contentPadding = PaddingValues(0.dp,10.dp,0.dp,10.dp)
                ) {
                    Icon(Icons.Default.Search, contentDescription = "Searh")
                }
            }
                Spacer(modifier = Modifier.size(10.dp,10.dp))
            Column() {
                Button(
                    onClick = { openDialog.value = true },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF3B3B3B),
                        contentColor = androidx.compose.ui.graphics.Color.White
                    ),
                    modifier = Modifier
                        .align(Alignment.End)
                        .width(48.dp)
                        .height(48.dp),
                    shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp),
                    contentPadding = PaddingValues(10.dp)
                ) {
                    Icon(Icons.Default.Info, contentDescription = "Information")
                }
            }
        }
    }
    }


    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,) {

        Box(modifier = Modifier.fillMaxSize()) {
        if (contactsListViewModel.contacts.count() > 0){
            LazyColumn(
                modifier = Modifier.padding(0.dp,70.dp,0.dp,0.dp)
            ) {

                items(contactsListViewModel.contacts) { contact ->
                    Row(modifier = Modifier.fillMaxSize()) {
                            Card(modifier = Modifier
                                .fillMaxSize()
                                .selectable(
                                    selected = contact.id == selectedItem,
                                    onClick = { nav.navigate(showNoteDestination(contact))
                                    })
                                .padding(5.dp),
                                backgroundColor = colorList.random(),shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp)) {
                                Column(modifier = Modifier.padding(20.dp)) {
                                    Row() {
                                        Text(text = contact.name ?: "Nothing", fontSize = 25.sp)
                                    }

                                }
                            }

                    }
                }
            }
        }else{
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(R.drawable.rafiki),
                    contentDescription = null
                )
                Text(
                    text = "Create your first note !",
                    fontSize = 20.sp,
                    color = androidx.compose.ui.graphics.Color.White,
                )
            }
        }


        Button(
            onClick = { nav.navigate(SecondScreenDestination) },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF252525), contentColor = androidx.compose.ui.graphics.Color.White),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .shadow(5.dp, RoundedCornerShape(46.dp)),
            shape = RoundedCornerShape(46.dp, 46.dp, 46.dp, 46.dp),
            contentPadding = PaddingValues(20.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Note")
        }
    }
    }

    if (openDialog.value) {
        AlertDialog(
            shape = RoundedCornerShape(20.dp),
            backgroundColor = Color(0xFF252525),
            onDismissRequest = {
                openDialog.value = false
            },

            title = {
                Text(
                    text = "Designed by - Malik Alabouli\n" +
                            "Redesigned by - \n" +
                            "Illustrations - \n" +
                            "Icons - \n" +
                            "Font -\n",
                    fontSize = 15.sp,
                    color = Color(0xFFCFCFCF),
                )
            },
            text = {
                Text(text = "Made By Malik Alnabouli", fontSize = 15.sp, color = Color(0xFFCFCFCF), textAlign = TextAlign.Center)
            },
            buttons = {

            }
        )
    }


}


@Composable
@Destination
fun SecondScreen(nav: DestinationsNavigator, addContactViewModel: AddContactViewModel= hiltViewModel()) {


    val isVisible = remember { mutableStateOf(false) }
    var title by rememberSaveable { mutableStateOf("") }
    var desc by rememberSaveable { mutableStateOf("") }
    val openDialog = remember { mutableStateOf(false)  }
    val openDialog2 = remember { mutableStateOf(false)  }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column() {
                Button(
                    onClick = { if(title != ""){openDialog.value = true}else{nav.navigate(FirstScreenDestination)} },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF3B3B3B),
                        contentColor = androidx.compose.ui.graphics.Color.White
                    ),
                    modifier = Modifier
                        .align(Alignment.End)
                        .width(48.dp)
                        .height(48.dp),
                    shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp),
                    contentPadding = PaddingValues(0.dp, 10.dp, 0.dp, 10.dp)
                ) {
//                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    Icon(
                        painter = painterResource(R.drawable.vector__2_),
                        contentDescription = "Back"
                    )
                }
            }
            Column() {
                Row() {

                    Column() {
                        Button(
                            onClick = {
                                if (title != "") {
                                    isVisible.value = !isVisible.value
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFF3B3B3B),
                                contentColor = androidx.compose.ui.graphics.Color.White
                            ),
                            modifier = Modifier
                                .align(Alignment.End)
                                .width(48.dp)
                                .height(48.dp),
                            shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp),
                            contentPadding = PaddingValues(0.dp, 10.dp, 0.dp, 10.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.visibility),
                                contentDescription = "View"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.size(10.dp, 10.dp))
                    Column() {
                        Button(
                            onClick = {
                                if (title != "") {
                                    openDialog2.value = true
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFF3B3B3B),
                                contentColor = androidx.compose.ui.graphics.Color.White
                            ),
                            modifier = Modifier
                                .align(Alignment.End)
                                .width(48.dp)
                                .height(48.dp),
                            shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp),
                            contentPadding = PaddingValues(10.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.vector__1_),
                                contentDescription = "Save"
                            )
                        }
                    }
                }
            }
        }


        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp, 5.dp, 5.dp, 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {

            if (isVisible.value && title != "") {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)) {
                        Text(
                            text = title, fontSize = 48.sp,
                            color = androidx.compose.ui.graphics.Color.White,
                        )
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)) {
                        Text(
                            text = desc,
                            fontSize = 23.sp,
                            color = androidx.compose.ui.graphics.Color.White,
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = title, onValueChange = { title = it },
                            textStyle = TextStyle.Default.copy(
                                fontSize = 48.sp,
                                color = androidx.compose.ui.graphics.Color.White
                            ),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color(0xFF252525),
                                unfocusedBorderColor = Color(0xFF252525)
                            ),
                            placeholder = {
                                Text(text = "Title", fontSize = 48.sp, color = Color(0xFF9A9A9A))
                            },
//                            singleLine = true,
                        )
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            modifier = Modifier.background(Color(0xFF252525)),
                            value = desc, onValueChange = { desc = it },
                            textStyle = TextStyle.Default.copy(
                                fontSize = 23.sp,
                                color = androidx.compose.ui.graphics.Color.White
                            ),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color(0xFF252525),
                                unfocusedBorderColor = Color(0xFF252525)
                            ),
                            placeholder = {
                                Text(
                                    text = "Type something...",
                                    fontSize = 23.sp,
                                    color = Color(0xFF9A9A9A)
                                )
                            },
                        )
                    }
                }
            }
        }
    }


        if (openDialog.value) {
            AlertDialog(
                shape = RoundedCornerShape(20.dp),
                backgroundColor = Color(0xFF252525),
                onDismissRequest = {
                    openDialog.value = false
                },

                title = {
                    Column(modifier = Modifier
                        .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Info, contentDescription = "Info",modifier = Modifier.size(50.dp),tint = Color(0xFF606060))
                    }
                },
                text = {
                    Text(text = "Are your sure you want discard your changes ?", fontSize = 23.sp, color = Color(0xFFCFCFCF), textAlign = TextAlign.Center)
                },

                buttons = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp, 0.dp, 8.dp, 8.dp)
                    ) {

                        Column(modifier = Modifier.padding(20.dp,0.dp,20.dp,20.dp)) {
                        Button(
                            onClick = {
                                openDialog.value = false
                                nav.navigate(FirstScreenDestination)
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF0000))
                        ) {
                            Text(
                                "Discard",
                                fontSize = 18.sp,
                                color = androidx.compose.ui.graphics.Color.White
                            )
                        }
                        }

                        Column(modifier = Modifier.padding(20.dp,0.dp,20.dp,20.dp)) {
                            Button(
                                onClick = {
                                    openDialog.value = false
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF30BE71))
                            ) {
                                Text(
                                    "Keep",
                                    fontSize = 18.sp,
                                    color = androidx.compose.ui.graphics.Color.White
                                )
                            }
                        }

                    }
                }
            )
        }

    if (openDialog2.value) {
        AlertDialog(
            shape = RoundedCornerShape(20.dp),
            backgroundColor = Color(0xFF252525),
            onDismissRequest = {
                openDialog2.value = false
            },

            title = {
                Column(modifier = Modifier
                    .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Info, contentDescription = "Info",modifier = Modifier.size(50.dp),tint = Color(0xFF606060))
                }
            },
            text = {
                Text(text = "Save changes ?", fontSize = 23.sp, color = Color(0xFFCFCFCF), textAlign = TextAlign.Center)
            },

            buttons = {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp, 0.dp, 8.dp, 8.dp)
                ) {

                    Column(modifier = Modifier.padding(20.dp,0.dp,20.dp,20.dp)) {
                        Button(
                            onClick = {
                                openDialog2.value = false
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF0000))
                        ) {
                            Text(
                                "Discard",
                                fontSize = 18.sp,
                                color = androidx.compose.ui.graphics.Color.White
                            )
                        }
                    }

                    Column(modifier = Modifier.padding(20.dp,0.dp,20.dp,20.dp)) {
                        Button(
                            onClick = {
                                if (title != "") {
                                    nav.navigate(FirstScreenDestination) {
                                        addContactViewModel.addContact(
                                            Contact(
                                                name = title,
                                                phone = desc
                                            )
                                        )
                                    }
                                }
                                openDialog2.value = false
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF30BE71))
                        ) {
                            Text(
                                "Save",
                                fontSize = 18.sp,
                                color = androidx.compose.ui.graphics.Color.White
                            )
                        }
                    }

                }
            }
        )
    }

}


    @Composable
    @Destination
    fun showNote(nav: DestinationsNavigator, addContactViewModel: AddContactViewModel= hiltViewModel(),contact: Contact) {

    val isVisible = remember { mutableStateOf(true) }
    var title by rememberSaveable { mutableStateOf(contact.name) }
    var desc by rememberSaveable { mutableStateOf(contact.phone) }
    val openDialog = remember { mutableStateOf(false)  }
        val openDialog2 = remember { mutableStateOf(false)  }
        val editNote = remember { mutableStateOf(true)  }
        val openDialog3 = remember { mutableStateOf(false)  }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            if (editNote.value && isVisible.value) {
                Column() {
                    Button(
                        onClick = {
                                nav.navigate(FirstScreenDestination)
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF3B3B3B),
                            contentColor = androidx.compose.ui.graphics.Color.White
                        ),
                        modifier = Modifier
                            .align(Alignment.End)
                            .width(48.dp)
                            .height(48.dp),
                        shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp),
                        contentPadding = PaddingValues(0.dp, 10.dp, 0.dp, 10.dp)
                    ) {
//                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        Icon(
                            painter = painterResource(R.drawable.vector__2_),
                            contentDescription = "Back"
                        )
                    }
                }
                Column() {
                    Row(modifier = Modifier
                        .padding(0.dp,10.dp,0.dp,0.dp),) {
                        Column() {
                            Button(
                                onClick = {
                                    openDialog3.value = true
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(0xFF3B3B3B),
                                    contentColor = androidx.compose.ui.graphics.Color.White
                                ),
                                modifier = Modifier
                                    .align(Alignment.End)
                                    .width(48.dp)
                                    .height(48.dp),
                                shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp),
                                contentPadding = PaddingValues(0.dp, 10.dp, 0.dp, 10.dp)
                            ) {
//                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    modifier = Modifier.size(50.dp),
                                    tint = Color(0xFFFFFFFF)
                                )

                            }
                        }
                        Spacer(modifier = Modifier.size(10.dp, 10.dp))
                        Column() {
                            Button(
                                onClick = {
                                    editNote.value = false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(0xFF3B3B3B),
                                    contentColor = androidx.compose.ui.graphics.Color.White
                                ),
                                modifier = Modifier
                                    .align(Alignment.End)
                                    .width(48.dp)
                                    .height(48.dp),
                                shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp),
                                contentPadding = PaddingValues(0.dp, 10.dp, 0.dp, 10.dp)
                            ) {
//                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                                Icon(
                                    painter = painterResource(R.drawable.vector__3_),
                                    contentDescription = "Edit"
                                )
                            }
                        }
                    }

                }
            } else {
            Column() {
                Button(
                    onClick = {
                        if (title != "") {
                            openDialog.value = true
                        } else {
                            nav.navigate(FirstScreenDestination)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF3B3B3B),
                        contentColor = androidx.compose.ui.graphics.Color.White
                    ),
                    modifier = Modifier
                        .align(Alignment.End)
                        .width(48.dp)
                        .height(48.dp),
                    shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp),
                    contentPadding = PaddingValues(0.dp, 10.dp, 0.dp, 10.dp)
                ) {
//                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    Icon(
                        painter = painterResource(R.drawable.vector__2_),
                        contentDescription = "Back"
                    )
                }
            }
            Column() {
                Row() {

                    Spacer(modifier = Modifier.size(10.dp, 10.dp))
                    Column() {
                        Button(
                            onClick = {
                                if (title != "") {
                                    openDialog2.value = true
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFF3B3B3B),
                                contentColor = androidx.compose.ui.graphics.Color.White
                            ),
                            modifier = Modifier
                                .align(Alignment.End)
                                .width(48.dp)
                                .height(48.dp),
                            shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp),
                            contentPadding = PaddingValues(10.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.vector__1_),
                                contentDescription = "Save"
                            )
                        }
                    }
                }
            }
        }
        }


        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp, 5.dp, 5.dp, 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {

            if (isVisible.value && editNote.value) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)) {
                        Text(
                            text = title, fontSize = 48.sp,
                            color = androidx.compose.ui.graphics.Color.White,
                        )
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)) {
                        Text(
                            text = desc,
                            fontSize = 23.sp,
                            color = androidx.compose.ui.graphics.Color.White,
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = title, onValueChange = { title = it },
                            textStyle = TextStyle.Default.copy(
                                fontSize = 48.sp,
                                color = androidx.compose.ui.graphics.Color.White
                            ),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color(0xFF252525),
                                unfocusedBorderColor = Color(0xFF252525)
                            ),
                            placeholder = {
                                Text(text = "Title", fontSize = 48.sp, color = Color(0xFF9A9A9A))
                            },
//                            singleLine = true,
                        )
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            modifier = Modifier.background(Color(0xFF252525)),
                            value = desc, onValueChange = { desc = it },
                            textStyle = TextStyle.Default.copy(
                                fontSize = 23.sp,
                                color = androidx.compose.ui.graphics.Color.White
                            ),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color(0xFF252525),
                                unfocusedBorderColor = Color(0xFF252525)
                            ),
                            placeholder = {
                                Text(
                                    text = "Type something...",
                                    fontSize = 23.sp,
                                    color = Color(0xFF9A9A9A)
                                )
                            },
                        )
                    }
                }
            }
        }
    }


        if (openDialog3.value) {
            AlertDialog(
                shape = RoundedCornerShape(20.dp),
                backgroundColor = Color(0xFF252525),
                onDismissRequest = {
                    openDialog3.value = false
                },

                title = {
                    Column(modifier = Modifier
                        .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete",modifier = Modifier.size(50.dp),tint = Color(0xFF606060))
                    }
                },
                text = {
                    Text(text = "Are your sure you want Delete Note ?", fontSize = 23.sp, color = Color(0xFFCFCFCF), textAlign = TextAlign.Center)
                },

                buttons = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp, 0.dp, 8.dp, 8.dp)
                    ) {

                        Column(modifier = Modifier.padding(20.dp,0.dp,20.dp,20.dp)) {
                            Button(
                                onClick = {
                                    openDialog3.value = false
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF30BE71))
                            ) {
                                Text(
                                    "Discard",
                                    fontSize = 18.sp,
                                    color = androidx.compose.ui.graphics.Color.White
                                )
                            }
                        }

                        Column(modifier = Modifier.padding(20.dp,0.dp,20.dp,20.dp)) {
                            Button(
                                onClick = {
                                    openDialog3.value = false

                                    nav.navigate(FirstScreenDestination) {
                                        addContactViewModel.deleteContact(
                                            Contact(
                                                id = contact.id,
                                                name = title,
                                                phone = desc
                                            )
                                        )
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color( 0xFFFF0000))
                            ) {
                                Text(
                                    "Delete",
                                    fontSize = 18.sp,
                                    color = androidx.compose.ui.graphics.Color.White
                                )
                            }
                        }

                    }
                }
            )
        }

    if (openDialog.value) {
        AlertDialog(
            shape = RoundedCornerShape(20.dp),
            backgroundColor = Color(0xFF252525),
            onDismissRequest = {
                openDialog.value = false
            },

            title = {
                Column(modifier = Modifier
                    .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Info, contentDescription = "Info",modifier = Modifier.size(50.dp),tint = Color(0xFF606060))
                }
            },
            text = {
                Text(text = "Are your sure you want discard your changes ?", fontSize = 23.sp, color = Color(0xFFCFCFCF), textAlign = TextAlign.Center)
            },

            buttons = {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp, 0.dp, 8.dp, 8.dp)
                ) {

                    Column(modifier = Modifier.padding(20.dp,0.dp,20.dp,20.dp)) {
                        Button(
                            onClick = {
                                openDialog.value = false
                                nav.navigate(showNoteDestination(contact))
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF0000))
                        ) {
                            Text(
                                "Discard",
                                fontSize = 18.sp,
                                color = androidx.compose.ui.graphics.Color.White
                            )
                        }
                    }

                    Column(modifier = Modifier.padding(20.dp,0.dp,20.dp,20.dp)) {
                        Button(
                            onClick = {
                                openDialog.value = false
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF30BE71))
                        ) {
                            Text(
                                "Keep",
                                fontSize = 18.sp,
                                color = androidx.compose.ui.graphics.Color.White
                            )
                        }
                    }

                }
            }
        )
    }

    if (openDialog2.value) {
        AlertDialog(
            shape = RoundedCornerShape(20.dp),
            backgroundColor = Color(0xFF252525),
            onDismissRequest = {
                openDialog2.value = false
            },

            title = {
                Column(modifier = Modifier
                    .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Info, contentDescription = "Info",modifier = Modifier.size(50.dp),tint = Color(0xFF606060))
                }
            },
            text = {
                Text(text = "Save changes ?", fontSize = 23.sp, color = Color(0xFFCFCFCF), textAlign = TextAlign.Center)
            },

            buttons = {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp, 0.dp, 8.dp, 8.dp)
                ) {

                    Column(modifier = Modifier.padding(20.dp,0.dp,20.dp,20.dp)) {
                        Button(
                            onClick = {
                                openDialog2.value = false
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF0000))
                        ) {
                            Text(
                                "Discard",
                                fontSize = 18.sp,
                                color = androidx.compose.ui.graphics.Color.White
                            )
                        }
                    }

                    Column(modifier = Modifier.padding(20.dp,0.dp,20.dp,20.dp)) {
                        Button(
                            onClick = {
                                if (title != "") {
                                        addContactViewModel.editContact(
                                            Contact(
                                                id = contact.id,
                                                name = title,
                                                phone = desc
                                            )
                                        )
                                    editNote.value = true
                                }
                                openDialog2.value = false
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF30BE71))
                        ) {
                            Text(
                                "Save",
                                fontSize = 18.sp,
                                color = androidx.compose.ui.graphics.Color.White
                            )
                        }
                    }

                }
            }
        )
    }
}



@Composable
@Destination
fun searchNote(nav: DestinationsNavigator, contactsListViewModel: ContactsListViewModel = hiltViewModel()) {

    val isVisible = remember { mutableStateOf(true) }
    var search by rememberSaveable { mutableStateOf("") }
    var title by rememberSaveable { mutableStateOf("") }
    var desc by rememberSaveable { mutableStateOf("") }
    val openDialog = remember { mutableStateOf(false)  }
    val openDialog2 = remember { mutableStateOf(false)  }
    val editNote = remember { mutableStateOf(true)  }
    val colorList: List<Color> = listOf(Color(0xFFFD99FF), Color(0xFFFF9E9E), Color(0xFF91F48F), Color(0xFFFFF599), Color(0xFF9EFFFF), Color(0xFFB69CFF))

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp,50.dp,10.dp,0.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = search, onValueChange = { search = it },
                textStyle = TextStyle.Default.copy(
                    fontSize = 20.sp,
                    color = Color(0xFFCCCCCC)
                ),
                shape = RoundedCornerShape(30.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF3B3B3B),
                    unfocusedBorderColor = Color(0xFF3B3B3B),
                    backgroundColor = Color(0xFF3B3B3B)
                ),

                trailingIcon = {
                    IconButton(
                        onClick = {
                            nav.navigate(FirstScreenDestination)
                        },
                    ) {
                        Icon(
                            Icons.Default.Close, contentDescription = "Person Icon", tint = Color(0xFFCCCCCC)
                        )
                    }
                               },

                placeholder = {
                    Text(text = "Search by the keyword...", fontSize = 20.sp, color = Color(0xFF9A9A9A))
                },
                singleLine = true,
            )
        }



        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,) {
            var selectedItem by remember{mutableStateOf(-1)}

            Box(modifier = Modifier.fillMaxSize()) {
                if (search != "" && contactsListViewModel.contacts.filter { it.name.contains(search, ignoreCase = true) }.count() > 0){
                    LazyColumn(
                    ) {

                        items(contactsListViewModel.contacts.filter { it.name.contains(search, ignoreCase = true) }) { contact ->
                            Row(modifier = Modifier.fillMaxSize()) {
                                Card(modifier = Modifier
                                    .fillMaxSize()
                                    .selectable(
                                        selected = contact.id == selectedItem,
                                        onClick = { nav.navigate(showNoteDestination(contact)) })
                                    .padding(5.dp),
                                    backgroundColor = colorList.random(),shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp)) {
                                    Column(modifier = Modifier.padding(20.dp)) {
                                        Row() {
                                            Text(text = contact.name ?: "Nothing", fontSize = 25.sp)
                                        }

                                    }
                                }
                            }
                        }
                    }
                }else if(search != ""){
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            modifier = Modifier.fillMaxWidth(),
                            painter = painterResource(R.drawable.cuate),
                            contentDescription = null
                        )
                        Text(
                            text = "File not found. Try searching again.",
                            fontSize = 20.sp,
                            color = androidx.compose.ui.graphics.Color.White,
                        )
                    }
                }

            }
        }




    }
}



@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DbProjectTheme {
        Greeting("Android")
    }
}