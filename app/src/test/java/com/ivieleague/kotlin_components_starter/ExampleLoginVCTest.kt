package com.ivieleague.kotlin_components_starter

import org.junit.Test

class ExampleLoginVCTest {

    @Test
    fun successfulLogin() {
        val vc = ExampleLoginVC()
        vc.emailObs.value = "test@gmail.com"
        vc.passwordObs.value = "testpass"
        assert(vc.isValid())
        assert(vc.attemptLogin().invoke().isSuccessful())
    }

    @Test
    fun failedLoginEmail() {
        val vc = ExampleLoginVC()
        vc.emailObs.value = "wrong@gmail.com"
        vc.passwordObs.value = "testpass"
        assert(vc.isValid())
        assert(!vc.attemptLogin().invoke().isSuccessful())
    }

    @Test
    fun failedLoginPassword() {
        val vc = ExampleLoginVC()
        vc.emailObs.value = "test@gmail.com"
        vc.passwordObs.value = "wrongpass"
        assert(vc.isValid())
        assert(!vc.attemptLogin().invoke().isSuccessful())
    }

    @Test
    fun emptyEmail() {
        val vc = ExampleLoginVC()
        vc.emailObs.value = ""
        vc.passwordObs.value = "testpass"
        assert(!vc.isValid())
    }

    @Test
    fun invalidEmail() {
        val vc = ExampleLoginVC()
        vc.emailObs.value = "not an email"
        vc.passwordObs.value = "testpass"
        assert(!vc.isValid())
    }

    @Test
    fun emptyPassword() {
        val vc = ExampleLoginVC()
        vc.emailObs.value = "test@gmail.com"
        vc.passwordObs.value = ""
        assert(!vc.isValid())
    }

    @Test
    fun incompletePassword() {
        val vc = ExampleLoginVC()
        vc.emailObs.value = "test@gmail.com"
        vc.passwordObs.value = "test"
        assert(!vc.isValid())
    }
}