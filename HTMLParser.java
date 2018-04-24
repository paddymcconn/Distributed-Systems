import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class HTMLParser {

	private static final String FILENAME = "C:\\Users\\Patrick\\Desktop\\Final Year Project\\test";

	public static void main(String[] args) {

		// for writing and reading to file
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			File file = new File(FILENAME+".txt");

			if (!file.exists()) {
				file.createNewFile();
			}
			
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);
			String url = "https://www.flutter.io/";
			Document doc;
			doc = Jsoup.connect(url).get();
			
			Elements elements = doc.body().select("*");
			String remdups = "";
			for (Element element : elements) {
				if (remdups.equals(element.text())) {
					continue;
				} else {
					bw.write(element.text());
					bw.append("\n");
				}
				remdups = element.text();
			}
			ArrayList<String> urls = GetUrls(doc); // for all the other urls
			/*for(int i =0; i<urls.size(); i++)
			{
				System.out.println(i + "  " + urls.get(i));
			}*/
			MultiScraper(urls); // scraping for the other urls
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* method for getting the Urls from the first entry point */
	public static ArrayList<String> GetUrls(Document Docs) {
		ArrayList<String> Urls = new ArrayList<String>();
		Elements links = Docs.select("a");
		for (Element link : links) {
			String absHref = link.attr("abs:href");
			System.out.println(absHref);
			if (absHref == "")
			{
				continue;
				// skip this loop, its not a Href.
			}
			else
			{
				Urls.add(absHref);
			}
		}
		return Urls;
	}

	public static void MultiScraper(ArrayList<String> ar) {
		BufferedWriter bw = null;
		FileWriter fw = null;
		Document Doc = null;
		for (int i = 0; i < ar.size(); i++) {
			String url = ar.get(i).toString();
			System.out.println(ar.get(i).toString());
			try {
				File file = new File(FILENAME+i+".txt");

				if (!file.exists()) {
					file.createNewFile();
				}
				fw = new FileWriter(file.getAbsoluteFile(), true);
				bw = new BufferedWriter(fw);

				Doc = Jsoup.connect(url).get();

				Elements elements = Doc.body().select("*");
				String removedups = "";
				for (Element element : elements) {

					if (removedups.equals(element.text())) {
						continue;
					} else {
						bw.write(element.text());
						bw.append("\n");
					}
					removedups = element.text();
				} // end for each
			} // end try

			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		} // end arraylist for
	} // end multiscraper
} // end class