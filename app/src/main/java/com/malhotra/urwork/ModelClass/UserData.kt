package com.malhotra.urwork.ModelClass

data class UserData(var id : String,
                    val name : String,
                    val email : String,
                    var phone : String?,
                    var image : String? = null)