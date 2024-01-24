package com.example.recipeapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.smallTopAppBarColors
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(
    modifier: Modifier = Modifier

){
    val backgroundColor: Color = Color(0xFFFFCDD2)
    val notLoadedColor: Color = Color(0xFFea7c7c)


    val recipeViewModel: MainViewModel = viewModel()
    val viewState by recipeViewModel.categoriesState
    val systemUiController = rememberSystemUiController()
    if(isSystemInDarkTheme()){
        systemUiController.setSystemBarsColor(
            color = backgroundColor
        )
    }else{
        systemUiController.setSystemBarsColor(
            color = backgroundColor
        )
    }

    Column(
        modifier = Modifier
        .background(backgroundColor) // Set the background color here
    ){
        TopAppBar(
            colors = smallTopAppBarColors(containerColor = backgroundColor),
            /*navigationIcon = {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null )
            },*/
            title = {
            Text(
                text = "Categories",
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                fontSize = 30.sp
                )
        }
        )
        Box(
            modifier = Modifier.fillMaxSize(),
        ){
            when{
                viewState.loading ->{
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        color = backgroundColor,
                        trackColor = notLoadedColor,

                        )
                }
                viewState.error != null->{
                    Text(text = "Error Occurred.")
                }
                else->{
                    CategoryScreen(categories = viewState.list)
                }
            }
        }
    }
}

@Composable
fun CategoryScreen(categories: List<Category>){
    LazyVerticalGrid(
        GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
    ){
        items(categories){
            category->
            CategoryItemCard(category = category)
        }
    }
}

@Composable
fun CategoryItem(category: Category){
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = rememberAsyncImagePainter(category.strCategoryThumb),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
        )


        Text(
            text = category.strCategory,
            color = Color.Black,
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 4.dp)
            )
    }
}

@Composable
fun CategoryItemCard(category: Category){
    val cardColor: Color = Color(0xFFea7c7c)
    val backgroundColor: Color = Color(0xFFFFCDD2)

    val gradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFFEF5350), // Start color
            Color(0xFFFFCCBC)  // End color
        )
    )

    ElevatedCard(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp)),
        colors = cardColors(
            containerColor = cardColor
        )
    ){
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(2.dp)
                    .background(backgroundColor, RoundedCornerShape(8.dp)),

            contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(category.strCategoryThumb),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                )
            }



            Text(
                text = category.strCategory,
                color = Color.Black,
                style = TextStyle(fontWeight = FontWeight.Normal),
                fontSize = 15.sp,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )
        }
    }
}