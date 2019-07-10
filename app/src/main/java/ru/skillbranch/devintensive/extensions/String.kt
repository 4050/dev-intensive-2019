package ru.skillbranch.devintensive.extensions

fun String.truncate(length: Int = 16): String {
    return if (this.trimEnd().length <= length) this.trimEnd() else "${this.substring(0, length).trimEnd()}..."
}

fun String.stripHtml(): String {
    return this.stripHtmlTags().stripHtmlEscapeSequences().stripMultipleWhitespaces()
}

fun String.stripHtmlTags(): String {
    var result: String = this
    var processedLength = 0
    do {
        val tagOpenPosition = result.indexOf("<", processedLength)
        val tagClosePosition = if (tagOpenPosition >= 0) result.indexOf(">", tagOpenPosition) else -1
        if (tagOpenPosition >= 0 && tagClosePosition >= 0)
        {
            result = result.removeRange(tagOpenPosition, tagClosePosition + 1)
            processedLength = tagOpenPosition
        }
    } while (tagOpenPosition >= 0 && tagClosePosition >= 0)

    return result
}

fun String.stripHtmlEscapeSequences(): String {
    val regex = Regex("""&amp(;)?|&lt(;)?|&gt(;)?|&quot(;)?|&apos(;)?|&#\d{1,}(;)?""")
    return regex.replace(this, "")
}

fun String.stripMultipleWhitespaces(): String {
    val regex = Regex(""" {1,}""")
    return regex.replace(this, " ")
}
