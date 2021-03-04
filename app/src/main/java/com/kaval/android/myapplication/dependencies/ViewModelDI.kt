package com.kaval.android.myapplication.dependencies

import com.kaval.android.myapplication.data.api.ApiHelper
import com.kaval.android.myapplication.data.api.ApiServiceImpl
import com.kaval.android.myapplication.data.repositoriy.StockRepository
import com.kaval.android.myapplication.ui.stocks.StockViewModel
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel

val ViewModelDependency = module {

    viewModel { StockViewModel(StockRepository(ApiHelper(ApiServiceImpl(androidContext()))))}
}