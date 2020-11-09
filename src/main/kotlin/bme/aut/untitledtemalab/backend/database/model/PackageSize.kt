package bme.aut.untitledtemalab.backend.database.model

enum class PackageSize(private val value: String) {
    small("small"),
    medium("medium"),
    large("large");

    override fun toString(): String {
        return value
    }

    companion object {
        fun fromValue(text: String): PackageSize? {
            for (b in values()) {
                if (b.value == text) {
                    return b
                }
            }
            return null
        }
    }
}
