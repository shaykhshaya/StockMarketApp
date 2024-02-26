package com.example.stockmarketapp.presentation.company_listings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stockmarketapp.domain.model.CompanyListing
import com.example.stockmarketapp.presentation.destinations.CompanyInfoScreenDestination
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
@Destination(start = true)
fun CompanyListingsScreen(
    navigator: DestinationsNavigator,
    viewModel: CompanyListingsViewModel = hiltViewModel()
) {

    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewModel.state.isRefreshing
    )
    val state = viewModel.state

    var isFocusEnabled by remember {
        mutableStateOf(false)
    }
    val focusRequester = remember { FocusRequester()}

    LaunchedEffect(isFocusEnabled ){
        if(isFocusEnabled) focusRequester.requestFocus()
    }




    Column(
        modifier = Modifier.fillMaxSize()
    ){

      OutlinedTextField(
          value = state.searchQuery,
          onValueChange = {
              viewModel.onEvent(
                  CompanyListingsEvent.OnSearchQueryChange(it)
              )
          },
          modifier = Modifier
              .padding(16.dp)
              .fillMaxWidth()
              .focusRequester(focusRequester)
              .clickable { isFocusEnabled = true },
          enabled = isFocusEnabled,
          placeholder = {
              Text(text = "Search...")
          },
          maxLines = 1,
          singleLine = true
      )

        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                viewModel.onEvent(CompanyListingsEvent.Refresh)
            }) {
            
            LazyColumn(
                modifier= Modifier.fillMaxSize()
            ){
                items(state.companies.size){ i ->
                    val company = state.companies[i]
                    CompanyItem(
                        company = company,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navigator.navigate(
                                    CompanyInfoScreenDestination(company.symbol)
                                )
                            }
                            .padding(16.dp)
                    )
                    if(i<state.companies.size){
                        Divider(modifier = Modifier.padding(
                            horizontal = 16.dp
                        ))
                    }

                }
            }

        }


    }




}