package com.ivieleague.kotlin.anko

import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.Gravity
import android.view.ViewManager
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import org.jetbrains.anko.*

/**
 *
 * Created by jivie on 2/15/16.
 *
 */

inline fun TextView.materialStyleTertiary(dark: Boolean) {
    textColorResource = if (dark) android.R.color.tertiary_text_dark else android.R.color.tertiary_text_light
    ellipsize = TextUtils.TruncateAt.END
}

inline fun TextView.materialStyleSecondary(dark: Boolean) {
    textColorResource = if (dark) android.R.color.secondary_text_dark else android.R.color.secondary_text_light
    ellipsize = TextUtils.TruncateAt.END
}

inline fun TextView.materialStylePrimary(dark: Boolean) {
    textColorResource = if (dark) android.R.color.primary_text_dark else android.R.color.primary_text_light
    ellipsize = TextUtils.TruncateAt.END
}

inline fun ImageButton.materialStyleAction() {
    leftPadding = dip(16)
    backgroundResource = selectableItemBackgroundBorderlessResource
}

inline fun ViewManager.rowOneLine(
        dark: Boolean = false,
        crossinline title: TextView.() -> Unit
): LinearLayout {
    return verticalLayout {
        backgroundResource = selectableItemBackgroundResource
        minimumHeight = dip(72)
        padding = dip(16)
        gravity = Gravity.CENTER_VERTICAL or Gravity.LEFT
        textView() {
            materialStylePrimary(dark)
            title()
        }
    }
}

inline fun ViewManager.rowOneLineRadio(
        dark: Boolean = false,
        crossinline title: TextView.() -> Unit,
        crossinline radioButtonInit: RadioButton.() -> Unit
): LinearLayout {
    return linearLayout {
        backgroundResource = selectableItemBackgroundResource
        minimumHeight = dip(72)
        padding = dip(16)
        gravity = Gravity.CENTER_VERTICAL or Gravity.LEFT
        val rad = radioButton() {
            radioButtonInit()
        }.lparams(wrapContent, wrapContent) {
            rightMargin = dip(16)
        }
        textView() {
            materialStylePrimary(dark)
            title()
        }.lparams(0, wrapContent, 1f)

        onClick() {
            rad.performClick()
        }
    }
}

inline fun ViewManager.rowTwoLine(
        dark: Boolean = false,
        crossinline title: TextView.() -> Unit,
        crossinline subtitle: TextView.() -> Unit
): LinearLayout {
    return verticalLayout {
        backgroundResource = selectableItemBackgroundResource
        minimumHeight = dip(72)
        padding = dip(16)
        gravity = Gravity.CENTER_VERTICAL or Gravity.LEFT
        textView() {
            materialStylePrimary(dark)
            title()
        }.lparams { bottomMargin = dip(4) }
        textView() {
            materialStyleTertiary(dark)
            subtitle()
        }
    }
}

inline fun ViewManager.rowTwoLineDrawableRight(
        dark: Boolean = false,
        crossinline title: TextView.() -> Unit,
        crossinline subtitle: TextView.() -> Unit,
        drawable: Drawable
): LinearLayout {
    return linearLayout {
        verticalLayout {
            backgroundResource = selectableItemBackgroundResource
            minimumHeight = dip(72)
            padding = dip(16)
            gravity = Gravity.CENTER_VERTICAL or Gravity.LEFT
            textView() {
                materialStylePrimary(dark)
                title()
            }.lparams { bottomMargin = dip(4) }
            textView() {
                materialStyleTertiary(dark)
                subtitle()
            }
        }.lparams(dip(0), wrapContent) {
            weight = 1f
        }

        imageView(drawable) {
            padding = dip(8)
            gravity = Gravity.CENTER
        }.lparams {
            gravity = Gravity.CENTER
        }
    }
}

