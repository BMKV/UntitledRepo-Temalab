package hu.bme.aut.untitledtemalab.data

import com.google.gson.annotations.SerializedName

enum class PackageSize {
    @SerializedName("small")
    Small {
        override fun getBackendValueName(): String {
            return "small"
        }
    },

    @SerializedName("medium")
    Medium {
        override fun getBackendValueName(): String {
            return "medium"
        }
    },

    @SerializedName("large")
    Large {
        override fun getBackendValueName(): String {
            return "large"
        }
    };

    abstract fun getBackendValueName(): String
}