package com.itami.data.mapper

import com.itami.data.database.entity.WeightEntity
import com.itami.data.dto.response.WeightResponse
import com.itami.data.model.user.Weight
import com.itami.utils.DateTimeUtil

fun WeightEntity.toWeight() = Weight(
    id = this.id.value,
    weightGrams = this.weightGrams,
    userId = this.user.id.value,
    datetime = this.datetime
)

fun Weight.toWeightResponse() = WeightResponse(
    id = this.id,
    weightGrams = this.weightGrams,
    datetime = DateTimeUtil.datetimeToString(this.datetime)
)