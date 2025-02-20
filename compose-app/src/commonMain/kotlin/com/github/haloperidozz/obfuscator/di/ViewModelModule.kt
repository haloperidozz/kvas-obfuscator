/*
 * Copyright (C) 2025 haloperidozz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.github.haloperidozz.obfuscator.di

import com.github.haloperidozz.obfuscator.ui.viewmodel.MainScreenViewModel
import com.github.haloperidozz.obfuscator.ui.viewmodel.SelectScreenViewModel
import com.github.haloperidozz.obfuscator.ui.viewmodel.shared.SharedTextGeneratorHolder
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    single<SharedTextGeneratorHolder> { SharedTextGeneratorHolder() }

    viewModel<MainScreenViewModel> { MainScreenViewModel(get()) }
    viewModel<SelectScreenViewModel> { SelectScreenViewModel(get(), get()) }
}
