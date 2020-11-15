package hu.bme.aut.untitledtemalab.data

enum class PackageSize {
    Small {
        override fun getBackendValueName(): String {
            return "small"
        }
    },
    Medium {
        override fun getBackendValueName(): String {
            return "medium"
        }
    },
    Large {
        override fun getBackendValueName(): String {
            return "large"
        }
    };

    abstract fun getBackendValueName(): String
}