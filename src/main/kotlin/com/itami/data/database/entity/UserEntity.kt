package com.itami.data.database.entity

import com.itami.data.database.table.Users
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserEntity>(Users)

    var googleId by Users.googleId
    var email by Users.email
    var name by Users.name
    var profilePictureUrl by Users.profilePictureUrl
    var age by Users.age
    var weightGrams by Users.weightGrams
    var heightCm by Users.heightCm
    var gender by Users.gender
    var weightGoal by Users.weightGoal
    var lifestyle by Users.lifestyle
    var dailyProteins by Users.dailyProteins
    var dailyFats by Users.dailyFats
    var dailyCarbs by Users.dailyCarbs
    var dailyCalories by Users.dailyCalories
    var createdAt by Users.createdAt
}