package com.leodeleon.popmovies.util

import android.support.design.widget.TabLayout

private val tabStub: (TabLayout.Tab) -> Unit = {}
fun TabLayout.listen(
		onSelected: (TabLayout.Tab) -> Unit = tabStub,
		onReselected: (TabLayout.Tab) -> Unit = tabStub,
		onUnselected: (TabLayout.Tab) -> Unit = tabStub
) {
	this.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
		override fun onTabReselected(tab: TabLayout.Tab) {
			onReselected(tab)
		}

		override fun onTabUnselected(tab: TabLayout.Tab) {
			onUnselected(tab)
		}

		override fun onTabSelected(tab: TabLayout.Tab) {
			onSelected(tab)
		}
	})
}