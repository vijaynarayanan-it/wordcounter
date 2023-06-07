package org.synechron.wordcounter.utils;

public final class WordUtils {
	private WordUtils() {
		// to avoid object creation
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
