package com.getir.gtcalendar.v2.month.model

/**
 * Determines how the height of the day content is calculated.
 */
enum class ContentHeightMode {
    /**
     * The day container will wrap its height. This allows you to
     * use [androidx.compose.foundation.layout.aspectRatio] if you want square day content
     * or [androidx.compose.foundation.layout.height] if you want a specific height value
     * for the day content.
     */
    Wrap,

    /**
     * The days in each month will spread to fill the parent's height after
     * any available header and footer content height has been accounted for.
     * This allows you to use [androidx.compose.foundation.layout.fillMaxHeight] for the day content
     * height. With this option, your Calendar composable should also
     * be created with [androidx.compose.foundation.layout.fillMaxHeight] or [androidx.compose.foundation.layout.height].
     */
    Fill,
    ;
}
