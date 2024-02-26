package com.example.stockmarketapp.data.mapper

import com.example.stockmarketapp.data.local.CompanyListingEntity
import com.example.stockmarketapp.data.remote.dto.CompanyInfoDto
import com.example.stockmarketapp.domain.model.CompanyInfo
import com.example.stockmarketapp.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

/**made nullable as if api hit limit gets over then it will send json but with doesnot include these
 * fields*/
fun CompanyInfoDto.toCompanyIfo(): CompanyInfo {
    return CompanyInfo(
        symbol = symbol?:"",
        description = description?:"",
        name = name?:"",
        country = country?:"",
        industry = industry?:""
    )
}