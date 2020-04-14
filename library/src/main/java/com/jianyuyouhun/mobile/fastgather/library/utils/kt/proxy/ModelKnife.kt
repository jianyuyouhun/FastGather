package com.jianyuyouhun.mobile.fastgather.library.utils.kt.proxy

import com.jianyuyouhun.mobile.fastgather.library.app.BaseManager
import com.jianyuyouhun.mobile.fastgather.library.app.AbstractJApp
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * manager延时加载策略，使用方式
 * {
 *      private val userManager by bindManager(UserManager::class.java)
 * }
 * Created by wangyu on 2017/11/1.
 */

fun <Manager : BaseManager> Any.bindManager(cls: Class<Manager>)
        : ReadOnlyProperty<Any, Manager> = require(cls.name, managerFinder)

private val Any.managerFinder: Any.(String) -> BaseManager?
    get() = { AbstractJApp.getInstance().getManager(it) }

private fun managerNotFound(name: String, desc: KProperty<*>): Nothing =
        throw IllegalStateException("Manager Name $name for '${desc.name}' not found.")

@Suppress("UNCHECKED_CAST")
private fun <T, Manager : BaseManager> require(name: String, finder: T.(String) -> BaseManager?)
        = LazyROP { t: T, desc -> t.finder(name) as Manager? ?: managerNotFound(name, desc) }