package marcWeiss.sTracker.component.resourcesNaming;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import marcWeiss.sTracker.component.helper.Utility;
import marcWeiss.sTracker.statistic.component.service.StatisticServiceType;

@Component
public class LinkToRessourceUpdater {

	@Autowired
	ServletContext servletContext;

	@Autowired
	StatisticServiceType statisticService;

	String fullRessourcePathhRegex = "=\"(\\/sTracker\\/resources)(?<filePathNoExtention>[^\"]*)(?<fileExtention>\\.[^\"]*)\"";

	String fullCSSRessourcePathhRegex = "url\\((\\/sTracker\\/resources)(?<filePathNoExtention>.*)(?<fileExtention>\\.[^\\)]*)";

	List<String> regexs = Arrays.asList(fullRessourcePathhRegex, fullCSSRessourcePathhRegex);

	// default constructor
	public LinkToRessourceUpdater() {
		super();
	}

	// find link to resources and update them
	public String updateLinks(String content) {
		content = content.replaceAll("\\$checksum\\$\\d*\\$", "");
		StringBuffer contentBuffer = new StringBuffer(content);
		for (String regex : regexs) {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(contentBuffer);
			while (m.find()) {

				// extract and correct path to file from HTML
				String originalfilePath = m.group("filePathNoExtention") + m.group("fileExtention");

				//initializing object needed to read data
				String resourceContent = null;
				InputStream stream = null;
				try {
					
					// read resource file from webapp/resources folder and provide
					// it as a String
					stream = servletContext.getResourceAsStream("/resources" + originalfilePath);
					resourceContent = Utility.readFromInputStream(stream);

					// produce checksum string from file resource content and insert
					// it in the path resource from original content
					String checkSum = FletcherChecksum.getFletcherForString(resourceContent);
					checkSum = "$checksum$" + checkSum + "$";
					contentBuffer.insert(m.end("filePathNoExtention"), checkSum);
					
				} catch (Exception e) {
					statisticService.persistMethodEvent(e);
					e.printStackTrace();
				} finally {
					if(stream != null){
						try {
							stream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return contentBuffer.toString();
	}

	public static void main(String[] args) throws IOException {
	}

}
