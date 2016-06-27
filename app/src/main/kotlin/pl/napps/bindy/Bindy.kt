@file:JvmName("Bindy")
package pl.napps.bindy

import android.app.Activity
import android.content.Context
import android.content.res.XmlResourceParser
import android.graphics.Color
import android.graphics.Movie
import android.view.View
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Created by mnarowski on 2016-06-26.
 */

abstract class BaseBinder<K,V> : ReadOnlyProperty<K,V>{

}

abstract class Binder<T>(id:Int): BaseBinder<Context, T>() {
    private var value:T? = null;

    override fun getValue(thisRef: Context, property: KProperty<*>):T {
        value = value ?: obtain(thisRef,id)
        return value as T;
    }

    protected abstract fun obtain(context: Context, id: Int):T

    private val id = id
}

class bindInt(id:Int) : Binder<Int>(id){
    override fun obtain(context: Context, id: Int): Int {
        return context.resources.getInteger(id)
    }
}

class bindString(id:Int): Binder<String>(id){
    override fun obtain(context: Context, id: Int): String {
        return context.resources.getString(id)
    }

}

class bindColor(id:Int): Binder<Int>(id){
    override fun obtain(context: Context, id: Int): Int {
        return context.resources.getColor(id,context.theme)
    }

}

class bindArray(id:Int) : Binder<Array<String>>(id){
    override fun obtain(context: Context, id: Int): Array<String> {
        return context.resources.getStringArray(id)
    }
}

class bindXml(id:Int): Binder<XmlResourceParser>(id){
    override fun obtain(context: Context, id: Int): XmlResourceParser {
        return context.resources.getXml(id)
    }
}

class bindMovie(id:Int): Binder<Movie>(id){
    override fun obtain(context: Context, id: Int): Movie {
        return context.resources.getMovie(id)
    }
}

class bindView<T>(id:Int): BaseBinder<Activity,T>(){
    override fun getValue(thisRef: Activity, property: KProperty<*>): T {
        return thisRef.findViewById(id) as T;
    }

    private val id = id;

}

