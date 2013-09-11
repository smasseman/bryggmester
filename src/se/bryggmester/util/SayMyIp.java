package se.bryggmester.util;

import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author jorgen.smas@entercash.com
 */
public class SayMyIp {

	@Autowired
	private ServletContext ctx;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@PostConstruct
	public void sayMyIp() throws UnsupportedAudioFileException, IOException,
			LineUnavailableException, InterruptedException {
		String ip = getIp();
		logger.debug("My IP is " + ip);
		List<File> files = new ArrayList<>(ip.length());
		for (char c : ip.toCharArray()) {
			files.add(getAudioFile(c));
		}
		new AudioPlayer().play(files);
		logger.debug("Done playing ip.");
	}

	private File getAudioFile(char c) {
		String name = String.valueOf(c);
		if (c == '.')
			name = "plus";
		return new File(ctx.getRealPath("/audio") + "/" + name + ".wav");
	}

	private String getIp() throws SocketException {
		Enumeration<NetworkInterface> enumerations = NetworkInterface
				.getNetworkInterfaces();
		List<String> result = new ArrayList<String>();
		while (enumerations.hasMoreElements()) {
			NetworkInterface nic = enumerations.nextElement();
			Enumeration<InetAddress> ipadrs = nic.getInetAddresses();
			while (ipadrs.hasMoreElements()) {
				InetAddress ipadr = ipadrs.nextElement();
				if (ipadr instanceof Inet4Address) {
					if (!ipadr.isLoopbackAddress()
							&& !ipadr.getHostAddress().startsWith("169.254.")) {
						result.add(ipadr.getHostAddress());
					}
				}
			}
		}
		Collections.sort(result);
		return result.get(0);

	}
}
