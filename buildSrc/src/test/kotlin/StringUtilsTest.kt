import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@DisplayName("StringUtils Tests")
internal class StringUtilsTest {
    companion object {
        @JvmStatic
        fun getSnapshotVersions() = listOf(
            "1.0.0.snapshot",
            "1.0.0.SNAPSHOT",
            "1.0.0.snapSHOT",
            "1.0.0-SNAPSHOT",
            "1.0-SNAPSHOT-1",
            "1.0.0.SNAPSHOT.1"
        )

        @JvmStatic
        fun getReleaseVersions() = listOf(
            "1.0.0.release",
            "1.0.0.RELEASE",
            "1.0.0.relEASE",
            "1.0.0-RELEASE",
            "1.0-RELEASE-1",
            "1.0.0.RELEASE.1"
        )

        @JvmStatic
        fun getNonTaggedVersions() = listOf(
            "1.0.0",
            "1.0.0-DEV"
        )
    }

    @Test
    fun `Given camel case string, When call toKebabCase function, Then returns kebab case version`() {
        val kebabCaseString = "camelCaseString".toKebabCase()
        assertThat(kebabCaseString).isEqualTo("camel-case-string")
    }

    @Test
    fun `Given kebab case string, When call toCamelCase function, Then returns`() {
        val camelCaseString = "kebab-case-string".toCamelCase()
        assertThat(camelCaseString).isEqualTo("kebabCaseString")
    }

    @ParameterizedTest
    @MethodSource("getSnapshotVersions")
    fun `Given string contains snapshot keyword, when call isSnapshot, Then returns true`(
        value: String
    ) {
        assertThat(value.isSnapshot()).isTrue
    }

    @ParameterizedTest
    @MethodSource("getNonTaggedVersions")
    fun `Given string does not contain snapshot keyword, when call isSnapshot, Then returns true`(
        value: String
    ) {
        assertThat(value.isSnapshot()).isFalse
    }

    @ParameterizedTest
    @MethodSource("getReleaseVersions")
    fun `Given string contains release keyword, when call isRelease, Then returns true`(
        value: String
    ) {
        assertThat(value.isRelease()).isTrue
    }

    @ParameterizedTest
    @MethodSource("getNonTaggedVersions")
    fun `Given string does not contain release keyword, When call isRelease, Then returns false`(
        value: String
    ) {
        assertThat(value.isRelease()).isFalse
    }
}