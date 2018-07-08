package com.minek.kotlin.everywhere

import org.junit.Test
import kotlin.internal.Exact
import kotlin.reflect.KProperty1
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

private typealias PropertyPair<K, V> = Pair<KProperty1<K, V>, V>

class TestExact {
    @Test
    fun testExact() {
        data class Person(val name: String, val age: Int)

        fun <T> expectedPropertyPair(match: Boolean, vararg pairs: PropertyPair<T, *>) {
            pairs.forEach { (property, value) ->
                if (match) {
                    assertEquals(property.returnType.classifier, value!!::class)
                } else {
                    assertNotEquals(property.returnType.classifier, value!!::class)
                }
            }
        }


        infix fun <K, V> KProperty1<K, @Exact V>.of(value: V): PropertyPair<K, V> {
            return this to value
        }


        expectedPropertyPair(false, Person::name to 10, Person::age to "12")
        expectedPropertyPair(true, Person::name of "12", Person::age of 12)
    }
}