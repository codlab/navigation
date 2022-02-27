package com.chrynan.navigation.compose

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
@ExperimentalNavigationApi
internal actual fun <Context, Key> InternalNavContainer(
    navigator: BaseComposeNavigatorByContentViewModel<Context, Key>
) {
    val contentKey = rememberSaveable(navigator.keySaver) { mutableStateOf(navigator.initialKey) }

    navigator.keyChanges.collectAsStateIn(state = contentKey)

    val scope = object : ComposeNavigationContentScope<Key> {

        override val navigator: ComposeStackNavigatorByContent<Key> = navigator
    }

    Box {
        navigator.apply {
            scope.content(contentKey.value)
        }
    }

    BackHandler { navigator.goBack() }
}

@Composable
@ExperimentalNavigationApi
internal actual fun <Context, Key, NavigationScope : ComposeNavigationKeyScope<Key>> InternalNavContainer(
    navigator: BaseComposeNavigatorByKeyViewModel<Context, Key, NavigationScope>,
    scope: NavigationScope
) {
    val contentKey = rememberSaveable(navigator.keySaver) { mutableStateOf(navigator.initialKey) }

    navigator.keyChanges.collectAsStateIn(state = contentKey)

    Box {
        navigator.apply {
            scope.content(contentKey.value)
        }
    }

    BackHandler { navigator.goBack() }
}
