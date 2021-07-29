package vladyslav.yarokh.ktxfragmentresultapi.ui.home

import vladyslav.yarokh.ktxfragmentresultapi.ui.data.ProductItem

interface OnProductItemClick {
    fun onProductClick(product: ProductItem)
}