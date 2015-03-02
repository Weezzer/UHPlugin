/**
 *  Plugin UltraHardcore Reloaded (UHPlugin)
 *  Copyright (C) 2013 azenet
 *  Copyright (C) 2014-2015 Amaury Carrade
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see [http://www.gnu.org/licenses/].
 */
package me.azenet.UHPlugin.utils;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class CommandUtils {

	/**
	 * Returns the args without the first item.
	 *
	 * @param args The arguments sent to the parent command.
	 * @return The arguments to send to the child command.
	 */
	public static String[] getSubcommandArguments(String[] args) {
		if(args.length <= 1) {
			return new String[0];
		}

		return Arrays.copyOfRange(args, 1, args.length);
	}


	/**
	 * Returns a list of autocompletion suggestions based on what the user typed and on a list of
	 * available commands.
	 *
	 * @param typed What the user typed. This string needs to include <em>all</em> the words typed.
	 * @param suggestionsList The list of the suggestions.
	 * @param numberOfWordsToIgnore If non-zero, this number of words will be ignored at the beginning of the string. This is used to handle multiple-words autocompletion.
	 *
	 * @return The list of matching suggestions.
	 */
	public static List<String> getAutocompleteSuggestions(String typed, List<String> suggestionsList, int numberOfWordsToIgnore) {
		List<String> list = new ArrayList<String>();

		// For each suggestion:
		//  - if there isn't any world to ignore, we just compare them;
		//  - else, we removes the correct number of words at the beginning of the string;
		//    then, if the raw suggestion matches the typed text, we adds to the suggestion list
		//    the filtered suggestion, because the Bukkit's autocompleter works on a “per-word” basis.

		for(String rawSuggestion : suggestionsList) {
			String suggestion;

			if(numberOfWordsToIgnore == 0) {
				suggestion = rawSuggestion;
			}
			else {
				// Not the primary use, but, hey! It works.
				suggestion = UHUtils.getStringFromCommandArguments(rawSuggestion.split(" "), numberOfWordsToIgnore);
			}

			if(rawSuggestion.toLowerCase().startsWith(typed.toLowerCase())) {
				list.add(suggestion);
			}
		}

		Collections.sort(list, Collator.getInstance());

		return list;
	}

	/**
	 * Returns a list of autocompletion suggestions based on what the user typed and on a list of
	 * available commands.
	 *
	 * @param typed What the user typed.
	 * @param suggestionsList The list of the suggestions.
	 *
	 * @return The list of matching suggestions.
	 */
	public static List<String> getAutocompleteSuggestions(String typed, List<String> suggestionsList) {
		return getAutocompleteSuggestions(typed, suggestionsList, 0);
	}
}
