@file:JvmName("BindingUtils")

package dk.quan.plandayhr.util

import androidx.databinding.InverseMethod
import dk.quan.plandayhr.R
import dk.quan.plandayhr.data.models.Gender


@InverseMethod("buttonIdToGender")
fun genderToButtonId(gender: String?): Int {
    var selectedButtonId = -1

    gender.run {
        selectedButtonId = when (this) {
            Gender.MALE.sex -> R.id.radioBtn_male
            Gender.FEMALE.sex -> R.id.radioBtn_female
            else -> -1
        }
    }

    return selectedButtonId
}

fun buttonIdToGender(selectedButtonId: Int): String? {
    return when (selectedButtonId) {
        R.id.radioBtn_male -> Gender.MALE.sex
        R.id.radioBtn_female -> Gender.FEMALE.sex
        else -> null
    }
}