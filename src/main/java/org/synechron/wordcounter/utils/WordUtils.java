package org.synechron.wordcounter.utils;

import org.synechron.wordcounter.service.impl.AddAndCountWordService;

public final class WordUtils {
	private WordUtils() {
		// to avoid object creation
	}

	public static AddAndCountWordService countWords(String text) {
		assert (text != null);
		AddAndCountWordService result = new AddAndCountWordService();
		int i = 0;
		while (i < text.length()) {
			while (i < text.length() && !Character.isAlphabetic(text.charAt(i))) {
				i++;
			}
			int bi = i;
			while (i < text.length() && Character.isAlphabetic(text.charAt(i))) {
				i++;
			}
			int ei = i;
			if (bi != ei) {
				String word = text.substring(bi, ei);
				result.add(word, 1);
			}
		}
		return result;
	}

	/**
	 * For more information about using google translate api {@link https://www.google.com/script/start/}
	 * @param word
	 * @return
	 */
	public static String translate(String word) {
		// Assuming of consuming third party library like Google translate API

//        String urlStr = "https://your.google.script.url" +
//                "?q=" + URLEncoder.encode(text, "UTF-8") +
//                "&target=" + langTo +
//                "&source=" + langFrom;
//        URL url = new URL(urlStr);
//        StringBuilder response = new StringBuilder();
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        con.setRequestProperty("User-Agent", "Mozilla/5.0");
//        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//        String inputLine;
//        while ((inputLine = in.readLine()) != null) {
//            response.append(inputLine);
//        }
//        in.close();
		return word;
	}
}
