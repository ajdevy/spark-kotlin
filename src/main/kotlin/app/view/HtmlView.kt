package app.view

abstract class HtmlView {

    override fun toString(): String {
        return getHtml()
    }

    protected abstract fun getHtml(): String
}