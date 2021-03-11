package io.getstream.chat.android.livedata.utils

import io.getstream.chat.android.client.api.models.AndFilterObject
import io.getstream.chat.android.client.api.models.AutocompleteFilterObject
import io.getstream.chat.android.client.api.models.ContainsFilterObject
import io.getstream.chat.android.client.api.models.DistinctFilterObject
import io.getstream.chat.android.client.api.models.EqualsFilterObject
import io.getstream.chat.android.client.api.models.ExistsFilterObject
import io.getstream.chat.android.client.api.models.FilterObject
import io.getstream.chat.android.client.api.models.GreaterThanFilterObject
import io.getstream.chat.android.client.api.models.GreaterThanOrEqualsFilterObject
import io.getstream.chat.android.client.api.models.InFilterObject
import io.getstream.chat.android.client.api.models.LessThanFilterObject
import io.getstream.chat.android.client.api.models.LessThanOrEqualsFilterObject
import io.getstream.chat.android.client.api.models.NeutralFilterObject
import io.getstream.chat.android.client.api.models.NorFilterObject
import io.getstream.chat.android.client.api.models.NotEqualsFilterObject
import io.getstream.chat.android.client.api.models.NotExistsFilterObject
import io.getstream.chat.android.client.api.models.NotInFilterObject
import io.getstream.chat.android.client.api.models.OrFilterObject
import io.getstream.chat.android.client.extensions.snakeToLowerCamelCase
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.client.models.CustomObject
import java.lang.ClassCastException
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

internal fun <T : CustomObject> Collection<T>.filter(filterObject: FilterObject): List<T> =
    filter { filterObject.filter(it) }

@Suppress("UNCHECKED_CAST")
private fun <T : CustomObject> FilterObject.filter(t: T): Boolean = try {
    when (this) {
        is AndFilterObject -> TODO()
        is OrFilterObject -> TODO()
        is NorFilterObject -> TODO()
        is ContainsFilterObject -> t.getMemberPropertyOrExtra(fieldName, List::class)?.contains(value) ?: false
        is AutocompleteFilterObject -> t.getMemberPropertyOrExtra(fieldName, String::class)?.contains(value) ?: false
        is ExistsFilterObject -> t.getMemberPropertyOrExtra(fieldName, Any::class) != null
        is NotExistsFilterObject -> t.getMemberPropertyOrExtra(fieldName, Any::class) == null
        is EqualsFilterObject -> value == t.getMemberPropertyOrExtra(fieldName, value::class)
        is NotEqualsFilterObject -> TODO()
        is GreaterThanFilterObject -> TODO()
        is GreaterThanOrEqualsFilterObject -> TODO()
        is LessThanFilterObject -> TODO()
        is LessThanOrEqualsFilterObject -> TODO()
        is InFilterObject -> TODO()
        is NotInFilterObject -> TODO()
        is DistinctFilterObject -> (t as? Channel)?.let { channel ->
            channel.id.startsWith("!members") &&
                channel.members.size == memberIds.size &&
                channel.members.map { it.user.id }.containsAll(memberIds)
        } ?: false
        NeutralFilterObject -> true
    }
} catch (e: ClassCastException) {
    false
}

@Suppress("UNCHECKED_CAST")
private fun <T : Any> CustomObject.getMemberPropertyOrExtra(name: String, clazz: KClass<out T>): T? =
    name.snakeToLowerCamelCase().let { fieldName ->
        this::class.memberProperties.firstOrNull { it.name == fieldName }?.getter?.call(this)?.cast(clazz)
            ?: extraData[name] as? T
    }

private fun <T : Any> Any.cast(clazz: KClass<out T>): T = clazz.javaObjectType.cast(this)!!
