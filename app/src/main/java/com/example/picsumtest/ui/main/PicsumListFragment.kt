package com.example.picsumtest.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.picsumtest.data.model.Picsum
import kotlinx.coroutines.FlowPreview

class PicsumListFragment : Fragment() {
    val id = "picsum_list_fragment"

    companion object {
        fun newInstance() = PicsumListFragment()
    }

    private val viewModel by lazy { ViewModelProvider(this).get(PiscumListViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                PicsumList(viewModel)
            }
        }
    }
}

@Composable
fun PicsumList(viewModel: PiscumListViewModel) {

    val pagedData = object : PagingSource<Int, Picsum>() {

        override fun getRefreshKey(state: PagingState<Int, Picsum>): Int? {
            return state.anchorPosition?.let {
                state.closestPageToPosition(it)?.prevKey?.plus(1)
                    ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
            }
        }//"viewModel"

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Picsum> {
            val pageNo = params.key ?: 0
            val pageInfo = viewModel.loadPicsumPage(pageNo, 20)
            val prevKey = if (pageNo > 0) pageNo - 1 else null

            // This API defines that it's out of data when a page returns empty. When out of
            // data, we return `null` to signify no more pages should be loaded
            val nextKey = if (pageInfo.isNotEmpty()) pageNo + 1 else null
            return LoadResult.Page(pageInfo, prevKey, nextKey)
        }

    }
    val pager = Pager(PagingConfig(20)) { pagedData }.flow.collectAsLazyPagingItems()
    LazyColumn {
        items(pager) {
            it?.let { PiscumItem(picsum = it) }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun PiscumItem(picsum: Picsum) {
    Row(Modifier.padding(5.dp), verticalAlignment = Alignment.CenterVertically) {
        Column() {
            Surface(Modifier
                .height(250.dp)
                .align(Alignment.CenterHorizontally)
                /*.clip(CircleShape)*/) {
                Image(
                    painter = rememberImagePainter(data = "${picsum.download_url}.jpg"),
                    contentDescription = "Изображение"
                )
            }
            Text(
                text = picsum.author,
                Modifier
                    .padding(15.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Preview
@Composable
fun PicsumSample() {
    PiscumItem(
        Picsum(
            0,
            "Alejandro Escamilla",
            5616,
            3744,
            "drawable/logo.png",
            "https://picsum.photos/..."
        )
    )
}