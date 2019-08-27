package marcWeiss.sTracker.component.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Properties;

import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class Utility {

	static int count = 0;

	public static Properties getProp(String path) {

		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream(path);
			prop.load(input);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return prop;
	}

	public static String FileToString(String path) throws IOException {
		return new String(Files.readAllBytes(Paths.get(path)));

	}

	public static String FileToString(File file) throws IOException {
		return new String(Files.readAllBytes(file.toPath()));

	}

	public static String resourceToString(Resource resource) throws IOException {
		return new String(Files.readAllBytes(resource.getFile().toPath()));

	}

	public static void StringToFile(String string, String path) throws IOException {
		Files.write(Paths.get(path + "_" + (count++)), string.getBytes());
	}

	public static String readFromInputStream(InputStream inputStream) throws IOException, NullPointerException {
		StringBuilder resultStringBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = br.readLine()) != null) {
				resultStringBuilder.append(line).append("\n");
			}
		}
		return resultStringBuilder.toString();
	}

	public static String determineTargetUrl() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken || authentication == null)
			{
			return "/home";
			}
		boolean isUser = false;
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
				isUser = true;
				break;
			} else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}

		if (isAdmin) {
			return "redirect:/adminPage/users";
		} else if (isUser) {
			return "redirect:/view/currentList";
		} else {
			throw new IllegalStateException();
		}
	}
}
