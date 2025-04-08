package org.depromeet.clog.server.domain.common

@JvmInline
value class AppVersion(private val version: String) : Comparable<AppVersion> {

    init {
        require(VERSION_REGEX.matches(version)) { "Invalid version format: $version" }
    }

    companion object {
        private val VERSION_REGEX = Regex("""\d+\.\d+\.\d+""")
        const val APP_VERSION_HEADER = "APP_VERSION"
    }

    private val parts: List<Int>
        get() = version.split(".").map { it.toInt() }

    override fun compareTo(other: AppVersion): Int {
        for (i in 0 until 3) {
            val cmp = parts[i].compareTo(other.parts[i])
            if (cmp != 0) return cmp
        }
        return 0
    }

    override fun toString(): String = version
}
