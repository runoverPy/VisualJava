mod macros;

use java_lang_system::*;

#[allow(non_snake_case)]
mod java_lang_Object {
    use jni::{JNIEnv, NativeMethod};
    use jni::descriptors::Desc;
    use jni::objects::{JClass, JObject, JStaticFieldID, JString, JValue};
    use jni::strings::JNIString;
    use jni::sys::{jint, jlong, jstring, jboolean, jclass, jobject};

    #[no_mangle]
    pub extern "system" fn Java_java_lang_Object_getClass(env: JNIEnv, object: JObject) -> jclass {
        env.get_object_class(object).expect("Could not retrieve class").into_inner()
    }

    #[no_mangle]
    pub extern "system" fn Java_java_lang_Object_hashCode(env: JNIEnv, object: JObject) -> jint {

        todo!()
    }

    #[no_mangle]
    pub extern "system" fn Java_java_lang_Object_clone(env: JNIEnv, object: JObject) -> jobject {
        todo!()
    }

    #[no_mangle]
    pub extern "system" fn Java_java_lang_Object_notify(env: JNIEnv, object: JObject) {
        todo!()
    }

    #[no_mangle]
    pub extern "system" fn Java_java_lang_Object_notifyAll(env: JNIEnv, object: JObject) {
        todo!()
    }

    #[no_mangle]
    pub extern "system" fn Java_java_lang_Object_wait(env: JNIEnv, object: JObject, time_out_millis: jlong) {
        todo!()
    }
}

mod java_lang_system {
    use jni::{JNIEnv, NativeMethod};
    use jni::descriptors::Desc;
    use jni::objects::{JClass, JObject, JStaticFieldID, JString, JValue};
    use jni::strings::JNIString;
    use jni::sys::{jint, jlong, jstring, jboolean};

    #[no_mangle]
    pub extern "system" fn Java_java_lang_System_registerNatives(env: JNIEnv, class: JClass) {
        // let methods = &[
        //     NativeMethod {
        //         name: JNIString::from("setIn0"),
        //         sig: JNIString::from("(Ljava/io/InputStream;)V"),
        //         fn_ptr: *Java_java_lang_System_setIn0
        //     },
        //     NativeMethod {
        //         name: JNIString::from("setOut0"),
        //         sig: JNIString::from("(Ljava/io/PrintStream;)V"),
        //         fn_ptr: *Java_java_lang_System_setOut0
        //     },
        //     NativeMethod {
        //         name: JNIString::from("setErr0"),
        //         sig: JNIString::from("(Ljava/io/PrintStream;)V"),
        //         fn_ptr: *Java_java_lang_System_setErr0
        //     }
        // ];
        // env.register_native_methods(class, methods).expect("Failed to register native methods");
    }

    #[no_mangle]
    pub extern "system" fn Java_java_lang_System_setIn0(env: JNIEnv, class: JClass, stream: JObject) {
        let out_field_id: JStaticFieldID = env.get_static_field_id(class, "in", "Ljava/io/InputStream;")
            .expect("Cannot access System.in field");
        env.set_static_field(class, out_field_id, JValue::from(stream))
            .expect("Cannot set value of System.in field");
    }

    #[no_mangle]
    pub extern "system" fn Java_java_lang_System_setOut0(env: JNIEnv, class: JClass, stream: JObject) {
        let out_field_id: JStaticFieldID = env.get_static_field_id(class, "out", "Ljava/io/PrintStream;")
            .expect("Cannot access System.out field");
        env.set_static_field(class, out_field_id, JValue::from(stream))
            .expect("Cannot set value of System.out field");
    }

    #[no_mangle]
    pub extern "system" fn Java_java_lang_System_setErr0(env: JNIEnv, class: JClass, stream: JObject) {
        let out_field_id: JStaticFieldID = env.get_static_field_id(class, "err", "Ljava/io/PrintStream;")
            .expect("Cannot access System.err field");
        env.set_static_field(class, out_field_id, JValue::from(stream))
            .expect("Cannot set value of System.err field");
    }

    #[no_mangle]
    pub extern "system" fn Java_java_lang_System_currentTimeMillis(env: JNIEnv, class: JClass) -> jlong {
        use std::time::{SystemTime, UNIX_EPOCH};
        let now = SystemTime::now();
        now.duration_since(UNIX_EPOCH)
            .expect("Clock set to before 1.1.1970;00:00:00")
            .as_millis() as jlong
    }

    #[no_mangle]
    pub extern "system" fn Java_java_lang_System_nanoTime(env: JNIEnv, class: JClass) -> jlong {
        use std::time::{SystemTime, UNIX_EPOCH};
        let now = SystemTime::now();
        now.duration_since(UNIX_EPOCH)
            .expect("Clock set to before 1.1.1970;00:00:00")
            .as_nanos() as jlong
    }

    #[no_mangle]
    pub extern "system" fn Java_java_lang_System_arraycopy(env: JNIEnv, class: JClass, arr1: JObject, pos1: jint, arr2: JObject, pos2: jint, len: jint) {
        todo!()
    }

    #[no_mangle]
    pub extern "system" fn Java_java_lang_System_identityHashCode(env: JNIEnv, class: JClass, object: JObject) -> jint {
        todo!()
    }

    #[no_mangle]
    pub extern "system" fn Java_java_lang_System_mapLibraryName(env: JNIEnv, class: JClass, string: JString) -> jstring {
        todo!()
    }
}