inline fun ViewManager.rowTwoLineDrawableLeft(
        dark: Boolean = false,
        crossinline title: TextView.() -> Unit,
        crossinline subtitle: TextView.() -> Unit,
        drawable: Drawable
): LinearLayout {
    return linearLayout {
        imageView(drawable) {
            padding = dip(8)
            gravity = Gravity.CENTER
        }.lparams {
            gravity = Gravity.CENTER
        }

        verticalLayout {
            backgroundResource = selectableItemBackgroundResource
            minimumHeight = dip(72)
            padding = dip(16)
            gravity = Gravity.CENTER_VERTICAL or Gravity.LEFT
            textView() {
                materialStylePrimary(dark)
                title()
            }.lparams { bottomMargin = dip(4) }
            textView() {
                materialStyleTertiary(dark)
                subtitle()
            }
        }.lparams(dip(0), wrapContent) {
            weight = 1f
        }
    }
}

inline fun ViewManager.rowTwoLineAction(
        actionIcon: Int,
        crossinline action: () -> Unit,
        dark: Boolean = false,
        crossinline title: TextView.() -> Unit,
        crossinline subtitle: TextView.() -> Unit
): LinearLayout {
    return linearLayout {
        backgroundResource = selectableItemBackgroundResource
        minimumHeight = dip(72)
        padding = dip(16)
        gravity = Gravity.CENTER
        verticalLayout {
            textView() {
                materialStylePrimary(dark)
                title()
            }.lparams { bottomMargin = dip(4) }
            textView() {
                materialStyleTertiary(dark)
                subtitle()
            }
        }.lparams(0, wrapContent, 1f)

        imageButton(actionIcon) {
            materialStyleAction()
            onClick {
                action()
            }
        }
    }
}

inline fun ViewManager.rowTwoTwoLine(
        dark: Boolean = false,
        crossinline title: TextView.() -> Unit,
        crossinline subtitle: TextView.() -> Unit,
        crossinline rightTop: TextView.() -> Unit,
        crossinline rightBottom: TextView.() -> Unit
): LinearLayout {
    return linearLayout {
        backgroundResource = selectableItemBackgroundResource
        minimumHeight = dip(72)
        padding = dip(16)
        gravity = Gravity.CENTER
        verticalLayout {
            textView() {
                materialStylePrimary(dark)
                title()
            }.lparams { bottomMargin = dip(4) }
            textView() {
                materialStyleTertiary(dark)
                subtitle()
            }
        }.lparams(0, wrapContent, 1f)

        verticalLayout {
            gravity = Gravity.RIGHT
            textView() {
                materialStyleTertiary(dark)
                rightTop()
            }.lparams(wrapContent, wrapContent) { bottomMargin = dip(4) }
            textView() {
                materialStyleTertiary(dark)
                rightBottom()
            }.lparams(wrapContent, wrapContent)
        }.lparams(wrapContent, wrapContent)
    }
}

inline fun ViewManager.rowTwoTwoLineAction(
        actionIcon: Int,
        crossinline action: () -> Unit,
        dark: Boolean = false,
        crossinline title: TextView.() -> Unit,
        crossinline subtitle: TextView.() -> Unit,
        crossinline rightTop: TextView.() -> Unit,
        crossinline rightBottom: TextView.() -> Unit
): LinearLayout {
    return linearLayout {
        backgroundResource = selectableItemBackgroundResource
        minimumHeight = dip(72)
        padding = dip(16)
        gravity = Gravity.CENTER
        verticalLayout {
            textView() {
                materialStylePrimary(dark)
                title()
            }.lparams { bottomMargin = dip(4) }
            textView() {
                materialStyleTertiary(dark)
                subtitle()
            }
        }.lparams(0, wrapContent, 1f)

        verticalLayout {
            gravity = Gravity.RIGHT
            textView() {
                materialStyleTertiary(dark)
                rightTop()
            }.lparams(wrapContent, wrapContent) { bottomMargin = dip(4) }
            textView() {
                materialStyleTertiary(dark)
                rightBottom()
            }.lparams(wrapContent, wrapContent)
        }.lparams(wrapContent, wrapContent)

        imageButton(actionIcon) {
            materialStyleAction()
            onClick {
                action()
            }
        }
    }
}