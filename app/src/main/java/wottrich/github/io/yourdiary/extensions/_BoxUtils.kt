package wottrich.github.io.yourdiary.extensions

import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import wottrich.github.io.yourdiary.box.ObjectBox


inline fun <reified T> box () : Box<T> = ObjectBox.boxStore.boxFor()

inline fun <reified T> boxList () : List<T> = ObjectBox.boxStore.boxFor<T>().all
