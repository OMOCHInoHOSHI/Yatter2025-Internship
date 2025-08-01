package com.dmm.bootcamp.yatter2025.common.ddd

abstract class Entity<out T : Identifier<*>>(open val id: T) {

  override fun equals(other: Any?): Boolean {
    return (other is Entity<*>) && other.id == id
  }

  override fun hashCode(): Int {
    return id.hashCode()
  }
}
