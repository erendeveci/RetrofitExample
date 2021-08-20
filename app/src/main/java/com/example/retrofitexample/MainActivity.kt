package com.example.retrofitexample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.retrofitexample.model.PostModel
import com.example.retrofitexample.services.PostAPI
import com.example.retrofitexample.ui.theme.RetrofitExampleTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrofitExampleTheme {

                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    var BASE_URL = "https://jsonplaceholder.typicode.com"
    var postModels = remember { mutableStateListOf<PostModel>() }

    var retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PostAPI::class.java)
    var postList = retrofit.getPosts()
    postList.enqueue(object : Callback<List<PostModel>> {
        override fun onResponse(call: Call<List<PostModel>>, response: Response<List<PostModel>>) {

            if (response.isSuccessful) {
                response.body()?.let {
                    postModels.addAll(it)

                }

            }
        }

        override fun onFailure(call: Call<List<PostModel>>, t: Throwable) {
            Log.d("Fail", t.localizedMessage)
        }


    })

    Scaffold() {
        PostList(posts = postModels)
    }

}

@Composable
fun PostList(posts:List<PostModel>) {

    LazyColumn() {
        items(posts) {post->
            PostRow(postModel = post)

        }
    }
}

@Composable
fun PostRow(postModel: PostModel) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.SpaceBetween

        ) {

            Text(modifier = Modifier.fillMaxWidth(0.7f),
                text = postModel.title, style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Red,
                fontFamily = FontFamily.Serif,
                fontStyle = FontStyle.Italic
            )

            Text(
                modifier = Modifier,
                textAlign = TextAlign.End,
                text = postModel.id.toString(),
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
                color = Color.LightGray
            )


        }
        Spacer(modifier = Modifier.padding(5.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = postModel.body, fontWeight = FontWeight.Medium, fontStyle = FontStyle.Italic

            )


        }
        Spacer(modifier = Modifier.padding(2.dp))
        Divider(
            color = Color.DarkGray,
            modifier = Modifier
                .fillMaxWidth()
                .width(1.dp)
        )

    }


}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RetrofitExampleTheme {
        MainScreen()
    }
}